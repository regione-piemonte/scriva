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

import it.csi.scriva.scrivafoweb.business.be.GisApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.GisApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.GeecoUrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

@Component
public class GisApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements GisApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private GisApiServiceHelper gisApiServiceHelper;

    /**
     * Gets geeco url.
     *
     * @param idRuoloApplicativo the id ruolo applicativo
     * @param idOggettoIstanza   the id oggetto istanza
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the geeco url
     */
    @Override
    public Response getGeecoUrl(Integer idRuoloApplicativo, Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        GeecoUrlDTO result = new GeecoUrlDTO();
        try {
            result = gisApiServiceHelper.getGeecoUrl(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloApplicativo, idOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logError(className, e);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return Response.ok(result).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Gets url.
     *
     * @param geecoUrlReq     the geeco url req dto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the url
     * @throws Exception the exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public Response getUrl(Map<String, Object> geecoUrlReq, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, geecoUrlReq);
        return getResponsePOST(uriInfo.getPath(), geecoUrlReq, securityContext, httpHeaders, httpRequest, uriInfo);
    }

}