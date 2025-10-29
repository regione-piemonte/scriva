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
 * The interface Tipi natura giuridica api.
 *
 * @author CSI PIEMONTE
 */
@Path("/tipi-natura-giuridica")
@Produces(MediaType.APPLICATION_JSON)
public interface TipiNaturaGiuridicaApi {

    /**
     * Load tipi natura giuridica response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadTipiNaturaGiuridica(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load tipo natura giuridica response.
     *
     * @param idTipoNaturaGiuridica idTipoNaturaGiuridica
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id/{idTipoNaturaGiuridica}")
    Response loadTipoNaturaGiuridica(@PathParam("idTipoNaturaGiuridica") Long idTipoNaturaGiuridica, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load tipo natura giuridica by code response.
     *
     * @param codTipoNaturaGiuridica codTipoNaturaGiuridica
     * @param securityContext        SecurityContext
     * @param httpHeaders            HttpHeaders
     * @param httpRequest            HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/codice/{codTipoNaturaGiuridica}")
    Response loadTipoNaturaGiuridicaByCode(@PathParam("codTipoNaturaGiuridica") String codTipoNaturaGiuridica, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}