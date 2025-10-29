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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface limiti amministrativi api.
 *
 * @author CSI PIEMONTE
 */
@Path("/limiti-amministrativi")
@Produces(MediaType.APPLICATION_JSON)
public interface LimitiAmministrativiApi {

    /**
     * Load nazioni response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codIstat        the cod istat
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/nazioni")
    Response loadNazioni(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                         @QueryParam("cod_istat") String codIstat,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load nazioni attive response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/nazioni/attive")
    Response loadNazioniAttive(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                               @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load regioni response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codIstat        the cod istat
     * @param codIstatNazione the cod istat nazione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/regioni")
    Response loadRegioni(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                         @QueryParam("cod_istat") String codIstat, @QueryParam("cod_istat_nazione") String codIstatNazione,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load regioni attive response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/regioni/attive")
    Response loadRegioniAttive(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                               @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Load province response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codIstat        the cod istat
     * @param codIstatRegione the cod istat regione
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/province")
    Response loadProvince(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                          @QueryParam("cod_istat") String codIstat, @QueryParam("cod_istat_regione") String codIstatRegione, @QueryParam("id_adempimento") Long idAdempimento,
                          @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load province attive response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/province/attive")
    Response loadProvinceAttive(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load comuni response.
     *
     * @param xRequestAuth      the x request auth
     * @param xRequestId        the x request id
     * @param codIstat          the cod istat
     * @param codIstatProvincia the cod istat provincia
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @GET
    @Path("/comuni")
    Response loadComuni(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                        @QueryParam("cod_istat") String codIstat, @QueryParam("cod_istat_provincia") String codIstatProvincia, @QueryParam("sigla_provincia") String siglaProvincia,
                        @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load comuni attivi response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/comuni/attivi")
    Response loadComuniAttivi(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}