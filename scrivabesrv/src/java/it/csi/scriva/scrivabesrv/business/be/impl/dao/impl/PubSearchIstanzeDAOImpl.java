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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.PubSearchIstanzeDAO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import it.csi.scriva.scrivabesrv.dto.PubAdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubSearchDTO;
import it.csi.scriva.scrivabesrv.dto.PubStatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SintesiDTO;
import it.csi.scriva.scrivabesrv.dto.StatoProcedimentoStataleDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.FasePubblicazioneEnum;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Search dao.
 *
 * @author CSI PIEMONTE
 */
public class PubSearchIstanzeDAOImpl extends ScrivaBeSrvGenericDAO<PubIstanzaDTO> implements PubSearchIstanzeDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_DATA_ISTANZE_STORICO_BY_ID_ISTANZA = "select data_inizio_osservazioni\r\n" + 
    		"from (\r\n" + 
    		"select  data_inizio_osservazioni\r\n" + 
    		"from scriva_t_istanza ist\r\n" + 
    		"where ist.id_istanza  = :idIstanza \r\n" + 
    		"and  ist.data_inizio_osservazioni IS not NULL and DATE(ist.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    		"union \r\n" + 
    		"select data_inizio_osservazioni \r\n" + 
    		"from scriva_r_istanza_storico sris\r\n" + 
    		"where sris.id_istanza = :idIstanza \r\n" + 
    		"and sris.data_inizio_osservazioni IS not NULL and DATE(sris.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    		") as elenco_oss\r\n" + 
    		"order by 1";
//    	"SELECT sris.data_inizio_osservazioni   " +
//        "  FROM scriva_r_istanza_storico sris   " +
//    	"  WHERE sris.id_istanza = :idIstanza   " +
//        "  AND sris.data_inizio_osservazioni IS not NULL AND DATE(sris.data_inizio_osservazioni) <= DATE(NOW()) " +
//        " ORDER BY data_inizio_osservazioni     ";
    
    private static final String QUERY_MAX_DATA_ISTANZE_STORICO_BY_ID_ISTANZA = "SELECT \r\n" + 
    		"   distinct  FIRST_VALUE(data_fine_osservazioni) \r\n" + 
    		"    OVER(\r\n" + 
    		"        ORDER BY data_inizio_osservazioni desc\r\n" + 
    		"    ) data_fine_osservazioni\r\n" + 
    		"    from\r\n" + 
    		"(select  distinct data_inizio_osservazioni , data_fine_osservazioni \r\n" + 
    		"from scriva_t_istanza ist\r\n" + 
    		"where ist.id_istanza  = :idIstanza \r\n" + 
    		"and  ist.data_inizio_osservazioni IS not null and DATE(ist.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    		"union \r\n" + 
    		"select distinct data_inizio_osservazioni , data_fine_osservazioni \r\n" + 
    		"from scriva_r_istanza_storico sris\r\n" + 
    		"where sris.id_istanza = :idIstanza \r\n" + 
    		"and sris.data_inizio_osservazioni IS not NULL and DATE(sris.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    		") as elenco_oss\r\n" + 
    		"";
//    	"SELECT MAX (sris.data_fine_osservazioni)   " +
//        "  FROM scriva_r_istanza_storico sris   " +
//    	"  WHERE sris.id_istanza = :idIstanza   ";
    
    private static final String WHERE_ID_ISTANZA = "AND sti.id_istanza = :idIstanza ";

    private static final String QUERY_LOAD_PUB_ISTANZE_BY_ID = "SELECT sti.*, sdsi.*, sda.*, sdep.*, sdta.*, 'false' as flg_osservazioni, DATE(sti.data_fine_osservazioni) , fa.fase_pubblicazione, date(null) AS data_atto_conclusivo, 0 AS CONTATORE_PRATICHE\n" +
            "FROM scriva_t_istanza sti\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "INNER JOIN scriva_d_adempimento sda on sti.id_adempimento = sda.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento\n" +
            "LEFT JOIN scriva_d_esito_procedimento sdep on sdep.id_esito_procedimento = sti.id_esito_procedimento\n" +
            "INNER JOIN (\n" +
            "    SELECT sti.id_istanza ,\n" +
            "        CASE WHEN sdsi.cod_stato_istanza = 'CONCLUSA' THEN 'CONCLUSA'\n" +
            "             WHEN (sti.data_fine_osservazioni IS NOT NULL AND DATE(sti.data_fine_osservazioni) >= DATE(NOW())) THEN 'CONSULTA'\n" +
            "             ELSE 'IN_CORSO'\n" +
            "        END AS fase_pubblicazione\n" +
            "    FROM scriva_t_istanza sti\n" +
            "    INNER JOIN scriva_d_stato_istanza sdsi ON sti.id_stato_istanza = sdsi.id_stato_istanza\n" +
            ") AS fa ON fa.id_istanza = sti.id_istanza\n" +
            "WHERE 1 = 1\n" +
            WHERE_ID_ISTANZA;

    private static final String MAPPE = "SELECT json_build_object (\r\n" +
            "    'service',json_build_array (\r\n" +
            "        json_build_object (    \r\n" +
            "            'label', label_adempimento,\r\n" +
            "            'level', json_agg(levels)\r\n" +
            "        )\r\n" +
            "    )\r\n" +
            ")\r\n" +
            "FROM (\r\n" +
            "SELECT label_adempimento,\r\n" +
            "json_build_object (\r\n" +
            "    'label', LABEL, \r\n" +
            "    'type', TYPE, \r\n" +
            "    'enable', ENABLE, \r\n" +
            "    'geojson', json_agg(geojson::json)\r\n" +
            ") AS levels\r\n" +
            "FROM (\r\n" +
            "    SELECT \r\n" +
            "    sdta.des_tipo_adempimento AS label_adempimento,\r\n" +
            "    srglv.label_pubblicazione As LABEL,\r\n" +
            "    srglv.type_geojson AS type,\r\n" +
            "    'true' AS ENABLE,\r\n" +
            "    public.ST_AsGeoJSON((sgloi.geometria),15,0) As geojson\r\n" +
            "    FROM scriva_t_istanza sti \r\n" +
            "    INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento =sti.id_adempimento\r\n" +
            "    INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento\r\n" +
            "    INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_istanza = sti.id_istanza\r\n" +
            "    LEFT JOIN scriva_geo_linea_ogg_istanza sgloi ON sgloi.id_oggetto_istanza = stoi.id_oggetto_istanza\r\n" +
            "    INNER JOIN scriva_r_geeco_layer_virtuali srglv ON srglv.id_virtuale = sgloi.id_virtuale\r\n" +
            "    WHERE 1 =1 \r\n" +
            "    AND sti.id_istanza = :idIstanza\r\n" +
            "UNION\r\n" +
            "    SELECT \r\n" +
            "    sdta.des_tipo_adempimento AS label_adempimento,\r\n" +
            "    srglv.label_pubblicazione As LABEL,\r\n" +
            "    srglv.type_geojson AS type,\r\n" +
            "    'true' AS ENABLE,\r\n" +
            "    public.ST_AsGeoJSON((sgloi.geometria),15,0) As geojson\r\n" +
            "    FROM scriva_t_istanza sti \r\n" +
            "    INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento =sti.id_adempimento\r\n" +
            "    INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento\r\n" +
            "    INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_istanza = sti.id_istanza\r\n" +
            "    LEFT JOIN scriva_geo_area_ogg_istanza sgloi ON sgloi.id_oggetto_istanza = stoi.id_oggetto_istanza\r\n" +
            "    INNER JOIN scriva_r_geeco_layer_virtuali srglv ON srglv.id_virtuale = sgloi.id_virtuale\r\n" +
            "    WHERE 1 =1 \r\n" +
            "    AND sti.id_istanza = :idIstanza \r\n" +
            "UNION\r\n" +
            "    SELECT \r\n" +
            "    sdta.des_tipo_adempimento AS label_adempimento,\r\n" +
            "    srglv.label_pubblicazione As LABEL,\r\n" +
            "    srglv.type_geojson AS type,\r\n" +
            "    'true' AS ENABLE,\r\n" +
            "    public.ST_AsGeoJSON((sgloi.geometria),15,0) As geojson\r\n" +
            "    FROM scriva_t_istanza sti \r\n" +
            "    INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento =sti.id_adempimento\r\n" +
            "    INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento\r\n" +
            "    INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_istanza = sti.id_istanza\r\n" +
            "    LEFT JOIN scriva_geo_punto_ogg_istanza sgloi ON sgloi.id_oggetto_istanza = stoi.id_oggetto_istanza\r\n" +
            "    INNER JOIN scriva_r_geeco_layer_virtuali srglv ON srglv.id_virtuale = sgloi.id_virtuale\r\n" +
            "    WHERE 1 =1 \r\n" +
            "    AND sti.id_istanza = :idIstanza \r\n" +
            ") AS geometrie\r\n" +
            "GROUP BY label_adempimento, LABEL, TYPE, ENABLE\r\n" +
            ") AS geometrie_adempimento\r\n" +
            "GROUP BY label_adempimento";


    String QUERY_LOAD_ISTANZE_PUBBLICATE = "with elenco_oss as (SELECT distinct id_istanza as istanza , \r\n" + 
    		"     FIRST_VALUE( fine) \r\n" + 
    		"    OVER( \r\n" + 
    		"	PARTITION BY id_istanza\r\n" + 
    		"        ORDER BY inizio desc\r\n" + 
    		"    ) last_data_oss\r\n" + 
    		"    from\r\n" + 
    		"(select  distinct ist.id_istanza , data_inizio_osservazioni inizio , data_fine_osservazioni  fine\r\n" + 
    		"from scriva_t_istanza ist\r\n" + 
    		"where ist.data_inizio_osservazioni IS not null and DATE(ist.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    		"and ist.data_pubblicazione is not null\r\n" + 
    		"union \r\n" + 
    		"select distinct sris.id_istanza , sris.data_inizio_osservazioni inizio , sris.data_fine_osservazioni fine\r\n" + 
    		"from scriva_r_istanza_storico sris\r\n" + 
    		"inner join scriva_t_istanza ist2 on ist2.id_istanza = sris.id_istanza \r\n" + 
    		"where  sris.data_inizio_osservazioni IS not NULL and DATE(sris.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    		"and ist2.data_pubblicazione is not null\r\n" + 
    		") as periodi_oss)\r\n" + 
    		"( select elenco_oss.last_data_oss as DATA_FINE_OSSERVAZIONI, sti.*,sdsi.*,sda.*,sdep.*,sdta.*,COALESCE (srac.valore, 'false') as flg_osservazioni,\r\n" + 
    		"--DATE(sti.data_fine_osservazioni),	\r\n" + 
    		"fa.fase_pubblicazione , \r\n" + 
    		"date(null)  AS data_atto_conclusivo,COUNT(1) over (partition by ( fa.fase_pubblicazione)) as CONTATORE_PRATICHE\r\n" + 
    		"  from scriva_t_istanza sti  \r\n" + 
    		"  inner join scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' \r\n" + 
    		"  inner join scriva_d_adempimento sda on sti.id_adempimento = sda.id_adempimento   \r\n" + 
    		"  inner join scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento  \r\n" + 
    		"  left join scriva_d_esito_procedimento sdep on sdep.id_esito_procedimento = sti.id_esito_procedimento     left join scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS' \r\n" + 
    		"  left join scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva and sdis.cod_informazione_scriva = 'OSSERVAZIONI' \r\n" + 
    		"  inner join (select sti.id_istanza ,CASE \r\n" + 
    		"  when sdsi.cod_stato_istanza = 'CONCLUSA' then  'CONCLUSA' \r\n" + 
    		"  WHEN (sti.data_inizio_osservazioni IS not NULL and DATE(sti.data_inizio_osservazioni) <= DATE(NOW()) and sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) )  THEN 'CONSULTA' \r\n" + 
    		"  ELSE 'IN_CORSO'  end AS fase_pubblicazione \r\n" + 
    		"  from scriva_t_istanza sti  \r\n" + 
    		"  inner join scriva_d_stato_istanza sdsi \r\n" + 
    		"  on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' ) as fa \r\n" + 
    		"  on fa.id_istanza = sti.id_istanza \r\n" + 
    		//"  left join scriva_d_configurazione sdconf  on sdconf.chiave = 'ANNI_PUBB_CONCLUSE'  \r\n" + 
    		"  left join elenco_oss on istanza = sti.id_istanza  \r\n" + 
    		"  __dynamic_inner_join_conditions__ \r\n" + 
    		"  			WHERE sti.DATA_PUBBLICAZIONE is not null  \r\n" + 
    		"			and fa.fase_pubblicazione <> 'CONCLUSA'		\r\n" + 
    		"			__dynamic_where_conditions__  \r\n" + 
    		"  union 			 \r\n" + 
    		"  select elenco_oss.last_data_oss as DATA_FINE_OSSERVAZIONI, sti.*,sdsi.*,sda.*,sdep.*,sdta.*,   \r\n" + 
    		"  COALESCE (srac.valore, 'false') as flg_osservazioni, \r\n" + 
    		"  --DATE(sti.data_fine_osservazioni),\r\n" + 
    		"  --DATE(sti.data_fine_osservazioni),	\r\n" + 
    		"  fa.fase_pubblicazione  , \r\n" + 
    		"  data_atto_conclusivo  AS data_atto_conclusivo,\r\n" + 
    		"  COUNT(1) over (partition by (select 1 from DUAL)) as CONTATORE_PRATICHE\r\n" + 
    		"  from scriva_t_istanza sti  \r\n" + 
    		"  inner join scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' \r\n" + 
    		"  inner join scriva_d_adempimento sda on sti.id_adempimento = sda.id_adempimento   \r\n" + 
    		"  inner join scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento  \r\n" + 
    		"  left join scriva_d_esito_procedimento sdep on sdep.id_esito_procedimento = sti.id_esito_procedimento     left join scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS' \r\n" + 
    		"  left join scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva and sdis.cod_informazione_scriva = 'OSSERVAZIONI' \r\n" + 
    		"  inner join (select sti.id_istanza ,CASE \r\n" + 
    		"  when sdsi.cod_stato_istanza = 'CONCLUSA' then  'CONCLUSA' \r\n" + 
    		"  WHEN (sti.data_inizio_osservazioni IS not NULL and DATE(sti.data_inizio_osservazioni) <= DATE(NOW()) and sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) )  THEN 'CONSULTA' \r\n" + 
    		"  ELSE 'IN_CORSO'  end AS fase_pubblicazione from scriva_t_istanza sti  \r\n" + 
    		"  inner join scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' ) as fa on  \r\n" + 
    		"  fa.id_istanza = sti.id_istanza  \r\n" + 
    		"  LEFT JOIN (select MAX(stai.data_upload) as DATA_ATTO_CONCLUSIVO , stai.id_istanza \r\n" + 
    		"	from scriva_t_allegato_istanza stai\r\n" + 
    		"	inner join scriva_d_tipologia_allegato sdta2 on sdta2.id_tipologia_allegato = stai.id_tipologia_allegato\r\n" + 
    		"	where sdta2.cod_tipologia_allegato = :tipoAttoConclusivo\r\n" + 
    		"	and stai.data_pubblicazione is not null \r\n" + 
    		"	and stai.flg_cancellato = 0\r\n" + 
    		"	group by stai.id_istanza) AS atto_conclusivo ON atto_conclusivo.id_istanza = sti.id_istanza  \r\n" + 
    		"	__dynamic_due_inner_join_conditions__\r\n" + 
    		//"  left join scriva_d_configurazione sdconf  on sdconf.chiave = 'ANNI_PUBB_CONCLUSE'   \r\n" + 
    		"  left join elenco_oss on istanza = sti.id_istanza\r\n" + 
    		"			WHERE sti.DATA_PUBBLICAZIONE <= NOW() \r\n" + 
    		"			and fa.fase_pubblicazione = 'CONCLUSA'	\r\n" + 
    		"__dynamic_where_conditions__ \r\n" + 
    		"__dynamic_where_conditions_only_conclusa__			\r\n" + 
    		"   ) \r\n" + 
    		"__dynamic_orderby_conditions__\r\n" + 
    		"\r\n" + 
    		"\r\n" + 
    		"";
//            "( select sti.*,sdsi.*,sda.*,sdep.*,sdta.*,COALESCE (srac.valore, 'false') as flg_osservazioni,DATE(sti.data_fine_osservazioni),	fa.fase_pubblicazione , date(null)  AS data_atto_conclusivo,COUNT(1) over (partition by ( fa.fase_pubblicazione)) as CONTATORE_PRATICHE\r\n" +
//            "  from scriva_t_istanza sti  \r\n" +
//            "  inner join scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' \r\n" +
//            "  inner join scriva_d_adempimento sda on sti.id_adempimento = sda.id_adempimento   \r\n" +
//            "  inner join scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento  \r\n" +
//            "  left join scriva_d_esito_procedimento sdep on sdep.id_esito_procedimento = sti.id_esito_procedimento   " +
//            "  left join scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS' \r\n" +
//            "  left join scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva and sdis.cod_informazione_scriva = 'OSSERVAZIONI' \r\n" +
//            "  inner join (select sti.id_istanza ,CASE \r\n" +
//            "  when sdsi.cod_stato_istanza = 'CONCLUSA' then  'CONCLUSA' \r\n" +
//            "  WHEN (sti.data_inizio_osservazioni IS not NULL and DATE(sti.data_inizio_osservazioni) <= DATE(NOW()) and sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) )  THEN 'CONSULTA' \r\n" + 
//            "  ELSE 'IN_CORSO'  end AS fase_pubblicazione \r\n" +
//            "  from scriva_t_istanza sti  \r\n" +
//            "  inner join scriva_d_stato_istanza sdsi \r\n" +
//            "  on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' ) as fa \r\n" +
//            "  on fa.id_istanza = sti.id_istanza "
//            + "__dynamic_inner_join_conditions__ \r\n" +
//            "			WHERE sti.DATA_PUBBLICAZIONE is not null  \r\n" +
//            "			and fa.fase_pubblicazione <> 'CONCLUSA'		\r\n"
//            + "__dynamic_where_conditions__ \r\n" +
//            "  union 			 \r\n" +
//            "  select sti.*,sdsi.*,sda.*,sdep.*,sdta.*,   \r\n" +
//            "  COALESCE (srac.valore, 'false') as flg_osservazioni, \r\n" +
//            "  DATE(sti.data_fine_osservazioni),fa.fase_pubblicazione  , \r\n" +
//            "  data_atto_conclusivo  AS data_atto_conclusivo,\r\n" +
//            "  COUNT(1) over (partition by (select 1 from DUAL)) as CONTATORE_PRATICHE\r\n" +
//            "  from scriva_t_istanza sti  \r\n" +
//            "  inner join scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' \r\n" +
//            "  inner join scriva_d_adempimento sda on sti.id_adempimento = sda.id_adempimento   \r\n" +
//            "  inner join scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento  \r\n" +
//            "  left join scriva_d_esito_procedimento sdep on sdep.id_esito_procedimento = sti.id_esito_procedimento   " +
//            "  left join scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS' \r\n" +
//            "  left join scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva and sdis.cod_informazione_scriva = 'OSSERVAZIONI' \r\n" +
//            "  inner join (select sti.id_istanza ,CASE \r\n" +
//            "  when sdsi.cod_stato_istanza = 'CONCLUSA' then  'CONCLUSA' \r\n" +
//            "  WHEN (sti.data_inizio_osservazioni IS not NULL and DATE(sti.data_inizio_osservazioni) <= DATE(NOW()) and sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) )  THEN 'CONSULTA' \r\n" +
//            "  ELSE 'IN_CORSO'  end AS fase_pubblicazione from scriva_t_istanza sti  \r\n" +
//            "  inner join scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza and sdsi.ind_visibile like '%'||:componenteApp||'%' ) as fa on  \r\n" +
//            "  fa.id_istanza = sti.id_istanza  \r\n" +
//            "  LEFT JOIN (select MAX(stai.data_upload) as DATA_ATTO_CONCLUSIVO , stai.id_istanza \r\n" +
//            "	from scriva_t_allegato_istanza stai\r\n" +
//            "	inner join scriva_d_tipologia_allegato sdta2 on sdta2.id_tipologia_allegato = stai.id_tipologia_allegato\r\n" +
//            "	where sdta2.cod_tipologia_allegato = :tipoAttoConclusivo\r\n" +
//            "	and stai.data_pubblicazione is not null \r\n" +
//            "	and stai.flg_cancellato = 0\r\n" +
//            "	group by stai.id_istanza) AS atto_conclusivo ON atto_conclusivo.id_istanza = sti.id_istanza  "
//            + "__dynamic_due_inner_join_conditions__ \r\n" +
//            "			WHERE sti.DATA_PUBBLICAZIONE <= NOW() \r\n" + // is not null  \r\n" +
//            "			and fa.fase_pubblicazione = 'CONCLUSA'		\r\n" +
//            "__dynamic_where_conditions__ " +
//            "__dynamic_where_conditions_only_conclusa__ )" +
//            "__dynamic_orderby_conditions__";

    private static final String FIELDS = ""
    	+ " sdam.cod_ambito, des_adempimento, cod_tipo_adempimento, sti.id_istanza , sti.cod_pratica , label_stato, sti.data_inserimento_pratica, sti.data_pubblicazione, fa.fase_pubblicazione, data_protocollo_istanza \r\n";

    //SCRIVA-941 vanno IN CONSULTAZIONE quelle con periodo di osservazione in essere ma NON ANCORA CONCLUSE
    String QUERY_LOAD_ISTANZE_PUBBLICATE_SINTESI =
    	" SELECT *, 																										\r\n" +
    	" SUM(flag_conta_conclusa) OVER () AS contatore_conclusa,													 		\r\n" +
    	" SUM(flag_conta_in_corso) OVER () AS contatore_in_corso,														 	\r\n" +
    	" SUM(flag_conta_consultazione) OVER () AS contatore_in_consultazione, 											 	\r\n" +
    	" COUNT(1) OVER () AS contatore_tutte 																				\r\n" +
    	" FROM "
    	+ "   (  \r\n" + 
    	"   with elenco_oss as (SELECT distinct id_istanza as istanza ,\r\n" + 
    	"     FIRST_VALUE( fine)\r\n" + 
    	"    OVER(\r\n" + 
    	"        PARTITION BY id_istanza\r\n" + 
    	"        ORDER BY inizio desc\r\n" + 
    	"    ) last_data_oss\r\n" + 
    	"    from\r\n" + 
    	"(select  distinct ist.id_istanza , data_inizio_osservazioni inizio , data_fine_osservazioni  fine\r\n" + 
    	"from scriva_t_istanza ist\r\n" + 
    	"where ist.data_inizio_osservazioni IS not null and DATE(ist.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    	"and ist.data_pubblicazione is not null\r\n" + 
    	"union\r\n" + 
    	"select distinct sris.id_istanza , sris.data_inizio_osservazioni inizio , sris.data_fine_osservazioni fine\r\n" + 
    	"from scriva_r_istanza_storico sris\r\n" + 
    	"inner join scriva_t_istanza ist2 on ist2.id_istanza = sris.id_istanza\r\n" + 
    	"where  sris.data_inizio_osservazioni IS not NULL and DATE(sris.data_inizio_osservazioni) <= DATE(NOW())\r\n" + 
    	"and ist2.data_pubblicazione is not null\r\n" + 
    	") as periodi_oss)																									\r\n" +
    	"     SELECT elenco_oss.last_data_oss as DATA_FINE_OSSERVAZIONI, " + FIELDS + " , 																						\r\n" +
    	//"		DATE(sti.data_fine_osservazioni) as data_fine_osservazione,	                                                \r\n" +
    	"		COALESCE (srac.valore, 'false') as flg_osservazioni,														\r\n" +
    	"		case                                                                                                        \r\n" +
//    	"    WHEN (sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW())               \r\n" +
//    	"                  and fa.fase_pubblicazione <> 'CONCLUSI') 	                                                    \r\n" +
        "WHEN (sti.data_inizio_osservazioni IS not NULL and DATE(sti.data_inizio_osservazioni) <= DATE(NOW())               \r\n" + 
        "and sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) and fa.fase_pubblicazione <> 'CONCLUSI' ) \r\n" +
    	"			 THEN 1 else 0 end as flag_conta_consultazione,															\r\n" +
    	"		date(sti.data_conclusione_procedimento) as data_conclusione_procedimento, 																\r\n" +
    	"		case WHEN fa.fase_pubblicazione = 'CONCLUSI' then 1 else 0 end as flag_conta_conclusa,						\r\n" +
    	"		case WHEN fa.fase_pubblicazione = 'IN CORSO DI ISTRUTTORIA' then 1 else 0 end as flag_conta_in_corso, 		    \r\n" +
    	"			STRING_AGG(DISTINCT des_competenza_territorio, ',') AS des_competenza_territorio, 						\r\n" +
    	"			STRING_AGG(DISTINCT den_oggetto, ',') AS den_oggetto,                             						\r\n" +
    	"			STRING_AGG(DISTINCT des_oggetto, ',') AS des_oggetto,                             						\r\n" +
    	"			STRING_AGG(DISTINCT denom_comune, ',') as denom_comune                           					 	\r\n" +
    	"		FROM scriva_t_istanza sti                                                           						\r\n" +
    	"		INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza  					\r\n" +
    	"			  and sdsi.ind_visibile like '%'||:componenteApp||'%'                         							\r\n" +
    	"		INNER JOIN scriva_d_adempimento sda on sti.id_adempimento = sda.id_adempimento								\r\n" +
    	"		INNER JOIN scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento 			\r\n" +
    	"       INNER JOIN scriva_d_ambito sdam ON sdta.id_ambito = sdam.id_ambito                                          \r\n" +
    	"		left join scriva_d_esito_procedimento sdep on sdep.id_esito_procedimento = sti.id_esito_procedimento 		\r\n" +
    	"		left join scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS' 	\r\n" +
    	"		left join scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva  	\r\n" +
    	"			 and sdis.cod_informazione_scriva = 'OSSERVAZIONI' 														\r\n" +
    	"		INNER JOIN 																									\r\n" +
    	"			(select sti.id_istanza ,CASE 																			\r\n" +
    	"				when sdsi.cod_stato_istanza = 'CONCLUSA' then  'CONCLUSI'  											\r\n" +
    	"				ELSE 'IN CORSO DI ISTRUTTORIA'  end AS fase_pubblicazione 											\r\n" +
    	"			 FROM scriva_t_istanza sti  																			\r\n" +
    	"			 INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza  				\r\n" +
    	"				   and sdsi.ind_visibile like '%'||:componenteApp||'%' 												\r\n" +
    	"			) as fa on fa.id_istanza = sti.id_istanza 																\r\n" +
    	"    	INNER JOIN scriva_r_istanza_competenza sric on sric.id_istanza = sti.id_istanza								\r\n" +
    	"    	INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sric.id_competenza_territorio \r\n" +
    	"    	INNER JOIN scriva_t_oggetto_istanza stoi on STOI.id_istanza = sti.id_istanza 								\r\n" +
    	"    	INNER JOIN scriva_r_ubica_oggetto_istanza sruoi on sruoi.id_oggetto_istanza = stoi.id_oggetto_istanza 		\r\n" +
    	"    	INNER JOIN scriva_d_comune sdc ON sdc.id_comune = sruoi.id_comune 											\r\n" +
    	"		__dynamic_inner_join_conditions__ 																			\r\n" +
    	"       left join elenco_oss on istanza = sti.id_istanza													        \r\n" +
    	"		WHERE sti.DATA_PUBBLICAZIONE is not null and trunc(sti.data_pubblicazione, 'dd') <= (current_date)  		\r\n" +
    	"		  and fa.fase_pubblicazione <> 'CONCLUSI' 																	\r\n" +
    	"         and (sric.flg_autorita_principale = 1 or sric.flg_autorita_assegnata_bo = 1)                              \r\n" +
    	"	  __dynamic_where_conditions__ 																					\r\n" +
    	"		GROUP BY elenco_oss.last_data_oss, " + FIELDS + ", flg_osservazioni, flag_conta_consultazione, 				\r\n" +
    	"                data_conclusione_procedimento, flag_conta_conclusa, flag_conta_in_corso							\r\n" +
    	"	UNION  																										    \r\n" +
    	"      SELECT elenco_oss.last_data_oss as DATA_FINE_OSSERVAZIONI, " + FIELDS + " , 																						\r\n" +
    	//"		DATE(sti.data_fine_osservazioni) as data_fine_osservazione,	                                                \r\n" +
    	"		COALESCE (srac.valore, 'false') as flg_osservazioni,														\r\n" +
    	"		case                                                                                                        \r\n" +
    	//"WHEN (sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW())       \r\n" +
    	//"                  and fa.fase_pubblicazione <> 'CONCLUSI') 	                                                    \r\n" +
    	"WHEN (sti.data_inizio_osservazioni IS not NULL and DATE(sti.data_inizio_osservazioni) <= DATE(NOW())               \r\n" + 
    	" and sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) and fa.fase_pubblicazione <> 'CONCLUSI' )   \r\n" +
    	"			 THEN 1 else 0 end as flag_conta_consultazione,															\r\n" +
    	"		date(sti.data_conclusione_procedimento) as data_conclusione_procedimento, 				                    \r\n" +
    	"		case WHEN fa.fase_pubblicazione = 'CONCLUSI' then 1 else 0 end as flag_conta_conclusa,						\r\n" +
    	"		case WHEN fa.fase_pubblicazione = 'IN CORSO DI ISTRUTTORIA' then 1 else 0 end as flag_conta_in_corso, 		\r\n" +
    	"			STRING_AGG(DISTINCT des_competenza_territorio, ',') AS des_competenza_territorio, 						\r\n" +
    	"			STRING_AGG(DISTINCT den_oggetto, ',') AS den_oggetto,        						                    \r\n" +
    	"			STRING_AGG(DISTINCT des_oggetto, ',') AS des_oggetto,                             						\r\n" +
    	"			STRING_AGG(DISTINCT denom_comune, ',') as denom_comune 						                    	    \r\n" +
    	"		FROM scriva_t_istanza sti                                                       					    	\r\n" +
    	"		INNER JOIN scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza  					\r\n" +
    	"			  and sdsi.ind_visibile like '%'||:componenteApp||'%'                         							\r\n" +
    	"		INNER JOIN scriva_d_adempimento sda on sti.id_adempimento = sda.id_adempimento								\r\n" +
    	"		INNER JOIN scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento 			\r\n" +
    	"       INNER JOIN scriva_d_ambito sdam ON sdta.id_ambito = sdam.id_ambito                                          \r\n" +
    	"		left join scriva_d_esito_procedimento sdep on sdep.id_esito_procedimento = sti.id_esito_procedimento 		\r\n" +
    	"		left join scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS' 	\r\n" +
    	"		left join scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva  	\r\n" +
    	"			 and sdis.cod_informazione_scriva = 'OSSERVAZIONI' 														\r\n" +
    	"		INNER JOIN 																									\r\n" +
    	"			(SELECT sti.id_istanza ,CASE 																			\r\n" +
    	"				when sdsi.cod_stato_istanza = 'CONCLUSA' then  'CONCLUSI'  											\r\n" +
    	"				ELSE 'IN CORSO DI ISTRUTTORIA'  end AS fase_pubblicazione 											\r\n" +
    	"			 FROM scriva_t_istanza sti  																			\r\n" +
    	"			 inner join scriva_d_stato_istanza sdsi on sti.id_stato_istanza = sdsi.id_stato_istanza  				\r\n" +
    	"				   and sdsi.ind_visibile like '%'||:componenteApp||'%' 												\r\n" +
    	"			) as fa on fa.id_istanza = sti.id_istanza 																\r\n" +
    	"    	INNER JOIN scriva_r_istanza_competenza sric on sric.id_istanza = sti.id_istanza								\r\n" +
    	"    	INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sric.id_competenza_territorio \r\n" +
    	"    	INNER JOIN scriva_t_oggetto_istanza stoi on STOI.id_istanza = sti.id_istanza 								\r\n" +
    	"    	INNER JOIN scriva_r_ubica_oggetto_istanza sruoi on sruoi.id_oggetto_istanza = stoi.id_oggetto_istanza 		\r\n" +
    	"    	INNER JOIN scriva_d_comune sdc ON sdc.id_comune = sruoi.id_comune 											\r\n" +
    	"       __dynamic_due_inner_join_conditions__ 																		\r\n" +
    	"		left join elenco_oss on istanza = sti.id_istanza															\r\n" +
    	"		WHERE sti.DATA_PUBBLICAZIONE is not null and trunc(sti.data_pubblicazione, 'dd') <= (current_date)			\r\n" +
    	"		  and fa.fase_pubblicazione = 'CONCLUSI' 																	\r\n" +
    	"         and (sric.flg_autorita_principale = 1 or sric.flg_autorita_assegnata_bo = 1)                              \r\n" +
    	"	  __dynamic_where_conditions__ 																					\r\n" +
    	"	  __dynamic_where_conditions_only_conclusa__ 																	\r\n" +
    	"		GROUP BY elenco_oss.last_data_oss, " + FIELDS + ", flg_osservazioni, flag_conta_consultazione, 				\r\n" +
    	"                data_conclusione_procedimento, flag_conta_conclusa, flag_conta_in_corso							\r\n" +
    	"	) pratiche_pubblicate 																							\r\n" +
    	"__dynamic_orderby_conditions__";

    private static final String INNER_JOIN_DENOMINAZIONE = "inner join scriva_t_oggetto_istanza stoi on stoi.id_istanza =sti.id_istanza ";

    private static final String INNER_JOIN_SIGLA_PROVINCIA = "inner join scriva_r_ubica_oggetto_istanza sruoi on sruoi.id_oggetto_istanza = stoi.id_oggetto_istanza\r\n"
            + "inner join scriva_d_comune sdc on sdc.id_comune = sruoi.id_comune \r\n"
            + "inner join scriva_d_provincia sdp on sdp.id_provincia = sdc.id_provincia ";

    private static final String INNER_JOIN_PROVINCIA = " inner join scriva_d_provincia sdp on sdp.id_provincia = sdc.id_provincia ";

    private static final String INNER_JOIN_COD_CATEGORIA_ALLEGATO = " left join scriva_r_adempi_config srac3 on srac3.id_adempimento = sti.id_adempimento and srac3.chiave = 'ANNI_' || ";

    //private static final String INNER_JOIN_ANNO_PUB_CONCLUSE = " left join scriva_r_adempi_config srac2 on srac2.id_adempimento = sti.id_adempimento and srac2.chiave = 'ANNI_PUBB_CONCLUSE'   ";
    private static final String INNER_JOIN_ANNO_PUB_CONCLUSE = "  left join scriva_d_configurazione sdconf  on sdconf.chiave = 'ANNI_PUBB_CONCLUSE'  ";


    private static final String WHERE_ID_AMBITO = "and sdta.id_ambito =  :idAmbito ";

    private static final String WHERE_ID_TIPO_ADEMPIMENTO = "and sdta.id_tipo_adempimento = :idTipoAdempimento ";

    private static final String WHERE_ID_ADEMPIMENTO = "AND sti.id_adempimento =:idAdempimento ";

    private static final String WHERE_IN_CONSULTAZIONE = "AND (sti.data_inizio_osservazioni IS not NULL and DATE(sti.data_inizio_osservazioni) <= DATE(NOW())               \r\n" + 
    		"and sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) and fa.fase_pubblicazione <> 'CONCLUSI' )  ";//" AND sti.data_fine_osservazioni IS not NULL and DATE(sti.data_fine_osservazioni) >= DATE(NOW()) and fa.fase_pubblicazione <> 'CONCLUSI' ";

    private static final String WHERE_ID_COMPETENZA_TERRITORIO = //" and exists (select 1 from scriva_r_istanza_competenza sric\r\n" +
            //" 			where sric.id_istanza = sti.id_istanza \r\n" +
            " 			and sric.id_competenza_territorio = :idCompetenzaTerritorio ";//) ";

    private static final String WHERE_ANNO_PRESENTAZIONE_PROGETTO = "and EXTRACT(YEAR FROM sti.data_inserimento_pratica ) = :annoPresentazioneProgetto ";

    private static final String WHERE_ANNO_PRESENTAZIONE_PROGETTO_MAGGIORE = "and EXTRACT(YEAR FROM sti.data_inserimento_pratica ) >= :annoPresentazioneProgetto ";

    private static final String WHERE_ANNO_PRESENTAZIONE_PROGETTO_MINORE = "and EXTRACT(YEAR FROM sti.data_inserimento_pratica ) <= :annoPresentazioneProgetto ";

    private static final String WHERE_CODICE_PRATICA = "and UPPER(sti.cod_pratica) like UPPER('%'||:codicePratica ||'%') ";

    private static final String WHERE_DENOMINAZIONE = "and UPPER(stoi.den_oggetto) like UPPER('%'||:denominazione||'%') ";
    private static final String WHERE_DENOMINAZIONE_SINTESI = "and UPPER(den_oggetto) like UPPER('%'||:denominazione||'%') ";

    private static final String WHERE_SIGLA_PROVINCIA_OGGETTO = "and sdp.sigla_provincia = :siglaProvinciaOggetto ";

    private static final String WHERE_COD_ISTAT_COMUNE_OGGETTO = "and sdc.cod_istat_comune = :codIstatComuneOggetto ";

    //private static final String WHERE_COD_STATO_ISTANZA = "and sdsi.cod_stato_istanza = :codStatoIstanza ";

    private static final String WHERE_COD_STATO_ISTANZA_2 = "and (sdsi.cod_stato_istanza = :labelStatoMaiuscolo or\r\n " +
    	   "  sdsi.cod_stato_istanza in (select sdsii.cod_stato_istanza\r\n" +
    	   "					 from scriva_d_stato_istanza as sdsii\r\n" +
    	   "                     where sdsii.label_stato=:labelStato ))";

    private static final String WHERE_COD_STATO_PROCEDIMENTO_STATALE = "and sti.id_stato_proced_statale in (\r\n"
    	+ "			              select sdsp.id_stato_proced_statale \r\n"
    	+ "			              from scriva_d_stato_proced_statale sdsp\r\n"
    	+ "			              where sdsp.cod_stato_proced_statale = :codStatoProcedimentoStatale)";

    //private static final String WHERE_DES_STATO_PROCEDIMENTO_STATALE = " and sti.des_stato_procedimento_statale = :statoProcStatale ";
    //private static final String WHERE_DES_STATO_PROCEDIMENTO_STATALE = " "; // eliminato campo

    private static final String WHERE_FASE_PUBBLICAZIONE = "and fa.fase_pubblicazione=:fasePubblicazione  ";

    private static final String WHERE_COD_CATEGORIA_ALLEGATO = " AND   ( (srac3.valore is null or (\r\n " +
            "					EXTRACT(YEAR from sti.data_inserimento_pratica) >= (EXTRACT(YEAR FROM Now() ) - cast(srac3.valore as numeric)))) \r\n" +
            "			and exists (select 1 from scriva_t_allegato_istanza stai2\r\n" +
            "   				inner join scriva_d_tipologia_allegato sdta3 on sdta3.id_tipologia_allegato = stai2.id_tipologia_allegato \r\n" +
            "   				inner join scriva_d_categoria_allegato sdca on sdca.id_categoria_allegato = sdta3.id_categoria_allegato \r\n" +
            "   				where stai2.id_istanza= sti.id_istanza \r\n" +
            "   				and sdca.cod_categoria_allegato = :codCategoriaAllegato \r\n" +
            "   				and flg_cancellato = 0)			\r\n" +
            "		) ";

    private static final String WHERE_FLAG_INFRA = "  ";
    private static final String WHERE_FLAG_PNRR = "   ";

    private static final String WHERE_VINCOLO = "  and exists (select 1 from scriva_r_istanza_vincolo_aut sriva \r\n" +
            "															inner join scriva_d_vincolo_autorizza sdva on \r\n" +
            "															sdva.id_vincolo_autorizza = sriva.id_vincolo_autorizza \r\n" +
            "															where sriva.id_istanza  = sti.id_istanza \r\n" +
            "															and sdva.cod_vincolo_autorizza in (:vincolo ) )  ";

    private static final String WHERE_COD_CATEGORIA_OGGETTO = " and exists (select 1 from scriva_t_oggetto_istanza stoi\r\n" +
            "			inner join scriva_r_ogg_istanza_categoria sroic on sroic.id_oggetto_istanza = stoi.id_oggetto_istanza \r\n" +
            "			inner join scriva_d_categoria_oggetto sdco on sdco.id_categoria_oggetto = sroic.id_categoria_oggetto\r\n" +
            "			where stoi.id_istanza = sti.id_istanza \r\n" +
            "			and sdco.cod_categoria_oggetto = :codCategoriaOggetto) ";

    //private static final String WHERE_ANNO_PUB_CONCLUSE = "	and EXTRACT(YEAR from sti.data_inserimento_pratica) >= EXTRACT(YEAR FROM Now() ) - coalesce(cast(srac2.valore as numeric), 1000) ";
    private static final String WHERE_ANNO_PUB_CONCLUSE = "	and (sti.data_inserimento_pratica is null or EXTRACT(YEAR from sti.data_inserimento_pratica) >= EXTRACT(YEAR FROM Now() ) - coalesce(cast(sdconf.valore as numeric), 1000))  ";

    /**
     * Search istanze fo list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @param attoreScriva   the attore scriva
     * @return the list
     */
    @Override
    public List<PubIstanzaDTO> searchIstanze(PubSearchDTO searchCriteria, String offset, String limit, String sort, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            StringBuilder dynamicWhereConditions = new StringBuilder();
            StringBuilder dynamicWhereConditionsOnlyConclusa = new StringBuilder();
            String dynamicOrderByCondition = "";

            StringBuilder dynamicInnerJoinConditions = new StringBuilder();

            StringBuilder dynamicDueInnerJoinConditions = new StringBuilder();

            if (searchCriteria.getIdIstanza() != null) {
                map.put("idIstanza", searchCriteria.getIdIstanza());
                dynamicWhereConditions.append(WHERE_ID_ISTANZA);

            }
            if (searchCriteria.getIdAmbito() != null) {
                map.put("idAmbito", searchCriteria.getIdAmbito());
                dynamicWhereConditions.append(WHERE_ID_AMBITO);
            }

            if (searchCriteria.getIdTipoAdempimento() != null) {
                map.put("idTipoAdempimento", searchCriteria.getIdTipoAdempimento());
                dynamicWhereConditions.append(WHERE_ID_TIPO_ADEMPIMENTO);
            }

            if (searchCriteria.getIdAdempimento() != null) {
                map.put("idAdempimento", searchCriteria.getIdAdempimento());
                dynamicWhereConditions.append(WHERE_ID_ADEMPIMENTO);
            }

            if (searchCriteria.getIdCompetenzaTerritorio() != null) {
                map.put("idCompetenzaTerritorio", searchCriteria.getIdCompetenzaTerritorio());
                dynamicWhereConditions.append(WHERE_ID_COMPETENZA_TERRITORIO);
            }

            if (searchCriteria.getAnnoPresentazioneProgetto() != null) {
                map.put("annoPresentazioneProgetto", searchCriteria.getAnnoPresentazioneProgetto());

                if (searchCriteria.getMaggioreDi() != null && searchCriteria.getMaggioreDi()) {
                    dynamicWhereConditions.append(WHERE_ANNO_PRESENTAZIONE_PROGETTO_MAGGIORE);
                } else if (searchCriteria.getMinoreDi() != null && searchCriteria.getMinoreDi()) {
                    dynamicWhereConditions.append(WHERE_ANNO_PRESENTAZIONE_PROGETTO_MINORE);
                } else {

                    dynamicWhereConditions.append(WHERE_ANNO_PRESENTAZIONE_PROGETTO);
                }

            } else {
                // seconda query delle concluse
             	if (StringUtils.isNotBlank(searchCriteria.getFasePubblicazione())) {
                    if (searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.TUTTE.getDescrizione())
                    	|| searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.CONCLUSA.getDescrizione()) ) {
                    	dynamicWhereConditionsOnlyConclusa.append(WHERE_ANNO_PUB_CONCLUSE);
                    }
            	}
            }

            if (StringUtils.isNotBlank(searchCriteria.getCodicePratica())) {
                map.put("codicePratica", searchCriteria.getCodicePratica());
                dynamicWhereConditions.append(WHERE_CODICE_PRATICA);
            }

            if (StringUtils.isNotBlank(searchCriteria.getDenominazioneOggetto())) {
                map.put("denominazione", searchCriteria.getDenominazioneOggetto());
                dynamicWhereConditions.append(WHERE_DENOMINAZIONE);

                dynamicInnerJoinConditions.append(INNER_JOIN_DENOMINAZIONE);
            }

            if (StringUtils.isNotBlank(searchCriteria.getSiglaProvinciaOggetto())) {
                map.put("siglaProvinciaOggetto", searchCriteria.getSiglaProvinciaOggetto());
                dynamicWhereConditions.append(WHERE_SIGLA_PROVINCIA_OGGETTO);

                if (StringUtils.isBlank(searchCriteria.getDenominazioneOggetto())) {
                    dynamicInnerJoinConditions.append(INNER_JOIN_DENOMINAZIONE);
                }

                dynamicInnerJoinConditions.append(INNER_JOIN_SIGLA_PROVINCIA);
            }

            if (StringUtils.isNotBlank(searchCriteria.getCodIstatComuneOggetto())) {
                map.put("codIstatComuneOggetto", searchCriteria.getCodIstatComuneOggetto());
                dynamicWhereConditions.append(WHERE_COD_ISTAT_COMUNE_OGGETTO);
                if (StringUtils.isBlank(searchCriteria.getDenominazioneOggetto())
                        && (StringUtils.isBlank(searchCriteria.getSiglaProvinciaOggetto()))) {
                    dynamicInnerJoinConditions.append(INNER_JOIN_DENOMINAZIONE);
                    dynamicInnerJoinConditions.append(INNER_JOIN_SIGLA_PROVINCIA);
                } else if (StringUtils.isBlank(searchCriteria.getSiglaProvinciaOggetto())) {
                    dynamicInnerJoinConditions.append(INNER_JOIN_SIGLA_PROVINCIA);
                }

            }

            if (StringUtils.isNotBlank(searchCriteria.getLabelStato())) {
                map.put("labelStato", searchCriteria.getLabelStato());
                map.put("labelStatoMaiuscolo", searchCriteria.getLabelStato().toUpperCase());
                dynamicWhereConditions.append(WHERE_COD_STATO_ISTANZA_2);
            }


            if (StringUtils.isNotBlank(searchCriteria.getCodStatoProcedStatale())) {
            	map.put("codStatoProcedimentoStatale", searchCriteria.getCodStatoProcedStatale());
                dynamicWhereConditions.append(WHERE_COD_STATO_PROCEDIMENTO_STATALE);
            }

            /* DOMANDA*/
            if (StringUtils.isNotBlank(searchCriteria.getCodCategoriaAllegato())) {
                map.put("codCategoriaAllegato", searchCriteria.getCodCategoriaAllegato());
                dynamicWhereConditions.append(WHERE_COD_CATEGORIA_ALLEGATO);
                dynamicInnerJoinConditions.append(INNER_JOIN_COD_CATEGORIA_ALLEGATO + "'" + searchCriteria.getCodCategoriaAllegato() + "' ");
            }

            if (searchCriteria.getFlgInfrastruttureStrategiche() != null && searchCriteria.getFlgInfrastruttureStrategiche()) {
                dynamicWhereConditions.append(WHERE_FLAG_INFRA);
            }

            if (searchCriteria.getFlgPnrr() != null && searchCriteria.getFlgPnrr()) {
                dynamicWhereConditions.append(WHERE_FLAG_PNRR);
            }

            if (searchCriteria.getVincolo() != null) {
                map.put("vincolo", searchCriteria.getVincolo());
                dynamicWhereConditions.append(WHERE_VINCOLO);

            }
            if (StringUtils.isNotBlank(searchCriteria.getCodCategoriaOggetto())) {
                map.put("codCategoriaOggetto", searchCriteria.getCodCategoriaOggetto());
                dynamicWhereConditions.append(WHERE_COD_CATEGORIA_OGGETTO);
                if (StringUtils.isBlank(searchCriteria.getDenominazioneOggetto())
                        && (StringUtils.isBlank(searchCriteria.getSiglaProvinciaOggetto()))
                        && (StringUtils.isBlank(searchCriteria.getCodIstatComuneOggetto()))) {
                    dynamicInnerJoinConditions.append(INNER_JOIN_DENOMINAZIONE);
                    dynamicInnerJoinConditions.append(INNER_JOIN_SIGLA_PROVINCIA);
                } else if (StringUtils.isBlank(searchCriteria.getSiglaProvinciaOggetto())
                        && (StringUtils.isBlank(searchCriteria.getCodIstatComuneOggetto()))) {
                    dynamicInnerJoinConditions.append(INNER_JOIN_SIGLA_PROVINCIA);
                }

            }

            //SCRIVA-724 - l'ordinamento delle date specifico per fase, deve essere impostato solo se da FE non sono arrivati i parametri di sort
            // TODO: da rivedere perche' in questo modo viene ordinata solo la pagina richiesta
            if (StringUtils.isNotBlank(searchCriteria.getFasePubblicazione())) {
                dynamicDueInnerJoinConditions = dynamicInnerJoinConditions;
                if (searchCriteria.getFasePubblicazione().equalsIgnoreCase("TUTTE")) {
                	if (!StringUtils.isNotBlank(sort)) {
                		dynamicOrderByCondition = " order by data_inserimento_pratica desc";
                	}
                    dynamicDueInnerJoinConditions.append(INNER_JOIN_ANNO_PUB_CONCLUSE);
                } else if (searchCriteria.getFasePubblicazione().equalsIgnoreCase("CONSULTA")) {
                	if (!StringUtils.isNotBlank(sort)) {
                		dynamicOrderByCondition = " order by data_fine_osservazioni asc ";
                	}
                	map.put("fasePubblicazione", searchCriteria.getFasePubblicazione());
                    dynamicWhereConditions.append(WHERE_FASE_PUBBLICAZIONE);
                } else if (searchCriteria.getFasePubblicazione().equalsIgnoreCase("IN_CORSO")) {
                	if (!StringUtils.isNotBlank(sort)) {
                		dynamicOrderByCondition = " order by data_protocollo_istanza desc ";
                	}
                    map.put("fasePubblicazione", searchCriteria.getFasePubblicazione());
                    dynamicWhereConditions.append(WHERE_FASE_PUBBLICAZIONE);
                } else if (searchCriteria.getFasePubblicazione().equalsIgnoreCase("CONCLUSA")) {
                    dynamicDueInnerJoinConditions.append(INNER_JOIN_ANNO_PUB_CONCLUSE);
                    if (!StringUtils.isNotBlank(sort)) {
                    	dynamicOrderByCondition = "  order by data_conclusione_procedimento desc ";
                    	//dynamicOrderByCondition = "  order by DATA_ATTO_CONCLUSIVO desc ";
                    }
                    map.put("fasePubblicazione", searchCriteria.getFasePubblicazione());
                    dynamicWhereConditions.append(WHERE_FASE_PUBBLICAZIONE);
                }

            } else {
                //DOMANDA SONO TUTTE
                dynamicDueInnerJoinConditions = dynamicInnerJoinConditions;
                dynamicOrderByCondition = " order by data_inserimento_pratica desc";
                dynamicDueInnerJoinConditions.append(INNER_JOIN_ANNO_PUB_CONCLUSE);
            }

            map.put("componenteApp", attoreScriva.getComponente());
            map.put("tipoAttoConclusivo", "ATTO_CONCLUSIVO");
            String query = QUERY_LOAD_ISTANZE_PUBBLICATE
                    .replace("__dynamic_inner_join_conditions__", "\n" + dynamicInnerJoinConditions.toString())
                    .replace("__dynamic_due_inner_join_conditions__", "\n" + dynamicDueInnerJoinConditions.toString())
                    .replace("__dynamic_where_conditions__", dynamicWhereConditions.toString())
                    .replace("__dynamic_where_conditions_only_conclusa__", dynamicWhereConditionsOnlyConclusa.toString())
                    .replace("__dynamic_orderby_conditions__", dynamicOrderByCondition);
            MapSqlParameterSource params = getParameterValue(map);
            logDebug(className, "query eseguita :  \n" + getQuery(query, offset, limit) + "\n");
            return template.query(getQuery(query, offset, limit), params, getRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
    }

	@Override
	public List<SintesiDTO> searchSintesiIstanze(PubSearchDTO searchCriteria, String offset, String limit, String sort, AttoreScriva attoreScriva) {
		logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();

            StringBuilder dynamicWhereConditions = new StringBuilder();
            StringBuilder dynamicWhereConditionsOnlyConclusa = new StringBuilder();

            String dynamicOrderByCondition = "";

            StringBuilder dynamicInnerJoinConditions = new StringBuilder();
            StringBuilder dynamicDueInnerJoinConditions = new StringBuilder();

            if (searchCriteria.getIdIstanza() != null) {
                map.put("idIstanza", searchCriteria.getIdIstanza());
                dynamicWhereConditions.append(WHERE_ID_ISTANZA);

            }
            if (searchCriteria.getIdAmbito() != null) {
                map.put("idAmbito", searchCriteria.getIdAmbito());
                dynamicWhereConditions.append(WHERE_ID_AMBITO);
            }

            if (searchCriteria.getIdTipoAdempimento() != null) {
                map.put("idTipoAdempimento", searchCriteria.getIdTipoAdempimento());
                dynamicWhereConditions.append(WHERE_ID_TIPO_ADEMPIMENTO);
            }

            if (searchCriteria.getIdAdempimento() != null) {
                map.put("idAdempimento", searchCriteria.getIdAdempimento());
                dynamicWhereConditions.append(WHERE_ID_ADEMPIMENTO);
            }

            if (searchCriteria.getIdCompetenzaTerritorio() != null) {
                map.put("idCompetenzaTerritorio", searchCriteria.getIdCompetenzaTerritorio());
                dynamicWhereConditions.append(WHERE_ID_COMPETENZA_TERRITORIO);
            }

            if (searchCriteria.getAnnoPresentazioneProgetto() != null) {
                map.put("annoPresentazioneProgetto", searchCriteria.getAnnoPresentazioneProgetto());

                if (searchCriteria.getMaggioreDi() != null && searchCriteria.getMaggioreDi()) {
                    dynamicWhereConditions.append(WHERE_ANNO_PRESENTAZIONE_PROGETTO_MAGGIORE);
                }
                else if (searchCriteria.getMinoreDi() != null && searchCriteria.getMinoreDi()) {
                    dynamicWhereConditions.append(WHERE_ANNO_PRESENTAZIONE_PROGETTO_MINORE);
                }
                else {
                    dynamicWhereConditions.append(WHERE_ANNO_PRESENTAZIONE_PROGETTO);
                }
            }
            else {
            	if (StringUtils.isNotBlank(searchCriteria.getFasePubblicazione())) {
                    if (searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.TUTTE.getDescrizione())
                    	|| searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.CONCLUSA.getDescrizione()) ) {
                    	dynamicWhereConditionsOnlyConclusa.append(WHERE_ANNO_PUB_CONCLUSE);
                    }
            	}
            }

            if (StringUtils.isNotBlank(searchCriteria.getCodicePratica())) {
                map.put("codicePratica", searchCriteria.getCodicePratica());
                dynamicWhereConditions.append(WHERE_CODICE_PRATICA);
            }

            if (StringUtils.isNotBlank(searchCriteria.getDenominazioneOggetto())) {
                map.put("denominazione", searchCriteria.getDenominazioneOggetto());
                dynamicWhereConditions.append(WHERE_DENOMINAZIONE_SINTESI);
            }

            if (StringUtils.isNotBlank(searchCriteria.getSiglaProvinciaOggetto())) {
                map.put("siglaProvinciaOggetto", searchCriteria.getSiglaProvinciaOggetto());
                dynamicWhereConditions.append(WHERE_SIGLA_PROVINCIA_OGGETTO);
                dynamicInnerJoinConditions.append(INNER_JOIN_PROVINCIA);
            }

            if (StringUtils.isNotBlank(searchCriteria.getCodIstatComuneOggetto())) {
                map.put("codIstatComuneOggetto", searchCriteria.getCodIstatComuneOggetto());
                dynamicWhereConditions.append(WHERE_COD_ISTAT_COMUNE_OGGETTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getLabelStato())) {
                map.put("labelStato", searchCriteria.getLabelStato());
                map.put("labelStatoMaiuscolo", searchCriteria.getLabelStato().toUpperCase());
                dynamicWhereConditions.append(WHERE_COD_STATO_ISTANZA_2);
            }

            if (StringUtils.isNotBlank(searchCriteria.getCodStatoProcedStatale())) {
            	map.put("codStatoProcedimentoStatale", searchCriteria.getCodStatoProcedStatale());
                dynamicWhereConditions.append(WHERE_COD_STATO_PROCEDIMENTO_STATALE);
            }

            if (searchCriteria.getFlgInfrastruttureStrategiche() != null && searchCriteria.getFlgInfrastruttureStrategiche()) {
                dynamicWhereConditions.append(WHERE_FLAG_INFRA);
            }

            if (searchCriteria.getFlgPnrr() != null && searchCriteria.getFlgPnrr()) {
                dynamicWhereConditions.append(WHERE_FLAG_PNRR);
            }

            if (searchCriteria.getVincolo() != null) {
                map.put("vincolo", searchCriteria.getVincolo());
                dynamicWhereConditions.append(WHERE_VINCOLO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getCodCategoriaOggetto())) {
                map.put("codCategoriaOggetto", searchCriteria.getCodCategoriaOggetto());
                dynamicWhereConditions.append(WHERE_COD_CATEGORIA_OGGETTO);
                if (StringUtils.isBlank(searchCriteria.getDenominazioneOggetto())
                        && (StringUtils.isBlank(searchCriteria.getSiglaProvinciaOggetto()))
                        && (StringUtils.isBlank(searchCriteria.getCodIstatComuneOggetto()))) {
                    dynamicInnerJoinConditions.append(INNER_JOIN_PROVINCIA);
                }
                else if (StringUtils.isBlank(searchCriteria.getSiglaProvinciaOggetto())
                        && (StringUtils.isBlank(searchCriteria.getCodIstatComuneOggetto()))) {
                    dynamicInnerJoinConditions.append(INNER_JOIN_PROVINCIA);
                }
            }

            //SCRIVA-724 - l'ordinamento delle date specifico per fase, deve essere impostato solo se da FE non sono arrivati i parametri di sort
            if (StringUtils.isNotBlank(searchCriteria.getFasePubblicazione())) {
                dynamicDueInnerJoinConditions = dynamicInnerJoinConditions;
                if (searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.TUTTE.getDescrizione())) {
                	if (!StringUtils.isNotBlank(sort)) {
                		dynamicOrderByCondition = " order by data_inserimento_pratica desc";
                	}
                    dynamicDueInnerJoinConditions.append(INNER_JOIN_ANNO_PUB_CONCLUSE);
                }
                else if (searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.CONSULTA.getDescrizione())) {
                	if (!StringUtils.isNotBlank(sort)) {
                		dynamicOrderByCondition = " order by data_fine_osservazioni asc ";
                	}
                	map.put("fasePubblicazione", searchCriteria.getFasePubblicazione());
                	dynamicWhereConditions.append(WHERE_IN_CONSULTAZIONE);
                }
                else if (searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.IN_CORSO.getDescrizione())) {
                	if (!StringUtils.isNotBlank(sort)) {
                		dynamicOrderByCondition = " order by data_protocollo_istanza desc ";
                	}
                    map.put("fasePubblicazione", searchCriteria.getFasePubblicazione());
                    dynamicWhereConditions.append(WHERE_FASE_PUBBLICAZIONE);
                }
                else if (searchCriteria.getFasePubblicazione().equalsIgnoreCase(FasePubblicazioneEnum.CONCLUSA.getDescrizione())) {
                    dynamicDueInnerJoinConditions.append(INNER_JOIN_ANNO_PUB_CONCLUSE);
                    if (!StringUtils.isNotBlank(sort)) {
                    	dynamicOrderByCondition = "  order by data_conclusione_procedimento desc ";
                    }
                    map.put("fasePubblicazione", searchCriteria.getFasePubblicazione());
                    dynamicWhereConditions.append(WHERE_FASE_PUBBLICAZIONE);
                }

            }
            else {
                //DOMANDA SONO TUTTE
                dynamicDueInnerJoinConditions = dynamicInnerJoinConditions;
                dynamicOrderByCondition = " order by data_inserimento_pratica desc";
                dynamicDueInnerJoinConditions.append(INNER_JOIN_ANNO_PUB_CONCLUSE);
            }


            /* PARTE DEGLI AVVISI*/
            if (StringUtils.isNotBlank(searchCriteria.getCodCategoriaAllegato())) {
                map.put("codCategoriaAllegato", searchCriteria.getCodCategoriaAllegato());
                dynamicWhereConditions.append(WHERE_COD_CATEGORIA_ALLEGATO);
                dynamicInnerJoinConditions.append(INNER_JOIN_COD_CATEGORIA_ALLEGATO + "'" + searchCriteria.getCodCategoriaAllegato() + "' ");
                dynamicOrderByCondition = " order by data_fine_osservazioni asc";
            }

            // ordinamento da FE
            if (StringUtils.isNotBlank(sort)) {
                dynamicOrderByCondition = getQuerySortingSegment(sort);
            }

            map.put("componenteApp", attoreScriva.getComponente());
            String query = QUERY_LOAD_ISTANZE_PUBBLICATE_SINTESI
                    .replace("__dynamic_inner_join_conditions__", "\n" + dynamicInnerJoinConditions.toString())
                    .replace("__dynamic_due_inner_join_conditions__", "\n" + dynamicDueInnerJoinConditions.toString())
                    .replace("__dynamic_where_conditions__", dynamicWhereConditions.toString())
                    .replace("__dynamic_where_conditions_only_conclusa__", dynamicWhereConditionsOnlyConclusa.toString())
                    .replace("__dynamic_orderby_conditions__", dynamicOrderByCondition);
            MapSqlParameterSource params = getParameterValue(map);
            logDebug(className, "query eseguita :  \n" + getQuery(query, offset, limit) + "\n");
            return template.query(getQuery(query, offset, limit), params, getRowMapper1());
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
	}
	
	@Override
	public List<Timestamp> getStoricoDataInizioOsservazione(Long idIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_LOAD_DATA_ISTANZE_STORICO_BY_ID_ISTANZA, null, null), params, getTimestampRowMapper("data_inizio_osservazioni"));
     
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
    }
	
	

	@Override
	public List<Timestamp> getStoricoMaxDataFineOsservazione(Long idIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_MAX_DATA_ISTANZE_STORICO_BY_ID_ISTANZA, null, null), params, getTimestampRowMapper("data_fine_osservazioni"));
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
    }


	/**
     * Load mappe string.
     *
     * @param id the id
     * @return the string
     */
    @Override
    public String loadMappe(Long id) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", id);
            return getJsonFromQuerySimple(className, MAPPE, map, null, null);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load istanza by id list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<PubIstanzaDTO> loadIstanzaById(Long idIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            return findListByQuery(className, QUERY_LOAD_PUB_ISTANZE_BY_ID, map);
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from the
     * database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaSearchResultDTO>
     */
    @Override
    public RowMapper<PubIstanzaDTO> getRowMapper() throws SQLException {
        return new PubIstanzaRowMapper();
    }


	@Override
    public RowMapper<SintesiDTO> getRowMapper1() throws SQLException {
        return new SintesiRowMapper();
    }
	
	

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaSearchResultDTO>
     */
    @Override
    public RowMapper<PubIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new PubIstanzaRowMapper();
    }

    /**
     * The type Istanza search resul row mapper.
     */
    public static class PubIstanzaRowMapper implements RowMapper<PubIstanzaDTO> {
        /**
         * Instantiates a new Istanza search resul row mapper.
         *
         * @throws SQLException the sql exception
         */
    	public PubIstanzaRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data in the
         * ResultSet. This method should not call {@code next()} on the ResultSet; it is
         * only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting column values
         *                      (that is, there's no need to catch SQLException)
         */
        @Override
        public PubIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PubIstanzaDTO bean = new PubIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, PubIstanzaDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setCodIstanza(rs.getString("cod_istanza"));
            bean.setContatorePratiche(rs.getLong("CONTATORE_PRATICHE"));
            bean.setFasePubblicazione(rs.getString("fase_pubblicazione"));

            PubStatoIstanzaDTO statoIstanza = new PubStatoIstanzaDTO();
            populateBeanStatoIstanza(rs, statoIstanza);
            bean.setStatoIstanza(statoIstanza);

            PubAdempimentoDTO adempimento = new PubAdempimentoDTO();
            populateBeanAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento);

            bean.setDataProtocollo(rs.getTimestamp("data_protocollo_istanza"));
            //bean.setNumeroProtocollo(rs.getLong("num_protocollo_istanza"));
            bean.setNumeroProtocollo(rs.getString("num_protocollo_istanza"));
            bean.setDataInserimentoIstanza(rs.getTimestamp("data_inserimento_istanza"));
            bean.setDataModificaIstanza(rs.getTimestamp("data_modifica_istanza"));
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            bean.setDataInserimentoPratica(rs.getTimestamp("data_inserimento_pratica"));
            bean.setDataModificaPratica(rs.getTimestamp("data_modifica_pratica"));
            bean.setDesStatoSintesiPagamento(rs.getString("des_stato_sintesi_pagamento"));
            
            //SCRIVA-945
            List<Timestamp> dataInizioOsservazione = new ArrayList<Timestamp>();
            dataInizioOsservazione.add(rs.getTimestamp("data_inizio_osservazioni"));
            bean.setDataInizioOsservazione(dataInizioOsservazione);
            
            bean.setDataFineOsservazione(rs.getTimestamp("data_fine_osservazioni"));
            bean.setFlgOsservazione(rs.getString("flg_osservazioni").equalsIgnoreCase("false") ? Boolean.FALSE : Boolean.TRUE);
            //bean.setDataPubblicazioneEsterna(rs.getTimestamp("data_pubblicazione_esterna"));
            bean.setDataScadenzaProcedimento(rs.getTimestamp("data_scadenza_procedimento"));
            // SCRIVA-847
            bean.setDataConclusioneProcedimento(rs.getTimestamp("data_conclusione_procedimento"));

            // SCRIVA-793
            //bean.setDesEsitoProcedimento(rs.getString("des_esito_procedimento"));
            if (rs.getLong("id_esito_procedimento")>0) {
                EsitoProcedimentoDTO esitoProcedimento = new EsitoProcedimentoDTO();
                esitoProcedimento.setIdEsitoProcedimento(rs.getLong("id_esito_procedimento"));
                bean.setEsitoProcedimento(esitoProcedimento);
            }
            if (rs.getLong("id_esito_proced_statale")>0) {
            	EsitoProcedimentoDTO esitoProcedimentoStatale = new EsitoProcedimentoDTO();
            	esitoProcedimentoStatale.setIdEsitoProcedimento(rs.getLong("id_esito_proced_statale"));
            	bean.setEsitoProcedimentoStatale(esitoProcedimentoStatale);
            }
            if (rs.getLong("id_stato_proced_statale")>0) {
            	StatoProcedimentoStataleDTO statoProcedimentoStatale = new StatoProcedimentoStataleDTO();
            	statoProcedimentoStatale.setIdStatoProcedStatale(rs.getLong("id_stato_proced_statale"));
            	bean.setStatoProcedimentoStatale(statoProcedimentoStatale);
            }
        }

		private void populateBeanStatoIstanza(ResultSet rs, PubStatoIstanzaDTO bean) throws SQLException {
            bean.setCodStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDesStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
        }

        private void populateBeanAdempimento(ResultSet rs, PubAdempimentoDTO bean) throws SQLException {
            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
        }

    }

    /**
     * The type Istanza search resul row mapper.
     */
    public static class SintesiRowMapper implements RowMapper<SintesiDTO> {
        /**
         * Instantiates a new Istanza search resul row mapper.
         *
         * @throws SQLException the sql exception
         */
    	public SintesiRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data in the
         * ResultSet. This method should not call {@code next()} on the ResultSet; it is
         * only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting column values
         *                      (that is, there's no need to catch SQLException)
         */
        @Override
        public SintesiDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	SintesiDTO bean = new SintesiDTO();
            populateBeanSintesi(rs, bean);
            return bean;
        }

        private void populateBeanSintesi(ResultSet rs, SintesiDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            bean.setDataConclusioneProcedimento(rs.getTimestamp("data_conclusione_procedimento"));
            bean.setDataFineOsservazione(rs.getTimestamp("data_fine_osservazioni"));
            bean.setDataInserimentoPratica(rs.getTimestamp("data_inserimento_pratica"));
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setDataProtocolloIstanza(rs.getTimestamp("data_protocollo_istanza"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
            bean.setDenomComune(rs.getString("denom_comune"));
            bean.setDesCompetenzaTerritorio(rs.getString("des_competenza_territorio"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setFasePubblicazione(rs.getString("fase_pubblicazione"));

            bean.setFlgOsservazione(rs.getString("flg_osservazioni").equalsIgnoreCase("false") ? Boolean.FALSE : Boolean.TRUE);
            bean.setLabelStato(rs.getString("label_stato"));

            bean.setContatoreAll(rs.getLong("contatore_tutte"));
            bean.setContatoreConclusa(rs.getLong("contatore_conclusa"));
            bean.setContatoreInConsultazione(rs.getLong("contatore_in_consultazione"));
            bean.setContatoreInCorso(rs.getLong("contatore_in_corso"));

            bean.setFlagContaConclusa(rs.getInt("flag_conta_conclusa"));
            bean.setFlagContaConsultazione(rs.getInt("flag_conta_consultazione"));
            bean.setFlagContaInCorso(rs.getInt("flag_conta_in_corso"));

        }

    }




}