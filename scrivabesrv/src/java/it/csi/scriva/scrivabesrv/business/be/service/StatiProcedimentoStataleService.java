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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.StatoProcedimentoStataleDTO;

import java.util.List;

/**
 * The interface Stati procedimento statale service.
 *
 * @author CSI PIEMONTE
 */
public interface StatiProcedimentoStataleService {

    /**
     * Load stati procedimento statale by code list.
     *
     * @param codStatoProcStatale the cod stato proc statale
     * @param attoreScriva        the attore scriva
     * @return the list
     */
    List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStataleByCode(String codStatoProcStatale, AttoreScriva attoreScriva);

}