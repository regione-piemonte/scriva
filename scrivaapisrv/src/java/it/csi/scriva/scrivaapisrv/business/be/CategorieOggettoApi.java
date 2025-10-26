/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaCategoriaExtendedDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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
 * The interface Categorie oggetto api.
 *
 * @author CSI PIEMONTE
 */
@Path("/categorie-progettuali")
@Produces(MediaType.APPLICATION_JSON)
public interface CategorieOggettoApi {

    /**
     * Gets categorie progettuali.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response categorie progettuali
     */
    @GET
    Response getCategorieProgettuali(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets categoria progettuale by id adempimento.
     *
     * @param idAdempimento   idAdempimento
     * @param search          the search
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response categoria progettuale by id adempimento
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response getCategoriaProgettualeByIdAdempimento(@PathParam("idAdempimento") Long idAdempimento, @DefaultValue("") @QueryParam(value = "search") String search, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets categoria progettuale by id oggetto istanza.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response categoria progettuale by id oggetto istanza
     */
    @GET
    @Path("/id-oggetto-istanza/{idOggettoIstanza}")
    Response getCategoriaProgettualeByIdOggettoIstanza(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Save oggetto istanza categoria response.
     *
     * @param oggettoIstanzaCategoria oggettoIstanzaCategoria
     * @param securityContext         SecurityContext
     * @param httpHeaders             HttpHeaders
     * @param httpRequest             HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveOggettoIstanzaCategoria(@RequestBody OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update oggetto istanza categoria response.
     *
     * @param oggettoIstanzaCategoria oggettoIstanzaCategoria
     * @param securityContext         SecurityContext
     * @param httpHeaders             HttpHeaders
     * @param httpRequest             HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateOggettoIstanzaCategoria(@RequestBody OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Add categorie modifica response.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @POST
    @Path("/id-oggetto-istanza/{idOggettoIstanza}")
    Response addCategorieModifica(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
    Response deleteOggettoIstanzaCategoria(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}