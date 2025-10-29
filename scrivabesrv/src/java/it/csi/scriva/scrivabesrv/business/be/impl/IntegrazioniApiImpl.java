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

import it.csi.scriva.scrivabesrv.business.be.IntegrazioniApi;
import it.csi.scriva.scrivabesrv.business.be.service.IntegrazioneIstanzaService;
import it.csi.scriva.scrivabesrv.dto.IntegrazioneIstanzaExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Collections;

/**
 * The type Integrazioni api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IntegrazioniApiImpl extends BaseApiServiceImpl implements IntegrazioniApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private IntegrazioneIstanzaService integrazioneIstanzaService;

    /**
     * Load integrazioni response.
     *
     * @param idIstanza           the id istanza
     * @param codTipoIntegrazione the cod tipo integrazione
     * @param securityContext     SecurityContext
     * @param httpHeaders         HttpHeaders
     * @param httpRequest         HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadIntegrazioni(Long idIstanza, String codTipoIntegrazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        logEnd(className);
        return getResponseList(integrazioneIstanzaService.loadIntegrazioniIstanzaByIdIstanza(idIstanza, codTipoIntegrazione), className);
    }

    /**
     * Save integrazioni response.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param securityContext     SecurityContext
     * @param httpHeaders         HttpHeaders
     * @param httpRequest         HttpServletRequest
     * @return Response response
     */
    @Override
    public Response saveIntegrazioni(IntegrazioneIstanzaExtendedDTO integrazioneIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, integrazioneIstanza);
        Response response = setAttoreRight(httpHeaders, integrazioneIstanza.getIdIstanza());
        if (response != null) {
            return response;
        }
        logEnd(className);
        Long idIntegrazioneIstanza = integrazioneIstanzaService.saveIntegrazioneIstanza(integrazioneIstanza, attoreScriva);
        updateIstanzaPraticaTimestampAttore(integrazioneIstanza.getIdIstanza(), attoreScriva);
        return getResponseList(idIntegrazioneIstanza != null ? integrazioneIstanzaService.loadIntegrazioniIstanzaById(idIntegrazioneIstanza) : Collections.emptyList(), className);
    }

    /**
     * Update integrazioni response.
     *
     * @param integrazioneIstanza the integrazione istanza
     * @param securityContext     SecurityContext
     * @param httpHeaders         HttpHeaders
     * @param httpRequest         HttpServletRequest
     * @return Response response
     */
    @Override
    public Response updateIntegrazioni(IntegrazioneIstanzaExtendedDTO integrazioneIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, integrazioneIstanza);
        Response response = setAttoreRight(httpHeaders, integrazioneIstanza.getIdIstanza());
        if (response != null) {
            return response;
        }
        integrazioneIstanzaService.updateIntegrazioneIstanza(integrazioneIstanza, attoreScriva);
        updateIstanzaPraticaTimestampAttore(integrazioneIstanza.getIdIstanza(), attoreScriva);
        logEnd(className);
        return getResponseList(integrazioneIstanzaService.loadIntegrazioniIstanzaById(integrazioneIstanza.getIdIntegrazioneIstanza()), className);
    }
}