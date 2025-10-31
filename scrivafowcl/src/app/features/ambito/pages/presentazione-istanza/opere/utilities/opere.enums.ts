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
 * Enum che definisce i livelli dei dati da recuperare per la gestione della configurazione del json data quadro.
 */
export enum IndLivelliOpere {
  secondo = 2,
}

/**
 * Enum che definisce le tipologie oggetto-istanza dell'applicazione.
 * @author Ismaele Bottelli
 * @date 09/12/2024
 * @jira SCRIVA-1574
 * @notes Enumeratore gemello della tabella SCRIVA.scriva_d_tipologia_oggetto. Alcune delle voci sono usate per logiche applicative.
 *        In caso di errori verificare che, rispetto alla data indicata, i valori siano correttamente allineati rispetto alla tabella.
 */
export enum TipologieOggettoIstanza {
  ELETTRO_OLEO_GAS_DOTTO = 'ELETTRO_OLEO_GAS_DOTTO',
  IMPIANTO_ENERGETICO = 'IMPIANTO_ENERGETICO',
  FERRO_STRADA_PARK_AEROPORTO = 'FERRO_STRADA_PARK_AEROPORTO',
  DIGA_INVASO = 'DIGA_INVASO',
  PORTO_DEMANIO_IDRICO = 'PORTO_DEMANIO_IDRICO',
  PISTA_SCI = 'PISTA_SCI',
  FORE_DEFORESTAZIONE = 'FORE_DEFORESTAZIONE',
  GENERICA = 'GENERICA',
  DERIVAZIONE = 'DERIVAZIONE',
  PROGETTO = 'PROGETTO',
  ATTIVITA = 'ATTIVITA',
  POZZO = 'POZZO',
  SORGENTE = 'SORGENTE',
  FONTANILE = 'FONTANILE',
  PRESA_DA_ACQUE_SUPERFICIALI = 'PRESA_DA_ACQUE_SUPERFICIALI',
  TRINCEA_DRENANTE = 'TRINCEA_DRENANTE',
  STAZIONE_DI_POMPAGGIO = 'STAZIONE_DI_POMPAGGIO',
  MISURATORE_DI_PORTATA = 'MISURATORE_DI_PORTATA',
  SERBATOIO_DI_ACCUMULO = 'SERBATOIO_DI_ACCUMULO',
  UNITA_LOCALE_OPERATIVA = 'UNITA_LOCALE_OPERATIVA',
  ATTIVITA_ESTRATTIVA = 'ATTIVITA_ESTRATTIVA',
  SITO_CONTAMINATO = 'SITO_CONTAMINATO',
  RESTITUZIONE = 'RESTITUZIONE',
  CONDOTTA_FORZATA = 'CONDOTTA_FORZATA',
  CONDOTTA_DERIVATIVA = 'CONDOTTA_DERIVATIVA',
  CANALE_DERIVATIVO = 'CANALE_DERIVATIVO',
  GALLERIA_DERIVATIVA = 'GALLERIA_DERIVATIVA',
  NON_DEFINITO = '',
}