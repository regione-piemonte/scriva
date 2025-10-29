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

import it.csi.scriva.scrivabesrv.business.be.CategorieAllegatoApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CategoriaAllegatoDAO;
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
 * The type Categorie allegato api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategorieAllegatoApiServiceImpl extends BaseApiServiceImpl implements CategorieAllegatoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private CategoriaAllegatoDAO categoriaAllegatoDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCategorieAllegato(String codCategAllegato, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codCategAllegato);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
            return getResponseList(categoriaAllegatoDAO.loadTipologieAllegato(codCategAllegato), className);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
    }

    /**
     * @param idAdempimento   idAdempimentocategoriaAllegatoDAO.loadTipologieAllegato(codCategAllegato)
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCategoriaAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
            return getResponseList(categoriaAllegatoDAO.loadCategoriaAllegatoByIdAdempimento(idAdempimento, attoreScriva.getComponente()), className);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
    }

    /**
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCategoriaAllegatoByCodeAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codAdempimento);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
            return getResponseList(categoriaAllegatoDAO.loadCategoriaAllegatoByCodeAdempimento(codAdempimento, attoreScriva.getComponente()), className);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
    }
}