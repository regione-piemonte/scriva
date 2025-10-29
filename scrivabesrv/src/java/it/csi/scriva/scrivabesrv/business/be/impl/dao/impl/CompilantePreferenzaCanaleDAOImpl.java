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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompilantePreferenzaCanaleDAO;
import it.csi.scriva.scrivabesrv.dto.CanaleNotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleDTO;
import it.csi.scriva.scrivabesrv.dto.CompilantePreferenzaCanaleExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface Compilante preferenza canale dao.
 *
 * @author CSI PIEMONTE
 */
public class CompilantePreferenzaCanaleDAOImpl extends ScrivaBeSrvGenericDAO<CompilantePreferenzaCanaleDTO> implements CompilantePreferenzaCanaleDAO {

    private final String className = this.getClass().getSimpleName();

    //private static final String QUERY_LOAD_COMPILANTI_PREFERENZE_CANALE = "SELECT COALESCE(srcpc.flg_abilitato, 0 ) as flg_abilitato_calc\n" +
    private static final String QUERY_LOAD_COMPILANTI_PREFERENZE_CANALE = "SELECT COALESCE(srcpc.flg_abilitato, srdc.flg_canale_default) as flg_abilitato_calc\n" +
            ", srcpc.id_compilante_preferenza_cn, srcpc.flg_abilitato\n" +
            ", stc.id_compilante, stc.cf_compilante, stc.cognome_compilante, stc.nome_compilante, stc.des_email_compilante\n" +
            ", sdcn.*\n" +
            "FROM scriva_r_destinatario_canale srdc\n" +
            "INNER JOIN scriva_d_destinatario sdd ON sdd.id_destinatario = srdc.id_destinatario\n" +
            "INNER JOIN scriva_d_canale_notifica sdcn ON sdcn.id_canale_notifica = srdc.id_canale_notifica\n" +
            "LEFT JOIN _replaceTableName_ srcpc on srcpc.id_canale_notifica = sdcn.id_canale_notifica\n" +
            "LEFT JOIN scriva_t_compilante stc ON stc.id_compilante = srcpc.id_compilante\n" +
            "WHERE sdd.cod_destinatario = 'COMPILANTE'\n" +
            "AND (srdc.data_fine IS NULL OR srdc.data_fine > now())\n" +
            "AND (sdcn.data_fine IS NULL OR sdcn.data_fine > now())\n";

    private static final String QUERY_LOAD_PREFERENZE_CANALE = "SELECT srdc.flg_canale_default as flg_abilitato_calc\n" +
            ", NULL AS id_compilante_preferenza_cn, NULL AS flg_abilitato\n" +
            ", NULL AS id_compilante, NULL AS cf_compilante, NULL AS cognome_compilante, NULL AS nome_compilante, NULL AS des_email_compilante\n" +
            ", sdcn.*\n" +
            "FROM scriva_r_destinatario_canale srdc\n" +
            "INNER JOIN scriva_d_destinatario sdd ON sdd.id_destinatario = srdc.id_destinatario\n" +
            "INNER JOIN scriva_d_canale_notifica sdcn ON sdcn.id_canale_notifica = srdc.id_canale_notifica\n" +
            "WHERE sdd.cod_destinatario = 'COMPILANTE'\n" +
            "AND (srdc.data_fine IS NULL OR srdc.data_fine > now())\n" +
            "AND (sdcn.data_fine IS NULL OR sdcn.data_fine > now())\n";

    private static final String QUERY_UPSERT_COMPILANTE_PREFERENZA = "INSERT INTO scriva.scriva_r_compilante_preferenza_cn\n" +
            "(id_compilante_preferenza_cn, id_compilante, id_canale_notifica, flg_abilitato, \n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES\n" +
            "(nextval('seq_scriva_r_compilante_preferenza_cn'), :idCompilante, :idCanaleNotifica, :flgAbilitato,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataupd, :gestAttoreUpd, :gestUid)\n" +
            "ON CONFLICT (id_compilante, id_canale_notifica)\n" +
            "DO\n" +
            "    UPDATE \n" +
            "    SET flg_abilitato = :flgAbilitato, \n" +
            "    gest_data_upd = :gestDataupd,\n" +
            "    gest_attore_upd = :gestAttoreUpd";


    private static final String WHERE_FLG_CANALE_DEFAULT = "AND srdc.flg_canale_default = 0\n";

    private static final String WHERE_ID_COMPILANTE_PREF_CANALE = "AND srcpc.id_compilante_preferenza_cn = :idPreferenza\n";

    private static final String WHERE_ID_COMPILANTE = "AND srcpc.id_compilante = :idCompilante\n";

    private static final String WHERE_CF_COMPILANTE = "AND (stc.cf_compilante = :cfCompilante OR stc.cf_compilante IS NULL)\n";

    private static final String AND_JOIN_CF_COMPILANTE = "AND stc.cf_compilante = :cfCompilante)\n";

    /**
     * Load compilanti preferenze canale list.
     *
     * @return the list
     */
    @Override
    public List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantiPreferenzeCanale() {
        logBegin(className);
        return loadCompilantePreferenze(null, null, null, Boolean.TRUE);
    }

    /**
     * Load compilante preferenza canale list.
     *
     * @param idCompilantePreferenzaCanale the id compilante preferenza canale
     * @return the list
     */
    @Override
    public List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzaCanale(Long idCompilantePreferenzaCanale) {
        logBegin(className);
        return loadCompilantePreferenze(idCompilantePreferenzaCanale, null, null, Boolean.TRUE);
    }

    /**
     * Load compilante preferenze by id compilante list.
     *
     * @param idCompilante the id compilante
     * @param flgDefault   the flg default
     * @return the list
     */
    @Override
    public List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzeCanaleByIdCompilante(Long idCompilante, Boolean flgDefault) {
        logBegin(className);
        return loadCompilantePreferenze(null, idCompilante, null, flgDefault);
    }

    /**
     * Load compilante preferenze by codice fiscale list.
     *
     * @param cfCompilante the codice fiscale
     * @param flgDefault   the flg default
     * @return the list
     */
    @Override
    public List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenzeCanaleByCodiceFiscale(String cfCompilante, Boolean flgDefault) {
        logBegin(className);
        return loadCompilantePreferenze(null, null, cfCompilante, flgDefault);
    }

    /**
     * Load compilante preferenze canale by codice fiscale with union list.
     *
     * @param flgDefault   the flg default
     * @return the list
     */
    @Override
    public List<CompilantePreferenzaCanaleExtendedDTO> loadPreferenzeCanale(Boolean flgDefault) {
        logBegin(className);
        return findListByQuery(className, getQueryPreferenzeCanale(flgDefault), new HashMap<>());
    }

    /**
     * Upsert compilante preferenza integer.
     *
     * @param dto the compilante preferenza canale
     * @return the integer
     */
    @Override
    public Integer upsertCompilantePreferenzaCanale(CompilantePreferenzaCanaleDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idCompilante", dto.getIdCompilante());
            map.put("idCanaleNotifica", dto.getIdCanaleNotifica());
            map.put("flgAbilitato", Boolean.TRUE.equals(dto.getFlgAbilitato()) ? 1 : 0);
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreUpd());
            map.put("gestDataupd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", generateGestUID(dto.getIdCompilante().toString() + dto.getIdCanaleNotifica() + now));
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPSERT_COMPILANTE_PREFERENZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load compilante preferenze list.
     *
     * @param idCompilantePreferenzaCanale the id compilante preferenza canale
     * @param idCompilante                 the id compilante
     * @param cfCompilante                 the codice fiscale
     * @return the list
     */
    private List<CompilantePreferenzaCanaleExtendedDTO> loadCompilantePreferenze(Long idCompilantePreferenzaCanale, Long idCompilante, String cfCompilante, Boolean flgCanaleDefault) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompilantePreferenzaCanale", idCompilantePreferenzaCanale);
        map.put("idCompilante", idCompilante);
        map.put("cfCompilante", cfCompilante);
        logEnd(className);
        return findListByQuery(className, getQueryBase(idCompilantePreferenzaCanale, idCompilante, cfCompilante, flgCanaleDefault), map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_COMPILANTI_PREFERENZE_CANALE + WHERE_ID_COMPILANTE_PREF_CANALE, null, null);
    }

    /**
     * Gets query base.
     *
     * @param idCompilantePreferenzaCanale the id compilante preferenza canale
     * @param idCompilante                 the id compilante
     * @param cfCompilante                 the cf compilante
     * @param flgCanaleDefault             the flg canale default
     * @return the query base
     */
    private String getQueryBase(Long idCompilantePreferenzaCanale, Long idCompilante, String cfCompilante, Boolean flgCanaleDefault) {
        logBegin(className);
        String query = QUERY_LOAD_COMPILANTI_PREFERENZE_CANALE;

        if (idCompilantePreferenzaCanale != null) {
            query += WHERE_ID_COMPILANTE_PREF_CANALE;
        }
        if (idCompilante != null) {
            query += WHERE_ID_COMPILANTE;
        }
        if (StringUtils.isNotBlank(cfCompilante)) {
            query += WHERE_CF_COMPILANTE;
        }
        if (Boolean.FALSE.equals(flgCanaleDefault)) {
            query += WHERE_FLG_CANALE_DEFAULT;
        }
        logEnd(className);
        return query;
    }

    /**
     * Gets query union.
     *
     * @param flgCanaleDefault the flg canale default
     * @return the query union
     */
    private String getQueryPreferenzeCanale(Boolean flgCanaleDefault) {
        logBegin(className);
        String query = QUERY_LOAD_PREFERENZE_CANALE;
        if (Boolean.FALSE.equals(flgCanaleDefault)) {
            query += WHERE_FLG_CANALE_DEFAULT;
        }
        logEnd(className);
        return query;
    }


    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<CompilantePreferenzaCanaleDTO> getRowMapper() throws SQLException {
        return new CompilantePreferenzaCanaleRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<CompilantePreferenzaCanaleExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new CompilantePreferenzaCanaleExtendedRowMapper();
    }

    /**
     * The type Compilante preferenza canale row mapper.
     */
    public static class CompilantePreferenzaCanaleRowMapper implements RowMapper<CompilantePreferenzaCanaleDTO> {

        /**
         * Instantiates a new Compilante preferenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompilantePreferenzaCanaleRowMapper() throws SQLException {
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
        public CompilantePreferenzaCanaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompilantePreferenzaCanaleDTO bean = new CompilantePreferenzaCanaleDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CompilantePreferenzaCanaleDTO bean) throws SQLException {
            bean.setIdCompilantePreferenzaCanale(rs.getLong("id_compilante_preferenza_cn"));
            bean.setIdCompilante(rs.getLong("id_compilante"));
            bean.setIdCanaleNotifica(rs.getLong("id_canale_notifica"));
            bean.setFlgAbilitato(rs.getInt("flg_abilitato") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    /**
     * The type Compilante preferenza canale extended row mapper.
     */
    public static class CompilantePreferenzaCanaleExtendedRowMapper implements RowMapper<CompilantePreferenzaCanaleExtendedDTO> {

        /**
         * Instantiates a new Compilante preferenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompilantePreferenzaCanaleExtendedRowMapper() throws SQLException {
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
        public CompilantePreferenzaCanaleExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompilantePreferenzaCanaleExtendedDTO bean = new CompilantePreferenzaCanaleExtendedDTO();
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
        public void populateBean(ResultSet rs, CompilantePreferenzaCanaleExtendedDTO bean) throws SQLException {
            bean.setIdCompilantePreferenzaCanale(rs.getLong("id_compilante_preferenza_cn"));

            CompilanteDTO compilante = new CompilanteDTO();
            populateCompilanteBean(rs, compilante);
            bean.setCompilante(compilante.getIdCompilante() != 0 ? compilante : null);

            CanaleNotificaExtendedDTO canaleNotifica = new CanaleNotificaExtendedDTO();
            populateCanaleNotificaBean(rs, canaleNotifica);
            bean.setCanaleNotifica(canaleNotifica);
            bean.setFlgAbilitato(rs.getInt("flg_abilitato_calc") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateCompilanteBean(ResultSet rs, CompilanteDTO bean) throws SQLException {
            bean.setIdCompilante(rs.getLong("id_compilante"));
            bean.setCodiceFiscaleCompilante(rs.getString("cf_compilante"));
            bean.setCognomeCompilante(rs.getString("cognome_compilante"));
            bean.setNomeCompilante(rs.getString("nome_compilante"));
            bean.setDesEmailCompilante(rs.getString("des_email_compilante"));
        }

        /**
         * Populate canale notifica bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateCanaleNotificaBean(ResultSet rs, CanaleNotificaExtendedDTO bean) throws SQLException {
            bean.setIdCanaleNotifica(rs.getLong("id_canale_notifica"));
            bean.setIdComponenteApplPush(rs.getLong("id_componente_appl_push"));
            bean.setCodCanaleNotifica(rs.getString("cod_canale_notifica"));
            bean.setDesCanaleNotifica(rs.getString("des_canale_notifica"));
            bean.setFlgCampoCc(rs.getBoolean("flg_campo_cc"));
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setIndTipoCanale(rs.getString("ind_tipo_canale"));
        }

    }

}