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

import it.csi.scriva.scrivabesrv.business.be.AdempimentiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.AdempiEsitoProcedimentoService;
import it.csi.scriva.scrivabesrv.business.be.service.AdempiStatoIstanzaService;
import it.csi.scriva.scrivabesrv.business.be.service.AdempimentiService;
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
 * The type Adempimenti service api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdempimentiApiServiceImpl extends BaseApiServiceImpl implements AdempimentiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentiService adempimentiService;

    @Autowired
    private AdempiEsitoProcedimentoService adempiEsitoProcedimentoService;

    @Autowired
    private AdempiStatoIstanzaService adempiStatoIstanzaService;

    /**
     * Load adempimenti response.
     *
     * @param xRequestAuth       the x request auth
     * @param xRequestId         the x request id
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipoadempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the response
     */
    @Override
    public Response loadAdempimenti(String xRequestAuth, String xRequestId, Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAmbito [" + idAmbito + "] - codAmbito [" + codAmbito + "] - idTipoAdempimento [" + idTipoAdempimento + "] - codTipoAdempimento [" + codTipoAdempimento + "] - codAdempimento [" + codAdempimento + "] - idCompilante [" + idCompilante + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentiService.loadAdempimenti(idAmbito, codAmbito, idTipoAdempimento, codTipoAdempimento, codAdempimento, idCompilante, attoreScriva), className);
    }

    /**
     * adempimento per id
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentoById(String xRequestAuth, String xRequestId, Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentiService.loadAdempimento(idAdempimento, attoreScriva), className);
    }

    /**
     * Load adempi esito procedimento response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idAdempimento   the id adempimento
     * @param flgAttivi       the flg attivi
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadAdempiStatoIstanza(String xRequestAuth, String xRequestId, Long idAdempimento, Boolean flgAttivi, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento : [" + idAdempimento + "] - flgAttivi : [" + flgAttivi + "]");
        return getResponseList(adempiEsitoProcedimentoService.loadAdempiEsitoProcedimento(idAdempimento, flgAttivi), className);
    }

    /**
     * Load adempi esito procedimento response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadAdempiStatoIstanza(String xRequestAuth, String xRequestId, Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento : [" + idAdempimento + "]");
        return getResponseList(adempiStatoIstanzaService.loadAdempiStatoIstanza(idAdempimento), className);
    }

}