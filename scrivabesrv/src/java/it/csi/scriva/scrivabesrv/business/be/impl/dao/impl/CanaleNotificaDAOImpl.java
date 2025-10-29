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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CanaleNotificaDAO;
import it.csi.scriva.scrivabesrv.dto.CanaleNotificaDTO;
import it.csi.scriva.scrivabesrv.dto.CanaleNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.util.notification.model.enumeration.IndChannelTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Canale notifica dao.
 */
public class CanaleNotificaDAOImpl extends ScrivaBeSrvGenericDAO<CanaleNotificaDTO> implements CanaleNotificaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ATTIVI = "AND (DATE(data_inizio) <= DATE(NOW()) AND (data_fine IS NULL OR DATE(data_fine)>= DATE(NOW())))\n";

    private static final String WHERE_COMPONENTE_APP = "AND UPPER(sdca.cod_componente_app) = UPPER(:componenteApp)\n";

    private static final String WHERE_ID_CANALE_NOTIFICA = "AND sdcn.id_canale_notifica = :idCanaleNotifica\n";

    private static final String WHERE_COD_CANALE_NOTIFICA = "AND UPPER(sdcn.cod_canale_notifica) = UPPER(:codCanaleNotifica)\n";

    private static final String WHERE_COD_CANALE_NOTIFICA_LIST = "AND UPPER(sdcn.cod_canale_notifica) IN (:codCanaleNotificaList)\n";

    private static final String WHERE_IND_TIPO_CANALE = "AND UPPER(sdcn.ind_tipo_canale) = UPPER(:indTipoCanale)\n";

    private static final String QUERY_LOAD_CANALI_NOTIFICA = "SELECT * FROM _replaceTableName_ sdcn\n" +
            "LEFT JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdcn.id_componente_appl_push\n" +
            "WHERE 1=1\n";

    /**
     * Load canali notifica list.
     *
     * @param componenteApp the componente app
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    @Override
    public List<CanaleNotificaDTO> loadCanaliNotifica(String componenteApp, Boolean flgAttivi) {
        logBegin(className);
        return loadCanaleNotifica(null, null, null, null, componenteApp, flgAttivi);
    }

    /**
     * Load canale notifica list.
     *
     * @param idCanaleNotifica the id canale notifica
     * @return the list
     */
    @Override
    public List<CanaleNotificaDTO> loadCanaleNotifica(Long idCanaleNotifica) {
        logBegin(className);
        return loadCanaleNotifica(idCanaleNotifica, null, null, null, null, Boolean.FALSE);
    }

    /**
     * Load canale notifica by code list.
     *
     * @param codCanaleNotifica the cod canale notifica
     * @return the list
     */
    @Override
    public List<CanaleNotificaDTO> loadCanaleNotificaByCodeList(String codCanaleNotifica) {
        logBegin(className);
        return loadCanaleNotifica(null, codCanaleNotifica, null, null, null, Boolean.FALSE);
    }

    /**
     * Load canale notifica by code list.
     *
     * @param codCanaleNotificaList the cod canale notifica list
     * @param indTipoCanale         the ind tipo canale
     * @param componenteApp         the componente app
     * @return the list
     */
    @Override
    public List<CanaleNotificaDTO> loadCanaleNotificaByCodeList(List<String> codCanaleNotificaList, IndChannelTypeEnum indTipoCanale, String componenteApp) {
        logBegin(className);
        return loadCanaleNotifica(null, null, codCanaleNotificaList, indTipoCanale, componenteApp, Boolean.TRUE);
    }

    /**
     * Load canale notifica list.
     *
     * @param idCanaleNotifica  the id canale notifica
     * @param codCanaleNotifica the cod canale notifica
     * @param componenteApp     the componente app
     * @param flgAttivi         the flg attivi
     * @return the list
     */
    private List<CanaleNotificaDTO> loadCanaleNotifica(Long idCanaleNotifica, String codCanaleNotifica, List<String> codCanaleNotificaList, IndChannelTypeEnum indTipoCanale, String componenteApp, Boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_CANALI_NOTIFICA + (Boolean.TRUE.equals(flgAttivi) ? WHERE_ATTIVI : "");
        if (idCanaleNotifica != null) {
            map.put("idCanaleNotifica", idCanaleNotifica);
            query += WHERE_ID_CANALE_NOTIFICA;
        }
        if (StringUtils.isNotBlank(codCanaleNotifica)) {
            map.put("codCanaleNotifica", codCanaleNotifica);
            query += WHERE_COD_CANALE_NOTIFICA;
        }
        if (CollectionUtils.isNotEmpty(codCanaleNotificaList)) {
            map.put("codCanaleNotificaList", codCanaleNotificaList);
            query += WHERE_COD_CANALE_NOTIFICA_LIST;
        }
        if (indTipoCanale != null) {
            map.put("indTipoCanale", indTipoCanale.name());
            query += WHERE_IND_TIPO_CANALE;
        }
        if (StringUtils.isNotBlank(componenteApp)) {
            map.put("componenteApp", componenteApp);
            query += WHERE_COMPONENTE_APP;
        }
        logEnd(className);
        return findListByQuery(className, query, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_CANALI_NOTIFICA + WHERE_ID_CANALE_NOTIFICA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<CanaleNotificaDTO> getRowMapper() throws SQLException {
        return new CanaleNotificaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<CanaleNotificaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new CanaleNotificaExtendedRowMapper();
    }

    /**
     * The type Canale notifica row mapper.
     */
    public static class CanaleNotificaRowMapper implements RowMapper<CanaleNotificaDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CanaleNotificaRowMapper() throws SQLException {
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
        public CanaleNotificaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CanaleNotificaDTO bean = new CanaleNotificaDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, CanaleNotificaDTO bean) throws SQLException {
            bean.setIdCanaleNotifica(rs.getLong("id_canale_notifica"));
            bean.setIdComponenteApplPush(rs.getLong("id_componente_appl_push") > 0 ? rs.getLong("id_componente_appl_push") : null);
            bean.setCodCanaleNotifica(rs.getString("cod_canale_notifica"));
            bean.setDesCanaleNotifica(rs.getString("des_canale_notifica"));
            bean.setFlgCampoCc(rs.getBoolean("flg_campo_cc"));
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setIndTipoCanale(rs.getString("ind_tipo_canale"));
        }
    }

    /**
     * The type Canale notifica extended row mapper.
     */
    public static class CanaleNotificaExtendedRowMapper implements RowMapper<CanaleNotificaExtendedDTO> {

        /**
         * Instantiates a new Canale notifica extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CanaleNotificaExtendedRowMapper() throws SQLException {
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
        public CanaleNotificaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CanaleNotificaExtendedDTO bean = new CanaleNotificaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, CanaleNotificaExtendedDTO bean) throws SQLException {
            bean.setIdCanaleNotifica(rs.getLong("id_canale_notifica"));

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateBeanComponenteApp(rs, componenteApp);
            bean.setComponenteApplPush(componenteApp.getIdComponenteApp() > 0 ? componenteApp : null);

            bean.setCodCanaleNotifica(rs.getString("cod_canale_notifica"));
            bean.setDesCanaleNotifica(rs.getString("des_canale_notifica"));
            bean.setFlgCampoCc(rs.getBoolean("flg_campo_cc"));
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setIndTipoCanale(rs.getString("ind_tipo_canale"));
        }

        private void populateBeanComponenteApp(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("id_componente_app"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }

    }

}