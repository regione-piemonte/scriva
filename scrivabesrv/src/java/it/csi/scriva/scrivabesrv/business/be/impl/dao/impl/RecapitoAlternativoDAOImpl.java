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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.RecapitoAlternativoDAO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RecapitoAlternativoDTO;
import it.csi.scriva.scrivabesrv.dto.RecapitoAlternativoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Recapito alternativo dao.
 *
 * @author CSI PIEMONTE
 */
public class RecapitoAlternativoDAOImpl extends ScrivaBeSrvGenericDAO<RecapitoAlternativoDTO> implements RecapitoAlternativoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_recapito_alternativo = :idRecapitoAlternativo";

    private static final String QUERY_RECAPITI_ALTERNATIVI = "SELECT srra.*\n" +
            ", sdc_res.id_comune AS id_comune_sdc_res, sdc_res.cod_istat_comune AS cod_istat_comune_sdc_res, sdc_res.cod_belfiore_comune AS cod_belfiore_comune_sdc_res, sdc_res.denom_comune AS denom_comune_sdc_res, sdc_res.id_provincia AS id_provincia_sdc_res, sdc_res.data_inizio_validita AS data_inizio_validita_sdc_res, sdc_res.data_fine_validita AS data_fine_validita_sdc_res, sdc_res.dt_id_comune AS dt_id_comune_sdc_res, sdc_res.dt_id_comune_prev AS dt_id_comune_prev_sdc_res, sdc_res.dt_id_comune_next AS dt_id_comune_next_sdc_res, sdc_res.cap_comune AS cap_comune_sdc_res\n" +
            ", sdp_res.id_provincia AS id_provincia_sdp_res, sdp_res.cod_provincia AS cod_provincia_sdp_res, sdp_res.denom_provincia AS denom_provincia_sdp_res, sdp_res.sigla_provincia AS sigla_provincia_sdp_res, sdp_res.id_regione AS id_regione_sdp_res, sdp_res.data_inizio_validita AS data_inizio_validita_sdp_res, sdp_res.data_fine_validita AS data_fine_validita_sdp_res\n" +
            ", sdr_res.id_regione AS id_regione_sdr_res, sdr_res.cod_regione AS cod_regione_sdr_res, sdr_res.denom_regione AS denom_regione_sdr_res, sdr_res.id_nazione AS id_nazione_sdr_res, sdr_res.data_inizio_validita AS data_inizio_validita_sdr_res, sdr_res.data_fine_validita AS data_fine_validita_sdr_res\n" +
            ", sdn_res.id_nazione AS id_nazione_sdn_res, sdn_res.cod_istat_nazione AS cod_istat_nazione_sdn_res, sdn_res.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_res, sdn_res.denom_nazione AS denom_nazione_sdn_res, sdn_res.data_inizio_validita AS data_inizio_validita_sdn_res, sdn_res.data_fine_validita AS data_fine_validita_sdn_res, sdn_res.dt_id_stato AS dt_id_stato_sdn_res, sdn_res.dt_id_stato_prev AS dt_id_stato_prev_sdn_res, sdn_res.dt_id_stato_next AS dt_id_stato_next_sdn_res, sdn_res.id_origine AS id_origine_sdn_res\n" +
            ", sdc_sl.id_comune AS id_comune_sdc_sl, sdc_sl.cod_istat_comune AS cod_istat_comune_sdc_sl, sdc_sl.cod_belfiore_comune AS cod_belfiore_comune_sdc_sl, sdc_sl.denom_comune AS denom_comune_sdc_sl, sdc_sl.id_provincia AS id_provincia_sdc_sl, sdc_sl.data_inizio_validita AS data_inizio_validita_sdc_sl, sdc_sl.data_fine_validita AS data_fine_validita_sdc_sl, sdc_sl.dt_id_comune AS dt_id_comune_sdc_sl, sdc_sl.dt_id_comune_prev AS dt_id_comune_prev_sdc_sl, sdc_sl.dt_id_comune_next AS dt_id_comune_next_sdc_sl, sdc_sl.cap_comune AS cap_comune_sdc_sl\n" +
            ", sdp_sl.id_provincia AS id_provincia_sdp_sl, sdp_sl.cod_provincia AS cod_provincia_sdp_sl, sdp_sl.denom_provincia AS denom_provincia_sdp_sl, sdp_sl.sigla_provincia AS sigla_provincia_sdp_sl, sdp_sl.id_regione AS id_regione_sdp_sl, sdp_sl.data_inizio_validita AS data_inizio_validita_sdp_sl, sdp_sl.data_fine_validita AS data_fine_validita_sdp_sl\n" +
            ", sdr_sl.id_regione AS id_regione_sdr_sl, sdr_sl.cod_regione AS cod_regione_sdr_sl, sdr_sl.denom_regione AS denom_regione_sdr_sl, sdr_sl.id_nazione AS id_nazione_sdr_sl, sdr_sl.data_inizio_validita AS data_inizio_validita_sdr_sl, sdr_sl.data_fine_validita AS data_fine_validita_sdr_sl\n" +
            ", sdn_sl.id_nazione AS id_nazione_sdn_sl, sdn_sl.cod_istat_nazione AS cod_istat_nazione_sdn_sl, sdn_sl.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_sl, sdn_sl.denom_nazione AS denom_nazione_sdn_sl, sdn_sl.data_inizio_validita AS data_inizio_validita_sdn_sl, sdn_sl.data_fine_validita AS data_fine_validita_sdn_sl, sdn_sl.dt_id_stato AS dt_id_stato_sdn_sl, sdn_sl.dt_id_stato_prev AS dt_id_stato_prev_sdn_sl, sdn_sl.dt_id_stato_next AS dt_id_stato_next_sdn_sl, sdn_sl.id_origine AS id_origine_sdn_sl\n" +
            ", sdnr.id_nazione AS id_nazione_sdnr, sdnr.cod_istat_nazione AS cod_istat_nazione_sdnr, sdnr.cod_belfiore_nazione AS cod_belfiore_nazione_sdnr, sdnr.denom_nazione AS denom_nazione_sdnr, sdnr.data_inizio_validita AS data_inizio_validita_sdnr, sdnr.data_fine_validita AS data_fine_validita_sdnr, sdnr.dt_id_stato AS dt_id_stato_sdnr, sdnr.dt_id_stato_prev AS dt_id_stato_prev_sdnr, sdnr.dt_id_stato_next AS dt_id_stato_next_sdnr, sdnr.id_origine AS id_origine_sdnr\n" +
            ", sdns.id_nazione AS id_nazione_sdns, sdns.cod_istat_nazione AS cod_istat_nazione_sdns, sdns.cod_belfiore_nazione AS cod_belfiore_nazione_sdns, sdns.denom_nazione AS denom_nazione_sdns, sdns.data_inizio_validita AS data_inizio_validita_sdns, sdns.data_fine_validita AS data_fine_validita_sdns, sdns.dt_id_stato AS dt_id_stato_sdns, sdns.dt_id_stato_prev AS dt_id_stato_prev_sdns, sdns.dt_id_stato_next AS dt_id_stato_next_sdns, sdns.id_origine AS id_origine_sdns\n" +
            "FROM _replaceTableName_ srra\n" +
            "LEFT JOIN scriva_d_comune sdc_res ON sdc_res.id_comune = srra.id_comune_residenza\n" +
            "LEFT JOIN scriva_d_provincia sdp_res ON sdp_res.id_provincia = sdc_res.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_res ON sdr_res.id_regione = sdp_res.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_res ON sdn_res.id_nazione = sdr_res.id_nazione\n" +
            "LEFT JOIN scriva_d_comune sdc_sl ON sdc_sl.id_comune = srra.id_comune_sede_legale\n" +
            "LEFT JOIN scriva_d_provincia sdp_sl ON sdp_sl.id_provincia = sdc_sl.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_sl ON sdr_sl.id_regione = sdp_sl.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_sl ON sdn_sl.id_nazione = sdr_sl.id_nazione\n" +
            "LEFT JOIN scriva_t_soggetto_istanza stsi on srra.id_soggetto_istanza = stsi.id_soggetto_istanza\n" +
            "LEFT JOIN scriva_d_nazione sdnr ON sdnr.id_nazione = srra.id_nazione_residenza\n" +
            "LEFT JOIN scriva_d_nazione sdns ON sdns.id_nazione = srra.id_nazione_sede_legale\n" +
            "WHERE 1=1\n";

    private static final String QUERY_RECAPITO_ALTERNATIVO_BY_ID = QUERY_RECAPITI_ALTERNATIVI + "AND srra.id_recapito_alternativo = :idRecapitoAlternativo\n";

    private static final String QUERY_RECAPITO_ALTERNATIVO_BY_ID_SOGGETTO_ISTANZA = QUERY_RECAPITI_ALTERNATIVI + "AND srra.id_soggetto_istanza = :idSoggettoIstanza\n";

    private static final String QUERY_RECAPITO_ALTERNATIVO_BY_COD = QUERY_RECAPITI_ALTERNATIVI + "AND srra.cod_recapito_alternativo = :codRecapitoAlternativo\n";

    private static final String QUERY_INSERT_RECAPITO_ALTERNATIVO = "INSERT INTO _replaceTableName_ (id_recapito_alternativo, cod_recapito_alternativo,\n" +
            "id_comune_residenza, id_comune_sede_legale, id_istanza_attore, indirizzo_soggetto, num_civico_indirizzo, citta_estera_residenza, id_nazione_residenza,\n" +
            "presso, num_telefono, num_cellulare, des_localita, des_email, des_pec,\n" +
            "citta_estera_sede_legale, id_nazione_sede_legale, cap_residenza, cap_sede_legale, id_funzionario,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid, id_soggetto_istanza) \n" +
            "VALUES (nextval('seq_scriva_r_recapito_alternativo'), nextval('seq_scriva_cod_allegato_alternativo'),\n" +
            ":idComuneResidenza,:idComuneSedeLegale,:idIstanzaAttore,:indirizzoSoggetto,:numCivicoIndirizzo,:cittaEsteraResidenza,:idNazioneResidenza,\n" +
            ":presso,:numTelefono,:numCellulare,:desLocalita,:desEmail,:desPec,\n" +
            ":cittaEsteraSedeLegale, :idNazioneSedeLegale, :capResidenza, :capSedeLegale, :idFunzionario,\n" +
            ":gestDataIns,:gestAttoreIns,:gestDataUpd,:gestAttoreUpd,:gestUid,:idSoggettoIstanza)\n";

    private static final String QUERY_INSERT_STORICO_RECAPITO_ALTERNATIVO = "INSERT INTO _replaceTableName_\n" +
            "(id_recapito_alternativo_storico, id_recapito_alternativo, cod_recapito_alternativo, \n" +
            "id_comune_residenza, id_comune_sede_legale, id_istanza_attore, indirizzo_soggetto, \n" +
            "num_civico_indirizzo, citta_estera_residenza, id_nazione_residenza, presso, num_telefono, \n" +
            "num_cellulare, des_localita, des_email, des_pec, gest_data_ins, gest_attore_ins, gest_data_upd, \n" +
            "gest_attore_upd, gest_uid, id_soggetto_istanza, citta_estera_sede_legale, id_nazione_sede_legale, \n" +
            "cap_residenza, cap_sede_legale, id_funzionario, data_aggiornamento)\n" +
            "SELECT nextval('seq_scriva_s_recapito_alternativo'), id_recapito_alternativo, cod_recapito_alternativo, \n" +
            "id_comune_residenza, id_comune_sede_legale, id_istanza_attore, indirizzo_soggetto, \n" +
            "num_civico_indirizzo, citta_estera_residenza, id_nazione_residenza, presso, num_telefono, \n" +
            "num_cellulare, des_localita, des_email, des_pec, gest_data_ins, gest_attore_ins, gest_data_upd, \n" +
            "gest_attore_upd, gest_uid, id_soggetto_istanza, citta_estera_sede_legale, id_nazione_sede_legale, \n" +
            "cap_residenza, cap_sede_legale, id_funzionario, data_aggiornamento\n" +
            "FROM scriva_r_recapito_alternativo\n" +
            "WHERE gest_uid = :gestUid ";

    private static final String QUERY_UPDATE_RECAPITO_ALTERNATIVO = "UPDATE _replaceTableName_\n" +
            "SET id_comune_residenza=:idComuneResidenza, id_comune_sede_legale=:idComuneSedeLegale, id_istanza_attore=:idIstanzaAttore, indirizzo_soggetto=:indirizzoSoggetto,\n" +
            "num_civico_indirizzo=:numCivicoIndirizzo, citta_estera_residenza=:cittaEsteraResidenza, id_nazione_residenza=:idNazioneResidenza,\n" +
            "presso=:presso, num_telefono=:numTelefono, num_cellulare=:numCellulare, des_localita=:desLocalita, des_email=:desEmail, des_pec=:desPec,\n" +
            "citta_estera_sede_legale = :cittaEsteraSedeLegale, id_nazione_sede_legale = :idNazioneSedeLegale, cap_residenza = :capResidenza, cap_sede_legale = :capSedeLegale, id_funzionario = :idFunzionario,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE id_recapito_alternativo=:idRecapitoAlternativo";

    private static final String QUERY_DELETE_RECAPITO_ALTERNATIVO_BY_UID = "DELETE FROM _replaceTableName_ WHERE gest_uid = :gestUid";

    private static final String QUERY_DELETE_RECAPITO_ALTERNATIVO_BY_ID_SOGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE id_soggetto_istanza = :idSoggettoIstanza";

    /**
     * Load recapiti alternativi list.
     *
     * @return the list
     */
    @Override
    public List<RecapitoAlternativoExtendedDTO> loadRecapitiAlternativi() {
        logBegin(className);
        return findListByQuery(className, QUERY_RECAPITI_ALTERNATIVI, null);
    }

    /**
     * Load recapito alternativo by id list.
     *
     * @param idRecapitoAlternativo the id recapito alternativo
     * @return the list
     */
    @Override
    public List<RecapitoAlternativoExtendedDTO> loadRecapitoAlternativoById(Long idRecapitoAlternativo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRecapitoAlternativo", idRecapitoAlternativo);
        return findListByQuery(className, QUERY_RECAPITO_ALTERNATIVO_BY_ID, map);
    }

    /**
     * Load recapito alternativo by code list.
     *
     * @param codRecapitoAlternativo the cod recapito alternativo
     * @return the list
     */
    @Override
    public List<RecapitoAlternativoExtendedDTO> loadRecapitoAlternativoByCode(String codRecapitoAlternativo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codRecapitoAlternativo", codRecapitoAlternativo);
        return findListByQuery(className, QUERY_RECAPITO_ALTERNATIVO_BY_COD, map);
    }

    /**
     * Load recapito alternativo by id soggetto istanza list.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the list
     */
    @Override
    public List<RecapitoAlternativoExtendedDTO> loadRecapitoAlternativoByIdSoggettoIstanza(Long idSoggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggettoIstanza", idSoggettoIstanza);
        return findListByQuery(className, QUERY_RECAPITO_ALTERNATIVO_BY_ID_SOGGETTO_ISTANZA, map);
    }

    /**
     * Save recapito alternativo long.
     *
     * @param dto the recapito alternativo
     * @return the long
     */
    @Override
    public Long saveRecapitoAlternativo(RecapitoAlternativoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idSoggettoIstanza", dto.getIdSoggettoIstanza());
            map.put("idComuneResidenza", dto.getIdComuneResidenza());
            map.put("idComuneSedeLegale", dto.getIdComuneSedeLegale());
            map.put("indirizzoSoggetto", dto.getIndirizzoSoggetto());
            map.put("numCivicoIndirizzo", dto.getNumCivicoIndirizzo());
            map.put("cittaEsteraResidenza", dto.getCittaEsteraResidenza());
            map.put("idNazioneResidenza", dto.getIdNazioneResidenza());
            map.put("presso", dto.getPresso());
            map.put("numTelefono", dto.getNumTelefono());
            map.put("numCellulare", dto.getNumCellulare());
            map.put("desLocalita", dto.getDesLocalita());
            map.put("desEmail", dto.getDesEmail());
            map.put("desPec", dto.getDesPec());
            map.put("cittaEsteraSedeLegale", dto.getCittaEsteraSedeLegale());
            map.put("idNazioneSedeLegale", dto.getIdNazioneSedeLegale());
            map.put("capResidenza", dto.getCapResidenza());
            map.put("capSedeLegale", dto.getCapSedeLegale());
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
            map.put("idFunzionario", dto.getIdFunzionario());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdSoggettoIstanza().toString() + dto.getIdComuneResidenza() + dto.getIndirizzoSoggetto() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_RECAPITO_ALTERNATIVO, null, null), params, keyHolder, new String[]{"id_recapito_alternativo"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_RECAPITO_ALTERNATIVO), params);
            }
            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update recapito alternativo integer.
     *
     * @param dto the recapito alternativo
     * @return the integer
     */
    @Override
    public Integer updateRecapitoAlternativo(RecapitoAlternativoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idRecapitoAlternativo", dto.getIdRecapitoAlternativo());

            RecapitoAlternativoDTO recapitoAlternativoOld = this.findByPK(className, map);
            if (null == recapitoAlternativoOld) {
                logError(className, "Record non trovato con id [" + dto.getIdRecapitoAlternativo() + "]");
                return -1;
            }
            int returnValue = 1;
            if (!dto.equals(recapitoAlternativoOld)) {
                Long idIstanzaAttoreOld = recapitoAlternativoOld.getIdIstanzaAttore()!=null && recapitoAlternativoOld.getIdIstanzaAttore() > 0 ? recapitoAlternativoOld.getIdIstanzaAttore() : null;
                Long idFunzionarioOld = recapitoAlternativoOld.getIdFunzionario() != null && recapitoAlternativoOld.getIdFunzionario() > 0 ? recapitoAlternativoOld.getIdFunzionario() : null;
                map.put("idSoggettoIstanza", recapitoAlternativoOld.getIdSoggettoIstanza());
                map.put("idComuneResidenza", dto.getIdComuneResidenza());
                map.put("idComuneSedeLegale", dto.getIdComuneSedeLegale());
                map.put("indirizzoSoggetto", dto.getIndirizzoSoggetto());
                map.put("numCivicoIndirizzo", dto.getNumCivicoIndirizzo());
                map.put("cittaEsteraResidenza", dto.getCittaEsteraResidenza());
                map.put("idNazioneResidenza", dto.getIdNazioneResidenza());
                map.put("presso", dto.getPresso());
                map.put("numTelefono", dto.getNumTelefono());
                map.put("numCellulare", dto.getNumCellulare());
                map.put("desLocalita", dto.getDesLocalita());
                map.put("desEmail", dto.getDesEmail());
                map.put("desPec", dto.getDesPec());
                map.put("cittaEsteraSedeLegale", dto.getCittaEsteraSedeLegale());
                map.put("idNazioneSedeLegale", dto.getIdNazioneSedeLegale());
                map.put("capResidenza", dto.getCapResidenza());
                map.put("capSedeLegale", dto.getCapSedeLegale());
                map.put("idIstanzaAttore", dto.getIdIstanzaAttore() != null ? dto.getIdIstanzaAttore() : idIstanzaAttoreOld);
                map.put("idFunzionario", dto.getIdFunzionario() != null ? dto.getIdFunzionario() : idFunzionarioOld);
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                MapSqlParameterSource params = getParameterValue(map);
                returnValue = template.update(getQuery(QUERY_UPDATE_RECAPITO_ALTERNATIVO, null, null), params);
                if (returnValue > 0) {
                    Map<String, Object> storicoMap = new HashMap<>();
                    storicoMap.put("gestUid", dto.getGestUID());
                    params = getParameterValue(storicoMap);
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_RECAPITO_ALTERNATIVO), params);
                }
            }
            return returnValue;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete recapito alternativo integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteRecapitoAlternativo(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_RECAPITO_ALTERNATIVO_BY_UID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete recapito alternativo by id soggetto istanza integer.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @return the integer
     */
    @Override
    public Integer deleteRecapitoAlternativoByIdSoggettoIstanza(Long idSoggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idSoggettoIstanza", idSoggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_RECAPITO_ALTERNATIVO_BY_ID_SOGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<RecapitoAlternativoDTO> getRowMapper() throws SQLException {
        return new RecapitoAlternativoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<RecapitoAlternativoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new RecapitoAlternativoExtendedRowMapper();
    }

    /**
     * The type Recapito alternativo row mapper.
     */
    public static class RecapitoAlternativoRowMapper implements RowMapper<RecapitoAlternativoDTO> {

        /**
         * Instantiates a new Recapito alternativo istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RecapitoAlternativoRowMapper() throws SQLException {
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
        public RecapitoAlternativoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecapitoAlternativoDTO bean = new RecapitoAlternativoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RecapitoAlternativoDTO bean) throws SQLException {
            bean.setIdRecapitoAlternativo(rs.getLong("id_recapito_alternativo"));
            bean.setCodRecapitoAlternativo(rs.getString("cod_recapito_alternativo"));
            bean.setIdSoggettoIstanza(rs.getLong("id_soggetto_istanza"));
            bean.setIdComuneResidenza(rs.getLong("id_comune_residenza"));
            bean.setIdComuneSedeLegale(rs.getLong("id_comune_sede_legale"));
            bean.setIndirizzoSoggetto(rs.getString("indirizzo_soggetto"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setCittaEsteraResidenza(rs.getString("citta_estera_residenza"));
            bean.setIdNazioneResidenza(rs.getLong("id_nazione_residenza"));
            bean.setPresso(rs.getString("presso"));
            bean.setNumTelefono(rs.getString("num_telefono"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesLocalita(rs.getString("des_localita"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setDesPec(rs.getString("des_pec"));
            bean.setCittaEsteraSedeLegale(rs.getString("citta_estera_sede_legale"));
            bean.setIdNazioneSedeLegale(rs.getLong("id_nazione_sede_legale") > 0 ? rs.getLong("id_nazione_sede_legale") : null);
            bean.setCapResidenza(rs.getString("cap_residenza"));
            bean.setCapSedeLegale(rs.getString("cap_sede_legale"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Recapito alternativo extended row mapper.
     */
    public static class RecapitoAlternativoExtendedRowMapper implements RowMapper<RecapitoAlternativoExtendedDTO> {

        /**
         * Instantiates a new Recapito alternativo istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RecapitoAlternativoExtendedRowMapper() throws SQLException {
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
        public RecapitoAlternativoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RecapitoAlternativoExtendedDTO bean = new RecapitoAlternativoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RecapitoAlternativoExtendedDTO bean) throws SQLException {
            bean.setIdRecapitoAlternativo(rs.getLong("id_recapito_alternativo"));
            bean.setCodRecapitoAlternativo(rs.getString("cod_recapito_alternativo"));
            bean.setIdSoggettoIstanza(rs.getLong("id_soggetto_istanza"));

            ComuneExtendedDTO comuneResidenza = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneResidenza, "_res");
            bean.setComuneResidenza(comuneResidenza.getIdComune() != 0 ? comuneResidenza : null);

            ComuneExtendedDTO comuneSedeLegale = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneSedeLegale, "_sl");
            bean.setComuneSedeLegale(comuneSedeLegale.getIdComune() != 0 ? comuneSedeLegale : null);

            bean.setIndirizzoSoggetto(rs.getString("indirizzo_soggetto"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setCittaEsteraResidenza(rs.getString("citta_estera_residenza"));

            NazioneDTO nazioneResidenza = new NazioneDTO();
            populateBeanNazione(rs, nazioneResidenza, "r");
            bean.setNazioneResidenza(nazioneResidenza.getIdNazione() != 0 ? nazioneResidenza : null);

            bean.setPresso(rs.getString("presso"));
            bean.setNumTelefono(rs.getString("num_telefono"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesLocalita(rs.getString("des_localita"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setDesPec(rs.getString("des_pec"));
            bean.setCittaEsteraSedeLegale(rs.getString("citta_estera_sede_legale"));

            NazioneDTO nazioneSedeLegale = new NazioneDTO();
            populateBeanNazione(rs, nazioneSedeLegale, "s");
            bean.setNazioneSedeLegale(nazioneSedeLegale.getIdNazione() > 0 ? nazioneSedeLegale : null);

            bean.setCapResidenza(rs.getString("cap_residenza"));
            bean.setCapSedeLegale(rs.getString("cap_sede_legale"));

            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanComune(ResultSet rs, ComuneExtendedDTO bean, String aliasTable) throws SQLException {
            final String prefix = "_sdc";
            bean.setIdComune(rs.getLong("id_comune" + prefix + aliasTable));
            bean.setCodIstatComune(rs.getString("cod_istat_comune" + prefix + aliasTable));
            bean.setCodBelfioreComune(rs.getString("cod_belfiore_comune" + prefix + aliasTable));
            bean.setDenomComune(rs.getString("denom_comune" + prefix + aliasTable));

            ProvinciaExtendedDTO provincia = new ProvinciaExtendedDTO();
            populateBeanProvincia(rs, provincia, aliasTable);
            bean.setProvincia(provincia);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix + aliasTable));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix + aliasTable));
            bean.setDtIdComune(rs.getLong("dt_id_comune" + prefix + aliasTable));
            bean.setDtIdComunePrev(rs.getLong("dt_id_comune_prev" + prefix + aliasTable));
            bean.setDtIdComuneNext(rs.getLong("dt_id_comune_next" + prefix + aliasTable));
            bean.setCapComune(rs.getString("cap_comune" + prefix + aliasTable));
        }

        private void populateBeanProvincia(ResultSet rs, ProvinciaExtendedDTO bean, String aliasTable) throws SQLException {
            final String prefix = "_sdp";
            bean.setIdProvincia(rs.getLong("id_provincia" + prefix + aliasTable));
            bean.setCodProvincia(rs.getString("cod_provincia" + prefix + aliasTable));
            bean.setDenomProvincia(rs.getString("denom_provincia" + prefix + aliasTable));
            bean.setSiglaProvincia(rs.getString("sigla_provincia" + prefix + aliasTable));

            RegioneExtendedDTO regione = new RegioneExtendedDTO();
            populateBeanRegione(rs, regione, aliasTable);
            bean.setRegione(regione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix + aliasTable));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix + aliasTable));
        }

        private void populateBeanRegione(ResultSet rs, RegioneExtendedDTO bean, String aliasTable) throws SQLException {
            final String prefix = "_sdr";
            bean.setIdRegione(rs.getLong("id_regione" + prefix + aliasTable));
            bean.setCodRegione(rs.getString("cod_regione" + prefix + aliasTable));
            bean.setDenomRegione(rs.getString("denom_regione" + prefix + aliasTable));

            NazioneDTO nazione = new NazioneDTO();
            populateBeanNazione(rs, nazione, aliasTable);
            bean.setNazione(nazione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix + aliasTable));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix + aliasTable));
        }

        private void populateBeanNazione(ResultSet rs, NazioneDTO bean, String aliasTable) throws SQLException {
            final String prefix = "_sdn";
            bean.setIdNazione(rs.getLong("id_nazione" + prefix + aliasTable));
            bean.setCodIstatNazione(rs.getString("cod_istat_nazione" + prefix + aliasTable));
            bean.setCodBelfioreNazione(rs.getString("cod_belfiore_nazione" + prefix + aliasTable));
            bean.setDenomNazione(rs.getString("denom_nazione" + prefix + aliasTable));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix + aliasTable));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix + aliasTable));
            bean.setDtIdStato(rs.getLong("dt_id_stato" + prefix + aliasTable));
            bean.setDtIdStatoPrev(rs.getLong("dt_id_stato_prev" + prefix + aliasTable));
            bean.setDtIdStatoNext(rs.getLong("dt_id_stato_next" + prefix + aliasTable));
            bean.setIdOrigine(rs.getLong("id_origine" + prefix + aliasTable));
        }
    }

}