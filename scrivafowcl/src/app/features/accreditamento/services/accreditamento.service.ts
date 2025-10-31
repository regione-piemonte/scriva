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
import * as cryptoJS from 'crypto-js';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Compilante } from 'src/app/shared/models';
import { AppConfigService } from 'src/app/shared/services';
import { Accreditamento } from '../models';

@Injectable({ providedIn: 'root' })
export class AccreditamentoService {
  /** String che definisce il path per: /accreditamenti */
  private PATH_ACCREDITAMENTI = `/accreditamenti`;

  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  /**
   * Funzione che verifica l'otp richiesto.
   * @param uid string con lo uid generato insieme all'otp.
   * @param otp string con l'otp da verificare.
   * @returns Obscervable<Compilante> con il risultato della verifica dell'otp.
   */
  verificaOTP(uid: string, otp: string): Observable<Compilante> {
    // Costruisco l'url della chiamata
    const base = this.beUrl;
    const accreditamento = this.PATH_ACCREDITAMENTI;
    const uidPath = `/${uid}`;
    const otpPath = `/valida`;
    const url = `${base}${accreditamento}${uidPath}${otpPath}?otp=${cryptoJS.MD5(otp)}`;

    // Richiamo il servizio di verifica dell'otp
    return this.http.get<Compilante>(url).pipe(map((res) => res[0]));
  }

  getAccreditamento(uid: string, otp: string): Observable<Compilante> {
    return this.http
      .get<Compilante>(
        `${this.beUrl}/accreditamenti/${uid}/valida?otp=${cryptoJS.MD5(otp)}`
      )
      .pipe(map((res) => res[0]));
  }

  sendMailOtp(accreditamento: Accreditamento): Observable<Accreditamento> {
    return this.http.post<Accreditamento>(
      `${this.beUrl}/accreditamenti`,
      accreditamento
    );
  }
}
