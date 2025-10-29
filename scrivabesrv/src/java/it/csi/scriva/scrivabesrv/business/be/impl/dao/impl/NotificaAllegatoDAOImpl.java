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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotificaAllegatoDAO;
import it.csi.scriva.scrivabesrv.dto.NotificaAllegatoDTO;
import org.apache.commons.lang3.StringUtils;
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
 * The type Notifica allegato dao.
 *
 * @author CSI PIEMONTE
 */
public class NotificaAllegatoDAOImpl extends ScrivaBeSrvGenericDAO<NotificaAllegatoDTO> implements NotificaAllegatoDAO {

    private final String className = this.getClass().getSimpleName();
    private static final String QUERY_LOAD_NOTIFICA_ALLEGATO = "SELECT stn.id_istanza,\n" +
            "srna.*\n" +
            "FROM _replaceTableName_ srna\n" +
            "INNER JOIN scriva_t_notifica stn ON stn.id_notifica = srna.id_notifica\n" +
            "WHERE 1 = 1\n";

    private static final String WHERE_ID_NOTIFICA_ALLEGATO = "AND id_notifica_allegato = :idNotificaAllegato\n";
    private static final String WHERE_ID_NOTIFICA = "AND id_notifica = :idNotifica\n";
    private static final String WHERE_ID_ALLEGATO_ISTANZA = "AND id_allegato_istanza = :idAllegatoIstanza\n";
    private static final String WHERE_ID_ISTANZA = "AND id_istanza = :idIstanza\n";
    private static final String WHERE_COD_ALLEGATO = "AND cod_allegato = :codAllegato\n";
    private static final String WHERE_GESTUID_RICH = "AND gest_uid_richiesta = :gestUidRichiesta\n";
    private static final String WHERE_DES_TIPO_RICH = "AND des_tipo_richiesta = :desTipoRichiesta\n";
    private static final String WHERE_GESTUID = "AND srna.gest_uid = :gestUid\n";
    ;

    private static final String QUERY_INSERT_NOTIFICA_ALLEGATO = "INSERT INTO _replaceTableName_\n" +
            "(id_notifica_allegato, id_notifica, des_segnalazione, id_allegato_istanza,\n" +
            "cod_allegato, nome_allegato, flg_allegato_fallito, gest_uid_richiesta, des_tipo_richiesta,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(\n" +
            "nextval('seq_scriva_r_notifica_allegato'), :idNotifica, :desSegnalazione, :idAllegatoIstanza,\n" +
            ":codAllegato, :nomeAllegato, :flgAllegatoFallito, :gestUidRichiesta, :desTipoRichiesta,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_NOTIFICA_ALLEGATO = "UPDATE _replaceTableName_\n" +
            "SET des_segnalazione=:desSegnalazione, id_allegato_istanza=:idAllegatoIstanza, cod_allegato=:codAllegato,\n" +
            "nome_allegato=:nomeAllegato, flg_allegato_fallito=:flgAllegatoFallito, gest_uid_richiesta=:gestUidRichiesta,\n" +
            "des_tipo_richiesta=:desTipoRichiesta, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE id_notifica_allegato=:idNotificaAllegato";

    private static final String QUERY_DELETE_NOTIFICA_ALLEGATO = "DELETE FROM _replaceTableName_ srna\n";

    /**
     * Load notifica allegato list.
     *
     * @return the list
     */
    @Override
    public List<NotificaAllegatoDTO> loadNotificaAllegato() {
        return loadNotificaAllegato(null, null, null, null, null, null, null);
    }

    /**
     * Load notifica allegato list.
     *
     * @param idNotificaAllegato the id notifica allegato
     * @param idNotifica         the id notifica
     * @param idAllegatoIstanza  the id allegato istanza
     * @param idIstanza          the id istanza
     * @param codAllegato        the cod allegato
     * @param gestUidRichiesta   the gest uid richiesta
     * @param desTipoRichiesta   the des tipo richiesta
     * @return the list
     */
    @Override
    public List<NotificaAllegatoDTO> loadNotificaAllegato(Long idNotificaAllegato,
                                                          Long idNotifica,
                                                          Long idAllegatoIstanza,
                                                          Long idIstanza,
                                                          String codAllegato,
                                                          String gestUidRichiesta,
                                                          String desTipoRichiesta) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_NOTIFICA_ALLEGATO;
        if (idNotificaAllegato != null) {
            map.put("idNotificaAllegato", idNotificaAllegato);
            query += WHERE_ID_NOTIFICA_ALLEGATO;
        }
        if (idNotifica != null) {
            map.put("idNotifica", idNotifica);
            query += WHERE_ID_NOTIFICA;
        }
        if (idAllegatoIstanza != null) {
            map.put("idAllegatoIstanza", idAllegatoIstanza);
            query += WHERE_ID_ALLEGATO_ISTANZA;
        }
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        if (StringUtils.isNotBlank(codAllegato)) {
            map.put("codAllegato", codAllegato);
            query += WHERE_COD_ALLEGATO;
        }
        if (StringUtils.isNotBlank(gestUidRichiesta)) {
            map.put("gestUidRichiesta", gestUidRichiesta);
            query += WHERE_GESTUID_RICH;
        }
        if (StringUtils.isNotBlank(desTipoRichiesta)) {
            map.put("desTipoRichiesta", desTipoRichiesta);
            query += WHERE_DES_TIPO_RICH;
        }
        logEnd(className);
        return findListByQuery(className, query, map);
    }

    /**
     * Save notifica allegato long.
     *
     * @param dto the notifica allegato
     * @return the long
     */
    @Override
    public Long saveNotificaAllegato(NotificaAllegatoDTO dto) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idNotifica", dto.getIdNotifica());
            map.put("idAllegatoIstanza", dto.getIdAllegatoIstanza());
            map.put("desSegnalazione", dto.getDesSegnalazione());
            map.put("codAllegato", dto.getCodAllegato());
            map.put("nomeAllegato", dto.getNomeAllegato());
            map.put("flgAllegatoFallito", Boolean.TRUE.equals(dto.getFlgAllegatoFallito()) ? 1 : 0);
            map.put("gestUidRichiesta", dto.getGestUidRichiesta());
            map.put("desTipoRichiesta", dto.getDesTipoRichiesta());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            String uid = generateGestUID(String.valueOf(dto.getIdNotifica()) + now);
            map.put("gestUid", uid);

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(getQuery(QUERY_INSERT_NOTIFICA_ALLEGATO, null, null), params, keyHolder, new String[]{"id_notifica_allegato"});
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
     * Update notifica allegato integer.
     *
     * @param dto the notifica allegato
     * @return the integer
     */
    @Override
    public Integer updateNotificaAllegato(NotificaAllegatoDTO dto) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            NotificaAllegatoDTO notificaAllegato = this.findById(dto.getIdNotificaAllegato());
            if (notificaAllegato == null) {
                logError(className, "Record non trovato con idNotifica [" + dto.getIdNotificaAllegato() + "]");
                return -1;
            }
            map.put("idNotificaAllegato", dto.getIdNotificaAllegato());
            map.put("desSegnalazione", dto.getDesSegnalazione());
            map.put("idAllegatoIstanza", dto.getIdAllegatoIstanza());
            map.put("codAllegato", dto.getCodAllegato());
            map.put("nomeAllegato", dto.getNomeAllegato());
            map.put("flgAllegatoFallito", Boolean.TRUE.equals(dto.getFlgAllegatoFallito()) ? 1 : 0);
            map.put("gestUidRichiesta", dto.getGestUidRichiesta());
            map.put("desTipoRichiesta", dto.getDesTipoRichiesta());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_NOTIFICA_ALLEGATO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete notifica allegato integer.
     *
     * @param gestUid the gest uid
     * @return Integer integer
     */
    @Override
    public Integer deleteNotificaAllegato(String gestUid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_NOTIFICA_ALLEGATO + WHERE_GESTUID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by id notifica allegato dto.
     *
     * @param idNotificaAllegato the id notifica allegato
     * @return the notifica allegato dto
     */
    public NotificaAllegatoDTO findById(Long idNotificaAllegato) {
        logBeginInfo(className, idNotificaAllegato);
        Map<String, Object> map = new HashMap<>();
        map.put("idNotificaAllegato", idNotificaAllegato);
        return findSimpleDTOByQuery(className, QUERY_LOAD_NOTIFICA_ALLEGATO + WHERE_ID_NOTIFICA_ALLEGATO, map);
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
    public RowMapper<NotificaAllegatoDTO> getRowMapper() throws SQLException {
        return new NotificaAllegatoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<NotificaAllegatoDTO> getExtendedRowMapper() throws SQLException {
        return new NotificaAllegatoRowMapper();
    }

    /**
     * The type Notifica allegato row mapper.
     */
    public static class NotificaAllegatoRowMapper implements RowMapper<NotificaAllegatoDTO> {

        /**
         * Instantiates a new Notifica allegato row mapper.
         *
         * @throws SQLException the sql exception
         */
        public NotificaAllegatoRowMapper() throws SQLException {
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
        public NotificaAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificaAllegatoDTO bean = new NotificaAllegatoDTO();
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
        public void populateBean(ResultSet rs, NotificaAllegatoDTO bean) throws SQLException {
            bean.setIdNotificaAllegato(rs.getLong("id_notifica_allegato"));
            bean.setIdNotifica(rs.getLong("id_notifica"));
            bean.setDesSegnalazione(rs.getString("des_segnalazione"));
            bean.setIdAllegatoIstanza(rs.getLong("id_allegato_istanza"));
            bean.setCodAllegato(rs.getString("cod_allegato"));
            bean.setNomeAllegato(rs.getString("nome_allegato"));
            bean.setFlgAllegatoFallito(rs.getInt("flg_allegato_fallito") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUidRichiesta(rs.getString("gest_uid_richiesta"));
            bean.setDesTipoRichiesta(rs.getString("des_tipo_richiesta"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}