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

import it.csi.scriva.scrivafoweb.business.be.VincoliAutorizzazioniApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.VincoliAutorizzazioniApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AdempimentoTipoAllegatoExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.VincoloAutorizzaExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class VincoliAutorizzazioniApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements VincoliAutorizzazioniApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    VincoliAutorizzazioniApiServiceHelper vincoliAutorizzazioniApiServiceHelper;

    /**
     * Load vincoli autorizzazioni response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getVincoliAutorizzazioni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<VincoloAutorizzaExtendedDTO> vincoloAutorizzaList = new ArrayList<VincoloAutorizzaExtendedDTO>();
        try {
            vincoloAutorizzaList = vincoliAutorizzazioniApiServiceHelper.getVincoliAutorizzazioni(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return Response.ok(vincoloAutorizzaList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Load vincolo autorizzazione by adempimento response.
     *
     * @param idAdempimento   the id adempimento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getVincoloAutorizzazioneByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        List<VincoloAutorizzaExtendedDTO> vincoloAutorizzaList = new ArrayList<VincoloAutorizzaExtendedDTO>();
        try {
            vincoloAutorizzaList = vincoliAutorizzazioniApiServiceHelper.getVincoloAutorizzazioneByAdempimento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento);
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
        return Response.ok(vincoloAutorizzaList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Load configurazioni allegati by istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response getConfigurazioniAllegatiByIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        List<AdempimentoTipoAllegatoExtendedDTO> adempimentoTipoAllegatoList = new ArrayList<>();
        try {
            adempimentoTipoAllegatoList = vincoliAutorizzazioniApiServiceHelper.getConfigurazioniAllegatiByIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
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
        return Response.ok(adempimentoTipoAllegatoList).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
    }

    /**
     * Load ricadenza vincolo idrogeologico by istanza response.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the response
     */
    @Override
    public Response loadRicadenzaVincoloIdrogeologicoByIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

}