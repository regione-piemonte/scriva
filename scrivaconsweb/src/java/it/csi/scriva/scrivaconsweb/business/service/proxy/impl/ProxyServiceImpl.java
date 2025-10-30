/*-
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaconsweb.business.service.proxy.impl;

import java.io.File;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import it.csi.scriva.scrivaconsweb.business.service.exception.ProxyRequestException;
import it.csi.scriva.scrivaconsweb.business.service.proxy.ProxyService;
import it.csi.scriva.scrivaconsweb.business.service.util.ScrivaConsWebHttpClientProxyService;
import it.csi.scriva.scrivaconsweb.dto.UserInfo;
import it.csi.scriva.scrivaconsweb.dto.XRequestAuth;
import it.csi.scriva.scrivaconsweb.filter.IrideIdAdapterFilter;

@Service
public class ProxyServiceImpl implements ProxyService {

	// private static final Logger logger =
	// LoggerFactory.getLogger(Constants.SERVICE_PROXY);

	private static final Logger logger = LoggerFactory.getLogger(ProxyServiceImpl.class);

	private static final String HEADER_ATTORE_SCRIVA = "Attore-Scriva";
	private static final String HEADER_X_REQUEST_AUTH = "X-Request-Auth";
	private static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";




	@Autowired
	@Qualifier("proxyHttpsClient")
	private CloseableHttpClient proxyHttpsClient;

	@Autowired
	private Environment env;

	@Override
	public Map<String, Object> getResponse(String servicePath, MultivaluedMap<String, String> requestParameters,
			HttpHeaders httpHeader, HttpServletRequest httpRequest) throws ProxyRequestException {
		Map<String, Object> response = new HashMap<>();

		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);

		// gestione system
		String url = env.getProperty("scrivaconsweb-proxy.proxy.base.url", String.class);

		String PubOss = env.getProperty("scrivaconsweb-proxy.PubOss", String.class);
		String passwordOss = env.getProperty("scrivaconsweb-proxy.passwordOss", String.class);
		String Pubread = env.getProperty("scrivaconsweb-proxy.Pubread", String.class);
		String passwordRead = env.getProperty("scrivaconsweb-proxy.passwordRead", String.class);

		/*
		 * int connectionTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.connection.timeout.millis",
		 * Integer.class); int socketTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.socket.timeout.millis",
		 * Integer.class);
		 */

		try {
			// gestione servicePath
			URIBuilder uriBuilder = new URIBuilder(url + servicePath);

			if (requestParameters != null && !requestParameters.isEmpty()) {
				for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
				}
			}

			UserInfo userInfo = (UserInfo) httpRequest.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			XRequestAuth appo = new XRequestAuth();
			appo.setComponenteApplicativa("PUBWEB");

			if (userInfo != null) {
				appo.setPassword(passwordOss);
				appo.setRuolo(PubOss);
				appo.setUsername(userInfo.getCodFisc());
			} else {
				appo.setPassword(passwordRead);
				appo.setRuolo(Pubread);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOperationContext = objectMapper.writeValueAsString(appo);
			String val = new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));

			HttpRequestBase request = null;
			request = new HttpGet(uriBuilder.build());

			request.addHeader(HEADER_X_REQUEST_AUTH, val);

			logger.info("HEADER_X_REQUEST_AUTH " + val);
			
			
			/*
			 * if (httpHeader != null) { MultivaluedMap<String, String> requestHeaders =
			 * httpHeader.getRequestHeaders(); List<String> headerXRequestAuthList =
			 * requestHeaders.get(HEADER_X_REQUEST_AUTH); List<String>
			 * headerAttoreIstanzaList = requestHeaders.get(HEADER_ATTORE_SCRIVA); String
			 * headerXRequestAuth = headerXRequestAuthList != null &&
			 * !headerXRequestAuthList.isEmpty() ? headerXRequestAuthList.get(0) : null;
			 * String headerAttoreIstanza = headerAttoreIstanzaList != null &&
			 * !headerAttoreIstanzaList.isEmpty() ? headerAttoreIstanzaList.get(0) : null;
			 * 
			 * String headerXRequestAuthAppoggio =
			 * "ewoiY29tcG9uZW50ZUFwcGxpY2F0aXZhIiA6ICJQVUJXRUIiLAoicnVvbG8iIDogIlNDUklWQVBVQl9PU1MiLAoidXNlcm5hbWUiIDogIkdSU05UTjgwQTAxQjM1NFciLAoicGFzc3dvcmQiIDogImM5NmFkZmMyOGYxZGRmNGFhN2U3Y2ZmYmM5YjIzZTBhIgp9";
			 * 
			 * request.addHeader(HEADER_X_REQUEST_AUTH, val);
			 * 
			 * request.addHeader(HEADER_ATTORE_SCRIVA, headerAttoreIstanza);
			 * 
			 * }
			 */

			logger.info("Path richiamato dal proxy " + request);

			CloseableHttpResponse resp = proxyHttpsClient.execute(request);
			response.put("RESPONSE_HTTP_STATUS", resp.getStatusLine().getStatusCode());

			Header[] contentDispositionHeader = resp.getHeaders("Content-Disposition");

			Header[] paginationInfoHeader = resp.getHeaders("PaginationInfo");

			Header[] contatoreHeader = resp.getHeaders("Counter");

			if (resp.getEntity() != null) {
				if (resp.getEntity().getContentType() != null) {
					response.put("RESPONSE_CONTENT_TYPE", resp.getEntity().getContentType().getValue());
				}

				// response.put("RESPONSE_ENTITY", EntityUtils.toString(resp.getEntity()));

				response.put("RESPONSE_ENTITY", resp.getEntity().getContent());
			}

			if (contentDispositionHeader != null && contentDispositionHeader.length > 0) {
				response.put("RESPONSE_CONTENT_DISPOSITION", contentDispositionHeader[0].getValue());
			}

			if (paginationInfoHeader != null && paginationInfoHeader.length > 0) {
				response.put("RESPONSE_PAGINATION_INFO", paginationInfoHeader[0].getValue());
			}

			if (contatoreHeader != null && contatoreHeader.length > 0) {
				response.put("RESPONSE_CONTATORE_INFO", contatoreHeader[0].getValue());
			}

		} catch (Throwable t) {
			logger.error("Errore interno nel proxy per getResponse: " + t, t);
			throw new ProxyRequestException("Errore interno nel proxy per getResponse: " + t, t);
		}
		return response;
	}

	@Override
	public Map<String, Object> getResponsePost(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, Map<String, Object> requestBody, String headerParam,
			HttpServletRequest httpRequest) throws ProxyRequestException {

		String PubOss = env.getProperty("scrivaconsweb-proxy.PubOss", String.class);
		String passwordOss = env.getProperty("scrivaconsweb-proxy.passwordOss", String.class);
		String Pubread = env.getProperty("scrivaconsweb-proxy.Pubread", String.class);
		String passwordRead = env.getProperty("scrivaconsweb-proxy.passwordRead", String.class);

		Map<String, Object> response = new HashMap<>();
		
		if (logger.isDebugEnabled())
			logger.debug("getResponsePost() proxy: pathService => " + servicePath);

		// gestione system
		String url = env.getProperty("scrivaconsweb-proxy.proxy.base.url", String.class);
		/*
		 * int connectionTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.connection.timeout.millis",
		 * Integer.class); int socketTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.socket.timeout.millis",
		 * Integer.class);
		 */

		try {
			// gestione servicePath
			URIBuilder uriBuilder = new URIBuilder(url + servicePath);

			if (requestParameters != null && !requestParameters.isEmpty()) {
				for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
				}
			}
			JSONObject jsonObject = new JSONObject(requestBody);

			HttpPost request = new HttpPost(uriBuilder.build());
			

			StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
			request.setEntity(entity);

			UserInfo userInfo = (UserInfo) httpRequest.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			XRequestAuth appo = new XRequestAuth();
			appo.setComponenteApplicativa("PUBWEB");

			if (userInfo != null) {
				appo.setPassword(passwordOss);
				appo.setRuolo(PubOss);
				appo.setUsername(userInfo.getCodFisc());
			} else {
				appo.setPassword(passwordRead);
				appo.setRuolo(Pubread);
			}
			
			//logger.info("getResponsePost(): appo: " + appo);
			//logger.info("getResponsePost(): userInfo" + userInfo);


			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOperationContext = objectMapper.writeValueAsString(appo);
			String val = new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));

			request.addHeader("Attore-Scriva", headerParam);
			request.addHeader(HEADER_X_REQUEST_AUTH, val);
			

			request.addHeader("content-type", "application/json");

			
			/*
			 * RequestConfig config = RequestConfig.custom()
  					.setConnectTimeout(timeout * 1000)
  					.setConnectionRequestTimeout(timeout * 1000)
  					.setSocketTimeout(timeout * 1000).build();
					CloseableHttpClient client =  HttpClientBuilder.create().setDefaultRequestConfig(config).build();
			 */

			logger.info("getResponsePost() Path richiamato dal proxy " + request);
			
			ScrivaConsWebHttpClientProxyService scrivaConsWebHttpClientProxyService= new ScrivaConsWebHttpClientProxyService(url);
			//ScrivaBeProxyServiceHelper scrivaBeProxyServiceHelper = new ScrivaBeProxyServiceHelper(url);
			
			MultivaluedMap<String, Object> headerParams = new MultivaluedHashMap<>();
			headerParams.add(HEADER_ATTORE_SCRIVA, headerParam);
			headerParams.add(AUTH_ID_MARKER , httpRequest.getHeader(AUTH_ID_MARKER));
			headerParams.add(HEADER_X_REQUEST_AUTH, val);
			// lo setta all'interno 
			//headerParams.add("content-type", "application/json");
			
			//logger.info("HEADER_X_REQUEST_AUTH " + val);
			//logger.info("Attore-Scriva" + headerParam);

			//chiamata new
			response = scrivaConsWebHttpClientProxyService.getMapResponsePOST(servicePath, requestParameters, headerParams, requestBody);
			
			/* chiamata old
			 *  
			 
			CloseableHttpResponse resp = proxyHttpsClient.execute(request);

			response.put("RESPONSE_HTTP_STATUS", resp.getStatusLine().getStatusCode());

			Header[] contatoreHeader = resp.getHeaders("Counter");
			Header[] paginationInfoHeader = resp.getHeaders("PaginationInfo");
			
			if (resp.getEntity() != null) {
				if (resp.getEntity().getContentType() != null) {
					logger.info("getResponsePost() RESPONSE_CONTENT_TYPE " + resp.getEntity().getContentType().getValue());
					response.put("RESPONSE_CONTENT_TYPE", resp.getEntity().getContentType().getValue());
				}
				logger.info("getResponsePost() RESPONSE_ENTITY " + resp.getEntity());
				response.put("RESPONSE_ENTITY", EntityUtils.toString(resp.getEntity()));
			}
			
			if (contatoreHeader != null && contatoreHeader.length > 0) {
				logger.info("getResponsePost() RESPONSE_CONTATORE_INFO " + contatoreHeader[0].getValue());
				response.put("RESPONSE_CONTATORE_INFO", contatoreHeader[0].getValue());
			}
			
			if (paginationInfoHeader != null && paginationInfoHeader.length > 0) {
				logger.info("getResponsePost() RESPONSE_PAGINATION_INFO " + paginationInfoHeader[0].getValue());
				response.put("RESPONSE_PAGINATION_INFO", paginationInfoHeader[0].getValue());
			}
			
			Header[] contentDisposition = resp.getHeaders(Constants.HEADER_CONTENT_DISPOSITION);
			if (contentDisposition!=null && contentDisposition.length > 0) {
				response.put("RESPONSE_CONTENT_DISPOSITION", contentDisposition[0].getValue());
			}
    
			resp.close();
			*/
			
		} catch (Throwable t) {
			logger.error("Errore interno nel proxy per getResponse: " + t, t);
			throw new ProxyRequestException("Errore interno nel proxy per getResponse: " + t, t);
		}
		
		return response;
	}
	
	@Override
	public Response getResponseFilePost(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, Map<String, Object> requestBody, String headerParam,
			HttpServletRequest httpRequest) throws ProxyRequestException {

		String PubOss = env.getProperty("scrivaconsweb-proxy.PubOss", String.class);
		String passwordOss = env.getProperty("scrivaconsweb-proxy.passwordOss", String.class);
		String Pubread = env.getProperty("scrivaconsweb-proxy.Pubread", String.class);
		String passwordRead = env.getProperty("scrivaconsweb-proxy.passwordRead", String.class);

		Response response = null;

		if (logger.isDebugEnabled())
			logger.debug("getResponseFilePost() proxy: pathService => " + servicePath);

		// gestione system
		String url = env.getProperty("scrivaconsweb-proxy.proxy.base.url", String.class);

		try {
			// gestione servicePath
			URIBuilder uriBuilder = new URIBuilder(url + servicePath);

			if (requestParameters != null && !requestParameters.isEmpty()) {
				for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
				}
			}
			JSONObject jsonObject = new JSONObject(requestBody);

			HttpPost request = new HttpPost(uriBuilder.build());
			

			StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
			request.setEntity(entity);

			UserInfo userInfo = (UserInfo) httpRequest.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			XRequestAuth appo = new XRequestAuth();
			appo.setComponenteApplicativa("PUBWEB");

			if (userInfo != null) {
				appo.setPassword(passwordOss);
				appo.setRuolo(PubOss);
				appo.setUsername(userInfo.getCodFisc());
			} else {
				appo.setPassword(passwordRead);
				appo.setRuolo(Pubread);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOperationContext = objectMapper.writeValueAsString(appo);
			String val = new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));

			request.addHeader("Attore-Scriva", headerParam);
			request.addHeader(HEADER_X_REQUEST_AUTH, val);
			
			request.addHeader("content-type", "application/json");

			logger.info("getResponsePost() Path richiamato dal proxy " + request);
			
			ScrivaConsWebHttpClientProxyService scrivaConsWebHttpClientProxyService= new ScrivaConsWebHttpClientProxyService(url);
			
			MultivaluedMap<String, Object> headerParams = new MultivaluedHashMap<>();
			headerParams.add(HEADER_ATTORE_SCRIVA, headerParam);
			headerParams.add(AUTH_ID_MARKER , httpRequest.getHeader(AUTH_ID_MARKER));
			headerParams.add(HEADER_X_REQUEST_AUTH, val);
			
			response = scrivaConsWebHttpClientProxyService.getResponseFilePOST(servicePath, requestParameters, headerParams, requestBody);
			
		} catch (Throwable t) {
			logger.error("Errore interno nel proxy per getResponse: " + t, t);
			throw new ProxyRequestException("Errore interno nel proxy per getResponse: " + t, t);
		}
		
		return response;
	}

	@Override
	public Map<String, Object> getResponsePut(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, Map<String, Object> requestBody, String headerParam,
			HttpServletRequest httpRequest) throws ProxyRequestException {

		String PubOss = env.getProperty("scrivaconsweb-proxy.PubOss", String.class);
		String passwordOss = env.getProperty("scrivaconsweb-proxy.passwordOss", String.class);
		String Pubread = env.getProperty("scrivaconsweb-proxy.Pubread", String.class);
		String passwordRead = env.getProperty("scrivaconsweb-proxy.passwordRead", String.class);

		Map<String, Object> response = new HashMap<>();

		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);

		// gestione system
		String url = env.getProperty("scrivaconsweb-proxy.proxy.base.url", String.class);
		/*
		 * int connectionTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.connection.timeout.millis",
		 * Integer.class); int socketTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.socket.timeout.millis",
		 * Integer.class);
		 */

		try {
			// gestione servicePath
			URIBuilder uriBuilder = new URIBuilder(url + servicePath);

			if (requestParameters != null && !requestParameters.isEmpty()) {
				for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
				}
			}

			JSONObject jsonObject = new JSONObject(requestBody);

			HttpPut request = new HttpPut(uriBuilder.build());

			StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
			request.setEntity(entity);

			UserInfo userInfo = (UserInfo) httpRequest.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			XRequestAuth appo = new XRequestAuth();
			appo.setComponenteApplicativa("PUBWEB");

			if (userInfo != null) {
				appo.setPassword(passwordOss);
				appo.setRuolo(PubOss);
				appo.setUsername(userInfo.getCodFisc());
			} else {
				appo.setPassword(passwordRead);
				appo.setRuolo(Pubread);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOperationContext = objectMapper.writeValueAsString(appo);
			String val = new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));

			request.addHeader("Attore-Scriva", headerParam);
			request.addHeader(HEADER_X_REQUEST_AUTH, val);

			request.addHeader("content-type", "application/json");

			logger.info("Path richiamato dal proxy " + request);
			CloseableHttpResponse resp = proxyHttpsClient.execute(request);
			
			//TODO: da implementare al posto CloseableHttpResponse
			//ScrivaConsWebHttpClientProxyService scrivaConsWebHttpClientProxyService= new ScrivaConsWebHttpClientProxyService(url);
			//response=scrivaConsWebHttpClientProxyService.getMapResponseGET(servicePath, requestParameters, headerParams)
			
			response.put("RESPONSE_HTTP_STATUS", resp.getStatusLine().getStatusCode());

			if (resp.getEntity() != null) {
				if (resp.getEntity().getContentType() != null) {
					response.put("RESPONSE_CONTENT_TYPE", resp.getEntity().getContentType().getValue());
				}

				response.put("RESPONSE_ENTITY", EntityUtils.toString(resp.getEntity()));
			}

		} catch (Throwable t) {
			logger.error("Errore interno nel proxy per getResponse: " + t, t);
			throw new ProxyRequestException("Errore interno nel proxy per getResponse: " + t, t);
		}
		return response;
	}

	@Override
	public Map<String, Object> getResponseMutliPart(String verb, String servicePath,
			MultivaluedMap<String, String> requestParameters, MultipartFormDataInput formDataInput, String headerParam,
			HttpServletRequest httpRequest) throws ProxyRequestException {

		String PubOss = env.getProperty("scrivaconsweb-proxy.PubOss", String.class);
		String passwordOss = env.getProperty("scrivaconsweb-proxy.passwordOss", String.class);
		String Pubread = env.getProperty("scrivaconsweb-proxy.Pubread", String.class);
		String passwordRead = env.getProperty("scrivaconsweb-proxy.passwordRead", String.class);
		Map<String, Object> response = new HashMap<>();

		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);
		String url = env.getProperty("scrivaconsweb-proxy.proxy.base.url", String.class);
		/*
		 * int connectionTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.connection.timeout.millis",
		 * Integer.class); int socketTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.socket.timeout.millis",
		 * Integer.class);
		 */

		try {
			// gestione servicePath
			URIBuilder uriBuilder = new URIBuilder(url + servicePath);

			if (requestParameters != null && !requestParameters.isEmpty()) {
				for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
				}
			}

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			String allegatoIstanza = formDataInput.getFormDataPart("allegatoIstanza", String.class, null);
			builder.addTextBody("allegatoIstanza", allegatoIstanza, ContentType.TEXT_PLAIN);

			String fileName = headerParam.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
			// This attaches the file to the POST:
			File file = formDataInput.getFormDataPart("file", File.class, null);
			builder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, fileName);

			HttpEntity multipart = builder.build();

			HttpPost request = new HttpPost(uriBuilder.build());

			request.setEntity(multipart);

			// request.addHeader("Attore-Scriva", headerParam);

			UserInfo userInfo = (UserInfo) httpRequest.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			XRequestAuth appo = new XRequestAuth();
			appo.setComponenteApplicativa("PUBWEB");

			if (userInfo != null) {
				appo.setPassword(passwordOss);
				appo.setRuolo(PubOss);
				appo.setUsername(userInfo.getCodFisc());
			} else {
				appo.setPassword(passwordRead);
				appo.setRuolo(Pubread);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOperationContext = objectMapper.writeValueAsString(appo);
			String val = new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));

			// request.addHeader("content-type", "application/json");
			// request.addHeader("content-type", "multipart/form-data");

			request.addHeader(HEADER_X_REQUEST_AUTH, val);

			logger.info("Path richiamato dal proxy " + request);
			CloseableHttpResponse resp = proxyHttpsClient.execute(request);
			response.put("RESPONSE_HTTP_STATUS", resp.getStatusLine().getStatusCode());

			if (resp.getEntity() != null) {
				if (resp.getEntity().getContentType() != null) {
					response.put("RESPONSE_CONTENT_TYPE", resp.getEntity().getContentType().getValue());
				}

				response.put("RESPONSE_ENTITY", EntityUtils.toString(resp.getEntity()));
			}

		} catch (Throwable t) {
			logger.error("Errore interno nel proxy per getResponse: " + t, t);
			throw new ProxyRequestException("Errore interno nel proxy per getResponse: " + t, t);
		}
		return response;
	}

	@Override
	public Map<String, Object> getDelete(String servicePath, MultivaluedMap<String, String> requestParameters,
			HttpHeaders httpHeader, HttpServletRequest httpRequest) throws ProxyRequestException {

		String PubOss = env.getProperty("scrivaconsweb-proxy.PubOss", String.class);
		String passwordOss = env.getProperty("scrivaconsweb-proxy.passwordOss", String.class);
		String Pubread = env.getProperty("scrivaconsweb-proxy.Pubread", String.class);
		String passwordRead = env.getProperty("scrivaconsweb-proxy.passwordRead", String.class);

		Map<String, Object> response = new HashMap<>();

		if (logger.isDebugEnabled())
			logger.debug("proxy: pathService => " + servicePath);

		// gestione system
		String url = env.getProperty("scrivaconsweb-proxy.proxy.base.url", String.class);
		/*
		 * int connectionTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.connection.timeout.millis",
		 * Integer.class); int socketTimeout =
		 * env.getProperty("scrivaconsweb-proxy.proxy.socket.timeout.millis",
		 * Integer.class);
		 */

		try {
			// gestione servicePath
			URIBuilder uriBuilder = new URIBuilder(url + servicePath);

			if (requestParameters != null && !requestParameters.isEmpty()) {
				for (Entry<String, List<String>> entry : requestParameters.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
				}
			}

			HttpRequestBase request = null;
			request = new HttpDelete(uriBuilder.build());

			UserInfo userInfo = (UserInfo) httpRequest.getSession()
					.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
			XRequestAuth appo = new XRequestAuth();
			appo.setComponenteApplicativa("PUBWEB");

			if (userInfo != null) {
				appo.setPassword(passwordOss);
				appo.setRuolo(PubOss);
				appo.setUsername(userInfo.getCodFisc());
			} else {
				appo.setPassword(passwordRead);
				appo.setRuolo(Pubread);
			}

			ObjectMapper objectMapper = new ObjectMapper();
			String jsonOperationContext = objectMapper.writeValueAsString(appo);
			String val = new String(Base64.getEncoder().encode(jsonOperationContext.getBytes()));

			request.addHeader(HEADER_X_REQUEST_AUTH, val);

			/*
			 * if (httpHeader != null) { MultivaluedMap<String, String> requestHeaders =
			 * httpHeader.getRequestHeaders(); List<String> headerXRequestAuthList =
			 * requestHeaders.get(HEADER_X_REQUEST_AUTH); List<String>
			 * headerAttoreIstanzaList = requestHeaders.get(HEADER_ATTORE_SCRIVA); String
			 * headerXRequestAuth = headerXRequestAuthList != null &&
			 * !headerXRequestAuthList.isEmpty() ? headerXRequestAuthList.get(0) : null;
			 * String headerAttoreIstanza = headerAttoreIstanzaList != null &&
			 * !headerAttoreIstanzaList.isEmpty() ? headerAttoreIstanzaList.get(0) : null;
			 * 
			 * String headerXRequestAuthAppoggio =
			 * "ewoiY29tcG9uZW50ZUFwcGxpY2F0aXZhIiA6ICJQVUJXRUIiLAoicnVvbG8iIDogIlNDUklWQVBVQl9PU1MiLAoidXNlcm5hbWUiIDogIkdSU05UTjgwQTAxQjM1NFciLAoicGFzc3dvcmQiIDogImM5NmFkZmMyOGYxZGRmNGFhN2U3Y2ZmYmM5YjIzZTBhIgp9";
			 * 
			 * request.addHeader(HEADER_X_REQUEST_AUTH, headerXRequestAuthAppoggio);
			 * 
			 * request.addHeader(HEADER_ATTORE_SCRIVA, headerAttoreIstanza);
			 * 
			 * }
			 */

			logger.info("Path richiamato dal proxy " + request);

			CloseableHttpResponse resp = proxyHttpsClient.execute(request);
			response.put("RESPONSE_HTTP_STATUS", resp.getStatusLine().getStatusCode());

			if (resp.getEntity() != null) {
				if (resp.getEntity().getContentType() != null) {
					response.put("RESPONSE_CONTENT_TYPE", resp.getEntity().getContentType().getValue());
				}
				response.put("RESPONSE_ENTITY", resp.getEntity().getContent());
			}

		} catch (Throwable t) {
			logger.error("Errore interno nel proxy per getResponse: " + t, t);
			throw new ProxyRequestException("Errore interno nel proxy per getResponse: " + t, t);
		}
		return response;
	}

}
