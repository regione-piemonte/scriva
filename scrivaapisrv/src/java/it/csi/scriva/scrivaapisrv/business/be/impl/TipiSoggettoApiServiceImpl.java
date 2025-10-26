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

import it.csi.scriva.scrivaapisrv.business.be.TipiSoggettoApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.TipiSoggettoApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoSoggettoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class TipiSoggettoApiServiceImpl extends AbstractApiServiceImpl implements TipiSoggettoApi {

    @Autowired
    TipiSoggettoApiServiceHelper tipiSoggettoApiServiceHelper;

    @Override
    public Response getTipiSoggetto(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[TipiSoggettoApiServiceImpl::getTipiSoggetto] BEGIN");
        List<TipoSoggettoDTO> listTipiSoggetto = new ArrayList<>();
        try {
            listTipiSoggetto = tipiSoggettoApiServiceHelper.getTipiSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[TipiSoggettoApiServiceImpl::getTipiSoggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[TipiSoggettoApiServiceImpl::getTipiSoggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[TipiSoggettoApiServiceImpl::getTipiSoggetto] END");
        }
        return Response.ok(listTipiSoggetto).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getTipoSoggetto(Long idTipoSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[TipiSoggettoApiServiceImpl::getTipoSoggetto] BEGIN");
        LOGGER.debug("[TipiSoggettoApiServiceImpl::getTipoSoggetto] Parametro in input idTipoSoggetto [" + idTipoSoggetto + "]");
        List<TipoSoggettoDTO> listTipiSoggetto = new ArrayList<>();
        try {
            listTipiSoggetto = tipiSoggettoApiServiceHelper.getTipoSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTipoSoggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[TipiSoggettoApiServiceImpl::getTipoSoggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[TipiSoggettoApiServiceImpl::getTipoSoggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[TipiSoggettoApiServiceImpl::getTipiSoggetto] END");
        }
        return Response.ok(listTipiSoggetto).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}