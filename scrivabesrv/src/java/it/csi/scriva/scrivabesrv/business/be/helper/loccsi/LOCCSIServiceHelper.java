/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.loccsi;

import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.loccsi.dto.SearchIndexResult;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * The type Loccsi service helper.
 */
public class LOCCSIServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();
    private static final List<String> CONF_KEYS_LOCSI = Arrays.asList(CONF_KEY_APIMANAGER_CONSUMER_KEY, CONF_KEY_APIMANAGER_CONSUMER_SECRET);


    /**
     * Instantiates a new Loccsi service helper.
     *
     * @param endPoint   the end point
     * @param serviceUrl the service url
     */
    public LOCCSIServiceHelper(String endPoint, String serviceUrl) {
        this.tokenUrl = endPoint + "/token";
        this.apiEndpoint = endPoint + serviceUrl;
    }

    /**
     * Suggest toponomastica search index result.
     *
     * @param query the query
     * @return the search index result
     * @throws CosmoException the cosmo exception
     */
    public List<SearchIndexResult> suggestToponomastica(String xRequestID, String xForwardedFor, @NotNull String query, Integer limit, Integer offset) throws CosmoException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input query : \n" + query + "\n";
        LOGGER.debug(getClassFunctionDebugString(className, methodName, inputParam));
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        List<SearchIndexResult> result = null;
        String api = "/catalogs/toponomastica/suggest?q=" + query;
        api += limit!=null ? "&limit=" + limit : "";
        api += offset!=null ? "&offset=" + offset : "";
        try {
            this.setConfKeys(CONF_KEYS_LOCSI);
            String url = apiEndpoint + api;
            LOGGER.debug(getClassFunctionDebugString(className, methodName, "url [" + url + "]"));
            Response resp = getBuilder(url)
                    .header("X-Request-ID", xRequestID)
                    .header("X-Forwarded-For", xForwardedFor)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            if (resp.getStatus() >= 400) {
                String esito = resp.readEntity(String.class);
                LOGGER.debug(getClassFunctionErrorInfo(className, methodName, inputParam + "response : \n" + esito));
                throw new CosmoException(esito);
            } else {
                result = resp.readEntity(new GenericType<List<SearchIndexResult>>() {});
                LOGGER.debug(getClassFunctionErrorInfo(className, methodName, inputParam + "response : \n" + result));
            }
        } catch (Exception e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }


}