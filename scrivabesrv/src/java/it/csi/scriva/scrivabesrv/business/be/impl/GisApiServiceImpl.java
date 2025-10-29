/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.GisApi;
import it.csi.scriva.scrivabesrv.business.be.service.GeecoService;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiIstanzaService;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoUrlDTO;
import it.csi.scriva.scrivabesrv.dto.GeecoUrlReqDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaGeecoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Gis api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GisApiServiceImpl extends BaseApiServiceImpl implements GisApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Geeco service.
     */
    @Autowired
    GeecoService geecoService;

    /**
     * The Oggetti istanza service.
     */
    @Autowired
    OggettiIstanzaService oggettiIstanzaService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Response getGeecoUrl(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, int idRuoloApplicativo, Long idOggettoIstanza) {
        logBeginInfo(className, "idRuoloApplicativo [" + idRuoloApplicativo + "] - idOggettoIstanza [" + idOggettoIstanza + "]");
        OggettoIstanzaGeecoDTO oggetto = oggettiIstanzaService.getOggettoIstanzaGeeco(idOggettoIstanza);

        Response response = setAttoreRight(httpHeaders, oggetto.getIdIstanza(), Boolean.FALSE);
        if (response != null) {
            return response;
        }
        GeecoUrlDTO geecoUrl = new GeecoUrlDTO();
        geecoUrl.setUrl(geecoService.getGeecoUrl(idOggettoIstanza, oggetto, attoreScriva));

        oggettiIstanzaService.updateFlgGeoModificatoByIdOggIst(idOggettoIstanza, Boolean.FALSE);
        logEnd(className);
        return Response.ok(geecoUrl).build();
    }

    /**
     * Gets url.
     *
     * @param geecoUrlReq     the geeco url req dto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the url
     */
    @Override
    public Response getUrl(GeecoUrlReqDTO geecoUrlReq, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, geecoUrlReq);

        ErrorDTO error = geecoService.validate(geecoUrlReq);
        if (error != null) {
            return getResponseError(className, error);
        }

        Response response = setAttoreRight(httpHeaders, geecoUrlReq.getIdIstanza(), Boolean.FALSE);
        if (response != null) {
            return response;
        }
        GeecoUrlDTO geecoUrl = geecoService.getUrl(geecoUrlReq, attoreScriva);

        oggettiIstanzaService.updateFlgGeoModificatoByIdIst(geecoUrlReq.getIdIstanza(), Boolean.FALSE);
        return Response.ok(geecoUrl).build();
    }


}