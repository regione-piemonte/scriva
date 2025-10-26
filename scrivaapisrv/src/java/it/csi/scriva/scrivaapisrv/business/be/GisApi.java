/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**********************************************
 * CSI PIEMONTE
 **********************************************/
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

@Path("/geeco")
@Produces(MediaType.APPLICATION_JSON)
public interface GisApi {

    @GET
    @Path("/id-ruolo-applicativo/{idRuoloApplicativo}/id-oggetto-istanza/{idOggettoIstanza}")
    public Response getGeecoUrl(@PathParam("idRuoloApplicativo") Integer idRuoloApplicativo,
            @PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
            @Context HttpServletRequest httpRequest);
}