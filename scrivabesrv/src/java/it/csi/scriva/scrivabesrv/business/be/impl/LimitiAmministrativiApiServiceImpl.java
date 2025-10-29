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

import it.csi.scriva.scrivabesrv.business.be.LimitiAmministrativiApi;
import it.csi.scriva.scrivabesrv.business.be.service.LimitiAmministrativiService;
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
 * The type Limiti amministrativi api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LimitiAmministrativiApiServiceImpl extends BaseApiServiceImpl implements LimitiAmministrativiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    LimitiAmministrativiService limitiAmministrativiService;

    /**
     * Load nazioni response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codIstat        the cod istat
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadNazioni(String xRequestAuth, String xRequestId, String codIstat, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codIstat);
        return getResponseList(limitiAmministrativiService.loadNazioni(codIstat), className);
    }

    /**
     * Load nazioni attive response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadNazioniAttive(String xRequestAuth, String xRequestId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(limitiAmministrativiService.loadNazioniAttive(), className);
    }

    /**
     * Load regioni response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codIstat        the cod istat
     * @param codIstatNazione the cod istat nazione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadRegioni(String xRequestAuth, String xRequestId, String codIstat, String codIstatNazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codIstat : [" + codIstat + "] - codIstatNazione : [" + codIstatNazione + "]");
        return getResponseList(limitiAmministrativiService.loadRegioni(codIstat, codIstatNazione), className);
    }

    /**
     * Load regioni attive response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadRegioniAttive(String xRequestAuth, String xRequestId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(limitiAmministrativiService.loadRegioniAttive(), className);
    }

    /**
     * Load province response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codIstat        the cod istat
     * @param codIstatRegione the cod istat regione
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadProvince(String xRequestAuth, String xRequestId, String codIstat, String codIstatRegione, Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codIstat : [" + codIstat + "] - codIstatRegione : [" + codIstatRegione + "] - idAdempimento : [" + idAdempimento + "]");
        return getResponseList(limitiAmministrativiService.loadProvince(codIstat, codIstatRegione, idAdempimento), className);
    }

    /**
     * Load province attive response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadProvinceAttive(String xRequestAuth, String xRequestId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(limitiAmministrativiService.loadProvinceAttive(), className);
    }

    /**
     * Load comuni response.
     *
     * @param xRequestAuth      the x request auth
     * @param xRequestId        the x request id
     * @param codIstat          the cod istat
     * @param codIstatProvincia the cod istat provincia
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response loadComuni(String xRequestAuth, String xRequestId, String codIstat, String codIstatProvincia, String siglaProvincia, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codIstat : [" + codIstat + "] - codIstatProvincia : [" + codIstatProvincia + "] - siglaProvincia : [" + siglaProvincia + "]");
        return getResponseList(limitiAmministrativiService.loadComuni(codIstat, codIstatProvincia, siglaProvincia), className);
    }

    /**
     * Load comuni attivi response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadComuniAttivi(String xRequestAuth, String xRequestId, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(limitiAmministrativiService.loadComuniAttivi(), className);
    }

}