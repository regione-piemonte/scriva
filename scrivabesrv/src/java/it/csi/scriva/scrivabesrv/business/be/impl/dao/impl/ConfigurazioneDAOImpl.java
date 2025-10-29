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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Configurazione dao.
 *
 * @author CSI PIEMONTE
 */
public class ConfigurazioneDAOImpl extends ScrivaBeSrvGenericDAO<ConfigurazioneDTO> implements ConfigurazioneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_CONFIG = "SELECT * FROM _replaceTableName_ ";

    private static final String QUERY_LOAD_CONFIG_BY_KEY = QUERY_LOAD_CONFIG + "WHERE chiave = :key";

    private static final String QUERY_LOAD_CONFIG_BY_KEY_LIST = QUERY_LOAD_CONFIG + "WHERE TRIM(BOTH FROM chiave) IN (:keys)";

    /**
     * @return List<ConfigurazioneDTO>
     */
    @Override
    public List<ConfigurazioneDTO> loadConfig() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_CONFIG, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ConfigurazioneDTO> loadConfigByKey(String key) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        return findListByQuery(className, QUERY_LOAD_CONFIG_BY_KEY, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> loadConfigByKeyList(List<String> keys) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("keys", keys);
            MapSqlParameterSource params = getParameterValue(map);
            List<ConfigurazioneDTO> res = template.query(getQuery(QUERY_LOAD_CONFIG_BY_KEY_LIST, null, null), params, getRowMapper());
            Map<String, String> configs = new HashMap<>();
            for (ConfigurazioneDTO dto : res) {
                configs.put(dto.getChiave(), dto.getValore());
            }
            return configs;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrimaryKeySelect() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RowMapper<ConfigurazioneDTO> getRowMapper() throws SQLException {
        return new ConfigurazioneRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<ConfigurazioneDTO> getExtendedRowMapper() throws SQLException {
        return new ConfigurazioneRowMapper();
    }

    /**
     * The type Configurazione row mapper.
     */
    public static class ConfigurazioneRowMapper implements RowMapper<ConfigurazioneDTO> {

        /**
         * Instantiates a new Configurazione row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfigurazioneRowMapper() throws SQLException {
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
        public ConfigurazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfigurazioneDTO bean = new ConfigurazioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfigurazioneDTO bean) throws SQLException {
            bean.setChiave(rs.getString("chiave"));
            bean.setValore(rs.getString("valore"));
            bean.setNote(rs.getString("note"));
        }
    }

}