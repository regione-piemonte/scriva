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

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaDAO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoResponsabileDTO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Istanza competenza dao.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaCompetenzaDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaCompetenzaDTO> implements IstanzaCompetenzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_ISTANZA = "AND sric.id_istanza = :idIstanza ";
    private static final String WHERE_IND_ASS_DA_SISTEMA = "AND sric.ind_assegnata_da_sistema = :indAssegnataDaSistema ";

    private static final String WHERE_ID_COMPETENZA_TERRITORIO = "AND sric.id_competenza_territorio = :idCompetenzaTerritorio \n";

    private static final String WHERE_FLG_AUTORITA_PRINCIPALE = "AND sric.flg_autorita_principale = 1 \n";
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(srac.data_inizio_validita) <= DATE(NOW()) AND (srac.data_fine_validita IS NULL OR DATE(srac.data_fine_validita)>= DATE(NOW())))\n ";

    private static final String WHERE_TIPO_COMPETENZA = "AND sdtc.id_competenza_territorio = :idTipoCompetenza \\n "; 
    
    private static final String ORDER_BY_ID_ISTANZA = "ORDER BY sric.id_istanza";

    private static final String ORDER_BY_ORD_TIPO_COMPETENZA = "ORDER BY sdtc.ordinamento_tipo_competenza, stct.des_competenza_territorio DESC";

    private static final String QUERY_LOAD_ISTANZE_COMPETENZA = "SELECT sric.*, sric.data_inizio_validita AS data_inizio_validita_ic, sric.data_fine_validita AS data_fine_validita_ic, sric.gest_attore_ins AS gest_attore_ins_ic, sric.gest_data_ins AS gest_data_ins_ic, sric.gest_attore_upd AS gest_attore_upd_ic, sric.gest_data_upd AS gest_data_upd_ic, sric.gest_uid AS gest_uid_ic, \n" +
            "stct.*, stct.id_competenza_territorio AS competenza_territorio_id, stct.des_pec as pec, stct.des_email as email,\n" +
            "sdtc.*, sdtc.id_tipo_competenza AS tipo_competenza_id, \n" +
            "srac.*, srac.des_pec as des_adempi_pec, srac.des_email as des_adempi_email,\n" +
            "sdec.*, sdec.id_ente_creditore AS ente_creditore_id, \n" +
            "sdc.*, sdc.id_comune AS comune_id, sdc.data_inizio_validita AS data_inizio_validita_sdc, sdc.data_fine_validita AS data_fine_validita_sdc, \n" +
            "sdp.*, sdp.id_provincia AS provincia_id, sdp.data_inizio_validita AS data_inizio_validita_sdp, sdp.data_fine_validita AS data_fine_validita_sdp, \n" +
            "sdr.*, sdr.id_regione AS regione_id, sdr.data_inizio_validita AS data_inizio_validita_sdr, sdr.data_fine_validita AS data_fine_validita_sdr, \n" +
            "sdn.*, sdn.id_nazione AS nazione_id, sdn.data_inizio_validita AS data_inizio_validita_sdn, sdn.data_fine_validita AS data_fine_validita_sdn \n" +
            "FROM _replaceTableName_ sric \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sric.id_istanza\n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sric.id_competenza_territorio \n" +
            "INNER JOIN scriva_r_adempi_competenza srac ON (srac.id_competenza_territorio = sric.id_competenza_territorio AND srac.id_adempimento = sti.id_adempimento)\n" +
            "INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \n" +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore \n" +
            "LEFT JOIN scriva_d_comune sdc ON sdc.id_comune = stct.id_comune_competenza \n" +
            "LEFT JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia \n" +
            "LEFT JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione \n" +
            "LEFT JOIN scriva_d_nazione sdn ON sdn.id_nazione = sdr.id_nazione \n" +
            "WHERE 1 = 1 \n";

    private static final String QUERY_LOAD_ISTANZA_COMPETENZA_BY_ID_ISTANZA = QUERY_LOAD_ISTANZE_COMPETENZA + WHERE_ID_ISTANZA;

    private static final String QUERY_LOAD_ISTANZA_COMPETENZA_BY_ID_ISTANZA_COMPETENZA = QUERY_LOAD_ISTANZE_COMPETENZA + WHERE_ID_ISTANZA + WHERE_ID_COMPETENZA_TERRITORIO;

    private static final String QUERY_LOAD_ISTANZA_COMPETENZA_BY_ID_ISTANZA_PRINCIPALE = QUERY_LOAD_ISTANZE_COMPETENZA + WHERE_ID_ISTANZA + WHERE_FLG_AUTORITA_PRINCIPALE;

    private static final String QUERY_INSERT_ISTANZA_COMPETENZA = "INSERT INTO _replaceTableName_ \n" +
            "(id_istanza, id_competenza_territorio, data_inizio_validita, data_fine_validita, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid, flg_autorita_principale, flg_autorita_assegnata_bo, ind_assegnata_da_sistema) \n" +
            "VALUES (:idIstanza, :idCompetenzaTerritorio, :dataInizioValidita, :dataFineValidita, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid, :flgAutoritaPrincipale, :flgAutoritaAssegnataBo, :indAssegnataDaSistema)";

    private static final String QUERY_INSERT_STORICO_ISTANZA_COMPETENZA = "INSERT INTO _replaceTableName_\n" +
            "(id_istanza_comp_storico, id_istanza, id_competenza_territorio, data_inizio_validita, \n" +
            "data_fine_validita, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, \n" +
            "gest_uid, flg_autorita_principale, flg_autorita_assegnata_bo, ind_assegnata_da_sistema)\n" +
            "SELECT nextval('seq_scriva_s_istanza_competenza'), id_istanza, id_competenza_territorio, data_inizio_validita, \n" +
            "data_fine_validita, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, \n" +
            "gest_uid, flg_autorita_principale, flg_autorita_assegnata_bo, ind_assegnata_da_sistema\n" +
            "FROM scriva_r_istanza_competenza\n" +
            "WHERE gest_uid = :gestUid ";

    private static final String QUERY_UPDATE_ISTANZA_COMPETENZA = "UPDATE _replaceTableName_ \n" +
            "SET data_inizio_validita=:dataInizioValidita, data_fine_validita=:dataFineValidita, gest_data_ins=:gestDataIns, gest_attore_ins=:gestAttoreIns, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd, gest_uid=:gestUid, flg_autorita_principale=:flgAutoritaPrincipale, flg_autorita_assegnata_bo=:flgAutoritaAssegnataBo\n" +
            "WHERE id_istanza=:idIstanza AND id_competenza_territorio=:idCompetenzaTerritorio ";

    private static final String QUERY_DELETE_ISTANZA_COMPETENZA = "DELETE FROM _replaceTableName_ WHERE gest_uid=:gestUid";

    /*-- CALCOLA AC --*/
    private static final String QUERY_COMUNI_UBICA_OGGETTI_ISTANZA_BY_ID_ISTANZA = "SELECT DISTINCT sdc.id_comune \n" +
            "FROM scriva_t_oggetto_istanza stoi \n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdto ON sdto.id_tipologia_oggetto = stoi.id_tipologia_oggetto \n" +
            "LEFT JOIN scriva_r_ogg_istanza_categoria sroic ON sroic.id_oggetto_istanza = stoi.id_oggetto_istanza \n" +
            "INNER JOIN scriva_r_ubica_oggetto_istanza sruoi ON sruoi.id_oggetto_istanza = stoi.id_oggetto_istanza \n" +
            "INNER JOIN scriva_d_comune sdc ON sdc.id_comune = sruoi.id_comune \n" +
            "WHERE sroic.data_fine_validita IS NULL \n" +
            "AND sdc.data_fine_validita IS NULL \n" +
            "AND stoi.id_istanza = :idIstanza \n";

    private static final String WHERE_FLG_ADESIONE_ADEMPIMENTO = "AND srac.ind_adesione_adempimento != 0 \n";

    private static final String WHERE_FLG_COMPETENZA_PRINCIPALE = "AND srac.flg_principale = 1 \n";

    private static final String WHERE_ID_COMUNE_IN = "AND sdc.id_comune IN ( " + QUERY_COMUNI_UBICA_OGGETTI_ISTANZA_BY_ID_ISTANZA + ") \n";

    private static final String WHERE_COD_COMPETENZA_IN = "AND sdtc.cod_tipo_competenza IN (:livelliCompetenzaList) \n";
    
    private static final String WHERE_COD_COMPETENZA_NOT = "AND sdtc.cod_tipo_competenza NOT LIKE 'GEST_%' \n";

    private static final String QUERY_DELETE_ISTANZA_COMPETENZA_BY_ID_ISTANZA_AND_ID_COMPENTENZA = "DELETE FROM _replaceTableName_ WHERE sric.id_istanza = :idIstanza AND sric.id_competenza_territorio =:identificativiCompetenzaList";


    private static final String QUERY_ID_COMPETENZA_TERRITORIO_REGIONALE_BY_ID_ISTANZA_ADEMPIMENTO = "SELECT srcr.id_competenza_territorio \n" +
            "FROM scriva_r_adempi_competenza srac \n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srac.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \n" +
            "INNER JOIN scriva_r_competenza_regione srcr ON srcr.id_competenza_territorio = srac.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_provincia sdp ON sdp.id_regione = srcr.id_regione \n" +
            "INNER JOIN scriva_d_comune sdc ON sdc.id_provincia = sdp.id_provincia \n" +
            "WHERE 1 = 1 \n" +
            "AND sdc.data_fine_validita IS NULL \n" +
            "AND sdp.data_fine_validita IS NULL \n" +
            "AND stct.data_fine_validita IS NULL \n" +
            "AND srac.id_adempimento = :idAdempimento \n" +
            WHERE_ID_COMUNE_IN;

    private static final String QUERY_ID_COMPETENZA_TERRITORIO_PROVINCIALE_BY_ID_ISTANZA_ADEMPIMENTO = "SELECT srcp.id_competenza_territorio \n" +
            "FROM scriva_r_adempi_competenza srac \n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srac.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \n" +
            "INNER JOIN scriva_r_competenza_provincia srcp ON srcp.id_competenza_territorio = srac.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_comune sdc ON sdc.id_provincia = srcp.id_provincia \n" +
            "WHERE 1 = 1 \n" +
            "AND sdc.data_fine_validita IS NULL \n" +
            "AND stct.data_fine_validita IS NULL \n" +
            "AND srac.id_adempimento = :idAdempimento \n" +
            WHERE_ID_COMUNE_IN;

    private static final String QUERY_ID_COMPETENZA_TERRITORIO_COMUNALE_BY_ID_ISTANZA_ADEMPIMENTO = "SELECT srcc.id_competenza_territorio \n" +
            "FROM scriva_r_adempi_competenza srac \n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srac.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \n" +
            "INNER JOIN scriva_r_competenza_comune srcc ON srcc.id_competenza_territorio = srac.id_competenza_territorio \n" +
            "INNER JOIN scriva_d_comune sdc ON sdc.id_comune = srcc.id_comune \n" +
            "WHERE 1 = 1 \n" +
            "AND sdc.data_fine_validita IS NULL \n" +
            "AND stct.data_fine_validita IS NULL \n" +
            "AND srac.id_adempimento = :idAdempimento \n" +
            WHERE_ID_COMUNE_IN;
    ;

    private static final String QUERY_ID_COMPTENZE_TERRITORIO_SECONDARIE = "SELECT DISTINCT id_competenza_territorio FROM (\n" +
            QUERY_ID_COMPETENZA_TERRITORIO_REGIONALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_COD_COMPETENZA_IN +
            WHERE_COD_COMPETENZA_NOT +
            "UNION " +
            QUERY_ID_COMPETENZA_TERRITORIO_PROVINCIALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_COD_COMPETENZA_IN +
            WHERE_COD_COMPETENZA_NOT +
            "UNION " +
            QUERY_ID_COMPETENZA_TERRITORIO_COMUNALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_COD_COMPETENZA_IN +
            WHERE_COD_COMPETENZA_NOT +
            " ) AS ac_secondarie";

    private static final String QUERY_ID_COMPTENZE_TERRITORIO_PRINCIPALI = "SELECT DISTINCT id_competenza_territorio FROM (\n" +
            QUERY_ID_COMPETENZA_TERRITORIO_REGIONALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_FLG_ADESIONE_ADEMPIMENTO +
            WHERE_FLG_COMPETENZA_PRINCIPALE +
            "UNION " +
            QUERY_ID_COMPETENZA_TERRITORIO_PROVINCIALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_FLG_ADESIONE_ADEMPIMENTO +
            WHERE_FLG_COMPETENZA_PRINCIPALE +
            "UNION " +
            QUERY_ID_COMPETENZA_TERRITORIO_COMUNALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_FLG_ADESIONE_ADEMPIMENTO +
            WHERE_FLG_COMPETENZA_PRINCIPALE +
            " ) AS ac_principali";


    private static final String QUERY_ID_COMPTENZE_TERRITORIO_VERIFICA = "SELECT DISTINCT id_competenza_territorio FROM (\n" +
            QUERY_ID_COMPETENZA_TERRITORIO_REGIONALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_FLG_ADESIONE_ADEMPIMENTO +
            "UNION " +
            QUERY_ID_COMPETENZA_TERRITORIO_PROVINCIALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_FLG_ADESIONE_ADEMPIMENTO +
            "UNION " +
            QUERY_ID_COMPETENZA_TERRITORIO_COMUNALE_BY_ID_ISTANZA_ADEMPIMENTO +
            WHERE_FLG_ADESIONE_ADEMPIMENTO +
            " ) AS ac_calcolate";

    private static final String QUERY_LIVELLI_COMPETENZA = "SELECT\n" + 
    		"DISTINCT SDTC.COD_TIPO_COMPETENZA\n" + 
    		"FROM\n" + 
    		"SCRIVA_R_OGG_ISTANZA_CATEGORIA SROIC\n" + 
    		"LEFT JOIN SCRIVA_R_TIPO_COMPETENZA_CAT SRTCC ON\n" + 
    		"SRTCC.ID_CATEGORIA_OGGETTO = SROIC.ID_CATEGORIA_OGGETTO\n" + 
    		"LEFT JOIN SCRIVA_D_TIPO_COMPETENZA SDTC ON\n" + 
    		"SDTC.ID_TIPO_COMPETENZA = SRTCC.ID_TIPO_COMPETENZA\n" + 
    		"LEFT JOIN SCRIVA_T_OGGETTO_ISTANZA STOI ON\n" + 
    		"STOI.ID_OGGETTO_ISTANZA = SROIC.ID_OGGETTO_ISTANZA\n" + 
    		"WHERE 1 = 1 \n" +
    		"AND SROIC.DATA_FINE_VALIDITA IS NULL\n" + 
    		"AND  SDTC.COD_TIPO_COMPETENZA IS NOT NULL\n" +
    		"AND STOI.ID_ISTANZA = :idIstanza;";
    		



    /**
     * @return List<IstanzaCompetenzaExtendedDTO>
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> loadIstanzeCompetenza() {
        logBegin(className);
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return template.query(getQuery(QUERY_LOAD_ISTANZE_COMPETENZA + WHERE_DATA_INIZIO_FINE + ORDER_BY_ID_ISTANZA, null, null), params, getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param idIstanza idIstanza
     * @return List<IstanzaCompetenzaExtendedDTO>
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanza(Long idIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_LOAD_ISTANZA_COMPETENZA_BY_ID_ISTANZA + ORDER_BY_ORD_TIPO_COMPETENZA, null, null), params, getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }
    
    /**
     * @param idIstanza idIstanza
     * @return List<IstanzaCompetenzaExtendedDTO>
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanza(Long idIstanza, String indAssegnataDaSistema) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_LOAD_ISTANZA_COMPETENZA_BY_ID_ISTANZA + WHERE_IND_ASS_DA_SISTEMA + ORDER_BY_ORD_TIPO_COMPETENZA, null, null), params, getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }


    /**
     * @param idIstanza              idIstanza
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @return List<IstanzaCompetenzaExtendedDTO>
     */
    @Override
    public List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanza(Long idIstanza, Long idCompetenzaTerritorio) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getPrimaryKeySelect(), params, getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    @Override
    public List<IstanzaCompetenzaExtendedDTO> loadIstanzaCompetenzaByIdIstanzaClean(Long idIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            
            MapSqlParameterSource params = getParameterValue(map);
            String query = getQuery(QUERY_LOAD_ISTANZE_COMPETENZA + WHERE_ID_ISTANZA, null, null);
            return template.query( query, params, getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    
    /**
     * @param idAdempimento idAdempimento
     * @param idIstanza     idIstanza
     * @return List<Long> idCompetenzeTerritorio
     */
    @Override
    public List<Long> loadCompetenzaTerritorioSecondarieByIdAdempimentoIdIstanza(Long idAdempimento, Long idIstanza, List<String> livelliCompetenzaList) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAdempimento", idAdempimento);
            map.put("idIstanza", idIstanza);
            map.put("livelliCompetenzaList", livelliCompetenzaList);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_ID_COMPTENZE_TERRITORIO_SECONDARIE, null, null), params, getLongRowMapper("id_competenza_territorio"));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load competenza territorio principali by id adempimento id istanza list.
     *
     * @param idAdempimento the id adempimento
     * @param idIstanza     the id istanza
     * @return the list
     */
    @Override
    public List<Long> loadCompetenzaTerritorioPrincipaliByIdAdempimentoIdIstanza(Long idAdempimento, Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idIstanza", idIstanza);
        return findListLongByQuery(className, QUERY_ID_COMPTENZE_TERRITORIO_PRINCIPALI, map, "id_competenza_territorio");
    }

    /**
     * Load competenza territorio verifica by id adempimento id istanza list.
     *
     * @param idAdempimento the id adempimento
     * @param idIstanza     the id istanza
     * @return the list
     */
    @Override
    public List<Long> loadCompetenzaTerritorioVerificaByIdAdempimentoIdIstanza(Long idAdempimento, Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("idIstanza", idIstanza);
        return findListLongByQuery(className, QUERY_ID_COMPTENZE_TERRITORIO_VERIFICA, map, "id_competenza_territorio");
    }

    /**
     * @param dto istanzaCompetenza
     * @return id record salvato
     */
    @Override
    public Long saveIstanzaCompetenza(IstanzaCompetenzaDTO dto) throws DuplicateRecordException {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idCompetenzaTerritorio", dto.getIdCompetenzaTerritorio());
            map.put("dataInizioValidita", now);
            map.put("dataFineValidita", dto.getDataFineValidita());
            map.put("flgAutoritaPrincipale", Boolean.TRUE.equals(dto.getFlgAutoritaPrincipale()) ? 1 : 0);
            map.put("flgAutoritaAssegnataBo", Boolean.TRUE.equals(dto.getFlgAutoritaAssegnataBO()) ? 1 : 0);
            map.put("indAssegnataDaSistema", StringUtils.isNotBlank( dto.getIndAssegnataDaSistema()) ? dto.getIndAssegnataDaSistema() : "0") ;
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdIstanza().toString() + dto.getIdCompetenzaTerritorio().toString() + now));
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_ISTANZA_COMPETENZA, null, null), params, keyHolder, new String[]{"id_istanza"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_ISTANZA_COMPETENZA), params);
            }
            return key.longValue();
        } catch (DuplicateKeyException e) {
            logError(className, e);
            throw new DuplicateRecordException();
        } catch (DataAccessException e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param dto istanzaCompetenza
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIstanzaCompetenza(IstanzaCompetenzaDTO dto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", dto.getIdIstanza());
        map.put("idCompetenzaTerritorio", dto.getIdCompetenzaTerritorio());
        int returnValue = 1;
        try {
            IstanzaCompetenzaDTO istanzaCompetenzaOld = this.findByPK(map);
            if (null == istanzaCompetenzaOld) {
                logError(className, "Record non trovato con idIstanza [" + dto.getIdIstanza() + "] e idCompetenzaTerritorio [" + dto.getIdCompetenzaTerritorio() + "]");
                return -1;
            }
            Date now = Calendar.getInstance().getTime();
            map.put("dataInizioValidita", istanzaCompetenzaOld.getDataInizioValidita());
            map.put("dataFineValidita", dto.getDataFineValidita());
            map.put("flgAutoritaPrincipale", Boolean.TRUE.equals(dto.getFlgAutoritaPrincipale()) ? 1 : 0);
            map.put("flgAutoritaAssegnataBo", Boolean.TRUE.equals(dto.getFlgAutoritaAssegnataBO()) ? 1 : 0);
            map.put("gestDataIns", istanzaCompetenzaOld.getGestDataIns());
            map.put("gestAttoreIns", istanzaCompetenzaOld.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", generateGestUID(dto.getIdIstanza().toString() + dto.getIdCompetenzaTerritorio().toString() + now));
            MapSqlParameterSource params = getParameterValue(map);
            returnValue = template.update(getQuery(QUERY_UPDATE_ISTANZA_COMPETENZA, null, null), params);

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_ISTANZA_COMPETENZA), params);
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
     * @param gestUid gestUid
     * @return numero record cancellati
     */
    @Override
    public Integer deleteIstanzaCompetenza(String gestUid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_COMPETENZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }
    
    /**
     * @param List<Integer> identificativiCompetenzaList
     * @return numero record cancellati
     */
    @Override
    public Integer deleteIstanzaCompetenzaByIdCompAndIdIst(Long idIstanza, Long idCompetenzaTerritorio) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_COMPETENZA_BY_ID_ISTANZA_AND_ID_COMPENTENZA, null, null), params);
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
        return getQuery(QUERY_LOAD_ISTANZA_COMPETENZA_BY_ID_ISTANZA_COMPETENZA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaCompetenzaDTO>
     */
    @Override
    public RowMapper<IstanzaCompetenzaDTO> getRowMapper() throws SQLException {
        return new IstanzaCompetenzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaCompetenzaExtendedDTO>
     */
    @Override
    public RowMapper<IstanzaCompetenzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaCompetenzaExtendedRowMapper();
    }

    /**
     * The type Istanza competenza row mapper.
     */
    public static class IstanzaCompetenzaRowMapper implements RowMapper<IstanzaCompetenzaDTO> {

        /**
         * Instantiates a new Istanza competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaCompetenzaRowMapper() throws SQLException {
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
        public IstanzaCompetenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaCompetenzaDTO bean = new IstanzaCompetenzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaCompetenzaDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setFlgAutoritaPrincipale(rs.getInt("flg_autorita_principale") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAutoritaAssegnataBO(rs.getInt("flg_autorita_assegnata_bo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Istanza competenza extended row mapper.
     */
    public static class IstanzaCompetenzaExtendedRowMapper implements RowMapper<IstanzaCompetenzaExtendedDTO> {

        /**
         * Instantiates a new Istanza competenza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaCompetenzaExtendedRowMapper() throws SQLException {
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
        public IstanzaCompetenzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaCompetenzaExtendedDTO bean = new IstanzaCompetenzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaCompetenzaExtendedDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));

            CompetenzaTerritorioExtendedDTO competenzaTerritorio = new CompetenzaTerritorioExtendedDTO();
            populateBeanCompetenzaTerritorio(rs, competenzaTerritorio);
            bean.setCompetenzaTerritorio(competenzaTerritorio);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita_ic"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita_ic"));
            bean.setFlgAutoritaPrincipale(rs.getInt("flg_autorita_principale") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAutoritaAssegnataBO(rs.getInt("flg_autorita_assegnata_bo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins_ic"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins_ic"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd_ic"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd_ic"));
            bean.setGestUID(rs.getString("gest_uid_ic"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setIndAssegnataDaSistema(rs.getString("ind_assegnata_da_sistema"));
        }

        private void populateBeanCompetenzaTerritorio(ResultSet rs, CompetenzaTerritorioExtendedDTO bean) throws SQLException {
            bean.setIdCompetenzaTerritorio(rs.getLong("competenza_territorio_id"));

            TipoCompetenzaDTO tipoCompetenza = new TipoCompetenzaDTO();
            populateBeanTipoCompetenza(rs, tipoCompetenza);
            bean.setTipoCompetenza(tipoCompetenza);
            bean.setIdTipoCompetenza(rs.getLong("tipo_competenza_id"));

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
            bean.setDesPecAdempi(rs.getString("des_adempi_pec"));
            bean.setDesEmailPec(rs.getString("pec"));
            bean.setDesEmail(rs.getString("email"));
            bean.setDesEmailAdempi(rs.getString("des_adempi_email"));
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
//            CompetenzaTerritorioResponsabileDTO responsabiliCompetenzaTerritorio = new CompetenzaTerritorioResponsabileDTO();
//            populateBeanCompTerrResp(rs, responsabiliCompetenzaTerritorio);
//            List<CompetenzaTerritorioResponsabileDTO> respComp = new ArrayList<CompetenzaTerritorioResponsabileDTO>();
//            respComp.add(responsabiliCompetenzaTerritorio);
//            bean.setResponsabiliCompetenzeTerritorio(respComp);
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

        private void populateBeanCompTerrResp(ResultSet rs, CompetenzaTerritorioResponsabileDTO bean) throws SQLException {
            bean.setIdCompetenzaResponsabile(rs.getLong("id_competenza_responsabile"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));

            TipoResponsabileDTO tipoResp = new TipoResponsabileDTO();
            populateBeanTipoResp(rs, tipoResp);
            bean.setTipoResponsabile(tipoResp);

            bean.setLabelResponsabile(rs.getString("label_responsabile"));
            bean.setNominativoResponsabile(rs.getString("nominativo_responsabile"));
            bean.setRecapitoResponsabile(rs.getString("recapito_responsabile"));
            bean.setFlgRiservato(rs.getBoolean("flg_riservato"));
        }
        
        private void populateBeanTipoResp(ResultSet rs, TipoResponsabileDTO bean) throws SQLException {
            bean.setIdTipoResponsabile(rs.getLong("id_tipo_responsabile"));
            bean.setCodiceTipoResponsabile(rs.getString("cod_tipo_responsabile"));
            bean.setDescrizioneTipoResponsabile(rs.getString("des_tipo_responsabile"));
            
        }
    }

	/**
	*
	* @param idIstanza
	* @return
	*
	*/
	@Override
	public List<String> loadLivelliCompetenzaCategorieProgettuali(Long idIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_LIVELLI_COMPETENZA, null, null), params, getStringRowMapper("cod_tipo_competenza"));
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
	}

}