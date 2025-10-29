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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoCompetenzaDAO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo competenza dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoCompetenzaDAOImpl extends ScrivaBeSrvGenericDAO<TipoCompetenzaDTO> implements TipoCompetenzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_CLAUSE = "ORDER BY ordinamento_tipo_competenza";

    private static final String QUERY_TIPI_COMPETENZA = "SELECT sdtc.*, NULL AS id_categoria_oggetto FROM _replaceTableName_ sdtc ";

    private static final String QUERY_TIPI_COMPETENZA_BY_ID = QUERY_TIPI_COMPETENZA + "WHERE sdtc.id_tipo_competenza = :idTipoCompetenza ";

    private static final String QUERY_TIPI_COMPETENZA_BY_CODE = QUERY_TIPI_COMPETENZA + "WHERE sdtc.cod_tipo_competenza = :codTipoCompetenza ";

    private static final String QUERY_TIPI_COMPETENZA_BY_ID_CATEGORIA_OGGETTO = QUERY_TIPI_COMPETENZA +
            "INNER JOIN scriva_r_tipo_competenza_cat srtcc ON srtcc.id_tipo_competenza = sdtc.id_tipo_competenza " +
            "WHERE srtcc.id_categoria_oggetto = :idCategoriaOggetto ";

    private static final String QUERY_TIPI_COMPETENZA_BY_ID_ADEMPIMENTO = "SELECT sdtc.*, srtcc.id_categoria_oggetto FROM _replaceTableName_ sdtc " +
            "INNER JOIN scriva_r_tipo_competenza_cat srtcc ON srtcc.id_tipo_competenza = sdtc.id_tipo_competenza " +
            "WHERE srtcc.id_categoria_oggetto IN " +
            "(SELECT sdco.id_categoria_oggetto " +
            "FROM scriva_d_categoria_oggetto sdco " +
            "INNER JOIN scriva_r_adempi_categoria_ogg sraco ON sraco.id_categoria_oggetto = sdco.id_categoria_oggetto " +
            "WHERE sraco.id_adempimento = :idAdempimento) ";

    private static final String WHERE_DATA_INIZIO_FINE_VALIDITA  =  " AND (DATE(sraco.data_inizio_validita) <= DATE(NOW()) AND (coalesce(sraco.data_fine_validita, DATE(NOW())))>= DATE(NOW()))\n ";
    
    private static final String QUERY_TIPI_COMPETENZA_BY_ID_ADEMPIMENTO_REV = "SELECT sdtc.*, srtcc.id_categoria_oggetto FROM _replaceTableName_ sdtc " +
            "INNER JOIN scriva_r_tipo_competenza_cat srtcc ON srtcc.id_tipo_competenza = sdtc.id_tipo_competenza " +
            "WHERE srtcc.id_categoria_oggetto IN " +
            "(SELECT sdco.id_categoria_oggetto " +
            "FROM scriva_d_categoria_oggetto sdco " +
            "INNER JOIN scriva_r_adempi_categoria_ogg sraco ON sraco.id_categoria_oggetto = sdco.id_categoria_oggetto " +
            "WHERE sraco.id_adempimento = :idAdempimento " +
            WHERE_DATA_INIZIO_FINE_VALIDITA +
            ")";
    /**
     * @return List<TipoCompetenzaDTO>
     */
    @Override
    public List<TipoCompetenzaDTO> loadTipiCompetenza() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_TIPI_COMPETENZA + ORDER_BY_CLAUSE, null);
    }

    /**
     * @param idTipoCompetenza idTipoCompetenza
     * @return List<TipoCompetenzaDTO>
     */
    @Override
    public List<TipoCompetenzaDTO> loadTipoCompetenzaById(Long idTipoCompetenza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoCompetenza", idTipoCompetenza);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param codTipoCompetenza codTipoCompetenza
     * @return List<TipoCompetenzaDTO>
     */
    @Override
    public List<TipoCompetenzaDTO> loadTipoCompetenzaByCode(String codTipoCompetenza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoCompetenza", codTipoCompetenza);
        return findSimpleDTOListByQuery(className, QUERY_TIPI_COMPETENZA_BY_CODE, map);
    }

    /**
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<TipoCompetenzaDTO>
     */
    @Override
    public List<TipoCompetenzaDTO> loadTipoCompetenzaByIdCategoriaOggetto(Long idCategoriaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCategoriaOggetto", idCategoriaOggetto);
        return findSimpleDTOListByQuery(className, QUERY_TIPI_COMPETENZA_BY_ID_CATEGORIA_OGGETTO + ORDER_BY_CLAUSE, map);
    }

    /**
     * Load tipo competenza by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<TipoCompetenzaDTO> loadTipoCompetenzaByIdAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findSimpleDTOListByQuery(className, QUERY_TIPI_COMPETENZA_BY_ID_ADEMPIMENTO_REV, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_TIPI_COMPETENZA_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoCompetenzaDTO>
     */
    @Override
    public RowMapper<TipoCompetenzaDTO> getRowMapper() throws SQLException {
        return new TipoCompetenzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<TipoCompetenzaDTO> getExtendedRowMapper() throws SQLException {
        return new TipoCompetenzaRowMapper();
    }

    /**
     * The type Tipo competenza row mapper.
     */
    public static class TipoCompetenzaRowMapper implements RowMapper<TipoCompetenzaDTO> {

        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoCompetenzaRowMapper() throws SQLException {
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
        public TipoCompetenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoCompetenzaDTO bean = new TipoCompetenzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        public void populateBean(ResultSet rs, TipoCompetenzaDTO bean) throws SQLException {
            bean.setIdTipoCompetenza(rs.getLong("id_tipo_competenza"));
            bean.setCodTipoCompetenza(rs.getString("cod_tipo_competenza"));
            bean.setDesTipoCompetenza(rs.getString("des_tipo_competenza"));
            bean.setDesTipoCompetenzaEstesa(rs.getString("des_tipo_competenza_estesa"));
            bean.setOrdinamentoTipoCompetenza(rs.getInt("ordinamento_tipo_competenza"));
            bean.setIdCategoriaOggetto(rs.getLong("id_categoria_oggetto"));
        }
    }

}