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

import it.csi.scriva.scrivafoweb.business.be.CompilantiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.CompilantiApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompilanteDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompilantePreferenzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * The type Compilanti api service.
 */
@Component
public class CompilantiApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements CompilantiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private CompilantiApiServiceHelper compilantiApiServiceHelper;

    @Override
    public Response getCompilanti(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        try {
            return Response.ok(compilantiApiServiceHelper.getCompilanti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest))).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response getCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        try {
            UserInfo user = getSessionUser(httpRequest);
            List<CompilanteDTO> list = compilantiApiServiceHelper.getCompilanteByCodiceFiscale(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), user.getCodFisc());
            if (list.isEmpty()) {
                CompilanteDTO compilante = new CompilanteDTO();
                compilante.setCodiceFiscaleCompilante(user.getCodFisc());
                compilante.setNomeCompilante(user.getNome());
                compilante.setCognomeCompilante(user.getCognome());
                list.add(compilante);
            }
            return Response.ok(list).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update compilanti response.
     *
     * @param compilante      the compilante
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response updateCompilanti(Map<String, Object> requestBody, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, requestBody);
        return getResponsePUT(uriInfo.getPath(), requestBody, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Gets compilante.
     *
     * @param idCompilante    the id compilante
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the compilante
     */
//@Override
    public Response getCompilante(Long idCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idCompilante);
        try {
            return Response.ok(compilantiApiServiceHelper.getCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idCompilante)).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response getCompilanteByCodiceFiscale(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, (Object) codiceFiscale);
        try {
            return Response.ok(compilantiApiServiceHelper.getCompilanteByCodiceFiscale(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiceFiscale)).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response addPreferenza(CompilantePreferenzaExtendedDTO preferenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, preferenza);
        try {
            UserInfo user = getSessionUser(httpRequest);
            preferenza.setGestAttoreIns(user.getCodFisc());
            return Response.ok(compilantiApiServiceHelper.addPreferenza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), preferenza)).status(201).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response getPreferenzeByCompilante(Long idCompilante, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idCompilante);
        try {
            return Response.ok(compilantiApiServiceHelper.getPreferenzeByCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idCompilante)).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response getPreferenzeByCfCompilante(String cfCompilante, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, (Object) cfCompilante);
        try {
            return Response.ok(compilantiApiServiceHelper.getPreferenzeByCfCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), cfCompilante)).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response deletePreferenza(Long idPreferenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idPreferenza);
        try {
            compilantiApiServiceHelper.deletePreferenza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idPreferenza);
            return Response.noContent().build();
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
    }

    @Override
    public Response logout(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        compilantiApiServiceHelper.logout(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        httpRequest.getSession().invalidate();
        return Response.noContent().build();
    }

    /**
     * @param httpHeaders
     * @param httpRequest
     * @return
     */

    @Override
    public Response loadPreferenzeNotificheByCfCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {

        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);

    }

    /**
     * @param compilantePreferenzaCanaleList
     * @param httpHeaders
     * @param httpRequest
     * @return
     */
    @Override
    public Response upsertCompilantePreferenzaCanale(List<Map<?, ?>> compilantePreferenzaCanaleList,
                                                     SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {


        return getResponsePUT(uriInfo.getPath(), compilantePreferenzaCanaleList, securityContext, httpHeaders, httpRequest, uriInfo);

    }

}