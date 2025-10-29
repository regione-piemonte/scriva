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

import it.csi.scriva.scrivafoweb.business.be.AdempimentiEstensioniAllegatiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.AdempimentiEstensioniAllegatiApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoEstensioneAllegatoDTO;
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
 * The type Adempimenti estensioni allegati api service.
 */
@Component
public class AdempimentiEstensioniAllegatiApiServiceImpl extends AbstractApiServiceImpl implements AdempimentiEstensioniAllegatiApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Adempimenti estensioni allegati api service helper.
     */
    @Autowired
    AdempimentiEstensioniAllegatiApiServiceHelper adempimentiEstensioniAllegatiApiServiceHelper;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentiEstensioniAllegati(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<AdempimentoEstensioneAllegatoDTO> listAdempimentiEstensioniAllegato = new ArrayList<>();
        try {
            listAdempimentiEstensioniAllegato = adempimentiEstensioniAllegatiApiServiceHelper.getAdempimentiEstensioniAllegati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return Response.ok(listAdempimentiEstensioniAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentoEstensioneAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        List<AdempimentoEstensioneAllegatoDTO> listAdempimentiEstensioniAllegato = new ArrayList<>();
        try {
            listAdempimentiEstensioniAllegato = adempimentiEstensioniAllegatiApiServiceHelper.getAdempimentoEstensioneAllegatoByIdAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
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
        return Response.ok(listAdempimentiEstensioniAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentoEstensioneAllegatoByCodAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logger.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByCodAdempimento] BEGIN");
        logBeginInfo(className,"Parametro in input codAdempimento [" + codAdempimento + "]");
        List<AdempimentoEstensioneAllegatoDTO> listAdempimentiEstensioniAllegato = new ArrayList<>();
        try {
            listAdempimentiEstensioniAllegato = adempimentiEstensioniAllegatiApiServiceHelper.getAdempimentoEstensioneAllegatoByCodAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento);
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
        return Response.ok(listAdempimentiEstensioniAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();

    }

}