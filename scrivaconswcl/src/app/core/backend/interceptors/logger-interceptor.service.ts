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
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { Logger } from '@eng-ds/logger';

@Injectable()
export class LoggerInterceptor implements HttpInterceptor {
  constructor(private logger: Logger) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const started = Date.now();

    return next.handle(req).pipe(
      finalize(() => {
        const elapsed = Date.now() - started;
        this.logger.info(
          `URL: ${req.url} Method: ${req.method} Time took: ${elapsed} ms`
        );
      })
    );
  }
}
