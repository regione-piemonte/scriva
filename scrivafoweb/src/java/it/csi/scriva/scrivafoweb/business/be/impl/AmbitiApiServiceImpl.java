/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.AmbitiApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Ambiti api service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class AmbitiApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements AmbitiApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * Gets ambiti.
     *
     * @param idAmbito        the id ambito
     * @param codAmbito       the cod ambito
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the ambiti
     */
    @Override
    public Response getAmbiti(Long idAmbito, String codAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "Parametro in input idAmbito [" + idAmbito + "] - codAmbito [" + codAmbito + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Gets ambito.
     *
     * @param idAmbito        the id ambito
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the ambito
     */
    @Override
    public Response getAmbito(Long idAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idAmbito);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

}