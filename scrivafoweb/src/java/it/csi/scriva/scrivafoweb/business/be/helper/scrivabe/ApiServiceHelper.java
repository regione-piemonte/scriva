/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Api service helper.
 */
public class ApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    //private final CloseableHttpClient proxyHttpsClient = HttpClients.createDefault();

    /**
     * Instantiates a new Api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public ApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets response GET.
     *
     * @param servicePath       the service path
     * @param requestParameters the request parameters
     * @param headerParams      the header params
     * @return the response
     * @throws Exception the exception
     */
    public Map<String, Object> getResponseGET(String servicePath, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams) throws Exception {
        logBegin(className);
        return getResponse(servicePath, Constants.REQ_GET, requestParameters, headerParams, null);
    }

    /**
     * Gets response post.
     *
     * @param servicePath       the service path
     * @param requestParameters the request parameters
     * @param headerParams      the header params
     * @param requestBody       the request body
     * @return the response post
     * @throws Exception the exception
     */
    public Map<String, Object> getResponsePOST(String servicePath, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception {
        logBegin(className);
        return getResponse(servicePath, Constants.REQ_POST, requestParameters, headerParams, requestBody);
    }

    /**
     * Gets response put.
     *
     * @param servicePath       the service path
     * @param requestParameters the request parameters
     * @param headerParams      the header params
     * @param requestBody       the request body
     * @return the response put
     * @throws Exception the exception
     */
    public Map<String, Object> getResponsePUT(String servicePath, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception {
        logBegin(className);
        return getResponse(servicePath, Constants.REQ_PUT, requestParameters, headerParams, requestBody);
    }

    /**
     * Gets response patch.
     *
     * @param servicePath       the service path
     * @param requestParameters the request parameters
     * @param headerParams      the header params
     * @param requestBody       the request body
     * @return the response patch
     * @throws Exception the exception
     */
    public Map<String, Object> getResponsePATCH(String servicePath, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception {
        logBegin(className);
        return getResponse(servicePath, Constants.REQ_PATCH, requestParameters, headerParams, requestBody);
    }

    /**
     * Gets response del.
     *
     * @param servicePath       the service path
     * @param requestParameters the request parameters
     * @param headerParams      the header params
     * @return the response del
     * @throws Exception the exception
     */
    public Map<String, Object> getResponseDEL(String servicePath, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams) throws Exception {
        logBegin(className);
        return getResponse(servicePath, Constants.REQ_DEL, requestParameters, headerParams, null);
    }

    /**
     * Gets response.
     *
     * @param servicePath       the service path
     * @param reqType           the req type
     * @param requestParameters the request parameters
     * @param headerParams      the header params
     * @param requestBody       the request body
     * @return the response
     * @throws Exception the exception
     */
    private Map<String, Object> getResponse(String servicePath, String reqType, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception {
        logBegin(className);
        Map<String, Object> response = new HashMap<>();
        HttpRequestBase request;
        try (CloseableHttpClient proxyHttpsClient = HttpClients.createDefault()) {
            URIBuilder uriBuilder = getURIBuilder(endpointBase + servicePath, requestParameters);
            request = getHttpRequest(uriBuilder, reqType, headerParams, requestBody);
            CloseableHttpResponse resp = proxyHttpsClient.execute(request);
            response.put("RESPONSE_HTTP_STATUS", resp.getStatusLine().getStatusCode());
            if (resp.getEntity() != null) {
                if (resp.getEntity().getContentType() != null) {
                    response.put("RESPONSE_CONTENT_TYPE", resp.getEntity().getContentType().getValue());
                } // Content-Disposition: attachment
                response.put("RESPONSE_ENTITY", resp.getEntity().getContent());
                //setHeadersOnMap(response, resp);
            }
            return response;
        } catch (Exception e) {
            logError(className, e);
            throw new Exception(e);
        } finally {
            logEnd(className);
        }
    }

    private void setHeadersOnMap(Map<String, Object> map, CloseableHttpResponse resp) {
        if (resp != null && resp.getAllHeaders().length > 0) {
            for (Header header : resp.getAllHeaders()) {
                if (header.getName().equalsIgnoreCase("CountCompetenze")) {
                    map.put(header.getName(), header.getValue());
                }
            }
        }
    }


}