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
import { Router } from '@angular/router';
import { cloneDeep } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { AuthStoreService, MessageService } from '../../../../shared/services';
import {
  RicercaPaginataResponse,
  ScrivaPagination,
} from '../../../../shared/services/helpers/utilities/http-helper.classes';
import { ScrivaSortTypes } from '../../../../shared/services/helpers/utilities/http-helper.enums';
import { INotificheSearchData } from '../../components/notifiche-search/utilities/notifiche-search.interfaces';
import { CodStatiNotifiche } from '../../enums/notifiche.enums';
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { ISearchNotificheRequest } from '../../models/search-notifiche-request';
import { NotificheConfigurazioniService } from '../../services/notifiche-configurazioni.service';
import { NotificheHttpService } from '../../services/notifiche-http/notifiche-http.service';
import { NotifichePagingCount } from '../../services/notifiche-http/utilities/notifiche-http.classes';
import { NotificheService } from '../../services/notifiche.service';
import { NOTIFICHE_CONSTS } from './utilities/notifiche.consts';
import { ScrivaAlertConfigs } from 'src/app/shared/components/scriva-alert/utilities/scriva-alert.classes';
import { ScrivaAlertService } from 'src/app/shared/services/scriva-alert/scriva-alert.service';

@Component({
  selector: 'scriva-notifiche',
  templateUrl: './notifiche.component.html',
  styleUrls: ['./notifiche.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class NotificheComponent implements OnInit {
  /** Costanti di comodo per la gestione del componente. */
  N_C = NOTIFICHE_CONSTS;

  /** NotificaApplicativa[] che definisce la lista di notifiche per l'utente. */
  notificheApp: NotificaApplicativa[];
  
  /** SearchNotificheRequest contenente la struttura dell'oggetto di filtro, generato dalla form, alla sua ultima conferma. */
  private lastFiltri: ISearchNotificheRequest;
  /** ScrivaPagination che definisce la struttura per la paginazione delle notifiche. */
  private _notifichePag: ScrivaPagination;

  /** Boolean che definisce il flag per la gestione dell'apertura/chiusura del form di ricerca/filtro. */
  showFormRicerca = false;

  /** ISearchNotificheRequest che definisce il fittro di  default per la componente */
  filtriDefault: ISearchNotificheRequest = {
    cod_stato_notifica: CodStatiNotifiche.nonCancellate,
  };
  /* Ordinamento corrente della lista */
  _sortDirection: ScrivaSortTypes;

  /** ScrivaAlertConfigs contenente le configurazioni per la gestione dell'alert utente per nessuna notifica. */
  alertConfigsNoNotifica: ScrivaAlertConfigs = new ScrivaAlertConfigs();



  /**
   * Costruttore.
   */
  constructor(
    private _authStore: AuthStoreService,
    private _message: MessageService,
    private _notifiche: NotificheService,
    private _notificheConfig: NotificheConfigurazioniService,
    private _notificheHttp: NotificheHttpService,
    private _scrivaAlert: ScrivaAlertService,
    private _spinner: NgxSpinnerService,
    private _router: Router
  ) {}

  /**
   * NgOnInit.
   */
  ngOnInit(): void {
    // Richiamo l'init del componente
    this.initComponente();
  }

  /**
   * NgOnDestroy.
   */
  ngOnDestroy(): void {}

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
    // Richiamo la funzione di scarico delle notifiche NON cancellate
    this.cercaNotificheNonCancellate();

    // Lancio la configurazione statica per il messaggio da visualizzare quando non ci sono notifiche
    this.setupAlertNessunaNotifica();
  }

  /**
   * Funzione di setup per l'alert specifico che gestisce la casistica di nessuna specifica.
   */
   private setupAlertNessunaNotifica() {
    // Definisco la configurazione statica per l'alert di nessuna notifica
    this.alertConfigsNoNotifica = this._scrivaAlert.createAlertFromMsgCode({
      code: ScrivaCodesMesseges.I021,
    });
    // Definisco le altre configurazioni per l'alert
    this.alertConfigsNoNotifica.persistentMessage = true;
    this.alertConfigsNoNotifica.allowAlertClose = false;
  }

  /**
   * ########################################
   * FUNZIONI DI RICERCA E GESTIONE RISULTATI
   * ########################################
   */

  /**
   * Funzione specifica di ricerca delle notifiche.
   * Questa funzione è dedicata alla ricerca delle notifiche (senza filtri o paginazioni particolari), ma che non contemplino lo scarico delle notifiche cancellate.
   */
  cercaNotificheNonCancellate() {
    // I filtri iniziali sono coincidenti con il reset
    this.onResetFiltri();

    // La paginazione è quella iniziale
    const paginazione = new ScrivaPagination();

    // Richiamo lo scarico delle notifiche con la specifica configurazione
    this.cercaNotifiche(this.lastFiltri, paginazione);
  }

  /**
   * Funzione di ricerca delle notifiche.
   * A seguito della ricerca, verranno aggiornate le notifiche e l'eventuale paginazione da mostrare all'utente.
   * @param filtri ISearchNotificheRequest con l'oggetto di filtro d'applicare alla ricerca.
   * @param paginazione ScrivaPagination che definisce i filtri per la paginazione.
   */
  cercaNotifiche(
    filtri: ISearchNotificheRequest,
    paginazione: ScrivaPagination
  ) {
    // Avvio lo spinner
    this._spinner.show();

    // Richiamo la funzione di scarico delle notifiche
    this._notificheHttp.getNotifichePaginated(filtri, paginazione).subscribe({
      next: (ricerca: RicercaPaginataResponse<NotificaApplicativa[]>) => {
        // Aggiorno le informazioni per le notifiche e per la paginazione
        this.aggiornaNotificheEPaginazione(ricerca);
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

    // Dall'ultima request estraggo la request
    let search: ISearchNotificheRequest;
    search = this.lastFiltri;

    let pagination: ScrivaPagination;
    pagination = this.notifichePag;
    if(this._sortDirection) {
      pagination.sortDirection =  this._sortDirection;
      // Imposto il filtro di ordinamento per la data
      pagination.sortBy = this.orderDefault;
    }


    // Lancio la funzione di lettura per tutte le notifiche
    this._notificheHttp.notificaAppLettaBySearch(search, pagination).subscribe({
      next: (notificheLette: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Aggiorno le informazioni per le notifiche e per la paginazione
        this.aggiornaNotificheEPaginazione(notificheLette);
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
   * Funzione collegata al template.
   * Quando viene premuto il pulsante per l'apertura/chiusura della form di ricerca, avviene il toggle di visualizzazione.
   */
  toggleFormRicerca() {
    // Effettuo il toggle del flag per visualizzare il form di ricerca
    this.showFormRicerca = !this.showFormRicerca;
  }

  /**
   * Funzione collegata al template.
   * Viene lanciata la ricerca delle notifiche con ordinamento per data.
   */
  onOrdinaPerData() {
    // Dall'ultima request estraggo la request
    let search: ISearchNotificheRequest;
    search = this.lastFiltri;
    // La paginazione è quella iniziale
    const paginazione = new ScrivaPagination();

    // Imposto il filtro di ordinamento per la data
    paginazione.sortBy = this.orderDefault;
    console.log(this._sortDirection);
    // toogle della paginazione
    paginazione.sortDirection = this._sortDirection && ScrivaSortTypes.crescente===this._sortDirection? ScrivaSortTypes.decrescente: ScrivaSortTypes.crescente;
    // tengo da parte ordinamento per eventuale click successivo
    this._sortDirection =  paginazione.sortDirection;

    // Richiamo il servizio di ricerca notifiche
    this.cercaNotifiche(search, paginazione);
  }

  /**
   * Funzione collegata al template.
   * Quando viene premuto l'ingranaggio, si naviga verso la pagina del profilo utente.
   */
  navigateToProfilo() {
    // Effettuo la navigazione verso il profilo
    this._router.navigate(['/profilo']);
  }

  /**
   * Funzione collegata al componente che crea i filtri di ricerca per le notifiche.
   * @param filtri INotificheSearchData contenente le informazioni di configurazione generate per i filtri.
   */
  onConfermaFiltri(filtri: INotificheSearchData) {

    const isEmpty = Object.values(filtri).every(x => x === null || x === '');
    
    let search: ISearchNotificheRequest;
    if(!isEmpty){
      // Converto l'oggetto di filtri del form nella request di ricerca
      search = this.filtroFormToSearchServer(filtri);
      // Salvo l'oggetto di filtro come ultima configurazione di ricerca
      this.lastFiltri = search;
    } else {
      // in caso di filtri vuoti dal form devo resettare al comportamento di
      // default, perché potrei avere in memoria dati delle vecchia ricerca
      this.onResetFiltri();
    } 
    
    // La paginazione è quella iniziale
    const paginazione = new ScrivaPagination();
    if(this._sortDirection) {
      paginazione.sortDirection =  this._sortDirection;
      // Imposto il filtro di ordinamento per la data
      paginazione.sortBy = this.orderDefault;
    }
    
    // Richiamo il servizio di ricerca notifiche
    this.cercaNotifiche(this.lastFiltri, paginazione);
  }

  /**
   * Funzione collegata al componente che resetta i filtri di ricerca per le notifiche.
   */
  onResetFiltri() {
    // Resetto i filtri impostati con quello di default
    this.lastFiltri = {...this.filtriDefault};
    // Vado su ordinamento di default
    this._sortDirection = null;
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione andrà a contrassegnare la notifica come "cancellata" ed aggiornerà i dati.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   */
  onNotificaCancellata(notificaCard: NotificaApplicativa) {
    // Avvio lo spinner
    this._spinner.show();
    // Dall'ultima request estraggo la request
    let search: ISearchNotificheRequest;
    search = this.lastFiltri;
    // Dall'ultima request estraggo la paginazione per i filtri
    let pagination: ScrivaPagination;
    pagination = this.notifichePag;
    if(this._sortDirection) {
      pagination.sortDirection =  this._sortDirection;
      // Imposto il filtro di ordinamento per la data
      pagination.sortBy = this.orderDefault;
    }


    // Lancio la funzione di cancellazione della notifica
    this._notificheHttp
      .notificaAppCancellata(notificaCard , search, pagination)
      .subscribe({
        next: (res: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Aggiorno le informazioni per le notifiche e per la paginazione
          this.aggiornaNotificheEPaginazione(res);
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

    // Dall'ultima request estraggo la request
    let search: ISearchNotificheRequest;
    search = this.lastFiltri;
    // Dall'ultima request estraggo la paginazione per i filtri
    let pagination: ScrivaPagination;
    pagination = this.notifichePag;
    if(this._sortDirection) {
      pagination.sortDirection =  this._sortDirection;
      // Imposto il filtro di ordinamento per la data
      pagination.sortBy = this.orderDefault;
    }

    // Lancio la funzione di lettura della notifica
    this._notificheHttp
      .notificaAppLetta(notificaCard, search, pagination)
      .subscribe({
        next: (res: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Aggiorno le informazioni per le notifiche e per la paginazione
          this.aggiornaNotificheEPaginazione(res);
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
   * Funzione collegata al template ed invocata al cambio di paginazione dal componente paginatore.
   * @param paginazione ScrivaPagination con la nuova paginazione richiesta.
   */
  onCambioPaginatore(paginazione: ScrivaPagination) {
    // Recupero i filtri dall'ultima richiesta
    let search: ISearchNotificheRequest;
    search = this.lastFiltri;
    // Definisco la nuova paginazione
    let pagination: ScrivaPagination;
    pagination = paginazione;
    
    if(this._sortDirection) {
      pagination.sortDirection =  this._sortDirection;
      // Imposto il filtro di ordinamento per la data
      pagination.sortBy = this.orderDefault;
    }


    // Richiamo il servizio di ricerca notifiche
    this.cercaNotifiche(search, pagination);
  }

  /**
   * #####################################
   * FUNZIONI DI UTILITY PER IL COMPONENTE
   * #####################################
   */

  /**
   * Funzione di comodo per la conversione dell'oggetto filtro dalla form all'oggetto di ricerca server.
   * @param filtri INotificheSearchData con l'oggetto da convertire.
   * @returns SearchNotificheRequest convertito
   */
  private filtroFormToSearchServer(
    filtri: INotificheSearchData
  ): ISearchNotificheRequest {
    // Richiamo e ritorno la funzione del servizio
    return this._notifiche.convertINotificheSearchDataToSearchNotificheRequest(
      filtri
    );
  }

  /**
   * Funzione che aggiorna le notifiche e la paginazione sulla base della risposta ritornata dai servizi delle notifiche.
   * @param notificheRes RicercaPaginataResponse<NotificaApplicativa[]> con le informazioni restituite dal server.
   */
  private aggiornaNotificheEPaginazione(
    notificheRes: RicercaPaginataResponse<NotificaApplicativa[]>
  ) {
    // Aggiorno le informazioni per le notifiche e per la paginazione
    this.notificheApp = notificheRes.sources;
    this._notifichePag = notificheRes.paging;
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
    const target = this.N_C.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo che ritorna se l'applicazione è attiva per il FO.
   * @returns boolean che specifica se il profilo attivo è di FO.
   */
  get isFrontOffice(): boolean {
    // Richiamo e ritorno il flag settato nel servizio
    return this._authStore.isFrontOffice;
  }

  /**
   * Getter di comodo per il campo di ordinamento di default.
   * @returns string con il campo per l'ordinamento di default.
   */
  get orderDefault() {
    // Richiamo il servizio contenente il default
    return this._notificheConfig.getScrivaNotifyFieldForOrderBy();
  }

  /**
   * Getter di comodo che crea una copia della variabile: _notifichePag.
   * @returns ScrivaPagination copiato dalla variabile locale.
   */
  get notifichePag(): ScrivaPagination {
    // Clono e ritorno l'oggetto di paginazione notifiche
    return cloneDeep(this._notifichePag);
  }

  /**
   * Getter di comodo che verifica se esistono notifiche.
   * @returns boolean con il risultato della verifica.
   */
  get checkNotifiche(): boolean {
    // Verifico se esiste almeno una notifica
    return this.notificheApp?.length > 0;
  }
  
}
