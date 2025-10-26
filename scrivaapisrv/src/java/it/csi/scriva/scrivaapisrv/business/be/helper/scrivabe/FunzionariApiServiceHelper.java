/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.FunzionarioAutorizzatoDTO;

/**
 * The type Funzionari api service helper.
 */
public class FunzionariApiServiceHelper extends AbstractServiceHelper {

    private final String CLASSNAME = this.getClass().getSimpleName();

    /**
     * Instantiates a new Funzionari api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public FunzionariApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    /**
     * Gets funzionario by cf.
     *
     * @param requestHeaders the request headers
     * @return the funzionario by cf
     * @throws GenericException the generic exception
     */
    public FunzionarioAutorizzatoDTO getFunzionarioByCf(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        FunzionarioAutorizzatoDTO result = null;
        String targetUrl = this.endpointBase + "/funzionari";
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, "SERVER EXCEPTION : " + err));
                throw new GenericException(err);
            }
            result = resp.readEntity(new GenericType<>() {});
        } catch (ProcessingException e) {
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return result;
    }

}