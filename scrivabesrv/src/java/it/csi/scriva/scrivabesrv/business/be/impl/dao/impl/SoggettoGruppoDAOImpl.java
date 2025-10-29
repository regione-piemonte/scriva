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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoGruppoDAO;
import it.csi.scriva.scrivabesrv.dto.GruppoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoGruppoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoGruppoExtendedDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Soggetto gruppo dao.
 *
 * @author CSI PIEMONTE
 */
public class SoggettoGruppoDAOImpl extends ScrivaBeSrvGenericDAO<SoggettoGruppoDTO> implements SoggettoGruppoDAO {
    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_SOGGETTI_GRUPPO = "SELECT srsg.* FROM _replaceTableName_ srsg\n";

    private static final String QUERY_SOGGETTI_GRUPPO_BY_PK = "SELECT srsg.* FROM _replaceTableName_ srsg\n" +
            "WHERE id_gruppo_soggetto = :idGruppoSoggetto AND id_soggetto_istanza = :idSoggettoIstanza";

    private static final String QUERY_SOGGETTO_GRUPPO = "SELECT srsg.*, srsg.gest_uid AS gest_uid_sg\n" +
            ", stgs.*, stgs.gest_uid AS gest_uid_gs\n" +
            "FROM _replaceTableName_ srsg\n" +
            "INNER JOIN scriva_t_gruppo_soggetto stgs ON srsg.id_gruppo_soggetto = stgs.id_gruppo_soggetto\n";

    private static final String QUERY_SOGGETTO_GRUPPO_BY_ID_SOGGETTO_ISTANZA = QUERY_SOGGETTO_GRUPPO +
            "WHERE srsg.id_soggetto_istanza = :idSoggettoIstanza\n";

    private static final String QUERY_SOGGETTO_GRUPPO_BY_ID_GRUPPO_SOGGETTO = QUERY_SOGGETTO_GRUPPO +
            "WHERE srsg.id_gruppo_soggetto = :idGruppoSoggetto\n";

    private static final String QUERY_INSERT_SOGGETTO_GRUPPO = "INSERT INTO _replaceTableName_\n" +
            "(id_gruppo_soggetto, id_soggetto_istanza, flg_capogruppo, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) VALUES\n" +
            "(:idGruppoSoggetto, :idSoggettoIstanza, :flgCapogruppo, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_INSERT_STORICO_SOGGETTO_GRUPPO = "INSERT INTO scriva_s_soggetto_gruppo\n" +
            "(id_gruppo_soggetto_storico, id_gruppo_soggetto, id_soggetto_istanza, flg_capogruppo, \n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_s_soggetto_gruppo'),id_gruppo_soggetto, id_soggetto_istanza, flg_capogruppo, \n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid\n" +
            "FROM scriva_r_soggetto_gruppo\n" +
            "WHERE gest_uid = :gestUid ";

    private static final String QUERY_UPDATE_SOGGETTO_GRUPPO = "UPDATE scriva_r_soggetto_gruppo SET\n" +
            "flg_capogruppo = :flgCapogruppo, gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd\n" +
            "WHERE id_gruppo_soggetto = :idGruppoSoggetto AND id_soggetto_istanza = :idSoggettoIstanza";

    private static final String QUERY_DELETE_SOGGETTO_GRUPPO_BY_UID = "DELETE FROM _replaceTableName_ WHERE gest_uid = :gestUID";

    /**
     * Load gruppi soggetto list.
     *
     * @return the list
     */
    @Override
    public List<SoggettoGruppoDTO> loadSoggettiGruppo() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_SOGGETTI_GRUPPO, null);
    }

    /**
     * Load soggetti gruppo by pk soggetto gruppo dto.
     *
     * @param idGruppoSoggetto  the id gruppo soggetto
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the soggetto gruppo dto
     */
    @Override
    public SoggettoGruppoDTO loadSoggettiGruppoByPK(Long idGruppoSoggetto, Long idSoggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idGruppoSoggetto", idGruppoSoggetto);
        map.put("idSoggettoIstanza", idSoggettoIstanza);
        return findByPK(className, map);
    }

    /**
     * Load soggetto gruppo by id list.
     *
     * @param idGruppoSoggetto the id soggetto gruppo
     * @return the list
     */
    @Override
    public List<SoggettoGruppoExtendedDTO> loadSoggettoGruppoByIdGruppoSoggetto(Long idGruppoSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idGruppoSoggetto", idGruppoSoggetto);
        return findListByQuery(className, QUERY_SOGGETTO_GRUPPO_BY_ID_GRUPPO_SOGGETTO, map);
    }

    /**
     * Load soggetto gruppo by id soggetto istanza list.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the list
     */
    @Override
    public List<SoggettoGruppoExtendedDTO> loadSoggettoGruppoByIdSoggettoIstanza(Long idSoggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggettoIstanza", idSoggettoIstanza);
        return findListByQuery(className, QUERY_SOGGETTO_GRUPPO_BY_ID_SOGGETTO_ISTANZA, map);
    }

    /**
     * Save soggetto gruppo long.
     *
     * @param dto the gruppo soggetto
     * @return the long
     */
    @Override
    public Long saveSoggettoGruppo(SoggettoGruppoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idGruppoSoggetto", dto.getIdGruppoSoggetto());
            map.put("idSoggettoIstanza", dto.getIdSoggettoIstanza());
            map.put("flgCapogruppo", dto.getFlgCapogruppo() ? 1 : 0);
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdGruppoSoggetto().toString() + dto.getIdSoggettoIstanza() + dto.getFlgCapogruppo() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_SOGGETTO_GRUPPO, null, null), params, keyHolder, new String[]{"id_gruppo_soggetto"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_SOGGETTO_GRUPPO), params);
            }
            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update soggetto gruppo integer.
     *
     * @param dto the gruppo soggetto
     * @return the integer
     */
    @Override
    public Integer updateSoggettoGruppo(SoggettoGruppoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idGruppoSoggetto", dto.getIdGruppoSoggetto());
            map.put("idSoggettoIstanza", dto.getIdSoggettoIstanza());
            SoggettoGruppoDTO soggettoGruppoOld = this.findByPK(className, map);
            if (null == soggettoGruppoOld) {
                logError(className, "Record non trovato con idGruppoSoggetto [" + dto.getIdGruppoSoggetto() + "]");
                return -1;
            }
            int returnValue = 1;
            if (!dto.equals(soggettoGruppoOld)) {
                map.put("flgCapogruppo", dto.getFlgCapogruppo() ? 1 : 0);
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                MapSqlParameterSource params = getParameterValue(map);
                returnValue = template.update(getQuery(QUERY_UPDATE_SOGGETTO_GRUPPO, null, null), params);
                if (returnValue > 0) {
                    Map<String, Object> storicoMap = new HashMap<>();
                    storicoMap.put("gestUid", dto.getGestUID());
                    params = getParameterValue(storicoMap);
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_SOGGETTO_GRUPPO), params);
                }
            }
            return returnValue;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete soggetto gruppo by uid integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteSoggettoGruppoByUid(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_SOGGETTO_GRUPPO_BY_UID, null, null), params);
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
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_SOGGETTI_GRUPPO_BY_PK, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<SoggettoGruppoDTO> getRowMapper() throws SQLException {
        return new SoggettoGruppoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<SoggettoGruppoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new SoggettoGruppoExtendedRowMapper();
    }

    /**
     * The type Soggetto gruppo row mapper.
     */
    public static class SoggettoGruppoRowMapper implements RowMapper<SoggettoGruppoDTO> {

        /**
         * Instantiates a new Recapito alternativo istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public SoggettoGruppoRowMapper() throws SQLException {
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
        public SoggettoGruppoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoggettoGruppoDTO bean = new SoggettoGruppoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, SoggettoGruppoDTO bean) throws SQLException {
            bean.setIdGruppoSoggetto(rs.getLong("id_gruppo_soggetto"));
            bean.setIdSoggettoIstanza(rs.getLong("id_soggetto_istanza"));
            bean.setFlgCapogruppo(rs.getInt("flg_capogruppo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Soggetto gruppo extended row mapper.
     */
    public static class SoggettoGruppoExtendedRowMapper implements RowMapper<SoggettoGruppoExtendedDTO> {

        /**
         * Instantiates a new Recapito alternativo istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public SoggettoGruppoExtendedRowMapper() throws SQLException {
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
        public SoggettoGruppoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoggettoGruppoExtendedDTO bean = new SoggettoGruppoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, SoggettoGruppoExtendedDTO bean) throws SQLException {
            GruppoSoggettoDTO gruppoSoggetto = new GruppoSoggettoDTO();
            populateBeanGruppoSoggetto(rs, gruppoSoggetto);
            bean.setGruppoSoggetto(gruppoSoggetto);

            bean.setIdSoggettoIstanza(rs.getLong("id_soggetto_istanza"));
            bean.setFlgCapogruppo(rs.getInt("flg_capogruppo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUID(rs.getString("gest_uid_sg"));
        }

        private void populateBeanGruppoSoggetto(ResultSet rs, GruppoSoggettoDTO bean) throws SQLException {
            bean.setIdGruppoSoggetto(rs.getLong("id_gruppo_soggetto"));
            bean.setCodGruppoSoggetto(rs.getString("cod_gruppo_soggetto"));
            bean.setDesGruppoSoggetto(rs.getString("des_gruppo_soggetto"));
            bean.setFlgCreazioneAutomatica(rs.getInt("flg_creazione_automatica") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setGestUID(rs.getString("gest_uid_gs"));
        }
    }

}