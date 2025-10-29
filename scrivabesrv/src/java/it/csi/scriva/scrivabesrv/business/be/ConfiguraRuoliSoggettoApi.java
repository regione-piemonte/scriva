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

import it.csi.scriva.scrivabesrv.dto.ConfiguraRuoloSoggettoExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
 * The interface Configura ruoli soggetto api.
 *
 * @author CSI PIEMONTE
 */
@Path("/configura-ruoli-soggetti")
@Produces(MediaType.APPLICATION_JSON)
public interface ConfiguraRuoliSoggettoApi {

    /**
     * Load configura ruoli soggetto response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadConfiguraRuoliSoggetto(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load configura ruoli soggetto by ruolo compilante response.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/ruolo-compilante/{idRuoloCompilante}")
    Response loadConfiguraRuoliSoggettoByRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load configura ruoli soggetto by adempimento response.
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/adempimento/{idAdempimento}")
    Response loadConfiguraRuoliSoggettoByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load configura ruoli soggetto by ruolo soggetto response.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/ruolo-soggetto/{idRuoloSoggetto}")
    Response loadConfiguraRuoliSoggettoByRuoloSoggetto(@PathParam("idRuoloSoggetto") Long idRuoloSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save configura ruolo soggetto response.
     *
     * @param configuraRuoloSoggetto ConfiguraRuoloSoggettoExtendedDTO
     * @param securityContext        SecurityContext
     * @param httpHeaders            HttpHeaders
     * @param httpRequest            HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveConfiguraRuoloSoggetto(@RequestBody ConfiguraRuoloSoggettoExtendedDTO configuraRuoloSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}