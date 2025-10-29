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

import it.csi.scriva.scrivabesrv.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;
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
 * The interface Oggetti api.
 *
 * @author CSI PIEMONTE
 */
@Path("/oggetti")
@Produces(MediaType.APPLICATION_JSON)
public interface OggettiApi {

    /**
     * Load oggetti response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idComune        the id comune
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadOggetti(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                         @QueryParam("id_comune") Long idComune, @QueryParam("id_istanza") Long idIstanza,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load oggetto by uid response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param uidOggetto      uidOggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/{uidOggetto}")
    Response loadOggettoByUID(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                              @PathParam("uidOggetto") String uidOggetto,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Search oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param searchOggetto   SearchOggettoDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    Response searchOggetto(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                           @RequestBody SearchOggettoDTO searchOggetto,
                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param oggetto         OggettoUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveOggetto(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                         @QueryParam("cod_adempimento") String codAdempimento,
                         @RequestBody OggettoUbicazioneExtendedDTO oggetto,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param oggetto         OggettoUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateOggetto(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                           @RequestBody OggettoUbicazioneExtendedDTO oggetto,
                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param uidOggetto      uidOggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{uidOggetto}")
    Response deleteOggetto(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                           @PathParam("uidOggetto") String uidOggetto,
                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}