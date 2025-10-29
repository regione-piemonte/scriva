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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.math.BigDecimal;

/**
 * The interface GeoPoint converter api.
 *
 * @author CSI PIEMONTE
 */
@Path("/geopoint-converter")
@Produces(MediaType.APPLICATION_JSON)
public interface GeoPointConverterApi {

    /**
     * Load tipologie oggetto by ademinpimento coderesponse.
     *
     * @param latitudine      the latitudine
     * @param longitudine     the longitudine
     * @param sourceEPSG      the optional sourceEPSG
     * @param targetEPSG      the optional targetEPSG
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    Response convertPoint(@QueryParam(value = "lat") BigDecimal latitudine,
                          @QueryParam(value = "lon") BigDecimal longitudine,
                          @QueryParam(value = "source_epsg") Integer sourceEPSG,
                          @QueryParam(value = "target_epsg") Integer targetEPSG,
                          @Context SecurityContext securityContext,
                          @Context HttpHeaders httpHeaders,
                          @Context HttpServletRequest httpRequest);

}