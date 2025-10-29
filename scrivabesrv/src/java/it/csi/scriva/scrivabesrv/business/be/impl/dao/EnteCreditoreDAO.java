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

import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;

import java.util.List;

/**
 * The interface Ente creditore dao.
 *
 * @author CSI PIEMONTE
 */
public interface EnteCreditoreDAO {

    /**
     * Load enti creditori list.
     *
     * @return List<EnteCreditoreDTO> list
     */
    List<EnteCreditoreDTO> loadEntiCreditori();

    /**
     * Load ente creditore by id list.
     *
     * @param idEnteCreditore idEnteCreditore
     * @return List<EnteCreditoreDTO> list
     */
    List<EnteCreditoreDTO> loadEnteCreditoreById(Long idEnteCreditore);

    /**
     * Load ente creditore by cf list.
     *
     * @param cfEnteCreditore cfEnteCreditore
     * @return List<TipoCompetenzaDTO> list
     */
    List<EnteCreditoreDTO> loadEnteCreditoreByCF(String cfEnteCreditore);

}