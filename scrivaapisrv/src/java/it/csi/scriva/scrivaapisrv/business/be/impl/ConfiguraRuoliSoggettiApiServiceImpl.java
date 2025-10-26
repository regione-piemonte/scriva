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

import it.csi.scriva.scrivaapisrv.business.be.ConfiguraRuoliSoggettiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.ConfiguraRuoliSoggettiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ConfiguraRuoloSoggettoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfiguraRuoliSoggettiApiServiceImpl extends AbstractApiServiceImpl implements ConfiguraRuoliSoggettiApi {

    @Autowired
    private ConfiguraRuoliSoggettiApiServiceHelper configuraRuoliSoggettiApiServiceHelper;

    @Override
    public Response getConfiguraRuoliSoggetti(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggetti] BEGIN");
        List<ConfiguraRuoloSoggettoExtendedDTO> list = new ArrayList<ConfiguraRuoloSoggettoExtendedDTO>();
        try {
            list = configuraRuoliSoggettiApiServiceHelper.getConfiguraRuoliSoggetti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggetti] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggetti] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggetti] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getConfiguraRuoliSoggettiByRuoloCompilante(Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloCompilante] BEGIN");
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloCompilante] Parametro in input idRuoloCompilante [" + idRuoloCompilante + "]");
        List<ConfiguraRuoloSoggettoExtendedDTO> list = new ArrayList<ConfiguraRuoloSoggettoExtendedDTO>();
        try {
            list = configuraRuoliSoggettiApiServiceHelper.getConfiguraRuoliSoggettiByRuoloCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloCompilante);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloCompilante] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloCompilante] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloCompilante] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getConfiguraRuoliSoggettiByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByAdempimento] BEGIN");
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByAdempimento] Parametro in input idAdempimento [" + idAdempimento + "]");
        List<ConfiguraRuoloSoggettoExtendedDTO> list = new ArrayList<ConfiguraRuoloSoggettoExtendedDTO>();
        try {
            list = configuraRuoliSoggettiApiServiceHelper.getConfiguraRuoliSoggettiByAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByAdempimento] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getConfiguraRuoliSoggettiByRuoloSoggetto(Long idRuoloSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloSoggetto] BEGIN");
        LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloSoggetto] Parametro in input idRuoloSoggetto [" + idRuoloSoggetto + "]");
        List<ConfiguraRuoloSoggettoExtendedDTO> list = new ArrayList<ConfiguraRuoloSoggettoExtendedDTO>();
        try {
            list = configuraRuoliSoggettiApiServiceHelper.getConfiguraRuoliSoggettiByRuoloSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloSoggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloSoggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloSoggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[ConfiguraRuoliSoggettiApiServiceImpl::getConfiguraRuoliSoggettiByRuoloSoggetto] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}