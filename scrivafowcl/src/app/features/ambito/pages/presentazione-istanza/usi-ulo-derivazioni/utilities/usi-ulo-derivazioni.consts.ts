/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaGestioneDatiQuadro } from '../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { OpereConsts } from '../../opere/utilities/opere.consts';
import { TipologieOggettoIstanza } from '../../opere/utilities/opere.enums';

/**
 * Classe contenente le informazioni costanti per la classe omonima.
 */
export class UsiUloDerivazioniConsts extends OpereConsts {
  /** string con il codice dell'help per l'apertura della modale. */
  readonly CODICE_MASCHERA: string = `MSCR028F`;

  /** string che definisce la descrizione da visualizzare per il form di ricerca delle opere. */
  HEADER_RICERCA_OPERE: string = `Cerca un'unità locale operativa già esistente mediante il il codice fiscale dell'azienda, il comune di localizzazione e la denominazione.`;
  /** string che definisce la descrizione da visualizzare per l'alternativa alla ricerca opere. */
  ALTERNATIVA_RICERCA_OPERE: string = `Se non presente inserisci una nuova Unità Locale Operativa`;
  /** string che definisce la descrizione per la selezione delle opere da associare. */
  SELEZIONA_OPERE_ASSOCIARE: string = `Seleziona le ULO da associare alla derivazione`;

  /** string con la label per la gestione dell'input per il codice fiscale azienda. */
  readonly CODICE_FISCALE: string = 'Codice fiscale';
  /** string con la label per la gestione dell'input per la descrizione azienda. */
  readonly DENOMINAZIONE: string = 'Denominazione';
  /** string con la label per la gestione dell'input per la descrizione azienda. */
  readonly AZIENDA: string = 'azienda';

  /** TipologieOggettoIstanza con la tipologia dell'oggetto istanza di DER per la gestione dell'oggetto istanza. Per default è stringa vuota e va specializzata sul quadro specifico. */
  TIPOLOGIA_OGG_IST_DER = TipologieOggettoIstanza.UNITA_LOCALE_OPERATIVA;
  /** ScrivaGestioneDatiQuadro con il tipo di flusso da impiegare per la gestione del pulsante "AVANTI". */
  GESTIONE_DATI_QUADRO = ScrivaGestioneDatiQuadro.quadroSenzaDettagli;

  /**
   * Costruttore.
   */
  constructor() {
    super();
  }
}
