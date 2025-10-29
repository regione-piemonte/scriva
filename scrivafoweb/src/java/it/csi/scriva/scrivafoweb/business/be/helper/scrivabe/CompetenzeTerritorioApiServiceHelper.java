/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.ListObjectWithHeaderResultFoDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Competenze territorio api service helper.
 */
public class CompetenzeTerritorioApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Competenze territorio api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public CompetenzeTerritorioApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets competenze territorio.
     *
     * @param requestHeaders the request headers
     * @param idAdempimento  the id adempimento
     * @param idIstanza      the id istanza
     * @return the competenze territorio
     * @throws GenericException the generic exception
     */
    public List<Object> getCompetenzeTerritorioOld(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento, Long idIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<Object> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/competenze-territorio";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_adempimento", idAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            result = setGetResult(className, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    /**
     * Gets competenze territorio.
     *
     * @param requestHeaders the request headers
     * @param idAdempimento  the id adempimento
     * @param idIstanza      the id istanza
     * @return the competenze territorio
     * @throws GenericException the generic exception
     */
    public ListObjectWithHeaderResultFoDTO getCompetenzeTerritorio(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento, Long idIstanza) throws GenericException {
        logBegin(className);
        ListObjectWithHeaderResultFoDTO result = null;
        String targetUrl = this.endpointBase + "/competenze-territorio";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_adempimento", idAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            result = new ListObjectWithHeaderResultFoDTO();
            result.setObjectList(resp.readEntity(new GenericType<List<Object>>() {
            }));
            result.setCountHeader(resp.getHeaderString("CountCompetenze"));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Verifica competenze territorio by id istanza adempimento list.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<IstanzaCompetenzaExtendedDTO> verificaCompetenzeTerritorioByIdIstanzaAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<IstanzaCompetenzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/competenze-territorio/_check-ac";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            result = setGetResult(className, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    /**
     * Calculate istanza competenza territorio secondarie list.
     *
     * @param requestHeaders    the request headers
     * @param idIstanza         the id istanza
     * @param livelliCompetenza the livelli competenza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<IstanzaCompetenzaExtendedDTO> calculateIstanzaCompetenzaTerritorioSecondarie(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String livelliCompetenza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<IstanzaCompetenzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/competenze-territorio/ac-secondarie";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "lvl", livelliCompetenza);
            result = setSaveUpdateListResult(className, methodName, Boolean.TRUE, targetUrl + queryParameters, requestHeaders, null, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    /**
     * Delete istanza competenza territorio secondarie boolean.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteIstanzaCompetenzaTerritorioSecondarie(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        String targetUrl = this.endpointBase + "/competenze-territorio/ac-secondarie";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            return setDeleteResult(className, methodName, targetUrl + queryParameters, requestHeaders);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
    }


    /**
     * Save istanza competenza territorio istanza competenza extended dto.
     *
     * @param requestHeaders    the request headers
     * @param istanzaCompetenza the istanza competenza
     * @return the istanza competenza extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaCompetenzaExtendedDTO saveIstanzaCompetenzaTerritorio(MultivaluedMap<String, Object> requestHeaders, IstanzaCompetenzaExtendedDTO istanzaCompetenza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        IstanzaCompetenzaExtendedDTO result = null; // new IstanzaCompetenzaExtendedDTO();
        String targetUrl = this.endpointBase + "/competenze-territorio";
        try {
            return setSaveResult(className, methodName, targetUrl, requestHeaders, istanzaCompetenza, IstanzaCompetenzaExtendedDTO.class);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
    }

    /**
     * Update istanza competenza territorio istanza competenza extended dto.
     *
     * @param requestHeaders    the request headers
     * @param istanzaCompetenza the istanza competenza
     * @return the istanza competenza extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaCompetenzaExtendedDTO updateIstanzaCompetenzaTerritorio(MultivaluedMap<String, Object> requestHeaders, IstanzaCompetenzaExtendedDTO istanzaCompetenza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        IstanzaCompetenzaExtendedDTO result = new IstanzaCompetenzaExtendedDTO();
        String targetUrl = this.endpointBase + "/competenze-territorio";
        try {
            Entity<IstanzaCompetenzaExtendedDTO> entity = Entity.json(istanzaCompetenza);
            return setSaveUpdateResult(className, methodName, Boolean.FALSE, targetUrl, requestHeaders, istanzaCompetenza, IstanzaCompetenzaExtendedDTO.class);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
    }

    /**
     * Gets competenza territorio responsabile.
     *
     * @param requestHeaders the request headers
     * @param idAdempimento  the id adempimento
     * @param idIstanza      the id istanza
     * @return the competenze territorio
     * @throws GenericException the generic exception
     */
    public CompetenzaTerritorioResponsabileDTO getCompetenzeTerritorioByIdCompResp(MultivaluedMap<String, Object> requestHeaders, Long idCompetenzaResponsabile) throws GenericException {
        logBegin(className);
        CompetenzaTerritorioResponsabileDTO result = new CompetenzaTerritorioResponsabileDTO();
        String targetUrl = this.endpointBase + "/competenze-territorio/" + idCompetenzaResponsabile + "/responsabili" ;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            GenericType<CompetenzaTerritorioResponsabileDTO> competenzaTerritorioResponsabileDTO = new GenericType<CompetenzaTerritorioResponsabileDTO>() {
            };
            result = resp.readEntity(competenzaTerritorioResponsabileDTO);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

}