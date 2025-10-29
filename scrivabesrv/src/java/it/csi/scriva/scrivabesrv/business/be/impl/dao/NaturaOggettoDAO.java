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

import it.csi.scriva.scrivabesrv.dto.NaturaOggettoDTO;

import java.util.List;

/**
 * The interface Natura oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface NaturaOggettoDAO {

    /**
     * Load nature oggetto list.
     *
     * @return List<NaturaOggettoDTO> list
     */
    List<NaturaOggettoDTO> loadNatureOggetto();

    /**
     * Load natura oggetto list.
     *
     * @param idNaturaOggetto idNaturaOggetto
     * @return List<NaturaOggettoDTO> list
     */
    List<NaturaOggettoDTO> loadNaturaOggetto(Long idNaturaOggetto);

    /**
     * Load natura oggetto by code list.
     *
     * @param codNaturaOggetto codNaturaOggetto
     * @return List<NaturaOggettoDTO> list
     */
    List<NaturaOggettoDTO> loadNaturaOggettoByCode(String codNaturaOggetto);

    /**
     * Save natura oggetto.
     *
     * @param dto NaturaOggettoDTO
     */
    void saveNaturaOggetto(NaturaOggettoDTO dto);

    /**
     * Update natura oggetto.
     *
     * @param dto NaturaOggettoDTO
     */
    void updateNaturaOggetto(NaturaOggettoDTO dto);

    /**
     * Delete natura oggetto.
     *
     * @param gestUID gestUID
     */
    void deleteNaturaOggetto(String gestUID);

}