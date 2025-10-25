/* *****************************************************
 * Copyright Regione Piemonte - 2025
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************/

-- scriva.v_utility_lista_istanze source

CREATE OR REPLACE VIEW v_utility_lista_istanze
AS WITH istanza_adempimento AS (
         SELECT '-->' AS dati_adempimento,
            dad.id_adempimento,
            dad.des_adempimento,
            dad.des_estesa_adempimento,
            dad.flg_attivo,
            dad.id_tipo_adempimento,
            dad.cod_adempimento,
            dad.ind_visibile,
            '-->' AS dati_tipo_adempimento,
            dta.id_ambito,
            dta.cod_tipo_adempimento,
            dta.des_tipo_adempimento,
            dta.des_estesa_tipo_adempimento,
            dta.ordinamento_tipo_adempimento,
            dta.flg_attivo
           FROM scriva_d_adempimento dad
             LEFT JOIN scriva_d_tipo_adempimento dta ON dad.id_tipo_adempimento = dta.id_tipo_adempimento
        )
 SELECT '-->'::text AS dati_istanza,
    ist.id_istanza,
    ist.id_stato_istanza,
    ist.id_adempimento,
    ist.data_inserimento_istanza,
    ist.data_modifica_istanza,
    ist.cod_istanza,
    ist.json_data,
    ist.gest_data_ins,
    ist.gest_attore_ins,
    ist.gest_data_upd,
    ist.gest_attore_upd,
    ist.gest_uid,
    ist.cod_pratica,
    ist.id_template,
    ist.data_inserimento_pratica,
    ist.data_pubblicazione,
    ist.num_protocollo_istanza,
    ist.data_protocollo_istanza,
    ist.anno_registro,
    '-->' AS dati_stato_istanza,
    dsi.cod_stato_istanza,
    dsi.des_stato_istanza,
    dsi.flg_storico_istanza,
    dsi.ind_visibile,
    '-->' AS dati_adempimento,
    iad.cod_tipo_adempimento,
    iad.cod_adempimento,
    '-->' AS dati_storici,
    COALESCE(sis.quanti, 0::bigint) AS quanti_storici
   FROM scriva_t_istanza ist
     LEFT JOIN scriva_d_stato_istanza dsi ON ist.id_stato_istanza = dsi.id_stato_istanza
     LEFT JOIN istanza_adempimento iad(dati_adempimento, id_adempimento, des_adempimento, des_estesa_adempimento, flg_attivo, id_tipo_adempimento, cod_adempimento, ind_visibile, dati_tipo_adempimento, id_ambito, cod_tipo_adempimento, des_tipo_adempimento, des_estesa_tipo_adempimento, ordinamento_tipo_adempimento, flg_attivo_1) ON ist.id_adempimento = iad.id_adempimento
     LEFT JOIN ( SELECT rsi.id_istanza,
            count(*) AS quanti
           FROM scriva_r_istanza_storico rsi
          GROUP BY rsi.id_istanza) sis ON ist.id_istanza = sis.id_istanza
  ORDER BY ist.id_istanza;