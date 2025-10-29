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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ReferenteIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
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
 * The type Referente istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class ReferenteIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<ReferenteIstanzaDTO> implements ReferenteIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_REFERENTI_ISTANZE = "SELECT srri.*, srri.gest_uid AS gest_uid_ref, \n" +
            "sti.*, sti.id_istanza as istanza_id, \n" +
            "stc.*, stc.id_compilante AS compilante_id, \n" +
            "sdsi.*, sdsi.id_stato_istanza AS stato_istanza_id, \n" +
            "sdad.*, sdad.id_adempimento AS adempimento_id, \n" +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, \n" +
            "sda.*, sda.id_ambito as ambito_id \n" +
            "FROM _replaceTableName_ srri \n" +
            "INNER JOIN scriva_t_istanza sti ON srri.id_istanza = sti.id_istanza \n" +
            "INNER JOIN scriva_r_istanza_attore sria ON sria.id_istanza_attore = sti.id_istanza_attore_owner \n" +
            "LEFT JOIN scriva_t_compilante stc ON sria.id_compilante = stc.id_compilante \n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sti.id_stato_istanza = sdsi.id_stato_istanza \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sti.id_adempimento = sdad.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito \n" +
            "WHERE sria.data_revoca IS NULL \n";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_REFERENTI_ISTANZE + "AND srri.id_referente_istanza = :idReferenteIstanza\n";

    private static final String QUERY_LOAD_REFERENTI_ISTANZA_BY_ID_ISTANZA = QUERY_LOAD_REFERENTI_ISTANZE + "AND sti.id_istanza = :idIstanza\n";

    private static final String QUERY_LOAD_REFERENTI_ISTANZA_BY_CODE_ISTANZA = QUERY_LOAD_REFERENTI_ISTANZE + "AND sti.cod_istanza = :codeIstanza\n";

    private static final String QUERY_INSERT_REFERENTE_ISTANZA = "INSERT INTO _replaceTableName_ \n"
            + "(id_referente_istanza, id_istanza, cognome_referente, nome_referente, num_tel_referente, \n"
            + "des_email_referente, des_mansione_referente, num_cellulare_referente, des_pec_referente, \n"
            + "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) \n"
            + "VALUES(nextval('seq_scriva_r_referente_istanza'), :idIstanza, :cognome, :nome, :tel, \n"
            + ":email, :mansione, :numCell, :desPec, \n"
            + ":dateIns, :attoreIns, :dateUpd, :attoreUpd, :uid)";

    private static final String QUERY_UPDATE_REFERENTE_ISTANZA = "UPDATE _replaceTableName_ \n"
            + "SET id_istanza = :idIstanza, cognome_referente = :cognome, nome_referente = :nome, num_tel_referente = :tel, \n"
            + "des_email_referente = :email, des_mansione_referente = :mansione, num_cellulare_referente = :numCell, des_pec_referente = :desPec, \n"
            + "gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd \n"
            + "where id_referente_istanza = :idReferenteIstanza \n";

    private static final String QUERY_DELETE_REFERENTE_ISTANZA = "DELETE FROM _replaceTableName_ where gest_uid = :uid ";

    private static final String QUERY_SELECT_ID_ISTANZA = "SELECT id_istanza FROM _replaceTableName_ where gest_uid = :uid ";

    private static final String QUERY_DELETE_REFERENTE_ISTANZA_BY_ID = "DELETE FROM _replaceTableName_ where id_referente_istanza = :idReferenteIstanza ";

    /**
     * Load referenti istanze list.
     *
     * @return List<ReferenteIstanzaExtendedDTO> list
     */
    @Override
    public List<ReferenteIstanzaDTO> loadReferentiIstanze() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_REFERENTI_ISTANZE, null);
    }

    /**
     * Load referente istanza list.
     *
     * @param idReferenteIstanza idReferenteIstanza
     * @return List<ReferenteIstanzaExtendedDTO> list
     */
    @Override
    public List<ReferenteIstanzaDTO> loadReferenteIstanza(Long idReferenteIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idReferenteIstanza", idReferenteIstanza);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load referenti istanza by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<ReferenteIstanzaExtendedDTO> list
     */
    @Override
    public List<ReferenteIstanzaDTO> loadReferentiIstanzaByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_REFERENTI_ISTANZA_BY_ID_ISTANZA, map);
    }

    /**
     * Load referenti istanza by code istanza list.
     *
     * @param codeIstanza codeIstanza
     * @return List<ReferenteIstanzaExtendedDTO> list
     */
    @Override
    public List<ReferenteIstanzaDTO> loadReferentiIstanzaByCodeIstanza(String codeIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codeIstanza", codeIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_REFERENTI_ISTANZA_BY_CODE_ISTANZA, map);
    }

    /**
     * Save referente istanza long.
     *
     * @param dto ReferenteIstanzaDTO
     * @return id record salvato
     */
    @Override
    public Long saveReferenteIstanza(ReferenteIstanzaDTO dto) {
        logBegin(className);
        try {
            Date now = Calendar.getInstance().getTime();
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", dto.getIdIstanza());
            map.put("cognome", dto.getCognomeReferente());
            map.put("nome", dto.getNomeReferente());
            map.put("tel", dto.getNumTelReferente());
            map.put("email", dto.getDesEmailReferente());
            map.put("mansione", dto.getDesMansioneReferente());
            map.put("numCell", dto.getNumCellulareReferente());
            map.put("desPec", dto.getDesPecReferente());
            map.put("dateIns", now);
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreIns());
            map.put("uid", generateGestUID(String.valueOf(dto.getIdIstanza()) + now));

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(getQuery(QUERY_INSERT_REFERENTE_ISTANZA, null, null), params, keyHolder, new String[]{"id_referente_istanza"});
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
     * Update referente istanza integer.
     *
     * @param dto ReferenteIstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateReferenteIstanza(ReferenteIstanzaDTO dto) {
        logBegin(className);
        try {
            Date now = Calendar.getInstance().getTime();
            Map<String, Object> map = new HashMap<>();
            map.put("idReferenteIstanza", dto.getIdReferenteIstanza());
            map.put("idIstanza", dto.getIdIstanza());
            map.put("cognome", dto.getCognomeReferente());
            map.put("nome", dto.getNomeReferente());
            map.put("tel", dto.getNumTelReferente());
            map.put("email", dto.getDesEmailReferente());
            map.put("mansione", dto.getDesMansioneReferente());
            map.put("numCell", dto.getNumCellulareReferente());
            map.put("desPec", dto.getDesPecReferente());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreUpd());
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_REFERENTE_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete referente istanza integer.
     *
     * @param uid uid
     * @return numero record cancellati
     */
    @Override
    public Integer deleteReferenteIstanza(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_REFERENTE_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }


        /**
     * Select id istanza integer.
     *
     * @param uid uid
     * @return numero record cancellati
     */
    @Override
    public Long loadIdIstanzaByUID(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);
           // return template.update(getQuery(QUERY_DELETE_REFERENTE_ISTANZA, null, null), params);
            return template.queryForObject(getQuery(QUERY_SELECT_ID_ISTANZA, null, null), params, Long.class);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete referente istanza by id integer.
     *
     * @param idReferenteIstanza idReferenteIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteReferenteIstanzaById(Long idReferenteIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idReferenteIstanza", idReferenteIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_REFERENTE_ISTANZA_BY_ID, null, null), params);
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
     * @return RowMapper<ReferenteIstanzaDTO>
     */
    @Override
    public RowMapper<ReferenteIstanzaDTO> getRowMapper() throws SQLException {
        return new ReferenteIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<ReferenteIstanzaExtendedDTO>
     */
    @Override
    public RowMapper<ReferenteIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new ReferenteIstanzaRowMapper();
    }

    /**
     * The type Referente istanza row mapper.
     */
    public static class ReferenteIstanzaRowMapper implements RowMapper<ReferenteIstanzaDTO> {

        /**
         * Instantiates a new Referente istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ReferenteIstanzaRowMapper() throws SQLException {
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
        public ReferenteIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReferenteIstanzaDTO bean = new ReferenteIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ReferenteIstanzaDTO bean) throws SQLException {
            bean.setIdReferenteIstanza(rs.getLong("id_referente_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setCognomeReferente(rs.getString("cognome_referente"));
            bean.setNomeReferente(rs.getString("nome_referente"));
            bean.setNumTelReferente(rs.getString("num_tel_referente"));
            bean.setDesEmailReferente(rs.getString("des_email_referente"));
            bean.setDesMansioneReferente(rs.getString("des_mansione_referente"));
            bean.setNumCellulareReferente(rs.getString("num_cellulare_referente"));
            bean.setDesPecReferente(rs.getString("des_pec_referente"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}