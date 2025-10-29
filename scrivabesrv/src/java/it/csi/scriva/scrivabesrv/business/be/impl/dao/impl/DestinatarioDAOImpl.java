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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.DestinatarioDAO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.DestinatarioDTO;
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
 * The type Destinatario dao.
 */
public class DestinatarioDAOImpl extends ScrivaBeSrvGenericDAO<DestinatarioDTO> implements DestinatarioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ATTIVI = "AND (DATE(sdd.data_inizio) <= DATE(NOW()) AND (sdd.data_fine IS NULL OR DATE(sdd.data_fine)>= DATE(NOW())))\n";

    private static final String WHERE_COMPONENTE_APP = "AND UPPER(sdca.cod_componente_app) = UPPER(:componenteApp)\n";

    private static final String WHERE_ID_DESTINATARIO = "AND sdcn.id_destinatario = :idDestinatario\n";

    private static final String WHERE_COD_DESTINATARIO = "AND UPPER(sdcn.cod_destinatario) = UPPER(:codDestinatario)\n";

    private static final String QUERY_LOAD_DESTINATARI = "SELECT * FROM _replaceTableName_ sdd\n" +
            "INNER JOIN scriva_d_tipo_destinatario sdtd ON sdtd.id_tipo_destinatario = sdd.id_tipo_destinatario\n" +
            "LEFT JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdd.id_componente_app\n" +
            "LEFT JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = sdd.id_profilo_app\n" +
            "WHERE 1=1\n";

    /**
     * Load destinatari list.
     *
     * @param componenteApp the componente app
     * @param flgAttivi     the flg attivi
     * @return the list
     */
    @Override
    public List<DestinatarioExtendedDTO> loadDestinatari(String componenteApp, Boolean flgAttivi) {
        logBegin(className);
        return loadDestinatario(null, null, componenteApp, flgAttivi);
    }

    /**
     * Load destinatario list.
     *
     * @param idDestinatario the id destinatario
     * @return the list
     */
    @Override
    public List<DestinatarioExtendedDTO> loadDestinatario(Long idDestinatario) {
        logBegin(className);
        return loadDestinatario(idDestinatario, null, null, Boolean.FALSE);
    }

    /**
     * Load destinatario by code list.
     *
     * @param codDestinatario the cod destinatario
     * @return the list
     */
    @Override
    public List<DestinatarioExtendedDTO> loadDestinatarioByCode(String codDestinatario) {
        logBegin(className);
        return loadDestinatario(null, codDestinatario, null, Boolean.FALSE);
    }

    /**
     * Load destinatario by code list.
     *
     * @param idDestinatario  the id destinatario
     * @param codDestinatario the cod destinatario
     * @param componenteApp   the componente app
     * @param flgAttivi       the flg attivi
     * @return the list
     */
    private List<DestinatarioExtendedDTO> loadDestinatario(Long idDestinatario, String codDestinatario, String componenteApp, Boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_DESTINATARI + (Boolean.TRUE.equals(flgAttivi) ? WHERE_ATTIVI : "");
        if (idDestinatario != null) {
            map.put("idDestinatario", idDestinatario);
            query += WHERE_ID_DESTINATARIO;
        }
        if (StringUtils.isNotBlank(codDestinatario)) {
            map.put("codDestinatario", codDestinatario);
            query += WHERE_COD_DESTINATARIO;
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
        return getQuery(QUERY_LOAD_DESTINATARI + WHERE_ID_DESTINATARIO, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<DestinatarioDTO> getRowMapper() throws SQLException {
        return new DestinatarioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<DestinatarioExtendedDTO> getExtendedRowMapper() throws SQLException {
        return null;
    }

    /**
     * The type Destinatario row mapper.
     */
    public static class DestinatarioRowMapper implements RowMapper<DestinatarioDTO> {

        /**
         * Instantiates a new Destinatario row mapper.
         *
         * @throws SQLException the sql exception
         */
        public DestinatarioRowMapper() throws SQLException {
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
        public DestinatarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DestinatarioDTO bean = new DestinatarioDTO();
            populateBean(rs, bean);
            return bean;
        }

        public void populateBean(ResultSet rs, DestinatarioDTO bean) throws SQLException {
            bean.setIdDestinatario(rs.getLong("id_destinatario"));
            bean.setIdTipoDestinatario(rs.getLong("id_tipo_destinatario"));
            bean.setIdProfiloApp(rs.getLong("id_profilo_app") > 0 ? rs.getLong("id_profilo_app") : null);
            bean.setIdComponenteApp(rs.getLong("id_componente_app") > 0 ? rs.getLong("id_componente_app") : null);
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
    }

    /**
     * The type Destinatario extended row mapper.
     */
    public static class DestinatarioExtendedRowMapper implements RowMapper<DestinatarioExtendedDTO> {

        /**
         * Instantiates a new Destinatario row mapper.
         *
         * @throws SQLException the sql exception
         */
        public DestinatarioExtendedRowMapper() throws SQLException {
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
        public DestinatarioExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DestinatarioExtendedDTO bean = new DestinatarioExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        public void populateBean(ResultSet rs, DestinatarioExtendedDTO bean) throws SQLException {
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
    }


}