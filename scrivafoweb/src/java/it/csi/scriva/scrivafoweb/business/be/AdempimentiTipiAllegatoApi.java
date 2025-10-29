/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package it.csi.scriva.scrivafoweb.business.be;


import java.util.List;

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

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoTipoAllegatoExtendedDTO;

/**
 * @author CSI PIEMONTE
 */
@Path("/tipologie-allegato")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiTipiAllegatoApi {

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    Response getTipologieAllegato(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * @param idAdempimento idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response getTipologiaAllegatoByIdAdempimento(@PathParam ("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * @param codAdempimento codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    @Path("/code-adempimento/{codAdempimento}")
    Response getTipologiaAllegatoByCodeAdempimento(@PathParam ("codAdempimento") String codAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    
 /**
     * Load tipologia allegato by code adempimento response.
     *
     * @param codAdempimento  codAdempimento
     * @param codTipologiaAllegato codTipologiaAllegato
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/code-adempimento/{codAdempimento}/code-tipologia-allegato/{codTipologiaAllegato}")
    Response loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(@PathParam("codAdempimento") String codAdempimento, @PathParam("codTipologiaAllegato") String codTipologiaAllegato, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    
    
    /**
     * @param codAdempimento codAdempimento
     * @param codCategoria codCategoria
     * @param securityContext SecurityContext
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response
     */
    @GET
    @Path("/code-adempimento/{codAdempimento}/code-categoria/{codCategoria}")
    Response getTipologieAllegatoByCodeAdempimentoAndCodeCategoria(@PathParam ("codAdempimento") String codAdempimento, @PathParam ("codCategoria") String codCategoria, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}