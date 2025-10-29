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

import it.csi.scriva.scrivabesrv.business.be.AbilitazioniApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.AbilitazioniService;
import it.csi.scriva.scrivabesrv.dto.IstanzaAttoreExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Component

@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)

public class AbilitazioniApiServiceImpl extends BaseApiServiceImpl implements AbilitazioniApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    AbilitazioniService abilitazioniService;

    /**
     * Load abilitazioni by id istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadAbilitazioniByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(abilitazioniService.loadAbilitazioniByIdIstanzaCFAttore(idIstanza, attoreScriva), className);
    }

    /**
     * Load abilitazioni revocabili for istanza by id istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadAbilitazioniRevocabiliForIstanzaByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(abilitazioniService.loadAbilitazioniRevocabiliForIstanzaByIdIstanza(idIstanza, attoreScriva), className);
    }

    /**
     * Load abilitazioni concesse for istanza by id istanza response.
     * Estrae le occorrenze della SCRIVA_R_ISTANZA_ATTORE dove:
     * - Id_istanza = istanza selezionata precedentemente dall’attore nella videata di ricerca istanze
     * - Cf_abilitante_delegante = cf attore in linea
     * - Id_profilo_applicativo = ABILITATO_CONSULTA oppure ABILITATO_GESTIONE (o id_tipo_abilitazione valorizzata)
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadAbilitazioniConcesseForIstanzaByIdIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, idIstanza);
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        return getResponseList(abilitazioniService.loadAbilitazioniConcesseForIstanzaByIdIstanza(idIstanza, attoreScriva), className);
    }

    /**
     * Update abilitazione response.
     *
     * @param istanzaAttore   the istanza attore
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response updateAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaAttore);
        Response response = setAttoreRight(httpHeaders, istanzaAttore.getIdIstanza());
        if (response != null) {
            return response;
        }
        IstanzaAttoreExtendedDTO istanzaAttoreResult = null;
        try {
            istanzaAttoreResult = abilitazioniService.updateAbilitazione(istanzaAttore, attoreScriva);
        } catch (GenericException e) {
            logError(className, e);
            return Response.serverError().entity(e.getError()).status(e.getError().getStatus() != null ? Integer.parseInt(e.getError().getStatus()) : 500).build();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return getResponseSaveUpdate(istanzaAttoreResult, className);
    }

    /**
     * Save abilitazione response.
     *
     * @param istanzaAttore   the istanza attore
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response saveAbilitazione(IstanzaAttoreExtendedDTO istanzaAttore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaAttore);
        Response response = setAttoreRight(httpHeaders, istanzaAttore.getIdIstanza());
        if (response != null) {
            return response;
        }
        List<IstanzaAttoreExtendedDTO> istanzaAttoreNewList = null;
        try {
            istanzaAttoreNewList = abilitazioniService.saveAbilitazione(istanzaAttore, attoreScriva);
        } catch (GenericException e) {
            logError(className, e);
            return Response.serverError().entity(e.getError()).status(e.getError().getStatus() != null ? Integer.parseInt(e.getError().getStatus()) : 500).build();
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return getResponseSaveUpdate(istanzaAttoreNewList != null && !istanzaAttoreNewList.isEmpty() ? istanzaAttoreNewList.get(0) : null, className);
    }

}