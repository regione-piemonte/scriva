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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoMessaggioDAO;
import it.csi.scriva.scrivabesrv.dto.TipoMessaggioDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoDTO;

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
 * The type Tipo messaggio dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoMessaggioDAOImpl extends ScrivaBeSrvGenericDAO<TipoMessaggioDTO> implements TipoMessaggioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "select * from _replaceTableName_ where id_tipo_messaggio = :idTipoMessaggio";

    private static final String QUERY_LOAD_TIPI_MESSAGGIO = "select * from _replaceTableName_";

    private static final String QUERY_LOAD_TIPO_MESSAGGIO_BY_CODE = "select * from _replaceTableName_ where cod_tipo_messaggio = :codTipoMessaggio";

    private static final String QUERY_INSERT_TIPO_MESSAGGIO = "INSERT INTO _replaceTableName_ "
            + "(id_tipo_messaggio, cod_tipo_messaggio, des_tipo_messaggio, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) "
            + "VALUES(nextval(''), :codTipoMessaggio, :desTipoMessaggio, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_TIPO_MESSAGGIO = "UPDATE _replaceTableName_ "
            + "SET cod_tipo_messaggio = :codTipoMessaggio, des_tipo_messaggio = :desTipoMessaggio, gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd "
            + "WHERE id_tipo_messaggio = :idTipoMessaggio";

    private static final String QUERY_DELETE_TIPO_MESSAGGIO = "DELETE FROM _replaceTableName_ WHERE id_tipo_messaggio = :idTipoMessaggio ";

    /**
     * Load tipi messaggio list.
     *
     * @return List<TipoMessaggioDTO> list
     */
    @Override
    public List<TipoMessaggioDTO> loadTipiMessaggio() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_MESSAGGIO, null);
    }

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoMessaggio idTipoMessaggio
     * @return List<TipoMessaggioDTO> list
     */
    @Override
    public List<TipoMessaggioDTO> loadTipoMessaggio(Long idTipoMessaggio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoMessaggio", idTipoMessaggio);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoMessaggio codTipoMessaggio
     * @return List<TipoMessaggioDTO> list
     */
    @Override
    public List<TipoMessaggioDTO> loadTipoMessaggioByCode(String codTipoMessaggio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoMessaggio", codTipoMessaggio);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPO_MESSAGGIO_BY_CODE, map);
    }
    
    /**
     * Find by pk tipo messaggio dto.
     *
     * @param idTipoMessaggio idTipoMessaggio
     * @return TipoMessaggioDTO tipo messaggio dto
     */
    @Override
    public TipoMessaggioDTO findByPK(Long idTipoMEssaggio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idUbicaOggetto", idTipoMEssaggio);
        return findByPK(className, map);
    }


    /**
     * Save tipo messaggio long.
     *
     * @param dto TipoMessaggioDTO
     * @return id record salvato
     */
    @Override
    public Long saveTipoMessaggio(TipoMessaggioDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("codTipoMessaggio", dto.getCodTipoMessaggio());
            map.put("desTipoMessaggio", dto.getDesTipoMessaggio());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getGestAttoreIns() + dto.getIdTipoMessaggio().toString() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_TIPO_MESSAGGIO, null, null), params, keyHolder, new String[]{"id_tipo_messaggio"});
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
     * Update tipo messaggio integer.
     *
     * @param dto TipoMessaggioDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateTipoMessaggio(TipoMessaggioDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            
            TipoMessaggioDTO tipoMessaggio = this.findByPK(dto.getIdTipoMessaggio());
            if (null == tipoMessaggio) {
                LOGGER.error("[TipoMessaggioDAOImpl::updateTipoMessaggio] Record non trovato con id [" + dto.getIdTipoMessaggio() + "]");
                return -1;
            }
            
            map.put("codTipoMessaggio", dto.getCodTipoMessaggio());
            map.put("desTipoMessaggio", dto.getDesTipoMessaggio());
            map.put("idTipoMessaggio", dto.getIdTipoMessaggio());
            map.put("gestDataIns", tipoMessaggio.getGestDataIns());
            map.put("gestAttoreIns", tipoMessaggio.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", tipoMessaggio.getGestUID());
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_TIPO_MESSAGGIO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete tipo messaggio integer.
     *
     * @param idTipoMessaggio idTipoMessaggio
     * @return numero record cancellati
     */
    @Override
    public Integer deleteTipoMessaggio(Long idTipoMessaggio) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idTipoMessaggio", idTipoMessaggio);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_INSERT_TIPO_MESSAGGIO, null, null), params);
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
     * @return RowMapper<TipoMessaggioDTO>
     */
    @Override
    public RowMapper<TipoMessaggioDTO> getRowMapper() throws SQLException {
        return new TipoMessaggioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoMessaggioDTO>
     */
    @Override
    public RowMapper<TipoMessaggioDTO> getExtendedRowMapper() throws SQLException {
        return new TipoMessaggioRowMapper();
    }

    /**
     * The type Tipo messaggio row mapper.
     */
    public static class TipoMessaggioRowMapper implements RowMapper<TipoMessaggioDTO> {

        /**
         * Instantiates a new Tipo messaggio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoMessaggioRowMapper() throws SQLException {
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
        public TipoMessaggioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoMessaggioDTO bean = new TipoMessaggioDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoMessaggioDTO bean) throws SQLException {
            bean.setIdTipoMessaggio(rs.getLong("id_tipo_messaggio"));
            bean.setCodTipoMessaggio(rs.getString("cod_tipo_messaggio"));
            bean.setDesTipoMessaggio(rs.getString("des_tipo_messaggio"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));

        }
    }
}