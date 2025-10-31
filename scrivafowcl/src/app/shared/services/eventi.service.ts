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
import { Observable, of } from 'rxjs';

import { AppConfigService } from './app-config.service';
import { TipoEvento } from '../models';

@Injectable({ providedIn: 'root' })
export class EventiService {

  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  getTipiEvento(): Observable<TipoEvento[]> {
    return this.http.get<TipoEvento[]>(`${this.beUrl}/tipi-evento`);
  }
}
