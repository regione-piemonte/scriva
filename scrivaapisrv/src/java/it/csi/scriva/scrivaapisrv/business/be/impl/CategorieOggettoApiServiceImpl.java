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

import it.csi.scriva.scrivaapisrv.business.be.CategorieOggettoApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.CategorieOggettoApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.CategoriaOggettoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.OggettoIstanzaCategoriaExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Categorie oggetto api service.
 */
@Component
public class CategorieOggettoApiServiceImpl extends AbstractApiServiceImpl implements CategorieOggettoApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    /**
     * The Categorie oggetto api service helper.
     */
    @Autowired
    CategorieOggettoApiServiceHelper categorieOggettoApiServiceHelper;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategorieProgettuali(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<CategoriaOggettoExtendedDTO> categoriaOggettoList = new ArrayList<CategoriaOggettoExtendedDTO>();
        try {
            categoriaOggettoList = categorieOggettoApiServiceHelper.getCategorieProgettuali(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(categoriaOggettoList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<CategoriaOggettoExtendedDTO> categoriaOggettoList = new ArrayList<CategoriaOggettoExtendedDTO>();
        try {
            categoriaOggettoList = categorieOggettoApiServiceHelper.getCategorieProgettualiByIdAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento, search);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(categoriaOggettoList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<CategoriaOggettoExtendedDTO> categoriaOggettoList = new ArrayList<CategoriaOggettoExtendedDTO>();
        try {
            categoriaOggettoList = categorieOggettoApiServiceHelper.getCategorieProgettualiByIdOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return Response.ok(categoriaOggettoList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input oggettoIstanzaCategoria:\n" + oggettoIstanzaCategoria + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        OggettoIstanzaCategoriaExtendedDTO dto = new OggettoIstanzaCategoriaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanzaCategoria.setGestAttoreIns(user.getCodFisc());
        try {
            dto = categorieOggettoApiServiceHelper.saveOggettoIstanzaCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanzaCategoria);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return getResponseWithSharedHeaders(dto, categorieOggettoApiServiceHelper, 201);
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input oggettoIstanzaCategoria:\n" + oggettoIstanzaCategoria + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        OggettoIstanzaCategoriaExtendedDTO dto = new OggettoIstanzaCategoriaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanzaCategoria.setGestAttoreIns(user.getCodFisc());
        try {
            dto = categorieOggettoApiServiceHelper.updateOggettoIstanzaCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanzaCategoria);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return getResponseWithSharedHeaders(dto, categorieOggettoApiServiceHelper, 200);
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idOggettoIstanza [" + idOggettoIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<OggettoIstanzaCategoriaExtendedDTO> oggettoIstanzaCategoriaList = new ArrayList<>();
        UserInfo user = getSessionUser(httpRequest);
        try {
            oggettoIstanzaCategoriaList = categorieOggettoApiServiceHelper.addCategorieModifica(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggettoIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return getResponseWithSharedHeaders(oggettoIstanzaCategoriaList, categorieOggettoApiServiceHelper, 200);
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
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input uid [" + uid + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        UserInfo user = getSessionUser(httpRequest);
        try {
            categorieOggettoApiServiceHelper.deleteOggettoIstanzaCategoria(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return getResponseWithSharedHeaders(null, categorieOggettoApiServiceHelper, 204);
    }
}