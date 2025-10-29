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


import it.csi.scriva.scrivafoweb.business.be.OggettiIstanzaApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.OggettiIstanzaApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneGeoRefExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneGeometrieExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchOggettoDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class OggettiIstanzaApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements OggettiIstanzaApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private OggettiIstanzaApiServiceHelper oggettiIstanzaApiServiceHelper;

    /**
     * Gets oggetti istanza.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the oggetti istanza
     */
    @Override
    public Response getOggettiIstanza(String xRequestAuth, String xRequestId,
                                      Long idIstanza,
                                      SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        List<OggettoIstanzaUbicazioneExtendedDTO> list;
        try {
            list = idIstanza != null ? oggettiIstanzaApiServiceHelper.getOggettoIstanzaByIdIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza) : oggettiIstanzaApiServiceHelper.getOggettiIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
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
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
    }

    /**
     * Gets oggetto istanza by id.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the oggetto istanza by id
     */
    @Override
    public Response getOggettoIstanzaById(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        List<OggettoIstanzaUbicazioneGeometrieExtendedDTO> list;
        try {
            list = oggettiIstanzaApiServiceHelper.getOggettoIstanzaById(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idOggettoIstanza);
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
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
    }

    /**
     * Gets oggetto istanza by uid.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the oggetto istanza by uid
     */
    @Override
    public Response getOggettoIstanzaByUID(String uidOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, (Object) uidOggettoIstanza);
        logger.debug("[OggettiIstanzaApiServiceImpl::getOggettoIstanzaByUID] BEGIN");
        List<OggettoIstanzaUbicazioneExtendedDTO> list;
        try {
            list = oggettiIstanzaApiServiceHelper.getOggettoIstanzaByUID(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uidOggettoIstanza);
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
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
    }

    /**
     * Search oggetto istanza response.
     *
     * @param searchOggettoIstanza the search oggetto istanza
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
    @Override
    public Response searchOggettoIstanza(SearchOggettoDTO searchOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, searchOggettoIstanza);
        List<OggettoIstanzaUbicazioneExtendedDTO> list;
        try {
            list = oggettiIstanzaApiServiceHelper.searchOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), searchOggettoIstanza);
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
        return getResponseWithSharedHeaders(list, oggettiIstanzaApiServiceHelper);
    }

    /**
     * Save oggetto istanza response.
     *
     * @param oggettoIstanza  the oggetto istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanza);
        OggettoIstanzaUbicazioneExtendedDTO dto = new OggettoIstanzaUbicazioneExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanza.setGestAttoreIns(user.getCodFisc());
        try {
            dto = oggettiIstanzaApiServiceHelper.saveOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanza);
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
        return getResponseWithSharedHeaders(dto, oggettiIstanzaApiServiceHelper, 201);
    }

    /**
     * Update oggetto istanza response.
     *
     * @param oggettoIstanza  the oggetto istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response updateOggettoIstanza(OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggettoIstanza);
        OggettoIstanzaUbicazioneExtendedDTO dto = new OggettoIstanzaUbicazioneExtendedDTO();
        UserInfo user = getSessionUser(httpRequest);
        oggettoIstanza.setGestAttoreUpd(user.getCodFisc());
        try {
            dto = oggettiIstanzaApiServiceHelper.updateOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), oggettoIstanza);
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
        return getResponseWithSharedHeaders(dto, oggettiIstanzaApiServiceHelper);
    }

    /**
     * Delete oggetto istanza response.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response deleteOggettoIstanza(String uidOggettoIstanza, String checkFigli, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, (Object) uidOggettoIstanza);
        try {
            oggettiIstanzaApiServiceHelper.deleteOggettoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uidOggettoIstanza, checkFigli);
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
     * Verifica georeferenziazione response.
     *
     * @param codAdempimento  the cod adempimento
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response verificaGeoreferenziazione(String codAdempimento, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, (Object) "codAdempimento [" + codAdempimento + "] - idIstanza [" + idIstanza + "]");
        List<OggettoIstanzaUbicazioneGeoRefExtendedDTO> dto = null;
        try {
            dto = oggettiIstanzaApiServiceHelper.verificaGeoreferenziazione(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codAdempimento, idIstanza);
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
        return getResponseWithSharedHeaders(dto, oggettiIstanzaApiServiceHelper);
    }

    /**
     * Gets aree protette by comuni ubicazione oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the aree protette by comuni ubicazione oggetto istanza
     */
    @Override
    public Response getAreeProtetteByComuniUbicazioneOggettoIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idOggettoIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Gets aree protette by geometrie oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the aree protette by geometrie oggetto istanza
     */
    @Override
    public Response getAreeProtetteByGeometrieOggettoIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idOggettoIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Gets siti rete natura 2000 by comuni ubicazione oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the siti rete natura 2000 by comuni ubicazione oggetto istanza
     */
    @Override
    public Response getSitiReteNatura2000ByComuniUbicazioneOggettoIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idOggettoIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Gets siti rete natura 2000 by geometrie oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the siti rete natura 2000 by geometrie oggetto istanza
     */
    @Override
    public Response getSitiReteNatura2000ByGeometrieOggettoIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idOggettoIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

}