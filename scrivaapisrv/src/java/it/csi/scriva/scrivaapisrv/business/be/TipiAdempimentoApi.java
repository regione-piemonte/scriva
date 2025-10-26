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
 * The interface Tipi adempimento api.
 *
 * @author CSI PIEMONTE
 */
@Path("/tipi-adempimento")
@Produces(MediaType.APPLICATION_JSON)
public interface TipiAdempimentoApi {

    /**
     * Gets tipi adempimento.
     *
     * @param idAmbito           the id ambito
     * @param idCompilante       the id compilante
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response tipi adempimento
     */
    @GET
    Response getTipiAdempimento(@QueryParam("id_ambito") Long idAmbito, @QueryParam("id_compilante") Long idCompilante, @QueryParam("codice") String codTipoAdempimento, @QueryParam("codice_ambito") String codAmbito,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets tipo adempimento.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response tipo adempimento
     */
    @GET
    @Path("/{idTipoAdempimento}")
    Response getTipoAdempimento(@PathParam("idTipoAdempimento") Long idTipoAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}