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

import it.csi.scriva.scrivaapisrv.business.be.OggettiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.OggettiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoUbicazioneExtendedDTO;
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
public class OggettiApiServiceImpl extends AbstractApiServiceImpl implements OggettiApi {

    @Autowired
    private OggettiApiServiceHelper oggettiApiServiceHelper;

    @Override
    public Response getOggetti(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::getOggetti] BEGIN");
        List<OggettoUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiApiServiceHelper.getOggetti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::getOggetti] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::getOggetti] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::getOggetti] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getOggettoById(Long idOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::getOggettoById] BEGIN");
        LOGGER.debug("[OggettiApiServiceImpl::getOggettoById] Parametro in input idOggetto :\r\n " + idOggetto);
        List<OggettoUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiApiServiceHelper.getOggettoById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::getOggettoById] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::getOggettoById] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::getOggettoById] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getOggettoByIdComune(Long idComune, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::getOggettoByIdComune] BEGIN");
        LOGGER.debug("[OggettiApiServiceImpl::getOggettoByIdComune] Parametro in input idComune : " + idComune);
        List<OggettoUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiApiServiceHelper.getOggettoByIdComune(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idComune);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::getOggettoByIdComune] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::getOggettoByIdComune] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::getOggettoByIdComune] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getOggettoByUID(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::getOggettoByUID] BEGIN");
        LOGGER.debug("[OggettiApiServiceImpl::getOggettoByUID] Parametro in input uid :\r\n " + uid);
        List<OggettoUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiApiServiceHelper.getOggettoByUID(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::getOggettoByUID] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::getOggettoByUID] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::getOggettoByUID] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response searchOggetto(SearchOggettoDTO searchOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::searchOggetto] BEGIN");
        LOGGER.debug("[OggettiApiServiceImpl::searchOggetto] Parametro in input searchOggetto :\r\n " + searchOggetto.toString() + "\n");
        List<OggettoUbicazioneExtendedDTO> list = new ArrayList<>();
        try {
            list = oggettiApiServiceHelper.searchOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), searchOggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::searchOggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::searchOggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::searchOggetto] END");
        }
        return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response saveOggetto(OggettoUbicazioneExtendedDTO oggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::saveOggetto] BEGIN");
        LOGGER.debug("[OggettiApiServiceImpl::saveOggetto] Parametro in input oggetto :\r\n " + oggetto.toString());
        OggettoUbicazioneExtendedDTO dto = new OggettoUbicazioneExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggetto.setGestAttoreIns(user.getCodFisc());
        try {
            dto = oggettiApiServiceHelper.saveOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::saveOggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::saveOggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::saveOggetto] END");
        }
        return Response.ok(dto).status(201).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response updateOggetto(OggettoUbicazioneExtendedDTO oggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::updateOggetto] BEGIN");
        LOGGER.debug("[OggettiApiServiceImpl::updateOggetto] Parametro in input oggetto :\r\n " + oggetto.toString());
        OggettoUbicazioneExtendedDTO dto = new OggettoUbicazioneExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggetto.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = oggettiApiServiceHelper.updateOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggetto);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::updateOggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::updateOggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::updateOggetto] END");
        }
        return Response.ok(dto).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response deleteOggetto(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[OggettiApiServiceImpl::deleteOggetto] BEGIN");
        LOGGER.debug("[OggettiApiServiceImpl::deleteOggetto] Parametro in input uid [" + uid + "]");
        try {
            oggettiApiServiceHelper.deleteOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[OggettiApiServiceImpl::deleteOggetto] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[OggettiApiServiceImpl::deleteOggetto] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[OggettiApiServiceImpl::deleteOggetto] END");
        }
        return Response.noContent().build();
    }
}