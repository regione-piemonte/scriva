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

import it.csi.scriva.scrivabesrv.business.be.exception.DAOException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.PingDAO;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Ping dao.
 *
 * @author CSI PIEMONTE
 */
public class PingDAOImpl extends ScrivaBeSrvGenericDAO<String> implements PingDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PING_DB_ON = "SELECT 1 AS ping";

    public void setTemplate(NamedParameterJdbcTemplate template) {
        this.template = template;
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
     * Ping db string.
     *
     * @return OK /KO
     * @throws DAOException DAOException
     */
    @Override
    public String pingDB() throws DAOException {
        logBegin(className);
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return template.queryForObject(QUERY_PING_DB_ON, params, getRowMapper());
        } catch (DataAccessResourceFailureException e) {
            logError(className, "Service unavailable : unable to get DB connection. The DB may be down\n" + e);
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<String>
     */
    @Override
    public RowMapper<String> getRowMapper() {
        return new StringRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<String>
     */
    @Override
    public RowMapper<String> getExtendedRowMapper() throws SQLException {
        return new StringRowMapper();
    }

    /**
     * Specific inner class for 'RowMapper' implementation
     */
    public static class StringRowMapper implements RowMapper<String> {

        /**
         * Instantiates a new String row mapper.
         */
        public StringRowMapper() {
            // Instatiate class
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
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("ping");
        }
    }

}