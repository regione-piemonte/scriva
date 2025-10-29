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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ComuneOldDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ProvinciaOldDTO;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class ProvinceApiServiceHelper extends AbstractServiceHelper {


    public ProvinceApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<ProvinciaOldDTO> getProvince(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logger.debug("[ProvinceApiServiceHelper::getProvincie] BEGIN");
        List<ProvinciaOldDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/province";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ProvinceApiServiceHelper::getProvincie] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ProvinciaOldDTO>> soggettiListType = new GenericType<List<ProvinciaOldDTO>>() {
            };
            result = resp.readEntity(soggettiListType);
        } catch (ProcessingException e) {
            logger.error("[ProvinceApiServiceHelper::getProvincie] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ProvinceApiServiceHelper::getProvincie] END");
        }
        return result;
    }

    public List<ComuneOldDTO> getComuniByProvincia(MultivaluedMap<String, Object> requestHeaders, String siglaProvincia, String fields) throws GenericException {
        logger.debug("[ProvinceApiServiceHelper::getComuniByProvincia] BEGIN");
        List<ComuneOldDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/province/" + siglaProvincia + "/comuni";
        if (StringUtils.isNotBlank(fields)) {
            targetUrl = targetUrl + "?fields=" + fields;
        }
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error("[ProvinceApiServiceHelper::getComuniByProvincia] SERVER EXCEPTION : " + err);
                throw new GenericException(err);
            }
            GenericType<List<ComuneOldDTO>> soggettiListType = new GenericType<List<ComuneOldDTO>>() {
            };
            result = resp.readEntity(soggettiListType);
        } catch (ProcessingException e) {
            logger.error("[ProvinceApiServiceHelper::getComuniByProvincia] EXCEPTION : " + e.getMessage());
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug("[ProvinceApiServiceHelper::getComuniByProvincia] END");
        }
        return result;
    }

}