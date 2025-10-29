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

import it.csi.scriva.scrivabesrv.dto.PubSearchDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
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
 * The interface Istanze pubblicate api.
 *
 * @author CSI PIEMONTE
 */
@Path("/istanze-pubblicate")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzePubblicateApi {

    /**
     * Search Dettaglio istanza response.
     *
     * @param searchCriteria  SearchDTO
     * @param offset          offset
     * @param limit           limit
     * @param sort            sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    Response searchIstanzePubblicate(
            @RequestBody PubSearchDTO searchCriteria,
            @DefaultValue("1") @QueryParam(value = "offset") Integer offset,
            @DefaultValue("10") @QueryParam(value = "limit") Integer limit,
            @DefaultValue("") @QueryParam(value = "sort") String sort,
            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Search istanze response.
     *
     * @param searchCriteria  SearchDTO
     * @param offset          offset
     * @param limit           limit
     * @param sort            sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Path("/_search")
    Response searchSintesiIstanzePubblicate(@RequestBody PubSearchDTO searchCriteria,
                                            @DefaultValue("1") @QueryParam(value = "offset") Integer offset,
                                            @DefaultValue("10") @QueryParam(value = "limit") Integer limit,
                                            @DefaultValue("") @QueryParam(value = "sort") String sort,
                                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load mappe response.
     *
     * @param id              the id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/mappe/{id}")
    Response loadMappe(@PathParam("id") Long id,
                       @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}