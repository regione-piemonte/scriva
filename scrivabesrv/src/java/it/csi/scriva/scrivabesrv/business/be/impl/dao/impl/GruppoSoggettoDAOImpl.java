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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.GruppoSoggettoDAO;
import it.csi.scriva.scrivabesrv.dto.GruppoSoggettoDTO;
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
 * The type Gruppo soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class GruppoSoggettoDAOImpl extends ScrivaBeSrvGenericDAO<GruppoSoggettoDTO> implements GruppoSoggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_GRUPPI_SOGGETTO = "SELECT stgs.* FROM _replaceTableName_ stgs\n";

    private static final String QUERY_GRUPPO_SOGGETTO_BY_ID = QUERY_GRUPPI_SOGGETTO + "WHERE stgs.id_gruppo_soggetto = :idGruppoSoggetto\n";

    private static final String QUERY_GRUPPO_SOGGETTO_BY_ID_SOGGETTO_ISTANZA = QUERY_GRUPPI_SOGGETTO +
            "INNER JOIN scriva_r_soggetto_gruppo srsg ON srsg.id_gruppo_soggetto = stgs.id_gruppo_soggetto\n" +
            "WHERE srsg.id_soggetto_istanza = :idSoggettoIstanza\n";

    private static final String QUERY_INSERT_GRUPPO_SOGGETTO = "INSERT INTO _replaceTableName_\n " +
            "(id_gruppo_soggetto, cod_gruppo_soggetto, des_gruppo_soggetto, flg_creazione_automatica, id_istanza_attore, id_funzionario,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) VALUES\n" +
            "(nextval('seq_scriva_t_gruppo_soggetto'), 'GRP' || nextval('seq_scriva_cod_gruppo_soggetto'), :desGruppoSoggetto, :flgCreazioneAutomatica, :idIstanzaAttore, :idFunzionario,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_INSERT_STORICO_GRUPPO_SOGGETTO = "INSERT INTO _replaceTableName_\n" +
            "(id_gruppo_soggetto_storico, id_gruppo_soggetto, cod_gruppo_soggetto, des_gruppo_soggetto, \n" +
            "flg_creazione_automatica, id_istanza_attore, gest_data_ins, gest_attore_ins, gest_data_upd, \n" +
            "gest_attore_upd, gest_uid, id_funzionario, data_aggiornamento)\n" +
            "SELECT nextval('seq_scriva_s_gruppo_soggetto'), id_gruppo_soggetto, cod_gruppo_soggetto, des_gruppo_soggetto, \n" +
            "flg_creazione_automatica, id_istanza_attore, gest_data_ins, gest_attore_ins, gest_data_upd, \n" +
            "gest_attore_upd, gest_uid, id_funzionario, data_aggiornamento\n" +
            "FROM scriva_t_gruppo_soggetto\n" +
            "WHERE gest_uid = :gestUid ";

    private static final String QUERY_UPDATE_GRUPPO_SOGGETTO = "UPDATE _replaceTableName_ SET\n" +
            "des_gruppo_soggetto = :desGruppoSoggetto, flg_creazione_automatica = :flgCreazioneAutomatica, id_istanza_attore = :idIstanzaAttore, id_funzionario = :idFunzionario,\n" +
            "gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd\n" +
            "WHERE id_gruppo_soggetto = :idGruppoSoggetto\n";

    private static final String QUERY_DELETE_GRUPPO_SOGGETTO_BY_UID = "DELETE FROM _replaceTableName_ WHERE gest_uid = :gestUID";

    /**
     * Load gruppi soggetto list.
     *
     * @return the list
     */
    @Override
    public List<GruppoSoggettoDTO> loadGruppiSoggetto() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_GRUPPI_SOGGETTO, null);
    }

    /**
     * Load gruppo soggetto by id list.
     *
     * @param idGruppoSoggetto the id gruppo soggetto
     * @return the list
     */
    @Override
    public List<GruppoSoggettoDTO> loadGruppoSoggettoById(Long idGruppoSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idGruppoSoggetto", idGruppoSoggetto);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load gruppo soggetto by id soggetto istanza list.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the list
     */
    @Override
    public List<GruppoSoggettoDTO> loadGruppoSoggettoByIdSoggettoIstanza(Long idSoggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggettoIstanza", idSoggettoIstanza);
        return findSimpleDTOListByQuery(className, QUERY_GRUPPO_SOGGETTO_BY_ID_SOGGETTO_ISTANZA, map);
    }

    /**
     * Save gruppo soggetto long.
     *
     * @param dto the gruppo soggetto
     * @return the long
     */
    @Override
    public Long saveGruppoSoggetto(GruppoSoggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("desGruppoSoggetto", dto.getDesGruppoSoggetto());
            map.put("flgCreazioneAutomatica", dto.getFlgCreazioneAutomatica() ? 1 : 0);
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
            map.put("idFunzionario", dto.getIdFunzionario());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getCodGruppoSoggetto() + dto.getDesGruppoSoggetto() + dto.getFlgCreazioneAutomatica() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_GRUPPO_SOGGETTO, null, null), params, keyHolder, new String[]{"id_gruppo_soggetto"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_GRUPPO_SOGGETTO), params);
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
     * Update gruppo soggetto integer.
     *
     * @param dto the gruppo soggetto
     * @return the integer
     */
    @Override
    public Integer updateGruppoSoggetto(GruppoSoggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idGruppoSoggetto", dto.getIdGruppoSoggetto());

            GruppoSoggettoDTO gruppoSoggettoOld = this.findByPK(className, map);
            if (null == gruppoSoggettoOld) {
                logError(className, "Record non trovato con id [" + dto.getIdGruppoSoggetto() + "]");
                return -1;
            }

            int returnValue = 1;
            Long idIstanzaAttoreOld = gruppoSoggettoOld.getIdIstanzaAttore() != null && gruppoSoggettoOld.getIdIstanzaAttore() > 0 ? gruppoSoggettoOld.getIdIstanzaAttore() : null;
            Long idFunzionarioOld = gruppoSoggettoOld.getIdFunzionario() != null && gruppoSoggettoOld.getIdFunzionario() > 0 ? gruppoSoggettoOld.getIdFunzionario() : null;
            if (!dto.equals(gruppoSoggettoOld)) {
                map.put("desGruppoSoggetto", dto.getDesGruppoSoggetto());
                map.put("flgCreazioneAutomatica", Boolean.TRUE.equals(dto.getFlgCreazioneAutomatica()) ? 1 : 0);
                map.put("idIstanzaAttore", dto.getIdIstanzaAttore() != null ? dto.getIdIstanzaAttore() : idIstanzaAttoreOld);
                map.put("idFunzionario", dto.getIdFunzionario() != null ? dto.getIdFunzionario() : idFunzionarioOld);
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                MapSqlParameterSource params = getParameterValue(map);

                returnValue = template.update(getQuery(QUERY_UPDATE_GRUPPO_SOGGETTO, null, null), params);

                if (returnValue > 0) {
                    Map<String, Object> storicoMap = new HashMap<>();
                    storicoMap.put("gestUid", dto.getGestUID());
                    params = getParameterValue(storicoMap);
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_GRUPPO_SOGGETTO), params);
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
     * Delete gruppo soggetto by uid integer.
     *
     * @param gestUID the uid
     * @return the integer
     */
    @Override
    public Integer deleteGruppoSoggettoByUid(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_GRUPPO_SOGGETTO_BY_UID, null, null), params);
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
        return getQuery(QUERY_GRUPPO_SOGGETTO_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<GruppoSoggettoDTO> getRowMapper() throws SQLException {
        return new GruppoSoggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<GruppoSoggettoDTO> getExtendedRowMapper() throws SQLException {
        return new GruppoSoggettoRowMapper();
    }

    /**
     * The type Gruppo soggetto row mapper.
     */
    public static class GruppoSoggettoRowMapper implements RowMapper<GruppoSoggettoDTO> {

        /**
         * Instantiates a new Recapito alternativo istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public GruppoSoggettoRowMapper() throws SQLException {
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
        public GruppoSoggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            GruppoSoggettoDTO bean = new GruppoSoggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, GruppoSoggettoDTO bean) throws SQLException {
            bean.setIdGruppoSoggetto(rs.getLong("id_gruppo_soggetto"));
            bean.setCodGruppoSoggetto(rs.getString("cod_gruppo_soggetto"));
            bean.setDesGruppoSoggetto(rs.getString("des_gruppo_soggetto"));
            bean.setFlgCreazioneAutomatica(rs.getInt("flg_creazione_automatica") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }
}