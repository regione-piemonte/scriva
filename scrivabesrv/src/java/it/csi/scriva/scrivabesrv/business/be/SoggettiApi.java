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

import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

/**
 * The interface Soggetti api.
 *
 * @author CSI PIEMONTE
 */
@Path("/soggetti")
@Produces(MediaType.APPLICATION_JSON)
public interface SoggettiApi {

    /**
     * Load soggetti response.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param idSoggetto           the id soggetto
     * @param codiceFiscale        the codice fiscale
     * @param tipoSoggetto         the tipo soggetto
     * @param codAdempimento       the cod adempimento
     * @param codiceFiscaleImpresa the codice fiscale impresa
     * @param securityContext      SecurityContext
     * @param httpHeaders          HttpHeaders
     * @param httpRequest          HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadSoggetti(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                          @QueryParam("id_soggetto") Long idSoggetto, @QueryParam("codice_fiscale") String codiceFiscale, @QueryParam("tipo_soggetto") String tipoSoggetto, @QueryParam("cod_adempimento") String codAdempimento, @QueryParam("codice_fiscale_impresa") String codiceFiscaleImpresa,
                          @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetti by tipo soggetto response.
     *
     * @param idTipoSoggetto  idTipoSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id-tipo-soggetto/{idTipoSoggetto}")
    Response loadSoggettiByTipoSoggetto(@PathParam("idTipoSoggetto") Long idTipoSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetto by id response.
     *
     * @param idSoggetto      idSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id/{idSoggetto}")
    Response loadSoggettoById(@PathParam("idSoggetto") Long idSoggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetto by codice fiscale and tipo response.
     *
     * @param codiceFiscale   codiceFiscaleSoggetto
     * @param tipoSoggetto    tipoSoggetto
     * @param tipoAdempimento tipoAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/cf/{codiceFiscale}/tipo-soggetto/{tipoSoggetto}/tipo-adempimento/{tipoAdempimento}")
    Response loadSoggettoByCodiceFiscaleAndTipo(@PathParam("codiceFiscale") String codiceFiscale, @PathParam("tipoSoggetto") String tipoSoggetto, @PathParam("tipoAdempimento") String tipoAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load soggetto impresa response.
     *
     * @param codiceFiscaleImpresa  codiceFiscaleImpresa
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @param tipoSoggetto          tipoSoggetto
     * @param tipoAdempimento       tipoAdempimento
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/cf-impresa/{codiceFiscaleImpresa}/cf-soggetto/{codiceFiscaleSoggetto}/tipo-soggetto/{tipoSoggetto}/tipo-adempimento/{tipoAdempimento}")
    Response loadSoggettoImpresa(@PathParam("codiceFiscaleImpresa") String codiceFiscaleImpresa, @PathParam("codiceFiscaleSoggetto") String codiceFiscaleSoggetto, @PathParam("tipoSoggetto") String tipoSoggetto, @PathParam("tipoAdempimento") String tipoAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Save soggetto response.
     *
     * @param soggetto        SoggettoExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveSoggetto(@RequestBody SoggettoExtendedDTO soggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update soggetto response.
     *
     * @param soggetto        List<SoggettoExtendedDTO>
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateSoggetto(@RequestBody SoggettoExtendedDTO soggetto, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete soggetto response.
     *
     * @param uid             uidSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{uid}")
    Response deleteSoggetto(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete soggetto by id response.
     *
     * @param id              idSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/id/{id}")
    Response deleteSoggettoById(@PathParam("id") Long id, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}