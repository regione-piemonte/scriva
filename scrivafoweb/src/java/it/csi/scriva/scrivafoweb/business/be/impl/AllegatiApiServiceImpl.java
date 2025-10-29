/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.AllegatiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.AllegatiIstanzaApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.GenericInputParDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.dto.ObjectListFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Allegati api service.
 */
@Component
public class AllegatiApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements AllegatiApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Allegati istanza api service helper.
     */
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
    public Response getAllegati(Long idIstanza, String codAllegato, String codCategoriaAllegato, String flgCancLogica,
                                Integer offset, Integer limit, String sort, Boolean system, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametro in input idIstanza [" + idIstanza + "] - codCategoriaAllegato [" + codCategoriaAllegato + "] - flgCancLogica [" + flgCancLogica + "]");
        ObjectListFoDTO allegatoIstanzaList = null;
        try {
            allegatoIstanzaList = allegatiIstanzaApiServiceHelper.getAllegati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, codAllegato, codCategoriaAllegato, flgCancLogica, offset, limit, sort, system);
            if (null == allegatoIstanzaList) {
                String errorMessage = "Risorsa non trovata.";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                logError(className, err);
                return Response.serverError().entity(err).status(404).build();
            }
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(allegatoIstanzaList.getObjectList()).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_PAGINATION_INFO, allegatoIstanzaList.getPaginationInfoHeader()).build();
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
    public Response getFileAllegati(String xRequestAuth, String xRequestId, Long idIstanza, String codAllegato, String outputFormat, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametro in input idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - outputFormat [" + outputFormat + "]");
        FileFoDTO fileFoDTO = null;
        try {
            fileFoDTO = allegatiIstanzaApiServiceHelper.getFileAllegati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, codAllegato, outputFormat);
            if ("PDF".equalsIgnoreCase(outputFormat)) {
                return Response.noContent().build();
            }
            return Response.ok(fileFoDTO.getFile(), MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_CONTENT_DISPOSITION, fileFoDTO.getContentDispositionHeader()).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets file allegati selezionati.
     *
     * @param codiciAllegato  the codici allegato
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param codAllegato     the cod allegato
     * @param idOsservazione  the id osservazione
     * @param outputFormat    the output format
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the file allegati selezionati
     * @throws Exception the exception
     */
    @Override
    public Response getFileAllegatiSelezionati(GenericInputParDTO codiciAllegato, String xRequestAuth, String xRequestId, Long idIstanza, String codAllegato, Long idOsservazione, String outputFormat,
                                               SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()) + "\ncodiciAllegato :\n" + codiciAllegato);
        FileFoDTO fileFoDTO = null;
        try {
            fileFoDTO = allegatiIstanzaApiServiceHelper.getFileAllegatiSelezionati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiciAllegato, idIstanza, codAllegato, idOsservazione, outputFormat);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(fileFoDTO.getFile(), MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_CONTENT_DISPOSITION, fileFoDTO.getContentDispositionHeader()).build();
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
        logBeginInfo(className, "Parametro in input uuidIndex [" + uuidIndex + "]");
        FileFoDTO fileFoDTO = null;
        try {
            fileFoDTO = allegatiIstanzaApiServiceHelper.getAllegatiByUuidIndex(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uuidIndex);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(fileFoDTO.getFile(), MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_CONTENT_DISPOSITION, fileFoDTO.getContentDispositionHeader()).build();
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
        logBeginInfo(className, formDataInput);
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = new ArrayList<>();
        try {
            logInfo(className, "uploadIndexFile", "TIMER: inizio chiamata di upload");
            listAllegatiIstanza = allegatiIstanzaApiServiceHelper.uploadIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), formDataInput);
            logInfo(className, "uploadIndexFile", "TIMER: fine chiamata di upload");
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
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
        logBeginInfo(className, allegatoIstanza);
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = new ArrayList<>();
        try {
            listAllegatiIstanza = allegatiIstanzaApiServiceHelper.updateIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), allegatoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
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
        logBeginInfo(className, "Parametro in input uuidIndex [" + uuidIndex + "]");
        try {
            allegatiIstanzaApiServiceHelper.deleteIndexFile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uuidIndex);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
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
        logBeginInfo(className, idIstanza);
        List<CategoriaAllegatoDTO> listCategoriaAllegati = new ArrayList<>();
        try {
            listCategoriaAllegati = allegatiIstanzaApiServiceHelper.getCategoriaAllegatoMandatoryByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
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
        logBeginInfo(className, idIstanza);
        try {
            allegatiIstanzaApiServiceHelper.getCategoriaAllegatoMandatoryDelegaFirmataByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
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
        logBeginInfo(className, idIstanza);
        List<CategoriaAllegatoDTO> listCategoriaAllegati = new ArrayList<>();
        try {
            listCategoriaAllegati = allegatiIstanzaApiServiceHelper.getCategoriaAllegatoMandatoryAdempimentoByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return getResponseWithSharedHeaders(listCategoriaAllegati, allegatiIstanzaApiServiceHelper);
    }

    /**
     * Search allegati response.
     *
     * @param requestBody     the request body
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response searchAllegati(Map<String, Object> requestBody, Integer offset, Integer limit, String sort, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "sort [" + sort + "] - offset [" + offset + "] - limit [" + limit + "]\nrequestBody:\n" + requestBody);
        return getResponsePOST(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

}