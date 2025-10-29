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

import it.csi.scriva.scrivabesrv.business.be.RuoliSoggettoApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.RuoloSoggettoDAO;
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
 * The type Ruoli soggetto api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RuoliSoggettoApiServiceImpl extends BaseApiServiceImpl implements RuoliSoggettoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private RuoloSoggettoDAO ruoloSoggettoDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoliSoggetto(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(ruoloSoggettoDAO.loadRuoliSoggetto(), className);
    }

    /**
     * @param idRuoloSoggetto idRuoloSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoloSoggetto(Long idRuoloSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idRuoloSoggetto);
        return getResponseList(ruoloSoggettoDAO.loadRuoloSoggetto(idRuoloSoggetto), className);
    }

    /**
     * @param codRuoloSoggetto codRuoloSoggetto
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoloSoggettoByCode(String codRuoloSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codRuoloSoggetto);
        return getResponseList(ruoloSoggettoDAO.loadRuoloSoggettoByCode(codRuoloSoggetto), className);
    }

    /**
     * @param idAdempimento     idAdempimento
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento(Long idAdempimento, Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "] - idRuoloCompilante [" + idRuoloCompilante + "]");
        return getResponseList(ruoloSoggettoDAO.loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento(idRuoloCompilante, idAdempimento), className);
    }

}