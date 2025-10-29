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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.RegioneDAO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Regione dao.
 *
 * @author CSI PIEMONTE
 */
public class RegioneDAOImpl extends ScrivaBeSrvGenericDAO<RegioneDTO> implements RegioneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String DEFAULT_ORDER_BY_CLAUSE = "ORDER BY sdr.denom_regione ";

    private static final String WHERE_DATA_FINE_VAL_NULL = "AND sdr.data_fine_validita IS NULL AND sdn.data_fine_validita IS NULL\n";

    private static final String WHERE_COD_ISTAT = "AND cod_regione = :codIstatRegione\n";

    private static final String WHERE_COD_ISTAT_NAZIONE = "AND cod_istat_nazione = :codIstatNazione\n";

    private static final String QUERY_LOAD_REGIONI = "SELECT sdr.*, sdr.data_inizio_validita AS data_inizio_validita_r, sdr.data_fine_validita AS data_fine_validita_r\n" +
            ", sdn.*, sdn.data_inizio_validita AS data_inizio_validita_n, sdn.data_fine_validita AS data_fine_validita_n\n" +
            "FROM _replaceTableName_ sdr\n" +
            "INNER JOIN scriva_d_nazione sdn on sdn.id_nazione = sdr.id_nazione\n" +
            "WHERE 1=1 ";

    private static final String QUERY_LOAD_REGIONI_ATTIVE = QUERY_LOAD_REGIONI + WHERE_DATA_FINE_VAL_NULL;

    private static final String QUERY_LOAD_REGIONE_BY_ID = QUERY_LOAD_REGIONI + "AND id_regione = :idRegione\n";

    private static final String QUERY_LOAD_REGIONE_BY_COD_ISTAT = QUERY_LOAD_REGIONI + WHERE_COD_ISTAT;

    private static final String QUERY_LOAD_REGIONE_BY_DENOM = QUERY_LOAD_REGIONI + "AND denom_regione = :denomRegione\n";

    private static final String QUERY_LOAD_REGIONE_BY_ID_NAZIONE = QUERY_LOAD_REGIONI + "AND id_nazione = :idNazione\n" + DEFAULT_ORDER_BY_CLAUSE;

    private static final String QUERY_LOAD_REGIONE_BY_COD_ISTAT_NAZIONE = QUERY_LOAD_REGIONI + WHERE_COD_ISTAT_NAZIONE + DEFAULT_ORDER_BY_CLAUSE;

    private static final String QUERY_LOAD_REGIONE_BY_DENOM_NAZIONE = QUERY_LOAD_REGIONI + "AND denom_nazione = :denomNazione\n" + DEFAULT_ORDER_BY_CLAUSE;

    /**
     * Load regioni list.
     *
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioni() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_REGIONI + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load regioni list.
     *
     * @param codIstatRegione the cod istat regione
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioni(String codIstatRegione, String codIstatNazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_LOAD_REGIONI;
        if (StringUtils.isNotBlank(codIstatRegione)) {
            map.put("codIstatRegione", codIstatRegione);
            query += WHERE_COD_ISTAT;
        }
        if (StringUtils.isNotBlank(codIstatNazione)) {
            map.put("codIstatNazione", codIstatNazione);
            query += WHERE_COD_ISTAT_NAZIONE;
        }
        return findListByQuery(className, query + DEFAULT_ORDER_BY_CLAUSE, map);
    }

    /**
     * Load regioni attive list.
     *
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioniAttive() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_REGIONI_ATTIVE + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load regione by id list.
     *
     * @param idRegione the id regione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioneById(Long idRegione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRegione", idRegione);
        return findListByQuery(className, QUERY_LOAD_REGIONE_BY_ID, map);
    }

    /**
     * Load regione by cod istat list.
     *
     * @param codIstatRegione the cod istat regione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioneByCodIstat(String codIstatRegione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatRegione", codIstatRegione);
        return findListByQuery(className, QUERY_LOAD_REGIONE_BY_COD_ISTAT, map);
    }

    /**
     * Load regione by denominazione list.
     *
     * @param denomRegione the denom regione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioneByDenominazione(String denomRegione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("denomRegione", denomRegione);
        return findListByQuery(className, QUERY_LOAD_REGIONE_BY_DENOM, map);
    }

    /**
     * Load regione by id nazione list.
     *
     * @param idNazione the id nazione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioneByIdNazione(Long idNazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idNazione", idNazione);
        return findListByQuery(className, QUERY_LOAD_REGIONE_BY_ID_NAZIONE, map);
    }

    /**
     * Load regione by cod istat nazione list.
     *
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioneByCodIstatNazione(String codIstatNazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatNazione", codIstatNazione);
        return findListByQuery(className, QUERY_LOAD_REGIONE_BY_COD_ISTAT_NAZIONE, map);
    }

    /**
     * Load regione by denom nazione list.
     *
     * @param denomNazione the denom nazione
     * @return the list
     */
    @Override
    public List<RegioneExtendedDTO> loadRegioneByDenomNazione(Long denomNazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("denomNazione", denomNazione);
        return findListByQuery(className, QUERY_LOAD_REGIONE_BY_DENOM_NAZIONE, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_REGIONE_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<RegioneDTO> getRowMapper() throws SQLException {
        return new RegioneRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<RegioneExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new RegioneExtendedRowMapper();
    }

    private static class RegioneRowMapper implements RowMapper<RegioneDTO> {

        /**
         * Instantiates a new Nazione row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RegioneRowMapper() throws SQLException {
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
        public RegioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RegioneDTO bean = new RegioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RegioneDTO bean) throws SQLException {
            bean.setIdRegione(rs.getLong("id_regione"));
            bean.setCodRegione(rs.getString("cod_regione"));
            bean.setDenomRegione(rs.getString("denom_regione"));
            bean.setIdNazione(rs.getLong("id_nazione"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_r"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_r"));
        }
    }

    private static class RegioneExtendedRowMapper implements RowMapper<RegioneExtendedDTO> {

        /**
         * Instantiates a new Nazione row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RegioneExtendedRowMapper() throws SQLException {
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
        public RegioneExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RegioneExtendedDTO bean = new RegioneExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RegioneExtendedDTO bean) throws SQLException {
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