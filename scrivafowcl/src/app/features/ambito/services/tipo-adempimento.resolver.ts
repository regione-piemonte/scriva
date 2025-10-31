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
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, filter, tap } from 'rxjs/operators';
import { TipoAdempimento } from 'src/app/shared/models';
import { AdempimentoService } from 'src/app/shared/services';

import { AmbitoStoreService } from './ambito-store.service';

@Injectable()
export class TipoAdempimentoResolver implements Resolve<TipoAdempimento> {
  constructor(
    private adempimentoService: AdempimentoService,
    private ambitoStoreService: AmbitoStoreService
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<TipoAdempimento> {
    return this.adempimentoService
      .getTipoAdempimentoByCode(route.paramMap.get('codTipoAdempimento'))
      .pipe(
        catchError((err) => {
          return throwError(err);
        }),
        filter((tipoAdempimento) => !!tipoAdempimento),
        tap((tipoAdempimento) => {
          // this.ambitoStoreService.tipoAdempimentoSub.next(tipoAdempimento);
        })
      );
  }
}
