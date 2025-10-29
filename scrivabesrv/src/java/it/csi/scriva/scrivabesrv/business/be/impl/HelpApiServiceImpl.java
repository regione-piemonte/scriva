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

import it.csi.scriva.scrivabesrv.business.be.HelpApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.HelpDAO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.HelpExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * The type Help api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HelpApiServiceImpl extends BaseApiServiceImpl implements HelpApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private HelpDAO helpDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadHelp(String chiaveHelp, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, chiaveHelp);
        List<HelpExtendedDTO> helpList = chiaveHelp != null ? helpDAO.loadHelpByChiave(chiaveHelp) : helpDAO.loadHelp();
        if (null == helpList) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        logEnd(className);
        return Response.ok(helpList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
        //return getResponseList(chiaveHelp != null ? helpDAO.loadHelpByChiave(chiaveHelp) : helpDAO.loadHelp(), className);
    }

    /**
     * @param idHelp          idHelp
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadHelpById(Long idHelp, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idHelp);
        return getResponseList(helpDAO.loadHelpById(idHelp), className);
    }

    /**
     * @param chiaveHelp      chiaveHelp
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadHelpByChiave(String chiaveHelp, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, chiaveHelp);
        List<HelpExtendedDTO> helpList = helpDAO.loadHelpByChiave(chiaveHelp);
        if (null == helpList) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        logEnd(className);
        return Response.ok(helpList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }
}