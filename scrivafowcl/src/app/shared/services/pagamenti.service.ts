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

import { AppConfigService } from './app-config.service';
import { PagamentoIstanza } from 'src/app/features/ambito/models';
import { EsitoPagamento } from 'src/app/features/ppay/models/esito-pagamento.model';
import { Istanza } from '../models';

@Injectable({providedIn: 'root'})
export class PagamentiService {

  private beUrl = '';

  pagamentoErrorCode = null;

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  getPagamentoErrorCode(): string {
    return this.pagamentoErrorCode;
  }

  setPagamentoErrorCode(code: string) {
    this.pagamentoErrorCode = code;
  }

  getPagamentiIstanza(idIstanza: number): Observable<PagamentoIstanza[]> {
    return this.http.get<PagamentoIstanza[]>(`${this.beUrl}/pagamenti/id-istanza/${idIstanza}`);
  }

  putPagamentiIstanza(pagamento: PagamentoIstanza): Observable<PagamentoIstanza> {
    return this.http.put<PagamentoIstanza>(`${this.beUrl}/pagamenti/altri-canali`, pagamento);
  }

  avviaPagamento(pagamento: PagamentoIstanza) {
    return this.http.post(`${this.beUrl}/pagamenti`, pagamento);
  }

  sendPagamentoResult(esito: EsitoPagamento): Observable<Istanza> {
    return this.http.post<Istanza>(`${this.beUrl}/pagamenti/ppay-result`, esito);
  }

  getRT(idIstanza: number): Observable<any> {
    // se 200 -> ci sono nuove RT
    // se 204 -> nessuna nuova RT
    return this.http.get(`${this.beUrl}/pagamenti/rt/id-istanza/${idIstanza}`, { observe: 'response' });
  }
}
