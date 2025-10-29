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

import it.csi.scriva.scrivafoweb.business.be.ProvinceApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.ProvinceApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ComuneOldDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ProvinciaOldDTO;
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
public class ProvinceApiServiceImpl extends AbstractApiServiceImpl implements ProvinceApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private ProvinceApiServiceHelper provinceApiServiceHelper;

    @Override
    public Response getProvince(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<ProvinciaOldDTO> listSoggetti = new ArrayList<>();
        try {
            listSoggetti = provinceApiServiceHelper.getProvince(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getComuniByProvincia(String siglaProvincia, String fields, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<ComuneOldDTO> listSoggetti = new ArrayList<>();
        try {
            listSoggetti = provinceApiServiceHelper.getComuniByProvincia(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), siglaProvincia, fields);
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
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

}