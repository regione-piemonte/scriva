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
import { TipologieOggettoIstanza } from "../../opere/utilities/opere.enums";
import { ScrivaGestioneDatiQuadro } from "../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums";

/**
 * Classe contenente le informazioni costanti per la classe omonima.
 */
export class RestituzioniConsts extends OpereConsts {
  /** string con il codice dell'help per l'apertura della modale. */
  readonly CODICE_MASCHERA: string = `MSCR030F`;

  /** string che definisce la descrizione da visualizzare per il form di ricerca delle opere. */
  HEADER_RICERCA_OPERE: string = `Cerca una restituzione già esistente mediante il comune o il codice rilievo.`;
  /** string che definisce la descrizione da visualizzare per l'alternativa alla ricerca opere. */
  ALTERNATIVA_RICERCA_OPERE: string = `In alternativa, inserisci una nuova restituzione`;
  /** string che definisce la descrizione per la selezione delle opere da associare. */
  SELEZIONA_OPERE_ASSOCIARE: string = `Seleziona le restituzioni da associare alla derivazione`;
  
  /** TipologieOggettoIstanza con la tipologia dell'oggetto istanza di DER per la gestione dell'oggetto istanza. Per default è stringa vuota e va specializzata sul quadro specifico. */
  TIPOLOGIA_OGG_IST_DER = TipologieOggettoIstanza.RESTITUZIONE;
  /** ScrivaGestioneDatiQuadro con il tipo di flusso da impiegare per la gestione del pulsante "AVANTI". */
  GESTIONE_DATI_QUADRO = ScrivaGestioneDatiQuadro.quadroSenzaDettagli;

  /**
   * Costruttore.
   */
  constructor() {
    super();
  }
}
