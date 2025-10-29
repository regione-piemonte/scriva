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

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Files api.
 */
@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
public interface FilesApi {

    /**
     * Upload file response.
     *
     * @param input           the input
     * @param uidIstanza      the uid istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("/uid-istanza/{uidIstanza}")
    @Consumes({"multipart/form-data"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response uploadFile(MultipartFormDataInput input, @PathParam("uidIstanza") String uidIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Delete file response.
     *
     * @param uidIstanza      the uid istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/uid-istanza/{uidIstanza}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteFile(@PathParam("uidIstanza") String uidIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Download files response.
     *
     * @param type            the type
     * @param name            the name
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/type/{type}/name/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response downloadFiles(@PathParam("type") String type, @PathParam("name") String name, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}