/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/istanze-template-quadri")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzaTemplateQuadroApi {

    @GET
    @Path("/id-istanza/{idIstanza}")
    public Response getIstanzaTemplateQuadroByIdIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
    public Response getIstanzaTemplateQuadro(@PathParam("idIstanza") Long idIstanza, @PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/code-template/{codeTemplate}/id-istanza/{idIstanza}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplateQuadriByCodeTemplateAndIdIstanza(@PathParam("codeTemplate") String codeTemplate, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @POST
    @Consumes({ "application/json" })
    public Response saveIstanzaTemplateQuadro(@RequestBody IstanzaTemplateQuadroDTO istanzaTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @PUT
    @Consumes({ "application/json" })
    public Response updateIstanzaTemplateQuadro(@RequestBody IstanzaTemplateQuadroDTO istanzaTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @DELETE
    @Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
    public Response deleteIstanzaTemplateQuadro(@PathParam("idIstanza") Long idIstanza, @PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}