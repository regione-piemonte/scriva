/*

========================LICENSE_START=================================
Copyright (C) 2025 Regione Piemonte
SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
SPDX-License-Identifier: EUPL-1.2
=========================LICENSE_END==================================
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

@Path("/adempimenti-config")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiConfigApi {

    @GET
    public Response getAdempimentiConfig( @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/adempimento/{desAdempimento}")
    public Response getAdempimentoConfigByAdempimento(@PathParam("desAdempimento") String desAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    @GET
    @Path("/adempimento/{desAdempimento}/info/{infoScriva}")
    public Response getAdempimentoConfigByAdempimentoInformazione(@PathParam("desAdempimento") String desAdempimento, @PathParam("infoScriva") String infoScriva, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    @GET
    @Path("/adempimento/{desAdempimento}/info/{infoScriva}/chiave/{chiave}")
    public Response getAdempimentoConfigByAdempimentoInformazioneChiave(@PathParam("desAdempimento") String desAdempimento, @PathParam("infoScriva") String infoScriva, @PathParam("chiave") String chiave, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}