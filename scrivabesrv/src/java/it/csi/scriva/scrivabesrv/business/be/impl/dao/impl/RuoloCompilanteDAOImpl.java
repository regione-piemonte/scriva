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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.RuoloCompilanteDAO;
import it.csi.scriva.scrivabesrv.dto.RuoloCompilanteDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ruolo compilante dao.
 *
 * @author CSI PIEMONTE
 */
public class RuoloCompilanteDAOImpl extends ScrivaBeSrvGenericDAO<RuoloCompilanteDTO> implements RuoloCompilanteDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_RUOLI_COMPILANTE = "SELECT * FROM _replaceTableName_\n";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_RUOLI_COMPILANTE + "WHERE id_ruolo_compilante = :idRuoloCompilante";

    private static final String QUERY_LOAD_RUOLI_COMPILANTE_BY_CODE = QUERY_LOAD_RUOLI_COMPILANTE + "WHERE UPPER(cod_ruolo_compilante) = UPPER(:codRuoloCompilante)";

    private static final String QUERY_LOAD_RUOLO_COMPILANTE = "select sdrc.* \n" + 
    		"from scriva_r_adempi_ruolo_compila srarc \n" + 
    		"inner join scriva_d_ruolo_compilante sdrc on sdrc.id_ruolo_compilante = srarc.id_ruolo_compilante \n" + 
    		"where id_adempimento in (select id_adempimento from scriva_t_istanza \n" + 
    		"where id_istanza = :idIstanza) \n" + 
    		"and flg_ruolo_default =1";
    
    
    private static final String FO = "and (IND_VISIBILE IS NULL OR IND_VISIBILE = '')";
    
    private static final String BO = "and IND_VISIBILE like '%BO%'";
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(srarc.data_inizio_validita) <= DATE(NOW()) AND (srarc.data_fine_validita IS NULL OR DATE(srarc.data_fine_validita)>= DATE(NOW())))\n ";

    private static final String ORDER_BY = " ORDER BY srarc.ordinamento";
    
    /**
     * Load ruoli compilante list.
     *
     * @return List<RuoloCompilanteDTO> list
     */
    @Override
    public List<RuoloCompilanteDTO> loadRuoliCompilante() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_RUOLI_COMPILANTE, null);
    }

    /**
     * Load ruolo compilante list.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<RuoloCompilanteDTO> list
     */
    @Override
    public List<RuoloCompilanteDTO> loadRuoloCompilante(Long idRuoloCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRuoloCompilante", idRuoloCompilante);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load ruolo compilante by code list.
     *
     * @param codRuoloCompilante codRuoloCompilante
     * @return List<RuoloCompilanteDTO> list
     */
    @Override
    public List<RuoloCompilanteDTO> loadRuoloCompilanteByCode(String codRuoloCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codRuoloCompilante", codRuoloCompilante);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_RUOLI_COMPILANTE_BY_CODE, map);
    }

    /**
     * Save ruolo compilante.
     *
     * @param dto RuoloCompilanteDTO
     */
    @Override
    public void saveRuoloCompilante(RuoloCompilanteDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Update ruolo compilante.
     *
     * @param dto RuoloCompilanteDTO
     */
    @Override
    public void updateRuoloCompilante(RuoloCompilanteDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Delete ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     */
    @Override
    public void deleteRuoloCompilante(Long idRuoloCompilante) {
        // TODO Auto-generated method stub
    }
    
    /**
     * Load ruolo compilante.
     *
     * @return RuoloCompilanteDTO
     */
    @Override
    public List<RuoloCompilanteDTO> loadRuoloCompilanteByAttore(String componenteAttore, Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        List<RuoloCompilanteDTO> ruoloComp = new ArrayList<RuoloCompilanteDTO>();
        if(componenteAttore.equals("BO")) 
        	ruoloComp =  findSimpleDTOListByQuery(className, QUERY_LOAD_RUOLO_COMPILANTE + BO + WHERE_DATA_INIZIO_FINE + ORDER_BY, map);
        else if(componenteAttore.equals("FO"))
        	ruoloComp =  findSimpleDTOListByQuery(className, QUERY_LOAD_RUOLO_COMPILANTE + FO + WHERE_DATA_INIZIO_FINE + ORDER_BY, map);
		return ruoloComp;
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
     * @return RowMapper<RuoloCompilanteDTO>
     */
    @Override
    public RowMapper<RuoloCompilanteDTO> getRowMapper() throws SQLException {
        return new RuoloCompilanteRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<RuoloCompilanteDTO>
     */
    @Override
    public RowMapper<RuoloCompilanteDTO> getExtendedRowMapper() throws SQLException {
        return new RuoloCompilanteRowMapper();
    }

    /**
     * The type Ruolo compilante row mapper.
     */
    public static class RuoloCompilanteRowMapper implements RowMapper<RuoloCompilanteDTO> {

        /**
         * Instantiates a new Ruolo compilante row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RuoloCompilanteRowMapper() throws SQLException {
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
        public RuoloCompilanteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RuoloCompilanteDTO bean = new RuoloCompilanteDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RuoloCompilanteDTO bean) throws SQLException {
            bean.setIdRuoloCompilante(rs.getLong("id_ruolo_compilante"));
            bean.setCodiceRuoloCompilante(rs.getString("cod_ruolo_compilante"));
            bean.setDescrizioneRuoloCompilante(rs.getString("des_ruolo_compilante"));
        }
    }


}