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
 * Enum che definisce il livello di gestione degli elementi scriva.
 */
export enum ScrivaInfoLevels {
  success = 'SUCCESS',
  danger = 'DANGER',
  info = 'INFO',
  warning = 'WARNING',
  debug = 'DEBUG',
  error = 'ERROR',
  log = 'LOG',
  none = '',
}

/**
 * Enum che definisce la tipologia applicativa per SCRIVA.
 */
export enum ScrivaComponenteApp {
  frontOffice = 'FO',
  backOffice = 'BO',
}

/**
 * Enum che definisce il formato delle date usate dall'applicazione.
 */
export enum ScrivaFormatoDate {
  view = 'DD/MM/YYYY',
  server = 'YYYY-MM-DD',
}

/**
 * Enum personalizzato per la gestione del tipo di css da usare.
 */
export enum ScrivaCssHandlerTypes {
  class = 'class',
  style = 'style',
}

/**
 * Enum che definisce le tipologie di button di scriva.
 */
export enum ScrivaButtonTypes {
  default = 'btn-default',
  link = 'btn-link',
  primary = 'btn-primary',
}

/**
 * Enum che definisce il tipo di sanitizer da impiegare per la pipe: 'sanitizer'.
 */
export enum SanitizerTypes {
  html = 'html',
  style = 'style',
  script = 'script',
  url = 'url',
  resourceUrl = 'resourceUrl',
}

/**
 * Enum che definisce come deve essere formattato il dato in uscita dal componente del datepicker.
 */
export enum ScrivaDatesFormat {
  moment = 'moment',
  ngbDateStruct = 'ngbDateStruct',
  viewFormat = 'viewFormat',
  serverFormat = 'serverFormat',
}

/**
 * Enum che definisce la mappatura dei codici tipi adempimenti.
 */
export enum ScrivaCodTipiAdempimenti {
  VIA = 'VIA',
  DER = 'DER',
}

/**
 * Enum che definisce i tipi soggetto gestiti da scriva.
 */
export enum ScrivaTipiPersona {
  personaFisica = 'PF', // Persona fisica
  personaGiuridicaPubblica = 'PG', // Persona Giuridica Privata
  personaGiuridicaPrivata = 'PB', // Persona Giuridica Pubblica
  PF = 'PF', // Persona fisica (per comodità sulla nomenclatura, riporto anche il codice)
  PG = 'PG', // Persona Giuridica Privata (per comodità sulla nomenclatura, riporto anche il codice)
  PB = 'PB', // Persona Giuridica Pubblica (per comodità sulla nomenclatura, riporto anche il codice)
}

/**
 * Enum che definisce i codici per i ruoli compilante da scriva.
 * @property DCPF = Delegato con potere di firma.
 * @property DSPF_FirmatarioLegaleRappresentanteProponente = Delegato senza potere di firma (firmatario legale rappresentante/proponente).
 * @property DSPF_FirmatarioAltroDelegato = Delegato senza potere di firma (firmatario altro delegato).
 */
export enum ScrivaCodiciRuoloCompilante {
  DCPF = 'DEL_FIRMA',
  DSPF_FirmatarioLegaleRappresentanteProponente = 'DEL',
  DSPF_FirmatarioAltroDelegato = 'ALTRO_DEL',
}

/**
 * Enumeratore di comodo che mappa gli stati di un'istanza con accopiamento con l'id dello stato istanza.
 */
export enum ScrivaStatiIstanza {
  BOZZA = 10,
}

/**
 * Enum che definisce i possibili flussi per la gestione degli "AVANTI" del componente delle opere.
 */
export enum ScrivaGestioneDatiQuadro {
  standard = "STANDARD",
  quadroSenzaDettagli = "QUADRO_SENZA_DETTAGLI",
}