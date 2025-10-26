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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

public class AdempimentiConfigApiServiceHelper extends AbstractServiceHelper {

    public AdempimentiConfigApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoConfigDTO> getAdempimentiConfig(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentiConfig] BEGIN");
        List<AdempimentoConfigDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-config";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentiConfig] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoConfigDTO>> procedimentiConfigListType = new GenericType<List<AdempimentoConfigDTO>>() {
            };
            result = resp.readEntity(procedimentiConfigListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentiConfig] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentiConfig] END");
        }
        return result;
    }

    public List<AdempimentoConfigDTO> getAdempimentoConfigByAdempimento(MultivaluedMap<String, Object> requestHeaders, String desAdempimento) throws GenericException {
        LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimento] BEGIN");
        List<AdempimentoConfigDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-config/adempimento/" + desAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoConfigDTO>> procedimentiConfigListType = new GenericType<List<AdempimentoConfigDTO>>() {
            };
            result = resp.readEntity(procedimentiConfigListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoConfigDTO> getAdempimentoConfigByAdempimentoInformazione(MultivaluedMap<String, Object> requestHeaders, String desAdempimento, String infoScriva) throws GenericException {
        LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazione] BEGIN");
        List<AdempimentoConfigDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-config/adempimento/" + desAdempimento + "/info/" + infoScriva;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazione] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoConfigDTO>> procedimentiConfigListType = new GenericType<List<AdempimentoConfigDTO>>() {
            };
            result = resp.readEntity(procedimentiConfigListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazione] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazione] END");
        }
        return result;
    }

    public List<AdempimentoConfigDTO> getAdempimentoConfigByAdempimentoInformazioneChiave(MultivaluedMap<String, Object> requestHeaders, String desAdempimento, String infoScriva, String chiave) throws GenericException {
        LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazioneChiave] BEGIN");
        List<AdempimentoConfigDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti-config/adempimento/" + desAdempimento + "/info/" + infoScriva + "/chiave/" + chiave;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazioneChiave] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoConfigDTO>> procedimentiConfigListType = new GenericType<List<AdempimentoConfigDTO>>() {
            };
            result = resp.readEntity(procedimentiConfigListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazioneChiave] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceHelper::getAdempimentoConfigByAdempimentoInformazioneChiave] END");
        }
        return result;
    }

}