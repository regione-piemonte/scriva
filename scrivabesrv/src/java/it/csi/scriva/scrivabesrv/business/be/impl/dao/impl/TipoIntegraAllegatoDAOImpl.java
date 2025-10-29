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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoIntegraAllegatoDAO;
import it.csi.scriva.scrivabesrv.dto.TipoIntegraAllegatoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo integra allegato dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoIntegraAllegatoDAOImpl extends ScrivaBeSrvGenericDAO<TipoIntegraAllegatoDTO> implements TipoIntegraAllegatoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID = "AND sdtia.id_tipo_integrazione = :idTipoIntegraAllegato\n";

    private static final String WHERE_CODE = "AND UPPER(sdtia.cod_tipo_integrazione) = UPPER(:codTipoIntegraAllegato) \n";

    private static final String QUERY_TIPI_INTEGRA_ALLEGATO = "SELECT *\n" +
            "FROM _replaceTableName_ sdtia\n" +
            "WHERE 1 = 1 \n";

    /**
     * Load tipi integra allegato list.
     *
     * @return the list
     */
    @Override
    public List<TipoIntegraAllegatoDTO> loadTipiIntegraAllegato() {
        logBegin(className);
        return findListByQuery(className, QUERY_TIPI_INTEGRA_ALLEGATO, null);
    }

    /**
     * Load tipo integra allegato by id list.
     *
     * @param idTipoIntegraAllegato the id tipo integra allegato
     * @return the list
     */
    @Override
    public List<TipoIntegraAllegatoDTO> loadTipoIntegraAllegatoById(Long idTipoIntegraAllegato) {
        logBegin(className);
        Map<String, Object> map = null;
        String query = QUERY_TIPI_INTEGRA_ALLEGATO;
        if (idTipoIntegraAllegato != null) {
            map = new HashMap<>();
            map.put("idTipoIntegraAllegato", idTipoIntegraAllegato);
            query += WHERE_ID;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load tipo integra allegato by code list.
     *
     * @param codTipoIntegraAllegato the cod tipo integra allegato
     * @return the list
     */
    @Override
    public List<TipoIntegraAllegatoDTO> loadTipoIntegraAllegatoByCode(String codTipoIntegraAllegato) {
        logBegin(className);
        Map<String, Object> map = null;
        String query = QUERY_TIPI_INTEGRA_ALLEGATO;
        if (StringUtils.isNotBlank(codTipoIntegraAllegato)) {
            map = new HashMap<>();
            map.put("codTipoIntegraAllegato", codTipoIntegraAllegato);
            query += WHERE_CODE;
        }
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
        return QUERY_TIPI_INTEGRA_ALLEGATO + WHERE_ID;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoIntegraAllegatoDTO> getRowMapper() throws SQLException {
        return new TipoIntegraAllegatoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoIntegraAllegatoDTO> getExtendedRowMapper() throws SQLException {
        return new TipoIntegraAllegatoRowMapper();
    }

    public static class TipoIntegraAllegatoRowMapper implements RowMapper<TipoIntegraAllegatoDTO> {

        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoIntegraAllegatoRowMapper() throws SQLException {
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
        public TipoIntegraAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoIntegraAllegatoDTO bean = new TipoIntegraAllegatoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoIntegraAllegatoDTO bean) throws SQLException {
            bean.setIdTipoIntegraAllegato(rs.getLong("id_tipo_integrazione"));
            bean.setCodTipoIntegraAllegato(rs.getString("cod_tipo_integrazione"));
            bean.setDesTipoIntegraAllegato(rs.getString("des_tipo_integrazione"));
        }
    }

}