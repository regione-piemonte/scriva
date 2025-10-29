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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioProfiloDAO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioProfiloDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioProfiloExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Funzionario profilo dao.
 *
 * @author CSI PIEMONTE
 */
public class FunzionarioProfiloDAOImpl extends ScrivaBeSrvGenericDAO<FunzionarioProfiloDTO> implements FunzionarioProfiloDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_FUNZIONARI_PROFILO = "SELECT * FROM _replaceTableName_ srfp WHERE 1=1 ";

    private static final String QUERY_LOAD_FUNZIONARIO_PROFILO = "SELECT srfp.*, srfp.data_inizio_validita AS data_inizio_validita_fp, srfp.data_fine_validita AS data_fine_validita_fp \n" +
            ", sdpa.* \n" +
            ", sdca.*, sdca.id_componente_app AS componente_app_id \n" +
            "FROM _replaceTableName_ srfp \n" +
            "INNER JOIN scriva_t_funzionario stf ON stf.id_funzionario = srfp.id_funzionario \n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app \n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app \n" +
            "WHERE 1=1 ";

    private static final String QUERY_LOAD_FUNZIONARIO_PROFILO_BY_ID_FUNZIONARIO = QUERY_LOAD_FUNZIONARI_PROFILO + "AND srfp.id_funzionario = :idFunzionario ";

    private static final String QUERY_LOAD_FUNZIONARIO_PROFILO_BY_ID_PROFILO = QUERY_LOAD_FUNZIONARI_PROFILO + "AND srfp.id_profilo_app = :idProfilo ";

    private static final String QUERY_LOAD_FUNZIONARIO_PROFILO_BY_ID_FUNZIONARIO_PROFILO = QUERY_LOAD_FUNZIONARI_PROFILO
            + "AND srfp.id_funzionario = :idFunzionario "
            + "AND srfp.id_profilo_app = :idProfilo ";

    private static final String QUERY_LOAD_FUNZIONARIO_PROFILO_BY_CF = QUERY_LOAD_FUNZIONARIO_PROFILO + "AND stf.cf_funzionario = :codiceFiscaleFunzionario ";

    /**
     * Load funzionari profilo list.
     *
     * @return the list
     */
    @Override
    public List<FunzionarioProfiloExtendedDTO> loadFunzionariProfilo() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARI_PROFILO, null);
    }

    /**
     * Load funzionario profilo by id funzionario list.
     *
     * @param idFunzionario the id funzionario
     * @return the list
     */
    @Override
    public List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByIdFunzionario(Long idFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idFunzionario", idFunzionario);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_PROFILO_BY_ID_FUNZIONARIO, map);
    }

    /**
     * Load funzionario profilo by id profilo list.
     *
     * @param idProfilo the id profilo
     * @return the list
     */
    @Override
    public List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByIdProfilo(Long idProfilo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idProfilo", idProfilo);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_PROFILO_BY_ID_PROFILO, map);
    }

    /**
     * Load funzionario profilo by id funzionario profilo list.
     *
     * @param idFunzionario the id funzionario
     * @param idProfilo     the id profilo
     * @return the list
     */
    @Override
    public List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByIdFunzionarioProfilo(Long idFunzionario, Long idProfilo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idFunzionario", idFunzionario);
        map.put("idProfilo", idProfilo);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_PROFILO_BY_ID_FUNZIONARIO_PROFILO, map);

    }

    /**
     * Load funzionario profilo by cf list.
     *
     * @param codiceFiscaleFunzionario the codice fiscale funzionario
     * @return the list
     */
    @Override
    public List<FunzionarioProfiloExtendedDTO> loadFunzionarioProfiloByCf(String codiceFiscaleFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codiceFiscaleFunzionario", codiceFiscaleFunzionario);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_PROFILO_BY_CF, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_FUNZIONARIO_PROFILO_BY_ID_FUNZIONARIO_PROFILO, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<FunzionarioProfiloDTO> getRowMapper() throws SQLException {
        return new FunzionarioProfiloRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<FunzionarioProfiloExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new FunzionarioProfiloExtendedRowMapper();
    }

    public static class FunzionarioProfiloRowMapper implements RowMapper<FunzionarioProfiloDTO> {

        /**
         * Instantiates a new Ente creditore row mapper.
         *
         * @throws SQLException the sql exception
         */
        public FunzionarioProfiloRowMapper() throws SQLException {
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
        public FunzionarioProfiloDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FunzionarioProfiloDTO bean = new FunzionarioProfiloDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, FunzionarioProfiloDTO bean) throws SQLException {
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setIdProfiloApp(rs.getLong("id_profilo_app"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
        }
    }

    public static class FunzionarioProfiloExtendedRowMapper implements RowMapper<FunzionarioProfiloExtendedDTO> {

        /**
         * Instantiates a new Ente creditore row mapper.
         *
         * @throws SQLException the sql exception
         */
        public FunzionarioProfiloExtendedRowMapper() throws SQLException {
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
        public FunzionarioProfiloExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FunzionarioProfiloExtendedDTO bean = new FunzionarioProfiloExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, FunzionarioProfiloExtendedDTO bean) throws SQLException {
            bean.setIdFunzionario(rs.getLong("id_funzionario"));

            ProfiloAppExtendedDTO profiloApp = new ProfiloAppExtendedDTO();
            populateProfiloAppBean(rs, profiloApp);
            bean.setProfiloApp(profiloApp);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_fp"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_fp"));
        }

        private void populateProfiloAppBean(ResultSet rs, ProfiloAppExtendedDTO bean) throws SQLException {
            bean.setIdProfiloApp(rs.getLong("id_profilo_app"));

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
    }


}