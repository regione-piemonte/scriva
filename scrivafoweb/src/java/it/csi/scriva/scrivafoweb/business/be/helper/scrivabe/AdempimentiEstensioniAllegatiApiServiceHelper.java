/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoEstensioneAllegatoDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class AdempimentiEstensioniAllegatiApiServiceHelper extends AbstractServiceHelper {

    public AdempimentiEstensioniAllegatiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoEstensioneAllegatoDTO> getAdempimentiEstensioniAllegati(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] BEGIN");
        List<AdempimentoEstensioneAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/estensioni-allegato";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoEstensioneAllegatoDTO>> adempimentoEstensioneAllegatoListType = new GenericType<List<AdempimentoEstensioneAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoEstensioneAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] END");
        }
        return result;
    }

    public List<AdempimentoEstensioneAllegatoDTO> getAdempimentoEstensioneAllegatoByIdAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        logger.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] BEGIN");
        List<AdempimentoEstensioneAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/estensioni-allegato/id-adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoEstensioneAllegatoDTO>> adempimentoEstensioneAllegatoListType = new GenericType<List<AdempimentoEstensioneAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoEstensioneAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoEstensioneAllegatoDTO> getAdempimentoEstensioneAllegatoByCodAdempimento(MultivaluedMap<String, Object> requestHeaders, String codAdempimento) throws GenericException {
        logger.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] BEGIN");
        List<AdempimentoEstensioneAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/estensioni-allegato/code-adempimento/" + codAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoEstensioneAllegatoDTO>> adempimentoEstensioneAllegatoListType = new GenericType<List<AdempimentoEstensioneAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoEstensioneAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] END");
        }
        return result;
    }

}