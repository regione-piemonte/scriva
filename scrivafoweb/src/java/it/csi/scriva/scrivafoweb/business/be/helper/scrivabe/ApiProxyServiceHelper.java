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

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Api service helper.
 */
public class ApiProxyServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public ApiProxyServiceHelper(String hostname, String endpointBase) {
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
        HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams, MediaType.APPLICATION_JSON);
        //Response response = getResponseFromReq(reqBuilder.GET().build());
        return getResponse(reqBuilder.GET().build());
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
        HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams, MediaType.APPLICATION_JSON);
        return getResponse(reqBuilder.POST(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
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
        HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams, MediaType.APPLICATION_JSON);
        return getResponse(reqBuilder.PUT(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
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
        HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams, MediaType.APPLICATION_JSON);
        return getResponse(reqBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
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
        HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams, MediaType.APPLICATION_JSON);
        return getResponse(reqBuilder.DELETE().build());
    }

    /**
     * Gets response.
     *
     * @param request the request
     * @return the response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public Map<String, Object> getResponse(HttpRequest request) throws IOException, InterruptedException {
        logBegin(className);
        Map<String, Object> response = new HashMap<>();
        try {
            HttpResponse<String> httpResponse = getHttpResponse(request);
            response.put("RESPONSE_HTTP_STATUS", httpResponse.statusCode());
            if (StringUtils.isNotBlank(httpResponse.body())) {
                response.put("RESPONSE_ENTITY", httpResponse.body());
            }
            setHeadersOnResp(response, httpResponse.headers());
            return response;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets response from req.
     *
     * @param request the request
     * @return the response from req
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public Response getResponseFromReq(HttpRequest request) throws IOException, InterruptedException {
        logBegin(className);
        try {
            HttpResponse<String> httpResponse = getHttpResponse(request);
            return Response.status(httpResponse.statusCode())
                    .header(Constants.HEADER_CONTENT_TYPE, httpResponse.headers().firstValue(Constants.HEADER_CONTENT_TYPE))
                    .header(Constants.HEADER_CONTENT_DISPOSITION, httpResponse.headers().firstValue(Constants.HEADER_CONTENT_DISPOSITION))
                    .header(Constants.HEADER_PAGINATION_INFO, httpResponse.headers().firstValue(Constants.HEADER_PAGINATION_INFO))
                    .header(Constants.HEADER_COUNT_NOTIFICHE, httpResponse.headers().firstValue(Constants.HEADER_COUNT_NOTIFICHE))
                    .entity(httpResponse.body()).build();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets http request builder.
     *
     * @param url               the url
     * @param requestParameters the request parameters
     * @param headerParams      the header params
     * @param contentType       the content type
     * @return the http request builder
     */
    private HttpRequest.Builder getHttpRequestBuilder(String url, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams, String contentType) {
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder(URI.create(endpointBase + url + getParamsString(requestParameters)))
                .header(Constants.HEADER_CONTENT_TYPE, contentType);
        setHeadersOnReq(headerParams, httpRequestBuilder);
        return httpRequestBuilder;
    }

    /**
     * Gets request body.
     *
     * @param requestBody the request body
     * @return the request body
     */
    private String getRequestBody(Map<String, Object> requestBody) {
        String entity = null;
        try {
            if (requestBody != null && !requestBody.isEmpty()) {
                JSONObject jsonObject = new JSONObject(requestBody);
                entity = new ObjectMapper().writeValueAsString(jsonObject);
            }
        } catch (Exception e) {
            logError(className, e);
        }
        return entity;
    }

    /**
     * Gets http response.
     *
     * @param request the request
     * @return the http response
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    private HttpResponse<String> getHttpResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                //.version(HttpClient.Version.HTTP_2)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Gets params string.
     *
     * @param requestParameters the request parameters
     * @return the params string
     */
    private String getParamsString(MultivaluedMap<String, String> requestParameters) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : requestParameters.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue().get(0), StandardCharsets.UTF_8));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0
                ? "?" + resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    /**
     * Sets headers on map.
     *
     * @param headerParams       the header params
     * @param httpRequestBuilder the http request builder
     */
    private void setHeadersOnReq(MultivaluedMap<String, Object> headerParams, HttpRequest.Builder httpRequestBuilder) {
        if (httpRequestBuilder != null && !CollectionUtils.isEmpty(headerParams)) {
            headerParams.forEach((key, value) -> httpRequestBuilder.header(key.toLowerCase(), (String) value.get(0)));
        }
    }

    private void setHeadersOnResp(Map<String, Object> response, HttpHeaders httpHeaders) {
        if (response != null && httpHeaders != null) {
            response.put("RESPONSE_CONTENT_TYPE", httpHeaders.firstValue(Constants.HEADER_CONTENT_TYPE).isPresent() ? httpHeaders.allValues(Constants.HEADER_CONTENT_TYPE).get(0) : "application/json");
            response.put("RESPONSE_CONTENT_DISPOSITION", httpHeaders.firstValue(Constants.HEADER_CONTENT_DISPOSITION).isPresent() ? httpHeaders.allValues(Constants.HEADER_CONTENT_DISPOSITION).get(0) : null);
            response.put("RESPONSE_PAGINATION_INFO", httpHeaders.firstValue(Constants.HEADER_PAGINATION_INFO).isPresent() ? httpHeaders.allValues(Constants.HEADER_PAGINATION_INFO).get(0) : null);
        }
    }


}