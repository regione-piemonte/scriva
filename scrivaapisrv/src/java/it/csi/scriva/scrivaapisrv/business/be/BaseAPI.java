/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be;


import it.csi.scriva.scrivaapisrv.business.be.common.ConfigurationHelper;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * The gestione quadri BO services.
 */
@SuppressWarnings("unchecked")
public abstract class BaseAPI<T extends Object> {

    private final Class<T> classAPI = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    protected Logger logger = Logger.getLogger(classAPI);

    /**
     * The Configuration helper.
     */
    @Autowired
    public ConfigurationHelper configurationHelper;

    /*
     * generic API call made via REASTEASY
     */
    public Response callAPI(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String method2Invoke, Object... params) throws Exception {
        if (method2Invoke == null)
            method2Invoke = Thread.currentThread().getStackTrace()[2].getMethodName();

        logger.debug("[" + getClass().getSimpleName() + "::" + method2Invoke + "] BEGIN");
        //		UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);

        try {
            Client client = ClientBuilder.newClient();
            client.register(new AddDefaultHeadersRequestFilter(userCf, XRequestId, XForwardedFor));

            WebTarget target = client.target(configurationHelper.getEndpointBase());
            ResteasyWebTarget rtarget = (ResteasyWebTarget) target;

            T api = (T) rtarget.proxy(classAPI);

            Method method = null;
            for (Method m : api.getClass().getDeclaredMethods())
                if (m.getName().equals(method2Invoke))
                    method = m;

            Object[] pars = new Object[]{ /*userCf != null? userCf : userInfo.getCodFisc(),
            		XRequestId != null? XRequestId : (String) MDC.get(Constants.X_REQUEST_ID),
            		XForwardedFor != null ? XForwardedFor : (String) MDC.get(Constants.X_FORWARDER_FOR), 
					securityContext, httpHeaders, httpRequest */};

            // appends params, if there are any
            if (params != null && params.length > 0) {
                Object[] newArr = new Object[pars.length + params.length];

                System.arraycopy(pars, 0, newArr, 0, pars.length);
                System.arraycopy(params, 0, newArr, pars.length, params.length);

                pars = newArr;
            }

            Response response = (Response) method.invoke(api, pars);

            logger.debug("[" + getClass().getSimpleName() + "::" + method2Invoke + "] END");
            return response;
        } catch (Throwable t) {
            logger.debug("[" + this.getClass().getSimpleName() + "::" + method2Invoke + "] EXCEPTION : " + t.getMessage());
            //			throw new RemoteException();
			throw new Exception(t.getMessage());
        }
    }

    /*
     * generic multipart API call made via REASTEASY
     */
    public Response callMultipartAPI(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, String path, String stringObjectParamName, // object name to extracted from multipart to be passed as string
                                     MultipartFormDataInput input) throws Exception {
        String method2Invoke = Thread.currentThread().getStackTrace()[2].getMethodName();
        logger.debug("[" + getClass().getSimpleName() + "::" + method2Invoke + "] BEGIN");

        // httpRequest.setAttribute(InputPart.DEFAULT_CHARSET_PROPERTY, "charset=UTF-8");
        // httpRequest.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY, "*/*; charset=UTF-8");

        try {
            //	        UserInfo userInfo = HeaderUtil.getUserInfo(httpRequest);

            Client client = ClientBuilder.newClient();
            client.register(new AddDefaultHeadersRequestFilter(userCf, XRequestId, XForwardedFor));

            WebTarget target = client.target(configurationHelper.getEndpointBase() + path);

            MultipartFormDataOutput output = new MultipartFormDataOutput();
            if (input.getFormDataMap().get("file") != null) {
                InputPart inputPart = input.getFormDataMap().get("file").get(0);
                String filename = inputPart.getHeaders().getFirst("Content-Disposition");

                // recupero valori nel form di input
                File file = input.getFormDataPart("file", File.class, null);
                OutputPart objPart = output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
                objPart.getHeaders().putSingle("Content-Disposition", "form-data; name=file; filename=" + filename);
            }

            //not working with utf8: String objectAsString = new String(input.getFormDataPart(stringObjectParamName, String.class, null).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            InputPart jsonPart = input.getFormDataMap().get(stringObjectParamName).get(0);
            jsonPart.setMediaType(MediaType.APPLICATION_JSON_TYPE);
            String objectAsString = jsonPart.getBody(String.class, null);

            output.addFormData(stringObjectParamName, objectAsString, MediaType.TEXT_PLAIN_TYPE);

            MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
            //	        headers.add("user-cf", userInfo.getCodFisc());
            headers.add("X-Request-ID", XRequestId);
            headers.add("X-Forwarded-For", XForwardedFor);

            Response response = target.request().headers(headers).post(Entity.entity(output, MediaType.MULTIPART_FORM_DATA));

            logger.debug("[" + getClass().getSimpleName() + "::" + method2Invoke + "] END");
            return response;
        } catch (Throwable t) {
            logger.debug("[" + this.getClass().getSimpleName() + "::" + method2Invoke + "] EXCEPTION : " + t.getMessage());
            //			throw new RemoteException();
            throw new Exception(t.getMessage());
        }
    }

    public static class AddDefaultHeadersRequestFilter implements ClientRequestFilter {
        private final String userCf;
        private final String XRequestId;
        private final String XForwardedFor;

        public AddDefaultHeadersRequestFilter(String userCf, String XRequestId, String XForwardedFor) {
            this.userCf = userCf;
            this.XRequestId = XRequestId;
            this.XForwardedFor = XForwardedFor;
        }

        @Override
        public void filter(ClientRequestContext requestContext) throws IOException {
            if (XRequestId != null)
                requestContext.getHeaders().add("", XRequestId.substring(0, XRequestId.indexOf('~')));
        }
    }
}