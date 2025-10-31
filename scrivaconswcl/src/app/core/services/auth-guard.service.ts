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
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { take, tap } from 'rxjs/operators';
import { NbAccessChecker } from '@nebular/security';
import { Observable } from 'rxjs';
import { NotificationService } from '@app/shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';
import { environment } from '@env/environment';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(
    private accessChecker: NbAccessChecker,
    private notificationService: NotificationService,
    private router: Router,
    private i18n: I18nService
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this._checkActivation(route, state);
  }

  canActivateChild(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    return this._checkActivation(route, state);
  }

  private _checkActivation(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> {
    let permission = 'view';
    let resource: string = state.url.split('/')[1];

    if (route.data.permission !== undefined) {
      permission = route.data.permission;
    }
    if (route.data.resource !== undefined) {
      resource = route.data.resource;
    }

    if (!resource) {
      throw new Error('AuthGuard: resource not found');
    }
    return this.accessChecker.isGranted(permission, resource).pipe(
      tap((authorized) => {
        if (!authorized) {
          this.notificationService.warning({
            title: this.i18n.translate('NOT_AUTHORIZED.NOTIFICATION.TITLE'),
            text: this.i18n.translate('NOT_AUTHORIZED.NOTIFICATION.TEXT')
          });
          localStorage.setItem('redirectUrl', state.url);
          window.location = `${environment.backend.baseUrl}/auth` as (
            | string
            | Location
          ) &
            Location;
          // this.router.navigate(['auth']);
        }
      }),
      take(1)
    );
  }
}
