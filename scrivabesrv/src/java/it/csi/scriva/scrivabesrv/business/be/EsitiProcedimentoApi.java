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

import javax.servlet.http.HttpServletRequest;
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
 * The interface Esiti procedimento api.
 *
 * @author CSI PIEMONTE
 */
@Path("/esiti-procedimento")
@Produces(MediaType.APPLICATION_JSON)
public interface EsitiProcedimentoApi {

    /**
     * Load esiti procedimento response.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param idEsitoProcedimento  the id esito procedimento
     * @param codEsitoProcedimento the cod esito procedimento
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
    @GET
    Response loadEsitiProcedimento(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                   @QueryParam("id") Long idEsitoProcedimento, @QueryParam("codice") String codEsitoProcedimento,
                                   @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}