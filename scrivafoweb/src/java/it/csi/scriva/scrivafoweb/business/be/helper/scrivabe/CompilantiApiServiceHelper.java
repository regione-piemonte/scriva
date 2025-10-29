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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompilanteDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompilantePreferenzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class CompilantiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public CompilantiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<CompilanteDTO> getCompilanti(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[CompilantiApiServiceHelper::getCompilanti] BEGIN");
        List<CompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/compilanti";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CompilantiApiServiceHelper::getCompilanti] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<CompilanteDTO>> listType = new GenericType<List<CompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[CompilantiApiServiceHelper::getCompilanti] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CompilantiApiServiceHelper::getCompilanti] END");
        }
        return result;
    }

    public List<CompilanteDTO> getCompilante(MultivaluedMap<String, Object> requestHeaders, Long idCompilante) throws GenericException {
        logger.debug("[CompilantiApiServiceHelper::getCompilante] BEGIN");
        List<CompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/compilanti/id/" + idCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CompilantiApiServiceHelper::getCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<CompilanteDTO>> listType = new GenericType<List<CompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[CompilantiApiServiceHelper::getCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CompilantiApiServiceHelper::getCompilante] END");
        }
        return result;
    }

    public List<CompilanteDTO> getCompilanteByCodiceFiscale(MultivaluedMap<String, Object> requestHeaders, String codiceFiscale) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<CompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/compilanti/cf/" + codiceFiscale;
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

    public CompilantePreferenzaExtendedDTO addPreferenza(MultivaluedMap<String, Object> requestHeaders, CompilantePreferenzaExtendedDTO preferenza) throws GenericException {
        logger.debug("[CompilantiApiServiceHelper::addPreferenza] BEGIN");
        CompilantePreferenzaExtendedDTO result = new CompilantePreferenzaExtendedDTO();
        String targetUrl = this.endpointBase + "/compilanti/preferenze";
        try {
            Entity<CompilantePreferenzaExtendedDTO> entity = Entity.json(preferenza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CompilantiApiServiceHelper::addPreferenza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<CompilantePreferenzaExtendedDTO> compilantePreferenzaDTOType = new GenericType<CompilantePreferenzaExtendedDTO>() {
            };
            result = resp.readEntity(compilantePreferenzaDTOType);
        } catch (ProcessingException e) {
            logger.error("[CompilantiApiServiceHelper::addPreferenza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CompilantiApiServiceHelper::addPreferenza] END");
        }
        return result;
    }

    public List<CompilantePreferenzaExtendedDTO> getPreferenzeByCompilante(MultivaluedMap<String, Object> requestHeaders, Long idCompilante) throws GenericException {
        logger.debug("[CompilantiApiServiceHelper::getPreferenzeByCompilante] BEGIN");
        List<CompilantePreferenzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/compilanti/preferenze/id-compilante/" + idCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CompilantiApiServiceHelper::getPreferenzeByCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<CompilantePreferenzaExtendedDTO>> listType = new GenericType<List<CompilantePreferenzaExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[CompilantiApiServiceHelper::getPreferenzeByCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CompilantiApiServiceHelper::getPreferenzeByCompilante] END");
        }
        return result;
    }

    public List<CompilantePreferenzaExtendedDTO> getPreferenzeByCfCompilante(MultivaluedMap<String, Object> requestHeaders, String cfCompilante) throws GenericException {
        logger.debug("[CompilantiApiServiceHelper::getPreferenzeByCfCompilante] BEGIN");
        List<CompilantePreferenzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/compilanti/preferenze/cf/" + cfCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CompilantiApiServiceHelper::getPreferenzeByCfCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<CompilantePreferenzaExtendedDTO>> listType = new GenericType<List<CompilantePreferenzaExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[CompilantiApiServiceHelper::getPreferenzeByCfCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CompilantiApiServiceHelper::getPreferenzeByCfCompilante] END");
        }
        return result;
    }

    public Boolean deletePreferenza(MultivaluedMap<String, Object> requestHeaders, Long idPreferenza) throws ProcessingException, GenericException {
        logger.debug("[CompilantiApiServiceHelper::deletePreferenza] BEGIN");
        String targetUrl = this.endpointBase + "/compilanti/preferenze/" + idPreferenza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CompilantiApiServiceHelper::deletePreferenza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            logger.error("[CompilantiApiServiceHelper::deletePreferenza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CompilantiApiServiceHelper::deletePreferenza] END");
        }
    }

    public Boolean logout(MultivaluedMap<String, Object> requestHeaders) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        Boolean result = Boolean.FALSE;
        String targetUrl = this.endpointBase + "/compilanti/logout";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            result = Boolean.TRUE;
        } catch (Exception e) {
            logger.error(getClassFunctionDebugString(className, methodName, e.getMessage()), e);
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }
}