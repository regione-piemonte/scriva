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

import it.csi.scriva.scrivafoweb.business.be.IstanzaVincoliAutorizzazioniApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.IstanzaVincoliAutorizzazioniApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaVincoloAutExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Istanza vincoli autorizzazioni api service.
 */
@Component
public class IstanzaVincoliAutorizzazioniApiServiceImpl extends ProxyApiServiceImpl implements IstanzaVincoliAutorizzazioniApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Istanza vincoli autorizzazioni api service helper.
     */
    @Autowired
    IstanzaVincoliAutorizzazioniApiServiceHelper istanzaVincoliAutorizzazioniApiServiceHelper;

    /**
     * Load istanza vincolo autorizzazione by istanza response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param codVincolo      the cod vincolo
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     */
    @Override
    public Response loadIstanzaVincoloAutorizzazioneByIstanza(String xRequestAuth, String xRequestId, Long idIstanza, String codVincolo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponse(uriInfo.getPath(), Constants.REQ_GET, securityContext, httpHeaders, httpRequest, uriInfo);
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
        logBeginInfo(className, istanzaVincoloAut);
        IstanzaVincoloAutExtendedDTO dto = new IstanzaVincoloAutExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaVincoloAut.setGestAttoreIns(user.getCodFisc());
        try {
            dto = istanzaVincoliAutorizzazioniApiServiceHelper.saveIstanzaVincoloAutorizzazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaVincoloAut);
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
        logBeginInfo(className, istanzaVincoloAut);
        IstanzaVincoloAutExtendedDTO dto = new IstanzaVincoloAutExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaVincoloAut.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = istanzaVincoliAutorizzazioniApiServiceHelper.updateIstanzaVincoloAutorizzazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaVincoloAut);
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
        logBeginInfo(className, (Object) uid);
        try {
            istanzaVincoliAutorizzazioniApiServiceHelper.deleteIstanzaVincoloAutorizzazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
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
        return Response.noContent().build();
    }
}