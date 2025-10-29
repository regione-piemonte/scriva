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

import it.csi.scriva.scrivabesrv.dto.IstanzaVincoloAutExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Vincoli autorizzazioni api.
 */
@Path("/istanza-vincoli-aut")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzaVincoliAutorizzazioniApi {

    /**
     * Load istanza vincolo autorizzazione by istanza response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param codVincolo      the cod vincolo
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    Response loadIstanzaVincoloAutorizzazioneByIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                                       @QueryParam("id_istanza") Long idIstanza, @QueryParam("cod_vincolo") String codVincolo,
                                                       @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save istanza vincolo autorizzazione response.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveIstanzaVincoloAutorizzazione(@RequestBody IstanzaVincoloAutExtendedDTO istanzaVincoloAut, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update istanza vincolo autorizzazione response.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIstanzaVincoloAutorizzazione(@RequestBody IstanzaVincoloAutExtendedDTO istanzaVincoloAut, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete istanza vincolo autorizzazione response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/{uid}")
    Response deleteIstanzaVincoloAutorizzazione(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}