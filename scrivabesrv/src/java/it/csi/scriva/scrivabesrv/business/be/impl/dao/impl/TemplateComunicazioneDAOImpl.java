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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TemplateComunicazioneDAO;
import it.csi.scriva.scrivabesrv.dto.TemplateComunicazioneDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Template comunicazione dao.
 *
 * @author CSI PIEMONTE
 */
public class TemplateComunicazioneDAOImpl extends ScrivaBeSrvGenericDAO<TemplateComunicazioneDTO> implements TemplateComunicazioneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_TEMPLATE_BY_CODE = "SELECT * FROM _replaceTableName_ WHERE TRIM(BOTH FROM UPPER(cod_template)) = UPPER(:code)";

    /**
     * Load template by code list.
     *
     * @param code codice template
     * @return List<TemplateComunicazioneDTO> list
     */
    @Override
    public List<TemplateComunicazioneDTO> loadTemplateByCode(String code) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TEMPLATE_BY_CODE, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TemplateComunicazioneDTO>
     */
    @Override
    public RowMapper<TemplateComunicazioneDTO> getRowMapper() throws SQLException {
        return new TemplateComunicazioneRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TemplateComunicazioneDTO>
     */
    @Override
    public RowMapper<TemplateComunicazioneDTO> getExtendedRowMapper() throws SQLException {
        return new TemplateComunicazioneRowMapper();
    }

    /**
     * The type Template comunicazione row mapper.
     */
    public static class TemplateComunicazioneRowMapper implements RowMapper<TemplateComunicazioneDTO> {

        /**
         * Instantiates a new Template comunicazione row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TemplateComunicazioneRowMapper() throws SQLException {
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
        public TemplateComunicazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemplateComunicazioneDTO bean = new TemplateComunicazioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TemplateComunicazioneDTO bean) throws SQLException {
            bean.setIdTemplate(rs.getLong("id_template"));
            bean.setCodTemplate(rs.getString("cod_template"));
            bean.setDesTemplate(rs.getString("des_template"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setDesCorpo(rs.getString("des_corpo"));
        }
    }
}