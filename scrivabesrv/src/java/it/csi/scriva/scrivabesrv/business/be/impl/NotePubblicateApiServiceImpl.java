/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.NotePubblicateApi;
import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.NoteIstanzaService;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Search api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NotePubblicateApiServiceImpl extends BaseApiServiceImpl implements NotePubblicateApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private NoteIstanzaService notePubblicateService;

    /**
     * Search note pubblicate response.
     *
     * @param idIstanza       the id istanza
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response searchNotePubblicate(Long idIstanza, Integer offset, Integer limit, String sort, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza :\n" + idIstanza + "\n offset : " + offset + "\n limit : " + limit);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        return getResponseList(notePubblicateService.searchNotePubblicate(attoreScriva.getComponente(), idIstanza, offset, limit, sort), className);
    }

    /**
     * Save nota istanza response.
     *
     * @param notaPubblicata  the nota pubblicata
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveNotaIstanza(NotaPubblicataDTO notaPubblicata, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws DuplicateRecordException {
        logBeginInfo(className, notaPubblicata);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        return getResponseSaveUpdate(notePubblicateService.saveNotaPubblicata(notaPubblicata, attoreScriva), className);
    }

    /**
     * Update nota istanza response.
     *
     * @param notaPubblicata  the nota pubblicata
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response updateNotaIstanza(NotaPubblicataDTO notaPubblicata, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, notaPubblicata);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        Integer resUpdate = notePubblicateService.updateNotaPubblicata(notaPubblicata, attoreScriva);
        return getResponseSaveUpdate(resUpdate != null && resUpdate > 0 ? (NotaPubblicataDTO) notePubblicateService.loadNotaPubblicataById(notaPubblicata.getIdNotaIstanza()) : resUpdate, className);
    }

    /**
     * Delete nota istanza response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response deleteNotaIstanza(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        return getResponseDelete(notePubblicateService.deleteNotaPubblicata(uid,attoreScriva), className);
    }

}