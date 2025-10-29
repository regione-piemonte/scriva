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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaAdempimentoDAO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaAdempimentoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Stato istanza adempimento dao.
 */
public class StatoIstanzaAdempimentoDAOImpl extends ScrivaBeSrvGenericDAO<StatoIstanzaAdempimentoDTO> implements StatoIstanzaAdempimentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO = "SELECT * FROM _replaceTableName_ ";

    private static final String QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO = QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO
            + "WHERE id_adempimento = :idAdempimento ";

    private static final String QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_STATO_ISTANZA = QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO
            + "WHERE id_stato_istanza = :idStatoIstanza ";

    private static final String QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO_STATO_ISTANZA = QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO
            + "AND id_stato_istanza = :idStatoIstanza ";

    private static final String QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO_STATO_ISTANZA_NEW_OLD = QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO_STATO_ISTANZA
            + "AND id_stato_ammesso = :idStatoIstanzaAmmesso ";


    /**
     * @return List<StatoIstanzaAdempimentoDTO>
     */
    @Override
    public List<StatoIstanzaAdempimentoDTO> loadStatiIstanzaAdempimento() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO, null);
    }

    /**
     * @param idAdempimento idAdempimento
     * @return List<StatoIstanzaAdempimentoDTO>
     */
    @Override
    public List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO, map);
    }

    /**
     * @param idStatoIstanza idStatoIstanza
     * @return List<StatoIstanzaAdempimentoDTO>
     */
    @Override
    public List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdStatoIstanza(Long idStatoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idStatoIstanza", idStatoIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_STATO_ISTANZA, map);
    }

    /**
     * @param idAdempimento  idAdempimento
     * @param idStatoIstanza idStatoIstanza
     * @return List<StatoIstanzaAdempimentoDTO>
     */
    @Override
    public List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdAdempimentoIdStatoIstanza(Long idAdempimento, Long idStatoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idStatoIstanza", idStatoIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO_STATO_ISTANZA, map);
    }

    /**
     * @param idAdempimento         idAdempimento
     * @param idStatoIstanza        idStatoIstanza
     * @param idStatoIstanzaAmmesso idStatoIstanzaAmmesso
     * @return List<StatoIstanzaAdempimentoDTO>
     */
    @Override
    public List<StatoIstanzaAdempimentoDTO> loadStatoIstanzaAdempimentoByIdAdempimentoIdStatoIstanzaNewOld(Long idAdempimento, Long idStatoIstanza, Long idStatoIstanzaAmmesso) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idStatoIstanza", idStatoIstanza);
        map.put("idStatoIstanzaAmmesso", idStatoIstanzaAmmesso);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_STATO_ISTANZA_ADEMPIMENTO_BY_ID_ADEMPIMENTO_STATO_ISTANZA_NEW_OLD, map);
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
     * @return RowMapper<StatoIstanzaAdempimentoDTO>
     */
    @Override
    public RowMapper<StatoIstanzaAdempimentoDTO> getRowMapper() throws SQLException {
        return new StatoIstanzaAdempimentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<StatoIstanzaAdempimentoDTO> getExtendedRowMapper() throws SQLException {
        return new StatoIstanzaAdempimentoRowMapper();
    }

    /**
     * The type Stato istanza adempimento row mapper.
     */
    public static class StatoIstanzaAdempimentoRowMapper implements RowMapper<StatoIstanzaAdempimentoDTO> {

        /**
         * Instantiates a new Stato istanza adempimento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public StatoIstanzaAdempimentoRowMapper() throws SQLException {
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
        public StatoIstanzaAdempimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StatoIstanzaAdempimentoDTO statoIstanzaAdempimento = new StatoIstanzaAdempimentoDTO();
            populateBean(rs, statoIstanzaAdempimento);
            return statoIstanzaAdempimento;
        }

        private void populateBean(ResultSet rs, StatoIstanzaAdempimentoDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setIdStatoAmmesso(rs.getLong("id_stato_ammesso"));
            bean.setIndModificabile(rs.getString("ind_modificabile"));
        }
    }

}