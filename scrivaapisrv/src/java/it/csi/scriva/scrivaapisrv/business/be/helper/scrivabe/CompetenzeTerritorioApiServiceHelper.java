/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedMap;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaCompetenzaExtendedDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Competenze territorio api service helper.
 */
public class CompetenzeTerritorioApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

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
    public List<Object> getCompetenzeTerritorio(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento, Long idIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<Object> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/competenze-territorio";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_adempimento", idAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            result = setGetResult(CLASSNAME, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<IstanzaCompetenzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/competenze-territorio/_check-ac";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            result = setGetResult(CLASSNAME, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<IstanzaCompetenzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/competenze-territorio/ac-secondarie";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "lvl", livelliCompetenza);
            result = setSaveUpdateListResult(CLASSNAME, methodName, Boolean.TRUE, targetUrl + queryParameters, requestHeaders, null, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        String targetUrl = this.endpointBase + "/competenze-territorio/ac-secondarie";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            return setDeleteResult(CLASSNAME, methodName, targetUrl + queryParameters, requestHeaders);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        IstanzaCompetenzaExtendedDTO result = null; // new IstanzaCompetenzaExtendedDTO();
        String targetUrl = this.endpointBase + "/competenze-territorio";
        try {
            return setSaveResult(CLASSNAME, methodName, targetUrl, requestHeaders, istanzaCompetenza, IstanzaCompetenzaExtendedDTO.class);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        IstanzaCompetenzaExtendedDTO result = new IstanzaCompetenzaExtendedDTO();
        String targetUrl = this.endpointBase + "/competenze-territorio";
        try {
            Entity<IstanzaCompetenzaExtendedDTO> entity = Entity.json(istanzaCompetenza);
            return setSaveUpdateResult(CLASSNAME, methodName, Boolean.FALSE, targetUrl, requestHeaders, istanzaCompetenza, IstanzaCompetenzaExtendedDTO.class);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
    }

}