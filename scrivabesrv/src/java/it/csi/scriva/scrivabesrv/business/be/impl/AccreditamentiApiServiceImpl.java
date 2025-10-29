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

import it.csi.scriva.scrivabesrv.business.be.AccreditamentiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.AccreditamentiService;
import it.csi.scriva.scrivabesrv.dto.AccreditamentoDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.collections4.CollectionUtils;
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
 * AccreditamentoApiServiceImpl class
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccreditamentiApiServiceImpl extends BaseApiServiceImpl implements AccreditamentiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AccreditamentiService accreditamentiService;

    /**
     * Save accreditamento response.
     *
     * @param accreditamento  AccreditamentoDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response AccreditamentoDTO
     */
    @Override
    public Response saveAccreditamento(AccreditamentoDTO accreditamento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, accreditamento);
        return Response.ok(accreditamentiService.saveAccreditamento(accreditamento)).status(201).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

    /**
     * Validate accreditamento response.
     *
     * @param uid             uid
     * @param otp             otp
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response validateAccreditamento(String uid, String otp, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, "uid [" + uid + "] - otp [" + otp + "]");
        List<CompilanteDTO> compilanteList = accreditamentiService.validateAccreditamento(uid, otp);
        if (CollectionUtils.isNotEmpty(compilanteList)) {
            return getResponseSave(compilanteList, className, "/compilanti/id/" + compilanteList.get(0).getIdCompilante());
        }
        return Response.ok(compilanteList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).build();
    }

}