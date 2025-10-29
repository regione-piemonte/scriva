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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Date;

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
     * @param idIstanza         the id istanza
     * @param codTipoEvento     the cod tipo evento
     * @param rifCanaleNotifica the rif canale notifica
     * @param codCanaleNotifica the cod canale notifica
     * @param uidRichiesta      the uid richiesta
     * @param dataIntegrazione  the data integrazione
     * @param tipoRichiesta     the tipo richiesta
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @POST
    Response traceIstanzaEvento(@QueryParam(value = "id_istanza") Long idIstanza,
                                @QueryParam(value = "cod_tipo_evento") String codTipoEvento,
                                @QueryParam(value = "rif_canale_notifica") String rifCanaleNotifica,
                                @QueryParam(value = "cod_canale_notifica") String codCanaleNotifica,
                                @QueryParam(value = "uid_richiesta") String uidRichiesta,
                                @QueryParam(value = "data_integrazione") Date dataIntegrazione,
                                @QueryParam(value = "tipo_richiesta") String tipoRichiesta,
                                @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}