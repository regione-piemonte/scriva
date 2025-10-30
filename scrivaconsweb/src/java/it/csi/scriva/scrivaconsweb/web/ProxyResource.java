/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import it.csi.scriva.scrivaconsweb.dispatcher.ProxyDispatcher;
import it.csi.scriva.scrivaconsweb.dto.UserInfo;
import it.csi.scriva.scrivaconsweb.filter.IrideIdAdapterFilter;
import it.csi.scriva.scrivaconsweb.util.SpringSupportedResource;

@Path("v1/")
public class ProxyResource extends SpringSupportedResource {

	private static Logger logger = Logger.getLogger(ProxyResource.class);

	@Autowired
	private ProxyDispatcher proxyDispatcher;

	@GET
	@Path("secure/ping/spid")
	public Response proxyGetPing(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
		UserInfo userInfo = (UserInfo) httpRequest.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
		return Response.ok(userInfo).header("Content-Type", "application/json").build();
	}

	@GET
	@Path("secure/{servicePath:.*}")
	public Response proxyGetSecure(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("secure", HttpMethod.GET, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}

		Map<String, Object> response = null;
		try {
			logger.info("Chiamata API : " + servicePath);
			response = proxyDispatcher.getResponse("GET", servicePath, uriInfo.getQueryParameters(), httpHeaders,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));

		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.header("Content-Disposition", (String) response.get("RESPONSE_CONTENT_DISPOSITION"))
				.header("PaginationInfo", (String) response.get("RESPONSE_PAGINATION_INFO"))
				.header("Counter", (String) response.get("RESPONSE_CONTATORE_INFO"))
				.entity(response.get("RESPONSE_ENTITY")).build();
	}

	
	@GET
	@Path("{servicePath:.*}")
	public Response proxyGet(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("", HttpMethod.GET, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}

		Map<String, Object> response = null;

		try {
			logger.info("Chiamata API : " + servicePath);
			response = proxyDispatcher.getResponse("GET", servicePath, uriInfo.getQueryParameters(), httpHeaders,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}

		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));

		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.header("Content-Disposition", (String) response.get("RESPONSE_CONTENT_DISPOSITION"))
				.header("PaginationInfo", (String) response.get("RESPONSE_PAGINATION_INFO"))
				.header("Counter", (String) response.get("RESPONSE_CONTATORE_INFO"))
				.entity(response.get("RESPONSE_ENTITY")).build();
	}
	
	@POST
	@Path("allegati/_download")
	public Response proxyFilePost(@Context UriInfo uriInfo,
			@RequestBody(required = false) Map<String, Object> requestBody,
			@HeaderParam(value = "Attore-Scriva") String headerParam,@Context HttpServletRequest httpRequest) {
		
		Response response = null;
		String servicePath = "allegati/_download";
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getFileResponse("POST", servicePath, uriInfo.getQueryParameters(), requestBody,
					headerParam,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		return response;
	}


	
	@POST
	@Path("secure/{servicePath:.*}")
	public Response proxyPostSecure(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@RequestBody(required = false) Map<String, Object> requestBody,
			@HeaderParam(value = "Attore-Scriva") String headerParam,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("secure", HttpMethod.POST, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getResponse("POST", servicePath, uriInfo.getQueryParameters(), requestBody,
					headerParam,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.header("PaginationInfo", (String) response.get("RESPONSE_PAGINATION_INFO"))
				.header("Counter", (String) response.get("RESPONSE_CONTATORE_INFO"))
				.entity((String) response.get("RESPONSE_ENTITY")).build();
	}

	
	
	
	@POST
	@Path("{servicePath:.*}")
	public Response proxyPost(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@RequestBody(required = false) Map<String, Object> requestBody,
			@HeaderParam(value = "Attore-Scriva") String headerParam,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("", HttpMethod.POST, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getResponse("POST", servicePath, uriInfo.getQueryParameters(), requestBody,
					headerParam,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.header("Content-Disposition", (String) response.get("RESPONSE_CONTENT_DISPOSITION"))
				.header("Content-Length", (String) response.get("RESPONSE_CONTENT_LENGTH"))
				.header("PaginationInfo", (String) response.get("RESPONSE_PAGINATION_INFO"))
				.header("Counter", (String) response.get("RESPONSE_CONTATORE_INFO"))
				.entity(response.get("RESPONSE_ENTITY")).build();
	}
	
	

	
	
	@PUT
	@Path("secure/{servicePath:.*}")
	public Response proxyPutSecure(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@RequestBody(required = true) Map<String, Object> requestBody,
			@HeaderParam(value = "Attore-Scriva") String headerParam,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("secure", HttpMethod.PUT, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getResponse("PUT", servicePath, uriInfo.getQueryParameters(), requestBody,
					headerParam,httpRequest);
		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.entity((String) response.get("RESPONSE_ENTITY")).build();
	}
	
	
	@PUT
	@Path("{servicePath:.*}")
	public Response proxyPut(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@RequestBody(required = true) Map<String, Object> requestBody,
			@HeaderParam(value = "Attore-Scriva") String headerParam,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("", HttpMethod.PUT, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getResponse("PUT", servicePath, uriInfo.getQueryParameters(), requestBody,
					headerParam,httpRequest);
		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.entity((String) response.get("RESPONSE_ENTITY")).build();
	}

	
	
	@POST
	@Path("secure/multipart/{servicePath:.*}")
	public Response proxyPostMediaTypeSecure(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			MultipartFormDataInput formDataInput, @HeaderParam(value = "Content-Disposition") String headerParam,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("secure/multipart", HttpMethod.POST, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getResponseMultiPart("POST", servicePath, uriInfo.getQueryParameters(),
					formDataInput, headerParam,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.entity((String) response.get("RESPONSE_ENTITY")).build();
	}
	
	
	
	@POST
	@Path("multipart/{servicePath:.*}")
	public Response proxyPostMediaType(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			MultipartFormDataInput formDataInput, @HeaderParam(value = "Content-Disposition") String headerParam,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("multipart", HttpMethod.POST, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getResponseMultiPart("POST", servicePath, uriInfo.getQueryParameters(),
					formDataInput, headerParam,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.entity((String) response.get("RESPONSE_ENTITY")).build();
	}

	
	@DELETE
	@Path("secure/{servicePath:.*}")
	public Response proxyDeleteSecure(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@Context HttpHeaders httpHeaders,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("secure", HttpMethod.DELETE, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getDelete("DELETE", servicePath, uriInfo.getQueryParameters(), httpHeaders,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.header("Content-Disposition", (String) response.get("RESPONSE_CONTENT_DISPOSITION"))
				.header("Transfer-Encoding", (String) response.get("RESPONSE_TRANSFER-ENCODING"))
				.entity(response.get("RESPONSE_ENTITY")).build();
	}
	
	
	@DELETE
	@Path("{servicePath:.*}")
	public Response proxyDelete(@PathParam("servicePath") String servicePath, @Context UriInfo uriInfo,
			@Context HttpHeaders httpHeaders,@Context HttpServletRequest httpRequest) {
		
		var isAllowed = ProxyResourceFilter.isPathAllowed("", HttpMethod.DELETE, servicePath);
		
		if (!isAllowed) {
			return Response.status(404).build();
		}
		
		Map<String, Object> response = null;
		logger.info("Chiamata API : " + servicePath);
		try {

			response = proxyDispatcher.getDelete("DELETE", servicePath, uriInfo.getQueryParameters(), httpHeaders,httpRequest);

		} catch (Exception e) {
			logger.error(e);
			return Response.status(500).build();
		}
		logger.info("HTTP_STATUS : " + (int) response.get("RESPONSE_HTTP_STATUS"));
		return Response.status((int) response.get("RESPONSE_HTTP_STATUS"))
				.header("Content-Type", (String) response.get("RESPONSE_CONTENT_TYPE"))
				.header("Content-Disposition", (String) response.get("RESPONSE_CONTENT_DISPOSITION"))
				.header("Transfer-Encoding", (String) response.get("RESPONSE_TRANSFER-ENCODING"))
				.entity(response.get("RESPONSE_ENTITY")).build();
	}

}