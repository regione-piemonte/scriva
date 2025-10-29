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
 * The interface Messaggi api.
 *
 * @author CSI PIEMONTE
 */
@Path("/messaggi")
@Produces(MediaType.APPLICATION_JSON)
public interface MessaggiApi {

    /**
     * Load messaggi response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadMessaggi(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load messaggio response.
     *
     * @param idMessaggio     idMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-messaggio/{idMessaggio}")
    Response loadMessaggio(@PathParam("idMessaggio") Long idMessaggio, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load messaggio by code response.
     *
     * @param codMessaggio    codMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/codice-messaggio/{codMessaggio}")
    Response loadMessaggioByCode(@PathParam("codMessaggio") String codMessaggio, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load messaggi by code tipo messaggio response.
     *
     * @param codTipoMessaggio codTipoMessaggio
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/codice-tipo-messaggio/{codTipoMessaggio}")
    Response loadMessaggiByCodeTipoMessaggio(@PathParam("codTipoMessaggio") String codTipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}