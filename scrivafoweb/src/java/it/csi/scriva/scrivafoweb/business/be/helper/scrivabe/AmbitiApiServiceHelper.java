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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AmbitoDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Ambiti api service helper.
 */
public class AmbitiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Ambiti api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public AmbitiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AmbitoDTO> getAmbiti(MultivaluedMap<String, Object> requestHeaders, Long idAmbito, String codAmbito) throws GenericException {
        logBegin(className);
        List<AmbitoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ambiti";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "idAmbito", idAmbito);
            queryParameters = buildQueryParameters(queryParameters, "codice", codAmbito);
            result = setGetResult(className, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Gets ambito.
     *
     * @param requestHeaders the request headers
     * @param idAmbito       the id ambito
     * @return the ambito
     * @throws GenericException the generic exception
     */
    public List<AmbitoDTO> getAmbito(MultivaluedMap<String, Object> requestHeaders, Long idAmbito) throws GenericException {
        logBegin(className);
        List<AmbitoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/ambiti/" + idAmbito;
        try {
            result = setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

}