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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoProfiloDAO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoProfiloDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoProfiloExtendedDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo adempimento profilo dao.
 */
public class TipoAdempimentoProfiloDAOImpl extends ScrivaBeSrvGenericDAO<TipoAdempimentoProfiloDTO> implements TipoAdempimentoProfiloDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI = "SELECT * FROM _replaceTableName_ ";

    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILO_BY_ID = QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI + "WHERE id_tipo_adempi_profilo = :idTipoAdempiProfilo";

    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI_BY_ID_PROFILO_APP_LIST = QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI + "WHERE id_profilo_app IN (:idProfiloAppList)";

    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILO_BY_COD_PROFILO_APP = QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI + "WHERE id_tipo_oggetto_app = :idTipoOggettoApp";

    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILO_BY_COD_ADEMPIMENTO = QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI + "WHERE id_tipo_oggetto_app = :idTipoOggettoApp";

    /**
     * @return List<TipoAdempimentoProfiloExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfili() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI, null);
    }

    /**
     * @param idTipoAdempimentoProfilo idTipoAdempimentoProfilo
     * @return List<TipoAdempimentoProfiloExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloById(Long idTipoAdempimentoProfilo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAdempimentoProfilo", idTipoAdempimentoProfilo);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load tipo adempimento profilo by id list list.
     *
     * @param idProfiloAppList the id profilo app list
     * @return the list
     */
    @Override
    public List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloByIdProfiloAppList(List<Long> idProfiloAppList) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idProfiloAppList", idProfiloAppList);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILI_BY_ID_PROFILO_APP_LIST, map);
    }

    /**
     * @param codProfiloApp codProfiloApp
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloByCodeProfiloApp(Long codProfiloApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codProfiloApp", codProfiloApp);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILO_BY_COD_PROFILO_APP, map);
    }

    /**
     * @param codAdempimento codAdempimento
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoProfiloDTO> loadTipoAdempimentoProfiloByCodeAdempimento(Long codAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILO_BY_COD_ADEMPIMENTO, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_TIPO_ADEMPIMENTO_PROFILO_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoAdempimentoProfiloDTO>
     */
    @Override
    public RowMapper<TipoAdempimentoProfiloDTO> getRowMapper() throws SQLException {
        return new TipoAdempimentoProfiloRowMapper();
    }

    @Override
    public RowMapper<TipoAdempimentoProfiloExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TipoAdempimentoProfiloExtendedRowMapper();
    }

    /**
     * The type Tipo adempimento profilo extended row mapper.
     */
    public static class TipoAdempimentoProfiloExtendedRowMapper implements RowMapper<TipoAdempimentoProfiloExtendedDTO> {

        /**
         * Instantiates a new Tipo adempimento profilo extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAdempimentoProfiloExtendedRowMapper() throws SQLException {
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
        public TipoAdempimentoProfiloExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAdempimentoProfiloExtendedDTO bean = new TipoAdempimentoProfiloExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoAdempimentoProfiloExtendedDTO bean) throws SQLException {
            bean.setIdTipoAdempimentoProfilo(rs.getLong("id_tipo_adempi_profilo"));

            ProfiloAppExtendedDTO profiloApp = new ProfiloAppExtendedDTO();
            populateBeanProfiloApp(rs, profiloApp);
            bean.setProfiloApp(profiloApp);

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            populateBeanTipoAdempimento(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento);

            bean.setFlgSoloLettura(1 == rs.getInt("flg_sola_lettura") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanProfiloApp(ResultSet rs, ProfiloAppExtendedDTO bean) throws SQLException {
            bean.setIdProfiloApp(rs.getLong("profilo_app_id"));

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateComponenteApp(rs, componenteApp);
            bean.setComponenteApp(componenteApp);

            bean.setCodProfiloApp(rs.getString("cod_profilo_app"));
            bean.setDesProfiloApp(rs.getString("des_profilo_app"));
            bean.setFlgProfiloIride(1 == rs.getInt("flg_profilo_iride") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCompetenza(1 == rs.getInt("flg_competenza") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateComponenteApp(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("componente_app_id"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }

        private void populateBeanTipoAdempimento(ResultSet rs, TipoAdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            bean.setAmbito(ambito);
            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            bean.setFlgAttivo(1 == rs.getInt("tipo_proc_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoTipoAdempimento(rs.getInt("ordinamento_tipo_adempimento"));
            bean.setPreferito(rs.getLong("preference"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    /**
     * The type Tipo adempimento profilo row mapper.
     */
    public static class TipoAdempimentoProfiloRowMapper implements RowMapper<TipoAdempimentoProfiloDTO> {

        /**
         * Instantiates a new Tipo adempimento profilo row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAdempimentoProfiloRowMapper() throws SQLException {
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
        public TipoAdempimentoProfiloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAdempimentoProfiloDTO bean = new TipoAdempimentoProfiloDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoAdempimentoProfiloDTO bean) throws SQLException {
            bean.setIdTipoAdempimentoProfilo(rs.getLong("id_tipo_adempi_profilo"));
            bean.setIdProfiloApp(rs.getLong("id_profilo_app"));
            bean.setIdTipoAdempimento(rs.getLong("id_tipo_adempimento"));
            bean.setFlgSoloLettura(1 == rs.getInt("flg_sola_lettura") ? Boolean.TRUE : Boolean.FALSE);
        }
    }

}