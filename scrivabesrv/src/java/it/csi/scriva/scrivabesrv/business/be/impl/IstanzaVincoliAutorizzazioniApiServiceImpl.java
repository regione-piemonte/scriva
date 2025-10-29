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

import it.csi.scriva.scrivabesrv.business.be.IstanzaVincoliAutorizzazioniApi;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaVincoloAutDAO;
import it.csi.scriva.scrivabesrv.dto.IstanzaVincoloAutExtendedDTO;
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
 * The type Vincoli autorizzazioni api service.
 */
/*
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IstanzaVincoliAutorizzazioniApiServiceImpl extends BaseApiServiceImpl implements IstanzaVincoliAutorizzazioniApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Istanza vincolo aut dao.
     */
    @Autowired
    IstanzaVincoloAutDAO istanzaVincoloAutDAO;

    /**
     * Load istanza vincolo autorizzazione by istanza response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param codVincolo      the cod vincolo
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response loadIstanzaVincoloAutorizzazioneByIstanza(String xRequestAuth, String xRequestId, Long idIstanza, String codVincolo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - codVincolo: [" + codVincolo + "]");
        /*
        Response response = setAttoreRight(httpHeaders, idIstanza);
        if (response != null) {
            return response;
        }
        */
        return getResponseList(istanzaVincoloAutDAO.loadIstanzaVincoloAutorizzazioneByIstanza(idIstanza, codVincolo), className);
    }

    /**
     * Save istanza vincolo autorizzazione response.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response saveIstanzaVincoloAutorizzazione(IstanzaVincoloAutExtendedDTO istanzaVincoloAut, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaVincoloAut);
        // Verifica permessi di scrittura su istanza
        //Response response = setAttoreRight(httpHeaders, istanzaVincoloAut.getIdIstanza(), Boolean.TRUE);

        Response response = setAttoreRight(httpHeaders, istanzaVincoloAut.getIdIstanza());
        /*
        if (response != null) {
            return response;
        }
        */
        istanzaVincoloAut.setGestAttoreIns(attoreScriva.getCodiceFiscale());
        Long idIstanzaVincoloAut = istanzaVincoloAutDAO.saveIstanzaVincoloAutorizzazione(istanzaVincoloAut.getDTO());
        if (idIstanzaVincoloAut == null) {
            return getResponseSaveUpdate(null, className);
        }
        updateIstanzaPraticaTimestampAttore(istanzaVincoloAut.getIdIstanza(), attoreScriva);    
        List<IstanzaVincoloAutExtendedDTO> istanzaVincoloAutList = istanzaVincoloAutDAO.loadIstanzaVincoloAutorizzazioneById(idIstanzaVincoloAut);
        return getResponseSave((istanzaVincoloAutList != null && !istanzaVincoloAutList.isEmpty() ? istanzaVincoloAutList.get(0) : istanzaVincoloAutList), className, (istanzaVincoloAutList != null && !istanzaVincoloAutList.isEmpty() ? "/vincoli-autorizzazioni/id-istanza/" + istanzaVincoloAut.getIdIstanza() : null));
    }

    /**
     * Update istanza vincolo autorizzazione response.
     *
     * @param istanzaVincoloAut the istanza vincolo aut
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    public Response updateIstanzaVincoloAutorizzazione(IstanzaVincoloAutExtendedDTO istanzaVincoloAut, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, istanzaVincoloAut);
        // Verifica permessi di scrittura su istanza
        //Response response = setAttoreRight(httpHeaders, istanzaVincoloAut.getIdIstanza(), Boolean.TRUE);

        Response response = setAttoreRight(httpHeaders, istanzaVincoloAut.getIdIstanza());
        /*
        if (response != null) {
            return response;
        }
        */
        istanzaVincoloAut.setGestAttoreUpd(attoreScriva.getCodiceFiscale());
        Integer res = istanzaVincoloAutDAO.updateIstanzaVincoloAutorizzazione(istanzaVincoloAut.getDTO());
        if (res == null || res < 1) {
            return getResponseSaveUpdate(null, className);
        }
        updateIstanzaPraticaTimestampAttore(istanzaVincoloAut.getIdIstanza(), attoreScriva);   
        List<IstanzaVincoloAutExtendedDTO> istanzaVincoloAutList = istanzaVincoloAutDAO.loadIstanzaVincoloAutorizzazioneById(istanzaVincoloAut.getIdIstanzaVincoloAut());
        return getResponseSaveUpdate((istanzaVincoloAutList != null && !istanzaVincoloAutList.isEmpty() ? istanzaVincoloAutList.get(0) : istanzaVincoloAutList), className);
    }

    /**
     * Delete istanza vincolo autorizzazione response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response deleteIstanzaVincoloAutorizzazione(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, uid);
        // Recupero istanzaVincoloAut per recuperare idIstanza e relativi permessi di scrittura
        List<IstanzaVincoloAutExtendedDTO> istanzaVincoloAutList = istanzaVincoloAutDAO.loadIstanzaVincoloAutorizzazioneById(uid);
        if (istanzaVincoloAutList == null || istanzaVincoloAutList.isEmpty()) {
            return getResponseSaveUpdate(null, className);
        }

        // Verifica permessi di scrittura su istanza
        //Response response = setAttoreRight(httpHeaders, istanzaVincoloAutList.get(0).getIdIstanza(), Boolean.TRUE);
        Response response = setAttoreRight(httpHeaders, istanzaVincoloAutList.get(0).getIdIstanza());
        /*
        if (response != null) {
            return response;
        }
        */
        updateIstanzaPraticaTimestampAttore(istanzaVincoloAutList.get(0).getIdIstanza(), attoreScriva);   
        return getResponseDelete(istanzaVincoloAutDAO.deleteIstanzaVincoloAutorizzazioneByIstanza(uid), className);
    }
}