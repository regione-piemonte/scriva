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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoEventoDAO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo evento dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoEventoDAOImpl extends ScrivaBeSrvGenericDAO<TipoEventoDTO> implements TipoEventoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_CLAUSE = "ORDER BY id_tipo_evento";

    private static final String WHERE_COMPONENTE_APP = "AND sdte.ind_visibile LIKE '%'||:codComponenteApp||'%' \n";

    private static final String QUERY_TIPI_EVENTO = "SELECT sdte.*, sdte.ind_visibile AS ind_visibile_tipo_evento, \n" +
            "sdsi.*, sdsi.ind_visibile AS ind_visibile_stato_istanza \n" +
            "FROM _replaceTableName_ sdte \n" +
            "LEFT JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sdte.id_stato_istanza_evento \n" +
            "WHERE 1 = 1 \n";

    private static final String QUERY_TIPO_EVENTO_BY_ID = QUERY_TIPI_EVENTO + "AND sdte.id_tipo_evento = :idTipoEvento \n";

    private static final String QUERY_TIPO_EVENTO_BY_CODE = QUERY_TIPI_EVENTO + "AND UPPER(sdte.cod_tipo_evento) = UPPER(:codTipoEvento) \n";

    private static final String QUERY_TIPO_EVENTO_BY_ID_STATO_ISTANZA = QUERY_TIPI_EVENTO + "AND sdte.id_stato_istanza_evento = :idStatoIstanza \n";

    @Override
    public List<TipoEventoExtendedDTO> loadTipiEvento() {
        return loadTipiEvento(null);
    }

    @Override
    public List<TipoEventoExtendedDTO> loadTipiEvento(String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = null;
        String query = QUERY_TIPI_EVENTO;
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map = new HashMap<>();
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE_APP;
        }
        return findListByQuery(className, query + ORDER_BY_CLAUSE, map);
    }

    @Override
    public List<TipoEventoExtendedDTO> loadTipoEventoById(Long idTipoEvento, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_TIPO_EVENTO_BY_ID;
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoEvento", idTipoEvento);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE_APP;
        }
        return findListByQuery(className, query + ORDER_BY_CLAUSE, map);
    }

    @Override
    public List<TipoEventoExtendedDTO> loadTipoEventoByCode(String codTipoEvento, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_TIPO_EVENTO_BY_CODE;
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoEvento", codTipoEvento);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE_APP;
        }
        return findListByQuery(className, query + ORDER_BY_CLAUSE, map);
    }

    /**
     * Load tipo evento by id stato istanza list.
     *
     * @param idStatoIstanza   the id stato istanza
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<TipoEventoExtendedDTO> loadTipoEventoByIdStatoIstanza(Long idStatoIstanza, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_TIPO_EVENTO_BY_ID_STATO_ISTANZA;
        Map<String, Object> map = new HashMap<>();
        map.put("idStatoIstanza", idStatoIstanza);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE_APP;
        }
        return findListByQuery(className, query + ORDER_BY_CLAUSE, map);
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
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoEventoDTO> getRowMapper() throws SQLException {
        return new TipoEventoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoEventoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TipoEventoExtendedRowMapper();
    }

    /**
     * The type Tipo evento row mapper.
     */
    public static class TipoEventoRowMapper implements RowMapper<TipoEventoDTO> {

        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoEventoRowMapper() throws SQLException {
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
        public TipoEventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoEventoDTO bean = new TipoEventoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoEventoDTO bean) throws SQLException {
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));
            bean.setIdStatoIstanzaEvento(rs.getLong("id_stato_istanza_evento"));
            bean.setCodTipoEvento(rs.getString("cod_tipo_evento"));
            bean.setDesTipoEvento(rs.getString("des_tipo_evento"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo"));
            bean.setFlgGeneraRicevuta(rs.getInt("flg_genera_ricevuta") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    /**
     * The type Tipo evento extended row mapper.
     */
    public static class TipoEventoExtendedRowMapper implements RowMapper<TipoEventoExtendedDTO> {

        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoEventoExtendedRowMapper() throws SQLException {
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
        public TipoEventoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoEventoExtendedDTO bean = new TipoEventoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoEventoExtendedDTO bean) throws SQLException {
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));

            StatoIstanzaDTO statoIstanza = new StatoIstanzaDTO();
            populateBeanStatoIstanza(rs, statoIstanza);
            bean.setStatoIstanzaEvento(statoIstanza.getIdStatoIstanza() != 0 ? statoIstanza : null);

            bean.setCodTipoEvento(rs.getString("cod_tipo_evento"));
            bean.setDesTipoEvento(rs.getString("des_tipo_evento"));
            //bean.setIndVisibile(rs.getString("ind_visibile_tipo_evento"));
            bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo"));
            bean.setFlgGeneraRicevuta(rs.getInt("flg_genera_ricevuta") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanStatoIstanza(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            //bean.setIndVisibile(rs.getString("ind_visibile_stato_istanza"));
        }

    }

}