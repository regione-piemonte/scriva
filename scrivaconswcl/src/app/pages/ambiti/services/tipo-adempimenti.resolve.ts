/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError, map } from 'rxjs/operators';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from '@shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';
import { AmbitiService } from '@pages/ambiti/services/ambiti.service';
import { ValueMapperService } from '@core/services';

@Injectable()
export class TipoAndempimentiResolver implements Resolve<any> {
  constructor(
    private valueMapperService: ValueMapperService,
    private ambitiService: AmbitiService,
    private notificationService: NotificationService,
    private i18n: I18nService,
    private router: Router
  ) {
  }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> | Promise<any> | any {
    return this.ambitiService.getTipoAdempimenti(route.params.id_ambito).pipe(
      map((res: any) => {
        return res || {};
      }),
      catchError((err: HttpErrorResponse) => {
        if (err.status === 400 || err.status === 404 || err.status === 500) {
          this.notificationService.success({
            title: this.i18n.translate('AMBITI.DETAIL.NOTIFICATION.ERROR.TITLE'),
            text: this.i18n.translate('AMBITI.DETAIL.NOTIFICATION.ERROR.TEXT')
          });
          this.router.navigate(['/home']);
        }
        return of(null);
      })
    );
  }
}
