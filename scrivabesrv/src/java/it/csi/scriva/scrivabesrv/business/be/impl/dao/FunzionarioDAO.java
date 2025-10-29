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

import it.csi.scriva.scrivabesrv.dto.FunzionarioDTO;

import java.util.List;

/**
 * The interface Funzionario dao.
 *
 * @author CSI PIEMONTE
 */
public interface FunzionarioDAO {

    /**
     * Load funzionari list.
     *
     * @return the list
     */
    List<FunzionarioDTO> loadFunzionari();

    /**
     * Load funzionario by id list.
     *
     * @param id the id
     * @return the list
     */
    List<FunzionarioDTO> loadFunzionarioById(Long id);

    /**
     * Load funzionario by uid funzionario dto.
     *
     * @param gestUID the gest uid
     * @return the funzionario dto
     */
    FunzionarioDTO loadFunzionarioByUid(String gestUID);

    /**
     * Load funzionario by cf list.
     *
     * @param codiceFiscaleFunzionario the codice fiscale funzionario
     * @return the list
     */
    List<FunzionarioDTO> loadFunzionarioByCf(String codiceFiscaleFunzionario);

}