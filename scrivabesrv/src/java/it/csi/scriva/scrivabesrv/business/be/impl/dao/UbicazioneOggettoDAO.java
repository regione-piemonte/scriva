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

import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoExtendedDTO;

import java.util.List;

/**
 * The interface Ubicazione oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface UbicazioneOggettoDAO {

    /**
     * Load ubicazioni oggetti list.
     *
     * @return List<UbicazioneOggettoExtendedDTO>   list
     */
    List<UbicazioneOggettoExtendedDTO> loadUbicazioniOggetti();

    /**
     * Load ubicazione oggetto list.
     *
     * @param idUbicaOggetto idUbicaOggetto
     * @return List<UbicazioneOggettoExtendedDTO>   list
     */
    List<UbicazioneOggettoExtendedDTO> loadUbicazioneOggetto(Long idUbicaOggetto);

    /**
     * Load ubicazione oggetto by id oggetto list.
     *
     * @param idOggetto idOggetto
     * @return List<UbicazioneOggettoExtendedDTO>   list
     */
    List<UbicazioneOggettoExtendedDTO> loadUbicazioneOggettoByIdOggetto(Long idOggetto);

    /**
     * Find by pk ubicazione oggetto dto.
     *
     * @param idUbicaOggetto idUbicaOggetto
     * @return UbicazioneOggettoDTO ubicazione oggetto dto
     */
    UbicazioneOggettoDTO findByPK(Long idUbicaOggetto);

    /**
     * Save ubicazione oggetto long.
     *
     * @param dto UbicazioneOggettoDTO
     * @return id record inserito
     */
    Long saveUbicazioneOggetto(UbicazioneOggettoDTO dto);

    /**
     * Update ubicazione oggetto integer.
     *
     * @param dto UbicazioneOggettoDTO
     * @return numero record aggiornati
     */
    Integer updateUbicazioneOggetto(UbicazioneOggettoDTO dto);

    /**
     * Delete ubicazione oggetto integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    Integer deleteUbicazioneOggetto(String gestUID);

    /**
     * Delete ubicazione oggetto by id integer.
     *
     * @param idUbicaOggetto idUbicaOggetto
     * @return numero record cancellati
     */
    Integer deleteUbicazioneOggettoById(Long idUbicaOggetto);

    /**
     * Delete ubicazione oggetto by id oggetto integer.
     *
     * @param idOggetto idOggetto
     * @return numero record cancellati
     */
    Integer deleteUbicazioneOggettoByIdOggetto(Long idOggetto);

    /**
     * Delete ubicazione oggetto by uid oggetto integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    Integer deleteUbicazioneOggettoByUidOggetto(String gestUID);

    /**
     * Delete ubicazioni oggetto by not in id ubicazioni oggetto integer.
     *
     * @param idOggetto           idOggetto
     * @param idUbicazioniOggetto idUbicazioniOggetto
     * @return numero record cancellati
     */
    Integer deleteUbicazioniOggettoByNotInIdUbicazioniOggetto(Long idOggetto, List<Long> idUbicazioniOggetto);

    /**
     * Copy ubicazioni oggetto by id oggetto istanza long.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    Integer copyUbicazioniOggettoByIdOggettoIstanza(Long idOggettoIstanza, Long idOggetto);

}