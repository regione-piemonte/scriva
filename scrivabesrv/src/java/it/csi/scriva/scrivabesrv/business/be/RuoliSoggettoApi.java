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
 * The interface Ruoli soggetto api.
 *
 * @author CSI PIEMONTE
 */
@Path("/ruoli-soggetto")
@Produces(MediaType.APPLICATION_JSON)
public interface RuoliSoggettoApi {

    /**
     * Load ruoli soggetto response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadRuoliSoggetto(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load ruolo soggetto response.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id/{idRuoloSoggetto}")
    Response loadRuoloSoggetto(@PathParam("idRuoloSoggetto") Long idRuoloSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load ruolo soggetto by code response.
     *
     * @param codRuoloSoggetto codRuoloSoggetto
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/codice/{codRuoloSoggetto}")
    Response loadRuoloSoggettoByCode(@PathParam("codRuoloSoggetto") String codRuoloSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load ruoli soggetto by id ruolo compilante and adempimento response.
     *
     * @param idAdempimento     idAdempimento
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}/id-ruolo-compilante/{idRuoloCompilante}")
    Response loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento(@PathParam("idAdempimento") Long idAdempimento, @PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


}