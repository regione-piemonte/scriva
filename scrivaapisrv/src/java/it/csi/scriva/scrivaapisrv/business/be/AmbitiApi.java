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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Ambiti api.
 *
 * @author CSI PIEMONTE
 */
@Path("/ambiti")
@Produces(MediaType.APPLICATION_JSON)
public interface AmbitiApi {

    /**
     * Gets ambiti.
     *
     * @param idAmbito        the id ambito
     * @param codAmbito       the cod ambito
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the ambiti
     */
    @GET
    public Response getAmbiti(@QueryParam("id_ambito") Long idAmbito, @QueryParam("codice") String codAmbito,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets ambito.
     *
     * @param idAmbito        the id ambito
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the ambito
     */
    @GET
    @Path("/{idAmbito}")
    public Response getAmbito(@PathParam("idAmbito") Long idAmbito, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}