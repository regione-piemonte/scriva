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

import it.csi.scriva.scrivabesrv.dto.StatoIstanzaAdempimentoDTO;

import java.util.List;

/**
 * The interface Stato istanza adempimento dao.
 *
 * @author CSI PIEMONTE
 */
public interface StatoIstanzaAdempimentoDAO {

    /**
     * Load stati istanza adempimento list.
     *
     * @return List<StatoIstanzaAdempimentoDTO> list
     */
    List<StatoIstanzaAdempimentoDTO> loadStatiIstanzaAdempimento();

    /**
     * Load stato istanza adempimento by id adempimento list.
     *
     * @param idAdempimento idAdempimento
     * @return List<StatoIstanzaAdempimentoDTO> list
     */
    List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdAdempimento(Long idAdempimento);

    /**
     * Load stato istanza adempimento by id stato istanza list.
     *
     * @param idStatoIstanza idStatoIstanza
     * @return List<StatoIstanzaAdempimentoDTO> list
     */
    List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdStatoIstanza(Long idStatoIstanza);

    /**
     * Load stato istanza adempimento by id adempimento id stato istanza list.
     *
     * @param idAdempimento  idAdempimento
     * @param idStatoIstanza idStatoIstanza
     * @return List<StatoIstanzaAdempimentoDTO> list
     */
    List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdAdempimentoIdStatoIstanza(Long idAdempimento, Long idStatoIstanza);


    /**
     * Load stato istanza adempimento by id adempimento id stato istanza new old list.
     *
     * @param idAdempimento         idAdempimento
     * @param idStatoIstanza        idStatoIstanza
     * @param idStatoIstanzaAmmesso idStatoIstanzaAmmesso
     * @return List<StatoIstanzaAdempimentoDTO> list
     */
    List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdAdempimentoIdStatoIstanzaNewOld(Long idAdempimento, Long idStatoIstanza, Long idStatoIstanzaAmmesso);

}