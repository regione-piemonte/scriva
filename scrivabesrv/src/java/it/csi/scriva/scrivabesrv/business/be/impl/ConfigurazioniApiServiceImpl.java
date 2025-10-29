/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.ConfigurazioniApi;
import it.csi.scriva.scrivabesrv.business.be.service.ConfigurazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Configurazioni api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConfigurazioniApiServiceImpl extends BaseApiServiceImpl implements ConfigurazioniApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    ConfigurazioniService configurazioniService;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadConfigurazioni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(configurazioniService.loadConfigurazioni(), className);
    }

    /**
     * @param key             key
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadConfigurazioneByKey(String key, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, key);
        return getResponseList(configurazioniService.loadConfigurazioneByKey(key), className);
    }
}