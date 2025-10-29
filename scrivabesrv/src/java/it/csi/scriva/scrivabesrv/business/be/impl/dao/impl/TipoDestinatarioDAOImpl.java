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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoDestinatarioDAO;
import it.csi.scriva.scrivabesrv.dto.TipoDestinatarioDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo destinatario dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoDestinatarioDAOImpl extends ScrivaBeSrvGenericDAO<TipoDestinatarioDTO> implements TipoDestinatarioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_TIPO_DESTINATARIO = "AND id_tipo_destinatario = :idTipoDestinatario\n";

    private static final String WHERE_COD_TIPO_DESTINATARIO = "AND UPPER(cod_tipo_destinatario) = UPPER(:codTipoDestinatario)\n";

    private static final String QUERY_LOAD_TIPI_DESTINATARIO = "SELECT * FROM _replaceTableName_ WHERE 1=1\n";

    /**
     * Load tipi messaggio list.
     *
     * @return List<TipoNotificaDTO>      list
     */
    @Override
    public List<TipoDestinatarioDTO> loadTipiDestinatario() {
        logBegin(className);
        return loadTipoDestinatario(null, null);
    }

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoDestinatario the id tipo destinatario
     * @return List<TipoNotificaDTO>      list
     */
    @Override
    public List<TipoDestinatarioDTO> loadTipoDestinatario(Long idTipoDestinatario) {
        logBegin(className);
        return loadTipoDestinatario(idTipoDestinatario, null);
    }

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoDestinatario the cod tipo destinatario
     * @return List<TipoNotificaDTO>      list
     */
    @Override
    public List<TipoDestinatarioDTO> loadTipoDestinatarioByCode(String codTipoDestinatario) {
        logBegin(className);
        return loadTipoDestinatario(null, codTipoDestinatario);
    }

    /**
     * Load tipo destinatario list.
     *
     * @param idTipoDestinatario  the id tipo destinatario
     * @param codTipoDestinatario the cod tipo destinatario
     * @return the list
     */
    private List<TipoDestinatarioDTO> loadTipoDestinatario(Long idTipoDestinatario, String codTipoDestinatario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_TIPI_DESTINATARIO;
        if (idTipoDestinatario != null) {
            map.put("idTipoDestinatario", idTipoDestinatario);
            query += WHERE_ID_TIPO_DESTINATARIO;
        }
        if (StringUtils.isNotBlank(codTipoDestinatario)) {
            map.put("codTipoDestinatario", codTipoDestinatario);
            query += WHERE_COD_TIPO_DESTINATARIO;
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
        return getQuery(QUERY_LOAD_TIPI_DESTINATARIO + WHERE_ID_TIPO_DESTINATARIO, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoDestinatarioDTO> getRowMapper() throws SQLException {
        return new TipoDestinatarioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoDestinatarioDTO> getExtendedRowMapper() throws SQLException {
        return new TipoDestinatarioRowMapper();
    }

    /**
     * The type Tipo destinatario row mapper.
     */
    public static class TipoDestinatarioRowMapper implements RowMapper<TipoDestinatarioDTO> {

        /**
         * Instantiates a new Tipo destinatario row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoDestinatarioRowMapper() throws SQLException {
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
        public TipoDestinatarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoDestinatarioDTO bean = new TipoDestinatarioDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoDestinatarioDTO bean) throws SQLException {
            bean.setIdTipoDestinatario(rs.getLong("id_tipo_destinatario"));
            bean.setCodTipoDestinatario(rs.getString("cod_tipo_destinatario"));
            bean.setDesTipoDestinatario(rs.getString("des_tipo_destinatario"));
        }
    }
}