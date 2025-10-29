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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoNotificaDAO;
import it.csi.scriva.scrivabesrv.dto.StatoNotificaDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Stato notifica dao.
 *
 * @author CSI PIEMONTE
 */
public class StatoNotificaDAOImpl extends ScrivaBeSrvGenericDAO<StatoNotificaDTO> implements StatoNotificaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_STATO_NOTIFICA = "AND id_stato_notifica = :idStatoNotifica\n";

    private static final String WHERE_COD_STATO_NOTIFICA = "AND UPPER(cod_stato_notifica) = UPPER(:codStatoNotifica)\n";

    private static final String QUERY_LOAD_STATI_NOTIFICA = "SELECT * FROM _replaceTableName_ WHERE 1=1\n";

    /**
     * Load stati notifica list.
     *
     * @return the list
     */
    @Override
    public List<StatoNotificaDTO> loadStatiNotifica() {
        logBegin(className);
        return loadStatoNotifica(null, null);
    }

    /**
     * Load stato notifica by id list.
     *
     * @param idStatoNotifica the id stato notifica
     * @return the list
     */
    @Override
    public List<StatoNotificaDTO> loadStatoNotificaById(Long idStatoNotifica) {
        logBegin(className);
        return loadStatoNotifica(idStatoNotifica, null);
    }

    /**
     * Load stato notifica by code list.
     *
     * @param codStatoNotifica the cod stato notifica
     * @return the list
     */
    @Override
    public List<StatoNotificaDTO> loadStatoNotificaByCode(String codStatoNotifica) {
        logBegin(className);
        return loadStatoNotifica(null, codStatoNotifica);
    }

    /**
     * Load stato notifica list.
     *
     * @param idStatoNotifica  the id stato notifica
     * @param codStatoNotifica the cod stato notifica
     * @return the list
     */
    public List<StatoNotificaDTO> loadStatoNotifica(Long idStatoNotifica, String codStatoNotifica) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_STATI_NOTIFICA;
        if (idStatoNotifica != null) {
            map.put("idStatoNotifica", idStatoNotifica);
            query += WHERE_ID_STATO_NOTIFICA;
        }
        if (StringUtils.isNotBlank(codStatoNotifica)) {
            map.put("codStatoNotifica", codStatoNotifica);
            query += WHERE_COD_STATO_NOTIFICA;
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
        return getQuery(QUERY_LOAD_STATI_NOTIFICA + WHERE_ID_STATO_NOTIFICA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<StatoNotificaDTO> getRowMapper() throws SQLException {
        return new StatoNotificaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<StatoNotificaDTO> getExtendedRowMapper() throws SQLException {
        return new StatoNotificaRowMapper();
    }

    /**
     * The type Stato notifica row mapper.
     */
    public static class StatoNotificaRowMapper implements RowMapper<StatoNotificaDTO> {

        /**
         * Instantiates a new Stato notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public StatoNotificaRowMapper() throws SQLException {
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
        public StatoNotificaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StatoNotificaDTO bean = new StatoNotificaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, StatoNotificaDTO bean) throws SQLException {
            bean.setIdStatoNotifica(rs.getLong("id_stato_notifica"));
            bean.setCodStatoNotifica(rs.getString("cod_stato_notifica"));
            bean.setDesStatoNotifica(rs.getString("des_stato_notifica"));
        }
    }
}