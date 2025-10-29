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

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


/**
 * The interface Search api.
 */
@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public interface SearchApi {

    /**
     * Search istanze response.
     *
     * @param searchCriteria  the search criteria
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param oggApp          the ogg app
     * @param compare         the compare
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("/istanze")
    Response searchIstanze(@RequestBody SearchDTO searchCriteria,
                           @QueryParam(value = "offset") Integer offset,
                           @QueryParam(value = "limit") Integer limit,
                           @DefaultValue("") @QueryParam(value = "sort") String sort,
                           @DefaultValue("TRUE") @QueryParam(value = "ogg_app") Boolean oggApp,
                           @DefaultValue("") @QueryParam(value = "compare") String compare,
                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}