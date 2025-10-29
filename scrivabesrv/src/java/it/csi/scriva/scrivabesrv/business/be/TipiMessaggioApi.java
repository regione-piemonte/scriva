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

import it.csi.scriva.scrivabesrv.dto.TipoMessaggioDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Tipi messaggio api.
 *
 * @author CSI PIEMONTE
 */
@Path("/tipi-messaggio")
@Produces(MediaType.APPLICATION_JSON)
public interface TipiMessaggioApi {

    /**
     * Load tipi messaggio response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Produces({"application/json"})
    Response loadTipiMessaggio(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load tipo messaggio response.
     *
     * @param idTipoMessaggio idTipoMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-tipo-messaggio/{idTipoMessaggio}")
    Response loadTipoMessaggio(@PathParam("idTipoMessaggio") Long idTipoMessaggio, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load tipo messaggio by code response.
     *
     * @param codTipoMessaggio codTipoMessaggio
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/codice-tipo-messaggio/{codTipoMessaggio}")
    Response loadTipoMessaggioByCode(@PathParam("codTipoMessaggio") String codTipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Save tipo messaggio response.
     *
     * @param tipoMessaggio   TipoMessaggioDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    Response saveTipoMessaggio(@RequestBody TipoMessaggioDTO tipoMessaggio, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update tipo messaggio response.
     *
     * @param tipoMessaggio   TipoMessaggioDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    Response updateTipoMessaggio(@RequestBody TipoMessaggioDTO tipoMessaggio, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete tipo messaggio response.
     *
     * @param idTipoMessaggio idTipoMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{idTipoMessaggio}")
    Response deleteTipoMessaggio(@PathParam("idTipoMessaggio") Long idTipoMessaggio, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}