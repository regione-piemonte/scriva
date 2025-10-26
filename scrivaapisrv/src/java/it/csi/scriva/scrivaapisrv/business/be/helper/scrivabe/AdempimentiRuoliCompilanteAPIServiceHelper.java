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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoRuoloCompilanteExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

public class AdempimentiRuoliCompilanteAPIServiceHelper extends AbstractServiceHelper {

    public AdempimentiRuoliCompilanteAPIServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilante(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilante] BEGIN");
        List<AdempimentoRuoloCompilanteExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>> listType = new GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilante] END");
        }
        return result;
    }

    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilanteByRuoloCompilante(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante) throws GenericException {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilante] BEGIN");
        List<AdempimentoRuoloCompilanteExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante/id-ruolo-compilante/" + idRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>> listType = new GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilante] END");
        }
        return result;
    }

    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilanteByAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByAdempimento] BEGIN");
        List<AdempimentoRuoloCompilanteExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante/id-adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>> listType = new GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante, Long idAdempimento) throws GenericException {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento] BEGIN");
        List<AdempimentoRuoloCompilanteExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante/id-ruolo-compilante/" + idRuoloCompilante + "/id-adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>> listType = new GenericType<List<AdempimentoRuoloCompilanteExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceHelper::getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento] END");
        }
        return result;
    }
}