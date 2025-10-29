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

import it.csi.scriva.scrivabesrv.dto.RuoloCompilanteDTO;

import java.util.List;

/**
 * The interface Ruolo compilante dao.
 *
 * @author CSI PIEMONTE
 */
public interface RuoloCompilanteDAO {

    /**
     * Load ruoli compilante list.
     *
     * @return List<RuoloCompilanteDTO> list
     */
    List<RuoloCompilanteDTO> loadRuoliCompilante();

    /**
     * Load ruolo compilante list.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<RuoloCompilanteDTO> list
     */
    List<RuoloCompilanteDTO> loadRuoloCompilante(Long idRuoloCompilante);

    /**
     * Load ruolo compilante by code list.
     *
     * @param codRuoloCompilante codRuoloCompilante
     * @return List<RuoloCompilanteDTO> list
     */
    List<RuoloCompilanteDTO> loadRuoloCompilanteByCode(String codRuoloCompilante);

    /**
     * Save ruolo compilante.
     *
     * @param dto RuoloCompilanteDTO
     */
    void saveRuoloCompilante(RuoloCompilanteDTO dto);

    /**
     * Update ruolo compilante.
     *
     * @param dto RuoloCompilanteDTO
     */
    void updateRuoloCompilante(RuoloCompilanteDTO dto);

    /**
     * Delete ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     */
    void deleteRuoloCompilante(Long idRuoloCompilante);

    /**

    /**
     * Load ruolo compilante.
     *
     * @param componenteAttore componenteAttore
     * @return RuoloCompilanteDTO
     */
    List<RuoloCompilanteDTO> loadRuoloCompilanteByAttore(String componenteAttore, Long idIstanza);
}