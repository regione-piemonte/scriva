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

import it.csi.scriva.scrivafoweb.business.be.AdempimentiRuoliCompilanteAPI;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.AdempimentiRuoliCompilanteAPIServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoRuoloCompilanteExtendedDTO;
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
 * The type Adempimenti ruoli compilante api serviceimpl.
 */
@Component
public class AdempimentiRuoliCompilanteAPIServiceimpl extends AbstractApiServiceImpl implements AdempimentiRuoliCompilanteAPI {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private AdempimentiRuoliCompilanteAPIServiceHelper adempimentiRuoliCompilanteAPIServiceHelper;

    @Override
    public Response getAdempimentiRuoliCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        logger.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilante] BEGIN");
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
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

    @Override
    public Response getAdempimentiRuoliCompilanteByRuoloCompilante(Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idRuoloCompilante);
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilanteByRuoloCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloCompilante);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
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

    @Override
    public Response getAdempimentiRuoliCompilanteByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilanteByAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
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

    @Override
    public Response getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(Long idRuoloCompilante, Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className,"Parametro in input idRuoloCompilante [" + idRuoloCompilante + "] - idAdempimento [" + idAdempimento + "]");
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloCompilante, idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
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