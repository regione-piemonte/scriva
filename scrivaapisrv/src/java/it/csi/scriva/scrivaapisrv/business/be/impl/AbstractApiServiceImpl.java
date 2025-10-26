/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.iride2.policy.entity.Identita;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.XRequestAuth;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;
import it.csi.scriva.scrivaapisrv.filter.IrideIdAdapterFilter;
import it.csi.scriva.scrivaapisrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.jboss.resteasy.util.Base64;
import org.json.simple.JSONObject;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The type Abstract api service.
 */
public abstract class AbstractApiServiceImpl {

    private final String className = this.getClass().getSimpleName();

    /**
     * The constant LOGGER.
     */
    protected static Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".business");

    /**
     * The constant AUTH_ID_MARKER.
     */
    protected static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";

    /**
     * The constant HEADER_ATTORE_SCRIVA.
     */
    protected static final String HEADER_ATTORE_SCRIVA = "Attore-Scriva";
    /**
     * The constant HEADER_X_REQUEST_AUTH.
     */
    protected static final String HEADER_X_REQUEST_AUTH = "X-Request-Auth";
    /**
     * The constant HEADER_VALUE_FO.
     */
    protected static final String HEADER_VALUE_FO = "FO";


    /**
     * Get method name string.
     *
     * @return the string
     */
    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    /**
     * Get method name string.
     *
     * @param stack the stack
     * @return the string
     */
    public String getMethodName(int stack) {
        return Thread.currentThread().getStackTrace()[stack].getMethodName();
    }


    /**
     * Log begin.
     *
     * @param classname  the classname
     * @param methodName the method name
     */
    protected void logBegin(String classname, String methodName) {
        logDebug(classname, methodName, "BEGIN");
    }

    /**
     * Log begin.
     *
     * @param classname the classname
     */
    protected void logBegin(String classname) {
        logDebug(classname, getMethodName(3), "BEGIN");
    }

    /**
     * Log begin info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param obj        the obj
     */
    protected void logBeginInfo(String className, String methodName, Object obj) {
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        if (obj != null) {
            String inputParam = "Parametri in input " + obj.getClass().getSimpleName() + ":\n" + obj + "\n";
            LOGGER.debug(getClassFunctionDebugString(className, methodName, inputParam));
        }
    }

    /**
     * Log begin info.
     *
     * @param className the class name
     * @param obj       the obj
     */
    protected void logBeginInfo(String className, Object obj) {
        logBegin(className);
        if (obj != null) {
            String inputParam = "Parametro in input " + obj.getClass().getSimpleName() + ":\n" + obj + "\n";
            logInfo(className, inputParam);
        }
    }

    /**
     * Log begin info.
     *
     * @param className  the class name
     * @param methodName the method name
     */
    protected void logBeginInfo(String className, String methodName) {
        logBeginInfo(className, methodName, null);
    }

    /**
     * Log end.
     *
     * @param classname  the classname
     * @param methodName the method name
     */
    protected void logEnd(String classname, String methodName) {
        logDebug(classname, methodName, "END");
    }

    /**
     * Log end.
     *
     * @param classname the classname
     */
    protected void logEnd(String classname) {
        logDebug(classname, getMethodName(3), "END");
    }

    /**
     * Log debug.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param info       the info
     */
    protected void logDebug(String classname, String methodName, String info) {
        log(classname, methodName, info, "DEBUG");
    }

    /**
     * Log debug.
     *
     * @param classname the classname
     * @param info      the info
     */
    protected void logDebug(String classname, String info) {
        log(classname, getMethodName(3), info, "DEBUG");
    }

    /**
     * Log error.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param error      the error
     */
    protected void logError(String classname, String methodName, Object error) {
        log(classname, methodName, error, "ERROR");
    }

    /**
     * Log error.
     *
     * @param classname the classname
     * @param error     the error
     */
    protected void logError(String classname, Object error) {
        log(classname, getMethodName(3), error, "ERROR");
    }

    /**
     * Log info.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param info       the info
     */
    protected void logInfo(String classname, String methodName, String info) {
        log(classname, methodName, info, "INFO");
    }

    /**
     * Log info.
     *
     * @param classname the classname
     * @param info      the info
     */
    protected void logInfo(String classname, String info) {
        log(classname, getMethodName(3), info, "INFO");
    }

    /**
     * Log.
     *
     * @param classname  the classname
     * @param methodName the method name
     * @param info       the info
     * @param type       the type
     */
    protected void log(String classname, String methodName, Object info, String type) {
        if (StringUtils.isNotBlank(methodName) && info != null && StringUtils.isNotBlank(type)) {
            String log = "[" + classname + "::" + methodName + "] " + info;
            switch (type.toUpperCase(Locale.ROOT)) {
                case "DEBUG":
                    LOGGER.debug(log);
                    break;
                case "ERROR":
                    LOGGER.error(log);
                    break;
                default:
                    LOGGER.info(log);
                    break;
            }
        }
    }

    /**
     * Gets class function begin info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @return the class function begin info
     */
    protected String getClassFunctionBeginInfo(String className, String methodName) {
        return getClassFunctionDebugString(className, methodName, "BEGIN");
    }

    /**
     * Gets class function end info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @return the class function end info
     */
    protected String getClassFunctionEndInfo(String className, String methodName) {
        return getClassFunctionDebugString(className, methodName, "END");
    }

    /**
     * Gets class function error info.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param error      the error
     * @return the class function error info
     */
    protected String getClassFunctionErrorInfo(String className, String methodName, Object error) {
        return getClassFunctionDebugString(className, methodName, "ERROR : " + error);
    }

    /**
     * Gets class function debug string.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param info       the info
     * @return the class function debug string
     */
    protected String getClassFunctionDebugString(String className, String methodName, String info) {
        String functionIdentity = "[" + className + "::" + methodName + "] ";
        return functionIdentity + info;
    }

    /**
     * Gets session user.
     *
     * @param httpRequest the http request
     * @return the session user
     */
    protected UserInfo getSessionUser(HttpServletRequest httpRequest) {
        return (UserInfo) httpRequest.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
    }

    /**
     * Gets multivalued map from http headers.
     *
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @return the multivalued map from http headers
     */
    protected MultivaluedMap<String, Object> getMultivaluedMapFromHttpHeaders(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        if (httpHeaders == null) {
            return null;
        } else {
            MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
            MultivaluedMap<String, Object> map = new MultivaluedHashMap<>();
            setFOHeader(requestHeaders, httpRequest);
            requestHeaders.forEach((name, values) -> {
                /*
                LOGGER.debug("Header name : " + name);
                if (!CollectionUtils.isEmpty(values)) {
                    LOGGER.debug("Header value : " + values.get(0));
                }
                */
                //if (HEADER_ATTORE_SCRIVA.equalsIgnoreCase(name) || HEADER_X_REQUEST_AUTH.equalsIgnoreCase(name) || AUTH_ID_MARKER.equalsIgnoreCase(name)) {
                if (HEADER_X_REQUEST_AUTH.equalsIgnoreCase(name) && !CollectionUtils.isEmpty(values)) {
                    XRequestAuth xRequestAuth = getXRequestAuth(values.get(0));
                    if (xRequestAuth != null) {
                        xRequestAuth.setUsername(StringUtils.isBlank(xRequestAuth.getUsername()) ? xRequestAuth.getComponenteApplicativa() : xRequestAuth.getUsername());
                        xRequestAuth.setIdentita(xRequestAuth.getIdentita()!=null ? xRequestAuth.getIdentita() : new Identita());
                        map.put(name, Collections.singletonList(getXRequestAuth(xRequestAuth)));
                    }
                }
            });
            return map;
        }
    }

    /**
     * Gets multivalued map from http headers.
     *
     * @param httpHeaders the http headers
     * @return the multivalued map from http headers
     */
    protected MultivaluedMap<String, Object> getMultivaluedMapFromHttpHeaders(HttpHeaders httpHeaders) {
        if (httpHeaders == null) {
            return null;
        } else {
            MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
            MultivaluedMap<String, Object> map = new MultivaluedHashMap<>();
            /*
            for (Map.Entry<String, List<String>> entry : requestHeaders.entrySet()) {
                if (entry.getValue() != null) {
                    map.addAll(entry.getKey(), new LinkedList<Object>(entry.getValue()));
                }
            }
            */
            requestHeaders.forEach((name, values) -> {
                if (    //!"Accept".equals(name) &&
                    //!"Accept-Encoding".equals(name) &&
                    //!"Cache-Control".equals(name) &&
                    //!"Connection".equals(name) &&
                        !"Content-Type".equals(name) &&
                                !"Content-Length".equals(name)
                    //!"Cookie".equals(name) &&
                    //!"Host".equals(name) &&
                    //!"Postman-Token".equals(name) &&
                    //!"Shib-Iride-IdentitaDigitale".equals(name) &&
                    //!"User-Agent".equals(name)
                ) {
                    if (!CollectionUtils.isEmpty(values)) {
                        map.put(name, (values.size() != 1) ? Collections.singletonList(values) : Collections.singletonList(values.get(0)));
                    }
                }
            });
            return map;
        }
    }

    /**
     * Sets fo header.
     *
     * @param headers     the headers
     * @param httpRequest the http request
     */
    protected void setFOHeader(MultivaluedMap<String, String> headers, HttpServletRequest httpRequest) {
        if (!headers.containsKey(HEADER_ATTORE_SCRIVA)) {
            UserInfo userInfo = getSessionUser(httpRequest);
            headers.add(HEADER_ATTORE_SCRIVA, HEADER_VALUE_FO + (userInfo != null ? "/" + userInfo.getCodFisc() : ""));
        }
    }

    /**
     * Gets response with shared headers.
     *
     * @param obj           the obj
     * @param serviceHelper the service helper
     * @return the response with shared headers
     */
    protected Response getResponseWithSharedHeaders(Object obj, AbstractServiceHelper serviceHelper) {
        return getResponseWithSharedHeaders(obj, serviceHelper, null);
    }

    /**
     * Gets response with shared headers.
     *
     * @param obj           the obj
     * @param serviceHelper the service helper
     * @param status        the status
     * @return the response with shared headers
     */
    protected Response getResponseWithSharedHeaders(Object obj, AbstractServiceHelper serviceHelper, Integer status) {
        String headerAttoreGestione = serviceHelper.getHeaderAttoreGestione();
        if (status != null && status == 204) {
            return Response.noContent().header(HttpHeaders.CONTENT_ENCODING, "identity").header(Constants.HEADER_ATTORE_GESTIONE, headerAttoreGestione).build();
        } else if (status != null && status > 400) {
            ErrorDTO err = new ErrorDTO(status.toString(), "E100", status == 404 ? "Elemento non trovato" : "Errore inaspettato nella gestione della richiesta. Riprova a breve.", null, null);
            return Response.serverError().entity(err).status(status).build();
        }
        return Response.ok(obj).header(HttpHeaders.CONTENT_ENCODING, "identity").header(Constants.HEADER_ATTORE_GESTIONE, headerAttoreGestione).status(status == null ? 200 : status).build();
    }


    /**
     * Attore scriva match session user boolean.
     *
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @return the boolean
     */
    protected boolean attoreScrivaMatchSessionUser(HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        boolean result = false;
        if (httpHeaders != null & httpRequest != null) {
            UserInfo userInfo = getSessionUser(httpRequest);
            MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();
            List<String> headerXRequestAuthList = requestHeaders.get(HEADER_X_REQUEST_AUTH);
            List<String> headerAttoreIstanzaList = requestHeaders.get(HEADER_ATTORE_SCRIVA);
            String headerXRequestAuth = headerXRequestAuthList != null && !headerXRequestAuthList.isEmpty() ? headerXRequestAuthList.get(0) : null;
            String headerAttoreIstanza = headerAttoreIstanzaList != null && !headerAttoreIstanzaList.isEmpty() ? headerAttoreIstanzaList.get(0) : null;
            if (StringUtils.isNotBlank(headerXRequestAuth)) {
                try {
                    String xRequestAuth = new String(Base64.decode(headerXRequestAuth));
                    ObjectMapper mapper = new ObjectMapper();
                    XRequestAuth requestAuth = mapper.readValue(xRequestAuth, XRequestAuth.class);
                    if (requestAuth != null) {
                        String cfAttoreScriva = requestAuth.getUsername();
                        result = cfAttoreScriva.equalsIgnoreCase(userInfo.getCodFisc());
                    }
                } catch (IOException e) {
                    LOGGER.error("[BaseApiServiceImpl::getAttoreScriva] EXCEPTION : " + e);
                }
            } else if (headerAttoreIstanza != null) {
                String[] headerAttoreIstanzaSplitted = headerAttoreIstanza.split("/");
                if (headerAttoreIstanzaSplitted.length > 1) {
                    String cfAttoreScriva = headerAttoreIstanzaSplitted[1];
                    result = cfAttoreScriva.equalsIgnoreCase(userInfo.getCodFisc());
                }
            }
        }
        return result;
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
    /*
    protected HttpRequestBase getHttpRequest(URIBuilder uriBuilder, String reqType, MultivaluedMap<String, Object> headerParams, Map<String, Object> requestBody) throws URISyntaxException {
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

     */

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

    /**
     * Sets request body.
     *
     * @param request     the request
     * @param requestBody the request body
     */
    protected void setRequestBody(HttpEntityEnclosingRequestBase request, Map<String, Object> requestBody) {
        if (request != null && requestBody != null && !requestBody.isEmpty()) {
            JSONObject jsonObject = new JSONObject(requestBody);
            StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
            request.setEntity(entity);
            request.addHeader("content-type", MediaType.APPLICATION_JSON);
        }
    }

    /**
     * Gets x request auth.
     *
     * @param headerXRequestAuth the header x request auth
     * @return the x request auth
     */
    protected XRequestAuth getXRequestAuth(String headerXRequestAuth) {
        try {
            if (StringUtils.isNotBlank(headerXRequestAuth)) {
                String xRequestAuth = new String(Base64.decode(headerXRequestAuth));
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(xRequestAuth, XRequestAuth.class);
            }
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets x request auth.
     *
     * @param xRequestAuth the x request auth
     * @return the x request auth
     */
    protected String getXRequestAuth(XRequestAuth xRequestAuth) {
        try {
            if (xRequestAuth != null) {
                ObjectMapper mapper = new ObjectMapper();
                String xRequestAuthString = mapper.writeValueAsString(xRequestAuth);
                return new String(java.util.Base64.getEncoder().encode(xRequestAuthString.getBytes()));
            }
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

}