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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ProfiloAppDAO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloOggettoAppDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoOggettoAppDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Profilo app dao.
 */
public class ProfiloAppDAOImpl extends ScrivaBeSrvGenericDAO<ProfiloAppDTO> implements ProfiloAppDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_PWD_RUOLO = "AND sdpa.pwd_ruolo = :password\n";

    private static final String WHERE_ID_PROFILO_APP = "AND sdpa.id_profilo_app = :idProfiloApp\n";

    private static final String WHERE_ID_PROFILO_APP_IN = "AND sdpa.id_profilo_app IN (\n_inline_view_)\n";

    private static final String INLINE_VIEW_FUNZIONARIO = "\tSELECT sdpa.id_profilo_app\n" +
            "\tFROM scriva_t_funzionario stf\n" +
            "\tLEFT JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario\n" +
            "\tLEFT JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
            "\tWHERE stf.cf_funzionario = :codiceFiscale\n";

    private static final String INLINE_VIEW_CITTADINO = "\tSELECT sria.id_profilo_app\n" +
            "\tFROM scriva_r_istanza_attore sria\n" +
            "\tWHERE sria.cf_attore = :codiceFiscale\n" +
            "\tAND sria.id_istanza = :idIstanza\n";

    private static final String WHERE_COD_PROFILO_APP = "AND sdpa.cod_profilo_app = :codProfiloApp\n";

    private static final String WHERE_COD_COMPONENTE_APP = "AND sdca.cod_componente_app = :codComponenteApp\n";

    private static final String WHERE_COD_OGGETTO_APP = "AND sdoa.cod_oggetto_app = :codOggettoApp\n";

    private static final String WHERE_COD_TIPO_OGGETTO_APP = "AND UPPER(sdtoa.cod_tipo_ogg_app) = :codTipoOggettoApp\n";

    private static final String QUERY_LOAD_PROFILI_APP = "SELECT sdpa.*, \n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id \n" +
            "FROM _replaceTableName_ sdpa \n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app \n" +
            "WHERE 1=1 \n";

    private static final String QUERY_LOAD_PROFILI_OGGETTO_APP = "SELECT sdpa.*, \n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id, \n" +
            "sdoa.*, sdoa.id_tipo_ogg_app AS tipo_ogg_app_id, \n" +
            "sdtoa.* \n" +
            "FROM _replaceTableName_ sdpa\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app\n" +
            "INNER JOIN scriva_r_profilo_ogg_app srpoa ON srpoa.id_profilo_app = sdpa.id_profilo_app\n" +
            "INNER JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srpoa.id_oggetto_app\n" +
            "INNER JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app\n" +
            "WHERE 1=1 \n";

    private static final String QUERY_LOAD_PROFILI_OGGETTO_APP_LEFT_JOIN = "SELECT sdpa.*, \n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id, \n" +
            "sdoa.*, sdoa.id_tipo_ogg_app AS tipo_ogg_app_id, \n" +
            "sdtoa.* \n" +
            ", sdte.* \n" +
            "FROM _replaceTableName_ sdpa\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app\n" +
            "LEFT JOIN scriva_r_profilo_ogg_app srpoa ON srpoa.id_profilo_app = sdpa.id_profilo_app\n" +
            "LEFT JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srpoa.id_oggetto_app\n" +
            "LEFT JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app\n" +
            "LEFT JOIN scriva_d_tipo_evento sdte ON sdte.id_tipo_evento = sdoa.id_tipo_evento\n" +
            "WHERE 1=1 \n";

    private static final String QUERY_LOAD_PROFILO_APP_BY_ID = QUERY_LOAD_PROFILI_APP + WHERE_ID_PROFILO_APP;

    private static final String QUERY_LOAD_PROFILO_APP_BY_COD = QUERY_LOAD_PROFILI_APP + WHERE_COD_PROFILO_APP;

    private static final String QUERY_LOAD_PROFILO_APP_BY_COD_PROFILO_AND_APP = QUERY_LOAD_PROFILO_APP_BY_COD + WHERE_COD_COMPONENTE_APP;

    private static final String QUERY_LOAD_PROFILI_OGGETTO_APP_FO = "SELECT sria.id_istanza, \n" +
            "sdpa.id_profilo_app, sdpa.id_componente_app, sdpa.cod_profilo_app, sdpa.des_profilo_app,\n" +
            "sdpa.flg_profilo_iride, sdpa.flg_competenza, sdpa.id_gestione_attore, sdpa.pwd_ruolo,\n" +
            "sdpa.gest_data_ins, sdpa.gest_attore_ins, sdpa.gest_data_upd, sdpa.gest_attore_upd, sdpa.gest_uid,\n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id,\n" +
            "sdoa.*, sdoa.id_tipo_ogg_app AS tipo_ogg_app_id,\n" +
            "sdtoa.*\n" +
            "FROM scriva_d_profilo_app sdpa\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app\n" +
            "INNER JOIN scriva_r_istanza_attore sria ON sria.id_profilo_app = sdpa.id_profilo_app\n" +
            "LEFT JOIN scriva_r_profilo_ogg_app srpoa ON srpoa.id_profilo_app = sdpa.id_profilo_app\n" +
            "LEFT JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srpoa.id_oggetto_app\n" +
            "LEFT JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app\n" +
            "WHERE sria.cf_attore = :codiceFiscale\n" +
            "AND sria.id_istanza IN (:idIstanzaList)";

    private static final String QUERY_LOAD_PROFILI_OGGETTO_APP_BO = "SELECT sdpa.id_profilo_app, sdpa.id_componente_app, sdpa.cod_profilo_app, sdpa.des_profilo_app, sdpa.flg_profilo_iride, sdpa.flg_competenza, sdpa.id_gestione_attore, sdpa.pwd_ruolo, sdpa.gest_data_ins, sdpa.gest_attore_ins, sdpa.gest_data_upd, sdpa.gest_attore_upd, sdpa.gest_uid,\n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id,\n" +
            "sdoa.*, sdoa.id_tipo_ogg_app AS tipo_ogg_app_id, \n" +
            "sdtoa.*\n" +
            "FROM scriva_d_profilo_app sdpa\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdpa.id_componente_app = sdca.id_componente_app\n" +
            "INNER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_profilo_app = sdpa.id_profilo_app\n" +
            "INNER JOIN scriva_t_funzionario stf ON stf.id_funzionario = srfp.id_funzionario\n" +
            "LEFT JOIN scriva_r_profilo_ogg_app srpoa ON srpoa.id_profilo_app = sdpa.id_profilo_app\n" +
            "LEFT JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srpoa.id_oggetto_app\n" +
            "LEFT JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app\n" +
            "WHERE stf.cf_funzionario = :codiceFiscale\n" +
            "AND srfp.data_fine_validita IS NULL\n";

    /**
     * @return List<ProfiloAppExtendedDTO>
     */
    @Override
    public List<ProfiloAppExtendedDTO> loadProfiliApp() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_PROFILI_APP, null);
    }

    /**
     * @param idProfiloApp idProfiloApp
     * @return List<ProfiloAppExtendedDTO>
     */
    @Override
    public List<ProfiloAppExtendedDTO> loadProfiloAppById(Long idProfiloApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idProfiloApp", idProfiloApp);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param codProfiloApp codProfiloApp
     * @return List<ProfiloAppExtendedDTO>
     */
    @Override
    public List<ProfiloAppExtendedDTO> loadProfiloAppByCode(String codProfiloApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codProfiloApp", codProfiloApp);
        return findListByQuery(className, QUERY_LOAD_PROFILO_APP_BY_COD, map);
    }

    /**
     * @param codProfiloApp    codProfiloApp
     * @param codComponenteApp codComponenteApp
     * @return List<ProfiloAppExtendedDTO>
     */
    @Override
    public List<ProfiloAppExtendedDTO> loadProfiloAppByCodeProfiloAndComponenteApp(String codProfiloApp, String codComponenteApp) {
        logBegin(className);
        return loadProfiloAppByCodeProfiloAndComponenteApp(codProfiloApp, codComponenteApp, null);
    }

    /**
     * Load profilo app by code profilo and componente app list.
     *
     * @param codProfiloApp    the cod profilo app
     * @param codComponenteApp the cod componente app
     * @param password         the password
     * @return the list
     */
    @Override
    public List<ProfiloAppExtendedDTO> loadProfiloAppByCodeProfiloAndComponenteApp(String codProfiloApp, String codComponenteApp, String password) {
        logBegin(className);
        return loadProfiloAppByCodeProfiloAndComponenteApp(codProfiloApp, codComponenteApp, password, null, null);
    }

    /**
     * Load profilo app by code profilo and componente app list.
     *
     * @param codProfiloApp     the cod profilo app
     * @param codComponenteApp  the cod componente app
     * @param password          the password
     * @param codOggettoApp     the cod oggetto app
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @return the list
     */
    @Override
    public List<ProfiloAppExtendedDTO> loadProfiloAppByCodeProfiloAndComponenteApp(String codProfiloApp, String codComponenteApp, String password, String codOggettoApp, String codTipoOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_PROFILO_APP_BY_COD_PROFILO_AND_APP;
        map.put("codProfiloApp", codProfiloApp);
        map.put("codComponenteApp", codComponenteApp);
        map.put("password", password);
        map.put("codOggettoApp", codOggettoApp);
        map.put("codTipoOggettoApp", codTipoOggettoApp);
        if (StringUtils.isNotBlank(codOggettoApp) || StringUtils.isNotBlank(codTipoOggettoApp)) {
            query = QUERY_LOAD_PROFILI_OGGETTO_APP + WHERE_COD_PROFILO_APP + WHERE_COD_COMPONENTE_APP;
            query += StringUtils.isNotBlank(codOggettoApp) ? WHERE_COD_OGGETTO_APP : "";
            query += StringUtils.isNotBlank(codTipoOggettoApp) ? WHERE_COD_TIPO_OGGETTO_APP : "";
        }
        query += StringUtils.isNotBlank(password) ? WHERE_PWD_RUOLO : "";
        return findListByQuery(className, query, map);
    }

    /**
     * Load profilo app by id istanza and attore list.
     *
     * @param idIstanza    the id istanza
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<ProfiloOggettoAppDTO> loadProfiloAppByIdIstanzaAndAttore(Long idIstanza, AttoreScriva attoreScriva) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_PROFILI_OGGETTO_APP_LEFT_JOIN;
        map.put("idIstanza", idIstanza);
        map.put("codiceFiscale", attoreScriva.getCodiceFiscale());
        query += WHERE_ID_PROFILO_APP_IN.replace("_inline_view_", ComponenteAppEnum.BO.name().equalsIgnoreCase(attoreScriva.getComponente()) ? INLINE_VIEW_FUNZIONARIO : INLINE_VIEW_CITTADINO);
        MapSqlParameterSource params = getParameterValue(map);
        try {
            return template.query(getQuery(query, null, null), params, getProfiloOggettoAppRowMapper());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return Collections.emptyList();
    }

    /**
     * Load profilo app by id istanza lisdt and attore.
     *
     * @param idIstanzaList the id istanza list
     * @param attoreScriva  the attore scriva
     * @return the list
     */
    @Override
    public List<ProfiloOggettoAppDTO> loadProfiloAppByIdIstanzaListAndAttore(List<Long> idIstanzaList, AttoreScriva attoreScriva) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanzaList", idIstanzaList);
        map.put("codiceFiscale", attoreScriva.getCodiceFiscale());
        try {
            return template.query(getQuery(QUERY_LOAD_PROFILI_OGGETTO_APP_FO, null, null), getParameterValue(map), getProfiloOggettoAppRowMapper());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return Collections.emptyList();
    }

    /**
     * Load profilo app by attore bo list.
     *
     * @param attoreScriva the attore scriva
     * @return the list
     */
    @Override
    public List<ProfiloOggettoAppDTO> loadProfiloAppByAttoreBO(AttoreScriva attoreScriva) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codiceFiscale", attoreScriva.getCodiceFiscale());
        try {
            return template.query(getQuery(QUERY_LOAD_PROFILI_OGGETTO_APP_BO, null, null), getParameterValue(map), getProfiloOggettoAppRowMapper());
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return Collections.emptyList();
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_PROFILO_APP_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<ProfiloAppDTO>
     */
    @Override
    public RowMapper<ProfiloAppDTO> getRowMapper() throws SQLException {
        return new ProfiloAppRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<ProfiloAppExtendedDTO>
     */
    @Override
    public RowMapper<ProfiloAppExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new ProfiloAppExtendedRowMapper();
    }

    /**
     * Gets profilo oggetto app row mapper.
     *
     * @return the profilo oggetto app row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<ProfiloOggettoAppDTO> getProfiloOggettoAppRowMapper() throws SQLException {
        return new ProfiloOggettoAppRowMapper();
    }

    private static class ProfiloAppRowMapper implements RowMapper<ProfiloAppDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public ProfiloAppRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public ProfiloAppDTO mapRow(ResultSet rs, int i) throws SQLException {
            ProfiloAppDTO bean = new ProfiloAppDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ProfiloAppDTO bean) throws SQLException {
            bean.setIdProfiloApp(rs.getLong("id_profilo_app"));
            bean.setIdComponenteApp(rs.getLong("id_componente_app"));
            bean.setCodProfiloApp(rs.getString("cod_profilo_app"));
            bean.setDesProfiloApp(rs.getString("des_profilo_app"));
            bean.setFlgProfiloIride(1 == rs.getInt("flg_profilo_iride") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCompetenza(1 == rs.getInt("flg_competenza") ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    private static class ProfiloAppExtendedRowMapper implements RowMapper<ProfiloAppExtendedDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public ProfiloAppExtendedRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public ProfiloAppExtendedDTO mapRow(ResultSet rs, int i) throws SQLException {
            ProfiloAppExtendedDTO bean = new ProfiloAppExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ProfiloAppExtendedDTO bean) throws SQLException {
            Long idIstanza;
            try {
                idIstanza = rs.getLong("id_istanza");
            } catch (SQLException e) {
                idIstanza = null;
            }
            bean.setIdIstanza(idIstanza);

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

    private static class ProfiloOggettoAppRowMapper implements RowMapper<ProfiloOggettoAppDTO> {

        /**
         * Costruttore
         *
         * @throws SQLException SQLException
         */
        public ProfiloOggettoAppRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public ProfiloOggettoAppDTO mapRow(ResultSet rs, int i) throws SQLException {
            ProfiloOggettoAppDTO bean = new ProfiloOggettoAppDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ProfiloOggettoAppDTO bean) throws SQLException {
            Long idIstanza;
            try {
                idIstanza = rs.getLong("id_istanza");
            } catch (SQLException e) {
                idIstanza = null;
            }
            bean.setIdIstanza(idIstanza);

            bean.setIdProfiloApp(rs.getLong("id_profilo_app"));

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateComponenteApp(rs, componenteApp);
            bean.setComponenteApp(componenteApp);

            bean.setCodProfiloApp(rs.getString("cod_profilo_app"));
            bean.setDesProfiloApp(rs.getString("des_profilo_app"));
            bean.setFlgProfiloIride(1 == rs.getInt("flg_profilo_iride") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCompetenza(1 == rs.getInt("flg_competenza") ? Boolean.TRUE : Boolean.FALSE);

            OggettoAppExtendedDTO oggettoApp = new OggettoAppExtendedDTO();
            populateOggettoApp(rs, oggettoApp);
            bean.setOggettoApp(oggettoApp.getIdOggettoApp() > 0 ? oggettoApp : null);
        }

        private void populateComponenteApp(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("componente_app_id"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }

        private void populateOggettoApp(ResultSet rs, OggettoAppExtendedDTO bean) throws SQLException {
            bean.setIdOggettoApp(rs.getLong("id_oggetto_app"));

            TipoOggettoAppDTO tipoOggettoApp = new TipoOggettoAppDTO();
            populateTipoOggettoApp(rs, tipoOggettoApp);
            bean.setTipoOggettoApp(tipoOggettoApp);

            TipoEventoDTO tipoEvento = new TipoEventoDTO();
            populateTipoEvento(rs, tipoEvento);
            bean.setTipoEvento(tipoEvento.getIdTipoEvento() != 0 ? tipoEvento : null);

            bean.setCodOggettoApp(rs.getString("cod_oggetto_app"));
            bean.setDesOggettoApp(rs.getString("des_oggetto_app"));
            bean.setDesOggettoApp(rs.getString("des_oggetto_app"));
            bean.setFlgPrevistoDaGestoreProcesso(rs.getInt("flg_previsto_da_gestore_processo") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateTipoOggettoApp(ResultSet rs, TipoOggettoAppDTO bean) throws SQLException {
            bean.setIdTipoOggettoApp(rs.getLong("tipo_ogg_app_id"));
            bean.setCodTipoOggettoApp(rs.getString("cod_tipo_ogg_app"));
            bean.setDesTipoOggettoApp(rs.getString("des_tipo_ogg_app"));
        }

        private void populateTipoEvento(ResultSet rs, TipoEventoDTO bean) throws SQLException {
            try {
                bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));
                bean.setCodTipoEvento(rs.getString("cod_tipo_evento"));
                bean.setDesTipoEvento(rs.getString("des_tipo_evento"));
            } catch (Exception e) {
                bean.setIdTipoEvento(0L);
            }
        }

    }

}