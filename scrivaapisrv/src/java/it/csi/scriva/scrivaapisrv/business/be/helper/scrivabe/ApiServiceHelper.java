/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * The type Api service helper.
 */
public class ApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private final CloseableHttpClient proxyHttpsClient = HttpClients.createDefault();

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
     * Gets response get.
     *
     * @param servicePath     the service path
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response get
     */
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
    public Response getResponsePOST(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return getResponse(servicePath, Constants.REQ_POST, httpHeaders, httpRequest, uriInfo, requestBody);
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
    public Response getResponsePUT(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return getResponse(servicePath, Constants.REQ_PUT, httpHeaders, httpRequest, uriInfo, requestBody);
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
    public Response getResponsePATCH(String servicePath, Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        return getResponse(servicePath, Constants.REQ_PATCH, httpHeaders, httpRequest, uriInfo, requestBody);
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
        return getResponse(servicePath, reqType, httpHeaders, httpRequest, uriInfo, null);
    }

    /**
     * Gets response.
     *
     * @param reqType     the req type
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @param uriInfo     the uri info
     * @param requestBody the request body
     * @return the response
     */
    private Response getResponse(String servicePath, String reqType, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo, Map<String, Object> requestBody) {
        logBeginInfo(className, getInputParams(StringUtils.isNotBlank(servicePath) ? servicePath : uriInfo.getPath(), uriInfo.getQueryParameters(), requestBody));
        try {
            return this.getResponse(StringUtils.isNotBlank(servicePath) ? servicePath : uriInfo.getPath(), reqType, uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), requestBody);
        } catch (Exception e) {
            ErrorDTO err = new ErrorDTO("500", "E100", "Errore durante il recupero delle informazioni.", null, null);
            logError(className, e);
            return Response.serverError().entity(err).status(500).build();
        }
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
     * @throws URISyntaxException the uri syntax exception
     * @throws IOException        the io exception
     */
    private Response getResponse(String servicePath, String reqType, MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws URISyntaxException, IOException {
        logBegin(className);
        try {
            URIBuilder uriBuilder = getURIBuilder(endpointBase + servicePath, requestParameters);
            HttpRequestBase request = getHttpRequest(uriBuilder, reqType, headerParams, requestBody);
            CloseableHttpResponse resp = proxyHttpsClient.execute(request);
            return Response.status(resp.getStatusLine().getStatusCode())
                    .entity(resp.getEntity() != null ? resp.getEntity().getContent() : null)
                    .header("Content-Type", resp.getEntity() != null && resp.getEntity().getContentType() != null ? resp.getEntity().getContentType().getValue() : null)
                    .header("Content-Disposition", resp.getHeaders("Content-disposition"))
                    .header("PaginationInfo", resp.getHeaders("PaginationInfo"))
                    .build();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Sets headers on map.
     *
     * @param map  the map
     * @param resp the resp
     */
    private void setHeadersOnMap(Map<String, Object> map, CloseableHttpResponse resp) {
        if (resp != null && resp.getAllHeaders().length > 0) {
            for (Header header : resp.getAllHeaders()) {
                if (header.getName().equalsIgnoreCase("CountCompetenze")) {
                    map.put(header.getName(), header.getValue());
                }
            }
        }
    }

    /**
     * Gets uri builder.
     *
     * @param url               the url
     * @param requestParameters the request parameters
     * @return the uri builder
     * @throws URISyntaxException the uri syntax exception
     */
    private URIBuilder getURIBuilder(String url, MultivaluedMap<String, String> requestParameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (requestParameters != null && !requestParameters.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : requestParameters.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
            }
        }
        return uriBuilder;
    }

    /**
     * Gets http request.
     *
     * @param uriBuilder   the uri builder
     * @param reqType      the req type
     * @param headerParams the header params
     * @param requestBody  the request body
     * @return the http request
     * @throws URISyntaxException the uri syntax exception
     */
    private HttpRequestBase getHttpRequest(URIBuilder uriBuilder, String reqType, MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws URISyntaxException {
        HttpRequestBase request;
        switch (reqType) {
            case Constants.REQ_POST:
                request = new HttpPost(uriBuilder.build());
                break;
            case Constants.REQ_PUT:
                request = new HttpPut(uriBuilder.build());
                break;
            case Constants.REQ_PATCH:
                request = new HttpPatch(uriBuilder.build());
                break;
            case Constants.REQ_DEL:
                request = new HttpDelete(uriBuilder.build());
                break;
            default:
                request = new HttpGet(uriBuilder.build());
                break;
        }
        setHeaderParams(request, headerParams);
        if (request instanceof HttpEntityEnclosingRequestBase) {
            setRequestBody((HttpEntityEnclosingRequestBase) request, requestBody);
        }
        return request;
    }

    /**
     * Set header params.
     *
     * @param request      the request
     * @param headerParams the header params
     */
    private void setHeaderParams(HttpRequestBase request, MultivaluedMap<String, Object> headerParams) {
        if (request != null && headerParams != null && !headerParams.isEmpty()) {
            for (String key : headerParams.keySet()) {
                request.addHeader(key, (String) headerParams.getFirst(key));
            }
        }
    }

}