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

import it.csi.scriva.scrivabesrv.dto.TipoAbilitazioneDTO;

import java.util.List;

/**
 * The interface Tipo abilitazione dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoAbilitazioneDAO {

    /**
     * Load tipi abilitazione list.
     *
     * @return List<TipoSoggettoDTO>  list
     */
    List<TipoAbilitazioneDTO> loadTipiAbilitazione();

    /**
     * Load tipo abilitazione by id list.
     *
     * @param idTipoAbilitazione idTipoAbilitazione
     * @return List<TipoSoggettoDTO>  list
     */
    List<TipoAbilitazioneDTO> loadTipoAbilitazioneById(Long idTipoAbilitazione);

    /**
     * Load tipo abilitazione by code list.
     *
     * @param codTipoAbilitazione codTipoAbilitazione
     * @return List<TipoSoggettoDTO>  list
     */
    List<TipoAbilitazioneDTO> loadTipoAbilitazioneByAdempimentoIstanzaCfAttore(String codTipoAbilitazione);

    /**
     * Load tipo abilitazione by code list.
     *
     * @param idAdempimento the id adempimento
     * @param idIstanza     the id istanza
     * @param cfAttore      the cf attore
     * @return the list
     */
    List<TipoAbilitazioneDTO> loadTipoAbilitazioneByAdempimentoIstanzaCfAttore(Long idAdempimento, Long idIstanza, String cfAttore);

}