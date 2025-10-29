/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivabesrv.business.be.AllegatiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.index.dto.Node;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIntegrazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CategoriaAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AllegatiService;
import it.csi.scriva.scrivabesrv.business.be.service.CosmoService;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaService;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GenericInputParDTO;
import it.csi.scriva.scrivabesrv.dto.IndexMetadataPropertyDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PaginationHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.SearchAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.VerificaFirmaDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ClasseAllegatoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.manager.AllegatiManager;
import it.csi.scriva.scrivabesrv.util.manager.IstanzaAttoreManager;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Allegati api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AllegatiApiServiceImpl extends BaseApiServiceImpl implements AllegatiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AllegatoIstanzaDAO allegatoIstanzaDAO;

    @Autowired
    private AllegatoIntegrazioneDAO allegatoIntegrazioneDAO;

    @Autowired
    private CategoriaAllegatoDAO categoriaAllegatoDAO;

    @Autowired
    private SoggettoIstanzaDAO soggettoIstanzaDAO;

    @Autowired
    private AllegatiManager allegatiManager;

    @Autowired
    private IstanzaAttoreManager istanzaAttoreManager;

    @Autowired
    private AllegatiService allegatiService;

    @Autowired
    private ConfigurazioneDAO configurazioneDAO;
    
    @Autowired
    private IstanzaDAO istanzaDAO;
    
    /**
     * The Cosmo service.
     */
    @Autowired
    CosmoService cosmoService;

    /**
     * The Istanza service.
     */
    @Autowired
    IstanzaService istanzaService;

    /**
     * Estrazione dei METADATI dei documenti alleati ad una specifica istanza passata in input (scriva_t_allegato_istanza)
     *
     * @param xRequestAuth          the x request auth
     * @param xRequestId            the x request id
     * @param idIstanza             the id istanza
     * @param codAllegato           the cod allegato
     * @param codClasseAllegato     the cod classe allegato
     * @param codCategoriaAllegato  the cod categoria allegato
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param flgCancLogica         the flg canc logica
     * @param flgLinkDocumento      the flg link documento
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param system                the system
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     */
    @Override
    public Response loadAllegati(String xRequestAuth, String xRequestId,
                                 Long idIstanza,
                                 String codAllegato,
                                 String codClasseAllegato,
                                 String codCategoriaAllegato,
                                 String codTipologiaAllegato,
                                 Long idIstanzaOsservazione,
                                 String flgCancLogica,
                                 String flgLinkDocumento,
                                 Integer offset, Integer limit, String sort, Boolean system, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        logDebug(className, "idIstanza [" + idIstanza + "] - codCategoriaAllegato [" + codCategoriaAllegato + "] - flgCancLogica [" + flgCancLogica + "] - flgLinkDocumento [" + flgLinkDocumento + "]");
        attoreScriva = getAttoreScriva(httpHeaders);
        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatiService.loadAllegatiByIdIstanza(idIstanza, codAllegato, codClasseAllegato, codCategoriaAllegato, codTipologiaAllegato, idIstanzaOsservazione, flgCancLogica, flgLinkDocumento, offset, limit, sort, system, attoreScriva);
        PaginationHeaderDTO paginationHeader = offset != null && limit != null ? getPaginationHeader(allegatoIstanzaList, offset, limit) : null;
        return Response.ok(allegatoIstanzaList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).header("PaginationInfo", paginationHeader != null ? paginationHeader.getMap() : null).build();
    }

    /**
     * Estrazione dei METADATI dei documenti alleati ad una specifica istanza passata in input (scriva_t_allegato_istanza)
     *
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     * @throws GenericException
     */
	@Override
	public Response searchAllegati(SearchAllegatoDTO searchCriteria, Integer offset, Integer limit, String sort,
								   SecurityContext securityContext,
								   HttpHeaders httpHeaders,
								   HttpServletRequest httpRequest) throws GenericException
	{
	        logBegin(className);
	        logDebug(className, "searchCriteria [" + searchCriteria + "]");
	        attoreScriva = getAttoreScriva(httpHeaders);
	        List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList =
	        	allegatiService.loadAllegatiByIdIstanza(searchCriteria.getIdIstanza(), searchCriteria.getCodAllegato(),
	        		searchCriteria.getCodClasseAllegato(), searchCriteria.getCodCategoriaAllegato(), searchCriteria.getCodTipologiaAllegato(),
	        		searchCriteria.getIdOsservazione(), searchCriteria.getFlgCancLogica(), searchCriteria.getFlgLinkDocumento(),
	        		offset, limit, sort, searchCriteria.getSys(), attoreScriva);
	        PaginationHeaderDTO paginationHeader = offset != null && limit != null ? getPaginationHeader(allegatoIstanzaList, offset, limit) : null;
	        return Response.ok(allegatoIstanzaList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).header("PaginationInfo", paginationHeader != null ? paginationHeader.getMap() : null).build();
	}

    /**
     * Generazione csv con elenco allegati associati all’istanza.
     * Generazione pdf con elenco allegati associati all’istanza.
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
    public Response loadFileAllegati(String xRequestAuth, String xRequestId, Long idIstanza, String codAllegato, Long idOsservazione, String outputFormat, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - outputFormat [" + outputFormat + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }

        File file;
        ErrorDTO error = null;
        try {
            file = allegatiService.getFileAllegati(idIstanza, codAllegato, idOsservazione, outputFormat, attoreScriva);
            if (file == null) {
            	
                error = getErrorManager().getError("500", "E083", null, null, null);
                setPlaceHolderValues(error, idIstanza);

                logError(className, "File not created");
                return Response.serverError().entity(error).status(500).build();
            }
            String fileName = file.getName();
            if (StringUtils.isNotBlank(codAllegato)) {
                List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(null, codAllegato, null, null, null, null, "TRUE", Boolean.FALSE, null, null, null, Boolean.TRUE, attoreScriva.getComponente());
                fileName = allegatoIstanzaList != null && !allegatoIstanzaList.isEmpty() ? allegatoIstanzaList.get(0).getNomeAllegato() : "none";
            }
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
        } catch (GenericException e) {
            if (e.getError() != null) {
                logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
            }
            logError(className, "idIstanza [" + idIstanza + "]\n" + e);
            error = getErrorManager().getError("500", "E083", null, null, null);
            setPlaceHolderValues(error, idIstanza);
            return Response.serverError().entity(error).status(500).build();
        } catch (Exception e) {
            logError(className, "idIstanza [" + idIstanza + "]\n" + e);
            error = getErrorManager().getError("500", "E083", null, null, null);
            setPlaceHolderValues(error, idIstanza);
            return Response.serverError().entity(error).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response loadFileAllegatiSelezionati(GenericInputParDTO codiciAllegato, String xRequestAuth, String xRequestId, Long idIstanza, String codAllegato, Long idOsservazione, String outputFormat,
                                                SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codAllegato [" + codAllegato + "] - outputFormat [" + outputFormat + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }

        File file;
        ErrorDTO error = null;
        try {
            file = allegatiService.getFileAllegatiList(idIstanza, codiciAllegato, idOsservazione, outputFormat, attoreScriva);
            if (file == null) {   
                error = getErrorManager().getError("500", "E083", null, null, null);
                setPlaceHolderValues(error, idIstanza);

                logError(className, "File not created");
                return Response.serverError().entity(error).status(500).build();
            }
            String fileName = file.getName();
            ;
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
        } catch (GenericException e) {
            if (e.getError() != null) {
                logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
            }
            logError(className, "idIstanza [" + idIstanza + "]\n" + e);
            error = getErrorManager().getError("500", "E083", null, null, null);
            setPlaceHolderValues(error, idIstanza);
            return Response.serverError().entity(error).status(500).build();
        } catch (Exception e) {
            logError(className, "idIstanza [" + idIstanza + "]\n" + e);
            error = getErrorManager().getError("500", "E083", null, null, null);
            setPlaceHolderValues(error, idIstanza);
            return Response.serverError().entity(error).status(500).build();
        } finally {
            logEnd(className);
        }
    }


    /**
     * Rappresenta il download da INDEX di un documento specifico.
     * Oltre al file binario vengono restituiti in output i metadati del documento, letti dalla SCRIVA_T_ALLEGATO_ISTANZA.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param uuidIndex       uuidIndex
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAllegatiByUuidIndex(String uuidIndex, String xRequestAuth, String xRequestId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "uuidIndex [" + uuidIndex + "]");
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = allegatoIstanzaDAO.loadAllegatoIstanzaByUuidIndex(uuidIndex);
        if (listAllegatiIstanza == null) {
            logError(className, "uuidIndex [" + uuidIndex + "] - Allegato non trovato su DB Scriva");
            logEnd(className);
            return getResponseError(className, 500, "E100", null, null);
        }
        Response response = setAttoreRight(httpHeaders, listAllegatiIstanza.get(0).getIdIstanza());
        if (response != null) {
            return response;
        }

        // recupero file da index
        File file = allegatiManager.getFileFromIndexByUuid(uuidIndex);
        if (file == null) {
            logError(className, "uuidIndex [" + uuidIndex + "] - Errore nel recupero file da index");
            logEnd(className);
            return getResponseError(className, 500, "E100", null, null);
        }
        logEnd(className);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + listAllegatiIstanza.get(0).getNomeAllegato() + "\"").build();
    }

    /**
     * Servizio per il caricamento di un documento allegato all’istanza su piattaforma INDEX.
     * Il servizio si occupa delle seguenti attività:
     * - Caricamento documento su INDEX
     * - Eventuale verifica firma documento e aggiornamento aspect firma
     * - Recupero metadati documento su INDEX
     * - Inserimento metadati documento su SCRIVA
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param formDataInput   MultipartFormDataInput
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response uploadIndexFile(String xRequestAuth, String xRequestId, MultipartFormDataInput formDataInput, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "formDataInput :\n" + formDataInput + "\n");
        logInfo(className, "uploadIndexFile", "TIMER: BEGIN");
      
        List<AllegatoIstanzaExtendedDTO> allegatiIstanzaList;
        VerificaFirmaDTO verificaFirma = new VerificaFirmaDTO();
        Boolean flgCancellato = null;
        String uuidIndex = null;
        ErrorDTO error = null;
        AllegatoIstanzaExtendedDTO allegatoIstanza = null;
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            error = e.getError();
            return getResponseError(className, Integer.parseInt(error.getStatus()), error.getCode(), error.getTitle(), error.getDetail());
        }

        try {
            String allegatoIstanzaJson = formDataInput.getFormDataPart("allegatoIstanza", String.class, null);
            File file = formDataInput.getFormDataPart("file", File.class, null);
            String fileName = allegatiManager.getFileName(formDataInput, "file");

            // Verifica parametri obbligatori
            error = this.validateFormData(file, allegatoIstanzaJson);
            if (null != error) {
                logError(className, "allegatoIstanzaJson\n" + allegatoIstanzaJson + "\n" + error);
                return getResponseError(className, Integer.parseInt(error.getStatus()), error.getCode(), error.getTitle(), error.getDetail());
            }

            // Recupero oggetto dalla stringa
            ObjectMapper mapper = new ObjectMapper();
            allegatoIstanza = mapper.readValue(allegatoIstanzaJson, AllegatoIstanzaExtendedDTO.class);

          /*Response response = setAttoreRight(httpHeaders, allegatoIstanza.getIdIstanza(), Boolean.TRUE);
            if (response != null) {
                return response;
            }*/

            // Validazione rispetto a quanto previsto per l'adempimento dell'istanza
            Map<String, Object> validateMap = allegatiService.validateAllegato(allegatoIstanza, attoreScriva);
            error = (ErrorDTO) validateMap.get("error");

            if (null != error) {
                logError(className, "allegatoIstanzaJson\n" + allegatoIstanzaJson + "\n" + error);
                return getResponseError(className, Integer.parseInt(error.getStatus()), error.getCode(), error.getTitle(), error.getDetail());
            }

            allegatoIstanza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
            AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato = (AdempimentoTipoAllegatoExtendedDTO) validateMap.get("adempimentoTipoAllegato");
            IstanzaExtendedDTO istanza = (IstanzaExtendedDTO) validateMap.get("istanza");

            // Verifica nome file duplicato
            error = allegatiManager.verifyDuplicateFilename(istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento(), istanza.getIdIstanza(), fileName);
            if (null != error) {
                logError(className, "allegatoIstanzaJson\n" + allegatoIstanzaJson + "\n" + error);
                return getResponseError(className, Integer.parseInt(error.getStatus()), error.getCode(), error.getTitle(), error.getDetail());
            }

            // Verifica validità estensione file
            error = allegatiManager.validateFileExtension(fileName, istanza.getAdempimento().getIdAdempimento());
            if (null != error) {
                logError(className, "allegatoIstanzaJson\n" + allegatoIstanzaJson + "\n" + error);
                return getResponseError(className, Integer.parseInt(error.getStatus()), error.getCode(), error.getTitle(), error.getDetail());
            }

            // caricamento file su Index
            logInfo(className, "uploadIndexFile", "TIMER: inizio caricamento file su Index: begin");
            uuidIndex = allegatiManager.uploadFileOnIndex(istanza, file, fileName);
            logInfo(className, "uploadIndexFile", "TIMER: fine caricamento file su Index: end");
          
            if (uuidIndex == null || StringUtils.isBlank(uuidIndex)) {
                logError(className, "allegatoIstanzaJson\n" + allegatoIstanzaJson + "\nErrore durante il caricamento dei file su index.");
                return getResponseError(className, 400, "I011", "Errore durante il caricamento dei file su index.", null);
            }

            // verifica firma
            if (adempimentoTipoAllegato != null && adempimentoTipoAllegato.getFlgFirmaDigitale()) {

                // chiamata index di verifica firma digitale
                verificaFirma = StringUtils.isNotBlank(uuidIndex) ? allegatiManager.verificaFirma(uuidIndex, null) : null;

                // verifica se la mancanza di firma digitale è bloccante
                if (Boolean.TRUE.equals(adempimentoTipoAllegato.getFlgFirmaNonValidaBloccante()
                        && verificaFirma != null)
                        && (verificaFirma.getIndFirma() != null && (verificaFirma.getIndFirma() == 1 || (verificaFirma.getIndFirma() == 3 && (verificaFirma.getErrorCode() == 3 || verificaFirma.getErrorCode() == 4))))) {
                    // cancellazione file da index
                    allegatiManager.deleteContenutoByUuid(uuidIndex);
                    logError(className, "allegatoIstanzaJson\n" + allegatoIstanzaJson + "\nRisultano delle anomalie nel documento firmato digitalmente.");
                    error = getErrorManager().getError("500", "E015", "Risultano delle anomalie nel documento firmato digitalmente.", null, null);
                    error.setTitle(error.getTitle().replace("{PH_NOME_ALLEGATO}", "'" + fileName + "'"));
                    return getResponseError(className, error);
                    //return getResponseError(className, 400, "E015", "Risultano delle anomalie nel documento firmato digitalmente.", null);
                }
            }

            // recupero metadata da index
            Node indexNode = allegatiManager.getMetadataIndexByUuid(uuidIndex);

            // populate allegato istanza
            allegatiManager.populateAllegatoIstanza(allegatoIstanza, indexNode, uuidIndex, fileName, file.length(), verificaFirma != null ? verificaFirma.getIndFirma() : null, false, istanza, null);
            if (allegatoIstanza.getIdIstanzaAttore() == null && ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente())) {
                Long idIstanzaAttoreCompilante = istanzaAttoreManager.getIdIstanzaAttoreCompilante(attoreScriva.getCodiceFiscale(), allegatoIstanza.getIdIstanza(), ComponenteAppEnum.findByDescr(attoreScriva.getComponente()), attoreScriva.getCodiceFiscale());
                allegatoIstanza.setIdIstanzaAttore(idIstanzaAttoreCompilante);
            }
            // calcola la gestione attore
            setAttoreRight(httpHeaders, allegatoIstanza.getIdIstanza());
            
            allegatoIstanza.setIdFunzionario(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? attoreScriva.getIdAttore() : null);
            if (allegatoIstanza.getClasseAllegato() == null) {
                allegatoIstanza.setClasseAllegato(allegatiService.getClasseAllegato(istanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCodTipologiaAllegato(), null));
            } else {

                if (allegatoIstanza.getClasseAllegato().getIdClasseAllegato() == null && StringUtils.isNotBlank(allegatoIstanza.getClasseAllegato().getCodClasseAllegato())) {
                    allegatoIstanza.setClasseAllegato(allegatiService.getClasseAllegatoByCode(allegatoIstanza.getClasseAllegato().getCodClasseAllegato()));
                }
            }

            //Impostazione dati di pubblicazione
            allegatiService.setDatiPubblicazione(allegatoIstanza, istanza, attoreScriva);

            // save allegato istanza
            Long idAllegatoIstanza = allegatoIstanzaDAO.saveAllegatoIstanza(allegatoIstanza.getDTO(), allegatoIstanza.getTipologiaAllegato().getCategoriaAllegato().getCodCategoriaAllegato());
            if (idAllegatoIstanza != null) {
                //before update retrieve data from db
                allegatiIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaById(idAllegatoIstanza);

                allegatoIstanza = allegatiIstanzaList != null && !allegatiIstanzaList.isEmpty() ? allegatiIstanzaList.get(0) : allegatoIstanza;
                //logInfo(className, "uploadIndexFile", "458");
                // Aggiornamento id_allegato_padre per i documenti associati ad un'osservazione (SCRIVA-1081)
                if (allegatoIstanza.getIdIstanzaOsservazione() != null && allegatoIstanza.getIdIstanzaOsservazione() > 0) {
                    allegatiService.updateIdPadreOsservazioni(allegatoIstanza, attoreScriva);
                }
                //logInfo(className, "uploadIndexFile", "463");
                // Aggiornamento data_atto istanza se categoria allegato è PROVV_FINALE (SCRIVA-)
                if (allegatoIstanza.getClasseAllegato() != null && ClasseAllegatoEnum.PROVV_FINALE.name().equalsIgnoreCase(allegatoIstanza.getClasseAllegato().getCodClasseAllegato())) {
                    istanzaService.updateDataConclProcedimentoFromProvvedimentoFinale(allegatoIstanza.getIdIstanza(), attoreScriva);
                }
                //logInfo(className, "uploadIndexFile", "468");
                // update metadata index
                if (adempimentoTipoAllegato != null) {
                    IndexMetadataPropertyDTO indexMetadataProperty = allegatiManager.getindexMetadataProperty(
                            istanza, allegatoIstanza, adempimentoTipoAllegato.getFlgObbligo(),
                            verificaFirma != null ? verificaFirma.getTipoFirma() : null,
                            verificaFirma != null ? verificaFirma.getErrorCode() : null,
                            flgCancellato, null
                    );
                    allegatiManager.updateMetadataIndexByUuid(uuidIndex, indexMetadataProperty);
                }
                logInfo(className, "uploadIndexFile", "479");
                allegatiIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaById(idAllegatoIstanza);

            } else {
                allegatiManager.deleteContenutoByUuid(uuidIndex);
                logError(className, "allegatoIstanzaJson\n" + allegatoIstanzaJson + "\nErrore durante il salvataggio su DB SCRIVA");
                return getResponseError(className, 500, "E100", null, null);
            }
        } catch (Exception e) {
            logError(className, e);
            if (uuidIndex != null) {
                allegatiManager.deleteContenutoByUuid(uuidIndex);
            }
            return getResponseError(className, 500, "E100", null, null);
        } 
        updateIstanzaPraticaTimestampAttore(allegatoIstanza.getIdIstanza(), attoreScriva);
        logInfo(className, "uploadIndexFile", "TIMER: END");    
        return Response.ok(allegatiIstanzaList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).build();
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
    public Response updateIndexFile(String xRequestAuth, String xRequestId, AllegatoIstanzaExtendedDTO allegatoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "allegatoIstanza :\n" + allegatoIstanza + "\n");
        Response response = setAttoreRight(httpHeaders, allegatoIstanza.getIdIstanza(), Boolean.TRUE);
        if (response != null) {
            return response;
        }
        
//TODO SOLO PER TEST 1488
//RIMUOVERE SUBITO DOPO ---- INIZIO
//    ErrorDTO errorTest;
//    if(allegatoIstanza.getIdAllegatoIstanza() == 1388)
//    {
//        errorTest = getErrorManager().getError("500", "E100", null, null, null);
//        return getResponseError(className, errorTest);
//    }
//RIMUOVERE SUBITO DOPO ---- FINE

        AllegatoIstanzaExtendedDTO allegatoIstanzaPrec = allegatiService.loadAllegatiById(allegatoIstanza.getIdAllegatoIstanza());
        // Validazione rispetto a quanto previsto per l'adempimento dell'istanza
        Map<String, Object> validateMap = allegatiService.validateAllegato(allegatoIstanza, attoreScriva);
        ErrorDTO error = (ErrorDTO) validateMap.get("error");
        if (null != error) {
            logError(className, "allegatoIstanza\n" + allegatoIstanza + "\n" + error);
            return getResponseError(className, error);
        }

        if(allegatoIstanza.getFlgRiservato() && allegatoIstanza.getFlgDaPubblicare())
        {
            if(!allegatoIstanzaPrec.getFlgRiservato() && allegatoIstanza.getFlgRiservato())
            {
            	error = getErrorManager().getError("400", "A058", "Non si puo settare riservato un documento già in pubblicazione.", null, null);	
            }	
            else
            {
            	error = getErrorManager().getError("400", "E004", "Non si puo pubblicare un documento riservato", null, null);	
            }	
        	
            logError(className, "allegatoIstanza\n" + allegatoIstanza + "\n" + error);
            return getResponseError(className, error);	
        }	
        
        String uuidIndex = allegatoIstanza.getUuidIndex();
        if (StringUtils.isBlank(uuidIndex)) {
            Map<String, String> details = new HashMap<>();
            details.put("uuid_index", ValidationResultEnum.MANDATORY.getDescription());
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
            logError(className, "allegatoIstanza\n" + allegatoIstanza + "\n" + error);
            return getResponseError(className, error);
        }

        AdempimentoTipoAllegatoExtendedDTO adempimentoTipoAllegato = (AdempimentoTipoAllegatoExtendedDTO) validateMap.get("adempimentoTipoAllegato");
        IstanzaExtendedDTO istanza = (IstanzaExtendedDTO) validateMap.get("istanza");

        // aggiornamento del record in tabella
        Integer res = allegatiService.updateAllegatoIstanza(allegatoIstanza, adempimentoTipoAllegato, attoreScriva);
        if (res == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "allegatoIstanza\n" + allegatoIstanza + "\nErrore durante l'aggiornamento del record su DB SCRIVA");
            return getResponseError(className, error);
        } else if (res < 1) {
            error = getErrorManager().getError("404", "", "Errore nell'aggiornamento dell'elemento; causa: elemento non trovato", null, null);
            logError(className, "allegatoIstanza\n" + allegatoIstanza + "\nErrore durante l'aggiornamento del record su DB SCRIVA : elemento non trovato");
            return getResponseError(className, error);
        } else {
            
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaUpdated = allegatoIstanzaDAO.loadAllegatoIstanzaById(allegatoIstanza.getIdAllegatoIstanza());

            // Aggiornamento data_atto istanza se categoria allegato è PROVV_FINALE (SCRIVA-) JIRA 1412
            if (allegatoIstanza.getClasseAllegato() != null && ClasseAllegatoEnum.PROVV_FINALE.name().equalsIgnoreCase(allegatoIstanza.getClasseAllegato().getCodClasseAllegato())) 
            {
                istanzaService.updateDataConclProcedimentoFromProvvedimentoFinale(allegatoIstanza.getIdIstanza(), attoreScriva);
            }
            updateIstanzaPraticaTimestampAttore(allegatoIstanza.getIdIstanza(), attoreScriva);
            logEnd(className);
            return Response.ok(allegatoIstanzaUpdated).status(200).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva.getProfiloAppIstanza()).build();
        }
    }

    /**
     * Il servizio permette di eliminare FISICAMENTE un documento da SCRIVA e da INDEX.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param uuidIndex       uuidIndex
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response deleteIndexFile(String xRequestAuth, String xRequestId, String uuidIndex, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "uuidIndex [" + uuidIndex + "]");
        ErrorDTO error = null;
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = allegatoIstanzaDAO.loadAllegatoIstanzaByUuidIndex(uuidIndex);
        if (listAllegatiIstanza == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "uuidIndex [" + uuidIndex + "] : Errore durante il recupero da DB SCRIVA");
            return getResponseError(className, error);
        }
        if (listAllegatiIstanza.isEmpty()) {
            error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
            logError(className, "uuidIndex [" + uuidIndex + "] : Elemento non trovato su DB SCRIVA");
            return getResponseError(className, error);
        }

        Response response = setAttoreRight(httpHeaders, listAllegatiIstanza.get(0).getIdIstanza(), Boolean.TRUE);
        /*
        if (response != null) {
            return response;
        }
        */
        try {
			attoreScriva = getAttoreScriva(httpHeaders);
			Long idIstanzaAttore = listAllegatiIstanza.get(0).getIdIstanzaAttore();
			Long idFunzionario = listAllegatiIstanza.get(0).getIdFunzionario();
			if(ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) && idFunzionario == null) {
	            error = getErrorManager().getError("500", "E100", null, null, null);
	            logError(className, "Componente BO e idFunzionario non valorizzato");
	            return getResponseError(className, error);
	        }
		    if(ComponenteAppEnum.FO.name().equalsIgnoreCase(attoreScriva.getComponente()) && idIstanzaAttore == null) {
		    	 error = getErrorManager().getError("500", "E100", null, null, null);
		         logError(className, "Componente FO e idIstanzaAttore non valorizzato");
		         return getResponseError(className, error);
		    }
		} catch (GenericException e) {
            logError(className, e);
            error = e.getError();
            return getResponseError(className, Integer.parseInt(error.getStatus()), error.getCode(), error.getTitle(), error.getDetail());
		}
        // cancellazione file da index
        error = allegatiManager.deleteContenutoByUuid(uuidIndex);

        if (error != null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "uuidIndex [" + uuidIndex + "] : Errore durante la cancellazione del file da INDEX");
            return getResponseError(className, error);
        }

        // Eventuale eliminazione se l'allegato è associato ad una integrazione
        allegatoIstanzaDAO.updateIdAllegatoIstanzaPadre(uuidIndex);
        // sleep inserito per test per verificare l'anomalia che si  presenta randomicamente in fase di cancellazione
        // RIMUOVERE SE NON RISOLVE IL PROBLEMA
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        }
        allegatoIntegrazioneDAO.deleteAllegatoIntegrazioneByUuidIndexAllegato(uuidIndex);

        // In caso di esito positivo eliminazione del record in tabella
        Integer res = allegatoIstanzaDAO.deleteAllegatoIstanzaByUuidIndex(uuidIndex);
        if (res == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "uuidIndex [" + uuidIndex + "] : Errore durante la cancellazione da DB SCRIVA");
            return getResponseError(className, error);
        } else if (res < 1) {
            error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento; causa: elemento non trovato", null, null);
            logError(className, "uuidIndex [" + uuidIndex + "] : Errore durante la cancellazione da DB SCRIVA : elemento non trovato");
            return getResponseError(className, error);
        } else {
            updateIstanzaPraticaTimestampAttore(listAllegatiIstanza.get(0).getIdIstanza(), attoreScriva);
            logEnd(className);
            
            return Response.noContent().build();
        }
    }

    /**
     * Il servizio verifica se sono stati associati all’istanza i documenti appartenenti alle tipologie obbligatorie previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie obbligatorie mancanti.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCategoriaAllegatoMandatoryByIdIstanza(String xRequestAuth, String xRequestId, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]");
        ErrorDTO error = null;
        /*
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
         */
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "idIstanza [" + idIstanza + "]", e);
            return getResponseError(className, error);
        }
        List<CategoriaAllegatoDTO> listCategoriaAllegati = categoriaAllegatoDAO.loadCategoriaAllegatoMandatoryByIdIstanza(idIstanza, attoreScriva.getComponente());
        if (listCategoriaAllegati == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "idIstanza [" + idIstanza + "] : Errore recupero categoria allegati");
            return getResponseError(className, error);
        }
        logEnd(className);
        return Response.ok(listCategoriaAllegati).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

    /**
     * Il servizio verifica se sono stati associati all’istanza documenti appartenenti a categorie non previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie errate.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCategoriaAllegatoMandatoryAdempimentoByIdIstanza(String xRequestAuth, String xRequestId, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]");
        /*
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
         */
        List<CategoriaAllegatoDTO> listCategoriaAllegati = categoriaAllegatoDAO.loadCategoriaAllegatoMandatoryAdempimentoByIdIstanza(idIstanza);
        if (listCategoriaAllegati == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "idIstanza [" + idIstanza + "] : Errore durante il recupero della categoria di allegati");
            return getResponseError(className, error);
        }
        logEnd(className);
        return Response.ok(listCategoriaAllegati).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

    /**
     * Load allegati by url cosmo response.
     *
     * @param urlCosmo        the url cosmo
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws CosmoException the cosmo exception
     */
    @Override
    public Response loadAllegatiByUrlCosmo(String urlCosmo, String xRequestAuth, String xRequestId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws CosmoException {
        logBegin(className);
        // recupero file da COSMO
        File file = cosmoService.getFileFromCosmo(urlCosmo);
        if (file == null) {
            logError(className, "urlCosmo [" + urlCosmo + "] - Errore nel recupero file da COSMO");
            logEnd(className);
            return getResponseError(className, 500, "E100", null, null);
        }
        logEnd(className);
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
    }

    /**
     * Check allegato mandatory delega firmata by id istanza response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response checkAllegatoMandatoryDelegaFirmataByIdIstanza(String xRequestAuth, String xRequestId, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza [" + idIstanza + "]");
        /*
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
         */
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzDelFirmaList = soggettoIstanzaDAO.loadSoggettoIstanzaWithDelegaByIdIstanza(idIstanza);
        if (soggettoIstanzDelFirmaList != null && !soggettoIstanzDelFirmaList.isEmpty()) {
            // Verifica presenza tra gli allegati della tipologia allegato DEL_FIRMA
            List<AllegatoIstanzaExtendedDTO> allegatoIstanzaList = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanzaAndCodTipologia(idIstanza, Constants.COD_TIPOLOGIA_DELEGA_FIRMA);
            if (allegatoIstanzaList == null || allegatoIstanzaList.isEmpty()) {
                ErrorDTO error = getErrorManager().getError("500", "E033", null, null, null);
                logError(className, "idIstanza [" + idIstanza + "]\n" + error);
                return getResponseError(className, error);
            }
        }
        logEnd(className);
        return Response.noContent().header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }


    /**
     * Valida il form inviato in fase di upload di file
     *
     * @param file                file
     * @param allegatoIstanzaJson allegatoIstanzaJson
     * @return ErrorDTO
     */
    private ErrorDTO validateFormData(File file, String allegatoIstanzaJson) {
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();

        if (null == file) {
            details.put("file", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (StringUtils.isBlank(allegatoIstanzaJson)) {
            details.put("allegatoIstanza", ValidationResultEnum.MANDATORY.getDescription());
        }

        if (!details.isEmpty()) {
            error = getErrorManager().getError("400", "E004", "I dati inseriti non sono corretti.", details, null);
        }

        return error;
    }

    /**
     * Gets allegato istanza by uuid index.
     *
     * @param uuidIndex the uuid index
     * @return the allegato istanza by uuid index
     */
    public AllegatoIstanzaExtendedDTO getAllegatoIstanzaByUuidIndex(String uuidIndex) {
        logBeginInfo(className, "uuidIndex [" + uuidIndex + "]");
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = allegatoIstanzaDAO.loadAllegatoIstanzaByUuidIndex(uuidIndex);
        logEnd(className);
        return listAllegatiIstanza != null && !listAllegatiIstanza.isEmpty() ? listAllegatiIstanza.get(0) : null;
    }

    @Override
    public Response deleteIndexFileCodiceAllegato(String xRequestAuth, String xRequestId, String codAllegato, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codAllegato [" + codAllegato + "]");
        ErrorDTO error = null;
        List<AllegatoIstanzaExtendedDTO> listAllegatiIstanza = allegatoIstanzaDAO.loadAllegatoIstanzaByIdIstanza(null, codAllegato, null, null, null, null, null, Boolean.FALSE, null, null, null, true, null);
        if (listAllegatiIstanza == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "codAllegato [" + codAllegato + "] : Errore durante il recupero dell'allegato da DB SCRIVA");
            return getResponseError(className, error);
        }
        if (listAllegatiIstanza.isEmpty()) {
            error = getErrorManager().getError("404", "", "Elemento non trovato", null, null);
            logError(className, "codAllegato [" + codAllegato + "] : Elemento non trovato su DB SCRIVA");
            return getResponseError(className, error);
        }

        Response response = setAttoreRight(httpHeaders, listAllegatiIstanza.get(0).getIdIstanza(), Boolean.TRUE);
	        /*
	        if (response != null) {
	            return response;
	        }
	        */
        String uuidIndex = listAllegatiIstanza.get(0).getUuidIndex();

        // cancellazione file da index
        error = allegatiManager.deleteContenutoByUuid(uuidIndex);
        if (error != null) {
            logError(className, "codAllegato [" + codAllegato + "]\n" + error);
            return getResponseError(className, error);
        }

        // In caso di esito positivo eliminazione del record in tabella
        allegatoIstanzaDAO.updateIdAllegatoIstanzaPadre(uuidIndex);
        Integer res = allegatoIstanzaDAO.deleteAllegatoIstanzaByUuidIndex(uuidIndex);
        if (res == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, "uuidIndex [" + uuidIndex + "] : Errore durante l'eliminazione del record da DB SCRIVA");
            return getResponseError(className, error);
        } else if (res < 1) {
            error = getErrorManager().getError("404", "", "Errore nella cancellazione dell'elemento; causa: elemento non trovato", null, null);
            logError(className, "uuidIndex [" + uuidIndex + "]\n" + error);
            return getResponseError(className, error);
        } else {
            logEnd(className);
            return Response.noContent().build();
        }
    }
    
    /**
     * Sets place holder values.
     *
     * @param error     the error
     * @param idIstanza the id istanza
     */
    private void setPlaceHolderValues(ErrorDTO error, Long idIstanza) {
        logBegin(className);
        if (error != null) {
            String errorTitle = error.getTitle();
            if (StringUtils.isNotBlank(errorTitle)) {
                List<IstanzaExtendedDTO> istanzaList = istanzaDAO.loadIstanza(idIstanza);
                if (CollectionUtils.isNotEmpty(istanzaList)) {
                    List<ConfigurazioneDTO> configurazioneList = configurazioneDAO.loadConfigByKey("SCRIVA_EMAIL_ASSISTENZA_" + istanzaList.get(0).getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
                    if (CollectionUtils.isNotEmpty(configurazioneList)) {
                        errorTitle = errorTitle.replace("{PH_SCRIVA_EMAIL_ASSISTENZA_<COD_TIPO_ADEMPIMENTO>}", configurazioneList.get(0).getValore());
                    }
                }
                errorTitle = errorTitle.replace("{PH_SCRIVA_EMAIL_ASSISTENZA_<COD_TIPO_ADEMPIMENTO>}", "");
                error.setTitle(errorTitle);
            }
        }
    }

}