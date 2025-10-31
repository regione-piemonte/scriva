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
import { map } from 'rxjs/operators';

import { HomeStoreService } from './home-store.service';

import { IstanzaSearch } from 'src/app/shared/models';
import { AppConfigService } from 'src/app/shared/services';

@Injectable()
export class HomeService {
  private beUrl = '';

  constructor(
    private http: HttpClient,
    private config: AppConfigService,
    private store: HomeStoreService
  ) {
    this.beUrl = this.config.getBEUrl();
  }

  /**
   * 
   * @update SCRIVA-1485 è stato rimosso il query param 'ogg_app=false' poiché il servizio non ritorna l'attore gestione istanza.
   */
  simpleSearch(data): Observable<{ count: number; istanze: IstanzaSearch[] }> {
    const pageNumber = 1;
    const limit = 7;
    return this.http
      .post<IstanzaSearch[]>(
        // `${this.beUrl}/search/istanze/?offset=${pageNumber}&limit=${limit}&ogg_app=false`,
        `${this.beUrl}/search/istanze/?offset=${pageNumber}&limit=${limit}`,
        data,
        { observe: 'response' }
      )
      .pipe(
        map((result: HttpResponse<IstanzaSearch[]>) => {
          const paginationInfo = result.headers.get('PaginationInfo');
          return {
            count: this._getNumberValueFromHeaderString(
              paginationInfo,
              'total_elements'
            ),
            istanze: result.body,
          };
        })
      );
  }

  private _getNumberValueFromHeaderString(
    header: string,
    value: string
  ): number {
    let found = 0;
    header
      .replace('{', '')
      .replace('}', '')
      .split(', ')
      .forEach((item) => {
        const itemArray = item.split('=');
        if (itemArray[0] === value) {
          found = parseInt(itemArray[1], 10);
          return;
        }
      });

    return found;
  }
}
