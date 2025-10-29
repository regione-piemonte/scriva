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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoRuoloCompilanteExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

/**
 * The type Adempimenti ruoli compilante api service helper.
 */
public class AdempimentiRuoliCompilanteAPIServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    /**
     * Instantiates a new Adempimenti ruoli compilante api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public AdempimentiRuoliCompilanteAPIServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets adempimenti ruoli compilante.
     *
     * @param requestHeaders the request headers
     * @return the adempimenti ruoli compilante
     * @throws ProcessingException the processing exception
     * @throws GenericException    the generic exception
     */
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilante(MultivaluedMap<String, Object> requestHeaders) throws ProcessingException, GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante";
        return setGetResult(className, targetUrl, requestHeaders);
    }

    /**
     * Gets adempimenti ruoli compilante by ruolo compilante.
     *
     * @param requestHeaders    the request headers
     * @param idRuoloCompilante the id ruolo compilante
     * @return the adempimenti ruoli compilante by ruolo compilante
     * @throws GenericException the generic exception
     */
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilanteByRuoloCompilante(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante/id-ruolo-compilante/" + idRuoloCompilante;
        return setGetResult(className, targetUrl, requestHeaders);
    }

    /**
     * Gets adempimenti ruoli compilante by adempimento.
     *
     * @param requestHeaders the request headers
     * @param idAdempimento  the id adempimento
     * @return the adempimenti ruoli compilante by adempimento
     * @throws GenericException the generic exception
     */
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilanteByAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante/id-adempimento/" + idAdempimento;
        return setGetResult(className, targetUrl, requestHeaders);
    }

    /**
     * Gets adempimenti ruoli compilante by ruolo compilante adempimento.
     *
     * @param requestHeaders    the request headers
     * @param idRuoloCompilante the id ruolo compilante
     * @param idAdempimento     the id adempimento
     * @return the adempimenti ruoli compilante by ruolo compilante adempimento
     * @throws GenericException the generic exception
     */
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilanteByRuoloCompilanteAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idRuoloCompilante, Long idAdempimento) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/adempimenti-ruoli-compilante/id-ruolo-compilante/" + idRuoloCompilante + "/id-adempimento/" + idAdempimento;
        return setGetResult(className, targetUrl, requestHeaders);
    }

}