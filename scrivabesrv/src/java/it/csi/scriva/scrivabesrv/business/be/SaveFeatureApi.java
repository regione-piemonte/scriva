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

import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * The interface Save feature api.
 *
 * @author CSI PIEMONTE
 */
@Path("/geeco/{environment}/{id_editing_session}/layers/{layer_id}")
@Produces(MediaType.APPLICATION_JSON)
public interface SaveFeatureApi {
    /**
     * Insert feature for editing layer response.
     *
     * @param p1 environment
     * @param p2 layer_id
     * @param p3 id_editing_session
     * @param p4 Feature
     * @param p5 SecurityContext
     * @param p6 HttpHeaders
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response insertFeatureForEditingLayer(@PathParam("environment") final String p1, @PathParam("layer_id") final String p2, @PathParam("id_editing_session") final String p3, final Feature p4, @Context final SecurityContext p5, @Context final HttpHeaders p6);

    /**
     * Update feature for editing layer response.
     *
     * @param p1 environment
     * @param p2 layer_id
     * @param p3 feature_id
     * @param p4 id_editing_session
     * @param p5 Feature
     * @param p6 SecurityContext
     * @param p7 HttpHeaders
     * @return Response response
     */
    @PUT
    @Path("/{feature_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateFeatureForEditingLayer(@PathParam("environment") final String p1, @PathParam("layer_id") final String p2, @PathParam("feature_id") final String p3, @PathParam("id_editing_session") final String p4, final Feature p5, @Context final SecurityContext p6, @Context final HttpHeaders p7);

    /**
     * Delete feature for editing layer response.
     *
     * @param p1 environment
     * @param p2 layer_id
     * @param p3 feature_id
     * @param p4 id_editing_session
     * @param p5 SecurityContext
     * @param p6 HttpHeaders
     * @return Response response
     */
    @DELETE
    @Path("/{feature_id}")
    Response deleteFeatureForEditingLayer(@PathParam("environment") final String p1, @PathParam("layer_id") final String p2, @PathParam("feature_id") final String p3, @PathParam("id_editing_session") final String p4, @Context final SecurityContext p5, @Context final HttpHeaders p6);
}