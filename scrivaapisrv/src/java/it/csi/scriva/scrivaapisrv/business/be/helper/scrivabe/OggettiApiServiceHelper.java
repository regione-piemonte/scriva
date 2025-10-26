/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SearchOggettoDTO;

import java.util.ArrayList;
import java.util.List;

public class OggettiApiServiceHelper extends AbstractServiceHelper {

    public OggettiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<OggettoUbicazioneExtendedDTO> getOggetti(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::getOggetti] BEGIN");
        List<OggettoUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::getOggetti] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<OggettoUbicazioneExtendedDTO>> oggettiListType = new GenericType<List<OggettoUbicazioneExtendedDTO>>() {
            };
            result = resp.readEntity(oggettiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::getOggetti] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiApiServiceHelper::getOggetti] END");
        }
        return result;
    }

    public List<OggettoUbicazioneExtendedDTO> getOggettoById(MultivaluedMap<String, Object> requestHeaders, Long idOggetto) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::getOggettoById] BEGIN");
        List<OggettoUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti/id/" + idOggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::getOggettoById] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<OggettoUbicazioneExtendedDTO>> oggettiListType = new GenericType<List<OggettoUbicazioneExtendedDTO>>() {
            };
            result = resp.readEntity(oggettiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::getOggettoById] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiApiServiceHelper::getOggettoById] END");
        }
        return result;
    }

    public List<OggettoUbicazioneExtendedDTO> getOggettoByIdComune(MultivaluedMap<String, Object> requestHeaders, Long idComune) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::getOggettoByIdComune] BEGIN");
        List<OggettoUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti/id-comune/" + idComune;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::getOggettoByIdComune] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<OggettoUbicazioneExtendedDTO>> oggettiListType = new GenericType<List<OggettoUbicazioneExtendedDTO>>() {
            };
            result = resp.readEntity(oggettiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::getOggettoByIdComune] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiApiServiceHelper::getOggettoByIdComune] END");
        }
        return result;
    }

    public List<OggettoUbicazioneExtendedDTO> getOggettoByUID(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::getOggettoById] BEGIN");
        List<OggettoUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::getOggettoByUID] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<OggettoUbicazioneExtendedDTO>> soggettiListType = new GenericType<List<OggettoUbicazioneExtendedDTO>>() {
            };
            result = resp.readEntity(soggettiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::getOggettoById] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiApiServiceHelper::getOggettoById] END");
        }
        return result;
    }

    public List<OggettoUbicazioneExtendedDTO> searchOggetto(MultivaluedMap<String, Object> requestHeaders, SearchOggettoDTO searchOggetto) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::searchOggetto] BEGIN");
        List<OggettoUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti/search";
        try {
            Entity<SearchOggettoDTO> entity = Entity.json(searchOggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::searchOggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<OggettoUbicazioneExtendedDTO>> soggettiListType = new GenericType<List<OggettoUbicazioneExtendedDTO>>() {
            };
            result = resp.readEntity(soggettiListType);
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::searchOggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiApiServiceHelper::searchOggetto] END");
        }
        return result;
    }

    public OggettoUbicazioneExtendedDTO saveOggetto(MultivaluedMap<String, Object> requestHeaders, OggettoUbicazioneExtendedDTO oggetto) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::saveOggetto] BEGIN");
        OggettoUbicazioneExtendedDTO result = new OggettoUbicazioneExtendedDTO();
        String targetUrl = this.endpointBase + "/oggetti";
        try {
            Entity<OggettoUbicazioneExtendedDTO> entity = Entity.json(oggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::saveOggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<OggettoUbicazioneExtendedDTO> oggettoDTOType = new GenericType<OggettoUbicazioneExtendedDTO>() {
            };
            result = resp.readEntity(oggettoDTOType);
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::saveOggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        LOGGER.debug("[OggettiApiServiceHelper::saveOggetto] END");
        return result;
    }

    public OggettoUbicazioneExtendedDTO updateOggetto(MultivaluedMap<String, Object> requestHeaders, OggettoUbicazioneExtendedDTO oggetto) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::updateOggetto] BEGIN");
        OggettoUbicazioneExtendedDTO result = new OggettoUbicazioneExtendedDTO();
        String targetUrl = this.endpointBase + "/oggetti";
        try {
            Entity<OggettoUbicazioneExtendedDTO> entity = Entity.json(oggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::updateOggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<OggettoUbicazioneExtendedDTO> oggettoType = new GenericType<OggettoUbicazioneExtendedDTO>() {
            };
            result = resp.readEntity(oggettoType);
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::updateOggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        LOGGER.debug("[OggettiApiServiceHelper::updateOggetto] END");
        return result;
    }

    public Boolean deleteOggetto(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        LOGGER.debug("[OggettiApiServiceHelper::deleteOggetto] BEGIN");
        String targetUrl = this.endpointBase + "/oggetti/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiApiServiceHelper::deleteOggetto] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiApiServiceHelper::deleteOggetto] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiApiServiceHelper::deleteOggetto] END");
        }
    }
}