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

import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import java.util.List;

/**
 * The interface Compilanti api.
 *
 * @author CSI PIEMONTE
 */
@Path("/compilanti")
@Produces(MediaType.APPLICATION_JSON)
public interface CompilantiApi {

    /**
     * Load compilanti response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadCompilanti(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update compilanti response.
     *
     * @param compilante      the compilante
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateCompilanti(@RequestBody CompilanteDTO compilante,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load compilante by codice fiscale response.
     *
     * @param codiceFiscale   codiceFiscale
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/cf/{codiceFiscale}")
    Response loadCompilanteByCodiceFiscale(@PathParam("codiceFiscale") String codiceFiscale, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load preferenze by compilante response.
     *
     * @param idCompilante idCompilante
     * @param httpHeaders  HttpHeaders
     * @param httpRequest  HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/preferenze/id-compilante/{idCompilante}")
    Response loadPreferenzeByCompilante(@PathParam("idCompilante") Long idCompilante, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load preferenze by cf compilante response.
     *
     * @param cfCompilante cfCompilante
     * @param httpHeaders  HttpHeaders
     * @param httpRequest  HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/preferenze/cf/{cfCompilante}")
    Response loadPreferenzeByCfCompilante(@PathParam("cfCompilante") String cfCompilante, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Add preferenza response.
     *
     * @param preferenza      CompilantePreferenzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Path("/preferenze")
    Response addPreferenza(@RequestBody CompilantePreferenzaExtendedDTO preferenza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load preferenze notifiche by cf compilante response.
     *
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @return the response
     */
    @GET
    @Path("/preferenze-notifiche")
    Response loadPreferenzeNotificheByCfCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Upsert compilante preferenza canale response.
     *
     * @param compilantePreferenzaCanaleList the compilante preferenza canale list
     * @param httpHeaders                    the http headers
     * @param httpRequest                    the http request
     * @return the response
     */
    @PUT
    @Path("/preferenze-notifiche")
    Response upsertCompilantePreferenzaCanale(@RequestBody List<CompilantePreferenzaCanaleExtendedDTO> compilantePreferenzaCanaleList, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete preferenza response.
     *
     * @param idPreferenza    idPreferenza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/preferenze/{idPreferenza}")
    Response deletePreferenza(@PathParam("idPreferenza") Long idPreferenza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Logout response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/logout")
    Response logout(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}