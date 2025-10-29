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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoDAO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
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
 * The type Tipo adempimento dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoAdempimentoDAOImpl extends ScrivaBeSrvGenericDAO<TipoAdempimentoDTO> implements TipoAdempimentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_FLG_ATTIVI = "AND sdta.flg_attivo = 1 AND sda.flg_attivo = 1\n ";

    private static final String WHERE_ID_AMBITO = "AND sdta.id_ambito = :idAmbito\n";

    private static final String WHERE_COD_AMBITO = "AND UPPER(sda.cod_ambito) = UPPER(:codAmbito)\n";

    private static final String WHERE_COD_TIPO_ADEMPIMENTO = "AND UPPER(sdta.cod_tipo_adempimento) = UPPER(:codTipoAdempimento)\n";

    private static final String ORDER_BY_ORDINAMENTO = "ORDER BY preference, sdta.ordinamento_tipo_adempimento\n";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ where id_tipo_adempimento = :idTipoAdempimento";

    private static final String QUERY_TIPI_ADEMPIMENTO = "SELECT sdta.*, sdta.id_ambito AS ambito_id, sdta.flg_attivo AS tipo_proc_attivo,\n"
            + "sda.*, sda.flg_attivo AS ambito_attivo, 0 AS preference\n"
            + "FROM _replaceTableName_ sdta\n"
            + "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito\n"
            + "WHERE 1 = 1\n";

    private static final String QUERY_TIPI_ADEMPIMENTO_BY_CF_FUNZIONARIO = "SELECT sdta.*, sdta.id_ambito AS ambito_id, sdta.flg_attivo AS tipo_proc_attivo,\n "
            + "sda.*, sda.flg_attivo AS ambito_attivo, 0 AS preference\n"
            + "FROM scriva_t_funzionario AS stf\n"
            + "INNER JOIN scriva_r_funzionario_profilo AS srfp ON srfp.id_funzionario = stf.id_funzionario AND srfp.data_fine_validita IS NULL\n"
            + "INNER JOIN scriva_r_tipo_adempi_profilo AS srtap ON srtap.id_profilo_app = srfp.id_profilo_app\n"
            + "INNER JOIN _replaceTableName_ AS sdta ON sdta.id_tipo_adempimento = srtap.id_tipo_adempimento\n"
            + "INNER JOIN scriva_d_ambito AS sda ON sda.id_ambito = sdta.id_ambito\n"
            + WHERE_FLG_ATTIVI
            + "AND stf.cf_funzionario = :cfFunzionario\n";


    private static final String QUERY_BY_ID = QUERY_TIPI_ADEMPIMENTO
            + "AND sdta.id_tipo_adempimento = :idTipoAdempimento\n";

    private static final String QUERY_TIPI_ADEMPIMENTO_ATTIVI = QUERY_TIPI_ADEMPIMENTO
            + WHERE_FLG_ATTIVI;

    private static final String QUERY_TIPI_ADEMPIMENTO_ATTIVI_WITH_PREFERENCE = "SELECT sdta.*, sdta.id_ambito AS ambito_id, sdta.flg_attivo AS tipo_proc_attivo, \n"
            + "sda.*, sda.flg_attivo AS ambito_attivo, COALESCE(srcp.id_preferenza, 0) AS preference \n"
            + "FROM _replaceTableName_ sdta \n"
            + "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito \n"
            + "LEFT JOIN (SELECT * FROM scriva_r_compilante_preferenza WHERE id_compilante = :idCompilante) srcp ON srcp.id_tipo_adempimento = sdta.id_tipo_adempimento \n"
            + "WHERE 1=1 \n"
            + WHERE_FLG_ATTIVI;

    private static final String QUERY_TIPI_ADEMPIMENTO_BY_ID_AMBITO = QUERY_TIPI_ADEMPIMENTO_ATTIVI
            + WHERE_ID_AMBITO;

    private static final String QUERY_TIPI_ADEMPIMENTO_BY_COD_AMBITO = QUERY_TIPI_ADEMPIMENTO_ATTIVI
            + "AND UPPER(sda.cod_ambito) = UPPER(:codAmbito) ";

    private static final String QUERY_TIPI_ADEMPIMENTO_BY_CODE = QUERY_TIPI_ADEMPIMENTO_ATTIVI
            + "AND UPPER(sdta.cod_tipo_adempimento) = UPPER(:codTipoAdempimento) ";

    private static final String QUERY_UPDATE_UUID_INDEX = "UPDATE _replaceTableName_ "
            + "SET uuid_index=:uuidIndex "
            + "WHERE id_tipo_adempimento=:idTipoAdempimento";

    //TODO da rivedere con BO
    private static final String QUERY_INSERT_TIPO_ADEMPIMENTO = "INSERT INTO _replaceTableName_ "
            + "(id_tipo_adempimento, id_ambito, cod_tipo_adempimento, des_tipo_adempimento, des_estesa_tipo_adempimento, ordinamento_tipo_adempimento, flg_attivo, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) "
            + "VALUES(nextval('<nome_della_sequence>'), :idambito, :codTipoAdempimento, :desTipoAdempimento, :desEstesaTipoAdempimento, :ordinamentoTipoAdempimento, :flgAttivo, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_TIPO_ADEMPIMENTO = "UPDATE _replaceTableName_ "
            + "SET id_ambito=:idambito, cod_tipo_adempimento=:codTipoAdempimento, des_tipo_adempimento=:desTipoAdempimento, des_estesa_tipo_adempimento=:desEstesaTipoAdempimento, ordinamento_tipo_adempimento=:ordinamentoTipoAdempimento, flg_attivo=:flgAttivo, uuid_index=:uuidIndex, gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd "
            + "WHERE id_tipo_adempimento=:idTipoAdempimento";

    private static final String QUERY_DELETE_TIPO_ADEMPIMENTO = "delete from _replaceTableName_ where id_tipo_adempimento = :idTipoAdempimento";

    /**
     * Load tipi adempimento attivi list.
     *
     * @return List<TipoAdempimentoExtendedDTO> list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoAttivi() {
        logBegin(className);
        return findListByQuery(className, QUERY_TIPI_ADEMPIMENTO_ATTIVI + ORDER_BY_ORDINAMENTO, null);
    }

    /**
     * Load tipi adempimento list.
     *
     * @param idAmbito           the id ambito
     * @param idCompilante       the id compilante
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @return the list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimento(Long idAmbito, Long idCompilante, String codTipoAdempimento, String codAmbito, Boolean flgAttivo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = flgAttivo ? QUERY_TIPI_ADEMPIMENTO_ATTIVI : QUERY_TIPI_ADEMPIMENTO;
        if (idCompilante != null) {
            map.put("idCompilante", idCompilante);
            query = QUERY_TIPI_ADEMPIMENTO_ATTIVI_WITH_PREFERENCE;
        } else {
            if (idAmbito != null) {
                map.put("idAmbito", idAmbito);
                query += WHERE_ID_AMBITO;
            }
            if (StringUtils.isNotBlank(codAmbito)) {
                map.put("codAmbito", codAmbito);
                query += WHERE_COD_AMBITO;
            }
            if (StringUtils.isNotBlank(codTipoAdempimento)) {
                map.put("codTipoAdempimento", codTipoAdempimento);
                query += WHERE_COD_TIPO_ADEMPIMENTO;
            }
        }

        return findListByQuery(className, query + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * Load tipi adempimento by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCfFunzionario(String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfFunzionario", cfFunzionario);
        return findListByQuery(className, QUERY_TIPI_ADEMPIMENTO_BY_CF_FUNZIONARIO + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * Load tipi adempimento by cf funzionario list.
     *
     * @param cfFunzionario      the cf funzionario
     * @param idAmbito           the id ambito
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAmbito          the cod ambito
     * @return the list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCfFunzionario(String cfFunzionario, Long idAmbito, String codTipoAdempimento, String codAmbito, Boolean flgAttivo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfFunzionario", cfFunzionario);
        String query = QUERY_TIPI_ADEMPIMENTO_BY_CF_FUNZIONARIO;
        if (idAmbito != null) {
            map.put("idAmbito", idAmbito);
            query += WHERE_ID_AMBITO;
        }
        if (StringUtils.isNotBlank(codAmbito)) {
            map.put("codAmbito", codAmbito);
            query += WHERE_COD_AMBITO;
        }
        if (StringUtils.isNotBlank(codTipoAdempimento)) {
            map.put("codTipoAdempimento", codTipoAdempimento);
            query += WHERE_COD_TIPO_ADEMPIMENTO;
        }
        return findListByQuery(className, query + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * Load tipi adempimento attivi by compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<TipoAdempimentoExtendedDTO> list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoAttiviByCompilante(Long idCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompilante", idCompilante);
        return findListByQuery(className, QUERY_TIPI_ADEMPIMENTO_ATTIVI_WITH_PREFERENCE + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * Load tipi adempimento by id ambito list.
     *
     * @param idAmbito idAmbito
     * @return List<TipoAdempimentoExtendedDTO> list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByIdAmbito(Long idAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAmbito", idAmbito);
        return findListByQuery(className, QUERY_TIPI_ADEMPIMENTO_BY_ID_AMBITO + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * Load tipi adempimento by id ambito cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @param idAmbito      the id ambito
     * @return the list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByIdAmbitoCfFunzionario(String cfFunzionario, Long idAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfFunzionario", cfFunzionario);
        map.put("idAmbito", idAmbito);
        return findListByQuery(className, QUERY_TIPI_ADEMPIMENTO_BY_CF_FUNZIONARIO + WHERE_ID_AMBITO + ORDER_BY_ORDINAMENTO, map);

    }

    /**
     * Load tipi adempimento by code ambito list.
     *
     * @param codAmbito codAmbito
     * @return List<TipoAdempimentoExtendedDTO> list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipiAdempimentoByCodeAmbito(String codAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAmbito", codAmbito);
        return findListByQuery(className, QUERY_TIPI_ADEMPIMENTO_BY_COD_AMBITO, map);
    }

    /**
     * Load tipo adempimento list.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @return List<TipoAdempimentoExtendedDTO> list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipoAdempimento(Long idTipoAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAdempimento", idTipoAdempimento);
        return findListByQuery(className, QUERY_BY_ID, map);
    }

    /**
     * Load tipo adempimento by code list.
     *
     * @param codTipoAdempimento codTipoAdempimento
     * @return List<TipoAdempimentoExtendedDTO> list
     */
    @Override
    public List<TipoAdempimentoExtendedDTO> loadTipoAdempimentoByCode(String codTipoAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoAdempimento", codTipoAdempimento);
        return findListByQuery(className, QUERY_TIPI_ADEMPIMENTO_BY_CODE, map);
    }

    /**
     * Find by pk tipo adempimento dto.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @return TipoAdempimentoDTO tipo adempimento dto
     */
    @Override
    public TipoAdempimentoDTO findByPK(Long idTipoAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAdempimento", idTipoAdempimento);
        return findByPK(className, map);
    }

    /**
     * Save tipo adempimento long.
     *
     * @param tipoAdempimentoDTO TipoAdempimentoDTO
     * @return id record salvato
     */
    @Override
    public Long saveTipoAdempimento(TipoAdempimentoDTO tipoAdempimentoDTO) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idambito", tipoAdempimentoDTO.getIdAmbito());
            map.put("destipoproc", tipoAdempimentoDTO.getDesTipoAdempimento());
            map.put("desestesatipoproc", tipoAdempimentoDTO.getCodTipoAdempimento());
            map.put("attivo", Boolean.TRUE.equals(tipoAdempimentoDTO.getFlgAttivo()) ? 1 : 0);
            map.put("ordintipoproc", tipoAdempimentoDTO.getOrdinamentoTipoAdempimento());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", tipoAdempimentoDTO.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", tipoAdempimentoDTO.getGestAttoreIns());
            map.put("gestUid", generateGestUID(tipoAdempimentoDTO.getGestAttoreIns() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_TIPO_ADEMPIMENTO, null, null), params, keyHolder, new String[]{"id_tipo_adempimento"});
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
     * Update tipo adempimento integer.
     *
     * @param tipoAdempimentoDTO TipoAdempimentoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateTipoAdempimento(TipoAdempimentoDTO tipoAdempimentoDTO) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idTipoAdempimento", tipoAdempimentoDTO.getIdTipoAdempimento());
            map.put("idambito", tipoAdempimentoDTO.getIdAmbito());
            map.put("codTipoAdempimento", tipoAdempimentoDTO.getCodTipoAdempimento());
            map.put("desTipoAdempimento", tipoAdempimentoDTO.getDesTipoAdempimento());
            map.put("desEstesaTipoAdempimento", tipoAdempimentoDTO.getDesEstesaTipoAdempimento());
            map.put("ordinamentoTipoAdempimento", tipoAdempimentoDTO.getOrdinamentoTipoAdempimento());
            map.put("flgAttivo", Boolean.TRUE.equals(tipoAdempimentoDTO.getFlgAttivo()) ? 1 : 0);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", tipoAdempimentoDTO.getGestAttoreIns());
            map.put("uuidIndex", tipoAdempimentoDTO.getUuidIndex());
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_TIPO_ADEMPIMENTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param idTipoAdempimento idTipoAdempimento
     * @param uuidIndex         uuidIndex
     * @return numero record aggiornati
     */
    @Override
    public Integer updateUuidIndex(Long idTipoAdempimento, String uuidIndex) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idTipoAdempimento", idTipoAdempimento);
            map.put("uuidIndex", uuidIndex);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_UUID_INDEX, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete tipo adempimento integer.
     *
     * @param idTipoAdempimento idTipoAdempimento
     * @return numero record cancellati
     */
    @Override
    public Integer deleteTipoAdempimento(Long idTipoAdempimento) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idTipoAdempimento", idTipoAdempimento);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_TIPO_ADEMPIMENTO, null, null), params);
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
     * @return RowMapper<TipoAdempimentoDTO>
     */
    @Override
    public RowMapper<TipoAdempimentoDTO> getRowMapper() throws SQLException {
        return new TipoAdempimentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoAdempimentoExtendedDTO>
     */
    @Override
    public RowMapper<TipoAdempimentoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TipoAdempimentoExtendedRowMapper();
    }

    /**
     * The type Tipo adempimento row mapper.
     */
    public static class TipoAdempimentoRowMapper implements RowMapper<TipoAdempimentoDTO> {

        /**
         * Instantiates a new Tipo adempimento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAdempimentoRowMapper() throws SQLException {
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
        public TipoAdempimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAdempimentoDTO bean = new TipoAdempimentoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoAdempimentoDTO bean) throws SQLException {
            bean.setIdTipoAdempimento(rs.getLong("id_tipo_adempimento"));
            bean.setIdAmbito(rs.getLong("id_ambito"));
            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            bean.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoTipoAdempimento(rs.getInt("ordinamento_tipo_adempimento"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Tipo adempimento extended row mapper.
     */
    public static class TipoAdempimentoExtendedRowMapper implements RowMapper<TipoAdempimentoExtendedDTO> {

        /**
         * Instantiates a new Tipo adempimento extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAdempimentoExtendedRowMapper() throws SQLException {
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
        public TipoAdempimentoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAdempimentoExtendedDTO bean = new TipoAdempimentoExtendedDTO();
            populateBeanExtended(rs, bean);
            return bean;
        }

        private void populateBeanExtended(ResultSet rs, TipoAdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdTipoAdempimento(rs.getLong("id_tipo_adempimento"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            bean.setAmbito(ambito);
            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            bean.setFlgAttivo(1 == rs.getInt("tipo_proc_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoTipoAdempimento(rs.getInt("ordinamento_tipo_adempimento"));
            bean.setPreferito(rs.getLong("preference"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }
    }

}