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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface tipo responsabile api.
 *
 * @author CSI PIEMONTE
 */
@Path("/tipo-responsabile")
@Produces(MediaType.APPLICATION_JSON)
public interface TipoResponsabileApi {

    /**
     * Load tipo responsabile response.
     *
     * @param idIstanza      the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadTipoResponsabile(@QueryParam("id_istanza") Long idIstanza,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

  
}