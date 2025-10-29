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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.SearchDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.StringRowMapper;
import it.csi.scriva.scrivabesrv.dto.IstanzaSearchResultDTO;
import it.csi.scriva.scrivabesrv.dto.SearchDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ProfiloAppEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Search dao.
 *
 * @author CSI PIEMONTE
 */
public class SearchDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaSearchResultDTO> implements SearchDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String FIELDS = "id_istanza, gest_uid, cod_istanza, cod_pratica, id_stato_istanza, des_estesa_stato_istanza, label_stato, data_inserimento_istanza, data_modifica_istanza, data_inserimento_pratica, data_modifica_pratica, cod_stato_istanza, des_stato_istanza, cod_ambito, cod_adempimento, des_adempimento, attore_modifica_fo, autore_modifica_bo, des_stato_sintesi_pagamento\n";

    //, den_soggetto, cognome_nome, den_oggetto, comune

    private static final String QUERY_LOAD_ISTANZE_UNION = "SELECT " + FIELDS + ", STRING_AGG(DISTINCT den_soggetto, ',') AS den_soggetto, STRING_AGG(DISTINCT cognome_nome, ',') AS cognome_nome, STRING_AGG(DISTINCT den_oggetto, ',') AS den_oggetto, STRING_AGG(DISTINCT comune_provincia, ',') AS comune " +
            "FROM ( \n" +
            "SELECT sti.*, sdsi.cod_stato_istanza, sdsi.des_stato_istanza, sdsi.des_estesa_stato_istanza, sdsi.label_stato, sdam.cod_ambito, sda.cod_adempimento, sda.des_adempimento, \n" +
            "stsi.den_soggetto, stsi.cognome || ' ' || stsi.nome AS cognome_nome, stoi.den_oggetto, sdc.denom_comune || ' (' || sdp.sigla_provincia || ')' AS comune_provincia,\n" +
            "stc.cognome_compilante  || ' ' || stc.nome_compilante AS attore_modifica_fo, stf.cognome_funzionario || ' ' || stf.nome_funzionario AS autore_modifica_bo\n" +
            "FROM scriva_r_istanza_attore sria \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sria.id_istanza \n" + //SCRIVA-266 : Ricerca Istanza (cambiata join da id_istanza_attore a id_istanza)
            "INNER JOIN scriva_d_adempimento sda ON sti.id_adempimento = sda.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sdam ON sdam.id_ambito = sdta.id_ambito \n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sti.id_stato_istanza = sdsi.id_stato_istanza \n" +
            "LEFT OUTER JOIN scriva_t_soggetto_istanza stsi ON stsi.id_istanza = sti.id_istanza \n" +
            "LEFT OUTER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_istanza = sti.id_istanza \n" +
            "LEFT OUTER JOIN scriva_t_oggetto sto ON sto.id_oggetto = stoi.id_oggetto \n" +
            "LEFT OUTER JOIN scriva_r_ubica_oggetto_istanza sruoi ON stoi.id_oggetto_istanza = sruoi.id_oggetto_istanza \n" +
            "LEFT OUTER JOIN scriva_d_comune sdc ON sruoi.id_comune = sdc.id_comune \n" +
            "LEFT OUTER JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia \n" +
            "LEFT OUTER JOIN scriva_r_istanza_evento srie ON srie.id_istanza = sti.id_istanza \n" +
            "LEFT OUTER JOIN scriva_r_istanza_attore sria2 ON sti.id_istanza_attore = sria2.id_istanza_attore \n" +
            "LEFT OUTER JOIN scriva_t_compilante stc ON stc.id_compilante = sria2.id_compilante \n" +
            "LEFT OUTER JOIN scriva_t_funzionario stf ON stf.id_funzionario = sti.id_funzionario \n" +
            "LEFT OUTER JOIN scriva_r_istanza_competenza sric ON (sric.id_istanza = sti.id_istanza AND sric.flg_autorita_principale = 1 AND sric.data_fine_validita IS NULL)\n" +
            //"WHERE UPPER(sria.cf_attore) = UPPER(:cfAttore) \n" +
            "WHERE sria.data_revoca IS NULL " +
            "__dynamic_where_conditions__" +
            ") istanze \n" +
            "GROUP BY " + FIELDS +
            " __dynamic_orderby_conditions__ \n";

    private static final String QUERY_SEARCH_ISTANZE = "WITH aggrega_soggetti AS (\n" +
            "                       SELECT sog.id_istanza,\n" +
            "                               STRING_AGG(DISTINCT sog.den_soggetto, ',') AS den_soggetto,\n" +
            "                               STRING_AGG(DISTINCT sog.cognome || ' ' || sog.nome, ',') AS cognome_nome,\n" +
            "                               STRING_AGG(DISTINCT sog.cf_soggetto, ',') AS cf_soggetto\n" +
            "                          FROM scriva_t_soggetto_istanza sog\n" +
            "                          where id_soggetto_padre is null\n" +
            "                      GROUP BY sog.id_istanza),\n" +
            "     aggrega_oggetti  AS ( -- (Aggrega dati degli oggetti in unica stringa)\n" +
            "                      SELECT  ogg.id_istanza,\n" +
            "                              STRING_AGG(DISTINCT ogg.den_oggetto, ',') AS den_oggetto,\n" +
            "                              STRING_AGG(DISTINCT sto.cod_scriva, ',') AS cod_scriva                              \n" +
            "                         FROM scriva_t_oggetto_istanza ogg\n" +
            "                   INNER JOIN scriva_t_oggetto sto ON ogg.id_oggetto = sto.id_oggetto                            \n" +
            "                    GROUP BY ogg.id_istanza),\n" +
            "     aggrega_comuni AS ( -- (Aggrega dati dei comuni/provincia delle ubicaziONi oggetti in unica stringa)\n" +
            "                    SELECT lista_comuni.id_istanza,\n" +
            "                            STRING_AGG(DISTINCT lista_comuni.comune_e_provincia, ',') AS comune\n" +
            "                    FROM  (\n" +
            "                            SELECT ogg.id_istanza, sdc.denom_comune || ' (' || sdp.sigla_provincia || ')' AS comune_e_provincia\n" +
            "                            FROM scriva_t_oggetto_istanza ogg\n" +
            "                            LEFT OUTER JOIN scriva_r_ubica_oggetto_istanza sruoi ON ogg.id_oggetto_istanza = sruoi.id_oggetto_istanza\n" +
            "                            LEFT OUTER JOIN scriva_d_comune sdc                  ON sruoi.id_comune = sdc.id_comune\n" +
            "                            LEFT OUTER JOIN scriva_d_provincia sdp               ON sdp.id_provincia = sdc.id_provincia\n" +
            "                     ) lista_comuni\n" +
            "                    GROUP BY  lista_comuni.id_istanza)\n" +
            "       SELECT DISTINCT sti.id_istanza,\n" +
            "              sti.gest_uid,\n" +
            "              sti.cod_istanza,\n" +
            "              sti.cod_pratica,\n" +
            "              sti.id_stato_istanza,\n" +
            "              sti.des_estesa_stato_istanza,\n" +
            "              sti.label_stato,\n" +
            "              sti.data_inserimento_istanza,\n" +
            "              sti.data_modifica_istanza,\n" +
            "              sti.data_inserimento_pratica,\n" +
            "              sti.data_modifica_pratica,\n" +
            "              sti.cod_stato_istanza,\n" +
            "              sti.des_stato_istanza,\n" +
            "              sti.cod_ambito,\n" +
            "              sti.cod_adempimento,\n" +
            "              sti.des_adempimento,\n" +
            "              sti.cod_tipo_adempimento,\n" +
            "              sti.des_tipo_adempimento,\n" +
            "              sria.attore_modifica_fo,\n" +
            "              sti.autore_modifica_bo,\n" +
            "              sti.des_stato_sintesi_pagamento,\n" +
            "              -- aggregaziONi --\n" +
            "              stsi.den_soggetto,\n" +
            "              stsi.cognome_nome,\n" +
            "              stsi.cf_soggetto,\n" +
            "              stoi.den_oggetto,\n" +
            "              stoi.cod_scriva,\n" +
            "              sdc.comune,\n" +
            "              -- info_aggiuntive\n" +
            "              sti.ind_visibile\n" +
            "              __dynamic_fields__" +
            "        FROM ( -- ISTANZE\n" +
            "             SELECT ist.id_istanza,\n" +
            "                     ist.gest_uid,\n" +
            "                     ist.cod_istanza,\n" +
            "                     ist.cod_pratica,\n" +
            "                     ist.id_stato_istanza,\n" +
            "                     sdsi.des_estesa_stato_istanza,\n" +
            "                     sdsi.label_stato,\n" +
            "                     ist.data_inserimento_istanza,\n" +
            "                     ist.data_modifica_istanza,\n" +
            "                     ist.data_inserimento_pratica,\n" +
            "                     ist.data_modifica_pratica,\n" +
            "                     sdsi.cod_stato_istanza,\n" +
            "                     sdsi.des_stato_istanza,\n" +
            "                     sdam.cod_ambito,\n" +
            "                     sda.cod_adempimento,\n" +
            "                     sda.des_adempimento,\n" +
            "                     sdta.id_tipo_adempimento,\n" +
            "                     sdta.cod_tipo_adempimento,\n" +
            "                     sdta.des_tipo_adempimento,\n" +
            "                     stf.cognome_funziONario || ' ' || stf.nome_funziONario AS autore_modifica_bo,\n" +
            "                     ist.des_stato_sintesi_pagamento,\n" +
            "                     UPPER(sdsi.ind_visibile) AS ind_visibile,\n" +
            "                     ist.id_istanza_attore\n" +
            "                FROM scriva_t_istanza ist\n" +
            "          INNER JOIN scriva_d_stato_istanza sdsi    ON ist.id_stato_istanza = sdsi.id_stato_istanza\n" +
            "          INNER JOIN scriva_d_adempimento sda       ON ist.id_adempimento = sda.id_adempimento\n" +
            "          INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento\n" +
            "          INNER JOIN scriva_d_ambito sdam           ON sdam.id_ambito = sdta.id_ambito\n" +
            "     LEFT OUTER JOIN scriva_t_funziONario stf       ON ist.id_funziONario = stf.id_funziONario\n" +
            "     __dynamic_ind_visibile_conditions__\n" +
            "                ) sti\n" +
            "  LEFT OUTER JOIN ( -- ATTORE_ISTANZA                                                      -- togliere DEBUG ! \n" +
            "               SELECT ria.id_istanza_Attore, ria.id_istanza, ria.id_compilante,\n" +
            "                      stc.cognome_compilante || ' ' || stc.nome_compilante AS attore_modifica_fo\n" +
            "                 FROM scriva_r_istanza_attore ria\n" +
            "      LEFT OUTER JOIN scriva_t_compilante stc ON stc.id_compilante = ria.id_compilante\n" +
            "              ) sria ON sti.id_istanza_attore = sria.id_istanza_attore             \n" +
            "  LEFT OUTER JOIN ( -- SOGGETTO_ISTANZA\n" +
            "               SELECT sog.id_istanza, sog.den_soggetto, sog.cognome_nome, sog.cf_soggetto\n" +
            "                 FROM aggrega_soggetti sog) stsi ON sti.id_istanza = stsi.id_istanza \n" +
            "  LEFT OUTER JOIN ( -- OGGETTO_ISTANZA\n" +
            "               SELECT ogg.id_istanza, ogg.den_oggetto, ogg.cod_scriva\n" +
            "                 FROM aggrega_oggetti ogg) stoi ON sti.id_istanza = stoi.id_istanza\n" +
            "  LEFT OUTER JOIN ( -- COMUNI_UBICAZIONE_OGGETTI\n" +
            "               SELECT com.id_istanza, com.comune\n" +
            "                 FROM aggrega_comuni com) sdc ON sti.id_istanza = sdc.id_istanza\n" +
            "__dynamic_joins__\n" +
            "WHERE 1=1\n" +
            "__dynamic_where_conditions__\n" +
            "__dynamic_orderby_conditions__\n";

    private static final String WHERE_COMPONENTE_APP = "AND UPPER(sdsi.ind_visibile) LIKE '%' || UPPER(:componenteApp) || '%'\n";
    
    private static final String WHERE_COMPONENTE_APP_SDA = "AND UPPER(sda.ind_visibile) LIKE '%' || UPPER(:componenteApp) || '%'\n";

    private static final String WHERE_CF_ATTORE = "AND UPPER(sria.cf_attore) = UPPER(:cfAttore) AND sria.data_revoca IS NULL\n";

    private static final String WHERE_CF_FUNZIONARIO = "AND sdta.id_tipo_adempimento IN (\n" +
            "    SELECT DISTINCT srtap.id_tipo_adempimento\n" +
            "    FROM scriva_t_funzionario stf \n" +
            "    LEFT OUTER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario\n" +
            "    LEFT OUTER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
            "    LEFT OUTER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sdpa.id_profilo_app \n" +
            "    WHERE stf.cf_funzionario = :cfFunzionario\n" +
            "    AND stf.data_fine_validita IS NULL\n" +
            "    AND srfp.data_fine_validita IS NULL\n" +
            ")\n";

    private static final String WHERE_CF_SOGGETTO = "AND UPPER(stsi.cf_soggetto) = UPPER(:cfSoggetto)\n";

    private static final String WHERE_TIPO_ADEMPIMENTO = "AND UPPER(sdta.cod_tipo_adempimento) = UPPER(:codTipoAdempimento)\n";

    private static final String WHERE_ADEMPIMENTO = "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n";

    private static final String WHERE_DATA_INS_FROM = "AND sti.data_inserimento_istanza >= :dataInsFrom\n";

    private static final String WHERE_DATA_INS_TO = "AND sti.data_inserimento_istanza <= :dataInsTo\n";

    private static final String WHERE_DATA_INS_BETWEEN = "AND sti.data_inserimento_istanza BETWEEN :dataInsFrom AND :dataInsTo\n";

    private static final String WHERE_STATO_ISTANZA = "AND UPPER(sdsi.cod_stato_istanza) = UPPER(:codStatoIstanza)\n";

    private static final String WHERE_PROVINCIA_OGGETTO_ISTANZA = "AND sdp.cod_provincia = :codProvincia\n";

    private static final String WHERE_COMUNE_OGGETTO_ISTANZA = "AND sdc.cod_istat_comune = :codIstatComune\n";

    private static final String WHERE_SIMPLE_SEARCH = "AND (\n" +
            "UPPER(sti.cod_pratica) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(sti.cod_istanza) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stsi.cf_soggetto) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stsi.den_soggetto) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stsi.cognome) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(sto.cod_scriva) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stoi.den_oggetto) LIKE '%' || UPPER(:simpleSearch) || '%' )\n";

    private static final String WHERE_SIMPLE_SEARCH_NEW = "AND (\n" +
            "UPPER(sti.cod_pratica) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(sti.cod_istanza) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stsi.cf_soggetto) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stsi.den_soggetto) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stsi.cognome_nome) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stoi.cod_scriva) LIKE '%' || UPPER(:simpleSearch) || '%' OR \n" +
            "UPPER(stoi.den_oggetto) LIKE '%' || UPPER(:simpleSearch) || '%' )\n";

    private static final String WHERE_DATA_EVENTO_FROM = "AND srie.data_evento >= :dataEventoFrom\n";

    private static final String WHERE_DATA_EVENTO_TO = "AND srie.data_evento <= :dataEventoTo\n";

    private static final String WHERE_DATA_EVENTO_BETWEEN = "AND srie.data_evento BETWEEN :dataEventoFrom AND :dataEventoTo\n";

    private static final String WHERE_EVENTO_ISTANZA = "AND srie.id_tipo_evento = :idEvento\n";

    private static final String WHERE_DES_STATO_SINTESI_PAGAMENTO = "AND UPPER(sti.des_stato_sintesi_pagamento) = UPPER(:desStatoSintesiPagamento) \n";

    private static final String WHERE_COND_ALIAS_FIELDNAME = "WHERE_COND";

    private static final String QUERY_FUNZIONARIO_ADEMPIMENTO_COMPETENZA_TERRITORIO = "SELECT '(sdta.id_tipo_adempimento=' || srtap.id_tipo_adempimento || ' AND sric.id_competenza_territorio=' || srfc.id_competenza_territorio ||') OR' AS " + WHERE_COND_ALIAS_FIELDNAME + "\n" +
            "FROM scriva_t_funzionario stf \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario\n" +
            "LEFT OUTER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
            "LEFT OUTER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sdpa.id_profilo_app \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_compete srfc ON srfc.id_funzionario = stf.id_funzionario\n" +
            "WHERE stf.cf_funzionario = :cfFunzionario\n" +
            "AND sdpa.flg_competenza = 1\n" +
            "AND srfc.id_competenza_territorio IS NOT NULL\n" +
            "AND stf.data_fine_validita IS NULL\n" +
            "AND srfp.data_fine_validita IS NULL\n" +
            "AND srfc.data_fine_validita IS NULL\n" +
            "AND srtap.id_tipo_adempimento IS NOT NULL\n" +
            "UNION\n" +
            "SELECT 'sdta.id_tipo_adempimento=' || srtap.id_tipo_adempimento || ' OR' AS " + WHERE_COND_ALIAS_FIELDNAME + "\n" +
            "FROM scriva_t_funzionario stf \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario\n" +
            "LEFT OUTER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
            "LEFT OUTER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sdpa.id_profilo_app \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_compete srfc ON srfc.id_funzionario = stf.id_funzionario\n" +
            "WHERE stf.cf_funzionario = :cfFunzionario\n" +
            "AND sdpa.flg_competenza = 0\n" +
            "AND srfc.id_competenza_territorio IS NOT NULL\n" +
            "AND stf.data_fine_validita IS NULL\n" +
            "AND srfp.data_fine_validita IS NULL\n" +
            "AND srfc.data_fine_validita IS NULL\n" +
            "AND srtap.id_tipo_adempimento IS NOT NULL\n";

    private static final String QUERY_FUNZIONARIO_ADEMPIMENTO_COMPETENZA_TERRITORIO_NEW = "SELECT '(sti.id_tipo_adempimento=' || srtap.id_tipo_adempimento || ' AND sric.id_competenza_territorio=' || srfc.id_competenza_territorio ||') OR' AS " + WHERE_COND_ALIAS_FIELDNAME + "\n" +
            "FROM scriva_t_funzionario stf \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario\n" +
            "LEFT OUTER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
            "LEFT OUTER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sdpa.id_profilo_app \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_compete srfc ON srfc.id_funzionario = stf.id_funzionario\n" +
            "WHERE stf.cf_funzionario = :cfFunzionario\n" +
            "AND sdpa.flg_competenza = 1\n" +
            "AND srfc.id_competenza_territorio IS NOT NULL\n" +
            "AND stf.data_fine_validita IS NULL\n" +
            "AND srfp.data_fine_validita IS NULL\n" +
            "AND srfc.data_fine_validita IS NULL\n" +
            "AND srtap.id_tipo_adempimento IS NOT NULL\n" +
            "UNION\n" +
            "SELECT 'sti.id_tipo_adempimento=' || srtap.id_tipo_adempimento || ' OR' AS " + WHERE_COND_ALIAS_FIELDNAME + "\n" +
            "FROM scriva_t_funzionario stf \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario\n" +
            "LEFT OUTER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
            "LEFT OUTER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sdpa.id_profilo_app \n" +
            "LEFT OUTER JOIN scriva_r_funzionario_compete srfc ON srfc.id_funzionario = stf.id_funzionario\n" +
            "WHERE stf.cf_funzionario = :cfFunzionario\n" +
            "AND sdpa.flg_competenza = 0\n" +
            "AND srfc.id_competenza_territorio IS NOT NULL\n" +
            "AND stf.data_fine_validita IS NULL\n" +
            "AND srfp.data_fine_validita IS NULL\n" +
            "AND srfc.data_fine_validita IS NULL\n" +
            "AND srtap.id_tipo_adempimento IS NOT NULL\n";

    /**
     * Search istanze fo list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @return the list
     */
    @Override
    public List<IstanzaSearchResultDTO> searchIstanze(SearchDTO searchCriteria, String offset, String limit, String sort) {
        return searchIstanze(searchCriteria, offset, limit, sort, null);
    }

    /**
     * Search istanze bo list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @param profiloApp
     * @return the list
     */
    @Override
    public List<IstanzaSearchResultDTO> searchIstanze(SearchDTO searchCriteria, String offset, String limit, String sort, ProfiloAppEnum profiloApp) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            StringBuilder dynamicWhereConditions = new StringBuilder();
            StringBuilder dynamicJoins = new StringBuilder();
            StringBuilder dynamicFields = new StringBuilder();
            
            StringBuilder dynamicIndVisibileConditions = new StringBuilder();
            String dynamicOrderByCondition = " ORDER BY data_inserimento_istanza DESC";

            if (StringUtils.isNotBlank(searchCriteria.getCfCompilante())) {
                map.put("cfAttore", searchCriteria.getCfCompilante());
                dynamicJoins.append("INNER JOIN ( -- ATTORE_ISTANZA (DA_CONSIDERARE VALIDO!)\n" +
                        "               SELECT DISTINCT att.id_istanza, att.cf_attore\n" +
                        "                 FROM scriva_r_istanza_attore att\n" +
                        "                WHERE UPPER(att.cf_attore) = UPPER(:cfAttore)\n" +
                        "                  AND att.data_revoca IS NULL\n" +
                        "              ) attore_valido ON sti.id_istanza = attore_valido.id_istanza\n");
            }

            if (StringUtils.isNotBlank(searchCriteria.getComponenteApp())) {
                map.put("componenteApp", searchCriteria.getComponenteApp());
                dynamicWhereConditions.append("AND sti.ind_visibile ILIKE '%' || :componenteApp || '%'\n");
            }

            if (StringUtils.isNotBlank(searchCriteria.getSimpleSearchMultipleFields())) {
                map.put("simpleSearch", searchCriteria.getSimpleSearchMultipleFields());
                dynamicWhereConditions.append(WHERE_SIMPLE_SEARCH_NEW);
            }

            if (StringUtils.isNotBlank(searchCriteria.getStatoIstanza())) {
            	
            	String[] arr = searchCriteria.getStatoIstanza().toUpperCase().split("\\|");
            	List<String> listStatoIstanza = Arrays.asList(arr);            	
                map.put("codStatoIstanza", listStatoIstanza);
                dynamicWhereConditions.append("AND sti.cod_stato_istanza IN(:codStatoIstanza)\n");
            }
            
         if (StringUtils.isNotBlank(searchCriteria.getLabelStato())) {                	
                map.put("labelStato", searchCriteria.getLabelStato());
                dynamicWhereConditions.append("AND UPPER(sti.label_stato) = UPPER(:labelStato)\n");
            }

            if (StringUtils.isNotBlank(searchCriteria.getCfSoggetto())) {
                map.put("cfSoggetto", searchCriteria.getCfSoggetto());
                dynamicWhereConditions.append(WHERE_CF_SOGGETTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getDesStatoSintesiPagamento())) {
                map.put("desStatoSintesiPagamento", searchCriteria.getDesStatoSintesiPagamento());
                dynamicWhereConditions.append(WHERE_DES_STATO_SINTESI_PAGAMENTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getTipoAdempimento())) {
                map.put("codTipoAdempimento", searchCriteria.getTipoAdempimento());
                dynamicWhereConditions.append("AND UPPER(sti.cod_tipo_adempimento) = UPPER(:codTipoAdempimento)\n");
            }

            if (StringUtils.isNotBlank(searchCriteria.getAdempimento())) {
                map.put("codAdempimento", searchCriteria.getAdempimento());
                dynamicWhereConditions.append("AND UPPER(sti.cod_adempimento) = UPPER(:codAdempimento)\n");
            }

            if (StringUtils.isNotBlank(searchCriteria.getProvinciaOggettoIstanza())) {
                map.put("codProvincia", searchCriteria.getProvinciaOggettoIstanza());
                dynamicWhereConditions.append("AND sdc.comune ILIKE '%('||:codProvincia||')%'\n");
            }

            if (StringUtils.isNotBlank(searchCriteria.getComuneOggettoIstanza())) {
                map.put("comune", searchCriteria.getComuneOggettoIstanza());
                dynamicWhereConditions.append("AND sdc.comune ILIKE '%'||:comune||'%'\n");
            }
            
            if (StringUtils.isNotBlank(searchCriteria.getComponenteApp())) {
                map.put("componenteApp", searchCriteria.getComponenteApp());
                dynamicIndVisibileConditions.append("WHERE UPPER(sda.ind_visibile) LIKE '%' || UPPER(:componenteApp) || '%'\n");
            }

            if (StringUtils.isNotBlank(searchCriteria.getCfFunzionario())) {
                map.put("cfFunzionario", searchCriteria.getCfFunzionario());
                if ((StringUtils.isNotBlank(searchCriteria.getTipoPratiche()) && searchCriteria.getTipoPratiche().equalsIgnoreCase("ALL"))
                        || profiloApp != null) {
                    dynamicWhereConditions.append("AND sti.id_tipo_adempimento IN (\n" +
                            "    SELECT DISTINCT srtap.id_tipo_adempimento\n" +
                            "    FROM scriva_t_funzionario stf \n" +
                            "    LEFT OUTER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario\n" +
                            "    LEFT OUTER JOIN scriva_d_profilo_app sdpa ON sdpa.id_profilo_app = srfp.id_profilo_app\n" +
                            "    LEFT OUTER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = sdpa.id_profilo_app \n" +
                            "    WHERE stf.cf_funzionario = :cfFunzionario\n" +
                            "    AND stf.data_fine_validita IS NULL\n" +
                            "    AND srfp.data_fine_validita IS NULL\n" +
                            ")\n");
                } else {
                    String whereFunzionarioCompetenzaCondition = getWhereCondFunzionarioCompetenzaNew(searchCriteria.getCfFunzionario());
                    if (StringUtils.isNotBlank(whereFunzionarioCompetenzaCondition)) {
                        dynamicWhereConditions.append(whereFunzionarioCompetenzaCondition);
                        //dynamicFields.append(",sric.id_competenza_territorio\n");
                        dynamicJoins.append("LEFT OUTER JOIN ( -- COMPETENZA_TERRITORIO\n" +
                                "               SELECT id_competenza_territorio, id_istanza\n" +
                                "                 FROM scriva_r_istanza_competenza\n" +
                                "                WHERE data_fine_validita IS NULL\n" +
                                "                  AND (flg_autorita_principale = 1 OR flg_autorita_assegnata_bo = 1)\n" +
                                "                  ) sric ON sti.id_istanza = sric.id_istanza\n");
                    }
                }
            }

            if (searchCriteria.getIdTipoEvento() != null) {
                map.put("idEvento", searchCriteria.getIdTipoEvento());
                dynamicJoins.append("  INNER JOIN ( -- ISTANZA_EVENTO\n" +
                        "               SELECT id_istanza, id_tipo_evento, data_evento\n" +
                        "                 FROM scriva_r_istanza_evento\n" +
                        "                  ) srie ON srie.id_istanza = sti.id_istanza\n");
                dynamicWhereConditions.append(WHERE_EVENTO_ISTANZA);
                if (searchCriteria.getDataEventoFrom() != null && searchCriteria.getDataEventoTo() != null) {
                    map.put("dataEventoFrom", searchCriteria.getDataEventoFrom());
                    map.put("dataEventoTo", searchCriteria.getDataEventoTo());
                    dynamicWhereConditions.append(WHERE_DATA_EVENTO_BETWEEN);
                } else {
                    if (null != searchCriteria.getDataEventoFrom()) {
                        map.put("dataEventoFrom", searchCriteria.getDataEventoFrom());
                        dynamicWhereConditions.append(WHERE_DATA_EVENTO_FROM);
                    } else if (null != searchCriteria.getDataEventoTo()) {
                        map.put("dataEventoTo", searchCriteria.getDataEventoTo());
                        dynamicWhereConditions.append(WHERE_DATA_EVENTO_TO);
                    }
                }
            }

            if (null != searchCriteria.getDataInserimentoFrom() && null != searchCriteria.getDataInserimentTo()) {
                map.put("dataInsFrom", searchCriteria.getDataInserimentoFrom());
                map.put("dataInsTo", searchCriteria.getDataInserimentTo());
                dynamicWhereConditions.append(WHERE_DATA_INS_BETWEEN);
            } else {
                if (null != searchCriteria.getDataInserimentoFrom()) {
                    map.put("dataInsFrom", searchCriteria.getDataInserimentoFrom());
                    dynamicWhereConditions.append(WHERE_DATA_INS_FROM);
                } else if (null != searchCriteria.getDataInserimentTo()) {
                    map.put("dataInsTo", searchCriteria.getDataInserimentTo());
                    dynamicWhereConditions.append(WHERE_DATA_INS_TO);
                }
            }

            if (StringUtils.isNotBlank(sort)) {
                dynamicOrderByCondition = getQuerySortingSegment(sort);
            }

            String query = QUERY_SEARCH_ISTANZE
                    .replace("__dynamic_where_conditions__", "\n" + dynamicWhereConditions.toString())
                    .replace("__dynamic_ind_visibile_conditions__", "\n" + dynamicIndVisibileConditions.toString())
                    .replace("__dynamic_joins__", "\n" + dynamicJoins.toString())
                    .replace("__dynamic_fields__", "\n" + dynamicFields.toString())
                    .replace("__dynamic_orderby_conditions__", dynamicOrderByCondition);
            LOGGER.debug("[SearchDAOImpl::searchIstanze] query eseguita :  \n" + getQuery(query, offset, limit) + "\n");
            return template.query(getQuery(query, offset, limit), getParameterValue(map), getRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }


    @Override
    public List<IstanzaSearchResultDTO> searchIstanzeOLD(SearchDTO searchCriteria, String offset, String limit, String sort) {
        return searchIstanzeOLD(searchCriteria, offset, limit, sort, null);
    }

    /**
     * Search istanze old list.
     *
     * @param searchCriteria the search criteria
     * @param offset         the offset
     * @param limit          the limit
     * @param sort           the sort
     * @return the list
     */
    @Override
    public List<IstanzaSearchResultDTO> searchIstanzeOLD(SearchDTO searchCriteria, String offset, String limit, String sort, ProfiloAppEnum profiloApp) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            StringBuilder dynamicWhereConditions = new StringBuilder();
            String dynamicOrderByCondition = " ORDER BY data_inserimento_istanza DESC";
            

            if (searchCriteria.getIdTipoEvento() != null) {
                map.put("idEvento", searchCriteria.getIdTipoEvento());
                dynamicWhereConditions.append(WHERE_EVENTO_ISTANZA);
                if (searchCriteria.getDataEventoFrom() != null && searchCriteria.getDataEventoTo() != null) {
                    map.put("dataEventoFrom", searchCriteria.getDataEventoFrom());
                    map.put("dataEventoTo", searchCriteria.getDataEventoTo());
                    dynamicWhereConditions.append(WHERE_DATA_EVENTO_BETWEEN);
                } else {
                    if (null != searchCriteria.getDataEventoFrom()) {
                        map.put("dataEventoFrom", searchCriteria.getDataEventoFrom());
                        dynamicWhereConditions.append(WHERE_DATA_EVENTO_FROM);
                    } else if (null != searchCriteria.getDataEventoTo()) {
                        map.put("dataEventoTo", searchCriteria.getDataEventoTo());
                        dynamicWhereConditions.append(WHERE_DATA_EVENTO_TO);
                    }
                }
            }

            if (null != searchCriteria.getDataInserimentoFrom() && null != searchCriteria.getDataInserimentTo()) {
                map.put("dataInsFrom", searchCriteria.getDataInserimentoFrom());
                map.put("dataInsTo", searchCriteria.getDataInserimentTo());
                dynamicWhereConditions.append(WHERE_DATA_INS_BETWEEN);
            } else {
                if (null != searchCriteria.getDataInserimentoFrom()) {
                    map.put("dataInsFrom", searchCriteria.getDataInserimentoFrom());
                    dynamicWhereConditions.append(WHERE_DATA_INS_FROM);
                } else if (null != searchCriteria.getDataInserimentTo()) {
                    map.put("dataInsTo", searchCriteria.getDataInserimentTo());
                    dynamicWhereConditions.append(WHERE_DATA_INS_TO);
                }
            }

            if (StringUtils.isNotBlank(searchCriteria.getProvinciaOggettoIstanza())) {
                map.put("codProvincia", searchCriteria.getProvinciaOggettoIstanza());
                dynamicWhereConditions.append(WHERE_PROVINCIA_OGGETTO_ISTANZA);
            }

            if (StringUtils.isNotBlank(searchCriteria.getComuneOggettoIstanza())) {
                map.put("codIstatComune", searchCriteria.getComuneOggettoIstanza());
                dynamicWhereConditions.append(WHERE_COMUNE_OGGETTO_ISTANZA);
            }

            if (StringUtils.isNotBlank(searchCriteria.getCfSoggetto())) {
                map.put("cfSoggetto", searchCriteria.getCfSoggetto());
                dynamicWhereConditions.append(WHERE_CF_SOGGETTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getStatoIstanza())) {
                map.put("codStatoIstanza", searchCriteria.getStatoIstanza());
                dynamicWhereConditions.append(WHERE_STATO_ISTANZA);
            }

            if (StringUtils.isNotBlank(searchCriteria.getSimpleSearchMultipleFields())) {
                map.put("simpleSearch", searchCriteria.getSimpleSearchMultipleFields());
                dynamicWhereConditions.append(WHERE_SIMPLE_SEARCH);
            }

            if (StringUtils.isNotBlank(searchCriteria.getTipoAdempimento())) {
                map.put("codTipoAdempimento", searchCriteria.getTipoAdempimento());
                dynamicWhereConditions.append(WHERE_TIPO_ADEMPIMENTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getAdempimento())) {
                map.put("codAdempimento", searchCriteria.getAdempimento());
                dynamicWhereConditions.append(WHERE_ADEMPIMENTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getDesStatoSintesiPagamento())) {
                map.put("desStatoSintesiPagamento", searchCriteria.getDesStatoSintesiPagamento());
                dynamicWhereConditions.append(WHERE_DES_STATO_SINTESI_PAGAMENTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getDesStatoSintesiPagamento())) {
                map.put("desStatoSintesiPagamento", searchCriteria.getDesStatoSintesiPagamento());
                dynamicWhereConditions.append(WHERE_DES_STATO_SINTESI_PAGAMENTO);
            }

            if (StringUtils.isNotBlank(searchCriteria.getCfCompilante())) {
                map.put("cfAttore", searchCriteria.getCfCompilante());
                dynamicWhereConditions.append(WHERE_CF_ATTORE);
            }

            if (StringUtils.isNotBlank(searchCriteria.getCfFunzionario())) {
                map.put("cfFunzionario", searchCriteria.getCfFunzionario());
                if ((StringUtils.isNotBlank(searchCriteria.getTipoPratiche()) && searchCriteria.getTipoPratiche().equalsIgnoreCase("ALL"))
                        || profiloApp != null) {
                    dynamicWhereConditions.append(WHERE_CF_FUNZIONARIO);
                } else {
                    String whereFunzionarioCompetenzaCondition = getWhereCondFunzionarioCompetenza(searchCriteria.getCfFunzionario());
                    if (StringUtils.isNotBlank(whereFunzionarioCompetenzaCondition)) {
                        dynamicWhereConditions.append(whereFunzionarioCompetenzaCondition);
                    }
                }
            }

            if (StringUtils.isNotBlank(searchCriteria.getComponenteApp())) {
                map.put("componenteApp", searchCriteria.getComponenteApp());
                dynamicWhereConditions.append(WHERE_COMPONENTE_APP);
            }

            if (StringUtils.isNotBlank(searchCriteria.getComponenteApp())) {
                map.put("componenteApp", searchCriteria.getComponenteApp());
                dynamicWhereConditions.append(WHERE_COMPONENTE_APP_SDA);
            }
            
            if (StringUtils.isNotBlank(sort)) {
                dynamicOrderByCondition = getQuerySortingSegment(sort);
            }

            String query = QUERY_LOAD_ISTANZE_UNION.replace("__dynamic_where_conditions__", "\n" + dynamicWhereConditions.toString()).replace("__dynamic_orderby_conditions__", dynamicOrderByCondition);
            MapSqlParameterSource params = getParameterValue(map);
            LOGGER.debug("[SearchDAOImpl::searchIstanze] query eseguita :  \n" + getQuery(query, offset, limit) + "\n");
            return template.query(getQuery(query, offset, limit), params, getRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    private String getWhereCondFunzionarioCompetenza(String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String result = null;
        map.put("cfFunzionario", cfFunzionario);
        MapSqlParameterSource params = getParameterValue(map);
        try {
            List<String> whereCondList = template.query(QUERY_FUNZIONARIO_ADEMPIMENTO_COMPETENZA_TERRITORIO, params, new StringRowMapper("WHERE_COND"));
            if (whereCondList != null && !whereCondList.isEmpty()) {
                for (String whereCond : whereCondList) {
                    result = StringUtils.isBlank(result) ? "AND (" + whereCond + "\n" : result + whereCond + "\n";
                }
                result = StringUtils.isNotBlank(result) ? result.substring(0, result.length() - 4) + ")\n" : "\n";
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    private String getWhereCondFunzionarioCompetenzaNew(String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String result = null;
        map.put("cfFunzionario", cfFunzionario);
        MapSqlParameterSource params = getParameterValue(map);
        try {
            List<String> whereCondList = template.query(QUERY_FUNZIONARIO_ADEMPIMENTO_COMPETENZA_TERRITORIO_NEW, params, new StringRowMapper("WHERE_COND"));
            if (whereCondList != null && !whereCondList.isEmpty()) {
                for (String whereCond : whereCondList) {
                    result = StringUtils.isBlank(result) ? "AND (" + whereCond + "\n" : result + whereCond + "\n";
                }
                result = StringUtils.isNotBlank(result) ? result.substring(0, result.length() - 4) + ")\n" : "\n";
            }
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return result;
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaSearchResultDTO>
     */
    @Override
    public RowMapper<IstanzaSearchResultDTO> getRowMapper() throws SQLException {
        return new IstanzaSearchResulRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaSearchResultDTO>
     */
    @Override
    public RowMapper<IstanzaSearchResultDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaSearchResulRowMapper();
    }

    /**
     * The type Istanza search resul row mapper.
     */
    public static class IstanzaSearchResulRowMapper implements RowMapper<IstanzaSearchResultDTO> {

        /**
         * Instantiates a new Istanza search resul row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaSearchResulRowMapper() throws SQLException {
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
        public IstanzaSearchResultDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaSearchResultDTO bean = new IstanzaSearchResultDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaSearchResultDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setGestUIDIstanza(rs.getString("gest_uid"));
            bean.setCodIstanza(rs.getString("cod_istanza"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            bean.setCodAdmbito(rs.getString("cod_ambito"));
            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDenSoggetto(StringUtils.isNotBlank(rs.getString("den_soggetto")) ? rs.getString("den_soggetto") : rs.getString("cognome_nome"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
            bean.setComune(rs.getString("comune"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDesStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
            bean.setDataInserimentoIstanza(rs.getTimestamp("data_inserimento_istanza"));
            bean.setDataModificaIstanza(rs.getTimestamp("data_modifica_istanza"));
            bean.setDataInserimentoPratica(rs.getTimestamp("data_inserimento_pratica"));
            bean.setDataModificaPratica(rs.getTimestamp("data_modifica_pratica"));
            bean.setAttoreModificaFO(rs.getString("attore_modifica_fo"));
            bean.setAttoreModificaBO(rs.getString("autore_modifica_bo"));
            bean.setDesStatoSintesiPagamento(rs.getString("des_stato_sintesi_pagamento"));
        }
    }
}