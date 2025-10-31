/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
export interface QdrOggettoConfig {
  ricerca_oggetto: boolean;
  proposta_comune_da_geo?: boolean;
  messaggi?: {
    cod_messaggio_pre_geeco: string;
    cod_messaggio_comuni_1: string;
    cod_messaggio_comuni_2: string;
    cod_messaggio_comuni_3: string;
    cod_messaggio_comuni_4: string;
    cod_messaggio_post_geeco_riepilogo_1: string;
    cod_messaggio_post_geeco_riepilogo_2: string;
    cod_messaggio_post_geeco_riepilogo_3: string;
    cod_messaggio_post_geeco_tab_1: string;
    cod_messaggio_post_geeco_tab_2: string;
    cod_messaggio_post_geeco_tab_3: string;
  };
  criteri_ricerca?: ICriteriRicercaQdrOggetto;
  risultati_ricerca?: {
    den_oggetto: ConfigElement;
    comune: ConfigElement;
    ubicazione: ConfigElement;
    pratiche_collegate: ConfigElement;
  };
  dettaglio_oggetto: {
    den_oggetto: ConfigElement;
    des_oggetto: ConfigElement;
    note_atto_precedente: ConfigNoteAttoPrecedente[];
    flg_geo_modificato: boolean;
    cod_scriva: ConfigElement;
    cod_oggetto_fonte: ConfigElement;
    cod_utenza: ConfigElement;
    coordinata_x: ConfigElement;
    coordinata_y: ConfigElement;
    tipologia_oggetto: ConfigElement;
    siti_rete_natura: ConfigElement;
    aree_protette: ConfigElement;
    dati_catastali: ConfigElement;
    vincoli: ConfigElement;
  };
  info_oggetti_istanza?: IInfoOggettiIstanzaQdr[];
}

export interface ConfigElement {
  visibile?: string | boolean;
  obbligatorio?: string | boolean;
  label?: string;
  elementi_discontinuita?: ConfigElement;
  origine_dato?: ConfigElement;
  distanza?: ConfigElement;
}

interface ConfigNoteAttoPrecedente {
  visible: string | boolean;
  obbligatorio: boolean;
  label: string;
  ricerca_oggetto: boolean;
  flg_nuovo_oggetto: boolean;
}

/**
 * Interface con le informazioni che identificano i dati per gli oggetti-istanza
 */
export interface IInfoOggettiIstanzaQdr {
  id_oggetto_istanza: number;
  flg_nuovo_oggetto: boolean;
}

/**
 * Interface che rappresenta i campi di ricerca configurabili da DB per la ricerca degli oggetti.
 */
export interface ICriteriRicercaQdrOggetto {
  provincia?: ConfigElement;
  id_comune?: ConfigElement;
  den_oggetto?: ConfigElement;
  cod_scriva?: ConfigElement;
  anno_presentazione?: ConfigElement;
  cf_soggetto?: ConfigElement;
}
