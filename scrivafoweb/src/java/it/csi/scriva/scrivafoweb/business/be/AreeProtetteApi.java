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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The interface Aree protette api.
 *
 * @author CSI PIEMONTE
 */
@Path("/aree-protette")
@Produces(MediaType.APPLICATION_JSON)
public interface AreeProtetteApi {

    /**
     * Gets aree protette.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param codIstatComuni   the cod istat comuni
     * @param checkComuni      the check comuni
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the aree protette
     * @throws Exception the exception
     */
    @GET
    Response getAreeProtette(@QueryParam("idOggettoIstanza") Long idOggettoIstanza,
                             @QueryParam("codIstatComuni") String codIstatComuni,
                             @QueryParam("checkComuni") @DefaultValue("true") Boolean checkComuni,
                             @Context SecurityContext securityContext,
                             @Context HttpHeaders httpHeaders,
                             @Context HttpServletRequest httpRequest,
                             @Context UriInfo uriInfo) throws Exception;

}