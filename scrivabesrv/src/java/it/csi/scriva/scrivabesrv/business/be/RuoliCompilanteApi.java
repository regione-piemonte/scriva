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
 * The interface Ruoli compilante api.
 *
 * @author CSI PIEMONTE
 */
@Path("/ruoli-compilante")
@Produces(MediaType.APPLICATION_JSON)
public interface RuoliCompilanteApi {

    /**
     * Load ruoli compilante response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadRuoliCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load ruolo compilante response.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id/{idRuoloCompilante}")
    Response loadRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load ruolo compilante by code response.
     *
     * @param codRuoloCompilante codRuoloCompilante
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/codice/{codRuoloCompilante}")
    Response loadRuoloCompilanteByCode(@PathParam("codRuoloCompilante") String codRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load ruoli compilante by adempimento response.
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/adempimento/{idAdempimento}")
    Response loadRuoliCompilanteByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}