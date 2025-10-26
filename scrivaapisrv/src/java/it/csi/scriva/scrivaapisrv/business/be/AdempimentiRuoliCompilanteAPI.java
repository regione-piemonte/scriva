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


@Path("/adempimenti-ruoli-compilante")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiRuoliCompilanteAPI {

    /**
     * @param securityContext getAdempimentiRuoliCompilante
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpRequest
     * @return Response
     */
    @GET
    public Response getAdempimentiRuoliCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext getAdempimentiRuoliCompilante
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpRequest
     * @return Response
     */
    @GET
    @Path("/id-ruolo-compilante/{idRuoloCompilante}")
    public Response getAdempimentiRuoliCompilanteByRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param idAdempimento idAdempimento
     * @param securityContext getAdempimentiRuoliCompilante
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpRequest
     * @return Response
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    public Response getAdempimentiRuoliCompilanteByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param idRuoloCompilante idRuoloCompilante
     * @param idAdempimento idAdempimento
     * @param securityContext getAdempimentiRuoliCompilante
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpRequest
     * @return Response
     */
    @GET
    @Path("/id-ruolo-compilante/{idRuoloCompilante}/id-adempimento/{idAdempimento}")
    public Response getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}