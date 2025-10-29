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

import it.csi.scriva.scrivafoweb.business.be.FunzionariApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.FunzionariApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.FunzionarioAutorizzatoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
public class FunzionariApiServiceImpl extends AbstractApiServiceImpl implements FunzionariApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    FunzionariApiServiceHelper funzionariApiServiceHelper;

    @Override
    public Response getFunzionari(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza "; // [" + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));

        FunzionarioAutorizzatoDTO funzionarioAutorizzato = null;
        try {
            funzionarioAutorizzato = funzionariApiServiceHelper.getFunzionarioByCf(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return Response.ok(funzionarioAutorizzato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }
}