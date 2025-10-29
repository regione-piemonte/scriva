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

import it.csi.scriva.scrivabesrv.dto.TipoOggettoAppDTO;

import java.util.List;

/**
 * The interface Tipo oggetto app dao.
 *
 * @author CSI PIEMONTE
 */
public interface TipoOggettoAppDAO {

    /**
     * Load tipi oggetto app list.
     *
     * @return List<TipoOggettoAppDTO> list
     */
    List<TipoOggettoAppDTO> loadTipiOggettoApp();

    /**
     * Load tipi oggetto app by id list.
     *
     * @param idTipoOggettoApp idTipoOggettoApp
     * @return List<TipoOggettoAppDTO> list
     */
    List<TipoOggettoAppDTO> loadTipiOggettoAppById(Long idTipoOggettoApp);

    /**
     * Load tipo oggetto app by code list.
     *
     * @param codTipoOggettoApp codTipoOggettoApp
     * @return List<TipoOggettoAppDTO> list
     */
    List<TipoOggettoAppDTO> loadTipoOggettoAppByCode(String codTipoOggettoApp);

}