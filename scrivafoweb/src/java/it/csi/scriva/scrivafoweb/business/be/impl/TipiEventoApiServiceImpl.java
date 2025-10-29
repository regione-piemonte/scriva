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

import it.csi.scriva.scrivafoweb.business.be.TipiEventoApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.TipiEventoServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoEventoExtendedDTO;
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
public class TipiEventoApiServiceImpl extends AbstractApiServiceImpl implements TipiEventoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    TipiEventoServiceHelper tipiEventoServiceHelper;

    /**
     * Gets tipi evento.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the tipi evento
     */
    @Override
    public Response getTipiEvento(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<TipoEventoExtendedDTO> tipoEventoList = new ArrayList<>();
        try {
            tipoEventoList = tipiEventoServiceHelper.getTipiEvento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, e);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(tipoEventoList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }
}