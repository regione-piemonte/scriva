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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNatura2000DAO;
import it.csi.scriva.scrivabesrv.dto.TipoNatura2000DTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo natura 2000 dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoNatura2000DAOImpl extends ScrivaBeSrvGenericDAO<TipoNatura2000DTO> implements TipoNatura2000DAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_TIPO_NATURA_2000 = "SELECT sdtn2.*\n" +
            "FROM _replaceTableName_ sdtn2\n" +
            "WHERE 1=1\n ";

    private static final String WHERE_ID_TIPO_NATURA_2000 = "AND id_tipo_natura_2000 = :idTipoNatura2000\n";

    private static final String WHERE_COD_TIPO_NATURA_2000 = "AND UPPER(cod_tipo_natura_2000) = UPPER(:codTipoNatura2000)\n";

    /**
     * Load tipi natura 2000 list.
     *
     * @return the list
     */
    @Override
    public List<TipoNatura2000DTO> loadTipiNatura2000() {
        return loadTipoNatura2000(null, null);
    }

    /**
     * Load tipo natura 2000 by id list.
     *
     * @param idTipoNatura2000 the id tipo natura 2000
     * @return the list
     */
    @Override
    public List<TipoNatura2000DTO> loadTipoNatura2000ById(Long idTipoNatura2000) {
        return loadTipoNatura2000(idTipoNatura2000, null);
    }

    /**
     * Load tipo natura 2000 by code list.
     *
     * @param codTipoNatura2000 the cod tipo natura 2000
     * @return the list
     */
    @Override
    public List<TipoNatura2000DTO> loadTipoNatura2000ByCode(String codTipoNatura2000) {
        return loadTipoNatura2000(null, codTipoNatura2000);
    }

    /**
     * Load tipo area protetta by code list.
     *
     * @param idTipoNatura2000  the id tipo natura 2000
     * @param codTipoNatura2000 the cod tipo natura 2000
     * @return the list
     */
    private List<TipoNatura2000DTO> loadTipoNatura2000(Long idTipoNatura2000, String codTipoNatura2000) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_TIPO_NATURA_2000;
        if (idTipoNatura2000 != null) {
            map.put("idTipoNatura2000", idTipoNatura2000);
            query += WHERE_ID_TIPO_NATURA_2000;
        }
        if (StringUtils.isNotBlank(codTipoNatura2000)) {
            map.put("codTipoNatura2000", codTipoNatura2000);
            query += WHERE_COD_TIPO_NATURA_2000;
        }
        logEnd(className);
        return findListByQuery(className, query, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_TIPO_NATURA_2000 + WHERE_ID_TIPO_NATURA_2000, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoNatura2000DTO> getRowMapper() throws SQLException {
        return new TipoNatura2000RowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoNatura2000DTO> getExtendedRowMapper() throws SQLException {
        return new TipoNatura2000RowMapper();
    }

    /**
     * The type Tipo area protetta row mapper.
     */
    public static class TipoNatura2000RowMapper implements RowMapper<TipoNatura2000DTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoNatura2000RowMapper() throws SQLException {
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
        public TipoNatura2000DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoNatura2000DTO bean = new TipoNatura2000DTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, TipoNatura2000DTO bean) throws SQLException {
            bean.setIdTipoNatura2000(rs.getLong("id_tipo_natura_2000"));
            bean.setCodTipoNatura2000(rs.getString("cod_tipo_natura_2000"));
            bean.setDesTipoNatura2000(rs.getString("des_tipo_natura_2000"));
//            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
//            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
//            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
//            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
//            bean.setGestUID(rs.getString("gest_uid"));
        }
    }
}