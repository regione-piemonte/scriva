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


import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompilantePreferenzaExtendedDTO;
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
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * The interface Compilanti api.
 */
@Path("/compilanti")
@Produces(MediaType.APPLICATION_JSON)
public interface CompilantiApi {

    /**
     * Gets compilanti.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the compilanti
     */
    @GET
    Response getCompilanti(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets compilante.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the compilante
     */
    @GET
    @Path("/user")
    Response getCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update compilanti response.
     *
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateCompilanti(@RequestBody Map<String, Object> requestBody,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;


    /**
     * Gets compilante by codice fiscale.
     *
     * @param codiceFiscale   the codice fiscale
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the compilante by codice fiscale
     */
    @GET
    @Path("/cf/{codiceFiscale}")
    Response getCompilanteByCodiceFiscale(@PathParam("codiceFiscale") String codiceFiscale, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Add preferenza response.
     *
     * @param preferenza      the preferenza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("/preferenze")
    Response addPreferenza(@RequestBody CompilantePreferenzaExtendedDTO preferenza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets preferenze by compilante.
     *
     * @param idCompilante the id compilante
     * @param httpHeaders  the http headers
     * @param httpRequest  the http request
     * @return the preferenze by compilante
     */
    @GET
    @Path("/preferenze/id-compilante/{idCompilante}")
    Response getPreferenzeByCompilante(@PathParam("idCompilante") Long idCompilante, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets preferenze by cf compilante.
     *
     * @param cfCompilante the cf compilante
     * @param httpHeaders  the http headers
     * @param httpRequest  the http request
     * @return the preferenze by cf compilante
     */
    @GET
    @Path("/preferenze/cf/{cfCompilante}")
    Response getPreferenzeByCfCompilante(@PathParam("cfCompilante") String cfCompilante, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete preferenza response.
     *
     * @param idPreferenza    the id preferenza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
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


    /**
     * Load preferenze notifiche by cf compilante response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return response response
     * @throws Exception the exception
     */
    @GET
    @Path("/preferenze-notifiche")
    Response loadPreferenzeNotificheByCfCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;


    /**
     * Upsert compilante preferenza canale response.
     *
     * @param compilantePreferenzaCanaleList the compilante preferenza canale list
     * @param securityContext                the security context
     * @param httpHeaders                    the http headers
     * @param httpRequest                    the http request
     * @param uriInfo                        the uri info
     * @return response response
     * @throws Exception the exception
     */
    @PUT
    @Path("/preferenze-notifiche")
    Response upsertCompilantePreferenzaCanale(@RequestBody List<Map<?, ?>> compilantePreferenzaCanaleList, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;


}