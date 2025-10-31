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
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { ScrivaAlertConfigs } from '../../components/scriva-alert/utilities/scriva-alert.classes';
import { ScrivaCodTMStiliAlert } from '../../components/scriva-alert/utilities/scriva-alert.enums';
import { ScrivaInfoLevels } from '../../enums/scriva-utilities/scriva-utilities.enums';
import { MessaggioUtente } from '../../models';
import { ScrivaUtilitiesService } from '../scriva-utilities/scriva-utilities.service';
import { ScrivaAlertMessagesService } from './scriva-alert-messages.service';
import {
  IAlertConfigsChecks,
  IAlertConfigsFromCode,
  IAlertConfigsUpdate,
} from './utilities/scriva-alert.interfaces';

/**
 * Servizio di utility con funzionalità di gestione per gli alert dell'applicazione.
 */
@Injectable({ providedIn: 'root' })
export class ScrivaAlertService {
  /**
   * Costruttore.
   */
  constructor(
    private _scrivaAlertMsg: ScrivaAlertMessagesService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {}

  /**
   * Funzione che gestisce il prompt dei messaggi.
   * Se i parametri non sono definiti, verrà effettuato un reset.
   * @param configs IACMgsUpdate che definisce l'oggetto di configrazione per l'aggiornamento.
   * @return ScrivaAlertConfigs con l'oggetto aggiornato.
   */
  aggiornaAlertConfigsMsg(configs: IAlertConfigsUpdate): ScrivaAlertConfigs {
    // Estraggo dall'input le informazioni
    let { alert, messaggi, tipo } = configs;
    // Verifico le proprietà in input
    alert = alert ?? new ScrivaAlertConfigs();

    // Se non esistono messaggi resetto le impostazioni
    if (!messaggi) {
      // Resetto la configurazione
      alert.messages = [];
      alert.type = ScrivaInfoLevels.none;
      alert.persistentMessage = false;

      // Ritorno l'oggetto
      return alert;
    }

    // Verifico che tipologia di alert visualizzare
    switch (tipo) {
      case ScrivaInfoLevels.danger:
      case ScrivaInfoLevels.info:
      case ScrivaInfoLevels.warning:
        alert.persistentMessage = true;
        break;
      case ScrivaInfoLevels.success:
        alert.persistentMessage = false;
        break;
      default:
        alert.persistentMessage = true;
    }

    // Aggiorno la configurazione
    alert.messages = messaggi;
    alert.type = tipo || ScrivaInfoLevels.none;

    // Ritorno l'oggetto
    return alert;
  }

  /**
   * Funzione di comodo che partendo da un errore server, genera uno specifico oggetto di alert gestendo la tipologia/colore dell'alert.
   * @param error ScrivaServerError che definisce le informazioni dell'errore generato dal server.
   */
  createAlertFromServerError(error: ScrivaServerError): ScrivaAlertConfigs {
    // Verifico esista l'oggetto
    if (!error) {
      // Nessuna configurazione
      return undefined;
    }

    // Estraggo dall'oggetto l'errore del server
    const code = error?.error?.code;
    // Ritorno la configurazione dell'alert passando il codice
    return this.createAlertFromMsgCode({ code });
  }

  /**
   * Funzione di comodo che partendo da un messaggio d'errore, genera uno specifico oggetto di alert gestendo la tipologia/colore dell'alert.
   * @param code IAlertConfigsFromCode che definisce le configurazioni per la generazione dell'alert.
   */
  createAlertFromMsgCode(configs: IAlertConfigsFromCode): ScrivaAlertConfigs {
    // Verifico il codice
    if (!configs || !configs.code) {
      // Niente codice, niente configurazione
      return undefined;
    }

    // Estraggo le informazioni per la configurazione dell'alert
    const { code } = configs || {};

    // Recupero dal servizio l'oggetto intero che identifica un messaggio
    const m = this._scrivaAlertMsg.getMessaggioUtente(code);
    // Verifico esista una configurazione
    if (!m) {
      // Codice non trovato, configurazione non definita
      return undefined;
    }

    // Creo l'oggetto per l'alert da ritornare
    const alert = new ScrivaAlertConfigs();

    // Definisco il title per l'alert da visualizzare
    alert.title = m.des_titolo_messaggio;
    // Definisco il messaggio dell'alert da visualizzare
    this.messageForAlertFromMsgCode(m, alert, configs);
    // Definisco lo stile grafico dell'alert
    this.typeForAlertFromMsgCode(m, alert);

    // Ritorno l'oggetto di alert
    return alert;
  }

  /**
   * Funzione che modifica tramite referenza i messaggi dell'alert passato in input.
   * Le informazioni dei messaggi verranno definite a seconda dell'input.
   * @param messaggio MessaggioUtente con la configurazione per la definizione del messaggio dell'alert.
   * @param alert ScrivaAlertConfigs con la configurazione per la definizione del messaggio dell'alert.
   * @param configs IAlertConfigsFromCode con la configurazione per la definizione del messaggio dell'alert.
   */
  private messageForAlertFromMsgCode(
    messaggio: MessaggioUtente,
    alert: ScrivaAlertConfigs,
    configs: IAlertConfigsFromCode
  ) {
    // Verifico esista l'alert
    if (!alert) {
      // nessuna configurazione
      return;
    }

    // Estraggo le informazioni per la configurazione dell'alert
    const { code, codesPlaceholders, dataReplace, fallbackPlaceholder } =
      configs || {};
    // Recupero le informazioni dalla configurazione del messaggio
    const { des_testo_messaggio } = messaggio || {};

    // Definisco il messaggio per l'alert a seconda della configurazione
    if (codesPlaceholders && dataReplace) {
      // Esistono informazioni per un messaggio con parametri da rimpiazzare
      const msg = this._scrivaAlertMsg.getMessageWithPlacholder(
        code,
        codesPlaceholders,
        dataReplace,
        fallbackPlaceholder
      );
      // Definisco un array inserendo il messaggio recuperato
      alert.messages = [msg];
      // #
    } else {
      // E' un messaggio semplice
      alert.messages = [des_testo_messaggio ?? ''];
      // #
    }
  }

  /**
   * Funzione che modifica tramite referenza lo stile dell'alert passato in input.
   * Le informazioni dei messaggi verranno definite a seconda dell'input.
   * @param messaggio MessaggioUtente con la configurazione per la definizione del messaggio dell'alert.
   * @param alert ScrivaAlertConfigs con la configurazione per la definizione del messaggio dell'alert.
   */
  private typeForAlertFromMsgCode(
    messaggio: MessaggioUtente,
    alert: ScrivaAlertConfigs
  ) {
    // Verifico esista l'alert
    if (!alert) {
      // nessuna configurazione
      return;
    }

    // Recupero le informazioni dalla configurazione del messaggio
    const { tipo_messaggio } = messaggio;
    // Recupero il codice del tipo messaggio
    const { cod_tipo_messaggio } = tipo_messaggio || {};

    // Recupero tramite mappatura codice messaggio/stile alert, lo stile per l'alert
    const type: any = ScrivaCodTMStiliAlert[cod_tipo_messaggio];
    // Assegno lo stile all'alert
    alert.type = type ?? ScrivaInfoLevels.info;
  }

  /**
   * Funzione di comodo che imposta il messaggio d'errore
   * @param callError ScrivaServerError che definisce il corpo dell'errore.
   * @param messageCode string che definisce il messaggio d'errore da visualizzare in testata. Default è 'E100'.
   * @param otherMessages Array di string contenente un array con altri messaggi di dettaglio per l'errore. Default è [].
   */
  messagesFromServerError(
    callError: ScrivaServerError,
    messageCode = ScrivaCodesMesseges.E100,
    otherMessages: string[] = []
  ): string[] {
    // Variabile di comodo
    const { error } = callError;
    // Definisco un array di messaggi d'errore
    let erroriValidazione: string[] = [];

    // Verifico se da server è stato restituito un codice specifico di errore
    if (error?.code) {
      // Sovrascrivo il message code di default
      messageCode = error.code as ScrivaCodesMesseges;
    }

    // Recupero il messaggio d'errore dal servizio
    const message = this._scrivaAlertMsg.getDescrizioneMessaggio(
      messageCode,
      error?.title
    );
    // Recupero il codice per errore inaspettato
    erroriValidazione.push(message);

    // Verifico se esistono anche altri messaggi
    if (otherMessages) {
      // Concateno le liste di errori
      erroriValidazione = erroriValidazione.concat(otherMessages);
    }

    // Aggiorno la lista di errori
    return erroriValidazione;
  }

  /**
   * Funzione che verifica per default se l'alert config in input esiste, e contiene messaggi.
   * @param alertConfigs ScrivaAlertConfigs da verificare.
   * @param alertConfigsChecks IAlertConfigsChecks contenente possibili controlli d'applicare sull'oggetto ScrivaAlertConfigs.
   * @returns boolean che definisce se l'oggetto è valido, per default e per ogni possibile check di configurazione
   */
  alertConfigsCheck(
    alertConfigs: ScrivaAlertConfigs,
    alertConfigsChecks?: IAlertConfigsChecks
  ): boolean {
    // Verifico che l'oggetto esista
    if (!alertConfigs) {
      return false;
    }

    // Definisco un flag che definisce il risultato del controllo sull'oggetto
    let check = false;

    // Definisco il controllo di default
    check = alertConfigs.messages?.length > 0;
    // Verifico il risultato
    if (!check) {
      return false;
    }

    // Controllo principale passato, verifico se esistono altri check da fare
    const checks = alertConfigsChecks !== undefined;
    const othersChecks =
      checks && !this._scrivaUtilities.isEmptyObject(alertConfigs);
    // Verifico i controlli
    if (!othersChecks) {
      return true;
    }

    // Gestisco le logiche in un try catch per evitare possibili errori nell'input
    try {
      // Ciclo le proprietà dell'oggetto
      for (let [keyCheck] of Object.keys(alertConfigsChecks)) {
        // Aggiorno il flag di default
        check = alertConfigsChecks[keyCheck](alertConfigs);
        // Verifico se la funzione ha invalidato il check
        if (!check) {
          return false;
        }
      }

      // Tutte le funzioni di check sono valide
      return true;
      // #
    } catch (e) {
      // Ritorno il controllo principale
      return true;
    }
  }
}
