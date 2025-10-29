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

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

/**
 * The interface Note pubblicate api.
 *
 * @author CSI PIEMONTE
 */
@Path("/note-pubblicate")
@Produces(MediaType.APPLICATION_JSON)
public interface NotePubblicateApi {

    /**
     * Search note pubblicate response.
     *
     * @param idIstanza       the id istanza
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    Response searchNotePubblicate(@QueryParam("id_istanza") Long idIstanza, @DefaultValue("1") @QueryParam(value = "offset") Integer offset, @DefaultValue("20") @QueryParam(value = "limit") Integer limit, @DefaultValue("") @QueryParam(value = "sort") String sort, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save nota istanza response.
     *
     * @param notaPubblicata  the nota pubblicata
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveNotaIstanza(@RequestBody NotaPubblicataDTO notaPubblicata, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws DuplicateRecordException;

    /**
     * Update nota istanza response.
     *
     * @param notaPubblicata  the nota pubblicata
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateNotaIstanza(@RequestBody NotaPubblicataDTO notaPubblicata, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete nota istanza response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/{uid}")
    Response deleteNotaIstanza(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}