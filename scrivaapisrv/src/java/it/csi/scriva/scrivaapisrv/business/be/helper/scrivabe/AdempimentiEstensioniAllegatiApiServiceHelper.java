/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
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

package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoEstensioneAllegatoDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

public class AdempimentiEstensioniAllegatiApiServiceHelper extends AbstractServiceHelper {

    public AdempimentiEstensioniAllegatiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoEstensioneAllegatoDTO> getAdempimentiEstensioniAllegati(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] BEGIN");
        List<AdempimentoEstensioneAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/estensioni-allegato";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoEstensioneAllegatoDTO>> adempimentoEstensioneAllegatoListType = new GenericType<List<AdempimentoEstensioneAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoEstensioneAllegatoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentiEstensioniAllegati] END");
        }
        return result;
    }

    public List<AdempimentoEstensioneAllegatoDTO> getAdempimentoEstensioneAllegatoByIdAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] BEGIN");
        List<AdempimentoEstensioneAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/estensioni-allegato/id-adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoEstensioneAllegatoDTO>> adempimentoEstensioneAllegatoListType = new GenericType<List<AdempimentoEstensioneAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoEstensioneAllegatoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByIdAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoEstensioneAllegatoDTO> getAdempimentoEstensioneAllegatoByCodAdempimento(MultivaluedMap<String, Object> requestHeaders, String codAdempimento) throws GenericException {
        LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] BEGIN");
        List<AdempimentoEstensioneAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/estensioni-allegato/code-adempimento/" + codAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoEstensioneAllegatoDTO>> adempimentoEstensioneAllegatoListType = new GenericType<List<AdempimentoEstensioneAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoEstensioneAllegatoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiEstensioniAllegatiApiServiceHelper::getAdempimentoEstensioneAllegatoByCodAdempimento] END");
        }
        return result;
    }

}