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

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SoggettoIstanzaExtendedDTO;
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
 * The interface Soggetti istanza api.
 */
@Path("/soggetti-istanza")
@Produces(MediaType.APPLICATION_JSON)
public interface SoggettiIstanzaApi {

    /**
     * Gets soggetti istanza.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the soggetti istanza
     */
    @GET
    Response getSoggettiIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                @QueryParam("id_istanza") Long idIstanza,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets soggetto istanza.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the soggetto istanza
     */
    @GET
    @Path("/id/{idSoggettoIstanza}")
    Response getSoggettoIstanza(@PathParam("idSoggettoIstanza") Long idSoggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets soggetto istanza by id soggetto padre.
     *
     * @param idSoggettoPadre the id soggetto padre
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the soggetto istanza by id soggetto padre
     */
    @GET
    @Path("/id-padre/{idSoggettoPadre}")
    Response getSoggettoIstanzaByIdSoggettoPadre(@PathParam("idSoggettoPadre") Long idSoggettoPadre, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets soggetti istanza by codice fiscale soggetto.
     *
     * @param codiceFiscale   the codice fiscale
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the soggetti istanza by codice fiscale soggetto
     */
    @GET
    @Path("/cf/{codiceFiscale}")
    Response getSoggettiIstanzaByCodiceFiscaleSoggetto(@PathParam("codiceFiscale") String codiceFiscale, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save soggetto istanza response.
     *
     * @param soggettoIstanza the soggetto istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveSoggettoIstanza(@RequestBody SoggettoIstanzaExtendedDTO soggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update soggetto istanza response.
     *
     * @param soggettoIstanza the soggetto istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateSoggettoIstanza(@RequestBody SoggettoIstanzaExtendedDTO soggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete soggetto istanza response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/{uid}")
    Response deleteSoggettoIstanza(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete soggetto istanza by id response.
     *
     * @param id              the id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/id/{id}")
    Response deleteSoggettoIstanzaById(@PathParam("id") Long id, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load recapiti alternativi by id soggetto istanza response.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @GET
    @Path("/recapiti-alternativi/id/{idSoggettoIstanza}")
    Response getRecapitiAlternativiByIdSoggettoIstanza(@PathParam("idSoggettoIstanza") Long idSoggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save recapito alternativo response.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @POST
    @Path("/recapiti-alternativi")
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveRecapitoAlternativo(@RequestBody RecapitoAlternativoExtendedDTO recapitoAlternativo, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update recapito alternativo response.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @PUT
    @Path("/recapiti-alternativi")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateRecapitoAlternativo(@RequestBody it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.RecapitoAlternativoExtendedDTO recapitoAlternativo, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete recapito alternativo response.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @DELETE
    @Path("/recapiti-alternativi/id/{idSoggettoIstanza}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response deleteRecapitoAlternativo(@PathParam("idSoggettoIstanza") Long idSoggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


}