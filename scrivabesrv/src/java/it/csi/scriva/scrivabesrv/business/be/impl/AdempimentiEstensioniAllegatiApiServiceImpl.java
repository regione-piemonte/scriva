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

import it.csi.scriva.scrivabesrv.business.be.AdempimentiEstensioniAllegatiApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoEstensioneAllegatoDAO;
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
 * The type Adempimenti estensioni allegati api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdempimentiEstensioniAllegatiApiServiceImpl extends BaseApiServiceImpl implements AdempimentiEstensioniAllegatiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentoEstensioneAllegatoDAO adempimentoEstensioneAllegatoDAO;

    /**
     * Restituisce l'elenco delle estensioni degli allegati previste per tutti gli adempimenti
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentiEstensioniAllegati(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(adempimentoEstensioneAllegatoDAO.loadAdempimentiEstensioniAllegati(), className);
    }

    /**
     * Restituisce l'elenco delle estensioni degli allegati previste per l'adempimento il cui id è passato in input
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentoEstensioneAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "]");
        return getResponseList(adempimentoEstensioneAllegatoDAO.loadAdempimentoEstensioneAllegatoByIdAdempimento(idAdempimento), className);
    }

    /**
     * Restituisce l'elenco delle estensioni degli allegati previste per l'adempimento il cui codice è passato in input
     *
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadAdempimentoEstensioneAllegatoByCodAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codAdempimento [" + codAdempimento + "]");
        return getResponseList(adempimentoEstensioneAllegatoDAO.loadAdempimentoEstensioneAllegatoByCodAdempimento(codAdempimento), className);
    }

}