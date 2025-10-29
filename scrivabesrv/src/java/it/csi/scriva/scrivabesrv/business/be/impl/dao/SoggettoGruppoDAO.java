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

import it.csi.scriva.scrivabesrv.dto.SoggettoGruppoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoGruppoExtendedDTO;

import java.util.List;

/**
 * The interface Soggetto gruppo dao.
 *
 * @author CSI PIEMONTE
 */
public interface SoggettoGruppoDAO {

    /**
     * Load gruppi soggetto list.
     *
     * @return the list
     */
    List<SoggettoGruppoDTO> loadSoggettiGruppo();

    /**
     * Load soggetti gruppo by pk soggetto gruppo dto.
     *
     * @param idGruppoSoggetto  the id gruppo soggetto
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the soggetto gruppo dto
     */
    SoggettoGruppoDTO loadSoggettiGruppoByPK(Long idGruppoSoggetto, Long idSoggettoIstanza);

    /**
     * Load soggetto gruppo by id list.
     *
     * @param idGruppoSoggetto the id soggetto gruppo
     * @return the list
     */
    List<SoggettoGruppoExtendedDTO> loadSoggettoGruppoByIdGruppoSoggetto(Long idGruppoSoggetto);

    /**
     * Load soggetto gruppo by id soggetto istanza list.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the list
     */
    List<SoggettoGruppoExtendedDTO> loadSoggettoGruppoByIdSoggettoIstanza(Long idSoggettoIstanza);

    /**
     * Save soggetto gruppo long.
     *
     * @param gruppoSoggetto the gruppo soggetto
     * @return the long
     */
    Long saveSoggettoGruppo(SoggettoGruppoDTO gruppoSoggetto);

    /**
     * Update soggetto gruppo integer.
     *
     * @param gruppoSoggetto the gruppo soggetto
     * @return the integer
     */
    Integer updateSoggettoGruppo(SoggettoGruppoDTO gruppoSoggetto);

    /**
     * Delete soggetto gruppo by uid integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteSoggettoGruppoByUid(String gestUID);
}