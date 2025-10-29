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

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.sql.Date;

/**
 * The interface Ping api.
 *
 * @author CSI PIEMONTE
 */
@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public interface PingApi {

    /**
     * Ping response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response ping(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test spid response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/spid")
    Response testSPID(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets fasi fake.
     *
     * @param protetto        the protetto
     * @param incidenza       the incidenza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the fasi fake
     */
    @GET
    @Path("/fake/fasi")
    Response getFasiFake(@QueryParam(value = "protetto") Boolean protetto, @QueryParam(value = "incidenza") Boolean incidenza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test pdf response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/pdf/{idIstanza}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response testPDF(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets json data.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the json data
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/json-data/{idIstanza}")
    Response getJsonData(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws GenericException;

    /**
     * Manager notifiche response.
     *
     * @param idIstanza         the id istanza
     * @param codTipoevento     the cod tipoevento
     * @param codCanaleNotifica the cod canale notifica
     * @param rifCanaleNotifica the rif canale notifica
     * @param dataIntegrazione  the data integrazione
     * @param abilitaInvio      the abilita invio
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @param uriInfo           the uri info
     * @return response response
     */
    @GET
    @Path("/notifica/manager")
    Response managerNotifiche(@QueryParam("idIstanza") Long idIstanza,
                              @QueryParam("codTipoevento") String codTipoevento,
                              @QueryParam("codCanaleNotifica") String codCanaleNotifica,
                              @QueryParam("rifCanaleNotifica") String rifCanaleNotifica,
                              @QueryParam("dataIntegrazione") Date dataIntegrazione,
                              @QueryParam("abilitaInvio") Boolean abilitaInvio,
                              @Context SecurityContext securityContext,
                              @Context HttpHeaders httpHeaders,
                              @Context HttpServletRequest httpRequest,
                              @Context UriInfo uriInfo);
}