/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SearchDTO;

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


@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public interface SearchApi {

    @POST
    @Path("/istanze")
    public Response searchIstanze(@RequestBody SearchDTO searchCriteria, @QueryParam(value = "offset") Integer offset, @QueryParam(value = "limit") Integer limit, @DefaultValue("") @QueryParam(value = "sort" ) String sort, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

}