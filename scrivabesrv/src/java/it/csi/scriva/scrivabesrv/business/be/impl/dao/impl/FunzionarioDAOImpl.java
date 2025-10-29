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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioDAO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Funzionario dao.
 *
 * @author CSI PIEMONTE
 */
public class FunzionarioDAOImpl extends ScrivaBeSrvGenericDAO<FunzionarioDTO> implements FunzionarioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_FUNZIONARI = "SELECT *\nFROM _replaceTableName_ stf\nWHERE 1=1\n";

    private static final String QUERY_LOAD_FUNZIONARIO_BY_ID = QUERY_LOAD_FUNZIONARI + "AND stf.id_funzionario = :id\n";

    private static final String QUERY_LOAD_FUNZIONARIO_BY_UID = QUERY_LOAD_FUNZIONARI + "AND stf.gest_uid = :gestUID\n";

    private static final String QUERY_LOAD_FUNZIONARIO_BY_CF = QUERY_LOAD_FUNZIONARI + "AND stf.cf_funzionario = :codiceFiscaleFunzionario\n";

    /**
     * Load funzionari list.
     *
     * @return the list
     */
    @Override
    public List<FunzionarioDTO> loadFunzionari() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_FUNZIONARI, null);
    }

    /**
     * Load funzionario by id list.
     *
     * @param id the id
     * @return the list
     */
    @Override
    public List<FunzionarioDTO> loadFunzionarioById(Long id) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_FUNZIONARIO_BY_ID, map);
    }

    /**
     * Load funzionario by uid funzionario dto.
     *
     * @param gestUID the gest uid
     * @return the funzionario dto
     */
    @Override
    public FunzionarioDTO loadFunzionarioByUid(String gestUID) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUID", gestUID);
        return findSimpleDTOByQuery(className, QUERY_LOAD_FUNZIONARIO_BY_UID, map);
    }

    /**
     * Load funzionario by cf list.
     *
     * @param codiceFiscaleFunzionario the codice fiscale funzionario
     * @return the list
     */
    @Override
    public List<FunzionarioDTO> loadFunzionarioByCf(String codiceFiscaleFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codiceFiscaleFunzionario", codiceFiscaleFunzionario);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_FUNZIONARIO_BY_CF, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_FUNZIONARIO_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<FunzionarioDTO> getRowMapper() throws SQLException {
        return new FunzionarioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<FunzionarioDTO> getExtendedRowMapper() throws SQLException {
        return new FunzionarioRowMapper();
    }

    /**
     * The type Funzionario row mapper.
     */
    public static class FunzionarioRowMapper implements RowMapper<FunzionarioDTO> {

        /**
         * Instantiates a new Ente creditore row mapper.
         *
         * @throws SQLException the sql exception
         */
        public FunzionarioRowMapper() throws SQLException {
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
        public FunzionarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FunzionarioDTO bean = new FunzionarioDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, FunzionarioDTO bean) throws SQLException {
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setCfFunzionario(rs.getString("cf_funzionario"));
            bean.setNomeFunzionario(rs.getString("nome_funzionario"));
            bean.setCognomeFunzionario(rs.getString("cognome_funzionario"));
            bean.setNumTelefonoFunzionario(rs.getString("num_telefono_funzionario"));
            bean.setDesEmailFunzionario(rs.getString("des_email_funzionario"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}