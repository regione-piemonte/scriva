/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be;

import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface LOCCSI api.
 *
 * @author CSI PIEMONTE
 */
@Path("/loccsi")
@Produces(MediaType.APPLICATION_JSON)
public interface LocCSIApi {

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
    @GET
    Response loadSuggest(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                         @QueryParam("q") String query, @DefaultValue("20") @QueryParam("limit") Integer limit, @DefaultValue("0") @QueryParam("offset") Integer offset,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws CosmoException;

}