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
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;

import java.util.ArrayList;
import java.util.List;

public class AdempimentiTipiAllegatoApiServiceHelper extends AbstractServiceHelper {

    public AdempimentiTipiAllegatoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologieAllegato(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologieAllegato] END");
        }
        return result;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologiaAllegatoByIdAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato/id-adempimento/" + idAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByIdAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologiaAllegatoByCodeAdempimento(MultivaluedMap<String, Object> requestHeaders, String codAdempimento) throws GenericException {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato/code-adempimento/" + codAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimento] END");
        }
        return result;
    }

    public List<AdempimentoTipoAllegatoExtendedDTO> getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria(MultivaluedMap<String, Object> requestHeaders, String codAdempimento, String codCategoria) throws GenericException {
        LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] BEGIN");
        List<AdempimentoTipoAllegatoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipologie-allegato/code-adempimento/" + codAdempimento + "/code-categoria/" + codCategoria;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<AdempimentoTipoAllegatoExtendedDTO>> adempimentoTipoAllegatoListType = new GenericType<List<AdempimentoTipoAllegatoExtendedDTO>>() {
            };
            result = resp.readEntity(adempimentoTipoAllegatoListType);
        } catch (ProcessingException e) {
            LOGGER.error("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] EXCEPTION : " + e.getMessage());
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug("[AdempimentiTipiAllegatoApiServiceHelper::getTipologiaAllegatoByCodeAdempimentoAndCodeCategoria] END");
        }
        return result;
    }
}