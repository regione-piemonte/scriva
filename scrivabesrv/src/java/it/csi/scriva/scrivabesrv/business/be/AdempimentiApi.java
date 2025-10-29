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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
 * The interface Adempimenti api.
 *
 * @author CSI PIEMONTE
 */
@Path("/adempimenti")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiApi {


    /**
     * Load adempimenti response.
     *
     * @param xRequestAuth       the x request auth
     * @param xRequestId         the x request id
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipoadempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the response
     */
    @GET
    Response loadAdempimenti(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                             @QueryParam("id_ambito") Long idAmbito, @QueryParam("cod_ambito") String codAmbito,
                             @QueryParam("id_tipo_adempimento") Long idTipoAdempimento, @QueryParam("cod_tipo_adempimento") String codTipoAdempimento,
                             @QueryParam("codice") String codAdempimento, @QueryParam("id_compilante") Long idCompilante,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * adempimento per id
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/{idAdempimento}/")
    Response loadAdempimentoById(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                 @PathParam("idAdempimento") Long idAdempimento,
                                 @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load adempi esito procedimento response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idAdempimento   the id adempimento
     * @param flgAttivi       the flg attivi
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/{idAdempimento}/esiti-procedimento")
    Response loadAdempiStatoIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                    @PathParam("idAdempimento") Long idAdempimento, @DefaultValue("FALSE") @QueryParam("attivi") Boolean flgAttivi,
                                    @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load adempi esito procedimento response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/{idAdempimento}/stati-istanza")
    Response loadAdempiStatoIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                    @PathParam("idAdempimento") Long idAdempimento,
                                    @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}