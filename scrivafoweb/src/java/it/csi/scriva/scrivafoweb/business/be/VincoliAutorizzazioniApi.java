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


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
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
 * The interface Vincoli autorizzazioni api.
 */
@Path("/vincoli-autorizzazioni")
@Produces(MediaType.APPLICATION_JSON)
public interface VincoliAutorizzazioniApi {

    /**
     * Gets vincoli autorizzazioni.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the vincoli autorizzazioni
     */
    @GET
    Response getVincoliAutorizzazioni(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets vincolo autorizzazione by adempimento.
     *
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the vincolo autorizzazione by adempimento
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response getVincoloAutorizzazioneByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets configurazioni allegati by istanza.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the configurazioni allegati by istanza
     */
    @GET
    @Path("/configurazioni-allegati/id-istanza/{idIstanza}")
    Response getConfigurazioniAllegatiByIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Load ricadenza vincolo idrogeologico by istanza response.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the response
     */
    @GET
    @Path("/idro/ricadenza")
    Response loadRicadenzaVincoloIdrogeologicoByIstanza(@QueryParam("id_oggetto_istanza") Long idOggettoIstanza,
                                                        @Context SecurityContext securityContext,
                                                        @Context HttpHeaders httpHeaders,
                                                        @Context HttpServletRequest httpRequest,
                                                        @Context UriInfo uriInfo) throws Exception;

}