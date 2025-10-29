/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.LogoutApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Logout api service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class LogoutApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements LogoutApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * Logout response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response logout(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBegin(className);
        Response response = getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
        httpRequest.getSession().invalidate();
        return response;
    }

}