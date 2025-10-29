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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CategoriaOggettoExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaCategoriaExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.ListObjectWithHeaderResultFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Categorie oggetto api service helper.
 */
public class CategorieOggettoApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/categorie-progettuali";

    /**
     * Instantiates a new Categorie oggetto api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public CategorieOggettoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets categorie progettuali.
     *
     * @param idAdempimento    the id adempimento
     * @param idIstanza        the id istanza
     * @param idOggettoIstanza the id oggetto istanza
     * @param search           the search
     * @param requestHeaders   the request headers
     * @return the categorie progettuali
     * @throws GenericException the generic exception
     */
    public ListObjectWithHeaderResultFoDTO getCategorieProgettuali(Long idAdempimento, Long idIstanza, Long idOggettoIstanza, String search, MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        ListObjectWithHeaderResultFoDTO result = null;
        String targetUrl = this.endpointBase;
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_adempimento", idAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            queryParameters = buildQueryParameters(queryParameters, "id_oggetto_istanza", idOggettoIstanza);
            queryParameters = buildQueryParameters(queryParameters, "search", search);
            Response resp = getInvocationBuilder(targetUrl + queryParameters, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            result = new ListObjectWithHeaderResultFoDTO();
            result.setObjectList(resp.readEntity(new GenericType<List<Object>>() {
            }));
            result.setCountHeader(resp.getHeaderString("CountCompetenze"));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets categorie progettuali by id adempimento.
     *
     * @param requestHeaders the request headers
     * @param idAdempimento  the id adempimento
     * @param search         the search
     * @return the categorie progettuali by id adempimento
     * @throws GenericException the generic exception
     */
    public ListObjectWithHeaderResultFoDTO getCategorieProgettualiByIdAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento, String search) throws GenericException {
        logBegin(className);
        ListObjectWithHeaderResultFoDTO result = null;
        String targetUrl = this.endpointBase + "/id-adempimento/" + idAdempimento + "?search=" + search;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
                throw new GenericException(err);
            }
            result = new ListObjectWithHeaderResultFoDTO();
            result.setObjectList(resp.readEntity(new GenericType<List<Object>>() {
            }));
            result.setCountHeader(resp.getHeaderString("CountCompetenze"));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets categorie progettuali by id oggetto istanza.
     *
     * @param requestHeaders   the request headers
     * @param idOggettoIstanza the id oggetto istanza
     * @return the categorie progettuali by id oggetto istanza
     * @throws GenericException the generic exception
     */
    public List<CategoriaOggettoExtendedDTO> getCategorieProgettualiByIdOggettoIstanza(MultivaluedMap<String, Object> requestHeaders, Long idOggettoIstanza) throws GenericException {
        logBegin(className);
        List<CategoriaOggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id-oggetto-istanza/" + idOggettoIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);

                logError(className, SERVER_EXCEPTION + err);
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<List<CategoriaOggettoExtendedDTO>>() {
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
     * Save oggetto istanza categoria oggetto istanza categoria extended dto.
     *
     * @param requestHeaders          the request headers
     * @param oggettoIstanzaCategoria the oggetto istanza categoria
     * @return the oggetto istanza categoria extended dto
     * @throws GenericException the generic exception
     */
    public OggettoIstanzaCategoriaExtendedDTO saveOggettoIstanzaCategoria(MultivaluedMap<String, Object> requestHeaders, OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria) throws GenericException {
        logBegin(className);
        OggettoIstanzaCategoriaExtendedDTO result = new OggettoIstanzaCategoriaExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<OggettoIstanzaCategoriaExtendedDTO> entity = Entity.json(oggettoIstanzaCategoria);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<OggettoIstanzaCategoriaExtendedDTO>() {
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
     * Update oggetto istanza categoria oggetto istanza categoria extended dto.
     *
     * @param requestHeaders          the request headers
     * @param oggettoIstanzaCategoria the oggetto istanza categoria
     * @return the oggetto istanza categoria extended dto
     * @throws GenericException the generic exception
     */
    public OggettoIstanzaCategoriaExtendedDTO updateOggettoIstanzaCategoria(MultivaluedMap<String, Object> requestHeaders, OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria) throws GenericException {
        logBegin(className);
        OggettoIstanzaCategoriaExtendedDTO result = new OggettoIstanzaCategoriaExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<OggettoIstanzaCategoriaExtendedDTO> entity = Entity.json(oggettoIstanzaCategoria);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<OggettoIstanzaCategoriaExtendedDTO>() {
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
     * Add categorie modifica list.
     *
     * @param requestHeaders   the request headers
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     * @throws GenericException the generic exception
     */
    public List<OggettoIstanzaCategoriaExtendedDTO> addCategorieModifica(MultivaluedMap<String, Object> requestHeaders, Long idOggettoIstanza) throws GenericException {
        logBegin(className);
        List<OggettoIstanzaCategoriaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id-oggetto-istanza/" + idOggettoIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(null);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<OggettoIstanzaCategoriaExtendedDTO>>() {
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
     * Delete oggetto istanza categoria.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public void deleteOggettoIstanzaCategoria(MultivaluedMap<String, Object> requestHeaders, String uid) throws ProcessingException, GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

}