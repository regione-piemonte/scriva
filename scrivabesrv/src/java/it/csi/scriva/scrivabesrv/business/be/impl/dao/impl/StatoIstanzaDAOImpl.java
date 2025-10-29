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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Stato istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class StatoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<StatoIstanzaDTO> implements StatoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_ID = "ORDER BY id_stato_istanza";

    private static final String QUERY_LOAD_STATI_ISTANZA = "SELECT * FROM _replaceTableName_\n";

    private static final String WHERE_IND_VISIBILE= " where ind_visibile like '%'||:visibile||'%' ";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_STATI_ISTANZA + "WHERE id_stato_istanza = :id\n";

    private static final String QUERY_LOAD_STATI_ISTANZA_BY_CODICE = QUERY_LOAD_STATI_ISTANZA + "WHERE UPPER(cod_stato_istanza) = UPPER(:codice)\n";

    private static final String QUERY_LOAD_STATI_ISTANZA_BY_COMP_APP = QUERY_LOAD_STATI_ISTANZA + "WHERE ind_visibile LIKE '%'||:componenteApp||'%'\n";

    /**
     * Load stati istanza list.
     *
     * @return List<StatoIstanzaDTO> list
     */
    @Override
    public List<StatoIstanzaDTO> loadStatiIstanza(Boolean visibile,AttoreScriva attoreScriva) {
        logBegin(className);
        String query="";
        Map<String, Object> map = null;
        if (Boolean.TRUE.equals(visibile)) {
        	map = new HashMap<>();
        	map.put("visibile", attoreScriva.getComponente());
        	query = QUERY_LOAD_STATI_ISTANZA +  WHERE_IND_VISIBILE  + ORDER_BY_ID;
        }else {
        	query = QUERY_LOAD_STATI_ISTANZA  + ORDER_BY_ID;
        }
        return findSimpleDTOListByQuery(className, query, map);
    }

    /**
     * Load stato istanza by codice stato istanza dto.
     *
     * @param codice codice
     * @return StatoIstanzaDTO stato istanza dto
     */
    @Override
    public StatoIstanzaDTO loadStatoIstanzaByCodice(String codice) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("codice", codice);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(getQuery(QUERY_LOAD_STATI_ISTANZA_BY_CODICE, null, null), params, getRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load stato istanza stato istanza dto.
     *
     * @param id idStatoIstanza
     * @return StatoIstanzaDTO stato istanza dto
     */
    @Override
    public StatoIstanzaDTO loadStatoIstanza(Long id) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return findByPK(className, map);
    }

    /**
     * Save stato istanza.
     *
     * @param dto StatoIstanzaDTO
     */
    @Override
    public void saveStatoIstanza(StatoIstanzaDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Update stato istanza.
     *
     * @param dto StatoIstanzaDTO
     */
    @Override
    public void updateStatoIstanza(StatoIstanzaDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Delete stato istanza.
     *
     * @param id idStatoIstanza
     */
    @Override
    public void deleteStatoIstanza(Long id) {
        // TODO Auto-generated method stub
    }

    /**
     * Load stati istanza by componente app list.
     *
     * @param componenteApp the componente app
     * @return the list
     */
    @Override
    public List<StatoIstanzaDTO> loadStatiIstanzaByComponenteApp(String componenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("componenteApp", componenteApp);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_STATI_ISTANZA_BY_COMP_APP + ORDER_BY_ID, map);
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
     * @return RowMapper<StatoIstanzaDTO>
     */
    @Override
    public RowMapper<StatoIstanzaDTO> getRowMapper() {
        return new StatoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<StatoIstanzaDTO>
     */
    @Override
    public RowMapper<StatoIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new StatoIstanzaRowMapper();
    }

    /**
     * The type Stato istanza row mapper.
     */
    public static class StatoIstanzaRowMapper implements RowMapper<StatoIstanzaDTO> {

        /**
         * Instantiates a new Stato istanza row mapper.
         */
        public StatoIstanzaRowMapper() {
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
        public StatoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StatoIstanzaDTO bean = new StatoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setFlgStoricoIstanza(rs.getInt("flg_storico_istanza") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
            bean.setPswRuolo(rs.getString("psw_ruolo"));
            bean.setIndRicercaOggetto(rs.getString("ind_ricerca_oggetto"));
            bean.setIndAggiornaOggetto(rs.getString("ind_aggiorna_oggetto"));
            bean.setFlgAggiornaOggetto(rs.getBoolean("flg_aggiorna_oggetto") ? Boolean.TRUE : Boolean.FALSE);
        }
    }


}