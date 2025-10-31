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
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { IScrivaTablePagination } from 'src/app/shared/services/helpers/utilities/http-helper.interfaces';
import { NotificaApplicativa } from '../../models/notifica-applicativa';
import { ISearchNotificheRequest } from '../../models/search-notifiche-request';
import { NotificheHttpService } from '../notifiche-http/notifiche-http.service';
import { NotifichePagingCount } from '../notifiche-http/utilities/notifiche-http.classes';

@Injectable({ providedIn: 'root' })
export class NotificaDettaglioService {
  /**
   * Costruttore.
   */
  constructor(private _notificheHttp: NotificheHttpService) {}

  /**
   * ##############################
   * FUNZIONI CON CHIAMATE ALLE API
   * ##############################
   */

  /**
   * Funzione di ricerca della notifica.
   * @param notifica NotificaApplicativa con l'oggetto da cui recuperare l'id della notifica da scaricare.
   * @returns Observable<NotificaApplicativa> con la risposta del servizio.
   */
  private getNotifica(
    notifica: NotificaApplicativa
  ): Observable<NotificaApplicativa> {
    // Recupero l'id della notifica
    const idNA = notifica?.id_notifica_applicativa;
    // Richiamo la funzione di scarico della notifica
    return this._notificheHttp.getNotifica(idNA);
  }

  /**
   * Funzione di cancellazione di una notifica applicativa.
   * @param notifica NotificaApplicativa passata come riferimento per lo scarico.
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  notificaAppCancellata(
    notifica: NotificaApplicativa
  ): Observable<NotificaApplicativa> {
    // Lancio la funzione di cancellazione della notifica
    return this._notificheHttp.notificaAppCancellataHp(notifica).pipe(
      switchMap((notifiche: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Vado a scaricare l'aggiornamento della notifica
        return this.getNotifica(notifica);
      })
    );
  }

  /**
   * Funzione di lettura di una notifica applicativa.
   * @param notifica NotificaApplicativa passata come riferimento per lo scarico.
   * @returns Observable<RicercaPaginataResponse<NotificaApplicativa[]>> con la risposta dati paginata.
   */
  notificaAppLetta(
    notifica: NotificaApplicativa
  ): Observable<NotificaApplicativa> {
    const paginazione: IScrivaTablePagination = {
      currentPage: 1,
      elementsForPage: 1,
      sortBy: '',
      total: 0,
    };
    const ricerca:ISearchNotificheRequest = {
      id_notifica_applicativa: notifica.id_notifica_applicativa,
    };
    // Lancio la funzione di lettura della notifica
    return this._notificheHttp.notificaAppLettaBySearch(ricerca,paginazione).pipe(
      switchMap((response: NotifichePagingCount<NotificaApplicativa[]>) => {
        // Vado a scaricare l'aggiornamento della notifica
        console.log(response);
        return of(response.sources[0]);
      })
    );
  }
}
