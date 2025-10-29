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
 * The interface Tipi soggetto api.
 *
 * @author CSI PIEMONTE
 */
@Path("/tipi-soggetto")
@Produces(MediaType.APPLICATION_JSON)
public interface TipiSoggettoApi {

    /**
     * Gets tipi soggetto.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response tipi soggetto
     */
    @GET
    Response getTipiSoggetto(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets tipo soggetto.
     *
     * @param idTipoSoggetto  idTipoSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response tipo soggetto
     */
    @GET
    @Path("/id/{idTipoSoggetto}")
    Response getTipoSoggetto(@PathParam("idTipoSoggetto") Long idTipoSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets tipo soggetto by code.
     *
     * @param codTipoSoggetto codTipoSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response tipo soggetto by code
     */
    @GET
    @Path("/codice/{codTipoSoggetto}")
    Response getTipoSoggettoByCode(@PathParam("codTipoSoggetto") String codTipoSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}