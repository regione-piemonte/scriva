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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.EsitoProcedimentoDAO;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Esito procedimento dao.
 *
 * @author CSI PIEMONTE
 */
public class EsitoProcedimentoDAOImpl extends ScrivaBeSrvGenericDAO<EsitoProcedimentoDTO> implements EsitoProcedimentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_ESITI_PROCEDIMENTO = "SELECT * FROM _replaceTableName_ WHERE 1 = 1\n";

    private static final String WHERE_ID_ESITO = "AND id_esito_procedimento = :idEsitoProcedimento\n";

    private static final String WHERE_COD_ESITO = "AND cod_esito_procedimento = :codEsitoProcedimento\n";

    private static final String ORDER_BY_DES_ESITO = "ORDER BY des_esito_procedimento ASC";

    /**
     * Load esito procedimento list.
     *
     * @param idEsitoProcedimento  the id esito procedimento
     * @param codEsitoProcedimento the cod esito procedimento
     * @return the list
     */
    @Override
    public List<EsitoProcedimentoDTO> loadEsitoProcedimento(Long idEsitoProcedimento, String codEsitoProcedimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_ESITI_PROCEDIMENTO;
        if (idEsitoProcedimento != null) {
            map.put("idEsitoProcedimento", idEsitoProcedimento);
            query += WHERE_ID_ESITO;
        }
        if (StringUtils.isNotBlank(codEsitoProcedimento)) {
            map.put("codEsitoProcedimento", codEsitoProcedimento);
            query += WHERE_COD_ESITO;
        }

        return findListByQuery(className, query + ORDER_BY_DES_ESITO, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_ESITI_PROCEDIMENTO, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<EsitoProcedimentoDTO> getRowMapper() throws SQLException {
        return new EsitoProcedimentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<EsitoProcedimentoDTO> getExtendedRowMapper() throws SQLException {
        return getRowMapper();
    }

    public static class EsitoProcedimentoRowMapper implements RowMapper<EsitoProcedimentoDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public EsitoProcedimentoRowMapper() throws SQLException {
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
        public EsitoProcedimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            EsitoProcedimentoDTO bean = new EsitoProcedimentoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, EsitoProcedimentoDTO bean) throws SQLException {
            bean.setIdEsitoProcedimento(rs.getLong("id_esito_procedimento"));
            bean.setCodEsitoProcedimento(rs.getString("cod_esito_procedimento"));
            bean.setDesEsitoProcedimento(rs.getString("des_esito_procedimento"));
            bean.setFlgPositivo(rs.getInt("flg_positivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

}