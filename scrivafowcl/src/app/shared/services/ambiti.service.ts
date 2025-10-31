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
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

import { AppConfigService } from './app-config.service';
import { Ambito } from '../models';

@Injectable()
export class AmbitiService {
  private beUrl = '';

  ambiti: Ambito[] = [];

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  /*
   * API ambiti
   */
  getAmbiti(): Observable<Ambito[]> {
    return this.http.get<Ambito[]>(`${this.beUrl}/ambiti`).pipe(
      tap(res => this.ambiti = res),
    );
  }

  getAmbitoById(id: number): Observable<Ambito> {
    return this.http
      .get<Ambito[]>(`${this.beUrl}/ambiti/${id}`)
      .pipe(
        map((res) => res[0])
      );
  }

  getAmbitoByCode(codAmbito: string): Observable<Ambito> {
    return this.http
      .get<Ambito[]>(`${this.beUrl}/ambiti?codice=${codAmbito}`)
      .pipe(
        map((res) => res[0])
      );
  }
}
