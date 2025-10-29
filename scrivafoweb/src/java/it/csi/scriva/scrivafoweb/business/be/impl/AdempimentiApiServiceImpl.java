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

import it.csi.scriva.scrivafoweb.business.be.AdempimentiApi;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Adempimenti api service.
 *
 * @author CSI PIEMONTE
 */
@Component
public class AdempimentiApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements AdempimentiApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * Gets adempimenti.
     *
     * @param xRequestAuth       the x request auth
     * @param xRequestId         the x request id
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @param uriInfo            the uri info
     * @return the adempimenti
     */
    @Override
    public Response getAdempimenti(String xRequestAuth, String xRequestId, Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * adempimento per id
     *
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @param uriInfo         the uri info
     * @return Response adempimento by id
     */
    @Override
    public Response getAdempimentoById(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Load adempi esito procedimento response.
     *
     * @param idAdempimento   the id adempimento
     * @param flgAttivi       the flg attivi
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     */
    @Override
    public Response loadAdempiEsitoProcedimento(Long idAdempimento, Boolean flgAttivi, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

}