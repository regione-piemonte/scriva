/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subscription } from 'rxjs';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';
import { ScrivaCodesMesseges } from 'src/app/core/enums/scriva-codes-messages.enums';
import { MessageService } from 'src/app/shared/services';
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { NotificheConfigurazioniService } from '../../services/notifiche-configurazioni.service';
import { NotificheHttpService } from '../../services/notifiche-http/notifiche-http.service';
import { NotifichePagingCount } from '../../services/notifiche-http/utilities/notifiche-http.classes';
import { NotificheService } from '../../services/notifiche.service';

@Component({
  selector: 'scriva-notifiche-home',
  templateUrl: './notifiche-home.component.html',
  styleUrls: ['./notifiche-home.component.scss'],
})
export class NotificheHomeComponent implements OnInit, OnDestroy {
  ALERT_ANCHOR: string = 'ALERT_NOTIFICHE_HOME';
  notificheApplicative: NotificaApplicativa[] = [];
  notificheMostrate: number;
  private _subscription: Subscription;

  constructor(
    private _message: MessageService,
    private _notifiche: NotificheService,
    private _notificheConfig: NotificheConfigurazioniService,
    private _notificheHttp: NotificheHttpService,
    private _spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this._subscription = this._notificheHttp
      .getNotificheApp()
      .subscribe((notificheApplicative: NotificaApplicativa[]) => {
        if (notificheApplicative && Array.isArray(notificheApplicative)) {
          if (!this.notificheMostrate) {
            this.notificheMostrate =
              this._notificheConfig.getScrivaNotifyNumNotificheHome();
          }
          this.notificheApplicative = notificheApplicative.slice(
            0,
            this.notificheMostrate
          );
        }
      });
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione andrà a contrassegnare la notifica come "cancellata" ed aggiornerà i dati.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  onNotificaCancellata(notificaCard: NotificaApplicativa) {
    // Avvio lo spinner
    this._spinner.show();

    // Lancio la funzione di cancellazione della notifica
    this._notificheHttp.notificaAppCancellataHp(notificaCard).subscribe({
      next: (res: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Chiudo lo spinner
        this._spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Richiamo la funzione di gestione degli errori
        this.onServiceError(e);
        // Chiudo lo spinner
        this._spinner.hide();
        // #
      },
    });
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione andrà a contrassegnare la notifica come "letta" ed aggiornerà i dati.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  onNotificaLetta(notificaCard: NotificaApplicativa) {
    // Avvio lo spinner
    this._spinner.show();

    // Lancio la funzione di lettura della notifica
    this._notificheHttp.notificaAppLettaHp(notificaCard).subscribe({
      next: (res: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Chiudo lo spinner
        this._spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Richiamo la funzione di gestione degli errori
        this.onServiceError(e);
        // Chiudo lo spinner
        this._spinner.hide();
        // #
      },
    });
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione effettuerà la navigazione dell'applicazione verso la pagina: dettaglio notifica.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  onDettaglioNotifica(notificaCard: NotificaApplicativa) {
    // Effettuo la navigazione verso la pagina di dettaglio notifica
    this._notifiche.navigateToNotifica(notificaCard.id_notifica_applicativa);
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione effettuerà la navigazione dell'applicazione verso la pagina: procedimento.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  navigateToPratica(notificaCard: NotificaApplicativa) {
    // Effettuo la navigazione verso la pagina di dettaglio procedimento
    this._notifiche.navigateToPratica(notificaCard);
  }

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
    // Definisco le configurazioni per la visualizzazione dell'alert
    const target = this.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }

  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  navigateToNotifiche() {
    this._notifiche.navigateToNotifiche();
  }

  get isEnableVediTutte(): boolean {
    // Ritorno la verifica sul numero delle notifiche applicative per abilitare vedi tutte
    return this.notificheApplicative?.length > 0;
  }

  /**
   * Getter che ritorna la verifica per le notifiche in pagina.
   * @returns boolean con il risultato del check.
   */
  get checkNotificheApplicative(): boolean {
    // Ritorno il check sulle notifiche
    return this.notificheApplicative?.length > 0;
  }
}
