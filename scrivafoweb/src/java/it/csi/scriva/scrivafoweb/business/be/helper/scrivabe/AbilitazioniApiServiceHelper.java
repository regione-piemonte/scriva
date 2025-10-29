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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoAbilitazioneDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Abilitazioni api service helper.
 */
public class AbilitazioniApiServiceHelper extends AbstractServiceHelper {

    public static final String ABILITAZIONI_ID_ISTANZA = "/abilitazioni/id-istanza/";
    private final String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Abilitazioni api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public AbilitazioniApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets abilitazioni by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the abilitazioni by id istanza
     * @throws GenericException the generic exception
     */
    public List<TipoAbilitazioneDTO> getAbilitazioniByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        List<TipoAbilitazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + ABILITAZIONI_ID_ISTANZA + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
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
     * Gets abilitazioni revocabili for istanza by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the abilitazioni revocabili for istanza by id istanza
     * @throws GenericException the generic exception
     */
    public List<IstanzaAttoreExtendedDTO> getAbilitazioniRevocabiliForIstanzaByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        List<IstanzaAttoreExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + ABILITAZIONI_ID_ISTANZA + idIstanza + "/revocabili";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
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
     * Gets abilitazioni concesse for istanza by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza      the id istanza
     * @return the abilitazioni concesse for istanza by id istanza
     * @throws GenericException the generic exception
     */
    public List<IstanzaAttoreExtendedDTO> getAbilitazioniConcesseForIstanzaByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        List<IstanzaAttoreExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + ABILITAZIONI_ID_ISTANZA + idIstanza + "/abilitazioni-concesse";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
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
     * Save abilitazione istanza attore extended dto.
     *
     * @param requestHeaders the request headers
     * @param istanzaAttore  the istanza attore
     * @return the istanza attore extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaAttoreExtendedDTO saveAbilitazione(MultivaluedMap<String, Object> requestHeaders, IstanzaAttoreExtendedDTO istanzaAttore) throws GenericException {
        logBegin(className);
        IstanzaAttoreExtendedDTO result = new IstanzaAttoreExtendedDTO();
        String targetUrl = this.endpointBase + "/abilitazioni";
        try {
            Entity<IstanzaAttoreExtendedDTO> entity = Entity.json(istanzaAttore);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
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
     * Update abilitazione istanza attore extended dto.
     *
     * @param requestHeaders the request headers
     * @param istanzaAttore  the istanza attore
     * @return the istanza attore extended dto
     * @throws GenericException the generic exception
     */
    public IstanzaAttoreExtendedDTO updateAbilitazione(MultivaluedMap<String, Object> requestHeaders, IstanzaAttoreExtendedDTO istanzaAttore) throws GenericException {
        logBegin(className);
        IstanzaAttoreExtendedDTO result = new IstanzaAttoreExtendedDTO();
        String targetUrl = this.endpointBase + "/abilitazioni";
        try {
            Entity<IstanzaAttoreExtendedDTO> entity = Entity.json(istanzaAttore);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, SERVER_EXCEPTION + err);
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

}