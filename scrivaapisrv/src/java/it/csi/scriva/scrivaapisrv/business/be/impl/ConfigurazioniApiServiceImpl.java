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

import it.csi.scriva.scrivaapisrv.business.be.ConfigurazioniApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.ConfigurazioniApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigurazioniApiServiceImpl extends AbstractApiServiceImpl implements ConfigurazioniApi {

    @Autowired
    ConfigurazioniApiServiceHelper configurazioniApiServiceHelper;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getConfigurazioni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[ConfigurazioniApiServiceImpl::getConfigurazioni] BEGIN");
        List<ConfigurazioneDTO> listConfigurazioni = new ArrayList<>();
        try {
            listConfigurazioni = configurazioniApiServiceHelper.getConfigurazioni(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[ConfigurazioniApiServiceImpl::getConfigurazioni] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[ConfigurazioniApiServiceImpl::getConfigurazioni] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[ConfigurazioniApiServiceImpl::getConfigurazioni] END");
        }
        return Response.ok(listConfigurazioni).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * @param key             key
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getConfigurazioneByKey(String key, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[ConfigurazioniApiServiceImpl::getConfigurazioneByKey] BEGIN");
        LOGGER.debug("[ConfigurazioniApiServiceImpl::getConfigurazioneByKey] Parametro in input key [" + key + "]");
        List<ConfigurazioneDTO> listConfigurazioni = new ArrayList<>();
        try {
            listConfigurazioni = configurazioniApiServiceHelper.getConfigurazioneByKey(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), key);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[ConfigurazioniApiServiceImpl::getConfigurazioneByKey] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[ConfigurazioniApiServiceImpl::getConfigurazioneByKey] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[ConfigurazioniApiServiceImpl::getConfigurazioneByKey] END");
        }
        return Response.ok(listConfigurazioni).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }
}