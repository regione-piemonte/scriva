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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.RuoloSoggettoDAO;
import it.csi.scriva.scrivabesrv.dto.RuoloSoggettoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ruolo soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class RuoloSoggettoDAOImpl extends ScrivaBeSrvGenericDAO<RuoloSoggettoDTO> implements RuoloSoggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_ruolo_soggetto = :idRuoloSoggetto";

    private static final String QUERY_LOAD_RUOLI_SOGGETTO_BY_CODE = "SELECT * FROM _replaceTableName_ WHERE UPPER(cod_ruolo_soggetto) = UPPER(:codRuoloSoggetto)";

    private static final String QUERY_LOAD_RUOLI_SOGGETTO = "SELECT * FROM _replaceTableName_ ORDER BY cod_ruolo_soggetto";

//    private static final String QUERY_LOAD_RUOLI_SOGGETTO_BY_RUOLO_COMPILANTE_AND_ADEMPIMENTO = "SELECT sdrs.* FROM _replaceTableName_ sdrs "
//            + "INNER JOIN scriva_r_configura_ruolo_sogg srcrs ON sdrs.id_ruolo_soggetto = srcrs.id_ruolo_soggetto "
//            + "WHERE srcrs.id_ruolo_compilante = :idRuoloCompilante "
//            + "AND srcrs.id_adempimento = :idAdempimento "
//            + "AND (DATE(srcrs.data_inizio_validita) <= DATE(NOW()) AND (srcrs.data_fine_validita IS NULL OR DATE(srcrs.data_fine_validita)>= DATE(NOW()))) ";
    //SCRIVA 960 aggiunta tabella scriva_r_adempi_ruolo_sogg
    private static final String QUERY_LOAD_RUOLI_SOGGETTO_BY_RUOLO_COMPILANTE_AND_ADEMPIMENTO = "SELECT sdrs.* FROM _replaceTableName_ sdrs " +
    		"             INNER JOIN scriva_r_configura_ruolo_sogg srcrs ON sdrs.id_ruolo_soggetto = srcrs.id_ruolo_soggetto " +
    		"             INNER JOIN scriva_r_adempi_ruolo_sogg srars  on srars.id_ruolo_soggetto = srcrs.id_ruolo_soggetto  and srars.id_adempimento  = srcrs.id_adempimento " +
    		"             WHERE srcrs.id_ruolo_compilante = :idRuoloCompilante " +
    		"             AND srcrs.id_adempimento = :idAdempimento " +
    		"             AND (DATE(srcrs.data_inizio_validita) <= DATE(NOW()) AND (srcrs.data_fine_validita IS NULL OR DATE(srcrs.data_fine_validita)>= DATE(NOW()))) " +
    		"             order by srars.ordinamento ";
    /**
     * Load ruoli soggetto list.
     *
     * @return List<RuoloSoggettoDTO> list
     */
    @Override
    public List<RuoloSoggettoDTO> loadRuoliSoggetto() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_RUOLI_SOGGETTO, null);
    }

    /**
     * Load ruolo soggetto list.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     * @return List<RuoloSoggettoDTO> list
     */
    @Override
    public List<RuoloSoggettoDTO> loadRuoloSoggetto(Long idRuoloSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRuoloSoggetto", idRuoloSoggetto);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load ruolo soggetto by code list.
     *
     * @param codRuoloSoggetto codRuoloSoggetto
     * @return List<RuoloSoggettoDTO> list
     */
    @Override
    public List<RuoloSoggettoDTO> loadRuoloSoggettoByCode(String codRuoloSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codRuoloSoggetto", codRuoloSoggetto);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_RUOLI_SOGGETTO_BY_CODE, map);
    }

    /**
     * Load ruoli soggetto by id ruolo compilante and adempimento list.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param idAdempimento     idAdempimento
     * @return List<RuoloSoggettoDTO> list
     */
    @Override
    public List<RuoloSoggettoDTO> loadRuoliSoggettoByIdRuoloCompilanteAndAdempimento(Long idRuoloCompilante, Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRuoloCompilante", idRuoloCompilante);
        map.put("idAdempimento", idAdempimento);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_RUOLI_SOGGETTO_BY_RUOLO_COMPILANTE_AND_ADEMPIMENTO, map);
    }

    /**
     * Save ruolo soggetto.
     *
     * @param dto RuoloSoggettoDTO
     */
    @Override
    public void saveRuoloSoggetto(RuoloSoggettoDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Update ruolo soggetto.
     *
     * @param dto RuoloSoggettoDTO
     */
    @Override
    public void updateRuoloSoggetto(RuoloSoggettoDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Delete ruolo soggetto.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     */
    @Override
    public void deleteRuoloSoggetto(Long idRuoloSoggetto) {
        // TODO Auto-generated method stub
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<RuoloSoggettoDTO>
     */
    @Override
    public RowMapper<RuoloSoggettoDTO> getRowMapper() throws SQLException {
        return new RuoloSoggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<RuoloSoggettoDTO>
     */
    @Override
    public RowMapper<RuoloSoggettoDTO> getExtendedRowMapper() throws SQLException {
        return new RuoloSoggettoRowMapper();
    }

    /**
     * The type Ruolo soggetto row mapper.
     */
    public static class RuoloSoggettoRowMapper implements RowMapper<RuoloSoggettoDTO> {

        /**
         * Instantiates a new Ruolo soggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RuoloSoggettoRowMapper() throws SQLException {
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
        public RuoloSoggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RuoloSoggettoDTO bean = new RuoloSoggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RuoloSoggettoDTO bean) throws SQLException {
            bean.setIdRuoloSoggetto(rs.getLong("id_ruolo_soggetto"));
            bean.setCodiceRuoloSoggetto(rs.getString("cod_ruolo_soggetto"));
            bean.setDescrizioneRuoloSoggetto(rs.getString("des_ruolo_soggetto"));
        }
    }


}