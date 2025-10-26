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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.CategoriaOggettoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaCategoriaExtendedDTO;
import it.csi.scriva.scrivaapisrv.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Categorie oggetto api service helper.
 */
public class CategorieOggettoApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

    /**
     * Instantiates a new Categorie oggetto api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public CategorieOggettoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets categorie progettuali.
     *
     * @param requestHeaders the request headers
     * @return the categorie progettuali
     * @throws GenericException the generic exception
     */
    public List<CategoriaOggettoExtendedDTO> getCategorieProgettuali(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<CategoriaOggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/categorie-progettuali";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<List<CategoriaOggettoExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
    public List<CategoriaOggettoExtendedDTO> getCategorieProgettualiByIdAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento, String search) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<CategoriaOggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/categorie-progettuali/id-adempimento/" + idAdempimento + "?search=" + search;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<List<CategoriaOggettoExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<CategoriaOggettoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/categorie-progettuali/id-oggetto-istanza/" + idOggettoIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<List<CategoriaOggettoExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        OggettoIstanzaCategoriaExtendedDTO result = new OggettoIstanzaCategoriaExtendedDTO();
        String targetUrl = this.endpointBase + "/categorie-progettuali";
        try {
            Entity<OggettoIstanzaCategoriaExtendedDTO> entity = Entity.json(oggettoIstanzaCategoria);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<OggettoIstanzaCategoriaExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        OggettoIstanzaCategoriaExtendedDTO result = new OggettoIstanzaCategoriaExtendedDTO();
        String targetUrl = this.endpointBase + "/categorie-progettuali";
        try {
            Entity<OggettoIstanzaCategoriaExtendedDTO> entity = Entity.json(oggettoIstanzaCategoria);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<OggettoIstanzaCategoriaExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<OggettoIstanzaCategoriaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/categorie-progettuali/id-oggetto-istanza/" + idOggettoIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(null);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<OggettoIstanzaCategoriaExtendedDTO>>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        String targetUrl = this.endpointBase + "/categorie-progettuali/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
    }

}