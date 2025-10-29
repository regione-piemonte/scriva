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

import com.fasterxml.jackson.core.JsonProcessingException;
import it.csi.scriva.scrivabesrv.business.be.NotificheApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.NotificheService;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.LoadNotificheCountDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaApplicativaDTO;
import it.csi.scriva.scrivabesrv.dto.PaginationHeaderDTO;
import it.csi.scriva.scrivabesrv.dto.SearchNotificheDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;


/**
 * The type Notifiche api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NotificheApiServiceImpl extends BaseApiServiceImpl implements NotificheApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private NotificheService notificheService;

    /**
     * Carica tutte le notifiche non cancellate relative all'utente corrente
     *
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadNotifiche(Integer offset, Integer limit, String sort, SecurityContext securityContext,
                                  HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws JsonProcessingException {
        logBeginInfo(className, "\n offset : " + offset + "\n limit : " + limit + "\n sort : " + sort);

        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }

        List<NotificaApplicativaDTO> notifiche = notificheService.loadNotifiche(attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), String.valueOf(offset), String.valueOf(limit), sort);
        if (notifiche == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }

        List<NotificaApplicativaDTO> notificheWithoutLimit = notificheService.loadNotifiche(attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), null, null, null);
        PaginationHeaderDTO paginationHeader = getPaginationHeader(notificheWithoutLimit, offset, limit);
        LoadNotificheCountDTO searchCount = notificheService.getNotificheCountHeader(notificheWithoutLimit);
        logEnd(className);

        return Response.ok(notifiche).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY)
                .header("PaginationInfo", paginationHeader.getJson())
                .header("CountNotifiche", searchCount.getJson())
                .build();
    }

    /**
     * Carica  le notifiche relative all' utente corrente in base all'id notifica
     *
     * @param idNotifica      the id notifica
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadNotificheById(Long idNotifica, SecurityContext securityContext, HttpHeaders httpHeaders,
                                      HttpServletRequest httpRequest) throws JsonProcessingException {
        logBeginInfo(className, "idNotifica : [" + idNotifica + "]");
        SearchNotificheDTO searchCriteria = new SearchNotificheDTO();
        searchCriteria.setIdNotificaApplicativa(idNotifica);
        return loadNotifiche(searchCriteria, 1, 1, null, securityContext, httpHeaders, httpRequest);
    }

    /**
     * Carica le Notifiche relative all'utente corrente anche in base ai criteri di ricerca indicati
     *
     * @param searchCriteria  the search criteria
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    @Override
    public Response loadNotifiche(SearchNotificheDTO searchCriteria, Integer offset, Integer limit, String sort,
                                  SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws JsonProcessingException {

        logBeginInfo(className, "searchCriteria :\n" + searchCriteria + "\n offset : " + offset + "\n limit : " + limit
                + "\n sort : " + sort);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }

        List<NotificaApplicativaDTO> notifiche = notificheService.loadNotifiche(attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), searchCriteria, String.valueOf(offset), String.valueOf(limit), sort);

        if (notifiche == null) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        }

        List<NotificaApplicativaDTO> notificheWithoutLimit = notificheService.loadNotifiche(
                attoreScriva.getCodiceFiscale(), attoreScriva.getComponente(), searchCriteria, null, null, null);
        PaginationHeaderDTO paginationHeader = getPaginationHeader(notificheWithoutLimit, offset, limit);
        LoadNotificheCountDTO searchCount = notificheService.getNotificheCountHeader(notificheWithoutLimit);
        logEnd(className);
        return Response.ok(notifiche).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY)
                .header("PaginationInfo", paginationHeader.getJson())
                .header("CountNotifiche", searchCount.getJson())
                .build();

    }

    /**
     * Update notifiche response.
     *
     * @param notificaList    the notifica list
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    @Override
    public Response updateNotifiche(List<NotificaApplicativaDTO> notificaList, Integer offset, Integer limit, String sort, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws JsonProcessingException {
        logBeginInfo(className, notificaList);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        notificheService.updateNotifica(notificaList, attoreScriva);
        logEnd(className);
        return this.loadNotifiche(offset, limit, sort, securityContext, httpHeaders, httpRequest);
    }

    /**
     * Update notifiche response.
     *
     * @param searchCriteria  the search criteria
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws JsonProcessingException the json processing exception
     */
    @Override
    public Response updateNotifiche(SearchNotificheDTO searchCriteria, Integer offset, Integer limit, String sort, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws JsonProcessingException {
        logBeginInfo(className, searchCriteria);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        notificheService.updateNotificheTutteLette(attoreScriva, searchCriteria);
        logEnd(className);
        return this.loadNotifiche(searchCriteria, offset, limit, sort, securityContext, httpHeaders, httpRequest);
    }

    /**
     * Update notifiche response.
     *
     * @param notifica        the notifica
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    public Response updateNotifiche(NotificaApplicativaDTO notifica, Integer offset, Integer limit, String sort,
                                    SecurityContext securityContext, HttpHeaders httpHeaders,
                                    HttpServletRequest httpRequest) throws JsonProcessingException {
        logBeginInfo(className, notifica);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        notificheService.updateNotifica(notifica, attoreScriva);
        logEnd(className);
        return this.loadNotifiche(offset, limit, sort, securityContext, httpHeaders, httpRequest);
    }

}