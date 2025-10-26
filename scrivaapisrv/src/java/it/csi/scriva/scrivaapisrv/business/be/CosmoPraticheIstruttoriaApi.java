/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;


import it.csi.scriva.scrivaapisrv.dto.PraticaIstruttoriaOldDTO;
import it.csi.scriva.scrivaapisrv.dto.PraticaIstruttoriaDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
 * The interface Cosmo pratiche istruttoria api.
 */
@Path("/pratiche-istruttoria")
@Produces(MediaType.APPLICATION_JSON)
public interface CosmoPraticheIstruttoriaApi {

    /**
     * Gestione pratiche istruttoria response.
     *
     * @param codIstanza          the cod istanza
     * @param codTipoEvento       the cod tipo evento
     * @param flagCodicePratica   the flag codice pratica
     * @param flagPubblicaPratica the flag pubblica pratica
     * @param praticaIstruttoria  the cosmo pratiche istruttoria
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @POST
    @Path("/OLD")
    @Consumes(MediaType.APPLICATION_JSON)
    Response gestionePraticheIstruttoriaOLD(@QueryParam("cod_istanza") String codIstanza, @QueryParam("cod_tipo_evento") String codTipoEvento,
                                            @QueryParam("flag_codice_Pratica") @DefaultValue("false") Boolean flagCodicePratica,
                                            @QueryParam("flag_pubblica_pratica") @DefaultValue("false") Boolean flagPubblicaPratica,
                                            @RequestBody(required = false) @DefaultValue("{}") PraticaIstruttoriaOldDTO praticaIstruttoria,
                                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gestione pratiche istruttoria new response.
     *
     * @param codIstanza          the cod istanza
     * @param codTipoEvento       the cod tipo evento
     * @param flagCodicePratica   the flag codice pratica
     * @param flagPubblicaPratica the flag pubblica pratica
     * @param praticaIstruttoria  the pratica istruttoria
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    Response gestionePraticheIstruttoriaNEW(@QueryParam("cod_istanza") String codIstanza, @QueryParam("cod_tipo_evento") String codTipoEvento,
                                         @QueryParam("flag_codice_Pratica") @DefaultValue("false") Boolean flagCodicePratica,
                                         @QueryParam("flag_pubblica_pratica") @DefaultValue("false") Boolean flagPubblicaPratica,
                                         @RequestBody(required = false) @DefaultValue("{}") PraticaIstruttoriaDTO praticaIstruttoria,
                                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}