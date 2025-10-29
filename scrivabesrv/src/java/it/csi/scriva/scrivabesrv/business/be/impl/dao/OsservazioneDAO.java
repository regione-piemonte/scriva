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
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * The interface Osservazione dao.
 *
 * @author CSI PIEMONTE
 */
public interface OsservazioneDAO {

    /**
     * Load osservazioni list.
     *
     * @param idIstanza            the id istanza
     * @param codStatoOsservazione the cod stato osservazione
     * @param idOsservazione       the id osservazione
     * @param attoreScriva         the attore scriva
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @return the list
     */
    List<OggettoOsservazioneExtendedDTO> loadOsservazioni(Long idIstanza, String codStatoOsservazione, Long idOsservazione, AttoreScriva attoreScriva, String offset, String limit, String sort);

    /**
     * Load osservazione by id list.
     *
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param attoreScriva          the attore scriva
     * @return the list
     */
    List<OggettoOsservazioneExtendedDTO> loadOsservazioneById(Long idIstanzaOsservazione, AttoreScriva attoreScriva);

    /**
     * Save osservazione long.
     *
     * @param osservazione the osservazione
     * @return the long
     */
    Long saveOsservazione(OggettoOsservazioneDTO osservazione);

    /**
     * Update osservazione integer.
     *
     * @param osservazione the osservazione
     * @return the integer
     */
    Integer updateOsservazione(OggettoOsservazioneDTO osservazione);

    /**
     * Delete osservazione by id istanza osservazione integer.
     *
     * @param idIstanzaOsservazione the id istanza osservazione
     * @return the integer
     */
    Integer deleteOsservazioneByIdIstanzaOsservazione(Long idIstanzaOsservazione);

    /**
     * Update data pubblicazione osservazione by id allegato integer.
     *
     * @param idAllegatoIstanza    the id allegato
     * @param dataPubblicazione    the data pubblicazione
     * @param codStatoOsservazione the cod stato osservazione
     * @param attoreScriva         the attore scriva
     * @return the integer
     */
    Integer updateDataPubbOsservazioneByIdAllegato(Long idAllegatoIstanza, Timestamp dataPubblicazione, String codStatoOsservazione, AttoreScriva attoreScriva);

	/**
	 * Load osservazioni list sintesi.
	 *
	 * @param idIstanza            the id istanza
	 * @param codStatoOsservazione the cod stato osservazione
	 * @param idOsservazione       the id osservazione
	 * @param attoreScriva         the attore scriva
	 * @param offset               the offset
	 * @param limit                the limit
	 * @param sort                 the sort
	 * @return the list
	 */
	List<OggettoOsservazioneExtendedDTO> loadOsservazioniSintesi(Long idIstanza, String codStatoOsservazione, Long idOsservazione, AttoreScriva attoreScriva, String offset, String limit, String sort);

}