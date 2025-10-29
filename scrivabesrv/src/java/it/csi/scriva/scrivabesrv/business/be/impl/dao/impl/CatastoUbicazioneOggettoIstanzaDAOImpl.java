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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CatastoUbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.CatastoUbicazioneOggettoIstanzaDTO;
import org.springframework.dao.DataAccessException;
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
 * The type Catasto ubicazione oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class CatastoUbicazioneOggettoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<CatastoUbicazioneOggettoIstanzaDTO> implements CatastoUbicazioneOggettoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_CATASTO_UBI_OGG_IST = "AND srcuoi.id_catasto_ubica_oggetto_ist = :idCatastoUbicazioneOggettoIstanza \n";

    private static final String WHERE_ID_UBICAZIONE_OGGETTO_ISTANZA = "AND srcuoi.id_ubica_oggetto_istanza = :idUbicazioneOggettoIstanza \n";

    private static final String WHERE_ID_OGGETTO_ISTANZA = "AND sruoi.id_oggetto_istanza = :idOggettoIstanza \n";

    private static final String WHERE_ID_ISTANZA = "AND stoi.id_istanza = :idIstanza \n";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ srcuoi WHERE 1=1\n" + WHERE_ID_CATASTO_UBI_OGG_IST;

    private static final String QUERY_CATASTO_UBI_OGG_IST = "SELECT * \n" +
            "FROM _replaceTableName_ srcuoi\n" + //scriva_r_catasto_ubi_ogg_ist
            "INNER JOIN scriva_r_ubica_oggetto_istanza sruoi ON sruoi.id_ubica_oggetto_istanza = srcuoi.id_ubica_oggetto_istanza\n" +
            "INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_oggetto_istanza = sruoi.id_oggetto_istanza\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_INSERT_CATASTO_UBI_OGG_IST = "INSERT INTO _replaceTableName_\n" +
            "(id_catasto_ubica_oggetto_ist, id_ubica_oggetto_istanza, sezione, foglio, particella, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES (nextval('seq_scriva_r_catasto_ubi_ogg_ist'), :idUbicaOggettoIstanza, :sezione, :foglio, :particella, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_INSERT_STORICO_CATASTO_UBI_OGG_IST = "INSERT INTO _replaceTableName_\n" +
            "(id_catasto_ubi_ogg_ist_storico, id_catasto_ubica_oggetto_ist, id_ubica_oggetto_istanza, sezione, foglio, \n" +
            "particella, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid, ind_fonte_dato, cod_belfiore)\n" +
            "SELECT nextval('seq_scriva_s_catasto_ubi_ogg_ist'), id_catasto_ubica_oggetto_ist, id_ubica_oggetto_istanza, sezione, foglio, \n" +
            "particella, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid, ind_fonte_dato, cod_belfiore\n" +
            "FROM scriva_r_catasto_ubi_ogg_ist\n" +
            "WHERE gest_uid = :gestUid";

    private static final String QUERY_UPDATE_CATASTO_UBI_OGG_IST = "UPDATE _replaceTableName_\n" +
            "SET sezione=:sezione, foglio=:foglio, particella=:particella,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE gest_uid=:gestUid";

    private static final String QUERY_COPY_FROM_ID_UBICA_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_catasto_ubica_oggetto_ist, id_ubica_oggetto_istanza, sezione, foglio, particella, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_r_catasto_ubi_ogg_ist'), :idUbicazioneOggettoIstanzaNew, sezione, foglio, particella, :gestDataIns, :gestAttoreIns, :gestDataIns, :gestAttoreIns, :gestUid\n" +
            "FROM _replaceTableName_ WHERE id_ubica_oggetto_istanza = :idUbicazioneOggettoIstanzaToCopy";

    private static final String QUERY_DELETE_CATASTO_UBI_OGG_IST = "DELETE FROM _replaceTableName_ srcuoi WHERE 1=1\n";

    private static final String QUERY_DELETE_CATASTO_UBI_OGG_IST_BY_UID = QUERY_DELETE_CATASTO_UBI_OGG_IST + "AND srcuoi.gest_uid = :gestUid \n";

    private static final String QUERY_DELETE_CATASTO_UBI_OGG_IST_BY_ID_OGGETTO_ISTANZA = QUERY_DELETE_CATASTO_UBI_OGG_IST + "AND id_ubica_oggetto_istanza IN (SELECT id_ubica_oggetto_istanza FROM scriva_r_ubica_oggetto_istanza sruoi WHERE sruoi.id_oggetto_istanza = :idOggettoIstanza)\n";

    private static final String QUERY_DELETE_CATASTO_UBI_OGG_IST_BY_ID_UBICAZIONE_OGG_IST = QUERY_DELETE_CATASTO_UBI_OGG_IST + WHERE_ID_UBICAZIONE_OGGETTO_ISTANZA;

    /**
     * Load catasto ubicazione oggetto istanza list.
     *
     * @param idCatastoUbicazioneOggettoIstanza the id catasto ubicazione oggetto istanza
     * @param idUbicazioneOggettoIstanza        the id ubicazione oggetto istanza
     * @param idOggettoIstanza                  the id oggetto istanza
     * @param idIstanza                         the id istanza
     * @return the list
     */
    @Override
    public List<CatastoUbicazioneOggettoIstanzaDTO> loadCatastoUbicazioneOggettoIstanza(Long idCatastoUbicazioneOggettoIstanza, Long idUbicazioneOggettoIstanza, Long idOggettoIstanza, Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_CATASTO_UBI_OGG_IST;
        if (idCatastoUbicazioneOggettoIstanza != null) {
            map.put("idCatastoUbicazioneOggettoIstanza", idCatastoUbicazioneOggettoIstanza);
            query += WHERE_ID_CATASTO_UBI_OGG_IST;
        }
        if (idUbicazioneOggettoIstanza != null) {
            map.put("idUbicazioneOggettoIstanza", idUbicazioneOggettoIstanza);
            query += WHERE_ID_UBICAZIONE_OGGETTO_ISTANZA;
        }
        if (idOggettoIstanza != null) {
            map.put("idOggettoIstanza", idOggettoIstanza);
            query += WHERE_ID_OGGETTO_ISTANZA;
        }
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Save catasto ubicazione oggetto istanza long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveCatastoUbicazioneOggettoIstanza(CatastoUbicazioneOggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idUbicaOggettoIstanza", dto.getIdUbicaOggettoIstanza());
            map.put("sezione", dto.getSezione());
            map.put("foglio", dto.getFoglio());
            map.put("particella", dto.getParticella());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdUbicaOggettoIstanza().toString() + dto.getGestAttoreIns() + now));

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_CATASTO_UBI_OGG_IST, null, null), params, keyHolder, new String[]{"id_catasto_ubica_oggetto_ist"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_CATASTO_UBI_OGG_IST), params);
            }

            return key.longValue();

        } catch (DataAccessException e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update catasto ubicazione oggetto istanza integer.
     *
     * @param dto the dto
     * @return the integer
     */
    @Override
    public Integer updateCatastoUbicazioneOggettoIstanza(CatastoUbicazioneOggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idCatastoUbicazioneOggettoIstanza", dto.getIdCatastoUbicaOggettoIstanza());
            CatastoUbicazioneOggettoIstanzaDTO catastoUbicazioneOggettoIstanzaOld = findByPK(className, map);
            if (catastoUbicazioneOggettoIstanzaOld == null) {
                logError(className, "Record non trovato con id [" + dto.getIdCatastoUbicaOggettoIstanza() + "]");
                return -1;
            }

            int returnValue = 1;
            if (!dto.equals(catastoUbicazioneOggettoIstanzaOld)) {
                map.put("sezione", dto.getSezione());
                map.put("foglio", dto.getFoglio());
                map.put("particella", dto.getParticella());
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                map.put("gestUid", catastoUbicazioneOggettoIstanzaOld.getGestUID());
                MapSqlParameterSource params = getParameterValue(map);
                returnValue = template.update(getQuery(QUERY_UPDATE_CATASTO_UBI_OGG_IST, null, null), params);

                if (returnValue > 0) {
                    Map<String, Object> storicoMap = new HashMap<>();
                    storicoMap.put("gestUid", map.get("gestUid"));
                    params = getParameterValue(storicoMap);
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_CATASTO_UBI_OGG_IST), params);
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
     * Delete catasto ubicazione oggetto istanza integer.
     *
     * @param uid the uid
     * @return the integer
     */
    @Override
    public Integer deleteCatastoUbicazioneOggettoIstanza(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_CATASTO_UBI_OGG_IST_BY_UID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete catasto ubicazione oggetto istanza by id oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    @Override
    public Integer deleteCatastoUbicazioneOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_CATASTO_UBI_OGG_IST_BY_ID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete catasto ubicazione oggetto istanza by id ubicazione oggetto istanza integer.
     *
     * @param idUbicazioneOggettoIstanza the id ubicazione oggetto istanza
     * @return the integer
     */
    @Override
    public Integer deleteCatastoUbicazioneOggettoIstanzaByIdUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idUbicazioneOggettoIstanza", idUbicazioneOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_CATASTO_UBI_OGG_IST_BY_ID_UBICAZIONE_OGG_IST, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save copy catasto ubicazione oggetto istanza long.
     *
     * @param idUbicazioneOggettoIstanzaNew    the id ubicazione oggetto istanza new
     * @param idUbicazioneOggettoIstanzaToCopy the id ubicazione oggetto istanza to copy
     * @param gestDataIns                      the gest data ins
     * @param gestAttoreIns                    the gest attore ins
     * @return the long
     */
    @Override
    public Long saveCopyCatastoUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanzaNew, Long idUbicazioneOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idUbicazioneOggettoIstanzaNew", idUbicazioneOggettoIstanzaNew);
            map.put("idUbicazioneOggettoIstanzaToCopy", idUbicazioneOggettoIstanzaToCopy);
            map.put("gestDataIns", gestDataIns);
            map.put("gestAttoreIns", gestAttoreIns);
            map.put("gestUid", generateGestUID(String.valueOf(idUbicazioneOggettoIstanzaNew) + gestDataIns));
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_COPY_FROM_ID_UBICA_OGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_catasto_ubica_oggetto_ist"});
            Number key = keyHolder.getKeyList().size() > 1 ? (Number) keyHolder.getKeyList().get(0).get("id_catasto_ubica_oggetto_ist") : keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_CATASTO_UBI_OGG_IST), params);
            }
            return key != null ? key.longValue() : null;
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
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>      row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<CatastoUbicazioneOggettoIstanzaDTO> getRowMapper() throws SQLException {
        return new CatastoUbicazioneOggettoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>      extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<CatastoUbicazioneOggettoIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new CatastoUbicazioneOggettoIstanzaRowMapper();
    }

    public static class CatastoUbicazioneOggettoIstanzaRowMapper implements RowMapper<CatastoUbicazioneOggettoIstanzaDTO> {

        /**
         * Instantiates a new OggettoIstanza Natura 2000 row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CatastoUbicazioneOggettoIstanzaRowMapper() throws SQLException {
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
        public CatastoUbicazioneOggettoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatastoUbicazioneOggettoIstanzaDTO bean = new CatastoUbicazioneOggettoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CatastoUbicazioneOggettoIstanzaDTO bean) throws SQLException {
            bean.setIdCatastoUbicaOggettoIstanza(rs.getLong("id_catasto_ubica_oggetto_ist"));
            bean.setIdUbicaOggettoIstanza(rs.getLong("id_ubica_oggetto_istanza"));
            bean.setSezione(rs.getString("sezione"));
            bean.setFoglio(rs.getInt("foglio"));
            bean.setParticella(rs.getString("particella"));
            bean.setIndFonteDato(rs.getString("ind_fonte_dato"));
            bean.setCodBelfiore(rs.getString("cod_belfiore"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }


}