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

import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.GruppoSoggettoDTO;

import javax.validation.constraints.NotNull;

/**
 * The interface Gruppo soggetto service.
 *
 * @author CSI PIEMONTE
 */
public interface GruppoSoggettoService {

    /**
     * Create gruppo soggetto gruppo soggetto dto.
     *
     * @param gruppoSoggetto the gruppo soggetto
     * @return the gruppo soggetto dto
     * @throws GenericException the generic exception
     */
    GruppoSoggettoDTO createGruppoSoggetto(GruppoSoggettoDTO gruppoSoggetto) throws GenericException;

    /**
     * Add soggetto istanza to gruppo.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param gruppoSoggetto    the gruppo soggetto
     * @param flgCapoGruppo     the flg capo gruppo
     * @param attoreScriva      the attore scriva
     */
    void addSoggettoIstanzaToGruppo(@NotNull Long idSoggettoIstanza, @NotNull GruppoSoggettoDTO gruppoSoggetto, @NotNull Boolean flgCapoGruppo, @NotNull AttoreScriva attoreScriva);

    /**
     * Update soggetto istanza gruppo.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param gruppoSoggetto    the gruppo soggetto
     * @param flgCapoGruppo     the flg capo gruppo
     * @param attoreScriva      the attore scriva
     * @return the integer
     */
    Integer updateSoggettoIstanzaGruppo(@NotNull Long idSoggettoIstanza, @NotNull GruppoSoggettoDTO gruppoSoggetto, @NotNull Boolean flgCapoGruppo, @NotNull AttoreScriva attoreScriva);

    /**
     * Remove soggetto istanza from gruppo.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the integer
     */
    Integer removeSoggettoIstanzaFromGruppo(Long idSoggettoIstanza);

    /**
     * Remove gruppo integer.
     *
     * @param idGruppo the id gruppo
     * @return the integer
     */
    Integer removeGruppo(Long idGruppo);

    /**
     * Delete gruppo soggetto integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteGruppoSoggetto(String gestUID);

    /**
     * Delete soggetto gruppo integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteSoggettoGruppo(String gestUID);

}