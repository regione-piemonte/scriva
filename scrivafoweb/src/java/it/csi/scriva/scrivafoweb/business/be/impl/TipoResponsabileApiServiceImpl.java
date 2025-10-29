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

import it.csi.scriva.scrivafoweb.business.be.IstanzeApi;
import it.csi.scriva.scrivafoweb.business.be.TipoResponsabileApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.IstanzeApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.TipoResponsabileApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Istanze api service.
 */
@Component
public class TipoResponsabileApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements TipoResponsabileApi {

    private final String className = this.getClass().getSimpleName();
    
    @Autowired
    TipoResponsabileApiServiceHelper tipoResponsabileApiServiceHelper;

    /**
     * Gets tipo responsabile by id istanza.
     *
     * @param idIstanza    the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the tipo responsabile by id istanza
     * @throws GenericException the generic exception
     */
    @Override
    public Response getTipoResponsabile(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        return getResponseWithSharedHeaders(className, tipoResponsabileApiServiceHelper.getTipoResponsabile(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza), tipoResponsabileApiServiceHelper, 200);
    }

 
}