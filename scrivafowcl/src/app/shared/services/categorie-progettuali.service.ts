/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AppConfigService } from './app-config.service';
import { CategoriaOggetto, OggettoIstanzaCategoria } from 'src/app/features/ambito/models';

@Injectable({ providedIn: 'root'})
export class CategorieProgettualiService {

  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  getCategorieProgettualiByAdempimento(idAdempimento: number): Observable<HttpResponse<CategoriaOggetto[]>> {
    return this.http.get<CategoriaOggetto[]>(`${this.beUrl}/categorie-progettuali?id_adempimento=${idAdempimento}`, { observe: 'response' });
  }

  getCategorieProgettualiByIstanza(idIstanza: number): Observable<OggettoIstanzaCategoria[]> {
    return this.http.get<OggettoIstanzaCategoria[]>(`${this.beUrl}/categorie-progettuali?id_istanza=${idIstanza}`);
  }

  saveCategorieProgettuali(oggettoIstanzaCategoria: OggettoIstanzaCategoria, flgPut?: boolean): Observable<OggettoIstanzaCategoria> {
    if (flgPut) {
      return this.http.put<OggettoIstanzaCategoria>(`${this.beUrl}/categorie-progettuali`, oggettoIstanzaCategoria);
    } else {
      return this.http.post<OggettoIstanzaCategoria>(`${this.beUrl}/categorie-progettuali`, oggettoIstanzaCategoria);
    }
  }

  deleteCategorieProgettuali(gestUID: string) {
    return this.http.delete(`${this.beUrl}/categorie-progettuali/` + gestUID);
  }

  calcolaCategorieAggiuntive(idOggettoIstanza: number) {
    return this.http.post(`${this.beUrl}/categorie-progettuali/id-oggetto-istanza/` + idOggettoIstanza, null);
  }
}
