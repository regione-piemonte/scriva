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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The interface Aree protette api.
 *
 * @author CSI PIEMONTE
 */
@Path("/rn2000")
@Produces(MediaType.APPLICATION_JSON)
public interface RetiNatura2000Api {

    /**
     * Gets reti natura 2000.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param codIstatComuni   the cod istat comuni
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the aree protette
     * @throws GenericException the generic exception
     */
    @GET
    Response getRn2000(@QueryParam("idOggettoIstanza") Long idOggettoIstanza,
                       @QueryParam("codIstatComuni") String codIstatComuni,
                       @QueryParam("checkComuni") @DefaultValue("true") Boolean checkComuni,
                       @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

}