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
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Page, PagedData } from '../models';
import { SearchStoreService } from './search-store.service';

import { IstanzaSearch } from 'src/app/shared/models';
import { AppConfigService } from 'src/app/shared/services';

@Injectable()
export class SearchService {
  private beUrl = '';

  constructor(
    private http: HttpClient,
    private config: AppConfigService,
    private store: SearchStoreService
  ) {
    this.beUrl = this.config.getBEUrl();
  }

  search(
    page: Page,
    data,
    sort: { dir: string; prop: string } = null
  ): Observable<PagedData<IstanzaSearch>> {

    this.store.dataSearch = {...data};
    let url = `${this.beUrl}/search/istanze/?offset=${page.pageNumber + 1}&limit=${page.size}`;

    if (sort?.prop) {
      url += '&sort=';
      sort.dir === 'desc' ? (url += '-') : (url += '+');
      url += sort.prop;
    }

    return this.http
      .post<IstanzaSearch[]>(url, data, { observe: 'response' })
      .pipe(
        map((result: HttpResponse<IstanzaSearch[]>) => {
          const paginationInfo = result.headers.get('PaginationInfo');
          this.store.paginationInfoSub.next(paginationInfo);
          const countIstanze = result.headers.get('CountIstanze');
          this.store.countIstanzeSub.next(countIstanze);

          const pagedData = new PagedData<IstanzaSearch>();
          pagedData.data = result.body;
          pagedData.page = {
            pageNumber:
              this._getNumberValueFromHeaderString(paginationInfo, 'page') - 1,
            size: this._getNumberValueFromHeaderString(
              paginationInfo,
              'page_size'
            ),
            totalElements: this._getNumberValueFromHeaderString(
              paginationInfo,
              'total_elements'
            ),
            totalPages: this._getNumberValueFromHeaderString(
              paginationInfo,
              'total_pages'
            ),
          };
          return pagedData;
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
