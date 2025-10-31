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
import { environment } from 'src/environments/environment';

@Injectable()
export class AppConfigService {
  // bePath = '/scrivafoweb/restfacade/be';
  // bePath = '/scrivabesrv/api/v1';
  bePath = '/scrivafoweb/api/v1';

  bePublishedRootPath = '/lgspa-demo/v1';

  getBEUrl(): string {
    // return environment.beServerPrefix + this.bePath;
    let beHost = window.location.origin;
    if (environment.local && environment.localEnv.beHost) {
      beHost = environment.localEnv.beHost;
    }
    return beHost + environment.beBasePath;
  }

  /**
   * Funzione che genera il path url per le chiamate ai servizi di backend.
   * @param paths Spread syntax che permette di passare un numero variabile di path che comporranno l'url.
   * @returns string con la url compilata per la chiamata ai servizi di BE.
   */
  appUrl(...paths: any[]): string {
    // Verifico l'input
    paths = paths ?? [];

    // Inizializzo l'url per la chiamata
    let url = this.getBEUrl();
    // Rimuovo possibili informazioni undefined
    paths = paths.filter((p: any) => p != undefined);
    // Effettuo una conversione e pulizia dei path da possibili "/" ad inizio stringa
    paths = paths.map((p: any) => {
      // Converto le informazioni in stringa
      p = p.toString();
      // Creo la regex che definisce se il primo carattere di una stringa è "/"
      const firstCharSlash = /^\//;
      // Tramite regex ritorno la stringa senza il primo carattere se "/"
      return p.replace(firstCharSlash, '');
    });

    // Compongo l'url con i vari path
    paths.forEach((path: string) => {
      // Costruisco la stringa
      url = `${url}/${path}`;
    });

    // Ritorno l'url generato
    return url;
  }

  /**
   * Getter che recupera dalle configurazioni di build se l'applicazione è in run in modalità di produzione.
   * @returns boolean con il risultato del check.
   */
  get isProductionMode(): boolean {
    // Recupero e ritorno la configurazione dall'environment
    return environment.production;
  }
}
