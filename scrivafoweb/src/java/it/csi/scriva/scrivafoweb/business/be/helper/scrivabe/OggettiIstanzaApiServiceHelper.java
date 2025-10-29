/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneGeoRefExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneGeometrieExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchOggettoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Oggetti istanza api service helper.
 */
public class OggettiIstanzaApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/oggetti-istanza";

    /**
     * Instantiates a new Oggetti istanza api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public OggettiIstanzaApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets oggetti istanza.
     *
     * @param requestHeaders the request headers
     * @return the oggetti istanza
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaUbicazioneExtendedDTO> getOggettiIstanza(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        logger.debug("[OggettiIstanzaApiServiceHelper::getOggettiIstanza] BEGIN");
        String targetUrl = this.endpointBase;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto istanza by id.
     *
     * @param requestHeaders the request headers
     * @param idOggetto      the id oggetto
     * @return the oggetto istanza by id
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaUbicazioneGeometrieExtendedDTO> getOggettoIstanzaById(MultivaluedMap<String, Object> requestHeaders, Long idOggetto) throws GenericException {
        logBeginInfo(className, idOggetto);
        String targetUrl = this.endpointBase + "/id/" + idOggetto;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto istanza by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the oggetto istanza by id istanza
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaUbicazioneExtendedDTO> getOggettoIstanzaByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        String targetUrl = this.endpointBase;
        List<OggettoIstanzaUbicazioneExtendedDTO> result = new ArrayList<>();
        try {
            String queryParameters = buildQueryParameters("", "id_istanza", idIstanza);
            return setGetResult(className, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto istanza by uid.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the oggetto istanza by uid
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaUbicazioneExtendedDTO> getOggettoIstanzaByUID(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        logBeginInfo(className, (Object) uid);
        String targetUrl = this.endpointBase + "/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Search oggetto istanza list.
     *
     * @param requestHeaders       the request headers
     * @param searchOggettoIstanza the search oggetto istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaUbicazioneExtendedDTO> searchOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, SearchOggettoDTO searchOggettoIstanza) throws GenericException {
        logBeginInfo(className, searchOggettoIstanza);
        String targetUrl = this.endpointBase + "/search";
        try {
            Entity<SearchOggettoDTO> entity = Entity.json(searchOggettoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save oggetto istanza oggetto istanza ubicazione extended dto.
     *
     * @param requestHeaders           the request headers
     * @param oggettoIstanzaUbicazione the oggetto
     * @return the oggetto istanza ubicazione extended dto
     * @throws GenericException the generic exception
     */
    public OggettoIstanzaUbicazioneExtendedDTO saveOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione) throws GenericException {
        logBeginInfo(className, oggettoIstanzaUbicazione);
        String targetUrl = this.endpointBase;
        try {
            Entity<OggettoIstanzaUbicazioneExtendedDTO> entity = Entity.json(oggettoIstanzaUbicazione);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update oggetto istanza oggetto istanza ubicazione extended dto.
     *
     * @param requestHeaders           the request headers
     * @param oggettoIstanzaUbicazione the oggetto istanza ubicazione
     * @return the oggetto istanza ubicazione extended dto
     * @throws GenericException the generic exception
     */
    public OggettoIstanzaUbicazioneExtendedDTO updateOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, OggettoIstanzaUbicazioneExtendedDTO oggettoIstanzaUbicazione) throws GenericException {
        logBeginInfo(className, oggettoIstanzaUbicazione);
        String targetUrl = this.endpointBase;
        try {
            Entity<OggettoIstanzaUbicazioneExtendedDTO> entity = Entity.json(oggettoIstanzaUbicazione);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto istanza boolean.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, String uid, String checkFigli) throws GenericException {
        logBeginInfo(className, (Object) uid);
        String targetUrl;
        if(checkFigli == null)
        	targetUrl = this.endpointBase + "/" + uid;
        else
        	targetUrl = this.endpointBase + "/" + uid + "?check_figli=" + checkFigli;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Verifica georeferenziazione list.
     *
     * @param requestHeaders the request headers
     * @param codAdempimento the cod adempimento
     * @param idIstanza      the id istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> verificaGeoreferenziazione(MultivaluedMap<String, Object> requestHeaders, String codAdempimento, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        String targetUrl = this.endpointBase + "/adempimento/" + codAdempimento + "/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }
}