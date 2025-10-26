/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AdempimentoExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;

public class AdempimentiApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

    public AdempimentiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<AdempimentoExtendedDTO> getAdempimenti(MultivaluedMap<String, Object> requestHeaders, Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
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
            result = setGetResult(CLASSNAME, methodName, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

    public List<AdempimentoExtendedDTO> getAdempimentoById(MultivaluedMap<String, Object> requestHeaders, Long idAdempimento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<AdempimentoExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/adempimenti/" + idAdempimento;
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