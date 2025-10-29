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

import it.csi.scriva.scrivabesrv.business.be.StatiProcedimentoStataleApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.StatiProcedimentoStataleService;
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
 * The interface Search api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StatiProcedimentoStataleApiImpl extends BaseApiServiceImpl implements StatiProcedimentoStataleApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private StatiProcedimentoStataleService statiProcedimentoStataleService;

    /**
     * Load stati procedimento statale response.
     *
     * @param codStatoProcStatale the cod stato proc statale
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @Override
    public Response loadStatiProcedimentoStatale(String codStatoProcStatale, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, "codStatoProcStatale [" + codStatoProcStatale + "]");
        attoreScriva = getAttoreScriva(httpHeaders);
        return getResponseList(statiProcedimentoStataleService.loadStatiProcedimentoStataleByCode(codStatoProcStatale, attoreScriva), className);
    }
}