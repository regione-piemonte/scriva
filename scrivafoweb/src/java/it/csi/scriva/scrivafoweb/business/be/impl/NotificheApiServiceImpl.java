/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

package it.csi.scriva.scrivafoweb.business.be.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.csi.scriva.scrivafoweb.business.be.NotificheApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * The type Notifiche api service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class NotificheApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements NotificheApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * Load notifiche response.
     *
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response loadNotifiche(Integer offset, Integer limit, String sort, SecurityContext securityContext,
                                  HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "sort [" + sort + "] - offset [" + offset + "] - limit [" + limit + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Load notifiche by id response.
     *
     * @param idNotifica      the id notifica
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    @Override
    public Response loadNotificheById(Long idNotifica, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "idNotifica [" + idNotifica + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Load notifiche response.
     *
     * @param requestBody     the request body
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response loadNotifiche(Map<String, Object> requestBody, Integer offset, Integer limit, String sort,
                                  SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "sort [" + sort + "] - offset [" + offset + "] - limit [" + limit + "]\nrequestBody:\n" + requestBody);
        return getResponsePOST(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Update notifiche response.
     *
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response updateNotifiche(List<Map<?, ?>> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, requestBody);
        return getResponsePUT(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Update notifiche response.
     *
     * @param requestBody     the request body
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response updateNotifiche(Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, requestBody);
        return getResponsePUT(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

}