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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivafoweb.util.Constants;

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

    private final String className = this.getClass().getSimpleName();

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
    public List<SoggettoExtendedDTO> getSoggetti(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase;
        try {
            result = setGetResult(className, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id/" + idSoggetto;
        try {
            result = setGetResult(className, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    /**
     * Gets soggetto by codice fiscale and tipo.
     *
     * @param requestHeaders  the request headers
     * @param codiceFiscale   the codice fiscale
     * @param tipoSoggetto    the tipo soggetto
     * @param tipoAdempimento the tipo adempimento
     * @return the soggetto by codice fiscale and tipo
     * @throws GenericException the generic exception
     */
    public List<SoggettoExtendedDTO> getSoggettoByCodiceFiscaleAndTipo(MultivaluedMap<String, Object> requestHeaders, String codiceFiscale, String tipoSoggetto, String tipoAdempimento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/cf/" + codiceFiscale + "/tipo-soggetto/" + tipoSoggetto + "/tipo-adempimento/" + tipoAdempimento;
        try {
            result = setGetResult(className, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    /**
     * Gets soggetto impresa.
     *
     * @param requestHeaders        the request headers
     * @param codiceFiscaleImpresa  the codice fiscale impresa
     * @param codiceFiscaleSoggetto the codice fiscale soggetto
     * @param tipoSoggetto          the tipo soggetto
     * @param tipoAdempimento       the tipo adempimento
     * @return the soggetto impresa
     * @throws GenericException the generic exception
     */
    public List<SoggettoExtendedDTO> getSoggettoImpresa(MultivaluedMap<String, Object> requestHeaders, String codiceFiscaleImpresa, String codiceFiscaleSoggetto, String tipoSoggetto, String tipoAdempimento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/cf-impresa/" + codiceFiscaleImpresa + "/cf-soggetto/" + codiceFiscaleSoggetto + "/tipo-soggetto/" + tipoSoggetto + "/tipo-adempimento/" + tipoAdempimento;
        try {
            result = setGetResult(className, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
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
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        SoggettoExtendedDTO result = new SoggettoExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<SoggettoExtendedDTO> entity = Entity.json(soggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<SoggettoExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        logger.debug(getClassFunctionEndInfo(className, methodName));
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
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        SoggettoExtendedDTO result = new SoggettoExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<SoggettoExtendedDTO> entitySoggetto = Entity.json(soggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entitySoggetto);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<SoggettoExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        logger.debug(getClassFunctionEndInfo(className, methodName));
        return result;
    }

    /**
     * Save soggetti list.
     *
     * @param requestHeaders the request headers
     * @param soggetti       the soggetti
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<SoggettoExtendedDTO> saveSoggetti(MultivaluedMap<String, Object> requestHeaders, List<SoggettoExtendedDTO> soggetti) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/save-list";
        try {
            Entity<List<SoggettoExtendedDTO>> entity = Entity.json(soggetti);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<SoggettoExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        logger.debug(getClassFunctionEndInfo(className, methodName));
        return result;
    }

    /**
     * Update soggetti list.
     *
     * @param requestHeaders the request headers
     * @param soggetti       the soggetti
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<SoggettoExtendedDTO> updateSoggetti(MultivaluedMap<String, Object> requestHeaders, List<SoggettoExtendedDTO> soggetti) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<SoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/update-list";
        try {
            Entity<List<SoggettoExtendedDTO>> entity = Entity.json(soggetti);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<SoggettoExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        logger.debug(getClassFunctionEndInfo(className, methodName));
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
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        String targetUrl = this.endpointBase + "/id/" + uid;
        try {
            return setDeleteResult(className, methodName, targetUrl, requestHeaders);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
    }
}