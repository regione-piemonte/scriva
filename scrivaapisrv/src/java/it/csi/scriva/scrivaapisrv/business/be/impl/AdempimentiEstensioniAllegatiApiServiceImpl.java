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

import it.csi.scriva.scrivaapisrv.business.be.AdempimentiEstensioniAllegatiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AdempimentiEstensioniAllegatiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoEstensioneAllegatoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdempimentiEstensioniAllegatiApiServiceImpl extends AbstractApiServiceImpl implements AdempimentiEstensioniAllegatiApi {

    @Autowired
    AdempimentiEstensioniAllegatiApiServiceHelper adempimentiEstensioniAllegatiApiServiceHelper;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentiEstensioniAllegati(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentiEstensioniAllegati] BEGIN");
        List<AdempimentoEstensioneAllegatoDTO> listAdempimentiEstensioniAllegato = new ArrayList<>();
        try {
            listAdempimentiEstensioniAllegato = adempimentiEstensioniAllegatiApiServiceHelper.getAdempimentiEstensioniAllegati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentiEstensioniAllegati] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentiEstensioniAllegati] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentiEstensioniAllegati] END");
        }
        return Response.ok(listAdempimentiEstensioniAllegato).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentoEstensioneAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByIdAdempimento] BEGIN");
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByIdAdempimento] Parametro in input idAdempimento [" + idAdempimento + "]");
        List<AdempimentoEstensioneAllegatoDTO> listAdempimentiEstensioniAllegato = new ArrayList<>();
        try {
            listAdempimentiEstensioniAllegato = adempimentiEstensioniAllegatiApiServiceHelper.getAdempimentoEstensioneAllegatoByIdAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByIdAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByIdAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByIdAdempimento] END");
        }
        return Response.ok(listAdempimentiEstensioniAllegato).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getAdempimentoEstensioneAllegatoByCodAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByCodAdempimento] BEGIN");
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByCodAdempimento] Parametro in input codAdempimento [" + codAdempimento + "]");
        List<AdempimentoEstensioneAllegatoDTO> listAdempimentiEstensioniAllegato = new ArrayList<>();
        try {
            listAdempimentiEstensioniAllegato = adempimentiEstensioniAllegatiApiServiceHelper.getAdempimentoEstensioneAllegatoByCodAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByCodAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByCodAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceImpl::getAdempimentoEstensioneAllegatoByCodAdempimento] END");
        }
        return Response.ok(listAdempimentiEstensioniAllegato).header(HttpHeaders.CONTENT_ENCODING, "identity").build();

    }

}