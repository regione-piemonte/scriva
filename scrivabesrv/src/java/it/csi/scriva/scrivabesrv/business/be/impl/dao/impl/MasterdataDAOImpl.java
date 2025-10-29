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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.MasterdataDAO;
import it.csi.scriva.scrivabesrv.dto.MasterdataDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Masterdata dao.
 *
 * @author CSI PIEMONTE
 */
public class MasterdataDAOImpl extends ScrivaBeSrvGenericDAO<MasterdataDTO> implements MasterdataDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_MASTERDATA_BY_CODE = "select * from _replaceTableName_ where cod_masterdata = :code";

    /**
     * Load by code masterdata dto.
     *
     * @param codMasterdata codMasterdata
     * @return MasterdataDTO masterdata dto
     */
    @Override
    public MasterdataDTO loadByCode(String codMasterdata) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("code", codMasterdata);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(getQuery(QUERY_MASTERDATA_BY_CODE, null, null), params, getRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<MasterdataDTO>
     */
    @Override
    public RowMapper<MasterdataDTO> getRowMapper() throws SQLException {
        return new MasterdataRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<MasterdataDTO>
     */
    @Override
    public RowMapper<MasterdataDTO> getExtendedRowMapper() throws SQLException {
        return new MasterdataRowMapper();
    }

    /**
     * The type Masterdata row mapper.
     */
    public static class MasterdataRowMapper implements RowMapper<MasterdataDTO> {

        /**
         * Instantiates a new Masterdata row mapper.
         *
         * @throws SQLException the sql exception
         */
        public MasterdataRowMapper() throws SQLException {
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
        public MasterdataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MasterdataDTO bean = new MasterdataDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, MasterdataDTO bean) throws SQLException {
            bean.setIdMasterdata(rs.getLong("id_masterdata"));
            bean.setCodMasterdata(rs.getString("cod_masterdata"));
            bean.setDesMasterdata(rs.getString("des_masterdata"));

        }
    }
}