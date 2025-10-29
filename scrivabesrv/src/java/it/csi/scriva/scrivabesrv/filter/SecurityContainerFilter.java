/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ProfiloAppDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UserInfo;
import it.csi.scriva.scrivabesrv.dto.XRequestAuth;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoOggettoAppEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Security container filter.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
@Component
public class SecurityContainerFilter implements ContainerRequestFilter {

    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = Logger.getLogger(Constants.COMPONENT_NAME + ".security");
    private final String className = this.getClass().getSimpleName();
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<>());
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Forbidden", 403, new Headers<>());
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<>());

    private static final String SECURITY_CONTROL_ENABLED = "SCRIVA_SECURITY_CONTROL_ENABLED";

    /**
     * The Http servlet request.
     */
    @Context
    HttpServletRequest httpServletRequest;

    /**
     * The Application context.
     */
    ApplicationContext applicationContext;

    /**
     * The Istanza dao.
     */
    IstanzaDAO istanzaDAO;
    /**
     * The Profilo app dao.
     */
    ProfiloAppDAO profiloAppDAO;
    /**
     * The Configurazione dao.
     */
    ConfigurazioneDAO configurazioneDAO;

    /**
     * Gets application context.
     *
     * @return the application context
     */
    public ApplicationContext getApplicationContext() {
        this.applicationContext = applicationContext != null ? applicationContext : WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getSession().getServletContext());
        return this.applicationContext;
    }

    /**
     * Gets istanza dao.
     *
     * @return the istanza dao
     */
    public IstanzaDAO getIstanzaDAO() {
        istanzaDAO = istanzaDAO != null ? istanzaDAO : getApplicationContext().getBean(IstanzaDAO.class);
        return istanzaDAO;
    }

    /**
     * Gets profilo app dao.
     *
     * @return the profilo app dao
     */
    public ProfiloAppDAO getProfiloAppDAO() {
        profiloAppDAO = profiloAppDAO != null ? profiloAppDAO : getApplicationContext().getBean(ProfiloAppDAO.class);
        return profiloAppDAO;
    }

    /**
     * Gets configurazione dao.
     *
     * @return the configurazione dao
     */
    public ConfigurazioneDAO getConfigurazioneDAO() {
        configurazioneDAO = configurazioneDAO != null ? configurazioneDAO : getApplicationContext().getBean(ConfigurazioneDAO.class);
        return configurazioneDAO;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String logPerfix = "[" + className + "::" + Thread.currentThread().getStackTrace()[1].getMethodName() + "] : ";
        LOGGER.debug(logPerfix + "BEGIN");
        try {
            // check if enable
            if (!checkIfEnabled()) {
                LOGGER.debug(logPerfix + "Security Container Filter disabled!!");
                return;
            }

            //Get method name
            String methodName = getMethodName(containerRequestContext);

            // method in whitelist ?
            if (isMethodInWhiteList(methodName)) {
                LOGGER.debug(logPerfix + methodName + " is in whitelist");
                return;
            }

            // get the X-REQ-HEADER
            String xReq = getXRerquestParam(containerRequestContext);
            if (StringUtils.isBlank(xReq)) {
                LOGGER.debug(logPerfix + "X-Request-Auth not present");
                return;
            }

            // can call method ?
            if (Boolean.FALSE.equals(canCallMethod(methodName, xReq))) {
                containerRequestContext.abortWith(ACCESS_FORBIDDEN);
            }
        } catch (Exception e) {
            containerRequestContext.abortWith(SERVER_ERROR);
        } finally {
            LOGGER.debug(logPerfix + "END");
        }

        /*
        if (!method.isAnnotationPresent(PermitAll.class)) {
            //Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                containerRequestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            //Get request headers
            final MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if (authorization == null || authorization.isEmpty()) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }

            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            //Decode username and password
            String usernameAndPassword = null;
            try {
                usernameAndPassword = new String(Base64.decode(encodedUserPassword));
            } catch (IOException e) {
                containerRequestContext.abortWith(SERVER_ERROR);
                return;
            }

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //Verifying Username and password
            System.out.println(username);
            System.out.println(password);

            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                //Is user valid?
                if (!isUserAllowed(username, password, rolesSet)) {
                    containerRequestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }
        }
        */
    }


    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        boolean isAllowed = false;

        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);
        String userRole = "ADMIN";

        //Step 2. Verify user role
        if (rolesSet.contains(userRole)) {
            isAllowed = true;
        }
        return isAllowed;
    }

    /**
     * Recuper il metodo chiamato
     *
     * @param containerRequestContext containerRequestContext
     * @return Method
     */
    private Method getMethod(ContainerRequestContext containerRequestContext) {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) containerRequestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        return methodInvoker.getMethod();
    }

    /**
     * @param method method
     * @return String[]
     */
    private String[] getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        String[] paramNames = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            paramNames[i] = parameters[i].getName();
            LOGGER.info("Parametro [" + i + "] : " + parameters[i].toString());
            /*
            for (Annotation annotation : parameters[i].getAnnotations()) {
                LOGGER.info(paramNames[i] + " : " + annotation.toString());
            }

             */
        }
        return paramNames;
    }

    /**
     * Recupera il nome del metodo chiamato
     *
     * @param containerRequestContext containerRequestContext
     * @return String
     */
    private String getMethodName(ContainerRequestContext containerRequestContext) {
        Method method = getMethod(containerRequestContext);
        String methodName = method.getName();
        LOGGER.debug(methodName);
        return methodName;
    }

    /**
     * Gets session user.
     *
     * @return the session user
     */
    protected UserInfo getSessionUser() {
        UserInfo userInfo = (UserInfo) httpServletRequest.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
        return userInfo != null ? userInfo : (UserInfo) httpServletRequest.getSession().getAttribute(IrideIdAdapterFilter.AUTH_ID_MARKER);
    }

    /**
     * Gets x request auth.
     *
     * @param headerXRequestAuth the header x request auth
     * @return the x request auth
     */
    private XRequestAuth getXRequestAuth(String headerXRequestAuth) {
        try {
            String xRequestAuth = new String(Base64.decode(headerXRequestAuth));
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(xRequestAuth, XRequestAuth.class);
        } catch (Exception e) {
            LOGGER.error("[BaseApiServiceImpl::getXRequestAuth] ERROR : " + e);
            return null;
        } finally {
            LOGGER.info("[BaseApiServiceImpl::getXRequestAuth] END");
        }
    }

    /**
     * Gets headers.
     *
     * @param containerRequestContext containerRequestContext
     * @return the headers
     */
    protected Map<String, String> getHeaders(ContainerRequestContext containerRequestContext) {
        final MultivaluedMap<String, String> requestHeaders = containerRequestContext.getHeaders();
        Map<String, String> headerMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : requestHeaders.entrySet()) {
            LOGGER.debug(entry.getKey() + " : " + entry.getValue());
            headerMap.put(entry.getKey(), entry.getValue().get(0));
        }
        return headerMap;
    }

    /**
     * Can call method boolean.
     *
     * @param methodName              the method name
     * @param containerRequestContext the containerRequestContext
     * @return the boolean
     */
    protected Boolean canCallMethod(String methodName, ContainerRequestContext containerRequestContext) {
        Boolean result = Boolean.FALSE;

        Map<String, String> headers = getHeaders(containerRequestContext);
        String xreq = headers.get(Constants.HEADER_X_REQUEST_AUTH);

        if (StringUtils.isNotBlank(xreq)) {
            XRequestAuth requestAuth = getXRequestAuth(xreq);
            List<ProfiloAppExtendedDTO> profiloAppList = requestAuth != null ? getProfiloAppDAO().loadProfiloAppByCodeProfiloAndComponenteApp(requestAuth.getRuolo(), requestAuth.getComponenteApplicativa(), requestAuth.getPassword(), methodName, TipoOggettoAppEnum.SERVIZIO.name()) : null;
            result = profiloAppList != null && !profiloAppList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
        }
        return result;
    }

    /**
     * Can call method boolean.
     *
     * @param methodName the method name
     * @param xReq       the base64 xReq
     * @return the boolean
     */
    protected Boolean canCallMethod(String methodName, String xReq) {
        XRequestAuth requestAuth = getXRequestAuth(xReq);
        List<ProfiloAppExtendedDTO> profiloAppList = requestAuth != null ?
                getProfiloAppDAO().loadProfiloAppByCodeProfiloAndComponenteApp(requestAuth.getRuolo(), requestAuth.getComponenteApplicativa(), requestAuth.getPassword(), methodName, TipoOggettoAppEnum.SERVIZIO.name())
                :
                null;
        return profiloAppList != null && !profiloAppList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Gets x rerquest param.
     *
     * @param containerRequestContext the container request context
     * @return the x rerquest param
     */
    protected String getXRerquestParam(ContainerRequestContext containerRequestContext) {
        Map<String, String> headers = getHeaders(containerRequestContext);
        return headers.get(Constants.HEADER_X_REQUEST_AUTH);
    }

    /**
     * Method name in white list boolean.
     *
     * @param methodName the method name
     * @return the boolean
     */
    protected boolean methodNameInWhiteList(String methodName) {
        return Constants.METHOD_NAME_WHITELIST.contains(methodName);
    }

    /**
     * Is method in white list boolean.
     *
     * @param methodName the method name
     * @return the boolean
     */
    protected boolean isMethodInWhiteList(String methodName) {
        List<ProfiloAppExtendedDTO> profiloAppList = getProfiloAppDAO().loadProfiloAppByCodeProfiloAndComponenteApp("ALL", ComponenteAppEnum.ESTERNA.name(), null, methodName, TipoOggettoAppEnum.SERVIZIO.name());
        return profiloAppList != null && !profiloAppList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * Check if enabled boolean.
     *
     * @return the boolean
     */
    protected boolean checkIfEnabled() {
        boolean result = Boolean.FALSE;
        List<ConfigurazioneDTO> configurazioneList = getConfigurazioneDAO().loadConfigByKey(SECURITY_CONTROL_ENABLED);
        if (configurazioneList != null && !configurazioneList.isEmpty()) {
            result = Boolean.parseBoolean(configurazioneList.get(0).getValore().toUpperCase().trim());
        }
        return result;
    }


}