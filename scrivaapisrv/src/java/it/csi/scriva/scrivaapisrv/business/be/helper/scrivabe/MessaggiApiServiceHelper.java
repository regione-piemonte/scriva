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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.MessaggioExtendedDTO;

import java.util.ArrayList;
import java.util.List;

public class MessaggiApiServiceHelper extends AbstractServiceHelper {

    public MessaggiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<MessaggioExtendedDTO> getMessaggi(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[MessaggiApiServiceHelper::getMessaggi] BEGIN");
        List<MessaggioExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/messaggi";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[MessaggiApiServiceHelper::getMessaggi] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<MessaggioExtendedDTO>> messaggiListType = new GenericType<List<MessaggioExtendedDTO>>() {
            };
            result = resp.readEntity(messaggiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[MessaggiApiServiceHelper::getMessaggi] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[MessaggiApiServiceHelper::getMessaggi] END");
        }
        return result;
    }

    public List<MessaggioExtendedDTO> getMessaggio(MultivaluedMap<String, Object> requestHeaders, Long idMessaggio) throws GenericException {
        LOGGER.debug("[MessaggiApiServiceHelper::getMessaggio] BEGIN");
        List<MessaggioExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/messaggi/id-messaggio/" + idMessaggio;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[MessaggiApiServiceHelper::getMessaggio] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<MessaggioExtendedDTO>> messaggiListType = new GenericType<List<MessaggioExtendedDTO>>() {
            };
            result = resp.readEntity(messaggiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[MessaggiApiServiceHelper::getMessaggio] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[MessaggiApiServiceHelper::getMessaggio] END");
        }
        return result;
    }

    public List<MessaggioExtendedDTO> getMessaggioByCode(MultivaluedMap<String, Object> requestHeaders, String codMessaggio) throws GenericException {
        LOGGER.debug("[MessaggiApiServiceHelper::getMessaggioByCode] BEGIN");
        List<MessaggioExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/messaggi/codice-messaggio/" + codMessaggio;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[MessaggiApiServiceHelper::getMessaggioByCode] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<MessaggioExtendedDTO>> messaggiListType = new GenericType<List<MessaggioExtendedDTO>>() {
            };
            result = resp.readEntity(messaggiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[MessaggiApiServiceHelper::getMessaggioByCode] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[MessaggiApiServiceHelper::getMessaggioByCode] END");
        }
        return result;
    }
}