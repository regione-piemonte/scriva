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

import it.csi.scriva.scrivaapisrv.business.be.IstanzaVincoliAutorizzazioniApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.IstanzaVincoliAutorizzazioniApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaVincoloAutExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class IstanzaVincoliAutorizzazioniApiServiceImpl extends AbstractApiServiceImpl implements IstanzaVincoliAutorizzazioniApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    @Autowired
    IstanzaVincoliAutorizzazioniApiServiceHelper istanzaVincoliAutorizzazioniApiServiceHelper;

    /**
     * Load istanza vincolo autorizzazione by istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getIstanzaVincoloAutorizzazioneByIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<IstanzaVincoloAutExtendedDTO> istanzaVincoloAutList = new ArrayList<>();
        try {
            istanzaVincoloAutList = istanzaVincoliAutorizzazioniApiServiceHelper.getIstanzaVincoloAutorizzazioneByIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
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
        return Response.ok(istanzaVincoloAutList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Save istanza vincolo autorizzazione response.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response saveIstanzaVincoloAutorizzazione(IstanzaVincoloAutExtendedDTO istanzaVincoloAut, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input istanzaVincoloAut:\n" + istanzaVincoloAut + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        IstanzaVincoloAutExtendedDTO dto = new IstanzaVincoloAutExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaVincoloAut.setGestAttoreIns(user.getCodFisc());
        try {
            dto = istanzaVincoliAutorizzazioniApiServiceHelper.saveIstanzaVincoloAutorizzazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaVincoloAut);
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
        return getResponseWithSharedHeaders(dto, istanzaVincoliAutorizzazioniApiServiceHelper, 201);
    }

    /**
     * Update istanza vincolo autorizzazione response.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response updateIstanzaVincoloAutorizzazione(IstanzaVincoloAutExtendedDTO istanzaVincoloAut, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input istanzaVincoloAut:\n" + istanzaVincoloAut + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        IstanzaVincoloAutExtendedDTO dto = new IstanzaVincoloAutExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaVincoloAut.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = istanzaVincoliAutorizzazioniApiServiceHelper.updateIstanzaVincoloAutorizzazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaVincoloAut);
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
        return getResponseWithSharedHeaders(dto, istanzaVincoliAutorizzazioniApiServiceHelper, 200);
    }

    /**
     * Delete istanza vincolo autorizzazione response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response deleteIstanzaVincoloAutorizzazione(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input uid: [" + uid + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        Boolean resp = Boolean.FALSE;
        try {
            resp = istanzaVincoliAutorizzazioniApiServiceHelper.deleteIstanzaVincoloAutorizzazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
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
        return Response.noContent().build();
    }
}