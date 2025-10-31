/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiClient } from '@app/core/api-client/public-api';
import { Observable } from 'rxjs';
import { Osservazione } from '../interfaces/osservazione';
import { TableSort } from '@shared/table/models/table-sort';

@Injectable({
  providedIn: 'root'
})
export class OsservazioniService {
  constructor(private apiClient: ApiClient) {}

  getOsservazioni(
    cod_stato_osservazione?: string,
    offset?: number,
    size?: number,
    sort?: TableSort[]
  ): Observable<HttpResponse<Osservazione[]>> {
    let params = new HttpParams();
    if (cod_stato_osservazione) {
      params = params.set('cod_stato_osservazione', cod_stato_osservazione);
    }
    if (offset) {
      params = params.set('offset', offset.toString());
    }

    if (sort[0]) {
      if (sort[0]?.dir === 'asc') {
        params = params.set('sort', sort[0].prop);
      } else {
        params = params.set('sort', '-' + sort[0].prop);
      }
    }
    return this.apiClient.requestWithHeader<any>(
      'getOsservazioni',
      null,
      params
    );
  }
}
