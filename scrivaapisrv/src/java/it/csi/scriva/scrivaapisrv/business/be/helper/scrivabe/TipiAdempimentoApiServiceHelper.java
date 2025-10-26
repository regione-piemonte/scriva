/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoAdempimentoExtendedDTO;

import java.util.ArrayList;
import java.util.List;

public class TipiAdempimentoApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

    public TipiAdempimentoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<TipoAdempimentoExtendedDTO> getTipiAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<TipoAdempimentoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-adempimento";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "id_ambito", idAmbito);
            queryParameters = buildQueryParameters(queryParameters, "id_compilante", idCompilante);
            queryParameters = buildQueryParameters(queryParameters, "codice", codTipoAdempimento);
            queryParameters = buildQueryParameters(queryParameters, "codice_ambito", codAmbito);
            result = setGetResult(CLASSNAME, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    public List<TipoAdempimentoExtendedDTO> getTipoAdempimento(MultivaluedMap<String, Object> requestHeaders, Long idTipoAdempimento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<TipoAdempimentoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/tipi-adempimento/" + idTipoAdempimento;
        try {
            result = setGetResult(CLASSNAME, methodName, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

}