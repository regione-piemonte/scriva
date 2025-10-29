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

import it.csi.scriva.scrivafoweb.business.be.IntegrazioniApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

/**
 * The type Integrazioni api.
 *
 * @author CSI PIEMONTE
 */
@Component
public class IntegrazioniApiImpl extends ScrivaBeProxyApiServiceImpl implements IntegrazioniApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * Load integrazioni response.
     *
     * @param idIstanza           the id istanza
     * @param codTipoIntegrazione the cod tipo integrazione
     * @param securityContext     SecurityContext
     * @param httpHeaders         HttpHeaders
     * @param httpRequest         HttpServletRequest
     * @param uriInfo             the uri info
     * @return Response response
     */
    @Override
    public Response loadIntegrazioni(Long idIstanza, String codTipoIntegrazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "idIstanza [" + idIstanza + "] - codTipoIntegrazione [" + codTipoIntegrazione + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Save integrazioni response.
     *
     * @param requestBody     the request body
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @param uriInfo         the uri info
     * @return Response response
     */
    @Override
    public Response saveIntegrazioni(Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "requestBody:\n" + requestBody);
        return getResponsePOST(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Update integrazioni response.
     *
     * @param requestBody     the request body
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @param uriInfo         the uri info
     * @return Response response
     */
    @Override
    public Response updateIntegrazioni(Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, requestBody);
        return getResponsePUT(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }
}