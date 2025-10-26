/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaapisrv.business.be.TipiEventoApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.TipiEventoApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoEventoExtendedDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class TipiEventoApiServiceImpl extends AbstractApiServiceImpl implements TipiEventoApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    @Autowired
    TipiEventoApiServiceHelper tipiEventoApiServiceHelper;

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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<TipoEventoExtendedDTO> tipoEventoList = new ArrayList<>();
        try {
            tipoEventoList = tipiEventoApiServiceHelper.getTipiEvento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), null);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(tipoEventoList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }
}