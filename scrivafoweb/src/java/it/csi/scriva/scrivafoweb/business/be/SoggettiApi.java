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

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SoggettoExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

/**
 * The interface Soggetti api.
 */
@Path("/soggetti")
@Produces(MediaType.APPLICATION_JSON)
public interface SoggettiApi {

    /**
     * Gets soggetti.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the soggetti
     */
    @GET
    Response getSoggetti(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets soggetto by id.
     *
     * @param idSoggetto      the id soggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the soggetto by id
     */
    @GET
    @Path("/id/{idSoggetto}")
    Response getSoggettoById(@PathParam("idSoggetto") Long idSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets soggetto by codice fiscale and tipo.
     *
     * @param codiceFiscale   the codice fiscale
     * @param tipoSoggetto    the tipo soggetto
     * @param tipoAdempimento the tipo adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the soggetto by codice fiscale and tipo
     */
    @GET
    @Path("/cf/{codiceFiscale}/tipo-soggetto/{tipoSoggetto}/tipo-adempimento/{tipoAdempimento}")
    Response getSoggettoByCodiceFiscaleAndTipo(@PathParam("codiceFiscale") String codiceFiscale, @PathParam("tipoSoggetto") String tipoSoggetto, @PathParam("tipoAdempimento") String tipoAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets soggetto impresa.
     *
     * @param codiceFiscaleImpresa  the codice fiscale impresa
     * @param codiceFiscaleSoggetto the codice fiscale soggetto
     * @param tipoSoggetto          the tipo soggetto
     * @param tipoAdempimento       the tipo adempimento
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the soggetto impresa
     */
    @GET
    @Path("/cf-impresa/{codiceFiscaleImpresa}/cf-soggetto/{codiceFiscaleSoggetto}/tipo-soggetto/{tipoSoggetto}/tipo-adempimento/{tipoAdempimento}")
    Response getSoggettoImpresa(@PathParam("codiceFiscaleImpresa") String codiceFiscaleImpresa, @PathParam("codiceFiscaleSoggetto") String codiceFiscaleSoggetto, @PathParam("tipoSoggetto") String tipoSoggetto, @PathParam("tipoAdempimento") String tipoAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save soggetto response.
     *
     * @param soggetto        the soggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Consumes({"application/json"})
    Response saveSoggetto(@RequestBody @Valid SoggettoExtendedDTO soggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update soggetto response.
     *
     * @param soggetto        the soggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @PUT
    @Consumes({"application/json"})
    Response updateSoggetto(@RequestBody SoggettoExtendedDTO soggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete soggetto response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/id/{uid}")
    Response deleteSoggetto(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}