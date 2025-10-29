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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ConfiguraRuoloSoggettoExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ConfiguraRuoliSoggettiApiServiceHelper extends AbstractServiceHelper {

    public ConfiguraRuoliSoggettiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggetti(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> listType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] END");
        }
        return result;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggettiByRuoloCompilante(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante) throws GenericException {
        logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti/ruolo-compilante/" + idRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> ruoliCompilanteListType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(ruoliCompilanteListType);
        } catch (ProcessingException e) {
            logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] END");
        }
        return result;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggettiByAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti/adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> listType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] END");
        }
        return result;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggettiByRuoloSoggetto(MultivaluedMap<String, Object> requestHeaders, Long idRuoloSoggetto) throws GenericException {
        logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti/ruolo-soggetto/" + idRuoloSoggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> listType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] END");
        }
        return result;
    }

}