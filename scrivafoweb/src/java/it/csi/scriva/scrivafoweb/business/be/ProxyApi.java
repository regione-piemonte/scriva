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

import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
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
import java.util.Map;


/**
 * The interface Proxy api.
 *
 * @author CSI PIEMONTE
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface ProxyApi {

    /**
     * Load suggest response.
     *
     * @param servicePath     the service path
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     */
    @GET
    @Path("{servicePath:.*}")
    Response getResponseGET(@PathParam("servicePath") String servicePath,
                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Gets response post.
     *
     * @param servicePath     the service path
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response post
     */
    @POST
    @Path("{servicePath:.*}")
    @Consumes({"application/json"})
    Response getResponsePOST(@PathParam("servicePath") String servicePath,
                             @RequestBody(required = false) Map<String, Object> requestBody,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo);

    /**
     * Gets response put.
     *
     * @param servicePath     the service path
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response put
     */
    @PUT
    @Path("{servicePath:.*}")
    @Consumes({"application/json"})
    Response getResponsePUT(@PathParam("servicePath") String servicePath,
                            @RequestBody(required = false) Map<String, Object> requestBody,
                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo);

    /**
     * Gets response patch.
     *
     * @param servicePath     the service path
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response patch
     */
    @PATCH
    @Path("{servicePath:.*}")
    @Consumes({"application/json"})
    Response getResponsePATCH(@PathParam("servicePath") String servicePath,
                              @RequestBody(required = false) Map<String, Object> requestBody,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo);

    /**
     * Gets response del.
     *
     * @param servicePath     the service path
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response del
     */
    @DELETE
    @Path("{servicePath:.*}")
    Response getResponseDEL(@PathParam("servicePath") String servicePath,
                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo);

}