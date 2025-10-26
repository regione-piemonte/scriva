/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.TipoEventoExtendedDTO;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

public class TipiEventoApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public TipiEventoApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<TipoEventoExtendedDTO> getTipiEvento(MultivaluedMap<String, Object> requestHeaders, String codTipoEvento) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(className, methodName));
        String targetUrl = this.endpointBase + "/tipi-evento" + (StringUtils.isNotBlank(codTipoEvento) ? "?codice=" + codTipoEvento : "");
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Tipo evento : Parametri in ingresso non validi.");
                logError(className, err);
                throw new GenericException(err);
            }
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(className, methodName));
        }
    }

}