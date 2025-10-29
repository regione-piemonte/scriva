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

import it.csi.scriva.scrivafoweb.business.be.RetiNatura2000Api;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Aree protette api.
 *
 * @author CSI PIEMONTE
 */
@Component
public class RetiNatura2000ApiImpl extends ScrivaBeProxyApiServiceImpl implements RetiNatura2000Api {

    private final String className = this.getClass().getSimpleName();

    /**
     * Gets rn 2000.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param codIstatComuni   the cod istat comuni
     * @param checkComuni      the check comuni
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the aree protette
     * @throws Exception the exception
     */
    @Override
    public Response getRn2000(Long idOggettoIstanza, String codIstatComuni, Boolean checkComuni, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idOggettoIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

}