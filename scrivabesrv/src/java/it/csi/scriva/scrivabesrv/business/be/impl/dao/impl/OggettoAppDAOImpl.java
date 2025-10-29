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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoAppDAO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.OggettoAppDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoOggettoAppDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Oggetto app dao.
 *
 * @author CSI PIEMONTE
 */
public class OggettoAppDAOImpl extends ScrivaBeSrvGenericDAO<OggettoAppDTO> implements OggettoAppDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_OGGETTI_APP = "SELECT * FROM _replaceTableName_ ";

    private static final String QUERY_LOAD_OGGETTO_APP_BY_ID = QUERY_LOAD_OGGETTI_APP + "WHERE id_oggetto_app = :idOggettoApp";

    private static final String QUERY_LOAD_OGGETTO_APP_BY_CODE = QUERY_LOAD_OGGETTI_APP + "WHERE UPPER(cod_oggetto_app) = UPPER(:codOggettoApp)";


    /**
     * @return List<OggettoAppDTO>
     */
    @Override
    public List<OggettoAppExtendedDTO> loadOggettiApp() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_OGGETTI_APP, null);
    }

    /**
     * @param idOggettoApp idOggettoApp
     * @return List<OggettoAppDTO>
     */
    @Override
    public List<OggettoAppExtendedDTO> loadOggettoAppById(Long idOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoApp", idOggettoApp);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param codOggettoApp codOggettoApp
     * @return List<OggettoAppDTO>
     */
    @Override
    public List<OggettoAppExtendedDTO> loadOggettoAppByCode(String codOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codOggettoApp", codOggettoApp);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_APP_BY_CODE, map);
    }

    /**
     * Load oggetto app by id istanza id istanza and attore list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<OggettoAppExtendedDTO> loadOggettoAppByIdIstanzaIdIstanzaAndAttore(Long idIstanza, AttoreScriva attoreScriva) {
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
        return getQuery(QUERY_LOAD_OGGETTO_APP_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<OggettoAppDTO>
     */
    @Override
    public RowMapper<OggettoAppDTO> getRowMapper() throws SQLException {
        return new OggettoAppRowMapper();
    }

    @Override
    public RowMapper<OggettoAppExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new OggettoAppExtendedRowMapper();
    }

    /**
     * The type Oggetto app extended row mapper.
     */
    public static class OggettoAppExtendedRowMapper implements RowMapper<OggettoAppExtendedDTO> {

        /**
         * Instantiates a new Oggetto app extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoAppExtendedRowMapper() throws SQLException {
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
        public OggettoAppExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoAppExtendedDTO bean = new OggettoAppExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoAppExtendedDTO bean) throws SQLException {
            bean.setIdOggettoApp(rs.getLong("id_oggetto_app"));

            TipoOggettoAppDTO tipoOggettoApp = new TipoOggettoAppDTO();
            populateBeanTipoOggettoApp(rs, tipoOggettoApp);
            bean.setTipoOggettoApp(tipoOggettoApp);

            TipoEventoDTO tipoEvento = new TipoEventoDTO();
            populateBeanTipoEvento(rs, tipoEvento);
            bean.setTipoEvento(tipoEvento);

            bean.setCodOggettoApp(rs.getString("cod_oggetto_app"));
            bean.setDesOggettoApp(rs.getString("des_oggetto_app"));
            bean.setFlgPrevistoDaGestoreProcesso(rs.getInt("flg_previsto_da_gestore_processo") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanTipoOggettoApp(ResultSet rs, TipoOggettoAppDTO bean) throws SQLException {
            bean.setIdTipoOggettoApp(rs.getLong("tipo_oggetto_app_id"));
            bean.setCodTipoOggettoApp(rs.getString("cod_tipo_oggetto_app"));
            bean.setDesTipoOggettoApp(rs.getString("des_tipo_oggetto_app"));
        }

        private void populateBeanTipoEvento(ResultSet rs, TipoEventoDTO bean) throws SQLException {
            if (rs.getLong("id_tipo_evento") > 0) {
                bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));
                bean.setCodTipoEvento(rs.getString("cod_tipo_evento"));
                bean.setDesTipoEvento(rs.getString("des_tipo_evento"));
                bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo"));
            }
        }

    }

    /**
     * The type Oggetto app row mapper.
     */
    public static class OggettoAppRowMapper implements RowMapper<OggettoAppDTO> {

        /**
         * Instantiates a new Oggetto app row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoAppRowMapper() throws SQLException {
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
        public OggettoAppDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoAppDTO bean = new OggettoAppDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoAppDTO bean) throws SQLException {
            bean.setIdOggettoApp(rs.getLong("id_oggetto_app"));
            bean.setIdTipoOggettoApp(rs.getLong("id_tipo_oggetto_app"));
            bean.setCodOggettoApp(rs.getString("cod_oggetto_app"));
            bean.setDesOggettoApp(rs.getString("des_oggetto_app"));
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento") > 0 ? rs.getLong("id_tipo_evento") : null);
            bean.setFlgPrevistoDaGestoreProcesso(rs.getInt("flg_previsto_da_gestore_processo") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

}