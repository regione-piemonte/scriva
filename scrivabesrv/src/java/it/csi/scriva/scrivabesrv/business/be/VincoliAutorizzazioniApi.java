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
 * The interface Vincoli autorizzazioni api.
 */
@Path("/vincoli-autorizzazioni")
@Produces(MediaType.APPLICATION_JSON)
public interface VincoliAutorizzazioniApi {

    /**
     * Load vincoli autorizzazioni response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    Response loadVincoliAutorizzazioni(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load vincolo autorizzazione by adempimento response.
     *
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response loadVincoloAutorizzazioneByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load configurazioni allegati by istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/configurazioni-allegati/id-istanza/{idIstanza}")
    Response loadConfigurazioniAllegatiByIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load ricadenza vincolo idrogeologico by istanza response.
     *
     * @param idOggettoIstanza the id istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @GET
    @Path("/idro/ricadenza")
    Response loadRicadenzaVincoloIdrogeologicoByIstanza(@QueryParam("id_oggetto_istanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}