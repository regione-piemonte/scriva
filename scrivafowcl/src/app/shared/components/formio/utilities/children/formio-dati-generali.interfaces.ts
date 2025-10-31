/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

/**
 * Interfaccia con le informazioni con i dati del FormIo generato per: dati generali DER.
 */
export interface IQuadroDatiGeneraliDER {
  JS_DATI_IDENTIFICATIVI?: any; // {codice_rilievo: '', tipo_deriv: {…}}
  JS_ESERCIZIO_DERIVAZIONE?: any; // {portata_max: '2', portata_med: '2', volume_max: '2'}
  JS_NOTE?: any; // {note_derivazione: ''}
  JS_RESTITUZIONE?: any; // {portata_tot_rest: '2', vol_annuo_tot_rest: '2'}
  JS_SANATORIA?: any; // {data_inizio_uso_acqua: '2025-02-06T00:00:00+01:00', titolo_autor_preesistente: '', flg_autor_provv_uso: 'no'}
  JS_SOVRACANONE?: any; // {flg_sovracanone: 'no'}
  JS_USI?: any; // {flg_uso_promiscuo: 'no', quantitativi_idrici_complessivi: Array(1), capacita_accumulo: ''}
  JS_DATI_ISTANZA?: IQdrConfig_QdrDerDatiGen;
  id_oggetto?: number; // 1174
}

/**
 * Interfaccia che contiene parte delle informazioni che il quadro dati generali dentro il quadro config per json_data può contenere.
 */
export interface IQdrConfig_QdrDerDatiGen {
  flg_opere_fisse?: boolean;
  procedura_semplificata?: boolean;
  tipo_concessione?: string;
  uso_esclusivo_domestico?: boolean;
  uso_geotermico_max20l_s?: boolean;
  [key: string]: any;
}
