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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

public class ConfigurazioniApiServiceHelper extends AbstractServiceHelper {

    public ConfigurazioniApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<ConfigurazioneDTO> getConfigurazioni(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[ConfigurazioniApiServiceHelper::getConfigurazioni] BEGIN");
        List<ConfigurazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configurazioni";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[ConfigurazioniApiServiceHelper::getConfigurazioni] SERVER EXCEPTION : " + err.toString());
                throw new GenericException(err);
            }
            GenericType<List<ConfigurazioneDTO>> configurazioniListType = new GenericType<List<ConfigurazioneDTO>>() {
            };
            result = resp.readEntity(configurazioniListType);
        } catch (ProcessingException e) {
            LOGGER.error("[ConfigurazioniApiServiceHelper::getConfigurazioni] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[ConfigurazioniApiServiceHelper::getConfigurazioni] END");
        }
        return result;
    }

    public List<ConfigurazioneDTO> getConfigurazioneByKey(MultivaluedMap<String, Object> requestHeaders, String key) throws GenericException {
        LOGGER.debug("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] BEGIN");
        List<ConfigurazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configurazioni/key/" + key;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfigurazioneDTO>> configurazioniListType = new GenericType<List<ConfigurazioneDTO>>() {
            };
            result = resp.readEntity(configurazioniListType);
        } catch (ProcessingException e) {
            LOGGER.error("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] END");
        }
        return result;
    }


}