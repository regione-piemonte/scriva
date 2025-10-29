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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilanteDAO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Compilante dao.
 *
 * @author CSI PIEMONTE
 */
public class CompilanteDAOImpl extends ScrivaBeSrvGenericDAO<CompilanteDTO> implements CompilanteDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_COMPILANTI = "select * from _replaceTableName_";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_COMPILANTI + " where id_compilante = :idCompilante";

    private static final String QUERY_LOAD_COMPILANTI_BY_CODICE_FISCALE = QUERY_LOAD_COMPILANTI + " where UPPER(cf_compilante) = UPPER(:cfCompilante)";

    private static final String QUERY_DELETE_COMPILANTE = "delete from _replaceTableName_ where gest_uid = :uid";

    private static final String QUERY_INSERT_COMPILANTE = "INSERT INTO _replaceTableName_ "
            + "(id_compilante, cf_compilante, cognome_compilante, nome_compilante, des_email_compilante, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) "
            + "VALUES(nextval('seq_scriva_t_compilante'), :cfCompilante, :cognomeCompilante, :nomeCompilante, :desEmailCompilante, :dateIns, :attoreIns, :dateUpd, :attoreUpd, :uid)";

    private static final String QUERY_UPDATE_COMPILANTE = "UPDATE _replaceTableName_\n"
            + "SET des_email_compilante = :desEmailCompilante,\n"
            + "gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n "
            + "WHERE cf_compilante = :cfCompilante";

    /**
     * Load compilanti list.
     *
     * @return List<CompilanteDTO> list
     */
    @Override
    public List<CompilanteDTO> loadCompilanti() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_COMPILANTI, null);
    }

    /**
     * Load compilante by codice fiscale list.
     *
     * @param codiceFiscale codiceFiscale
     * @return List<CompilanteDTO> list
     */
    @Override
    public List<CompilanteDTO> loadCompilanteByCodiceFiscale(String codiceFiscale) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfCompilante", codiceFiscale);
        return findListByQuery(className, QUERY_LOAD_COMPILANTI_BY_CODICE_FISCALE, map);
    }

    /**
     * Save compilante long.
     *
     * @param dto CompilanteDTO
     * @return id record inserito
     */
    @Override
    public Long saveCompilante(CompilanteDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();

            Date now = Calendar.getInstance().getTime();
            map.put("cfCompilante", dto.getCodiceFiscaleCompilante());
            map.put("cognomeCompilante", dto.getCognomeCompilante());
            map.put("nomeCompilante", dto.getNomeCompilante());
            map.put("desEmailCompilante", dto.getDesEmailCompilante());
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateIns", now);
            map.put("attoreUpd", dto.getGestAttoreUpd());
            map.put("dateUpd", now);
            map.put("uid", generateGestUID(dto.getCodiceFiscaleCompilante() + now));
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_COMPILANTE, null, null), params, keyHolder, new String[]{"id_compilante"});

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
     * Update compilante integer.
     *
     * @param dto CompilanteDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateCompilante(CompilanteDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            List<CompilanteDTO> compilanteList = this.loadCompilanteByCodiceFiscale(dto.getCodiceFiscaleCompilante());
            if (CollectionUtils.isEmpty(compilanteList)) {
                logError(className, "Record non trovato con codice fiscale [" + dto.getCodiceFiscaleCompilante() + "]");
                return -1;
            }
            map.put("cfCompilante", dto.getCodiceFiscaleCompilante());
            map.put("desEmailCompilante", StringUtils.isNotBlank(dto.getDesEmailCompilante()) ? dto.getDesEmailCompilante() : compilanteList.get(0).getDesEmailCompilante());
            map.put("attoreUpd", dto.getGestAttoreUpd());
            map.put("dateUpd", now);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_COMPILANTE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<CompilanteDTO> list
     */
    @Override
    public List<CompilanteDTO> loadCompilante(Long idCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompilante", idCompilante);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Delete compilante integer.
     *
     * @param uid uid
     * @return numero record cancallati
     */
    @Override
    public Integer deleteCompilante(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_COMPILANTE, null, null), params);
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
     * @return RowMapper<CompilanteDTO>
     */
    @Override
    public RowMapper<CompilanteDTO> getRowMapper() throws SQLException {
        return new CompilanteRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<CompilanteDTO> getExtendedRowMapper() throws SQLException {
        return new CompilanteRowMapper();
    }

    /**
     * The type Compilante row mapper.
     */
    public static class CompilanteRowMapper implements RowMapper<CompilanteDTO> {

        /**
         * Instantiates a new Compilante row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompilanteRowMapper() throws SQLException {
            //Instatiate class
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
        public CompilanteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompilanteDTO bean = new CompilanteDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CompilanteDTO bean) throws SQLException {
            bean.setIdCompilante(rs.getLong("id_compilante"));
            bean.setCodiceFiscaleCompilante(rs.getString("cf_compilante"));
            bean.setCognomeCompilante(rs.getString("cognome_compilante"));
            bean.setNomeCompilante(rs.getString("nome_compilante"));
            bean.setDesEmailCompilante(rs.getString("des_email_compilante"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}