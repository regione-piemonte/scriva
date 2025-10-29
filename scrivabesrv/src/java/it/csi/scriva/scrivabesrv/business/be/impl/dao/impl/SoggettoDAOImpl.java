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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoDAO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNaturaGiuridicaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoSoggettoDTO;
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
 * The type Soggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class SoggettoDAOImpl extends ScrivaBeSrvGenericDAO<SoggettoDTO> implements SoggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_soggetto = :idSoggetto";

    private static final String QUERY_LOAD_SOGGETTI = "SELECT soggetto.*\n" +
            ", tipo_soggetto.*\n" +
            ", tipo_natura_giuridica.*\n" +
            // nascita
            ", sdc_nasc.id_comune AS id_comune_sdc_nasc, sdc_nasc.cod_istat_comune AS cod_istat_comune_sdc_nasc, sdc_nasc.cod_belfiore_comune AS cod_belfiore_comune_sdc_nasc, sdc_nasc.denom_comune AS denom_comune_sdc_nasc, sdc_nasc.id_provincia AS id_provincia_sdc_nasc, sdc_nasc.data_inizio_validita AS data_inizio_validita_sdc_nasc, sdc_nasc.data_fine_validita AS data_fine_validita_sdc_nasc, sdc_nasc.dt_id_comune AS dt_id_comune_sdc_nasc, sdc_nasc.dt_id_comune_prev AS dt_id_comune_prev_sdc_nasc, sdc_nasc.dt_id_comune_next AS dt_id_comune_next_sdc_nasc, sdc_nasc.cap_comune AS cap_comune_sdc_nasc\n" +
            ", sdp_nasc.id_provincia AS id_provincia_sdp_nasc, sdp_nasc.cod_provincia AS cod_provincia_sdp_nasc, sdp_nasc.denom_provincia AS denom_provincia_sdp_nasc, sdp_nasc.sigla_provincia AS sigla_provincia_sdp_nasc, sdp_nasc.id_regione AS id_regione_sdp_nasc, sdp_nasc.data_inizio_validita AS data_inizio_validita_sdp_nasc, sdp_nasc.data_fine_validita AS data_fine_validita_sdp_nasc\n" +
            ", sdr_nasc.id_regione AS id_regione_sdr_nasc, sdr_nasc.cod_regione AS cod_regione_sdr_nasc, sdr_nasc.denom_regione AS denom_regione_sdr_nasc, sdr_nasc.id_nazione AS id_nazione_sdr_nasc, sdr_nasc.data_inizio_validita AS data_inizio_validita_sdr_nasc, sdr_nasc.data_fine_validita AS data_fine_validita_sdr_nasc\n" +
            ", sdn_nasc.id_nazione AS id_nazione_sdn_nasc, sdn_nasc.cod_istat_nazione AS cod_istat_nazione_sdn_nasc, sdn_nasc.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_nasc, sdn_nasc.denom_nazione AS denom_nazione_sdn_nasc, sdn_nasc.data_inizio_validita AS data_inizio_validita_sdn_nasc, sdn_nasc.data_fine_validita AS data_fine_validita_sdn_nasc, sdn_nasc.dt_id_stato AS dt_id_stato_sdn_nasc, sdn_nasc.dt_id_stato_prev AS dt_id_stato_prev_sdn_nasc, sdn_nasc.dt_id_stato_next AS dt_id_stato_next_sdn_nasc, sdn_nasc.id_origine AS id_origine_sdn_nasc\n" +
            // nazione nascita
            ", sdnn_nasc.id_nazione AS id_nazione_sdnn_nasc, sdnn_nasc.cod_istat_nazione AS cod_istat_nazione_sdnn_nasc, sdnn_nasc.cod_belfiore_nazione AS cod_belfiore_nazione_sdnn_nasc, sdnn_nasc.denom_nazione AS denom_nazione_sdnn_nasc, sdnn_nasc.data_inizio_validita AS data_inizio_validita_sdnn_nasc, sdnn_nasc.data_fine_validita AS data_fine_validita_sdnn_nasc, sdnn_nasc.dt_id_stato AS dt_id_stato_sdnn_nasc, sdnn_nasc.dt_id_stato_prev AS dt_id_stato_prev_sdnn_nasc, sdnn_nasc.dt_id_stato_next AS dt_id_stato_next_sdnn_nasc, sdnn_nasc.id_origine AS id_origine_sdnn_nasc\n" +
            // residenza
            ", sdc_res.id_comune AS id_comune_sdc_res, sdc_res.cod_istat_comune AS cod_istat_comune_sdc_res, sdc_res.cod_belfiore_comune AS cod_belfiore_comune_sdc_res, sdc_res.denom_comune AS denom_comune_sdc_res, sdc_res.id_provincia AS id_provincia_sdc_res, sdc_res.data_inizio_validita AS data_inizio_validita_sdc_res, sdc_res.data_fine_validita AS data_fine_validita_sdc_res, sdc_res.dt_id_comune AS dt_id_comune_sdc_res, sdc_res.dt_id_comune_prev AS dt_id_comune_prev_sdc_res, sdc_res.dt_id_comune_next AS dt_id_comune_next_sdc_res, sdc_res.cap_comune AS cap_comune_sdc_res\n" +
            ", sdp_res.id_provincia AS id_provincia_sdp_res, sdp_res.cod_provincia AS cod_provincia_sdp_res, sdp_res.denom_provincia AS denom_provincia_sdp_res, sdp_res.sigla_provincia AS sigla_provincia_sdp_res, sdp_res.id_regione AS id_regione_sdp_res, sdp_res.data_inizio_validita AS data_inizio_validita_sdp_res, sdp_res.data_fine_validita AS data_fine_validita_sdp_res\n" +
            ", sdr_res.id_regione AS id_regione_sdr_res, sdr_res.cod_regione AS cod_regione_sdr_res, sdr_res.denom_regione AS denom_regione_sdr_res, sdr_res.id_nazione AS id_nazione_sdr_res, sdr_res.data_inizio_validita AS data_inizio_validita_sdr_res, sdr_res.data_fine_validita AS data_fine_validita_sdr_res\n" +
            ", sdn_res.id_nazione AS id_nazione_sdn_res, sdn_res.cod_istat_nazione AS cod_istat_nazione_sdn_res, sdn_res.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_res, sdn_res.denom_nazione AS denom_nazione_sdn_res, sdn_res.data_inizio_validita AS data_inizio_validita_sdn_res, sdn_res.data_fine_validita AS data_fine_validita_sdn_res, sdn_res.dt_id_stato AS dt_id_stato_sdn_res, sdn_res.dt_id_stato_prev AS dt_id_stato_prev_sdn_res, sdn_res.dt_id_stato_next AS dt_id_stato_next_sdn_res, sdn_res.id_origine AS id_origine_sdn_res\n" +
            // nazione residenza
            ", sdnn_res.id_nazione AS id_nazione_sdnn_res, sdnn_res.cod_istat_nazione AS cod_istat_nazione_sdnn_res, sdnn_res.cod_belfiore_nazione AS cod_belfiore_nazione_sdnn_res, sdnn_res.denom_nazione AS denom_nazione_sdnn_res, sdnn_res.data_inizio_validita AS data_inizio_validita_sdnn_res, sdnn_res.data_fine_validita AS data_fine_validita_sdnn_res, sdnn_res.dt_id_stato AS dt_id_stato_sdnn_res, sdnn_res.dt_id_stato_prev AS dt_id_stato_prev_sdnn_res, sdnn_res.dt_id_stato_next AS dt_id_stato_next_sdnn_res, sdnn_res.id_origine AS id_origine_sdnn_res\n" +
            // sede legale
            ", sdc_sl.id_comune AS id_comune_sdc_sl, sdc_sl.cod_istat_comune AS cod_istat_comune_sdc_sl, sdc_sl.cod_belfiore_comune AS cod_belfiore_comune_sdc_sl, sdc_sl.denom_comune AS denom_comune_sdc_sl, sdc_sl.id_provincia AS id_provincia_sdc_sl, sdc_sl.data_inizio_validita AS data_inizio_validita_sdc_sl, sdc_sl.data_fine_validita AS data_fine_validita_sdc_sl, sdc_sl.dt_id_comune AS dt_id_comune_sdc_sl, sdc_sl.dt_id_comune_prev AS dt_id_comune_prev_sdc_sl, sdc_sl.dt_id_comune_next AS dt_id_comune_next_sdc_sl, sdc_sl.cap_comune AS cap_comune_sdc_sl\n" +
            ", sdp_sl.id_provincia AS id_provincia_sdp_sl, sdp_sl.cod_provincia AS cod_provincia_sdp_sl, sdp_sl.denom_provincia AS denom_provincia_sdp_sl, sdp_sl.sigla_provincia AS sigla_provincia_sdp_sl, sdp_sl.id_regione AS id_regione_sdp_sl, sdp_sl.data_inizio_validita AS data_inizio_validita_sdp_sl, sdp_sl.data_fine_validita AS data_fine_validita_sdp_sl\n" +
            ", sdr_sl.id_regione AS id_regione_sdr_sl, sdr_sl.cod_regione AS cod_regione_sdr_sl, sdr_sl.denom_regione AS denom_regione_sdr_sl, sdr_sl.id_nazione AS id_nazione_sdr_sl, sdr_sl.data_inizio_validita AS data_inizio_validita_sdr_sl, sdr_sl.data_fine_validita AS data_fine_validita_sdr_sl\n" +
            ", sdn_sl.id_nazione AS id_nazione_sdn_sl, sdn_sl.cod_istat_nazione AS cod_istat_nazione_sdn_sl, sdn_sl.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_sl, sdn_sl.denom_nazione AS denom_nazione_sdn_sl, sdn_sl.data_inizio_validita AS data_inizio_validita_sdn_sl, sdn_sl.data_fine_validita AS data_fine_validita_sdn_sl, sdn_sl.dt_id_stato AS dt_id_stato_sdn_sl, sdn_sl.dt_id_stato_prev AS dt_id_stato_prev_sdn_sl, sdn_sl.dt_id_stato_next AS dt_id_stato_next_sdn_sl, sdn_sl.id_origine AS id_origine_sdn_sl\n" +
            // nazione sede legale
            ", sdn_sle.id_nazione AS id_nazione_sdn_sle, sdn_sle.cod_istat_nazione AS cod_istat_nazione_sdn_sle, sdn_sle.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_sle, sdn_sle.denom_nazione AS denom_nazione_sdn_sle, sdn_sle.data_inizio_validita AS data_inizio_validita_sdn_sle, sdn_sle.data_fine_validita AS data_fine_validita_sdn_sle, sdn_sle.dt_id_stato AS dt_id_stato_sdn_sle, sdn_sle.dt_id_stato_prev AS dt_id_stato_prev_sdn_sle, sdn_sle.dt_id_stato_next AS dt_id_stato_next_sdn_sle, sdn_sle.id_origine AS id_origine_sdn_sle\n" +
            "FROM _replaceTableName_ soggetto\n" +
            "INNER JOIN scriva_d_tipo_soggetto tipo_soggetto ON soggetto.id_tipo_soggetto = tipo_soggetto.id_tipo_soggetto\n" +
            "LEFT JOIN scriva_d_tipo_natura_giuridica tipo_natura_giuridica ON soggetto.id_tipo_natura_giuridica = tipo_natura_giuridica.id_tipo_natura_giuridica\n" +
            "LEFT JOIN scriva_d_comune sdc_nasc ON sdc_nasc.id_comune = soggetto.id_comune_nascita\n" +
            "LEFT JOIN scriva_d_provincia sdp_nasc ON sdp_nasc.id_provincia = sdc_nasc.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_nasc ON sdr_nasc.id_regione = sdp_nasc.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_nasc ON sdn_nasc.id_nazione = sdr_nasc.id_nazione\n" +
            "LEFT JOIN scriva_d_comune sdc_res ON sdc_res.id_comune = soggetto.id_comune_residenza\n" +
            "LEFT JOIN scriva_d_provincia sdp_res ON sdp_res.id_provincia = sdc_res.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_res ON sdr_res.id_regione = sdp_res.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_res ON sdn_res.id_nazione = sdr_res.id_nazione\n" +
            "LEFT JOIN scriva_d_comune sdc_sl ON sdc_sl.id_comune = soggetto.id_comune_sede_legale\n" +
            "LEFT JOIN scriva_d_provincia sdp_sl ON sdp_sl.id_provincia = sdc_sl.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_sl ON sdr_sl.id_regione = sdp_sl.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_sl ON sdn_sl.id_nazione = sdr_sl.id_nazione\n" +
            "LEFT JOIN scriva_d_nazione sdnr ON sdnr.id_nazione = soggetto.id_nazione_residenza\n" +
            "LEFT JOIN scriva_d_nazione sdnn_nasc ON sdnn_nasc.id_nazione = soggetto.id_nazione_nascita\n" +
            "LEFT JOIN scriva_d_nazione sdnn_res ON sdnn_res.id_nazione = soggetto.id_nazione_residenza\n" +
            "LEFT JOIN scriva_d_nazione sdn_sle ON sdn_sle.id_nazione = soggetto.id_nazione_sede_legale\n";

    private static final String QUERY_LOAD_SOGGETTO_BY_ID = QUERY_LOAD_SOGGETTI + "WHERE soggetto.id_soggetto = :idSogg";

    private static final String QUERY_LOAD_SOGGETTI_BY_TIPO_SOGGETTO = QUERY_LOAD_SOGGETTI + "WHERE soggetto.id_tipo_soggetto = :idTipoSoggetto";

    private static final String QUERY_LOAD_SOGGETTO_BY_CODICE_FISCALE = QUERY_LOAD_SOGGETTI + "WHERE upper(soggetto.cf_soggetto) = upper(:codiceFiscaleSoggetto)";

    private static final String QUERY_LOAD_SOGGETTO_BY_CODICE_FISCALE_TIPO = QUERY_LOAD_SOGGETTI + "WHERE UPPER(soggetto.cf_soggetto) = UPPER(:codiceFiscaleSoggetto) " + "AND UPPER(tipo_soggetto.cod_tipo_soggetto) = UPPER(:tipoSoggetto)";

    private static final String QUERY_LOAD_SOGGETTI_BY_IDS_LIST = QUERY_LOAD_SOGGETTI + "WHERE soggetto.id_soggetto in (:ids)";

    private static final String QUERY_LOAD_SOGGETTO_BY_PARTITA_IVA = "SELECT * FROM _replaceTableName_ WHERE partita_iva_soggetto = :partitaIvaSoggetto";

    private static final String QUERY_DELETE_SOGGETTO = "DELETE FROM _replaceTableName_ WHERE gest_uid = :uid";

    private static final String QUERY_DELETE_SOGGETTO_BY_ID = "DELETE FROM _replaceTableName_ WHERE id_soggetto = :idSogg";

    private static final String QUERY_INSERT_SOGGETTO = "INSERT INTO _replaceTableName_\n" +
            "(id_soggetto, cf_soggetto, id_tipo_soggetto, id_tipo_natura_giuridica, id_comune_nascita, id_comune_residenza,\n" +
            "id_comune_sede_legale, partita_iva_soggetto, den_soggetto, data_cessazione_soggetto, nome, cognome, data_nascita_soggetto,\n" +
            "citta_estera_nascita, num_telefono, des_email, des_pec, indirizzo_soggetto, num_civico_indirizzo,\n" +
            "citta_estera_residenza, den_provincia_cciaa, den_anno_cciaa, den_numero_cciaa,\n" +
            "num_cellulare, id_nazione_nascita, id_nazione_residenza, des_localita,\n" +
            "citta_estera_sede_legale, id_nazione_sede_legale, cap_residenza, cap_sede_legale,\n" +
            "data_aggiornamento, id_funzionario,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, id_masterdata, id_masterdata_origine, gest_uid)\n" +
            "VALUES(nextval('seq_scriva_t_soggetto'), :cf, :idTipoSogg, :idTipoNatGiur, :idComuneNasc, :idComuneRes, " +
            ":idComuneSl, :piva, :denomSogg, :dataCessazSogg, :nome, :cognome, :dataNascSogg,\n" +
            ":cittaEsteraNasc, :numTel, :email, :pec, :indSogg, :numCivInd,\n" +
            ":cittaEsteraRes, :denomProvCCIAA, :denomAnnoCCIAA, :denomNumCCIAA,\n" +
            ":numCellulare, :idNazioneNascita, :idNazioneResidenza, :desLocalita,\n" +
            ":cittaEsteraSedeLegale, :idNazioneSedeLegale, :capResidenza, :capSedeLegale,\n" +
            ":dataAggiornamento, :idFunzionario,\n" +
            ":dateIns, :attoreIns, :dateUpd, :attoreUpd, :idMasterdata, :idMasterdataOrigine, :uid)\n";

    private static final String QUERY_INSERT_STORICO_SOGGETTO = "INSERT INTO _replaceTableName_\n" +
            "(id_soggetto_storico, id_soggetto, cf_soggetto, id_tipo_soggetto, id_tipo_natura_giuridica, id_comune_nascita, id_comune_residenza,\n" +
            "id_comune_sede_legale, partita_iva_soggetto, den_soggetto, data_cessazione_soggetto, nome, cognome, data_nascita_soggetto,\n" +
            "citta_estera_nascita, num_telefono, des_email, des_pec, indirizzo_soggetto, num_civico_indirizzo,\n" +
            "citta_estera_residenza, den_provincia_cciaa, den_anno_cciaa, den_numero_cciaa,\n" +
            "num_cellulare, id_nazione_nascita, id_nazione_residenza, des_localita,\n" +
            "citta_estera_sede_legale, id_nazione_sede_legale, cap_residenza, cap_sede_legale,\n" +
            "data_aggiornamento, id_funzionario,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, id_masterdata, id_masterdata_origine, gest_uid)\n" +
            "VALUES(nextval('seq_scriva_s_soggetto'), :idSogg, :cf, :idTipoSogg, :idTipoNatGiur, :idComuneNasc, :idComuneRes,\n" +
            ":idComuneSl, :piva, :denomSogg, :dataCessazSogg, :nome, :cognome, :dataNascSogg,\n" +
            ":cittaEsteraNasc, :numTel, :email, :pec, :indSogg, :numCivInd,\n" +
            ":cittaEsteraRes, :denomProvCCIAA, :denomAnnoCCIAA, :denomNumCCIAA,\n" +
            ":numCellulare, :idNazioneNascita, :idNazioneResidenza, :desLocalita,\n" +
            ":cittaEsteraSedeLegale, :idNazioneSedeLegale, :capResidenza, :capSedeLegale,\n" +
            ":dataAggiornamento, :idFunzionario,\n" +
            ":dateIns, :attoreIns, :dateUpd, :attoreUpd, :idMasterdata, :idMasterdataOrigine, :uid)\n";

    private static final String QUERY_UPDATE_SOGGETTO = "UPDATE _replaceTableName_\n" +
            "SET id_tipo_soggetto = :idTipoSogg, id_tipo_natura_giuridica = :idTipoNatGiur, den_soggetto = :denomSogg, indirizzo_soggetto = :indSogg, des_email = :email, des_pec = :pec,\n" +
            "data_cessazione_soggetto = :dataCessazSogg, nome = :nome, cognome = :cognome, num_civico_indirizzo = :numCivInd,\n" +
            "cf_soggetto = :cf, partita_iva_soggetto = :piva, id_comune_sede_legale = :idComuneSl,\n" +
            "num_telefono = :numTel, den_provincia_cciaa = :denomProvCCIAA, den_anno_cciaa = :denomAnnoCCIAA, den_numero_cciaa = :denomNumCCIAA,\n" +
            "data_nascita_soggetto = :dataNascSogg, id_comune_residenza = :idComuneRes, id_comune_nascita = :idComuneNasc, citta_estera_nascita = :cittaEsteraNasc,\n" +
            "citta_estera_residenza = :cittaEsteraRes, num_cellulare = :numCellulare, id_nazione_nascita = :idNazioneNascita, id_nazione_residenza = :idNazioneResidenza, des_localita = :desLocalita,\n" +
            "citta_estera_sede_legale = :cittaEsteraSedeLegale, id_nazione_sede_legale = :idNazioneSedeLegale, cap_residenza = :capResidenza, cap_sede_legale = :capSedeLegale,\n" +
            "data_aggiornamento=:dataAggiornamento, id_funzionario=:idFunzionario,\n" +
            "gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd,  id_masterdata = :idMasterdata\n" +
            "WHERE id_soggetto = :idSogg\n";

    private static final String QUERY_DELETE_SOGGETTO_STORICO = "DELETE FROM _replaceTableName_ WHERE id_soggetto = :idSogg";

    /**
     * Load soggetti list.
     *
     * @return List<SoggettoExtendedDTO> list
     */
    @Override
    public List<SoggettoExtendedDTO> loadSoggetti() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI, null);
    }

    /**
     * Load soggetti by tipo soggetto list.
     *
     * @param idTipoSoggetto idTipoSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    @Override
    public List<SoggettoExtendedDTO> loadSoggettiByTipoSoggetto(Long idTipoSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoSoggetto", idTipoSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_BY_TIPO_SOGGETTO, map);
    }

    /**
     * Load soggetti by id list list.
     *
     * @param ids idSoggetto multipli
     * @return List<SoggettoExtendedDTO> list
     */
    @Override
    public List<SoggettoExtendedDTO> loadSoggettiByIdList(List<Long> ids) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_BY_IDS_LIST, map);
    }

    /**
     * Load soggetto list.
     *
     * @param id idSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    @Override
    public List<SoggettoExtendedDTO> loadSoggetto(Long id) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSogg", id);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_BY_ID, map);
    }

    /**
     * Load soggetto by codice fiscale list.
     *
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    @Override
    public List<SoggettoExtendedDTO> loadSoggettoByCodiceFiscale(String codiceFiscaleSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codiceFiscaleSoggetto", codiceFiscaleSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_BY_CODICE_FISCALE, map);
    }

    /**
     * Load soggetto by codice fiscale and tipo list.
     *
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @param tipoSoggetto          tipoSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    @Override
    public List<SoggettoExtendedDTO> loadSoggettoByCodiceFiscaleAndTipo(String codiceFiscaleSoggetto, String tipoSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codiceFiscaleSoggetto", codiceFiscaleSoggetto);
        map.put("tipoSoggetto", tipoSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_BY_CODICE_FISCALE_TIPO, map);
    }

    /**
     * Load soggetto by partita iva list.
     *
     * @param partitaIvaSoggetto partitaIvaSoggetto
     * @return List<SoggettoExtendedDTO> list
     */
    @Override
    public List<SoggettoExtendedDTO> loadSoggettoByPartitaIva(String partitaIvaSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("partitaIvaSoggetto", partitaIvaSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_BY_PARTITA_IVA, map);
    }

    /**
     * Find by pk soggetto dto.
     *
     * @param idSoggetto idSoggetto
     * @return SoggettoDTO soggetto dto
     */
    @Override
    public SoggettoDTO findByPK(Long idSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggetto", idSoggetto);
        return findByPK(className, map);
    }

    /**
     * Save soggetto long.
     *
     * @param dto SoggettoDTO
     * @return id record salvato
     */
    @Override
    public Long saveSoggetto(SoggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idTipoSogg", dto.getIdTipoSoggetto());
            map.put("idTipoNatGiur", dto.getIdTipoNaturaGiuridica());
            map.put("denomSogg", dto.getDenSoggetto());
            map.put("dataCessazSogg", dto.getDataCessazioneSoggetto());
            map.put("indSogg", dto.getIndirizzoSoggetto());
            map.put("nome", dto.getNome());
            map.put("cognome", dto.getCognome());
            map.put("email", dto.getDesEmail());
            map.put("pec", dto.getDesPec());
            map.put("cf", dto.getCfSoggetto());
            map.put("piva", dto.getPivaSoggetto());
            map.put("numCivInd", dto.getNumCivicoIndirizzo());
            map.put("numTel", dto.getNumTelefono());
            map.put("denomProvCCIAA", dto.getDenProvinciaCCIAA());
            map.put("denomAnnoCCIAA", dto.getDenAnnoCCIAA());
            map.put("denomNumCCIAA", dto.getDenNumeroCCIAA());
            map.put("dataNascSogg", dto.getDataNascitaSoggetto());
            map.put("idComuneRes", dto.getIdComuneResidenza());
            map.put("idComuneNasc", dto.getIdComuneNascita());
            map.put("idComuneSl", dto.getIdComuneSedeLegale());
            map.put("cittaEsteraNasc", dto.getCittaEsteraNascita());
            map.put("cittaEsteraRes", dto.getCittaEsteraResidenza());
            map.put("dateIns", now);
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreIns());
            map.put("idMasterdata", dto.getIdMasterdata());
            map.put("idMasterdataOrigine", dto.getIdMasterdataOrigine());
            map.put("numCellulare", dto.getNumCellulare());
            map.put("idNazioneResidenza", dto.getIdNazioneResidenza());
            map.put("idNazioneNascita", dto.getIdNazioneNascita());
            map.put("desLocalita", dto.getDesLocalita());
            map.put("cittaEsteraSedeLegale", dto.getCittaEsteraSedeLegale());
            map.put("idNazioneSedeLegale", dto.getIdNazioneSedeLegale());
            map.put("capResidenza", dto.getCapResidenza());
            map.put("capSedeLegale", dto.getCapSedeLegale());
            map.put("dataAggiornamento", dto.getDataAggiornamento() != null ? dto.getDataAggiornamento() : now);
            map.put("idFunzionario", dto.getIdFunzionario());

            map.put("uid", generateGestUID(dto.getCfSoggetto() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            int returnValue = template.update(getQuery(QUERY_INSERT_SOGGETTO, null, null), params, keyHolder, new String[]{"id_soggetto"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                map.put("idSogg", key.longValue());
                params = getParameterValue(map);

                template.update(getQueryStorico(QUERY_INSERT_STORICO_SOGGETTO), params);
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
     * Update soggetto integer.
     *
     * @param dto SoggettoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateSoggetto(SoggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            SoggettoDTO soggetto = this.findByPK(dto.getIdSoggetto());
            if (null == soggetto) {
                logError(className, "Record non trovato con id [" + dto.getIdSoggetto() + "]");
                return -1;
            }

            int returnValue = 1;
            if (!dto.equals(soggetto)) {
                Long idFunzionarioOld = soggetto.getIdFunzionario() != null && soggetto.getIdFunzionario() > 0 ? soggetto.getIdFunzionario() : null;
                map.put("idSogg", dto.getIdSoggetto());
                map.put("idTipoSogg", dto.getIdTipoSoggetto());
                map.put("idTipoNatGiur", dto.getIdTipoNaturaGiuridica());
                map.put("denomSogg", dto.getDenSoggetto());
                map.put("dataCessazSogg", dto.getDataCessazioneSoggetto());
                map.put("indSogg", dto.getIndirizzoSoggetto());
                map.put("nome", dto.getNome());
                map.put("cognome", dto.getCognome());
                map.put("email", dto.getDesEmail());
                map.put("pec", dto.getDesPec());
                map.put("cf", dto.getCfSoggetto());
                map.put("piva", dto.getPivaSoggetto());
                map.put("numCivInd", dto.getNumCivicoIndirizzo());
                map.put("numTel", dto.getNumTelefono());
                map.put("denomProvCCIAA", dto.getDenProvinciaCCIAA());
                map.put("denomAnnoCCIAA", dto.getDenAnnoCCIAA());
                map.put("denomNumCCIAA", dto.getDenNumeroCCIAA());
                map.put("dataNascSogg", dto.getDataNascitaSoggetto());
                map.put("idComuneRes", dto.getIdComuneResidenza());
                map.put("idComuneNasc", dto.getIdComuneNascita());
                map.put("idComuneSl", dto.getIdComuneSedeLegale());
                map.put("cittaEsteraNasc", dto.getCittaEsteraNascita());
                map.put("cittaEsteraRes", dto.getCittaEsteraResidenza());
                map.put("dateIns", soggetto.getGestDataIns());
                map.put("attoreIns", soggetto.getGestAttoreIns());
                map.put("dateUpd", now);
                map.put("attoreUpd", dto.getGestAttoreUpd());
                map.put("idMasterdata", dto.getIdMasterdata());
                map.put("idMasterdataOrigine", soggetto.getIdMasterdataOrigine());
                map.put("numCellulare", dto.getNumCellulare());
                map.put("idNazioneResidenza", dto.getIdNazioneResidenza());
                map.put("idNazioneNascita", dto.getIdNazioneNascita());
                map.put("desLocalita", dto.getDesLocalita());
                map.put("cittaEsteraSedeLegale", dto.getCittaEsteraSedeLegale());
                map.put("idNazioneSedeLegale", dto.getIdNazioneSedeLegale());
                map.put("capResidenza", dto.getCapResidenza());
                map.put("capSedeLegale", dto.getCapSedeLegale());
                map.put("dataAggiornamento", dto.getDataAggiornamento() != null ? dto.getDataAggiornamento() : now);
                map.put("idFunzionario", dto.getIdFunzionario() != null ? dto.getIdFunzionario() : idFunzionarioOld);
                map.put("uid", soggetto.getGestUID());
                MapSqlParameterSource params = getParameterValue(map);

                returnValue = template.update(getQuery(QUERY_UPDATE_SOGGETTO, null, null), params);
                if (returnValue > 0) {
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_SOGGETTO), params);
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
     * Delete soggetto integer.
     *
     * @param uid uid
     * @return numero record cancellati
     */
    @Override
    public Integer deleteSoggetto(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_SOGGETTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete soggetto by id integer.
     *
     * @param id idSoggetto
     * @return numero record cancellati
     */
    public Integer deleteSoggettoById(Long id) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idSogg", id);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_SOGGETTO_BY_ID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete soggetto storico by soggetto integer.
     *
     * @param idSoggetto the id soggetto
     * @return the integer
     */
    public Integer deleteSoggettoStoricoBySoggetto(Long idSoggetto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idSogg", idSoggetto);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQueryStorico(QUERY_DELETE_SOGGETTO_STORICO), params);
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
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<SoggettoDTO>
     */
    @Override
    public RowMapper<SoggettoDTO> getRowMapper() throws SQLException {
        return new SoggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<SoggettoExtendedDTO>
     */
    @Override
    public RowMapper<SoggettoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new SoggettoExtendedRowMapper();
    }

    /**
     * The type Soggetto row mapper.
     */
    public static class SoggettoRowMapper implements RowMapper<SoggettoDTO> {

        /**
         * Instantiates a new Soggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public SoggettoRowMapper() throws SQLException {
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
        public SoggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoggettoDTO bean = new SoggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, SoggettoDTO bean) throws SQLException {
            bean.setIdSoggetto(rs.getLong("id_soggetto"));
            bean.setIdTipoSoggetto(rs.getLong("id_tipo_soggetto"));
            bean.setIdTipoNaturaGiuridica(rs.getLong("id_tipo_natura_giuridica"));
            bean.setDenSoggetto(rs.getString("den_soggetto"));
            bean.setDataCessazioneSoggetto(rs.getDate("data_cessazione_soggetto"));
            bean.setNome(rs.getString("nome"));
            bean.setCognome(rs.getString("cognome"));
            bean.setIndirizzoSoggetto(rs.getString("indirizzo_soggetto"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setDesPec(rs.getString("des_pec"));
            bean.setCfSoggetto(rs.getString("cf_soggetto"));
            bean.setPivaSoggetto(rs.getString("partita_iva_soggetto"));
            bean.setNumTelefono(rs.getString("num_telefono"));
            bean.setDenProvinciaCCIAA(rs.getString("den_provincia_cciaa"));
            bean.setDenAnnoCCIAA(rs.getInt("den_anno_cciaa"));
            bean.setDenNumeroCCIAA(rs.getString("den_numero_cciaa"));
            bean.setDataNascitaSoggetto(rs.getDate("data_nascita_soggetto"));
            bean.setIdComuneResidenza(rs.getLong("id_comune_residenza"));
            bean.setIdComuneNascita(rs.getLong("id_comune_nascita"));
            bean.setIdComuneSedeLegale(rs.getLong("id_comune_sede_legale"));
            bean.setCittaEsteraNascita(rs.getString("citta_estera_nascita"));
            bean.setCittaEsteraResidenza(rs.getString("citta_estera_residenza"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setIdNazioneNascita(rs.getLong("id_nazione_nascita") > 0 ? rs.getLong("id_nazione_nascita") : null);
            bean.setIdNazioneResidenza(rs.getLong("id_nazione_residenza"));
            bean.setDesLocalita(rs.getString("des_localita"));
            bean.setCittaEsteraSedeLegale(rs.getString("citta_estera_sede_legale"));
            bean.setIdNazioneSedeLegale(rs.getLong("id_nazione_sede_legale") > 0 ? rs.getLong("id_nazione_sede_legale") : null);
            bean.setCapResidenza(rs.getString("cap_residenza"));
            bean.setCapSedeLegale(rs.getString("cap_sede_legale"));

            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setIdMasterdata(rs.getLong("id_masterdata"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Soggetto extended row mapper.
     */
    public static class SoggettoExtendedRowMapper implements RowMapper<SoggettoExtendedDTO> {

        /**
         * Instantiates a new Soggetto extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public SoggettoExtendedRowMapper() throws SQLException {
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
        public SoggettoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoggettoExtendedDTO bean = new SoggettoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, SoggettoExtendedDTO bean) throws SQLException {
            bean.setIdSoggetto(rs.getLong("id_soggetto"));

            TipoSoggettoDTO tipoSoggetto = new TipoSoggettoDTO();
            populateBeanTipoSoggetto(rs, tipoSoggetto);
            bean.setTipoSoggetto(tipoSoggetto.getIdTipoSoggetto() != null ? tipoSoggetto : null);

            TipoNaturaGiuridicaDTO tipoNaturaGiuridica = new TipoNaturaGiuridicaDTO();
            populateBeanTipoNaturaGiuridica(rs, tipoNaturaGiuridica);
            bean.setTipoNaturaGiuridica(tipoNaturaGiuridica.getIdTipoNaturaGiuridica() != null ? tipoNaturaGiuridica : null);


            ComuneExtendedDTO comuneResidenza = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneResidenza, "_res");
            bean.setComuneResidenza(comuneResidenza.getIdComune() != 0 ? comuneResidenza : null);

            ComuneExtendedDTO comuneSedeLegale = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneSedeLegale, "_sl");
            bean.setComuneSedeLegale(comuneSedeLegale.getIdComune() != 0 ? comuneSedeLegale : null);

            ComuneExtendedDTO comuneNascita = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneNascita, "_nasc");
            bean.setComuneNascita(comuneNascita.getIdComune() != 0 ? comuneNascita : null);

            bean.setDenSoggetto(rs.getString("den_soggetto"));
            bean.setDataCessazioneSoggetto(rs.getDate("data_cessazione_soggetto"));
            bean.setNome(rs.getString("nome"));
            bean.setCognome(rs.getString("cognome"));
            bean.setIndirizzoSoggetto(rs.getString("indirizzo_soggetto"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setDesPec(rs.getString("des_pec"));
            bean.setCfSoggetto(rs.getString("cf_soggetto"));
            bean.setPivaSoggetto(rs.getString("partita_iva_soggetto"));
            bean.setNumTelefono(rs.getString("num_telefono"));
            bean.setDenProvinciaCCIAA(rs.getString("den_provincia_cciaa"));
            bean.setDenAnnoCCIAA(rs.getInt("den_anno_cciaa") > 0 ? rs.getInt("den_anno_cciaa") : null);
            bean.setDenNumeroCCIAA(rs.getString("den_numero_cciaa"));
            bean.setDataNascitaSoggetto(rs.getDate("data_nascita_soggetto"));
            bean.setCittaEsteraNascita(rs.getString("citta_estera_nascita"));
            bean.setCittaEsteraResidenza(rs.getString("citta_estera_residenza"));
            bean.setNumCellulare(rs.getString("num_cellulare"));

            NazioneDTO nazioneNascita = new NazioneDTO();
            populateBeanNazione(rs, nazioneNascita, "n_nasc");
            bean.setNazioneNascita(nazioneNascita.getIdNazione() > 0 ? nazioneNascita : null);

            NazioneDTO nazioneResidenza = new NazioneDTO();
            populateBeanNazione(rs, nazioneResidenza, "n_res");
            bean.setNazioneResidenza(nazioneResidenza.getIdNazione() > 0 ? nazioneResidenza : null);

            bean.setDesLocalita(rs.getString("des_localita"));
            bean.setCittaEsteraSedeLegale(rs.getString("citta_estera_sede_legale"));

            NazioneDTO nazioneSedeLegale = new NazioneDTO();
            populateBeanNazione(rs, nazioneSedeLegale, "_sle");
            bean.setNazioneSedeLegale(nazioneSedeLegale.getIdNazione() > 0 ? nazioneSedeLegale : null);

            bean.setCapResidenza(rs.getString("cap_residenza"));
            bean.setCapSedeLegale(rs.getString("cap_sede_legale"));
            bean.setDataAggiornamento(rs.getTimestamp("data_aggiornamento"));

            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setIdMasterdata(rs.getLong("id_masterdata"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanTipoSoggetto(ResultSet rs, TipoSoggettoDTO bean) throws SQLException {
            bean.setIdTipoSoggetto(rs.getLong("id_tipo_soggetto") > 0 ? rs.getLong("id_tipo_soggetto") : null);
            bean.setCodiceTipoSoggetto(rs.getString("cod_tipo_soggetto"));
            bean.setDescrizioneTipoSoggetto(rs.getString("des_tipo_soggetto"));
        }

        private void populateBeanTipoNaturaGiuridica(ResultSet rs, TipoNaturaGiuridicaDTO bean) throws SQLException {
            bean.setIdTipoNaturaGiuridica(rs.getLong("id_tipo_natura_giuridica") > 0 ? rs.getLong("id_tipo_natura_giuridica") : null);
            bean.setCodiceTipoNaturaGiuridica(rs.getString("cod_tipo_natura_giuridica"));
            bean.setDescrizioneTipoNaturaGiuridica(rs.getString("des_tipo_natura_giuridica"));
            bean.setSiglaTipoNaturaGiuridica(rs.getString("sigla_tipo_natura_giuridica"));
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

    /**
     * Gets query storico.
     *
     * @param query the query
     * @return the query storico
     */
    public String getQueryStorico(String query) {
        return query.replace("_replaceTableName_", getStoricoTableName());
    }

    /**
     * Gets storico table name.
     *
     * @return the storico table name
     */
    public String getStoricoTableName() {
        return storicoTableName;
    }

    /**
     * Sets storico table name.
     *
     * @param storicoTableName the storico table name
     */
    public void setStoricoTableName(String storicoTableName) {
        this.storicoTableName = storicoTableName;
    }

}