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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.FunzionarioCompetenzaDAO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Funzionario competenza dao.
 *
 * @author CSI PIEMONTE
 */
public class FunzionarioCompetenzaDAOImpl extends ScrivaBeSrvGenericDAO<FunzionarioCompetenzaDTO> implements FunzionarioCompetenzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_FUNZIONARIO_COMPETENZA = "SELECT srfc.*, srfc.data_inizio_validita AS data_inizio_validita_fc, srfc.data_fine_validita AS data_fine_validita_fc, \n" +
            "stct.*, stct.id_competenza_territorio AS competenza_territorio_id, stct.data_inizio_validita AS data_inizio_validita_ct, stct.data_fine_validita AS data_fine_validita_ct, \n" +
            "sdtc.*, sdtc.id_tipo_competenza AS tipo_competenza_id, \n" +
            "sdec.*, sdec.id_ente_creditore AS ente_creditore_id, \n" +
            "sdc.*, sdc.id_comune AS comune_id, sdc.data_inizio_validita AS data_inizio_validita_sdc, sdc.data_fine_validita AS data_fine_validita_sdc, \n" +
            "sdp.*, sdp.id_provincia AS provincia_id, sdp.data_inizio_validita AS data_inizio_validita_sdp, sdp.data_fine_validita AS data_fine_validita_sdp, \n" +
            "sdr.*, sdr.id_regione AS regione_id, sdr.data_inizio_validita AS data_inizio_validita_sdr, sdr.data_fine_validita AS data_fine_validita_sdr, \n" +
            "sdn.*, sdn.id_nazione AS nazione_id, sdn.data_inizio_validita AS data_inizio_validita_sdn, sdn.data_fine_validita AS data_fine_validita_sdn \n" +
            "FROM _replaceTableName_ srfc \n" +
            "INNER JOIN scriva_t_funzionario stf ON stf.id_funzionario = srfc.id_funzionario \n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srfc.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \n" +
            "left outer JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore \n" +
            "left outer JOIN scriva_d_comune sdc ON sdc.id_comune = stct.id_comune_competenza  \n" +
            "left outer JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia \n" +
            "left outer JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione \n" +
            "left outer JOIN scriva_d_nazione sdn ON sdn.id_nazione = sdr.id_nazione \n" +
            "WHERE 1=1 \n";

    private static final String QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_ID_FUNZIONARIO = QUERY_LOAD_FUNZIONARIO_COMPETENZA + "AND srfc.id_funzionario = :idFunzionario \n";

    private static final String QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_ID_COMPETENZA = QUERY_LOAD_FUNZIONARIO_COMPETENZA + "AND srfc.id_competenza_territorio = :idCompetenza \n";

    private static final String QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_ID_FUNZIONARIO_COMPETENZA = QUERY_LOAD_FUNZIONARIO_COMPETENZA
            + "AND srfc.id_funzionario = :idFunzionario \n"
            + "AND srfc.id_competenza_territorio = :idCompetenza \n";

    private static final String QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_CF = QUERY_LOAD_FUNZIONARIO_COMPETENZA + "AND stf.cf_funzionario = :codiceFiscaleFunzionario \n";

    /**
     * Load funzionari competenza list.
     *
     * @return the list
     */
    @Override
    public List<FunzionarioCompetenzaExtendedDTO> loadFunzionariCompetenza() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_COMPETENZA, null);
    }

    /**
     * Load funzionario competenza by id funzionario list.
     *
     * @param idFunzionario the id funzionario
     * @return the list
     */
    @Override
    public List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByIdFunzionario(Long idFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idFunzionario", idFunzionario);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_ID_FUNZIONARIO, map);
    }

    /**
     * Load funzionario competenza by id competenza list.
     *
     * @param idCompetenza the id competenza
     * @return the list
     */
    @Override
    public List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByIdCompetenza(Long idCompetenza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompetenza", idCompetenza);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_ID_COMPETENZA, map);
    }

    /**
     * Load funzionario competenza by id funzionario competenza list.
     *
     * @param idFunzionario the id funzionario
     * @param idCompetenza  the id competenza
     * @return the list
     */
    @Override
    public List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByIdFunzionarioCompetenza(Long idFunzionario, Long idCompetenza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idFunzionario", idFunzionario);
        map.put("idCompetenza", idCompetenza);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_ID_FUNZIONARIO_COMPETENZA, map);
    }

    /**
     * Load funzionario competenza by cf list.
     *
     * @param codiceFiscaleFunzionario the codice fiscale funzionario
     * @return the list
     */
    @Override
    public List<FunzionarioCompetenzaExtendedDTO> loadFunzionarioCompetenzaByCf(String codiceFiscaleFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codiceFiscaleFunzionario", codiceFiscaleFunzionario);
        return findListByQuery(className, QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_CF, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_FUNZIONARIO_COMPETENZA_BY_ID_FUNZIONARIO_COMPETENZA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<FunzionarioCompetenzaDTO> getRowMapper() throws SQLException {
        return new FunzionarioCompetenzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<FunzionarioCompetenzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new FunzionarioCompetenzaExtendedRowMapper();
    }

    /**
     * The type Funzionario competenza row mapper.
     */
    public static class FunzionarioCompetenzaRowMapper implements RowMapper<FunzionarioCompetenzaDTO> {

        /**
         * Instantiates a new Ente creditore row mapper.
         *
         * @throws SQLException the sql exception
         */
        public FunzionarioCompetenzaRowMapper() throws SQLException {
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
        public FunzionarioCompetenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FunzionarioCompetenzaDTO bean = new FunzionarioCompetenzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, FunzionarioCompetenzaDTO bean) throws SQLException {
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
        }
    }

    /**
     * The type Funzionario competenza extended row mapper.
     */
    public static class FunzionarioCompetenzaExtendedRowMapper implements RowMapper<FunzionarioCompetenzaExtendedDTO> {

        /**
         * Instantiates a new Ente creditore row mapper.
         *
         * @throws SQLException the sql exception
         */
        public FunzionarioCompetenzaExtendedRowMapper() throws SQLException {
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
        public FunzionarioCompetenzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FunzionarioCompetenzaExtendedDTO bean = new FunzionarioCompetenzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, FunzionarioCompetenzaExtendedDTO bean) throws SQLException {
            bean.setIdFunzionario(rs.getLong("id_funzionario"));

            CompetenzaTerritorioExtendedDTO competenzaTerritorio = new CompetenzaTerritorioExtendedDTO();
            populateBeanCompetenza(rs, competenzaTerritorio);
            bean.setCompetenzaTerritorio(competenzaTerritorio);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
        }

        private void populateBeanCompetenza(ResultSet rs, CompetenzaTerritorioExtendedDTO bean) throws SQLException {
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));

            TipoCompetenzaDTO tipoCompetenza = new TipoCompetenzaDTO();
            populateBeanTipoCompetenza(rs, tipoCompetenza);
            bean.setTipoCompetenza(tipoCompetenza);

            EnteCreditoreDTO enteCreditore = new EnteCreditoreDTO();
            populateBeanEnteCreditore(rs, enteCreditore);
            bean.setEnteCreditore(enteCreditore);

            ComuneExtendedDTO comuneCompetenza = new ComuneExtendedDTO();
            populateBeanComuneCompetenza(rs, comuneCompetenza);
            bean.setComuneCompetenza(comuneCompetenza);

            bean.setCodCompetenzaTerritorio(rs.getString("cod_competenza_territorio"));
            bean.setDesCompetenzaTerritorio(rs.getString("des_competenza_territorio"));
            bean.setDesCompetenzaTerritorioEstesa(rs.getString("des_competenza_territorio_estesa"));
            bean.setCodSuap(rs.getString("cod_suap"));
            bean.setIndirizzoCompetenza(rs.getString("indirizzo_competenza"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setCapCompetenza(rs.getString("cap_competenza"));
            bean.setDesEmailPec(rs.getString("des_pec"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setOrario(rs.getString("orario"));
            bean.setSitoWeb(rs.getString("sito_web"));
            //bean.setResponsabile(rs.getString("responsabile"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setCodIpa(rs.getString("cod_ipa"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setNumDimensionePec(rs.getString("num_dimensione_pec"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanTipoCompetenza(ResultSet rs, TipoCompetenzaDTO bean) throws SQLException {
            bean.setIdTipoCompetenza(rs.getLong("tipo_competenza_id"));
            bean.setCodTipoCompetenza(rs.getString("cod_tipo_competenza"));
            bean.setDesTipoCompetenza(rs.getString("des_tipo_competenza"));
            bean.setDesTipoCompetenzaEstesa(rs.getString("des_tipo_competenza_estesa"));
            bean.setOrdinamentoTipoCompetenza(rs.getInt("ordinamento_tipo_competenza"));
        }

        private void populateBeanEnteCreditore(ResultSet rs, EnteCreditoreDTO bean) throws SQLException {
            bean.setIdEnteCreditore(rs.getLong("ente_creditore_id"));
            bean.setCfEnteCreditore(rs.getString("cf_ente_creditore"));
            bean.setDenominazioneEnteCreditore(rs.getString("denominazione_ente_creditore"));
            bean.setIndirizzoTesoreria(rs.getString("indirizzo_tesoreria"));
            bean.setIbanAccredito(rs.getString("iban_accredito"));
            bean.setBicAccredito(rs.getString("bic_accredito"));
            bean.setFlgAderiscePiemontepay(rs.getInt("flg_aderisce_piemontepay") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanComuneCompetenza(ResultSet rs, ComuneExtendedDTO bean) throws SQLException {
            String prefix = "_sdc";
            bean.setIdComune(rs.getLong("comune_id"));
            bean.setCodIstatComune(rs.getString("cod_istat_comune"));
            bean.setCodBelfioreComune(rs.getString("cod_belfiore_comune"));
            bean.setDenomComune(rs.getString("denom_comune"));

            ProvinciaExtendedDTO provincia = new ProvinciaExtendedDTO();
            populateBeanProvincia(rs, provincia);
            bean.setProvincia(provincia);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
            bean.setDtIdComune(rs.getLong("dt_id_comune"));
            bean.setDtIdComunePrev(rs.getLong("dt_id_comune_prev"));
            bean.setDtIdComuneNext(rs.getLong("dt_id_comune_next"));
            bean.setCapComune(rs.getString("cap_comune"));
        }

        private void populateBeanProvincia(ResultSet rs, ProvinciaExtendedDTO bean) throws SQLException {
            final String prefix = "_sdp";
            bean.setIdProvincia(rs.getLong("provincia_id"));
            bean.setCodProvincia(rs.getString("cod_provincia"));
            bean.setDenomProvincia(rs.getString("denom_provincia"));
            bean.setSiglaProvincia(rs.getString("sigla_provincia"));

            RegioneExtendedDTO regione = new RegioneExtendedDTO();
            populateBeanRegione(rs, regione);
            bean.setRegione(regione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
        }

        private void populateBeanRegione(ResultSet rs, RegioneExtendedDTO bean) throws SQLException {
            final String prefix = "_sdr";
            bean.setIdRegione(rs.getLong("regione_id"));
            bean.setCodRegione(rs.getString("cod_regione"));
            bean.setDenomRegione(rs.getString("denom_regione"));

            NazioneDTO nazione = new NazioneDTO();
            populateBeanNazione(rs, nazione);
            bean.setNazione(nazione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
        }

        private void populateBeanNazione(ResultSet rs, NazioneDTO bean) throws SQLException {
            final String prefix = "_sdn";
            bean.setIdNazione(rs.getLong("nazione_id"));
            bean.setCodIstatNazione(rs.getString("cod_istat_nazione"));
            bean.setCodBelfioreNazione(rs.getString("cod_belfiore_nazione"));
            bean.setDenomNazione(rs.getString("denom_nazione"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
            bean.setDtIdStato(rs.getLong("dt_id_stato"));
            bean.setDtIdStatoPrev(rs.getLong("dt_id_stato_prev"));
            bean.setDtIdStatoNext(rs.getLong("dt_id_stato_next"));
            bean.setIdOrigine(rs.getLong("id_origine"));
            bean.setCodIso2(rs.getString("cod_iso2"));
        }

    }

}