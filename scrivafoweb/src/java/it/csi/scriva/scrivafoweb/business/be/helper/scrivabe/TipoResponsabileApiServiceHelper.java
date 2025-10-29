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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoResponsabileDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Istanze api service helper.
 */
public class TipoResponsabileApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/tipo-responsabile";

    /**
     * Instantiates a new tipo responsabile api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public TipoResponsabileApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets tipo responsabile by id istanza.
     *
     * @param requestHeaders the request headers
     * @param idIstanza   the id istanza
     * @return the tipo responsabile by id istanza
     * @throws GenericException the generic exception
     */
    public List<TipoResponsabileDTO> getTipoResponsabile(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logBegin(className);
        List<TipoResponsabileDTO> result = new ArrayList<>();
        String targetUrl;
        if(idIstanza != null)
            targetUrl = this.endpointBase + "?id_istanza=" + idIstanza;
        else
        	targetUrl = this.endpointBase;
        try {
            result = setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }


}