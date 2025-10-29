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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroDTO;
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
     * Gets istanza template quadro by id istanza.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the istanza template quadro by id istanza
     */
    @GET
    @Path("/id-istanza/{idIstanza}")
    Response getIstanzaTemplateQuadroByIdIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets istanza template quadro.
     *
     * @param idIstanza        the id istanza
     * @param idTemplateQuadro the id template quadro
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the istanza template quadro
     */
    @GET
    @Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
    Response getIstanzaTemplateQuadro(@PathParam("idIstanza") Long idIstanza, @PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets template quadri by code template and id istanza.
     *
     * @param codeTemplate    the code template
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the template quadri by code template and id istanza
     */
    @GET
    @Path("/code-template/{codeTemplate}/id-istanza/{idIstanza}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTemplateQuadriByCodeTemplateAndIdIstanza(@PathParam("codeTemplate") String codeTemplate, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Save istanza template quadro response.
     *
     * @param istanzaTemplateQuadro the istanza template quadro
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     */
    @POST
    @Consumes({ "application/json" })
    Response saveIstanzaTemplateQuadro(@RequestBody IstanzaTemplateQuadroDTO istanzaTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Update istanza template quadro response.
     *
     * @param istanzaTemplateQuadro the istanza template quadro
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     */
    @PUT
    @Consumes({ "application/json" })
    Response updateIstanzaTemplateQuadro(@RequestBody IstanzaTemplateQuadroDTO istanzaTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Delete istanza template quadro response.
     *
     * @param idIstanza        the id istanza
     * @param idTemplateQuadro the id template quadro
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @DELETE
    @Path("/id-istanza/{idIstanza}/id-template-quadro/{idTemplateQuadro}")
    Response deleteIstanzaTemplateQuadro(@PathParam("idIstanza") Long idIstanza, @PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

}