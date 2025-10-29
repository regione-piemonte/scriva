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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ComuneDAO;
import it.csi.scriva.scrivabesrv.dto.ComuneDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
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
 * The type Comune dao.
 *
 * @author CSI PIEMONTE
 */
public class ComuneDAOImpl extends ScrivaBeSrvGenericDAO<ComuneDTO> implements ComuneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String DEFAULT_ORDER_BY_CLAUSE = "ORDER BY denom_comune ";

    private static final String WHERE_DATA_FINE_VAL_NULL = "AND sdc.data_fine_validita IS NULL " +
            "AND sdp.data_fine_validita IS NULL " +
            "AND sdr.data_fine_validita IS NULL " +
            "AND sdn.data_fine_validita IS NULL\n";

    private static final String WHERE_COD_ISTAT = "AND cod_istat_comune = :codIstatComune\n";

    private static final String WHERE_COD_ISTAT_LIST = "AND cod_istat_comune IN (:codIstatList)\n";

    private static final String WHERE_SIGLA_PROVINCIA = "and sdp.sigla_provincia = :siglaProvincia  ";

    private static final String WHERE_COD_ISTAT_PROVINCIA = "AND cod_provincia = :codIstatProvincia\n";

    private static final String QUERY_LOAD_COMUNI = "SELECT sdc.*, sdc.data_inizio_validita AS data_inizio_validita_c, sdc.data_fine_validita AS data_fine_validita_c\n" +
            ", sdp.*, sdp.data_inizio_validita AS data_inizio_validita_p, sdp.data_fine_validita AS data_fine_validita_p\n" +
            ", sdr.*, sdr.data_inizio_validita AS data_inizio_validita_r, sdr.data_fine_validita AS data_fine_validita_r\n" +
            ", sdn.*, sdn.data_inizio_validita AS data_inizio_validita_n, sdn.data_fine_validita AS data_fine_validita_n\n" +
            "FROM scriva_d_comune sdc\n" +
            "INNER JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia\n" +
            "INNER JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione\n" +
            "INNER JOIN scriva_d_nazione sdn on sdn.id_nazione = sdr.id_nazione\n" +
            "WHERE 1=1\n";

    private static final String QUERY_LOAD_COMUNI_ATTIVI = QUERY_LOAD_COMUNI + WHERE_DATA_FINE_VAL_NULL;

    private static final String QUERY_LOAD_COMUNE_BY_ID = QUERY_LOAD_COMUNI + "AND id_comune = :idComune\n";

    private static final String QUERY_LOAD_COMUNE_BY_COD_ISTAT = QUERY_LOAD_COMUNI + WHERE_COD_ISTAT;

    private static final String QUERY_LOAD_COMUNE_BY_DENOM = QUERY_LOAD_COMUNI + "AND denom_comune = :denomComune\n";

    private static final String QUERY_LOAD_COMUNE_BY_ID_PROVINCIA = QUERY_LOAD_COMUNI + "AND id_provincia = :idProvincia\n" + DEFAULT_ORDER_BY_CLAUSE;

    private static final String QUERY_LOAD_COMUNE_BY_COD_ISTAT_PROVINCIA = QUERY_LOAD_COMUNI + "AND cod_provincia = :codIstatProvincia\n" + DEFAULT_ORDER_BY_CLAUSE;

    private static final String QUERY_LOAD_COMUNE_BY_DENOM_PROVINCIA = QUERY_LOAD_COMUNI + "AND denom_provincia = :denomProvincia\n" + DEFAULT_ORDER_BY_CLAUSE;

    /**
     * Load comuni list.
     *
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuni() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_COMUNI + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load comuni list.
     *
     * @param codIstatComune    the cod istat
     * @param codIstatProvincia the cod istat provincia
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuni(String codIstatComune, String codIstatProvincia, String siglaProvincia) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_LOAD_COMUNI;
        if (StringUtils.isNotBlank(codIstatComune)) {
            map.put("codIstatComune", codIstatComune);
            query += WHERE_COD_ISTAT;
        }
        if (StringUtils.isNotBlank(codIstatProvincia)) {
            map.put("codIstatProvincia", codIstatProvincia);
            query += WHERE_COD_ISTAT_PROVINCIA;
        }
        if (StringUtils.isNotBlank(siglaProvincia)) {
            map.put("siglaProvincia", siglaProvincia);
            query += WHERE_SIGLA_PROVINCIA;
        }

        return findListByQuery(className, query + DEFAULT_ORDER_BY_CLAUSE, map);
    }

    /**
     * Load comuni list.
     *
     * @param codIstatList the cod istat list
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuni(List<String> codIstatList) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatList", codIstatList);
        return findListByQuery(className, QUERY_LOAD_COMUNI + WHERE_COD_ISTAT_LIST, map);
    }

    /**
     * Load comuni attivi list.
     *
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuniAttivi() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_COMUNI_ATTIVI + DEFAULT_ORDER_BY_CLAUSE, null);
    }

    /**
     * Load comune by id list.
     *
     * @param idComune the id comune
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuneById(Long idComune) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idComune", idComune);
        return findListByQuery(className, QUERY_LOAD_COMUNE_BY_ID, map);
    }

    /**
     * Load comune by cod istat list.
     *
     * @param codIstatComune the cod istat comune
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuneByCodIstat(String codIstatComune) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatComune", codIstatComune);
        return findListByQuery(className, QUERY_LOAD_COMUNE_BY_COD_ISTAT, map);
    }

    /**
     * Load comune by denominazione list.
     *
     * @param denomComune the denom comune
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuneByDenominazione(String denomComune) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("denomComune", denomComune);
        return findListByQuery(className, QUERY_LOAD_COMUNE_BY_DENOM, map);
    }

    /**
     * Load comune by id regione list.
     *
     * @param idProvincia the id provincia
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuneByIdProvincia(Long idProvincia) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idProvincia", idProvincia);
        return findListByQuery(className, QUERY_LOAD_COMUNE_BY_ID_PROVINCIA, map);
    }

    /**
     * Load comune by cod istat provincia list.
     *
     * @param codIstatProvincia the cod istat provincia
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuneByCodIstatProvincia(String codIstatProvincia) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstatProvincia", codIstatProvincia);
        return findListByQuery(className, QUERY_LOAD_COMUNE_BY_COD_ISTAT_PROVINCIA, map);
    }

    /**
     * Load comune by denom regione list.
     *
     * @param denomProvincia the denom provincia
     * @return the list
     */
    @Override
    public List<ComuneExtendedDTO> loadComuneByDenomProvincia(Long denomProvincia) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("denomProvincia", denomProvincia);
        return findListByQuery(className, QUERY_LOAD_COMUNE_BY_DENOM_PROVINCIA, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_COMUNE_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ComuneDTO> getRowMapper() throws SQLException {
        return new ComuneRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ComuneExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new ComuneExtendedRowMapper();
    }

    /**
     * The type Comune row mapper.
     */
    public static class ComuneRowMapper implements RowMapper<ComuneDTO> {

        /**
         * Instantiates a new Comune row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ComuneRowMapper() throws SQLException {
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
        public ComuneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ComuneDTO bean = new ComuneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ComuneDTO bean) throws SQLException {
            bean.setIdComune(rs.getLong("id_comune"));
            bean.setCodIstatComune(rs.getString("cod_istat_comune"));
            bean.setCodBelfioreComune(rs.getString("cod_belfiore_comune"));
            bean.setDenomComune(rs.getString("denom_comune"));
            bean.setIdProvincia(rs.getLong("id_provincia"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_c"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_c"));
            bean.setDtIdComune(rs.getLong("dt_id_comune"));
            bean.setDtIdComunePrev(rs.getLong("dt_id_comune_prev"));
            bean.setDtIdComuneNext(rs.getLong("dt_id_comune_next"));
            bean.setCapComune(rs.getString("cap_comune"));
        }
    }

    /**
     * The type Comune extended row mapper.
     */
    public static class ComuneExtendedRowMapper implements RowMapper<ComuneExtendedDTO> {

        /**
         * Instantiates a new Comune row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ComuneExtendedRowMapper() throws SQLException {
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
        public ComuneExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ComuneExtendedDTO bean = new ComuneExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ComuneExtendedDTO bean) throws SQLException {
            bean.setIdComune(rs.getLong("id_comune"));
            bean.setCodIstatComune(rs.getString("cod_istat_comune"));
            bean.setCodBelfioreComune(rs.getString("cod_belfiore_comune"));
            bean.setDenomComune(rs.getString("denom_comune"));

            ProvinciaExtendedDTO provincia = new ProvinciaExtendedDTO();
            populateBeanProvincia(rs, provincia);
            bean.setProvincia(provincia);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_c"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_c"));
            bean.setDtIdComune(rs.getLong("dt_id_comune"));
            bean.setDtIdComunePrev(rs.getLong("dt_id_comune_prev"));
            bean.setDtIdComuneNext(rs.getLong("dt_id_comune_next"));
            bean.setCapComune(rs.getString("cap_comune"));
        }

        private void populateBeanProvincia(ResultSet rs, ProvinciaExtendedDTO bean) throws SQLException {
            bean.setIdProvincia(rs.getLong("id_provincia"));
            bean.setCodProvincia(rs.getString("cod_provincia"));
            bean.setDenomProvincia(rs.getString("denom_provincia"));
            bean.setSiglaProvincia(rs.getString("sigla_provincia"));

            RegioneExtendedDTO regione = new RegioneExtendedDTO();
            populateBeanRegione(rs, regione);
            bean.setRegione(regione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_p"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_p"));
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