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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.HelpExtendedDTO;

import java.util.ArrayList;
import java.util.List;

public class HelpApiServiceHelper extends AbstractServiceHelper {

    public HelpApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<HelpExtendedDTO> getHelp(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[HelpApiServiceHelper::getHelp] BEGIN");
        List<HelpExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/help";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[HelpApiServiceHelper::getHelp] SERVER EXCEPTION : " + err.toString());
                throw new GenericException(err);
            }
            GenericType<List<HelpExtendedDTO>> helpListType = new GenericType<List<HelpExtendedDTO>>() {
            };
            result = resp.readEntity(helpListType);
        } catch (ProcessingException e) {
            LOGGER.error("[HelpApiServiceHelper::getHelp] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[HelpApiServiceHelper::getHelp] END");
        }
        return result;
    }

    public List<HelpExtendedDTO> getHelpById(MultivaluedMap<String, Object> requestHeaders, Long idHelp) throws GenericException {
        LOGGER.debug("[HelpApiServiceHelper::getHelpById] BEGIN");
        List<HelpExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/help/id/" + idHelp;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[HelpApiServiceHelper::getHelpById] SERVER EXCEPTION : " + err.toString());
                throw new GenericException(err);
            }
            GenericType<List<HelpExtendedDTO>> helpListType = new GenericType<List<HelpExtendedDTO>>() {
            };
            result = resp.readEntity(helpListType);
        } catch (ProcessingException e) {
            LOGGER.error("[HelpApiServiceHelper::getHelpById] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[HelpApiServiceHelper::getHelpById] END");
        }
        return result;
    }

    public List<HelpExtendedDTO> getHelpByChiave(MultivaluedMap<String, Object> requestHeaders, String chiaveHelp) throws GenericException {
        LOGGER.debug("[HelpApiServiceHelper::getHelpByChiave] BEGIN");
        List<HelpExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/help/chiave/" + chiaveHelp;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[HelpApiServiceHelper::getHelpByChiave] SERVER EXCEPTION : " + err.toString());
                throw new GenericException(err);
            }
            GenericType<List<HelpExtendedDTO>> helpListType = new GenericType<List<HelpExtendedDTO>>() {
            };
            result = resp.readEntity(helpListType);
        } catch (ProcessingException e) {
            LOGGER.error("[HelpApiServiceHelper::getHelpByChiave] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[HelpApiServiceHelper::getHelpByChiave] END");
        }
        return result;
    }
}