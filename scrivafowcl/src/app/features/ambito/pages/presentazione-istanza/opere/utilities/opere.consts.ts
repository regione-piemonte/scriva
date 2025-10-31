/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { ScrivaCodesMesseges } from '../../../../../../core/enums/scriva-codes-messages.enums';
import { ScrivaGestioneDatiQuadro } from '../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { KEY_FORMIO_SECTION_REQUIRED } from '../../../../../../shared/services/formio/utilities/formio.consts';
import { TipologieOggettoIstanza } from './opere.enums';

/**
 * Classe contenente le informazioni costanti per la classe omonima.
 */
export class OpereConsts {
  /** string con il codice dell'help per l'apertura della modale. Il codice è da definire sulla costante specifica per componente. */
  readonly CODICE_MASCHERA: string = `MSCR____`;
  /** string con il codice dell'help dell'header per il quadro. Il codice è da definire sulla costante specifica per componente.  */
  readonly KEY_HELP_QUADRO_HEADER: string = `intest_quadro_1`;

  /** string che definisce il prefisso per l'identificazione delle informazioni dei dati tecnici. */
  PREFIX_JS_DT: string = 'JS_DATI_';
  /** string che definisce la proprietà del FormIo che definisce se una sezione dati risulta obbligatoria. */
  SECTION_REQUIRED: string = KEY_FORMIO_SECTION_REQUIRED;

  /** string che definisce l'id dell'elemento HTML come àncora per la messaggistica utente. */
  readonly ALERT_TARGET_OPERA: string = 'operaContainer';
  /** string che definisce l'id dell'elemento HTML come àncora per la messaggistica utente. */
  readonly ALERT_TARGET_CAPTAZIONE: string = 'captazione-content';

  /** string che definisce la descrizione da visualizzare per il form di ricerca delle opere. */
  HEADER_RICERCA_OPERE: string = `Cerca un'opera già esistente mediante il nome dell'opera, il comune o il codice ROC.`;
  /** string che definisce la descrizione da visualizzare per l'alternativa alla ricerca opere. */
  ALTERNATIVA_RICERCA_OPERE: string = `In alternativa, inserisci una nuova opera`;
  /** string che definisce la descrizione per la selezione delle opere da associare. */
  SELEZIONA_OPERE_ASSOCIARE: string = `Seleziona le opere da associare alla derivazione`;

  /** string che definisce l'id per il div di aggancio per la gestione degli alert della pagina. */
  GEECO_ALERT_CONTAINER: string = `geeco-header-alert`;

  /** ScrivaCodesMesseges con il messaggio da visualizzare quando la ricerca opere è completata. */
  CODE_SUCCESSO_RICERCA = ScrivaCodesMesseges.I030;

  /** ScrivaGestioneDatiQuadro con il tipo di flusso da impiegare per la gestione del pulsante "AVANTI". Per default è: ScrivaGestioneDatiQuadro.standard. */
  GESTIONE_DATI_QUADRO = ScrivaGestioneDatiQuadro.standard;
  /** TipologieOggettoIstanza con la tipologia dell'oggetto istanza di DER per la gestione dell'oggetto istanza. Per default è stringa vuota e va specializzata sul quadro specifico. */
  TIPOLOGIA_OGG_IST_DER = TipologieOggettoIstanza.NON_DEFINITO;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * Getter che genera la stringa come chiave identificativa del dato tecnico generata dal quadro formio di riferimento.
   * Questo getter sostituisce il processo che generalmente avviene quando l'utente apre il dettaglio di un'opera e salva i dati tecnici.
   * A quel punto viene generata una chiave con un prefisso e il nome del tipo oggetto istanza di derivazione a cui viene associata una lista di oggetti generati da Formio.
   * @param key string con il tipo oggetto istanza derivazioni usato per generare la chiave del dato tecnico specifico per il dato del formio per generare la chiave completa.
   * @returns string con la chiave generata.
   */
  chiaveDatoTecnicoFormIo(key: string): string {
    // Compongo e ritorno la chiave
    return `${this.PREFIX_JS_DT}${key}`;
    // #
  }

  /**
   * Getter che genera un dato tecnico vuoto come quadro formio.
   * Questo getter sostituisce il processo che generalmente avviene quando l'utente apre il dettaglio di un'opera e salva i dati tecnici.
   * @returns any con l'oggetto vuoto con la chiave generato.
   */
  get datoTecnicoDataQuadro(): any {
    // Recupero la chiave specifica dell'oggetto istanza
    const keyDT: string = this.TIPOLOGIA_OGG_IST_DER;
    // Recupero la chiave del dato tecnico
    const keyFormio: string = this.chiaveDatoTecnicoFormIo(keyDT);
    // Compongo e ritorno la chiave
    return { [keyFormio]: [] };
    // #
  }
}
