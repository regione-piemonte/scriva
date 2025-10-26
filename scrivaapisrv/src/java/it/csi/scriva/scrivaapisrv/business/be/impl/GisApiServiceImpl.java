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

import it.csi.scriva.scrivaapisrv.business.be.GisApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.GisApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.GeecoUrlDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
public class GisApiServiceImpl extends AbstractApiServiceImpl implements GisApi {

    @Autowired
    private GisApiServiceHelper gisApiServiceHelper;

    @Override
    public Response getGeecoUrl(Integer idRuoloApplicativo, Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[GisApiServiceImpl::getGeecoUrl] BEGIN");
        GeecoUrlDTO result = new GeecoUrlDTO();
        try {
            result = gisApiServiceHelper.getGeecoUrl(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloApplicativo, idOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[GisApiServiceImpl::getGeecoUrl] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[GisApiServiceImpl::getGeecoUrl] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[GisApiServiceImpl::getGeecoUrl] END");
        }
        return Response.ok(result).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}