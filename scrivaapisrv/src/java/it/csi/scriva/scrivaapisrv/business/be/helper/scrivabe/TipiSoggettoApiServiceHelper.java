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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoSoggettoDTO;

import java.util.ArrayList;
import java.util.List;

public class TipiSoggettoApiServiceHelper extends AbstractServiceHelper {

    public TipiSoggettoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<TipoSoggettoDTO> getTipiSoggetto(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[TipiSoggettoApiServiceHelper::getTipiSoggetto] BEGIN");
        List<TipoSoggettoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-soggetto";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[TipiSoggettoApiServiceHelper::getTipiSoggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<TipoSoggettoDTO>> tipiSoggettoListType = new GenericType<List<TipoSoggettoDTO>>() {
            };
            result = resp.readEntity(tipiSoggettoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[TipiSoggettoApiServiceHelper::getTipiSoggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[TipiSoggettoApiServiceHelper::getTipiSoggetto] END");
        }
        return result;
    }

    public List<TipoSoggettoDTO> getTipoSoggetto(MultivaluedMap<String, Object> requestHeaders, Long idTipoSoggetto) throws GenericException {
        LOGGER.debug("[TipiSoggettoApiServiceHelper::getTipoSoggetto] BEGIN");
        List<TipoSoggettoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-soggetto/id/" + idTipoSoggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[TipiSoggettoApiServiceHelper::getTipiSoggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<TipoSoggettoDTO>> tipiSoggettoListType = new GenericType<List<TipoSoggettoDTO>>() {
            };
            result = resp.readEntity(tipiSoggettoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[TipiSoggettoApiServiceHelper::getTipoSoggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[TipiSoggettoApiServiceHelper::getTipoSoggetto] END");
        }
        return result;
    }

}