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
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaEventoExtendedDTO;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.util.List;

public class IstanzaEventiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    public IstanzaEventiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase;
    }

    public List<IstanzaEventoExtendedDTO> traceIstanzaEvento(MultivaluedMap<String, Object> requestHeaders,
                                                             Long idIstanza,
                                                             String codTipoEvento,
                                                             String rifCanaleNotifica,
                                                             String codCanaleNotifica,
                                                             String uidRichiesta,
                                                             Date dataIntegrazione,
                                                             String tipoRichiesta) throws GenericException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug(getClassFunctionBeginInfo(className, methodName));
        String targetUrl = this.endpointBase + "/eventi?cod_tipo_evento=" + codTipoEvento
                + (idIstanza != null ? "&id_istanza=" + idIstanza : "")
                + (StringUtils.isNotBlank(rifCanaleNotifica) ? "&rif_canale_notifica=" + rifCanaleNotifica : "")
                + (StringUtils.isNotBlank(codCanaleNotifica) ? "&cod_canale_notifica=" + codCanaleNotifica : "")
                + (StringUtils.isNotBlank(uidRichiesta) ? "&uid_richiesta=" + uidRichiesta : "")
                + (StringUtils.isNotBlank(tipoRichiesta) ? "&tipo_richiesta=" + tipoRichiesta : "")
                + (dataIntegrazione != null ? "&data_integrazione=" + dataIntegrazione : "");
        try {
            Response resp = getInvocationBuilder(targetUrl, requestHeaders).post(null);
            if (resp.getStatus() >= 400) {
                ErrorDTO err = getError(resp);
                logger.error(getClassFunctionErrorInfo(className, methodName, SERVER_EXCEPTION + err));
                throw new GenericException(err);
            }
            setHeaderAttoreGestione(resp.getHeaderString(Constants.HEADER_ATTORE_GESTIONE));
            return resp.readEntity(new GenericType<>() {
            });
        } catch (ProcessingException e) {
            logger.error(getClassFunctionErrorInfo(className, methodName, e));
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logger.debug(getClassFunctionEndInfo(className, methodName));
        }
    }

}