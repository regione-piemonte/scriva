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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.LongRowMapper;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.NaturaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaGeecoDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import org.springframework.dao.EmptyResultDataAccessException;
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
 * The type Oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class OggettoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<OggettoIstanzaDTO> implements OggettoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanza";

    private static final String QUERY_LOAD_OGGETTO_ISTANZA_GEECO = "SELECT oi.id_istanza, oi.id_oggetto_istanza, oi.den_oggetto, i.id_adempimento, oi.ind_livello\n" +
            "FROM scriva_t_oggetto_istanza oi " +
            "JOIN scriva_t_istanza i ON oi.id_istanza = i.id_istanza " +
            "WHERE oi.id_oggetto_istanza = :idOggettoIstanza";

    private static final String QUERY_LOAD_OGGETTO_ISTANZA_GEECO_NEW = "SELECT oi.id_istanza, oi.id_oggetto_istanza, oi.den_oggetto, i.id_adempimento, oi.ind_livello\n" +
            "FROM scriva_t_oggetto_istanza oi\n" +
            "INNER JOIN scriva_t_istanza i ON oi.id_istanza = i.id_istanza" +
            "__dynamic_inner_join_conditions__" +
            "WHERE i.id_istanza = :idIstanza\n";

    private static final String WHERE_ID_OGG_ISTANZA_LIST = "AND oi.id_oggetto_istanza IN (:idOggettoIstanzaList)\n";
    private static final String WHERE_ID_LAYER_LIST = "AND srglv.id_virtuale IN (:idLayerList)\n";
    private static final String WHERE_IND_LVL_ESTRAZIONE = "AND oi.ind_livello = :indLivelloEstrazione\n";
    private static final String INNER_JOIN_GEECO_LAYER = "INNER JOIN scriva_r_geeco_layer_virtuali srglv ON srglv.id_tipologia_oggetto = oi.id_tipologia_oggetto\n";

    private static final String QUERY_LOAD_OGGETTI_ISTANZA = "SELECT stoi.id_oggetto_istanza, stoi.id_masterdata AS id_masterdata_oi, stoi.id_masterdata_origine AS id_masterdata_origine_oi, stoi.den_oggetto AS den_oggetto_oi, stoi.des_oggetto AS des_oggetto_oi, stoi.cod_oggetto_fonte AS cod_oggetto_fonte_oi, stoi.coordinata_x AS coordinata_x_oi, stoi.coordinata_y AS coordinata_y_oi, stoi.ind_geo_stato AS ind_geo_stato_oi, stoi.gest_data_ins AS gest_data_ins_oi, stoi.gest_attore_ins AS gest_attore_ins_oi, stoi.gest_data_upd AS gest_data_upd_oi, stoi.gest_attore_upd AS gest_attore_upd_oi, stoi.gest_uid AS gest_uid_oi, stoi.id_funzionario AS id_funzionario_oi, stoi.cod_utenza AS cod_utenza_oi, stoi.note_atto_precedente AS note_atto_precedente_oi, stoi.flg_geo_modificato AS flg_geo_modificato_oi, stoi.ind_livello AS ind_livello_oi\n" +
            ", sto.id_oggetto AS id_oggetto_o, sto.id_stato_oggetto AS id_stato_oggetto_o, sto.id_masterdata AS id_masterdata_o, sto.id_masterdata_origine AS id_masterdata_origine_o, sto.cod_scriva AS cod_scriva_o, sto.den_oggetto AS den_oggetto_o, sto.des_oggetto AS des_oggetto_o, sto.cod_oggetto_fonte AS cod_oggetto_fonte_o, sto.coordinata_x AS coordinata_x_o, sto.coordinata_y AS coordinata_y_o, sto.gest_data_ins AS gest_data_ins_o, sto.gest_attore_ins AS gest_attore_ins_o, sto.gest_data_upd AS gest_data_upd_o, sto.gest_attore_upd AS gest_attore_upd_o, sto.gest_uid AS gest_uid_o\n" +
            ", sdtoi.id_tipologia_oggetto AS id_tipologia_oggetto_oi, sdtoi.id_natura_oggetto AS id_natura_oggetto_oi, sdtoi.cod_tipologia_oggetto AS cod_tipologia_oggetto_oi, sdtoi.des_tipologia_oggetto AS des_tipologia_oggetto_oi\n" +
            ", sdto.id_tipologia_oggetto AS id_tipologia_oggetto_o, sdto.id_natura_oggetto AS id_natura_oggetto_o, sdto.cod_tipologia_oggetto AS cod_tipologia_oggetto_o, sdto.des_tipologia_oggetto AS des_tipologia_oggetto_o\n" +
            ", sti.id_istanza\n" +
            ", sdad.*, sdad.id_adempimento AS adempimento_id\n" +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id\n" +
            ", sda.*, sda.id_ambito AS ambito_id\n" +
            //", stc.id_compilante, stc.cf_compilante, stc.cognome_compilante, stc.nome_compilante " +
            ", sdsi.*\n" +
            ", sdso.*\n" +
            ", sdno.cod_natura_oggetto AS cod_natura_oggetto_o, sdno.des_natura_oggetto AS des_natura_oggetto_o\n" +
            ", sdnoi.cod_natura_oggetto AS cod_natura_oggetto_oi, sdnoi.des_natura_oggetto AS des_natura_oggetto_oi\n" +
            "FROM _replaceTableName_ stoi\n" +
            "INNER JOIN scriva_t_oggetto sto ON stoi.id_oggetto = sto.id_oggetto\n" +
            "LEFT JOIN scriva_d_stato_oggetto sdso ON sto.id_stato_oggetto = sdso.id_stato_oggetto\n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdtoi ON stoi.id_tipologia_oggetto = sdtoi.id_tipologia_oggetto\n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdto ON sto.id_tipologia_oggetto = sdto.id_tipologia_oggetto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdno ON sdto.id_natura_oggetto = sdno.id_natura_oggetto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdnoi ON sdtoi.id_natura_oggetto = sdnoi.id_natura_oggetto\n" +
            "INNER JOIN scriva_t_istanza sti ON stoi.id_istanza = sti.id_istanza\n" +
            "INNER JOIN scriva_d_adempimento sdad ON sti.id_adempimento = sdad.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito\n" +
            //"INNER JOIN scriva_t_compilante stc on sti.id_compilante = stc.id_compilante " +
            "INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza\n";

    private static final String QUERY_LOAD_OGGETTO_ISTANZA_BY_ID = QUERY_LOAD_OGGETTI_ISTANZA + "WHERE stoi.id_oggetto_istanza = :idOggettoIstanza\n";

    private static final String QUERY_LOAD_OGGETTO_ISTANZA_BY_UID = QUERY_LOAD_OGGETTI_ISTANZA + " WHERE stoi.gest_uid = :gestUID";

    private static final String QUERY_LOAD_OGGETTO_ISTANZA_BY_ID_OGGETTO = QUERY_LOAD_OGGETTI_ISTANZA + " WHERE stoi.id_oggetto = :idOggetto";

    private static final String QUERY_LOAD_OGGETTO_ISTANZA_BY_ID_ISTANZA = QUERY_LOAD_OGGETTI_ISTANZA + " WHERE stoi.id_istanza = :idIstanza";

    private static final String QUERY_LOAD_OGGETTO_ISTANZA_SIMPLE_BY_ID_ISTANZA = "SELECT * FROM _replaceTableName_ WHERE id_istanza = :idIstanza";

    private static final String QUERY_SEARCH_OGGETTO_ISTANZA = QUERY_LOAD_OGGETTI_ISTANZA
            + " INNER JOIN scriva_r_ubica_oggetto_istanza sruoi ON sruoi.id_oggetto_istanza = stoi.id_oggetto_istanza "
            + "INNER JOIN scriva_t_soggetto_istanza stsi ON stsi.id_istanza = sti.id_istanza "
            + "WHERE UPPER(sdsi.cod_stato_istanza) != 'BOZZA' "
            + "AND UPPER(sdso.cod_stato_oggetto) != 'BOZZA' "
            + "AND sruoi.id_comune = :idComune "
            + "AND stsi.id_soggetto IN (:idsSoggetti)";

    private static final String QUERY_INSERT_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_ \n" +
            "(id_oggetto_istanza, id_oggetto, id_istanza, id_tipologia_oggetto, id_masterdata, id_masterdata_origine,\n" +
            "den_oggetto, des_oggetto, cod_oggetto_fonte, coordinata_x, coordinata_y,\n" +
            "ind_geo_stato, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid, id_istanza_attore, id_funzionario,\n" +
            "cod_utenza, note_atto_precedente, ind_livello) " +
            "VALUES (nextval('seq_scriva_t_oggetto_istanza'), :idOggetto, :idIstanza, :idTipologiaOggetto, :idMasterdata, :idMasterdataOrigine,\n" +
            ":denOggetto, :desOggetto, :codOggettoFonte, :coordinataX, :coordinataY,\n" +
            ":indGeoStato, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid, :idIstanzaAttore, :idFunzionario,\n" +
            ":codUtenza, :noteAttoPrecedente, :indLivello)";

    private static final String QUERY_UPDATE_OGGETTO_ISTANZA = "UPDATE _replaceTableName_ \n" +
            "SET id_oggetto=:idOggetto, id_istanza=:idIstanza, id_tipologia_oggetto=:idTipologiaOggetto,\n" +
            "id_masterdata=:idMasterdata, den_oggetto=:denOggetto, des_oggetto=:desOggetto,\n" +
            "cod_oggetto_fonte=:codOggettoFonte, coordinata_x=:coordinataX, coordinata_y=:coordinataY,\n" +
            "ind_geo_stato=:indGeoStato, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd,\n" +
            "id_istanza_attore=:idIstanzaAttore, id_funzionario=:idFunzionario, cod_utenza=:codUtenza,\n" +
            "note_atto_precedente=:noteAttoPrecedente,flg_geo_modificato=:flgGeoModificato, ind_livello=:indLivello\n" +
            "WHERE id_oggetto_istanza=:idOggettoIstanza";

    private static final String QUERY_UPDATE_IND_GEO_STATO = "UPDATE _replaceTableName_ \n" +
            "SET ind_geo_stato=:indGeoStato, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE id_oggetto_istanza=:idOggettoIstanza";

    private static final String QUERY_DELETE_OGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE gest_uid = :gestUID";

    private static final String QUERY_DELETE_OGGETTO_ISTANZA_BY_ID = "DELETE FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanza";

    private static final String QUERY_INSERT_STORICO_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_istanza_storico, id_oggetto_istanza, id_oggetto, id_istanza, id_tipologia_oggetto,\n" +
            "id_masterdata, id_masterdata_origine, den_oggetto, des_oggetto,\n" +
            "coordinata_x, coordinata_y, ind_geo_stato,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid,\n" +
            "id_istanza_attore, id_funzionario, cod_oggetto_fonte, cod_utenza, note_atto_precedente, flg_geo_modificato, ind_livello)\n" +
            "VALUES (nextval('seq_scriva_s_oggetto_istanza'), :idOggettoIstanza, :idOggetto, :idIstanza, :idTipologiaOggetto,\n" +
            ":idMasterdata, :idMasterdataOrigine, :denOggetto, :desOggetto,\n" +
            ":coordinataX, :coordinataY, :indGeoStato,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid,\n" +
            ":idIstanzaAttore, :idFunzionario, :codOggettoFonte, :codUtenza, :noteAttoPrecedente, :flgGeoModificato, :indLivello)";

    private static final String QUERY_GET_ID_OGGETTO_ISTANZA_WITH_GEO_LAST_DATE = "SELECT id_oggetto_istanza FROM (\n" +
            "SELECT DISTINCT stoi.id_oggetto_istanza, stoi.id_oggetto, stoi.id_istanza, sgaoi.gest_data_upd AS gest_data_upd\n" +
            "FROM _replaceTableName_ stoi\n" +
            "INNER JOIN scriva_t_oggetto sto ON stoi.id_oggetto = sto.id_oggetto\n" +
            "INNER JOIN scriva_d_stato_oggetto sdso ON sto.id_stato_oggetto = sdso.id_stato_oggetto\n" +
            "INNER JOIN scriva_t_istanza sti ON stoi.id_istanza = sti.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "INNER JOIN scriva_geo_area_ogg_istanza sgaoi ON sgaoi.id_oggetto_istanza = stoi.id_oggetto_istanza\n" +
            "WHERE UPPER(sdsi.cod_stato_istanza) != 'BOZZA'\n" +
            "AND UPPER(sdso.cod_stato_oggetto) != 'BOZZA'\n" +
            "AND sto.id_oggetto = :idOggetto\n" +
            "UNION\n" +
            "SELECT DISTINCT stoi.id_oggetto_istanza, stoi.id_oggetto, stoi.id_istanza, sgloi.gest_data_upd AS gest_data_upd\n" +
            "FROM _replaceTableName_ stoi\n" +
            "INNER JOIN scriva_t_oggetto sto ON stoi.id_oggetto = sto.id_oggetto\n" +
            "INNER JOIN scriva_d_stato_oggetto sdso ON sto.id_stato_oggetto = sdso.id_stato_oggetto\n" +
            "INNER JOIN scriva_t_istanza sti ON stoi.id_istanza = sti.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "INNER JOIN scriva_geo_linea_ogg_istanza sgloi ON sgloi.id_oggetto_istanza = stoi.id_oggetto_istanza\n" +
            "WHERE UPPER(sdsi.cod_stato_istanza) != 'BOZZA'\n" +
            "AND UPPER(sdso.cod_stato_oggetto) != 'BOZZA'\n" +
            "AND sto.id_oggetto = :idOggetto\n" +
            "UNION\n" +
            "SELECT DISTINCT stoi.id_oggetto_istanza, stoi.id_oggetto, stoi.id_istanza, sgpoi.gest_data_upd AS gest_data_upd\n" +
            "FROM _replaceTableName_ stoi\n" +
            "INNER JOIN scriva_t_oggetto sto ON stoi.id_oggetto = sto.id_oggetto\n" +
            "INNER JOIN scriva_d_stato_oggetto sdso ON sto.id_stato_oggetto = sdso.id_stato_oggetto\n" +
            "INNER JOIN scriva_t_istanza sti ON stoi.id_istanza = sti.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "INNER JOIN scriva_geo_punto_ogg_istanza sgpoi ON sgpoi.id_oggetto_istanza = stoi.id_oggetto_istanza\n" +
            "WHERE UPPER(sdsi.cod_stato_istanza) != 'BOZZA'\n" +
            "AND UPPER(sdso.cod_stato_oggetto) != 'BOZZA'\n" +
            "AND sto.id_oggetto = :idOggetto\n" +
            ") istanza\n" +
            "ORDER BY gest_data_upd DESC\n" +
            "LIMIT 1";

    private static final String QUERY_GET_LAST_ID_OGGETTO_ISTANZA_REF_OGGETTO = "SELECT id_oggetto_istanza\n" +
            "FROM scriva_t_oggetto sto\n" +
            "INNER JOIN _replaceTableName_ stoi ON (stoi.id_istanza = sto.id_istanza_aggiornamento AND stoi.id_oggetto = :idOggetto)\n" +
            "WHERE sto.id_oggetto = :idOggetto";

    private static final String QUERY_UPDATE_FLG_GEO_MODIFICATO = "UPDATE _replaceTableName_ \n" +
            "SET flg_geo_modificato=:flgGeoModificato\n" +
            "WHERE id_oggetto_istanza=:idOggettoIstanza";

    private static final String QUERY_UPDATE_FLG_GEO_MODIFICATO_BY_ISTANZA = "UPDATE _replaceTableName_ \n" +
            "SET flg_geo_modificato=:flgGeoModificato\n" +
            "WHERE id_istanza=:idIstanza";

    /**
     * Load oggetti istanza list.
     *
     * @return List<OggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<OggettoIstanzaExtendedDTO> loadOggettiIstanza() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_OGGETTI_ISTANZA, null);
    }

    /**
     * Load oggetto istanza by id list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<OggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaById(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_ISTANZA_BY_ID, map);
    }

    /**
     * Load oggetto istanza by uid list.
     *
     * @param gestUID gestUID
     * @return List<OggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaByUID(String gestUID) {
        logBeginInfo(className, (Object) gestUID);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUID", gestUID);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_ISTANZA_BY_UID, map);
    }

    /**
     * Load oggetto istanza by id oggetto list.
     *
     * @param idOggetto idOggetto
     * @return List<OggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaByIdOggetto(Long idOggetto) {
        logBeginInfo(className, idOggetto);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggetto", idOggetto);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_ISTANZA_BY_ID_OGGETTO, map);
    }

    /**
     * Load oggetto istanza by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<OggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<OggettoIstanzaExtendedDTO> loadOggettoIstanzaByIdIstanza(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_ISTANZA_BY_ID_ISTANZA, map);
    }

    /**
     * Load oggetto istanza simple by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<OggettoIstanzaDTO> list
     */
    @Override
    public List<OggettoIstanzaDTO> loadOggettoIstanzaSimpleByIdIstanza(Long idIstanza) {
        logBeginInfo(className, idIstanza);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_OGGETTO_ISTANZA_SIMPLE_BY_ID_ISTANZA, map);
    }

    /**
     * Search oggetto istanza list.
     *
     * @param searchOggettoIstanza SearchOggettoDTO
     * @return List<OggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<OggettoIstanzaExtendedDTO> searchOggettoIstanza(SearchOggettoDTO searchOggettoIstanza) {
        logBeginInfo(className, searchOggettoIstanza);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", searchOggettoIstanza.getIdIstanza());
        map.put("idComune", searchOggettoIstanza.getIdComune());
        map.put("idsSoggetti", searchOggettoIstanza.getIdSoggetti());
        return findListByQuery(className, QUERY_SEARCH_OGGETTO_ISTANZA, map);
    }

    /**
     * Gets oggetto istanza geeco.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return OggettoIstanzaGeecoDTO oggetto istanza geeco
     */
    @Override
    public OggettoIstanzaGeecoDTO getOggettoIstanzaGeeco(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(QUERY_LOAD_OGGETTO_ISTANZA_GEECO, params, getOggettoIstanzaGeecoRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto istanza with last geo ref.
     *
     * @param idOggetto idOggetto
     * @return id record trovato
     */
    @Override
    public Long getOggettoIstanzaWithLastGeoRef(Long idOggetto) {
        logBeginInfo(className, idOggetto);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggetto", idOggetto);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(getQuery(QUERY_GET_ID_OGGETTO_ISTANZA_WITH_GEO_LAST_DATE, null, null), params, getLongRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto istanza update oggetto.
     *
     * @param idOggetto the id oggetto
     * @return the oggetto istanza update oggetto
     */
    @Override
    public Long getLastIdOggettoIstanzaRefOggetto(Long idOggetto) {
        logBeginInfo(className, idOggetto);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggetto", idOggetto);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(getQuery(QUERY_GET_LAST_ID_OGGETTO_ISTANZA_REF_OGGETTO, null, null), params, getLongRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update flg geo modificato integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param value            the value
     * @return the integer
     */
    @Override
    public Integer updateFlgGeoModificatoByIdOggIst(Long idOggettoIstanza, Boolean value) {
        logBeginInfo(className, idOggettoIstanza);
        try {
            Map<String, Object> map = new HashMap<>();
            OggettoIstanzaDTO oggettoIstanza = this.findByPK(idOggettoIstanza);
            if (null == oggettoIstanza) {
                logError(className, "Record non trovato con id [" + idOggettoIstanza + "]");
                return -1;
            }
            map.put("idOggettoIstanza", idOggettoIstanza);
            map.put("flgGeoModificato", Boolean.TRUE.equals(value) ? 1 : 0);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_FLG_GEO_MODIFICATO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update flg geo modificato by id ist integer.
     *
     * @param idIstanza the id istanza
     * @param value     the value
     * @return the integer
     */
    @Override
    public Integer updateFlgGeoModificatoByIdIst(Long idIstanza, Boolean value) {
        logBeginInfo(className, idIstanza);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("flgGeoModificato", Boolean.TRUE.equals(value) ? 1 : 0);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_FLG_GEO_MODIFICATO_BY_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets oggetto istanza geeco.
     *
     * @param idIstanza            the id istanza
     * @param idOggettoIstanzaList the id oggetto istanza list
     * @param idLayerList          the id layer list
     * @param indLivelloEstrazione the ind livello estrazione
     * @return the oggetto istanza geeco
     */
    @Override
    public List<OggettoIstanzaGeecoDTO> getOggettoIstanzaGeeco(Long idIstanza, List<Long> idOggettoIstanzaList, List<Long> idLayerList, Long indLivelloEstrazione) {
        logBegin(className);
        try {
            String query = QUERY_LOAD_OGGETTO_ISTANZA_GEECO_NEW;
            StringBuilder dynamicInnerJoinConditions = new StringBuilder();
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);

            if (idOggettoIstanzaList != null && !idOggettoIstanzaList.isEmpty()) {
                map.put("idOggettoIstanzaList", idOggettoIstanzaList);
                query += WHERE_ID_OGG_ISTANZA_LIST;
            }
            if (idLayerList != null && !idLayerList.isEmpty()) {
                map.put("idLayerList", idLayerList);
                query += WHERE_ID_LAYER_LIST;
                dynamicInnerJoinConditions.append(INNER_JOIN_GEECO_LAYER);
            }
            if (indLivelloEstrazione != null) {
                map.put("indLivelloEstrazione", indLivelloEstrazione);
                query += WHERE_IND_LVL_ESTRAZIONE;
            }
            query = query.replace("__dynamic_inner_join_conditions__", "\n" + dynamicInnerJoinConditions.toString());
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(query, params, getOggettoIstanzaGeecoRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save oggetto istanza long.
     *
     * @param dto OggettoIstanzaDTO
     * @return id record salvato
     */
    @Override
    public Long saveOggettoIstanza(OggettoIstanzaDTO dto) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idOggetto", dto.getIdOggetto());
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idTipologiaOggetto", dto.getIdTipologiaOggetto());
            map.put("indGeoStato", dto.getIndGeoStato());
            map.put("denOggetto", dto.getDenOggetto());
            map.put("desOggetto", dto.getDesOggetto());
            map.put("coordinataX", dto.getCoordinataX());
            map.put("coordinataY", dto.getCoordinataY());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("idMasterdata", dto.getIdMasterdata());
            map.put("idMasterdataOrigine", dto.getIdMasterdataOrigine());
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
            map.put("idFunzionario", dto.getIdFunzionario());
            map.put("codOggettoFonte", dto.getCodOggettoFonte());
            map.put("codUtenza", dto.getCodUtenza());
            map.put("noteAttoPrecedente", dto.getNoteAttoPrecedente());
            map.put("flgGeoModificato", dto.getFlgGeoModificato() != null && dto.getFlgGeoModificato() ? 1 : 0);
            map.put("indLivello", dto.getIndLivello() != null ? dto.getIndLivello() : 0);
            map.put("gestUid", generateGestUID(dto.getDenOggetto() + dto.getCoordinataX() + dto.getCoordinataY() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_OGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_oggetto_istanza"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                map.put("idOggettoIstanza", key.longValue());
                params = getParameterValue(map);

                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGGETTO_ISTANZA), params);
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
     * Update oggetto istanza integer.
     *
     * @param dto OggettoIstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateOggettoIstanza(OggettoIstanzaDTO dto) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            OggettoIstanzaDTO oggettoIstanza = this.findByPK(dto.getIdOggettoIstanza());
            if (null == oggettoIstanza) {
                LOGGER.error("[OggettoIstanzaDAOImpl::updateOggettoIstanza] Record non trovato con id [" + dto.getIdOggetto() + "]");
                return -1;
            }

            int returnValue = 1;
            if (!dto.equals(oggettoIstanza)) {
                map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
                map.put("idOggetto", dto.getIdOggetto());
                map.put("idIstanza", dto.getIdIstanza());
                map.put("idTipologiaOggetto", dto.getIdTipologiaOggetto());
                map.put("indGeoStato", dto.getIndGeoStato());
                map.put("denOggetto", dto.getDenOggetto());
                map.put("desOggetto", dto.getDesOggetto());
                map.put("coordinataX", dto.getCoordinataX());
                map.put("coordinataY", dto.getCoordinataY());
                map.put("gestDataIns", oggettoIstanza.getGestDataIns());
                map.put("gestAttoreIns", oggettoIstanza.getGestAttoreIns());
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                map.put("idMasterdata", dto.getIdMasterdata());
                map.put("idMasterdataOrigine", oggettoIstanza.getIdMasterdataOrigine());
                map.put("gestUid", oggettoIstanza.getGestUID());
                map.put("idIstanzaAttore", dto.getIdIstanzaAttore() != null && dto.getIdIstanzaAttore() > 0 ? dto.getIdIstanzaAttore() : oggettoIstanza.getIdIstanzaAttore());
                map.put("idFunzionario", dto.getIdFunzionario() != null && dto.getIdFunzionario() > 0 ? dto.getIdFunzionario() : oggettoIstanza.getIdFunzionario());
                map.put("codOggettoFonte", dto.getCodOggettoFonte() != null ? dto.getCodOggettoFonte() : oggettoIstanza.getCodOggettoFonte());
                map.put("codUtenza", dto.getCodUtenza() != null ? dto.getCodUtenza() : oggettoIstanza.getCodUtenza());
                map.put("noteAttoPrecedente", dto.getNoteAttoPrecedente() != null ? dto.getNoteAttoPrecedente() : oggettoIstanza.getNoteAttoPrecedente());
                map.put("flgGeoModificato", Boolean.TRUE.equals(dto.getFlgGeoModificato()) ? 1 : 0);
                // tentativo di fix a basso rischio regrressioni per 1636: per qualche motivo indLivello viene inserito sempre  quello padre nel dto (vedi getOggettoIstanzaUbicazione della classe GeecoServiceImpl) per cui
                // se arriva un figlio in questo caso viene inserito il livello del padre, in modo errato. Per ovviare senza toccare le logiche precedenti cisto che i motivi sono ignoti intevengo qui faccendogli prendere 
                // il livello maggiore.
                map.put("indLivello", (dto.getIndLivello() != null && dto.getIndLivello() != 0 && dto.getIndLivello() >= oggettoIstanza.getIndLivello()) ? dto.getIndLivello() : oggettoIstanza.getIndLivello()); 
                MapSqlParameterSource params = getParameterValue(map);
                
                LOGGER.info("indLivello in map: " + map.get("indLivello"));
                
                returnValue = template.update(getQuery(QUERY_UPDATE_OGGETTO_ISTANZA, null, null), params);
                if (returnValue > 0) {
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_OGGETTO_ISTANZA), params);
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
     * Update ind geo stato integer.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @param indGeoStato      indGeoStato
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIndGeoStato(Long idOggettoIstanza, String indGeoStato) {
        logBeginInfo(className, idOggettoIstanza);
        final String GEECO = "Geeco";
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            OggettoIstanzaDTO oggettoIstanza = this.findByPK(idOggettoIstanza);
            if (null == oggettoIstanza) {
                logError(className, "Record non trovato con id [" + idOggettoIstanza + "]");
                return -1;
            }
            map.put("idOggettoIstanza", idOggettoIstanza);
            map.put("indGeoStato", indGeoStato);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", oggettoIstanza.getGestAttoreUpd()); //SCRIVA-1365
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_IND_GEO_STATO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto istanza integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    @Override
    public Integer deleteOggettoIstanza(String gestUID) {
        logBeginInfo(className, gestUID);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto by id integer.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteOggettoById(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGGETTO_ISTANZA_BY_ID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by pk oggetto istanza dto.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return OggettoIstanzaDTO oggetto istanza dto
     */
    @Override
    public OggettoIstanzaDTO findByPK(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        OggettoIstanzaDTO dto = findByPK(className, map);
        return dto;
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
     * @return RowMapper<OggettoIstanzaDTO>
     */
    @Override
    public RowMapper<OggettoIstanzaDTO> getRowMapper() throws SQLException {
        return new OggettoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<OggettoIstanzaExtendedDTO>
     */
    @Override
    public RowMapper<OggettoIstanzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new OggettoIstanzaExtendedRowMapper();
    }

    /**
     * Gets oggetto istanza geeco row mapper.
     *
     * @return the oggetto istanza geeco row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<OggettoIstanzaGeecoDTO> getOggettoIstanzaGeecoRowMapper() throws SQLException {
        return new OggettoIstanzaGeecoRowMapper();
    }

    /**
     * Gets long row mapper.
     *
     * @return the long row mapper
     */
    public RowMapper<Long> getLongRowMapper() {
        return new LongRowMapper("id_oggetto_istanza");
    }

    /**
     * The type Oggetto istanza extended row mapper.
     */
    public static class OggettoIstanzaExtendedRowMapper implements RowMapper<OggettoIstanzaExtendedDTO> {

        /**
         * Instantiates a new Oggetto istanza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaExtendedRowMapper() throws SQLException {
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
        public OggettoIstanzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaExtendedDTO bean = new OggettoIstanzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaExtendedDTO bean) throws SQLException {
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));

            OggettoExtendedDTO oggetto = new OggettoExtendedDTO();
            populateBeanOggetto(rs, oggetto, "_o");
            bean.setOggetto(oggetto);

            bean.setIdIstanza(rs.getLong("id_istanza"));

            TipologiaOggettoExtendedDTO tipologiaOggetto = new TipologiaOggettoExtendedDTO();
            populateBeanTipologiaOggetto(rs, tipologiaOggetto, "_oi");
            bean.setTipologiaOggetto(tipologiaOggetto);

            bean.setIndGeoStato(rs.getString("ind_geo_stato_oi"));
            bean.setDenOggetto(rs.getString("den_oggetto_oi"));
            bean.setDesOggetto(rs.getString("des_oggetto_oi"));
            bean.setCoordinataX(rs.getBigDecimal("coordinata_x_oi"));
            bean.setCoordinataY(rs.getBigDecimal("coordinata_y_oi"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine_oi"));
            bean.setIdMasterdata(rs.getLong("id_masterdata_oi"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins_oi"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins_oi"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd_oi"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd_oi"));
            bean.setGestUID(rs.getString("gest_uid_oi"));
            bean.setCodOggettoFonte(rs.getString("cod_oggetto_fonte_oi"));
            bean.setCodUtenza(rs.getString("cod_utenza_oi"));
            bean.setNoteAttoPrecedente(rs.getString("note_atto_precedente_oi"));
            bean.setFlgGeoModificato(rs.getInt("flg_geo_modificato_oi") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndLivello(rs.getInt("ind_livello_oi"));
        }

        // OGGETTO
        private void populateBeanOggetto(ResultSet rs, OggettoExtendedDTO bean, String alias) throws SQLException {
            bean.setIdOggetto(rs.getLong("id_oggetto" + alias));
            TipologiaOggettoExtendedDTO tipologiaOggetto = new TipologiaOggettoExtendedDTO();
            populateBeanTipologiaOggetto(rs, tipologiaOggetto, alias);
            bean.setTipologiaOggetto(tipologiaOggetto);
            StatoOggettoDTO statoOggetto = new StatoOggettoDTO();
            populateBeanStatoOggetto(rs, statoOggetto);
            bean.setStatoOggetto(statoOggetto);
            bean.setCodScriva(rs.getString("cod_scriva" + alias));
            bean.setDenOggetto(rs.getString("den_oggetto" + alias));
            bean.setDesOggetto(rs.getString("des_oggetto" + alias));
            bean.setCoordinataX(rs.getBigDecimal("coordinata_x" + alias));
            bean.setCoordinataY(rs.getBigDecimal("coordinata_y" + alias));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine" + alias));
            bean.setIdMasterdata(rs.getLong("id_masterdata" + alias));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins" + alias));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins" + alias));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd" + alias));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd" + alias));
            bean.setGestUID(rs.getString("gest_uid" + alias));
            bean.setCodOggettoFonte(rs.getString("cod_oggetto_fonte" + alias));
        }

        private void populateBeanTipologiaOggetto(ResultSet rs, TipologiaOggettoExtendedDTO bean, String alias) throws SQLException {
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto" + alias));
            NaturaOggettoDTO naturaOggetto = new NaturaOggettoDTO();
            populateBeanNaturaOggetto(rs, naturaOggetto, alias);
            bean.setNaturaOggetto(naturaOggetto);
            bean.setCodTipologiaOggetto(rs.getString("cod_tipologia_oggetto" + alias));
            bean.setDesTipologiaOggetto(rs.getString("des_tipologia_oggetto" + alias));
        }

        private void populateBeanNaturaOggetto(ResultSet rs, NaturaOggettoDTO bean, String alias) throws SQLException {
            bean.setIdNaturaOggetto(rs.getLong("id_natura_oggetto" + alias));
            bean.setCodNaturaOggetto(rs.getString("cod_natura_oggetto" + alias));
            bean.setDesNaturaOggetto(rs.getString("des_natura_oggetto" + alias));
        }

        private void populateBeanStatoOggetto(ResultSet rs, StatoOggettoDTO bean) throws SQLException {
            bean.setIdStatoOggetto(rs.getLong("id_stato_oggetto"));
            bean.setCodStatoOggetto(rs.getString("cod_stato_oggetto"));
            bean.setDesStatoOggetto(rs.getString("des_stato_oggetto"));
        }

        private void populateBeanStatoIstanza(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
            bean.setIndRicercaOggetto(rs.getString("ind_ricerca_oggetto"));
            bean.setIndAggiornaOggetto(rs.getString("ind_aggiorna_oggetto"));
            bean.setFlgAggiornaOggetto(rs.getBoolean("flg_aggiorna_oggetto") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanCompilante(ResultSet rs, CompilanteDTO bean) throws SQLException {
            bean.setIdCompilante(rs.getLong("id_compilante"));
            bean.setCodiceFiscaleCompilante(rs.getString("cf_compilante"));
            bean.setCognomeCompilante(rs.getString("cognome_compilante"));
            bean.setNomeCompilante(rs.getString("nome_compilante"));
        }

        private void populateBeanAdempimento(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
        }

        private void pupulateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }

    }

    /**
     * The type Oggetto istanza row mapper.
     */
    public static class OggettoIstanzaRowMapper implements RowMapper<OggettoIstanzaDTO> {

        /**
         * Instantiates a new Oggetto istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaRowMapper() throws SQLException {
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
        public OggettoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaDTO bean = new OggettoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaDTO bean) throws SQLException {
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdOggetto(rs.getLong("id_oggetto"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setIndGeoStato(rs.getString("ind_geo_stato"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setCoordinataX(rs.getBigDecimal("coordinata_x"));
            bean.setCoordinataY(rs.getBigDecimal("coordinata_y"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine"));
            bean.setIdMasterdata(rs.getLong("id_masterdata"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore") > 0 ? rs.getLong("id_istanza_attore") : null);
            bean.setIdFunzionario(rs.getLong("id_funzionario") > 0 ? rs.getLong("id_funzionario") : null);
            bean.setCodOggettoFonte(rs.getString("cod_oggetto_fonte"));
            bean.setCodUtenza(rs.getString("cod_utenza"));
            bean.setNoteAttoPrecedente(rs.getString("note_atto_precedente"));
            bean.setFlgGeoModificato(rs.getInt("flg_geo_modificato") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndLivello(rs.getInt("ind_livello"));
        }

    }

    /**
     * The type Oggetto istanza geeco row mapper.
     */
    public static class OggettoIstanzaGeecoRowMapper implements RowMapper<OggettoIstanzaGeecoDTO> {

        /**
         * Instantiates a new Oggetto istanza geeco row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaGeecoRowMapper() throws SQLException {
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
        public OggettoIstanzaGeecoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaGeecoDTO bean = new OggettoIstanzaGeecoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaGeecoDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setDenOggettoIstanza(rs.getString("den_oggetto"));
            bean.setIndLivello(rs.getInt("ind_livello"));
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