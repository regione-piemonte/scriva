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

import it.csi.scriva.scrivabesrv.business.be.VincoliAutorizzazioniApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.service.VincoliAutorizzazioniService;
import it.csi.scriva.scrivabesrv.util.manager.VincoliManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * The type Vincoli autorizzazioni api service.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VincoliAutorizzazioniApiServiceImpl extends BaseApiServiceImpl implements VincoliAutorizzazioniApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    AdempimentoTipoAllegatoDAO adempimentoTipoAllegatoDAO;

    @Autowired
    VincoliManager vincoliManager;

    @Autowired
    VincoliAutorizzazioniService vincoliAutorizzazioniService;

    /**
     * Load vincoli autorizzazioni response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadVincoliAutorizzazioni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        return getResponseList(vincoliAutorizzazioniService.loadVincoliAutorizzazioni(), className);
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
    public Response loadVincoloAutorizzazioneByAdempimento(Long idAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idAdempimento);
        return getResponseList(vincoliAutorizzazioniService.loadVincoloAutorizzazioneByAdempimento(idAdempimento), className);
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
    public Response loadConfigurazioniAllegatiByIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        return getResponseList(adempimentoTipoAllegatoDAO.loadTipologieAllegatoByIstanzaVincoloAut(idIstanza, null), className);
    }

    /**
     * Load ricadenza vincolo idrogeologico by istanza response.
     *
     * @param idOggettoIstanza the id istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the response
     */
    @Override
    public Response loadRicadenzaVincoloIdrogeologicoByIstanza(Long idOggettoIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idOggettoIstanza);
        return getResponseList(vincoliManager.ricadenzaVincoloIdrogeologicoByIstanza(idOggettoIstanza), className);
    }
}