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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;

/**
 * The interface Tipologie oggetto api.
 *
 * @author CSI PIEMONTE
 */
@Path("/tipologie-oggetto")
@Produces(MediaType.APPLICATION_JSON)
public interface TipologieOggettoApi {

    /**
     * Load tipologie oggetto by ademinpimento coderesponse.
     *
     * @param codAdempimento   the cod adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    Response loadTipologieOggettoByAdempimentoCode(@QueryParam(value = "cod_adempimento") String codAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}