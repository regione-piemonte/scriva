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
import { Observable, of, throwError } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from '@shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { Pratiche } from '@assets/json/pratiche';

@Injectable()
export class DettaglioPraticaResolver implements Resolve<any> {
  constructor(
    private praticheService: PraticheService,
    private notification: NotificationService,
    private i18n: I18nService,
    private loadingService: LoadingService
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    const idIstanza = route.parent.params.id_istanza;

    return this.praticheService.getPracticeDetail(idIstanza).pipe(
      catchError((e: HttpErrorResponse) => {
        this.notification.error({
          text: this.i18n.translate('PRACTICE.DETAIL.ERROR.TEXT'),
          title: this.i18n.translate('PRACTICE.DETAIL.ERROR.TITLE')
        });
        this.loadingService.hide();
        return throwError(e);
      }),
      map((pratiche: any) => {
        return pratiche[0];
      })
    );
  }
}
