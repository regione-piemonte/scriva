/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot
} from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { PraticheService } from './pratiche.service';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { Pratica } from '../model';
import { NotificationService } from '@shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';

@Injectable()
export class NuovaOsservazioneResolver implements Resolve<any> {
  constructor(
    private praticheService: PraticheService,
    private loadingService: LoadingService,
    private i18n: I18nService,
    private notification: NotificationService
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return this.praticheService
      .getPracticeDetail(route.parent.params.id_istanza)
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.notification.error({
            text: e.error.title,
            title: 'Errore'
          });
          this.loadingService.hide();
          return throwError(e);
        }),
        map((res: Pratica) => {
          return res || {};
        })
      );
  }
}
