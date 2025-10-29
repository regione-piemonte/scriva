/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.LimitiAmministrativiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.LimitiAmministrativiApiServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Component
public class LimitiAmministrativiApiServiceImpl extends AbstractApiServiceImpl implements LimitiAmministrativiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private LimitiAmministrativiApiServiceHelper limitiAmministrativiApiServiceHelper;

    /**
     * Load nazioni response.
     *
     * @param codIstat        the cod istat
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getNazioni(String codIstat, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) codIstat);
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getNazioni(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codIstat), limitiAmministrativiApiServiceHelper, 200);
    }

    /**
     * Load nazioni attive response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getNazioniAttive(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getNazioniAttive(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest)), limitiAmministrativiApiServiceHelper, 200);
    }

    /**
     * Load regioni response.
     *
     * @param codIstat        the cod istat
     * @param codIstatNazione the cod istat nazione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getRegioni(String codIstat, String codIstatNazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "codIstat [" + codIstat + "] - codIstatNazione [" + codIstatNazione + "]");
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getRegioni(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codIstat, codIstatNazione), limitiAmministrativiApiServiceHelper, 200);
    }

    /**
     * Load regioni attive response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getRegioniAttive(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getRegioniAttive(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest)), limitiAmministrativiApiServiceHelper, 200);
    }

    /**
     * Load province response.
     *
     * @param codIstat        the cod istat
     * @param codIstatRegione the cod istat regione
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getProvince(String codIstat, String codIstatRegione, Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "codIstat [" + codIstat + "] - codIstatRegione [" + codIstatRegione + "] - idAdempimento [" + idAdempimento + "]");
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getProvince(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codIstat, codIstatRegione, idAdempimento), limitiAmministrativiApiServiceHelper, 200);
    }

    /**
     * Load province attive response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getProvinceAttive(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getProvinceAttive(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest)), limitiAmministrativiApiServiceHelper, 200);
    }

    /**
     * Load comuni response.
     *
     * @param codIstat          the cod istat
     * @param codIstatProvincia the cod istat provincia
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response getComuni(String codIstat, String codIstatProvincia, String attiva, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "codIstat [" + codIstat + "] - codIstatProvincia [" + codIstatProvincia + "] - attiva [" + attiva + "]");
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getComuni(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codIstat, codIstatProvincia, attiva), limitiAmministrativiApiServiceHelper, 200);
    }

    /**
     * Load comuni attivi response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getComuniAttivi(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        return getResponseWithSharedHeaders(className, limitiAmministrativiApiServiceHelper.getComuniAttivi(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest)), limitiAmministrativiApiServiceHelper, 200);
    }

}