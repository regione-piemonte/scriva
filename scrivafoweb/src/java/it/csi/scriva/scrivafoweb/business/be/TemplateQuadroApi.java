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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Template quadro api.
 *
 * @author CSI PIEMONTE
 */
@Path("/template-quadri")
@Produces(MediaType.APPLICATION_JSON)
public interface TemplateQuadroApi {

    /**
     * Gets template quadri by code adempimento.
     *
     * @param codeAdempimento codeAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     httpRequest
     * @return Response template quadri by code adempimento
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/code-adempimento/{codeAdempimento}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTemplateQuadriByCodeAdempimento(@PathParam("codeAdempimento") String codeAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets template quadri by code template.
     *
     * @param codeTemplate    codeTemplate
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     httpRequest
     * @return Response template quadri by code template
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/code-template/{codeTemplate}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTemplateQuadriByCodeTemplate(@PathParam("codeTemplate") String codeTemplate, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets template quadri by id template quadro.
     *
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      httpRequest
     * @return Response template quadri by id template quadro
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/id-template-quadro/{idTemplateQuadro}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTemplateQuadriByIdTemplateQuadro(@PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

}