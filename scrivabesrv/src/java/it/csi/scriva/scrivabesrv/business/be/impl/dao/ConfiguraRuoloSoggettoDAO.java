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

import it.csi.scriva.scrivabesrv.dto.ConfiguraRuoloSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.ConfiguraRuoloSoggettoExtendedDTO;

import java.util.List;

/**
 * The interface Configura ruolo soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface ConfiguraRuoloSoggettoDAO {

    /**
     * Load configura ruoli soggetti list.
     *
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggetti();

    /**
     * Load configura ruoli soggetto by ruolo compilante list.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettoByRuoloCompilante(Long idRuoloCompilante);

    /**
     * Load configura ruoli soggetti by adempimento list.
     *
     * @param idAdempimento idAdempimento
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettiByAdempimento(Long idAdempimento);

    /**
     * Load configura ruoli soggetti by ruolo soggetto list.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettiByRuoloSoggetto(Long idRuoloSoggetto);

    /**
     * Load configura ruoli soggetti by adempimento ruolo compilante list.
     *
     * @param idAdempimento     idAdempimento
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettiByAdempimentoRuoloCompilante(Long idAdempimento, Long idRuoloCompilante);

    /**
     * Save configura ruolo soggetto integer.
     *
     * @param configuraRuoloSoggettoDTO ConfiguraRuoloSoggettoDTO
     * @return id del record inserito
     */
    Integer saveConfiguraRuoloSoggetto(ConfiguraRuoloSoggettoDTO configuraRuoloSoggettoDTO);

    /**
     * Update configura ruolo soggetto integer.
     *
     * @param configuraRuoloSoggettoDTO ConfiguraRuoloSoggettoDTO
     * @return numero record aggiornati
     */
    Integer updateConfiguraRuoloSoggetto(ConfiguraRuoloSoggettoDTO configuraRuoloSoggettoDTO);

    /**
     * Delete configura ruolo soggetto integer.
     *
     * @param configuraRuoloSoggettoDTO ConfiguraRuoloSoggettoDTO
     * @return numero record cancellati
     */
    Integer deleteConfiguraRuoloSoggetto(ConfiguraRuoloSoggettoDTO configuraRuoloSoggettoDTO);
}