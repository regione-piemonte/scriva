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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.DestinatarioCanaleDAO;
import it.csi.scriva.scrivabesrv.dto.CanaleNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.DestinatarioCanaleDTO;
import it.csi.scriva.scrivabesrv.dto.DestinatarioCanaleExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.DestinatarioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppDTO;
import it.csi.scriva.scrivabesrv.dto.TipoDestinatarioDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface Destinatario canale dao.
 *
 * @author CSI PIEMONTE
 */
public class DestinatarioCanaleDAOImpl extends ScrivaBeSrvGenericDAO<DestinatarioCanaleDTO> implements DestinatarioCanaleDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ATTIVI = "AND (DATE(srdc.data_inizio) <= DATE(NOW()) AND (srdc.data_fine IS NULL OR DATE(srdc.data_fine)>= DATE(NOW())))\n";

    private static final String WHERE_ID_DESTINATARIO_CANALE = "AND UPPER(sdcn.cod_canale_notifica) = UPPER(:codCanaleNotifica)\n";

    private static final String WHERE_ID_DESTINATARIO = "AND UPPER(srdc.id_destinatario) = UPPER(:idDestinatario)\n";

    private static final String WHERE_COD_DESTINATARIO = "AND UPPER(sdd.cod_destinatario) = UPPER(:codDestinatario)\n";

    private static final String WHERE_COD_TIPO_DESTINATARIO = "AND UPPER(sdtd.cod_tipo_destinatario) = UPPER(:codTipoDestinatario)\n";

    private static final String WHERE_COMPONENTE_APP = "AND UPPER(sdcn.cod_canale_notifica) = UPPER(:codCanaleNotifica)\n";

    private static final String QUERY_LOAD_DESTINATARI_CANALE = "SELECT * FROM _replaceTableName_ srdc\n" +
            "INNER JOIN scriva_d_destinatario sdd ON sdd.id_destinatario = srdc.id_destinatario\n" +
            "INNER JOIN scriva_d_tipo_destinatario sdtd ON sdtd.id_tipo_destinatario = sdd.id_tipo_destinatario\n" +
            "LEFT JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdd.id_componente_app\n" +
            "LEFT JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = sdd.id_profilo_app\n" +
            "INNER JOIN scriva_d_canale_notifica sdcn ON sdcn.id_canale_notifica = srdc.id_canale_notifica\n" +
            "WHERE 1=1\n";

    /**
     * Load destinatari canale list.
     *
     * @param componenteApp the componente app
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    @Override
    public List<DestinatarioCanaleExtendedDTO> loadDestinatariCanale(String componenteApp, Boolean flgAttivi) {
        logBegin(className);
        return loadDestinatariCanale(null, null, null, null, componenteApp, flgAttivi);
    }

    /**
     * Load destinario canale list.
     *
     * @param idDestinatarioCanale the id destinario canale
     * @return the list
     */
    @Override
    public List<DestinatarioCanaleExtendedDTO> loadDestinatarioCanale(Long idDestinatarioCanale) {
        logBegin(className);
        return loadDestinatariCanale(idDestinatarioCanale, null, null, null, null, null);
    }

    /**
     * Load destinario canale by id destinatario list.
     *
     * @param idDestinatario the id destinatario
     * @return the list
     */
    @Override
    public List<DestinatarioCanaleExtendedDTO> loadDestinarioCanaleByIdDestinatario(Long idDestinatario) {
        logBegin(className);
        return loadDestinatariCanale(null, idDestinatario, null, null, null, null);
    }

    /**
     * Load destinatario canale by code destinatario list.
     *
     * @param codDestinatario the cod destinatario
     * @return the list
     */
    @Override
    public List<DestinatarioCanaleExtendedDTO> loadDestinatarioCanaleByCodeDestinatario(String codDestinatario) {
        logBegin(className);
        return loadDestinatariCanale(null, null, codDestinatario, null, null, null);
    }

    /**
     * Load destinatario canale by code tipo destinatario list.
     *
     * @param codTipoDestinatario the cod tipo destinatario
     * @return the list
     */
    @Override
    public List<DestinatarioCanaleExtendedDTO> loadDestinatarioCanaleByCodeTipoDestinatario(String codTipoDestinatario) {
        logBegin(className);
        return loadDestinatariCanale(null, null, null, codTipoDestinatario, null, null);
    }


    private List<DestinatarioCanaleExtendedDTO> loadDestinatariCanale(Long idDestinatarioCanale, Long idDestinatario, String codDestinatario, String codTipoDestinatario, String componenteApp, Boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_DESTINATARI_CANALE + (Boolean.TRUE.equals(flgAttivi) ? WHERE_ATTIVI : "");
        if (idDestinatarioCanale != null) {
            map.put("idDestinatarioCanale", idDestinatarioCanale);
            query += WHERE_ID_DESTINATARIO_CANALE;
        }
        if (idDestinatario != null) {
            map.put("idDestinatario", idDestinatario);
            query += WHERE_ID_DESTINATARIO;
        }
        if (StringUtils.isNotBlank(codDestinatario)) {
            map.put("codDestinatario", codDestinatario);
            query += WHERE_COD_DESTINATARIO;
        }
        if (StringUtils.isNotBlank(codTipoDestinatario)) {
            map.put("codTipoDestinatario", codTipoDestinatario);
            query += WHERE_COD_TIPO_DESTINATARIO;
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
        return getQuery(QUERY_LOAD_DESTINATARI_CANALE + WHERE_ID_DESTINATARIO_CANALE, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<DestinatarioCanaleDTO> getRowMapper() throws SQLException {
        return new DestinatarioCanaleRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<DestinatarioCanaleExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new DestinatarioCanaleExtendedRowMapper();
    }

    /**
     * The type Destinatario canale row mapper.
     */
    public static class DestinatarioCanaleRowMapper implements RowMapper<DestinatarioCanaleDTO> {

        /**
         * Instantiates a new Destinatario row mapper.
         *
         * @throws SQLException the sql exception
         */
        public DestinatarioCanaleRowMapper() throws SQLException {
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
        public DestinatarioCanaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DestinatarioCanaleDTO bean = new DestinatarioCanaleDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, DestinatarioCanaleDTO bean) throws SQLException {
            bean.setIdDestinatarioCanale(rs.getLong("id_destinatario_canale"));
            bean.setIdDestinatario(rs.getLong("id_destinatario"));
            bean.setIdCanaleNotifica(rs.getLong("id_canale_notifica"));
            bean.setFlgCanaleDefault(rs.getInt("flg_canale_default") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Destinatario canale extended row mapper.
     */
    public static class DestinatarioCanaleExtendedRowMapper implements RowMapper<DestinatarioCanaleExtendedDTO> {

        /**
         * Instantiates a new Destinatario row mapper.
         *
         * @throws SQLException the sql exception
         */
        public DestinatarioCanaleExtendedRowMapper() throws SQLException {
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
        public DestinatarioCanaleExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DestinatarioCanaleExtendedDTO bean = new DestinatarioCanaleExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, DestinatarioCanaleExtendedDTO bean) throws SQLException {
            bean.setIdDestinatarioCanale(rs.getLong("id_destinatario_canale"));

            DestinatarioExtendedDTO destinatario = new DestinatarioExtendedDTO();
            populateDestinatarioBean(rs, destinatario);
            bean.setIdDestinatario(rs.getLong("id_destinatario"));

            CanaleNotificaExtendedDTO canaleNotifica = new CanaleNotificaExtendedDTO();
            populateCanaleNotificaBean(rs, canaleNotifica);
            bean.setCanaleNotifica(canaleNotifica.getIdCanaleNotifica() > 0 ? canaleNotifica : null);

            bean.setFlgCanaleDefault(rs.getInt("flg_canale_default") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateDestinatarioBean(ResultSet rs, DestinatarioExtendedDTO bean) throws SQLException {
            bean.setIdDestinatario(rs.getLong("id_destinatario"));

            TipoDestinatarioDTO tipoDestinatario = new TipoDestinatarioDTO();
            populateTipoDestinatarioBean(rs, tipoDestinatario);
            bean.setTipoDestinatario(tipoDestinatario);

            ProfiloAppDTO profiloApp = new ProfiloAppDTO();
            populateProfiloAppBean(rs, profiloApp);
            bean.setProfiloApp(profiloApp.getIdProfiloApp() > 0 ? profiloApp : null);

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateComponenteAppBean(rs, componenteApp);
            bean.setComponenteApp(componenteApp.getIdComponenteApp() > 0 ? componenteApp : null);

            bean.setCodDestinatario(rs.getString("cod_destinatario"));
            bean.setDenDestinatario(rs.getString("den_destinatario"));
            bean.setNotaDestinatario(rs.getString("nota_destinatario"));
            bean.setNome(rs.getString("nome"));
            bean.setCognome(rs.getString("cognome"));
            bean.setDesUfficioEnte(rs.getString("des_ufficio_ente"));
            bean.setDesNota(rs.getString("des_nota"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setFlgVerificaPreferenzeNotifiche(rs.getInt("flg_verifica_preferenze_notifiche") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
        }

        private void populateTipoDestinatarioBean(ResultSet rs, TipoDestinatarioDTO bean) throws SQLException {
            bean.setIdTipoDestinatario(rs.getLong("id_tipo_destinatario"));
            bean.setCodTipoDestinatario(rs.getString("cod_tipo_destinatario"));
            bean.setDesTipoDestinatario(rs.getString("des_tipo_destinatario"));
        }

        private void populateProfiloAppBean(ResultSet rs, ProfiloAppDTO bean) throws SQLException {
            bean.setIdProfiloApp(rs.getLong("id_profilo_app"));
            bean.setIdComponenteApp(rs.getLong("id_componente_app"));
            bean.setCodProfiloApp(rs.getString("cod_profilo_app"));
            bean.setDesProfiloApp(rs.getString("des_profilo_app"));
            bean.setFlgProfiloIride(1 == rs.getInt("flg_profilo_iride") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCompetenza(1 == rs.getInt("flg_competenza") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateComponenteAppBean(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("id_componente_app"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }

        private void populateCanaleNotificaBean(ResultSet rs, CanaleNotificaExtendedDTO bean) throws SQLException {
            bean.setIdCanaleNotifica(rs.getLong("id_canale_notifica"));

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateComponenteAppBean(rs, componenteApp);
            bean.setComponenteApplPush(componenteApp.getIdComponenteApp() > 0 ? componenteApp : null);

            bean.setCodCanaleNotifica(rs.getString("cod_canale_notifica"));
            bean.setDesCanaleNotifica(rs.getString("des_canale_notifica"));
            bean.setFlgCampoCc(rs.getBoolean("flg_campo_cc"));
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setIndTipoCanale(rs.getString("ind_tipo_canale"));
        }


    }


}