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

import it.csi.scriva.scrivafoweb.business.be.LocCSIApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Loc csi api service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class LocCSIApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements LocCSIApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * Load sugget response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param query           the query
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadSuggest(String xRequestAuth, String xRequestId, String xForwardedFor, String query, Integer limit, Integer offset, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className,"query [" + query + "] - offset [" + offset + "] - limit [" + limit + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }
}