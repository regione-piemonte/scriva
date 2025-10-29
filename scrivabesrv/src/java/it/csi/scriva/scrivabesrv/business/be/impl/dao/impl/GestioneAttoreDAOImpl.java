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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.GestioneAttoreDAO;
import it.csi.scriva.scrivabesrv.dto.GestioneAttoreDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ambito dao.
 *
 * @author CSI PIEMONTE
 */
public class GestioneAttoreDAOImpl extends ScrivaBeSrvGenericDAO<GestioneAttoreDTO> implements GestioneAttoreDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_GESTIONE_ATTORE = "AND id_gestione_attore = :idGestioneAttore\n";

    private static final String ORDER_BY = "ORDER BY ordinamento_gestione_attore\n";

    private static final String WHERE_COD_GESTIONE_ATTORE = "AND UPPER(cod_gestione_attore) = UPPER(:codGestioneAttore)\n";

    private static final String QUERY_GESTIONE_ATTORE = "SELECT * FROM _replaceTableName_ \n" +
            "WHERE 1 =1 \n";

    /**
     * Load gestione attore list.
     *
     * @return the list
     */
    @Override
    public List<GestioneAttoreDTO> loadGestioneAttore() {
        return loadGestioneAttore(null, null);
    }

    /**
     * Load gestione attore by id list.
     *
     * @param idGestioneAttore the id gestione attore
     * @return the list
     */
    @Override
    public List<GestioneAttoreDTO> loadGestioneAttoreById(Long idGestioneAttore) {
        return loadGestioneAttore(idGestioneAttore, null);
    }

    /**
     * Load gestione attore by code list.
     *
     * @param codGestioneAttore the cod gestione attore
     * @return the list
     */
    @Override
    public List<GestioneAttoreDTO> loadGestioneAttoreByCode(String codGestioneAttore) {
        return loadGestioneAttore(null, codGestioneAttore);
    }

    /**
     * Load gestione attore list.
     *
     * @param idGestioneAttore  the id gestione attore
     * @param codGestioneAttore the cod gestione attore
     * @return the list
     */
    private List<GestioneAttoreDTO> loadGestioneAttore(Long idGestioneAttore, String codGestioneAttore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_GESTIONE_ATTORE;
        if (idGestioneAttore != null) {
            map.put("idGestioneAttore", idGestioneAttore);
            query += WHERE_ID_GESTIONE_ATTORE;
        }
        if (StringUtils.isNotBlank(codGestioneAttore)) {
            map.put("codGestioneAttore", codGestioneAttore);
            query += WHERE_COD_GESTIONE_ATTORE;
        }
        return findListByQuery(className, query + ORDER_BY, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_GESTIONE_ATTORE, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RowMapper<GestioneAttoreDTO> getRowMapper() throws SQLException {
        return new GestioneAttoreRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<GestioneAttoreDTO> getExtendedRowMapper() throws SQLException {
        return new GestioneAttoreRowMapper();
    }


    /**
     * The type Ambito row mapper.
     */
    public static class GestioneAttoreRowMapper implements RowMapper<GestioneAttoreDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public GestioneAttoreRowMapper() throws SQLException {
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
        public GestioneAttoreDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            GestioneAttoreDTO bean = new GestioneAttoreDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, GestioneAttoreDTO bean) throws SQLException {
            bean.setIdGestioneAttore(rs.getLong("id_gestione_attore"));
            bean.setCodGestioneAttore(rs.getString("cod_gestione_attore"));
            bean.setDesGestioneAttore(rs.getString("des_gestione_attore"));
            bean.setOrdinamentoGestioneAttore(rs.getLong("ordinamento_gestione_attore"));
        }
    }

}