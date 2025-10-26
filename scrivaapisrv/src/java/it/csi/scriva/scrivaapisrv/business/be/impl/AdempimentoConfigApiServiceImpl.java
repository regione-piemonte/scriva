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

import it.csi.scriva.scrivaapisrv.business.be.AdempimentiConfigApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.AdempimentiConfigApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoConfigDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdempimentoConfigApiServiceImpl extends AbstractApiServiceImpl implements AdempimentiConfigApi {

    @Autowired
    AdempimentiConfigApiServiceHelper adempimentoConfigApiServiceHelper;

    @Override
    public Response getAdempimentiConfig(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentiConfig] BEGIN");
        List<AdempimentoConfigDTO> listAdempimentoConfig = new ArrayList<>();
        try {
            listAdempimentoConfig = adempimentoConfigApiServiceHelper.getAdempimentiConfig(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentiConfig] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentiConfig] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentiConfig] END");
        }
        return Response.ok(listAdempimentoConfig).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getAdempimentoConfigByAdempimento(String desAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimento] BEGIN");
        LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimento] Parametro in input desAdempimento [" + desAdempimento + "]");
        List<AdempimentoConfigDTO> listAdempimentoConfig = new ArrayList<>();
        try {
            listAdempimentoConfig = adempimentoConfigApiServiceHelper.getAdempimentoConfigByAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), desAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimento] END");
        }
        return Response.ok(listAdempimentoConfig).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getAdempimentoConfigByAdempimentoInformazione(String desAdempimento, String infoScriva, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazione] BEGIN");
        LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazione] Parametro in input desAdempimento [" + desAdempimento + "] - infoScriva [" + infoScriva + "]");
        List<AdempimentoConfigDTO> listAdempimentoConfig = new ArrayList<>();
        try {
            listAdempimentoConfig = adempimentoConfigApiServiceHelper.getAdempimentoConfigByAdempimentoInformazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), desAdempimento, infoScriva);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazione] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazione] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazione] END");
        }
        return Response.ok(listAdempimentoConfig).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getAdempimentoConfigByAdempimentoInformazioneChiave(String desAdempimento, String infoScriva, String chiave, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazioneChiave] BEGIN");
        LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazioneChiave] Parametro in input desAdempimento [" + desAdempimento + "] - infoScriva [" + infoScriva + "] - chiave [" + chiave + "]");
        List<AdempimentoConfigDTO> listAdempimentoConfig = new ArrayList<>();
        try {
            listAdempimentoConfig = adempimentoConfigApiServiceHelper.getAdempimentoConfigByAdempimentoInformazioneChiave(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), desAdempimento, infoScriva, chiave);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazioneChiave] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazioneChiave] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[AdempimentoConfigApiServiceImpl::getAdempimentoConfigByAdempimentoInformazioneChiave] END");
        }
        return Response.ok(listAdempimentoConfig).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}