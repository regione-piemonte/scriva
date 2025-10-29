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
 * The interface Adempimenti estensioni allegati api.
 *
 * @author CSI PIEMONTE
 */
@Path("/estensioni-allegato")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiEstensioniAllegatiApi {

    /**
     * Load adempimenti estensioni allegati response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadAdempimentiEstensioniAllegati(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Load adempimento estensione allegato by id adempimento response.
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response loadAdempimentoEstensioneAllegatoByIdAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load adempimento estensione allegato by cod adempimento response.
     *
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/code-adempimento/{codAdempimento}")
    Response loadAdempimentoEstensioneAllegatoByCodAdempimento(@PathParam("codAdempimento") String codAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}