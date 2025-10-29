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

import it.csi.scriva.scrivafoweb.business.be.CategorieAllegatoApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.CategorieAllegatoApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Categorie allegato api service.
 */
@Component
public class CategorieAllegatoApiServiceImpl extends AbstractApiServiceImpl implements CategorieAllegatoApi {

    /**
     * The Categorie allegato api service helper.
     */
    @Autowired
    CategorieAllegatoApiServiceHelper categorieAllegatoApiServiceHelper;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategorieAllegato(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logger.debug("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] BEGIN");
        List<CategoriaAllegatoDTO> listCategorieAllegato = new ArrayList<CategoriaAllegatoDTO>();
        try {
            listCategorieAllegato = categorieAllegatoApiServiceHelper.getCategorieAllegato(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] END");
        }
        return Response.ok(listCategorieAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * @param idAdempimento   idAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategoriaAllegatoByIdAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logger.debug("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] BEGIN");
        logger.debug("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] Parametro in input idAdempimento [" + idAdempimento + "]");
        List<CategoriaAllegatoDTO> listCategorieAllegato = new ArrayList<CategoriaAllegatoDTO>();
        try {
            listCategorieAllegato = categorieAllegatoApiServiceHelper.getCategoriaAllegatoByIdAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug("[CategorieAllegatoApiServiceImpl::getCategorieAllegato] END");
        }
        return Response.ok(listCategorieAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response getCategoriaAllegatoByCodeAdempimento(String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logger.debug("[CategorieAllegatoApiServiceImpl::getCategoriaAllegatoByCodeAdempimento] BEGIN");
        logger.debug("[CategorieAllegatoApiServiceImpl::getCategoriaAllegatoByCodeAdempimento] Parametro in input codAdempimento [" + codAdempimento + "]");
        List<CategoriaAllegatoDTO> listCategorieAllegato = new ArrayList<CategoriaAllegatoDTO>();
        try {
            listCategorieAllegato = categorieAllegatoApiServiceHelper.getCategoriaAllegatoByCodeAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error("[CategorieAllegatoApiServiceImpl::getCategoriaAllegatoByCodeAdempimento] ERROR : " + err);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error("[CategorieAllegatoApiServiceImpl::getCategoriaAllegatoByCodeAdempimento] ERROR : " + errorMessage);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug("[CategorieAllegatoApiServiceImpl::getCategoriaAllegatoByCodeAdempimento] END");
        }
        return Response.ok(listCategorieAllegato).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }
}