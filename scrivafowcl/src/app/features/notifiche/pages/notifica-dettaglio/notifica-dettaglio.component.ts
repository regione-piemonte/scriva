/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';
import { ScrivaCodesMesseges } from 'src/app/core/enums/scriva-codes-messages.enums';
import { IstanzaService, MessageService } from 'src/app/shared/services';
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { NotificaDettaglioService } from '../../services/notifica-dettaglio/notifica-dettaglio.service';
import { NotificheService } from '../../services/notifiche.service';
import { NOTIFICHE_DETTAGLIO_CONSTS } from './utilities/notifiche-dettaglio.consts';
import { NotificaCardTypeView } from '../../components/notifica-card/notifiche-card/notifica-card.enums';

@Component({
  selector: 'scriva-notifica-dettaglio',
  templateUrl: './notifica-dettaglio.component.html',
  styleUrls: ['./notifica-dettaglio.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class NotificaDettaglioComponent implements OnInit {
  /** Oggetto di costanti con le informazioni per il componente. */
  ND_C = NOTIFICHE_DETTAGLIO_CONSTS;

  /** NotificaApplicativa con le informazioni di dettaglio per la notifica. */
  notifica: NotificaApplicativa;

  /* Vista da cui sono arrivato al dettaglio */
  from:NotificaCardTypeView;

  /**
   * Costruttore.
   */
  constructor(
    private _istanza: IstanzaService,
    private _message: MessageService,
    private _notificaDettaglio: NotificaDettaglioService,
    private _notifiche: NotificheService,
    private _route: ActivatedRoute,
    private _router: Router,
    private _spinner: NgxSpinnerService
  ) {
    // Lancio il setup del componente
    this.setupComponente();
  }

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
  private setupComponente() {}

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di init del componente, lanciato dall'NgOnInit.
   */
  private initComponente() {
    // Lancio l'inizializzazione del form
    this.initRouteParams();
    // recupero Vista da cui sono arrivato al dettaglio 
    this.from = this._notifiche.from;
  }

  /**
   * Funzione di init del componente.
   */
  private initRouteParams() {
    // recupero dal servizio di routing le info di dettaglio della notifica
    this._route.data.subscribe({
      next: (routeParams: any) => {
        // recuperiamo dal servizio di routing le info per la notifica applicativa
        this.notifica = routeParams?.notificaApplicativa;
        // Richiamo la funzione di lettura della notifica
        this.leggiNotifica(this.notifica);
      },
    });
  }

  /**
   * ##############################
   * FUNZIONI COLLEGATE AL TEMPLATE
   * ##############################
   */

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione andrà a contrassegnare la notifica come "cancellata" ed aggiornerà i dati.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  onNotificaCancellata(notificaCard: NotificaApplicativa) {
    // Controllo la validità per l'operazione
    if (notificaCard.data_cancellazione != undefined) {
      // Non ri-eseguo la cancellazione
      return;
    }

    // Avvio lo spinner
    this._spinner.show();

    // Lancio la funzione di cancellazione della notifica
    this._notificaDettaglio.notificaAppCancellata(notificaCard).subscribe({
      next: (notificaUpd: NotificaApplicativa) => {
        this._redirectAfterDelete();
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
  leggiNotifica(notificaCard: NotificaApplicativa) {
    // Controllo la validità per l'operazione
    if (notificaCard.data_lettura != undefined) {
      // Non ri-eseguo la cancellazione
      return;
    }

    // Avvio lo spinner
    this._spinner.show();

    // Lancio la funzione di lettura della notifica
    this._notificaDettaglio.notificaAppLetta(notificaCard).subscribe({
      next: (notificaUpd: NotificaApplicativa) => {
        // Gestisco la logica dopo l'aggiornamento
        this.onNotificaUpdate(notificaUpd);
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
   * La funzione effettuerà la navigazione dell'applicazione verso la pagina: procedimento.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  onVaiAllaPratica(notificaCard: NotificaApplicativa) {
    // Effettuo la navigazione verso la pagina di dettaglio procedimento
    this._notifiche.navigateToPratica(notificaCard);
  }

  /** Torna alla home. */
  vaiAllaHome() {
    this._istanza.setIdIstanza(null);
    this._router.navigate(['/home']);
  }

  /** Torna alle notifiche. */
  vaiAlleNotifiche() {
    this._istanza.setIdIstanza(null);
    this._notifiche.navigateToNotifiche();
  }

  /**
   * Metodo di comodo che dopo la cancellazione
   * rimanda alla sezione come da specifiche Scriva962
   */
  private _redirectAfterDelete() {
    if(this.isFromSidebar) {
      this.vaiAlleNotifiche();
    }
    if(this.isFromHome) {
      this.vaiAllaHome();
    }
  }

  /**
   * ##################################
   * FUNZIONI DI UTILITY DEL COMPONENTE
   * ##################################
   */

  /**
   * Funzione di supporto invocata all'aggiornamento delle informazioni della notifica applicativa.
   * @param notificaUpd NotificaApplicativa con l'oggetto aggiornato della notifica.
   */
  private onNotificaUpdate(notificaUpd: NotificaApplicativa) {
    // Aggiorno l'oggetto della notifica
    this.notifica = notificaUpd;
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
    const target = this.ND_C.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }

  /**
  * Getter di comodo per verificare se si arriva dalla home page 
  * (caso di default se non si ha from restituisce un true)
  * @returns boolean
  */
  get isFromHome(): boolean {
    return this.from === NotificaCardTypeView.HOME || !this.from;
  }

  /**
  * Getter di comodo per verificare se si arriva dalla sidebar
  * @returns boolean
  */
  get isFromSidebar(): boolean {
    return this.from === NotificaCardTypeView.SIDEBAR;
  }

}
