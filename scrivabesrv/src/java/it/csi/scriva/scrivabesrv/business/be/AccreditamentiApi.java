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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.AccreditamentoDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
 * The interface Accreditamento api.
 *
 * @author CSI PIEMONTE
 */
@Path("/accreditamenti")
@Produces(MediaType.APPLICATION_JSON)
public interface AccreditamentiApi {

    /**
     * Save accreditamento response.
     *
     * @param accreditamento  AccreditamentoDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response AccreditamentoDTO
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveAccreditamento(@RequestBody AccreditamentoDTO accreditamento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Validate accreditamento response.
     *
     * @param uid             uid
     * @param otp             otp
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/{uid}/valida")
    @Consumes(MediaType.APPLICATION_JSON)
    Response validateAccreditamento(@PathParam("uid") String uid, @QueryParam("otp") String otp, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

}