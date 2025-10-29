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

import it.csi.scriva.scrivabesrv.dto.TipoMessaggioDTO;

import java.util.List;

/**
 * The interface Tipo messaggio dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoMessaggioDAO {

    /**
     * Load tipi messaggio list.
     *
     * @return List<TipoMessaggioDTO> list
     */
    List<TipoMessaggioDTO> loadTipiMessaggio();

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoMessaggio idTipoMessaggio
     * @return List<TipoMessaggioDTO> list
     */
    List<TipoMessaggioDTO> loadTipoMessaggio(Long idTipoMessaggio);

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoMessaggio codTipoMessaggio
     * @return List<TipoMessaggioDTO> list
     */
    List<TipoMessaggioDTO> loadTipoMessaggioByCode(String codTipoMessaggio);

    /**
     * Save tipo messaggio long.
     *
     * @param dto TipoMessaggioDTO
     * @return id record salvato
     */
    Long saveTipoMessaggio(TipoMessaggioDTO dto);

    /**
     * Update tipo messaggio integer.
     *
     * @param dto TipoMessaggioDTO
     * @return numero record aggiornati
     */
    Integer updateTipoMessaggio(TipoMessaggioDTO dto);

    /**
     * Delete tipo messaggio integer.
     *
     * @param idTipoMessaggio idTipoMessaggio
     * @return numero record cancellati
     */
    Integer deleteTipoMessaggio(Long idTipoMessaggio);

	TipoMessaggioDTO findByPK(Long idTipoMEssaggio);
}