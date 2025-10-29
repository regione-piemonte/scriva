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

import it.csi.scriva.scrivabesrv.business.be.TipiSoggettoApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoSoggettoDAO;
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
 * The type Tipi soggetto api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipiSoggettoApiServiceImpl extends BaseApiServiceImpl implements TipiSoggettoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TipoSoggettoDAO tipoSoggettoDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getTipiSoggetto(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(tipoSoggettoDAO.loadTipiSoggetto(), className);
    }

    /**
     * @param idTipoSoggetto  idTipoSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getTipoSoggetto(Long idTipoSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoSoggetto);
        return getResponseList(tipoSoggettoDAO.loadTipoSoggettoById(idTipoSoggetto), className);
    }

    /**
     * @param codTipoSoggetto codTipoSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getTipoSoggettoByCode(String codTipoSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codTipoSoggetto);
        return getResponseList(tipoSoggettoDAO.loadTipoSoggettoByCode(codTipoSoggetto), className);
    }

}