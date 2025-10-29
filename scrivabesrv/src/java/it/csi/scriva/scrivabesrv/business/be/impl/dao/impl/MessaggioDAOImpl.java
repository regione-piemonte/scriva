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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.MessaggioDAO;
import it.csi.scriva.scrivabesrv.dto.MessaggioDTO;
import it.csi.scriva.scrivabesrv.dto.MessaggioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoMessaggioDTO;
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
 * The type Messaggio dao.
 *
 * @author CSI PIEMONTE
 */
public class MessaggioDAOImpl extends ScrivaBeSrvGenericDAO<MessaggioDTO> implements MessaggioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "select * from _replaceTableName_ where id_messaggio = :idMessaggio";

    private static final String QUERY_LOAD_MESSAGGI = "select sdm.*, sdm.id_tipo_messaggio as tipo_messaggio_id, sdtm.* "
            + "from _replaceTableName_ sdm  "
            + "inner join scriva_d_tipo_messaggio sdtm on sdm.id_tipo_messaggio = sdtm.id_tipo_messaggio ";

    private static final String QUERY_LOAD_MESSAGGI_BY_CODE_TIPO_MESSAGGIO = "select sdm.*, sdm.id_tipo_messaggio as tipo_messaggio_id, sdtm.* "
            + "from _replaceTableName_ sdm  "
            + "inner join scriva_d_tipo_messaggio sdtm on sdm.id_tipo_messaggio = sdtm.id_tipo_messaggio "
            + "where UPPER(sdtm.cod_tipo_messaggio) = UPPER(:codTipoMessaggio)";

    private static final String QUERY_LOAD_MESSAGGIO = "select sdm.*, sdm.id_tipo_messaggio as tipo_messaggio_id, sdtm.* "
            + "from _replaceTableName_ sdm  "
            + "inner join scriva_d_tipo_messaggio sdtm on sdm.id_tipo_messaggio = sdtm.id_tipo_messaggio "
            + "where sdm.id_messaggio = :idMessaggio";

    private static final String QUERY_LOAD_MESSAGGIO_BY_CODE = "select sdm.*, sdm.id_tipo_messaggio as tipo_messaggio_id, sdtm.* "
            + "from _replaceTableName_ sdm  "
            + "inner join scriva_d_tipo_messaggio sdtm on sdm.id_tipo_messaggio = sdtm.id_tipo_messaggio "
            + "where UPPER(sdm.cod_messaggio) = UPPER(:codMessaggio)";

    private static final String QUERY_INSERT_MESSAGGIO = "INSERT INTO _replaceTableName_ "
            + "(id_messaggio, id_tipo_messaggio, cod_messaggio, des_testo_messaggio, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) "
            + "VALUES(nextval(''), :idTipoMessaggio, :codMessaggio, :desTestoMessaggio, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_MESSAGGIO = "UPDATE _replaceTableName_ "
            + "SET id_tipo_messaggio = :idTipoMessaggio,  cod_messaggio = :codMessaggio, des_testo_messaggio = :desTestoMessaggio, gest_data_ins = :gestDataIns, gest_attore_ins = :gestAttoreIns, gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd, gest_uid = :gestUid "
            + "WHERE id_messaggio = :idMessaggio";

    private static final String QUERY_DELETE_MESSAGGIO = "DELETE FROM _replaceTableName_ WHERE id_messaggio = :idMessaggio ";

    /**
     * Load messaggi list.
     *
     * @return List<MessaggioExtendedDTO> list
     */
    @Override
    public List<MessaggioExtendedDTO> loadMessaggi() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_MESSAGGI, null);
    }

    /**
     * Load messaggio list.
     *
     * @param idMessaggio idMessaggio
     * @return List<MessaggioExtendedDTO> list
     */
    @Override
    public List<MessaggioExtendedDTO> loadMessaggio(Long idMessaggio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idMessaggio", idMessaggio);
        return findListByQuery(className, QUERY_LOAD_MESSAGGIO, map);
    }

    /**
     * Load messaggio by code list.
     *
     * @param codMessaggio codMessaggio
     * @return List<MessaggioExtendedDTO> list
     */
    @Override
    public List<MessaggioExtendedDTO> loadMessaggioByCode(String codMessaggio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codMessaggio", codMessaggio);
        return findListByQuery(className, QUERY_LOAD_MESSAGGIO_BY_CODE, map);
    }

    /**
     * Load messaggi by code tipo messaggio list.
     *
     * @param codTipoMessaggio codTipoMessaggio
     * @return List<MessaggioExtendedDTO> list
     */
    @Override
    public List<MessaggioExtendedDTO> loadMessaggiByCodeTipoMessaggio(String codTipoMessaggio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoMessaggio", codTipoMessaggio);
        return findListByQuery(className, QUERY_LOAD_MESSAGGI_BY_CODE_TIPO_MESSAGGIO, map);
    }

    /**
     * Save messaggio long.
     *
     * @param dto MessaggioDTO
     * @return id del record salvato
     */
    @Override
    public Long saveMessaggio(MessaggioDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("id_tipo_messaggio", dto.getIdTipoMessaggio());
            map.put("cod_messaggio", dto.getCodMessaggio());
            map.put("des_testo_messaggio", dto.getDesTestoMessaggio());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getGestAttoreIns() + dto.getIdTipoMessaggio().toString() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_MESSAGGIO, null, null), params, keyHolder, new String[]{"id_messaggio"});
            Number key = keyHolder.getKey();

            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update messaggio integer.
     *
     * @param dto MessaggioDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateMessaggio(MessaggioDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("id_tipo_messaggio", dto.getIdTipoMessaggio());
            map.put("cod_messaggio", dto.getCodMessaggio());
            map.put("des_testo_messaggio", dto.getDesTestoMessaggio());
            map.put("idMessaggio", dto.getIdMessaggio());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getGestAttoreIns() + dto.getIdTipoMessaggio().toString() + now));
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_MESSAGGIO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete messaggio integer.
     *
     * @param idMessaggio idMessaggio
     * @return numero record cancellati
     */
    @Override
    public Integer deleteMessaggio(Long idMessaggio) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idMessaggio", idMessaggio);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_MESSAGGIO, null, null), params);
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
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<MessaggioDTO>
     */
    @Override
    public RowMapper<MessaggioDTO> getRowMapper() throws SQLException {
        return new MessaggioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<MessaggioExtendedDTO>
     */
    @Override
    public RowMapper<MessaggioExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new MessaggioExtendedRowMapper();
    }

    /**
     * The type Messaggio row mapper.
     */
    public static class MessaggioRowMapper implements RowMapper<MessaggioDTO> {

        /**
         * Instantiates a new Messaggio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public MessaggioRowMapper() throws SQLException {
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
        public MessaggioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MessaggioDTO bean = new MessaggioDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, MessaggioDTO bean) throws SQLException {
            //id_messaggio, id_tipo_messaggio, cod_messaggio, des_testo_messaggio
            bean.setIdMessaggio(rs.getLong("id_messaggio"));
            bean.setIdTipoMessaggio(rs.getLong("id_tipo_messaggio"));
            bean.setCodMessaggio(rs.getString("cod_messaggio"));
            bean.setDesTestoMessaggio(rs.getString("des_testo_messaggio"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Messaggio extended row mapper.
     */
    public static class MessaggioExtendedRowMapper implements RowMapper<MessaggioExtendedDTO> {

        /**
         * Instantiates a new Messaggio extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public MessaggioExtendedRowMapper() throws SQLException {
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
        public MessaggioExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MessaggioExtendedDTO bean = new MessaggioExtendedDTO();
            populateBeanExtended(rs, bean);
            return bean;
        }

        private void populateBeanExtended(ResultSet rs, MessaggioExtendedDTO bean) throws SQLException {
            bean.setIdMessaggio(rs.getLong("id_messaggio"));

            TipoMessaggioDTO tipoMessaggio = new TipoMessaggioDTO();
            populateBeanTipoMessaggio(rs, tipoMessaggio);
            bean.setTipoMessaggio(tipoMessaggio);

            bean.setCodMessaggio(rs.getString("cod_messaggio"));
            bean.setDesTestoMessaggio(rs.getString("des_testo_messaggio"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanTipoMessaggio(ResultSet rs, TipoMessaggioDTO bean) throws SQLException {
            bean.setIdTipoMessaggio(rs.getLong("tipo_messaggio_id"));
            bean.setCodTipoMessaggio(rs.getString("cod_tipo_messaggio"));
            bean.setDesTipoMessaggio(rs.getString("des_tipo_messaggio"));
        }
    }

}