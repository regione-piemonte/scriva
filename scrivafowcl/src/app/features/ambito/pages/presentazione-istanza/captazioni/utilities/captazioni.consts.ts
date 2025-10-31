/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { OpereConsts } from "../../opere/utilities/opere.consts";

/**
 * Classe contenente le informazioni costanti per la classe omonima.
 */
export class CaptazioniConsts extends OpereConsts {
  /** string con il codice dell'help per l'apertura della modale. */
  readonly CODICE_MASCHERA: string = `MSCR024F`;

  /** string che definisce la descrizione da visualizzare per il form di ricerca delle opere. */
  HEADER_RICERCA_OPERE: string = `Cerca un'opera di captazione già esistente mediante il nome dell'opera, il comune o il codice ROC.`;
  /** string che definisce la descrizione da visualizzare per l'alternativa alla ricerca opere. */
  ALTERNATIVA_RICERCA_OPERE: string = `In alternativa, inserisci una nuova opera di captazione`;
  /** string che definisce la descrizione per la selezione delle opere da associare. */
  SELEZIONA_OPERE_ASSOCIARE: string = `Seleziona le opere di captazione da associare alla derivazione`;

  /**
   * Costruttore.
   */
  constructor() {
    super();
  }
}
