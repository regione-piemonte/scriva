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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.StringRowMapper;
import it.csi.scriva.scrivabesrv.dto.NaturaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.SearchOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import org.apache.commons.lang3.StringUtils;
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
 * The type Oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class OggettoDAOImpl extends ScrivaBeSrvGenericDAO<OggettoDTO> implements OggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_oggetto = :idOggetto";

    private static final String QUERY_LOAD_OGGETTI = "SELECT sto.*, sdto.cod_tipologia_oggetto , sdto.des_tipologia_oggetto, sdso.cod_stato_oggetto, sdso.des_stato_oggetto, sdno.*\n" +
            "FROM _replaceTableName_ sto\n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdto ON sto.id_tipologia_oggetto = sdto.id_tipologia_oggetto\n" +
            "LEFT JOIN scriva_d_stato_oggetto sdso ON sto.id_stato_oggetto = sdso.id_stato_oggetto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdno ON sdto.id_natura_oggetto = sdno.id_natura_oggetto\n";

    private static final String QUERY_LOAD_OGGETTO_BY_ID = QUERY_LOAD_OGGETTI + " WHERE id_oggetto = :idOggetto";

    private static final String QUERY_LOAD_OGGETTO_BY_ID_COMUNE = QUERY_LOAD_OGGETTI + " WHERE sto.id_oggetto IN (\n" +
            "SELECT id_oggetto FROM scriva_r_ubica_oggetto sruo WHERE sruo.id_comune = :idComune\n" +
            ")";

    private static final String QUERY_LOAD_OGGETTO_BY_ID_ISTANZA = QUERY_LOAD_OGGETTI + " WHERE sto.id_oggetto IN (\n" +
            "SELECT id_oggetto FROM scriva_t_oggetto_istanza stoi WHERE stoi.id_istanza = :idIstanza\n" +
            ")";

    private static final String QUERY_LOAD_OGGETTO_BY_UID = QUERY_LOAD_OGGETTI + " WHERE gest_uid = :gestUID";

    private static final String QUERY_LOAD_OGGETTO_BY_COD_SCRIVA = "SELECT * FROM _replaceTableName_  WHERE cod_scriva = :codScriva";

    private static final String QUERY_SEARCH_OGGETTO = "SELECT DISTINCT sto.*, sdto.cod_tipologia_oggetto , sdto.des_tipologia_oggetto, sdso.cod_stato_oggetto, sdso.des_stato_oggetto, sdno.*\n" +
            "FROM scriva_t_oggetto_istanza stoi\n" +
            "INNER JOIN _replaceTableName_ sto ON stoi.id_oggetto = sto.id_oggetto\n" +
            "INNER JOIN scriva_d_stato_oggetto sdso ON sto.id_stato_oggetto = sdso.id_stato_oggetto\n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdtoi ON stoi.id_tipologia_oggetto = sdtoi.id_tipologia_oggetto\n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdto ON sto.id_tipologia_oggetto = sdto.id_tipologia_oggetto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdno ON sdto.id_natura_oggetto = sdno.id_natura_oggetto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdnoi ON sdtoi.id_natura_oggetto = sdnoi.id_natura_oggetto\n" +
            "INNER JOIN scriva_t_istanza sti ON stoi.id_istanza = sti.id_istanza\n" +
            "INNER JOIN scriva_r_istanza_attore sria ON sria.id_istanza_attore = sti.id_istanza_attore_owner\n" +
            "INNER JOIN scriva_t_compilante stc ON sria.id_compilante = stc.id_compilante\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "INNER JOIN scriva_r_ubica_oggetto sruo ON sto.id_oggetto = sruo.id_oggetto\n" +
            "INNER JOIN scriva_t_soggetto_istanza stsi ON stsi.id_istanza = sti.id_istanza\n" +
            "WHERE UPPER(sdsi.cod_stato_istanza) != 'BOZZA'\n" +
            "AND UPPER(sdso.cod_stato_oggetto) != 'BOZZA'\n" +
            "AND sruo.id_comune = :idComune\n" +
            "AND stsi.id_soggetto IN (:idsSoggetti)\n";

    private static final String QUERY_SEARCH_OGGETTO_NEW = "SELECT DISTINCT sto.*, sdto.cod_tipologia_oggetto , sdto.des_tipologia_oggetto, sdso.cod_stato_oggetto, sdso.des_stato_oggetto, sdno.* \n" +
            "FROM _replaceTableName_ sto \n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdto ON sdto.id_tipologia_oggetto = sto.id_tipologia_oggetto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdno ON sdto.id_natura_oggetto = sdno.id_natura_oggetto\n" +
            "LEFT JOIN scriva_d_stato_oggetto sdso ON sdso.id_stato_oggetto = sto.id_stato_oggetto\n" +
            //"INNER JOIN scriva_r_adempi_tipo_oggetto srato ON (srato.id_tipologia_oggetto = sto.id_tipologia_oggetto AND srato.id_adempimento = :idAdempimento ) AND srato.flg_ricerca=1)\n" +
            //"INNER JOIN scriva_r_adempi_tipo_oggetto srato ON (srato.id_tipologia_oggetto = sto.id_tipologia_oggetto AND srato.id_adempimento = :idAdempimento ))\n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sto.id_istanza_aggiornamento\n" +
            "INNER JOIN scriva_r_ubica_oggetto sruo ON sruo.id_oggetto = sto.id_oggetto\n" +
            "INNER JOIN scriva_t_soggetto_istanza stsi ON (stsi.id_istanza = sti.id_istanza AND stsi.id_soggetto_padre IS NULL)\n" +
            "__dynamic_inner_join__" +
            "WHERE 1=1\n";

    private static final String WHERE_DEN_OGGETTO = "AND sto.den_oggetto ILIKE '%' || :denOggetto || '%'\n";

    private static final String WHERE_COD_SCRIVA_FONTE = "AND (UPPER(sto.cod_scriva::TEXT) ILIKE UPPER('%' || :codScriva || '%') OR UPPER(sto.cod_oggetto_fonte) ILIKE UPPER('%' || :codScriva || '%'))\n";

    private static final String WHERE_COD_TIPO_OGGETTO = "AND sdto.cod_tipologia_oggetto IN (:codTipologieOggetto)\n";

    private static final String WHERE_ID_COMUNE = "AND sruo.id_comune = :idComune\n";

    private static final String WHERE_ID_SOGGETTI = "AND stsi.id_soggetto IN (:idSoggetti)\n";

    private static final String WHERE_CF_SOGGETTO = "AND stsi.cf_soggetto IN (:cfSoggetto)\n";

    private static final String INNER_JOIN_ANNO_PRESENTAZIONE = "INNER JOIN scriva_r_istanza_stato sris ON sris.id_istanza = sto.id_istanza_aggiornamento AND sris.id_stato_istanza = sti.id_stato_istanza AND EXTRACT(YEAR FROM sris.data_cambio_stato) >= :annoPresentazione\n";

    private static final String INNER_JOIN_SOGGETTI_ISTANZA = "INNER JOIN scriva_t_soggetto_istanza stsi ON stsi.id_istanza = sti.id_istanza AND stsi.id_soggetto_padre IS NULL\n";

    private static final String WHERE_STATI_ISTANZA = "AND (\n" +
            "    sti.id_stato_istanza IN (\n" +
            "        SELECT sdsi.id_stato_istanza\n" +
            "        FROM scriva_d_stato_istanza sdsi\n" +
            "        WHERE sdsi.ind_ricerca_oggetto='S'\n" +
            "    )\n" +
            "    OR sti.id_stato_istanza||'/'||sti.id_esito_procedimento  IN (\n" +
            "        SELECT sdsi.id_stato_istanza||'/'||sdep.id_esito_procedimento \n" +
            "        FROM scriva_d_esito_procedimento sdep\n" +
            "        INNER JOIN scriva_d_stato_istanza sdsi ON sdep.cod_esito_procedimento = ANY(regexp_split_to_array(sdsi.ind_ricerca_oggetto , '-'))\n" +
            "        WHERE sdsi.ind_ricerca_oggetto NOT IN ('S', 'N')\n" +
            "    )\n" +
            ")\n";

    private static final String QUERY_INSERT_OGGETTO = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto, id_tipologia_oggetto, id_stato_oggetto, id_masterdata, id_masterdata_origine, cod_scriva,\n" +
            "den_oggetto, des_oggetto, cod_oggetto_fonte, coordinata_x, coordinata_y, id_funzionario,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid,\n" +
            "data_aggiornamento, id_istanza_aggiornamento)\n" +
            "VALUES (nextval('seq_scriva_t_oggetto'), :idTipologiaOggetto, :idStatoOggetto, :idMasterdata, :idMasterdataOrigine, :codScriva,\n" +
            ":denOggetto, :desOggetto, :codOggettoFonte, :coordinataX, :coordinataY, :idFunzionario,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid,\n" +
            ":dataAggiornamento, :idIstanzaAggiornamento)";


    private static final String QUERY_UPDATE_OGGETTO = "UPDATE _replaceTableName_\n" +
            "SET id_tipologia_oggetto=:idTipologiaOggetto, id_stato_oggetto=:idStatoOggetto, id_masterdata=:idMasterdata,\n" +
            "den_oggetto=:denOggetto, des_oggetto=:desOggetto, cod_oggetto_fonte=:codOggettoFonte,\n" +
            "coordinata_x=:coordinataX, coordinata_y=:coordinataY, id_funzionario=:idFunzionario,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd,\n" +
            "data_aggiornamento=:dataAggiornamento, id_istanza_aggiornamento=:idIstanzaAggiornamento\n" +
            "WHERE id_oggetto=:idOggetto\n";

    private static final String QUERY_SELECT_COD_SCRIVA = "SELECT 'SCRV' || nextval('seq_scriva_cod_scriva') AS cod_scriva";

    private static final String QUERY_DELETE_OGGETTO = "DELETE FROM _replaceTableName_ WHERE gest_uid = :gestUID";

    private static final String QUERY_DELETE_OGGETTO_BY_ID = "DELETE FROM _replaceTableName_ WHERE id_oggetto = :idOggetto";

    private static final String QUERY_INSERT_STORICO_OGGETTO = "INSERT INTO _replaceTableName_ \n" +
            "(id_oggetto_storico, id_oggetto, id_tipologia_oggetto, id_stato_oggetto,\n" +
            "id_masterdata, id_masterdata_origine, cod_scriva, den_oggetto, des_oggetto,\n" +
            "cod_oggetto_fonte, coordinata_x, coordinata_y, id_funzionario,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid,\n" +
            "data_aggiornamento, id_istanza_aggiornamento)\n" +
            "SELECT nextval('seq_scriva_s_oggetto'), id_oggetto, id_tipologia_oggetto, id_stato_oggetto,\n" +
            "id_masterdata, id_masterdata_origine, cod_scriva, den_oggetto, des_oggetto,\n" +
            "cod_oggetto_fonte, coordinata_x, coordinata_y, id_funzionario,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid,\n" +
            "data_aggiornamento, id_istanza_aggiornamento\n" +
            "FROM scriva_t_oggetto\n" +
            "WHERE gest_uid = :gestUid";

    private static final String QUERY_UPDATE_OGGETTO_BY_OGGETTO_ISTANZA = "UPDATE _replaceTableName_ \n" +
            "SET\n  id_tipologia_oggetto=oggetto_istanza.id_tipologia_oggetto,\n" +
            "  id_masterdata=oggetto_istanza.id_masterdata,\n" +
            "  id_masterdata_origine=oggetto_istanza.id_masterdata_origine,\n" +
            "  den_oggetto=oggetto_istanza.den_oggetto,\n" +
            "  des_oggetto=oggetto_istanza.des_oggetto,\n" +
            "  coordinata_x=oggetto_istanza.coordinata_x,\n" +
            "  coordinata_y=oggetto_istanza.coordinata_y,\n" +
            "  gest_data_upd=now(),\n" +
            "  gest_attore_upd=oggetto_istanza.gest_attore_upd,\n" +
            "  data_aggiornamento=now(),\n" +
            "  cod_oggetto_fonte=oggetto_istanza.cod_oggetto_fonte,\n" +
            "  id_istanza_aggiornamento=oggetto_istanza.id_istanza,\n" +
            "  id_funzionario=oggetto_istanza.id_funzionario\n" +
            "FROM (SELECT * FROM scriva_t_oggetto_istanza WHERE id_oggetto_istanza = :idOggettoIstanza) AS oggetto_istanza\n" +
            "WHERE scriva_t_oggetto.id_oggetto=oggetto_istanza.id_oggetto\n";

    /**
     * Load oggetti list.
     *
     * @return List<OggettoExtendedDTO> list
     */
    @Override
    public List<OggettoExtendedDTO> loadOggetti() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_OGGETTI, null);
    }

    /**
     * Load oggetto by id list.
     *
     * @param idOggetto idOggetto
     * @return List<OggettoExtendedDTO> list
     */
    @Override
    public List<OggettoExtendedDTO> loadOggettoById(Long idOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggetto", idOggetto);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_BY_ID, map);
    }

    /**
     * Load oggetto by id comune list.
     *
     * @param idComune idComune
     * @return List<OggettoExtendedDTO> list
     */
    @Override
    public List<OggettoExtendedDTO> loadOggettoByIdComune(Long idComune) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idComune", idComune);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_BY_ID_COMUNE, map);
    }

    /**
     * Load oggetto by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<OggettoExtendedDTO> loadOggettoByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_BY_ID_ISTANZA, map);
    }

    /**
     * Load oggetto by uid list.
     *
     * @param gestUID gestUID
     * @return List<OggettoExtendedDTO> list
     */
    @Override
    public List<OggettoExtendedDTO> loadOggettoByUID(String gestUID) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUID", gestUID);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_BY_UID, map);
    }

    /**
     * Load oggetto by cod scriva list.
     *
     * @param codScriva the cod scriva
     * @return the list
     */
    @Override
    public List<OggettoDTO> loadOggettoByCodScriva(String codScriva) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codScriva", codScriva);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_OGGETTO_BY_COD_SCRIVA, map);
    }

    /**
     * Gets next cod scriva.
     *
     * @return the next cod scriva
     */
    @Override
    public String getNextCodScriva() {
        logBegin(className);
        List<String> queryList = template.query(getQuery(QUERY_SELECT_COD_SCRIVA, null, null), new MapSqlParameterSource(), new StringRowMapper("cod_scriva"));
        return queryList != null && !queryList.isEmpty() ? queryList.get(0) : null;
    }

    /**
     * Search oggetto list.
     *
     * @param searchOggetto searchOggetto
     * @return List<OggettoExtendedDTO> list
     */
    @Override
    public List<OggettoExtendedDTO> searchOggetto(SearchOggettoDTO searchOggetto) {
        logBegin(className);
        String query = QUERY_SEARCH_OGGETTO_NEW;
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", searchOggetto.getIdAdempimento());
        map.put("idComune", searchOggetto.getIdComune());

        if (StringUtils.isNotBlank(searchOggetto.getDenOggetto())) {
            map.put("denOggetto", searchOggetto.getDenOggetto());
            query += WHERE_DEN_OGGETTO;
        }

        if (StringUtils.isNotBlank(searchOggetto.getCodScriva())) {
            map.put("codScriva", searchOggetto.getCodScriva());
            query += WHERE_COD_SCRIVA_FONTE;
        }

        return findListByQuery(className, query, map);
    }

    /**
     * Search oggetto list.
     *
     * @param searchOggetto the search oggetto
     * @param statiIstanza  the stati istanza
     * @return the list
     */
    @Override
    public List<OggettoExtendedDTO> searchOggetto(SearchOggettoDTO searchOggetto, List<String> statiIstanza, Integer annoPresentazione) {
        logBegin(className);
        String query = QUERY_SEARCH_OGGETTO_NEW;
        String dynamicInnerJoin = "";
        Map<String, Object> map = new HashMap<>();

        if (searchOggetto.getIdComune() != null) {
            map.put("idComune", searchOggetto.getIdComune());
            query += WHERE_ID_COMUNE;
        }

        if (StringUtils.isNotBlank(searchOggetto.getDenOggetto())) {
            map.put("denOggetto", searchOggetto.getDenOggetto().trim());
            query += WHERE_DEN_OGGETTO;
        }

        if (StringUtils.isNotBlank(searchOggetto.getCodScriva())) {
            map.put("codScriva", searchOggetto.getCodScriva().trim());
            query += WHERE_COD_SCRIVA_FONTE;
        }

        if (searchOggetto.getCodTipoOggettoSearchList() != null && !searchOggetto.getCodTipoOggettoSearchList().isEmpty()) {
            map.put("codTipologieOggetto", searchOggetto.getCodTipoOggettoSearchList());
            query += WHERE_COD_TIPO_OGGETTO;
        }

        if ((searchOggetto.getIdSoggetti() != null && !searchOggetto.getIdSoggetti().isEmpty()) || StringUtils.isNotBlank(searchOggetto.getCfSoggetto())) {
            map.put("idSoggetti", searchOggetto.getIdSoggetti());
            map.put("cfSoggetto", StringUtils.isNotBlank(searchOggetto.getCfSoggetto()) ? searchOggetto.getCfSoggetto().trim() : "");
            query += searchOggetto.getIdSoggetti() != null && !searchOggetto.getIdSoggetti().isEmpty() ? WHERE_ID_SOGGETTI : WHERE_CF_SOGGETTO;
        }

        if (searchOggetto.getIdIstanza() != null) {
            query += WHERE_STATI_ISTANZA;
            if (searchOggetto.getAnnoPresentazione() != null) {
                map.put("annoPresentazione", searchOggetto.getAnnoPresentazione());
                dynamicInnerJoin += INNER_JOIN_ANNO_PRESENTAZIONE;
            }
        }
        query = query.replace("__dynamic_inner_join__", dynamicInnerJoin);

        return findListByQuery(className, query, map);
    }

    /**
     * Save oggetto long.
     *
     * @param dto OggettoDTO
     * @return id record salvato
     */
    @Override
    public Long saveOggetto(OggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idTipologiaOggetto", dto.getIdTipologiaOggetto());
            map.put("idStatoOggetto", dto.getIdStatoOggetto());
            map.put("denOggetto", dto.getDenOggetto());
            map.put("desOggetto", dto.getDesOggetto());
            map.put("codOggettoFonte", dto.getCodOggettoFonte());
            map.put("codScriva", dto.getCodScriva());
            map.put("coordinataX", dto.getCoordinataX());
            map.put("coordinataY", dto.getCoordinataY());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("idMasterdata", dto.getIdMasterdata());
            map.put("idMasterdataOrigine", dto.getIdMasterdataOrigine());
            map.put("idFunzionario", dto.getIdFunzionario());
            map.put("dataAggiornamento", now);
            map.put("idIstanzaAggiornamento", dto.getIdIstanzaAggiornamento());

            map.put("gestUid", generateGestUID(dto.getDenOggetto() + dto.getCoordinataX() + dto.getCoordinataY() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_OGGETTO, null, null), params, keyHolder, new String[]{"id_oggetto", "cod_scriva"});
            Map<String, Object> keys = keyHolder.getKeys();

            if (returnValue > 0) {
                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGGETTO), params);
            }

            return ((Number) keys.get("id_oggetto")).longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update oggetto integer.
     *
     * @param dto OggettoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateOggetto(OggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            OggettoDTO oggetto = this.findByPK(dto.getIdOggetto());
            if (null == oggetto) {
                logError(className, "Record non trovato con id [" + dto.getIdOggetto() + "]");
                return -1;
            }

            int returnValue = 1;
            Long idFunzionarioOld = oggetto.getIdFunzionario() != null && oggetto.getIdFunzionario() > 0 ? oggetto.getIdFunzionario() : null;
            Long idIstanzaAggiornamentoOld = oggetto.getIdIstanzaAggiornamento() != null && oggetto.getIdIstanzaAggiornamento() > 0 ? oggetto.getIdIstanzaAggiornamento() : null;
            if (!dto.equals(oggetto)) {
                map.put("idOggetto", dto.getIdOggetto());
                map.put("idTipologiaOggetto", (dto.getIdTipologiaOggetto() != null ? dto.getIdTipologiaOggetto() : oggetto.getIdTipologiaOggetto()));
                if(dto.getIdStatoOggetto() != null && dto.getIdStatoOggetto() != 0L)
                	map.put("idStatoOggetto", dto.getIdStatoOggetto()); 
                else if(oggetto.getIdStatoOggetto() !=null && oggetto.getIdStatoOggetto() != 0L)
                	map.put("idStatoOggetto", oggetto.getIdStatoOggetto());
                else
                	map.put("idStatoOggetto", null);
                map.put("codScriva", oggetto.getCodScriva());
                map.put("denOggetto", dto.getDenOggetto());
                map.put("desOggetto", dto.getDesOggetto());
                map.put("codOggettoFonte", oggetto.getCodOggettoFonte());
                map.put("coordinataX", dto.getCoordinataX());
                map.put("coordinataY", dto.getCoordinataY());
                map.put("gestDataIns", oggetto.getGestDataIns());
                map.put("gestAttoreIns", oggetto.getGestAttoreIns());
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                map.put("idMasterdata", dto.getIdMasterdata());
                map.put("idMasterdataOrigine", oggetto.getIdMasterdataOrigine());
                map.put("idFunzionario", dto.getIdFunzionario() != null ? dto.getIdFunzionario() : idFunzionarioOld);
                map.put("gestUid", oggetto.getGestUID());
                map.put("dataAggiornamento", now);
                map.put("idIstanzaAggiornamento", dto.getIdIstanzaAggiornamento()!=null ? dto.getIdIstanzaAggiornamento() : idIstanzaAggiornamentoOld);
                MapSqlParameterSource params = getParameterValue(map);

                returnValue = template.update(getQuery(QUERY_UPDATE_OGGETTO, null, null), params);
                if (returnValue > 0) {
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_OGGETTO), params);
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
     * Delete oggetto integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    @Override
    public Integer deleteOggetto(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_OGGETTO, null, null), params);
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
     * @param idOggetto idOggetto
     * @return numero record cancellati
     */
    @Override
    public Integer deleteOggettoById(Long idOggetto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggetto", idOggetto);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_OGGETTO_BY_ID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update oggetto by oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    @Override
    public Integer updateOggettoByOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_OGGETTO_BY_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by pk oggetto dto.
     *
     * @param idOggetto idOggetto
     * @return OggettoDTO oggetto dto
     */
    @Override
    public OggettoDTO findByPK(Long idOggetto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggetto", idOggetto);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(getPrimaryKeySelect(), params, getRowMapper());
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
     * @return RowMapper<OggettoDTO>
     */
    @Override
    public RowMapper<OggettoDTO> getRowMapper() throws SQLException {
        return new OggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<OggettoExtendedDTO>
     */
    @Override
    public RowMapper<OggettoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new OggettoExtendedRowMapper();
    }


    /**
     * The type Oggetto extended row mapper.
     */
    public static class OggettoExtendedRowMapper implements RowMapper<OggettoExtendedDTO> {

        /**
         * Instantiates a new Oggetto extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoExtendedRowMapper() throws SQLException {
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
        public OggettoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoExtendedDTO bean = new OggettoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoExtendedDTO bean) throws SQLException {
            bean.setIdOggetto(rs.getLong("id_oggetto"));
            TipologiaOggettoExtendedDTO tipologiaOggetto = new TipologiaOggettoExtendedDTO();
            populateBeanTipologiaOggetto(rs, tipologiaOggetto);
            bean.setTipologiaOggetto(tipologiaOggetto);
            StatoOggettoDTO statoOggetto = new StatoOggettoDTO();
            populateBeanStatoOggetto(rs, statoOggetto);
            bean.setStatoOggetto(statoOggetto);
            bean.setCodOggettoFonte(rs.getString("cod_oggetto_fonte"));
            bean.setCodScriva(rs.getString("cod_scriva"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setCoordinataX(rs.getBigDecimal("coordinata_x"));
            bean.setCoordinataY(rs.getBigDecimal("coordinata_y"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine"));
            bean.setIdMasterdata(rs.getLong("id_masterdata"));
            bean.setIdIstanzaAggiornamento(rs.getLong("id_istanza_aggiornamento"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanTipologiaOggetto(ResultSet rs, TipologiaOggettoExtendedDTO bean) throws SQLException {
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            NaturaOggettoDTO naturaOggetto = new NaturaOggettoDTO();
            populateBeanNaturaOggetto(rs, naturaOggetto);
            bean.setNaturaOggetto(naturaOggetto);
            bean.setCodTipologiaOggetto(rs.getString("cod_tipologia_oggetto"));
            bean.setDesTipologiaOggetto(rs.getString("des_tipologia_oggetto"));
        }

        private void populateBeanNaturaOggetto(ResultSet rs, NaturaOggettoDTO bean) throws SQLException {
            bean.setIdNaturaOggetto(rs.getLong("id_natura_oggetto"));
            bean.setCodNaturaOggetto(rs.getString("cod_natura_oggetto"));
            bean.setDesNaturaOggetto(rs.getString("des_natura_oggetto"));
        }


        private void populateBeanStatoOggetto(ResultSet rs, StatoOggettoDTO bean) throws SQLException {
            bean.setIdStatoOggetto(rs.getLong("id_stato_oggetto"));
            bean.setCodStatoOggetto(rs.getString("cod_stato_oggetto"));
            bean.setDesStatoOggetto(rs.getString("des_stato_oggetto"));
        }
    }

    /**
     * The type Oggetto row mapper.
     */
    public static class OggettoRowMapper implements RowMapper<OggettoDTO> {

        /**
         * Instantiates a new Oggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoRowMapper() throws SQLException {
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
        public OggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoDTO bean = new OggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoDTO bean) throws SQLException {
            bean.setIdOggetto(rs.getLong("id_oggetto"));
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setIdStatoOggetto(rs.getLong("id_stato_oggetto"));
            bean.setCodOggettoFonte(rs.getString("cod_oggetto_fonte"));
            bean.setCodScriva(rs.getString("cod_scriva"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setCoordinataX(rs.getBigDecimal("coordinata_x"));
            bean.setCoordinataY(rs.getBigDecimal("coordinata_y"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine"));
            bean.setIdMasterdata(rs.getLong("id_masterdata"));
            bean.setIdIstanzaAggiornamento(rs.getLong("id_istanza_aggiornamento"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}