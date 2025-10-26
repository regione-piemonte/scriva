/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import org.springframework.web.bind.annotation.RequestBody;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.PPayResultDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.PagamentoIstanzaExtendedDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Stati istanza api.
 *
 * @author CSI PIEMONTE
 */
@Path("/pagamenti")
@Produces(MediaType.APPLICATION_JSON)
public interface PagamentiApi {

    /**
     * Load stati istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-istanza/{idIstanza}")
    Response getPagamentiIstanzaByIdIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save tentativo pagamento response.
     *
     * @param pagamentoIstanza the pagamento istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveTentativoPagamento(@RequestBody PagamentoIstanzaExtendedDTO pagamentoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save result tentativo pagamento response.
     *
     * @param pPayResult      the p pay result
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/ppay-result")
    Response saveResultTentativoPagamento(@RequestBody PPayResultDTO pPayResult, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save tentativo pagamento altri canali response.
     *
     * @param pagamentoIstanza the pagamento istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/altri-canali")
    Response saveTentativoPagamentoAltriCanali(@RequestBody PagamentoIstanzaExtendedDTO pagamentoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets rt by id istanza.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the rt by id istanza
     */
    @GET
    @Path("/rt/id-istanza/{idIstanza}")
    Response getRTByIdIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}