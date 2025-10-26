/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.scriva.scrivaapisrv.business.be.MessaggiApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.MessaggiApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.MessaggioExtendedDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessaggiApiServiceImpl extends AbstractApiServiceImpl implements MessaggiApi {

    @Autowired
    MessaggiApiServiceHelper messaggiApiServiceHelper;

    @Override
    public Response getMessaggi(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[MessaggiApiServiceImpl::getMessaggi] BEGIN");
        List<MessaggioExtendedDTO> listMessaggi = new ArrayList<>();
        try {
            listMessaggi = messaggiApiServiceHelper.getMessaggi(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[MessaggiApiServiceImpl::getMessaggi] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[MessaggiApiServiceImpl::getMessaggi] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[MessaggiApiServiceImpl::getMessaggi] END");
        }
        return Response.ok(listMessaggi).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getMessaggio(Long idMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[MessaggiApiServiceImpl::getMessaggio] BEGIN");
        LOGGER.debug("[MessaggiApiServiceImpl::getMessaggio] Parametro in input idMessaggio [" + idMessaggio + "]");
        List<MessaggioExtendedDTO> listMessaggi = new ArrayList<>();
        try {
            listMessaggi = messaggiApiServiceHelper.getMessaggio(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idMessaggio);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[MessaggiApiServiceImpl::getMessaggio] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[MessaggiApiServiceImpl::getMessaggio] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[MessaggiApiServiceImpl::getMessaggio] END");
        }
        return Response.ok(listMessaggi).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    @Override
    public Response getMessaggioByCode(String codMessaggio, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[MessaggiApiServiceImpl::getMessaggioByCode] BEGIN");
        LOGGER.debug("[MessaggiApiServiceImpl::getMessaggioByCode] Parametro in input codMessaggio [" + codMessaggio + "]");
        List<MessaggioExtendedDTO> listMessaggi = new ArrayList<>();
        try {
            listMessaggi = messaggiApiServiceHelper.getMessaggioByCode(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codMessaggio);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[MessaggiApiServiceImpl::getMessaggioByCode] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[MessaggiApiServiceImpl::getMessaggioByCode] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[MessaggiApiServiceImpl::getMessaggioByCode] END");
        }
        return Response.ok(listMessaggi).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

}