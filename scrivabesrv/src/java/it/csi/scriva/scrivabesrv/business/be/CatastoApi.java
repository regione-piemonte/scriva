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
import javax.ws.rs.HeaderParam;
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
 * The interface Catasto api.
 *
 * @author CSI PIEMONTE
 */
@Path("/catasto")
@Produces(MediaType.APPLICATION_JSON)
public interface CatastoApi {

    /**
     * Load comuni response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param candidateName   the candidate name
     * @param expansionMode   the expansion mode
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/comuni")
    Response loadComuniCatasto(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                               @QueryParam("candidateName") String candidateName, @QueryParam("expansionMode") String expansionMode, @QueryParam("epsg") Integer epsg,
                               @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset, @Context SecurityContext securityContext,
                               @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load sezioni per comune response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/comuni/{codiceComune}/sezioni")
    Response loadSezioniPerComune(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                                  @PathParam("codiceComune") String codiceComune, @QueryParam("epsg") Integer epsg, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset,
                                  @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load sezione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param epsg            the epsg
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/comuni/{codiceComune}/sezioni/{codiceSezione}")
    Response loadSezione(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                         @PathParam("codiceComune") String codiceComune, @PathParam("codiceSezione") String codiceSezione, @QueryParam("epsg") Integer epsg,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load fogli per comune per sezione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/comuni/{codiceComune}/sezioni/{codiceSezione}/fogli")
    Response loadFogliPerComunePerSezione(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                                          @PathParam("codiceComune") String codiceComune, @PathParam("codiceSezione") String codiceSezione, @QueryParam("epsg") Integer epsg,
                                          @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset, @Context SecurityContext securityContext,
                                          @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load foglio response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param numeroFoglio    the numero foglio
     * @param epsg            the epsg
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/comuni/{codiceComune}/sezioni/{codiceSezione}/fogli/{numeroFoglio}")
    Response loadFoglio(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                        @PathParam("codiceComune") String codiceComune, @PathParam("codiceSezione") String codiceSezione, @PathParam("numeroFoglio") String numeroFoglio,
                        @QueryParam("epsg") Integer epsg, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load fogli intersecanti geometria response.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @GET
    @Path("/fogli/interseca")
    Response loadFogliIntersecantiGeometria(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                                            @QueryParam("id_oggetto_istanza") Long idOggettoIstanza,
                                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load fogli contenuti geometria response.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @GET
    @Path("/fogli/contiene")
    Response loadFogliContenutiGeometria(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                                         @QueryParam("id_oggetto_istanza") Long idOggettoIstanza,
                                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load particelle per foglio per sezione per comune response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param numeroFoglio    the numero foglio
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/comuni/{codiceComune}/sezioni/{codiceSezione}/fogli/{numeroFoglio}/particelle")
    Response loadParticellePerFoglioPerSezionePerComune(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                        @PathParam("codiceComune") String codiceComune, @PathParam("codiceSezione") String codiceSezione, @PathParam("numeroFoglio") String numeroFoglio,
                        @QueryParam("epsg") Integer epsg, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset, @Context SecurityContext securityContext,
                        @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load particella response.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param codiceComune     the codice comune
     * @param codiceSezione    the codice sezione
     * @param numeroFoglio     the numero foglio
     * @param numeroParticella the numero particella
     * @param epsg             the epsg
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @GET
    @Path("/comuni/{codiceComune}/sezioni/{codiceSezione}/fogli/{numeroFoglio}/particelle/{numeroParticella}")
    Response loadParticella(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                            @PathParam("codiceComune") String codiceComune, @PathParam("codiceSezione") String codiceSezione, @PathParam("numeroFoglio") String numeroFoglio,
                            @PathParam("numeroParticella") String numeroParticella,  @QueryParam("epsg") Integer epsg, @Context SecurityContext securityContext,
                            @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load particelle intersecanti geometria response.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @GET
    @Path("/particelle/interseca")
    Response loadParticelleIntersecantiGeometria(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                                                 @QueryParam("id_oggetto_istanza") Long idOggettoIstanza,
                                                 @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load particelle contenute geometria response.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @GET
    @Path("/particelle/contiene")
    Response loadParticelleContenuteGeometria(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @HeaderParam("X-Forwarded-For") String xForwardedFor,
                                              @QueryParam("id_oggetto_istanza") Long idOggettoIstanza,
                                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}