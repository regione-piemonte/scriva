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

import it.csi.scriva.scrivabesrv.dto.SearchDTO;
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
 *
 * @author CSI PIEMONTE
 */
@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public interface SearchApi {

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
    @Path("/istanze")
    Response searchIstanze(@RequestBody SearchDTO searchCriteria,
                           @DefaultValue("1") @QueryParam(value = "offset") Integer offset,
                           @DefaultValue("20") @QueryParam(value = "limit") Integer limit,
                           @DefaultValue("") @QueryParam(value = "sort") String sort,
                           @DefaultValue("TRUE") @QueryParam(value = "ogg_app") Boolean oggApp,
                           @DefaultValue("") @QueryParam(value = "compare") String compare,
                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}