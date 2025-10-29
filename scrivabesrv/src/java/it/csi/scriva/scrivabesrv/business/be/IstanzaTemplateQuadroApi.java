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

import it.csi.scriva.scrivabesrv.dto.IstanzaTemplateQuadroDTO;
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

/**
 * The interface Istanza template quadro api.
 *
 * @author CSI PIEMONTE
 */
@Path("/istanze-template-quadri")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzaTemplateQuadroApi {

    /**
     * Load istanza template quadro by id istanza response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-istanza/{idIstanza}")
    Response loadIstanzaTemplateQuadroByIdIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load istanza template quadro response.
     *
     * @param idIstanza        idIstanza
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
    Response loadIstanzaTemplateQuadro(@PathParam("idIstanza") Long idIstanza, @PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save istanza template quadro response.
     *
     * @param istanzaTemplateQuadro IstanzaTemplateQuadroDTO
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveIstanzaTemplateQuadro(@RequestBody IstanzaTemplateQuadroDTO istanzaTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update istanza template quadro response.
     *
     * @param istanzaTemplateQuadro IstanzaTemplateQuadroDTO
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIstanzaTemplateQuadro(@RequestBody IstanzaTemplateQuadroDTO istanzaTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete istanza template quadro response.
     *
     * @param idIstanza        idIstanza
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
    Response deleteIstanzaTemplateQuadro(@PathParam("idIstanza") Long idIstanza, @PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}