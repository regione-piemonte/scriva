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

import it.csi.scriva.scrivafoweb.business.be.CategorieOggettoApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.CategorieOggettoApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaCategoriaExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.ListObjectWithHeaderResultFoDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Categorie oggetto api service.
 */
@Component
public class CategorieOggettoApiServiceImpl extends ProxyApiServiceImpl implements CategorieOggettoApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Categorie oggetto api service helper.
     */
    @Autowired
    CategorieOggettoApiServiceHelper categorieOggettoApiServiceHelper;

    /**
     * Gets categorie progettuali.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param idAdempimento    the id adempimento
     * @param idIstanza        the id istanza
     * @param idOggettoIstanza the id oggetto istanza
     * @param search           the search
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @param uriInfo          the uri info
     * @return Response categorie progettuali
     */
    @Override
    public Response getCategorieProgettuali(String xRequestAuth, String xRequestId, Long idAdempimento, Long idIstanza, Long idOggettoIstanza, String search, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) {
        logBegin(className);
        ListObjectWithHeaderResultFoDTO categoriaOggettoList = null;
        try {
            categoriaOggettoList = categorieOggettoApiServiceHelper.getCategorieProgettuali(idAdempimento, idIstanza, idOggettoIstanza, search, getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
            return Response.ok(categoriaOggettoList.getObjectList()).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header("CountCompetenze", categoriaOggettoList.getCountHeader()).build();
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
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategoriaProgettualeByIdAdempimento(Long idAdempimento, String search, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "] - search [" + search + "]");
        ListObjectWithHeaderResultFoDTO categoriaOggettoList = null;
        try {
            categoriaOggettoList = categorieOggettoApiServiceHelper.getCategorieProgettualiByIdAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento, search);
            return Response.ok(categoriaOggettoList.getObjectList()).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header("CountCompetenze", categoriaOggettoList.getCountHeader()).build();
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
     * @param idOggettoIstanza idOggettoIstanza
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategoriaProgettualeByIdOggettoIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        try {
            return Response.ok(categorieOggettoApiServiceHelper.getCategorieProgettualiByIdOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggettoIstanza)).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
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
     * @param oggettoIstanzaCategoria oggettoIstanzaCategoria
     * @param securityContext         SecurityContext
     * @param httpHeaders             HttpHeaders
     * @param httpRequest             HttpServletRequest
     * @return Response
     */
    @Override
    public Response saveOggettoIstanzaCategoria(OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanzaCategoria);
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanzaCategoria.setGestAttoreIns(user.getCodFisc());
        try {
            return getResponseWithSharedHeaders(categorieOggettoApiServiceHelper.saveOggettoIstanzaCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanzaCategoria), categorieOggettoApiServiceHelper, 201);
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
     * @param oggettoIstanzaCategoria oggettoIstanzaCategoria
     * @param securityContext         SecurityContext
     * @param httpHeaders             HttpHeaders
     * @param httpRequest             HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateOggettoIstanzaCategoria(OggettoIstanzaCategoriaExtendedDTO oggettoIstanzaCategoria, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanzaCategoria);
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanzaCategoria.setGestAttoreIns(user.getCodFisc());
        try {
            return getResponseWithSharedHeaders(categorieOggettoApiServiceHelper.updateOggettoIstanzaCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanzaCategoria), categorieOggettoApiServiceHelper, 200);
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
     * Add categorie modifica response.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    public Response addCategorieModifica(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        try {
            return getResponseWithSharedHeaders(categorieOggettoApiServiceHelper.addCategorieModifica(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggettoIstanza), categorieOggettoApiServiceHelper, 200);
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
     * Delete soggetto istanza response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response deleteOggettoIstanzaCategoria(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);
        try {
            categorieOggettoApiServiceHelper.deleteOggettoIstanzaCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
            return getResponseWithSharedHeaders(null, categorieOggettoApiServiceHelper, 204);
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
}