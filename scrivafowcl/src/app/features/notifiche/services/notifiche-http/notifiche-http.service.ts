/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { cloneDeep } from 'lodash';
import * as moment from 'moment';
import { BehaviorSubject, Observable, Subscription, timer } from 'rxjs';
import { map, switchMap, tap } from 'rxjs/operators';
import { AppConfigService } from 'src/app/shared/services/app-config.service';
import { ScrivaSortTypes } from 'src/app/shared/services/helpers/utilities/http-helper.enums';
import { IScrivaTablePagination } from 'src/app/shared/services/helpers/utilities/http-helper.interfaces';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { DynamicObjAny } from '../../../../core/interfaces/scriva.interfaces';
import { MessageService } from '../../../../shared/services';
import { HttpHelperService } from '../../../../shared/services/helpers/http-helper.service';
import {
  RicercaPaginataResponse,
  ScrivaPagination,
} from '../../../../shared/services/helpers/utilities/http-helper.classes';
import { LoggerService } from '../../../../shared/services/logger.service';
import { ScrivaUtilitiesService } from '../../../../shared/services/scriva-utilities/scriva-utilities.service';
import { ILoadNotificheCount } from '../../models/load-notifiche-count';
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { ISearchNotificheRequest } from '../../models/search-notifiche-request';
import { NotificheConfigurazioniService } from '../notifiche-configurazioni.service';
import { NotifichePagingCount } from './utilities/notifiche-http.classes';

@Injectable({ providedIn: 'root' })
export class NotificheHttpService {
  /** String costante che rappresenta il nome dell'header ritornato per il recupero del dato riferito ai contatori delle notifiche. */
  private HEADER_COUNT_NOTIFICHE = 'CountNotifiche';
  /** String di configurazione per il path: /notifiche. */
  private PATH_NOTIFICHE = '/notifiche';
  /** String di configurazione per il path: /_search. */
  private PATH_SEARCH = '/_search';

  /** BehaviorSubject per notifiche applicative  */
  private notificaApp$ = new BehaviorSubject<NotificaApplicativa[]>(null);
  /** BehaviorSubject per i contatori delle notifiche  */
  private loadNotificheCount$ = new BehaviorSubject<ILoadNotificheCount>(null);
  /** Subscription per il recupero delle notifiche temporizzato */
  private notificationTimer$: Subscription;

  /** number che definisce i millisecondi per le notifiche temporizzate. */
  private scrivaNotifyTime: number;

  constructor(
    private _appConfig: AppConfigService,
    private _logger: LoggerService,
    private _http: HttpClient,
    private _httpHelper: HttpHelperService,
    private _message: MessageService,
    private _notificheConfig: NotificheConfigurazioniService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {}

  /**
   * #######################################################
   * FUNZIONI COLLEGATE AI SERVIZI PER LA GESTIONE NOTIFICHE
   * #######################################################
   */

  /**
   * Funzione che scarica le informazioni di una singola notifica, dato l'id notifica applicativa.
   * @param idNotifica number che definisce l'id della notifica da scaricare.
   * @returns Observable<NotificaApplicativa> con l'oggetto scaricato. Altrimenti undefined.
   */
  getNotifica(idNotifica: number): Observable<NotificaApplicativa> {
    // Costruisco l'url di richiesta
    const url: string = this.pathNotifiche + '/' + idNotifica;
    // Richiamo il servizio per lo scarico dati
    return this._http.get<NotificaApplicativa[]>(url).pipe(
      map((notifiche: NotificaApplicativa[]) => {
        // Verifico se esiste l'array di ritorno ed estraggo il primo valore
        return notifiche.length > 0 ? notifiche[0] : undefined;
      })
    );
  }

  /**
   * Funzione che scarica le notifiche applicative, con paginazione ed escludendo le notifiche cancellate logicamente.
   * La cancellazione logica è definita dalla proprietà della notifica: "data_cancellazione" valorizzata.
   * @param ricerca ISearchNotificheRequest che definisce i parametri di ricerca per le notifiche.
   * @param paginazione ScrivaPagination che definisce la paginazione per i risultati.
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  getNotifichePaginated(
    ricerca: ISearchNotificheRequest,
    paginazione?: ScrivaPagination
  ): Observable<RicercaPaginataResponse<NotificaApplicativa[]>> {
    // Costruisco l'url di richiesta
    const url: string = this.pathNotificheSearch;
    // Prendo i parametri
    const params = this._httpHelper.createPagingParams(paginazione);
    // Definisco il body come ricerca
    const body = ricerca ?? {};
    // Definisco le options
    const options = { params };

    // Lancio la chiamata per il recupero dati
    return this._httpHelper.searchWithPost<NotificaApplicativa[]>(
      url,
      body,
      options
    );
  }

  /**
   * Funzione di recupero dei dati delle notifiche paginati e con l'oggetto di contatore dei tipi di notifiche.
   * @returns Observable<NotifichePagingCount> con la risposta paginata e con i contatori delle notifiche.
   */
  getNotificheWithCounters(): Observable<
    NotifichePagingCount<NotificaApplicativa[]>
  > {
    // Costruisco l'url di richiesta
    const url: string = this.pathNotifiche;
    // Cotruisco la paginazione con il numero di notifiche preso da configurazione
    const paginazione: IScrivaTablePagination = {
      currentPage: 1,
      elementsForPage: this._notificheConfig.getScrivaNotifyNumNotificheHome(),
      sortBy: '',
      sortDirection: ScrivaSortTypes.nessuno,
      total: 0,
    };
    // Prendo i parametri
    const params = this._httpHelper.createPagingParams(paginazione);
    // Recupero la configurazione per ottenere i dati dell'header
    const observe = this._httpHelper.observeResponse;
    // Definisco le informazioni per le options
    const options: DynamicObjAny = { params, ...observe };

    // Lancio la chiamata per il recupero dati
    return this._http
      .get<HttpResponse<NotificaApplicativa[]>>(url, options)
      .pipe(
        map((response: HttpResponse<NotificaApplicativa[]>) => {
          // Estraggo e ritorno le informazioni dalla response
          return this.paginationWithCounter(response);
        })
      );
  }

  /**
   * Funzione di recupero dei dati delle notifiche paginati e con l'oggetto di contatore dei tipi di notifiche.
   * @param ricerca ISearchNotificheRequest che definisce i parametri di ricerca per le notifiche.
   * @param paginazione ScrivaPagination che definisce la paginazione per i risultati.
   * @returns Observable<NotifichePagingCount> con la risposta paginata e con i contatori delle notifiche.
   */
  postNotificheWithCounters(
    ricerca?: ISearchNotificheRequest,
    paginazione?: ScrivaPagination
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Costruisco l'url di richiesta
    const url: string = this.pathNotificheSearch;
    // Prendo i parametri
    const params = this._httpHelper.createPagingParams(paginazione);
    // Definisco il body come ricerca
    const body = ricerca ?? {};
    // Recupero la configurazione per ottenere i dati dell'header
    const observe = this._httpHelper.observeResponse;

    // Definisco le informazioni per le options
    const options: DynamicObjAny = { params, ...observe };

    // Lancio la chiamata per il recupero dati
    return this._http
      .post<HttpResponse<NotificaApplicativa[]>>(url, body, options)
      .pipe(
        map((response: HttpResponse<NotificaApplicativa[]>) => {
          // Estraggo e ritorno le informazioni dalla response
          return this.paginationWithCounter(response);
        })
      );
  }

  /**
   * Funzione di aggiornamento per le notifiche applicative.
   * @param notifiche NotificaApplicativa[] come lista di notifiche d'aggiornare.
   * @returns Observable<NotificaApplicativa[]> con la lista di notifiche recuperate dalla chiamata.
   */
  updateNotificheWithCounters(
    notifiche?: NotificaApplicativa[]
  ): Observable<NotificaApplicativa[]> {
    // Costruisco l'url di richiesta
    const url: string = this.pathNotifiche;
    // Per effettuare la chiamata al server, bisogna passare un array di notifiche
    const body = notifiche;

    // Lancio la chiamata per il recupero dati
    return this._http.put<NotificaApplicativa[]>(url, body);
  }

  /**
   * Funzione di aggiornamento per le notifiche applicative.
   * @param notifiche NotificaApplicativa[] come lista di notifiche d'aggiornare.
   * @returns Observable<NotificaApplicativa[]> con la lista di notifiche recuperate dalla chiamata.
   */
  updateNotificaWithCounters(
    notifica: NotificaApplicativa
  ): Observable<NotificaApplicativa[]> {
    // Per effettuare la chiamata al server, bisogna passare un array di notifiche
    const body = [notifica];
    // Lancio la chiamata per il recupero dati
    return this.updateNotificheWithCounters(body);
  }

  /**
   * Funzione di aggiornamento per le notifiche applicative.
   * @param ricerca ISearchNotificheRequest che definisce i parametri di ricerca per le notifiche.
   * @param paginazione ScrivaPagination che definisce la paginazione per i risultati.
   * @returns Observable<NotificaApplicativa[]> con la lista di notifiche recuperate dalla chiamata.
   */
  segnaNotificheComeLette(
    ricerca?: ISearchNotificheRequest,
    paginazione?: ScrivaPagination
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Costruisco l'url di richiesta
    const url: string = this.pathNotificheSearch;
    // Prendo i parametri
    const params = this._httpHelper.createPagingParams(paginazione);
    // Definisco il body come ricerca
    const body = ricerca ?? {};
    // Recupero la configurazione per ottenere i dati dell'header
    const observe = this._httpHelper.observeResponse;
    // Definisco le options
    const options: DynamicObjAny = { params, ...observe };

    // Lancio la chiamata per il recupero dati
    return this._http
      .put<HttpResponse<NotificaApplicativa[]>>(url, body, options)
      .pipe(
        tap({
          next: (res) => {
            this.refreshTimerGetNotifiche();
            this.getNotificheWithCounters().subscribe({
              next: (response: NotifichePagingCount<NotificaApplicativa[]>) => {
                // Sono stati scaricate le informazioni, aggiorno i dati tramite ascoltatori
                this.updateNotificheListener(response);
                // #
              },
            });
          },
        }),
        map((response: HttpResponse<NotificaApplicativa[]>) => {
          // Estraggo e ritorno le informazioni dalla response
          return this.paginationWithCounter(response);
        })
      );
  }

  /**
   * Funzione che marca come "cancellata" una lista di notifiche.
   * @param notifica NotificaApplicativa contenente i dati della notifica da contrassegnare come cancellata.
   * @returns Observable<NotificaApplicativa[]> con il risultato dell'operazione.
   */
  private segnaNotificaComeCancellata(
    notifica: NotificaApplicativa
  ): Observable<NotificaApplicativa[]> {
    // Dichiaro la data di cancellazione per la notifica
    const now = moment();
    // Converto la data in formato server
    const nowServer = this._scrivaUtilities.convertMomentToServerDate(now);
    // Definisco i dati per la cancellazione della notifica
    const notificaDel: NotificaApplicativa = cloneDeep(notifica);
    // Per definire una notifica come cancellata, definisco la data cancellazione
    notificaDel.data_cancellazione = nowServer;

    // Lancio la chiamata per il recupero dati
    return this.updateNotificaWithCounters(notificaDel);
  }

  /**
   * Funzione che marca come "letta" una lista di notifiche.
   * @param notifica NotificaApplicativa contenente i dati della notifica da contrassegnare come letta.
   * @returns Observable<NotificaApplicativa[]> con il risultato dell'operazione.
   */
  private segnaNotificaComeLetta(
    notifica: NotificaApplicativa
  ): Observable<NotificaApplicativa[]> {
    // Dichiaro la data di lettura per la notifica
    const now = moment();
    // Converto la data in formato server
    const nowServer = this._scrivaUtilities.convertMomentToServerDate(now);
    // Definisco i dati per la cancellazione della notifica
    const notificaUpd: NotificaApplicativa = cloneDeep(notifica);
    // Per definire una notifica come letta, definisco la data lettura
    notificaUpd.data_lettura = nowServer;

    // Lancio la chiamata per il recupero dati
    return this.updateNotificaWithCounters(notificaUpd);
  }

  /**
   * ########################################################################################
   * FUNZIONI DI COMODO PER LA GESTIONE DELLE NOTIFICHE E GLI EVENTI SU NOTIFICHE E CONTATORI
   * ########################################################################################
   */

  /**
   * Resituisco Observable con i contatori delle notifiche applicative
   * @returns Observable<LoadNotificheCount>
   */
  getLoadNotificheCount(): Observable<ILoadNotificheCount> {
    // Restistuisco il behevior subject
    return this.loadNotificheCount$.asObservable();
  }

  /**
   * Resituisco Observable con i contatori delle notifiche applicative
   * @returns Observable<NotificaApplicativa>
   */
  getNotificheApp(): Observable<NotificaApplicativa[]> {
    // Restistuisco il behevior subject
    return this.notificaApp$.asObservable();
  }

  /**
   * Funzione che va a marcare una notifica come "cancellata".
   * La funzione poi aggiornerà gli event emitter dell'applicazione per notificare l'avvenuta modifica.
   * @version 20/08/2023 La cancellazione di una notifica prevede il richiamo dell'update notifiche passando esclusivamente un singolo oggetto nell'array.
   * @param notifica NotificaApplicativa contenente i dati della notifica da contrassegnare.
   * @param ricerca ISearchNotificheRequest che definisce i parametri di ricerca per le notifiche.
   * @param paginazione ScrivaPagination che definisce la paginazione per i risultati.
   * @returns Observable<NotifichePagingCount<NotificaApplicativa[]>> con il dato della notifica cancellata.
   */
  notificaAppCancellata(
    notifica: NotificaApplicativa,
    ricerca: ISearchNotificheRequest,
    paginazione: ScrivaPagination
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Richiamo il servizio per cancellare logicamente una notifica
    return this.segnaNotificaComeCancellata(notifica).pipe(
      // Aggiornati i dati, riscarico le informazioni paginate per le notifiche
      switchMap((response: NotificaApplicativa[]) => {
        // Estraggo e ritorno le informazioni dalla response
        return this.postNotificheWithCounters(ricerca, paginazione);
      }),
      tap((notificheRes: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Gestisco in maniera globale l'aggiornamento delle notifiche
        this.refreshTimerGetNotifiche();
        this.getNotificheWithCounters().subscribe({
          next: (response: NotifichePagingCount<NotificaApplicativa[]>) => {
            // Sono stati scaricate le informazioni, aggiorno i dati tramite ascoltatori
            this.updateNotificheListener(response);
            // #
          },
        });
      })
    );
  }

  /**
   * Funzione che va a marcare una notifica come "letta".
   * La funzione poi aggiornerà gli event emitter dell'applicazione per notificare l'avvenuta modifica.
   * @version 20/08/2023 La lettura di una notifica prevede il richiamo dell'update notifiche passando esclusivamente un singolo oggetto nell'array.
   * @param notifica NotificaApplicativa contenente i dati della notifica da contrassegnare.
   * @param ricerca ISearchNotificheRequest che definisce i parametri di ricerca per le notifiche.
   * @param paginazione ScrivaPagination che definisce la paginazione per i risultati.
   * @returns Observable<NotifichePagingCount<NotificaApplicativa[]>> con il dato della notifica letta.
   */
  notificaAppLetta(
    notifica: NotificaApplicativa,
    ricerca: ISearchNotificheRequest,
    paginazione: ScrivaPagination
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Richiamo il servizio per aggiornare una notifica come letta
    return this.segnaNotificaComeLetta(notifica).pipe(
      // Aggiornati i dati, riscarico le informazioni paginate per le notifiche
      switchMap((response: NotificaApplicativa[]) => {
        // Estraggo e ritorno le informazioni dalla response
        return this.postNotificheWithCounters(ricerca, paginazione);
      }),
      tap((notificheRes: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Gestisco in maniera globale l'aggiornamento delle notifiche
        this.refreshTimerGetNotifiche();
        this.getNotificheWithCounters().subscribe({
          next: (response: NotifichePagingCount<NotificaApplicativa[]>) => {
            // Sono stati scaricate le informazioni, aggiorno i dati tramite ascoltatori
            this.updateNotificheListener(response);
            // #
          },
        });
        // #
      })
    );
  }

  /**
   * Funzione che va a marcare una notifica come "cancellata" per la hp
   * La funzione poi aggiornerà gli event emitter dell'applicazione per notificare l'avvenuta modifica.
   * @param notifica NotificaApplicativa contenente i dati della notifica da contrassegnare.
   * @returns Observable<NotifichePagingCount<NotificaApplicativa[]>> con il dato della notifica cancellata.
   */
  notificaAppCancellataHp(
    notifica: NotificaApplicativa
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Richiamo il servizio per cancellare logicamente una notifica
    // Costruisco l'url di richiesta
    const url: string = this.pathNotifiche;

    const paginazione: IScrivaTablePagination = {
      currentPage: 1,
      elementsForPage: this._notificheConfig.getScrivaNotifyNumNotificheHome(),
      sortBy: '',
      sortDirection: ScrivaSortTypes.nessuno,
      total: 0,
    };

    // Prendo i parametri
    const params = this._httpHelper.createPagingParams(paginazione);
    // Recupero la configurazione per ottenere i dati dell'header
    const observe = this._httpHelper.observeResponse;
    // Definisco le informazioni per le options
    const options: DynamicObjAny = { params, ...observe };

    // Dichiaro la data di cancellazione per la notifica
    const now = moment();
    // Converto la data in formato server
    const nowServer = this._scrivaUtilities.convertMomentToServerDate(now);
    // Definisco i dati per la cancellazione della notifica
    const notificaDel: NotificaApplicativa = cloneDeep(notifica);
    // Per definire una notifica come cancellata, definisco la data cancellazione
    notificaDel.data_cancellazione = nowServer;
    // Per effettuare la chiamata al server, bisogna passare un array di notifiche
    const body = [notificaDel];

    // Lancio la chiamata per il recupero dati
    return this._http
      .put<HttpResponse<NotificaApplicativa[]>>(url, body, options)
      .pipe(
        map((response: HttpResponse<NotificaApplicativa[]>) => {
          // Estraggo e ritorno le informazioni dalla response
          return this.paginationWithCounter(response);
        }),
        tap((notificheRes: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Gestisco in maniera globale l'aggiornamento delle notifiche
          return this.aggiornaNotificheApplicazione(notificheRes);
          // #
        })
      );
  }

  /**
   * Funzione che va a marcare una notifica come "letta" per la hp
   * La funzione poi aggiornerà gli event emitter dell'applicazione per notificare l'avvenuta modifica.
   * @version 20/08/2023 La lettura di una notifica prevede il richiamo dell'update notifiche passando esclusivamente un singolo oggetto nell'array.
   * @param notifica NotificaApplicativa contenente i dati della notifica da contrassegnare.
   * @returns Observable<NotifichePagingCount<NotificaApplicativa[]>> con il dato della notifica letta.
   */
  notificaAppLettaHp(
    notifica: NotificaApplicativa
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Richiamo il servizio per cancellare logicamente una notifica
    // Costruisco l'url di richiesta
    const url: string = this.pathNotifiche;
    const paginazione: IScrivaTablePagination = {
      currentPage: 1,
      elementsForPage: this._notificheConfig.getScrivaNotifyNumNotificheHome(),
      sortBy: '',
      sortDirection: ScrivaSortTypes.nessuno,
      total: 0,
    };
    // Prendo i parametri
    const params = this._httpHelper.createPagingParams(paginazione);
    // Recupero la configurazione per ottenere i dati dell'header
    const observe = this._httpHelper.observeResponse;
    // Definisco le informazioni per le options
    const options: DynamicObjAny = { params, ...observe };

    // Dichiaro la data di cancellazione per la notifica
    const now = moment();
    // Converto la data in formato server
    const nowServer = this._scrivaUtilities.convertMomentToServerDate(now);
    // Definisco i dati per la cancellazione della notifica
    const notificaLet: NotificaApplicativa = cloneDeep(notifica);
    // Per definire una notifica come cancellata, definisco la data cancellazione
    notificaLet.data_lettura = nowServer;
    // Per effettuare la chiamata al server, bisogna passare un array di notifiche
    const body = [notificaLet];

    // Lancio la chiamata per il recupero dati
    return this._http
      .put<HttpResponse<NotificaApplicativa[]>>(url, body, options)
      .pipe(
        map((response: HttpResponse<NotificaApplicativa[]>) => {
          // Estraggo e ritorno le informazioni dalla response
          return this.paginationWithCounter(response);
        }),
        tap((notificheRes: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Gestisco in maniera globale l'aggiornamento delle notifiche
          return this.aggiornaNotificheApplicazione(notificheRes);
          // #
        })
      );
  }

  /**
   * Funzione che va a marcare una notifica come "letta" per la hp
   * La funzione poi aggiornerà gli event emitter dell'applicazione per notificare l'avvenuta modifica.
   * @version 20/08/2023 La lettura di una notifica prevede il richiamo dell'update notifiche passando esclusivamente un singolo oggetto nell'array.
   * @param notifica NotificaApplicativa contenente i dati della notifica da contrassegnare.
   * @returns Observable<NotifichePagingCount<NotificaApplicativa[]>> con il dato della notifica letta.
   */
  notificaAppLettaBySearch(
    ricerca?: ISearchNotificheRequest,
    paginazione?: ScrivaPagination
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Richiamo il servizio per segnare come letta in base ai filtri
    // Costruisco l'url di richiesta
    const url: string = this.pathNotificheSearch;
    // Prendo i parametri
    const params = this._httpHelper.createPagingParams(paginazione);
    // Recupero la configurazione per ottenere i dati dell'header
    const observe = this._httpHelper.observeResponse;
    // Definisco le informazioni per le options
    const options: DynamicObjAny = { params, ...observe };

    // Per effettuare la chiamata al server, bisogna passare un array di notifiche
    const body = ricerca;

    // Lancio la chiamata per il recupero dati
    return this._http
      .put<HttpResponse<NotificaApplicativa[]>>(url, body, options)
      .pipe(
        tap({
          next: (res) => {
            this.refreshTimerGetNotifiche();
            this.getNotificheWithCounters().subscribe({
              next: (response: NotifichePagingCount<NotificaApplicativa[]>) => {
                // Sono stati scaricate le informazioni, aggiorno i dati tramite ascoltatori
                this.updateNotificheListener(response);
                // #
              },
            });
          },
        }),
        map((response: HttpResponse<NotificaApplicativa[]>) => {
          // Estraggo e ritorno le informazioni dalla response
          return this.paginationWithCounter(response);
        })
      );
  }

  /**
   * Funzione che marca come "lette" tutte le notifiche che sono rappresentate dai filtri di ricerca.
   * La funzione NON andrà ad emettere l'aggiornamento di notifiche e contatori delle notifiche.
   * @param ricerca ISearchNotificheRequest che definisce i parametri di ricerca per le notifiche.
   * @param paginazione ScrivaPagination che definisce la paginazione per i risultati.
   * @returns Observable<NotifichePagingCount<NotificaApplicativa[]>> con i dati aggiornati.
   */
  notificheAppFiltrateLette(
    ricerca?: ISearchNotificheRequest,
    paginazione?: ScrivaPagination
  ): Observable<NotifichePagingCount<NotificaApplicativa[]>> {
    // Il filtro esiste, richiamo la funzione apposita
    return this.updateNotificheWithCounters(undefined).pipe(
      switchMap((response: NotificaApplicativa[]) => {
        // Estraggo e ritorno le informazioni dalla response
        return this.postNotificheWithCounters(ricerca, paginazione);
      }),
      tap((notificheRes: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Gestisco in maniera globale l'aggiornamento delle notifiche
        return this.aggiornaNotificheApplicazione(
          notificheRes,
          ricerca,
          paginazione
        );
        // #
      })
    );
  }

  /**
   * #####################################################
   * FUNZIONI DI UTILITY PER LA GESTIONE DATI DEL SERVIZIO
   * #####################################################
   */

  /**
   * Funzione che effettua scarico e aggiornamento delle notifiche applicative a livello "globale".
   * @param notificheRes NotifichePagingCount<NotificaApplicativa[]> con il possibile risultato di scarico delle informazioni.
   * @param ricerca ISearchNotificheRequest che definisce i parametri di ricerca per le notifiche.
   * @param paginazione ScrivaPagination che definisce la paginazione per i risultati.
   */
  private aggiornaNotificheApplicazione(
    notificheRes?: NotifichePagingCount<NotificaApplicativa[]>,
    ricerca?: ISearchNotificheRequest,
    paginazione?: ScrivaPagination
  ) {
    // Verifico se NON esiste una risposta pronta, o se esiste che questa risposta non sia stata filtrata (ricerca) o paginata (paginazione)
    if (!notificheRes && (ricerca || paginazione)) {
      // Potrebbero esserci stati filtri sulle notifiche, effettuiamo una chiamata pulita senza filtri e con paginazione 1
      this.postNotificheWithCounters().subscribe({
        next: (response: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Lancio l'aggiornamento delle informazioni per le notifiche applicative
          this.updateNotificheListener(response);
          // Rilancio il servizio di polling notifiche
          this.refreshTimerGetNotifiche();
        },
      });
      // #
    } else {
      // La risposta ottenuta non è stata filtrata in maniera custom, posso aggiornare direttamente i listener
      this.updateNotificheListener(notificheRes);
      // Rilancio il servizio di polling notifiche
      this.refreshTimerGetNotifiche();
    }
  }

  /**
   * Funzione di comodo che gestisce la response delle chiamata per lo scarico delle notifiche.
   * La funzione recupererà i dati per le notifiche, la paginazione e i contatori specifici delle notifiche.
   * @param response HttpResponse<NotificaApplicativa[]> con le informazioni scaricate dal server.
   * @returns NotifichePagingCount<NotificaApplicativa[]> con il risultato della ricerca.
   */
  private paginationWithCounter(
    response: HttpResponse<NotificaApplicativa[]>
  ): NotifichePagingCount<NotificaApplicativa[]> {
    // Estraggo dall'header le configurazioni di paginazione standard
    const pagination: RicercaPaginataResponse<NotificaApplicativa[]> =
      this._httpHelper.ricercaPaginataResponse<NotificaApplicativa[]>(response);

    // Estraggo le informazioni sui contatori delle notifiche
    let infoCount: ILoadNotificheCount;
    infoCount = this._httpHelper.getObjectFromHeader(
      this.HEADER_COUNT_NOTIFICHE,
      response
    );

    // Creo un nuovo oggetto che contiene la risposta paginata e i contatori delle notifiche
    const result = new NotifichePagingCount<NotificaApplicativa[]>({
      sources: pagination.sources,
      paging: pagination.paging,
      contatori: infoCount,
    });

    // Al termine ritorno l'oggetto completo
    return result;
  }

  /**
   * Funzione di comodo che estrae e aggiorna le informazioni all'interno del servizio.
   * L'aggiornamento interno prevede l'attiviazione dei subscriber collegati a notifiche e contatori notifiche.
   * @param notificheResponse NotifichePagingCount<NotificaApplicativa[]> contenente la risposta dal server.
   */
  updateNotificheListener(
    notificheResponse: NotifichePagingCount<NotificaApplicativa[]>
  ) {
    // Estraggo dalla response le informazioni per le notifiche e per i contatori
    const notifiche = notificheResponse?.sources ?? [];
    const contatori = notificheResponse?.contatori;

    // Aggiorno gli eventi che notificano di notifiche e contatori
    this.updateNotificaApplicative(notifiche);
    this.updateLoadNotificheCount(contatori);
  }

  /**
   * Funzione di supporto che effettua il next per il valore del BehaviorSubject registrato.
   * @param notifiche NotificaApplicativa[] che definisce la nuova lista di notifiche da mantenere.
   */
  private updateNotificaApplicative(notifiche: NotificaApplicativa[]) {
    // Lancio il next dell'observable
    this.notificaApp$.next(notifiche);
  }

  /**
   * Funzione di supporto che effettua il next per il valore del BehaviorSubject registrato.
   * @param notificheCount ILoadNotificheCount che definisce la nuova lista di notifiche da mantenere.
   */
  private updateLoadNotificheCount(notificheCount: ILoadNotificheCount) {
    // Lancio il next dell'observable
    this.loadNotificheCount$.next(notificheCount);
  }

  /**
   * #############################################################
   * FUNZIONI DI GESTIONE DEL POLLING PER LE NOTIFICHE APPLICATIVE
   * #############################################################
   */

  /**
   * Fuzione che inizializza il Timer per il recupero delle notifiche in automatico.
   */
  initTimerGetNotifiche() {
    // Recupero i millisecondi per la temporizzazione
    this.scrivaNotifyTime = this._notificheConfig.getScrivaNotifyTime();
    // Chiamo il metodo che starta le notifiche
    this.startTimerGetNotifiche();
  }

  /**
   * Funzione che effettua lo start del Timer per il recupero delle notifiche per notifiche per home page e sidebar in automatico dal BE.
   */
  private startTimerGetNotifiche() {
    // Sottoscrivo un ascoltatore al polling notifiche e gestisco la propagazione delle modifiche
    this.notificationTimer$ = timer(
      this.scrivaNotifyTime,
      this.scrivaNotifyTime
    )
      .pipe(
        switchMap((counter: number) => {
          // Ad ogni chiamata lancio lo scarico delle informazioni con i contatori di testata
          return this.getNotificheWithCounters();
          // #
        })
      )
      .subscribe({
        next: (response: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Sono stati scaricate le informazioni, aggiorno i dati tramite ascoltatori
          this.updateNotificheListener(response);
          // #
        },
        error: (e: ScrivaServerError) => {
          // Definisco le informazioni per visualizzare l'alert box nell'app componente
          const target = 'errorBox';
          // Si è verificato un errore, gestisco la segnalazione utente
          const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
          const autoFade = true;
          // Visualizzo il messaggio
          this._message.showMessage(code, target, autoFade);
        },
      });
  }

  /**
   * Funzione che interrompe il timer del polling.
   * Una volta interrotto lo riaggancia facendo partire da zero il timer.
   */
  private refreshTimerGetNotifiche() {
    // Interrompo il timer per le notifiche
    this.notificationTimer$.unsubscribe();
    // Faccio ripartire il polling per le notifiche
    this.startTimerGetNotifiche();
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter di comodo per l'url delle API per: notifiche.
   * @returns string con l'url di puntamento alle API.
   */
  get pathNotifiche(): string {
    // Richiamo il servizio passando i path
    return this._appConfig.appUrl(this.PATH_NOTIFICHE);
  }

  /**
   * Getter di comodo per l'url delle API per: ricerca notifiche.
   * @returns string con l'url di puntamento alle API.
   */
  get pathNotificheSearch(): string {
    // Richiamo il servizio passando i path
    return this._appConfig.appUrl(this.PATH_NOTIFICHE, this.PATH_SEARCH);
  }
}
