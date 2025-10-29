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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAbilitazioneDAO;
import it.csi.scriva.scrivabesrv.dto.TipoAbilitazioneDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo abilitazione dao.
 */
public class TipoAbilitazioneDAOImpl extends ScrivaBeSrvGenericDAO<TipoAbilitazioneDTO> implements TipoAbilitazioneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_TIPI_ABILITAZIONE = "SELECT * FROM _replaceTableName_ ";

    private static final String QUERY_LOAD_TIPO_ABILITAZIONE_BY_ID = QUERY_LOAD_TIPI_ABILITAZIONE + "WHERE id_tipo_abilitazione = :idTipoAbilitazione";

    private static final String QUERY_LOAD_TIPO_ABILITAZIONE_BY_COD = QUERY_LOAD_TIPI_ABILITAZIONE + "WHERE cod_tipo_abilitazione = :codTipoAbilitazione";

    private static final String QUERY_LOAD_TIPO_ABILITAZIONE_BY_ADEMPIMENTO_ISTANZA_CF = "SELECT DISTINCT dta.* " +
            "FROM _replaceTableName_ dta " +
            "INNER JOIN scriva_r_adempi_config ac ON ac.valore = dta.cod_tipo_abilitazione " +
            "WHERE ac.id_adempimento = :idAdempimento " +
            "AND id_informazione_scriva = 8  " +
            "AND chiave IN ( " +
            "SELECT DISTINCT cod_profilo_app " +
            "FROM scriva_d_profilo_app sdpa " +
            "INNER JOIN scriva_r_istanza_attore sria ON sria.id_profilo_app = sdpa.id_profilo_app " +
            "AND sria.id_istanza = :idIstanza " +
            "AND sria.data_revoca IS NULL " +
            "AND sria.cf_attore  = :cfAttore)";

    /**
     * @return List<TipoSoggettoDTO>
     */
    @Override
    public List<TipoAbilitazioneDTO> loadTipiAbilitazione() {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_ABILITAZIONE, null);
    }

    /**
     * @param idTipoAbilitazione idTipoAbilitazione
     * @return List<TipoSoggettoDTO>
     */
    @Override
    public List<TipoAbilitazioneDTO> loadTipoAbilitazioneById(Long idTipoAbilitazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAbilitazione", idTipoAbilitazione);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param codTipoAbilitazione codTipoAbilitazione
     * @return List<TipoSoggettoDTO>
     */
    @Override
    public List<TipoAbilitazioneDTO> loadTipoAbilitazioneByAdempimentoIstanzaCfAttore(String codTipoAbilitazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoAbilitazione", codTipoAbilitazione);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPO_ABILITAZIONE_BY_COD, map);
    }

    /**
     * Load tipo abilitazione by code list.
     *
     * @param idAdempimento the id adempimento
     * @param idIstanza     the id istanza
     * @param cfAttore      the cf attore
     * @return the list
     */
    @Override
    public List<TipoAbilitazioneDTO> loadTipoAbilitazioneByAdempimentoIstanzaCfAttore(Long idAdempimento, Long idIstanza, String cfAttore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idIstanza", idIstanza);
        map.put("cfAttore", cfAttore);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPO_ABILITAZIONE_BY_ADEMPIMENTO_ISTANZA_CF, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_TIPO_ABILITAZIONE_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoAbilitazioneDTO>
     */
    @Override
    public RowMapper<TipoAbilitazioneDTO> getRowMapper() throws SQLException {
        return new TipoAbilitazioneRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<TipoAbilitazioneDTO> getExtendedRowMapper() throws SQLException {
        return new TipoAbilitazioneRowMapper();
    }

    private static class TipoAbilitazioneRowMapper implements RowMapper<TipoAbilitazioneDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public TipoAbilitazioneRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public TipoAbilitazioneDTO mapRow(ResultSet rs, int i) throws SQLException {
            TipoAbilitazioneDTO bean = new TipoAbilitazioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoAbilitazioneDTO bean) throws SQLException {
            bean.setIdTipoAbilitazione(rs.getLong("id_tipo_abilitazione"));
            bean.setCodTipoAbilitazione(rs.getString("cod_tipo_abilitazione"));
            bean.setDesTipoAbilitazione(rs.getString("des_tipo_abilitazione"));
        }
    }

}