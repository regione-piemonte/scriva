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

import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaCategoriaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaCategoriaExtendedDTO;

import java.util.List;

/**
 * The interface Oggetto istanza categoria dao.
 *
 * @author CSI PIEMONTE
 */
public interface OggettoIstanzaCategoriaDAO {

    /**
     * Load oggetti istanza categoria list.
     *
     * @return List<OggettoIstanzaCategoriaExtendedDTO>   list
     */
    List<OggettoIstanzaCategoriaExtendedDTO> loadOggettiIstanzaCategoria();

    /**
     * Load oggetto istanza categoria by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<OggettoIstanzaCategoriaExtendedDTO>   list
     */
    List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdOggettoIstanza(Long idOggettoIstanza,String componenteApp);

    /**
     * Load oggetto istanza categoria by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdIstanza(Long idIstanza);

    /**
     * Load oggetto istanza categoria by id categoria oggetto list.
     *
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<OggettoIstanzaCategoriaExtendedDTO>   list
     */
    List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdCategoriaOggetto(Long idCategoriaOggetto);

    /**
     * Load oggetto istanza categoria by id oggetto istanza id categoria list.
     *
     * @param idOggettoIstanza   idOggettoIstanza
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<OggettoIstanzaCategoriaExtendedDTO>   list
     */
    List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdOggettoIstanzaIdCategoria(Long idOggettoIstanza, Long idCategoriaOggetto);

    /**
     * Load oggetto istanza categoria by uid list.
     *
     * @param uid the uid
     * @return the list
     */
    List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByUID(String uid);

    /**
     * Save oggetto istanza categoria long.
     *
     * @param dto SoggettoIstanzaDTO
     * @return id record salvato
     */
    Long saveOggettoIstanzaCategoria(OggettoIstanzaCategoriaDTO dto);

    /**
     * Update oggetto istanza categoria integer.
     *
     * @param dto SoggettoIstanzaDTO
     * @return numero record inseriti
     */
    Integer updateOggettoIstanzaCategoria(OggettoIstanzaCategoriaDTO dto);

    /**
     * Delete oggetto istanza categoria integer.
     *
     * @param uid uidOggettoIstanzaCategoria
     * @return numero record cancellati
     */
    Integer deleteOggettoIstanzaCategoria(String uid);

    /**
     * Delete oggetto istanza categoria integer.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return numero record cancellati
     */
    Integer deleteOggettoIstanzaCategoria(Long idOggettoIstanza);

}