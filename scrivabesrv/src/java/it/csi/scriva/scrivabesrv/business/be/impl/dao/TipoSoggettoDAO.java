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

import it.csi.scriva.scrivabesrv.dto.TipoSoggettoDTO;

import java.util.List;

/**
 * The interface Tipo soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoSoggettoDAO {

    /**
     * Load tipi soggetto list.
     *
     * @return List<TipoSoggettoDTO> list
     */
    List<TipoSoggettoDTO> loadTipiSoggetto();

    /**
     * Load tipo soggetto by id list.
     *
     * @param idTipoSoggetto idTipoSoggetto
     * @return List<TipoSoggettoDTO> list
     */
    List<TipoSoggettoDTO> loadTipoSoggettoById(Long idTipoSoggetto);

    /**
     * Load tipo soggetto by code list.
     *
     * @param codTipoSoggetto codTipoSoggetto
     * @return List<TipoSoggettoDTO> list
     */
    List<TipoSoggettoDTO> loadTipoSoggettoByCode(String codTipoSoggetto);

    /**
     * Save tipo soggetto.
     *
     * @param dto TipoSoggettoDTO
     */
    void saveTipoSoggetto(TipoSoggettoDTO dto);

    /**
     * Update tipo soggetto.
     *
     * @param dto TipoSoggettoDTO
     */
    void updateTipoSoggetto(TipoSoggettoDTO dto);

    /**
     * Delete tipo soggetto.
     *
     * @param id idTipoSoggetto
     */
    void deleteTipoSoggetto(Long id);

}