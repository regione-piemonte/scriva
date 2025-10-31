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
import { ApiClient } from '@eng-ds/api-client';
import { Observable } from 'rxjs';
import { ApiPageableResponse } from '@app/core';

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  constructor(private apiClient: ApiClient) {
  }

  getAdempimentiList(): Observable<ApiPageableResponse<any>> {
    return this.apiClient.request('getAdempimenti');
  }

  getAdempimentiByCodInfo(codTipoAdempimento: string, infoScriva: string): Observable<any> {
    return this.apiClient.request('getAdempimentiConfig', null, {
      cod_tipo_adempimento: codTipoAdempimento,
      info: infoScriva
    });
  }

}
