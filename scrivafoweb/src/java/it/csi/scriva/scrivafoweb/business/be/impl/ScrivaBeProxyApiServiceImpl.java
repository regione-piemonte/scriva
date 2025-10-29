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

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.ScrivaBeProxyServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Map;

/**
 * The type Scriva be proxy api service.
 */
@Component
public class ScrivaBeProxyApiServiceImpl extends AbstractApiServiceImpl {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Scriva be proxy service helper.
     */
    @Autowired
    ScrivaBeProxyServiceHelper scrivaBeProxyServiceHelper;

    /**
     * Gets response get.
     *
     * @param servicePath     the service path
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response get
     * @throws Exception the exception
     */
    public Response getResponseGET(String servicePath, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return scrivaBeProxyServiceHelper.getResponseGET(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
    }

    /**
     * Gets response post.
     *
     * @param servicePath     the service path
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response post
     * @throws Exception the exception
     */
    public Response getResponsePOST(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return scrivaBeProxyServiceHelper.getResponsePOST(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
    }
    
    /**
     * Gets response post.
     *
     * @param servicePath     the service path
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response post
     * @throws Exception the exception
     */
    
   
    public Response getResponsePOST(String servicePath, List<Map<?,?>> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParamsExt(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return scrivaBeProxyServiceHelper.getResponsePOST(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
    }

    /**
     * Gets response put.
     *
     * @param servicePath     the service path
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response put
     * @throws Exception the exception
     */
    public Response getResponsePUT(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return scrivaBeProxyServiceHelper.getResponsePUT(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
    }
    
    public Response getResponsePUT(String servicePath, List<Map<?,?>> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParamsExt(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return scrivaBeProxyServiceHelper.getResponsePUT(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
    }

    
    /**
     * Gets response patch.
     *
     * @param servicePath     the service path
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response patch
     * @throws Exception the exception
     */
    public Response getResponsePATCH(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return scrivaBeProxyServiceHelper.getResponsePATCH(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
    }

    /**
     * Gets response del.
     *
     * @param servicePath     the service path
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response del
     * @throws Exception the exception
     */
    public Response getResponseDEL(String servicePath, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return scrivaBeProxyServiceHelper.getResponseDEL(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
    }

}