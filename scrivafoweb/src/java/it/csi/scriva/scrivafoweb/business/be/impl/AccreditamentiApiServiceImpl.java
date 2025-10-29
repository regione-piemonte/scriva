/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.AccreditamentiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.AccreditamentiApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AccreditamentoDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompilanteDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Accreditamenti api service.
 */
@Component
public class AccreditamentiApiServiceImpl extends AbstractApiServiceImpl implements AccreditamentiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AccreditamentiApiServiceHelper accreditamentiApiServiceHelper;

    /**
     * Save accreditamento response.
     *
     * @param accreditamento  the accreditamento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveAccreditamento(AccreditamentoDTO accreditamento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametro in input accreditamento :\r\n " + accreditamento);
        AccreditamentoDTO dto = new AccreditamentoDTO();
        try {
            dto = accreditamentiApiServiceHelper.saveRichiestaAccreditamento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), accreditamento);
        } catch (GenericException e) {
            logError(className, e);
            ErrorDTO err = e.getError();
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(dto).status(201).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Validate accreditamento response.
     *
     * @param uid             the uid
     * @param otp             the otp
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response validateAccreditamento(String uid, String otp, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametri in input uid [" + uid + "] - otp [" + otp + "]");
        List<CompilanteDTO> list = new ArrayList<>();
        try {
            list = accreditamentiApiServiceHelper.validateAccreditamento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid, otp);
        } catch (GenericException e) {
            logError(className, e);
            ErrorDTO err = e.getError();
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

}