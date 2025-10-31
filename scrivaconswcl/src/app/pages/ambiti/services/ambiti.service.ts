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
import { Adempimenti } from '@pages/ambiti/model';

@Injectable()
export class AmbitiService {
  constructor(private apiClient: ApiClient) {
  }

  getTipoAdempimenti(codice_ambito: string): Observable<Adempimenti> {
    return this.apiClient.request('getAdempimentiByAmbito', null, { codice_ambito });
  }

  getAdempimentiConfig(codTipoAdempimento: string, infoScriva: string): Observable<any> {
    return this.apiClient.request('getAdempimentiConfig', null, {
      cod_tipo_adempimento: codTipoAdempimento,
      info: infoScriva
    });
  }
}
