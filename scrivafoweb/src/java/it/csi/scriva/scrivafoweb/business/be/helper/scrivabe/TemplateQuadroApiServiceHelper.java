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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TemplateQuadroExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TemplateQuadroApiServiceHelper extends AbstractServiceHelper {

    public TemplateQuadroApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<TemplateQuadroExtendedDTO> getTemplateQuadriByCodeAdempimento(MultivaluedMap<String, Object> requestHeaders, String codeAdempimento) throws GenericException {
        logger.debug("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeAdempimento] BEGIN");
        List<TemplateQuadroExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/template-quadri/code-adempimento/" + codeAdempimento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeAdempimento] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<TemplateQuadroExtendedDTO>> listType = new GenericType<List<TemplateQuadroExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeAdempimento] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeAdempimento] END");
        }
        return result;
    }

    public List<TemplateQuadroExtendedDTO> getTemplateQuadriByCodeTemplate(MultivaluedMap<String, Object> requestHeaders, String codeTemplate) throws GenericException {
        logger.debug("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeTemplate] BEGIN");
        List<TemplateQuadroExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/template-quadri/code-template/" + codeTemplate;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeTemplate] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<TemplateQuadroExtendedDTO>> listType = new GenericType<List<TemplateQuadroExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeTemplate] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[TemplateQuadroApiServiceHelper::getTemplateQuadriByCodeTemplate] END");
        }
        return result;
    }

    public List<TemplateQuadroExtendedDTO> getTemplateQuadriByIdTemplateQuadro(MultivaluedMap<String, Object> requestHeaders, Long idTemplateQuadro) throws GenericException {
        logger.debug("[TemplateQuadroApiServiceHelper::getTemplateQuadriByIdTemplateQuadro] BEGIN");
        List<TemplateQuadroExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/template-quadri/id-template-quadro/" + idTemplateQuadro;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[TemplateQuadroApiServiceHelper::getTemplateQuadriByIdTemplateQuadro] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<TemplateQuadroExtendedDTO>> listType = new GenericType<List<TemplateQuadroExtendedDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logger.error("[TemplateQuadroApiServiceHelper::getTemplateQuadriByIdTemplateQuadro] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[TemplateQuadroApiServiceHelper::getTemplateQuadriByIdTemplateQuadro] END");
        }
        return result;
    }
}