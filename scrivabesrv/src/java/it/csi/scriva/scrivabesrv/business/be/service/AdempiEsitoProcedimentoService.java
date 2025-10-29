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

import it.csi.scriva.scrivabesrv.dto.AdempiEsitoProcedimentoExtendedDTO;

import java.util.List;

/**
 * The interface Adempi esito procedimento service.
 *
 * @author CSI PIEMONTE
 */
public interface AdempiEsitoProcedimentoService {

    /**
     * Load adempi esito procedimento list.
     *
     * @param idAdempimento the id adempimento
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    List<AdempiEsitoProcedimentoExtendedDTO> loadAdempiEsitoProcedimento(Long idAdempimento, boolean flgAttivi);

}