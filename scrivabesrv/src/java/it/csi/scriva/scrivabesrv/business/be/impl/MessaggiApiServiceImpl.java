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

import it.csi.scriva.scrivabesrv.business.be.MessaggiApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.MessaggioDAO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.MessaggioExtendedDTO;
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
 * The type Messaggi api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessaggiApiServiceImpl extends BaseApiServiceImpl implements MessaggiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private MessaggioDAO messaggioDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadMessaggi(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(messaggioDAO.loadMessaggi(), className);
    }

    /**
     * @param idMessaggio     idMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadMessaggio(Long idMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idMessaggio);
        return getResponseList(messaggioDAO.loadMessaggio(idMessaggio), className);
    }

    /**
     * @param codMessaggio    codMessaggio
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadMessaggioByCode(String codMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codMessaggio);
        return getResponseList(messaggioDAO.loadMessaggioByCode(codMessaggio), className);
    }

    /**
     * @param codTipoMessaggio codTipoMessaggio
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadMessaggiByCodeTipoMessaggio(String codTipoMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codTipoMessaggio);
        return getResponseList(messaggioDAO.loadMessaggiByCodeTipoMessaggio(codTipoMessaggio), className);
    }

    /**
     * Save messaggio response.
     *
     * @param messaggio       the messaggio
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
//    @Override
    public Response saveMessaggio(MessaggioExtendedDTO messaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, messaggio);
        Long idMessaggio = messaggioDAO.saveMessaggio(messaggio.getDTO());
        if (null == idMessaggio) {
            ErrorDTO error = getErrorManager().getError("500", "E100", null, null, null);
            return getResponseError(className, error);
        } else {
            return getResponseSave(messaggioDAO.loadMessaggio(idMessaggio).get(0), className, "/messaggi/id-messaggio/" + idMessaggio);
        }
    }

    /**
     * Update messaggio response.
     *
     * @param messaggio       the messaggio
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
//    @Override
    public Response updateMessaggio(MessaggioExtendedDTO messaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, messaggio);
        Integer res = messaggioDAO.updateMessaggio(messaggio.getDTO());
        if (null == res || res < 1) {
            return getResponseSaveUpdate(res, className);
        }
        return getResponseSaveUpdate(messaggio, className);
    }

    /**
     * Delete messaggio response.
     *
     * @param idMessaggio     the id messaggio
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
//    @Override
    public Response deleteMessaggio(Long idMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idMessaggio);
        return getResponseDelete(messaggioDAO.deleteMessaggio(idMessaggio), className);
    }

}