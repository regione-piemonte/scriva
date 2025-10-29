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

import it.csi.scriva.scrivafoweb.business.be.AdempimentiTipiAllegatoApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.AdempimentiTipiAllegatoApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoTipoAllegatoExtendedDTO;
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
 * The type Adempimenti tipi allegato api service.
 */
@Component
public class AdempimentiTipiAllegatoApiServiceImpl extends AbstractApiServiceImpl implements AdempimentiTipiAllegatoApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Adempimenti tipi allegato api service helper.
     */
    @Autowired
    AdempimentiTipiAllegatoApiServiceHelper adempimentiTipiAllegatoApiServiceHelper;

    @Override
    public Response getTipologieAllegato(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologieAllegato(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getTipologiaAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologiaAllegatoByIdAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
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
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getTipologiaAllegatoByCodeAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametro in input codAdempimento [" + codAdempimento + "]");
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologiaAllegatoByCodeAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento);
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
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(String codAdempimento, String codTipologiaAllegato, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codAdempimento [" + codAdempimento + "], codTipologiaAllegato [" + codTipologiaAllegato + "]");
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento, codTipologiaAllegato);
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
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getTipologieAllegatoByCodeAdempimentoAndCodeCategoria(String codAdempimento, String codCategoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametro in input codAdempimento [" + codAdempimento + "] - codCategoria [" + codCategoria + "]");
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento, codCategoria);
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
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }
}