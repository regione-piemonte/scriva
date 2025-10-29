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

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * The interface Notifiche api.
 *
 * @author CSI PIEMONTE
 */
@Path("/notifiche")
@Produces(MediaType.APPLICATION_JSON)
public interface NotificheApi {

    /**
     * Load notifiche response.
     *
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @GET
    Response loadNotifiche(@QueryParam(value = "offset") @DefaultValue("1") Integer offset,
                           @QueryParam(value = "limit") @DefaultValue("999") Integer limit,
                           @QueryParam(value = "sort") @DefaultValue("-data_inserimento") String sort,
                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Load notifiche by id response.
     *
     * @param idNotifica      the id notifica
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    @GET
    @Path("/{idNotifica}")
    Response loadNotificheById(@PathParam("idNotifica") Long idNotifica,
                               @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws JsonProcessingException, Exception;

    /**
     * Load notifiche response.
     *
     * @param requestBody     the request body
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @POST
    @Path("/_search")
    Response loadNotifiche(@RequestBody(required = true) Map<String, Object> requestBody,
                           @DefaultValue("1") @QueryParam(value = "offset") Integer offset,
                           @DefaultValue("20") @QueryParam(value = "limit") Integer limit,
                           @DefaultValue("-data_inserimento") @QueryParam(value = "sort") String sort,
                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Update notifiche response.
     *
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @PUT
    Response updateNotifiche(@RequestBody(required = true) List<Map<?, ?>> requestBody,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;


    /**
     * Load notifiche response.
     *
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @PUT
    @Path("/_search")
    Response updateNotifiche(@RequestBody(required = false) Map<String, Object> requestBody,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;


}