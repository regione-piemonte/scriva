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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoUbicazionePraticaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchOggettoDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Oggetti api service helper.
 */
public class OggettiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/oggetti";

    /**
     * Instantiates a new Oggetti api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public OggettiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets oggetti.
     *
     * @param requestHeaders the request headers
     * @return the oggetti
     * @throws GenericException the generic exception
     */
    public List<OggettoUbicazioneExtendedDTO> getOggetti(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase;
        return setGetResult(className, targetUrl, requestHeaders);
    }

    /**
     * Gets oggetto by id.
     *
     * @param requestHeaders the request headers
     * @param idOggetto      the id oggetto
     * @return the oggetto by id
     * @throws GenericException the generic exception
     */
    public List<OggettoUbicazioneExtendedDTO> getOggettoById(MultivaluedMap<String, Object> requestHeaders, Long idOggetto) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/id/" + idOggetto;
        return setGetResult(className, targetUrl, requestHeaders);
    }

    /**
     * Gets oggetto by id comune.
     *
     * @param requestHeaders the request headers
     * @param idComune       the id comune
     * @param idIstanza      the id istanza
     * @return the oggetto by id comune
     * @throws GenericException the generic exception
     */
    public List<OggettoUbicazioneExtendedDTO> getOggettoByIdComuneIstanza(MultivaluedMap<String, Object> requestHeaders, Long idComune, Long idIstanza) throws GenericException {
        logBeginInfo(className, idComune);
        String targetUrl = this.endpointBase;
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "id_comune", idComune);
            return setGetResult(className, targetUrl + queryParameters, requestHeaders);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto by uid.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the oggetto by uid
     * @throws GenericException the generic exception
     */
    public List<OggettoUbicazioneExtendedDTO> getOggettoByUID(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/" + uid;
        return setGetResult(className, targetUrl, requestHeaders);
    }

    /**
     * Search oggetto list.
     *
     * @param requestHeaders the request headers
     * @param searchOggetto  the search oggetto
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<OggettoUbicazionePraticaExtendedDTO> searchOggetto(MultivaluedMap<String, Object> requestHeaders, SearchOggettoDTO searchOggetto) throws GenericException {
        logBegin(className);
        List<OggettoUbicazionePraticaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/search";
        try {
            Entity<SearchOggettoDTO> entity = Entity.json(searchOggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Save oggetto oggetto ubicazione extended dto.
     *
     * @param requestHeaders the request headers
     * @param codAdempimento the cod adempimento
     * @param oggetto        the oggetto
     * @return the oggetto ubicazione extended dto
     * @throws GenericException the generic exception
     */
    public OggettoUbicazioneExtendedDTO saveOggetto(MultivaluedMap<String, Object> requestHeaders, String codAdempimento, OggettoUbicazioneExtendedDTO oggetto) throws GenericException {
        logBegin(className);
        OggettoUbicazioneExtendedDTO result = new OggettoUbicazioneExtendedDTO();
        String targetUrl = this.endpointBase + "?cod_adempimento=" + codAdempimento;
        try {
            Entity<OggettoUbicazioneExtendedDTO> entity = Entity.json(oggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            GenericType<OggettoUbicazioneExtendedDTO> oggettoDTOType = new GenericType<OggettoUbicazioneExtendedDTO>() {
            };
            result = resp.readEntity(oggettoDTOType);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Update oggetto oggetto ubicazione extended dto.
     *
     * @param requestHeaders the request headers
     * @param oggetto        the oggetto
     * @return the oggetto ubicazione extended dto
     * @throws GenericException the generic exception
     */
    public OggettoUbicazioneExtendedDTO updateOggetto(MultivaluedMap<String, Object> requestHeaders, OggettoUbicazioneExtendedDTO oggetto) throws GenericException {
        logBegin(className);
        OggettoUbicazioneExtendedDTO result = new OggettoUbicazioneExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<OggettoUbicazioneExtendedDTO> entity = Entity.json(oggetto);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            GenericType<OggettoUbicazioneExtendedDTO> oggettoType = new GenericType<OggettoUbicazioneExtendedDTO>() {
            };
            result = resp.readEntity(oggettoType);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Delete oggetto boolean.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteOggetto(MultivaluedMap<String, Object> requestHeaders, String uid) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/" + uid;
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
}