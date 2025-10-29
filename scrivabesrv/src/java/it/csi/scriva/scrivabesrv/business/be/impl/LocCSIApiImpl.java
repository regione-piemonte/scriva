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

import it.csi.scriva.scrivabesrv.business.be.LocCSIApi;
import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;
import it.csi.scriva.scrivabesrv.business.be.helper.loccsi.LOCCSIServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LocCSIApiImpl extends BaseApiServiceImpl implements LocCSIApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private LOCCSIServiceHelper loccsiServiceHelper;

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
    public Response loadSuggest(String xRequestAuth, String xRequestId, String xForwardedFor, String query, Integer limit, Integer offset, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws CosmoException {
        logBeginInfo(className, "query : [" + query + "] - limit : [" + limit + "] - offset : [" + offset + "]");
        return getResponseList(loccsiServiceHelper.suggestToponomastica(xRequestId, xForwardedFor, query, limit, offset), className);
    }

}