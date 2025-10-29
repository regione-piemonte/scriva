/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.impl.AbstractApiServiceImpl;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * The type Abstract service helper.
 */
public abstract class AbstractServiceHelper extends AbstractApiServiceImpl {

    /**
     * The constant LOGGER.
     */
    public static final String SERVER_EXCEPTION = "SERVER EXCEPTION : ";
    /**
     * The constant ERRORE_NELLA_CHIAMATA_AL_SERVIZIO.
     */
    public static final String ERRORE_NELLA_CHIAMATA_AL_SERVIZIO = "Errore nella chiamata al servizio [ ";

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
     * The Header profili app.
     */
    protected String headerProfiliApp = null;

    /**
     * The Header tipo adempimento ogg app.
     */
    protected String headerTipoAdempimentoOggApp = null;

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

    /**
     * Gets header profili app.
     *
     * @return the header profili app
     */
    public String getHeaderProfiliApp() {
        return headerProfiliApp;
    }

    /**
     * Sets header profili app.
     *
     * @param headerProfiliApp the header profili app
     */
    public void setHeaderProfiliApp(String headerProfiliApp) {
        this.headerProfiliApp = headerProfiliApp;
    }

    /**
     * Gets header tipo adempimento ogg app.
     *
     * @return the header tipo adempimento ogg app
     */
    public String getHeaderTipoAdempimentoOggApp() {
        return headerTipoAdempimentoOggApp;
    }

    /**
     * Sets header tipo adempimento ogg app.
     *
     * @param headerTipoAdempimentoOggApp the header tipo adempimento ogg app
     */
    public void setHeaderTipoAdempimentoOggApp(String headerTipoAdempimentoOggApp) {
        this.headerTipoAdempimentoOggApp = headerTipoAdempimentoOggApp;
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
     * Gets invocation builder.
     *
     * @param targetUrl       the target url
     * @param requestHeaders  the request headers
     * @param queryParameters the query parameters
     * @return the invocation builder
     */
    public static Invocation.Builder getInvocationBuilder(String targetUrl, MultivaluedMap<String, Object> requestHeaders, MultivaluedMap<String, String> queryParameters) {
        Client client = ClientBuilder.newBuilder().build();
        String url = targetUrl;
        if (queryParameters != null && !queryParameters.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : queryParameters.entrySet()) {
                url = url.equalsIgnoreCase(targetUrl) ? url + "?" + entry.getKey() + "=" + entry.getValue().get(0) : url + "&" + entry.getKey() + "=" + entry.getValue().get(0);
            }
        }
        WebTarget target = client.target(url);
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
        ErrorDTO err = new ErrorDTO();
        GenericType<ErrorDTO> errGenericType = new GenericType<ErrorDTO>() {
        };
        try {
            err = response.readEntity(errGenericType);
        } catch (Exception e) {
            err.setStatus(String.valueOf(response.getStatus()));
            err.setCode(String.valueOf(response.getStatus()));
            err.setTitle("Errore inatteso dal server");
        }
        return err;
    }

    /**
     * Sets get result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
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
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<List<T>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
    }

    /**
     * Sets get result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @return the get result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> List<T> setGetResult(String className, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders) throws ProcessingException, GenericException {
        List<T> result;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<T>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
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
     * Sets get result.
     *
     * @param <T>            the type parameter
     * @param className      the class name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @return the get result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> List<T> setGetResult(String className, String targetUrl, MultivaluedMap<String, Object> requestHeaders) throws ProcessingException, GenericException {
        return setGetResult(className, getMethodName(3), targetUrl, requestHeaders);
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
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        return Boolean.TRUE;
    }

    /**
     * Sets delete result.
     *
     * @param className      the class name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @return the delete result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected boolean setDeleteResult(String className, String targetUrl, MultivaluedMap<String, Object> requestHeaders) throws ProcessingException, GenericException {
        return setDeleteResult(className, getMethodName(3), targetUrl, requestHeaders);
    }

    /**
     * Sets save result.
     *
     * @param <T>            the type parameter
     * @param className      the classname
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
     * @param className      the classname
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
     * @param className      the classname
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
     * @param className      the classname
     * @param methodName     the method name
     * @param targetUrl      the target url
     * @param requestHeaders the request headers
     * @param object         the object
     * @param type           the type
     * @return the update result
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    protected <T> T setUpdateResult(String className, String methodName, String targetUrl, MultivaluedMap<String, Object> requestHeaders, T object, Class<T> type) throws ProcessingException, GenericException {
        return setSaveUpdateResult(className, methodName, Boolean.FALSE, targetUrl, requestHeaders, object, type);
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
     * @param className      the classname
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
                logger.error(getClassFunctionErrorInfo(className, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<T>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        //return result;
    }

    /**
     * Sets save update result.
     *
     * @param <T>            the type parameter
     * @param className      the classname
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
                logger.error(getClassFunctionErrorInfo(className, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(type);
            //return resp.readEntity(new GenericType<T>() {
            //});
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        }
        //return result;
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
                logger.error(getClassFunctionErrorInfo(className, methodName, err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<List<T>>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
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
    public URIBuilder getURIBuilder(String url, MultivaluedMap<String, String> requestParameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (requestParameters != null && !requestParameters.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : requestParameters.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue().get(0));
            }
        }
        return uriBuilder;
    }

}