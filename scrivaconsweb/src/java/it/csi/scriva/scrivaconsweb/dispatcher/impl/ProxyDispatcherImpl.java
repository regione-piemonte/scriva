/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.dispatcher.impl;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaconsweb.business.service.exception.ProxyRequestException;
import it.csi.scriva.scrivaconsweb.business.service.proxy.ProxyService;
import it.csi.scriva.scrivaconsweb.dispatcher.ProxyDispatcher;

@Component
public class ProxyDispatcherImpl implements ProxyDispatcher {

	@Autowired
	private ProxyService proxyService;

	@Override
	public Map<String, Object> getResponse(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ProxyRequestException {
		Map<String, Object> response = null;
		try {

			response = proxyService.getResponse(servicePath, requestParameters, httpHeaders, httpRequest);

		} catch (Exception e) {
			throw e;
		}

		return response;
	}

	@Override
	public Map<String, Object> getResponse(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, Map<String, Object> requestBody, String headerParam,HttpServletRequest httpRequest)
			throws ProxyRequestException {
		Map<String, Object> response = null;
		try {

			if (verb.equalsIgnoreCase("POST")) {
				response = proxyService.getResponsePost(verb, servicePath, requestParameters, requestBody, headerParam,httpRequest);
			} else {
				response = proxyService.getResponsePut(verb, servicePath, requestParameters, requestBody, headerParam,httpRequest);
			}

		} catch (Exception e) {
			throw e;
		}

		return response;
	}
	
	@Override
	public Response getFileResponse(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, Map<String, Object> requestBody, String headerParam,HttpServletRequest httpRequest)
			throws ProxyRequestException {
		Response response = null;
		try {

			if (verb.equalsIgnoreCase("POST")) {
				response = proxyService.getResponseFilePost(verb, servicePath, requestParameters, requestBody, headerParam,httpRequest);
			}

		} catch (Exception e) {
			throw e;
		}

		return response;
	}

	@Override
	public Map<String, Object> getResponseMultiPart(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, MultipartFormDataInput formDataInput, String headerParam,HttpServletRequest httpRequest)
			throws ProxyRequestException {

		Map<String, Object> response = null;
		try {

			response = proxyService.getResponseMutliPart(verb, servicePath, requestParameters, formDataInput,
					headerParam,httpRequest);

		} catch (Exception e) {
			throw e;
		}

		return response;
	}

	@Override
	public Map<String, Object> getDelete(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, HttpHeaders httpHeaders,HttpServletRequest httpRequest) throws ProxyRequestException {
		Map<String, Object> response = null;
		try {

			response = proxyService.getDelete(servicePath, requestParameters, httpHeaders,httpRequest);

		} catch (Exception e) {
			throw e;
		}

		return response;
	}

}
