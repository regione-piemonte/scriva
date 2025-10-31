/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { MessageService } from '../../../../shared/services';
import { IPreferenzaNotifica } from '../../models/preferenze-notifiche.model';
import { ProfiloNotificheService } from '../../services/profilo-notifiche/profilo-notifiche.service';
import { ProfiloService } from '../../services/profilo/profilo.service';
import { PROFILO_CONSTS } from '../profilo/utilities/profilo.consts';
import { PROFILO_NOTIFICHE_CONSTS } from './utilities/profilo-notifiche.consts';

@Component({
  selector: 'scriva-profilo-notifiche',
  templateUrl: './profilo-notifiche.component.html',
  styleUrls: ['./profilo-notifiche.component.scss'],
})
export class ProfiloNotificheComponent implements OnInit {
  /** Costante contenente le informazioni costanti del componente. */
  P_C = PROFILO_CONSTS;
  PN_C = PROFILO_NOTIFICHE_CONSTS;

  /** IPreferenzaNotifica[] contenente le configurazioni per le preferenze di notifica dell'utente allo stato iniziale. */
  preferenzeNotificheInit: IPreferenzaNotifica[];
  /** IPreferenzaNotifica[] contenente le configurazioni per le preferenze di notifica dell'utente a seguito delle modifiche sulle configurazioni. */
  preferenzeNotificheUpd: IPreferenzaNotifica[];
  /*  HTMLDivElement che contiene alert */
  alertDiv: HTMLDivElement = null;

  /**
   * Costruttore.
   */
  constructor(
    private _message: MessageService,
    private _profilo: ProfiloService,
    private _profiloNotifiche: ProfiloNotificheService
  ) {
    // Lancio il setup del componente
    this.setupComponente();
  }

  /**
   * NgOnInit.
   */
  ngOnInit() {
    // Lancio l'init del componente
    this.initComponente();
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  /**
   * Funzione di setup del componente, lanciato dal costruttore.
   */
  private setupComponente() {
    // Lancio il setup delle preferenze utente
    this.setupPreferenzeNotifiche();
  }

  /**
   * Funzione di comodo che recupera dal servizio profilo le informazioni per le preferenze notifiche.
   */
  private setupPreferenzeNotifiche() {
    // Recupero dal servizio condiviso i dati delle preferenze
    this.preferenzeNotificheInit = this._profilo.preferenzeNotifiche;
    // Al setup, le preferenze update sono uguale alle init
    this.preferenzeNotificheUpd = this.preferenzeNotificheInit;
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di init del componente, lanciato dall'NgOnInit.
   */
  private initComponente() {}

  /**
   * #############################################
   * FUNZIONI COLLEGATE AL TEMPLATE DEL COMPONENTE
   * #############################################
   */

  /**
   * Funzione collegata all'evento di modifica delle configurazioni per le preferenze notifiche.
   * @param preferenzeNotifiche IPreferenzaNotifica[] aggiornate secondo l'interazione con l'utente.
   */
  onPreferenzeChanged(preferenzeNotifiche: IPreferenzaNotifica[]) {
    // Aggiorno la lista delle preferenze aggiornate
    this.preferenzeNotificheUpd = preferenzeNotifiche;
  }

  /**
   * Funzione collegata al pulsante di conferma e salvataggio delle preferenze notifiche.
   * La funzione attiverà il salvataggio dati.
   */
  confermaModificaPreferenze() {
    // Recupero dal componente le preferenze all'ultima modifica
    let preferenzeNotifiche: IPreferenzaNotifica[];
    preferenzeNotifiche = this.preferenzeNotificheUpd;

    // Lancio la funzione del servizio per l'aggiornamento dei dati
    this._profiloNotifiche
      .aggiornaPreferenzeNotifiche(preferenzeNotifiche)
      .subscribe({
        next: (preferenzeNotifiche: IPreferenzaNotifica[]) => {
          // Aggiorno la lista di init e upd
          this.preferenzeNotificheInit = preferenzeNotifiche;
          this.preferenzeNotificheUpd = preferenzeNotifiche;
          // Visualizzo il messaggio di avvenuta modifica alle configurazioni
          const code = ScrivaCodesMesseges.P001;
          // Visualizzo il messaggio
          this.showAlert(code);
          // #
        },
        error: (e: ScrivaServerError) => {
          // Visualizzo il messaggio d'errore generato dal server
          this.onServiceError(e);
          // #
        },
      });
  }

  /**
   * ##################################
   * FUNZIONI DI UTILITY DEL COMPONENTE
   * ##################################
   */

  /**
   * Funzione di supporto per la gestione della segnalazione dell'errore da parte dei servizi.
   * @param e ScrivaServerError con il dettaglio d'errore generato.
   */
  private onServiceError(e?: ScrivaServerError) {
    // Si è verificato un errore, gestisco la segnalazione utente
    const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
    // Visualizzo il messaggio
    this.showAlert(code);
  }

  /**
   * Funzione di supporto per la gestione degli errori nel componente, dato il codice errore.
   * @param code string con il codice del messaggio da visualizzare.
   */
  private showAlert(code: string) {
    // nascondo il messagio se gia' presente in pagina
    this._message.hideMessage(this.alertDiv);
    // Definisco le configurazioni per la visualizzazione dell'alert
    const target = this.PN_C.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this.alertDiv = this._message.showMessage(code, target, autoFade);
  }
}
