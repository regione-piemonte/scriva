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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoNaturaGiuridicaDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TipiNaturaGiuridicaApiServiceHelper extends AbstractServiceHelper {

    public TipiNaturaGiuridicaApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<TipoNaturaGiuridicaDTO> getTipiNaturaGiuridica(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[TipiNaturaGiuridicaApiServiceHelper::getTipiNaturaGiuridica] BEGIN");
        List<TipoNaturaGiuridicaDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-natura-giuridica";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[TipiNaturaGiuridicaApiServiceHelper::getTipiNaturaGiuridica] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<TipoNaturaGiuridicaDTO>> tipiNaturaGiuridicaListType = new GenericType<List<TipoNaturaGiuridicaDTO>>() {
            };
            result = resp.readEntity(tipiNaturaGiuridicaListType);
        } catch (ProcessingException e) {
            logger.error("[TipiNaturaGiuridicaApiServiceHelper::getTipiNaturaGiuridica] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[TipiNaturaGiuridicaApiServiceHelper::getTipiNaturaGiuridica] END");
        }
        return result;
    }

    public List<TipoNaturaGiuridicaDTO> getTipoNaturaGiuridica(MultivaluedMap<String, Object> requestHeaders, Long idTipoNaturaGiuridica) throws GenericException {
        logger.debug("[TipiNaturaGiuridicaApiServiceHelper::getTipoNaturaGiuridica] BEGIN");
        List<TipoNaturaGiuridicaDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-natura-giuridica/id/" + idTipoNaturaGiuridica;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[TipiNaturaGiuridicaApiServiceHelper::getTipoNaturaGiuridica] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<TipoNaturaGiuridicaDTO>> tipiNaturaGiuridicaListType = new GenericType<List<TipoNaturaGiuridicaDTO>>() {
            };
            result = resp.readEntity(tipiNaturaGiuridicaListType);
        } catch (ProcessingException e) {
            logger.error("[TipiNaturaGiuridicaApiServiceHelper::getTipoNaturaGiuridica] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[TipiNaturaGiuridicaApiServiceHelper::getTipoNaturaGiuridica] END");
        }
        return result;
    }


}