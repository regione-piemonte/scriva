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

import it.csi.scriva.scrivabesrv.business.be.AmbitiApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AmbitoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.AmbitiService;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
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
 * The type Ambiti api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AmbitiApiServiceImpl extends BaseApiServiceImpl implements AmbitiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AmbitoDAO ambitoDAO;

    @Autowired
    private AmbitiService ambitiService;

    /**
     * Load ambiti response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codAmbito       the cod ambito
     * @param idAmbito        the id ambito
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadAmbiti(String xRequestAuth, String xRequestId, Long idAmbito, String codAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAmbito [" + idAmbito + "] - codAmbito [" + codAmbito + "]");
        return getResponseList(ambitiService.loadAmbiti(idAmbito, codAmbito), className);
        //return getResponseJson(ambitiService.loadJsonAmbiti(idAmbito, codAmbito), className, methodName);
    }

    /**
     * @param idAmbito        idAmbito
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAmbito(String xRequestAuth, String xRequestId, Long idAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAmbito [" + idAmbito + "]");
        return getResponseList(ambitiService.loadAmbito(idAmbito), className);
    }

    /**
     * @param ambito          AmbitoDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response saveAmbito(String xRequestAuth, String xRequestId, AmbitoDTO ambito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "ambito :\n" + ambito + "\n");
        ambito.setGestAttoreIns("SYSTEM");
        Long idAmbito = ambitoDAO.saveAmbito(ambito);
        return getResponseSave(ambitoDAO.loadAmbitoById(idAmbito), className, "/ambiti/" + idAmbito);
    }

    /**
     * @param ambito          AmbitoDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateAmbito(String xRequestAuth, String xRequestId, AmbitoDTO ambito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "ambito :\n" + ambito);
        ambito.setGestAttoreUpd("SYSTEM");
        return getResponseSaveUpdate(ambitoDAO.updateAmbito(ambito), className);
    }

}