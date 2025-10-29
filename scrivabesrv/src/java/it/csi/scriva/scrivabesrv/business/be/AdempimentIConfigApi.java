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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Adempiment i config api.
 *
 * @author CSI PIEMONTE
 */
@Path("/adempimenti-config")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentIConfigApi {

    /**
     * Load adempimenti config response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadAdempimentiConfig(@QueryParam("cod_tipo_adempimento") String codTipoAdempimento, @QueryParam("info") String info, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load adempimento config by adempimento response.
     *
     * @param desAdempimento  desAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/adempimento/{desAdempimento}")
    Response loadAdempimentoConfigByAdempimento(@PathParam("desAdempimento") String desAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load adempimento config by adempimento informazione response.
     *
     * @param desAdempimento  desAdempimento
     * @param infoScriva      infoScriva
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/adempimento/{desAdempimento}/info/{infoScriva}")
    Response loadAdempimentoConfigByAdempimentoInformazione(@PathParam("desAdempimento") String desAdempimento, @PathParam("infoScriva") String infoScriva, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load adempimento config by adempimento informazione chiave response.
     *
     * @param desAdempimento  desAdempimento
     * @param infoScriva      infoScriva
     * @param chiave          chiave
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/adempimento/{desAdempimento}/info/{infoScriva}/chiave/{chiave}")
    Response loadAdempimentoConfigByAdempimentoInformazioneChiave(@PathParam("desAdempimento") String desAdempimento, @PathParam("infoScriva") String infoScriva, @PathParam("chiave") String chiave, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}