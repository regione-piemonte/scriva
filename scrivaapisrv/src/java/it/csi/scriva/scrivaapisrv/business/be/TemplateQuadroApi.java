/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

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

@Path("/template-quadri")
@Produces(MediaType.APPLICATION_JSON)
public interface TemplateQuadroApi {

    /**
     * @param codeAdempimento codeAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest httpRequest
     * @return Response
     */
    @GET
    @Path("/code-adempimento/{codeAdempimento}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplateQuadriByCodeAdempimento(@PathParam("codeAdempimento") String codeAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param codeTemplate codeTemplate
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest httpRequest
     * @return Response
     */
    @GET
    @Path("/code-template/{codeTemplate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplateQuadriByCodeTemplate(@PathParam("codeTemplate") String codeTemplate, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest httpRequest
     * @return Response
     */
    @GET
    @Path("/id-template-quadro/{idTemplateQuadro}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTemplateQuadriByIdTemplateQuadro(@PathParam("idTemplateQuadro") Long idTemplateQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}