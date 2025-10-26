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

import it.csi.scriva.scrivaapisrv.business.be.AdempimentiRuoliCompilanteAPI;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AdempimentiRuoliCompilanteAPIServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoRuoloCompilanteExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdempimentiRuoliCompilanteAPIServiceimpl extends AbstractApiServiceImpl
        implements AdempimentiRuoliCompilanteAPI {

    @Autowired
    private AdempimentiRuoliCompilanteAPIServiceHelper adempimentiRuoliCompilanteAPIServiceHelper;

    @Override
    public Response getAdempimentiRuoliCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilante] BEGIN");
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<AdempimentoRuoloCompilanteExtendedDTO>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilante] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilante] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilante] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getAdempimentiRuoliCompilanteByRuoloCompilante(Long idRuoloCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByRuoloCompilante] BEGIN");
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByRuoloCompilante] Parametro in input idRuoloCompilante [" + idRuoloCompilante + "]");
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<AdempimentoRuoloCompilanteExtendedDTO>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilanteByRuoloCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloCompilante);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByRuoloCompilante] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByRuoloCompilante] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByRuoloCompilante] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getAdempimentiRuoliCompilanteByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByAdempimento] BEGIN");
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByAdempimento] Parametro in input idAdempimento [" + idAdempimento + "]");
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<AdempimentoRuoloCompilanteExtendedDTO>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilanteByAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentiRuoliCompilanteByAdempimento] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(Long idRuoloCompilante, Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento] BEGIN");
        LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento] Parametro in input idRuoloCompilante [" + idRuoloCompilante + "] - idAdempimento [" + idAdempimento + "]");
        List<AdempimentoRuoloCompilanteExtendedDTO> list = new ArrayList<AdempimentoRuoloCompilanteExtendedDTO>();
        try {
            list = adempimentiRuoliCompilanteAPIServiceHelper.getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idRuoloCompilante, idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiRuoliCompilanteAPIServiceimpl::getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}