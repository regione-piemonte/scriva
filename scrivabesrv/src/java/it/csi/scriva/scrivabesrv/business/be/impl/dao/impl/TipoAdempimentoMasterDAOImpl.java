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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoMasterDAO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoMasterDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoMasterSlimDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo adempimento master dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoAdempimentoMasterDAOImpl extends ScrivaBeSrvGenericDAO<TipoAdempimentoMasterDTO> implements TipoAdempimentoMasterDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_CLAUSE = "ORDER BY srtam.ordinamento";

    private static final String QUERY_SELECT_MASTERDATA = "SELECT srtam.ordinamento, sdm.cod_masterdata, sdm.id_masterdata " +
            "FROM _replaceTableName_ srtam " +
            "INNER JOIN scriva_d_masterdata sdm on srtam.id_masterdata = sdm.id_masterdata " +
            "INNER JOIN scriva_d_informazioni_scriva sdis on srtam.id_informazione_scriva = sdis.id_informazione_scriva ";

    private static final String QUERY_SELECT_MASTERDATA_ORDER_CALL = QUERY_SELECT_MASTERDATA +
            "WHERE srtam.id_tipo_adempimento = :idTipoAdempimento AND UPPER(sdis.cod_informazione_scriva) = UPPER(:tipoInfo) " +
            ORDER_BY_CLAUSE;

    private static final String QUERY_SELECT_MASTERDATA_ORDER_CALL_BY_CODE = QUERY_SELECT_MASTERDATA +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON srtam.id_tipo_adempimento = sdta.id_tipo_adempimento " +
            "WHERE UPPER(sdta.cod_tipo_adempimento) = UPPER(:codTipoAdempimento) AND UPPER(sdis.cod_informazione_scriva) = UPPER(:tipoInfo) " +
            ORDER_BY_CLAUSE;

    /**
     * Gets masterdata order call.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @param tipoInfo          tipoInfo
     * @return List<TipoAdempimentoMasterSlimDTO> masterdata order call
     */
    @Override
    public List<TipoAdempimentoMasterSlimDTO> getMasterdataOrderCall(Long idTipoAdempimento, String tipoInfo) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idTipoAdempimento", idTipoAdempimento);
            map.put("tipoInfo", tipoInfo);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_SELECT_MASTERDATA_ORDER_CALL, null, null), params, getSlimRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets masterdata order call by code tipo adempimento.
     *
     * @param codTipoAdempimento codTipoAdempimento
     * @param tipoInfo           tipoInfo
     * @return List<TipoAdempimentoMasterSlimDTO> masterdata order call by code tipo adempimento
     */
    @Override
    public List<TipoAdempimentoMasterSlimDTO> getMasterdataOrderCallByCodeTipoAdempimento(String codTipoAdempimento, String tipoInfo) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("codTipoAdempimento", codTipoAdempimento);
            map.put("tipoInfo", tipoInfo);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_SELECT_MASTERDATA_ORDER_CALL_BY_CODE, null, null), params, getSlimRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
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
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoAdempimentoMasterDTO>
     */
    @Override
    public RowMapper<TipoAdempimentoMasterDTO> getRowMapper() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoAdempimentoMasterSlimDTO>
     */
    @Override
    public RowMapper<TipoAdempimentoMasterSlimDTO> getExtendedRowMapper() throws SQLException {
        return new TipoAdempimentoMasterSlimRowMapper();
    }

    /**
     * Gets slim row mapper.
     *
     * @return the slim row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<TipoAdempimentoMasterSlimDTO> getSlimRowMapper() throws SQLException {
        return new TipoAdempimentoMasterSlimRowMapper();
    }

    /**
     * The type Tipo adempimento master slim row mapper.
     */
    public static class TipoAdempimentoMasterSlimRowMapper implements RowMapper<TipoAdempimentoMasterSlimDTO> {

        /**
         * Instantiates a new Tipo adempimento master slim row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAdempimentoMasterSlimRowMapper() throws SQLException {
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
        public TipoAdempimentoMasterSlimDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAdempimentoMasterSlimDTO bean = new TipoAdempimentoMasterSlimDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoAdempimentoMasterSlimDTO bean) throws SQLException {
            bean.setIdMasterData(rs.getLong("id_masterdata"));
            bean.setOrdinamento(rs.getInt("ordinamento"));
            bean.setCodMasterData(rs.getString("cod_masterdata"));

        }
    }


}