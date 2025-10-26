/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.NazioneDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.RegioneExtendedDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Limiti amministrativi api service helper.
 *
 * @author CSI PIEMONTE
 */
public class LimitiAmministrativiApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/limiti-amministrativi";

    /**
     * Instantiates a new Limiti amministrativi api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public LimitiAmministrativiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets nazioni.
     *
     * @param requestHeaders the request headers
     * @param codIstat       the cod istat
     * @return the nazioni
     * @throws GenericException the generic exception
     */
    public List<NazioneDTO> getNazioni(MultivaluedMap<String, Object> requestHeaders, String codIstat) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<NazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/nazioni";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
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
     * Gets nazioni attive.
     *
     * @param requestHeaders the request headers
     * @return the nazioni attive
     * @throws GenericException the generic exception
     */
    public List<NazioneDTO> getNazioniAttive(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<NazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/nazioni/attive";
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    /**
     * Gets regioni.
     *
     * @param requestHeaders the request headers
     * @return the regioni
     * @throws GenericException the generic exception
     */
    public List<RegioneExtendedDTO> getRegioni(MultivaluedMap<String, Object> requestHeaders, String codIstat, String codIstatNazione) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<RegioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/regioni";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
            queryParameters = buildQueryParameters(queryParameters, "cod_istat_nazione", codIstatNazione);
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
     * Gets regioni attive.
     *
     * @param requestHeaders the request headers
     * @return the regioni attive
     * @throws GenericException the generic exception
     */
    public List<RegioneExtendedDTO> getRegioniAttive(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<RegioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/regioni/attive";
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    /**
     * Gets province.
     *
     * @param requestHeaders the request headers
     * @return the province
     * @throws GenericException the generic exception
     */
    public List<ProvinciaExtendedDTO> getProvince(MultivaluedMap<String, Object> requestHeaders, String codIstat, String codIstatRegione, Long idAdempimento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<ProvinciaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/province";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
            queryParameters = buildQueryParameters(queryParameters, "cod_istat_regione", codIstatRegione);
            queryParameters = buildQueryParameters(queryParameters, "id_adempimento", idAdempimento);
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
     * Gets province attive.
     *
     * @param requestHeaders the request headers
     * @return the province attive
     * @throws GenericException the generic exception
     */
    public List<ProvinciaExtendedDTO> getProvinceAttive(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<ProvinciaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/province/attive";
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    /**
     * Gets comuni.
     *
     * @param requestHeaders the request headers
     * @return the comuni
     * @throws GenericException the generic exception
     */
    public List<ComuneExtendedDTO> getComuni(MultivaluedMap<String, Object> requestHeaders, String codIstat, String codIstatProvincia) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<ComuneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/comuni";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
            queryParameters = buildQueryParameters(queryParameters, "cod_istat_provincia", codIstatProvincia);
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
     * Gets comuni attivi.
     *
     * @param requestHeaders the request headers
     * @return the comuni attivi
     * @throws GenericException the generic exception
     */
    public List<ComuneExtendedDTO> getComuniAttivi(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<ComuneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/comuni/attivi";
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

}