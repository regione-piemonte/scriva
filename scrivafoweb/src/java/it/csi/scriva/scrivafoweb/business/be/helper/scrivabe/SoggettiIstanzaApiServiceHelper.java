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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SoggettoIstanzaExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Soggetti istanza api service helper.
 *
 * @author CSI PIEMONTE
 */
public class SoggettiIstanzaApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/soggetti-istanza";

    /**
     * Instantiates a new Soggetti istanza api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public SoggettiIstanzaApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets soggetti istanza.
     *
     * @param requestHeaders the request headers
     * @return the soggetti istanza
     * @throws GenericException the generic exception
     */
    public List<SoggettoIstanzaExtendedDTO> getSoggettiIstanza(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
       logBegin(className);
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase;
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets soggetto istanza.
     *
     * @param requestHeaders    the request headers
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the soggetto istanza
     * @throws GenericException the generic exception
     */
    public List<SoggettoIstanzaExtendedDTO> getSoggettoIstanza(MultivaluedMap<String, Object> requestHeaders, Long idSoggettoIstanza) throws GenericException {
        logBeginInfo(className, idSoggettoIstanza);
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id/" + idSoggettoIstanza;
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets soggetto istanza by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the soggetto istanza by id istanza
     * @throws GenericException the generic exception
     */
    public List<SoggettoIstanzaExtendedDTO> getSoggettoIstanzaByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase;
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_istanza", idIstanza);
            return setGetResult(className, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets soggetto istanza by id soggetto padre.
     *
     * @param requestHeaders  the request headers
     * @param idSoggettoPadre the id soggetto padre
     * @return the soggetto istanza by id soggetto padre
     * @throws GenericException the generic exception
     */
    public List<SoggettoIstanzaExtendedDTO> getSoggettoIstanzaByIdSoggettoPadre(MultivaluedMap<String, Object> requestHeaders, Long idSoggettoPadre) throws GenericException {
        logBeginInfo(className, idSoggettoPadre);
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id-padre/" + idSoggettoPadre;
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets soggetti istanza by codice fiscale soggetto.
     *
     * @param requestHeaders the request headers
     * @param codiceFiscale  the codice fiscale
     * @return the soggetti istanza by codice fiscale soggetto
     * @throws GenericException the generic exception
     */
    public List<SoggettoIstanzaExtendedDTO> getSoggettiIstanzaByCodiceFiscaleSoggetto(MultivaluedMap<String, Object> requestHeaders, String codiceFiscale) throws GenericException {
        logBeginInfo(className, (Object) codiceFiscale);
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/cf/" + codiceFiscale;
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save soggetto istanza soggetto istanza extended dto.
     *
     * @param requestHeaders  the request headers
     * @param soggettoIstanza the soggetto istanza
     * @return the soggetto istanza extended dto
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public SoggettoIstanzaExtendedDTO saveSoggettoIstanza(MultivaluedMap<String, Object> requestHeaders, SoggettoIstanzaExtendedDTO soggettoIstanza) throws ProcessingException, GenericException {
        logBeginInfo(className, soggettoIstanza);
        String targetUrl = this.endpointBase;
        try {
            Entity<SoggettoIstanzaExtendedDTO> entity = Entity.json(soggettoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            GenericType<SoggettoIstanzaExtendedDTO> dtoType = new GenericType<SoggettoIstanzaExtendedDTO>() {
            };
            return resp.readEntity(dtoType);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update soggetto istanza soggetto istanza extended dto.
     *
     * @param requestHeaders  the request headers
     * @param soggettoIstanza the soggetto istanza
     * @return the soggetto istanza extended dto
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public SoggettoIstanzaExtendedDTO updateSoggettoIstanza(MultivaluedMap<String, Object> requestHeaders, SoggettoIstanzaExtendedDTO soggettoIstanza) throws ProcessingException, GenericException {
        logBeginInfo(className, soggettoIstanza);
        String targetUrl = this.endpointBase;
        try {
            Entity<SoggettoIstanzaExtendedDTO> entity = Entity.json(soggettoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            GenericType<SoggettoIstanzaExtendedDTO> dtoType = new GenericType<SoggettoIstanzaExtendedDTO>() {
            };
            return resp.readEntity(dtoType);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete soggetto istanza boolean.
     *
     * @param requestHeaders the request headers
     * @param uid            the uid
     * @return the boolean
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public Boolean deleteSoggettoIstanza(MultivaluedMap<String, Object> requestHeaders, String uid) throws ProcessingException, GenericException {
        logBeginInfo(className, (Object) uid);
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

    /**
     * Delete soggetto istanza by id boolean.
     *
     * @param requestHeaders the request headers
     * @param id             the id
     * @return the boolean
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public Boolean deleteSoggettoIstanzaById(MultivaluedMap<String, Object> requestHeaders, Long id) throws ProcessingException, GenericException {
        logBeginInfo(className, id);
        String targetUrl = this.endpointBase + "/id/" + id;
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
     * Gets recapiti alternativi by id soggetto istanza.
     *
     * @param requestHeaders    the request headers
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the recapiti alternativi by id soggetto istanza
     * @throws GenericException the generic exception
     */
    public List<RecapitoAlternativoExtendedDTO> getRecapitiAlternativiByIdSoggettoIstanza(MultivaluedMap<String, Object> requestHeaders, Long idSoggettoIstanza) throws GenericException {
        logBeginInfo(className, idSoggettoIstanza);
        List<RecapitoAlternativoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/recapiti-alternativi/id/" + idSoggettoIstanza;
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save recapito alternativo list.
     *
     * @param requestHeaders      the request headers
     * @param recapitoAlternativo the recapito alternativo
     * @return the list
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public RecapitoAlternativoExtendedDTO saveRecapitoAlternativo(MultivaluedMap<String, Object> requestHeaders, RecapitoAlternativoExtendedDTO recapitoAlternativo) throws ProcessingException, GenericException {
        logBeginInfo(className, recapitoAlternativo);
        String targetUrl = this.endpointBase + "/recapiti-alternativi";
        try {
            Entity<RecapitoAlternativoExtendedDTO> entity = Entity.json(recapitoAlternativo);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
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
     * Update recapito alternativo recapito alternativo extended dto.
     *
     * @param requestHeaders      the request headers
     * @param recapitoAlternativo the recapito alternativo
     * @return the recapito alternativo extended dto
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public RecapitoAlternativoExtendedDTO updateRecapitoAlternativo(MultivaluedMap<String, Object> requestHeaders, RecapitoAlternativoExtendedDTO recapitoAlternativo) throws ProcessingException, GenericException {
        logBeginInfo(className, recapitoAlternativo);
        String targetUrl = this.endpointBase + "/recapiti-alternativi";
        try {
            Entity<RecapitoAlternativoExtendedDTO> entity = Entity.json(recapitoAlternativo);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
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
     * Delete recapito alternativo boolean.
     *
     * @param requestHeaders    the request headers
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteRecapitoAlternativo(MultivaluedMap<String, Object> requestHeaders, Long idSoggettoIstanza) throws GenericException {
        logBeginInfo(className, idSoggettoIstanza);
        String targetUrl = this.endpointBase + "/recapiti-alternativi/id/" + idSoggettoIstanza;
        try {
            return setDeleteResult(className, targetUrl, requestHeaders);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }
}