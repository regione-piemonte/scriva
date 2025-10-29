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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.GruppoSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RuoloCompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.RuoloSoggettoDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
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
 * The type Soggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class SoggettoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<SoggettoIstanzaDTO> implements SoggettoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_SOGGETTI_ISTANZE = "SELECT soggetto_istanza.id_soggetto_istanza AS soggetto_istanza_id,\n" +
            "soggetto_istanza.id_soggetto_padre,\n" +
            "soggetto_istanza.cf_soggetto AS cf_soggetto_si, soggetto_istanza.partita_iva_soggetto AS partita_iva_soggetto_si, soggetto_istanza.den_soggetto AS den_soggetto_si, soggetto_istanza.data_cessazione_soggetto AS data_cessazione_soggetto_si, soggetto_istanza.nome AS nome_si, soggetto_istanza.cognome AS cognome_si, soggetto_istanza.data_nascita_soggetto AS data_nascita_soggetto_si, soggetto_istanza.citta_estera_nascita AS citta_estera_nascita_si, soggetto_istanza.num_telefono AS num_telefono_si, soggetto_istanza.des_email AS des_email_si, soggetto_istanza.des_pec AS des_pec_si, soggetto_istanza.indirizzo_soggetto AS indirizzo_soggetto_si, soggetto_istanza.num_civico_indirizzo AS num_civico_indirizzo_si, soggetto_istanza.citta_estera_residenza AS citta_estera_residenza_si, soggetto_istanza.den_provincia_cciaa AS den_provincia_cciaa_si, soggetto_istanza.den_anno_cciaa AS den_anno_cciaa_si, soggetto_istanza.den_numero_cciaa AS den_numero_cciaa_si, soggetto_istanza.gest_data_ins AS gest_data_ins_si, soggetto_istanza.gest_attore_ins AS gest_attore_ins_si, soggetto_istanza.gest_data_upd AS gest_data_upd_si, soggetto_istanza.gest_attore_upd AS gest_attore_upd_si, soggetto_istanza.id_masterdata as id_masterdata_si,\n" +
            "soggetto_istanza.gest_uid AS gest_uid_si, soggetto_istanza.id_masterdata_origine as id_masterdata_origine_si,\n" +
            "soggetto_istanza.num_cellulare AS num_cellulare_si, soggetto_istanza.des_localita as des_localita_si,\n" +
            "soggetto_istanza.citta_estera_sede_legale AS citta_estera_sede_legale_si, soggetto_istanza.id_nazione_sede_legale as id_nazione_sede_legale_si,\n" +
            "soggetto_istanza.cap_residenza AS cap_residenza_si, soggetto_istanza.cap_sede_legale as cap_sede_legale_si,\n" +
            "soggetto_istanza.id_istanza_attore AS id_istanza_attore_si,\n" +
            "istanza.id_istanza AS id_istanza,\n" +
            "ruolo_compilante.id_ruolo_compilante, ruolo_compilante.cod_ruolo_compilante, ruolo_compilante.des_ruolo_compilante, adempi_ruolo.flg_modulo_delega, adempi_ruolo.flg_modulo_procura,\n" +
            "ruolo_soggetto.id_ruolo_soggetto, ruolo_soggetto.cod_ruolo_soggetto, ruolo_soggetto.des_ruolo_soggetto,\n" +
            "stato_istanza.*,\n" +
            "adempimento.id_adempimento, adempimento.cod_adempimento, adempimento.des_adempimento, adempimento.des_estesa_adempimento,\n" +
            "tipo_adempimento.id_tipo_adempimento, tipo_adempimento.cod_tipo_adempimento,tipo_adempimento.des_tipo_adempimento, tipo_adempimento.des_estesa_tipo_adempimento,\n" +
            "ambito.id_ambito, ambito.cod_ambito, ambito.des_ambito, ambito.des_estesa_ambito,\n" +
            "tipo_soggetto_si.id_tipo_soggetto AS id_tipo_soggetto_si, tipo_soggetto_si.cod_tipo_soggetto AS cod_tipo_soggetto_si, tipo_soggetto_si.des_tipo_soggetto AS des_tipo_soggetto_si,\n" +
            "tipo_natura_giuridica_si.id_tipo_natura_giuridica AS id_tipo_natura_giuridica_si, tipo_natura_giuridica_si.cod_tipo_natura_giuridica AS cod_tipo_natura_giuridica_si, tipo_natura_giuridica_si.des_tipo_natura_giuridica AS des_tipo_natura_giuridica_si, tipo_natura_giuridica_si.sigla_tipo_natura_giuridica AS sigla_tipo_natura_giuridica_si,\n" +
            "sdc_si_nasc.id_comune AS id_comune_sdc_si_nasc, sdc_si_nasc.cod_istat_comune AS cod_istat_comune_sdc_si_nasc, sdc_si_nasc.cod_belfiore_comune AS cod_belfiore_comune_sdc_si_nasc, sdc_si_nasc.denom_comune AS denom_comune_sdc_si_nasc, sdc_si_nasc.id_provincia AS id_provincia_sdc_si_nasc, sdc_si_nasc.data_inizio_validita AS data_inizio_validita_sdc_si_nasc, sdc_si_nasc.data_fine_validita AS data_fine_validita_sdc_si_nasc, sdc_si_nasc.dt_id_comune AS dt_id_comune_sdc_si_nasc, sdc_si_nasc.dt_id_comune_prev AS dt_id_comune_prev_sdc_si_nasc, sdc_si_nasc.dt_id_comune_next AS dt_id_comune_next_sdc_si_nasc, sdc_si_nasc.cap_comune AS cap_comune_sdc_si_nasc,\n" +
            "sdp_si_nasc.id_provincia AS id_provincia_sdp_si_nasc, sdp_si_nasc.cod_provincia AS cod_provincia_sdp_si_nasc, sdp_si_nasc.denom_provincia AS denom_provincia_sdp_si_nasc, sdp_si_nasc.sigla_provincia AS sigla_provincia_sdp_si_nasc, sdp_si_nasc.id_regione AS id_regione_sdp_si_nasc, sdp_si_nasc.data_inizio_validita AS data_inizio_validita_sdp_si_nasc, sdp_si_nasc.data_fine_validita AS data_fine_validita_sdp_si_nasc,\n" +
            "sdr_si_nasc.id_regione AS id_regione_sdr_si_nasc, sdr_si_nasc.cod_regione AS cod_regione_sdr_si_nasc, sdr_si_nasc.denom_regione AS denom_regione_sdr_si_nasc, sdr_si_nasc.id_nazione AS id_nazione_sdr_si_nasc, sdr_si_nasc.data_inizio_validita AS data_inizio_validita_sdr_si_nasc, sdr_si_nasc.data_fine_validita AS data_fine_validita_sdr_si_nasc,\n" +
            "sdn_si_nasc.id_nazione AS id_nazione_sdn_si_nasc, sdn_si_nasc.cod_istat_nazione AS cod_istat_nazione_sdn_si_nasc, sdn_si_nasc.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_si_nasc, sdn_si_nasc.denom_nazione AS denom_nazione_sdn_si_nasc, sdn_si_nasc.data_inizio_validita AS data_inizio_validita_sdn_si_nasc, sdn_si_nasc.data_fine_validita AS data_fine_validita_sdn_si_nasc, sdn_si_nasc.dt_id_stato AS dt_id_stato_sdn_si_nasc, sdn_si_nasc.dt_id_stato_prev AS dt_id_stato_prev_sdn_si_nasc, sdn_si_nasc.dt_id_stato_next AS dt_id_stato_next_sdn_si_nasc, sdn_si_nasc.id_origine AS id_origine_sdn_si_nasc,\n" +
            "sdc_si_res.id_comune AS id_comune_sdc_si_res, sdc_si_res.cod_istat_comune AS cod_istat_comune_sdc_si_res, sdc_si_res.cod_belfiore_comune AS cod_belfiore_comune_sdc_si_res, sdc_si_res.denom_comune AS denom_comune_sdc_si_res, sdc_si_res.id_provincia AS id_provincia_sdc_si_res, sdc_si_res.data_inizio_validita AS data_inizio_validita_sdc_si_res, sdc_si_res.data_fine_validita AS data_fine_validita_sdc_si_res, sdc_si_res.dt_id_comune AS dt_id_comune_sdc_si_res, sdc_si_res.dt_id_comune_prev AS dt_id_comune_prev_sdc_si_res, sdc_si_res.dt_id_comune_next AS dt_id_comune_next_sdc_si_res, sdc_si_res.cap_comune AS cap_comune_sdc_si_res,\n" +
            "sdp_si_res.id_provincia AS id_provincia_sdp_si_res, sdp_si_res.cod_provincia AS cod_provincia_sdp_si_res, sdp_si_res.denom_provincia AS denom_provincia_sdp_si_res, sdp_si_res.sigla_provincia AS sigla_provincia_sdp_si_res, sdp_si_res.id_regione AS id_regione_sdp_si_res, sdp_si_res.data_inizio_validita AS data_inizio_validita_sdp_si_res, sdp_si_res.data_fine_validita AS data_fine_validita_sdp_si_res,\n" +
            "sdr_si_res.id_regione AS id_regione_sdr_si_res, sdr_si_res.cod_regione AS cod_regione_sdr_si_res, sdr_si_res.denom_regione AS denom_regione_sdr_si_res, sdr_si_res.id_nazione AS id_nazione_sdr_si_res, sdr_si_res.data_inizio_validita AS data_inizio_validita_sdr_si_res, sdr_si_res.data_fine_validita AS data_fine_validita_sdr_si_res,\n" +
            "sdn_si_res.id_nazione AS id_nazione_sdn_si_res, sdn_si_res.cod_istat_nazione AS cod_istat_nazione_sdn_si_res, sdn_si_res.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_si_res, sdn_si_res.denom_nazione AS denom_nazione_sdn_si_res, sdn_si_res.data_inizio_validita AS data_inizio_validita_sdn_si_res, sdn_si_res.data_fine_validita AS data_fine_validita_sdn_si_res, sdn_si_res.dt_id_stato AS dt_id_stato_sdn_si_res, sdn_si_res.dt_id_stato_prev AS dt_id_stato_prev_sdn_si_res, sdn_si_res.dt_id_stato_next AS dt_id_stato_next_sdn_si_res, sdn_si_res.id_origine AS id_origine_sdn_si_res,\n" +
            "sdc_si_sl.id_comune AS id_comune_sdc_si_sl, sdc_si_sl.cod_istat_comune AS cod_istat_comune_sdc_si_sl, sdc_si_sl.cod_belfiore_comune AS cod_belfiore_comune_sdc_si_sl, sdc_si_sl.denom_comune AS denom_comune_sdc_si_sl, sdc_si_sl.id_provincia AS id_provincia_sdc_si_sl, sdc_si_sl.data_inizio_validita AS data_inizio_validita_sdc_si_sl, sdc_si_sl.data_fine_validita AS data_fine_validita_sdc_si_sl, sdc_si_sl.dt_id_comune AS dt_id_comune_sdc_si_sl, sdc_si_sl.dt_id_comune_prev AS dt_id_comune_prev_sdc_si_sl, sdc_si_sl.dt_id_comune_next AS dt_id_comune_next_sdc_si_sl, sdc_si_sl.cap_comune AS cap_comune_sdc_si_sl,\n" +
            "sdp_si_sl.id_provincia AS id_provincia_sdp_si_sl, sdp_si_sl.cod_provincia AS cod_provincia_sdp_si_sl, sdp_si_sl.denom_provincia AS denom_provincia_sdp_si_sl, sdp_si_sl.sigla_provincia AS sigla_provincia_sdp_si_sl, sdp_si_sl.id_regione AS id_regione_sdp_si_sl, sdp_si_sl.data_inizio_validita AS data_inizio_validita_sdp_si_sl, sdp_si_sl.data_fine_validita AS data_fine_validita_sdp_si_sl,\n" +
            "sdr_si_sl.id_regione AS id_regione_sdr_si_sl, sdr_si_sl.cod_regione AS cod_regione_sdr_si_sl, sdr_si_sl.denom_regione AS denom_regione_sdr_si_sl, sdr_si_sl.id_nazione AS id_nazione_sdr_si_sl, sdr_si_sl.data_inizio_validita AS data_inizio_validita_sdr_si_sl, sdr_si_sl.data_fine_validita AS data_fine_validita_sdr_si_sl,\n" +
            "sdn_si_sl.id_nazione AS id_nazione_sdn_si_sl, sdn_si_sl.cod_istat_nazione AS cod_istat_nazione_sdn_si_sl, sdn_si_sl.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_si_sl, sdn_si_sl.denom_nazione AS denom_nazione_sdn_si_sl, sdn_si_sl.data_inizio_validita AS data_inizio_validita_sdn_si_sl, sdn_si_sl.data_fine_validita AS data_fine_validita_sdn_si_sl, sdn_si_sl.dt_id_stato AS dt_id_stato_sdn_si_sl, sdn_si_sl.dt_id_stato_prev AS dt_id_stato_prev_sdn_si_sl, sdn_si_sl.dt_id_stato_next AS dt_id_stato_next_sdn_si_sl, sdn_si_sl.id_origine AS id_origine_sdn_si_sl,\n" +
            "sdn_si_sle.id_nazione AS id_nazione_sdn_si_sle, sdn_si_sle.cod_istat_nazione AS cod_istat_nazione_sdn_si_sle, sdn_si_sle.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_si_sle, sdn_si_sle.denom_nazione AS denom_nazione_sdn_si_sle, sdn_si_sle.data_inizio_validita AS data_inizio_validita_sdn_si_sle, sdn_si_sle.data_fine_validita AS data_fine_validita_sdn_si_sle, sdn_si_sle.dt_id_stato AS dt_id_stato_sdn_si_sle, sdn_si_sle.dt_id_stato_prev AS dt_id_stato_prev_sdn_si_sle, sdn_si_sle.dt_id_stato_next AS dt_id_stato_next_sdn_si_sle, sdn_si_sle.id_origine AS id_origine_sdn_si_sle,\n" +
            "soggetto_f.id_soggetto AS id_soggetto_f, soggetto_f.cf_soggetto AS cf_soggetto_f, soggetto_f.partita_iva_soggetto AS partita_iva_soggetto_f, soggetto_f.den_soggetto AS den_soggetto_f, soggetto_f.data_cessazione_soggetto AS data_cessazione_soggetto_f, soggetto_f.nome AS nome_f, soggetto_f.cognome AS cognome_f, soggetto_f.data_nascita_soggetto AS data_nascita_soggetto_f, soggetto_f.citta_estera_nascita AS citta_estera_nascita_f, soggetto_f.num_telefono AS num_telefono_f, soggetto_f.des_email AS des_email_f, soggetto_f.des_pec AS des_pec_f, soggetto_f.indirizzo_soggetto AS indirizzo_soggetto_f, soggetto_f.num_civico_indirizzo AS num_civico_indirizzo_f, soggetto_f.citta_estera_residenza AS citta_estera_residenza_f, soggetto_f.den_provincia_cciaa AS den_provincia_cciaa_f, soggetto_f.den_anno_cciaa AS den_anno_cciaa_f, soggetto_f.den_numero_cciaa AS den_numero_cciaa_f, soggetto_f.gest_data_ins AS gest_data_ins_f, soggetto_f.gest_attore_ins AS gest_attore_ins_f, soggetto_f.gest_data_upd AS gest_data_upd_f, soggetto_f.gest_attore_upd AS gest_attore_upd_f, soggetto_f.id_masterdata as id_masterdata_f, soggetto_f.id_masterdata_origine as id_masterdata_origine_f, soggetto_f.gest_uid AS gest_uid_f, soggetto_f.num_cellulare AS num_cellulare_f, soggetto_f.des_localita AS des_localita_f, soggetto_f.citta_estera_sede_legale AS citta_estera_sede_legale_f, soggetto_f.id_nazione_sede_legale AS id_nazione_sede_legale_f, soggetto_f.cap_residenza AS cap_residenza_f, soggetto_f.cap_sede_legale AS cap_sede_legale_f,\n" +
            "tipo_soggetto_f.id_tipo_soggetto AS id_tipo_soggetto_f, tipo_soggetto_f.cod_tipo_soggetto AS cod_tipo_soggetto_f, tipo_soggetto_f.des_tipo_soggetto AS des_tipo_soggetto_f,\n" +
            "tipo_natura_giuridica_f.id_tipo_natura_giuridica AS id_tipo_natura_giuridica_f, tipo_natura_giuridica_f.cod_tipo_natura_giuridica AS cod_tipo_natura_giuridica_f, tipo_natura_giuridica_f.des_tipo_natura_giuridica AS des_tipo_natura_giuridica_f, tipo_natura_giuridica_f.sigla_tipo_natura_giuridica AS sigla_tipo_natura_giuridica_f,\n" +
            "sdc_f_nasc.id_comune AS id_comune_sdc_f_nasc, sdc_f_nasc.cod_istat_comune AS cod_istat_comune_sdc_f_nasc, sdc_f_nasc.cod_belfiore_comune AS cod_belfiore_comune_sdc_f_nasc, sdc_f_nasc.denom_comune AS denom_comune_sdc_f_nasc, sdc_f_nasc.id_provincia AS id_provincia_sdc_f_nasc, sdc_f_nasc.data_inizio_validita AS data_inizio_validita_sdc_f_nasc, sdc_f_nasc.data_fine_validita AS data_fine_validita_sdc_f_nasc, sdc_f_nasc.dt_id_comune AS dt_id_comune_sdc_f_nasc, sdc_f_nasc.dt_id_comune_prev AS dt_id_comune_prev_sdc_f_nasc, sdc_f_nasc.dt_id_comune_next AS dt_id_comune_next_sdc_f_nasc, sdc_f_nasc.cap_comune AS cap_comune_sdc_f_nasc,\n" +
            "sdp_f_nasc.id_provincia AS id_provincia_sdp_f_nasc, sdp_f_nasc.cod_provincia AS cod_provincia_sdp_f_nasc, sdp_f_nasc.denom_provincia AS denom_provincia_sdp_f_nasc, sdp_f_nasc.sigla_provincia AS sigla_provincia_sdp_f_nasc, sdp_f_nasc.id_regione AS id_regione_sdp_f_nasc, sdp_f_nasc.data_inizio_validita AS data_inizio_validita_sdp_f_nasc, sdp_f_nasc.data_fine_validita AS data_fine_validita_sdp_f_nasc,\n" +
            "sdr_f_nasc.id_regione AS id_regione_sdr_f_nasc, sdr_f_nasc.cod_regione AS cod_regione_sdr_f_nasc, sdr_f_nasc.denom_regione AS denom_regione_sdr_f_nasc, sdr_f_nasc.id_nazione AS id_nazione_sdr_f_nasc, sdr_f_nasc.data_inizio_validita AS data_inizio_validita_sdr_f_nasc, sdr_f_nasc.data_fine_validita AS data_fine_validita_sdr_f_nasc,\n" +
            "sdn_f_nasc.id_nazione AS id_nazione_sdn_f_nasc, sdn_f_nasc.cod_istat_nazione AS cod_istat_nazione_sdn_f_nasc, sdn_f_nasc.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_f_nasc, sdn_f_nasc.denom_nazione AS denom_nazione_sdn_f_nasc, sdn_f_nasc.data_inizio_validita AS data_inizio_validita_sdn_f_nasc, sdn_f_nasc.data_fine_validita AS data_fine_validita_sdn_f_nasc, sdn_f_nasc.dt_id_stato AS dt_id_stato_sdn_f_nasc, sdn_f_nasc.dt_id_stato_prev AS dt_id_stato_prev_sdn_f_nasc, sdn_f_nasc.dt_id_stato_next AS dt_id_stato_next_sdn_f_nasc, sdn_f_nasc.id_origine AS id_origine_sdn_f_nasc,\n" +
            "sdc_f_res.id_comune AS id_comune_sdc_f_res, sdc_f_res.cod_istat_comune AS cod_istat_comune_sdc_f_res, sdc_f_res.cod_belfiore_comune AS cod_belfiore_comune_sdc_f_res, sdc_f_res.denom_comune AS denom_comune_sdc_f_res, sdc_f_res.id_provincia AS id_provincia_sdc_f_res, sdc_f_res.data_inizio_validita AS data_inizio_validita_sdc_f_res, sdc_f_res.data_fine_validita AS data_fine_validita_sdc_f_res, sdc_f_res.dt_id_comune AS dt_id_comune_sdc_f_res, sdc_f_res.dt_id_comune_prev AS dt_id_comune_prev_sdc_f_res, sdc_f_res.dt_id_comune_next AS dt_id_comune_next_sdc_f_res, sdc_f_res.cap_comune AS cap_comune_sdc_f_res,\n" +
            "sdp_f_res.id_provincia AS id_provincia_sdp_f_res, sdp_f_res.cod_provincia AS cod_provincia_sdp_f_res, sdp_f_res.denom_provincia AS denom_provincia_sdp_f_res, sdp_f_res.sigla_provincia AS sigla_provincia_sdp_f_res, sdp_f_res.id_regione AS id_regione_sdp_f_res, sdp_f_res.data_inizio_validita AS data_inizio_validita_sdp_f_res, sdp_f_res.data_fine_validita AS data_fine_validita_sdp_f_res,\n" +
            "sdr_f_res.id_regione AS id_regione_sdr_f_res, sdr_f_res.cod_regione AS cod_regione_sdr_f_res, sdr_f_res.denom_regione AS denom_regione_sdr_f_res, sdr_f_res.id_nazione AS id_nazione_sdr_f_res, sdr_f_res.data_inizio_validita AS data_inizio_validita_sdr_f_res, sdr_f_res.data_fine_validita AS data_fine_validita_sdr_f_res,\n" +
            "sdn_f_res.id_nazione AS id_nazione_sdn_f_res, sdn_f_res.cod_istat_nazione AS cod_istat_nazione_sdn_f_res, sdn_f_res.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_f_res, sdn_f_res.denom_nazione AS denom_nazione_sdn_f_res, sdn_f_res.data_inizio_validita AS data_inizio_validita_sdn_f_res, sdn_f_res.data_fine_validita AS data_fine_validita_sdn_f_res, sdn_f_res.dt_id_stato AS dt_id_stato_sdn_f_res, sdn_f_res.dt_id_stato_prev AS dt_id_stato_prev_sdn_f_res, sdn_f_res.dt_id_stato_next AS dt_id_stato_next_sdn_f_res, sdn_f_res.id_origine AS id_origine_sdn_f_res,\n" +
            "sdc_f_sl.id_comune AS id_comune_sdc_f_sl, sdc_f_sl.cod_istat_comune AS cod_istat_comune_sdc_f_sl, sdc_f_sl.cod_belfiore_comune AS cod_belfiore_comune_sdc_f_sl, sdc_f_sl.denom_comune AS denom_comune_sdc_f_sl, sdc_f_sl.id_provincia AS id_provincia_sdc_f_sl, sdc_f_sl.data_inizio_validita AS data_inizio_validita_sdc_f_sl, sdc_f_sl.data_fine_validita AS data_fine_validita_sdc_f_sl, sdc_f_sl.dt_id_comune AS dt_id_comune_sdc_f_sl, sdc_f_sl.dt_id_comune_prev AS dt_id_comune_prev_sdc_f_sl, sdc_f_sl.dt_id_comune_next AS dt_id_comune_next_sdc_f_sl, sdc_f_sl.cap_comune AS cap_comune_sdc_f_sl,\n" +
            "sdp_f_sl.id_provincia AS id_provincia_sdp_f_sl, sdp_f_sl.cod_provincia AS cod_provincia_sdp_f_sl, sdp_f_sl.denom_provincia AS denom_provincia_sdp_f_sl, sdp_f_sl.sigla_provincia AS sigla_provincia_sdp_f_sl, sdp_f_sl.id_regione AS id_regione_sdp_f_sl, sdp_f_sl.data_inizio_validita AS data_inizio_validita_sdp_f_sl, sdp_f_sl.data_fine_validita AS data_fine_validita_sdp_f_sl,\n" +
            "sdr_f_sl.id_regione AS id_regione_sdr_f_sl, sdr_f_sl.cod_regione AS cod_regione_sdr_f_sl, sdr_f_sl.denom_regione AS denom_regione_sdr_f_sl, sdr_f_sl.id_nazione AS id_nazione_sdr_f_sl, sdr_f_sl.data_inizio_validita AS data_inizio_validita_sdr_f_sl, sdr_f_sl.data_fine_validita AS data_fine_validita_sdr_f_sl,\n" +
            "sdn_f_sl.id_nazione AS id_nazione_sdn_f_sl, sdn_f_sl.cod_istat_nazione AS cod_istat_nazione_sdn_f_sl, sdn_f_sl.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_f_sl, sdn_f_sl.denom_nazione AS denom_nazione_sdn_f_sl, sdn_f_sl.data_inizio_validita AS data_inizio_validita_sdn_f_sl, sdn_f_sl.data_fine_validita AS data_fine_validita_sdn_f_sl, sdn_f_sl.dt_id_stato AS dt_id_stato_sdn_f_sl, sdn_f_sl.dt_id_stato_prev AS dt_id_stato_prev_sdn_f_sl, sdn_f_sl.dt_id_stato_next AS dt_id_stato_next_sdn_f_sl, sdn_f_sl.id_origine AS id_origine_sdn_f_sl,\n" +
            "sdn_f_sle.id_nazione AS id_nazione_sdn_f_sle, sdn_f_sle.cod_istat_nazione AS cod_istat_nazione_sdn_f_sle, sdn_f_sle.cod_belfiore_nazione AS cod_belfiore_nazione_sdn_f_sle, sdn_f_sle.denom_nazione AS denom_nazione_sdn_f_sle, sdn_f_sle.data_inizio_validita AS data_inizio_validita_sdn_f_sle, sdn_f_sle.data_fine_validita AS data_fine_validita_sdn_f_sle, sdn_f_sle.dt_id_stato AS dt_id_stato_sdn_f_sle, sdn_f_sle.dt_id_stato_prev AS dt_id_stato_prev_sdn_f_sle, sdn_f_sle.dt_id_stato_next AS dt_id_stato_next_sdn_f_sle, sdn_f_sle.id_origine AS id_origine_sdn_f_sle\n" +
            ", srsg.*, srsg.gest_uid AS gest_uid_srsg\n" +
            ", stgs.*, stgs.gest_uid AS gest_uid_stgs\n" +
            ", sdnn_si.id_nazione AS id_nazione_sdnn_si, sdnn_si.cod_istat_nazione AS cod_istat_nazione_sdnn_si, sdnn_si.cod_belfiore_nazione AS cod_belfiore_nazione_sdnn_si, sdnn_si.denom_nazione AS denom_nazione_sdnn_si, sdnn_si.data_inizio_validita AS data_inizio_validita_sdnn_si, sdnn_si.data_fine_validita AS data_fine_validita_sdnn_si, sdnn_si.dt_id_stato AS dt_id_stato_sdnn_si, sdnn_si.dt_id_stato_prev AS dt_id_stato_prev_sdnn_si, sdnn_si.dt_id_stato_next AS dt_id_stato_next_sdnn_si, sdnn_si.id_origine AS id_origine_sdnn_si\n" +
            ", sdnr_si.id_nazione AS id_nazione_sdnr_si, sdnr_si.cod_istat_nazione AS cod_istat_nazione_sdnr_si, sdnr_si.cod_belfiore_nazione AS cod_belfiore_nazione_sdnr_si, sdnr_si.denom_nazione AS denom_nazione_sdnr_si, sdnr_si.data_inizio_validita AS data_inizio_validita_sdnr_si, sdnr_si.data_fine_validita AS data_fine_validita_sdnr_si, sdnr_si.dt_id_stato AS dt_id_stato_sdnr_si, sdnr_si.dt_id_stato_prev AS dt_id_stato_prev_sdnr_si, sdnr_si.dt_id_stato_next AS dt_id_stato_next_sdnr_si, sdnr_si.id_origine AS id_origine_sdnr_si\n" +
            ", sdns_si.id_nazione AS id_nazione_sdns_si, sdns_si.cod_istat_nazione AS cod_istat_nazione_sdns_si, sdns_si.cod_belfiore_nazione AS cod_belfiore_nazione_sdns_si, sdns_si.denom_nazione AS denom_nazione_sdns_si, sdns_si.data_inizio_validita AS data_inizio_validita_sdns_si, sdns_si.data_fine_validita AS data_fine_validita_sdns_si, sdns_si.dt_id_stato AS dt_id_stato_sdns_si, sdns_si.dt_id_stato_prev AS dt_id_stato_prev_sdns_si, sdns_si.dt_id_stato_next AS dt_id_stato_next_sdns_si, sdns_si.id_origine AS id_origine_sdns_si\n" +
            ", sdnn_so.id_nazione AS id_nazione_sdnn_so, sdnn_so.cod_istat_nazione AS cod_istat_nazione_sdnn_so, sdnn_so.cod_belfiore_nazione AS cod_belfiore_nazione_sdnn_so, sdnn_so.denom_nazione AS denom_nazione_sdnn_so, sdnn_so.data_inizio_validita AS data_inizio_validita_sdnn_so, sdnn_so.data_fine_validita AS data_fine_validita_sdnn_so, sdnn_so.dt_id_stato AS dt_id_stato_sdnn_so, sdnn_so.dt_id_stato_prev AS dt_id_stato_prev_sdnn_so, sdnn_so.dt_id_stato_next AS dt_id_stato_next_sdnn_so, sdnn_so.id_origine AS id_origine_sdnn_so\n" +
            ", sdnr_so.id_nazione AS id_nazione_sdnr_so, sdnr_so.cod_istat_nazione AS cod_istat_nazione_sdnr_so, sdnr_so.cod_belfiore_nazione AS cod_belfiore_nazione_sdnr_so, sdnr_so.denom_nazione AS denom_nazione_sdnr_so, sdnr_so.data_inizio_validita AS data_inizio_validita_sdnr_so, sdnr_so.data_fine_validita AS data_fine_validita_sdnr_so, sdnr_so.dt_id_stato AS dt_id_stato_sdnr_so, sdnr_so.dt_id_stato_prev AS dt_id_stato_prev_sdnr_so, sdnr_so.dt_id_stato_next AS dt_id_stato_next_sdnr_so, sdnr_so.id_origine AS id_origine_sdnr_so\n" +
            ", sdns_so.id_nazione AS id_nazione_sdns_so, sdns_so.cod_istat_nazione AS cod_istat_nazione_sdns_so, sdns_so.cod_belfiore_nazione AS cod_belfiore_nazione_sdns_so, sdns_so.denom_nazione AS denom_nazione_sdns_so, sdns_so.data_inizio_validita AS data_inizio_validita_sdns_so, sdns_so.data_fine_validita AS data_fine_validita_sdns_so, sdns_so.dt_id_stato AS dt_id_stato_sdns_so, sdns_so.dt_id_stato_prev AS dt_id_stato_prev_sdns_so, sdns_so.dt_id_stato_next AS dt_id_stato_next_sdns_so, sdns_so.id_origine AS id_origine_sdns_so\n" +
            "FROM _replaceTableName_ soggetto_istanza\n" +
            "INNER JOIN scriva_t_istanza istanza ON soggetto_istanza.id_istanza = istanza.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza stato_istanza ON istanza.id_stato_istanza = stato_istanza.id_stato_istanza\n" +
            "INNER JOIN scriva_d_adempimento adempimento ON istanza.id_adempimento = adempimento.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_adempimento tipo_adempimento ON adempimento.id_tipo_adempimento = tipo_adempimento.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito ambito ON tipo_adempimento.id_ambito = ambito.id_ambito\n" +
            "INNER JOIN scriva_d_ruolo_compilante ruolo_compilante ON soggetto_istanza.id_ruolo_compilante = ruolo_compilante.id_ruolo_compilante\n" +
            "INNER JOIN scriva_r_adempi_ruolo_compila adempi_ruolo ON adempi_ruolo.id_ruolo_compilante = soggetto_istanza.id_ruolo_compilante\n" +
            "LEFT JOIN scriva_d_ruolo_soggetto ruolo_soggetto ON soggetto_istanza.id_ruolo_soggetto = ruolo_soggetto.id_ruolo_soggetto\n" +
            "INNER JOIN scriva_d_tipo_soggetto tipo_soggetto_si ON soggetto_istanza.id_tipo_soggetto = tipo_soggetto_si.id_tipo_soggetto\n " +
            "LEFT JOIN scriva_d_tipo_natura_giuridica tipo_natura_giuridica_si ON soggetto_istanza.id_tipo_natura_giuridica = tipo_natura_giuridica_si.id_tipo_natura_giuridica\n" +
            "LEFT JOIN scriva_d_comune sdc_si_nasc ON sdc_si_nasc.id_comune = soggetto_istanza.id_comune_nascita\n" +
            "LEFT JOIN scriva_d_provincia sdp_si_nasc ON sdp_si_nasc.id_provincia = sdc_si_nasc.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_si_nasc ON sdr_si_nasc.id_regione = sdp_si_nasc.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_si_nasc ON sdn_si_nasc.id_nazione = sdr_si_nasc.id_nazione\n" +
            "LEFT JOIN scriva_d_comune sdc_si_res ON sdc_si_res.id_comune = soggetto_istanza.id_comune_residenza\n" +
            "LEFT JOIN scriva_d_provincia sdp_si_res ON sdp_si_res.id_provincia = sdc_si_res.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_si_res ON sdr_si_res.id_regione = sdp_si_res.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_si_res ON sdn_si_res.id_nazione = sdr_si_res.id_nazione\n" +
            "LEFT JOIN scriva_d_comune sdc_si_sl ON sdc_si_sl.id_comune = soggetto_istanza.id_comune_sede_legale\n" +
            "LEFT JOIN scriva_d_provincia sdp_si_sl ON sdp_si_sl.id_provincia = sdc_si_sl.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_si_sl ON sdr_si_sl.id_regione = sdp_si_sl.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_si_sl ON sdn_si_sl.id_nazione = sdr_si_sl.id_nazione\n" +
            "LEFT JOIN scriva_d_nazione sdn_si_sle ON sdn_si_sle.id_nazione = soggetto_istanza.id_nazione_sede_legale\n" +
            "INNER JOIN scriva_t_soggetto soggetto_f ON soggetto_istanza.id_soggetto = soggetto_f.id_soggetto\n" +
            "INNER JOIN scriva_d_tipo_soggetto tipo_soggetto_f ON soggetto_f.id_tipo_soggetto = tipo_soggetto_f.id_tipo_soggetto\n" +
            "LEFT JOIN scriva_d_tipo_natura_giuridica tipo_natura_giuridica_f ON soggetto_f.id_tipo_natura_giuridica = tipo_natura_giuridica_f.id_tipo_natura_giuridica\n" +
            "LEFT JOIN scriva_d_comune sdc_f_nasc ON sdc_f_nasc.id_comune = soggetto_f.id_comune_nascita\n" +
            "LEFT JOIN scriva_d_provincia sdp_f_nasc ON sdp_f_nasc.id_provincia = sdc_f_nasc.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_f_nasc ON sdr_f_nasc.id_regione = sdp_f_nasc.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_f_nasc ON sdn_f_nasc.id_nazione = sdr_f_nasc.id_nazione\n" +
            "LEFT JOIN scriva_d_comune sdc_f_res ON sdc_f_res.id_comune = soggetto_f.id_comune_residenza\n" +
            "LEFT JOIN scriva_d_provincia sdp_f_res ON sdp_f_res.id_provincia = sdc_f_res.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_f_res ON sdr_f_res.id_regione = sdp_f_res.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_f_res ON sdn_f_res.id_nazione = sdr_f_res.id_nazione\n" +
            "LEFT JOIN scriva_d_comune sdc_f_sl ON sdc_f_sl.id_comune = soggetto_f.id_comune_sede_legale\n" +
            "LEFT JOIN scriva_d_provincia sdp_f_sl ON sdp_f_sl.id_provincia = sdc_f_sl.id_provincia\n" +
            "LEFT JOIN scriva_d_regione sdr_f_sl ON sdr_f_sl.id_regione = sdp_f_sl.id_regione\n" +
            "LEFT JOIN scriva_d_nazione sdn_f_sl ON sdn_f_sl.id_nazione = sdr_f_sl.id_nazione\n" +
            "LEFT JOIN scriva_d_nazione sdn_f_sle ON sdn_f_sle.id_nazione = soggetto_f.id_nazione_sede_legale\n" +
            "LEFT JOIN scriva_r_soggetto_gruppo srsg ON srsg.id_soggetto_istanza = soggetto_istanza.id_soggetto_istanza\n" +
            "LEFT JOIN scriva_t_gruppo_soggetto stgs ON stgs.id_gruppo_soggetto = srsg.id_gruppo_soggetto\n" +
            "LEFT JOIN scriva_d_nazione sdnr_si ON sdnr_si.id_nazione = soggetto_istanza.id_nazione_residenza\n" +
            "LEFT JOIN scriva_d_nazione sdnn_si ON sdnn_si.id_nazione = soggetto_istanza.id_nazione_nascita\n" +
            "LEFT JOIN scriva_d_nazione sdns_si ON sdns_si.id_nazione = soggetto_istanza.id_nazione_sede_legale\n" +
            "LEFT JOIN scriva_d_nazione sdnr_so ON sdnr_so.id_nazione = soggetto_f.id_nazione_residenza\n" +
            "LEFT JOIN scriva_d_nazione sdnn_so ON sdnn_so.id_nazione = soggetto_f.id_nazione_nascita\n" +
            "LEFT JOIN scriva_d_nazione sdns_so ON sdns_so.id_nazione = soggetto_f.id_nazione_sede_legale\n" +
            "WHERE adempi_ruolo.id_adempimento = istanza.id_adempimento\n";

    private static final String QUERY_PRIMARY_KEY = "select * from _replaceTableName_ WHERE id_soggetto_istanza = :idSoggettoIstanza";

    private static final String QUERY_LOAD_SOGGETTO_ISTANZA_BY_ID = QUERY_LOAD_SOGGETTI_ISTANZE + "AND soggetto_istanza.id_soggetto_istanza = :idSoggettoIstanza";

    private static final String QUERY_LOAD_SOGGETTO_ISTANZA_BY_UID = QUERY_LOAD_SOGGETTI_ISTANZE + "AND soggetto_istanza.gest_uid = :uidSoggettoIstanza";

    private static final String QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_SOGGETTO = QUERY_LOAD_SOGGETTI_ISTANZE + "AND soggetto_istanza.id_soggetto = :idSoggetto";

    private static final String QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_SOGGETTO_PADRE = QUERY_LOAD_SOGGETTI_ISTANZE + "AND soggetto_istanza.id_soggetto_padre = :idSoggetto";

    private static final String QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_ISTANZA = QUERY_LOAD_SOGGETTI_ISTANZE + "AND soggetto_istanza.id_istanza = :idIstanza";

    private static final String QUERY_LOAD_SOGGETTI_ISTANZE_WITH_DELEGA_BY_ID_ISTANZA = "SELECT stsi.*\n" +
            "FROM _replaceTableName_ stsi\n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = stsi.id_istanza\n" +
            "INNER JOIN scriva_r_adempi_ruolo_compila srarc ON srarc.id_ruolo_compilante = stsi.id_ruolo_compilante\n" +
            "WHERE sti.id_adempimento = srarc.id_adempimento\n" +
            "AND srarc.flg_modulo_delega = 1\n" +
            "AND sti.id_istanza = :idIstanza\n";

    private static final String QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_ISTANZA_AND_CODICE_FISCALE_SOGGETTO = QUERY_LOAD_SOGGETTI_ISTANZE
            + "AND soggetto_istanza.id_istanza = :idIstanza\n"
            + "AND soggetto_istanza.cf_soggetto = :codiceFiscaleSoggetto\n";

    private static final String QUERY_LOAD_SOGGETTI_ISTANZE_BY_UID_ISTANZA_AND_CODICE_FISCALE_SOGGETTO = QUERY_LOAD_SOGGETTI_ISTANZE
            + "AND istanza.gest_uid = :uidIstanza\n "
            + "AND soggetto_istanza.cf_soggetto = :codiceFiscaleSoggetto\n";

    private static final String QUERY_DELETE_SOGGETTO_ISTANZA_BY_ID = "DELETE FROM _replaceTableName_ WHERE id_soggetto_istanza = :idSoggIst";

    private static final String QUERY_DELETE_SOGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE gest_uid = :uid";

    private static final String QUERY_LOAD_SOGGETTO_ISTANZA_BY_CODICE_FISCALE_SOGGETTO = "SELECT * FROM _replaceTableName_ WHERE cf_soggetto = :codiceFiscaleSoggetto";

    private static final String QUERY_LOAD_SOGGETTO_ISTANZA_BY_PARTITA_IVA_SOGGETTO = "SELECT * FROM _replaceTableName_ WHERE partita_iva_soggetto = :partitaIvaSoggetto";

    private static final String QUERY_INSERT_SOGGETTO_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_soggetto_istanza, id_soggetto, id_istanza, id_ruolo_compilante, id_soggetto_padre, id_ruolo_soggetto, cf_soggetto\n" +
            ", id_tipo_soggetto, id_tipo_natura_giuridica, id_comune_nascita, id_comune_residenza, id_comune_sede_legale, partita_iva_soggetto\n" +
            ", den_soggetto, data_cessazione_soggetto, nome, cognome, data_nascita_soggetto, citta_estera_nascita, num_telefono, des_email, des_pec\n" +
            ", indirizzo_soggetto, num_civico_indirizzo, citta_estera_residenza, den_provincia_cciaa, den_anno_cciaa, den_numero_cciaa\n" +
            ", num_cellulare, id_nazione_nascita, id_nazione_residenza, des_localita\n" +
            ", citta_estera_sede_legale, id_nazione_sede_legale, cap_residenza, cap_sede_legale\n" +
            ", gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, id_masterdata, id_masterdata_origine, gest_uid, id_istanza_attore, id_funzionario)\n" +
            "VALUES(nextval('seq_scriva_t_soggetto_istanza'), :idSoggetto, :idIstanza, :idRuoloCompil, :idSoggettoPadre, :idRuoloSoggetto, :cf\n" +
            ", :idTipoSogg, :idTipoNatGiur, :idComuneNasc, :idComuneRes, :idComuneSl, :piva\n" +
            ", :denomSogg, :dataCessazSogg, :nome, :cognome, :dataNascSogg, :cittaEsteraNasc, :numTel, :email, :pec\n" +
            ", :indSogg, :numCivInd, :cittaEsteraRes, :denomProvCCIAA, :denomAnnoCCIAA, :denomNumCCIAA\n" +
            ", :numCellulare, :idNazioneNascita, :idNazioneResidenza, :desLocalita\n" +
            ", :cittaEsteraSedeLegale, :idNazioneSedeLegale, :capResidenza, :capSedeLegale\n" +
            ", :dateIns, :attoreIns, :dateUpd, :attoreUpd, :idMasterdata, :idMasterdataOrigine, :uid, :idIstanzaAttore, :idFunzionario)";

    private static final String QUERY_INSERT_STORICO_SOGGETTO_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_soggetto_istanza_storico, id_soggetto_istanza, id_soggetto, id_istanza, id_ruolo_compilante, id_soggetto_padre, id_ruolo_soggetto, cf_soggetto\n" +
            ", id_tipo_soggetto, id_tipo_natura_giuridica, id_comune_nascita, id_comune_residenza, id_comune_sede_legale, partita_iva_soggetto\n" +
            ", den_soggetto, data_cessazione_soggetto, nome, cognome, data_nascita_soggetto, citta_estera_nascita, num_telefono, des_email, des_pec\n" +
            ", indirizzo_soggetto, num_civico_indirizzo, citta_estera_residenza, den_provincia_cciaa, den_anno_cciaa, den_numero_cciaa\n" +
            ", num_cellulare, id_nazione_nascita, id_nazione_residenza, des_localita\n" +
            ", citta_estera_sede_legale, id_nazione_sede_legale, cap_residenza, cap_sede_legale\n" +
            ", gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, id_masterdata, id_masterdata_origine, gest_uid, id_istanza_attore, id_funzionario)\n" +
            "VALUES(nextval('seq_scriva_s_soggetto_istanza'), :idSoggIst, :idSoggetto, :idIstanza, :idRuoloCompil, :idSoggettoPadre, :idRuoloSoggetto, :cf\n" +
            ", :idTipoSogg, :idTipoNatGiur, :idComuneNasc, :idComuneRes, :idComuneSl, :piva\n" +
            ", :denomSogg, :dataCessazSogg, :nome, :cognome, :dataNascSogg, :cittaEsteraNasc, :numTel, :email, :pec\n" +
            ", :indSogg, :numCivInd, :cittaEsteraRes, :denomProvCCIAA, :denomAnnoCCIAA, :denomNumCCIAA\n" +
            ", :numCellulare, :idNazioneNascita, :idNazioneResidenza, :desLocalita\n" +
            ", :cittaEsteraSedeLegale, :idNazioneSedeLegale, :capResidenza, :capSedeLegale\n" +
            ", :dateIns, :attoreIns, :dateUpd, :attoreUpd, :idMasterdata, :idMasterdataOrigine, :uid, :idIstanzaAttore, :idFunzionario)\n";

    private static final String QUERY_UPDATE_SOGGETTO_ISTANZA = "UPDATE _replaceTableName_\n" +
            "SET id_soggetto = :idSoggetto, id_istanza = :idIstanza, id_ruolo_compilante = :idRuoloCompil, id_soggetto_padre = :idSoggettoPadre, id_ruolo_soggetto = :idRuoloSoggetto\n" +
            ", cf_soggetto = :cf, id_tipo_soggetto = :idTipoSogg, id_tipo_natura_giuridica = :idTipoNatGiur, id_comune_nascita = :idComuneNasc, id_comune_residenza = :idComuneRes\n" +
            ", id_comune_sede_legale = :idComuneSl, partita_iva_soggetto = :piva, den_soggetto = :denomSogg, data_cessazione_soggetto = :dataCessazSogg, nome = :nome, cognome = :cognome\n" +
            ", data_nascita_soggetto = :dataNascSogg, citta_estera_nascita = :cittaEsteraNasc, num_telefono = :numTel, des_email = :email, des_pec = :pec, indirizzo_soggetto = :indSogg\n" +
            ", num_civico_indirizzo = :numCivInd, citta_estera_residenza = :cittaEsteraRes, den_provincia_cciaa = :denomProvCCIAA, den_anno_cciaa = :denomAnnoCCIAA, den_numero_cciaa = :denomNumCCIAA\n" +
            ", num_cellulare = :numCellulare, id_nazione_nascita = :idNazioneNascita, id_nazione_residenza = :idNazioneResidenza, des_localita = :desLocalita\n" +
            ", citta_estera_sede_legale = :cittaEsteraSedeLegale, id_nazione_sede_legale = :idNazioneSedeLegale, cap_residenza = :capResidenza, cap_sede_legale = :capSedeLegale\n" +
            ", gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd, id_masterdata = :idMasterdata, id_istanza_attore = :idIstanzaAttore, id_funzionario = :idFunzionario\n" +
            "WHERE id_soggetto_istanza = :idSoggIst\n";

    private static final String QUERY_UPDATE_ISTANZA_ATTORE = "UPDATE _replaceTableName_\n" +
            "SET id_istanza_attore = :idIstanzaAttore, gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n " +
            "WHERE id_soggetto_istanza = :idSoggettoIstanza\n";

    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(adempi_ruolo.data_inizio_validita) <= DATE(NOW()) AND (adempi_ruolo.data_fine_validita IS NULL OR DATE(adempi_ruolo.data_fine_validita)>= DATE(NOW())))\n ";
    
    /**
     * Load soggetti i stanze list.
     *
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettiIstanze() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_ISTANZE + WHERE_DATA_INIZIO_FINE, null);
    }

    /**
     * Load soggetto istanza list.
     *
     * @param id idSoggettoIstanza
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanza(Long id) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggettoIstanza", id);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_ISTANZA_BY_ID, map);
    }

    /**
     * Load soggetto istanza by uid list.
     *
     * @param uid uidSoggettoIstanza
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByUid(String uid) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uidSoggettoIstanza", uid);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_ISTANZA_BY_UID, map);
    }

    /**
     * Load soggetto istanza by id soggetto list.
     *
     * @param idSoggetto idSoggetto
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdSoggetto(Long idSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggetto", idSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_SOGGETTO, map);
    }

    /**
     * Load soggetto istanza by id soggetto padre list.
     *
     * @param idSoggettoPadre idSoggettoPadre
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdSoggettoPadre(Long idSoggettoPadre) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggetto", idSoggettoPadre);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_SOGGETTO_PADRE, map);
    }

    /**
     * Load soggetto istanza by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_ISTANZA, map);
    }

    /**
     * Load soggetto istanza with delega by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaWithDelegaByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_SOGGETTI_ISTANZE_WITH_DELEGA_BY_ID_ISTANZA, map);
    }

    /**
     * @param idIstanza             idIstanza
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoIstanzaExtendedDTO>
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByIdIstanzaAndCodiceFiscaleSoggetto(Long idIstanza, String codiceFiscaleSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("codiceFiscaleSoggetto", codiceFiscaleSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_ISTANZE_BY_ID_ISTANZA_AND_CODICE_FISCALE_SOGGETTO, map);
    }

    /**
     * @param uidIstanza            uidIstanza
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoIstanzaExtendedDTO>
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByUidIstanzaAndCodiceFiscaleSoggetto(Long uidIstanza, String codiceFiscaleSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uidIstanza", uidIstanza);
        map.put("codiceFiscaleSoggetto", codiceFiscaleSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTI_ISTANZE_BY_UID_ISTANZA_AND_CODICE_FISCALE_SOGGETTO, map);
    }

    /**
     * Load soggetto istanza by codice fiscale soggetto list.
     *
     * @param codiceFiscaleSoggetto codiceFiscaleSoggetto
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByCodiceFiscaleSoggetto(String codiceFiscaleSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codiceFiscaleSoggetto", codiceFiscaleSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_ISTANZA_BY_CODICE_FISCALE_SOGGETTO, map);
    }

    /**
     * Load soggetto istanza by partita iva soggetto list.
     *
     * @param partitaIvaSoggetto partitaIvaSoggetto
     * @return List<SoggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<SoggettoIstanzaExtendedDTO> loadSoggettoIstanzaByPartitaIvaSoggetto(String partitaIvaSoggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("partitaIvaSoggetto", partitaIvaSoggetto);
        return findListByQuery(className, QUERY_LOAD_SOGGETTO_ISTANZA_BY_PARTITA_IVA_SOGGETTO, map);
    }

    /**
     * Find by pk soggetto istanza dto.
     *
     * @param idSoggettoIstanza idSoggettoIstanza
     * @return SoggettoIstanzaDTO soggetto istanza dto
     */
    @Override
    public SoggettoIstanzaDTO findByPK(Long idSoggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idSoggettoIstanza", idSoggettoIstanza);
        return findByPK(className, map);
    }

    /**
     * Save soggetto istanza long.
     *
     * @param dto SoggettoIstanzaDTO
     * @return id record salvato
     */
    @Override
    public Long saveSoggettoIstanza(SoggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();

            Date now = Calendar.getInstance().getTime();

            map.put("idSoggettoPadre", dto.getIdSoggettoPadre());
            map.put("idTipoSogg", dto.getIdTipoSoggetto());
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idSoggetto", dto.getIdSoggetto());
            map.put("idRuoloSoggetto", dto.getIdRuoloSoggetto());
            map.put("idRuoloCompil", dto.getIdRuoloCompilante());
            map.put("idTipoNatGiur", dto.getIdTipoNaturaGiuridica());
            map.put("idComuneRes", dto.getIdComuneResidenza());
            map.put("idComuneNasc", dto.getIdComuneNascita());
            map.put("idComuneSl", dto.getIdComuneSedeLegale());
            map.put("denomSogg", dto.getDenSoggetto());
            map.put("dataCessazSogg", dto.getDataCessazioneSoggetto());
            map.put("nome", dto.getNome());
            map.put("cognome", dto.getCognome());
            map.put("indSogg", dto.getIndirizzoSoggetto());
            map.put("numCivInd", dto.getNumCivicoIndirizzo());
            map.put("email", dto.getDesEmail());
            map.put("pec", dto.getDesPec());
            map.put("cf", dto.getCfSoggetto());
            map.put("piva", dto.getPartitaIvaSoggetto());
            map.put("numTel", dto.getNumTelefono());
            map.put("denomProvCCIAA", dto.getDenProvinciaCCIAA());
            map.put("denomAnnoCCIAA", dto.getDenAnnoCCIAA());
            map.put("denomNumCCIAA", dto.getDenNumeroCCIAA());
            map.put("dataNascSogg", dto.getDataNascitaSoggetto());
            map.put("cittaEsteraNasc", dto.getCittaEsteraNascita());
            map.put("statoEsteroNasc", dto.getStatoEsteroNascita());
            map.put("cittaEsteraRes", dto.getCittaEsteraResidenza());
            map.put("dateIns", now);
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreIns());
            map.put("idMasterdata", dto.getIdMasterdata());
            map.put("idMasterdataOrigine", dto.getIdMasterdataOrigine());
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
            map.put("numCellulare", dto.getNumCellulare());
            map.put("idNazioneResidenza", dto.getIdNazioneResidenza());
            map.put("idNazioneNascita", dto.getIdNazioneNascita());
            map.put("desLocalita", dto.getDesLocalita());
            map.put("cittaEsteraSedeLegale", dto.getCittaEsteraSedeLegale());
            map.put("idNazioneSedeLegale", dto.getIdNazioneSedeLegale());
            map.put("capResidenza", dto.getCapResidenza());
            //map.put("capNscita", dto.getCapNascita());
            map.put("capSedeLegale", dto.getCapSedeLegale());
            map.put("idFunzionario", dto.getIdFunzionario());

            map.put("uid", generateGestUID(dto.getCfSoggetto() + dto.getDenSoggetto() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            int returnValue = template.update(getQuery(QUERY_INSERT_SOGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_soggetto_istanza"});
            Number key = keyHolder.getKey();

            // In fase di insert lo stato istanza è BOZZA pertanto non verrà inserita nello storico
            if (returnValue > 0) {
                map.put("idSoggIst", key.longValue());
                params = getParameterValue(map);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_SOGGETTO_ISTANZA), params);
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
     * Save soggetti istanza map.
     *
     * @param dtos List<SoggettoIstanzaDTO>
     * @return mappa con id salvati
     */
    @Override
    public Map<String, Long> saveSoggettiIstanza(List<SoggettoIstanzaDTO> dtos) {
        logBegin(className);
        Map<String, Long> keys = new HashMap<>();
        try {
            for (SoggettoIstanzaDTO dto : dtos) {
                Map<String, Object> map = new HashMap<>();
                Date now = Calendar.getInstance().getTime();

                map.put("idSoggettoPadre", dto.getIdSoggettoPadre());
                map.put("idTipoSogg", dto.getIdTipoSoggetto());
                map.put("idIstanza", dto.getIdIstanza());
                map.put("idSoggetto", dto.getIdSoggetto());
                map.put("idRuoloSoggetto", dto.getIdRuoloSoggetto());
                map.put("idRuoloCompil", dto.getIdRuoloCompilante());
                map.put("idTipoNatGiur", dto.getIdTipoNaturaGiuridica());
                map.put("idComuneRes", dto.getIdComuneResidenza());
                map.put("idComuneNasc", dto.getIdComuneNascita());
                map.put("idComuneSl", dto.getIdComuneSedeLegale());
                map.put("denomSogg", dto.getDenSoggetto());
                map.put("dataCessazSogg", dto.getDataCessazioneSoggetto());
                map.put("nome", dto.getNome());
                map.put("cognome", dto.getCognome());
                map.put("indSogg", dto.getIndirizzoSoggetto());
                map.put("numCivInd", dto.getNumCivicoIndirizzo());
                map.put("email", dto.getDesEmail());
                map.put("pec", dto.getDesPec());
                map.put("cf", dto.getCfSoggetto());
                map.put("piva", dto.getPartitaIvaSoggetto());
                map.put("numTel", dto.getNumTelefono());
                map.put("denomProvCCIAA", dto.getDenProvinciaCCIAA());
                map.put("denomAnnoCCIAA", dto.getDenAnnoCCIAA());
                map.put("denomNumCCIAA", dto.getDenNumeroCCIAA());
                map.put("dataNascSogg", dto.getDataNascitaSoggetto());
                map.put("cittaEsteraNasc", dto.getCittaEsteraNascita());
                map.put("statoEsteroNasc", dto.getStatoEsteroNascita());
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
                //map.put("capNscita", dto.getCapNascita());
                map.put("capSedeLegale", dto.getCapSedeLegale());
                map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
                map.put("idFunzionario", dto.getIdFunzionario());

                map.put("uid", generateGestUID(dto.getCfSoggetto() + dto.getDenSoggetto() + now));
                MapSqlParameterSource params = getParameterValue(map);

                KeyHolder keyHolder = new GeneratedKeyHolder();

                int returnValue = template.update(getQuery(QUERY_INSERT_SOGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_soggetto_istanza"});
                Number key = keyHolder.getKey();

                if (null != key && returnValue > 0) {
                    // Se riesco ad inserire il record lo aggiungo alla mappa
                    keys.put(dto.getCfSoggetto(), key.longValue());
                } else {
                    // Se non riesco ad inserire un record li cancello tutti
                    for (Long l : keys.values()) {
                        this.deleteSoggettoIstanzaById(l);
                    }
                }
            }
            return keys;
        } catch (Exception e) {
            logError(className, e);
            for (Long l : keys.values()) {
                this.deleteSoggettoIstanzaById(l);
            }
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update soggetto istanza integer.
     *
     * @param dto SoggettoIstanzaDTO
     * @return numero record inseriti
     */
    @Override
    public Integer updateSoggettoIstanza(SoggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            SoggettoIstanzaDTO soggettoIstanza = this.findByPK(dto.getIdSoggettoIstanza());
            if (null == soggettoIstanza) {
                logError(className, "Record non trovato con id [" + dto.getIdSoggettoIstanza() + "]");
                return -1;
            }

            int returnValue = 1;
            if (!dto.equals(soggettoIstanza)) {
                Long idIstanzaAttoreOld = soggettoIstanza.getIdIstanzaAttore() != null && soggettoIstanza.getIdIstanzaAttore() > 0 ? soggettoIstanza.getIdIstanzaAttore() : null;
                Long idFunzionarioOld = soggettoIstanza.getIdFunzionario() != null && soggettoIstanza.getIdFunzionario() > 0 ? soggettoIstanza.getIdFunzionario() : null;

                map.put("idSoggIst", dto.getIdSoggettoIstanza());
                map.put("idSoggettoPadre", dto.getIdSoggettoPadre());
                map.put("idTipoSogg", dto.getIdTipoSoggetto());
                map.put("idIstanza", dto.getIdIstanza());
                map.put("idSoggetto", dto.getIdSoggetto());
                map.put("idRuoloSoggetto", dto.getIdRuoloSoggetto());
                map.put("idRuoloCompil", dto.getIdRuoloCompilante());
                map.put("idTipoNatGiur", dto.getIdTipoNaturaGiuridica());
                map.put("idComuneRes", dto.getIdComuneResidenza());
                map.put("idComuneNasc", dto.getIdComuneNascita());
                map.put("idComuneSl", dto.getIdComuneSedeLegale());
                map.put("denomSogg", dto.getDenSoggetto());
                map.put("dataCessazSogg", dto.getDataCessazioneSoggetto());
                map.put("nome", dto.getNome());
                map.put("cognome", dto.getCognome());
                map.put("indSogg", dto.getIndirizzoSoggetto());
                map.put("numCivInd", dto.getNumCivicoIndirizzo());
                map.put("email", dto.getDesEmail());
                map.put("pec", dto.getDesPec());
                map.put("cf", dto.getCfSoggetto());
                map.put("piva", dto.getPartitaIvaSoggetto());
                map.put("numTel", dto.getNumTelefono());
                map.put("denomProvCCIAA", dto.getDenProvinciaCCIAA());
                map.put("denomAnnoCCIAA", dto.getDenAnnoCCIAA());
                map.put("denomNumCCIAA", dto.getDenNumeroCCIAA());
                map.put("dataNascSogg", dto.getDataNascitaSoggetto());
                map.put("cittaEsteraNasc", dto.getCittaEsteraNascita());
                map.put("statoEsteroNasc", dto.getStatoEsteroNascita());
                map.put("cittaEsteraRes", dto.getCittaEsteraResidenza());
                map.put("dateIns", soggettoIstanza.getGestDataIns());
                map.put("attoreIns", soggettoIstanza.getGestAttoreIns());
                map.put("dateUpd", now);
                map.put("attoreUpd", dto.getGestAttoreUpd());
                map.put("idMasterdata", dto.getIdMasterdata());
                map.put("idMasterdataOrigine", soggettoIstanza.getIdMasterdataOrigine());
                map.put("numCellulare", dto.getNumCellulare());
                map.put("idNazioneResidenza", dto.getIdNazioneResidenza());
                map.put("idNazioneNascita", dto.getIdNazioneNascita());
                map.put("desLocalita", dto.getDesLocalita());
                map.put("cittaEsteraSedeLegale", dto.getCittaEsteraSedeLegale());
                map.put("idNazioneSedeLegale", dto.getIdNazioneSedeLegale());
                map.put("capResidenza", dto.getCapResidenza());
                //map.put("capNscita", dto.getCapNascita());
                map.put("capSedeLegale", dto.getCapSedeLegale());

                map.put("uid", soggettoIstanza.getGestUID());
                map.put("idIstanzaAttore", dto.getIdIstanzaAttore() != null ? dto.getIdIstanzaAttore() : idIstanzaAttoreOld);
                map.put("idFunzionario", dto.getIdFunzionario() != null ? dto.getIdFunzionario() : idFunzionarioOld);
                MapSqlParameterSource params = getParameterValue(map);

                returnValue = template.update(getQuery(QUERY_UPDATE_SOGGETTO_ISTANZA, null, null), params);
                if (returnValue > 0) {
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_SOGGETTO_ISTANZA), params);
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
     * Delete soggetto istanza integer.
     *
     * @param uid uidSoggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteSoggettoIstanza(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_SOGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete soggetto istanza by id integer.
     *
     * @param id idSoggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteSoggettoIstanzaById(Long id) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idSoggIst", id);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_SOGGETTO_ISTANZA_BY_ID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza attore integer.
     *
     * @param idSoggettoIstanza the id soggetto istanza
     * @param idIstanzaAttore   idIstanzaAttore
     * @param attoreUpd         attoreUpd
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIstanzaAttore(Long idSoggettoIstanza, Long idIstanzaAttore, String attoreUpd) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            SoggettoIstanzaDTO soggettoIstanza = this.findByPK(idSoggettoIstanza);
            if (null == soggettoIstanza) {
                logError(className, "Record non trovato con id [" + idSoggettoIstanza + "]");
                return -1;
            }
            map.put("idSoggettoIstanza", idSoggettoIstanza);
            map.put("idIstanzaAttore", idIstanzaAttore);
            map.put("dateUpd", now);
            map.put("attoreUpd", attoreUpd);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_ISTANZA_ATTORE, null, null), params);
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
     * @return RowMapper<SoggettoIstanzaDTO>
     */
    @Override
    public RowMapper<SoggettoIstanzaDTO> getRowMapper() throws SQLException {
        return new SoggettoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<SoggettoIstanzaExtendedDTO>
     */
    @Override
    public RowMapper<SoggettoIstanzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new SoggettoIstanzaExtendedRowMapper();
    }

    /**
     * The type Soggetto istanza row mapper.
     */
    public static class SoggettoIstanzaRowMapper implements RowMapper<SoggettoIstanzaDTO> {

        /**
         * Instantiates a new Soggetto istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public SoggettoIstanzaRowMapper() throws SQLException {
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
        public SoggettoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoggettoIstanzaDTO bean = new SoggettoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, SoggettoIstanzaDTO bean) throws SQLException {
            bean.setIdSoggettoIstanza(rs.getLong("id_soggetto_istanza"));
            bean.setIdSoggettoPadre(rs.getLong("id_soggetto_padre") > 0 ? rs.getLong("id_soggetto_padre") : null);
            bean.setIdSoggetto(rs.getLong("id_soggetto"));
            bean.setIdTipoSoggetto(rs.getLong("id_tipo_soggetto"));
            bean.setIdRuoloSoggetto(rs.getLong("id_ruolo_soggetto") > 0 ? rs.getLong("id_ruolo_soggetto") : null);
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdRuoloCompilante(rs.getLong("id_ruolo_compilante"));
            bean.setIdTipoNaturaGiuridica(rs.getLong("id_tipo_natura_giuridica") > 0 ? rs.getLong("id_tipo_natura_giuridica") : null);
            bean.setIdComuneResidenza(rs.getLong("id_comune_residenza") > 0 ? rs.getLong("id_comune_residenza") : null);
            bean.setIdComuneNascita(rs.getLong("id_comune_nascita") > 0 ? rs.getLong("id_comune_nascita") : null);
            bean.setIdComuneSedeLegale(rs.getLong("id_comune_sede_legale") > 0 ? rs.getLong("id_comune_sede_legale") : null);
            bean.setDenSoggetto(rs.getString("den_soggetto"));
            bean.setDataCessazioneSoggetto(rs.getDate("data_cessazione_soggetto"));
            bean.setNome(rs.getString("nome"));
            bean.setCognome(rs.getString("cognome"));
            bean.setIndirizzoSoggetto(rs.getString("indirizzo_soggetto"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setDesPec(rs.getString("des_pec"));
            bean.setCfSoggetto(rs.getString("cf_soggetto"));
            bean.setPartitaIvaSoggetto(rs.getString("partita_iva_soggetto"));
            bean.setNumTelefono(rs.getString("num_telefono"));
            bean.setDenProvinciaCCIAA(rs.getString("den_provincia_cciaa"));
            bean.setDenAnnoCCIAA(rs.getInt("den_anno_cciaa") > 0 ? rs.getInt("den_anno_cciaa") : null);
            bean.setDenNumeroCCIAA(rs.getString("den_numero_cciaa"));
            bean.setDataNascitaSoggetto(rs.getDate("data_nascita_soggetto"));
            bean.setCittaEsteraNascita(rs.getString("citta_estera_nascita"));
            bean.setCittaEsteraResidenza(rs.getString("citta_estera_residenza"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setIdNazioneNascita(rs.getLong("id_nazione_nascita"));
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
            bean.setIdMasterdata(rs.getLong("id_masterdata") > 0 ? rs.getLong("id_masterdata") : null);
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine") > 0 ? rs.getLong("id_masterdata_origine") : null);
            bean.setGestUID(rs.getString("gest_uid"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
        }
    }

    /**
     * The type Soggetto istanza extended row mapper.
     */
    public static class SoggettoIstanzaExtendedRowMapper implements RowMapper<SoggettoIstanzaExtendedDTO> {

        /**
         * Instantiates a new Soggetto istanza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public SoggettoIstanzaExtendedRowMapper() throws SQLException {
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
        public SoggettoIstanzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            SoggettoIstanzaExtendedDTO bean = new SoggettoIstanzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, SoggettoIstanzaExtendedDTO bean) throws SQLException {
            bean.setIdSoggettoIstanza(rs.getLong("soggetto_istanza_id"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdSoggettoPadre(rs.getLong("id_soggetto_padre") > 0 ? rs.getLong("id_soggetto_padre") : null);

            SoggettoExtendedDTO soggettoFiglio = new SoggettoExtendedDTO();
            populateBeanSoggetto(rs, soggettoFiglio, "f");
            bean.setSoggetto(soggettoFiglio.getIdSoggetto() != null && soggettoFiglio.getIdSoggetto() != 0 ? soggettoFiglio : null);

            RuoloSoggettoDTO ruoloSoggetto = new RuoloSoggettoDTO();
            populateBeanRuoloSoggetto(rs, ruoloSoggetto);
            bean.setRuoloSoggetto(ruoloSoggetto.getIdRuoloSoggetto() != null && ruoloSoggetto.getIdRuoloSoggetto() != 0 ? ruoloSoggetto : null);

            RuoloCompilanteDTO ruoloCompilante = new RuoloCompilanteDTO();
            populateBeanRuoloCompilante(rs, ruoloCompilante);
            bean.setRuoloCompilante(ruoloCompilante.getIdRuoloCompilante() != null && ruoloCompilante.getIdRuoloCompilante() != 0 ? ruoloCompilante : null);

            TipoSoggettoDTO tipoSoggetto = new TipoSoggettoDTO();
            populateBeanTipoSoggetto(rs, tipoSoggetto, "si");
            bean.setTipoSoggetto(tipoSoggetto.getIdTipoSoggetto() != null && tipoSoggetto.getIdTipoSoggetto() != 0 ? tipoSoggetto : null);

            TipoNaturaGiuridicaDTO tipoNaturaGiuridica = new TipoNaturaGiuridicaDTO();
            if (rs.getLong("id_tipo_natura_giuridica_si") > 0) {
                populateBeanTipoNaturaGiuridica(rs, tipoNaturaGiuridica, "si");
            }
            bean.setTipoNaturaGiuridica(tipoNaturaGiuridica.getIdTipoNaturaGiuridica() != null && tipoNaturaGiuridica.getIdTipoNaturaGiuridica() != 0 ? tipoNaturaGiuridica : null);

            ComuneExtendedDTO comuneResidenza = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneResidenza, "_si_res");
            bean.setComuneResidenza(comuneResidenza.getIdComune() != 0 ? comuneResidenza : null);

            ComuneExtendedDTO comuneSedeLegale = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneSedeLegale, "_si_sl");
            bean.setComuneSedeLegale(comuneSedeLegale.getIdComune() != 0 ? comuneSedeLegale : null);

            ComuneExtendedDTO comuneNascita = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneNascita, "_si_nasc");
            bean.setComuneNascita(comuneNascita.getIdComune() != 0 ? comuneNascita : null);

            bean.setDenSoggetto(rs.getString("den_soggetto_si"));
            bean.setDataCessazioneSoggetto(rs.getDate("data_cessazione_soggetto_si"));
            bean.setNome(rs.getString("nome_si"));
            bean.setCognome(rs.getString("cognome_si"));
            bean.setIndirizzoSoggetto(rs.getString("indirizzo_soggetto_si"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo_si"));
            bean.setDesEmail(rs.getString("des_email_si"));
            bean.setDesPec(rs.getString("des_pec_si"));
            bean.setCfSoggetto(rs.getString("cf_soggetto_si"));
            bean.setPartitaIvaSoggetto(rs.getString("partita_iva_soggetto_si"));
            bean.setNumTelefono(rs.getString("num_telefono_si"));
            bean.setDenProvinciaCCIAA(rs.getString("den_provincia_cciaa_si"));
            bean.setDenAnnoCCIAA(rs.getInt("den_anno_cciaa_si") > 0 ? rs.getInt("den_anno_cciaa_si") : null);
            bean.setDenNumeroCCIAA(rs.getString("den_numero_cciaa_si"));
            bean.setDataNascitaSoggetto(rs.getDate("data_nascita_soggetto_si"));
            bean.setCittaEsteraNascita(rs.getString("citta_estera_nascita_si"));
            bean.setCittaEsteraResidenza(rs.getString("citta_estera_residenza_si"));
            bean.setNumCellulare(rs.getString("num_cellulare_si"));

            NazioneDTO nazioneNascita = new NazioneDTO();
            populateBeanNazione(rs, nazioneNascita, "n_si");
            bean.setNazioneNascita(nazioneNascita.getIdNazione() > 0 ? nazioneNascita : null);

            NazioneDTO nazioneResidenza = new NazioneDTO();
            populateBeanNazione(rs, nazioneResidenza, "r_si");
            bean.setNazioneResidenza(nazioneResidenza.getIdNazione() > 0 ? nazioneResidenza : null);

            bean.setDesLocalita(rs.getString("des_localita_si"));
            bean.setCittaEsteraSedeLegale(rs.getString("citta_estera_sede_legale_si"));

            NazioneDTO nazioneSedeLegale = new NazioneDTO();
            populateBeanNazione(rs, nazioneSedeLegale, "s_si");
            bean.setNazioneSedeLegale(nazioneSedeLegale.getIdNazione() > 0 ? nazioneSedeLegale : null);

            bean.setCapResidenza(rs.getString("cap_residenza_si"));
            bean.setCapSedeLegale(rs.getString("cap_sede_legale_si"));

            bean.setGestDataIns(rs.getTimestamp("gest_data_ins_si"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins_si"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd_si"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd_si"));
            bean.setIdMasterdata(rs.getLong("id_masterdata_si"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine_si"));
            bean.setGestUID(rs.getString("gest_uid_si"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore_si"));

            GruppoSoggettoDTO gruppoSoggetto = new GruppoSoggettoDTO();
            populateBeanGruppoSoggetto(rs, gruppoSoggetto);
            bean.setGruppoSoggetto(gruppoSoggetto.getIdGruppoSoggetto() != null && gruppoSoggetto.getIdGruppoSoggetto() != 0 ? gruppoSoggetto : null);
            bean.setFlgCapogruppo(rs.getInt("flg_capogruppo") == 1 ? Boolean.TRUE : Boolean.FALSE);

        }

        private void populateBeanSoggetto(ResultSet rs, SoggettoExtendedDTO bean, String alias) throws SQLException {
            bean.setIdSoggetto(rs.getLong("id_soggetto_" + alias) > 0 ? rs.getLong("id_soggetto_" + alias) : null);

            TipoSoggettoDTO tipoSoggetto = new TipoSoggettoDTO();
            populateBeanTipoSoggetto(rs, tipoSoggetto, alias);
            bean.setTipoSoggetto(tipoSoggetto.getIdTipoSoggetto() != null && tipoSoggetto.getIdTipoSoggetto() != 0 ? tipoSoggetto : null);

            TipoNaturaGiuridicaDTO tipoNaturaGiuridica = new TipoNaturaGiuridicaDTO();
            populateBeanTipoNaturaGiuridica(rs, tipoNaturaGiuridica, alias);
            bean.setTipoNaturaGiuridica(tipoNaturaGiuridica.getIdTipoNaturaGiuridica() != null && tipoNaturaGiuridica.getIdTipoNaturaGiuridica() != 0 ? tipoNaturaGiuridica : null);

            ComuneExtendedDTO comuneResidenza = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneResidenza, "_" + alias + "_res");
            bean.setComuneResidenza(comuneResidenza.getIdComune() != 0 ? comuneResidenza : null);

            ComuneExtendedDTO comuneSedeLegale = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneSedeLegale, "_" + alias + "_sl");
            bean.setComuneSedeLegale(comuneSedeLegale.getIdComune() != 0 ? comuneSedeLegale : null);

            ComuneExtendedDTO comuneNascita = new ComuneExtendedDTO();
            populateBeanComune(rs, comuneNascita, "_" + alias + "_nasc");
            bean.setComuneNascita(comuneNascita.getIdComune() != 0 ? comuneNascita : null);

            bean.setDenSoggetto(rs.getString("den_soggetto_" + alias));
            bean.setDataCessazioneSoggetto(rs.getDate("data_cessazione_soggetto_" + alias));
            bean.setNome(rs.getString("nome_" + alias));
            bean.setCognome(rs.getString("cognome_" + alias));
            bean.setIndirizzoSoggetto(rs.getString("indirizzo_soggetto_" + alias));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo_" + alias));
            bean.setDesEmail(rs.getString("des_email_" + alias));
            bean.setDesPec(rs.getString("des_pec_" + alias));
            bean.setCfSoggetto(rs.getString("cf_soggetto_" + alias));
            bean.setPivaSoggetto(rs.getString("partita_iva_soggetto_" + alias));
            bean.setNumTelefono(rs.getString("num_telefono_" + alias));
            bean.setDenProvinciaCCIAA(rs.getString("den_provincia_cciaa_" + alias));
            bean.setDenAnnoCCIAA(rs.getInt("den_anno_cciaa_" + alias) > 0 ? rs.getInt("den_anno_cciaa_" + alias) : null);
            bean.setDenNumeroCCIAA(rs.getString("den_numero_cciaa_" + alias));
            bean.setDataNascitaSoggetto(rs.getDate("data_nascita_soggetto_" + alias));
            bean.setCittaEsteraNascita(rs.getString("citta_estera_nascita_" + alias));
            bean.setCittaEsteraResidenza(rs.getString("citta_estera_residenza_" + alias));
            bean.setNumCellulare(rs.getString("num_cellulare_" + alias));

            NazioneDTO nazioneNascita = new NazioneDTO();
            populateBeanNazione(rs, nazioneNascita, "n_so");
            bean.setNazioneNascita(nazioneNascita.getIdNazione() > 0 ? nazioneNascita : null);

            NazioneDTO nazioneResidenza = new NazioneDTO();
            populateBeanNazione(rs, nazioneResidenza, "r_so");
            bean.setNazioneResidenza(nazioneResidenza.getIdNazione() > 0 ? nazioneResidenza : null);

            bean.setDesLocalita(rs.getString("des_localita_" + alias));
            bean.setCittaEsteraSedeLegale(rs.getString("citta_estera_sede_legale_" + alias));

            NazioneDTO nazioneSedeLegale = new NazioneDTO();
            populateBeanNazione(rs, nazioneSedeLegale, "s_so");
            bean.setNazioneSedeLegale(nazioneSedeLegale.getIdNazione() > 0 ? nazioneSedeLegale : null);

            bean.setCapResidenza(rs.getString("cap_residenza_" + alias));
            bean.setCapSedeLegale(rs.getString("cap_sede_legale_" + alias));

            bean.setGestDataIns(rs.getTimestamp("gest_data_ins_" + alias));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins_" + alias));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd_" + alias));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd_" + alias));
            bean.setIdMasterdata(rs.getLong("id_masterdata_" + alias) > 0 ? rs.getLong("id_masterdata_" + alias) : null);
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine_" + alias) > 0 ? rs.getLong("id_masterdata_origine_" + alias) : null);
            bean.setGestUID(rs.getString("gest_uid_" + alias));
        }

        private void populateBeanTipoSoggetto(ResultSet rs, TipoSoggettoDTO bean, String alias) throws SQLException {
            bean.setIdTipoSoggetto(rs.getLong("id_tipo_soggetto_" + alias) > 0 ? rs.getLong("id_tipo_soggetto_" + alias) : null);
            bean.setCodiceTipoSoggetto(rs.getString("cod_tipo_soggetto_" + alias));
            bean.setDescrizioneTipoSoggetto(rs.getString("des_tipo_soggetto_" + alias));
        }

        private void populateBeanTipoNaturaGiuridica(ResultSet rs, TipoNaturaGiuridicaDTO bean, String alias) throws SQLException {
            bean.setIdTipoNaturaGiuridica(rs.getLong("id_tipo_natura_giuridica_" + alias) > 0 ? rs.getLong("id_tipo_natura_giuridica_" + alias) : null);
            bean.setCodiceTipoNaturaGiuridica(rs.getString("cod_tipo_natura_giuridica_" + alias));
            bean.setDescrizioneTipoNaturaGiuridica(rs.getString("des_tipo_natura_giuridica_" + alias));
            bean.setSiglaTipoNaturaGiuridica(rs.getString("sigla_tipo_natura_giuridica_" + alias));
        }

        private void populateBeanRuoloSoggetto(ResultSet rs, RuoloSoggettoDTO bean) throws SQLException {
            bean.setIdRuoloSoggetto(rs.getLong("id_ruolo_soggetto") > 0 ? rs.getLong("id_ruolo_soggetto") : null);
            bean.setCodiceRuoloSoggetto(rs.getString("cod_ruolo_soggetto"));
            bean.setDescrizioneRuoloSoggetto(rs.getString("des_ruolo_soggetto"));
        }

        private void populateBeanRuoloCompilante(ResultSet rs, RuoloCompilanteDTO bean) throws SQLException {
            bean.setIdRuoloCompilante(rs.getLong("id_ruolo_compilante") > 0 ? rs.getLong("id_ruolo_compilante") : null);
            bean.setCodiceRuoloCompilante(rs.getString("cod_ruolo_compilante"));
            bean.setDescrizioneRuoloCompilante(rs.getString("des_ruolo_compilante"));
            bean.setFlgModuloDelega(rs.getInt("flg_modulo_delega") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgModuloProcura(rs.getInt("flg_modulo_procura") == 1 ? Boolean.TRUE : Boolean.FALSE);
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

        private void populateBeanGruppoSoggetto(ResultSet rs, GruppoSoggettoDTO bean) throws SQLException {
            bean.setIdGruppoSoggetto(rs.getLong("id_gruppo_soggetto"));
            bean.setCodGruppoSoggetto(rs.getString("cod_gruppo_soggetto"));
            bean.setDesGruppoSoggetto(rs.getString("des_gruppo_soggetto"));
            bean.setFlgCreazioneAutomatica(rs.getInt("flg_creazione_automatica") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUID(rs.getString("gest_uid_stgs"));
        }
    }

    /**
     * Gets query storico.
     *
     * @param query the query
     * @return the query storico
     */
    public String getQueryStorico(String query) {
        return query.replaceAll("_replaceTableName_", getStoricoTableName());
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