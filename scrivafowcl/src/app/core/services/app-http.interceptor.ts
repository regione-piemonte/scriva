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
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AuthStoreService } from 'src/app/shared/services';
import { HttpErrorService } from './http-error-response.service';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor {

  private componente: string;
  private loggedUserCf: string;

  constructor(
    private authStoreService: AuthStoreService,
    private httpErrorService: HttpErrorService
  ) { }

  public intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {

    this.componente = this.authStoreService.getComponente();
    this.loggedUserCf = this.authStoreService.getLoggedUserCf();

    let headers = req.headers;
    if (this.loggedUserCf) {
      headers = headers.set(
        'Attore-Scriva',
        this.componente + '/' + this.loggedUserCf
      );
    }
    
    if (environment.local && environment.localEnv.fakeAuth.enabled) {
      headers = headers.set(
        environment.localEnv.fakeAuth.headerName,
        environment.localEnv.fakeAuth.headerValue
      );
    }

    req = req.clone({ headers });

    return next
      .handle(req)
      .pipe(catchError((event: HttpEvent<any>) => this._handleError(event)));
  }

  private _handleError(err: HttpEvent<any>): Observable<any> {
    if (err instanceof HttpErrorResponse) {
      /** gestisco errore di tipo HttpErrorResponse */
      this.httpErrorService.handleError(err);
    }
    return throwError(err);
  }
}
