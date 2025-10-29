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

import it.csi.scriva.scrivafoweb.business.be.IstanzaEventiApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.IstanzaEventiApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaEventoExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Istanza eventi api service.
 */
@Component
public class IstanzaEventiApiServiceImpl extends AbstractApiServiceImpl implements IstanzaEventiApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Istanza eventi api service helper.
     */
    @Autowired
    IstanzaEventiApiServiceHelper istanzaEventiApiServiceHelper;

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
    public Response traceIstanzaEvento(Long idIstanza,
                                       String codTipoEvento,
                                       String rifCanaleNotifica,
                                       String codCanaleNotifica,
                                       String uidRichiesta,
                                       Date dataIntegrazione,
                                       String tipoRichiesta,
                                       SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        logBegin(className);
        List<IstanzaEventoExtendedDTO> istanzaEventoList = new ArrayList<>();
        try {
            istanzaEventoList = istanzaEventiApiServiceHelper.traceIstanzaEvento(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, codTipoEvento, rifCanaleNotifica, codCanaleNotifica, uidRichiesta, dataIntegrazione, tipoRichiesta);
            return getResponseWithSharedHeaders(istanzaEventoList, istanzaEventiApiServiceHelper, 200);
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