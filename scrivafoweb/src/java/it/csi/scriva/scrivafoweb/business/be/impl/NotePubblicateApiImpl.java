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

import it.csi.scriva.scrivafoweb.business.be.NotePubblicateApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

/**
 * The type Note pubblicate api.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotePubblicateApiImpl extends ScrivaBeProxyApiServiceImpl implements NotePubblicateApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * Search note pubblicate response.
     *
     * @param idIstanza       the id istanza
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response searchNotePubblicate(Long idIstanza, Integer offset, Integer limit, String sort, SecurityContext securityContext, javax.ws.rs.core.HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className,"idIstanza [" + idIstanza + "] - offset [" + offset + "] - limit [" + limit + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Save nota istanza response.
     *
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response saveNotaIstanza(Map<String, Object> requestBody, SecurityContext securityContext, javax.ws.rs.core.HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "requestBody:\n" + requestBody);
        return getResponsePOST(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Update nota istanza response.
     *
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response updateNotaIstanza(Map<String, Object> requestBody, SecurityContext securityContext, javax.ws.rs.core.HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "requestBody:\n" + requestBody);
        return getResponsePUT(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Delete nota istanza response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response deleteNotaIstanza(String uid, SecurityContext securityContext, javax.ws.rs.core.HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className,"uid [" + uid + "]");
        return getResponseDEL(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }
}