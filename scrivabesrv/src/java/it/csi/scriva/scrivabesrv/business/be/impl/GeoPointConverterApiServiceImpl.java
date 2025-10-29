/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.GeoPointConverterApi;
import it.csi.scriva.scrivabesrv.business.be.service.GeoPointConverterService;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GeoPointConverterApiServiceImpl extends BaseApiServiceImpl implements GeoPointConverterApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    GeoPointConverterService service;

    /**
     * Load tipologie oggetto by ademinpimento coderesponse.
     *
     * @param latitudine      the latitudine
     * @param longitudine     the longitudine
     * @param sourceEPSG      the optional sourceEPSG
     * @param targetEPSG      the optional targetEPSG
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response convertPoint(BigDecimal latitudine, BigDecimal longitudine, Integer sourceEPSG, Integer targetEPSG, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "latitudine [" + latitudine + "] - longitudine [" + longitudine + "] - sourceEPSG [" + sourceEPSG + "] - targetEPSG [" + targetEPSG + "]");
        if (latitudine == null || longitudine == null) {
            ErrorDTO error = getErrorManager().getError("404", "E036", "latitudine e longitudine sono obbligatori.", null, null);
            logError(className, error);
            return Response.serverError().entity(error).status(404).build();
        }
        return getResponseList(service.convert(sourceEPSG, targetEPSG, latitudine, longitudine), className);
    }

}