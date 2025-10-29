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

import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaUbicazioneExtendedDTO;
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
 * The interface Oggetti istanza api.
 *
 * @author CSI PIEMONTE
 */
@Path("/oggetti-istanza")
@Produces(MediaType.APPLICATION_JSON)
public interface OggettiIstanzaApi {

    /**
     * Load oggetti istanza response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadOggettiIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                @QueryParam("id_istanza") Long idIstanza,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load oggetto istanza by id response.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id/{idOggettoIstanza}")
    Response loadOggettoIstanzaById(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load oggetto istanza by uid response.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/{uidOggettoIstanza}")
    Response loadOggettoIstanzaByUID(@PathParam("uidOggettoIstanza") String uidOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save oggetto istanza response.
     *
     * @param oggettoIstanza  OggettoIstanzaUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveOggettoIstanza(@RequestBody OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update oggetto istanza response.
     *
     * @param oggettoIstanza  OggettoIstanzaUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateOggettoIstanza(@RequestBody OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete oggetto istanza response.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{uidOggettoIstanza}")
    Response deleteOggettoIstanza(@PathParam("uidOggettoIstanza") String uidOggettoIstanza, @QueryParam("check_figli") String checkFigli, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Verifica georeferenziazione response.
     *
     * @param codAdempimento  codAdempimento
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/adempimento/{codAdempimento}/id-istanza/{idIstanza}")
    Response verificaGeoreferenziazione(@PathParam("codAdempimento") String codAdempimento, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}