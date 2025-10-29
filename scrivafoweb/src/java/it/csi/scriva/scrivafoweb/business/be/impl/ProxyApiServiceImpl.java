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

import it.csi.scriva.scrivafoweb.business.be.ProxyApi;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.ApiProxyServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.ApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

/**
 * The type Proxy api service.
 */
@Component
public class ProxyApiServiceImpl extends AbstractApiServiceImpl implements ProxyApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Api service helper.
     */
    @Autowired
    ApiServiceHelper apiServiceHelper;

    @Autowired
    ApiProxyServiceHelper apiProxyServiceHelper;

    @Override
    public Response getResponseGET(String servicePath, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponse(servicePath, Constants.REQ_GET, securityContext, httpHeaders, httpRequest, uriInfo);
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
     */
    @Override
    public Response getResponsePOST(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return getResponse(servicePath, Constants.REQ_POST, securityContext, httpHeaders, httpRequest, uriInfo, requestBody);
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
     */
    @Override
    public Response getResponsePUT(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return getResponse(servicePath, Constants.REQ_PUT, securityContext, httpHeaders, httpRequest, uriInfo, requestBody);
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
     */
    @Override
    public Response getResponsePATCH(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return getResponse(servicePath, Constants.REQ_PATCH, securityContext, httpHeaders, httpRequest, uriInfo, requestBody);
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
     */
    @Override
    public Response getResponseDEL(String servicePath, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponse(servicePath, Constants.REQ_DEL, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Gets response.
     *
     * @param servicePath     the service path
     * @param reqType         the req type
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     */
    public Response getResponse(String servicePath, String reqType, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        return getResponse(servicePath, reqType, securityContext, httpHeaders, httpRequest, uriInfo, null);
    }

    /**
     * Gets response.
     *
     * @param servicePath     the service path
     * @param reqType         the req type
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @param requestBody     the request body
     * @return the response
     */
    private Response getResponse(String servicePath, String reqType, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo, Map<String, Object> requestBody) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        Map<String, Object> response = null;
        try {
            switch (reqType) {
                case Constants.REQ_POST:
                    response = apiServiceHelper.getResponsePOST(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
                    //response = apiProxyServiceHelper.getResponsePOST(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
                    break;
                case Constants.REQ_PUT:
                    //response = apiServiceHelper.getResponsePUT(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
                    response = apiProxyServiceHelper.getResponsePUT(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
                    break;
                case Constants.REQ_PATCH:
                    //response = apiServiceHelper.getResponsePATCH(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
                    response = apiProxyServiceHelper.getResponsePATCH(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
                    break;
                case Constants.REQ_DEL:
                    //response = apiServiceHelper.getResponseDEL(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
                    response = apiProxyServiceHelper.getResponseDEL(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
                    break;
                default:
                    //response = apiServiceHelper.getResponseGET(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
                    response = apiProxyServiceHelper.getResponseGET(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
                    break;
            }
            return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
                    .header(Constants.HEADER_CONTENT_TYPE, response.get("RESPONSE_CONTENT_TYPE"))
                    .header(Constants.HEADER_CONTENT_DISPOSITION, response.get("RESPONSE_CONTENT_DISPOSITION"))
                    .header(Constants.HEADER_PAGINATION_INFO, response.get("RESPONSE_PAGINATION_INFO"))
                    .entity(response.get("RESPONSE_ENTITY")).build();
        } catch (Exception e) {
            ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante il recupero delle informazioni.", null, null);
            logError(className, e);
            return Response.serverError().entity(err).status(500).build();
        }
    }

}