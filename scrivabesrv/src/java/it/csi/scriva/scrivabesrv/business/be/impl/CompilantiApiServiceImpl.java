/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl;

import it.csi.scriva.scrivabesrv.business.be.CompilantiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilantePreferenzaDAO;
import it.csi.scriva.scrivabesrv.business.be.service.CompilantePreferenzaCanaleService;
import it.csi.scriva.scrivabesrv.business.be.service.CompilantiService;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * The type Compilante api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompilantiApiServiceImpl extends BaseApiServiceImpl implements CompilantiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private CompilantiService compilantiService;

    @Autowired
    private CompilantePreferenzaCanaleService compilantePreferenzaCanaleService;

    @Autowired
    private CompilantePreferenzaDAO compilantePreferenzaDAO;

    /**
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCompilanti(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(compilantiService.loadCompilanti(), className);
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
    public Response updateCompilanti(CompilanteDTO compilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, compilante);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        compilante.setCodiceFiscaleCompilante(attoreScriva.getCodiceFiscale());
        compilante.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        compilantiService.updateCompilante(compilante);
        List<CompilanteDTO> compilanteList = compilantiService.loadCompilanti(attoreScriva.getCodiceFiscale());
        return getResponseSave(CollectionUtils.isNotEmpty(compilanteList) ? compilanteList.get(0) : compilante, className, null);
    }

    /**
     * @param codiceFiscale   codiceFiscale
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadCompilanteByCodiceFiscale(String codiceFiscale, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, codiceFiscale);
        List<CompilanteDTO> compilanteList = compilantiService.loadCompilanti(codiceFiscale);
        if (compilanteList == null) {
            ErrorDTO error = getErrorManager().getError("404", "E100", null, null, null);
            return getResponseError(className, error);
        }
        return Response.ok(compilanteList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).build();
    }

    /**
     * @param idCompilante idCompilante
     * @param httpHeaders  HttpHeaders
     * @param httpRequest  HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadPreferenzeByCompilante(Long idCompilante, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idCompilante);
        return getResponseList(compilantePreferenzaDAO.loadCompilantePreferenzeByCompilante(idCompilante), className);
    }

    /**
     * @param cfCompilante cfCompilante
     * @param httpHeaders  HttpHeaders
     * @param httpRequest  HttpServletRequest
     * @return Response
     */
    @Override
    public Response loadPreferenzeByCfCompilante(String cfCompilante, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, cfCompilante);
        return getResponseList(compilantePreferenzaDAO.loadCompilantePreferenzeByCodiceFiscale(cfCompilante), className);
    }

    /**
     * @param preferenza      CompilantePreferenzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response addPreferenza(CompilantePreferenzaExtendedDTO preferenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, preferenza);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        preferenza.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        Long idPreferenza = compilantePreferenzaDAO.saveCompilantePreferenza(preferenza.getDTO());
        preferenza = idPreferenza != null ? compilantePreferenzaDAO.loadCompilantePreferenza(idPreferenza).get(0) : null;
        return getResponseSave(preferenza, className, preferenza != null ? "/preferenze/id-compilante/" + preferenza.getCompilante().getIdCompilante() : null);

    }

    /**
     * Load preferenze notifiche by cf compilante response.
     *
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @return the response
     */
    @Override
    public Response loadPreferenzeNotificheByCfCompilante(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        return getResponseList(compilantePreferenzaCanaleService.loadCompilantePreferenzeByCodiceFiscale(attoreScriva.getCodiceFiscale()), className);
    }

    /**
     * Upsert compilante preferenza canale response.
     *
     * @param compilantePreferenzaCanaleList the compilante preferenza canale list
     * @param httpHeaders                    the http headers
     * @param httpRequest                    the http request
     * @return the response
     */
    @Override
    public Response upsertCompilantePreferenzaCanale(List<CompilantePreferenzaCanaleExtendedDTO> compilantePreferenzaCanaleList, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, compilantePreferenzaCanaleList);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        compilantePreferenzaCanaleService.upsertCompilantePreferenzaCanale(compilantePreferenzaCanaleList, attoreScriva);
        return getResponseList(compilantePreferenzaCanaleService.loadCompilantePreferenzeByCodiceFiscale(attoreScriva.getCodiceFiscale()), className);
    }

    /**
     * @param idPreferenza    idPreferenza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response deletePreferenza(Long idPreferenza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idPreferenza);
        return getResponseDelete(compilantePreferenzaDAO.deleteCompilantePreferenza(idPreferenza), className);
    }

    /**
     * Logout response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response logout(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        httpRequest.getSession().invalidate();
        return Response.noContent().build();
    }

}