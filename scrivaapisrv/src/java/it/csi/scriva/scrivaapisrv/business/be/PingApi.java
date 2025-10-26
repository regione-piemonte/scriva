/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public interface PingApi  {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    @GET
    @Path("/spid")
    public Response testSPID(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/fake/fasi")
    public Response getFasiFake(@QueryParam(value = "protetto") Boolean protetto, @QueryParam(value = "incidenza") Boolean incidenza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/pdf/{idIstanza}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response testPDF(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/cosmo/doc")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getDocCosmo(@QueryParam("link_documento") String linkdocumento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

//    @GET
//    @Path("/doc/{idIstanza}")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response compilaDoc(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

//    @GET
//    @Path("/json/{idIstanza}")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    public Response testJsonIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}