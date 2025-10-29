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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Search api.
 *
 * @author CSI PIEMONTE
 */
@Path("/stati-procedimento-statale")
@Produces(MediaType.APPLICATION_JSON)
public interface StatiProcedimentoStataleApi {

    /**
     * Load stati procedimento statale response.
     *
     * @param codStatoProcStatale the cod stato proc statale
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @GET
    @Path("")
    Response loadStatiProcedimentoStatale(@QueryParam("cod_stato_proced_statale") String codStatoProcStatale, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

}