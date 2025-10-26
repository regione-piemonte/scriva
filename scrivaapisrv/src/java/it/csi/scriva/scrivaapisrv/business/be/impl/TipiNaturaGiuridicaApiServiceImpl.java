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

import it.csi.scriva.scrivaapisrv.business.be.TipiNaturaGiuridicaApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.TipiNaturaGiuridicaApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoNaturaGiuridicaDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class TipiNaturaGiuridicaApiServiceImpl extends AbstractApiServiceImpl implements TipiNaturaGiuridicaApi {

    @Autowired
    TipiNaturaGiuridicaApiServiceHelper tipiNaturaGiuridicaApiServiceHelper;

    @Override
    public Response getTipiNaturaGiuridica(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[TipiNaturaGiuridicaApiServiceImpl::getTipiNaturaGiuridica] BEGIN");

        List<TipoNaturaGiuridicaDTO> listTipiNaturaGiuridica = new ArrayList<TipoNaturaGiuridicaDTO>();
        try {
            listTipiNaturaGiuridica = tipiNaturaGiuridicaApiServiceHelper.getTipiNaturaGiuridica(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[TipiNaturaGiuridicaApiServiceImpl::getTipiNaturaGiuridica] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[TipiNaturaGiuridicaApiServiceImpl::getTipiNaturaGiuridica] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[TipiNaturaGiuridicaApiServiceImpl::getTipiNaturaGiuridica] END");
        }
        return Response.ok(listTipiNaturaGiuridica).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getTipoNaturaGiuridica(Long idTipoNaturaGiuridica, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[TipiNaturaGiuridicaApiServiceImpl::getTipoNaturaGiuridica] BEGIN");
        LOGGER.debug("[TipiNaturaGiuridicaApiServiceImpl::getTipoNaturaGiuridica] Parametro in input idTipoNaturaGiuridica [" + idTipoNaturaGiuridica + "]");
        List<TipoNaturaGiuridicaDTO> listTipiNaturaGiuridica = new ArrayList<TipoNaturaGiuridicaDTO>();
        try {
            listTipiNaturaGiuridica = tipiNaturaGiuridicaApiServiceHelper.getTipoNaturaGiuridica(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTipoNaturaGiuridica);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[TipiNaturaGiuridicaApiServiceImpl::getTipoNaturaGiuridica] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[TipiNaturaGiuridicaApiServiceImpl::getTipoNaturaGiuridica] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[TipiNaturaGiuridicaApiServiceImpl::getTipoNaturaGiuridica] END");
        }
        return Response.ok(listTipiNaturaGiuridica).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}