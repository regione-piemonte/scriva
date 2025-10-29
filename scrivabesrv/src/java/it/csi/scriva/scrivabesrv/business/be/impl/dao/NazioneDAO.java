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

import it.csi.scriva.scrivabesrv.dto.NazioneDTO;

import java.util.List;

/**
 * The interface Nazione dao.
 *
 * @author CSI PIEMONTE
 */
public interface NazioneDAO {

    /**
     * Load nazioni list.
     *
     * @return the list
     */
    List<NazioneDTO> loadNazioni();

    /**
     * Load nazioni attive list.
     *
     * @return the list
     */
    List<NazioneDTO> loadNazioniAttive();

    /**
     * Load nazione by id list.
     *
     * @param idNazione the id nazione
     * @return the list
     */
    List<NazioneDTO> loadNazioneById(Long idNazione);

    /**
     * Load nazione by cod istat list.
     *
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    List<NazioneDTO> loadNazioneByCodIstat(String codIstatNazione);

    /**
     * Load nazione by denominazione list.
     *
     * @param denomNazione the denom nazione
     * @return the list
     */
    List<NazioneDTO> loadNazioneByDenominazione(String denomNazione);

    /**
     * Load nazione by cod iso list.
     *
     * @param codIso the cod iso
     * @return the list
     */
    List<NazioneDTO> loadNazioneByCodIso(String codIso);

}