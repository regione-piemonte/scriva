/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;


import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.Feature;

import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.UriInfo;

/**
 * The interface Save feature api.
 *
 * @author CSI PIEMONTE
 */
@Path("/geeco/{environment}/{id_editing_session}/layers/{layer_id}")
@Produces(MediaType.APPLICATION_JSON)
public interface GeecoFeatureApi {
    /**
     * Insert feature for editing layer response.
     *
     * @param environment      the environment
     * @param layerId          the layer id
     * @param idEditingSession the id editing session
     * @param feature          the feature
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response insertFeatureForEditingLayer(@PathParam("environment") final String environment,
                                          @PathParam("layer_id") final String layerId,
                                          @PathParam("id_editing_session") final String idEditingSession,
                                          final Feature feature,
                                          @Context final SecurityContext securityContext,
                                          @Context final HttpHeaders httpHeaders,
                                          @Context HttpServletRequest httpRequest,
                                          @Context UriInfo uriInfo);

    /**
     * Update feature for editing layer response.
     *
     * @param environment      the environment
     * @param layerId          the layer id
     * @param featureId        the feature id
     * @param idEditingSession the id editing session
     * @param feature          the feature
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return Response response
     */
    @PUT
    @Path("/{feature_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateFeatureForEditingLayer(@PathParam("environment") final String environment,
                                          @PathParam("layer_id") final String layerId,
                                          @PathParam("feature_id") final String featureId,
                                          @PathParam("id_editing_session") final String idEditingSession,
                                          final Feature feature,
                                          @Context final SecurityContext securityContext,
                                          @Context final HttpHeaders httpHeaders,
                                          @Context HttpServletRequest httpRequest,
                                          @Context UriInfo uriInfo);


    /**
     * Delete feature for editing layer response.
     *
     * @param environment      the environment
     * @param layerId          the layer id
     * @param featureId        the feature id
     * @param idEditingSession the id editing session
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return Response response
     */
    @DELETE
    @Path("/{feature_id}")
    Response deleteFeatureForEditingLayer(@PathParam("environment") final String environment,
                                          @PathParam("layer_id") final String layerId,
                                          @PathParam("feature_id") final String featureId,
                                          @PathParam("id_editing_session") final String idEditingSession,
                                          @Context final SecurityContext securityContext,
                                          @Context final HttpHeaders httpHeaders,
                                          @Context HttpServletRequest httpRequest,
                                          @Context UriInfo uriInfo);
}