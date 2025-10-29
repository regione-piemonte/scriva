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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAreaProtettaDAO;
import it.csi.scriva.scrivabesrv.dto.TipoAreaProtettaDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo area protetta dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoAreaProtettaDAOImpl extends ScrivaBeSrvGenericDAO<TipoAreaProtettaDTO> implements TipoAreaProtettaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_TIPO_AREA_PROTETTA = "SELECT sdtap.*\n" +
            "FROM _replaceTableName_ sdtap\n" +
            "WHERE 1=1\n ";

    private static final String WHERE_ID_TIPO_AREA_PROTETTA = "AND id_tipo_area_protetta = :idTipoAreaProtetta\n";

    private static final String WHERE_COD_TIPO_AREA_PROTETTA = "AND UPPER(cod_tipo_area_protetta) = UPPER(:codTipoAreaProtetta)\n";

    /**
     * Load tipi area protetta list.
     *
     * @return the list
     */
    @Override
    public List<TipoAreaProtettaDTO> loadTipiAreaProtetta() {
        return loadTipoAreaProtetta(null, null);
    }

    /**
     * Load tipo area protetta by id list.
     *
     * @param idTipoAreaProtetta the id tipo area protetta
     * @return the list
     */
    @Override
    public List<TipoAreaProtettaDTO> loadTipoAreaProtettaById(Long idTipoAreaProtetta) {
        return loadTipoAreaProtetta(idTipoAreaProtetta, null);
    }

    /**
     * Load tipo area protetta by code list.
     *
     * @param codTipoAreaProtetta the cod tipo area protetta
     * @return the list
     */
    @Override
    public List<TipoAreaProtettaDTO> loadTipoAreaProtettaByCode(String codTipoAreaProtetta) {
        return loadTipoAreaProtetta(null, codTipoAreaProtetta);
    }

    /**
     * Load tipo area protetta by code list.
     *
     * @param idTipoAreaProtetta  the id tipo area protetta
     * @param codTipoAreaProtetta the cod tipo area protetta
     * @return the list
     */
    private List<TipoAreaProtettaDTO> loadTipoAreaProtetta(Long idTipoAreaProtetta, String codTipoAreaProtetta) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_TIPO_AREA_PROTETTA;
        if (idTipoAreaProtetta != null) {
            map.put("idTipoAreaProtetta", idTipoAreaProtetta);
            query += WHERE_ID_TIPO_AREA_PROTETTA;
        }
        if (StringUtils.isNotBlank(codTipoAreaProtetta)) {
            map.put("codTipoAreaProtetta", codTipoAreaProtetta);
            query += WHERE_COD_TIPO_AREA_PROTETTA;
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
        return getQuery(QUERY_LOAD_TIPO_AREA_PROTETTA + WHERE_ID_TIPO_AREA_PROTETTA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoAreaProtettaDTO> getRowMapper() throws SQLException {
        return new TipoAreaProtettaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoAreaProtettaDTO> getExtendedRowMapper() throws SQLException {
        return new TipoAreaProtettaRowMapper();
    }


    /**
     * The type Tipo area protetta row mapper.
     */
    public static class TipoAreaProtettaRowMapper implements RowMapper<TipoAreaProtettaDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAreaProtettaRowMapper() throws SQLException {
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
        public TipoAreaProtettaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAreaProtettaDTO bean = new TipoAreaProtettaDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, TipoAreaProtettaDTO bean) throws SQLException {
            bean.setIdTipoAreaProtetta(rs.getLong("id_tipo_area_protetta"));
            bean.setCodTipoAreaProtetta(rs.getString("cod_tipo_area_protetta"));
            bean.setDesTipoAreaProtetta(rs.getString("des_tipo_area_protetta"));
//            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
//            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
//            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
//            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
//            bean.setGestUID(rs.getString("gest_uid"));
        }
    }
}