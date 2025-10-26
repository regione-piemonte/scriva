/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;



@Path("/configura-ruoli-soggetti")
@Produces(MediaType.APPLICATION_JSON)
public interface ConfiguraRuoliSoggettiApi {

    @GET
    public Response getConfiguraRuoliSoggetti(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/ruolo-compilante/{idRuoloCompilante}")
    public Response getConfiguraRuoliSoggettiByRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/adempimento/{idAdempimento}")
    public Response getConfiguraRuoliSoggettiByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/ruolo-soggetto/{idRuoloSoggetto}")
    public Response getConfiguraRuoliSoggettiByRuoloSoggetto(@PathParam("idRuoloSoggetto") Long idRuoloSoggetto,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}