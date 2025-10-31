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
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SearchStoreService {
  paginationInfoSub = new Subject<string>();
  countIstanzeSub = new Subject<string>();
  _dataSearch: any;
  _searchMode: string;

  constructor() {}

  set dataSearch(v:any) {
    this._dataSearch = v;
  }

  get dataSearch(): any {
    return this._dataSearch;
  }

  set searchMode(v:string) {
    this._searchMode = v;
  }

  get searchMode(): string {
    return this._searchMode;
  }
}
