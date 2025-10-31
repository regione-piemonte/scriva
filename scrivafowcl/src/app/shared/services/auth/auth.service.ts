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
import { map } from 'rxjs/operators';
import { AppConfigService } from '..';
import { environment } from '../../../../../buildfiles/environment.dev';
import { ScrivaComponenteApp } from '../../enums/scriva-utilities/scriva-utilities.enums';
import { Compilante, IFunzionarioAutorizzato } from '../../models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  /** Constate per il path: /compilanti. */
  private PATH_COMPILANTI = '/compilanti';
  /** Costante per il path: /logout. */
  private PATH_LOGOUT = '/logout';

  constructor(private http: HttpClient, private config: AppConfigService) {}

  /**
   * ##########################################################################
   * FUNZIONI DI RECUPERO DATI PER L'UTENTE COLLEGATO: COMPILANTE O FUNZIONARIO
   * ##########################################################################
   */

  getCompilante(cf: string): Observable<Compilante> {
    return this.http
      .get<Compilante[]>(`${this.config.getBEUrl()}/compilanti/cf/${cf}`)
      .pipe(map((res) => res[0]));
  }

  getCompilanteBySession(): Observable<Compilante> {
    return this.http
      .get<Compilante[]>(`${this.config.getBEUrl()}/compilanti/user`)
      .pipe(map((res) => res[0]));
  }

  getFunzionarioBySession(): Observable<IFunzionarioAutorizzato> {
    return this.http.get<IFunzionarioAutorizzato>(
      `${this.config.getBEUrl()}/funzionari`
    );
  }

  /**
   * Funzione di PUT per i dati di un compilante.
   * @param compilante Compilante con i dati d'aggiornare.
   */
  putCompilante(compilante: Compilante): Observable<Compilante> {
    // Definisco l'url
    const base = this.config.getBEUrl();
    const compilanti = this.PATH_COMPILANTI;
    const url = `${base}${compilanti}`;

    // Richiamo il servizio
    return this.http.put<Compilante>(url, compilante);
  }

  /**
   * ##################################
   * FUNZIONI PER IL LOGOUT APPLICATIVO
   * ##################################
   */

  /**
   * Funzione che invoca la logout applicativa.
   * @returns Observable<Compilante[]> con il risultato del logout.
   */
  callLogout(): Observable<Compilante[]> {
    // Definisco i path per invocare la logout
    const base = this.config.getBEUrl();
    // const compilanti = this.PATH_COMPILANTI;
    const logout = this.PATH_LOGOUT;

    // Definisco l'url per la logout
    const url = `${base}${logout}`;

    // Richiamo il logout
    return this.http.get<Compilante[]>(url);
  }

  /**
   * FUnzione che invoca e gestisce il processo di logout applicativo.
   * @param componenteApp ScrivaComponenteApp che definisce la componente applicativa per il redirect a seguito della logout.
   */
  logout(componenteApp: ScrivaComponenteApp) {
    // Invoco la logout tramite URL
    this.callLogout().subscribe({
      next: (compilanti: Compilante[]) => {
        // Lancio la funzione per la pulizia e redirect a seguito del logout
        this.onShibboletLogout(componenteApp);
        // #
      },
      error: (e: any) => {
        // TODO: cosa fare quando la logout fallisce? Alert?
      },
    });
  }

  /**
   * Funzione che pulisce le informazioni dell'app dalla memoria e lancia il redirect alla pagina "post" logout.
   * @param componenteApp ScrivaComponenteApp che definisce la componente applicativa per il redirect a seguito della logout.
   */
  onShibboletLogout(componenteApp: ScrivaComponenteApp) {
    // Effettuo una pulizia di session e local storage
    sessionStorage.clear();
    localStorage.clear();

    // L'url di logout è definito in base al componente applicativo
    let logoutUrl = '';
    // Verifico qual è il path da impostare
    switch (componenteApp) {
      case ScrivaComponenteApp.frontOffice:
        logoutUrl = environment.logoutUrlFO;
        break;
      case ScrivaComponenteApp.backOffice:
        logoutUrl = environment.logoutUrlBO;
        break;
    }

    // Effettuo il redirect per il logout
    window.open(logoutUrl, '_self');
  }
}
