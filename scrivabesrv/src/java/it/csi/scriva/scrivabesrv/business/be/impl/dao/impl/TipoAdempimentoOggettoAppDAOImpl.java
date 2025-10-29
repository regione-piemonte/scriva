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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoAdempimentoOggettoAppDAO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProfiloAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoOggettoAppExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoProfiloExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoOggettoAppDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo adempimento oggetto app dao.
 */
public class TipoAdempimentoOggettoAppDAOImpl extends ScrivaBeSrvGenericDAO<TipoAdempimentoOggettoAppDTO> implements TipoAdempimentoOggettoAppDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_TIPI_ADEMPIMENTO_OGGETTO_APP = "SELECT DISTINCT sti.id_istanza, sria.cf_attore,\n" +
            "srtaoa.flg_clona_istanza,\n" +
            "sdoa.*, sdoa.id_oggetto_app AS oggetto_app_id,\n" +
            "sdtoa.*, sdtoa.id_tipo_ogg_app AS tipo_ogg_app_id,\n" +
            "sdsi.*, sdsi.id_stato_istanza AS stato_istanza_id,\n" +
            "srsia.ind_modificabile,\n" +
            "srtap.flg_sola_lettura,\n" +
            "sdpa.*, sdpa.id_profilo_app AS profilo_app_id,\n" +
            "sdca.*, sdca.id_componente_app AS componente_app_id,\n" +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id,\n" +
            "sda.*, sda.id_ambito AS ambito_id,\n" +
            "sdte.*, sdte.id_tipo_evento AS tipo_evento_id\n" +
            "FROM scriva_t_istanza sti\n" +
            "INNER JOIN scriva_r_istanza_attore sria ON sria.id_istanza = sti.id_istanza\n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = sria.id_profilo_app\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdpa.id_componente_app\n" +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sdpa.id_profilo_app\n" +
            "INNER JOIN scriva_r_tipo_adempi_ogg_app srtaoa ON srtaoa.id_tipo_adempi_profilo = srtap.id_tipo_adempi_profilo\n" +
            "INNER JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srtaoa.id_oggetto_app\n" +
            "INNER JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = srtaoa.id_stato_istanza\n" +
            "INNER JOIN scriva_r_stato_istanza_adempi srsia ON srsia.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sti.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito\n" +
            "LEFT OUTER JOIN scriva_d_tipo_evento sdte ON sdte.id_tipo_evento = sdoa.id_tipo_evento\n" +
            "WHERE 1 = 1\n" +
            "AND sria.cf_attore = :cfAttore\n" +
            "AND UPPER(sdca.cod_componente_app) = 'FO' " +
            "AND sdsi.id_stato_istanza = sti.id_stato_istanza " +
            "AND sdtoa.cod_tipo_ogg_app = 'AZ_AVANZATA' " +
            "AND sria.data_revoca IS NULL";

    private static final String QUERY_LOAD_TIPO_ADEMPI_OGG_APP = "SELECT srtaoa.*\n" +
            ", sdoa.*\n" +
            ", sdtoa.*, sdtoa.id_tipo_ogg_app AS tipo_ogg_app_id\n" +
            ", sdsi.*, sdsi.id_stato_istanza AS stato_istanza_id\n" +
            ", srtap.*\n" +
            ", sdpa.*, sdpa.id_profilo_app AS profilo_app_id\n" +
            ", sdca.*, sdca.id_componente_app AS componente_app_id\n" +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id\n" +
            ", sda.*, sda.id_ambito AS ambito_id\n" +
            ", sdte.*, sdte.id_tipo_evento AS tipo_evento_id\n" +
            "FROM _replaceTableName_ srtaoa\n" +
            "INNER JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srtaoa.id_oggetto_app\n" +
            "inner JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = srtaoa.id_stato_istanza\n" +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_tipo_adempi_profilo = srtaoa.id_tipo_adempi_profilo\n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srtap.id_profilo_app\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdpa.id_componente_app\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = srtap.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito\n" +
            "LEFT OUTER JOIN scriva_d_tipo_evento sdte ON sdte.id_tipo_evento = sdoa.id_tipo_evento\n" +
            "WHERE 1=1\n";

    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_BY_ID = QUERY_LOAD_TIPI_ADEMPIMENTO_OGGETTO_APP + "WHERE id_tipo_oggetto_app = :idTipoOggettoApp";

    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_BY_ID_TIPO_ADEMPI_PROFILO = QUERY_LOAD_TIPO_ADEMPI_OGG_APP + "AND srtaoa.id_tipo_adempi_profilo = :idTipoAdempimentoProfilo";

    /*
    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_FOR_ACTIONS = "SELECT DISTINCT sti.id_istanza,\n" +
            "sria.cf_attore,\n" +
            "srtaoa.flg_clona_istanza,\n" +
            "sdoa.*, sdoa.id_oggetto_app AS oggetto_app_id,\n" +
            "sdtoa.*, sdtoa.id_tipo_ogg_app AS tipo_ogg_app_id,\n" +
            "sdsi.*,\n" +
            "sdsi.id_stato_istanza AS stato_istanza_id,\n" +
            "srsia.ind_modificabile,\n" +
            "srtap.flg_sola_lettura,\n" +
            "sdpa.*,\n" +
            "sdpa.id_profilo_app AS profilo_app_id,\n" +
            "sdca.*,\n" +
            "sdca.id_componente_app AS componente_app_id,\n" +
            "sdta.*,\n" +
            "sdta.id_tipo_adempimento AS tipo_adempimento_id,\n" +
            "sda.*,\n" +
            "sda.id_ambito AS ambito_id,\n" +
            "CASE\n" +
            "\tWHEN srsia.ind_modificabile LIKE 'FO%' AND srtap.flg_sola_lettura = 0 THEN 'WRITE'\n" +
            "\tWHEN srsia.ind_modificabile NOT LIKE 'FO%' AND srtap.flg_sola_lettura = 0 THEN 'WRITE_LOCK'\n" +
            "\tWHEN srtap.flg_sola_lettura = 1 THEN 'READ_ONLY'\n" +
            "\tELSE 'READ_ONLY'\n" +
            "END AS stato_attore,\n" +
            "CASE\n" +
            "\tWHEN sdpa.cod_profilo_app = 'COMPILANTE' THEN 1\n" +
            "\tWHEN sdpa.cod_profilo_app = 'RICHIEDENTE' THEN 2\n" +
            "\tWHEN sdpa.cod_profilo_app = 'ABILITATO_GEST' THEN 3\n" +
            "\tWHEN sdpa.cod_profilo_app = 'ABILITATO_CONS' THEN 4\n" +
            "\tELSE 5\n" +
            "END AS ord_profilo_app\n" +
            "FROM scriva_r_istanza_attore sria\n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sria.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sti.id_stato_istanza \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sti.id_adempimento = sdad.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito \n" +
            "INNER JOIN  scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sria.id_profilo_app  \n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = sria.id_profilo_app \n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdpa.id_componente_app \n" +
            "LEFT OUTER JOIN scriva_r_tipo_adempi_ogg_app srtaoa ON srtaoa.id_tipo_adempi_profilo = srtap.id_tipo_adempi_profilo AND sti.id_stato_istanza = srtaoa.id_stato_istanza \n" +
            "LEFT OUTER JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srtaoa.id_oggetto_app  \n" +
            "LEFT OUTER JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app \n" +
            "INNER JOIN scriva_r_stato_istanza_adempi srsia ON srsia.id_adempimento = sti.id_adempimento AND srsia.id_stato_istanza = sti.id_stato_istanza \n" +
            "WHERE sria.id_istanza  = :idIstanza\n" +
            "AND sria.cf_attore = :cfAttore\n" +
            "AND sria.data_revoca IS NULL\n" +
            "AND srtap.id_tipo_adempimento = sdta.id_tipo_adempimento\n";
    */
    private static final String QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_FOR_ACTIONS = "SELECT DISTINCT sti.id_istanza,\n" +
            "sria.cf_attore,\n" +
            "srtaoa.flg_clona_istanza,\n" +
            "sdoa.*, sdoa.id_oggetto_app AS oggetto_app_id,\n" +
            "sdtoa.*, sdtoa.id_tipo_ogg_app AS tipo_ogg_app_id,\n" +
            "sdsi.*,\n" +
            "sdsi.id_stato_istanza AS stato_istanza_id,\n" +
            "srsia.ind_modificabile,\n" +
            "srtap.flg_sola_lettura,\n" +
            "sdpa.*,\n" +
            "sdpa.id_profilo_app AS profilo_app_id,\n" +
            "sdca.*,\n" +
            "sdca.id_componente_app AS componente_app_id,\n" +
            "sdta.*,\n" +
            "sdta.id_tipo_adempimento AS tipo_adempimento_id,\n" +
            "sda.*,\n" +
            "sda.id_ambito AS ambito_id,\n" +
            "CASE \n" +
            "    WHEN sdga.cod_gestione_attore = 'WRITE' AND srsia.ind_modificabile NOT LIKE '%FO%' AND srtap.flg_sola_lettura = 0 THEN 'WRITE_LOCK'\n" +
            "    WHEN sdga.cod_gestione_attore like 'LOCK_QDR%' AND srsia.ind_modificabile NOT LIKE '%FO%' AND srtap.flg_sola_lettura = 0 THEN 'WRITE_LOCK'\n" +
            "    ELSE sdga.cod_gestione_attore\n" +
            "END AS stato_attore,\n" +
            "sdga.ordinamento_gestione_attore AS ord_profilo_app,\n" +
            "sdte.*, sdte.id_tipo_evento AS tipo_evento_id\n" +
            "FROM scriva_r_istanza_attore sria\n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sria.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sti.id_stato_istanza \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sti.id_adempimento = sdad.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito \n" +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sria.id_profilo_app AND srtap.id_tipo_adempimento = sdta.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srtap.id_profilo_app \n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdpa.id_componente_app \n" +
            "LEFT OUTER JOIN scriva_d_gestione_attore sdga ON sdga.id_gestione_attore = sdpa.id_gestione_attore \n" +
            "LEFT OUTER JOIN scriva_r_tipo_adempi_ogg_app srtaoa ON srtaoa.id_tipo_adempi_profilo = srtap.id_tipo_adempi_profilo AND sti.id_stato_istanza = srtaoa.id_stato_istanza \n" +
            "LEFT OUTER JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srtaoa.id_oggetto_app  \n" +
            "LEFT OUTER JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app \n" +
            "LEFT OUTER JOIN scriva_d_tipo_evento sdte ON sdte.id_tipo_evento = sdoa.id_tipo_evento \n" +
            "INNER JOIN scriva_r_stato_istanza_adempi srsia ON srsia.id_adempimento = sti.id_adempimento AND srsia.id_stato_istanza = sti.id_stato_istanza \n" +
            "WHERE sria.data_revoca IS NULL\n" +
            "AND sria.cf_attore = :cfAttore\n";

    private static final String QUERY_LOAD_BO_TIPO_ADEMPIMENTO_OGGETTO_APP_FOR_ACTIONS = "SELECT DISTINCT sti.id_istanza, \n" +
            "stf.cf_funzionario,\n" +
            "srtaoa.flg_clona_istanza,\n" +
            "sdoa.*, sdoa.id_oggetto_app AS oggetto_app_id,\n" +
            "sdtoa.*, sdtoa.id_tipo_ogg_app AS tipo_ogg_app_id,\n" +
            "sti.*,\n" +
            "sti.id_stato_istanza AS stato_istanza_id,\n" +
            "srtap.flg_sola_lettura,\n" +
            "sdpa.*,\n" +
            "sdpa.id_profilo_app AS profilo_app_id,\n" +
            "sdca.*,\n" +
            "sdca.id_componente_app AS componente_app_id,\n" +
            "sdta.*,\n" +
            "sdta.id_tipo_adempimento AS tipo_adempimento_id,\n" +
            "sda.*,\n" +
            "sda.id_ambito AS ambito_id,\n" +
            "sdga.cod_gestione_attore AS stato_attore,\n" +
            "sdga.ordinamento_gestione_attore AS ord_profilo_app,\n" +
            "sdte.*, sdte.id_tipo_evento AS tipo_evento_id\n" +
            "FROM scriva_t_funzionario AS stf\n" +
            "INNER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario AND srfp.data_fine_validita IS NULL\n" +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = srfp.id_profilo_app\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = srtap.id_tipo_adempimento AND sdta.flg_attivo = 1\n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito\n" +
            "INNER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdpa.id_componente_app\n" +
            "LEFT OUTER JOIN scriva_d_gestione_attore sdga ON sdga.id_gestione_attore = sdpa.id_gestione_attore\n" +
            "INNER JOIN (SELECT sti.id_istanza, sdta.id_tipo_adempimento AS id_tipo_adempimento_ist, sdsi.*, sric.id_competenza_territorio AS istanza_competenza\n" +
            "                FROM scriva_t_istanza sti \n" +
            "                INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento = sti.id_adempimento \n" +
            "                INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento \n" +
            "                INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sti.id_stato_istanza\n" + //"AND sdsi.ind_visibile LIKE '%' || :codComponenteApp || '%'" //Filtro su stato istanza
            "                LEFT OUTER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = sti.id_istanza AND sric.flg_autorita_principale = 1 AND sric.data_fine_validita IS NULL\n" +
            "                WHERE 1=1\n" +
            "                __where_id_istanza__) AS sti ON id_tipo_adempimento_ist = srtap.id_tipo_adempimento\n" +
            "INNER JOIN scriva_r_funzionario_compete srfc ON srfc.id_funzionario = stf.id_funzionario AND (srfc.id_competenza_territorio = istanza_competenza OR istanza_competenza IS NULL) AND srfc.data_fine_validita IS NULL\n" +
            //"INNER JOIN scriva_r_funzionario_compete srfc ON srfc.id_funzionario = stf.id_funzionario AND srfc.id_competenza_territorio = istanza_competenza AND srfc.data_fine_validita IS NULL\n" +
            "LEFT OUTER JOIN scriva_r_tipo_adempi_ogg_app srtaoa ON srtaoa.id_tipo_adempi_profilo = srtap.id_tipo_adempi_profilo AND sti.id_stato_istanza = srtaoa.id_stato_istanza\n" +
            "LEFT OUTER JOIN scriva_d_oggetto_app sdoa ON sdoa.id_oggetto_app = srtaoa.id_oggetto_app\n" +
            "LEFT OUTER JOIN scriva_d_tipo_oggetto_app sdtoa ON sdtoa.id_tipo_ogg_app = sdoa.id_tipo_ogg_app\n" +
            "LEFT OUTER JOIN scriva_d_tipo_evento sdte ON sdte.id_tipo_evento = sdoa.id_tipo_evento\n" +
            "WHERE stf.cf_funzionario = :cfAttore\n";


    private static final String WHERE_ID_ISTANZA = "AND sti.id_istanza = :idIstanza\n";
    private static final String WHERE_ID_ISTANZA_LIST = "AND sti.id_istanza IN (:idIstanzaList)\n";
    private static final String WHERE_COD_COMPONENTE_APP = "AND UPPER(sdca.cod_componente_app) = :codComponenteApp\n";
    private static final String WHERE_COD_TIPO_OGG_APP = "AND sdtoa.cod_tipo_ogg_app = :codTipoOggettoApp\n";

    
    /**
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettiApp() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_TIPI_ADEMPIMENTO_OGGETTO_APP, null);
    }

    /**
     * @param idTipoAdempimentoOggettoApp idTipoAdempimentoOggettoApp
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppById(Long idTipoAdempimentoOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAdempimentoOggettoApp", idTipoAdempimentoOggettoApp);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param idTipoAdempimentoProfilo idTipoAdempimentoProfilo
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppByIdTipoAdempimentoProfilo(Long idTipoAdempimentoProfilo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAdempimentoProfilo", idTipoAdempimentoProfilo);
        return findListByQuery(className, QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_BY_ID_TIPO_ADEMPI_PROFILO, map);
    }

    /**
     * @param codOggettoApp codOggettoApp
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppByCodeOggettoApp(String codOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codOggettoApp", codOggettoApp);
        return findListByQuery(className, QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_BY_ID, map);
    }

    /**
     * @param idIstanza         idIstanza
     * @param cfAttore          codice fiscale attore
     * @param codComponenteApp  codice componente applicativo
     * @param codTipoOggettoApp codice tipo oggetto applicativo
     * @return List<TipoAdempimentoOggettoAppExtendedDTO>
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppForAzioni(Long idIstanza, String cfAttore, String codComponenteApp, String codTipoOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = ComponenteAppEnum.BO.name().equalsIgnoreCase(codComponenteApp) ? QUERY_LOAD_BO_TIPO_ADEMPIMENTO_OGGETTO_APP_FOR_ACTIONS : QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_FOR_ACTIONS;
        map.put("cfAttore", cfAttore);

        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query = query.replace("__where_id_istanza__", WHERE_ID_ISTANZA);
            query += WHERE_ID_ISTANZA;
        }

        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COD_COMPONENTE_APP;
        }

        if (StringUtils.isNotBlank(codTipoOggettoApp)) {
            map.put("codTipoOggettoApp", codTipoOggettoApp);
            query += WHERE_COD_TIPO_OGG_APP;
        }
        query += "\nORDER BY sdga.ordinamento_gestione_attore, stato_attore\n";
        return findListByQuery(className, query, map);
    }

    /**
     * Load tipo adempimento oggetto app for azioni list.
     *
     * @param idIstanzaList     the id istanza list
     * @param cfAttore          the cf attore
     * @param codComponenteApp  the cod componente app
     * @param codTipoOggettoApp the cod tipo oggetto app
     * @return the list
     */
    @Override
    public List<TipoAdempimentoOggettoAppExtendedDTO> loadTipoAdempimentoOggettoAppForAzioni(List<Long> idIstanzaList, String cfAttore, String codComponenteApp, String codTipoOggettoApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = ComponenteAppEnum.BO.name().equalsIgnoreCase(codComponenteApp) ? QUERY_LOAD_BO_TIPO_ADEMPIMENTO_OGGETTO_APP_FOR_ACTIONS : QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_FOR_ACTIONS;
        map.put("cfAttore", cfAttore);

        if (CollectionUtils.isNotEmpty(idIstanzaList)) {
            map.put("idIstanzaList", idIstanzaList);
            query = query.replace("__where_id_istanza__", WHERE_ID_ISTANZA_LIST);
            query += WHERE_ID_ISTANZA_LIST;
        }

        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COD_COMPONENTE_APP;
        }

        if (StringUtils.isNotBlank(codTipoOggettoApp)) {
            map.put("codTipoOggettoApp", codTipoOggettoApp);
            query += WHERE_COD_TIPO_OGG_APP;
        }
        query += "\nORDER BY sdga.ordinamento_gestione_attore, stato_attore\n";
        return findListByQuery(className, query, map);
    }


    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_TIPO_ADEMPIMENTO_OGGETTO_APP_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoAdempimentoOggettoAppDTO>
     */
    @Override
    public RowMapper<TipoAdempimentoOggettoAppDTO> getRowMapper() throws SQLException {
        return new TipoAdempimentoOggettoAppRowMapper();
    }

    @Override
    public RowMapper<TipoAdempimentoOggettoAppExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TipoAdempimentoOggettoAppExtendedRowMapper();
    }

    /**
     * The type Tipo adempimento oggetto app extended row mapper.
     */
    public static class TipoAdempimentoOggettoAppExtendedRowMapper implements RowMapper<TipoAdempimentoOggettoAppExtendedDTO> {

        /**
         * Instantiates a new Tipo adempimento oggetto app extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAdempimentoOggettoAppExtendedRowMapper() throws SQLException {
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
        public TipoAdempimentoOggettoAppExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAdempimentoOggettoAppExtendedDTO bean = new TipoAdempimentoOggettoAppExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoAdempimentoOggettoAppExtendedDTO bean) throws SQLException {

            Long idIstanza;
            try {
                idIstanza = rs.getLong("id_istanza");
            } catch (SQLException e) {
                idIstanza = null;
            }
            bean.setIdIstanza(idIstanza);

            Long idTipoAdempimentoOggettoApp;
            try {
                idTipoAdempimentoOggettoApp = rs.getLong("id_tipo_adempi_ogg_app");
            } catch (SQLException e) {
                idTipoAdempimentoOggettoApp = null;
            }
            bean.setIdTipoAdempimentoOggettoApp(idTipoAdempimentoOggettoApp);

            OggettoAppExtendedDTO oggettoApp = new OggettoAppExtendedDTO();
            populateBeanOggettoApp(rs, oggettoApp);
            bean.setOggettoApp(oggettoApp.getIdOggettoApp() > 0 ? oggettoApp : null);

            StatoIstanzaDTO statoIstanza = new StatoIstanzaDTO();
            populateBeanStatoIstanza(rs, statoIstanza);
            bean.setStatoIstanza(statoIstanza);

            TipoAdempimentoProfiloExtendedDTO tipoAdempimentoProfilo = new TipoAdempimentoProfiloExtendedDTO();
            populateBeanTipoAdempimentoProfilo(rs, tipoAdempimentoProfilo);
            bean.setTipoAdempimentoProfilo(tipoAdempimentoProfilo);

            bean.setFlgClonaIstanza(1 == rs.getInt("flg_clona_istanza") ? Boolean.TRUE : Boolean.FALSE);

            Long idAdempimentoClonaIstanza;
            try {
                idAdempimentoClonaIstanza = rs.getLong("id_adempimento_clona_istanza");
            } catch (SQLException e) {
                idAdempimentoClonaIstanza = null;
            }
            bean.setIdAdempimentoClonaIstanza(idAdempimentoClonaIstanza);

            String statoAttore;
            try {
                statoAttore = rs.getString("stato_attore");
            } catch (SQLException e) {
                statoAttore = null;
            }
            bean.setStatoAttore(statoAttore);

            Integer ordinamentoProfiloApp;
            try {
                ordinamentoProfiloApp = rs.getInt("ord_profilo_app");
            } catch (SQLException e) {
                ordinamentoProfiloApp = null;
            }
            bean.setOrdinamentoProfiloApp(ordinamentoProfiloApp);
        }

        private void populateBeanOggettoApp(ResultSet rs, OggettoAppExtendedDTO bean) throws SQLException {
            bean.setIdOggettoApp(rs.getLong("id_oggetto_app"));

            TipoOggettoAppDTO tipoOggettoApp = new TipoOggettoAppDTO();
            populateBeanTipoOggettoApp(rs, tipoOggettoApp);
            bean.setTipoOggettoApp(tipoOggettoApp);

            TipoEventoDTO tipoEvento = new TipoEventoDTO();
            populateBeanTipoEvento(rs, tipoEvento);
            bean.setTipoEvento(tipoEvento);

            bean.setCodOggettoApp(rs.getString("cod_oggetto_app"));
            bean.setDesOggettoApp(rs.getString("des_oggetto_app"));
            bean.setFlgPrevistoDaGestoreProcesso(rs.getInt("flg_previsto_da_gestore_processo") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanTipoOggettoApp(ResultSet rs, TipoOggettoAppDTO bean) throws SQLException {
            bean.setIdTipoOggettoApp(rs.getLong("tipo_ogg_app_id"));
            bean.setCodTipoOggettoApp(rs.getString("cod_tipo_ogg_app"));
            bean.setDesTipoOggettoApp(rs.getString("des_tipo_ogg_app"));
        }

        private void populateBeanTipoEvento(ResultSet rs, TipoEventoDTO bean) throws SQLException {
            if (rs.getLong("tipo_evento_id") > 0) {
                bean.setIdTipoEvento(rs.getLong("tipo_evento_id"));
                bean.setCodTipoEvento(rs.getString("cod_tipo_evento"));
                bean.setDesTipoEvento(rs.getString("des_tipo_evento"));
                bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo"));
            }
        }

        private void populateBeanStatoIstanza(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("stato_istanza_id"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setFlgStoricoIstanza(1 == rs.getInt("flg_storico_istanza") ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndVisibile(rs.getString("ind_visibile"));
        }

        private void populateBeanTipoAdempimentoProfilo(ResultSet rs, TipoAdempimentoProfiloExtendedDTO bean) throws SQLException {
            Long idTipoAdempimentoProfilo;
            try {
                idTipoAdempimentoProfilo = rs.getLong("id_tipo_adempi_profilo");
            } catch (SQLException e) {
                idTipoAdempimentoProfilo = null;
            }
            bean.setIdTipoAdempimentoProfilo(idTipoAdempimentoProfilo);

            ProfiloAppExtendedDTO profiloApp = new ProfiloAppExtendedDTO();
            populateBeanProfiloApp(rs, profiloApp);
            bean.setProfiloApp(profiloApp);

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            populateBeanTipoAdempimento(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento);

            bean.setFlgSoloLettura(1 == rs.getInt("flg_sola_lettura") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanProfiloApp(ResultSet rs, ProfiloAppExtendedDTO bean) throws SQLException {
            bean.setIdProfiloApp(rs.getLong("profilo_app_id"));

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateComponenteApp(rs, componenteApp);
            bean.setComponenteApp(componenteApp);

            bean.setCodProfiloApp(rs.getString("cod_profilo_app"));
            bean.setDesProfiloApp(rs.getString("des_profilo_app"));
            bean.setFlgProfiloIride(1 == rs.getInt("flg_profilo_iride") ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCompetenza(1 == rs.getInt("flg_competenza") ? Boolean.TRUE : Boolean.FALSE);

            Integer ordinamentoProfiloApp;
            try {
                ordinamentoProfiloApp = rs.getInt("ord_profilo_app");
            } catch (SQLException e) {
                ordinamentoProfiloApp = null;
            }
            bean.setOrdinamentoProfiloApp(ordinamentoProfiloApp);
        }

        private void populateComponenteApp(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("componente_app_id"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }

        private void populateBeanTipoAdempimento(ResultSet rs, TipoAdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            bean.setAmbito(ambito);
            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }
    }

    /**
     * The type Tipo adempimento oggetto app row mapper.
     */
    public static class TipoAdempimentoOggettoAppRowMapper implements RowMapper<TipoAdempimentoOggettoAppDTO> {

        /**
         * Instantiates a new Tipo adempimento oggetto app row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoAdempimentoOggettoAppRowMapper() throws SQLException {
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
        public TipoAdempimentoOggettoAppDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoAdempimentoOggettoAppDTO bean = new TipoAdempimentoOggettoAppDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoAdempimentoOggettoAppDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipoAdempimentoOggettoApp(rs.getLong("id_tipo_adempi_ogg_app"));
            bean.setIdOggettoApp(rs.getLong("id_oggetto_app"));
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setIdTipoAdempimentoProfilo(rs.getLong("id_tipo_adempi_profilo"));
            bean.setFlgClonaIstanza(1 == rs.getInt("flg_clona_istanza") ? Boolean.TRUE : Boolean.FALSE);
            bean.setIdAdempimentoClonaIstanza(rs.getLong("id_adempimento_clona_istanza"));
        }
    }

}