/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.notification.service;

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;

import java.sql.Date;

/**
 * The interface Validation service.
 *
 * @author CSI PIEMONTE
 */
public interface ValidationService {

    /**
     * Validate message error dto.
     *
     * @return the error dto
     */
    ErrorDTO validateMessage();

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
    ErrorDTO validateInputParameters(Long idIstanza, String codTipoevento, String rifCanaleNotifica, String codCanaleNotifica, Date dataIntegrazione, AttoreScriva attoreScriva);

}