/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.TipiAdempimentoApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.TipiAdempimentoApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.TipoAdempimentoExtendedDTO;
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
public class TipiAdempimentoApiServiceImpl extends AbstractApiServiceImpl implements TipiAdempimentoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    TipiAdempimentoApiServiceHelper tipiAdempimentoApiServiceHelper;

    /**
     * Gets tipi adempimento.
     *
     * @param idAmbito           the id ambito
     * @param idCompilante       the id compilante
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response tipi adempimento
     */
    @Override
    public Response getTipiAdempimento(Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "Parametro in input idAmbito [" + idAmbito + "] - codAmbito [" + codAmbito + "] - idCompilante [" + idCompilante + "] - codTipoAdempimento [" + codTipoAdempimento + "]");
        List<TipoAdempimentoExtendedDTO> listTipiAdempimento = new ArrayList<>();
        try {
            listTipiAdempimento = tipiAdempimentoApiServiceHelper.getTipiAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAmbito, idCompilante, codTipoAdempimento, codAmbito);
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
        return Response.ok(listTipiAdempimento).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * @param idTipoAdempimento idTipoAdempimento
     * @param securityContext   SecurityContext
     * @param httpHeaders       HttpHeaders
     * @param httpRequest       HttpServletRequest
     * @return Response
     */
    @Override
    public Response getTipoAdempimento(Long idTipoAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idTipoAdempimento);
        List<TipoAdempimentoExtendedDTO> listTipiAdempimento = new ArrayList<>();
        try {
            listTipiAdempimento = tipiAdempimentoApiServiceHelper.getTipoAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idTipoAdempimento);
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
        return Response.ok(listTipiAdempimento).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

}