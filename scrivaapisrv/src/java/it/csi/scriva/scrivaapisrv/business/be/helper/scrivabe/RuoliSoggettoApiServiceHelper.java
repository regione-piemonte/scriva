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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.RuoloSoggettoDTO;

import java.util.ArrayList;
import java.util.List;

public class RuoliSoggettoApiServiceHelper extends AbstractServiceHelper {

    public RuoliSoggettoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<RuoloSoggettoDTO> getRuoliSoggetti(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoliSoggetti] BEGIN");
        List<RuoloSoggettoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-soggetto";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoliSoggetti] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloSoggettoDTO>> listType = new GenericType<List<RuoloSoggettoDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoliSoggetti] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoliSoggetti] END");
        }
        return result;
    }

    public List<RuoloSoggettoDTO> getRuoloSoggetto(MultivaluedMap<String, Object> requestHeaders, Long idRuoloSoggetto) throws GenericException {
        LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoloSoggetto] BEGIN");
        List<RuoloSoggettoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-soggetto/id/" + idRuoloSoggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoloSoggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloSoggettoDTO>> listType = new GenericType<List<RuoloSoggettoDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoloSoggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoloSoggetto] END");
        }
        return result;
    }

    public List<RuoloSoggettoDTO> getRuoloSoggettoByCode(MultivaluedMap<String, Object> requestHeaders, String codRuoloSoggetto) throws GenericException {
        LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoloSoggettoByCode] BEGIN");
        List<RuoloSoggettoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-soggetto/codice/" + codRuoloSoggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoloSoggettoByCode] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloSoggettoDTO>> listType = new GenericType<List<RuoloSoggettoDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoloSoggettoByCode] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoloSoggettoByCode] END");
        }
        return result;
    }

    public List<RuoloSoggettoDTO> getRuoliSoggettoByRuoloCompilanteAndAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante, Long idAdempimento) throws GenericException {
        LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoliSoggettoByRuoloCompilanteAndAdempimento] BEGIN");
        List<RuoloSoggettoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-soggetto/id-adempimento/" + idAdempimento + "/id-ruolo-compilante/" + idRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoliSoggettoByRuoloCompilanteAndAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloSoggettoDTO>> listType = new GenericType<List<RuoloSoggettoDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliSoggettoApiServiceHelper::getRuoliSoggettoByRuoloCompilanteAndAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliSoggettoApiServiceHelper::getRuoliSoggettoByRuoloCompilanteAndAdempimento] END");
        }
        return result;
    }
}