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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CompetenzaTerritorioDAO;

import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoResponsabileDTO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Competenza territorio dao.
 */
public class CompetenzaTerritorioDAOImpl extends ScrivaBeSrvGenericDAO<CompetenzaTerritorioDTO> implements CompetenzaTerritorioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_ADEMPIMENTO = "AND srac.id_adempimento = :idAdempimento \n";

    private static final String WHERE_COD_COMPONENTE_APP = "AND stct.ind_visibile LIKE '%'||:codComponenteApp||'%' \n";

    private static final String WHERE_FLG_ADESIONE_ADEMPIMENTO = "AND srac.ind_adesione_adempimento != 0\n";

    private static final String WHERE_FLG_PRINCIPALE = "AND srac.flg_principale = 1 \n";

    private static final String ORDER_BY_ORD_TIPO_COMPETENZA = "ORDER BY sdtc.ordinamento_tipo_competenza, stct.des_competenza_territorio ASC";
    
    private static final String ORDER_BY_ORDINAMENTO = "ORDER BY srac.ordinamento";

    private static final String QUERY_COMPETENZE_TERRITORIO = "SELECT * FROM _replaceTableName_ stct WHERE 1=1 \n";

    private static final String QUERY_COMPETENZA_TERRITORIO = "SELECT stct.*, stct.id_competenza_territorio AS competenza_territorio_id, \n" +
            "srac.*, \n" +
            "sdtc.*, sdtc.id_tipo_competenza AS tipo_competenza_id, \n" +
            "sdec.*, sdec.id_ente_creditore AS ente_creditore_id, \n" +
            "sdc.*, sdc.id_comune AS comune_id, sdc.data_inizio_validita AS data_inizio_validita_sdc, sdc.data_fine_validita AS data_fine_validita_sdc, \n" +
            "sdp.*, sdp.id_provincia AS provincia_id, sdp.data_inizio_validita AS data_inizio_validita_sdp, sdp.data_fine_validita AS data_fine_validita_sdp, \n" +
            "sdr.*, sdr.id_regione AS regione_id, sdr.data_inizio_validita AS data_inizio_validita_sdr, sdr.data_fine_validita AS data_fine_validita_sdr, \n" +
            "sdn.*, sdn.id_nazione AS nazione_id, sdn.data_inizio_validita AS data_inizio_validita_sdn, sdn.data_fine_validita AS data_fine_validita_sdn \n" +
            "FROM _replaceTableName_ stct \n" +
            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = stct.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \n" +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore \n" +
            "LEFT JOIN scriva_d_comune sdc ON sdc.id_comune = stct.id_comune_competenza \n" +
            "LEFT JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia \n" +
            "LEFT JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione \n" +
            "LEFT JOIN scriva_d_nazione sdn ON sdn.id_nazione = sdr.id_nazione \n" +
            "WHERE stct.data_fine_validita IS NULL \n";

    private static final String QUERY_COMPETENZA_TERRITORIO_NO_PARAM = " SELECT stct.*, stct.id_competenza_territorio AS competenza_territorio_id, \r\n" +
            " sdtc.*, sdtc.id_tipo_competenza AS tipo_competenza_id, \r\n" +
            " sdec.*, sdec.id_ente_creditore AS ente_creditore_id, sdc.*, sdc.id_comune AS comune_id, sdc.data_inizio_validita AS data_inizio_validita_sdc, sdc.data_fine_validita AS data_fine_validita_sdc, \r\n" +
            " sdp.*, sdp.id_provincia AS provincia_id, sdp.data_inizio_validita AS data_inizio_validita_sdp, sdp.data_fine_validita AS data_fine_validita_sdp, \r\n" +
            " sdr.*, sdr.id_regione AS regione_id, sdr.data_inizio_validita AS data_inizio_validita_sdr, sdr.data_fine_validita AS data_fine_validita_sdr, \r\n" +
            " sdn.*, sdn.id_nazione AS nazione_id, sdn.data_inizio_validita AS data_inizio_validita_sdn, sdn.data_fine_validita AS data_fine_validita_sdn \r\n" +
            " FROM scriva_t_competenza_territorio stct   \r\n" +
            " INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \r\n" +
            " INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore \r\n" +
            " LEFT JOIN scriva_d_comune sdc ON sdc.id_comune = stct.id_comune_competenza \r\n" +
            " LEFT JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia \r\n" +
            " LEFT JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione \r\n" +
            " LEFT JOIN scriva_d_nazione sdn ON sdn.id_nazione = sdr.id_nazione \r\n" +
            " WHERE stct.data_fine_validita IS NULL \r\n" +
            " and ind_visibile like  '%'||:codComponenteApp||'%'   ";

    private static final String QUERY_COMPETENZA_TERRITORIO_BY_ID = QUERY_COMPETENZA_TERRITORIO + "AND stct.id_competenza_territorio = :idCompetenzaTerritorio \n";

    private static final String QUERY_COMPETENZA_TERRITORIO_BY_ID_ADEMPIMENTO = QUERY_COMPETENZA_TERRITORIO + WHERE_ID_ADEMPIMENTO;

    private static final String QUERY_COMPETENZA_TERRITORIO_BY_ID_ADEMPIMENTO_COMPONENTE = QUERY_COMPETENZA_TERRITORIO_BY_ID_ADEMPIMENTO + WHERE_COD_COMPONENTE_APP;

    private static final String QUERY_COMPETENZA_TERRITORIO_BY_COD_ENTE_GESTORE = "SELECT stct.*\n" +
            "FROM scriva_t_competenza_territorio stct\n" +
            "INNER JOIN scriva_d_mappa_fonte_esterna sdmfe ON sdmfe.cod_scriva = stct.cod_competenza_territorio\n" +
            "WHERE stct.data_fine_validita IS NULL\n" +
            "AND sdmfe.cod_masterdata ='PARCHI'\n" +
            "AND sdmfe.info_fonte ='CodiceGestore'\n" +
            "AND sdmfe.cod_fonte = :codEnteGestore\n";
    
    private static final String QUERY_COMPETENZA_TERRITORIO_BY_ID_COMP_RESP = "SELECT srcr.*, sdtr.* FROM scriva_r_competenza_responsabile srcr\n" +
    		"INNER JOIN scriva_d_tipo_responsabile sdtr ON srcr.id_tipo_responsabile = sdtr.id_tipo_responsabile\n" + 
    	    "WHERE srcr.id_competenza_responsabile = :idCompetenzaTerritorioResponsabile";
    
    private static final String QUERY_COMPETENZA_TERRITORIO_BY_ID_COMP_TERR = "select distinct srcr.*, sdtr.* from scriva_r_competenza_responsabile srcr\r\n" + 
    		"left join scriva_d_tipo_responsabile sdtr ON srcr.id_tipo_responsabile = sdtr.id_tipo_responsabile\n" + 
    		"left join scriva_r_istanza_responsabile srir on  srir.id_tipo_responsabile = srcr.id_tipo_responsabile\r\n" + 
    		"where srcr.id_competenza_territorio = :idCompetenzaTerritorio";
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(srac.data_inizio_validita) <= DATE(NOW()) AND (srac.data_fine_validita IS NULL OR DATE(srac.data_fine_validita)>= DATE(NOW())))\n ";

    @Override
    public List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_COMPETENZA_TERRITORIO_NO_PARAM;
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
        }
        return findListByQuery(className, query + ORDER_BY_ORD_TIPO_COMPETENZA, map);
    }

    /**
     * Load competenze territorio list.
     *
     * @param idAdempimento    the id adempimento
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<CompetenzaTerritorioExtendedDTO> loadCompetenzeTerritorio(Long idAdempimento, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_COMPETENZA_TERRITORIO;
        if (idAdempimento != null) {
            map.put("idAdempimento", idAdempimento);
            query += WHERE_ID_ADEMPIMENTO;
        }
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            //query += WHERE_FLG_ADESIONE_ADEMPIMENTO + WHERE_COD_COMPONENTE_APP;
            query += WHERE_COD_COMPONENTE_APP;
        }
        return findListByQuery(className, query + WHERE_DATA_INIZIO_FINE + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @return List<CompetenzaTerritorioExtendedDTO>
     */
    @Override
    public List<CompetenzaTerritorioExtendedDTO> loadCompetenzaTerritorioById(Long idCompetenzaTerritorio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
        return findListByQuery(className, QUERY_COMPETENZA_TERRITORIO_BY_ID, map);
    }

    /**
     * Load competenza territorio by codice gestore parchi list.
     *
     * @param codEnteGestore the cod ente gestore
     * @return the list
     */
    @Override
    public List<CompetenzaTerritorioDTO> loadCompetenzaTerritorioByCodiceGestoreParchi(String codEnteGestore) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codEnteGestore", codEnteGestore);
        return findSimpleDTOListByQuery(className, QUERY_COMPETENZA_TERRITORIO_BY_COD_ENTE_GESTORE, map);
    }

    /**
     * @param idCompetenzaTerritorioResponsabile idCompetenzaTerritorioResponsabile
     * @return CompetenzaTerritorioResponsabileDTO
     */
    @Override
    public CompetenzaTerritorioResponsabileDTO loadCompetenzeTerritorioByIdCompResp(Long idCompetenzaTerritorioResponsabile) throws Exception {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompetenzaTerritorioResponsabile", idCompetenzaTerritorioResponsabile);
        MapSqlParameterSource params = getParameterValue(map);
        CompetenzaTerritorioResponsabileDTO compTerResp = template.queryForObject(QUERY_COMPETENZA_TERRITORIO_BY_ID_COMP_RESP,params, getCompTerRespRowMapper());
        return compTerResp; //findSimpleDTOListByQuery(className, QUERY_COMPETENZA_TERRITORIO_BY_ID_COMP_RESP, map);
    }
    
    
    /**
     * @param idCompetenzaTerritorio idCompetenzaTerritorioResponsabile
     * @return CompetenzaTerritorioResponsabileDTO
     */
    @Override
    public List<CompetenzaTerritorioResponsabileDTO> loadCompetenzeTerritorioByIdCompTerr(Long idCompetenzaTerritorio) throws Exception {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
        MapSqlParameterSource params = getParameterValue(map);
        List<CompetenzaTerritorioResponsabileDTO> compTerResp = template.query(QUERY_COMPETENZA_TERRITORIO_BY_ID_COMP_TERR, params, getCompTerRespRowMapper()); //template.queryForObject(QUERY_COMPETENZA_TERRITORIO_BY_ID_COMP_TERR,params, getCompTerRespRowMapper());
        return compTerResp; 
    }
    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_COMPETENZA_TERRITORIO_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<CompetenzaTerritorioDTO>
     */
    @Override
    public RowMapper<CompetenzaTerritorioDTO> getRowMapper() throws SQLException {
        return new CompetenzaTerritorioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<CompetenzaTerritorioDTO>
     */
    @Override
    public RowMapper<CompetenzaTerritorioExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new CompetenzaTerritorioExtendedRowMapper();
    }

    /**
     * The type Competenza territorio row mapper.
     */
    public static class CompetenzaTerritorioRowMapper implements RowMapper<CompetenzaTerritorioDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompetenzaTerritorioRowMapper() throws SQLException {
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
        public CompetenzaTerritorioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompetenzaTerritorioDTO bean = new CompetenzaTerritorioDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CompetenzaTerritorioDTO bean) throws SQLException {
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setIdTipoCompetenza(rs.getLong("id_tipo_competenza"));
            bean.setIdEnteCreditore(rs.getLong("id_ente_creditore"));
            bean.setIdComuneCompetenza(rs.getLong("id_comune_competenza"));
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
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setCodIpa(rs.getString("cod_ipa"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setNumDimensionePec(rs.getString("num_dimensione_pec"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Competenza territorio extended row mapper.
     */
    public static class CompetenzaTerritorioExtendedRowMapper implements RowMapper<CompetenzaTerritorioExtendedDTO> {

        /**
         * Instantiates a new Competenza territorio extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompetenzaTerritorioExtendedRowMapper() throws SQLException {
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
        public CompetenzaTerritorioExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CompetenzaTerritorioExtendedDTO bean = new CompetenzaTerritorioExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        public void populateBean(ResultSet rs, CompetenzaTerritorioExtendedDTO bean) throws SQLException {
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
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setCodIpa(rs.getString("cod_ipa"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setNumDimensionePec(rs.getString("num_dimensione_pec"));
            bean.setGestUID(rs.getString("gest_uid"));
            try {
                bean.setIndAdesioneAdempimento(rs.getInt("ind_adesione_adempimento"));
            } catch (SQLException e) {
                bean.setIndAdesioneAdempimento(null);
            }
            try {
                bean.setFlgPrincipale(rs.getInt("flg_principale") == 1 ? Boolean.TRUE : Boolean.FALSE);
            } catch (SQLException e) {
                bean.setFlgPrincipale(null);
            }
            try {
                bean.setUrlOneriPrevisti(rs.getString("url_oneri_previsti"));
            } catch (SQLException e) {
                bean.setUrlOneriPrevisti(null);
            }
            try {
                bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo") > 0 ? rs.getLong("id_componente_gestore_processo") : null);
            } catch (SQLException e) {
                bean.setIdComponenteGestoreProcesso(null);
            }
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
    
    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<CompetenzaTerritorioResponsabileDTO>
     */

    public RowMapper<CompetenzaTerritorioResponsabileDTO> getCompTerRespRowMapper() throws SQLException {
        return new CompetenzaTerritorioResponsabileRowMapper();
    }


    /**
     * The type Competenza territorio responsabile row mapper.
     */
    public static class CompetenzaTerritorioResponsabileRowMapper implements RowMapper<CompetenzaTerritorioResponsabileDTO> {

        /**
         * Instantiates a new Competenza territorio responsabile row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CompetenzaTerritorioResponsabileRowMapper() throws SQLException {
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
        public CompetenzaTerritorioResponsabileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	CompetenzaTerritorioResponsabileDTO bean = new CompetenzaTerritorioResponsabileDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CompetenzaTerritorioResponsabileDTO bean) throws SQLException {
        	bean.setIdCompetenzaResponsabile(rs.getLong("id_competenza_responsabile"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            
            TipoResponsabileDTO tipoResponsabile = new TipoResponsabileDTO();
            populateBeanTipoResponsabile(rs, tipoResponsabile);
            bean.setTipoResponsabile(tipoResponsabile);

            bean.setLabelResponsabile(rs.getString("label_responsabile"));
            bean.setNominativoResponsabile(rs.getString("nominativo_responsabile"));
            bean.setRecapitoResponsabile(rs.getString("recapito_responsabile"));
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUID(rs.getString("gest_uid"));
        }
        
        private void populateBeanTipoResponsabile(ResultSet rs, TipoResponsabileDTO bean) throws SQLException {

            bean.setIdTipoResponsabile(rs.getLong("id_tipo_responsabile"));
            bean.setCodiceTipoResponsabile(rs.getString("cod_tipo_responsabile"));
            bean.setDescrizioneTipoResponsabile(rs.getString("des_tipo_responsabile"));

        }
    }


}