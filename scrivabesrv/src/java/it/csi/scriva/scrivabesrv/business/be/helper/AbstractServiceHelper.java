/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.oauth2.OauthHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;
import java.util.Map;

/**
 * The type Abstract service helper.
 *
 * @author CSI PIEMONTE
 */
public abstract class AbstractServiceHelper extends BaseServiceImpl {

    /**
     * The constant LOGGER.
     */
    public static final Logger LOGGER = Logger.getLogger(Constants.COMPONENT_NAME + ".service");

    /**
     * The constant CONF_KEY_APIMANAGER_CONSUMER_KEY.
     */
    public static final String CONF_KEY_APIMANAGER_CONSUMER_KEY = "SCRIVA_APIMAN_CONSUMERKEY";
    /**
     * The constant CONF_KEY_APIMANAGER_CONSUMER_SECRET.
     */
    public static final String CONF_KEY_APIMANAGER_CONSUMER_SECRET = "SCRIVA_APIMAN_CONSUMERSECRET";

    /**
     * The Token url.
     */
    public String tokenUrl;
    /**
     * The Api endpoint.
     */
    public String apiEndpoint;
    /**
     * The Consumer key.
     */
    public String consumerKey;
    /**
     * The Consumer secret.
     */
    public String consumerSecret;

    /**
     * The Configurazione list.
     */
    public Map<String, String> configurazioneList;

    /**
     * The Configurazione dao.
     */
    @Autowired
    public ConfigurazioneDAO configurazioneDAO;

    /**
     * Sets conf keys.
     *
     * @param confKeys the conf keys
     * @throws JsonProcessingException the json processing exception
     */
    public void setConfKeys(List<String> confKeys) throws JsonProcessingException {
        this.configurazioneList = this.configurazioneList == null || this.configurazioneList.isEmpty() ?
                configurazioneDAO.loadConfigByKeyList(confKeys) :
                this.configurazioneList;
        if (this.configurazioneList != null && !this.configurazioneList.isEmpty()) {
            this.consumerKey = configurazioneList.getOrDefault(CONF_KEY_APIMANAGER_CONSUMER_KEY, null);
            this.consumerSecret = configurazioneList.getOrDefault(CONF_KEY_APIMANAGER_CONSUMER_SECRET, null);
        }
    }

    /**
     * Gets consumer key.
     *
     * @param confKeys the conf keys
     * @return the consumer key
     * @throws JsonProcessingException the json processing exception
     */
    public String getConsumerKey(List<String> confKeys) throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty() || StringUtils.isBlank(this.consumerKey)) {
            setConfKeys(confKeys);
        }
        return consumerKey;
    }

    /**
     * Gets consumer secret.
     *
     * @param confKeys the conf keys
     * @return the consumer secret
     * @throws JsonProcessingException the json processing exception
     */
    public String getConsumerSecret(List<String> confKeys) throws JsonProcessingException {
        if (this.configurazioneList == null || this.configurazioneList.isEmpty() || StringUtils.isBlank(this.consumerSecret)) {
            setConfKeys(confKeys);
        }
        return consumerSecret;
    }

    /**
     * Gets builder.
     *
     * @param url the url
     * @return the builder
     */
    public Invocation.Builder getBuilder(String url) throws JsonProcessingException {
        Client client = ClientBuilder.newBuilder().build();
        return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getToken(this.tokenUrl, this.consumerKey, this.consumerSecret));
    }

    /**
     * Gets token.
     *
     * @param tokenUrl       the token url
     * @param consumerKey    the consumer key
     * @param consumerSecret the consumer secret
     * @return the token
     */
    public String getToken(String tokenUrl, String consumerKey, String consumerSecret) {
        OauthHelper oauthHelper = new OauthHelper(
                tokenUrl,
                consumerKey,
                consumerSecret,
                10000);
        String token = oauthHelper.getToken();
        LOGGER.debug("token [" + token + "]");
        return token;
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
}