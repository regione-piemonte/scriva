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

import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

/**
 * The interface Integrazioni api.
 *
 * @author CSI PIEMONTE
 */
@Path("/integrazioni")
@Produces(MediaType.APPLICATION_JSON)
public interface IntegrazioniApi {

    /**
     * Load integrazioni response.
     *
     * @param idIstanza           the id istanza
     * @param codTipoIntegrazione the cod tipo integrazione
     * @param securityContext     SecurityContext
     * @param httpHeaders         HttpHeaders
     * @param httpRequest         HttpServletRequest
     * @param uriInfo             the uri info
     * @return Response response
     * @throws Exception the exception
     */
    @GET
    Response loadIntegrazioni(@QueryParam("id_istanza") Long idIstanza,
                              @QueryParam("cod_tipo_integrazione") String codTipoIntegrazione,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                              @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Save integrazioni response.
     *
     * @param requestBody     the request body
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @param uriInfo         the uri info
     * @return Response response
     * @throws Exception the exception
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveIntegrazioni(@RequestBody(required = true) Map<String, Object> requestBody,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                              @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Update integrazioni response.
     *
     * @param requestBody     the request body
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @param uriInfo         the uri info
     * @return Response response
     * @throws Exception the exception
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIntegrazioni(@RequestBody(required = true) Map<String, Object> requestBody,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                                @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

}