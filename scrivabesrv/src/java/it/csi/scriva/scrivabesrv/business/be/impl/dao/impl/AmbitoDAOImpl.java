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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AmbitoDAO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
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
 * The type Ambito dao.
 *
 * @author CSI PIEMONTE
 */
public class AmbitoDAOImpl extends ScrivaBeSrvGenericDAO<AmbitoDTO> implements AmbitoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_FLG_ATTIVO = "AND flg_attivo = 1 \n";

    private static final String WHERE_ID_AMBITO = "AND id_ambito = :idAmbito \n";

    private static final String WHERE_COD_AMBITO = "AND UPPER(cod_ambito) = UPPER(:codAmbito) \n";

    private static final String QUERY_AMBITI = "SELECT * FROM _replaceTableName_ \n" +
            "WHERE 1 =1 \n";

    private static final String QUERY_PRIMARY_KEY = QUERY_AMBITI + WHERE_ID_AMBITO;

    private static final String QUERY_AMBITI_ATTIVI = QUERY_AMBITI + WHERE_FLG_ATTIVO;

    private static final String QUERY_AMBITI_ATTIVI_TEST = "SELECT sda.*, array_agg(sdta.*) \n" +
            "FROM scriva_d_ambito sda \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_ambito = sda.id_ambito \n" +
            "WHERE sda.flg_attivo = 1\n" +
            "AND sdta.flg_attivo = 1\n" +
            "GROUP BY sda.id_ambito, sda.cod_ambito , sda.des_ambito, sda.des_estesa_ambito";

    private static final String QUERY_AMBITO_BY_CODE = QUERY_AMBITI + "AND UPPER(cod_ambito) = UPPER(:codAmbito) \n";

    private static final String QUERY_AMBITI_ATTIVI_BY_ID_LIST = QUERY_AMBITI + "AND flg_attivo = 1 AND id_ambito IN (:idAmbitoList)";

    private static final String QUERY_AMBITO_BY_CF_FUNZIONARIO = "SELECT DISTINCT sda.* \n" +
            "FROM scriva_t_funzionario stf \n" +
            "INNER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario AND srfp.data_fine_validita IS NULL \n" +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = srfp.id_profilo_app \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = srtap.id_tipo_adempimento \n" +
            "INNER JOIN _replaceTableName_ sda ON sdta.id_ambito = sda.id_ambito \n" +
            "WHERE sda.flg_attivo = 1 \n" +
            "AND stf.cf_funzionario = :cfFunzionario ";

    private static final String QUERY_INSERT_NEW_AMBITO = "INSERT INTO _replaceTableName_ (id_ambito, cod_ambito, des_ambito, des_estesa_ambito, flg_attivo, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) VALUES(nextval('seq_scriva_d_ambito'), :cod, :des, :desestesa, :attivo, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_AMBITO = "UPDATE _replaceTableName_ SET cod_ambito = :cod, des_ambito = :des, des_estesa_ambito = :desestesa, flg_attivo = :attivo, gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd WHERE id_ambito = :idAmbito ";

    private static final String QUERY_DELETE_AMBITO = "DELETE FROM _replaceTableName_ WHERE id_ambito = :idAmbito";

    /**
     * Load ambiti attivi list.
     *
     * @return List<AmbitoDTO> list
     */
    @Override
    public List<AmbitoDTO> loadAmbitiAttivi() {
        logBegin(className);
        return findListByQuery(className, QUERY_AMBITI_ATTIVI, null);
    }

    /**
     * Load ambiti attivi string.
     *
     * @return the string
     */
    @Override
    public String loadJsonAmbitiAttivi() {
        logBegin(className);
        return getJsonFromQuery(className, QUERY_AMBITI_ATTIVI, null, null, null);
    }

    /**
     * Load ambito list.
     *
     * @param idAmbito  the id ambito
     * @param codAmbito the cod ambito
     * @return the list
     */
    @Override
    public List<AmbitoDTO> loadAmbito(Long idAmbito, String codAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = idAmbito != null || StringUtils.isNotBlank(codAmbito) ? QUERY_AMBITI : QUERY_AMBITI_ATTIVI;
        if (idAmbito != null) {
            map.put("idAmbito", idAmbito);
            query += WHERE_ID_AMBITO;
        }
        if (StringUtils.isNotBlank(codAmbito)) {
            map.put("codAmbito", codAmbito);
            query += WHERE_COD_AMBITO;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load ambito by id list.
     *
     * @param idAmbito idAmbito
     * @return List<AmbitoDTO> list
     */
    @Override
    public List<AmbitoDTO> loadAmbitoById(Long idAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAmbito", idAmbito);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load ambito by id list list.
     *
     * @param idAmbitoList the id ambito list
     * @return the list
     */
    @Override
    public List<AmbitoDTO> loadAmbitoByIdList(List<Long> idAmbitoList) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAmbitoList", idAmbitoList);
        return findListByQuery(className, QUERY_AMBITI_ATTIVI_BY_ID_LIST, map);
    }

    /**
     * Load ambito by code list.
     *
     * @param codAmbito codice ambito
     * @return List<AmbitoDTO> list
     */
    @Override
    public List<AmbitoDTO> loadAmbitoByCode(String codAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAmbito", codAmbito);
        return findListByQuery(className, QUERY_AMBITO_BY_CODE, map);
    }

    /**
     * Load ambito by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    @Override
    public List<AmbitoDTO> loadAmbitoByCfFunzionario(String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfFunzionario", cfFunzionario);
        return findListByQuery(className, QUERY_AMBITO_BY_CF_FUNZIONARIO, map);
    }

    /**
     * Save ambito long.
     *
     * @param dto AmbitoDTO
     * @return id record inserito
     */
    @Override
    public Long saveAmbito(AmbitoDTO dto) {
        logBegin(className);
        try {
        	Date now = Calendar.getInstance().getTime();
            Map<String, Object> map = new HashMap<>();
            map.put("cod", dto.getCodAmbito());
            map.put("des", dto.getDesAmbito());
            map.put("desestesa", dto.getDesEstesaAmbito());
            map.put("attivo", Boolean.TRUE.equals(dto.getFlgAttivo()) ? 1 : 0);
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getGestAttoreIns() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_NEW_AMBITO, null, null), params, keyHolder, new String[]{"id_ambito"});

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
     * Update ambito integer.
     *
     * @param dto AmbitoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateAmbito(AmbitoDTO dto) {
        logBegin(className);
        try {
        	Date now = Calendar.getInstance().getTime();
            Map<String, Object> map = new HashMap<>();
            map.put("idAmbito", dto.getIdAmbito());
            map.put("cod", dto.getCodAmbito());
            map.put("des", dto.getDesAmbito());
            map.put("desestesa", dto.getDesEstesaAmbito());
            map.put("attivo", Boolean.TRUE.equals(dto.getFlgAttivo()) ? 1 : 0);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_AMBITO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ambito.
     *
     * @param idAmbito idAmbito
     */
    @Override
    public void deleteAmbito(Long idAmbito) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAmbito", idAmbito);
            MapSqlParameterSource params = getParameterValue(map);

            template.update(getQuery(QUERY_DELETE_AMBITO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RowMapper<AmbitoDTO> getRowMapper() throws SQLException {
        return new AmbitoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<AmbitoDTO> getExtendedRowMapper() throws SQLException {
        return new AmbitoRowMapper();
    }

    /**
     * The type Ambito row mapper.
     */
    public static class AmbitoRowMapper implements RowMapper<AmbitoDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AmbitoRowMapper() throws SQLException {
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
        public AmbitoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AmbitoDTO bean = new AmbitoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("id_ambito"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}