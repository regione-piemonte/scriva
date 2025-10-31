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
import { MessaggioUtente } from '../notification/model/notification.model';
import { ConswebCodesMesseges } from '@app/core/enums/codes-messagges.enums';

/**
 * Struttura di supporto per velocizzare il recupero dei messaggi dell'utente.
 * L'oggetto avrà come proprietà il codice messaggio dell'oggetto Message, e come valore l'oggetto Message.
 */
interface ConswebMessaggiUtente {
  [key: string]: MessaggioUtente;
}

/**
 * Servizio per la gestione dei messaggi per la visualizzazione all'utente.
 */
@Injectable({ providedIn: 'root' })
export class ConswebAlertMessagesService {
  /** string costante che definisce il placeholder per i messaggi non trovati. */
  private MSG_PLACEHOLDER = '[CONSWEB_ERROR] Message code not found.';

  /** RiscaMessaggiUtente contenente la mappatura dei messaggi per l'utente. */
  private _conswebMU: ConswebMessaggiUtente = {};

  /**
   * Costruttore.
   */
  constructor() {}

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
    const messaggio = this._conswebMU[code];

    // Verifico che sia stato trovato un messaggio
    if (!messaggio) {
      // Ritorno un placeholder
      return fallbackPlaceholder || this.MSG_PLACEHOLDER;
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
    const messaggio = this._conswebMU[code];
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
   * @param m MessaggioUtente[] per la generazione dei messaggi di Consweb.
   */
  initMessageService(m: MessaggioUtente[]) {
    // Lancio la conversione e assegno il risultato internamento
    this._conswebMU = this.convertMessaggiUtenteVoToConswebMessaggiUtente(m);
  }

  /**
   * Funzione che effettua il convert tra un array Message ad un oggetto ConswebMessaggiUtente.
   * @param m Array di Message da convertire.
   * @returns ConswebMessaggiUtente convertito.
   */
  private convertMessaggiUtenteVoToConswebMessaggiUtente(
    m: MessaggioUtente[]
  ): ConswebMessaggiUtente {
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
