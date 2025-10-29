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

import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;

import java.util.List;

/**
 * The interface Adempimenti service.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentiService {

    /**
     * Load adempimenti list.
     *
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param attoreScriva       the attore scriva
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimenti(Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante, AttoreScriva attoreScriva);

    /**
     * Load adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    List<AdempimentoExtendedDTO> loadAdempimento(Long idAdempimento, AttoreScriva attoreScriva);

}