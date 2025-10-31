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
import { ConfigAdempimento } from '../../models';
import { AppConfigService } from '../app-config.service';
import { TipoInfoAdempimento } from './utilities/configurazioni.enums';

@Injectable({ providedIn: 'root' })
export class ConfigurazioniScrivaService {
  private beUrl = '';

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();
  }

  getConfigurazione(key: string) {
    return this.http.get(`${this.beUrl}/configurazioni/key/${key}`);
  }

  getConfigurazioniByAdempimento(
    codAdempimento: string
  ): Observable<ConfigAdempimento[]> {
    return this.http.get<ConfigAdempimento[]>(
      `${this.beUrl}/adempimenti-config/adempimento/${codAdempimento}`
    );
  }

  getConfigurazioniByInfo(
    codAdempimento: string,
    info: TipoInfoAdempimento
  ): Observable<ConfigAdempimento[]> {
    return this.http.get<ConfigAdempimento[]>(
      `${this.beUrl}/adempimenti-config/adempimento/${codAdempimento}/info/${info}`
    );
  }

  getConfigurazioniByInfoAndChiave(
    codAdempimento: string,
    info: TipoInfoAdempimento,
    chiave: string
  ): Observable<ConfigAdempimento[]> {
    return this.http.get<ConfigAdempimento[]>(
      `${this.beUrl}/adempimenti-config/adempimento/${codAdempimento}/info/${info}/chiave/${chiave}`
    );
  }

  /**
   * Funzione che scarica le configurazioni per un adempimento, dato il tipo d'informazione e la chiave di accesso.
   * La funzione restituirà solo il primo (e si spera) unico elemento ottenuto dalla lista di ricerca.
   * @param codAdempimento string con il codice dell'adempimento per la ricerca.
   * @param info TipoInfoAdempimento con la chiave per lo scarico delle informazioni specifiche dell'adempimento.
   * @param chiave string con la chiave per la ricerca e scarico della configurazione.
   * @returns Observable<ConfigAdempimento> con l'oggetto trovato, altrimenti undefined.
   */
  getConfigurazioneByInfoAndChiave(
    codAdempimento: string,
    info: TipoInfoAdempimento,
    chiave: string
  ): Observable<ConfigAdempimento> {
    // Richiamo la funzione padre per lo scarico dati
    return this.getConfigurazioniByInfoAndChiave(
      codAdempimento,
      info,
      chiave
    ).pipe(
      map((configurazioni: ConfigAdempimento[]) => {
        // Verifico la lista
        configurazioni = configurazioni ?? [];
        // Ritorno solo il primo e unico (si spera) oggetto
        return configurazioni[0];
        // #
      })
    );
  }

  getInfoMessage(componente: string) {
    return this.getConfigurazione(`SCRIVA_HOME_INFO_${componente}`);
  }
}
