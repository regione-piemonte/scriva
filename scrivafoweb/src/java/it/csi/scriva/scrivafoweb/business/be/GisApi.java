/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be;

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
import javax.ws.rs.core.UriInfo;
import java.util.Map;

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
     * @param idRuoloApplicativo the id ruolo applicativo
     * @param idOggettoIstanza   the id oggetto istanza
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the geeco url
     */
    @GET
    @Path("/id-ruolo-applicativo/{idRuoloApplicativo}/id-oggetto-istanza/{idOggettoIstanza}")
    Response getGeecoUrl(@PathParam("idRuoloApplicativo") Integer idRuoloApplicativo,
                         @PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                         @Context HttpServletRequest httpRequest);

    /**
     * Gets url.
     *
     * @param geecoUrlReq     the geeco url req dto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the url
     * @throws Exception the exception
     */
    @POST
    @Path("/url")
    Response getUrl(@RequestBody Map<String, Object> geecoUrlReq,
                    @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;
}