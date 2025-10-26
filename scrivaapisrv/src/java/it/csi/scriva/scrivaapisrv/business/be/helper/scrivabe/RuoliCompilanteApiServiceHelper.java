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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.RuoloCompilanteDTO;

import java.util.ArrayList;
import java.util.List;

public class RuoliCompilanteApiServiceHelper extends AbstractServiceHelper {

    public RuoliCompilanteApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<RuoloCompilanteDTO> getRuoliCompilante(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] END");
        }
        return result;
    }

    public List<RuoloCompilanteDTO> getRuoloCompilante(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante) throws GenericException {
        LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilante] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante/id/" + idRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("RuoliCompilanteApiServiceHelper::getRuoloCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliCompilanteApiServiceHelper::getRuoloCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilante] END");
        }
        return result;
    }

    public List<RuoloCompilanteDTO> getRuoloCompilanteByCode(MultivaluedMap<String, Object> requestHeaders, String codRuoloCompilante) throws GenericException {
        LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante/codice/" + codRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] END");
        }
        return result;
    }

    public List<RuoloCompilanteDTO> getRuoliCompilanteByAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante/adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] END");
        }
        return result;
    }

}