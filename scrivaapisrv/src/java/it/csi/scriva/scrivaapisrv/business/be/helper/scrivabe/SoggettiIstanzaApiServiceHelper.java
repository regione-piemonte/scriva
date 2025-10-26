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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SoggettoIstanzaExtendedDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Soggetti istanza api service helper.
 *
 * @author CSI PIEMONTE
 */
public class SoggettiIstanzaApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase;
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoIstanza: [" + idSoggettoIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id/" + idSoggettoIstanza;
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoPadre: [" + idSoggettoPadre + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/id-padre/" + idSoggettoPadre;
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input codiceFiscale: [" + codiceFiscale + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<SoggettoIstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/cf/" + codiceFiscale;
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input soggettoIstanza:\n" + soggettoIstanza + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        SoggettoIstanzaExtendedDTO result = new SoggettoIstanzaExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            Entity<SoggettoIstanzaExtendedDTO> entity = Entity.json(soggettoIstanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[SoggettiIstanzaApiServiceHelper::saveSoggettoIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<SoggettoIstanzaExtendedDTO> dtoType = new GenericType<SoggettoIstanzaExtendedDTO>() {
            };
            result = resp.readEntity(dtoType);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input soggettoIstanza:\n" + soggettoIstanza + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        SoggettoIstanzaExtendedDTO result = new SoggettoIstanzaExtendedDTO();
        String targetUrl = this.endpointBase;
        try {
            LOGGER.debug("[SoggettiIstanzaApiServiceImpl::updateSoggettoIstanza] soggetto_istanza : " + soggettoIstanza.toString());
            Entity<SoggettoIstanzaExtendedDTO> entity = Entity.json(soggettoIstanza);
            LOGGER.debug("[SoggettiIstanzaApiServiceImpl::updateSoggettoIstanza] entity : " + entity.toString());
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[SoggettiIstanzaApiServiceHelper::updateSoggettoIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<SoggettoIstanzaExtendedDTO> dtoType = new GenericType<SoggettoIstanzaExtendedDTO>() {
            };
            result = resp.readEntity(dtoType);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input uid: [" + uid + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        String targetUrl = this.endpointBase + "/" + uid;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input id: [" + id + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        String targetUrl = this.endpointBase + "/id/" + id;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoIstanza: [" + idSoggettoIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<RecapitoAlternativoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/recapiti-alternativi/id/" + idSoggettoIstanza;
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input recapitoAlternativo:\n" + recapitoAlternativo + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        RecapitoAlternativoExtendedDTO result = null;
        String targetUrl = this.endpointBase + "/recapiti-alternativi";
        try {
            Entity<RecapitoAlternativoExtendedDTO> entity = Entity.json(recapitoAlternativo);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<>() {
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
     * Update recapito alternativo recapito alternativo extended dto.
     *
     * @param requestHeaders      the request headers
     * @param recapitoAlternativo the recapito alternativo
     * @return the recapito alternativo extended dto
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public RecapitoAlternativoExtendedDTO updateRecapitoAlternativo(MultivaluedMap<String, Object> requestHeaders, RecapitoAlternativoExtendedDTO recapitoAlternativo) throws ProcessingException, GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input recapitoAlternativo:\n" + recapitoAlternativo + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        RecapitoAlternativoExtendedDTO result;
        String targetUrl = this.endpointBase + "/recapiti-alternativi";
        try {
            Entity<RecapitoAlternativoExtendedDTO> entity = Entity.json(recapitoAlternativo);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<>() {
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
     * Delete recapito alternativo boolean.
     *
     * @param requestHeaders    the request headers
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public Boolean deleteRecapitoAlternativo(MultivaluedMap<String, Object> requestHeaders, Long idSoggettoIstanza) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoIstanza: [" + idSoggettoIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        String targetUrl = this.endpointBase + "/recapiti-alternativi/id/" + idSoggettoIstanza;
        try {
            return setDeleteResult(CLASSNAME, methodName, targetUrl, requestHeaders);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
    }
}