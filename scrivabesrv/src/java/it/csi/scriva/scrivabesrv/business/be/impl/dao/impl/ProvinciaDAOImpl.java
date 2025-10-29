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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ProvinciaDAO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Provincia dao.
 *
 * @author CSI PIEMONTE
 */
public class ProvinciaDAOImpl extends ScrivaBeSrvGenericDAO<ProvinciaDTO> implements ProvinciaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String DEFAULT_ORDER_BY_CLAUSE = "ORDER BY denom_provincia ";

    private static final String ORDER_BY_ADEMPI_PROVINCIA = "ORDER BY srap.ordinamento_adempi_provincia\n";

    private static final String WHERE_DATA_FINE_VAL_NULL = "AND sdp.data_fine_validita IS NULL\n" +
            "AND sdr.data_fine_validita IS NULL\n" +
            "AND sdn.data_fine_validita IS NULL\n";

    private static final String WHERE_COD_ISTAT = "AND cod_provincia = :codIstatProvincia\n";

    private static final String WHERE_COD_ISTAT_REGIONE = "AND cod_regione = :codIstatRegione\n";

    private static final String QUERY_LOAD_PROVINCE = "SELECT sdp.*, sdp.data_inizio_validita AS data_inizio_validita_p, sdp.data_fine_validita AS data_fine_validita_p\n" +
            ", sdr.*, sdr.data_inizio_validita AS data_inizio_validita_r, sdr.data_fine_validita AS data_fine_validita_r\n" +
            ", sdn.*, sdn.data_inizio_validita AS data_inizio_validita_n, sdn.data_fine_validita AS data_fine_validita_n\n" +
            ", NULL AS ordinamento_adempi_provincia, 9 AS flg_limitrofa\n" +
            "FROM scriva_d_provincia sdp\n" +
            "INNER JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione\n" +
            "INNER JOIN scriva_d_nazione sdn on sdn.id_nazione = sdr.id_nazione\n" +
            "WHERE 1=1\n";

    private static final String QUERY_LOAD_PROVINCE_ATTIVE = QUERY_LOAD_PROVINCE + WHERE_DATA_FINE_VAL_NULL;

    private static final String QUERY_LOAD_PROVINCIA_BY_ID = QUERY_LOAD_PROVINCE + "AND id_provincia = :idProvincia\n";

    private static final String QUERY_LOAD_PROVINCIA_BY_COD_ISTAT = QUERY_LOAD_PROVINCE + WHERE_COD_ISTAT;

    private static final String QUERY_LOAD_PROVINCIA_BY_DENOM = QUERY_LOAD_PROVINCE + "AND denom_provincia = :denomProvincia\n";

    private static final String QUERY_LOAD_PROVINCIA_BY_ID_REGIONE = QUERY_LOAD_PROVINCE + "AND id_regione = :idRegione\n" + DEFAULT_ORDER_BY_CLAUSE;

    private static final String QUERY_LOAD_PROVINCIA_BY_COD_ISTAT_REGIONE = QUERY_LOAD_PROVINCE + WHERE_COD_ISTAT_REGIONE + DEFAULT_ORDER_BY_CLAUSE;

    private static final String QUERY_LOAD_PROVINCIA_BY_DENOM_REGIONE = QUERY_LOAD_PROVINCE + "AND denom_regione = :denomRegione\n" + DEFAULT_ORDER_BY_CLAUSE;

    private static final String QUERY_LOAD_PROVINCIA_BY_ID_ADEMPIMENTO = "SELECT sdp.*, sdp.data_inizio_validita AS data_inizio_validita_p, sdp.data_fine_validita AS data_fine_validita_p\n" +
            ", sdr.*, sdr.data_inizio_validita AS data_inizio_validita_r, sdr.data_fine_validita AS data_fine_validita_r\n" +
            ", sdn.*, sdn.data_inizio_validita AS data_inizio_validita_n, sdn.data_fine_validita AS data_fine_validita_n\n" +
            ", srap.ordinamento_adempi_provincia, srap.flg_limitrofa\n" +
            "FROM scriva_d_provincia sdp\n" +
            "INNER JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione\n" +
            "INNER JOIN scriva_d_nazione sdn on sdn.id_nazione = sdr.id_nazione\n" +
            "INNER JOIN scriva_r_adempi_provincia srap on srap.id_provincia = sdp.id_provincia\n" +
            "WHERE srap.id_adempimento = :idAdempimento\n";

    /**
     * Load province list.
     *
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvince() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_PROVINCE + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load province list.
     *
     * @param codIstatProvincia the cod istat provincia
     * @param codIstatRegione   the cod istat regione
     * @param idAdempimento     the id adempimento
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvince(String codIstatProvincia, String codIstatRegione, Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = idAdempimento != null ? QUERY_LOAD_PROVINCIA_BY_ID_ADEMPIMENTO : QUERY_LOAD_PROVINCE;
        String orderBy = idAdempimento != null ? ORDER_BY_ADEMPI_PROVINCIA : DEFAULT_ORDER_BY_CLAUSE;
        if (StringUtils.isNotBlank(codIstatProvincia)) {
            map.put("codIstatProvincia", codIstatProvincia);
            query += WHERE_COD_ISTAT;
        }
        if (StringUtils.isNotBlank(codIstatRegione)) {
            map.put("codIstatRegione", codIstatRegione);
            query += WHERE_COD_ISTAT_REGIONE;
        }
        if (idAdempimento != null) {
            map.put("idAdempimento", idAdempimento);
        }
        return findListByQuery(className, query + orderBy, map);
    }

    /**
     * Load province attive list.
     *
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinceAttive() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_PROVINCE_ATTIVE + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load provincia by id list.
     *
     * @param idProvincia the id provincia
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinciaById(Long idProvincia) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idProvincia", idProvincia);
        return findListByQuery(className, QUERY_LOAD_PROVINCIA_BY_ID, map);
    }

    /**
     * Load provincia by cod istat list.
     *
     * @param codIstatProvincia the cod istat provincia
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinciaByCodIstat(String codIstatProvincia) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatProvincia", codIstatProvincia);
        return findListByQuery(className, QUERY_LOAD_PROVINCIA_BY_COD_ISTAT, map);
    }

    /**
     * Load provincia by denominazione list.
     *
     * @param denomProvincia the denom provincia
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinciaByDenominazione(String denomProvincia) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("denomProvincia", denomProvincia);
        return findListByQuery(className, QUERY_LOAD_PROVINCIA_BY_DENOM, map);
    }

    /**
     * Load provincia by id regione list.
     *
     * @param idRegione the id regione
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinciaByIdRegione(Long idRegione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRegione", idRegione);
        return findListByQuery(className, QUERY_LOAD_PROVINCIA_BY_ID_REGIONE, map);
    }

    /**
     * Load provincia by cod istat regione list.
     *
     * @param codIstatRegione the cod istat regione
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinciaByCodIstatRegione(String codIstatRegione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatRegione", codIstatRegione);
        return findListByQuery(className, QUERY_LOAD_PROVINCIA_BY_COD_ISTAT_REGIONE, map);
    }

    /**
     * Load provincia by denom regione list.
     *
     * @param denomRegione the denom regione
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinciaByDenomRegione(Long denomRegione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("denomRegione", denomRegione);
        return findListByQuery(className, QUERY_LOAD_PROVINCIA_BY_DENOM_REGIONE, map);
    }

    /**
     * Load provincia by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<ProvinciaExtendedDTO> loadProvinciaByIdAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_LOAD_PROVINCIA_BY_ID_ADEMPIMENTO + ORDER_BY_ADEMPI_PROVINCIA, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_PROVINCIA_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ProvinciaDTO> getRowMapper() throws SQLException {
        return new ProvinciaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ProvinciaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new ProvinciaExtendedRowMapper();
    }

    /**
     * The type Provincia row mapper.
     */
    public static class ProvinciaRowMapper implements RowMapper<ProvinciaDTO> {

        /**
         * Instantiates a new Provincia row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ProvinciaRowMapper() throws SQLException {
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
        public ProvinciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProvinciaDTO bean = new ProvinciaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ProvinciaDTO bean) throws SQLException {
            bean.setIdProvincia(rs.getLong("id_provincia"));
            bean.setCodProvincia(rs.getString("cod_provincia"));
            bean.setDenomProvincia(rs.getString("denom_provincia"));
            bean.setSiglaProvincia(rs.getString("sigla_provincia"));
            bean.setIdRegione(rs.getLong("id_regione"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_p"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_p"));
            bean.setOrdinamentoAdempiProvincia(rs.getInt("ordinamento_adempi_provincia"));
            if (rs.getInt("flg_limitrofa") != 9) {
                bean.setFlgLimitrofa(rs.getInt("flg_limitrofa") == 1 ? Boolean.TRUE : Boolean.FALSE);
            }
        }
    }

    /**
     * The type Provincia extended row mapper.
     */
    public static class ProvinciaExtendedRowMapper implements RowMapper<ProvinciaExtendedDTO> {

        /**
         * Instantiates a new Provincia row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ProvinciaExtendedRowMapper() throws SQLException {
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
        public ProvinciaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProvinciaExtendedDTO bean = new ProvinciaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ProvinciaExtendedDTO bean) throws SQLException {
            bean.setIdProvincia(rs.getLong("id_provincia"));
            bean.setCodProvincia(rs.getString("cod_provincia"));
            bean.setDenomProvincia(rs.getString("denom_provincia"));
            bean.setSiglaProvincia(rs.getString("sigla_provincia"));

            RegioneExtendedDTO regione = new RegioneExtendedDTO();
            populateBeanRegione(rs, regione);
            bean.setRegione(regione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_p"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_p"));
            bean.setOrdinamentoAdempiProvincia(rs.getInt("ordinamento_adempi_provincia"));
            if (rs.getInt("flg_limitrofa") != 9) {
                bean.setFlgLimitrofa(rs.getInt("flg_limitrofa") == 1 ? Boolean.TRUE : Boolean.FALSE);
            }
        }

        private void populateBeanRegione(ResultSet rs, RegioneExtendedDTO bean) throws SQLException {
            bean.setIdRegione(rs.getLong("id_regione"));
            bean.setCodRegione(rs.getString("cod_regione"));
            bean.setDenomRegione(rs.getString("denom_regione"));

            NazioneDTO nazione = new NazioneDTO();
            populateBeanNazione(rs, nazione);
            bean.setNazione(nazione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_r"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_r"));
        }

        private void populateBeanNazione(ResultSet rs, NazioneDTO bean) throws SQLException {
            bean.setIdNazione(rs.getLong("id_nazione"));
            bean.setCodIstatNazione(rs.getString("cod_istat_nazione"));
            bean.setCodBelfioreNazione(rs.getString("cod_belfiore_nazione"));
            bean.setDenomNazione(rs.getString("denom_nazione"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_n"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_n"));
            bean.setDtIdStato(rs.getLong("dt_id_stato"));
            bean.setDtIdStatoPrev(rs.getLong("dt_id_stato_prev"));
            bean.setDtIdStatoNext(rs.getLong("dt_id_stato_next"));
            bean.setIdOrigine(rs.getLong("id_origine"));
            bean.setCodIso2(rs.getString("cod_iso2"));
        }
    }
}