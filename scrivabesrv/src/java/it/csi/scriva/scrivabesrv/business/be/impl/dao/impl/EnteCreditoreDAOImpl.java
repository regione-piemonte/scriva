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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.EnteCreditoreDAO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ente creditore dao.
 */
public class EnteCreditoreDAOImpl extends ScrivaBeSrvGenericDAO<EnteCreditoreDTO> implements EnteCreditoreDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_CLAUSE = "ORDER BY denominazione_ente_creditore";

    private static final String WHERE_FLG_ATTIVO = "AND flg_attivo = 1 ";

    private static final String QUERY_ENTI_CREDITORI = "SELECT * FROM _replaceTableName_ WHERE 1=1 ";

    private static final String QUERY_ENTE_CREDITORE_BY_ID = QUERY_ENTI_CREDITORI + "AND id_ente_creditore = :idEnteCreditore ";

    private static final String QUERY_ENTE_CREDITORE_BY_CF = QUERY_ENTI_CREDITORI + "WHERE cf_ente_creditore = :cfEnteCreditore ";

    /**
     * @return List<EnteCreditoreDTO>
     */
    @Override
    public List<EnteCreditoreDTO> loadEntiCreditori() {
        logBegin(className);
        return findListByQuery(className, QUERY_ENTI_CREDITORI + ORDER_BY_CLAUSE, null);
    }

    /**
     * @param idEnteCreditore idEnteCreditore
     * @return List<EnteCreditoreDTO>
     */
    @Override
    public List<EnteCreditoreDTO> loadEnteCreditoreById(Long idEnteCreditore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idEnteCreditore", idEnteCreditore);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param cfEnteCreditore cfEnteCreditore
     * @return List<TipoCompetenzaDTO>
     */
    @Override
    public List<EnteCreditoreDTO> loadEnteCreditoreByCF(String cfEnteCreditore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfEnteCreditore", cfEnteCreditore);
        return findListByQuery(className, QUERY_ENTE_CREDITORE_BY_CF, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_ENTE_CREDITORE_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<EnteCreditoreDTO>
     */
    @Override
    public RowMapper<EnteCreditoreDTO> getRowMapper() throws SQLException {
        return new EnteCreditoreRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<EnteCreditoreDTO> getExtendedRowMapper() throws SQLException {
        return new EnteCreditoreRowMapper();
    }

    /**
     * The type Ente creditore row mapper.
     */
    public static class EnteCreditoreRowMapper implements RowMapper<EnteCreditoreDTO> {

        /**
         * Instantiates a new Ente creditore row mapper.
         *
         * @throws SQLException the sql exception
         */
        public EnteCreditoreRowMapper() throws SQLException {
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
        public EnteCreditoreDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnteCreditoreDTO bean = new EnteCreditoreDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, EnteCreditoreDTO bean) throws SQLException {
            bean.setIdEnteCreditore(rs.getLong("id_ente_creditore"));
            bean.setCfEnteCreditore(rs.getString("cf_ente_creditore"));
            bean.setDenominazioneEnteCreditore(rs.getString("denominazione_ente_creditore"));
            bean.setIndirizzoTesoreria(rs.getString("indirizzo_tesoreria"));
            bean.setIbanAccredito(rs.getString("iban_accredito"));
            bean.setBicAccredito(rs.getString("bic_accredito"));
            bean.setFlgAderiscePiemontepay(rs.getInt("flg_aderisce_piemontepay") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

}