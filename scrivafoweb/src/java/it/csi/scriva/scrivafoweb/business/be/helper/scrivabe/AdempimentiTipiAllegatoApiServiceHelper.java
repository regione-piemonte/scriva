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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class AdempimentiTipiAllegatoApiServiceHelper extends AbstractServiceHelper {

    public AdempimentiTipiAllegatoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologieAllegato(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] END");
        }
        return result;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologiaAllegatoByIdAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato/id-adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologiaAllegatoByCodeAdempimento(MultivaluedMap<String, Object> requestHeaders, String codAdempimento) throws GenericException {
        logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato/code-adempimento/" + codAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato(MultivaluedMap<String, Object> requestHeaders, String codAdempimento, String codTipologiaAllegato) throws GenericException {
        logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato/code-adempimento/" + codAdempimento + "/code-tipologia-allegato/" + codTipologiaAllegato;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::loadTipologiaAllegatoByCodeAdempimentoAndCodeTipologiaAllegato] END");
        }
        return result;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria(MultivaluedMap<String, Object> requestHeaders, String codAdempimento, String codCategoria) throws GenericException {
        logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato/code-adempimento/" + codAdempimento + "/code-categoria/" + codCategoria;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            logger.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] END");
        }
        return result;
    }
}