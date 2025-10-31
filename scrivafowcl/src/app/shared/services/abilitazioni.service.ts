/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { IstanzaAttore, TipoAbilitazione } from '../models';
import { AppConfigService } from './app-config.service';

@Injectable({
  providedIn: 'root'
})
export class AbilitazioniService {

  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  getTipiAbilitazione(idIstanza: number): Observable<TipoAbilitazione[]> {
    return this.http.get<TipoAbilitazione[]>(`${this.beUrl}/abilitazioni/id-istanza/${idIstanza}`);
  }

  getAbilitazioniConcesse(idIstanza: number): Observable<IstanzaAttore[]> {
    return this.http.get<IstanzaAttore[]>(`${this.beUrl}/abilitazioni/id-istanza/${idIstanza}/abilitazioni-concesse`);
  }

  getAbilitazioniRevocabili(idIstanza: number): Observable<IstanzaAttore[]> {
    return this.http.get<IstanzaAttore[]>(`${this.beUrl}/abilitazioni/id-istanza/${idIstanza}/revocabili`);
  }

  saveAbilitazione(abilitazione: IstanzaAttore): Observable<IstanzaAttore> {
    if (abilitazione.id_istanza_attore) {
      return this.http.put<IstanzaAttore>(`${this.beUrl}/abilitazioni`, abilitazione);
    } else {
      return this.http.post<IstanzaAttore>(`${this.beUrl}/abilitazioni`, abilitazione);
    }
  }
}
