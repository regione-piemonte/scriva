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

import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;

import java.util.List;

/**
 * The interface Stato istanza dao.
 *
 * @author CSI PIEMONTE
 */
public interface StatoIstanzaDAO {

    /**
     * Load stati istanza list.
     *
     * @return List<StatoIstanzaDTO>  list
     */
    List<StatoIstanzaDTO> loadStatiIstanza(Boolean visibile,AttoreScriva attoreScriva);

    /**
     * Load stato istanza stato istanza dto.
     *
     * @param id idStatoIstanza
     * @return StatoIstanzaDTO stato istanza dto
     */
    StatoIstanzaDTO loadStatoIstanza(Long id);

    /**
     * Load stato istanza by codice stato istanza dto.
     *
     * @param codice codice
     * @return StatoIstanzaDTO stato istanza dto
     */
    StatoIstanzaDTO loadStatoIstanzaByCodice(String codice);

    /**
     * Save stato istanza.
     *
     * @param dto StatoIstanzaDTO
     */
    void saveStatoIstanza(StatoIstanzaDTO dto);

    /**
     * Update stato istanza.
     *
     * @param dto StatoIstanzaDTO
     */
    void updateStatoIstanza(StatoIstanzaDTO dto);

    /**
     * Delete stato istanza.
     *
     * @param id idStatoIstanza
     */
    void deleteStatoIstanza(Long id);

    /**
     * Load stati istanza by componente app list.
     *
     * @param componenteApp the componente app
     * @return the list
     */
    List<StatoIstanzaDTO> loadStatiIstanzaByComponenteApp(String componenteApp);
}