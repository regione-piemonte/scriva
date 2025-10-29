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

import it.csi.scriva.scrivabesrv.dto.TipoNatura2000DTO;

import java.util.List;

/**
 * The interface Tipo natura 2000 dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoNatura2000DAO {

    /**
     * Load tipi natura 2000 list.
     *
     * @return the list
     */
    List<TipoNatura2000DTO> loadTipiNatura2000();

    /**
     * Load tipo natura 2000 by id list.
     *
     * @param idTipoNatura2000 the id tipo natura 2000
     * @return the list
     */
    List<TipoNatura2000DTO> loadTipoNatura2000ById(Long idTipoNatura2000);

    /**
     * Load tipo natura 2000 by code list.
     *
     * @param codTipoNatura2000 the cod tipo natura 2000
     * @return the list
     */
    List<TipoNatura2000DTO> loadTipoNatura2000ByCode(String codTipoNatura2000);

}