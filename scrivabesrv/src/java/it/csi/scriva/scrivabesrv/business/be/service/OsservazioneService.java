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


import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;

import java.util.List;

/**
 * The interface Osservazione service.
 *
 * @author CSI PIEMONTE
 */
public interface OsservazioneService {

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

	boolean isDataOsservazioneExpired(Long idIstanzaOsservazione, AttoreScriva attoreScriva);

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
	List<OggettoOsservazioneExtendedDTO> loadOsservazioniSintesi(Long idIstanza, String codStatoOsservazione, Long idOsservazione, AttoreScriva attoreScriva, String offset, String limit, String sort);

}