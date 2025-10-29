/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.service;

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.AccreditamentoDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;

import java.util.List;

/**
 * The interface Ambiti service.
 *
 * @author CSI PIEMONTE
 */
public interface AccreditamentiService {

    /**
     * Load accreditamento by pk accreditamento dto.
     *
     * @param pk the pk
     * @return the accreditamento dto
     */
    AccreditamentoDTO loadAccreditamentoByPK(Long pk);

    /**
     * Save accreditamento accreditamento dto.
     *
     * @param accreditamento the accreditamento
     * @return the accreditamento dto
     * @throws GenericException the generic exception
     */
    AccreditamentoDTO saveAccreditamento(AccreditamentoDTO accreditamento) throws GenericException;

    /**
     * Validate accreditamento list.
     *
     * @param uid the uid
     * @param otp the otp
     * @return the list
     * @throws GenericException the generic exception
     */
    List<CompilanteDTO> validateAccreditamento(String uid, String otp) throws GenericException;

    /**
     * Validate dto error dto.
     *
     * @param accreditamento the accreditamento
     * @return the error dto
     */
    ErrorDTO validateDTO(AccreditamentoDTO accreditamento);

}