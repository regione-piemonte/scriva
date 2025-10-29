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

import it.csi.scriva.scrivabesrv.dto.GestioneAttoreDTO;

import java.util.List;

/**
 * The interface Gestione attore dao.
 */
public interface GestioneAttoreDAO {

    /**
     * Load gestione attore list.
     *
     * @return the list
     */
    List<GestioneAttoreDTO> loadGestioneAttore();

    /**
     * Load gestione attore by id list.
     *
     * @param idGestioneAttore the id gestione attore
     * @return the list
     */
    List<GestioneAttoreDTO> loadGestioneAttoreById(Long idGestioneAttore);

    /**
     * Load gestione attore by code list.
     *
     * @param codGestioneAttore the cod gestione attore
     * @return the list
     */
    List<GestioneAttoreDTO> loadGestioneAttoreByCode(String codGestioneAttore);

}