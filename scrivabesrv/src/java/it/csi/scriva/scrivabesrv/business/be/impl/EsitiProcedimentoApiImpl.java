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

import it.csi.scriva.scrivabesrv.business.be.EsitiProcedimentoApi;
import it.csi.scriva.scrivabesrv.business.be.service.EsitiProcedimentoService;
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
 * The type Esiti procedimento api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EsitiProcedimentoApiImpl extends BaseApiServiceImpl implements EsitiProcedimentoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private EsitiProcedimentoService esitiProcedimentoService;

    /**
     * Load esiti procedimento response.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param idEsitoProcedimento  the id esito procedimento
     * @param codEsitoProcedimento the cod esito procedimento
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
    @Override
    public Response loadEsitiProcedimento(String xRequestAuth, String xRequestId, Long idEsitoProcedimento, String codEsitoProcedimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idEsitoProcedimento [" + idEsitoProcedimento + "] - codEsitoProcedimento [" + codEsitoProcedimento + "]");
        return getResponseList(esitiProcedimentoService.loadEsitiProcedimento(idEsitoProcedimento, codEsitoProcedimento), className);
    }
}