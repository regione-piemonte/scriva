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

import it.csi.scriva.scrivafoweb.business.be.OggettiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.OggettiApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchOggettoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Oggetti api service.
 */
@Component
public class OggettiApiServiceImpl extends AbstractApiServiceImpl implements OggettiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private OggettiApiServiceHelper oggettiApiServiceHelper;

    /**
     * Gets oggetti.
     *
     * @param idComune        the id comune
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the oggetti
     */
    @Override
    public Response getOggetti(String xRequestAuth, String xRequestId,
                               Long idComune, Long idIstanza,
                               SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        return getResponseWithSharedHeaders(idComune != null || idIstanza != null ? oggettiApiServiceHelper.getOggettoByIdComuneIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idComune, idIstanza) : oggettiApiServiceHelper.getOggetti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest)), oggettiApiServiceHelper, 200);
    }

    /**
     * Gets oggetto by id.
     *
     * @param idOggetto       the id oggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the oggetto by id
     * @throws GenericException the generic exception
     */
//@Override
    public Response getOggettoById(Long idOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idOggetto);
        return getResponseWithSharedHeaders(oggettiApiServiceHelper.getOggettoById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggetto), oggettiApiServiceHelper, 200);
    }

    /**
     * Gets oggetto by uid.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the oggetto by uid
     */
    @Override
    public Response getOggettoByUID(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) uid);
        return getResponseWithSharedHeaders(oggettiApiServiceHelper.getOggettoByUID(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid), oggettiApiServiceHelper, 200);
    }

    /**
     * Search oggetto response.
     *
     * @param searchOggetto   the search oggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response searchOggetto(SearchOggettoDTO searchOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, searchOggetto);
        return getResponseWithSharedHeaders(oggettiApiServiceHelper.searchOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), searchOggetto), oggettiApiServiceHelper, 200);
    }

    /**
     * Save oggetto response.
     *
     * @param oggetto         the oggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveOggetto(String codAdempimento, OggettoUbicazioneExtendedDTO oggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "codAdempimento [" + codAdempimento + "]\n" + oggetto);
        return getResponseWithSharedHeaders(oggettiApiServiceHelper.saveOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento, oggetto), oggettiApiServiceHelper, 201);
    }

    /**
     * Update oggetto response.
     *
     * @param oggetto         the oggetto
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response updateOggetto(OggettoUbicazioneExtendedDTO oggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, oggetto);
        return getResponseWithSharedHeaders(oggettiApiServiceHelper.updateOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggetto), oggettiApiServiceHelper, 201);
    }

    /**
     * Delete oggetto response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response deleteOggetto(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) uid);
        oggettiApiServiceHelper.deleteOggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
        return getResponseWithSharedHeaders(null, oggettiApiServiceHelper, 204);
    }
}