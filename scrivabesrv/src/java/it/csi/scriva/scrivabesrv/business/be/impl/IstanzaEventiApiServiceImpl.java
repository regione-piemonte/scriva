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

import it.csi.scriva.scrivabesrv.business.be.IstanzaEventiApi;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.service.IstanzaEventoService;
import it.csi.scriva.scrivabesrv.dto.IstanzaEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
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
import java.sql.Date;
import java.util.List;

/**
 * The interface Istanza eventi api.
 *
 * @author CSI PIEMONTE
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class IstanzaEventiApiServiceImpl extends BaseApiServiceImpl implements IstanzaEventiApi {

    private final String className = this.getClass().getSimpleName();

    @Autowired
    private IstanzaEventoService istanzaEventoService;

    /**
     * Trace eventi response.
     *
     * @param idIstanza         the id istanza
     * @param codTipoEvento     the cod tipo evento
     * @param rifCanaleNotifica the rif canale notifica
     * @param codCanaleNotifica the cod canale notifica
     * @param uidRichiesta      the uid richiesta
     * @param dataIntegrazione  the data integrazione
     * @param tipoRichiesta     the tipo richiesta
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @Override
    @Transactional
    public Response traceIstanzaEvento(Long idIstanza,
                                       String codTipoEvento,
                                       String rifCanaleNotifica,
                                       String codCanaleNotifica,
                                       String uidRichiesta,
                                       Date dataIntegrazione,
                                       String tipoRichiesta,
                                       SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBeginInfo(className, "idIstanza: [" + idIstanza + "] - codTipoEvento : [" + codTipoEvento + "] - rifCanaleNotifica : [" + rifCanaleNotifica + "] - codCanaleNotifica : [" + codCanaleNotifica + "] - uidRichiesta : [" + uidRichiesta + "] - tipoRichiesta : [" + tipoRichiesta + "] - dataIntegrazione : [" + dataIntegrazione + "]");
        Response response = setAttoreRight(httpHeaders, idIstanza);
        /*
        // PUBWEB SI ROMPE
        if (response != null) {
            return response;
        }
         */
        try {
            List<IstanzaEventoExtendedDTO> istanzaEventoList = istanzaEventoService.traceIstanzaEventoByCodeTipoEvento(idIstanza, codTipoEvento, rifCanaleNotifica, codCanaleNotifica, uidRichiesta, dataIntegrazione, tipoRichiesta, attoreScriva);
            setAttoreRight(httpHeaders, idIstanza);
            //return getResponseList(istanzaEventoList, className);
            updateIstanzaPraticaTimestampAttore(idIstanza, attoreScriva);
            return Response.ok(istanzaEventoList).header(HttpHeaders.CONTENT_ENCODING, Constants.IDENTITY).header(Constants.HEADER_ATTORE_GESTIONE, attoreScriva != null ? attoreScriva.getProfiloAppIstanza() : null).build();
        } catch (GenericException e) {
            logError(className, e);
            return getResponseError(className, e.getError());
        }
    }
}