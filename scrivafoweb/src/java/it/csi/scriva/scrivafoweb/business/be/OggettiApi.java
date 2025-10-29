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

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchOggettoDTO;
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
     * Gets oggetti.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idComune        the id comune
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the oggetti
     * @throws GenericException the generic exception
     */
    @GET
    Response getOggetti(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                        @QueryParam("id_comune") Long idComune, @QueryParam("id_istanza") Long idIstanza,
                        @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets oggetto by uid.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the oggetto by uid
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/{uid}")
    Response getOggettoByUID(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Search oggetto response.
     *
     * @param searchOggetto   the search oggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @POST
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    Response searchOggetto(@RequestBody SearchOggettoDTO searchOggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Save oggetto response.
     *
     * @param codAdempimento  the cod adempimento
     * @param oggetto         the oggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveOggetto(@QueryParam("cod_adempimento") String codAdempimento, @RequestBody OggettoUbicazioneExtendedDTO oggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Update oggetto response.
     *
     * @param oggetto         the oggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateOggetto(@RequestBody OggettoUbicazioneExtendedDTO oggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Delete oggetto response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @DELETE
    @Path("/{uid}")
    Response deleteOggetto(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

}