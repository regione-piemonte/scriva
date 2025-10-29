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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoRuoloCompilanteDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoRuoloCompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoRuoloCompilanteExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.RuoloCompilanteDTO;
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
 * The type Adempimento ruolo compilante dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoRuoloCompilanteDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoRuoloCompilanteDTO> implements AdempimentoRuoloCompilanteDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_CONDITION = "ORDER BY srarc.id_ruolo_compilante";
    
    private static final String ORDER_BY_ORDINAMENTO = "ORDER BY srarc.ordinamento";
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " WHERE (DATE(srarc.data_inizio_validita) <= DATE(NOW()) AND (srarc.data_fine_validita IS NULL OR DATE(srarc.data_fine_validita)>= DATE(NOW())))\n ";

    private static final String QUERY_ADEMPIMENTI_RUOLO_COMPILANTE = "SELECT srarc.*, " +
            "sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo, " +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo, " +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo, " +
            "sdrc.*, sdrc.id_ruolo_compilante AS ruolo_compilante_id " +
            "FROM _replaceTableName_ srarc " +
            "INNER JOIN scriva_d_adempimento sdad ON srarc.id_adempimento = sdad.id_adempimento " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento " +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito " +
            "INNER JOIN scriva_d_ruolo_compilante sdrc on srarc.id_ruolo_compilante = sdrc.id_ruolo_compilante ";

    private static final String QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_ORDER_BY = QUERY_ADEMPIMENTI_RUOLO_COMPILANTE + WHERE_DATA_INIZIO_FINE + ORDER_BY_ORDINAMENTO;

    private static final String QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_BY_RUOLO_COMPILANTE = QUERY_ADEMPIMENTI_RUOLO_COMPILANTE
            + "WHERE srarc.id_ruolo_compilante = :idRuoloCompilante "
            + ORDER_BY_CONDITION;

    private static final String QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_BY_ADEMPIMENTO = QUERY_ADEMPIMENTI_RUOLO_COMPILANTE
            + "WHERE srarc.id_adempimento = :idAdempimento ";
            

    private static final String QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_BY_RUOLO_COMPILANTE_ADEMPIMENTO = QUERY_ADEMPIMENTI_RUOLO_COMPILANTE
            + "WHERE srarc.id_ruolo_compilante = :idRuoloCompilante AND srarc.id_adempimento = :idAdempimento "
            + ORDER_BY_CONDITION;

    private static final String QUERY_INSERT_ADEMPIMENTO_RUOLO_COMPILANTE = "INSERT INTO _replaceTableName_ "
            + "(id_ruolo_compilante, id_adempimento, flg_popola_richiedente, flg_modulo_delega, flg_modulo_procura, flg_ruolo_default, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) "
            + "VALUES(:ruoloCompilante, :idAdempimento, :popolaRichiedente, :delega, :procura, :default, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_ADEMPIMENTO_RUOLO_COMPILANTE = "UPDATE _replaceTableName_ "
            + "SET flg_popola_richiedente = :popola, flg_modulo_delega = :delega,  flg_modulo_procura = :procura, , flg_ruolo_default = :default, gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd "
            + "WHERE id_ruolo_compilante = :ruoloCompilante AND id_adempimento = :idAdempimento;";

    private static final String WHERE_COMPONENTE = " AND srarc.ind_visibile LIKE '%'||:codComponenteApp||'%'\n";
    
    
    /**
     * Gets adempimenti ruoli compilante.
     *
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimenti ruoli compilante
     */
    @Override
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentiRuoliCompilante() {
        logBegin(className);
        return findListByQuery(className, QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_ORDER_BY, null);
    }

    /**
     * Gets adempimento ruolo compilante by ruolo compilante.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimento ruolo compilante by ruolo compilante
     */
    @Override
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentoRuoloCompilanteByRuoloCompilante(Long idRuoloCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRuoloCompilante", idRuoloCompilante);
        return findListByQuery(className, QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_BY_RUOLO_COMPILANTE, map);
    }

    /**
     * Gets adempimento ruolo compilante by adempimento.
     *
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimento ruolo compilante by adempimento
     */
    @Override
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentoRuoloCompilanteByAdempimento(Long idAdempimento, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        String query = QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_BY_ADEMPIMENTO;
        
        if (codComponenteApp != null) {
        	map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE;
        }
        
        return findListByQuery(className, query + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * Gets adempimento ruolo compilante by ruolo compilante adempimento.
     *
     * @param idRuoloCompilante idRuoloCompilante
     * @param idAdempimento     idAdempimento
     * @return List<AdempimentoRuoloCompilanteExtendedDTO> adempimento ruolo compilante by ruolo compilante adempimento
     */
    @Override
    public List<AdempimentoRuoloCompilanteExtendedDTO> getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(Long idRuoloCompilante, Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idRuoloCompilante", idRuoloCompilante);
        return findListByQuery(className, QUERY_ADEMPIMENTI_RUOLO_COMPILANTE_BY_RUOLO_COMPILANTE_ADEMPIMENTO, map);
    }

    /**
     * Save adempimento ruolo compilante integer.
     *
     * @param adempimentoRuoloCompilanteDTO AdempimentoRuoloCompilanteDTO
     * @return id record salvato
     */
    @Override
    public Integer saveAdempimentoRuoloCompilante(AdempimentoRuoloCompilanteDTO adempimentoRuoloCompilanteDTO) {
        logBegin(className);
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            Date now = Calendar.getInstance().getTime();
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("ruoloCompilante", adempimentoRuoloCompilanteDTO.getIdRuoloCompilante());
            map.put("idAdempimento", adempimentoRuoloCompilanteDTO.getIdAdempimento());
            map.put("popola", Boolean.TRUE.equals(adempimentoRuoloCompilanteDTO.getFlgPopolaRichiedente()) ? 1 : 0);
            map.put("delega", Boolean.TRUE.equals(adempimentoRuoloCompilanteDTO.getFlgModuloDelega()) ? 1 : 0);
            map.put("procura", Boolean.TRUE.equals(adempimentoRuoloCompilanteDTO.getFlgModuloProcura()) ? 1 : 0);
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", adempimentoRuoloCompilanteDTO.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", adempimentoRuoloCompilanteDTO.getGestAttoreIns());
            map.put("gestUid", generateGestUID(adempimentoRuoloCompilanteDTO.getGestAttoreIns() + now));
            params = getParameterValue(map);

            return template.update(getQuery(QUERY_INSERT_ADEMPIMENTO_RUOLO_COMPILANTE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update adempimento ruolo compilante integer.
     *
     * @param adempimentoRuoloCompilanteDTO AdempimentoRuoloCompilanteDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateAdempimentoRuoloCompilante(AdempimentoRuoloCompilanteDTO adempimentoRuoloCompilanteDTO) {
        logBegin(className);
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("ruoloCompilante", adempimentoRuoloCompilanteDTO.getIdRuoloCompilante());
            map.put("idAdempimento", adempimentoRuoloCompilanteDTO.getIdAdempimento());
            map.put("popola", Boolean.TRUE.equals(adempimentoRuoloCompilanteDTO.getFlgPopolaRichiedente()) ? 1 : 0);
            map.put("delega", Boolean.TRUE.equals(adempimentoRuoloCompilanteDTO.getFlgModuloDelega()) ? 1 : 0);
            map.put("procura", Boolean.TRUE.equals(adempimentoRuoloCompilanteDTO.getFlgModuloProcura()) ? 1 : 0);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", adempimentoRuoloCompilanteDTO.getGestAttoreIns());
            params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_ADEMPIMENTO_RUOLO_COMPILANTE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RowMapper<AdempimentoRuoloCompilanteDTO> getRowMapper() throws SQLException {
        return new AdempimentoRuoloCompilanteRowMapper();
    }

    @Override
    public RowMapper<AdempimentoRuoloCompilanteExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoRuoloCompilanteExtendedRowMapper();
    }

    /**
     * The type Adempimento ruolo compilante row mapper.
     */
    public static class AdempimentoRuoloCompilanteRowMapper implements RowMapper<AdempimentoRuoloCompilanteDTO> {

        /**
         * Instantiates a new Adempimento ruolo compilante row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoRuoloCompilanteRowMapper() throws SQLException {
            // Instatiate class
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
        public AdempimentoRuoloCompilanteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoRuoloCompilanteDTO bean = new AdempimentoRuoloCompilanteDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoRuoloCompilanteDTO bean) throws SQLException {
            bean.setIdRuoloCompilante(rs.getLong("id_ruolo_compilante"));
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setFlgPopolaRichiedente(1 == rs.getInt("flg_popola_richiedente") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgModuloDelega(1 == rs.getInt("flg_modulo_delega") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgModuloProcura(1 == rs.getInt("flg_modulo_procura") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgRuoloDefault(1 == rs.getInt("flg_ruolo_default") ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

    }

    /**
     * The type Adempimento ruolo compilante extended row mapper.
     */
    public static class AdempimentoRuoloCompilanteExtendedRowMapper implements RowMapper<AdempimentoRuoloCompilanteExtendedDTO> {

        /**
         * Instantiates a new Adempimento ruolo compilante extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoRuoloCompilanteExtendedRowMapper() throws SQLException {
            // Instatiate class
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
        public AdempimentoRuoloCompilanteExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoRuoloCompilanteExtendedDTO bean = new AdempimentoRuoloCompilanteExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoRuoloCompilanteExtendedDTO bean) throws SQLException {
            bean.setFlgPopolaRichiedente(1 == rs.getInt("flg_popola_richiedente") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgModuloDelega(1 == rs.getInt("flg_modulo_delega") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgModuloProcura(1 == rs.getInt("flg_modulo_procura") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgRuoloDefault(1 == rs.getInt("flg_ruolo_default") ? Boolean.TRUE : Boolean.FALSE);

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimentoExtended(rs, adempimento);
            bean.setAdempimento(adempimento.getIdAdempimento() != null ? adempimento : null);

            RuoloCompilanteDTO ruoloCompilante = new RuoloCompilanteDTO();
            populateBeanRuoloCompilante(rs, ruoloCompilante);
            bean.setRuoloCompilante(ruoloCompilante);
            
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanAdempimentoExtended(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
        }

        private void pupulateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
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

        private void populateBeanRuoloCompilante(ResultSet rs, RuoloCompilanteDTO bean) throws SQLException {
            bean.setIdRuoloCompilante(rs.getLong("ruolo_compilante_id"));
            bean.setCodiceRuoloCompilante(rs.getString("cod_ruolo_compilante"));
            bean.setDescrizioneRuoloCompilante(rs.getString("des_ruolo_compilante"));
        }

    }

}