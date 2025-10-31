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
import { IstanzaVincoloAut, OggettoIstanzaVincoloAutorizza, VincoloAutorizza } from 'src/app/features/ambito/models';

import { AppConfigService } from './app-config.service';

@Injectable({providedIn: 'root'})
export class VincoliAutService {

  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  getVincoliAutByAdempimento(idAdempimento: number): Observable<VincoloAutorizza[]> {
    return this.http.get<VincoloAutorizza[]>(`${this.beUrl}/vincoli-autorizzazioni/id-adempimento/${idAdempimento}`);
  }

  getVincoliAutByIstanza(idIstanza: number): Observable<IstanzaVincoloAut[]> {
    return this.http.get<IstanzaVincoloAut[]>(`${this.beUrl}/istanza-vincoli-aut?id_istanza=${idIstanza}`);
  }

  getVincoliIdroByIdOggettoIstanza(idOggettoIstanza: number): Observable<VincoloAutorizza[]> {
    return this.http.get<VincoloAutorizza[]>(`${this.beUrl}/vincoli-autorizzazioni/idro/ricadenza?id_oggetto_istanza=${idOggettoIstanza}`);
  }

  postVincoliAut(vincolo: IstanzaVincoloAut | Partial<IstanzaVincoloAut>): Observable<IstanzaVincoloAut> {
    return this.http.post<IstanzaVincoloAut>(`${this.beUrl}/istanza-vincoli-aut`, vincolo);
  }

  putVincoliAut(vincolo: IstanzaVincoloAut): Observable<IstanzaVincoloAut> {
    return this.http.put<IstanzaVincoloAut>(`${this.beUrl}/istanza-vincoli-aut`, vincolo);
  }

  deleteVincoloAut(uidVincolo: string) {
    return this.http.delete(`${this.beUrl}/istanza-vincoli-aut/${uidVincolo}`);
  }

  downloadNormativa(fileName: string) {
    return this.http.get(`${this.beUrl}/files/type/vincoli/name/${fileName}`, {responseType: 'blob'});
  }
}
