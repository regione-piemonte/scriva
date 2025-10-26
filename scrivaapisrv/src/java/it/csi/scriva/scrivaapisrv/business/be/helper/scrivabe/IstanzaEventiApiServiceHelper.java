/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe;

import it.csi.scriva.scrivaapisrv.business.be.exception.GenericException;
import it.csi.scriva.scrivaapisrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.IstanzaEventoExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

public class IstanzaEventiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public IstanzaEventiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<IstanzaEventoExtendedDTO> traceIstanzaEvento(MultivaluedMap<String, Object> requestHeaders, Long idIstanza, String codTipoEvento) throws GenericException {
        logBegin(className);
        String targetUrl = this.endpointBase + "/eventi?id_istanza=" + idIstanza + "&cod_tipo_evento=" + codTipoEvento;
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(null);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp, "Il cambio stato previsto dall’evento è incompatibile con quanto previsto per l’adempimento.");
                logError(className, err);
                throw new GenericException(err);
            }
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException("Errore nella chiamata al servizio [ " + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

}