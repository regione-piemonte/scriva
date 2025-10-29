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

import it.csi.scriva.scrivafoweb.business.be.SoggettiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.SoggettiApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SoggettoExtendedDTO;
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

@Component
public class SoggettiApiServiceImpl extends AbstractApiServiceImpl implements SoggettiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    SoggettiApiServiceHelper soggettiApiServiceHelper;

    @Override
    public Response getSoggetti(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<SoggettoExtendedDTO> listSoggetti = new ArrayList<>();
        try {
            listSoggetti = soggettiApiServiceHelper.getSoggetti(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getSoggettoById(Long idSoggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idSoggetto);
        List<SoggettoExtendedDTO> listSoggetti = new ArrayList<>();
        try {
            listSoggetti = soggettiApiServiceHelper.getSoggettoById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idSoggetto);
            if (listSoggetti.isEmpty()) {
                String errorMessage = "Risorsa non trovata con id [" + idSoggetto + "]";
                ErrorDTO err = new ErrorDTO("400", "", errorMessage, null, null);
                logError(className, err);
                return Response.serverError().entity(err).status(404).build();
            }
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
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getSoggettoByCodiceFiscaleAndTipo(String codiceFiscale, String tipoSoggetto, String tipoAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametri in input codiceFiscale [" + codiceFiscale + "] - tipoSoggetto [" + tipoSoggetto + "] - tipoAdempimento [" + tipoAdempimento + "]");
        List<SoggettoExtendedDTO> listSoggetti = new ArrayList<>();
        try {
            listSoggetti = soggettiApiServiceHelper.getSoggettoByCodiceFiscaleAndTipo(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiceFiscale, tipoSoggetto, tipoAdempimento);
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
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response getSoggettoImpresa(String codiceFiscaleImpresa, String codiceFiscaleSoggetto, String tipoSoggetto, String tipoAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametri in input codiceFiscaleImpresa [" + codiceFiscaleImpresa + "] - codiceFiscaleSoggetto [" + codiceFiscaleSoggetto + "] - tipoSoggetto [" + tipoSoggetto + "] - tipoAdempimento [" + tipoAdempimento + "]");
        List<SoggettoExtendedDTO> listSoggetti = new ArrayList<>();
        try {
            listSoggetti = soggettiApiServiceHelper.getSoggettoImpresa(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codiceFiscaleImpresa, codiceFiscaleSoggetto, tipoSoggetto, tipoAdempimento);
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
        return Response.ok(listSoggetti).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response saveSoggetto(SoggettoExtendedDTO soggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, soggetto);
        SoggettoExtendedDTO dto = new SoggettoExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        soggetto.setGestAttoreIns(user.getCodFisc());
        try {
            dto = soggettiApiServiceHelper.saveSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggetto);
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
        return Response.ok(dto).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).status(201).build();
    }

    @Override
    public Response updateSoggetto(SoggettoExtendedDTO soggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, soggetto);
        SoggettoExtendedDTO dto = new SoggettoExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        soggetto.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = soggettiApiServiceHelper.updateSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), soggetto);
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
        return Response.ok(dto).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    @Override
    public Response deleteSoggetto(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametro in input uid [" + uid + "]");
        try {
            soggettiApiServiceHelper.deleteSoggetto(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
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