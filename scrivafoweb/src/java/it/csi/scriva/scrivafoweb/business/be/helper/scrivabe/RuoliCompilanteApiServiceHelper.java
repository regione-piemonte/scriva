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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.RuoloCompilanteDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class RuoliCompilanteApiServiceHelper extends AbstractServiceHelper {

    public RuoliCompilanteApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<RuoloCompilanteDTO> getRuoliCompilante(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilante] END");
        }
        return result;
    }

    public List<RuoloCompilanteDTO> getRuoloCompilante(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante) throws GenericException {
        logger.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilante] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante/id/" + idRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("RuoliCompilanteApiServiceHelper::getRuoloCompilante] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[RuoliCompilanteApiServiceHelper::getRuoloCompilante] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilante] END");
        }
        return result;
    }

    public List<RuoloCompilanteDTO> getRuoloCompilanteByCode(MultivaluedMap<String, Object> requestHeaders, String codRuoloCompilante) throws GenericException {
        logger.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante/codice/" + codRuoloCompilante;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[RuoliCompilanteApiServiceHelper::getRuoloCompilanteByCode] END");
        }
        return result;
    }

    public List<RuoloCompilanteDTO> getRuoliCompilanteByAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        logger.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] BEGIN");
        List<RuoloCompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ruoli-compilante/adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<RuoloCompilanteDTO>> listType = new GenericType<List<RuoloCompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[RuoliCompilanteApiServiceHelper::getRuoliCompilanteByAdempimento] END");
        }
        return result;
    }

}