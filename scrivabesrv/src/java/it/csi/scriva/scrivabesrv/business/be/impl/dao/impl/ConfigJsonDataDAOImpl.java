/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigJsonDataDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigJsonDataDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigJsonDataDAOImpl extends ScrivaBeSrvGenericDAO<ConfigJsonDataDTO> implements ConfigJsonDataDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID = "AND id_config_json_data=:idConfigJsonData\n";

    private static final String WHERE_FLG_OBBLIGO = "OR flg_obbligo=1\n";

    private static final String WHERE_TIPI_QUADRO_IN = "AND cod_tipo_quadro IN (:tipiQuadroCodes)\n";

    private static final String QUERY_CONFIG_JSON_DATA = "SELECT sdcjd.*, sdtq.*\n" +
            "FROM _replaceTableName_ sdcjd\n" +
            "INNER JOIN scriva_d_tipo_quadro sdtq ON sdtq.id_tipo_quadro = sdcjd.id_tipo_quadro\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_UPDATE_JSON_DATA = "UPDATE _replaceTableName_\n" +
            "SET json_data_sample = to_json(:jsonData::json)" +
            "WHERE id_config_json_data=:idConfigJsonData";

    /**
     * Load map json data list.
     *
     * @return the list
     */
    @Override
    public List<ConfigJsonDataDTO> loadConfigJsonData() {
        return loadConfigJsonData(null, null, Boolean.FALSE);
    }

    /**
     * Load map json data by id list.
     *
     * @param id the id
     * @return the list
     */
    @Override
    public List<ConfigJsonDataDTO> loadConfigJsonDataById(Long id) {
        return loadConfigJsonData(id, null, Boolean.FALSE);
    }

    /**
     * Load map json data by tipi quadro list.
     *
     * @param tipiQuadroCodes  the tipi quadro codes
     * @param ignoreFlgObbligo the ignore flg obbligo
     * @return the list
     */
    @Override
    public List<ConfigJsonDataDTO> loadConfigJsonDataByTipiQuadro(List<String> tipiQuadroCodes, boolean ignoreFlgObbligo) {
        return loadConfigJsonData(null, tipiQuadroCodes, ignoreFlgObbligo);
    }

    /**
     * Load json by query string.
     *
     * @param query the query
     * @param map   the map
     * @return the string
     */
    @Override
    public String loadJsonByQuery(String query, Map<String, Object> map) {
        logBegin(className);
        return getJsonFromQuerySimple(className, query, map, null, null);
    }

    /**
     * Update json data integer.
     *
     * @param id       the id
     * @param jsonData the json data
     * @return the integer
     */
    @Override
    public Integer updateJsonData(Long id, String jsonData) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idConfigJsonData", id);
            map.put("jsonData", jsonData);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_JSON_DATA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }


    private List<ConfigJsonDataDTO> loadConfigJsonData(Long id, List<String> tipiQuadroCodes, boolean ignoreFlgObbligo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_CONFIG_JSON_DATA;

        if (id != null) {
            map.put("idConfigJsonData", id);
            query += WHERE_ID;
        }
        if (CollectionUtils.isNotEmpty(tipiQuadroCodes)) {
            map.put("tipiQuadroCodes", tipiQuadroCodes);
            query += WHERE_TIPI_QUADRO_IN;
        }
        return findListByQuery(className, query + (Boolean.FALSE.equals(ignoreFlgObbligo) ? WHERE_FLG_OBBLIGO : ""), map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_CONFIG_JSON_DATA + WHERE_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ConfigJsonDataDTO> getRowMapper() throws SQLException {
        return new ConfigJsonDataRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ConfigJsonDataDTO> getExtendedRowMapper() throws SQLException {
        return new ConfigJsonDataRowMapper();
    }

    public static class ConfigJsonDataRowMapper implements RowMapper<ConfigJsonDataDTO> {

        /**
         * Instantiates a new Nazione row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfigJsonDataRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data
         * in the ResultSet. This method should not call {@code next()} on
         * the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public ConfigJsonDataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfigJsonDataDTO bean = new ConfigJsonDataDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfigJsonDataDTO bean) throws SQLException {
            bean.setIdConfigJsonData(rs.getLong("id_config_json_data"));
            bean.setIdTipoQuadro(rs.getLong("id_tipo_quadro"));
            bean.setCodTipoQuadro(rs.getString("cod_tipo_quadro"));
            bean.setFlgObbligo(rs.getInt("flg_obbligo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDesTabella(rs.getString("des_tabella"));
            bean.setDesTag(rs.getString("des_tag"));
            bean.setQueryEstrazione(rs.getString("query_estrazione"));
        }
    }

}