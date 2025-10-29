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

import it.csi.scriva.scrivabesrv.business.be.RuoliCompilanteApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfiguraRuoloSoggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.RuoloCompilanteDAO;
import it.csi.scriva.scrivabesrv.dto.ConfiguraRuoloSoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.RuoloCompilanteDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Ruoli compilante api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RuoliCompilanteApiServiceImpl extends BaseApiServiceImpl implements RuoliCompilanteApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private RuoloCompilanteDAO ruoloCompilanteDAO;

    @Autowired
    private ConfiguraRuoloSoggettoDAO configuraRuoloSoggettoDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoliCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(ruoloCompilanteDAO.loadRuoliCompilante(), className);
    }

    /**
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoloCompilante(Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idRuoloCompilante);
        return getResponseList(ruoloCompilanteDAO.loadRuoloCompilante(idRuoloCompilante), className);
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoliCompilanteByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        Set<RuoloCompilanteDTO> ruoloCompilanteSet = new HashSet<>();
        List<ConfiguraRuoloSoggettoExtendedDTO> configuraRuoloSoggettoList = configuraRuoloSoggettoDAO.loadConfiguraRuoliSoggettiByAdempimento(idAdempimento);
        if (configuraRuoloSoggettoList == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }
        for (ConfiguraRuoloSoggettoExtendedDTO configuraRuoloSoggetto : configuraRuoloSoggettoList) {
            ruoloCompilanteSet.add(configuraRuoloSoggetto.getRuoloCompilante());
        }
        logEnd(className);
        return Response.ok(ruoloCompilanteSet).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

    /**
     * @param codRuoloCompilante codRuoloCompilante
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadRuoloCompilanteByCode(String codRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codRuoloCompilante);
        return getResponseList(ruoloCompilanteDAO.loadRuoloCompilanteByCode(codRuoloCompilante), className);
    }

}