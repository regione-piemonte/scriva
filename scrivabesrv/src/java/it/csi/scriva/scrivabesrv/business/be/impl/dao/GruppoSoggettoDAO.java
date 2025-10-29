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

import it.csi.scriva.scrivabesrv.dto.GruppoSoggettoDTO;

import java.util.List;

/**
 * The interface Gruppo soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface GruppoSoggettoDAO {

    /**
     * Load gruppi soggetto list.
     *
     * @return the list
     */
    List<GruppoSoggettoDTO> loadGruppiSoggetto();

    /**
     * Load gruppo soggetto by id list.
     *
     * @param idGruppoSoggetto the id gruppo soggetto
     * @return the list
     */
    List<GruppoSoggettoDTO> loadGruppoSoggettoById(Long idGruppoSoggetto);

    /**
     * Load gruppo soggetto by id soggetto istanza list.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the list
     */
    List<GruppoSoggettoDTO> loadGruppoSoggettoByIdSoggettoIstanza(Long idSoggettoIstanza);

    /**
     * Save gruppo soggetto long.
     *
     * @param gruppoSoggetto the gruppo soggetto
     * @return the long
     */
    Long saveGruppoSoggetto(GruppoSoggettoDTO gruppoSoggetto);

    /**
     * Update gruppo soggetto integer.
     *
     * @param gruppoSoggetto the gruppo soggetto
     * @return the integer
     */
    Integer updateGruppoSoggetto(GruppoSoggettoDTO gruppoSoggetto);

    /**
     * Delete gruppo soggetto by uid integer.
     *
     * @param uid the uid
     * @return the integer
     */
    Integer deleteGruppoSoggettoByUid(String gestUID);
}