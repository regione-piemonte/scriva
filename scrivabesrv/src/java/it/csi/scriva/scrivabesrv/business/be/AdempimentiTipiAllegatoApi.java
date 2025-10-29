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
 * The interface Adempimenti tipi allegato api.
 *
 * @author CSI PIEMONTE
 */
@Path("/tipologie-allegato")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiTipiAllegatoApi {

    /**
     * Load tipologie allegato response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadTipologieAllegato(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Load tipologia allegato by id adempimento response.
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response loadTipologiaAllegatoByIdAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load tipologia allegato by code adempimento response.
     *
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/code-adempimento/{codAdempimento}")
    Response loadTipologiaAllegatoByCodeAdempimento(@PathParam("codAdempimento") String codAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

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
     * Load tipologie allegato by code adempimento and code categoria response.
     *
     * @param codAdempimento  codAdempimento
     * @param codCategoria    codCategoria
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/code-adempimento/{codAdempimento}/code-categoria/{codCategoria}")
    Response loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(@PathParam("codAdempimento") String codAdempimento, @PathParam("codCategoria") String codCategoria, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}