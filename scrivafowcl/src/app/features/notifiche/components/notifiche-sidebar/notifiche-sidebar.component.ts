/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { ScrivaAlertConfigs } from '../../../../shared/components/scriva-alert/utilities/scriva-alert.classes';
import { CommonConsts } from '../../../../shared/consts/common-consts.consts';
import { RicercaPaginataResponse } from '../../../../shared/services/helpers/utilities/http-helper.classes';
import { ScrivaAlertService } from '../../../../shared/services/scriva-alert/scriva-alert.service';
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { NotifichePagingCount } from '../../services/notifiche-http/utilities/notifiche-http.classes';
import { NotificheSidebarService } from '../../services/notifiche-sidebar/notifiche-sidebar.service';
import { NotificheService } from '../../services/notifiche.service';
import { NOTIFICHE_SIDEBAR_CONSTS } from './utilities/notifiche-sidebar.consts';
import { NotificaCardTypeView } from '../notifica-card/notifiche-card/notifica-card.enums';

@Component({
  selector: 'scriva-notifiche-sidebar',
  templateUrl: './notifiche-sidebar.component.html',
  styleUrls: ['./notifiche-sidebar.component.scss'],
})
export class NotificheSidebarComponent implements OnInit {
  /** Oggetto di costanti condivise nell'applicazione. */
  C_C = new CommonConsts();

  /** Output NotificaApplicativa che definisce l'evento di avvenuta lettura della notifica. */
  @Output('onDettaglioNotifica') onDettaglioNotifica$ =
    new EventEmitter<NotificaApplicativa>();
  /** Output NotificaApplicativa che definisce l'evento di avvenuta lettura della notifica. */
  @Output('onVaiAllaPratica') onVaiAllaPratica$ =
    new EventEmitter<NotificaApplicativa>();
  /** Output any che definisce l'evento di avvenuta lettura della notifica. */
  @Output('onVaiATutteNotifiche') onVaiATutteNotifiche$ =
    new EventEmitter<any>();

  /** Costanti di comodo per la gestione del componente. */
  NSB_C = NOTIFICHE_SIDEBAR_CONSTS;

  /** NotificaApplicativa[] che definisce la lista di notifiche per la sidebar, mostrerà la prime 20. */
  notificheApp: NotificaApplicativa[];

  /** ScrivaAlertConfigs contenente le configurazioni per la gestione dell'alert utente. */
  alertConfigs: ScrivaAlertConfigs = new ScrivaAlertConfigs();
  /** ScrivaAlertConfigs contenente le configurazioni per la gestione dell'alert utente per nessuna notifica. */
  alertConfigsNoNotifica: ScrivaAlertConfigs = new ScrivaAlertConfigs();

  /**
   * Costruttore.
   */
  constructor(
    private _notifiche: NotificheService,
    private _notificheSidebar: NotificheSidebarService,
    private _scrivaAlert: ScrivaAlertService,
    private _spinner: NgxSpinnerService
  ) {
    // Richiamo il setup del componente
    this.setupComponente();
  }

  /**
   * NgOnInit.
   */
  ngOnInit(): void {
    // Richiamo l'init del componente
    this.initComponente();
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di setup del componente.
   */
  private setupComponente() {
    // Lancio la configurazione statica per il messaggio da visualizzare quando non ci sono notifiche
    this.setupAlertNessunaNotifica();
  }

  /**
   * Funzione di setup per l'alert specifico che gestisce la casistica di nessuna specifica.
   */
  private setupAlertNessunaNotifica() {
    // Definisco la configurazione statica per l'alert di nessuna notifica
    this.alertConfigsNoNotifica = this._scrivaAlert.createAlertFromMsgCode({
      code: ScrivaCodesMesseges.I023,
    });
    // Definisco le altre configurazioni per l'alert
    this.alertConfigsNoNotifica.persistentMessage = true;
    this.alertConfigsNoNotifica.allowAlertClose = false;
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di utility che raccoglie le funzioni di init del componente.
   */
  private initComponente() {
    // Lancio l'init per lo scarico delle notifiche
    this.initNotifiche();
  }

  /**
   * Funzione di init per le notifiche del componente.
   */
  private initNotifiche() {
    // Richiamo la funzione di scarico delle notifiche da leggere
    this.cercaNotificheSidebar();
  }

  /**
   * ########################################
   * FUNZIONI DI RICERCA E GESTIONE RISULTATI
   * ########################################
   */

  /**
   * Funzione specifica di ricerca delle notifiche.
   * Questa funzione è dedicata alla ricerca delle notifiche (senza filtri o paginazioni particolari), ma che non contemplino lo scarico delle notifiche da leggere.
   */
  cercaNotificheSidebar() {
    // Avvio lo spinner
    this._spinner.show();

    // Richiamo lo scarico delle notifiche con la specifica configurazione
    this._notificheSidebar.cercaNotificheSidebar().subscribe({
      next: (nonLette: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Aggiorno le informazioni per le notifiche e per la paginazione
        this.aggiornaNotifiche(nonLette);
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
   * ##############################
   * FUNZIONI COLLEGATE AL TEMPLATE
   * ##############################
   */

  /**
   * Funzione collegata al template del componente.
   * La funzione segnarà tutte le notifiche ATTUALMENTE in pagina come "lette".
   */
  segnaTutteNotificheLette() {
    // Avvio lo spinner
    this._spinner.show();

    // Lancio la funzione di lettura per tutte le notifiche
    this._notificheSidebar.segnaTutteNotificheLette().subscribe({
      next: (nonLette: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Aggiorno le informazioni per le notifiche e per la paginazione
        this.aggiornaNotifiche(nonLette);
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
    this._notificheSidebar.notificaAppCancellata(notificaCard).subscribe({
      next: (nonLette: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Aggiorno le informazioni per le notifiche e per la paginazione
        this.aggiornaNotifiche(nonLette);
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
    // Controllo la validità per l'operazione
    if (notificaCard.data_lettura != undefined) {
      // Non ri-eseguo la cancellazione
      return;
    }

    // Avvio lo spinner
    this._spinner.show();

    // Lancio la funzione di lettura della notifica
    this._notificheSidebar.notificaAppLetta(notificaCard).subscribe({
      next: (nonLette: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Aggiorno le informazioni per le notifiche e per la paginazione
        this.aggiornaNotifiche(nonLette);
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
    // Emetto l'evento di notifica di cambio pagina
    this.onDettaglioNotifica$.emit();
    // Effettuo la navigazione verso la pagina di dettaglio notifica
    // informando che arrivo dalla sidebar
    this._notifiche.navigateToNotifica(notificaCard.id_notifica_applicativa, NotificaCardTypeView.SIDEBAR);
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione effettuerà la navigazione dell'applicazione verso la pagina: procedimento.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  navigateToPratica(notificaCard: NotificaApplicativa) {
    // Emetto l'evento di notifica di cambio pagina
    this.onVaiAllaPratica$.emit();
    // Effettuo la navigazione verso la pagina di dettaglio procedimento
    this._notifiche.navigateToPratica(notificaCard);
  }
  
  /**
   * Funzione collegata al template del componente.
   * La funzione effettuerà la navigazione dell'applicazione verso la pagina: lista notifiche.
   */
  vaiAListaNotifiche() {
    // Emetto l'evento di notifica di cambio pagina
    this.onVaiATutteNotifiche$.emit();
    // Effettuo la navigazione verso la pagina di tutte le notifiche
    this._notifiche.navigateToNotifiche();
  }

  /**
   * #####################################
   * FUNZIONI DI UTILITY PER IL COMPONENTE
   * #####################################
   */

  /**
   * Funzione che aggiorna le notifiche sulla base della risposta ritornata dai servizi delle notifiche.
   * @param notificheRes RicercaPaginataResponse<NotificaApplicativa[]> con le informazioni restituite dal server.
   */
  private aggiornaNotifiche(
    notificheRes: RicercaPaginataResponse<NotificaApplicativa[]>
  ) {
    // Aggiorno le informazioni per le notifiche
    this.notificheApp = notificheRes.sources;
  }

  /**
   * Funzione che gestisce il prompt dei messaggi.
   * @param code string il codice per la rigenerazione dell'alert.
   */
  private aggiornaAlertConfigs(code: string) {
    // Aggiorno l'alert con le nuove informazioni
    this.alertConfigs = this._scrivaAlert.createAlertFromMsgCode({ code });
    // Definisco il timeout per l'alert
    this.alertConfigs.persistentMessage = false;
    this.alertConfigs.timeoutMessage = 5000;
  }

  /**
   * Funzione che gestisce il reset del prompt dei messaggi.
   * Se i parametri non sono definiti, verrà effettuato un reset.
   * @param c ScrivaAlertConfigs da resettare.
   */
  resetAlertConfigs(c?: ScrivaAlertConfigs) {
    // Verifico se esiste l'oggetto per l'alert, altrimenti imposto quello locale
    c = c ?? this.alertConfigs;
    // Resetto la configurazione
    c = this._scrivaAlert.aggiornaAlertConfigsMsg({ alert: c });
  }

  /**
   * Funzione di supporto per la gestione della segnalazione dell'errore da parte dei servizi.
   * @param e ScrivaServerError con il dettaglio d'errore generato.
   */
  private onServiceError(e?: ScrivaServerError) {
    // Si è verificato un errore, gestisco la segnalazione utente
    const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
    // Visualizzo il messaggio
    this.aggiornaAlertConfigs(code);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo che verifica se esistono notifiche.
   * @returns boolean con il risultato della verifica.
   */
  get checkNotifiche(): boolean {
    // Verifico se esiste almeno una notifica
    return this.notificheApp?.length > 0;
  }

  /**
   * Getter che verifica se l'oggetto alertConfigs esiste e ha messaggi.
   */
  get alertConfigsCheck() {
    // Verifico e ritorno la condizione
    return this._scrivaAlert.alertConfigsCheck(this.alertConfigs);
  }
}
