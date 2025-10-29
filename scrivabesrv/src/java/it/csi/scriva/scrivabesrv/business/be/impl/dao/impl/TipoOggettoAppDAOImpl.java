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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoOggettoAppDAO;
import it.csi.scriva.scrivabesrv.dto.TipoOggettoAppDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo oggetto app dao.
 */
public class TipoOggettoAppDAOImpl extends ScrivaBeSrvGenericDAO<TipoOggettoAppDTO> implements TipoOggettoAppDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_TIPI_OGGETTO_APP = "SELECT * FROM _replaceTableName_ ";

    private static final String QUERY_LOAD_TIPI_OGGETTO_APP_BY_ID = QUERY_LOAD_TIPI_OGGETTO_APP + "WHERE id_tipo_oggetto_app = :idTipoOggettoApp";

    private static final String QUERY_LOAD_TIPI_OGGETTO_APP_BY_CODE = QUERY_LOAD_TIPI_OGGETTO_APP + "WHERE UPPER(cod_tipo_oggetto_app) = UPPER(:codTipoOggettoApp)";

    /**
     * @return List<TipoOggettoAppDTO>
     */
    @Override
    public List<TipoOggettoAppDTO> loadTipiOggettoApp() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_OGGETTO_APP, null);
    }

    /**
     * @param idTipoOggettoApp idTipoOggettoApp
     * @return List<TipoOggettoAppDTO>
     */
    @Override
    public List<TipoOggettoAppDTO> loadTipiOggettoAppById(Long idTipoOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoOggettoApp", idTipoOggettoApp);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param codTipoOggettoApp codTipoOggettoApp
     * @return List<TipoOggettoAppDTO>
     */
    @Override
    public List<TipoOggettoAppDTO> loadTipoOggettoAppByCode(String codTipoOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoOggettoApp", codTipoOggettoApp);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_OGGETTO_APP_BY_CODE, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_TIPI_OGGETTO_APP_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoOggettoAppDTO>
     */
    @Override
    public RowMapper<TipoOggettoAppDTO> getRowMapper() throws SQLException {
        return new TipoOggettoAppRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<TipoOggettoAppDTO> getExtendedRowMapper() throws SQLException {
        return new TipoOggettoAppRowMapper();
    }

    /**
     * The type Tipo oggetto app row mapper.
     */
    public static class TipoOggettoAppRowMapper implements RowMapper<TipoOggettoAppDTO> {

        /**
         * Instantiates a new Tipo oggetto app row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoOggettoAppRowMapper() throws SQLException {
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
        public TipoOggettoAppDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoOggettoAppDTO bean = new TipoOggettoAppDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoOggettoAppDTO bean) throws SQLException {
            bean.setIdTipoOggettoApp(rs.getLong("id_tipo_oggetto_app"));
            bean.setCodTipoOggettoApp(rs.getString("cod_tipo_oggetto_app"));
            bean.setDesTipoOggettoApp(rs.getString("des_tipo_oggetto_app"));
        }
    }
}