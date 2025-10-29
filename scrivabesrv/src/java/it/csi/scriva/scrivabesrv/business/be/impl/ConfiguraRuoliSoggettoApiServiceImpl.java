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

import it.csi.scriva.scrivabesrv.business.be.ConfiguraRuoliSoggettoApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfiguraRuoloSoggettoDAO;
import it.csi.scriva.scrivabesrv.dto.ConfiguraRuoloSoggettoExtendedDTO;
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
 * The type Configura ruoli soggetto api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConfiguraRuoliSoggettoApiServiceImpl extends BaseApiServiceImpl implements ConfiguraRuoliSoggettoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private ConfiguraRuoloSoggettoDAO configuraRuoloSoggettoDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadConfiguraRuoliSoggetto(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(configuraRuoloSoggettoDAO.loadConfiguraRuoliSoggetti(), className);
    }

    /**
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadConfiguraRuoliSoggettoByRuoloCompilante(Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idRuoloCompilante);
        return getResponseList(configuraRuoloSoggettoDAO.loadConfiguraRuoliSoggettoByRuoloCompilante(idRuoloCompilante), className);
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadConfiguraRuoliSoggettoByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        return getResponseList(configuraRuoloSoggettoDAO.loadConfiguraRuoliSoggettiByAdempimento(idAdempimento), className);
    }

    /**
     * @param idRuoloSoggetto idRuoloSoggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadConfiguraRuoliSoggettoByRuoloSoggetto(Long idRuoloSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idRuoloSoggetto);
        return getResponseList(configuraRuoloSoggettoDAO.loadConfiguraRuoliSoggettiByRuoloSoggetto(idRuoloSoggetto), className);
    }

    /**
     * @param configuraRuoloSoggetto ConfiguraRuoloSoggettoExtendedDTO
     * @param securityContext        SecurityContext
     * @param httpHeaders            HttpHeaders
     * @param httpRequest            HttpServletRequest
     * @return Response
     */
    @Override
    public Response saveConfiguraRuoloSoggetto(ConfiguraRuoloSoggettoExtendedDTO configuraRuoloSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, configuraRuoloSoggetto);
        Integer id = configuraRuoloSoggettoDAO.saveConfiguraRuoloSoggetto(configuraRuoloSoggetto.getDTO());
        return getResponseSave(id != null ? configuraRuoloSoggettoDAO.loadConfiguraRuoliSoggettiByRuoloSoggetto(configuraRuoloSoggetto.getRuoloSoggetto().getIdRuoloSoggetto()) : null, className, "/configura-ruoli-soggetti/ruolo-soggetto/" + configuraRuoloSoggetto.getRuoloSoggetto().getIdRuoloSoggetto());
    }

}