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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.GeecoUrlDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class GisApiServiceHelper extends AbstractServiceHelper {

    public GisApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public GeecoUrlDTO getGeecoUrl(MultivaluedMap<String, Object> requestHeaders, Integer idRuoloApplicativo, Long idOggettoIstanza) throws GenericException {
        logger.debug("[GisApiServiceHelper::getGeecoUrl] BEGIN");
        GeecoUrlDTO result = new GeecoUrlDTO();
        String targetUrl = this.endpointBase + "/geeco/id-ruolo-applicativo/" + idRuoloApplicativo + "/id-oggetto-istanza/" + idOggettoIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[GisApiServiceHelper::getGeecoUrl] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<GeecoUrlDTO> genType = new GenericType<GeecoUrlDTO>() {
            };
            result = resp.readEntity(genType);
        } catch (ProcessingException e) {
            logger.debug("[GisApiServiceHelper::getGeecoUrl] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[GisApiServiceHelper::getGeecoUrl] END");
        }
        return result;
    }
}