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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.CompilanteDTO;
import it.csi.scriva.scrivabesrv.dto.EsitoProcedimentoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PraticaCollegataDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoProcedimentoStataleDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.LongStringRowMapper;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaDTO> implements IstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String FUNCTION_DELETE = "SELECT fnc_delete_istanza(:idIstanza::INTEGER)";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ where id_istanza = :idIstanza";

    private static final String QUERY_LOAD_BY_UID = "SELECT * FROM _replaceTableName_ where gest_uid = :uidIstanza";

    private static final String QUERY_LOAD_BY_ID_OGGETTO = "SELECT * FROM _replaceTableName_ sti\n" +
            "INNER JOIN scriva_t_oggetto sto ON sto.id_istanza_aggiornamento = sti.id_istanza \n" +
            "WHERE sto.id_oggetto = :idOggetto";

    private static final String QUERY_LOAD_ISTANZE = "with PRESENTATA as (select sris.id_istanza, max (sris.data_cambio_stato) data_invio_istanza\n" + 
		    "from scriva_r_istanza_stato sris\n" + 
    		"inner join scriva_d_stato_istanza sdsi2 on sdsi2.id_stato_istanza = sris.id_stato_istanza\n" + 
    		"where sdsi2.cod_stato_istanza = 'PRESENTATA'\n" + 
    		"group by sris.id_istanza\n)" +
    		"SELECT sti.*, sti.uuid_index AS istanza_uuid_index,\n" +
            "sti.gest_attore_ins AS attore_ins_istanza, sti.gest_data_ins AS data_ins_istanza, sti.gest_attore_upd AS attore_upd_istanza, sti.gest_data_upd as data_upd_istanza, sti.gest_uid as uid_istanza,\n" +
            "sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo, sdad.ind_visibile AS ind_visibile_adempi,\n " +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo,  sdta.uuid_index AS tipo_adempimento_uuid_index,\n" +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo,\n" +
            "sdsi.*, sdsi.id_stato_istanza AS stato_istanza_id,\n" +
            "sdep.cod_esito_procedimento , sdep.des_esito_procedimento, sdep.flg_positivo,\n" +
            "sdeps.cod_esito_procedimento AS cod_esito_procedimento_statale, sdeps.des_esito_procedimento AS des_esito_procedimento_statale, sdeps.flg_positivo AS flg_positivo_statale,\n" +
            "sdsps.*\n" +
            ", pr.data_invio_istanza\n" +
            "FROM _replaceTableName_ sti\n" +
            "INNER JOIN scriva_d_adempimento sdad ON sti.id_adempimento = sdad.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sti.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "LEFT JOIN scriva_d_esito_procedimento sdep ON sti.id_esito_procedimento = sdep.id_esito_procedimento\n" +
            "LEFT JOIN scriva_d_esito_procedimento sdeps ON sti.id_esito_proced_statale = sdeps.id_esito_procedimento\n" +
            "LEFT JOIN scriva_d_stato_proced_statale sdsps ON sti.id_stato_proced_statale = sdsps.id_stato_proced_statale\n" +
            "LEFT JOIN presentata pr on pr.id_istanza = sti.id_istanza\n";

    private static final String QUERY_LOAD_BY_ID_ISTANZA = QUERY_LOAD_ISTANZE + "WHERE sti.id_istanza = :idIstanza\n";

    private static final String QUERY_LOAD_BY_COD_ISTANZA = QUERY_LOAD_ISTANZE + "WHERE sti.cod_istanza = :codIstanza\n";

    private static final String QUERY_LOAD_BY_UID_ISTANZA = QUERY_LOAD_ISTANZE + "WHERE sti.gest_uid = :uidIstanza\n";

    private static final String QUERY_LOAD_BY_ID_OGGETTO_ISTANZA = QUERY_LOAD_ISTANZE +
            "INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_istanza = sti.id_istanza\n" +
            "WHERE stoi.id_oggetto_istanza = :idOggettoIstanza\n";

    private static final String QUERY_LOAD_ISTANZE_BY_ID_COMPILANTE = QUERY_LOAD_ISTANZE + "WHERE sti.id_compilante = :idCompilante\n";

    private static final String QUERY_INSERT_ISTANZA = "INSERT INTO _replaceTableName_ \n"
            + "(id_istanza, cod_istanza, id_stato_istanza, id_adempimento, data_inserimento_istanza, data_modifica_istanza, \n"
            + "json_data, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid, \n"
            + "uuid_index, cod_pratica, id_template, id_istanza_attore_owner, data_inserimento_pratica, data_modifica_pratica, \n"
            + "id_istanza_attore, des_stato_sintesi_pagamento, id_funzionario, data_pubblicazione, \n"
            + "num_protocollo_istanza, data_protocollo_istanza, data_inizio_osservazioni, data_fine_osservazioni, data_conclusione_procedimento, \n"
            + "id_esito_procedimento, data_scadenza_procedimento, anno_registro, id_stato_proced_statale, id_esito_proced_statale)\n"
            + "VALUES(nextval('seq_scriva_t_istanza'), :codIstanza, :idStatoIstanza, :idAdempimento, :dataInserimentoIstanza, :dataModIst, \n"
            + "to_json(:jsonData::json), :dateIns, :attoreIns, :dateUpd, :attoreUpd, :uid, \n"
            + ":uuidIndex, :codPratica, :idTemplate, :idIstanzaAttoreOwner, :dataInserimentoPratica, :dataModificaPratica, \n"
            + ":idIstanzaAttore, :desStatoSintesiPagamento, :idFunzionario, :dataPubblicazione, \n"
            + ":numProtocolloIstanza, :dataProtocolloIstanza, :dataInizioOsservazioni, :dataFineOsservazioni, :dataConclusioneProcedimento,\n"
            + ":idEsitoProcedimento, :dataScadenzaProcedimento, :annoRegistro, :idStatoProcedStatale, :idEsitoProcedStatale)\n";

    private static final String QUERY_UPDATE_ISTANZA = "UPDATE _replaceTableName_\n"
            + "SET id_stato_istanza=:idStatoIstanza, id_adempimento=:idAdempimento, data_modifica_istanza=:dataModIst,\n"
            + "json_data=to_json(:jsonData::json), gest_data_upd=:dateUpd, gest_attore_upd=:attoreUpd,\n"
            + "uuid_index=:uuidIndex, cod_pratica=:codPratica, id_template=:idTemplate, id_istanza_attore_owner=:idIstanzaAttoreOwner, data_inserimento_pratica=:dataInserimentoPratica, data_modifica_pratica=:dataModificaPratica,\n"
            + "id_istanza_attore=:idIstanzaAttore, des_stato_sintesi_pagamento=:desStatoSintesiPagamento, id_funzionario=:idFunzionario, data_pubblicazione=:dataPubblicazione,\n"
            + "num_protocollo_istanza=:numProtocolloIstanza, data_protocollo_istanza=:dataProtocolloIstanza, data_inizio_osservazioni=:dataInizioOsservazioni, data_fine_osservazioni=:dataFineOsservazioni, data_conclusione_procedimento=:dataConclusioneProcedimento,\n"
            + "id_esito_procedimento=:idEsitoProcedimento, data_scadenza_procedimento=:dataScadenzaProcedimento,\n"
            + "anno_registro=:annoRegistro, id_stato_proced_statale=:idStatoProcedStatale, id_esito_proced_statale=:idEsitoProcedStatale\n"
            + "WHERE id_istanza = :idIstanza";

    private static final String QUERY_UPDATE_JSONDATA_ISTANZA = "UPDATE _replaceTableName_ \n"
            + "SET json_data = to_json(:jsonData::json), gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n"
            + "WHERE id_istanza=:idIstanza";

    private static final String QUERY_NEW_PROGRESSIVO = "SELECT nextval('seq_scriva_cod_istanza') as new_prog";

    private static final String QUERY_UPDATE_STATO_ISTANZA = "UPDATE _replaceTableName_ \n"
            + "SET id_stato_istanza = :idStatoIstanza, gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n"
            + "WHERE gest_uid = :uidIstanza";

    private static final String QUERY_UPDATE_ISTANZA_ATTORE = "UPDATE _replaceTableName_ \n"
            + "SET id_istanza_attore_owner = :idIstanzaAttoreOwner, id_istanza_attore = :idIstanzaAttore,\n"
            + "gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n"
            + "WHERE id_istanza=:idIstanza";

    private static final String QUERY_INSERT_STORICO_ISTANZA = "INSERT INTO scriva_r_istanza_storico\n" +
            "(id_istanza_storico,\n" +
            "id_istanza, cod_istanza, cod_pratica, id_stato_istanza, id_adempimento, data_inserimento_istanza, data_modifica_istanza,\n " +
            "data_inserimento_pratica, data_modifica_pratica, json_data, uuid_index, id_istanza_attore_owner, id_template, id_istanza_attore,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid,\n" +
            "des_stato_sintesi_pagamento, id_funzionario, data_pubblicazione, num_protocollo_istanza, data_protocollo_istanza,\n" +
            "data_inizio_osservazioni, data_fine_osservazioni, data_conclusione_procedimento, id_esito_procedimento,\n" +
            "data_scadenza_procedimento, anno_registro, id_stato_proced_statale, id_esito_proced_statale)\n" +
            "SELECT nextval('seq_scriva_r_istanza_storico'), " +
            "id_istanza, cod_istanza, cod_pratica, id_stato_istanza, id_adempimento, data_inserimento_istanza, data_modifica_istanza,\n " +
            "data_inserimento_pratica, data_modifica_pratica, to_json(:jsonData::json), uuid_index, id_istanza_attore_owner, id_template, id_istanza_attore,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid,\n" +
            "des_stato_sintesi_pagamento, id_funzionario, data_pubblicazione, num_protocollo_istanza, data_protocollo_istanza,\n" +
            "data_inizio_osservazioni, data_fine_osservazioni, data_conclusione_procedimento, id_esito_procedimento,\n" +
            "data_scadenza_procedimento, anno_registro, id_stato_proced_statale, id_esito_proced_statale\n" +
            "FROM _replaceTableName_ sti WHERE sti.id_istanza = :idIstanza";

    private static final String QUERY_UPDATE_UUID_INDEX = "UPDATE _replaceTableName_ \n"
            + "SET uuid_index = :uuidIndex\n"
            + "WHERE id_istanza = :idIstanza";

    private static final String QUERY_LOAD_PRATICHE_COLLEGATE_OGGETTO = "SELECT sto.id_oggetto, sti.id_istanza, sti.cod_istanza, cod_pratica, stsi.cognome, stsi.nome, stsi.den_soggetto\n" +
            "FROM scriva_t_oggetto sto \n" +
            "INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_oggetto = sto.id_oggetto\n" +
            "INNER JOIN _replaceTableName_ sti ON sti.id_istanza = stoi.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sti.id_stato_istanza\n" +
            "INNER JOIN scriva_t_soggetto_istanza stsi ON (stsi.id_istanza = sti.id_istanza AND stsi.id_soggetto_padre IS NULL)\n" +
            "__dynamic_join__ " +
            "WHERE sdsi.ind_ricerca_oggetto = 'S'\n" +
            "AND sto.id_oggetto IN (:idOggetti)\n" +
            "UNION\n" +
            "SELECT sto.id_oggetto, sti.id_istanza, sti.cod_istanza, cod_pratica, stsi.cognome, stsi.nome, stsi.den_soggetto\n" +
            "FROM scriva_t_oggetto sto \n" +
            "INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_oggetto = sto.id_oggetto\n" +
            "INNER JOIN _replaceTableName_ sti ON sti.id_istanza = stoi.id_istanza\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sti.id_stato_istanza\n" +
            "INNER jOIN scriva_d_esito_procedimento sdep ON sdep.id_esito_procedimento = sti.id_esito_procedimento\n" +
            "INNER JOIN scriva_t_soggetto_istanza stsi ON (stsi.id_istanza = sti.id_istanza AND stsi.id_soggetto_padre IS NULL)\n" +
            "__dynamic_join__ " +
            "WHERE sdsi.ind_ricerca_oggetto like '%'||sdep.cod_esito_procedimento||'%'\n" +
            "AND sto.id_oggetto IN (:idOggetti)\n";

    private static final String JOIN_PRATICHE_ANNO_PRESENTAZIONE = "LEFT JOIN scriva_r_istanza_stato sris ON (sris.id_istanza = sti.id_istanza AND EXTRACT(YEAR FROM sris.data_cambio_stato) >= :annoPresentazione)\n" +
            "LEFT JOIN scriva_d_stato_istanza sdis ON sdis.id_stato_istanza = sris.id_stato_istanza AND UPPER(sdis.cod_stato_istanza)='PRESENTATA'\n";

    private static final String QUERY_LOAD_CODICE_PRATICA_OLD = "SELECT EXTRACT(YEAR FROM CURRENT_DATE)||'-'||(MAX(SPLIT_PART(SPLIT_PART(sti.cod_pratica,'-',2),'/',1))::INT+1)||'/'||:codiceTipoAdempimento||'-'||:codiceAdempimento AS codice_pratica\n" +
            "FROM scriva_t_istanza sti\n" +
            "WHERE sti.cod_pratica LIKE (SELECT EXTRACT(YEAR FROM CURRENT_DATE))||'%'\n";

//    private static final String QUERY_LOAD_CODICE_PRATICA = "SELECT COALESCE(:annoRegistro||'-'||(MAX(SPLIT_PART(SPLIT_PART(sti.cod_pratica,'-',2),'/',1)::INT)+1)||'/'||:codiceTipoAdempimento||'-'||:codiceAdempimento, :annoRegistro ||'-1/'||:codiceTipoAdempimento||'-'||:codiceAdempimento) AS codice_pratica\n" +
//            "FROM scriva_t_istanza sti\n" +
//            "WHERE sti.cod_pratica LIKE (:annoRegistro)||'%'\n";
    
    private static final String QUERY_LOAD_CODICE_PRATICA = "SELECT COALESCE(:annoRegistro||'-'||(MAX(SPLIT_PART(SPLIT_PART(sti.cod_pratica,'-',2),'/',1)::INT)+1)||'/'||:codiceTipoAdempimento||'-'||:codiceAdempimento, :annoRegistro ||'-1/'||:codiceTipoAdempimento||'-'||:codiceAdempimento) AS codice_pratica\r\n" + 
    		"FROM scriva_t_istanza sti\r\n" + 
    		"inner join scriva_d_adempimento sdta on sdta.id_adempimento = sti.id_adempimento\r\n" +  
    		"inner join scriva_d_tipo_adempimento sdta2 on sdta2.id_tipo_adempimento = sdta.id_tipo_adempimento\r\n" + 
    		"WHERE sti.cod_pratica LIKE (:annoRegistro)||'%'\r\n" + 
    		"and sti.gest_attore_ins <>'MIGRAZIONE'\r\n" + 
    		"--and sti.id_adempimento = :id_adempimento\r\n" + 
    		"and sdta2.cod_tipo_adempimento = :codiceTipoAdempimento\r\n" + 
    		"and sdta.cod_adempimento = :codiceAdempimento";

    private static final String EXTRACT_YEAR = "EXTRACT(YEAR FROM CURRENT_DATE)";

    private static final String QUERY_LOAD_JSON_CODICE_PRATICA = "SELECT row_to_json(json) FROM (\n" +
            QUERY_LOAD_CODICE_PRATICA +
            ") AS json";

    private static final String QUERY_UPDATE_DATA_PUBBLICAZIONE = "UPDATE _replaceTableName_ \n"
            + "SET data_pubblicazione = :dataPubblicazione\n" +
            ", id_istanza_attore = :idIstanzaAttore, id_funzionario = :idFunzionario\n"+
            ", data_modifica_istanza = :dataModIst, data_modifica_pratica = :dataModificaPratica\n"+
            ", gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n\n"
            + "WHERE id_istanza = :idIstanza";

    private static final String QUERY_UPDATE_DATA_CONCL_PROC_FROM_PROVV_FINALE = "UPDATE _replaceTableName_ sti\n" +
            "SET gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd\n" +
           // ", id_istanza_attore = :idIstanzaAttore, id_funzionario = :idFunzionario\n"+ jira SCRIVA-1601
            ", id_funzionario = :idFunzionario\n"+
            ", data_modifica_istanza = :dataModIst, data_modifica_pratica = :dataModificaPratica\n"+
            ", data_conclusione_procedimento = allegato.data_atto\n" +
            "FROM (\n" +
            "    SELECT MAX(stai.data_atto) data_atto, stai.id_istanza\n" +
            "    FROM scriva_t_allegato_istanza stai\n" +
            "    INNER JOIN scriva_d_classe_allegato sdca ON sdca.id_classe_allegato = stai.id_classe_allegato \n" +
            "    WHERE sdca.cod_classe_allegato = 'PROVV_FINALE' \n" +
            "    AND id_istanza = :idIstanza\n" +
            "    AND data_cancellazione IS NULL\n" +
            "    GROUP BY id_istanza\n" +
            ") allegato\n" +
            "WHERE sti.id_istanza = :idIstanza";

    private static final String QUERY_LOAD_ISTANZA_IND_MODIFICABILE ="select distinct \n" + 
    		"sti.id_istanza, \n" + 
    		"UPPER(srsia.ind_modificabile)as ind_modificabile \n" + 
    		"from _replaceTableName_ sti\n" + 
    		"inner join scriva_r_stato_istanza_adempi srsia on sti.id_stato_istanza = srsia.id_stato_istanza and sti.id_adempimento = srsia.id_adempimento\n" + 
    		"where sti.id_istanza in (:idIstanze)\n";

    private static final String QUERY_UPDATE_ISTANZA_TIMESTAMP ="UPDATE _replaceTableName_ \n"+
            "SET data_modifica_istanza = :dataModificaIstanza\n" +
            ", id_istanza_attore = :idIstanzaAttore \n" +
            "WHERE id_istanza = :idIstanza";
					
    private static final String QUERY_UPDATE_PRATICA_TIMESTAMP = "UPDATE _replaceTableName_ \n" +
            "SET data_modifica_pratica = :dataModificaPratica\n" +
            ", id_funzionario = :idFunzionario\n"+
            "WHERE id_istanza = :idIstanza";        




            private static final String QUERY_CALCOLA_GESTIONE_ATTORE_FO = 
            "WITH istanza_modificabile AS ( " +
            "SELECT DISTINCT id_istanza AS istMod, 1 AS isModificabile " +
            "FROM scriva_r_stato_istanza_adempi srsia " +
            "INNER JOIN scriva_t_istanza ist ON ist.id_stato_istanza = srsia.id_stato_istanza " +
            "WHERE srsia.id_adempimento = ist.id_adempimento " +
            "AND srsia.ind_modificabile LIKE '%' || :codComponenteApp || '%' " + // COMPONENTE CHIAMANTE
            "AND ist.id_istanza IN (:idIstanza) " +
            "), gestionePrioritaria AS ( " +
            "SELECT id_istanza, id_profilo_app, cod_profilo_app, id_gestione_attore, cod_gestione_attore, ordinamento_gestione_attore " +
            "FROM ( " +
            "SELECT sti2.id_istanza, sdpa2.id_profilo_app, sdpa2.cod_profilo_app, sdga2.id_gestione_attore, sdga2.cod_gestione_attore, sdga2.ordinamento_gestione_attore, " +
            "RANK() OVER (PARTITION BY sti2.id_istanza ORDER BY ordinamento_gestione_attore) AS gest_rank " +
            "FROM scriva_r_istanza_attore sria2 " +
            "INNER JOIN scriva_t_istanza sti2 ON sti2.id_istanza = sria2.id_istanza " +
            "INNER JOIN scriva_d_profilo_app sdpa2 ON sdpa2.id_profilo_app = sria2.id_profilo_app " +
            "INNER JOIN scriva_d_componente_app comp ON comp.id_componente_app = sdpa2.id_componente_app " +
            "LEFT JOIN scriva_d_gestione_attore sdga2 ON sdga2.id_gestione_attore = sdpa2.id_gestione_attore " +
            "WHERE sti2.id_istanza IN (:idIstanza) " +
            "AND sria2.data_revoca IS NULL " +
            "AND sria2.cf_attore = :cfAttore " +
            "AND comp.cod_componente_app = :codComponenteApp " +
            ") listaGestioneAttore " +
            "WHERE listaGestioneAttore.gest_rank = 1 " +
            "GROUP BY id_istanza, id_profilo_app, cod_profilo_app, id_gestione_attore, cod_gestione_attore, ordinamento_gestione_attore " +
            ") " +
            "SELECT ist.id_istanza, " +
            "STRING_AGG(DISTINCT CASE " +
            "WHEN gestAtt.cod_gestione_attore = 'WRITE' AND im.isModificabile IS NULL THEN 'WRITE_LOCK' " +
            "WHEN srtap.flg_sola_lettura = 1 THEN 'READ_ONLY' " +
            "WHEN gestAtt.cod_gestione_attore LIKE 'LOCK_%' AND im.isModificabile IS NULL THEN 'WRITE_LOCK' " +
            "WHEN gestAtt.cod_gestione_attore LIKE 'QDR_%' AND im.isModificabile IS NULL THEN 'WRITE_LOCK' " +
            "WHEN gestAtt.id_profilo_app IS NULL THEN 'READ_ONLY' " +
            "ELSE gestAtt.cod_gestione_attore " +
            "END, '|') AS attore_gestione_istanza " +
            "FROM scriva_t_istanza ist " +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = ist.id_stato_istanza " +
            "AND sdsi.ind_visibile LIKE '%' || :codComponenteApp || '%' " +
            "INNER JOIN scriva_d_adempimento ad ON ad.id_adempimento = ist.id_adempimento " +
            "LEFT JOIN istanza_modificabile im ON im.istMod = ist.id_istanza " +
            "INNER JOIN gestionePrioritaria gestAtt ON gestAtt.id_istanza = ist.id_istanza " +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = gestAtt.id_profilo_app " +
            "AND srtap.id_tipo_adempimento = ad.id_tipo_adempimento " +
            "WHERE ist.id_istanza IN (:idIstanza) " +
            "GROUP BY ist.id_istanza " +
            "ORDER BY ist.id_istanza;";


            private static final String QUERY_CALCOLA_GESTIONE_ATTORE_BO = 
            "WITH istanza_modificabile AS ( " +
            "SELECT DISTINCT ist.id_istanza AS istMod, 1 AS isModificabile " +
            "FROM scriva_r_stato_istanza_adempi srsia " +
            "INNER JOIN scriva_t_istanza ist ON ist.id_istanza IN (:idIstanza) " +
            "WHERE srsia.id_adempimento = ist.id_adempimento " +       
            "AND srsia.id_stato_istanza = ist.id_stato_istanza " +          
            "AND srsia.ind_modificabile LIKE '%' || :codComponenteApp || '%' " +
            "), gestionePrioritaria AS ( " +
            "SELECT id_istanza, id_profilo_app, cod_profilo_app, id_gestione_attore, cod_gestione_attore, ordinamento_gestione_attore " +
            "FROM ( " +
            "SELECT sti.id_istanza, sti.id_stato_istanza, f.id_funzionario, fp.id_profilo_app, sdpa.cod_profilo_app, sdga.id_gestione_attore, " +
            "sdga.cod_gestione_attore, sdga.ordinamento_gestione_attore, " +
            "RANK() OVER (PARTITION BY sti.id_istanza ORDER BY sdga.ordinamento_gestione_attore) AS gest_rank " +
            "FROM scriva_t_istanza sti " +
            "INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento = sti.id_adempimento " +
            "INNER JOIN (WITH istanza_compete AS ( " +
            "SELECT * FROM scriva_r_istanza_competenza sric WHERE id_istanza IN (:idIstanza) " +
            ") SELECT * FROM istanza_compete WHERE flg_autorita_assegnata_bo = 1 " +
            "UNION ALL SELECT * FROM istanza_compete WHERE flg_autorita_principale = 1 " +
            "AND NOT EXISTS (SELECT 1 FROM istanza_compete WHERE flg_autorita_assegnata_bo = 1)) istcomp ON istcomp.id_istanza = sti.id_istanza " +
            "INNER JOIN scriva_r_funzionario_compete fc ON fc.id_competenza_territorio = istcomp.id_competenza_territorio " +
            "INNER JOIN scriva_t_funzionario f ON f.id_funzionario = fc.id_funzionario " +
            "INNER JOIN scriva_r_funzionario_profilo fp ON fp.id_funzionario = f.id_funzionario " +
            "INNER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = fp.id_profilo_app " +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = sdpa.id_componente_app " +
            "INNER JOIN scriva_r_tipo_adempi_profilo tap ON tap.id_profilo_app = sdpa.id_profilo_app " +
            "AND tap.id_tipo_adempimento = sda.id_tipo_adempimento " +
            "LEFT JOIN scriva_d_gestione_attore sdga ON sdga.id_gestione_attore = sdpa.id_gestione_attore " +
            "WHERE sti.id_istanza IN (:idIstanza) AND f.cf_funzionario = :cfFunzionario " +
            "AND sdca.cod_componente_app = :codComponenteApp " +
            ") listaGestioneAttore " +
            "WHERE listaGestioneAttore.gest_rank = 1 " +
            "GROUP BY id_istanza, id_profilo_app, cod_profilo_app, id_gestione_attore, cod_gestione_attore, ordinamento_gestione_attore " +
            ") " +
            "SELECT ist.id_istanza, " +
            "STRING_AGG(DISTINCT CASE " +
            "WHEN gestAtt.cod_gestione_attore = 'WRITE' AND im.isModificabile IS NULL THEN 'WRITE_LOCK' " +
            "WHEN srtap.flg_sola_lettura = 1 THEN 'READ_ONLY' " +
            "WHEN gestAtt.cod_gestione_attore LIKE 'LOCK_%' AND im.isModificabile IS NULL THEN 'WRITE_LOCK' " +
            "WHEN gestAtt.cod_gestione_attore LIKE 'QDR_%' AND im.isModificabile IS NULL THEN 'WRITE_LOCK' " +
            "WHEN gestAtt.id_profilo_app IS NULL THEN 'READ_ONLY' " +
            "ELSE gestAtt.cod_gestione_attore " +
            "END, '|') AS attore_gestione_istanza " +
            "FROM scriva_t_istanza ist " +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = ist.id_stato_istanza " +
            "AND sdsi.ind_visibile LIKE '%' || :codComponenteApp || '%' " +
            "INNER JOIN scriva_d_adempimento ad ON ad.id_adempimento = ist.id_adempimento " +
            "LEFT JOIN istanza_modificabile im ON im.istMod = ist.id_istanza " +
            "LEFT JOIN gestionePrioritaria gestAtt ON gestAtt.id_istanza = ist.id_istanza " +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = gestAtt.id_profilo_app " +
            "AND srtap.id_tipo_adempimento = ad.id_tipo_adempimento " +
            "WHERE ist.id_istanza IN (:idIstanza) " +
            "GROUP BY ist.id_istanza " +
            "ORDER BY ist.id_istanza;";

                


    /**
     * Load istanze list.
     *
     * @return List<IstanzaExtendedDTO>        list
     */
    @Override
    public List<IstanzaExtendedDTO> loadIstanze() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_ISTANZE, null);
    }

    /**
     * Load istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaExtendedDTO>       list
     */
    @Override
    public List<IstanzaExtendedDTO> loadIstanza(Long idIstanza) {
        logBegin(className);

        List<IstanzaExtendedDTO> listaIStanze = new ArrayList<>(); 
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            String getQueryString = getQuery(QUERY_LOAD_BY_ID_ISTANZA, null, null);
            listaIStanze = template.query(getQueryString, params, getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
        return listaIStanze;
    }

    /**
     * Load istanza list.
     *
     * @param codIstanza the cod istanza
     * @return the list
     */
    @Override
    public List<IstanzaExtendedDTO> loadIstanza(String codIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codIstanza", codIstanza);
        return findListByQuery(className, QUERY_LOAD_BY_COD_ISTANZA, map);
    }

    /**
     * Load istanza by uid list.
     *
     * @param uidIstanza uidIstanza
     * @return List<IstanzaExtendedDTO>        list
     */
    @Override
    public List<IstanzaExtendedDTO> loadIstanzaByUID(String uidIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uidIstanza", uidIstanza);
        return findListByQuery(className, QUERY_LOAD_BY_UID_ISTANZA, map);
    }

    /**
     * Load istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    @Override
    public List<IstanzaExtendedDTO> loadIstanzaByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        return findListByQuery(className, QUERY_LOAD_BY_ID_OGGETTO_ISTANZA, map);
    }

    /**
     * Load istanze by id compilante list.
     *
     * @param idCompilante idCompilante
     * @return List<IstanzaExtendedDTO>        list
     */
    @Override
    public List<IstanzaExtendedDTO> loadIstanzeByIdCompilante(Long idCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompilante", idCompilante);
        return findListByQuery(className, QUERY_LOAD_ISTANZE_BY_ID_COMPILANTE, map);
    }

    /**
     * Load istanze by cf compilante list.
     *
     * @param cfCompilante codice fiscale compilante
     * @return List<IstanzaExtendedDTO>        list
     */
    @Override
    public List<IstanzaExtendedDTO> loadIstanzeByCfCompilante(String cfCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfCompilante", cfCompilante);
        return findListByQuery(className, QUERY_LOAD_ISTANZE_BY_ID_COMPILANTE, map);
    }
    
    public Map<Long, String> loadIstanzaIndModificabile(List<Long> idIstanze)
    {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanze", idIstanze);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_LOAD_ISTANZA_IND_MODIFICABILE, null, null), params, new ResultSetExtractor<Map<Long, String>>() {
                @Override
                public Map<Long, String> extractData(ResultSet rs) throws SQLException {
                    Map<Long, String> risultato = new HashMap<>();
                    while (rs.next()) {
                        long id_istanza = rs.getLong("id_istanza"); 
                        String ind_modificabile = rs.getString("ind_modificabile"); 
                                                  
                        risultato.put(id_istanza,ind_modificabile);
                    }
                    return risultato;
                }
            });
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    	
    }
    /**
     * Save istanza long.
     *
     * @param dto                   IstanzaDTO
     * @param codiceTipoAdempimento codiceTipoAdempimento
     * @param codiceAdempimento     the codice adempimento
     * @param saveStorico           the save storico
     * @return id record salvato
     */
    @Override
    public Long saveIstanza(IstanzaDTO dto, String codiceTipoAdempimento, String codiceAdempimento, boolean saveStorico, String componenteApplicativa) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();

            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();

            Integer anno = cal.get(Calendar.YEAR);

            Integer newProgAnno = this.getNewProgressivoAnno(anno, dto.getIdAdempimento());

            //StringBuilder codiceIstanza = new StringBuilder(codiceAdempimento).append("-").append(String.format("%04d", newProgAnno));
            //StringBuilder codiceIstanza = new StringBuilder(codiceTipoAdempimento).append("-").append(codiceAdempimento).append("-").append(newProgAnno); //SCRIVA-408
            StringBuilder codiceIstanza = new StringBuilder("IST-").append(codiceAdempimento).append("-").append(newProgAnno); //SCRIVA-1189
            LOGGER.info("[IstanzaDAOImpl::saveIstanza] INFO codiceIstanza : " + codiceIstanza);

            map.put("codIstanza", String.valueOf(codiceIstanza));
            map.put("idStatoIstanza", dto.getIdStatoIstanza());
            map.put("idAdempimento", dto.getIdAdempimento());
            map.put("dataInserimentoIstanza", now);
            map.put("dataModIst", now);
            map.put("jsonData", dto.getJsonData());
            map.put("dateIns", now);
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreIns());
            map.put("uuidIndex", dto.getUuidIndex());
            map.put("codPratica", null); // TODO GENERAZIONE PRATICA
            map.put("idTemplate", dto.getIdTemplate());
            map.put("idIstanzaAttoreOwner", dto.getIdIstanzaAttoreOwner());

            map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
            map.put("desStatoSintesiPagamento", dto.getDesStatoSintesiPagamento());
            map.put("idFunzionario", dto.getIdFunzionario());
            map.put("dataPubblicazione", dto.getDataPubblicazione());
            map.put("numProtocolloIstanza", dto.getNumProtocolloIstanza());
            map.put("dataProtocolloIstanza", dto.getDataProtocolloIstanza());
            map.put("dataInizioOsservazioni", dto.getDataInizioOsservazioni());
            map.put("dataFineOsservazioni", dto.getDataFineOsservazioni());
            map.put("dataConclusioneProcedimento", dto.getDataConclusioneProcedimento());
            map.put("idEsitoProcedimento", dto.getIdEsitoProcedimento());
            map.put("dataScadenzaProcedimento", dto.getDataScadenzaProcedimento());
            map.put("annoRegistro", dto.getAnnoRegistro());
            map.put("idStatoProcedStatale", dto.getIdStatoProcedStatale());
            map.put("idEsitoProcedStatale", dto.getIdEsitoProcedStatale());
            //jira 1456
            if(ComponenteAppEnum.BO.name().equalsIgnoreCase(componenteApplicativa)){
                map.put("dataInserimentoPratica", now);
                map.put("dataModificaPratica", now);
            }
            else{
                map.put("dataInserimentoPratica", dto.getDataInserimentoPratica());
                map.put("dataModificaPratica", dto.getDataModificaPratica());
            }

            String uid = generateGestUID(String.valueOf(codiceIstanza) + now);
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_ISTANZA, null, null), params, keyHolder, new String[]{"id_istanza"});
            Number key = keyHolder.getKey();
            if (key != null && saveStorico) {
                saveStoricoIstanza(key.longValue(), dto.getJsonData());
            }
            return key != null ? key.longValue() : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza integer.
     *
     * @param dto            IstanzaDTO
     * @param saveStorico    the save storico
     * @param flgCreaPratica the flg crea pratica
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIstanza(IstanzaDTO dto, boolean saveStorico, boolean flgCreaPratica) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();

            Date now = Calendar.getInstance().getTime();

            IstanzaDTO istanza = this.findByPK(dto.getIdIstanza());
            if (null == istanza) {
                logError(className, "Record non trovato con id [" + dto.getIdIstanza() + "]");
                return -1;
            }

            map.put("idIstanza", dto.getIdIstanza());
            map.put("idStatoIstanza", dto.getIdStatoIstanza() != null && dto.getIdStatoIstanza() != 0 ? dto.getIdStatoIstanza() : istanza.getIdStatoIstanza());
            map.put("idAdempimento", dto.getIdAdempimento() != null && dto.getIdAdempimento() != 0 ? dto.getIdAdempimento() : istanza.getIdAdempimento());
            //map.put("dataModIst", dto.getDataModificaIstanza() != null ? dto.getDataModificaIstanza() : now);


            map.put("jsonData", StringUtils.isNotBlank(dto.getJsonData()) ? dto.getJsonData() : istanza.getJsonData());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreUpd());

            map.put("uuidIndex", StringUtils.isNotBlank(dto.getUuidIndex()) ? dto.getUuidIndex() : istanza.getUuidIndex());

            Integer annoRegistroOld = istanza.getAnnoRegistro() != null && istanza.getAnnoRegistro() > 0 ? istanza.getAnnoRegistro() : null;
            Integer annoRegistro = dto.getAnnoRegistro() != null && dto.getAnnoRegistro() > 0 ? dto.getAnnoRegistro() : annoRegistroOld;
            map.put("codPratica", flgCreaPratica && StringUtils.isBlank(istanza.getCodPratica()) ? getCodicePratica(dto.getIdIstanza(), annoRegistro) : istanza.getCodPratica());
            if (Boolean.TRUE.equals(flgCreaPratica)) {
                map.put("dataInserimentoPratica", now);
            } else {
                map.put("dataInserimentoPratica", dto.getDataInserimentoPratica() != null ? dto.getDataInserimentoPratica() : istanza.getDataInserimentoPratica());
            }

            if (StringUtils.isBlank(istanza.getCodPratica())) {
                map.put("dataModIst", now);
                map.put("dataModificaPratica", now); //istanza.getDataModificaPratica());
            } else {
                map.put("dataModIst", istanza.getDataModificaIstanza());
                map.put("dataModificaPratica", now);
            }

            map.put("idTemplate", dto.getIdTemplate() != null && dto.getIdTemplate() != 0 ? dto.getIdTemplate() : istanza.getIdTemplate());

            Long idIstanzaAttoreOwnerOld = istanza.getIdIstanzaAttoreOwner() != 0 ? istanza.getIdIstanzaAttoreOwner() : null;
            map.put("idIstanzaAttoreOwner", dto.getIdIstanzaAttoreOwner() != null && dto.getIdIstanzaAttoreOwner() != 0 ? dto.getIdIstanzaAttoreOwner() : idIstanzaAttoreOwnerOld);

            Long idIstanzaAttoreOld = istanza.getIdIstanzaAttore() != 0 ? istanza.getIdIstanzaAttore() : null;
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore() != null && dto.getIdIstanzaAttore() != 0 ? dto.getIdIstanzaAttore() : idIstanzaAttoreOld);

            Long idFunzionarioOld = istanza.getIdFunzionario() != 0 ? istanza.getIdFunzionario() : null;
            map.put("idFunzionario", dto.getIdFunzionario() != null && dto.getIdFunzionario() != 0 ? dto.getIdFunzionario() : idFunzionarioOld);

            map.put("desStatoSintesiPagamento", StringUtils.isNotBlank(dto.getDesStatoSintesiPagamento()) ? dto.getDesStatoSintesiPagamento() : istanza.getDesStatoSintesiPagamento());
            map.put("dataPubblicazione", dto.getDataPubblicazione() != null ? dto.getDataPubblicazione() : null); //istanza.getDataPubblicazione());

            map.put("numProtocolloIstanza", StringUtils.isNotBlank(dto.getNumProtocolloIstanza()) ? dto.getNumProtocolloIstanza() : istanza.getNumProtocolloIstanza());
            map.put("dataProtocolloIstanza", dto.getDataProtocolloIstanza() != null ? dto.getDataProtocolloIstanza() : istanza.getDataProtocolloIstanza());
            map.put("dataInizioOsservazioni", dto.getDataInizioOsservazioni());
            map.put("dataFineOsservazioni", dto.getDataFineOsservazioni());
            //TODO: rivedere il flusso dei dati quando ci sono update parziali;
            map.put("dataConclusioneProcedimento", dto.getDataConclusioneProcedimento() != null ? dto.getDataConclusioneProcedimento() : null); // istanza.getDataConclusioneProcedimento()

            map.put("idEsitoProcedimento", dto.getIdEsitoProcedimento() != null && dto.getIdEsitoProcedimento() > 0 ? dto.getIdEsitoProcedimento() : (istanza.getIdEsitoProcedimento() > 0 ? istanza.getIdEsitoProcedimento() : null));
            map.put("dataScadenzaProcedimento", dto.getDataScadenzaProcedimento() != null ? dto.getDataScadenzaProcedimento() : null); //istanza.getDataScadenzaProcedimento());
            map.put("annoRegistro", dto.getAnnoRegistro() != null ? dto.getAnnoRegistro() : istanza.getAnnoRegistro());
            map.put("idEsitoProcedStatale", dto.getIdEsitoProcedStatale() != null && dto.getIdEsitoProcedStatale() > 0 ? dto.getIdEsitoProcedStatale() : (istanza.getIdEsitoProcedStatale() > 0 ? istanza.getIdEsitoProcedStatale() : null));
            map.put("idStatoProcedStatale", dto.getIdStatoProcedStatale() != null && dto.getIdStatoProcedStatale() > 0 ? dto.getIdStatoProcedStatale() : (istanza.getIdStatoProcedStatale() > 0 ? istanza.getIdEsitoProcedStatale() : null));
            MapSqlParameterSource params = getParameterValue(map);
            Integer result = template.update(getQuery(QUERY_UPDATE_ISTANZA, null, null), params);
            if (saveStorico) {
                saveStoricoIstanza(dto.getIdIstanza(), dto.getJsonData() != null ? dto.getJsonData() : istanza.getJsonData());
            }
            return result;
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update json data istanza integer.
     *
     * @param dto IstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateJsonDataIstanza(IstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();

            Date now = Calendar.getInstance().getTime();

            IstanzaDTO istanza = this.findByPK(dto.getIdIstanza());
            if (null == istanza) {
                logError(className, "Record non trovato con id [" + dto.getIdIstanza() + "]");
                return -1;
            }

            map.put("idIstanza", dto.getIdIstanza());
            map.put("jsonData", dto.getJsonData());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreUpd());

            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_JSONDATA_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update stato istanza integer.
     *
     * @param uidIstanza     uidIstanza
     * @param idStatoIstanza idStatoIstanza
     * @param attoreUpd      attoreUpd
     * @param saveStorico    the save storico
     * @return numero record aggiornati
     */
    @Override
    public Integer updateStatoIstanza(String uidIstanza, Long idStatoIstanza, String attoreUpd, String jsonData, boolean saveStorico) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            IstanzaDTO istanza = this.findByUID(uidIstanza);
            if (null == istanza) {
                logError(className, "Record non trovato con uid [" + uidIstanza + "]");
                return -1;
            }

            map.put("uidIstanza", uidIstanza);
            map.put("idStatoIstanza", idStatoIstanza);
            map.put("dateUpd", now);
            map.put("attoreUpd", attoreUpd);
            MapSqlParameterSource params = getParameterValue(map);

            int result = template.update(getQuery(QUERY_UPDATE_STATO_ISTANZA, null, null), params);
            if (saveStorico) {
                saveStoricoIstanza(istanza.getIdIstanza(), jsonData);
            }
            return result;

        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete istanza integer.
     *
     * @param uid uidIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteIstanza(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);

            LOGGER.debug("[IstanzaDAOImpl::deleteIstanza] result : " + template.queryForObject("SELECT fnc_delete_istanza(:idIstanza)", map, Integer.class));

            return 1;
            //return template.update(getQuery(QUERY_DELETE_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete istanza by id integer.
     *
     * @param id idIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteIstanzaById(Long id) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", id);
            Integer result = template.queryForObject(FUNCTION_DELETE, map, Integer.class);
            logDebug(className, "result function for idIstanza [" + id + "] : [" + result + "]");
            return result == 0 ? result : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by uid istanza dto.
     *
     * @param uidIstanza uidIstanza
     * @return IstanzaDTO istanza dto
     */
    @Override
    public IstanzaDTO findByUID(String uidIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uidIstanza", uidIstanza);
        return findSimpleDTOByQuery(className, QUERY_LOAD_BY_UID, map);
    }

    /**
     * Find by pk istanza dto.
     *
     * @param idIstanza idIstanza
     * @return IstanzaDTO istanza dto
     */
    @Override
    public IstanzaDTO findByPK(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findByPK(className, map);
    }

    /**
     * Find by id oggetto istanza dto.
     *
     * @param idOggetto the id oggetto
     * @return the istanza dto
     */
    @Override
    public IstanzaDTO findByIdOggetto(Long idOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggetto", idOggetto);
        return findSimpleDTOByQuery(className, QUERY_LOAD_BY_ID_OGGETTO, map);
    }

    /**
     * Update uuid index integer.
     *
     * @param idIstanza idIstanza
     * @param uuidIndex uuidIndex
     * @return numero record aggiornati
     */
    @Override
    public Integer updateUuidIndex(Long idIstanza, String uuidIndex) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("uuidIndex", uuidIndex);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_UUID_INDEX, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza attore integer.
     *
     * @param idIstanza            idIstanza
     * @param idIstanzaAttore      idIstanzaAttore
     * @param idIstanzaAttoreOwner the id istanza attore owner
     * @param attoreUpd            attoreUpd
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIstanzaAttore(Long idIstanza, Long idIstanzaAttore, Long idIstanzaAttoreOwner, String attoreUpd) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            IstanzaDTO istanza = this.findByPK(idIstanza);
            if (null == istanza) {
                logError(className, "Record non trovato con id [" + idIstanza + "]");
                return -1;
            }
            map.put("idIstanza", idIstanza);

            Long idIstanzaAttoreOld = istanza.getIdIstanzaAttore() != 0 ? istanza.getIdIstanzaAttore() : null;
            map.put("idIstanzaAttore", idIstanzaAttore != null ? idIstanzaAttore : idIstanzaAttoreOld);

            Long idIstanzaAttoreOwnerOld = istanza.getIdIstanzaAttoreOwner() != 0 ? istanza.getIdIstanzaAttoreOwner() : null;
            map.put("idIstanzaAttoreOwner", idIstanzaAttoreOwner != null ? idIstanzaAttoreOwner : idIstanzaAttoreOwnerOld);
            map.put("dateUpd", now);
            map.put("attoreUpd", attoreUpd);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_ISTANZA_ATTORE, null, null), params);

        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load pratiche collegate oggetto list.
     *
     * @param idOggetti the id oggetti
     * @return the list
     */
    @Override
    public List<PraticaCollegataDTO> loadPraticheCollegateOggetto(List<Long> idOggetti, Integer annoPresentazione) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggetti", idOggetti);
            map.put("annoPresentazione", annoPresentazione);
            MapSqlParameterSource params = getParameterValue(map);
            String query = QUERY_LOAD_PRATICHE_COLLEGATE_OGGETTO;
            String dynamicJoin = annoPresentazione != null ? JOIN_PRATICHE_ANNO_PRESENTAZIONE : "";
            query = query.replace("__dynamic_join__", dynamicJoin);
            return template.query(getQuery(query, null, null), params, getPraticaCollegataRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load codice pratica string.
     *
     * @param idIstanza the id istanza
     * @return the string
     */
    @Override
    public String loadCodicePratica(Long idIstanza, String codiceTipoAdempimento, String codiceAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("codiceTipoAdempimento", codiceTipoAdempimento);
        map.put("codiceAdempimento", codiceAdempimento);
        map.put("annoRegistro", Year.now().getValue());
        return template.queryForObject(QUERY_LOAD_CODICE_PRATICA, getParameterValue(map), String.class);
    }

    /**
     * Update data pubblicazione integer.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param attoreUpd         the attore upd
     * @param idIstanzaAttore   the id istanza attore
     * @param idFunzionario     the id funzionario
     * @param saveStorico       the save storico
     * @return the integer
     */
    @Override
    public Integer updateDataPubblicazione(Long idIstanza, Date dataPubblicazione, String attoreUpd, Long idIstanzaAttore, Long idFunzionario, boolean saveStorico) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            IstanzaDTO istanza = this.findByPK(idIstanza);
            if (null == istanza) {
                logError(className, "Record non trovato con id [" + idIstanza + "]");
                return -1;
            }
            map.put("idIstanza", idIstanza);
            map.put("dataPubblicazione", dataPubblicazione);

            if (StringUtils.isBlank(istanza.getCodPratica())) {
                map.put("dataModIst", now);
                map.put("dataModificaPratica", istanza.getDataModificaPratica());
            } else {
                map.put("dataModIst", istanza.getDataModificaIstanza());
                map.put("dataModificaPratica", now);
            }

            Long idIstanzaAttoreOld = istanza.getIdIstanzaAttore() != 0 ? istanza.getIdIstanzaAttore() : null;
            map.put("idIstanzaAttore", idIstanzaAttore != null && idIstanzaAttore != 0 ? idIstanzaAttore : idIstanzaAttoreOld);

            Long idFunzionarioOld = istanza.getIdFunzionario() != 0 ? istanza.getIdFunzionario() : null;
            map.put("idFunzionario", idFunzionario != null && idFunzionario != 0 ? idFunzionario : idFunzionarioOld);

            map.put("dateUpd", now);
            map.put("attoreUpd", attoreUpd);
            MapSqlParameterSource params = getParameterValue(map);
            int result = template.update(getQuery(QUERY_UPDATE_DATA_PUBBLICAZIONE, null, null), params);
            if (saveStorico) {
                saveStoricoIstanza(istanza.getIdIstanza(), istanza.getJsonData());
            }
            return result;
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update data atto from provvedimento finale integer.
     *
     * @param idIstanza       the id istanza
     * @param attoreUpd       the attore upd
     * @param idIstanzaAttore the id istanza attore
     * @param idFunzionario   the id funzionario
     * @param saveStorico     the save storico
     * @return the integer
     */
    @Override
    public Integer updateDataConclProcedimentoFromProvvedimentoFinale(Long idIstanza, String attoreUpd, Long idIstanzaAttore, Long idFunzionario, boolean saveStorico) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            IstanzaDTO istanza = this.findByPK(idIstanza);
            if (null == istanza) {
                logError(className, "Record non trovato con id [" + idIstanza + "]");
                return -1;
            }
            map.put("idIstanza", idIstanza);
            if (StringUtils.isBlank(istanza.getCodPratica())) {
                map.put("dataModIst", now);
                map.put("dataModificaPratica", istanza.getDataModificaPratica());
            } else {
                map.put("dataModIst", istanza.getDataModificaIstanza());
                map.put("dataModificaPratica", now);
            }

            Long idIstanzaAttoreOld = istanza.getIdIstanzaAttore() != 0 ? istanza.getIdIstanzaAttore() : null;
            //map.put("idIstanzaAttore", idIstanzaAttore != null && idIstanzaAttore != 0 ? idIstanzaAttore : idIstanzaAttoreOld);

            Long idFunzionarioOld = istanza.getIdFunzionario() != 0 ? istanza.getIdFunzionario() : null;
            //map.put("idFunzionario", idFunzionario != null && idFunzionario != 0 ? idFunzionario : idFunzionarioOld);

            map.put("dateUpd", now);
            map.put("attoreUpd", attoreUpd);
            
           // map.put("idIstanzaAttore", idIstanzaAttore); scriva-1601 come concordato con Simona nel caso di aggiornamento della data di conclusione 
           //del provvedimento finale non si deve aggiornare l'attore istanza in quanto questaprocedura può essere eseguita solo dal funzionario.
            map.put("idFunzionario", idFunzionario);
            MapSqlParameterSource params = getParameterValue(map);
            int result = template.update(getQuery(QUERY_UPDATE_DATA_CONCL_PROC_FROM_PROVV_FINALE, null, null), params);
            if (saveStorico) {
                saveStoricoIstanza(istanza.getIdIstanza(), istanza.getJsonData());
            }
            logInfo(className, "VERIFICA AGGIORNAMENTO DATA CONCLUSIONE PROCEDIMENTO");
            return result;
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    public Integer updateIstanzaPraticaTimestampAttore(Long idIstanza, String ComponenteApp, Long idIstanzaAttore, Long idFunzionario, boolean saveStorico)
    {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            IstanzaDTO istanza = this.findByPK(idIstanza);
            int result = 0;
            if (null == istanza) {
                logError(className, "Record non trovato con id [" + idIstanza + "]");
                return -1;
            }
            map.put("idIstanza", idIstanza);

            if(ComponenteAppEnum.FO.name().equalsIgnoreCase(ComponenteApp)) {
                map.put("idIstanzaAttore", idIstanzaAttore);
                map.put("dataModificaIstanza", now);
                MapSqlParameterSource params = getParameterValue(map);
                result = template.update(getQuery(QUERY_UPDATE_ISTANZA_TIMESTAMP, null, null), params);
            }
            else {
                map.put("idFunzionario", idFunzionario);
                map.put("dataModificaPratica", now);
                MapSqlParameterSource params = getParameterValue(map);
                result = template.update(getQuery(QUERY_UPDATE_PRATICA_TIMESTAMP, null, null), params);

            }

            if (saveStorico) {
                saveStoricoIstanza(istanza.getIdIstanza(), istanza.getJsonData());
            } 
            return result;   
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
       
    }


    /**
     * PER FO
     * Calcola la gestione attore per ogni sistanza fornita in input, in base al codice fiscale dell'attore *cfAttore* 
     * e al codice del componente applicativo *codComponenteApp*.
     * 
     *
     * @param idIstanzaList the list of instance IDs to be loaded
     * @param cfAttore the fiscal code of the actor
     * @param codComponenteApp the code of the application component
     * @return a list of pairs containing the instance ID and the actor management instance
     */
    public List<Pair<Long, String>> loadIstanzaGestioneAttoresFO(List<Long> idIstanzaList, String cfAttore, String codComponenteApp)
     {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanzaList);
            map.put("cfAttore", cfAttore);
            map.put("codComponenteApp", codComponenteApp);
            MapSqlParameterSource params = getParameterValue(map);
            String query = QUERY_CALCOLA_GESTIONE_ATTORE_FO;

            return template.query(getQuery(query, null, null), params,  new LongStringRowMapper("id_istanza","attore_gestione_istanza"));
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
     }

    /**
     * PER BO 
     * Calcola la gestione attore per ogni sistanza fornita in input, in base al codice fiscale dell'attore *cfAttore* 
     * e al codice del componente applicativo *codComponenteApp*.
     * 
     *
     * @param idIstanzaList the list of instance IDs to be loaded
     * @param cfAttore the fiscal code of the actor
     * @param codComponenteApp the code of the application component
     * @return a list of pairs containing the instance ID and the actor management instance
     */
    public List<Pair<Long, String>> loadIstanzaGestioneAttoresBO(List<Long> idIstanzaList, String cfAttore, String codComponenteApp)
    {
       logBegin(className);
       try {
           Map<String, Object> map = new HashMap<>();
           map.put("idIstanza", idIstanzaList);
           map.put("cfFunzionario", cfAttore);
           map.put("codComponenteApp", codComponenteApp);
           MapSqlParameterSource params = getParameterValue(map);
           String query = QUERY_CALCOLA_GESTIONE_ATTORE_BO;

           return template.query(getQuery(query, null, null), params,  new LongStringRowMapper("id_istanza","attore_gestione_istanza"));
       } catch (Exception e) {
           logError(className, e);
           return Collections.emptyList();
       } finally {
           logEnd(className);
       }
    }


    /**
     * Gets codice pratica.
     *
     * @param idIstanza    the id istanza
     * @param annoRegistro the anno registro
     * @return the codice pratica
     */
    protected String getCodicePratica(Long idIstanza, Integer annoRegistro) {
        logBegin(className);
        IstanzaExtendedDTO istanza = this.loadIstanza(idIstanza).get(0);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("codiceTipoAdempimento", istanza.getAdempimento().getTipoAdempimento().getCodTipoAdempimento());
        map.put("codiceAdempimento", istanza.getAdempimento().getCodAdempimento());
        map.put("annoRegistro", annoRegistro);
        logEnd(className);
        return template.queryForObject(annoRegistro != null ? QUERY_LOAD_CODICE_PRATICA : QUERY_LOAD_CODICE_PRATICA.replace(":annoRegistro", EXTRACT_YEAR), getParameterValue(map), String.class);
    }

    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }


    @Override
    public RowMapper<IstanzaDTO> getRowMapper() throws SQLException {
        return new IstanzaRowMapper();
    }

    @Override
    public RowMapper<IstanzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaExtendedRowMapper();
    }

    /**
     * Gets integer row mapper.
     *
     * @return the integer row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<Integer> getIntegerRowMapper() throws SQLException {
        return new IntegerRowMapper();
    }

    /**
     * Gets pratica collegata row mapper.
     *
     * @return the pratica collegata row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<PraticaCollegataDTO> getPraticaCollegataRowMapper() throws SQLException {
        return new PraticheCollegateOggettoRowMapper();
    }

    /**
     * The type Integer row mapper.
     */
    public static class IntegerRowMapper implements RowMapper<Integer> {

        /**
         * Instantiates a new Integer row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IntegerRowMapper() throws SQLException {
            // Instatiate class
        }

        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt("new_prog");
        }
    }

    /**
     * The type Istanza row mapper.
     */
    public static class IstanzaRowMapper implements RowMapper<IstanzaDTO> {

        /**
         * Instantiates a new Istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaRowMapper() throws SQLException {
            // Instatiate class
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
        public IstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaDTO bean = new IstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setCodIstanza(rs.getString("cod_istanza"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            bean.setDataInserimentoIstanza(rs.getTimestamp("data_inserimento_istanza"));
            bean.setDataModificaIstanza(rs.getTimestamp("data_modifica_istanza"));
            bean.setJsonData(rs.getString("json_data"));
            bean.setUuidIndex(rs.getString("uuid_index"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
            bean.setIdTemplate(rs.getLong("id_template"));
            bean.setIdIstanzaAttoreOwner(rs.getLong("id_istanza_attore_owner"));
            bean.setDataInserimentoPratica(rs.getTimestamp("data_inserimento_pratica"));
            bean.setDataModificaPratica(rs.getTimestamp("data_modifica_pratica"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setDesStatoSintesiPagamento(rs.getString("des_stato_sintesi_pagamento"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setNumProtocolloIstanza(rs.getString("num_protocollo_istanza"));
            bean.setDataProtocolloIstanza(rs.getTimestamp("data_protocollo_istanza"));
            bean.setDataInizioOsservazioni(rs.getTimestamp("data_inizio_osservazioni"));
            bean.setDataFineOsservazioni(rs.getTimestamp("data_fine_osservazioni"));
            bean.setDataConclusioneProcedimento(rs.getTimestamp("data_conclusione_procedimento"));
            bean.setIdEsitoProcedimento(rs.getLong("id_esito_procedimento"));
            bean.setDataScadenzaProcedimento(rs.getTimestamp("data_scadenza_procedimento"));
            bean.setAnnoRegistro(rs.getInt("anno_registro"));
            bean.setIdEsitoProcedStatale(rs.getLong("id_esito_proced_statale"));
            bean.setIdStatoProcedStatale(rs.getLong("id_stato_proced_statale"));
            //bean.setDataInvioIstanza(rs.getTimestamp("data_invio_istanza"));
        }
    }

    /**
     * The type Istanza extended row mapper.
     */
    public static class IstanzaExtendedRowMapper implements RowMapper<IstanzaExtendedDTO> {

        /**
         * Instantiates a new Istanza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaExtendedRowMapper() throws SQLException {
            // Instatiate class
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
        public IstanzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaExtendedDTO bean = new IstanzaExtendedDTO();
            populateBeanExtended(rs, bean);
            return bean;
        }

        private void populateBeanExtended(ResultSet rs, IstanzaExtendedDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));

            StatoIstanzaDTO statoIstanza = new StatoIstanzaDTO();
            populateBeanStatoIstanza(rs, statoIstanza);
            bean.setStatoIstanza(statoIstanza);

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento.getIdAdempimento() != null ? adempimento : null);

            EsitoProcedimentoDTO esitoProcedimento = new EsitoProcedimentoDTO();
            populateBeanEsitoProcedimento(rs, esitoProcedimento);
            bean.setEsitoProcedimento(esitoProcedimento.getIdEsitoProcedimento() != null && esitoProcedimento.getIdEsitoProcedimento() > 0 ? esitoProcedimento : null);

            EsitoProcedimentoDTO esitoProcedimentoStatale = new EsitoProcedimentoDTO();
            populateBeanEsitoProcedimentoStatale(rs, esitoProcedimentoStatale);
            bean.setEsitoProcedimentoStatale(esitoProcedimentoStatale.getIdEsitoProcedimento() != null && esitoProcedimentoStatale.getIdEsitoProcedimento() > 0 ? esitoProcedimentoStatale : null);

            StatoProcedimentoStataleDTO statoProcedimentoStatale = new StatoProcedimentoStataleDTO();
            populateBeanStatoProcedimentoStatale(rs, statoProcedimentoStatale);
            bean.setStatoProcedimentoStatale(statoProcedimentoStatale.getIdStatoProcedStatale() != null && statoProcedimentoStatale.getIdStatoProcedStatale() > 0 ? statoProcedimentoStatale : null);

            bean.setCodIstanza(rs.getString("cod_istanza"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            bean.setDataInserimentoIstanza(rs.getTimestamp("data_inserimento_istanza"));
            bean.setDataModificaIstanza(rs.getTimestamp("data_modifica_istanza"));
            bean.setJsonData(rs.getString("json_data"));
            bean.setGestDataIns(rs.getTimestamp("data_ins_istanza"));
            bean.setGestAttoreIns(rs.getString("attore_ins_istanza"));
            bean.setGestDataUpd(rs.getTimestamp("data_upd_istanza"));
            bean.setGestAttoreUpd(rs.getString("attore_upd_istanza"));
            bean.setGestUID(rs.getString("uid_istanza"));
            bean.setUuidIndex(rs.getString("istanza_uuid_index"));
            bean.setIdTemplate(rs.getLong("id_template"));
            bean.setIdIstanzaAttoreOwner(rs.getLong("id_istanza_attore_owner"));
            bean.setDataInserimentoPratica(rs.getTimestamp("data_inserimento_pratica"));
            bean.setDataModificaPratica(rs.getTimestamp("data_modifica_pratica"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setDesStatoSintesiPagamento(rs.getString("des_stato_sintesi_pagamento"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setNumProtocolloIstanza(rs.getString("num_protocollo_istanza"));
            bean.setDataProtocolloIstanza(rs.getTimestamp("data_protocollo_istanza"));
            bean.setDataInizioOsservazioni(rs.getTimestamp("data_inizio_osservazioni"));
            bean.setDataFineOsservazioni(rs.getTimestamp("data_fine_osservazioni"));
            bean.setDataConclusioneProcedimento(rs.getTimestamp("data_conclusione_procedimento"));
            bean.setDataScadenzaProcedimento(rs.getTimestamp("data_scadenza_procedimento"));
            bean.setAnnoRegistro(rs.getInt("anno_registro"));
            bean.setDataInvioIstanza(rs.getTimestamp("data_invio_istanza"));
        }


        private void populateBeanStatoIstanza(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("stato_istanza_id"));
            bean.setIndAggiornaOggetto(rs.getString("ind_aggiorna_oggetto"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
            bean.setIndRicercaOggetto(rs.getString("ind_ricerca_oggetto"));
            bean.setFlgAggiornaOggetto(rs.getBoolean("flg_aggiorna_oggetto") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanCompilante(ResultSet rs, CompilanteDTO bean) throws SQLException {
            bean.setIdCompilante(rs.getLong("compilante_id"));
            bean.setCodiceFiscaleCompilante(rs.getString("cf_compilante"));
            bean.setCognomeCompilante(rs.getString("cognome_compilante"));
            bean.setNomeCompilante(rs.getString("nome_compilante"));
            bean.setDesEmailCompilante(rs.getString("des_email_compilante"));
        }

        private void populateBeanAdempimento(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setFlgAttivo(1 == rs.getInt("adempimento_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
            bean.setIndVisibile(rs.getString("ind_visibile_adempi"));
        }

        private void pupulateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            tipoAdempimento.setFlgAttivo(1 == rs.getInt("tipo_adempimento_attivo") ? Boolean.TRUE : Boolean.FALSE);
            tipoAdempimento.setOrdinamentoTipoAdempimento(rs.getInt("ordinamento_tipo_adempimento"));
            tipoAdempimento.setUuidIndex(rs.getString("tipo_adempimento_uuid_index"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
            bean.setFlgAttivo(1 == rs.getInt("ambito_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanEsitoProcedimento(ResultSet rs, EsitoProcedimentoDTO bean) throws SQLException {
            bean.setIdEsitoProcedimento(rs.getLong("id_esito_procedimento"));
            bean.setCodEsitoProcedimento(rs.getString("cod_esito_procedimento"));
            bean.setDesEsitoProcedimento(rs.getString("des_esito_procedimento"));
            bean.setFlgPositivo(1 == rs.getInt("flg_positivo") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanEsitoProcedimentoStatale(ResultSet rs, EsitoProcedimentoDTO bean) throws SQLException {
            bean.setIdEsitoProcedimento(rs.getLong("id_esito_proced_statale"));
            bean.setCodEsitoProcedimento(rs.getString("cod_esito_procedimento_statale"));
            bean.setDesEsitoProcedimento(rs.getString("des_esito_procedimento_statale"));
            bean.setFlgPositivo(1 == rs.getInt("flg_positivo_statale") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanStatoProcedimentoStatale(ResultSet rs, StatoProcedimentoStataleDTO bean) throws SQLException {
            bean.setIdStatoProcedStatale(rs.getLong("id_stato_proced_statale"));
            bean.setCodStatoProcedStatale(rs.getString("cod_stato_proced_statale"));
            bean.setDesStatoProcedStatale(rs.getString("des_stato_proced_statale"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setDesEstesaStatoProcedStatale(rs.getString("des_estesa_stato_proced_statale"));
            bean.setLabelStatoProcedStatale(rs.getString("label_stato_proced_statale"));
        }
    }

    /**
     * The type Istanza row mapper.
     */
    public static class PraticheCollegateOggettoRowMapper implements RowMapper<PraticaCollegataDTO> {

        /**
         * Instantiates a new Istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public PraticheCollegateOggettoRowMapper() throws SQLException {
            // Instatiate class
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
        public PraticaCollegataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PraticaCollegataDTO bean = new PraticaCollegataDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, PraticaCollegataDTO bean) throws SQLException {
            bean.setIdOggetto(rs.getLong("id_oggetto"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setCodIstanza(rs.getString("cod_istanza"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            SoggettoIstanzaDTO soggetto = new SoggettoIstanzaDTO();
            soggetto.setNome(rs.getString("nome"));
            soggetto.setCognome(rs.getString("cognome"));
            soggetto.setDenSoggetto(rs.getString("den_soggetto"));
            bean.setSoggetto(soggetto);
        }
    }

    /**
     * Gets new progressivo anno.
     *
     * @param anno          the anno
     * @param idAdempimento the id adempimento
     * @return the new progressivo anno
     * @throws SQLException the sql exception
     */
    protected Integer getNewProgressivoAnno(Integer anno, Long idAdempimento) throws SQLException {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("annoIstanza", anno);
            map.put("idAdempimento", idAdempimento);
            MapSqlParameterSource params = getParameterValue(map);
            return template.queryForObject(getQuery(QUERY_NEW_PROGRESSIVO, null, null), params, getIntegerRowMapper());
        } catch (SQLException e) {
            logError(className, e);
            throw new SQLException(e);
        } catch (DataAccessException e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    private Long saveStoricoIstanza(Long idIstanza, String jsonData) {
        LOGGER.debug("[IstanzaDAOImpl::saveStoricoIstanza] BEGIN");
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("jsonData", jsonData);
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(getQuery(QUERY_INSERT_STORICO_ISTANZA, null, null), params, keyHolder, new String[]{"id_istanza_storico"});
            Number key = keyHolder.getKey();
            return key != null ? key.longValue() : null;
        } catch (Exception e) {
            LOGGER.error("[IstanzaDAOImpl::saveStoricoIstanza] ERROR :\n ", e);
            return null;
        } finally {
            LOGGER.debug("[IstanzaDAOImpl::saveStoricoIstanza] END");
        }
    }
}