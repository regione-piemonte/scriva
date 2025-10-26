/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivaapisrv.util.Constants;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Soggetti api service helper.
 *
 * @author CSI PIEMONTE
 */
public class SoggettiApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/soggetti";

    /**
     * Instantiates a new Soggetti api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public SoggettiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets soggetti.
     *
     * @param requestHeaders the request headers
     * @return the soggetti
     * @throws GenericException the generic exception
     */
    public List<SoggettoExtendedDTO> getSoggetti(MultivaluedMap<String, Object> requestHeaders, String codiceFiscale, String tipoSoggetto, String codAdempimento, String codiceFiscaleImpresa) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String queryParameters = "";
        queryParameters = buildQueryParameters(queryParameters, "codice_fiscale", codiceFiscale);
        queryParameters = buildQueryParameters(queryParameters, "tipo_soggetto", tipoSoggetto);
        queryParameters = buildQueryParameters(queryParameters, "cod_adempimento", codAdempimento);
        queryParameters = buildQueryParameters(queryParameters, "codice_fiscale_impresa", codiceFiscaleImpresa);
        String targetUrl = this.endpointBase;
        try {
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() != 404) {
                if (resp.getStatus() >= 400) {
                    ErrorDTO err = getError(resp);
                    LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                    throw new GenericException(err);
                }
                setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
                result = resp.readEntity(new GenericType<>() {
                });
            }
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    /**
     * Gets soggetto by id.
     *
     * @param requestHeaders the request headers
     * @param idSoggetto     the id soggetto
     * @return the soggetto by id
     * @throws GenericException the generic exception
     */
    public List<SoggettoExtendedDTO> getSoggettoById(MultivaluedMap<String, Object> requestHeaders, Long idSoggetto) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id/" + idSoggetto;
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
     * Save soggetto soggetto extended dto.
     *
     * @param requestHeaders the request headers
     * @param soggetto       the soggetto
     * @return the soggetto extended dto
     * @throws GenericException the generic exception
     */
    public SoggettoExtendedDTO saveSoggetto(MultivaluedMap<String, Object> requestHeaders, SoggettoExtendedDTO soggetto) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        SoggettoExtendedDTO result = new SoggettoExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<SoggettoExtendedDTO> entity = Entity.json(soggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<SoggettoExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        return result;
    }

    /**
     * Update soggetto soggetto extended dto.
     *
     * @param requestHeaders the request headers
     * @param soggetto       the soggetto
     * @return the soggetto extended dto
     * @throws GenericException the generic exception
     */
    public SoggettoExtendedDTO updateSoggetto(MultivaluedMap<String, Object> requestHeaders, SoggettoExtendedDTO soggetto) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        SoggettoExtendedDTO result = new SoggettoExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<SoggettoExtendedDTO> entitySoggetto = Entity.json(soggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entitySoggetto);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<SoggettoExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        return result;
    }

    /**
     * Delete soggetto boolean.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the boolean
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public Boolean deleteSoggetto(MultivaluedMap<String, Object> requestHeaders, String uid) throws ProcessingException, GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        String targetUrl = this.endpointBase + "/id/" + uid;
        try {
            return setDeleteResult(CLASSNAME, methodName, targetUrl, requestHeaders);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
    }
}