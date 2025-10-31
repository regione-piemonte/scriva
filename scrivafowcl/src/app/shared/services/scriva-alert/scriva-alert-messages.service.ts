/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { join, replace } from 'lodash';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { MessaggioUtente } from '../../models';
import {
  TScrivaDataPlaceholder,
  TScrivaDataPlaceholders,
} from './utilities/scriva-alert.types';

/**
 * Struttura di supporto per velocizzare il recupero dei messaggi dell'utente.
 * L'oggetto avrà come proprietà il codice messaggio dell'oggetto Message, e come valore l'oggetto Message.
 */
interface ScrivaMessaggiUtente {
  [key: string]: MessaggioUtente;
}

/**
 * Servizio per la gestione dei messaggi per la visualizzazione all'utente.
 */
@Injectable({ providedIn: 'root' })
export class ScrivaAlertMessagesService {
  /** string costante che definisce il placeholder per i messaggi non trovati. */
  private MSG_PLACEHOLDER = '[SCRIVA_ERROR] Message code not found.';

  /** RiscaMessaggiUtente contenente la mappatura dei messaggi per l'utente. */
  private _scrivaMU: ScrivaMessaggiUtente = {};

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * ######################
   * CONVERTER DEL SERVIZIO
   * ######################
   */

  /**
   * Funzione di comodo che converte le informazioni dei dataPlaceholder in un'unica stringa.
   * @param dataReplace TScrivaDataPlaceholder con le informazioni da convertire.
   * @returns string con una stringa leggibile.
   */
  private convertTScrivaDataPlaceholderToString(
    dataReplace: TScrivaDataPlaceholder
  ): string {
    // Verifico l'input
    if (dataReplace === undefined) {
      // Ritorno un default
      return;
    }

    // Funzione di comodo
    const isStringOrNumber = (v: any): boolean => {
      // Varibili di comodo
      const isString = typeof v === 'string';
      const isNumber = typeof v === 'number';
      // Ritorno le condizioni
      return isString || isNumber;
    };

    // Verfico la tipologia dell'input
    if (isStringOrNumber(dataReplace)) {
      // E' una stringa o un numero, ritorno il suo valore
      return dataReplace.toString();
      // #
    }

    // Variabili di comodo
    let dataMsg = '';
    const isArray = Array.isArray(dataReplace);
    // Verifico la tipologia dell'input
    if (isArray) {
      // Effettuo un typing forzato per evitare errori
      const dataReplaceTyped = dataReplace as any[];
      // Effettuo una join delle informazioni
      dataMsg = join(dataReplaceTyped, ', ');
      // Ritorno la stringa
      return dataMsg;
      // #
    }

    // Fallback, non si dovrebbe mai arrivare qui
    return dataReplace as any;
  }

  /**
   * #####################
   * FUNZIONI DEL SERVIZIO
   * #####################
   */

  /**
   * Funzione che restituisce un messaggio dato il suo codice.
   * @param code string codice del messaggio.
   * @param fallbackPlaceholder string opzionale che definisce il messaggio da ritornare in caso in cui non venga trovato per il codice.
   * @returns string con il messaggio.
   */
  getDescrizioneMessaggio(code: string, fallbackPlaceholder?: string): string {
    // Tento di recuperare il messaggio
    const messaggio = this._scrivaMU[code];

    // Verifico che sia stato trovato un messaggio
    if (!messaggio) {
      // Ritorno un placeholder
      return fallbackPlaceholder ?? this.MSG_PLACEHOLDER;
    }

    // Ritorno il messaggio
    return messaggio.des_testo_messaggio;
  }

  /**
   * Funzione che restituisce un messaggio dato il suo codice.
   * @param code string codice del messaggio.
   * @returns Message con l'intera configurazione del messaggio.
   */
  getMessaggioUtente(code: string): MessaggioUtente {
    // Tento di recuperare il messaggio
    const messaggio = this._scrivaMU[code];
    // Ritorno il messaggio
    return messaggio;
  }

  /**
   * Funzione che restituisce un messaggio con placeholder, tramite codice messaggio e i dati per la replace del placeholder.
   * Le chiavi di placeholder verranno recuperate per stesso codice messaggio.
   * ATTENZIONE: gli array codesPlaceholder e dataReplace verranno gestite utilizzando gli indici posizionali di codesPlaceholder. Quindi il placeholder alla posizione 0, verra sostituito dai dati alla posizione 0.
   * @param code string codice del messaggio.
   * @param dataReplace TScrivaDataPlaceholders con i dati da sostiture per i placeholder.
   * @param fallbackPlaceholder string opzionale che definisce il messaggio da ritornare in caso in cui non venga trovato per il codice.
   * @returns string con il messaggio.
   */
  getMessageWithPlacholderByCode(
    code: string,
    dataReplace: TScrivaDataPlaceholders,
    fallbackPlaceholder?: string
  ): string {
    // Recupero le chiavi di placeholder
    const cP = ScrivaCodesMesseges[code];
    // Variabili di comodo
    const c = code;
    const dR = dataReplace;
    const fPh = fallbackPlaceholder;
    // Ritorno il messaggio con placeholder
    return this.getMessageWithPlacholder(c, cP, dR, fPh);
  }

  /**
   * Funzione che restituisce un messaggio con placeholder, tramite codice messaggio e i dati per la replace del placeholder.
   * ATTENZIONE: gli array codesPlaceholder e dataReplace verranno gestite utilizzando gli indici posizionali di codesPlaceholder. Quindi il placeholder alla posizione 0, verra sostituito dai dati alla posizione 0.
   * @param code string codice del messaggio.
   * @param codesPlaceholders Array di string con i codici dei placeholder da sostituire nel messaggio.
   * @param dataReplace TScrivaDataPlaceholders con i dati da sostiture per i placeholder.
   * @param fallbackPlaceholder string opzionale che definisce il messaggio da ritornare in caso in cui non venga trovato per il codice.
   * @returns string con il messaggio.
   */
  getMessageWithPlacholder(
    code: string,
    codesPlaceholders: string[],
    dataReplace: TScrivaDataPlaceholders,
    fallbackPlaceholder?: string
  ): string {
    // Recupero il testo del messaggio
    let messaggio = this.getDescrizioneMessaggio(code, fallbackPlaceholder);

    // Verifico che esistano le strutture per il placeholding
    if (codesPlaceholders && dataReplace) {
      // Ciclo la lista dei placeholder
      for (let i = 0; i < codesPlaceholders.length; i++) {
        // Recupero il placholder
        const placeholder = codesPlaceholders[i];
        // Recupero e converto il dato per la sostituzione
        const dR = dataReplace[i];
        const dataPh = this.convertTScrivaDataPlaceholderToString(dR);

        // Verifico che sia stata effettuata la conversione
        if (dataPh !== undefined) {
          // Effettuo il raplace del placeholder con il dato
          messaggio = replace(messaggio, placeholder, dataPh);
          // #
        }
      }
    }

    // Ritorno il messaggio
    return messaggio;
  }

  /**
   * #############################
   * FUNZIONI DI INIT DEL SERVIZIO
   * #############################
   */

  /**
   * Funzione d'inizializzazione dati per l'applicazione.
   * @param m MessaggioUtente[] per la generazione dei messaggi di Scriva.
   */
  initMessageService(m: MessaggioUtente[]) {
    // Lancio la conversione e assegno il risultato internamento
    this._scrivaMU = this.convertMessaggiUtenteVoToScrivaMessaggiUtente(m);
  }

  /**
   * Funzione che effettua il convert tra un array Message ad un oggetto ScrivaMessaggiUtente.
   * @param m Array di Message da convertire.
   * @returns ScrivaMessaggiUtente convertito.
   */
  private convertMessaggiUtenteVoToScrivaMessaggiUtente(
    m: MessaggioUtente[]
  ): ScrivaMessaggiUtente {
    // Verifico l'input
    if (!m) return {};

    // Creo un contenitore dei dati
    const rmc = {};
    // Effettuo una conversione dell'array all'oggetto
    m.forEach((o: MessaggioUtente) => {
      // Definisco come proprietà il codice del messaggio
      const key = o.cod_messaggio.trim();
      // Assegno la contenitore la coppia key e oggetto
      rmc[key] = o;
    });

    // Ritorno l'oggetto
    return rmc;
  }
}
