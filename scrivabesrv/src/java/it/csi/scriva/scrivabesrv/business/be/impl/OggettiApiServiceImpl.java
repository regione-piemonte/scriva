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

import it.csi.scriva.scrivabesrv.business.be.OggettiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.OggettiService;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoUbicazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;
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
 * The type Oggetti api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OggettiApiServiceImpl extends BaseApiServiceImpl implements OggettiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private OggettiService oggettiService;

    /**
     * Load oggetti response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idComune        the id comune
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadOggetti(String xRequestAuth, String xRequestId, Long idComune, Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idComune);
        List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList = null;
        if (idIstanza != null) {
            oggettoUbicazioneList = oggettiService.loadOggettoByIdIstanza(idIstanza);
        } else if (idComune != null) {
            oggettoUbicazioneList = oggettiService.loadOggettoByIdComune(idComune);
        } else {
            oggettoUbicazioneList = oggettiService.loadOggetti();
        }
        return getResponseList(oggettoUbicazioneList, className);
    }

    /**
     * Load oggetto by id response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idOggetto       idOggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    //@Override
    public Response loadOggettoById(String xRequestAuth, String xRequestId, Long idOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggetto);
        return getResponseList(oggettiService.loadOggettoById(idOggetto), className);
    }

    /**
     * Load oggetto by uid response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param uidOggetto      uidOggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response loadOggettoByUID(String xRequestAuth, String xRequestId, String uidOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uidOggetto);
        return getResponseList(oggettiService.loadOggettoByUID(uidOggetto), className);
    }

    /**
     * Search oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param searchOggetto   SearchOggettoDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response searchOggetto(String xRequestAuth, String xRequestId, SearchOggettoDTO searchOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, searchOggetto);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(400).build();
        }
        List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList = oggettiService.searchOggetto(searchOggetto, attoreScriva);
        return getResponseList(oggettiService.setPraticheCollegateOggetto(oggettoUbicazioneList, searchOggetto.getAnnoPresentazione()), className);
    }

    /**
     * Save oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param oggetto         OggettoUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response saveOggetto(String xRequestAuth, String xRequestId, String codAdempimento, OggettoUbicazioneExtendedDTO oggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggetto);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }

        ErrorDTO error = oggettiService.validateDTO(oggetto, codAdempimento, Boolean.FALSE);
        if (null != error) {
            return getResponseError(className, error);
        }

        Long idOggetto = oggettiService.saveOggetto(oggetto, attoreScriva);

        if (null == idOggetto) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logEnd(className);
            return Response.serverError().entity(error).status(500).build();
        } else {
            List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList = oggettiService.loadOggettoById(idOggetto);
            return getResponseSaveUpdate(oggettoUbicazioneList != null && !oggettoUbicazioneList.isEmpty() ? oggettoUbicazioneList.get(0) : null, className, "oggetti/id/" + idOggetto);
        }
    }

    /**
     * Update oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param oggetto         OggettoUbicazioneExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response updateOggetto(String xRequestAuth, String xRequestId, OggettoUbicazioneExtendedDTO oggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, oggetto);
        try {
            attoreScriva = getAttoreScriva(httpHeaders);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
        ErrorDTO error = oggettiService.validateDTO(oggetto, null, Boolean.TRUE);
        if (null != error) {
            return getResponseError(className, error);
        }
        Integer resUpdateOggetto = oggettiService.updateOggetto(oggetto, attoreScriva);
        if (resUpdateOggetto == null) {
            error = getErrorManager().getError("500", "E100", null, null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(500).build();
        } else if (resUpdateOggetto < 1) {
            error = getErrorManager().getError("404", "", "Errore nell'aggiornamento dell'elemento; causa: elemento non trovato", null, null);
            logError(className, error);
            logEnd(className);
            return Response.serverError().entity(error).status(404).build();
        } else {
            List<OggettoUbicazioneExtendedDTO> oggettoUbicazioneList = oggettiService.loadOggettoByUID(oggetto.getGestUID());
            return getResponseSaveUpdate(oggettoUbicazioneList != null && !oggettoUbicazioneList.isEmpty() ? oggettoUbicazioneList.get(0) : null, className);
        }
    }

    /**
     * Delete oggetto response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param uidOggetto      uidOggetto
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @Override
    public Response deleteOggetto(String xRequestAuth, String xRequestId, String uidOggetto, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uidOggetto);
        return getResponseDelete(oggettiService.deleteOggetto(uidOggetto), className);
    }

}