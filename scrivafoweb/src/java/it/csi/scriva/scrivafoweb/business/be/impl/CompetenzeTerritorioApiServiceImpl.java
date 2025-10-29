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

import it.csi.scriva.scrivafoweb.business.be.CompetenzeTerritorioApi;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.CompetenzeTerritorioApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivafoweb.dto.ListObjectWithHeaderResultFoDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The type Competenze territorio api service.
 */
@Component
public class CompetenzeTerritorioApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements CompetenzeTerritorioApi {

    private final String className = this.getClass().getSimpleName();

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
        logBeginInfo(className, "idAdempimento [" + idAdempimento + "] - idIstanza [" + idIstanza + "]");
        ListObjectWithHeaderResultFoDTO istanzaCompetenzaResultFoDTO = null;
        try {
            istanzaCompetenzaResultFoDTO = competenzeTerritorioApiServiceHelper.getCompetenzeTerritorio(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idAdempimento, idIstanza);
            return Response.ok(istanzaCompetenzaResultFoDTO.getObjectList()).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header("CountCompetenze", istanzaCompetenzaResultFoDTO.getCountHeader()).build();
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
    }

    /**
     * Verifica competenze territorio by id istanza adempimento response.
     *
     * @param idIstanza       the id istanza
     * @param ac3             the ac 3
     * @param tipoSelezione   the tipo selezione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    /*@Override
    public Response verificaCompetenzeTerritorioByIdIstanzaAdempimento(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }
    */
    /**
     * Verifica competenze territorio by id istanza adempimento response.
     *
     * @param idIstanza       the id istanza
     * @param ac3             the ac 3
     * @param tipoSelezione   the tipo selezione
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response gestioneAC(Long idIstanza, Boolean ac3,String tipoSelezione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "]"+"ac3: [" + ac3 + "]"+"tipoSelezione: [" + tipoSelezione + "]");
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
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
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - livelliCompetenza [" + livelliCompetenza + "]");
        try {
            return Response.ok(competenzeTerritorioApiServiceHelper.calculateIstanzaCompetenzaTerritorioSecondarie(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, livelliCompetenza)).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
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
        logBeginInfo(className, "idIstanza: [" + idIstanza + "]");
        try {
            competenzeTerritorioApiServiceHelper.deleteIstanzaCompetenzaTerritorioSecondarie(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
            return Response.noContent().build();
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
        logBeginInfo(className, istanzaCompetenza);
        UserInfo user = getSessionUser(httpRequest);
        istanzaCompetenza.setGestAttoreIns(user.getCodFisc());
        try {
            return getResponseWithSharedHeaders(competenzeTerritorioApiServiceHelper.saveIstanzaCompetenzaTerritorio(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaCompetenza), competenzeTerritorioApiServiceHelper, 201);
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
        logBeginInfo(className, istanzaCompetenza);
        UserInfo user = getSessionUser(httpRequest);
        istanzaCompetenza.setGestAttoreUpd(user.getCodFisc());
        try {
            return getResponseWithSharedHeaders(competenzeTerritorioApiServiceHelper.updateIstanzaCompetenzaTerritorio(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanzaCompetenza), competenzeTerritorioApiServiceHelper, 200);
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
    }
    
    /**
     * Gets competenza territorio responsabile.
     *
     * @param idCompetenzaResponsabile   the id competenza responsabile
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response competenze territorio
     */

//    public Response getCompetenzeTerritorioByIdCompResp(Long idCompetenzaResponsabile, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
//        logBeginInfo(className, "idCompetenzaResponsabile [" + idCompetenzaResponsabile + "]");
//        CompetenzaTerritorioResponsabileDTO competenzaTerritorioResponsabileDTO = new CompetenzaTerritorioResponsabileDTO();
//        try {
//        	competenzaTerritorioResponsabileDTO = competenzeTerritorioApiServiceHelper.getCompetenzeTerritorioByIdCompResp(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idCompetenzaResponsabile);
//            return Response.ok(competenzaTerritorioResponsabileDTO).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
//        } catch (GenericException e) {
//            ErrorDTO err = e.getError();
//            logError(className, err);
//            return Response.serverError().entity(err).status(Integer.parseInt(err.getStatus())).build();
//        } catch (ProcessingException e) {
//            logError(className, e);
//            String errorMessage = e.getMessage();
//            ErrorDTO err = new ErrorDTO("500", "E100", errorMessage, null, null);
//            return Response.serverError().entity(err).status(500).build();
//        } finally {
//            logEnd(className);
//        }
//    }

	

	@Override
	public Response getCompetenzeTerritorioByIdCompResp(String xRequestAuth,
			String xRequestId, Long idCompetenzaResponsabile,
			SecurityContext securityContext,
			HttpHeaders httpHeaders,
			HttpServletRequest httpRequest) {
        logBeginInfo(className, "idCompetenzaResponsabile [" + idCompetenzaResponsabile + "]");
        CompetenzaTerritorioResponsabileDTO competenzaTerritorioResponsabileDTO = new CompetenzaTerritorioResponsabileDTO();
        try {
        	competenzaTerritorioResponsabileDTO = competenzeTerritorioApiServiceHelper.getCompetenzeTerritorioByIdCompResp(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idCompetenzaResponsabile);
            return Response.ok(competenzaTerritorioResponsabileDTO).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).build();
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
	}

}