/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao;

import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoOggettoExtendedDTO;

import java.util.List;

/**
 * The interface Riscossione ente dao.
 *
 * @author CSI PIEMONTE
 */
public interface AdempimentoTipoOggettoDAO {

    /**
     * Load adempimento tipi oggetto list.
     *
     * @return the list
     */
    List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggetto();

    /**
     * Load adempimento tipi oggetto by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByIdAdempimento(Long idAdempimento);

    /**
     * Load adempimento tipi oggetto by code adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByCodeAdempimento(String codAdempimento);

}