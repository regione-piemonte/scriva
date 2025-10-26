/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import it.csi.scriva.scrivaapisrv.business.be.AmbitiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AmbitiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AmbitoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
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
public class AmbitiApiServiceImpl extends AbstractApiServiceImpl implements AmbitiApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    @Autowired
    AmbitiApiServiceHelper ambitiApiServiceHelper;

    /**
     * Gets ambiti.
     *
     * @param idAmbito        the id ambito
     * @param codAmbito       the cod ambito
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the ambiti
     */
    @Override
    public Response getAmbiti(Long idAmbito, String codAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idAmbito [" + idAmbito + "] - codAmbito [" + codAmbito + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<AmbitoDTO> listAmbiti = new ArrayList<AmbitoDTO>();
        try {
            listAmbiti = ambitiApiServiceHelper.getAmbiti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAmbito, codAmbito);
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
        return Response.ok(listAmbiti).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Gets ambito.
     *
     * @param idAmbito        the id ambito
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the ambito
     */
    @Override
    public Response getAmbito(Long idAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idAmbito [" + idAmbito + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<AmbitoDTO> listAmbiti = new ArrayList<AmbitoDTO>();
        try {
            listAmbiti = ambitiApiServiceHelper.getAmbito(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAmbito);
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
        return Response.ok(listAmbiti).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}