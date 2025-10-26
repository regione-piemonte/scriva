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

import it.csi.scriva.scrivaapisrv.business.be.RuoliSoggettoApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.RuoliSoggettoApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.RuoloSoggettoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuoliSoggettoApiServiceImpl extends AbstractApiServiceImpl implements RuoliSoggettoApi {

    @Autowired
    private RuoliSoggettoApiServiceHelper ruoliSoggettoApiServiceHelper;

    @Override
    public Response getRuoliSoggetti(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliSoggettoApiServiceImpl::getRuoliSoggetti] BEGIN");
        List<RuoloSoggettoDTO> list = new ArrayList<RuoloSoggettoDTO>();
        try {
            list = ruoliSoggettoApiServiceHelper.getRuoliSoggetti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliSoggettoApiServiceImpl::getRuoliSoggetti] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliSoggettoApiServiceImpl::getRuoliSoggetti] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliSoggettoApiServiceImpl::getRuoliSoggetto] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getRuoloSoggetto(Long idRuoloSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliSoggettoApiServiceImpl::getRuoloSoggetto] BEGIN");
        LOGGER.debug("[RuoliSoggettoApiServiceImpl::getRuoloSoggetto] Parametro in input idRuoloSoggetto [" + idRuoloSoggetto + "]");
        List<RuoloSoggettoDTO> list = new ArrayList<RuoloSoggettoDTO>();
        try {
            list = ruoliSoggettoApiServiceHelper.getRuoloSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloSoggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliSoggettoApiServiceImpl::getRuoloSoggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoloSoggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloSoggetto] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento(Long idAdempimento, Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliSoggettoApiServiceImpl::loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento] BEGIN");
        LOGGER.debug("[RuoliSoggettoApiServiceImpl::loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento] Parametro in input idAdempimento [" + idAdempimento + "] - idRuoloCompilante [" + idRuoloCompilante + "]");
        List<RuoloSoggettoDTO> list = new ArrayList<RuoloSoggettoDTO>();
        try {
            list = ruoliSoggettoApiServiceHelper.getRuoliSoggettoByRuoloCompilanteAndAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloCompilante, idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliSoggettoApiServiceImpl::loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliCompilanteApiServiceImpl::loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceImpl::loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getRuoloSoggettoByCode(String codRuoloSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliSoggettoApiServiceImpl::getRuoloSoggettoByCode] BEGIN");
        LOGGER.debug("[RuoliSoggettoApiServiceImpl::getRuoloSoggettoByCode] Parametro in input codRuoloSoggetto [" + codRuoloSoggetto + "]");
        List<RuoloSoggettoDTO> list = new ArrayList<RuoloSoggettoDTO>();
        try {
            list = ruoliSoggettoApiServiceHelper.getRuoloSoggettoByCode(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codRuoloSoggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliSoggettoApiServiceImpl::getRuoloSoggettoByCode] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoloSoggettoByCode] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloSoggettoByCode] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}