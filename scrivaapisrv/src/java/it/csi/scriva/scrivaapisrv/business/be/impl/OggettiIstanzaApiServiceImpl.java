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

import it.csi.scriva.scrivaapisrv.business.be.OggettiIstanzaApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.OggettiIstanzaApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneGeoRefExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SearchOggettoDTO;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class OggettiIstanzaApiServiceImpl extends AbstractApiServiceImpl implements OggettiIstanzaApi {

    @Autowired
    private OggettiIstanzaApiServiceHelper oggettiIstanzaApiServiceHelper;

    @Override
    public Response getOggettiIstanza(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::getOggettiIstanza] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiIstanzaApiServiceHelper.getOggettiIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::getOggettiIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::getOggettiIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::getOggettiIstanza] END");
        }
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
        //return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getOggettoIstanzaById(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaById] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiIstanzaApiServiceHelper.getOggettoIstanzaById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaById] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaById] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaById] END");
        }
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response getOggettoIstanzaByUID(String uidOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaByUID] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiIstanzaApiServiceHelper.getOggettoIstanzaByUID(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uidOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaByUID] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaByUID] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaByUID] END");
        }
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response searchOggettoIstanza(SearchOggettoDTO searchOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::searchOggettoIstanza] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiIstanzaApiServiceHelper.searchOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), searchOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::searchOggettoIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::searchOggettoIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::searchOggettoIstanza] END");
        }
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response saveOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::saveOggettoIstanza] BEGIN");
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::saveOggettoIstanza] Parametro in input oggettoIstanza :\r\n " + oggettoIstanza.toString());
        OggettoIstanzaUbicazioneExtendedDTO dto = new OggettoIstanzaUbicazioneExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanza.setGestAttoreIns(user.getCodFisc());
        try {
            dto = oggettiIstanzaApiServiceHelper.saveOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::saveOggettoIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::saveOggettoIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::saveOggettoIstanza] END");
        }
        return getResponseWithSharedHeaders(dto, oggettiIstanzaApiServiceHelper, 201);
    }

    @Override
    public Response updateOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::updateOggettoIstanza] BEGIN");
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::updateOggettoIstanza] Parametro in input oggettoIstanza :\r\n " + oggettoIstanza.toString());
        OggettoIstanzaUbicazioneExtendedDTO dto = new OggettoIstanzaUbicazioneExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanza.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = oggettiIstanzaApiServiceHelper.updateOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::updateOggettoIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::updateOggettoIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::updateOggettoIstanza] END");
        }
        return getResponseWithSharedHeaders(dto, oggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response deleteOggettoIstanza(String uidOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::deleteOggettoIstanza] BEGIN");
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::deleteOggettoIstanza] Parametro in input uidOggettoIstanza [" + uidOggettoIstanza + "]");
        try {
            oggettiIstanzaApiServiceHelper.deleteOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uidOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::deleteOggettoIstanza] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::deleteOggettoIstanza] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::deleteOggettoIstanza] END");
        }
        return Response.noContent().build();
    }

    @Override
    public Response verificaGeoreferenziazione(String codAdempimento, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::verificaGeoreferenziazione] BEGIN");
        LOGGER.debug("[OggettiIstanzaApiServiceImpl::verificaGeoreferenziazione] Parametri in input codAdempimento [" + codAdempimento + "] - idIstanza [" + idIstanza + "]");
        List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> dto = null;
        try {
            dto = oggettiIstanzaApiServiceHelper.verificaGeoreferenziazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento, idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiIstanzaApiServiceImpl::verificaGeoreferenziazione] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiIstanzaApiServiceImpl::verificaGeoreferenziazione] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceImpl::verificaGeoreferenziazione] END");
        }
        return getResponseWithSharedHeaders(dto, oggettiIstanzaApiServiceHelper);
    }
}