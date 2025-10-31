/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { NotificaApplicativa } from '../models/notifica-applicativa';
import { NotificheHttpService } from '../services/notifiche-http/notifiche-http.service';
import { NotificheService } from '../services/notifiche.service';
import { MessageService } from '../../../shared/services';
import { NgxSpinnerService } from 'ngx-spinner';

/**
 * Type di comodo per definire le possibili configurazioni di risposta del resolver.
 */
type TNotificaAppDettaglioResolver =
  | Observable<NotificaApplicativa>
  | Promise<NotificaApplicativa>
  | NotificaApplicativa;

@Injectable()
export class NotificaApplicativaResolver
  implements Resolve<NotificaApplicativa>
{
  constructor(
    private _notificheHttp: NotificheHttpService,
    private _notifiche: NotificheService,
    private _spinner: NgxSpinnerService,
  ) {}

  /**
   * Resolve che recupera la notifica applicativa in base al paraemetro id del routing corrente.
   * @param route ActivatedRouteSnapshot come servizio di angualar dedicato.
   * @param state RouterStateSnapshot come servizio di angualar dedicato.
   * @returns TNotificaAppDettaglioResolver con il risultato del controllo.
   */
  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): TNotificaAppDettaglioResolver {
    // Faccio partire lo spinner di caricamento
    this._spinner.show();
    // Estraggo dai parametri di routing l'id della notifica
    const id = route?.params?.id;

    // Richiamo il servizio per il recupero della notifica
    return this._notificheHttp.getNotifica(id).pipe(
      map((notifica: NotificaApplicativa) => {
        // Nascondo lo spinner    
        this._spinner.show();
        // Ritorno l'oggetto della notifica
        return notifica || {};
        // #
      }),
      catchError((err: HttpErrorResponse) => {
        // Nascondo lo spinner
        this._spinner.show();
        // Effettuo una navigazione
        this._notifiche.navigateToNotifiche();
        // Effettuo la reject del resolver
        return of(null);
        // #
      })
    );
  }
}
