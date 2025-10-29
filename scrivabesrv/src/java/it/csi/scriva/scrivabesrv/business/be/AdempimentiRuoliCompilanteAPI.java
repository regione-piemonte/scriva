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

import it.csi.scriva.scrivabesrv.dto.AdempimentoRuoloCompilanteExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
 * The interface Adempimenti ruoli compilante api.
 *
 * @author CSI PIEMONTE
 */
@Path("/adempimenti-ruoli-compilante")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiRuoliCompilanteAPI {

    /**
     * Gets adempimenti ruoli compilante.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response adempimenti ruoli compilante
     */
    @GET
    Response getAdempimentiRuoliCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets adempimenti ruolo compilante by ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response adempimenti ruolo compilante by ruolo compilante
     */
    @GET
    @Path("/id-ruolo-compilante/{idRuoloCompilante}")
    Response getAdempimentiRuoloCompilanteByRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets adempimenti ruolo compilante by adempimento.
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response adempimenti ruolo compilante by adempimento
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response getAdempimentiRuoloCompilanteByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets adempimento ruolo compilante by ruolo compilante adempimento.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param idAdempimento     idAdempimento
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response adempimento ruolo compilante by ruolo compilante adempimento
     */
    @GET
    @Path("/id-ruolo-compilante/{idRuoloCompilante}/id-adempimento/{idAdempimento}")
    Response getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save adempimento ruolo compilante response.
     *
     * @param adempimentoRuoloCompilante AdempimentoRuoloCompilanteExtendedDTO
     * @param securityContext            SecurityContext
     * @param httpHeaders                HttpHeaders
     * @param httpRequest                HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveAdempimentoRuoloCompilante(@RequestBody AdempimentoRuoloCompilanteExtendedDTO adempimentoRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update adempimento ruolo compilante response.
     *
     * @param adempimentoRuoloCompilante AdempimentoRuoloCompilanteExtendedDTO
     * @param securityContext            SecurityContext
     * @param httpHeaders                HttpHeaders
     * @param httpRequest                HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateAdempimentoRuoloCompilante(@RequestBody AdempimentoRuoloCompilanteExtendedDTO adempimentoRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}