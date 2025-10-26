/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import javax.servlet.http.HttpServletRequest;
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
 * The interface Istanza eventi api.
 *
 * @author CSI PIEMONTE
 */
@Path("/eventi")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzaEventiApi {

    /**
     * Trace eventi response.
     *
     * @param idIstanza       the id istanza
     * @param codTipoEvento   the cod tipo evento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    Response traceIstanzaEvento(@QueryParam(value = "id_istanza") Long idIstanza, @QueryParam(value = "cod_tipo_evento") String codTipoEvento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}