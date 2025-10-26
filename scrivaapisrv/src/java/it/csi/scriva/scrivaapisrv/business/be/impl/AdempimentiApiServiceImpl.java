/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package it.csi.scriva.scrivaapisrv.business.be.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaapisrv.business.be.AdempimentiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AdempimentiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdempimentiApiServiceImpl extends AbstractApiServiceImpl implements AdempimentiApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    @Autowired
    AdempimentiApiServiceHelper adempimentiApiServiceHelper;

    /**
     * Gets adempimenti.
     *
     * @param xRequestAuth       the x request auth
     * @param xRequestId         the x request id
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the adempimenti
     */
    @Override
    public Response getAdempimenti(String xRequestAuth, String xRequestId, Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idAmbito [" + idAmbito + "] - codAmbito [" + codAmbito + "] - idTipoAdempimento [" + idTipoAdempimento + "] - codTipoAdempimento [" + codTipoAdempimento + "] - codAdempimento [" + codAdempimento + "] - idCompilante [" + idCompilante + "]";
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<AdempimentoExtendedDTO> adempimentoList = new ArrayList<>();
        try {
            adempimentoList = adempimentiApiServiceHelper.getAdempimenti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAmbito, codAmbito, idTipoAdempimento, codTipoAdempimento, codAdempimento, idCompilante);
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
        return Response.ok(adempimentoList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * adempimento per id
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentoById(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiApiServiceImpl::getAdempimentoById] BEGIN");
        LOGGER.debug("[AdempimentiApiServiceImpl::getAdempimentoById] Parametro in input idAdempimento [" + idAdempimento + "]");
        List<AdempimentoExtendedDTO> listAdempimenti = new ArrayList<>();
        try {
            listAdempimenti = adempimentiApiServiceHelper.getAdempimentoById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiApiServiceImpl::getAdempimentoById] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiApiServiceImpl::getAdempimentoById] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiApiServiceImpl::getAdempimentoById] END");
        }
        return Response.ok(listAdempimenti).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}