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

import it.csi.scriva.scrivaapisrv.business.be.SoggettiIstanzaApi;
import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.SoggettiIstanzaApiServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivaapisrv.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Soggetti istanza api service.
 */
@Component
public class SoggettiIstanzaApiServiceImpl extends AbstractApiServiceImpl implements SoggettiIstanzaApi {

    private final String CLASSNAME = this.getClass().getSimpleName();

    @Autowired
    private SoggettiIstanzaApiServiceHelper soggettiIstanzaApiServiceHelper;

    @Override
    public Response getSoggettiIstanza(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = soggettiIstanzaApiServiceHelper.getSoggettiIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response getSoggettoIstanza(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoIstanza [" + idSoggettoIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = soggettiIstanzaApiServiceHelper.getSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoIstanza);
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
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response getSoggettoIstanzaByIdSoggettoPadre(Long idSoggettoPadre, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoPadre [" + idSoggettoPadre + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = soggettiIstanzaApiServiceHelper.getSoggettoIstanzaByIdSoggettoPadre(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoPadre);
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
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response getSoggettiIstanzaByCodiceFiscaleSoggetto(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input codiceFiscale [" + codiceFiscale + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = soggettiIstanzaApiServiceHelper.getSoggettiIstanzaByCodiceFiscaleSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiceFiscale);
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
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response saveSoggettoIstanza(SoggettoIstanzaExtendedDTO soggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input soggettoIstanza:\n" + soggettoIstanza + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        SoggettoIstanzaExtendedDTO dto = new SoggettoIstanzaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        soggettoIstanza.setGestAttoreIns(user.getCodFisc());
        try {
            dto = soggettiIstanzaApiServiceHelper.saveSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggettoIstanza);
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
        return getResponseWithSharedHeaders(dto, soggettiIstanzaApiServiceHelper, 201);
    }

    @Override
    public Response updateSoggettoIstanza(SoggettoIstanzaExtendedDTO soggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input soggettoIstanza:\n" + soggettoIstanza + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        SoggettoIstanzaExtendedDTO dto = new SoggettoIstanzaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        soggettoIstanza.setGestAttoreUpd(user.getCodFisc());
        LOGGER.debug("[SoggettiIstanzaApiServiceImpl::updateSoggettoIstanza] user : " + user.toString());
        try {
            dto = soggettiIstanzaApiServiceHelper.updateSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggettoIstanza);
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
        return getResponseWithSharedHeaders(dto, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response deleteSoggettoIstanza(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input uid [" + uid + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        try {
            soggettiIstanzaApiServiceHelper.deleteSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
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

    @Override
    public Response deleteSoggettoIstanzaById(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input id [" + id + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        try {
            soggettiIstanzaApiServiceHelper.deleteSoggettoIstanzaById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), id);
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
     * Load recapiti alternativi by id soggetto istanza response.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response getRecapitiAlternativiByIdSoggettoIstanza(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoIstanza [" + idSoggettoIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        List<RecapitoAlternativoExtendedDTO> recapitoAlternativoList = new ArrayList<>();
        try {
            recapitoAlternativoList = soggettiIstanzaApiServiceHelper.getRecapitiAlternativiByIdSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoIstanza);
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
        return Response.ok(recapitoAlternativoList).header(HttpHeaders.CONTENT_ENCODING, "identity").build();
    }

    /**
     * Save recapito alternativo response.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @Override
    public Response saveRecapitoAlternativo(RecapitoAlternativoExtendedDTO recapitoAlternativo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input recapitoAlternativo:\n" + recapitoAlternativo + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        RecapitoAlternativoExtendedDTO recapitoAlternativoResult = null;
        try {
            recapitoAlternativoResult = soggettiIstanzaApiServiceHelper.saveRecapitoAlternativo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), recapitoAlternativo);
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
        return getResponseWithSharedHeaders(recapitoAlternativoResult, soggettiIstanzaApiServiceHelper, 201);
    }

    /**
     * Update recapito alternativo response.
     *
     * @param recapitoAlternativo the recapito alternativo
     * @param securityContext     the security context
     * @param httpHeaders         the http headers
     * @param httpRequest         the http request
     * @return the response
     */
    @Override
    public Response updateRecapitoAlternativo(RecapitoAlternativoExtendedDTO recapitoAlternativo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input recapitoAlternativo:\n" + recapitoAlternativo + "\n";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        try {
            recapitoAlternativo = soggettiIstanzaApiServiceHelper.updateRecapitoAlternativo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), recapitoAlternativo);
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
        return getResponseWithSharedHeaders(recapitoAlternativo, soggettiIstanzaApiServiceHelper, 201);
    }

    /**
     * Delete recapito alternativo response.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response deleteRecapitoAlternativo(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String inputParam = "Parametro in input idSoggettoIstanza [" + idSoggettoIstanza + "]";
        LOGGER.debug(getClassFunctionBeginInfo(CLASSNAME, methodName));
        LOGGER.debug(getClassFunctionDebugString(CLASSNAME, methodName, inputParam));
        try {
            soggettiIstanzaApiServiceHelper.deleteRecapitoAlternativo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoIstanza);
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

}