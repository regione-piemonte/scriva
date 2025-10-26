/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

package it.csi.scriva.scrivaapisrv.business.be.helper;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.impl.AbstractApiServiceImpl;
import it.csi.scriva.scrivaapisrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * The type Abstract service helper.
 */
public abstract class AbstractServiceHelper extends AbstractApiServiceImpl {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(Constants.COMPONENT_NAME + ".service");

    /**
     * The Hostname.
     */
    protected String hostname = null;
    /**
     * The Endpoint base.
     */
    protected String endpointBase = null;

    /**
     * The Header attore gestione.
     */
    protected String headerAttoreGestione = null;

    /**
     * Gets hostname.
     *
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Sets hostname.
     *
     * @param hostname the hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Gets endpoint base.
     *
     * @return the endpoint base
     */
    public String getEndpointBase() {
        return endpointBase;
    }

    /**
     * Sets endpoint base.
     *
     * @param endpointBase the endpoint base
     */
    public void setEndpointBase(String endpointBase) {
        this.endpointBase = endpointBase;
    }

    /**
     * Gets header attore gestione.
     *
     * @return the header attore gestione
     */
    public String getHeaderAttoreGestione() {
        return headerAttoreGestione;
    }

    /**
     * Sets header attore gestione.
     *
     * @param headerAttoreGestione the header attore gestione
     */
    public void setHeaderAttoreGestione(String headerAttoreGestione) {
        this.headerAttoreGestione = headerAttoreGestione;
    }

    protected String getClassFunctionBeginInfo(String className, String methodName) {
        return getClassFunctionDebugString(className, methodName, "BEGIN");
    }

    protected String getClassFunctionEndInfo(String className, String methodName) {
        return getClassFunctionDebugString(className, methodName, "END");
    }

    protected String getClassFunctionErrorInfo(String className, String methodName, Object error) {
        return getClassFunctionDebugString(className, methodName, "ERROR : " + error);
    }

    protected String getClassFunctionDebugString(String className, String methodName, String info) {
        String functionIdentity = "[" + className + "::" + methodName + "] ";
        return functionIdentity + info;
    }

    /**
     * Gets invocation builder.
     *
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @return the invocation builder
     */
    public static Invocation.Builder getInvocationBuilder(String targetUrl, MultivaluedMap<String, Object> requestHeaders) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(targetUrl);
        return requestHeaders != null ? target.request().headers(requestHeaders) : target.request();
    }

    /**
     * Gets response.
     *
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param action         the action
     * @param entity         the entity
     * @return the response
     */
    public static Response getResponse(String targetUrl, MultivaluedMap<String, Object> requestHeaders, String action, Entity entity) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(targetUrl, requestHeaders);
        switch (action) {
            case "POST":
                return invocationBuilder.post(entity);
            case "PUT":
                return invocationBuilder.put(entity);
            case "DEL":
                return invocationBuilder.delete();
            default:
                return invocationBuilder.get();
        }
    }

    /**
     * Gets error.
     *
     * @param response the response
     * @return the error
     */
    public static ErrorDTO getError(Response response) {
        return getError(response, null);
    }

    /**
     * Gets error.
     *
     * @param response the response
     * @param errTitle the err title
     * @return the error
     */
    public static ErrorDTO getError(Response response, String errTitle) {
        ErrorDTO err = new ErrorDTO();
        GenericType<ErrorDTO> errGenericType = new GenericType<ErrorDTO>() {
        };
        try {
            err = response.readEntity(errGenericType);
            err.setTitle(StringUtils.isNotBlank(errTitle) ? errTitle + " " + err.getTitle() : err.getTitle());
        } catch (Exception e) {
            err.setStatus(String.valueOf(response.getStatus()));
            err.setCode(String.valueOf(response.getStatus()));
            err.setTitle(StringUtils.isNotBlank(errTitle) ? errTitle + " Errore inatteso dal server" : "Errore inatteso dal server");
        }
        return err;
    }

    /**
     * Sets get result.
     *
     * @param <T>            the type parameter
     * @param className      the classname
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param result         the result
     * @return the get result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> List<T> setGetResult(String className, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders, List<T> result) throws ProcessingException, GenericException {
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<T>>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        return result;
    }

    /**
     * Sets get result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param result         the result
     * @return the get result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> List<T> setGetResult(String className, String targetUrl, MultivaluedMap<String, Object> requestHeaders, List<T> result) throws ProcessingException, GenericException {
        return setGetResult(className, getMethodName(3), targetUrl, requestHeaders, result);
    }

    /**
     * Sets delete result.
     *
     * @param className      the classname
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @return the delete result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected boolean setDeleteResult(String className, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders) throws ProcessingException, GenericException {
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(className, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        return Boolean.TRUE;
    }

    /**
     * Sets save result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param result         the result
     * @return the save result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> T setSaveResult(String className, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, T result) throws ProcessingException, GenericException {
        return setSaveUpdateResult(className, methodName, Boolean.TRUE, targetUrl, requestHeaders, object, result);
    }

    /**
     * Sets save result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param type           the type
     * @return the save result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> T setSaveResult(String className, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, Class<T> type) throws ProcessingException, GenericException {
        return setSaveUpdateResult(className, methodName, Boolean.TRUE, targetUrl, requestHeaders, object, type);
    }

    /**
     * Sets update result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param result         the result
     * @return the update result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> T setUpdateResult(String className, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, T result) throws ProcessingException, GenericException {
        return setSaveUpdateResult(className, methodName, Boolean.FALSE, targetUrl, requestHeaders, object, result);
    }

    /**
     * Sets update result.
     *
     * @param <T>            the type parameter
     * @param CLASSNAME      the classname
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param type           the type
     * @return the update result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> T setUpdateResult(String CLASSNAME, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, Class<T> type) throws ProcessingException, GenericException {
        return setSaveUpdateResult(CLASSNAME, methodName, Boolean.FALSE, targetUrl, requestHeaders, object, type);
    }

    /**
     * Build query parameters string.
     *
     * @param queryParameters the query parameters
     * @param paramName       the param name
     * @param paramValue      the param value
     * @return the string
     */
    protected String buildQueryParameters(String queryParameters, String paramName, Object paramValue) {
        String value = paramValue != null ? String.valueOf(paramValue) : null;
        if (StringUtils.isNotBlank(value)) {
            queryParameters += StringUtils.isNotBlank(queryParameters) ? "&" + paramName + "=" + paramValue : "?" + paramName + "=" + paramValue;
        }
        return queryParameters;
    }

    /**
     * Sets save update result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param methodName     the method name
     * @param saveMethod     the save method
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param result         the result
     * @return the save update result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> T setSaveUpdateResult(String className, String methodName, Boolean saveMethod, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, T result) throws ProcessingException, GenericException {
        try {
            Entity<T> entity = object != null ? Entity.json(object) : null;
            Response resp = Boolean.TRUE.equals(saveMethod) ? getInvocationBuilder(targetUrl, requestHeaders).post(entity) : getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<T>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
    }

    /**
     * Sets save update result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param methodName     the method name
     * @param saveMethod     the save method
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param type           the type
     * @return the save update result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> T setSaveUpdateResult(String className, String methodName, Boolean saveMethod, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, Class<T> type) throws ProcessingException, GenericException {
        try {
            Entity<T> entity = object != null ? Entity.json(object) : null;

            Response resp = Boolean.TRUE.equals(saveMethod) ? getInvocationBuilder(targetUrl, requestHeaders).post(entity) : getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(type);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
    }

    /**
     * Sets save update list result.
     *
     * @param <T>            the type parameter
     * @param className      the classname
     * @param methodName     the method name
     * @param saveMethod     the save method
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param result         the result
     * @return the save update list result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> List<T> setSaveUpdateListResult(String className, String methodName, Boolean saveMethod, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, List<T> result) throws ProcessingException, GenericException {
        try {
            Entity<T> entity = object != null ? Entity.json(object) : null;
            Response resp = Boolean.TRUE.equals(saveMethod) ? getInvocationBuilder(targetUrl, requestHeaders).post(entity) : getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<List<T>>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
    }

    /**
     * Gets input params.
     *
     * @param path              the path
     * @param requestParameters the request parameters
     * @return the input params
     */
    protected String getInputParams(String path, MultivaluedMap<String, String> requestParameters) {
        return getInputParams(path, requestParameters, null);
    }

    /**
     * Gets input params.
     *
     * @param path              the path
     * @param requestParameters the request parameters
     * @param requestBody       the request body
     * @return the input params
     */
    protected String getInputParams(String path, MultivaluedMap<String, String> requestParameters, Map<String, Object> requestBody) {
        final StringBuilder sb = new StringBuilder("Parametri in input \n");
        sb.append("path [").append(path).append("]\n");
        if (requestParameters != null && !requestParameters.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : requestParameters.entrySet()) {
                sb.append(entry.getKey()).append(" [").append(entry.getValue().get(0)).append("]\n");
            }
        }
        if (requestBody != null && !requestBody.isEmpty()) {
            for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
                sb.append(entry.getKey()).append(" [").append(entry.getValue()).append("]\n");
            }
        }
        return sb.toString();
    }

}