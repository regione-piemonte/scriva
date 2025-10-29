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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempiEsitoProcedimentoDAO;
import it.csi.scriva.scrivabesrv.dto.AdempiEsitoProcedimentoDTO;
import it.csi.scriva.scrivabesrv.dto.AdempiEsitoProcedimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Adempi esito procedimento dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempiEsitoProcedimentoDAOImpl extends ScrivaBeSrvGenericDAO<AdempiEsitoProcedimentoDTO> implements AdempiEsitoProcedimentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ATTIVI = " AND DATE(data_inizio_validita) <= DATE(NOW()) AND coalesce(DATE(data_fine_validita), DATE(NOW()))>= DATE(NOW())\n";

    private static final String WHERE_ID_ADEMPIMENTO = "AND id_adempimento = :idAdempimento\n";

    private static final String QUERY_LOAD_ADEMPI_ESITO_PROC = "SELECT * FROM _replaceTableName_ sraep\n" +
            "INNER JOIN scriva_d_esito_procedimento sdep ON sdep.id_esito_procedimento = sraep.id_esito_procedimento\n" +
            "WHERE 1 =1 \n";

    private static final String ORDER_BY = " ORDER BY sraep.ordinamento";
    /**
     * Load stati procedimento statale list.
     *
     * @param idAdempimento the id adempimento
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    @Override
    public List<AdempiEsitoProcedimentoExtendedDTO> loadAdempiEsitoProcedimento(Long idAdempimento, boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_ADEMPI_ESITO_PROC;
        if (idAdempimento != null) {
            map.put("idAdempimento", idAdempimento);
            query += WHERE_ID_ADEMPIMENTO;
        }
        if (Boolean.TRUE.equals(flgAttivi)) {
            query += WHERE_ATTIVI;
        }
        query += ORDER_BY;
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
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempiEsitoProcedimentoDTO> getRowMapper() throws SQLException {
        return new AdempiEsitoProcedimentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempiEsitoProcedimentoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new AdempiEsitoProcedimentoExtendedRowMapper();
    }

    /**
     * The type Adempi esito procedimento row mapper.
     */
    public static class AdempiEsitoProcedimentoRowMapper implements RowMapper<AdempiEsitoProcedimentoDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempiEsitoProcedimentoRowMapper() throws SQLException {
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
        public AdempiEsitoProcedimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempiEsitoProcedimentoDTO bean = new AdempiEsitoProcedimentoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempiEsitoProcedimentoDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setIdEsitoProcedimento(rs.getLong("id_esito_procedimento"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndEsito(rs.getString("ind_esito"));
        }
    }

    /**
     * The type Adempi esito procedimento extended row mapper.
     */
    public static class AdempiEsitoProcedimentoExtendedRowMapper implements RowMapper<AdempiEsitoProcedimentoExtendedDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempiEsitoProcedimentoExtendedRowMapper() throws SQLException {
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
        public AdempiEsitoProcedimentoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempiEsitoProcedimentoExtendedDTO bean = new AdempiEsitoProcedimentoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempiEsitoProcedimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));

            EsitoProcedimentoDTO esitoProcedimento = new EsitoProcedimentoDTO();
            populateBeanEsitoProcedimento(rs, esitoProcedimento);
            bean.setEsitoProcedimento(esitoProcedimento);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndEsito(rs.getString("ind_esito"));
        }

        private void populateBeanEsitoProcedimento(ResultSet rs, EsitoProcedimentoDTO esitoProcedimento) throws SQLException {
            esitoProcedimento.setIdEsitoProcedimento(rs.getLong("id_esito_procedimento"));
            esitoProcedimento.setCodEsitoProcedimento(rs.getString("cod_esito_procedimento"));
            esitoProcedimento.setDesEsitoProcedimento(rs.getString("des_esito_procedimento"));
            esitoProcedimento.setFlgPositivo(rs.getInt("flg_positivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

    }

}