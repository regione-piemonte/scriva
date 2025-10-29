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

import it.csi.scriva.scrivabesrv.dto.GeecoUrlReqDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Gis api.
 *
 * @author CSI PIEMONTE
 */
@Path("/geeco")
@Produces(MediaType.APPLICATION_JSON)
public interface GisApi {

    /**
     * Gets geeco url.
     *
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @param idRuoloApplicativo idRuoloApplicativo
     * @param idOggettoIstanza   idOggettoIstanza
     * @return Response geeco url
     */
    @GET
    @Path("/id-ruolo-applicativo/{idRuoloApplicativo}/id-oggetto-istanza/{idOggettoIstanza}")
    Response getGeecoUrl(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                         @Context HttpServletRequest httpRequest, @PathParam("idRuoloApplicativo") int idRuoloApplicativo,
                         @PathParam("idOggettoIstanza") Long idOggettoIstanza);

    /**
     * Gets url.
     *
     * @param geecoUrlReqDTO  the geeco url req dto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the url
     */
    @POST
    @Path("/url")
    Response getUrl(@RequestBody GeecoUrlReqDTO geecoUrlReqDTO,
                    @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}