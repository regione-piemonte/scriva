/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SoggettoExtendedDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * The interface Soggetti api.
 */
@Path("/soggetti")
@Produces(MediaType.APPLICATION_JSON)
public interface SoggettiApi {

    /**
     * Gets soggetti.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @param idSoggetto           the id soggetto
     * @param codiceFiscale        the codice fiscale
     * @param tipoSoggetto         the tipo soggetto
     * @param codAdempimento       the cod adempimento
     * @param codiceFiscaleImpresa the codice fiscale impresa
     * @return the soggetti
     */
    @ApiOperation(value = "carica soggetti", nickname = "getSoggetti", notes = "carica soggetti", response = List.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "soggetti", response = List.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid"),
            @ApiResponse(code = 401, message = "unauthorized")})
    @GET
    @Path("")
    Response getSoggetti(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
                         @QueryParam("id_soggetto") Long idSoggetto, @QueryParam("codice_fiscale") String codiceFiscale, @QueryParam("tipo_soggetto") String tipoSoggetto, @QueryParam("cod_adempimento") String codAdempimento,
                         @QueryParam("codice_fiscale_impresa") String codiceFiscaleImpresa);


    /**
     * Save soggetto response.
     *
     * @param xRequestAuth the x request auth
     * @param xRequestId   the x request id
     * @param soggetto     the soggetto
     * @param httpHeaders  the http headers
     * @param httpRequest  the http request
     * @return the response
     */
    @ApiOperation(value = "salva/aggiorna soggetto", nickname = "saveSoggetto", notes = "salva/aggiorna soggetto", response = SoggettoExtendedDTO.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "soggetto", response = SoggettoExtendedDTO.class),
            @ApiResponse(code = 400, message = "invalid input, object invalid"),
            @ApiResponse(code = 401, message = "unauthorized")
    })
    @POST
    @Path("")
    @Consumes({"application/json"})
    Response saveSoggetto(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                          @RequestBody SoggettoExtendedDTO soggetto,
                          @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}