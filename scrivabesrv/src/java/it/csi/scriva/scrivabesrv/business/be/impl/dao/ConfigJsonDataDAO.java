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

import it.csi.scriva.scrivabesrv.dto.ConfigJsonDataDTO;

import java.util.List;
import java.util.Map;

/**
 * The interface Config json data dao.
 *
 * @author CSI PIEMONTE
 */
public interface ConfigJsonDataDAO {

    /**
     * Load map json data list.
     *
     * @return the list
     */
    List<ConfigJsonDataDTO> loadConfigJsonData();

    /**
     * Load map json data by id list.
     *
     * @param id the id
     * @return the list
     */
    List<ConfigJsonDataDTO> loadConfigJsonDataById(Long id);

    /**
     * Load map json data by tipi quadro list.
     *
     * @param tipiQuadroCodes  the tipi quadro codes
     * @param ignoreFlgObbligo the ignore flg obbligo
     * @return the list
     */
    List<ConfigJsonDataDTO> loadConfigJsonDataByTipiQuadro(List<String> tipiQuadroCodes, boolean ignoreFlgObbligo);

    /**
     * Load json by query string.
     *
     * @param query the query
     * @param map   the map
     * @return the string
     */
    String loadJsonByQuery(String query, Map<String, Object> map);

    /**
     * Update json data integer.
     *
     * @param id       the id
     * @param jsonData the json data
     * @return the integer
     */
    Integer updateJsonData(Long id, String jsonData);

}