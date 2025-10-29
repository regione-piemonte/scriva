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
     * Load template quadri by code adempimento response.
     *
     * @param codeAdempimento codeAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/code-adempimento/{codeAdempimento}")
    Response loadTemplateQuadriByCodeAdempimento(@PathParam("codeAdempimento") String codeAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Load template quadri by code template response.
     *
     * @param codeTemplate    codeTemplate
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/code-template/{codeTemplate}")
    Response loadTemplateQuadriByCodeTemplate(@PathParam("codeTemplate") String codeTemplate, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Load template quadri by id template quadro response.
     *
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-template-quadro/{idTemplateQuadro}")
    Response loadTemplateQuadriByIdTemplateQuadro(@PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

}