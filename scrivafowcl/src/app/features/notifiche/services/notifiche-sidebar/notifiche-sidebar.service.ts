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
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import {
  RicercaPaginataResponse,
  ScrivaPagination,
} from '../../../../shared/services/helpers/utilities/http-helper.classes';
import { ScrivaSortTypes } from '../../../../shared/services/helpers/utilities/http-helper.enums';
import { CodStatiNotifiche } from '../../enums/notifiche.enums';
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { ISearchNotificheRequest } from '../../models/search-notifiche-request';
import { NotificheConfigurazioniService } from '../notifiche-configurazioni.service';
import { NotificheHttpService } from '../notifiche-http/notifiche-http.service';
import { NotifichePagingCount } from '../notifiche-http/utilities/notifiche-http.classes';

@Injectable({ providedIn: 'root' })
export class NotificheSidebarService {
  /**
   * Costruttore.
   */
  constructor(
    private _notificheConfig: NotificheConfigurazioniService,
    private _notificheHttp: NotificheHttpService
  ) {}

  /**
   * ##############################
   * FUNZIONI CON CHIAMATE ALLE API
   * ##############################
   */

  /**
   * Funzione specifica di ricerca delle notifiche.
   * Questa funzione è dedicata alla ricerca delle notifiche (senza filtri o paginazioni particolari), ma che non contemplino lo scarico delle notifiche da leggere.
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  cercaNotificheSidebar(): Observable<
    RicercaPaginataResponse<NotificaApplicativa[]>
  > {
    // I filtri iniziali sono undefined
    const filtri: ISearchNotificheRequest = this.sidebarFilters;
    // La paginazione è quella iniziale
    const paginazione: ScrivaPagination = this.sidebarPagination;

    // Richiamo lo scarico delle notifiche con la specifica configurazione
    return this.cercaNotifiche(filtri, paginazione);
  }

  /**
   * Funzione di ricerca delle notifiche.
   * A seguito della ricerca, verranno aggiornate le notifiche e l'eventuale paginazione da mostrare all'utente.
   * @param filtri ISearchNotificheRequest con l'oggetto di filtro d'applicare alla ricerca.
   * @param paginazione ScrivaPagination che definisce i filtri per la paginazione.
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  cercaNotifiche(
    filtri: ISearchNotificheRequest,
    paginazione: ScrivaPagination
  ): Observable<RicercaPaginataResponse<NotificaApplicativa[]>> {
    // Richiamo la funzione di scarico delle notifiche
    return this._notificheHttp.getNotifichePaginated(filtri, paginazione);
  }

  /**
   * Funzione collegata al template del componente.
   * La funzione segnarà tutte le notifiche ATTUALMENTE in pagina come "lette".
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  segnaTutteNotificheLette(): Observable<
    RicercaPaginataResponse<NotificaApplicativa[]>
  > {
    // Recupero i filtri di default
    let search: ISearchNotificheRequest;
    search = this.sidebarFilters;
    // Recupero la paginazione di default
    let pagination: ScrivaPagination;
    pagination = this.sidebarPagination;

    // Lancio la funzione di lettura per tutte le notifiche
    return this._notificheHttp.segnaNotificheComeLette(search, pagination);
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione andrà a contrassegnare la notifica come "cancellata" ed aggiornerà i dati.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  notificaAppCancellata(
    notificaCard: NotificaApplicativa
  ): Observable<RicercaPaginataResponse<NotificaApplicativa[]>> {
    // Lancio la funzione di cancellazione della notifica
    return this._notificheHttp.notificaAppCancellataHp(notificaCard).pipe(
      switchMap((lette: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Vado a scaricare le notiifiche non lette
        return this.cercaNotificheSidebar();
      })
    );
  }

  /**
   * Funzione collegata al template ed al componente di una card.
   * La funzione andrà a contrassegnare la notifica come "letta" ed aggiornerà i dati.
   * @param notificaCard NotificaApplicativa passata come riferimento per l'evento.
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  notificaAppLetta(
    notificaCard: NotificaApplicativa
  ): Observable<RicercaPaginataResponse<NotificaApplicativa[]>> {
    // Lancio la funzione di lettura della notifica
    return this._notificheHttp
      .notificaAppLettaHp(notificaCard)
      .pipe(
        switchMap((lette: NotifichePagingCount<NotificaApplicativa[]>) => {
          // Vado a scaricare le notiifiche non lette
          return this.cercaNotificheSidebar();
        })
      );
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter che genera l'oggetto fisso per il filtro della sidebar.
   * @returns ISearchNotificheRequest con la configurazione da ritornare.
   */
  get sidebarFilters(): ISearchNotificheRequest {
    // Ritorno un oggetto costante per i filtri della sidebar
    const filtri: ISearchNotificheRequest = {
      cod_stato_notifica: CodStatiNotifiche.nonCancellate,
    };

    // Ritorno l'oggetto di filtro
    return filtri;
  }

  /**
   * Getter che genera l'oggetto fisso per la paginazione della sidebar.
   * @returns ScrivaPagination con la configurazione da ritornare.
   */
  get sidebarPagination(): ScrivaPagination {
    // Ritorno un oggetto costante per la paginazione della sidebar
    const paginazione: ScrivaPagination = {
      currentPage: 1,
      elementsForPage: 20,
      maxPages: 10,
      sortBy: this.orderDefault,
      sortDirection: ScrivaSortTypes.decrescente,
    };

    // Ritorno l'oggetto di paginazione
    return paginazione;
  }

  /**
   * Getter di comodo per il campo di ordinamento di default.
   * @returns string con il campo per l'ordinamento di default.
   */
  get orderDefault() {
    // Richiamo il servizio contenente il default
    return this._notificheConfig.getScrivaNotifyFieldForOrderBy();
  }
}
