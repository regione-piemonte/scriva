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
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { I18nService } from '@eng-ds/translate';
import { NotificationService } from '@shared/notification/notification.service';
import { SecurityService } from '@app/core/services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private router: Router,
    private i18n: I18nService,
    private notificationService: NotificationService,
    private securityService: SecurityService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next
      .handle(req)
      .pipe(catchError((event: HttpEvent<any>) => this._handleError(event)));
  }

  private _handleError(event: HttpEvent<any>): Observable<HttpEvent<any>> {
    if (event instanceof HttpErrorResponse) {
      switch (event.status) {
        case 500:
        case 404:
          break;
        case 401:
          this._handleUnauthorized();
      }
    }
    return throwError(event);
  }

  private _handleUnauthorized() {
    this.notificationService.warning({
      text: this.i18n.translate('NOT_AUTHORIZED.NOTIFICATION.TEXT'),
      title: this.i18n.translate('NOT_AUTHORIZED.NOTIFICATION.TITLE')
    });
    this.securityService.onLogout();
    this.router.navigate(['home']);
  }
}
