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

import it.csi.scriva.scrivabesrv.business.be.AdempimentiRuoliCompilanteAPI;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoRuoloCompilanteDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoRuoloCompilanteExtendedDTO;
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
 * The type Adempimenti ruoli compilante api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdempimentiRuoliCompilanteAPIServiceImpl extends BaseApiServiceImpl implements AdempimentiRuoliCompilanteAPI {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentoRuoloCompilanteDAO adempimentoRuoloCompilanteDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentiRuoliCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(adempimentoRuoloCompilanteDAO.getAdempimentiRuoliCompilante(), className);
    }

    /**
     * @param idRuoloCompilante idRuoloCompilante
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentiRuoloCompilanteByRuoloCompilante(Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idRuoloCompilante [" + idRuoloCompilante + "]");
        return getResponseList(adempimentoRuoloCompilanteDAO.getAdempimentoRuoloCompilanteByRuoloCompilante(idRuoloCompilante), className);
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentiRuoloCompilanteByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "]");
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        }
        return getResponseList(adempimentoRuoloCompilanteDAO.getAdempimentoRuoloCompilanteByAdempimento(idAdempimento, attoreScriva.getComponente()), className); 
    }

    /**
     * @param idRuoloCompilante idRuoloCompilante
     * @param idAdempimento     idAdempimento
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(Long idRuoloCompilante, Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idRuoloCompilante [" + idRuoloCompilante + "] - idAdempimento [" + idAdempimento + "]");
        return getResponseList(adempimentoRuoloCompilanteDAO.getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(idRuoloCompilante, idAdempimento), className);
    }

    /**
     * @param adempimentoRuoloCompilante AdempimentoRuoloCompilanteExtendedDTO
     * @param securityContext            SecurityContext
     * @param httpHeaders                HttpHeaders
     * @param httpRequest                HttpServletRequest
     * @return Response
     */
    @Override
    public Response saveAdempimentoRuoloCompilante(AdempimentoRuoloCompilanteExtendedDTO adempimentoRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "adempimentoRuoloCompilante :\n" + adempimentoRuoloCompilante + "\n");
        Integer res = adempimentoRuoloCompilanteDAO.saveAdempimentoRuoloCompilante(adempimentoRuoloCompilante.getDTO());
        return getResponseSaveUpdate(null != res ? adempimentoRuoloCompilante : null, className, "/adempimenti-ruoli-compilante/id-ruolo-compilante/" + adempimentoRuoloCompilante.getRuoloCompilante().getIdRuoloCompilante() + "/id-adempimento/" + adempimentoRuoloCompilante.getAdempimento().getIdTipoAdempimento());
    }

    /**
     * @param adempimentoRuoloCompilante AdempimentoRuoloCompilanteExtendedDTO
     * @param securityContext            SecurityContext
     * @param httpHeaders                HttpHeaders
     * @param httpRequest                HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateAdempimentoRuoloCompilante(AdempimentoRuoloCompilanteExtendedDTO adempimentoRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "adempimentoRuoloCompilante :\n" + adempimentoRuoloCompilante + "\n");
        Integer res = adempimentoRuoloCompilanteDAO.updateAdempimentoRuoloCompilante(adempimentoRuoloCompilante.getDTO());
        return getResponseSaveUpdate(res != null && res > 0 ? adempimentoRuoloCompilante : res, className, "/adempimenti-ruoli-compilante/id-ruolo-compilante/" + adempimentoRuoloCompilante.getRuoloCompilante().getIdRuoloCompilante() + "/id-adempimento/" + adempimentoRuoloCompilante.getAdempimento().getIdTipoAdempimento());
    }

}