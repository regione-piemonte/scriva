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

import it.csi.scriva.scrivaapisrv.business.be.CompetenzeTerritorioApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.CompetenzeTerritorioApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Competenze territorio api service.
 */
@Component
public class CompetenzeTerritorioApiServiceImpl extends AbstractApiServiceImpl implements CompetenzeTerritorioApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    /**
     * The Competenze territorio api service helper.
     */
    @Autowired
    CompetenzeTerritorioApiServiceHelper competenzeTerritorioApiServiceHelper;

    /**
     * Gets competenze territorio.
     *
     * @param idAdempimento   the id adempimento
     * @param idIstanza       the id istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response competenze territorio
     */
    @Override
    public Response getCompetenzeTerritorio(Long idAdempimento, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idAdempimento [" + idAdempimento + "] - idIstanza [" + idIstanza + "]";
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        try {
            return Response.ok(competenzeTerritorioApiServiceHelper.getCompetenzeTerritorio(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento, idIstanza)).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
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
    }

    /**
     * Verifica competenze territorio by id istanza adempimento response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response verificaCompetenzeTerritorioByIdIstanzaAdempimento(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = new ArrayList<IstanzaCompetenzaExtendedDTO>();
        try {
            istanzaCompetenzaList = competenzeTerritorioApiServiceHelper.verificaCompetenzeTerritorioByIdIstanzaAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
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
        return Response.ok(istanzaCompetenzaList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Calculate istanza competenza territorio secondarie response.
     *
     * @param idIstanza         the id istanza
     * @param livelliCompetenza the livelli competenza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response calculateIstanzaCompetenzaTerritorioSecondarie(Long idIstanza, String livelliCompetenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "] - livelliCompetenza [" + livelliCompetenza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<IstanzaCompetenzaExtendedDTO> istanzaCompetenzaList = new ArrayList<IstanzaCompetenzaExtendedDTO>();
        try {
            istanzaCompetenzaList = competenzeTerritorioApiServiceHelper.calculateIstanzaCompetenzaTerritorioSecondarie(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, livelliCompetenza);
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
        return Response.ok(istanzaCompetenzaList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Delete istanza competenza territorio secondarie response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response deleteIstanzaCompetenzaTerritorioSecondarie(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idIstanza: [" + idIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        try {
            competenzeTerritorioApiServiceHelper.deleteIstanzaCompetenzaTerritorioSecondarie(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
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
        return Response.noContent().build();
    }

    /**
     * @param istanzaCompetenza istanzaCompetenza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response saveIstanzaCompetenzaTerritorio(IstanzaCompetenzaExtendedDTO istanzaCompetenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input istanzaCompetenza:\n" + istanzaCompetenza + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        IstanzaCompetenzaExtendedDTO dto = new IstanzaCompetenzaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaCompetenza.setGestAttoreIns(user.getCodFisc());
        try {
            dto = competenzeTerritorioApiServiceHelper.saveIstanzaCompetenzaTerritorio(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaCompetenza);
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
        return getResponseWithSharedHeaders(dto, competenzeTerritorioApiServiceHelper, 201);
    }

    /**
     * @param istanzaCompetenza istanzaCompetenza
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response updateIstanzaCompetenzaTerritorio(IstanzaCompetenzaExtendedDTO istanzaCompetenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input istanzaCompetenza:\n" + istanzaCompetenza + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        IstanzaCompetenzaExtendedDTO dto = new IstanzaCompetenzaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        istanzaCompetenza.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = competenzeTerritorioApiServiceHelper.updateIstanzaCompetenzaTerritorio(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaCompetenza);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, err));
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            LOGGER.error(getClassFunctionErrorInfo(CLASSNAME, methodName, e));
            return Response.serverError().entity(err).status(500).build();
        } finally {
            LOGGER.debug(getClassFunctionEndInfo(CLASSNAME, methodName));
        }
        return getResponseWithSharedHeaders(dto, competenzeTerritorioApiServiceHelper, 200);
    }
}