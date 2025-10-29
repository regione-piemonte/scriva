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

import it.csi.scriva.scrivafoweb.business.be.TipiNaturaGiuridicaApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.TipiNaturaGiuridicaApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoNaturaGiuridicaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class TipiNaturaGiuridicaApiServiceImpl extends AbstractApiServiceImpl implements TipiNaturaGiuridicaApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    TipiNaturaGiuridicaApiServiceHelper tipiNaturaGiuridicaApiServiceHelper;

    @Override
    public Response getTipiNaturaGiuridica(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<TipoNaturaGiuridicaDTO> listTipiNaturaGiuridica = new ArrayList<TipoNaturaGiuridicaDTO>();
        try {
            listTipiNaturaGiuridica = tipiNaturaGiuridicaApiServiceHelper.getTipiNaturaGiuridica(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return Response.ok(listTipiNaturaGiuridica).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getTipoNaturaGiuridica(Long idTipoNaturaGiuridica, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoNaturaGiuridica);
        List<TipoNaturaGiuridicaDTO> listTipiNaturaGiuridica = new ArrayList<TipoNaturaGiuridicaDTO>();
        try {
            listTipiNaturaGiuridica = tipiNaturaGiuridicaApiServiceHelper.getTipoNaturaGiuridica(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTipoNaturaGiuridica);
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
        return Response.ok(listTipiNaturaGiuridica).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

}