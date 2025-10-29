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

import it.csi.scriva.scrivabesrv.business.be.TemplateQuadroApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.TemplateQuadroDAO;
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
 * The type Template quadro api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TemplateQuadroApiServiceImpl extends BaseApiServiceImpl implements TemplateQuadroApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private TemplateQuadroDAO templateQuadroDAO;

    /**
     * @param codeAdempimento codeAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTemplateQuadriByCodeAdempimento(String codeAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, codeAdempimento);
        attoreScriva = getAttoreScriva(httpHeaders);
        return getResponseList(templateQuadroDAO.loadTemplateQuadroByCodeAdempimento(codeAdempimento, attoreScriva.getComponente()), className);
    }

    /**
     * @param codeTemplate    codeTemplate
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTemplateQuadriByCodeTemplate(String codeTemplate, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, codeTemplate);
        attoreScriva = getAttoreScriva(httpHeaders);
        return getResponseList(templateQuadroDAO.loadTemplateQuadroByCodeTemplate(codeTemplate, attoreScriva.getComponente()), className);
    }

    /**
     * @param idTemplateQuadro idTemplateQuadro
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadTemplateQuadriByIdTemplateQuadro(Long idTemplateQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idTemplateQuadro);
        attoreScriva = getAttoreScriva(httpHeaders);
        return getResponseList(templateQuadroDAO.loadTemplateQuadroById(idTemplateQuadro, attoreScriva.getComponente()), className);
    }

}