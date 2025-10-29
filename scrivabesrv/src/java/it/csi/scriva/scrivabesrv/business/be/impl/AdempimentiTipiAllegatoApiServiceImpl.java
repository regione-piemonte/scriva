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

import it.csi.scriva.scrivabesrv.business.be.AdempimentiTipiAllegatoApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoAllegatoDAO;
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
 * The type Adempimenti tipi allegato api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdempimentiTipiAllegatoApiServiceImpl extends BaseApiServiceImpl implements AdempimentiTipiAllegatoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentoTipoAllegatoDAO adempimentoTipoAllegatoDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipologieAllegato(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentoTipoAllegatoDAO.loadTipologieAllegato(), className);
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipologiaAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentoTipoAllegatoDAO.loadTipologieAllegatoByIdAdempimento(idAdempimento, attoreScriva.getComponente()), className);
    }

    /**
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipologiaAllegatoByCodeAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codAdempimento [" + codAdempimento + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodeAdempimento(codAdempimento, attoreScriva.getComponente()), className);
        }

  
        /**
         * Carica la tipologia di allegato in base al codice adempimento e al codice tipologia allegato.
         * non filtra per ind_modifica
         * 
         * @param codAdempimento il codice dell'adempimento
         * @param codTipologiaAllegato il codice della tipologia di allegato
         * @param securityContext il contesto di sicurezza
         * @param httpHeaders le intestazioni HTTP
         * @param httpRequest la richiesta HTTP
         * @return la risposta contenente la lista delle tipologie di allegato
         */
        @Override
        public Response loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(String codAdempimento, String codTipologiaAllegato, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codAdempimento [" + codAdempimento + "], codTipologiaAllegato [" + codTipologiaAllegato + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentoTipoAllegatoDAO.loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(codAdempimento, codTipologiaAllegato, attoreScriva.getComponente()), className);
        }

    /**
     * @param codAdempimento  codAdempimento
     * @param codCategoria    codCategoria
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(String codAdempimento, String codCategoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codAdempimento [" + codAdempimento + "] - codCategoria [" + codCategoria + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentoTipoAllegatoDAO.loadTipologieAllegatoByCodeAdempimentoAndCodeCategoria(codAdempimento, codCategoria, attoreScriva.getComponente()), className);
    }


}