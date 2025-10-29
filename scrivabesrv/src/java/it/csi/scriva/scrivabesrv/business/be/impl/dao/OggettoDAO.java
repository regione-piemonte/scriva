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

import it.csi.scriva.scrivabesrv.dto.OggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;

import java.util.List;

/**
 * The interface Oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoDAO {

    /**
     * Load oggetti list.
     *
     * @return List<OggettoExtendedDTO>      list
     */
    List<OggettoExtendedDTO> loadOggetti();

    /**
     * Load oggetto by id list.
     *
     * @param idOggetto idOggetto
     * @return List<OggettoExtendedDTO>      list
     */
    List<OggettoExtendedDTO> loadOggettoById(Long idOggetto);

    /**
     * Load oggetto by id comune list.
     *
     * @param idComune idComune
     * @return List<OggettoExtendedDTO>      list
     */
    List<OggettoExtendedDTO> loadOggettoByIdComune(Long idComune);

    /**
     * Load oggetto by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<OggettoExtendedDTO> loadOggettoByIdIstanza(Long idIstanza);

    /**
     * Load oggetto by uid list.
     *
     * @param gestUID gestUID
     * @return List<OggettoExtendedDTO>      list
     */
    List<OggettoExtendedDTO> loadOggettoByUID(String gestUID);

    /**
     * Load oggetto by cod scriva list.
     *
     * @param codScriva the cod scriva
     * @return the list
     */
    List<OggettoDTO> loadOggettoByCodScriva(String codScriva);

    /**
     * Gets next cod scriva.
     *
     * @return the next cod scriva
     */
    String getNextCodScriva();

    /**
     * Search oggetto list.
     *
     * @param searchOggetto searchOggetto
     * @return List<OggettoExtendedDTO>      list
     */
    List<OggettoExtendedDTO> searchOggetto(SearchOggettoDTO searchOggetto);

    /**
     * Search oggetto list.
     *
     * @param searchOggetto     the search oggetto
     * @param statiIstanza      the stati istanza
     * @param annoPresentazione the anno presentazione
     * @return the list
     */
    List<OggettoExtendedDTO> searchOggetto(SearchOggettoDTO searchOggetto, List<String> statiIstanza, Integer annoPresentazione);

    /**
     * Find by pk oggetto dto.
     *
     * @param idOggetto idOggetto
     * @return OggettoDTO oggetto dto
     */
    OggettoDTO findByPK(Long idOggetto);

    /**
     * Save oggetto long.
     *
     * @param dto OggettoDTO
     * @return id record salvato
     */
    Long saveOggetto(OggettoDTO dto);

    /**
     * Update oggetto integer.
     *
     * @param dto OggettoDTO
     * @return numero record aggiornati
     */
    Integer updateOggetto(OggettoDTO dto);

    /**
     * Delete oggetto integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    Integer deleteOggetto(String gestUID);

    /**
     * Delete oggetto by id integer.
     *
     * @param idOggetto idOggetto
     * @return numero record cancellati
     */
    Integer deleteOggettoById(Long idOggetto);

    /**
     * Update oggetto by oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    Integer updateOggettoByOggettoIstanza(Long idOggettoIstanza);

}