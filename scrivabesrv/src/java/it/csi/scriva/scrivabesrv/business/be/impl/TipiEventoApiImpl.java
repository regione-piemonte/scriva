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

import it.csi.scriva.scrivabesrv.business.be.TipiEventoApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.TipiEventoService;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TipiEventoApiImpl extends BaseApiServiceImpl implements TipiEventoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    TipiEventoService tipiEventoService;

    /**
     * Load tipo evento by code response.
     *
     * @param codTipoEvento   the cod tipo evento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadTipoEventoByCode(String codTipoEvento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codTipoEvento);
        AttoreScriva attoreScriva = null;
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        return getResponseList(tipiEventoService.loadTipoEventoByCode(codTipoEvento, attoreScriva.getComponente()), className);
    }
}