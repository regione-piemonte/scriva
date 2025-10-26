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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneGeoRefExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SearchOggettoDTO;
import it.csi.scriva.scrivaapisrv.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class OggettiIstanzaApiServiceHelper extends AbstractServiceHelper {

    public OggettiIstanzaApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<OggettoIstanzaUbicazioneExtendedDTO> getOggettiIstanza(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::getOggettiIstanza] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti-istanza";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::getOggettiIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<OggettoIstanzaUbicazioneExtendedDTO>>() {});
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::getOggettiIstanza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceHelper::getOggettiIstanza] END");
        }
        return result;
    }

    public List<OggettoIstanzaUbicazioneExtendedDTO> getOggettoIstanzaById(MultivaluedMap<String, Object> requestHeaders, Long idOggetto) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaById] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti-istanza/id/" + idOggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaById] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<OggettoIstanzaUbicazioneExtendedDTO>>() {});
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaById] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaById] END");
        }
        return result;
    }

    public List<OggettoIstanzaUbicazioneExtendedDTO> getOggettoIstanzaByUID(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaByUID] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti-istanza/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaByUID] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<OggettoIstanzaUbicazioneExtendedDTO>>() {});
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaByUID] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceHelper::getOggettoIstanzaByUID] END");
        }
        return result;
    }

    public List<OggettoIstanzaUbicazioneExtendedDTO> searchOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, SearchOggettoDTO searchOggettoIstanza) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::searchOggettoIstanza] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti-istanza/search";
        try {
            Entity<SearchOggettoDTO> entity = Entity.json(searchOggettoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::searchOggettoIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<OggettoIstanzaUbicazioneExtendedDTO>>() {});
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::searchOggettoIstanza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceHelper::searchOggettoIstanza] END");
        }
        return result;
    }

    public OggettoIstanzaUbicazioneExtendedDTO saveOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, OggettoIstanzaUbicazioneExtendedDTO oggetto) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::saveOggettoIstanza] BEGIN");
        OggettoIstanzaUbicazioneExtendedDTO result = new OggettoIstanzaUbicazioneExtendedDTO();
        String targetUrl = this.endpointBase + "/oggetti-istanza";
        try {
            Entity<OggettoIstanzaUbicazioneExtendedDTO> entity = Entity.json(oggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::saveOggettoIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<OggettoIstanzaUbicazioneExtendedDTO>() {});
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::saveOggettoIstanza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::saveOggettoIstanza] END");
        return result;
    }

    public OggettoIstanzaUbicazioneExtendedDTO updateOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, OggettoIstanzaUbicazioneExtendedDTO oggetto) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::updateOggettoIstanza] BEGIN");
        OggettoIstanzaUbicazioneExtendedDTO result = new OggettoIstanzaUbicazioneExtendedDTO();
        String targetUrl = this.endpointBase + "/oggetti-istanza";
        try {
            Entity<OggettoIstanzaUbicazioneExtendedDTO> entity = Entity.json(oggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::updateOggettoIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<OggettoIstanzaUbicazioneExtendedDTO>() {});
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::updateOggettoIstanza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        }
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::updateOggettoIstanza] END");
        return result;
    }

    public Boolean deleteOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::deleteOggettoIstanza] BEGIN");
        String targetUrl = this.endpointBase + "/oggetti-istanza/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::deleteOggettoIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::deleteOggettoIstanza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceHelper::deleteOggettoIstanza] END");
        }
    }

    public List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> verificaGeoreferenziazione(MultivaluedMap<String, Object> requestHeaders, String codAdempimento, Long idIstanza) throws GenericException {
        LOGGER.debug("[OggettiIstanzaApiServiceHelper::verificaGeoreferenziazione] BEGIN");
        List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/oggetti-istanza/adempimento/" + codAdempimento + "/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[OggettiIstanzaApiServiceHelper::verificaGeoreferenziazione] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<OggettoIstanzaUbicazioneGeoRefExtendedDTO>>() {});
        } catch (ProcessingException e) {
            LOGGER.error("[OggettiIstanzaApiServiceHelper::verificaGeoreferenziazione] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[OggettiIstanzaApiServiceHelper::verificaGeoreferenziazione] END");
        }
        return result;
    }
}