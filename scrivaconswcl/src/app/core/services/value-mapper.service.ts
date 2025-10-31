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
import { Ambito, TipoAdempimento } from '@pages/ambiti/model';
import { ApiClient } from '../api-client/public-api';

@Injectable()
export class ValueMapperService {
  // tslint:disable-next-line:variable-name
  private _tipiAdempimenti: Map<string, TipoAdempimento> = new Map();
  private _ambiti: Map<string, Ambito> = new Map();

  constructor(private apiService: ApiClient) {}

  getConfig(): Promise<any> {
    const configsPromises = [];

    configsPromises.push(this.getTipiAdempimento().toPromise());
    configsPromises.push(this.getAmbiti().toPromise());

    return Promise.all(configsPromises).then((response) => {
      const tipi = response[0];
      const ambiti = response[1];

      ambiti.forEach((value: Ambito) => {
        this._ambiti.set(value.cod_ambito, value);
      });

      tipi.forEach((value: TipoAdempimento) => {
        this._tipiAdempimenti.set(value.cod_tipo_adempimento, value);
      });
    });
  }

  getAmbiti(): Observable<Ambito[]> {
    return this.apiService.request<any>('getAmbiti');
  }

  getTipiAdempimento(): Observable<TipoAdempimento[]> {
    return this.apiService.request<any>('getAdempimenti');
  }

  getAllAmbiti(): Ambito[] {
    return [...this._ambiti.values()];
  }

  getAmbito(name: string): Ambito {
    return this._ambiti.get(name);
  }

  getAmbitoFromAdempimento(name: string): Ambito {
    return [...this._tipiAdempimenti.values()].find(
      (i) => i.cod_tipo_adempimento === name
    )?.ambito;
  }

  getTipoAdempimento(name: string): TipoAdempimento {
    return this._tipiAdempimenti.get(name);
  }
}
