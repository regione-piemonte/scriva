/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.dispatcher;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import it.csi.scriva.scrivaconsweb.business.service.exception.ProxyRequestException;

public interface ProxyDispatcher {

    //POST 
	//public Map<String, Object> getResponse(String systemToRedirect, String servicePath, MultivaluedMap<String, String> requestParameters) throws ProxyRequestException;
	public Map<String, Object> getResponse(String verb, String servicePath, MultivaluedMap<String, String> requestParameters, Map<String, Object> requestBody, String headerParam, HttpServletRequest httpRequest) throws ProxyRequestException;
 
	//POST per file allegati
	public Response getFileResponse(String verb,String servicePath, MultivaluedMap<String, String> requestParameters,Map<String, Object> requestBody,String headerParam,HttpServletRequest httpRequest) throws ProxyRequestException;
    
    //GET
    public Map<String, Object> getResponse(String verb,String servicePath, MultivaluedMap<String, String> requestParameters,HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws ProxyRequestException;
    
    //MULTIPART
    public Map<String, Object> getResponseMultiPart(String verb,String servicePath, MultivaluedMap<String, String> requestParameters,MultipartFormDataInput formDataInput,String headerParam,HttpServletRequest httpRequest) throws ProxyRequestException;
    
    //DELETE
    public Map<String, Object> getDelete(String verb,String servicePath, MultivaluedMap<String, String> requestParameters,HttpHeaders httpHeaders,HttpServletRequest httpRequest) throws ProxyRequestException;

   
    
}
