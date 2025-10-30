/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.business.service.util;


import java.io.IOException;

import java.io.InputStream;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class ScrivaConsWebHttpClientProxyService {

	//private final String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ScrivaConsWebHttpClientProxyService.class);

	private String urlEnv;

	public ScrivaConsWebHttpClientProxyService(String urlEnv) {
		this.urlEnv = urlEnv;
	}
    /*GET*/
	public Map<String, Object> getMapResponseGET(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams) throws Exception 
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getMapResponse(reqBuilder.GET().build());
	}

	public Response getResponseGET(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams) throws Exception 
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getResponseFromReq(reqBuilder.GET().build());
	}

	public HttpResponse<String> getHttpResponseGET(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams) throws Exception 
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getHttpResponse(reqBuilder.GET().build());
	}

	/*POST*/
	public Map<String, Object> getMapResponsePOST(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception 
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getMapResponse(
				reqBuilder.POST(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
	}

	public Response getResponsePOST(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getResponseFromReq(
				reqBuilder.POST(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
	}
	
	public Response getResponseFilePOST(String servicePath, MultivaluedMap<String, String> requestParameters,
		MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception
	{
	if (logger.isDebugEnabled())
		logger.debug("proxy: pathService => " + servicePath);
	HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
			MediaType.APPLICATION_JSON);
	return getResponseFileFromReq(
			reqBuilder.POST(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
	}
	

	public HttpResponse<String> getHttpResponsePOST(String servicePath,
			MultivaluedMap<String, String> requestParameters, MultivaluedMap<String, Object> headerParams,
			Map<String, Object> requestBody) throws Exception
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getHttpResponse(
				reqBuilder.POST(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
	}
    /*PUT*/
	public Map<String, Object> getMapResponsePUT(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception 
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getMapResponse(reqBuilder.PUT(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
	}

	public Response getResponsePUT(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getResponseFromReq(
				reqBuilder.PUT(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
	}

	public HttpResponse<String> getHttpResponsePUT(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws Exception 
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getHttpResponse(
				reqBuilder.PUT(HttpRequest.BodyPublishers.ofString(getRequestBody(requestBody))).build());
	}
	/*DEL*/
	public Map<String, Object> getMapResponseDEL(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams) throws Exception
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getMapResponse(reqBuilder.DELETE().build());
	}

	public Response getResponseDEL(String servicePath, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams) throws Exception
	{
		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		HttpRequest.Builder reqBuilder = getHttpRequestBuilder(servicePath, requestParameters, headerParams,
				MediaType.APPLICATION_JSON);
		return getResponseFromReq(reqBuilder.DELETE().build());
	}

	/**************************************************************************************************/
	
	/*ISTANZA HTTP REQUEST*/
	private HttpRequest.Builder getHttpRequestBuilder(String url, MultivaluedMap<String, String> requestParameters,
			MultivaluedMap<String, Object> headerParams, String contentType) 
	{
		logger.info("urlEnv: " + urlEnv);
		logger.info("url: " + url);
		String reqPar = getParamsString(requestParameters);
		logger.info("requestParameters: " + reqPar);
		URI uri = URI.create(urlEnv + url + reqPar);
		logger.info("URI: " + uri);
		logger.info("contentType: " + contentType);
		HttpRequest.Builder httpRequestBuilder = HttpRequest
				.newBuilder(uri)
				.header(Constants.HEADER_CONTENT_TYPE, contentType);
		setHeadersOnReq(headerParams, httpRequestBuilder);
		return httpRequestBuilder;
	}
	
	/*SET di eventuali query param */
	private String getParamsString(MultivaluedMap<String, String> requestParameters) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, List<String>> entry : requestParameters.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue().get(0), StandardCharsets.UTF_8));
			result.append("&");
		}
		String resultString = result.toString();
		return resultString.length() > 0 ? "?" + resultString.substring(0, resultString.length() - 1) : resultString;
	}
	
	/*SET di eventuali header param*/
	private void setHeadersOnReq(MultivaluedMap<String, Object> headerParams, HttpRequest.Builder httpRequestBuilder) {
		if (httpRequestBuilder != null && !CollectionUtils.isEmpty(headerParams)) {
			for (Map.Entry<String, List<Object>> entry : headerParams.entrySet()) {
				 //logger.info("entry.getKey() fuori dalla if " + entry.getKey());
				 if (entry.getValue() != null && entry.getKey()!=null && entry.getValue().size()>0) {
					 //logger.info("entry.getKey() " + entry.getKey());
					 //logger.info("entry.getValue() " + entry.getValue());
					 //logger.info("entry.getValue().get(0) " + entry.getValue().get(0));
					 httpRequestBuilder.header(entry.getKey().toLowerCase(), (String) entry.getValue().get(0));
				 }
			}
		}
	}
	
	/*Inoltro richiesta con risposta*/
	private HttpResponse<String> getHttpResponse(HttpRequest request) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}
	private HttpResponse<InputStream> getHttpResponseFile(HttpRequest request) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
		return client.send(request, HttpResponse.BodyHandlers.ofInputStream());
	}
	
	/*get dei Responce come mappa*/
	public Map<String, Object> getMapResponse(HttpRequest request) throws IOException, InterruptedException {
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

		}
	}
	
	/*get dei Responce*/
	public Response getResponseFromReq(HttpRequest request) throws IOException, InterruptedException {

		try {
			HttpResponse<String> httpResponse = getHttpResponse(request);
			return Response.status(httpResponse.statusCode())
					.header(Constants.HEADER_CONTENT_TYPE,
							httpResponse.headers().firstValue(Constants.HEADER_CONTENT_TYPE).isPresent()
									? httpResponse.headers().allValues(Constants.HEADER_CONTENT_TYPE).get(0)
									: MediaType.APPLICATION_JSON)
					.header(Constants.HEADER_CONTENT_DISPOSITION,
						httpResponse.headers().firstValue(Constants.HEADER_CONTENT_DISPOSITION).isPresent()
								? httpResponse.headers().allValues(Constants.HEADER_CONTENT_DISPOSITION).get(0)
								: null)
					.header(Constants.HEADER_PAGINATION_INFO,
							httpResponse.headers().firstValue(Constants.HEADER_PAGINATION_INFO).isPresent()
									? httpResponse.headers().allValues(Constants.HEADER_PAGINATION_INFO).get(0)
									: null)
					.entity(httpResponse.body()).build();
		} finally {
			if (logger.isDebugEnabled())
				logger.debug("proxy:  end ");
		}
	}
	
	public Response getResponseFileFromReq(HttpRequest request) throws IOException, InterruptedException {

		try {
			HttpResponse<InputStream> httpResponse = getHttpResponseFile(request);
			return Response.status(httpResponse.statusCode())
					.header(Constants.HEADER_CONTENT_TYPE,
							httpResponse.headers().firstValue(Constants.HEADER_CONTENT_TYPE).isPresent()
									? httpResponse.headers().allValues(Constants.HEADER_CONTENT_TYPE).get(0)
									: MediaType.APPLICATION_JSON)
					.header(Constants.HEADER_CONTENT_DISPOSITION,
						httpResponse.headers().firstValue(Constants.HEADER_CONTENT_DISPOSITION).isPresent()
								? httpResponse.headers().allValues(Constants.HEADER_CONTENT_DISPOSITION).get(0)
								: null)
					.header(Constants.HEADER_PAGINATION_INFO,
							httpResponse.headers().firstValue(Constants.HEADER_PAGINATION_INFO).isPresent()
									? httpResponse.headers().allValues(Constants.HEADER_PAGINATION_INFO).get(0)
									: null)
					.entity(httpResponse.body().readAllBytes()).build();
		} finally {
			if (logger.isDebugEnabled())
				logger.debug("proxy:  end ");
		}
	}


	/*set del body in caso di POST*/
	private String getRequestBody(Map<String, Object> requestBody) {
		String entity = "{}";
		try {
			if (requestBody != null && !requestBody.isEmpty()) {
				JSONObject jsonObject = new JSONObject(requestBody);
				//entity = new ObjectMapper().writeValueAsString(jsonObject);
				entity = jsonObject.toString();
			}
		} catch (Exception e) {
			logger.error("proxy:  getRequestBody(): " + e);

		}
		return entity;
	}

	private void setHeadersOnResp(Map<String, Object> response, HttpHeaders httpHeaders) {
		if (response != null && httpHeaders != null) {
			response.put("RESPONSE_CONTENT_TYPE", httpHeaders.firstValue(Constants.HEADER_CONTENT_TYPE).get());
			
			if (!httpHeaders.firstValue(Constants.HEADER_PAGINATION_INFO).isEmpty()) {
				response.put("RESPONSE_PAGINATION_INFO", httpHeaders.firstValue(Constants.HEADER_PAGINATION_INFO).get());
			}
			if (!httpHeaders.firstValue(Constants.HEADER_COUNTER).isEmpty()) {
				response.put("RESPONSE_CONTATORE_INFO", httpHeaders.firstValue(Constants.HEADER_COUNTER).get());
			}
			if (!httpHeaders.firstValue(Constants.HEADER_CONTENT_DISPOSITION).isEmpty()) {
				response.put("RESPONSE_CONTENT_DISPOSITION", httpHeaders.firstValue(Constants.HEADER_CONTENT_DISPOSITION).get());
			}
			if (!httpHeaders.firstValue(Constants.HEADER_CONTENT_LENGTH).isEmpty()) {
				response.put("RESPONSE_CONTENT_LENGTH", httpHeaders.firstValue(Constants.HEADER_CONTENT_LENGTH).get());
			}
		}
	}


}
