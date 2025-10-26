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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.GeecoUrlDTO;

public class GisApiServiceHelper extends AbstractServiceHelper {

    public GisApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public GeecoUrlDTO getGeecoUrl(MultivaluedMap<String, Object> requestHeaders, Integer idRuoloApplicativo, Long idOggettoIstanza) throws GenericException {
        LOGGER.debug("[GisApiServiceHelper::getGeecoUrl] BEGIN");
        GeecoUrlDTO result = new GeecoUrlDTO();
        String targetUrl = this.endpointBase + "/geeco/id-ruolo-applicativo/" + idRuoloApplicativo + "/id-oggetto-istanza/" + idOggettoIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[GisApiServiceHelper::getGeecoUrl] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<GeecoUrlDTO> genType = new GenericType<GeecoUrlDTO>() {
            };
            result = resp.readEntity(genType);
        } catch (ProcessingException e) {
            LOGGER.debug("[GisApiServiceHelper::getGeecoUrl] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[GisApiServiceHelper::getGeecoUrl] END");
        }
        return result;
    }
}