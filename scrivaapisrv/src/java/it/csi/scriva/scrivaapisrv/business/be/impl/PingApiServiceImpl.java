/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import it.csi.scriva.scrivaapisrv.business.be.PingApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AllegatiIstanzaApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.PingApiServiceHelper;
import it.csi.scriva.scrivaapisrv.dto.FileFoDTO;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;
import it.csi.scriva.scrivaapisrv.filter.IrideIdAdapterFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class PingApiServiceImpl extends AbstractApiServiceImpl implements PingApi {

    @Autowired
    PingApiServiceHelper pingApiServiceHelper;

    @Autowired
    AllegatiIstanzaApiServiceHelper allegatiIstanzaApiServiceHelper;

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

        return Response.ok(out).header("Content-Disposition", "attachment; filename=MODULO_TOTALE_PROVAJSON.pdf").build();
    }

    @Override
    public Response getDocCosmo(String linkdocumento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        FileFoDTO fileFo = allegatiIstanzaApiServiceHelper.getAllegatiCosmo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), linkdocumento);
        return Response.ok(fileFo.getFile()).header("Content-Disposition", fileFo.getContentDispositionHeader()).build();
    }


}