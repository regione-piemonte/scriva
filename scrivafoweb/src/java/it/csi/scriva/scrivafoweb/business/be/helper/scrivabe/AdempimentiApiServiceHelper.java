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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

public class AdempimentiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public AdempimentiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoExtendedDTO> getAdempimenti(MultivaluedMap<String, Object> requestHeaders, Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<AdempimentoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_ambito", idAmbito);
            queryParameters = buildQueryParameters(queryParameters, "cod_ambito", codAmbito);
            queryParameters = buildQueryParameters(queryParameters, "id_tipo_adempimento", idTipoAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "cod_tipo_adempimento", codTipoAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "codice", codAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "id_compilante", idCompilante);
            result = setGetResult(className, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    public List<AdempimentoExtendedDTO> getAdempimentoById(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<AdempimentoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti/" + idAdempimento;
        try {
            result = setGetResult(className, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

}