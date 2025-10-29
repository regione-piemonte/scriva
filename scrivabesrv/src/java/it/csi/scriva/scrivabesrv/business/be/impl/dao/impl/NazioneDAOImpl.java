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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.NazioneDAO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Nazione dao.
 *
 * @author CSI PIEMONTE
 */
public class NazioneDAOImpl extends ScrivaBeSrvGenericDAO<NazioneDTO> implements NazioneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String DEFAULT_ORDER_BY_CLAUSE = "ORDER BY denom_nazione ";

    private static final String WHERE_DATA_FINE_VAL_NULL = "AND data_fine_validita IS NULL\n";

    private static final String QUERY_LOAD_NAZIONI = "SELECT * FROM _replaceTableName_ WHERE 1=1\n";

    private static final String QUERY_LOAD_NAZIONI_ATTIVE = QUERY_LOAD_NAZIONI + WHERE_DATA_FINE_VAL_NULL;

    private static final String QUERY_LOAD_NAZIONE_BY_ID = QUERY_LOAD_NAZIONI + "AND id_nazione = :idNazione\n";

    private static final String QUERY_LOAD_NAZIONE_BY_COD_ISTAT = QUERY_LOAD_NAZIONI + "AND cod_istat_nazione = :codIstatNazione\n";

    private static final String QUERY_LOAD_NAZIONE_BY_DENOM = QUERY_LOAD_NAZIONI + "AND denom_nazione = :denomNazione\n";

    private static final String QUERY_LOAD_NAZIONE_BY_COD_ISO = QUERY_LOAD_NAZIONI + "AND cod_iso2 = :codIso\n";

    /**
     * Load nazioni list.
     *
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioni() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_NAZIONI + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load nazioni attive list.
     *
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioniAttive() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_NAZIONI_ATTIVE + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load nazione by id list.
     *
     * @param idNazione the id nazione
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioneById(Long idNazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idNazione", idNazione);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_NAZIONE_BY_ID, map);
    }

    /**
     * Load nazione by cod istat list.
     *
     * @param codIstatNazione the cod istat nazione
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioneByCodIstat(String codIstatNazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatNazione", codIstatNazione);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_NAZIONE_BY_COD_ISTAT, map);
    }

    /**
     * Load nazione by denominazione list.
     *
     * @param denomNazione the denom nazione
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioneByDenominazione(String denomNazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("denomNazione", denomNazione);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_NAZIONE_BY_DENOM, map);
    }

    /**
     * Load nazione by cod iso list.
     *
     * @param codIso the cod iso
     * @return the list
     */
    @Override
    public List<NazioneDTO> loadNazioneByCodIso(String codIso) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIso", codIso);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_NAZIONE_BY_COD_ISO, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_NAZIONE_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<NazioneDTO> getRowMapper() throws SQLException {
        return new NazioneRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<NazioneDTO> getExtendedRowMapper() throws SQLException {
        return new NazioneRowMapper();
    }

    /**
     * The type Nazione row mapper.
     */
    public static class NazioneRowMapper implements RowMapper<NazioneDTO> {

        /**
         * Instantiates a new Nazione row mapper.
         *
         * @throws SQLException the sql exception
         */
        public NazioneRowMapper() throws SQLException {
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
        public NazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NazioneDTO bean = new NazioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, NazioneDTO bean) throws SQLException {
            bean.setIdNazione(rs.getLong("id_nazione"));
            bean.setCodIstatNazione(rs.getString("cod_istat_nazione"));
            bean.setCodBelfioreNazione(rs.getString("cod_belfiore_nazione"));
            bean.setDenomNazione(rs.getString("denom_nazione"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setDtIdStato(rs.getLong("dt_id_stato"));
            bean.setDtIdStatoPrev(rs.getLong("dt_id_stato_prev"));
            bean.setDtIdStatoNext(rs.getLong("dt_id_stato_next"));
            bean.setIdOrigine(rs.getLong("id_origine"));
            bean.setCodIso2(rs.getString("cod_iso2"));
        }
    }

}