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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfiguraRuoloSoggettoDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.ConfiguraRuoloSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.ConfiguraRuoloSoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RuoloCompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.RuoloSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Configura ruolo soggetto dao.
 */
public class ConfiguraRuoloSoggettoDAOImpl extends ScrivaBeSrvGenericDAO<ConfiguraRuoloSoggettoDTO> implements ConfiguraRuoloSoggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_CONFIGURA_RUOLI_SOGGETTI = "SELECT sdrc.*, sdrc.id_ruolo_compilante AS ruolo_compilante_id, " +
            "sdrs.*, sdrs.id_ruolo_soggetto AS ruolo_soggetto_id, " +
            "sdad.*, sdad.id_adempimento AS adempimento_id, " +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, " +
            "sda.*, sda.id_ambito AS ambito_id " +
            "FROM _replaceTableName_ srcrs " +
            "INNER JOIN scriva_d_adempimento sdad ON srcrs.id_adempimento = sdad.id_adempimento " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento " +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito  " +
            "INNER JOIN scriva_d_ruolo_compilante sdrc ON srcrs.id_ruolo_compilante = sdrc.id_ruolo_compilante " +
            "INNER JOIN scriva_d_ruolo_soggetto sdrs ON srcrs.id_ruolo_soggetto = sdrs.id_ruolo_soggetto ";

    private static final String QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_RUOLO_COMPILANTE = QUERY_CONFIGURA_RUOLI_SOGGETTI +
            "WHERE srcrs.id_ruolo_compilante = :idRuoloCompilante";

    private static final String QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_ADEMPIMENTO = QUERY_CONFIGURA_RUOLI_SOGGETTI +
            "WHERE srcrs.id_adempimento = :idAdempimento";

    private static final String QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_RUOLO_SOGGETTO = QUERY_CONFIGURA_RUOLI_SOGGETTI +
            "WHERE srcrs.id_ruolo_soggetto = :idRuoloSoggetto";

    private static final String QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_ADEMPIMENTO_AND_RUOLO_COMPILANTE = QUERY_CONFIGURA_RUOLI_SOGGETTI +
            "WHERE srcrs.id_adempimento = :idAdempimento AND srcrs.id_ruolo_compilante = :idRuoloCompilante";

    private static final String QUERY_INSERT_CONFIGURA_RUOLO_SOGGETTO = "INSERT INTO _replaceTableName_ (id_ruolo_soggetto, id_adempimento, id_ruolo_compilante, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES(:idRuoloSoggetto, :idAdempimento, :idRuoloCompilante, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " WHERE (DATE(srcrs.data_inizio_validita) <= DATE(NOW()) AND (srcrs.data_fine_validita IS NULL OR DATE(srcrs.data_fine_validita)>= DATE(NOW())))\n ";

    /**
     * Load configura ruoli soggetti list.
     *
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    @Override
    public List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggetti() {
        logBegin(className);
        return findListByQuery(className, QUERY_CONFIGURA_RUOLI_SOGGETTI + WHERE_DATA_INIZIO_FINE, null);
    }

    /**
     * Load configura ruoli soggetto by ruolo compilante list.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    @Override
    public List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettoByRuoloCompilante(Long idRuoloCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRuoloCompilante", idRuoloCompilante);
        return findListByQuery(className, QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_RUOLO_COMPILANTE, map);
    }

    /**
     * Load configura ruoli soggetti by adempimento list.
     *
     * @param idAdempimento idAdempimento
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    @Override
    public List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettiByAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_ADEMPIMENTO, map);
    }

    /**
     * Load configura ruoli soggetti by ruolo soggetto list.
     *
     * @param idRuoloSoggetto idRuoloSoggetto
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    @Override
    public List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettiByRuoloSoggetto(Long idRuoloSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRuoloSoggetto", idRuoloSoggetto);
        return findListByQuery(className, QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_RUOLO_SOGGETTO, map);
    }

    /**
     * Load configura ruoli soggetti by adempimento ruolo compilante list.
     *
     * @param idAdempimento     idAdempimento
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<ConfiguraRuoloSoggettoExtendedDTO> list
     */
    @Override
    public List<ConfiguraRuoloSoggettoExtendedDTO> loadConfiguraRuoliSoggettiByAdempimentoRuoloCompilante(Long idAdempimento, Long idRuoloCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idRuoloCompilante", idRuoloCompilante);
        return findListByQuery(className, QUERY_CONFIGURA_RUOLI_SOGGETTI_BY_ADEMPIMENTO_AND_RUOLO_COMPILANTE, map);
    }

    /**
     * Save configura ruolo soggetto integer.
     *
     * @param dto ConfiguraRuoloSoggettoDTO
     * @return id del record inserito
     */
    @Override
    public Integer saveConfiguraRuoloSoggetto(ConfiguraRuoloSoggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idRuoloSoggetto", dto.getIdRuoloSoggetto());
            map.put("idAdempimento", dto.getIdAdempimento());
            map.put("idRuoloCompilante", dto.getIdRuoloCompilante());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getGestAttoreIns() + now));

            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_INSERT_CONFIGURA_RUOLO_SOGGETTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update configura ruolo soggetto integer.
     *
     * @param configuraRuoloSoggettoDTO ConfiguraRuoloSoggettoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateConfiguraRuoloSoggetto(ConfiguraRuoloSoggettoDTO configuraRuoloSoggettoDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Delete configura ruolo soggetto integer.
     *
     * @param configuraRuoloSoggettoDTO ConfiguraRuoloSoggettoDTO
     * @return numero record cancellati
     */
    @Override
    public Integer deleteConfiguraRuoloSoggetto(ConfiguraRuoloSoggettoDTO configuraRuoloSoggettoDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<ConfiguraRuoloSoggettoDTO>
     */
    @Override
    public RowMapper<ConfiguraRuoloSoggettoDTO> getRowMapper() throws SQLException {
        return new ConfiguraRuoloSoggettoRowMapper();
    }

    /**
     * The type Configura ruolo soggetto row mapper.
     */
    public static class ConfiguraRuoloSoggettoRowMapper implements RowMapper<ConfiguraRuoloSoggettoDTO> {

        /**
         * Instantiates a new Configura ruolo soggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfiguraRuoloSoggettoRowMapper() throws SQLException {
            // Instatiate class
        }

        @Override
        public ConfiguraRuoloSoggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfiguraRuoloSoggettoDTO bean = new ConfiguraRuoloSoggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfiguraRuoloSoggettoDTO bean) throws SQLException {
            bean.setIdRuoloCompilante(rs.getLong("id_ruolo_compilante"));
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setIdRuoloSoggetto(rs.getLong("id_ruolo_soggetto"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    @Override
    public RowMapper<ConfiguraRuoloSoggettoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new ConfiguraRuoloSoggettoExtendedRowMapper();
    }

    /**
     * The type Configura ruolo soggetto extended row mapper.
     */
    public static class ConfiguraRuoloSoggettoExtendedRowMapper implements RowMapper<ConfiguraRuoloSoggettoExtendedDTO> {

        /**
         * Instantiates a new Configura ruolo soggetto extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfiguraRuoloSoggettoExtendedRowMapper() throws SQLException {
            // Instatiate class
        }

        @Override
        public ConfiguraRuoloSoggettoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfiguraRuoloSoggettoExtendedDTO bean = new ConfiguraRuoloSoggettoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfiguraRuoloSoggettoExtendedDTO bean) throws SQLException {
            RuoloCompilanteDTO ruoloCompilante = new RuoloCompilanteDTO();
            populateBeanRuoloCompilante(rs, ruoloCompilante);
            bean.setRuoloCompilante(ruoloCompilante.getIdRuoloCompilante() != null ? ruoloCompilante : null);

            RuoloSoggettoDTO ruoloSoggetto = new RuoloSoggettoDTO();
            populateBeanRuoloSoggetto(rs, ruoloSoggetto);
            bean.setRuoloSoggetto(ruoloSoggetto.getIdRuoloSoggetto() != null ? ruoloSoggetto : null);

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento.getIdAdempimento() != null ? adempimento : null);
        }

        private void populateBeanAdempimento(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
        }

        private void pupulateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }

        private void populateBeanRuoloCompilante(ResultSet rs, RuoloCompilanteDTO bean) throws SQLException {
            bean.setIdRuoloCompilante(rs.getLong("ruolo_compilante_id"));
            bean.setCodiceRuoloCompilante(rs.getString("cod_ruolo_compilante"));
            bean.setDescrizioneRuoloCompilante(rs.getString("des_ruolo_compilante"));
        }

        private void populateBeanRuoloSoggetto(ResultSet rs, RuoloSoggettoDTO bean) throws SQLException {
            bean.setIdRuoloSoggetto(rs.getLong("ruolo_soggetto_id"));
            bean.setCodiceRuoloSoggetto(rs.getString("cod_ruolo_soggetto"));
            bean.setDescrizioneRuoloSoggetto(rs.getString("des_ruolo_soggetto"));
        }

    }

}