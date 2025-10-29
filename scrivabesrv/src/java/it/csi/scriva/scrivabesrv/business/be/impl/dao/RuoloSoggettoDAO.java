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

import it.csi.scriva.scrivabesrv.dto.RuoloSoggettoDTO;

import java.util.List;

/**
 * The interface Ruolo soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface RuoloSoggettoDAO {

    /**
     * Load ruoli soggetto list.
     *
     * @return List<RuoloSoggettoDTO> list
     */
    List<RuoloSoggettoDTO> loadRuoliSoggetto();

    /**
     * Load ruolo soggetto list.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     * @return List<RuoloSoggettoDTO> list
     */
    List<RuoloSoggettoDTO> loadRuoloSoggetto(Long idRuoloSoggetto);

    /**
     * Load ruolo soggetto by code list.
     *
     * @param codRuoloSoggetto codRuoloSoggetto
     * @return List<RuoloSoggettoDTO> list
     */
    List<RuoloSoggettoDTO> loadRuoloSoggettoByCode(String codRuoloSoggetto);

    /**
     * Save ruolo soggetto.
     *
     * @param dto RuoloSoggettoDTO
     */
    void saveRuoloSoggetto(RuoloSoggettoDTO dto);

    /**
     * Update ruolo soggetto.
     *
     * @param dto RuoloSoggettoDTO
     */
    void updateRuoloSoggetto(RuoloSoggettoDTO dto);

    /**
     * Delete ruolo soggetto.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     */
    void deleteRuoloSoggetto(Long idRuoloSoggetto);

    /**
     * Load ruoli soggetto by id ruolo compilante and adempimento list.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param idAdempimento     idAdempimento
     * @return List<RuoloSoggettoDTO> list
     */
    List<RuoloSoggettoDTO> loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento(Long idRuoloCompilante, Long idAdempimento);

}