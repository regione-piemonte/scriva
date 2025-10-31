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
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { ConfigAdempimento } from 'src/app/shared/models/adempimento/config-adempimento.model';

import { AppConfigService } from 'src/app/shared/services';

@Injectable()
export class ConfigurazioneAdempimentoService { /* todo THIS CLASS IS NOT NEEDED ANYMORE */
  private beUrl = '';

  private listConfigAdempimento: ConfigAdempimento[] = [];
  listConfigAdempimentoSub = new BehaviorSubject<ConfigAdempimento[]>([]);

  constructor(private http: HttpClient, private config: AppConfigService) {
    this.beUrl = this.config.getBEUrl();

    this.listConfigAdempimentoSub.subscribe((list) => {
      this.listConfigAdempimento = list;
    });
  }

  getConfigByAdempimento(
    codAdempimento: string
  ): Observable<ConfigAdempimento[]> {
    return this.http.get<ConfigAdempimento[]>(
      `${this.beUrl}/adempimenti-config/adempimento/${codAdempimento}`
    );
  }

  getProperty(property: string): string {
    const conf = this.listConfigAdempimento.find(
      (config) => config.chiave === property
    );
    return conf?.valore ? conf.valore : '';
  }

  getNumeroSoggetti(): string {
    return this.getProperty('IndNumSoggetto');
  }

  getTipoSoggetto(): string {
    return this.getProperty('IndTipoSoggetto');
  }

  getNumeroReferenti(): string {
    return this.getProperty('IndNumReferente');
  }

  getNumeroRichiedenti(): string {
    return this.getProperty('IndNumRichiedente');
  }

  getNumeroOggetti(): string {
    return this.getProperty('IndNumOggetto');
  }

  getNumeroComuniOggetto() {
    return this.getProperty('IndNumComuniOggetto');
  }

  getGeoMode() {
    return this.getProperty('IndGeoMode');
  }
}
