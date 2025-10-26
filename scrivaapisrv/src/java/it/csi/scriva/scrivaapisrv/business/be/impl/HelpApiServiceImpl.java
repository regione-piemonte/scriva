/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.impl;

import it.csi.scriva.scrivaapisrv.business.be.HelpApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.HelpApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.HelpExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class HelpApiServiceImpl extends AbstractApiServiceImpl implements HelpApi {

    @Autowired
    private HelpApiServiceHelper helpApiServiceHelper;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getHelp(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[HelpApiServiceImpl::getHelp] BEGIN");
        List<HelpExtendedDTO> helpList = new ArrayList<>();
        try {
            helpList = helpApiServiceHelper.getHelp(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[HelpApiServiceImpl::getHelp] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[HelpApiServiceImpl::getHelp] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[HelpApiServiceImpl::getHelp] END");
        }
        return Response.ok(helpList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * @param idHelp          idHelp
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getHelpById(Long idHelp, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[HelpApiServiceImpl::getHelpById] BEGIN");
        List<HelpExtendedDTO> helpList = new ArrayList<>();
        try {
            helpList = helpApiServiceHelper.getHelpById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idHelp);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[HelpApiServiceImpl::getHelpById] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[HelpApiServiceImpl::getHelpById] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[HelpApiServiceImpl::getHelpById] END");
        }
        return Response.ok(helpList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * @param chiaveHelp      chiaveHelp
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getHelpByChiave(String chiaveHelp, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        LOGGER.debug("[HelpApiServiceImpl::getHelpByChiave] BEGIN");
        List<HelpExtendedDTO> helpList = new ArrayList<>();
        try {
            helpList = helpApiServiceHelper.getHelpByChiave(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), chiaveHelp);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error("[HelpApiServiceImpl::getHelpByChiave] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error("[HelpApiServiceImpl::getHelpByChiave] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug("[HelpApiServiceImpl::getHelpByChiave] END");
        }
        return Response.ok(helpList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }
}