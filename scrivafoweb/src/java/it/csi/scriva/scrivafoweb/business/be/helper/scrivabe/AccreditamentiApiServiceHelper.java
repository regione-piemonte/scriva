/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.AccreditamentoDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.CompilanteDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class AccreditamentiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public AccreditamentiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public AccreditamentoDTO saveRichiestaAccreditamento(MultivaluedMap<String, Object> requestHeaders, AccreditamentoDTO richiestaAccreditamento) throws GenericException {
        logBegin(className);
        AccreditamentoDTO result = new AccreditamentoDTO();
        String targetUrl = this.endpointBase + "/accreditamenti";
        try {
            Entity<AccreditamentoDTO> entity = Entity.json(richiestaAccreditamento);
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(entity);
            logDebug(className, "RESPONSE STATUS : " + resp.getStatus());
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            GenericType<AccreditamentoDTO> dtoType = new GenericType<AccreditamentoDTO>() {
            };
            result = resp.readEntity(dtoType);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }


    public List<CompilanteDTO> validateAccreditamento(MultivaluedMap<String, Object> requestHeaders, String uid, String otp) throws GenericException {
        logBegin(className);
        List<CompilanteDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/accreditamenti/" + uid + "/valida?otp=" + otp;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).get();
            logDebug(className, "RESPONSE STATUS : " + resp.getStatus());
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logError(className, err);
                throw new GenericException(err);
            }
            GenericType<List<CompilanteDTO>> listType = new GenericType<List<CompilanteDTO>>() {
            };
            result = resp.readEntity(listType);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
        return result;
    }

}