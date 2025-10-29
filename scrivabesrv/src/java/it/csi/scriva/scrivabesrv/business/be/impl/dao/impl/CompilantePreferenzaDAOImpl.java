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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilantePreferenzaDAO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
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
 * The type Compilante preferenza dao.
 *
 * @author CSI PIEMONTE
 */
public class CompilantePreferenzaDAOImpl extends ScrivaBeSrvGenericDAO<CompilantePreferenzaDTO> implements CompilantePreferenzaDAO {
    //TODO rivedere le query per adempimento

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_COMPILANTI_PREFERENZE = "SELECT srcp.*, srcp.gest_uid AS uid, " +
            "stc.*, stc.id_compilante AS compilante_id, " +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, " +
            "sda.*, sda.id_ambito AS ambito_id " +
            "FROM  _replaceTableName_ srcp  " +
            "INNER JOIN scriva_t_compilante stc on srcp.id_compilante = stc.id_compilante " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON srcp.id_tipo_adempimento = sdta.id_tipo_adempimento " +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sdta.id_ambito ";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_COMPILANTI_PREFERENZE + "WHERE id_preferenza = :idPreferenza";

    private static final String QUERY_LOAD_COMPILANTE_PREFERENZE_BY_ID_COMPILANTE = QUERY_LOAD_COMPILANTI_PREFERENZE
            + "WHERE srcp.id_compilante = :idCompilante";

    private static final String QUERY_LOAD_COMPILANTE_PREFERENZE_BY_CF_COMPILANTE = QUERY_LOAD_COMPILANTI_PREFERENZE
            + "WHERE stc.cf_compilante = :cfCompilante";

    private static final String QUERY_LOAD_COMPILANTE_PREFERENZE_BY_ID_COMPILANTE_AND_ID_TIPO_ADEMPIMENTO = QUERY_LOAD_COMPILANTI_PREFERENZE
            + "WHERE srcp.id_compilante = :idCompilante and srcp.id_tipo_adempimento = :idTipoAdempimento";

    private static final String QUERY_INSERT_COMPILANTE_PREFERENZA = "INSERT INTO _replaceTableName_ "
            + "(id_preferenza, id_compilante, id_tipo_adempimento, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd) "
            + "VALUES(nextval('seq_scriva_r_compilante_preferenza'), :idCompilante, :idTipoAdempimento, :dateIns, :attoreIns, :dateUpd, :attoreUpd) ";

    private static final String QUERY_UPDATE_COMPILANTE_PREFERENZA = "UPDATE _replaceTableName_ "
            + "SET id_compilante = :idCompilante, id_tipo_adempimento = :idTipoAdempimento, gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd "
            + "WHERE id_preferenza = :idPreferenza ";

    private static final String QUERY_DELETE_COMPILANTE_PREFERENZA = "DELETE FROM _replaceTableName_ WHERE id_preferenza = :idPreferenza";

    /**
     * Load compilanti preferenze list.
     *
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    @Override
    public List<CompilantePreferenzaExtendedDTO> loadCompilantiPreferenze() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_COMPILANTI_PREFERENZE, null);
    }

    /**
     * Load compilante preferenza list.
     *
     * @param idPreferenza idPreferenza
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    @Override
    public List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenza(Long idPreferenza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPreferenza", idPreferenza);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load compilante preferenze by compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    @Override
    public List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenzeByCompilante(Long idCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompilante", idCompilante);
        return findListByQuery(className, QUERY_LOAD_COMPILANTE_PREFERENZE_BY_ID_COMPILANTE, map);
    }

    /**
     * Load compilante preferenze by codice fiscale list.
     *
     * @param codiceFiscale codiceFiscale
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    @Override
    public List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenzeByCodiceFiscale(String codiceFiscale) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfCompilante", codiceFiscale);
        return findListByQuery(className, QUERY_LOAD_COMPILANTE_PREFERENZE_BY_CF_COMPILANTE, map);
    }

    /**
     * Load compilante preferenze by tipo adempimento list.
     *
     * @param idCompilante      idCompilante
     * @param idTipoAdempimento idTipoAdempimento
     * @return List<CompilantePreferenzaExtendedDTO> list
     */
    @Override
    public List<CompilantePreferenzaExtendedDTO> loadCompilantePreferenzeByTipoAdempimento(Long idCompilante, Long idTipoAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompilante", idCompilante);
        map.put("idTipoAdempimento", idTipoAdempimento);
        return findListByQuery(className, QUERY_LOAD_COMPILANTE_PREFERENZE_BY_ID_COMPILANTE_AND_ID_TIPO_ADEMPIMENTO, map);
    }

    /**
     * Save compilante preferenza long.
     *
     * @param dto CompilantePreferenzaDTO
     * @return id record inserito
     */
    @Override
    public Long saveCompilantePreferenza(CompilantePreferenzaDTO dto) {
        logBegin(className);
        try {
            Date now = Calendar.getInstance().getTime();
            Map<String, Object> map = new HashMap<>();
            map.put("idCompilante", dto.getIdCompilante());
            map.put("idTipoAdempimento", dto.getIdTipoAdempimento());
            map.put("dateIns", now);
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreIns());
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_COMPILANTE_PREFERENZA, null, null), params, keyHolder, new String[]{"id_preferenza"});
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
     * Delete compilante preferenza integer.
     *
     * @param idPreferenza idPreferenza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteCompilantePreferenza(Long idPreferenza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idPreferenza", idPreferenza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_COMPILANTE_PREFERENZA, null, null), params);
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
     * @return RowMapper<CompilantePreferenzaDTO>
     */
    @Override
    public RowMapper<CompilantePreferenzaDTO> getRowMapper() throws SQLException {
        return new CompilantePreferenzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<CompilantePreferenzaExtendedDTO>
     */
    @Override
    public RowMapper<CompilantePreferenzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new CompilantePreferenzaExtendedRowMapper();
    }

    /**
     * The type Compilante preferenza row mapper.
     */
    public static class CompilantePreferenzaRowMapper implements RowMapper<CompilantePreferenzaDTO> {

        /**
         * Instantiates a new Compilante preferenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompilantePreferenzaRowMapper() throws SQLException {
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
        public CompilantePreferenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompilantePreferenzaDTO bean = new CompilantePreferenzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CompilantePreferenzaDTO bean) throws SQLException {
            bean.setIdPreferenza(rs.getLong("id_preferenza"));
            bean.setIdCompilante(rs.getLong("id_compilante"));
            bean.setIdTipoAdempimento(rs.getLong("id_tipo_messaggio"));
        }
    }

    /**
     * The type Compilante preferenza extended row mapper.
     */
    public static class CompilantePreferenzaExtendedRowMapper implements RowMapper<CompilantePreferenzaExtendedDTO> {

        /**
         * Instantiates a new Compilante preferenza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompilantePreferenzaExtendedRowMapper() throws SQLException {
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
        public CompilantePreferenzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompilantePreferenzaExtendedDTO bean = new CompilantePreferenzaExtendedDTO();
            populateExtendedBean(rs, bean);
            return bean;
        }

        private void populateExtendedBean(ResultSet rs, CompilantePreferenzaExtendedDTO bean) throws SQLException {
            bean.setIdPreferenza(rs.getLong("id_preferenza"));

            CompilanteDTO compilante = new CompilanteDTO();
            populateBeanCompilante(rs, compilante);
            bean.setCompilante(compilante.getIdCompilante() != null ? compilante : null);

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            populateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);
        }

        private void populateBeanCompilante(ResultSet rs, CompilanteDTO bean) throws SQLException {
            bean.setIdCompilante(rs.getLong("compilante_id"));
            bean.setCodiceFiscaleCompilante(rs.getString("cf_compilante"));
            bean.setCognomeCompilante(rs.getString("cognome_compilante"));
            bean.setNomeCompilante(rs.getString("nome_compilante"));
        }

        private void populateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));

        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }
    }
}