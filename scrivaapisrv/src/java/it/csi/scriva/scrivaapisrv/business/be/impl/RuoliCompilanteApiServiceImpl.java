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

import it.csi.scriva.scrivaapisrv.business.be.RuoliCompilanteApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.RuoliCompilanteApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.RuoloCompilanteDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class RuoliCompilanteApiServiceImpl extends AbstractApiServiceImpl implements RuoliCompilanteApi {

    @Autowired
    private RuoliCompilanteApiServiceHelper ruoliCompilanteApiServiceHelper;

    @Override
    public Response getRuoliCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoliCompilante] BEGIN");
        List<RuoloCompilanteDTO> list = new ArrayList<RuoloCompilanteDTO>();
        try {
            list = ruoliCompilanteApiServiceHelper.getRuoliCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoliCompilante] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoliCompilante] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoliCompilante] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getRuoloCompilante(Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloCompilante] BEGIN");
        LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloCompilante] Parametro in input idRuoloCompilante [" + idRuoloCompilante + "]");
        List<RuoloCompilanteDTO> list = new ArrayList<RuoloCompilanteDTO>();
        try {
            list = ruoliCompilanteApiServiceHelper.getRuoloCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloCompilante);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoloCompilante] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoloCompilante] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloCompilante] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getRuoloCompilanteByCode(String codRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloCompilanteByCode] BEGIN");
        LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloCompilanteByCode] Parametro in input codRuoloCompilante [" + codRuoloCompilante + "]");
        List<RuoloCompilanteDTO> list = new ArrayList<RuoloCompilanteDTO>();
        try {
            list = ruoliCompilanteApiServiceHelper.getRuoloCompilanteByCode(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codRuoloCompilante);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoloCompilanteByCode] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoloCompilanteByCode] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoloCompilanteByCode] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getRuoliCompilanteByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoliCompilanteByAdempimento] BEGIN");
        LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoliCompilanteByAdempimento] Parametro in input idAdempimento [" + idAdempimento + "]");
        List<RuoloCompilanteDTO> list = new ArrayList<RuoloCompilanteDTO>();
        try {
            list = ruoliCompilanteApiServiceHelper.getRuoliCompilanteByAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoliCompilanteByAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[RuoliCompilanteApiServiceImpl::getRuoliCompilanteByAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[RuoliCompilanteApiServiceImpl::getRuoliCompilanteByAdempimento] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}