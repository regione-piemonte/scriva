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
import { map } from 'rxjs/operators';

import { AppConfigService } from './app-config.service';
import { Comune, LOCCSILocation, Nazione, Provincia, Regione } from '../models';

@Injectable()
export class LocationService {
  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  /*
   * API limiti amministrativi
   */
  getNazioni(): Observable<Nazione[]> {
    return this.http.get<Nazione[]>(`${this.beUrl}/limiti-amministrativi/nazioni`);
  }

  getRegioniByNazione(codIstatNazione: string): Observable<Regione[]> {
    return this.http.get<Regione[]>(`${this.beUrl}/limiti-amministrativi/regioni?cod_istat_nazione=${codIstatNazione}`);
  }

  getProvince(): Observable<Provincia[]> {
    return this.http.get<Provincia[]>(`${this.beUrl}/limiti-amministrativi/province`);
  }

  getProvinceAttive(): Observable<Provincia[]> {
    return this.http.get<Provincia[]>(`${this.beUrl}/limiti-amministrativi/province/attive`);
  }

  getProvinceByRegione(codIstatRegione: string): Observable<Provincia[]> {
    return this.http.get<Provincia[]>(`${this.beUrl}/limiti-amministrativi/province?cod_istat_regione=${codIstatRegione}`);
  }

  getProvinceByAdempimento(idAdempimento: number): Observable<Provincia[]> {
    return this.http.get<Provincia[]>(`${this.beUrl}/limiti-amministrativi/province?id_adempimento=${idAdempimento}`);
  }

  getComuniByProvincia(codIstatProvincia: string): Observable<Comune[]> {
    return this.http.get<Comune[]>(`${this.beUrl}/limiti-amministrativi/comuni?cod_istat_provincia=${codIstatProvincia}`);
  }

  getComuneByCodiceIstat(codIstatComune: string): Observable<Comune> {
    return this.http.get<Comune[]>(`${this.beUrl}/limiti-amministrativi/comuni?cod_istat=${codIstatComune}`)
      .pipe(
        map(comuni => comuni[0])
      );
  }

  /*
   * API LOCCSI
   */
  searchAddress(indirizzo: string): Observable<LOCCSILocation[]> {
    return this.http.get<LOCCSILocation[]>(`${this.beUrl}/loccsi?q=${indirizzo}&limit=50&offset=0`);
  }
}
