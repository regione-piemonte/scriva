/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { OpereConsts } from '../../opere/utilities/opere.consts';
import { TipologieOggettoIstanza } from '../../opere/utilities/opere.enums';
import { ScrivaGestioneDatiQuadro } from '../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';

/**
 * Classe contenente le informazioni costanti per la classe omonima.
 */
export class InfrastruttureAccessorieConsts extends OpereConsts {
  /** string con il codice dell'help per l'apertura della modale. */
  readonly CODICE_MASCHERA: string = `MSCR025F`;

  /** string che definisce la descrizione da visualizzare per il form di ricerca delle opere. */
  HEADER_RICERCA_OPERE: string = `Cerca un'infrastruttura accessoria già esistente mediante il nome dell'opera, il comune o il codice rilievo.`;
  /** string che definisce la descrizione da visualizzare per l'alternativa alla ricerca opere. */
  ALTERNATIVA_RICERCA_OPERE: string = `In alternativa, inserisci una nuova infrastruttura accessoria`;
  /** string che definisce la descrizione per la selezione delle opere da associare. */
  SELEZIONA_OPERE_ASSOCIARE: string = `Seleziona le infrastrutture accessorie da associare alla derivazione`;

  /**
   * TipologieOggettoIstanza con la tipologia dell'oggetto istanza di DER per la gestione dell'oggetto istanza. Per default è stringa vuota e va specializzata sul quadro specifico.
   * Questo tipo di quadro ha più tipi oggetto istanza per i dati tecnici associati, segno il primo sulla variabile condivisa, ne creo altre.
   * Faccio poi l'override per l'oggetto del FormIo per il json data e il json data riepilogo per il quadro "senza dati" o senza opere associate.
   */
  TIPOLOGIA_OGG_IST_DER = TipologieOggettoIstanza.MISURATORE_DI_PORTATA;
  TIPOLOGIA_OGG_IST_DER_2 = TipologieOggettoIstanza.SERBATOIO_DI_ACCUMULO;
  TIPOLOGIA_OGG_IST_DER_3 = TipologieOggettoIstanza.STAZIONE_DI_POMPAGGIO;

  /** ScrivaGestioneDatiQuadro con il tipo di flusso da impiegare per la gestione del pulsante "AVANTI". */
  GESTIONE_DATI_QUADRO = ScrivaGestioneDatiQuadro.quadroSenzaDettagli;

  /**
   * Costruttore.
   */
  constructor() {
    super();
  }

  /**
   * Getter che genera un dato tecnico vuoto come quadro formio.
   * Questo getter sostituisce il processo che generalmente avviene quando l'utente apre il dettaglio di un'opera e salva i dati tecnici.
   * @returns any con l'oggetto vuoto con la chiave generato.
   * @override
   */
  get datoTecnicoDataQuadro(): any {
    // Recupero la chiave specifica dell'oggetto istanza
    const keyDT1: string = this.TIPOLOGIA_OGG_IST_DER;
    const keyDT2: string = this.TIPOLOGIA_OGG_IST_DER_2;
    const keyDT3: string = this.TIPOLOGIA_OGG_IST_DER_3;
    // Recupero la chiave del dato tecnico
    const keyFormio1: string = this.chiaveDatoTecnicoFormIo(keyDT1);
    const keyFormio2: string = this.chiaveDatoTecnicoFormIo(keyDT2);
    const keyFormio3: string = this.chiaveDatoTecnicoFormIo(keyDT3);
    // Compongo e ritorno la chiave
    return {
      [keyFormio1]: [],
      [keyFormio2]: [],
      [keyFormio3]: [],
    };
    // #
  }
}
