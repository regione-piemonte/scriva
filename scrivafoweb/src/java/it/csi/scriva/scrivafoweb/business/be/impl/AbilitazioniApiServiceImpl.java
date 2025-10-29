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

import it.csi.scriva.scrivafoweb.business.be.AbilitazioniApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.AbilitazioniApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaAttoreExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoAbilitazioneDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * The type Abilitazioni api service.
 */
@Component
public class AbilitazioniApiServiceImpl extends AbstractApiServiceImpl implements AbilitazioniApi {

    /**
     * The constant PARAMETRO_IN_INPUT_ID_ISTANZA.
     */
    public static final String PARAMETRO_IN_INPUT_ID_ISTANZA = "Parametro in input idIstanza [";
    private final String className = this.getClass().getSimpleName();

    /**
     * The Abilitazioni api service helper.
     */
    @Autowired
    AbilitazioniApiServiceHelper abilitazioniApiServiceHelper;

    /**
     * Load abilitazioni by id istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getAbilitazioniByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = PARAMETRO_IN_INPUT_ID_ISTANZA + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        List<TipoAbilitazioneDTO> tipoAbilitazioneList;
        try {
            tipoAbilitazioneList = abilitazioniApiServiceHelper.getAbilitazioniByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return Response.ok(tipoAbilitazioneList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Load abilitazioni revocabili for istanza by id istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getAbilitazioniRevocabiliForIstanzaByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = PARAMETRO_IN_INPUT_ID_ISTANZA + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList;
        try {
            istanzaAttoreList = abilitazioniApiServiceHelper.getAbilitazioniRevocabiliForIstanzaByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return Response.ok(istanzaAttoreList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Load abilitazioni concesse for istanza by id istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getAbilitazioniConcesseForIstanzaByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = PARAMETRO_IN_INPUT_ID_ISTANZA + idIstanza + "]";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        List<IstanzaAttoreExtendedDTO> istanzaAttoreList;
        try {
            istanzaAttoreList = abilitazioniApiServiceHelper.getAbilitazioniConcesseForIstanzaByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return Response.ok(istanzaAttoreList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Update abilitazione response.
     *
     * @param istanzaAttore   the istanza attore
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response updateAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input istanzaAttore :\n" + istanzaAttore + "\n";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        IstanzaAttoreExtendedDTO dto = new IstanzaAttoreExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaAttore.setGestAttoreIns(user.getCodFisc());
        try {
            dto = abilitazioniApiServiceHelper.updateAbilitazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaAttore);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return getResponseWithSharedHeaders(dto, abilitazioniApiServiceHelper, 200);
    }

    /**
     * Save abilitazione response.
     *
     * @param istanzaAttore   the istanza attore
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input istanzaAttore :\n" + istanzaAttore + "\n";
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        logger.debug(getClassFunctionDebugString(className, methodName, inputParam));
        IstanzaAttoreExtendedDTO dto = new IstanzaAttoreExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaAttore.setGestAttoreIns(user.getCodFisc());
        try {
            dto = abilitazioniApiServiceHelper.saveAbilitazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaAttore);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            logger.error(getClassFunctionErrorInfo(className, methodName, err));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
        return getResponseWithSharedHeaders(dto, abilitazioniApiServiceHelper, 201);
    }
}