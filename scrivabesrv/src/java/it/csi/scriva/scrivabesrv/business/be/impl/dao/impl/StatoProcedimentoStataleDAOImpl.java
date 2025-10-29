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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoProcedimentoStataleDAO;
import it.csi.scriva.scrivabesrv.dto.StatoProcedimentoStataleDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Stato procedimento statale dao.
 *
 * @author CSI PIEMONTE
 */
public class StatoProcedimentoStataleDAOImpl extends ScrivaBeSrvGenericDAO<StatoProcedimentoStataleDTO> implements StatoProcedimentoStataleDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_IND_VISIBILE = "AND ind_visibile LIKE '%'||:codComponenteApp||'%' \n";

    private static final String WHERE_ID_STATO_PROC_STATALE = "AND id_stato_proced_statale = :idStatoProcStatale \n";

    private static final String WHERE_COD_STATO_PROC_STATALE = "AND UPPER(cod_stato_proced_statale) = UPPER(:codStatoProcStatale) \n";

    private static final String QUERY_LOAD_STATI_PROC_STATALE = "SELECT * FROM _replaceTableName_\n" +
            "WHERE 1 =1 \n";

    /**
     * Load stati procedimento statale list.
     *
     * @param codComponenteApp the componente app
     * @return the list
     */
    @Override
    public List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStatale(String codComponenteApp) {
        logBegin(className);
        return loadStatiProcedimentoStatale(null, null, codComponenteApp);
    }

    /**
     * Load stati procedimento statale by id list.
     *
     * @param idStatoProcStatale the id stato proc statale
     * @return the list
     */
    @Override
    public List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStataleById(Long idStatoProcStatale) {
        logBegin(className);
        return loadStatiProcedimentoStatale(idStatoProcStatale, null, null);
    }

    /**
     * Load stati procedimento statale by code list.
     *
     * @param codStatoProcStatale the cod stato proc statale
     * @return the list
     */
    @Override
    public List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStataleByCode(String codStatoProcStatale) {
        logBegin(className);
        return loadStatiProcedimentoStatale(null, codStatoProcStatale, null);
    }

    /**
     * Load stati procedimento statale list.
     *
     * @param idStatoProcStatale  the id stato proc statale
     * @param codStatoProcStatale the cod stato proc statale
     * @param codComponenteApp    the cod componente app
     * @return the list
     */
    public List<StatoProcedimentoStataleDTO> loadStatiProcedimentoStatale(Long idStatoProcStatale, String codStatoProcStatale, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_STATI_PROC_STATALE;
        if (idStatoProcStatale != null) {
            map.put("idStatoProcStatale", idStatoProcStatale);
            query += WHERE_ID_STATO_PROC_STATALE;
        }
        if (StringUtils.isNotBlank(codStatoProcStatale)) {
            map.put("codStatoProcStatale", codStatoProcStatale);
            query += WHERE_COD_STATO_PROC_STATALE;
        }
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_IND_VISIBILE;
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
        return getQuery(QUERY_LOAD_STATI_PROC_STATALE + WHERE_ID_STATO_PROC_STATALE, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<StatoProcedimentoStataleDTO> getRowMapper() throws SQLException {
        return new StatoProcStataleRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<StatoProcedimentoStataleDTO> getExtendedRowMapper() throws SQLException {
        return new StatoProcStataleRowMapper();
    }

    /**
     * The type Stato proc statale row mapper.
     */
    public static class StatoProcStataleRowMapper implements RowMapper<StatoProcedimentoStataleDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public StatoProcStataleRowMapper() throws SQLException {
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
        public StatoProcedimentoStataleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StatoProcedimentoStataleDTO bean = new StatoProcedimentoStataleDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, StatoProcedimentoStataleDTO bean) throws SQLException {
            bean.setIdStatoProcedStatale(rs.getLong("id_stato_proced_statale"));
            bean.setCodStatoProcedStatale(rs.getString("cod_stato_proced_statale"));
            bean.setDesStatoProcedStatale(rs.getString("des_stato_proced_statale"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setDesEstesaStatoProcedStatale(rs.getString("des_estesa_stato_proced_statale"));
            bean.setLabelStatoProcedStatale(rs.getString("label_stato_proced_statale"));
        }
    }

}