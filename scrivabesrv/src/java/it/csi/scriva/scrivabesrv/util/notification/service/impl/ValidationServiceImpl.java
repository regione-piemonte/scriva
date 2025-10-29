/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.service.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.BaseApiServiceImpl;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import it.csi.scriva.scrivabesrv.util.notification.service.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationServiceImpl extends BaseApiServiceImpl implements ValidationService {

    private final String className = this.getClass().getSimpleName();


    /**
     * Validate message error dto.
     *
     * @return the error dto
     */
    @Override
    public ErrorDTO validateMessage() {
        logBegin(className);
        return null;
    }

    /**
     * Validate input parameters error dto.
     *
     * @param idIstanza         the id istanza
     * @param codTipoevento     the cod tipoevento
     * @param rifCanaleNotifica the rif canale notifica
     * @param codCanaleNotifica the cod canale notifica
     * @param dataIntegrazione  the data integrazione
     * @param attoreScriva      the attore scriva
     * @return the error dto
     */
    @Override
    public ErrorDTO validateInputParameters(Long idIstanza, String codTipoevento, String rifCanaleNotifica, String codCanaleNotifica, Date dataIntegrazione, AttoreScriva attoreScriva) {
        logBeginInfo(className, "\nidIstanza : [" + idIstanza + "]\ncodTipoevento : [" + codTipoevento + "]\nrifCanaleNotifica : [" + rifCanaleNotifica + "]\ncodCanaleNotifica : [" + codCanaleNotifica + "]\ndataIntegrazione : [" + dataIntegrazione + "]\nattoreScriva : [" + attoreScriva + "]\n");
        ErrorDTO error = null;
        Map<String, String> details = new HashMap<>();
        try {
            if (StringUtils.isBlank(codTipoevento)) {
                details.put("codTipoevento", ValidationResultEnum.MANDATORY.getDescription());
            }
            if (StringUtils.isBlank(rifCanaleNotifica) && StringUtils.isNotBlank(codCanaleNotifica)) {
                details.put("rifCanaleNotifica", ValidationResultEnum.INVALID.getDescription());
            }
            if (StringUtils.isNotBlank(rifCanaleNotifica) && StringUtils.isBlank(codCanaleNotifica)) {
                details.put("codCanaleNotifica", ValidationResultEnum.INVALID.getDescription());
            }
            if (attoreScriva == null || StringUtils.isBlank(attoreScriva.getCodiceFiscale()) || StringUtils.isBlank(attoreScriva.getComponente())) {
                details.put("attoreScriva", ValidationResultEnum.INVALID.getDescription());
            }

            if (!details.isEmpty()) {
                error = getErrorManager().getError("400", "E004", "I dati inseriti non sono formalmente corretti.", details, null);
            }
            return error;
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return error;
    }
}