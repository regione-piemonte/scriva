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

import it.csi.scriva.scrivabesrv.business.be.TipologieOggettoApi;
import it.csi.scriva.scrivabesrv.business.be.service.TipologieOggettoService;
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
 * The type Tipologie oggetto api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipologieOggettoApiImpl extends BaseApiServiceImpl implements TipologieOggettoApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Tipologie oggetto service.
     */
    @Autowired
    TipologieOggettoService tipologieOggettoService;

    /**
     * Load tipologie oggetto by adempimento code response.
     *
     * @param codAdempimento   the cod adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadTipologieOggettoByAdempimentoCode(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(tipologieOggettoService.loadTipologiaOggettoByCodeAdempimento(codAdempimento), className);
    }
}