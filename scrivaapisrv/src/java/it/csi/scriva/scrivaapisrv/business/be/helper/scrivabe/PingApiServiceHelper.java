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

import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.dto.Ping;

import java.util.ArrayList;
import java.util.List;

public class PingApiServiceHelper extends AbstractServiceHelper {

    public PingApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<Ping> ping(MultivaluedMap<String, Object> requestHeaders) {
        LOGGER.debug("[PingApiServiceHelper::ping] BEGIN");
        List<Ping> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ping";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            GenericType<List<Ping>> pingListType = new GenericType<List<Ping>>() {
            };
            result = resp.readEntity(pingListType);
        } catch (ProcessingException e) {
            LOGGER.error("[PingApiServiceHelper::ping] EXCEPTION : " + e.getMessage());
            Ping errorPing = new Ping();
            errorPing.setLabel("Database");
            errorPing.setStatus("KO");
            result.add(errorPing);
        }
        LOGGER.debug("[PingApiServiceHelper::ping] END");
        return result;
    }


    public byte[] testPDF(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) {
        LOGGER.debug("[PingApiServiceHelper::testPDF] BEGIN");
        byte[] result = null;
        String targetUrl = this.endpointBase + "/ping/pdf/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            GenericType<byte[]> bao = new GenericType<byte[]>() {
            };
            result = resp.readEntity(bao);
        } catch (ProcessingException e) {
            LOGGER.error("[PingApiServiceHelper::testPDF] EXCEPTION : " + e.getMessage());
        }
        LOGGER.debug("[PingApiServiceHelper::testPDF] END");
        return result;
    }
}