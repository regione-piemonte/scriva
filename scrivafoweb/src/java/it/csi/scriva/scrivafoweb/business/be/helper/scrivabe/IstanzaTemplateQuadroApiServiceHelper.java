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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaTemplateQuadroExtendedDTO;
import it.csi.scriva.scrivafoweb.util.Constants;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class IstanzaTemplateQuadroApiServiceHelper extends AbstractServiceHelper {

    public IstanzaTemplateQuadroApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<IstanzaTemplateQuadroExtendedDTO> getIstanzaTemplateQuadroByIdIstanza(MultivaluedMap<String, Object> requestHeaders, Long idIstanza) throws GenericException {
        logger.debug("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByIdIstanza] BEGIN");
        List<IstanzaTemplateQuadroExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/istanze-template-quadri/id-istanza/" + idIstanza;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByIdIstanza] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<IstanzaTemplateQuadroExtendedDTO>>() {});
        } catch (ProcessingException e) {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByIdIstanza] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByIdIstanza] END");
        }
        return result;
    }

    public List<IstanzaTemplateQuadroExtendedDTO> getIstanzaTemplateQuadroByPK(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, Long idTemplateQuadro) throws GenericException {
        logger.debug("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByPK] BEGIN");
        List<IstanzaTemplateQuadroExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/istanze-template-quadri/id-istanza/" + idIstanza + "/id-template-quadro/" + idTemplateQuadro;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByPK] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<List<IstanzaTemplateQuadroExtendedDTO>>() {});
        } catch (ProcessingException e) {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByPK] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::getIstanzaTemplateQuadroByPK] END");
        }
        return result;
    }

    public IstanzaTemplateQuadroDTO saveIstanzaTemplateQuadro(MultivaluedMap<String, Object> requestHeaders, IstanzaTemplateQuadroDTO istanzaTemplateQuadro) throws GenericException {
        logger.debug("[IstanzaTemplateQuadroApiServiceHelper::saveIstanzaTemplateQuadro] BEGIN");
        IstanzaTemplateQuadroDTO result = new IstanzaTemplateQuadroDTO();
        String targetUrl = this.endpointBase + "/istanze-template-quadri";
        try {
            Entity<IstanzaTemplateQuadroDTO> entity = Entity.json(istanzaTemplateQuadro);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[IstanzaTemplateQuadroApiServiceHelper::saveIstanzaTemplateQuadro] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<IstanzaTemplateQuadroDTO>() {});
        } catch (Exception e) {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::saveIstanzaTemplateQuadro] EXCEPTION : ", e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::saveIstanzaTemplateQuadro] END");
        }
        return result;
    }

    public IstanzaTemplateQuadroDTO updateIstanzaTemplateQuadro(MultivaluedMap<String, Object> requestHeaders, IstanzaTemplateQuadroDTO istanzaTemplateQuadro) throws GenericException {
        logger.debug("[IstanzaTemplateQuadroApiServiceHelper::updateIstanzaTemplateQuadro] BEGIN");
        IstanzaTemplateQuadroDTO result = new IstanzaTemplateQuadroDTO();
        String targetUrl = this.endpointBase + "/istanze-template-quadri";
        try {
            Entity<IstanzaTemplateQuadroDTO> entity = Entity.json(istanzaTemplateQuadro);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).put(entity);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[IstanzaTemplateQuadroApiServiceHelper::updateIstanzaTemplateQuadro] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            result = resp.readEntity(new GenericType<IstanzaTemplateQuadroDTO>() {});
        } catch (ProcessingException e) {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::updateIstanzaTemplateQuadro] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::updateIstanzaTemplateQuadro] END");
        }
        return result;
    }

    public Boolean deleteIstanzaTemplateQuadro(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, Long idTemplateQuadro) throws GenericException {
        logger.debug("[IstanzaTemplateQuadroApiServiceHelper::deleteIstanzaTemplateQuadro] BEGIN");
        String targetUrl = this.endpointBase + "/istanze-template-quadri/id-istanza/" + idIstanza + "/id-template-quadro/" + idTemplateQuadro;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).delete();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[IstanzaTemplateQuadroApiServiceHelper::deleteIstanzaTemplateQuadro] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            } else {
                return Boolean.TRUE;
            }
        } catch (ProcessingException e) {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::deleteIstanzaTemplateQuadro] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[IstanzaTemplateQuadroApiServiceHelper::deleteIstanzaTemplateQuadro] END");
        }
    }

}