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

import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;

import java.util.List;

/**
 * The interface Tipologia oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipologiaOggettoDAO {

    /**
     * Load tipologie oggetto list.
     *
     * @return List<TipologiaOggettoExtendedDTO>    list
     */
    List<TipologiaOggettoExtendedDTO> loadAll();

    /**
     * Load tipologia oggetto list.
     *
     * @param idTipologiaOggetto the id tipologia oggetto
     * @return List<TipologiaOggettoExtendedDTO>    list
     */
    List<TipologiaOggettoExtendedDTO> loadTipologiaOggetto(Long idTipologiaOggetto);

    /**
     * Load tipologia oggetto by code list.
     *
     * @param codTipologiaOggetto the cod tipologia oggetto
     * @return List<TipologiaOggettoExtendedDTO>    list
     */
    List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByCode(String codTipologiaOggetto);

    /**
     * Load tipologia oggetto by code adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByCodeAdempimento(String codAdempimento);

    /**
     * Load tipologia oggetto by ogg istanza categoria list.
     *
     * @param idIstanza        the id istanza
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByOggIstanzaCategoria(Long idIstanza, Long idOggettoIstanza);

    /**
     * Load tipologia oggetto by id layers list.
     *
     * @param idLayerList the id layer list
     * @return the list
     */
    List<TipologiaOggettoDTO> loadTipologiaOggettoByIdLayers(List<Long> idLayerList);

}