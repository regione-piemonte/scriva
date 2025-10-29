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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoSoggettoDAO;
import it.csi.scriva.scrivabesrv.dto.TipoSoggettoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoSoggettoDAOImpl extends ScrivaBeSrvGenericDAO<TipoSoggettoDTO> implements TipoSoggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "select * from _replaceTableName_ where id_tipo_soggetto = :id";

    private static final String QUERY_LOAD_TIPI_SOGGETTO = "select * from _replaceTableName_";

    private static final String QUERY_LOAD_TIPI_SOGGETTO_BY_CODE = "select * from _replaceTableName_ where UPPER(cod_tipo_soggetto) = UPPER(:codTipoSoggetto)";

    /**
     * Load tipi soggetto list.
     *
     * @return List<TipoSoggettoDTO> list
     */
    @Override
    public List<TipoSoggettoDTO> loadTipiSoggetto() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_SOGGETTO, null);
    }

    /**
     * Load tipo soggetto by id list.
     *
     * @param idTipoSoggetto idTipoSoggetto
     * @return List<TipoSoggettoDTO> list
     */
    @Override
    public List<TipoSoggettoDTO> loadTipoSoggettoById(Long idTipoSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("id", idTipoSoggetto);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load tipo soggetto by code list.
     *
     * @param codTipoSoggetto codTipoSoggetto
     * @return List<TipoSoggettoDTO> list
     */
    @Override
    public List<TipoSoggettoDTO> loadTipoSoggettoByCode(String codTipoSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoSoggetto", codTipoSoggetto);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_SOGGETTO_BY_CODE, map);
    }

    /**
     * Save tipo soggetto.
     *
     * @param dto TipoSoggettoDTO
     */
    @Override
    public void saveTipoSoggetto(TipoSoggettoDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Update tipo soggetto.
     *
     * @param dto TipoSoggettoDTO
     */
    @Override
    public void updateTipoSoggetto(TipoSoggettoDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Delete tipo soggetto.
     *
     * @param id idTipoSoggetto
     */
    @Override
    public void deleteTipoSoggetto(Long id) {
        // TODO Auto-generated method stub
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<TipoSoggettoDTO> getRowMapper() throws SQLException {
        return new TipoSoggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<TipoSoggettoDTO> getExtendedRowMapper() throws SQLException {
        return new TipoSoggettoRowMapper();
    }

    /**
     * The type Tipo soggetto row mapper.
     */
    public static class TipoSoggettoRowMapper implements RowMapper<TipoSoggettoDTO> {

        /**
         * Instantiates a new Tipo soggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoSoggettoRowMapper() throws SQLException {
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
        public TipoSoggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoSoggettoDTO bean = new TipoSoggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoSoggettoDTO bean) throws SQLException {
            bean.setIdTipoSoggetto(rs.getLong("id_tipo_soggetto"));
            bean.setCodiceTipoSoggetto(rs.getString("cod_tipo_soggetto"));
            bean.setDescrizioneTipoSoggetto(rs.getString("des_tipo_soggetto"));

        }
    }

}