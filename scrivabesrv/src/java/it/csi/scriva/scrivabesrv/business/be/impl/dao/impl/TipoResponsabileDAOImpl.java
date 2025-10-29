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

import java.sql.ResultSet;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoResponsabileDAO;
import it.csi.scriva.scrivabesrv.dto.TipoResponsabileDTO;

/**
 * The type Istanza responsabile dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoResponsabileDAOImpl extends ScrivaBeSrvGenericDAO<TipoResponsabileDTO> implements TipoResponsabileDAO {

    private final String className = this.getClass().getSimpleName();

    

    private static final String QUERY_LOAD_TIPO_RESPONSABILE = "SELECT sdtr.* \n" +
            "FROM _replaceTableName_ sdtr \n";
//            "INNER JOIN scriva_d_tipo_responsabile sdtr ON srir.id_tipo_responsabile = sdtr.id_tipo_responsabile";
    

    private static final String QUERY_LOAD_TIPO_RESPONSABILE_BY_COD_TIPO_RESPONSABILE = QUERY_LOAD_TIPO_RESPONSABILE +
            "where sdtr.cod_tipo_responsabile in (:codTipoResponsabile) \n";
  
    
    /**
     * Load istanza responsabili by istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaResponsabileDTO> list
     */
    @Override
    public List<TipoResponsabileDTO> loadTipoResponsabile(Long idIstanza, List<String> codTipoResponsabile) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        if(idIstanza != null && codTipoResponsabile != null) {
        	map.put("codTipoResponsabile", codTipoResponsabile);
        	
        	return findListByQuery(className, QUERY_LOAD_TIPO_RESPONSABILE_BY_COD_TIPO_RESPONSABILE, map);
        	
        }
        else {
        	return findListByQuery(className, QUERY_LOAD_TIPO_RESPONSABILE, null);
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
     * @return RowMapper<TipoResponsabileDTO>
     */
    @Override
    public RowMapper<TipoResponsabileDTO> getRowMapper() throws SQLException {
        return new TipoResponsabileRowMapper();
    }

    private static class TipoResponsabileRowMapper implements RowMapper<TipoResponsabileDTO> {

        /**
         * Instantiates a new Tipo responsabile row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoResponsabileRowMapper() throws SQLException {
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

        public TipoResponsabileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TipoResponsabileDTO bean = new TipoResponsabileDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoResponsabileDTO bean) throws SQLException {
            bean.setIdTipoResponsabile(rs.getLong("id_tipo_responsabile"));
            bean.setCodiceTipoResponsabile(rs.getString("cod_tipo_responsabile"));
            bean.setDescrizioneTipoResponsabile(rs.getString("des_tipo_responsabile"));
            
        }
    }

	@Override
	public RowMapper<TipoResponsabileDTO> getExtendedRowMapper() throws SQLException {
		// TODO Auto-generated method stub
		return new TipoResponsabileExtendedRowMapper();
	}
	
	 private static class TipoResponsabileExtendedRowMapper implements RowMapper<TipoResponsabileDTO> {

	        /**
	         * Instantiates a new Tipo responsabile row mapper.
	         *
	         * @throws SQLException the sql exception
	         */
	        public TipoResponsabileExtendedRowMapper() throws SQLException {
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

	        public TipoResponsabileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	TipoResponsabileDTO bean = new TipoResponsabileDTO();
	            populateBean(rs, bean);
	            return bean;
	        }

	        private void populateBean(ResultSet rs, TipoResponsabileDTO bean) throws SQLException {
	            bean.setIdTipoResponsabile(rs.getLong("id_tipo_responsabile"));
	            bean.setCodiceTipoResponsabile(rs.getString("cod_tipo_responsabile"));
	            bean.setDescrizioneTipoResponsabile(rs.getString("des_tipo_responsabile"));
	            
	        }
	    }



}