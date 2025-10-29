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

import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
 * The interface Osservzioni api.
 *
 * @author CSI PIEMONTE
 */
@Path("/osservazioni")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzaOsservazioni {

    /**
     * Load osservazioni response.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param idIstanza            the id istanza
     * @param codStatoOsservazione the cod stato osservazione
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
//    @GET
//    Response loadOsservazioni(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
//                              @QueryParam("id_istanza") Long idIstanza, 
//                              @QueryParam("cod_stato_osservazione") String codStatoOsservazione,
//                              @QueryParam("id_osservazione") Long idOsservazione,
//                              @DefaultValue("1") @QueryParam(value = "offset") String offset, 
//                              @DefaultValue("10") @QueryParam(value = "limit") String limit, 
//                              @DefaultValue("") @QueryParam(value = "sort") String sort,
//                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
    
    @GET
    Response loadOsservazioniSintesi(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                              @QueryParam("id_istanza") Long idIstanza, 
                              @QueryParam("cod_stato_osservazione") String codStatoOsservazione,
                              @QueryParam("id_osservazione") Long idOsservazione,
                              @DefaultValue("1") @QueryParam(value = "offset") String offset, 
                              @DefaultValue("10") @QueryParam(value = "limit") String limit, 
                              @DefaultValue("") @QueryParam(value = "sort") String sort,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save osservazione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param osservazione    the osservazione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveOsservazione(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                              @RequestBody OggettoOsservazioneExtendedDTO osservazione,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update osservazione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param osservazione    the osservazione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateOsservazione(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                @RequestBody OggettoOsservazioneExtendedDTO osservazione,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete osservazione response.
     *
     * @param xRequestAuth          the x request auth
     * @param xRequestId            the x request id
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteOsservazione(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                @PathParam("id") Long idIstanzaOsservazione,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}