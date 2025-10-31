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
import { Observable } from 'rxjs';
import { ApiCSIPageableResponse } from '@core/backend/model';
import { ApiClient } from '@eng-ds/api-client';
import { Pratica } from '@pages/pratiche/model';
import { FasePubblicazione } from '@pages/pratiche/enum';
import { TableSort } from '@shared/table/models/table-sort';
import { HttpParams, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AvvisiService {
  constructor(private apiClient: ApiClient) {}

  getAvvisiList(
    id_ambito: number,
    id_tipo_adempimento: number,
    id_competenza_territorio = 1,
    offset?: number,
    size?: number,
    sort?: TableSort[],
    fase_pubblicazione = FasePubblicazione.TUTTE
  ): Observable<HttpResponse<ApiCSIPageableResponse<Pratica>>> {
    let params = new HttpParams();

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
      'getPracticesList',
      {
        id_ambito,
        id_tipo_adempimento,
        id_competenza_territorio,
        fase_pubblicazione,
        cod_categoria_allegato: 'AVVISO-PUB'
      },
      params
    );
  }
}
