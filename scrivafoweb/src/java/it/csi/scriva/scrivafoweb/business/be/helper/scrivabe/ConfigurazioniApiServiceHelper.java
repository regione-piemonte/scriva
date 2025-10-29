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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ConfigurazioniApiServiceHelper extends AbstractServiceHelper {

    public ConfigurazioniApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<ConfigurazioneDTO> getConfigurazioni(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[ConfigurazioniApiServiceHelper::getConfigurazioni] BEGIN");
        List<ConfigurazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configurazioni";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ConfigurazioniApiServiceHelper::getConfigurazioni] SERVER EXCEPTION : " + err.toString());
                throw new GenericException(err);
            }
            GenericType<List<ConfigurazioneDTO>> configurazioniListType = new GenericType<List<ConfigurazioneDTO>>() {
            };
            result = resp.readEntity(configurazioniListType);
        } catch (ProcessingException e) {
            logger.error("[ConfigurazioniApiServiceHelper::getConfigurazioni] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ConfigurazioniApiServiceHelper::getConfigurazioni] END");
        }
        return result;
    }

    public List<ConfigurazioneDTO> getConfigurazioneByKey(MultivaluedMap<String, Object> requestHeaders, String key) throws GenericException {
        logger.debug("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] BEGIN");
        List<ConfigurazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/configurazioni/key/" + key;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ConfigurazioneDTO>> configurazioniListType = new GenericType<List<ConfigurazioneDTO>>() {
            };
            result = resp.readEntity(configurazioniListType);
        } catch (ProcessingException e) {
            logger.error("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ConfigurazioniApiServiceHelper::getConfigurazioneByKey] END");
        }
        return result;
    }


}