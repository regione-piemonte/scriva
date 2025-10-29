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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComponenteAppDAO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaGestore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Componente app dao.
 *
 * @author CSI PIEMONTE
 */
public class ComponenteAppDAOImpl extends ScrivaBeSrvGenericDAO<ComponenteAppDTO> implements ComponenteAppDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID = "AND sdca.id_componente_app = :idComponenteApp";

    private static final String WHERE_COD = "AND sdca.cod_componente_app = :codComponenteApp";

    private static final String QUERY_LOAD_COMPONENTE_APP = "SELECT sdca.*\n" +
            "FROM _replaceTableName_ sdca\n" +
            "WHERE 1=1\n";

    private static final String QUERY_LOAD_COMPONENTE_APP_BY_ADEMP_COMPET = "SELECT sdca.*\n" +
            "FROM _replaceTableName_ sdca\n" +
            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_componente_gestore_processo = sdca.id_componente_app\n" +
            "WHERE 1=1\n" +
            "AND srac.id_adempimento = :idAdempimento\n" +
            "AND srac.id_competenza_territorio = :idCompetenzaTerritorio\n";

    private static final String QUERY_LOAD_IST_COMP_GESTORE = "SELECT sric.id_istanza, sric.id_competenza_territorio, srac.id_componente_gestore_processo\n" +
            "        FROM scriva_r_istanza_competenza sric\n" +
            "        INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sric.id_istanza\n" +
            "        INNER JOIN scriva_r_adempi_competenza srac ON srac.id_adempimento = sti.id_adempimento AND srac.id_competenza_territorio = sric.id_competenza_territorio\n" +
            "        WHERE sric.id_istanza IN (:idIstanzaList)\n" +
            "        AND sric.flg_autorita_assegnata_bo = 1\n" +
            "        UNION\n" +
            "        SELECT sric.id_istanza, sric.id_competenza_territorio, srac.id_componente_gestore_processo\n" +
            "        FROM scriva_r_istanza_competenza sric\n" +
            "        INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sric.id_istanza\n" +
            "        INNER JOIN scriva_r_adempi_competenza srac ON srac.id_adempimento = sti.id_adempimento AND srac.id_competenza_territorio = sric.id_competenza_territorio\n" +
            "        WHERE sric.id_istanza IN (:idIstanzaList)\n" +
            "        AND sric.flg_autorita_principale =1";

    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(srac.data_inizio_validita) <= DATE(NOW()) AND (srac.data_fine_validita IS NULL OR DATE(srac.data_fine_validita)>= DATE(NOW())))\n ";
    
    /**
     * Load componenti app list.
     *
     * @return List<ComponenteAppDTO>     list
     */
    @Override
    public List<ComponenteAppDTO> loadComponentiApp() {
        logBegin(className);
        return loadComponenteAppById(null);
    }

    /**
     * Load componente app by id list.
     *
     * @param idComponenteApp the id componente app
     * @return List<ComponenteAppDTO>     list
     */
    @Override
    public List<ComponenteAppDTO> loadComponenteAppById(Long idComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = getPrimaryKeySelect();
        if (idComponenteApp != null) {
            map.put("idComponenteApp", idComponenteApp);
            query += WHERE_ID;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load componente app by code list.
     *
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<ComponenteAppDTO> loadComponenteAppByCode(String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = getPrimaryKeySelect();
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COD;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load componente app by id adempimento comp territorio list.
     *
     * @param idAdempimento          the id adempimento
     * @param idCompetenzaTerritorio the id competenza territorio
     * @return the componente app dto
     */
    @Override
    public ComponenteAppDTO loadComponenteAppByIdAdempimentoCompTerritorio(Long idAdempimento, Long idCompetenzaTerritorio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
        return findSimpleDTOByQuery(className, QUERY_LOAD_COMPONENTE_APP_BY_ADEMP_COMPET, map);
    }

    /**
     * Load istanza competenza gestore list.
     *
     * @param idIstanzaList the id istanza list
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaGestore> loadIstanzaCompetenzaGestore(List<Long> idIstanzaList) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanzaList", idIstanzaList);
            return template.query(getQuery(QUERY_LOAD_IST_COMP_GESTORE, null, null), getParameterValue(map), getIstanzaCompetenzaGestoreRowMapper());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return null;
    }


    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_COMPONENTE_APP, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ComponenteAppDTO> getRowMapper() throws SQLException {
        return new ComponenteAppRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ComponenteAppDTO> getExtendedRowMapper() throws SQLException {
        return new ComponenteAppRowMapper();
    }

    /**
     * Gets istanza competenza gestore row mapper.
     *
     * @return the istanza competenza gestore row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<IstanzaCompetenzaGestore> getIstanzaCompetenzaGestoreRowMapper() throws SQLException {
        return new IstanzaCompetenzaGestoreRowMapper();
    }

    /**
     * The type Componente app row mapper.
     */
    public static class ComponenteAppRowMapper implements RowMapper<ComponenteAppDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ComponenteAppRowMapper() throws SQLException {
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
        public ComponenteAppDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ComponenteAppDTO bean = new ComponenteAppDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("id_componente_app"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }
    }

    /**
     * The type Istanza competenza gestore row mapper.
     */
    public static class IstanzaCompetenzaGestoreRowMapper implements RowMapper<IstanzaCompetenzaGestore> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaCompetenzaGestoreRowMapper() throws SQLException {
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
        public IstanzaCompetenzaGestore mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaCompetenzaGestore bean = new IstanzaCompetenzaGestore();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaCompetenzaGestore bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo"));
        }
    }


}