/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ConfiguraRuoloSoggettoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

public class ConfiguraRuoliSoggettiApiServiceHelper extends AbstractServiceHelper {

    public ConfiguraRuoliSoggettiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggetti(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> listType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggetti] END");
        }
        return result;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggettiByRuoloCompilante(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante) throws GenericException {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti/ruolo-compilante/" + idRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> ruoliCompilanteListType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(ruoliCompilanteListType);
        } catch (ProcessingException e) {
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloCompilante] END");
        }
        return result;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggettiByAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti/adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> listType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByAdempimento] END");
        }
        return result;
    }


    public List<ConfiguraRuoloSoggettoExtendedDTO> getConfiguraRuoliSoggettiByRuoloSoggetto(MultivaluedMap<String, Object> requestHeaders, Long idRuoloSoggetto) throws GenericException {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configura-ruoli-soggetti/ruolo-soggetto/" + idRuoloSoggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>> listType = new GenericType<List<ConfiguraRuoloSoggettoExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceHelper::getConfiguraRuoliSoggettiByRuoloSoggetto] END");
        }
        return result;
    }

}