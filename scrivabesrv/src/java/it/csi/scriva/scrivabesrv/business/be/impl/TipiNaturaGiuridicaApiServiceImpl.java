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

import it.csi.scriva.scrivabesrv.business.be.TipiNaturaGiuridicaApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNaturaGiuridicaDAO;
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
 * The type Tipi natura giuridica api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipiNaturaGiuridicaApiServiceImpl extends BaseApiServiceImpl implements TipiNaturaGiuridicaApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipoNaturaGiuridicaDAO tipoNaturaGiuridicaDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipiNaturaGiuridica(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(tipoNaturaGiuridicaDAO.loadTipiNaturaGiuridica(), className);
    }

    /**
     * @param idTipoNaturaGiuridica idTipoNaturaGiuridica
     * @param securityContext       SecurityContext
     * @param httpHeaders           HttpHeaders
     * @param httpRequest           HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipoNaturaGiuridica(Long idTipoNaturaGiuridica, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoNaturaGiuridica);
        return getResponseList(tipoNaturaGiuridicaDAO.loadTipoNaturaGiuridicaById(idTipoNaturaGiuridica), className);
    }

    /**
     * @param codTipoNaturaGiuridica codTipoNaturaGiuridica
     * @param securityContext        SecurityContext
     * @param httpHeaders            HttpHeaders
     * @param httpRequest            HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipoNaturaGiuridicaByCode(String codTipoNaturaGiuridica, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codTipoNaturaGiuridica);
        return getResponseList(tipoNaturaGiuridicaDAO.loadTipoNaturaGiuridicaByCode(codTipoNaturaGiuridica), className);
    }
}