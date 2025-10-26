/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import it.csi.scriva.scrivaapisrv.business.be.AllegatiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AllegatiIstanzaApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.dto.FileFoDTO;
import it.csi.scriva.scrivaapisrv.dto.ObjectListFoDTO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AllegatiApiServiceImpl extends AbstractApiServiceImpl implements AllegatiApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    @Autowired
    AllegatiIstanzaApiServiceHelper allegatiIstanzaApiServiceHelper;

    /**
     * Estrazione degli allegati presenti su SCRIVA. Nessun filtro applicato
     * Estrazione dei METADATI dei documenti alleati ad una specifica istanza passata in input (scriva_t_allegato_istanza)
     *
     * @param idIstanza       idIstanza
     * @param offset          offset
     * @param limit           limit
     * @param sort            sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAllegati(Long idIstanza, Integer offset, Integer limit, String sort, Boolean system, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza [" + idIstanza + "] - offset [" + offset + "] - limit [" + limit + "] - sort [" + sort + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        ObjectListFoDTO allegatoIstanzaList = null;
        try {
            allegatoIstanzaList = allegatiIstanzaApiServiceHelper.getAllegati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, null, offset, limit, sort, system);
            if (null == allegatoIstanzaList) {
                String errorMessage = "Risorsa non trovata.";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
                LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
                return Response.serverError().entity(err).status(404).build();
            }
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(allegatoIstanzaList.getObjectList()).header(HttpHeaders.CONTENT_ENCODING, "identity").header("PaginationInfo", allegatoIstanzaList.getPaginationInfoHeader()).build();
    }

    /**
     * Generazione csv con elenco allegati associati all’istanza.
     * Caricamento su index del pdf con l'elenco degli allegati.
     * Generazione zip con file allegati associati all’istanza.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param outputFormat    the output format
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getFileAllegati(String xRequestAuth, String xRequestId, Long idIstanza, String outputFormat, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza [" + idIstanza + "] - outputFormat [" + outputFormat + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        FileFoDTO fileFoDTO = null;
        try {
            fileFoDTO = allegatiIstanzaApiServiceHelper.getFileAllegati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, outputFormat);
            if ("PDF".equalsIgnoreCase(outputFormat)) {
                return Response.noContent().build();
            }
            return Response.ok(fileFoDTO.getFile(), MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_ENCODING, "identity").header("Content-Disposition", fileFoDTO.getContentDispositionHeader()).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
    }

    /**
     * Rappresenta il download da INDEX di un documento specifico.
     * Oltre al file binario vengono restituiti in output i metadati del documento, letti dalla SCRIVA_T_ALLEGATO_ISTANZA.
     *
     * @param uuidIndex       uuidIndex
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAllegatiByUuidIndex(String uuidIndex, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input uuidIndex [" + uuidIndex + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        FileFoDTO fileFoDTO = null;
        try {
            fileFoDTO = allegatiIstanzaApiServiceHelper.getAllegatiByUuidIndex(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uuidIndex);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AllegatiApiServiceImpl::getAllegatiByUuidIndex] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AllegatiApiServiceImpl::getAllegatiByUuidIndex] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AllegatiApiServiceImpl::getAllegatiByUuidIndex] END");
        }
        return Response.ok(fileFoDTO.getFile(), MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_ENCODING, "identity").header("Content-Disposition", fileFoDTO.getContentDispositionHeader()).build();
    }

    /**
     * Servizio per il caricamento di un documento allegato all’istanza su piattaforma INDEX.
     * Il servizio si occupa delle seguenti attività:
     * - Caricamento documento su INDEX
     * - Eventuale verifica firma documento e aggiornamento aspect firma
     * - Recupero metadati documento su INDEX
     * - Inserimento metadati documento su SCRIVA
     *
     * @param formDataInput   MultipartFormDataInput
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response uploadIndexFile(MultipartFormDataInput formDataInput, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AllegatiApiServiceImpl::uploadIndexFile] BEGIN");
        LOGGER.debug("[AllegatiApiServiceImpl::uploadIndexFile] Parametro in input formDataInput [" + formDataInput + "]");
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = new ArrayList<>();
        try {
            listAllegatiIstanza = allegatiIstanzaApiServiceHelper.uploadIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), formDataInput);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AllegatiApiServiceImpl::uploadIndexFile] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AllegatiApiServiceImpl::uploadIndexFile] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AllegatiApiServiceImpl::uploadIndexFile] END");
        }
        return getResponseWithSharedHeaders(listAllegatiIstanza, allegatiIstanzaApiServiceHelper);
    }

    /**
     * Questo metodo serve per aggiornare i metadati di un documento già presente su INDEX e su SCRIVA.
     * Può essere utilizzato anche per la cancellazione logica di un documento, in quanto è rappresentata dall’aggiornamento dei metadati DATA_CANCELLAZIONE = sysdate e FLG_CANCELLATO = 1 su scriva e dell’aspect "scriva:cancellato" del documento corrispondente su INDEX
     *
     * @param allegatoIstanza AllegatoIstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateIndexFile(AllegatoIstanzaExtendedDTO allegatoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AllegatiApiServiceImpl::updateIndexFile] BEGIN");
        LOGGER.debug("[AllegatiApiServiceImpl::updateIndexFile] Parametro in input allegatoIstanza [" + allegatoIstanza + "]");
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = new ArrayList<>();
        try {
            listAllegatiIstanza = allegatiIstanzaApiServiceHelper.updateIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), allegatoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AllegatiApiServiceImpl::updateIndexFile] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AllegatiApiServiceImpl::updateIndexFile] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AllegatiApiServiceImpl::updateIndexFile] END");
        }
        return getResponseWithSharedHeaders(listAllegatiIstanza, allegatiIstanzaApiServiceHelper);
    }

    /**
     * Il servizio permette di eliminare FISICAMENTE un documento da SCRIVA e da INDEX.
     *
     * @param uuidIndex       uuidIndex
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteIndexFile(String uuidIndex, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AllegatiApiServiceImpl::deleteIndexFile] BEGIN");
        LOGGER.debug("[AllegatiApiServiceImpl::deleteIndexFile] Parametro in input uuidIndex [" + uuidIndex + "]");
        try {
            allegatiIstanzaApiServiceHelper.deleteIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uuidIndex);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AllegatiApiServiceImpl::deleteIndexFile] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AllegatiApiServiceImpl::deleteIndexFile] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AllegatiApiServiceImpl::deleteIndexFile] END");
        }
        return Response.noContent().build();
    }

    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti appartenenti alle tipologie obbligatorie previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie obbligatorie mancanti.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategoriaAllegatoMandatoryByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] BEGIN");
        LOGGER.debug("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] Parametro in input idIstanza [" + idIstanza + "]");
        List<CategoriaAllegatoDTO> listCategoriaAllegati = new ArrayList<>();
        try {
            listCategoriaAllegati = allegatiIstanzaApiServiceHelper.getCategoriaAllegatoMandatoryByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] END");
        }
        return getResponseWithSharedHeaders(listCategoriaAllegati, allegatiIstanzaApiServiceHelper);
    }

    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti di delega qualora tra i soggetti vi sia un delegato con firma.
     * Se l’esito è negativo sarà presentato un errore.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response checkAllegatoMandatoryDelegaFirmataByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        try {
            allegatiIstanzaApiServiceHelper.getCategoriaAllegatoMandatoryDelegaFirmataByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryByIdIstanza] END");
        }
        return getResponseWithSharedHeaders(null, allegatiIstanzaApiServiceHelper, 204);
    }

    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti appartenenti alle categorie non previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie obbligatorie mancanti.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategoriaAllegatoMandatoryAdempimentoByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryAdempimentoByIdIstanza] BEGIN");
        LOGGER.debug("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryAdempimentoByIdIstanza] Parametro in input idIstanza [" + idIstanza + "]");
        List<CategoriaAllegatoDTO> listCategoriaAllegati = new ArrayList<>();
        try {
            listCategoriaAllegati = allegatiIstanzaApiServiceHelper.getCategoriaAllegatoMandatoryAdempimentoByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryAdempimentoByIdIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryAdempimentoByIdIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AllegatiApiServiceImpl::getCategoriaAllegatoMandatoryAdempimentoByIdIstanza] END");
        }
        return getResponseWithSharedHeaders(listCategoriaAllegati, allegatiIstanzaApiServiceHelper);
    }

}