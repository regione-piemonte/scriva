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

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaCompetenzaExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import javax.ws.rs.core.UriInfo;

/**
 * The interface Competenze territorio api.
 *
 * @author CSI PIEMONTE
 */
@Path("/competenze-territorio")
@Produces(MediaType.APPLICATION_JSON)
public interface CompetenzeTerritorioApi {

    /**
     * Gets competenze territorio.
     *
     * @param idAdempimento   the id adempimento
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response competenze territorio
     */
    @GET
    Response getCompetenzeTerritorio(@QueryParam("id_adempimento") Long idAdempimento, @QueryParam("id_istanza") Long idIstanza,
                                     @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Verifica competenze territorio by id istanza adempimento response.
     *
     * @param idIstanza       the id istanza
     * @param ac3             the ac 3
     * @param tipoSelezione   the tipo selezione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
/*  @GET
    @Path("/_check-ac")
    Response verificaCompetenzeTerritorioByIdIstanzaAdempimento(@QueryParam("id_istanza") Long idIstanza,
                                                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
                                                                @Context UriInfo uriInfo) throws Exception;

   /*
    /**
     * Verifica competenze territorio by id istanza adempimento response.
     *
     * @param idIstanza       the id istanza
     * @param ac3             the ac 3
     * @param tipoSelezione   the tipo selezione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @GET
    @Path("/_check-ac")
	Response gestioneAC(    @QueryParam("id_istanza") Long idIstanza,
	                        @QueryParam("ac3") Boolean ac3,
	                        @QueryParam("tipo_selezione") String tipoSelezione,
	                        @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
	                        @Context UriInfo uriInfo) throws Exception;

    /**
     * Calculate istanza competenza territorio secondarie response.
     *
     * @param idIstanza         the id istanza
     * @param livelliCompetenza the livelli competenza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @POST
    @Path("/ac-secondarie")
    Response calculateIstanzaCompetenzaTerritorioSecondarie(@QueryParam("id_istanza") Long idIstanza, @DefaultValue("REGIONALE,PROVINCIALE,COMUNALE") @QueryParam(value = "lvl") String livelliCompetenza,
                                                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete istanza competenza territorio secondarie response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Path("/ac-secondarie")
    Response deleteIstanzaCompetenzaTerritorioSecondarie(@QueryParam("id_istanza") Long idIstanza,
                                                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Save istanza competenza territorio response.
     *
     * @param istanzaCompetenza istanzaCompetenza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveIstanzaCompetenzaTerritorio(@RequestBody IstanzaCompetenzaExtendedDTO istanzaCompetenza,
                                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update istanza competenza territorio response.
     *
     * @param istanzaCompetenza istanzaCompetenza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIstanzaCompetenzaTerritorio(@RequestBody IstanzaCompetenzaExtendedDTO istanzaCompetenza,
                                               @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Get competenze territorio by id competenze responsabile.
     *
     * @param xRequestAuth             the x request auth
     * @param xRequestId               the x request id
     * @param idCompetenzaResponsabile the id competenza responsabile
     * @param securityContext          SecurityContext
     * @param httpHeaders              HttpHeaders
     * @param httpRequest              HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/{id}/responsabili")
    Response getCompetenzeTerritorioByIdCompResp(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                          @PathParam("id") Long idCompetenzaResponsabile,
                                          @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}