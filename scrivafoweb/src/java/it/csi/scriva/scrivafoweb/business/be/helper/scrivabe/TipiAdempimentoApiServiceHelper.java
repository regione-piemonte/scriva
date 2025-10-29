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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoAdempimentoExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

public class TipiAdempimentoApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public TipiAdempimentoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<TipoAdempimentoExtendedDTO> getTipiAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoAdempimentoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-adempimento";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_ambito", idAmbito);
            queryParameters = buildQueryParameters(queryParameters, "id_compilante", idCompilante);
            queryParameters = buildQueryParameters(queryParameters, "codice", codTipoAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "codice_ambito", codAmbito);
            result = setGetResult(className, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return result;
    }

    public List<TipoAdempimentoExtendedDTO> getTipoAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idTipoAdempimento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        List<TipoAdempimentoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-adempimento/" + idTipoAdempimento;
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