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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AllegatoIntegrazioneDAO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIntegrazioneDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Allegato integrazione dao.
 */
public class AllegatoIntegrazioneDAOImpl extends ScrivaBeSrvGenericDAO<AllegatoIntegrazioneDTO> implements AllegatoIntegrazioneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_ALLEGATI_INTEGRAZIONE = "SELECT sriia.*, stai.nome_allegato, stai.cod_allegato,  stai.uuid_index, stai.data_protocollo_allegato, stai.num_protocollo_allegato\n" +
            "FROM _replaceTableName_ sriia\n" +
            "INNER JOIN scriva_t_allegato_istanza stai ON stai.id_allegato_istanza = sriia.id_allegato_istanza\n" +
            "WHERE sriia.id_integrazione_istanza = :idIntegrazioneIstanza\n";


    private static final String QUERY_INSERT_ALLEGATO_INTEGRAZIONE = "INSERT INTO _replaceTableName_\n" +
            "(id_integra_istanza_allegato, id_integrazione_istanza, id_allegato_istanza, flg_allegato_rif_protocollo,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(nextval('seq_scriva_r_integra_istanza_allegato'), :idIntegrazioneIstanza, :idAllegatoIstanza, :flgAllegatoRifProtocollo,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_DELETE_ALLEGATO_INTEGRAZIONE = "DELETE FROM _replaceTableName_\n" +
            "WHERE id_integrazione_istanza=:idIntegrazioneIstanza";

    private static final String QUERY_DELETE_ALLEGATO_INTEGRAZIONE_BY_UID_ALLEG = "DELETE FROM _replaceTableName_\n" +
            "WHERE id_allegato_istanza= (\n" +
            "   SELECT id_allegato_istanza\n" +
            "   FROM scriva_t_allegato_istanza\n" +
            "   WHERE uuid_index = :uuidIndex" +
            ")";

    /**
     * Load allegato integrazione list.
     *
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @return the list
     */
    @Override
    public List<AllegatoIntegrazioneDTO> loadAllegatoIntegrazione(Long idIntegrazioneIstanza) {
        logBeginInfo(className, "\n idIntegrazioneIstanza : [" + idIntegrazioneIstanza + "]");
        Map<String, Object> map = new HashMap<>();
        map.put("idIntegrazioneIstanza", idIntegrazioneIstanza);
        logEnd(className);
        return findListByQuery(className, QUERY_LOAD_ALLEGATI_INTEGRAZIONE, map, null, null);
    }

    /**
     * Saveallegato integrazione compilante long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveAllegatoIntegrazione(AllegatoIntegrazioneDTO dto, Date dataIns) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIntegrazioneIstanza", dto.getIdIntegrazioneIstanza());
            map.put("idAllegatoIstanza", dto.getIdAllegatoIstanza());
            map.put("flgAllegatoRifProtocollo", Boolean.TRUE.equals(dto.getFlgAllegatoRifProtocollo()) ? 1 : 0);
            map.put("gestDataIns", dataIns);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", dataIns);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            String uid = generateGestUID(String.valueOf(dto.getIdIntegrazioneIstanza()) + dto.getIdAllegatoIstanza() + dataIns);
            map.put("gestUid", uid);

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query = getQuery(QUERY_INSERT_ALLEGATO_INTEGRAZIONE, null, null);
            logDebug(className, "\nquery executed : \n" + query + "\n");
            template.update(query, params, keyHolder, new String[]{"id_integra_istanza_allegato"});
            Number key = keyHolder.getKey();
            return key != null ? key.longValue() : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete allegato integrazione integer.
     *
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @return the integer
     */
    @Override
    public Integer deleteAllegatoIntegrazione(Long idIntegrazioneIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIntegrazioneIstanza", idIntegrazioneIstanza);
            return template.update(getQuery(QUERY_DELETE_ALLEGATO_INTEGRAZIONE, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete allegato integrazione by id allegato integer.
     *
     * @param uuidIndex the uuid index
     * @return the integer
     */
    @Override
    public Integer deleteAllegatoIntegrazioneByUuidIndexAllegato(String uuidIndex) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uuidIndex", uuidIndex);
            return template.update(getQuery(QUERY_DELETE_ALLEGATO_INTEGRAZIONE_BY_UID_ALLEG, null, null), getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return -1;
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
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AllegatoIntegrazioneDTO> getRowMapper() throws SQLException {
        return new AllegatoIntegrazioneRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AllegatoIntegrazioneDTO> getExtendedRowMapper() throws SQLException {
        return new AllegatoIntegrazioneRowMapper();
    }

    /**
     * The type Allegato integrazione row mapper.
     */
    public static class AllegatoIntegrazioneRowMapper implements RowMapper<AllegatoIntegrazioneDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AllegatoIntegrazioneRowMapper() throws SQLException {
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
        public AllegatoIntegrazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AllegatoIntegrazioneDTO bean = new AllegatoIntegrazioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, AllegatoIntegrazioneDTO bean) throws SQLException {
            bean.setIdIntegraIstanzaAllegato(rs.getLong("id_integra_istanza_allegato"));
            bean.setIdIntegrazioneIstanza(rs.getLong("id_integrazione_istanza"));
            bean.setIdAllegatoIstanza(rs.getLong("id_allegato_istanza"));
            bean.setFlgAllegatoRifProtocollo(rs.getInt("flg_allegato_rif_protocollo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setNomeAllegato(rs.getString("nome_allegato"));
            bean.setCodAllegato(rs.getString("cod_allegato"));
            bean.setUuidIndex(rs.getString("uuid_index"));
            bean.setDataProtocolloAllegato(rs.getDate("data_protocollo_allegato"));
            bean.setNumProtocolloAllegato(rs.getString("num_protocollo_allegato"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }


}