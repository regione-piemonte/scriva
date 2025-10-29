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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
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
 *
 * @author CSI PIEMONTE
 */
@Path("/soggetti-istanza")
@Produces(MediaType.APPLICATION_JSON)
public interface SoggettiIstanzaApi {

    /**
     * Load soggetti istanza response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadSoggettiIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                 @QueryParam("id_istanza") Long idIstanza,
                                 @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetto istanza response.
     *
     * @param idSoggettoIstanza idSoggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id/{idSoggettoIstanza}")
    Response loadSoggettoIstanza(@PathParam("idSoggettoIstanza") Long idSoggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetto istanza by id soggetto padre response.
     *
     * @param idSoggettoPadre idSoggettoPadre
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-padre/{idSoggettoPadre}")
    Response loadSoggettoIstanzaByIdSoggettoPadre(@PathParam("idSoggettoPadre") Long idSoggettoPadre, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetto istanza by id istanza response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-istanza/{idIstanza}")
    Response loadSoggettoIstanzaByIdIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetti istanza by codice fiscale soggetto response.
     *
     * @param codiceFiscale   codiceFiscale
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/cf/{codiceFiscale}")
    Response loadSoggettiIstanzaByCodiceFiscaleSoggetto(@PathParam("codiceFiscale") String codiceFiscale, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save soggetto istanza response.
     *
     * @param soggettoIstanza SoggettoIstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveSoggettoIstanza(@RequestBody SoggettoIstanzaExtendedDTO soggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update soggetto istanza response.
     *
     * @param soggettoIstanza SoggettoIstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateSoggettoIstanza(@RequestBody SoggettoIstanzaExtendedDTO soggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Delete soggetto istanza response.
     *
     * @param uid             uidSoggettoIStanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{uid}")
    Response deleteSoggettoIstanza(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete soggetto istanza by id response.
     *
     * @param idSoggettoIstanza idSoggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/id/{idSoggettoIstanza}")
    Response deleteSoggettoIstanzaById(@PathParam("idSoggettoIstanza") Long idSoggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
    Response loadRecapitiAlternativiByIdSoggettoIstanza(@PathParam("idSoggettoIstanza") Long idSoggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
    Response updateRecapitoAlternativo(@RequestBody RecapitoAlternativoExtendedDTO recapitoAlternativo, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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