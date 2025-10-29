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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class CategorieAllegatoApiServiceHelper extends AbstractServiceHelper {

    public CategorieAllegatoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<CategoriaAllegatoDTO> getCategorieAllegato(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[CategorieiAllegatoApiServiceHelper::getCategorieAllegato] BEGIN");
        List<CategoriaAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/categorie-allegato";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CategorieiAllegatoApiServiceHelper::getCategorieAllegato] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<CategoriaAllegatoDTO>> adempimentoTipoAllegatoListType = new GenericType<List<CategoriaAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[CategorieiAllegatoApiServiceHelper::getCategorieAllegato] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CategorieiAllegatoApiServiceHelper::getCategorieAllegato] END");
        }
        return result;
    }

    public List<CategoriaAllegatoDTO> getCategoriaAllegatoByIdAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        logger.debug("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByIdAdempimento] BEGIN");
        List<CategoriaAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/categorie-allegato/id-adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByIdAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<CategoriaAllegatoDTO>> adempimentoTipoAllegatoListType = new GenericType<List<CategoriaAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByIdAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByIdAdempimento] END");
        }
        return result;
    }

    public List<CategoriaAllegatoDTO> getCategoriaAllegatoByCodeAdempimento(MultivaluedMap<String, Object> requestHeaders, String codAdempimento) throws GenericException {
        logger.debug("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByCodeAdempimento] BEGIN");
        List<CategoriaAllegatoDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/categorie-allegato/code-adempimento/" + codAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByCodeAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<CategoriaAllegatoDTO>> adempimentoTipoAllegatoListType = new GenericType<List<CategoriaAllegatoDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByCodeAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[CategorieiAllegatoApiServiceHelper::getCategoriaAllegatoByCodeAdempimento] END");
        }
        return result;
    }

}