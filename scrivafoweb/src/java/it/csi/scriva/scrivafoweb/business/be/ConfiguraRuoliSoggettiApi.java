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


/**
 * The interface Configura ruoli soggetti api.
 */
@Path("/configura-ruoli-soggetti")
@Produces(MediaType.APPLICATION_JSON)
public interface ConfiguraRuoliSoggettiApi {

    /**
     * Gets configura ruoli soggetti.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the configura ruoli soggetti
     */
    @GET
    Response getConfiguraRuoliSoggetti(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets configura ruoli soggetti by ruolo compilante.
     *
     * @param idRuoloCompilante the id ruolo compilante
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the configura ruoli soggetti by ruolo compilante
     */
    @GET
    @Path("/ruolo-compilante/{idRuoloCompilante}")
    Response getConfiguraRuoliSoggettiByRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets configura ruoli soggetti by adempimento.
     *
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the configura ruoli soggetti by adempimento
     */
    @GET
    @Path("/adempimento/{idAdempimento}")
    Response getConfiguraRuoliSoggettiByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets configura ruoli soggetti by ruolo soggetto.
     *
     * @param idRuoloSoggetto the id ruolo soggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the configura ruoli soggetti by ruolo soggetto
     */
    @GET
    @Path("/ruolo-soggetto/{idRuoloSoggetto}")
    Response getConfiguraRuoliSoggettiByRuoloSoggetto(@PathParam("idRuoloSoggetto") Long idRuoloSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}