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

import it.csi.scriva.scrivabesrv.business.be.CatastoApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.CatastoFoglioDTO;
import it.csi.scriva.scrivabesrv.dto.CatastoParticellaDTO;
import it.csi.scriva.scrivabesrv.dto.CatastoSezioneDTO;
import it.csi.scriva.scrivabesrv.util.manager.CatastoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.List;

/**
 * The type Catasto api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CatastoApiServiceImpl extends BaseApiServiceImpl implements CatastoApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private CatastoManager catastoManager;

    /**
     * Load comuni response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param candidateName   the candidate name
     * @param expansionMode   the expansion mode
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadComuniCatasto(String xRequestAuth, String xRequestId, String xForwardedFor, String candidateName, String expansionMode, Integer epsg, Integer limit, Integer offset, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "candidateName [" + candidateName + "] - expansionMode [" + expansionMode + "] - epsg [" + epsg + "] - limit [" + limit + "] - offset [" + offset + "]");
        try {
            return getResponseList(catastoManager.loadComuni(xRequestId, xForwardedFor, candidateName, expansionMode, epsg, limit, offset), className);
        } catch (GenericException e) {
            return getResponseError(className, e.getError());
        }
    }

    /**
     * Load sezioni per comune response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadSezioniPerComune(String xRequestAuth, String xRequestId, String xForwardedFor, String codiceComune, Integer epsg, Integer limit, Integer offset, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceComune [" + codiceComune + "] - epsg [" + epsg + "] - limit [" + limit + "] - offset [" + offset + "]");
        List<CatastoSezioneDTO> sezioniList;
        try {
            sezioniList = catastoManager.loadSezioniPerComune(xRequestId, xForwardedFor, codiceComune, epsg, limit, offset);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(sezioniList, className);
    }

    /**
     * Load sezione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param epsg            the epsg
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadSezione(String xRequestAuth, String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, Integer epsg, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceComune [" + codiceComune + "] - codiceSezione [" + codiceSezione + "] - epsg [" + epsg + "]");
        List<CatastoSezioneDTO> sezioniList;
        try {
            sezioniList = catastoManager.loadSezione(xRequestId, xForwardedFor, codiceComune, codiceSezione, epsg);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(sezioniList, className);
    }

    /**
     * Load fogli per comune per sezione response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadFogliPerComunePerSezione(String xRequestAuth, String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, Integer epsg, Integer limit, Integer offset, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceComune [" + codiceComune + "] - codiceSezione [" + codiceSezione + "] - epsg [" + epsg + "] - limit [" + limit + "] - offset [" + offset + "]");
        List<CatastoFoglioDTO> fogliList;
        try {
            fogliList = catastoManager.loadFogliPerComunePerSezione(xRequestId, xForwardedFor, codiceComune, codiceSezione, epsg, limit, offset);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(fogliList, className);
    }

    /**
     * Load foglio response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param numeroFoglio    the numero foglio
     * @param epsg            the epsg
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadFoglio(String xRequestAuth, String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, String numeroFoglio, Integer epsg, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceComune [" + codiceComune + "] - codiceSezione [" + codiceSezione + "] - epsg [" + epsg + "]");
        List<CatastoFoglioDTO> fogliList;
        try {
            fogliList = catastoManager.loadFoglio(xRequestId, xForwardedFor, codiceComune, codiceSezione, numeroFoglio, epsg);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(fogliList, className);
    }

    /**
     * Load fogli intersecanti geometria response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadFogliIntersecantiGeometria(String xRequestAuth, String xRequestId, String xForwardedFor, Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        List<CatastoFoglioDTO> fogliList;
        try {
            fogliList = catastoManager.loadFogliIntersecantiGeometria(xRequestId, xForwardedFor, idOggettoIstanza);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(fogliList, className);
    }

    /**
     * Load fogli contenuti geometria response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadFogliContenutiGeometria(String xRequestAuth, String xRequestId, String xForwardedFor, Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        List<CatastoFoglioDTO> fogliList;
        try {
            fogliList = catastoManager.loadFogliContenutiGeometria(xRequestId, xForwardedFor, idOggettoIstanza);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(fogliList, className);
    }

    /**
     * Load particelle per foglio per sezione per comune response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param codiceComune    the codice comune
     * @param codiceSezione   the codice sezione
     * @param numeroFoglio    the numero foglio
     * @param epsg            the epsg
     * @param limit           the limit
     * @param offset          the offset
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadParticellePerFoglioPerSezionePerComune(String xRequestAuth, String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, String numeroFoglio, Integer epsg, Integer limit, Integer offset, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceComune [" + codiceComune + "] - codiceSezione [" + codiceSezione + "] - epsg [" + epsg + "] - limit [" + limit + "] - offset [" + offset + "]");
        List<CatastoParticellaDTO> particelleList;
        try {
            particelleList = catastoManager.loadParticellePerFoglioPerSezionePerComune(xRequestId, xForwardedFor, codiceComune, codiceSezione, numeroFoglio, epsg, limit, offset);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(particelleList, className);
    }

    /**
     * Load particella response.
     *
     * @param xRequestAuth     the x request auth
     * @param xRequestId       the x request id
     * @param xForwardedFor    the x forwarded for
     * @param codiceComune     the codice comune
     * @param codiceSezione    the codice sezione
     * @param numeroFoglio     the numero foglio
     * @param numeroParticella the numero particella
     * @param epsg             the epsg
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    public Response loadParticella(String xRequestAuth, String xRequestId, String xForwardedFor, String codiceComune, String codiceSezione, String numeroFoglio, String numeroParticella, Integer epsg, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "codiceComune [" + codiceComune + "] - codiceSezione [" + codiceSezione + "] - epsg [" + epsg + "]");
        List<CatastoParticellaDTO> particelleList;
        try {
            particelleList = catastoManager.loadParticella(xRequestId, xForwardedFor, codiceComune, codiceSezione, numeroFoglio, numeroParticella, epsg);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(particelleList, className);
    }

    /**
     * Load particelle intersecanti geometria response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadParticelleIntersecantiGeometria(String xRequestAuth, String xRequestId, String xForwardedFor, Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        List<CatastoParticellaDTO> particelleList;
        try {
            particelleList = catastoManager.loadParticelleIntersecantiGeometria(xRequestId, xForwardedFor, idOggettoIstanza);
        } catch (IOException e) {
            logError(className, e);
            return getResponseError(className, 500, "E100", null, null);
        } catch (GenericException e) {
            logError(className, e.getError());
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(particelleList, className);
    }

    /**
     * Load particelle contenute geometria response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param xForwardedFor   the x forwarded for
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadParticelleContenuteGeometria(String xRequestAuth, String xRequestId, String xForwardedFor, Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        List<CatastoParticellaDTO> particelleList;
        try {
            particelleList = catastoManager.loadParticelleContenuteGeometria(xRequestId, xForwardedFor, idOggettoIstanza);
        } catch (GenericException e) {
            logError(className, e);
            logEnd(className);
            return Response.serverError().entity(e.getError()).status(Integer.parseInt(e.getError().getStatus())).build();
        }
        return getResponseList(particelleList, className);
    }
}