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


@Path("/ruoli-compilante")
@Produces(MediaType.APPLICATION_JSON)
public interface RuoliCompilanteApi {

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    public Response getRuoliCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    @Path("/id/{idRuoloCompilante}")
    public Response getRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param codRuoloCompilante codRuoloCompilante
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    @Path("/codice/{codRuoloCompilante}")
    public Response getRuoloCompilanteByCode(@PathParam("codRuoloCompilante") String codRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * @param idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    @Path("/adempimento/{idAdempimento}")
    public Response getRuoliCompilanteByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}