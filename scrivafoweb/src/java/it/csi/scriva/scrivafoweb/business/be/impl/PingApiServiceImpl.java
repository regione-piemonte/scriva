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

import it.csi.scriva.scrivafoweb.business.be.PingApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.PingApiServiceHelper;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import it.csi.scriva.scrivafoweb.filter.IrideIdAdapterFilter;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class PingApiServiceImpl extends ProxyApiServiceImpl implements PingApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private PingApiServiceHelper pingApiServiceHelper;

    @Override
    public Response ping(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return Response.ok(pingApiServiceHelper.ping(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest))).build();
    }

    @Override
    public Response testSPID(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        UserInfo userInfo = (UserInfo) httpRequest.getSession().getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
        return Response.ok(userInfo).build();
    }

    @Override
    public Response getFasiFake(Boolean protetto, Boolean incidenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        List<String> fasiList = new ArrayList<>();

        if (protetto || incidenza) {
            fasiList.add("VALUTAZIONE");
        } else {
            fasiList.add("VALUTAZIONE PRELIMINARE");
            fasiList.add("VERIFICA");
            fasiList.add("SPECIFICAZIONE");
            fasiList.add("VALUTAZIONE");
            fasiList.add("OTTEMPERANZA");
            fasiList.add("VERIFICA DI OTTEMPERANZA");
        }

        return Response.ok(fasiList).build();
    }

    @Override
    public Response testPDF(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        byte[] out = pingApiServiceHelper.testPDF(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);

        return Response.ok(out).header(Constants.HEADER_CONTENT_DISPOSITION, "attachment; filename=MODULO_TOTALE_PROVAJSON.pdf").build();
    }

    /**
     * Gets json data.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the json data
     * @throws GenericException the generic exception
     */
    @Override
    public Response getJsonData(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws GenericException {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponse(uriInfo.getPath(), Constants.REQ_GET, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Manager notifiche response.
     *
     * @param idIstanza         the id istanza
     * @param codTipoevento     the cod tipoevento
     * @param codCanaleNotifica the cod canale notifica
     * @param rifCanaleNotifica the rif canale notifica
     * @param dataIntegrazione  the data integrazione
     * @param abilitaInvio      the abilita invio
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @param uriInfo           the uri info
     * @return response response
     */
    @Override
    public Response managerNotifiche(Long idIstanza, String codTipoevento, String codCanaleNotifica, String rifCanaleNotifica, Date dataIntegrazione, Boolean abilitaInvio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponse(uriInfo.getPath(), Constants.REQ_GET, securityContext, httpHeaders, httpRequest, uriInfo);
    }


}