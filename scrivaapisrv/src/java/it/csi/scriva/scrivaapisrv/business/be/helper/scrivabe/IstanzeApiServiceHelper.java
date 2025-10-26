/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.util.Constants;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Istanze api service helper.
 */
public class IstanzeApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/istanze";

    /**
     * Instantiates a new Istanze api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public IstanzeApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets istanze.
     *
     * @param requestHeaders the request headers
     * @return the istanze
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> getIstanze(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        List<IstanzaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase;
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the istanza
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> getIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        String targetUrl = this.endpointBase + "/id/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Istanza : Parametri in ingresso non validi.");
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets istanza.
     *
     * @param requestHeaders the request headers
     * @param codIstanza     the cod istanza
     * @return the istanza
     * @throws GenericException the generic exception
     */
    public List<IstanzaExtendedDTO> getIstanza(MultivaluedMap<String, Object> requestHeaders, String codIstanza) throws GenericException {
        logBegin(className);
        logDebug(className, "codIstanza : [" + codIstanza + "]");
        String targetUrl = this.endpointBase + "?cod_istanza=" + codIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Istanza : Parametri in ingresso non validi.");
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save istanza istanza extended dto.
     *
     * @param requestHeaders the request headers
     * @param istanza        the istanza
     * @return the istanza extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaExtendedDTO saveIstanza(MultivaluedMap<String, Object> requestHeaders, IstanzaExtendedDTO istanza) throws GenericException {
        logBeginInfo(className, istanza);
        String targetUrl = this.endpointBase;
        try {
            Entity<IstanzaExtendedDTO> entity = Entity.json(istanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Istanza : errore durante il salvataggio.");
                logError(className, err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<IstanzaExtendedDTO>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza istanza extended dto.
     *
     * @param requestHeaders the request headers
     * @param istanza        the istanza
     * @param flgCreaPratica the flg crea pratica
     * @return the istanza extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaExtendedDTO updateIstanza(MultivaluedMap<String, Object> requestHeaders, IstanzaExtendedDTO istanza, boolean flgCreaPratica) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + (Boolean.TRUE.equals(flgCreaPratica) ? "?flg_crea_pratica=" + flgCreaPratica : "");
        try {
            Entity<IstanzaExtendedDTO> entity = Entity.json(istanza);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Istanza : errore durante l'aggiornamento.");
                logError(className, err);
                throw new GenericException(err);
            }
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    public List<IstanzaExtendedDTO> pubblicaIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/" + idIstanza + "/_pubblica";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).method("PATCH", Entity.json(null));
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Istanza : errore durante l'aggiornamento.");
                logError(className, err);
                throw new GenericException(err);
            }
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }


    /**
     * Gets codice pratica.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the codice pratica
     * @throws GenericException the generic exception
     */
    public List<String> getCodicePratica(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBeginInfo(className, idIstanza);
        List<String> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/" + idIstanza + "/_creaCodicePratica";
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

}