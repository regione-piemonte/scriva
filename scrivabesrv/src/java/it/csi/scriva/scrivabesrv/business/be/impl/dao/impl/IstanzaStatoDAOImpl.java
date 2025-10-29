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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaStatoDAO;

import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaStatoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Istanza stato dao.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaStatoDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaStatoDTO> implements IstanzaStatoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_ISTANZA_STATI = "SELECT sris.*, \n" +
            "sdsi.cod_stato_istanza, sdsi.des_stato_istanza, sdsi.des_estesa_stato_istanza, sdsi.label_stato \n" +
            "FROM _replaceTableName_ sris \n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sris.id_stato_istanza = sdsi.id_stato_istanza";

    private static final String QUERY_LOAD_ISTANZA_STATI_BY_ISTANZA = QUERY_LOAD_ISTANZA_STATI + " WHERE sris.id_istanza = :idIstanza";

    private static final String QUERY_INSERT_ISTANZA_STATO = "INSERT INTO _replaceTableName_ " +
            "(id_istanza, data_cambio_stato, id_stato_istanza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES(:idIstanza, :dataCambioStato, :idStatoIstanza, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUID)";

    /**
     * Load istanza stati list.
     *
     * @return List<IstanzaStatoExtendedDTO> list
     */
    @Override
    public List<IstanzaStatoExtendedDTO> loadIstanzaStati() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_STATI, null);
    }

    /**
     * Load istanza stati by istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaStatoExtendedDTO> list
     */
    @Override
    public List<IstanzaStatoExtendedDTO> loadIstanzaStatiByIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_STATI_BY_ISTANZA, map);
    }

    /**
     * Save istanza stato long.
     *
     * @param dto IstanzaStatoDTO
     * @return id record salvato
     */
    @Override
    public Long saveIstanzaStato(IstanzaStatoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idStatoIstanza", dto.getIdStatoIstanza());
            map.put("dataCambioStato", now);
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            String uid = generateGestUID(String.valueOf(dto.getIdIstanza()) + (dto.getIdStatoIstanza()) + now);
            map.put("gestUID", uid);
            MapSqlParameterSource params = getParameterValue(map);

            return (long) template.update(getQuery(QUERY_INSERT_ISTANZA_STATO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
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
     * @return RowMapper<IstanzaStatoDTO>
     */
    @Override
    public RowMapper<IstanzaStatoDTO> getRowMapper() throws SQLException {
        return new IstanzaStatoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaStatoExtendedDTO>
     */
    @Override
    public RowMapper<IstanzaStatoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaStatoExtendedRowMapper();
    }

    private static class IstanzaStatoRowMapper implements RowMapper<IstanzaStatoDTO> {

        /**
         * Instantiates a new Istanza stato row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaStatoRowMapper() throws SQLException {
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
        public IstanzaStatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaStatoDTO bean = new IstanzaStatoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaStatoDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setDataCambioStato(rs.getTimestamp("data_cambio_stato"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }


    private static class IstanzaStatoExtendedRowMapper implements RowMapper<IstanzaStatoExtendedDTO> {

        /**
         * Instantiates a new Istanza stato extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaStatoExtendedRowMapper() throws SQLException {
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
        public IstanzaStatoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaStatoExtendedDTO bean = new IstanzaStatoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaStatoExtendedDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            StatoIstanzaDTO statoIstanza = new StatoIstanzaDTO();
            populateBeanStatoIstanza(rs, statoIstanza);
            bean.setStatoIstanza(statoIstanza);
            bean.setDataCambioStato(rs.getTimestamp("data_cambio_stato"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanStatoIstanza(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
            bean.setIndRicercaOggetto(rs.getString("ind_ricerca_oggetto"));
            bean.setIndAggiornaOggetto(rs.getString("ind_aggiorna_oggetto"));
            bean.setFlgAggiornaOggetto(rs.getBoolean("flg_aggiorna_oggetto") ? Boolean.TRUE : Boolean.FALSE);
        }
    }
}