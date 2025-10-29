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


import it.csi.scriva.scrivafoweb.business.be.SoggettiIstanzaApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.SoggettiIstanzaApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
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
 * The type Soggetti istanza api service.
 */
@Component
public class SoggettiIstanzaApiServiceImpl extends AbstractApiServiceImpl implements SoggettiIstanzaApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private SoggettiIstanzaApiServiceHelper soggettiIstanzaApiServiceHelper;

    @Override
    public Response getSoggettiIstanza(String xRequestAuth, String xRequestId,
                                       Long idIstanza,
                                       SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = idIstanza != null ? soggettiIstanzaApiServiceHelper.getSoggettoIstanzaByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza) : soggettiIstanzaApiServiceHelper.getSoggettiIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response getSoggettoIstanza(Long idSoggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggettoIstanza);
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = soggettiIstanzaApiServiceHelper.getSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoIstanza);
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
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response getSoggettoIstanzaByIdSoggettoPadre(Long idSoggettoPadre, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggettoPadre);
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = soggettiIstanzaApiServiceHelper.getSoggettoIstanzaByIdSoggettoPadre(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoPadre);
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
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response getSoggettiIstanzaByCodiceFiscaleSoggetto(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className,"Parametro in input codiceFiscale [" + codiceFiscale + "]");
        List<SoggettoIstanzaExtendedDTO> list = new ArrayList<>();
        try {
            list = soggettiIstanzaApiServiceHelper.getSoggettiIstanzaByCodiceFiscaleSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiceFiscale);
        } catch (GenericException e) {
            ErrorDTO err = e.getError();
            logError(className, e);
            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
        } catch (ProcessingException e) {
            logError(className, e);
            String errorMessage = e.getMessage();
            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
            return Response.serverError().entity(err).status(500).build();
        } finally {
            logEnd(className);
        }
        return getResponseWithSharedHeaders(list, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response saveSoggettoIstanza(SoggettoIstanzaExtendedDTO soggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, soggettoIstanza);
        SoggettoIstanzaExtendedDTO dto = new SoggettoIstanzaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        soggettoIstanza.setGestAttoreIns(user.getCodFisc());
        try {
            dto = soggettiIstanzaApiServiceHelper.saveSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggettoIstanza);
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
        return getResponseWithSharedHeaders(dto, soggettiIstanzaApiServiceHelper, 201);
    }

    @Override
    public Response updateSoggettoIstanza(SoggettoIstanzaExtendedDTO soggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, soggettoIstanza);
        SoggettoIstanzaExtendedDTO dto = new SoggettoIstanzaExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        soggettoIstanza.setGestAttoreUpd(user.getCodFisc());
        logger.debug("[SoggettiIstanzaApiServiceImpl::updateSoggettoIstanza] user : " + user.toString());
        try {
            dto = soggettiIstanzaApiServiceHelper.updateSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggettoIstanza);
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
        return getResponseWithSharedHeaders(dto, soggettiIstanzaApiServiceHelper);
    }

    @Override
    public Response deleteSoggettoIstanza(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className,"Parametro in input uid [" + uid + "]");
        try {
            soggettiIstanzaApiServiceHelper.deleteSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
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
        return Response.noContent().build();
    }

    @Override
    public Response deleteSoggettoIstanzaById(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, id);
        try {
            soggettiIstanzaApiServiceHelper.deleteSoggettoIstanzaById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), id);
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
        logBeginInfo(className, idSoggettoIstanza);
        List<RecapitoAlternativoExtendedDTO> recapitoAlternativoList = new ArrayList<>();
        try {
            recapitoAlternativoList = soggettiIstanzaApiServiceHelper.getRecapitiAlternativiByIdSoggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoIstanza);
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
        return Response.ok(recapitoAlternativoList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
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
        logBeginInfo(className, recapitoAlternativo);
        RecapitoAlternativoExtendedDTO recapitoAlternativoResult = null;
        try {
            recapitoAlternativoResult = soggettiIstanzaApiServiceHelper.saveRecapitoAlternativo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), recapitoAlternativo);
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
        logBeginInfo(className, recapitoAlternativo);
        try {
            recapitoAlternativo = soggettiIstanzaApiServiceHelper.updateRecapitoAlternativo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), recapitoAlternativo);
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
        logBeginInfo(className, idSoggettoIstanza);
        try {
            soggettiIstanzaApiServiceHelper.deleteRecapitoAlternativo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggettoIstanza);
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
        return Response.noContent().build();
    }

}