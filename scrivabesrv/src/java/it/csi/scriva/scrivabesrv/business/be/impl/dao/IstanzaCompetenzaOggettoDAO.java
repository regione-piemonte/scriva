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

import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaOggettoDTO;

import java.util.List;

/**
 * The interface Istanza competenza oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface IstanzaCompetenzaOggettoDAO {

    /**
     * Load istanza competenza oggetti list.
     *
     * @return the list
     */
    List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggetti();

    /**
     * Load istanza competenza oggetti list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggetti(Long idIstanza);

    /**
     * Load istanza competenza oggetti list.
     *
     * @param idIstanza              the id istanza
     * @param idCompetenzaTerritorio the id competenza territorio
     * @return the list
     */
    List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggetti(Long idIstanza, Long idCompetenzaTerritorio);

    /**
     * Save istanza competenza oggetto long.
     *
     * @param istanzaCompetenzaOggetto the istanza competenza oggetto
     * @return the long
     */
    void saveIstanzaCompetenzaOggetto(IstanzaCompetenzaOggettoDTO istanzaCompetenzaOggetto);

    /**
     * Delete istanza competenza.
     *
     * @param idIstanza              the id istanza
     * @param idCompetenzaTerritorio the id competenza territorio
     * @return the int
     */
    int deleteIstanzaCompetenzaOggetto(Long idIstanza, Long idCompetenzaTerritorio);

    /**
     * Delete istanza competenza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the int
     */
    int deleteIstanzaCompetenzaOggetto(Long idOggettoIstanza);

	/**
	 * Load istanza competenza oggetti per comuni.
	 *
	 * @param idIstanza the id istanza
	 * @return the list
	 */
	List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggettiPerComuni(Long idIstanza);

	/**
	 * Delete istanza competenza oggetto.
	 *
	 * @param idIstanza the id istanza
	 * @param idCompetenzaTerritorio the id competenza territorio
	 * @param idOggettoIstanza the id oggetto istanza
	 * @return the int
	 */
	int deleteIstanzaCompetenzaOggetto(Long idIstanza, Long idCompetenzaTerritorio, Long idOggettoIstanza);

	/**
	 * Load istanza competenza oggetti per SN 200.
	 *
	 * @param idIstanza the id istanza
	 * @return the list
	 */
	List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggettiPerSN200(Long idIstanza);

}