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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigGeecoDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigGeecoDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Config geeco dao.
 *
 * @author CSI PIEMONTE
 */
public class ConfigGeecoDAOImpl extends ScrivaBeSrvGenericDAO<ConfigGeecoDTO> implements ConfigGeecoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "select * from _replaceTableName_ where id_config_geeco = :idCOnfigGeeco";

    private static final String QUERY_SELECT_CONFIGURAZIONE = "SELECT c.uuid_geeco, c.env_geeco, c.version_geeco, r.json_startupinfo, r.json_editinglayers, r.json_quitinfo " +
            "FROM scriva_r_adempi_ruolo_app r " +
            "JOIN scriva_d_config_geeco c ON r.id_config_geeco = c.id_config_geeco " +
            "WHERE r.id_adempimento = :idAdempimento and r.id_ruolo_applicativo = :idRuoloApplicativo";

    private static final String QUERY_SELECT_NEW_CONFIGURAZIONE = "SELECT sdcg.uuid_geeco, sdcg.env_geeco, sdcg.version_geeco, srara.json_startupinfo, srara.json_editinglayers, srara.json_quitinfo \n" +
            "FROM scriva_r_adempi_ruolo_app srara \n" +
            "INNER JOIN scriva_d_config_geeco sdcg ON sdcg.id_config_geeco = srara.id_config_geeco\n" +
            "INNER JOIN scriva_d_gestione_attore sdga ON sdga.id_gestione_attore = srara.id_gestione_attore\n" +
            "WHERE srara.id_adempimento = :idAdempimento\n" +
            "AND sdga.cod_gestione_attore = CASE WHEN :codGestioneAttore NOT IN ('WRITE', 'READ_ONLY') THEN 'READ_ONLY' ELSE :codGestioneAttore END";
            // "AND sdga.cod_gestione_attore  = :codGestioneAttore";


    /*--------------------------------------------------------------------------
    LOGICA DELLA QUERY 


        Recupera la configurazione GEECO collegata a un adempimento
        (`:idAdempimento`) solo se l’attore corrente possiede almeno i permessi
        richiesti (`:codGestioneAttore`).

        PROBLEMA
        :codGestioneAttore può contenere:
        - un singolo codice ― es.  WRITE  o  READ_ONLY o WRITE_LOCK
        - codici "QDR_…", "LOCK_QDR_…", 
        - più codici concatenati da '|' (es. "QDR_ALLEGATO|QDR_OGGETTO")
            *tutti* appartenenti allo stesso gruppo (solo QDR_… o solo LOCK_QDR_…).

        Occorre ridurre questi valori alla possibilità di scrittura o sola lettura su GEECO (config: readonly= true/false)
        secondo le regole della gestione attore

        ─────────────────────────────────────────────────────────────────────
        Vedi jira 1612 e jira 1615
            WRITE                                -> WRITE
            READ_ONLY                            -> READ_ONLY
            WRITE_LOCK                           -> READ_ONLY
            LOCK_QDR_SOGGETTO	                 -> WRITE
            QDR_ALLEGATO	                     -> READ_ONLY
            QDR_OGGETTO	                         -> WRITE
            LOCK_QDR_OGGETTO	                 -> READ_ONLY
            QDR_<QUADRI DIVERSI DA OGGETTO>	     -> READ_ONLY
            LOCK_<QUADRI DIVERSI DA OGGETTO>     -> WRITE     
        GRUPPO QDR_<…>
            - se presente QDR_OGGETTO            -> WRITE
            - altrimenti                         -> READ_ONLY
        GRUPPO LOCK_QDR_<…>
            - se presente LOCK_QDR_OGGETTO       -> READ_ONLY
            - altrimenti                         -> WRITE
        GRUPPI MISTI (LOCK_ + QDR_)              -> READ_ONLY (fallback di sicurezza in quanto non previsti e potenzialmente conflittuali)
        ─────────────────────────────────────────────────────────────────────

    STEP DELLA SUB-QUERY
        1) 'vals'           divide la stringa in un array, poi in più righe (UNNEST).
        2) 'info'           calcola 7 flag booleani con 'bool_or':
            has_write, has_read_only, has_write_lock,
            any_lock (almeno un LOCK_QDR_…),
            any_qdr  (almeno un QDR_…),
            has_lock_oggetto, has_qdr_oggetto.
        3) 'CASE' finale    applica le regole di precedenza:
            a. gruppi misti (non dovrebbero essere possibili) -> quindi READ_ONLY
            b. literal READ_ONLY / WRITE_LOCK hanno priorità
            c. literal WRITE vince se senza conflitti
            d. gruppo LOCK_QDR -> WRITE   salvo che nn sia LOCK_QDR_OGGETTO
            e. gruppo QDR_     -> READ_ONLY salvo che nn sia QDR_OGGETTO



        TEST CASE

         GESTIONE									READONLY ATTESO		
 
        'WRITE'										false	               
        'QDR_SOGGETTO'								true		
        'WRITE_LOCK'								true		
        'LOCK_QDR_SOGGETTO'							false					
        'QDR_ALLEGATO'								true		
        'READ_ONLY'									true		
        'QDR_OGGETTO'								false		
        'LOCK_QDR_OGGETTO'							true		
        'QDR_ALLEGATO|QDR_OGGETTO'					false		
        'QDR_ALLEGATO|QDR_SOGGETTO'					true					
        'LOCK_QDR_ALLEGATO|LOCK_QDR_OGGETTO'		true		
        'LOCK_QDR_ALLEGATO|LOCK_QDR_SOGGETTO'		false					
        'LOCK_QDR_ALLEGATO|QDR_SOGGETTO'			true		
    --------------------------------------------------------------------------*/
    private static final String QUERY_SELECT_CONFIGURAZIONE_ADVANCED =
            "SELECT sdcg.uuid_geeco, sdcg.env_geeco, sdcg.version_geeco, "
            + "srara.json_startupinfo, srara.json_editinglayers, srara.json_quitinfo \n"
            + "FROM scriva_r_adempi_ruolo_app srara \n"
            + "INNER JOIN scriva_d_config_geeco sdcg "
            + "  ON sdcg.id_config_geeco = srara.id_config_geeco \n"
            + "INNER JOIN scriva_d_gestione_attore sdga "
            + "  ON sdga.id_gestione_attore = srara.id_gestione_attore \n"
            + "WHERE srara.id_adempimento = :idAdempimento \n"
            + "  AND sdga.cod_gestione_attore = ( \n"
            + "        WITH vals AS ( \n"
            + "              SELECT unnest(string_to_array(:codGestioneAttore, '|')) AS v \n"
            + "        ), \n"
            + "        info AS ( \n"
            + "              SELECT \n"
            + "                  bool_or(v = 'WRITE')                AS has_write, \n"
            + "                  bool_or(v = 'READ_ONLY')            AS has_read_only, \n"
            + "                  bool_or(v = 'WRITE_LOCK')           AS has_write_lock, \n"
            + "                  bool_or(v LIKE 'LOCK_%')            AS any_lock, \n"
            + "                  bool_or(v LIKE 'QDR_%')             AS any_qdr, \n"
            + "                  bool_or(v = 'LOCK_QDR_OGGETTO')     AS has_lock_oggetto, \n"
            + "                  bool_or(v = 'QDR_OGGETTO')          AS has_qdr_oggetto \n"
            + "              FROM vals \n"
            + "        ) \n"
            + "        SELECT CASE \n"
            + "                 WHEN (any_lock AND any_qdr)                 THEN 'READ_ONLY' \n"
            + "                 WHEN has_read_only OR has_write_lock        THEN 'READ_ONLY' \n"
            + "                 WHEN has_write                              THEN 'WRITE' \n"
            + "                 WHEN any_lock                               THEN CASE \n"
            + "                                                                  WHEN has_lock_oggetto THEN 'READ_ONLY' \n"
            + "                                                                  ELSE 'WRITE' \n"
            + "                                                               END \n"
            + "                 ELSE /* gruppo QDR */ CASE \n"
            + "                                           WHEN has_qdr_oggetto THEN 'WRITE' \n"
            + "                                           ELSE 'READ_ONLY' \n"
            + "                                      END \n"
            + "               END \n"
            + "        FROM info \n"
            + "  )";

    /**
     * Gets config.
     *
     * @param idRuoloApplicativo idRuoloApplicativo
     * @param idAdempimento      idAdempimento
     * @return ConfigGeecoDTO config
     */
    @Override
    public ConfigGeecoDTO getConfig(Integer idRuoloApplicativo, Long idAdempimento) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAdempimento", idAdempimento);
            map.put("idRuoloApplicativo", idRuoloApplicativo);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(QUERY_SELECT_CONFIGURAZIONE, params, getRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets config.
     *
     * @param codGestioneAttore the cod gestione attore
     * @param idAdempimento     the id adempimento
     * @return the config
     */
    @Override
    public ConfigGeecoDTO getConfig(String codGestioneAttore, Long idAdempimento) {
        logBeginInfo(className, "idAdempimento : [" + idAdempimento + "] - codGestioneAttore : [" + codGestioneAttore + "]");
        Map<String, Object> map = new HashMap<>();
        map.put("codGestioneAttore", codGestioneAttore);
        map.put("idAdempimento", idAdempimento);
        return findSimpleDTOByQuery(className, QUERY_SELECT_CONFIGURAZIONE_ADVANCED, map);
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
    public RowMapper<ConfigGeecoDTO> getRowMapper() throws SQLException {
        return new ConfigGeecoDTORowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<ConfigGeecoDTO> getExtendedRowMapper() throws SQLException {
        return new ConfigGeecoDTORowMapper();
    }


    /**
     * The type Config geeco dto row mapper.
     */
    public static class ConfigGeecoDTORowMapper implements RowMapper<ConfigGeecoDTO> {

        /**
         * Instantiates a new Config geeco dto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfigGeecoDTORowMapper() throws SQLException {
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
        public ConfigGeecoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfigGeecoDTO bean = new ConfigGeecoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfigGeecoDTO bean) throws SQLException {
            bean.setEnvGeeco(rs.getString("env_geeco"));
            bean.setJsonEditinglayers(rs.getString("json_editinglayers"));
            bean.setJsonQuitinfo(rs.getString("json_quitinfo"));
            bean.setJsonStartupinfo(rs.getString("json_startupinfo"));
            bean.setUuidGeeco(rs.getString("uuid_geeco"));
            bean.setVersionGeeco(rs.getString("version_geeco"));
        }
    }


}