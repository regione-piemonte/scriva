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

import it.csi.scriva.scrivaapisrv.business.be.AdempimentiTipiAllegatoApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AdempimentiTipiAllegatoApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdempimentiTipiAllegatoApiServiceImpl extends AbstractApiServiceImpl implements AdempimentiTipiAllegatoApi {

    @Autowired
    AdempimentiTipiAllegatoApiServiceHelper adempimentiTipiAllegatoApiServiceHelper;

    @Override
    public Response getTipologieAllegato(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegato] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologieAllegato(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegato] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegato] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegato] END");
        }
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getTipologiaAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByIdAdempimento] BEGIN");
        LOGGER.debug("[AdempimentiApiServiceImpl::getAdempimentoById] Parametro in input idAdempimento [" + idAdempimento + "]");
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologiaAllegatoByIdAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByIdAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByIdAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByIdAdempimento] END");
        }
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getTipologiaAllegatoByCodeAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByCodeAdempimento] BEGIN");
        LOGGER.debug("[AdempimentiApiServiceImpl::getTipologiaAllegatoByCodeAdempimento] Parametro in input codAdempimento [" + codAdempimento + "]");
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologiaAllegatoByCodeAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByCodeAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByCodeAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologiaAllegatoByCodeAdempimento] END");
        }
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getTipologieAllegatoByCodeAdempimentoAndCodeCategoria(String codAdempimento, String codCategoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegatoByCodeAdempimentoAndCodeCategoria] BEGIN");
        LOGGER.debug("[AdempimentiApiServiceImpl::getTipologieAllegatoByCodeAdempimentoAndCodeCategoria] Parametro in input codAdempimento [" + codAdempimento + "] - codCategoria [" + codCategoria + "]");
        List<AdempimentoTipoAllegatoExtendedDTO> listAdempimentiTipiAllegato = new ArrayList<>();
        try {
            listAdempimentiTipiAllegato = adempimentiTipiAllegatoApiServiceHelper.getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento, codCategoria);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegatoByCodeAdempimentoAndCodeCategoria] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegatoByCodeAdempimentoAndCodeCategoria] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceImpl::getTipologieAllegatoByCodeAdempimentoAndCodeCategoria] END");
        }
        return Response.ok(listAdempimentiTipiAllegato).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }
}