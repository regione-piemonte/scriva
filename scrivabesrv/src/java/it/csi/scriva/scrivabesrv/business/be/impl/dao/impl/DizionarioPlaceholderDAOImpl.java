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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.DizionarioPlaceholderDAO;
import it.csi.scriva.scrivabesrv.dto.DizionarioPlaceholderDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Dizionario placeholder dao.
 */
public class DizionarioPlaceholderDAOImpl extends ScrivaBeSrvGenericDAO<DizionarioPlaceholderDTO> implements DizionarioPlaceholderDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_DIZIONARIO_PH = "AND id_dizionario_placeholder = :idDizionarioPlaceholder\n";

    private static final String WHERE_COD_DIZIONARIO_PH = "AND UPPER(cod_dizionario_placeholder) = UPPER(:codDizionarioPlaceholder)\n";

    private static final String WHERE_COD_DIZIONARIO_PH_LIST = "AND cod_dizionario_placeholder IN (:codDizionarioPlaceholderList)\n";

    private static final String QUERY_LOAD_DIZIONARI_PH = "SELECT * FROM _replaceTableName_ WHERE 1=1\n";

    /**
     * Load dizionari placeholder list.
     *
     * @return the list
     */
    @Override
    public List<DizionarioPlaceholderDTO> loadDizionariPlaceholder() {
        logBegin(className);
        return loadDizionarioPlaceholder(null, null, null);
    }

    /**
     * Load dizionario placeholder by id list.
     *
     * @param idDizionarioPlaceholder the id dizionario placeholder
     * @return the list
     */
    @Override
    public List<DizionarioPlaceholderDTO> loadDizionarioPlaceholderById(Long idDizionarioPlaceholder) {
        logBegin(className);
        return loadDizionarioPlaceholder(idDizionarioPlaceholder, null, null);
    }

    /**
     * Load dizionario placeholder by code list.
     *
     * @param codDizionarioPlaceholder the cod dizionario placeholder
     * @return the list
     */
    @Override
    public List<DizionarioPlaceholderDTO> loadDizionarioPlaceholderByCode(String codDizionarioPlaceholder) {
        logBegin(className);
        return loadDizionarioPlaceholder(null, codDizionarioPlaceholder, null);
    }

    /**
     * Load dizionario placeholder by code list.
     *
     * @param codDizionarioPlaceholderList the cod dizionario placeholder list
     * @return the list
     */
    @Override
    public List<DizionarioPlaceholderDTO> loadDizionarioPlaceholderByCode(List<String> codDizionarioPlaceholderList) {
        return null;
    }

    /**
     * Load dizionario placeholder list.
     *
     * @param idDizionarioPlaceholder  the id dizionario placeholder
     * @param codDizionarioPlaceholder the cod dizionario placeholder
     * @return the list
     */
    private List<DizionarioPlaceholderDTO> loadDizionarioPlaceholder(Long idDizionarioPlaceholder, String codDizionarioPlaceholder, List<String> codDizionarioPlaceholderList) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_DIZIONARI_PH;
        if (idDizionarioPlaceholder != null) {
            map.put("idDizionarioPlaceholder", idDizionarioPlaceholder);
            query += WHERE_ID_DIZIONARIO_PH;
        }
        if (StringUtils.isNotBlank(codDizionarioPlaceholder)) {
            map.put("codDizionarioPlaceholder", codDizionarioPlaceholder);
            query += WHERE_COD_DIZIONARIO_PH;
        }
        if (CollectionUtils.isNotEmpty(codDizionarioPlaceholderList)) {
            map.put("codDizionarioPlaceholderList", codDizionarioPlaceholderList);
            query += WHERE_COD_DIZIONARIO_PH_LIST;
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
        return getQuery(QUERY_LOAD_DIZIONARI_PH + WHERE_ID_DIZIONARIO_PH, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<DizionarioPlaceholderDTO> getRowMapper() throws SQLException {
        return new DizionarioPlaceholderRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<DizionarioPlaceholderDTO> getExtendedRowMapper() throws SQLException {
        return new DizionarioPlaceholderRowMapper();
    }

    /**
     * The type Dizionario placeholder row mapper.
     */
    public static class DizionarioPlaceholderRowMapper implements RowMapper<DizionarioPlaceholderDTO> {

        /**
         * Instantiates a new Dizionario placeholder row mapper.
         *
         * @throws SQLException the sql exception
         */
        public DizionarioPlaceholderRowMapper() throws SQLException {
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
        public DizionarioPlaceholderDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DizionarioPlaceholderDTO bean = new DizionarioPlaceholderDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, DizionarioPlaceholderDTO bean) throws SQLException {
            bean.setIdDizionarioPlaceholder(rs.getLong("id_dizionario_placeholder"));
            bean.setCodDizionarioPlaceholder(rs.getString("cod_dizionario_placeholder"));
            bean.setNomeTabella(rs.getString("nome_tabella"));
            bean.setNomeCampo(rs.getString("nome_campo"));
            bean.setDesDizionarioPlaceholder(rs.getString("des_dizionario_placeholder"));
        }
    }
}