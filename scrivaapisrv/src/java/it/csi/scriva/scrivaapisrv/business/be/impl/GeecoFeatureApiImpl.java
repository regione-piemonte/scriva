/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.csi.scriva.scrivaapisrv.business.be.GeecoFeatureApi;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.ApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.Feature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Map;


/**
 * The type Geeco feature api.
 *
 * @author CSI PIEMONTE
 */
@Component
public class GeecoFeatureApiImpl extends AbstractApiServiceImpl implements GeecoFeatureApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Api service helper.
     */
    @Autowired
    ApiServiceHelper apiServiceHelper;

    /**
     * Insert feature for editing layer response.
     *
     * @param environment      the environment
     * @param layerId          the layer id
     * @param idEditingSession the id editing session
     * @param feature          the feature
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @return Response response
     */
    @Override
    public Response insertFeatureForEditingLayer(String environment, String layerId, String idEditingSession,
                                                 Feature feature,
                                                 SecurityContext securityContext, HttpHeaders httpHeaders,
                                                 HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, "environment : [" + environment + "] - layerId : [" + layerId + "] - idEditingSession : [" + idEditingSession + "] - feature :\n" + feature);
        String servicePath = getBaseServicePath(environment, layerId, null, idEditingSession);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return apiServiceHelper.getResponsePOST(servicePath, objectMapper.convertValue(feature, Map.class), securityContext, httpHeaders, httpRequest, uriInfo);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update feature for editing layer response.
     *
     * @param environment      the environment
     * @param layerId          the layer id
     * @param featureId        the feature id
     * @param idEditingSession the id editing session
     * @param feature          the feature
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @return Response response
     */
    @Override
    public Response updateFeatureForEditingLayer(String environment, String layerId, String featureId, String idEditingSession,
                                                 Feature feature,
                                                 SecurityContext securityContext, HttpHeaders httpHeaders,
                                                 HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, "environment : [" + environment + "] - layerId : [" + layerId + "] - featureId : [" + featureId + "] - idEditingSession : [" + idEditingSession + "] - feature :\n" + feature);
        String servicePath = getBaseServicePath(environment, layerId, featureId, idEditingSession);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return apiServiceHelper.getResponsePUT(servicePath, objectMapper.convertValue(feature, Map.class), securityContext, httpHeaders, httpRequest, uriInfo);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete feature for editing layer response.
     *
     * @param environment      the environment
     * @param layerId          the layer id
     * @param featureId        the feature id
     * @param idEditingSession the id editing session
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @return Response response
     */
    @Override
    public Response deleteFeatureForEditingLayer(String environment, String layerId, String featureId, String idEditingSession,
                                                 SecurityContext securityContext, HttpHeaders httpHeaders,
                                                 HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, "environment : [" + environment + "] - layerId : [" + layerId + "] - featureId : [" + featureId + "] - idEditingSession : [" + idEditingSession + "]");
        String servicePath = getBaseServicePath(environment, layerId, featureId, idEditingSession);
        try {
            return apiServiceHelper.getResponseDEL(servicePath, securityContext, httpHeaders, httpRequest, uriInfo);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets base service path.
     *
     * @param environment      the environment
     * @param layerId          the layer id
     * @param featureId        the feature id
     * @param idEditingSession the id editing session
     * @return the base service path
     */
    private String getBaseServicePath(String environment, String layerId, String featureId, String idEditingSession) {
        return "/geeco/" + environment + "/" + idEditingSession + "/layers/" + layerId + (StringUtils.isNotBlank(featureId) ? "/" + featureId : "");
    }
}