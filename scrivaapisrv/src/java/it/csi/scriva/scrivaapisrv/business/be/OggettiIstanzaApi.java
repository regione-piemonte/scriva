/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SearchOggettoDTO;

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

@Path("/oggetti-istanza")
@Produces(MediaType.APPLICATION_JSON)
public interface OggettiIstanzaApi {

    @GET
    public Response getOggettiIstanza(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/id/{idOggettoIstanza}")
    public Response getOggettoIstanzaById(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/{uidOggettoIstanza}")
    public Response getOggettoIstanzaByUID(@PathParam("uidOggettoIstanza") String uidOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @POST
    @Path("/search")
    @Consumes({ "application/json" })
    public Response searchOggettoIstanza(@RequestBody SearchOggettoDTO searchOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @POST
    @Consumes({ "application/json" })
    public Response saveOggettoIstanza(@RequestBody OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @PUT
    @Consumes({ "application/json" })
    public Response updateOggettoIstanza(@RequestBody OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @DELETE
    @Path("/{uidOggetto}")
    public Response deleteOggettoIstanza(@PathParam("uidOggetto") String uidOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/adempimento/{codAdempimento}/id-istanza/{idIstanza}")
    public Response verificaGeoreferenziazione(@PathParam("codAdempimento") String codAdempimento, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}