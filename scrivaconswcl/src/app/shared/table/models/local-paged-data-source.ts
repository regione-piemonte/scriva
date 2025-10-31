/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { ApiFilterRequest } from '@app/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TablePage } from './table-page';
import { AbstractDataSource } from './abstract-data-source';
import { TableSort } from './table-sort';

export declare type ServerApiFunction<T> = () => Observable<T[]>;

interface DataSourceConstructor<T> {
  tablePage?: TablePage;
  tableSort?: TableSort[];
  observable: any;
  filters?: ApiFilterRequest[] | any;
}

export class LocalPagedDataSource<T> extends AbstractDataSource<T> {
  protected observable: ServerApiFunction<T>;
  protected localRows: T[] = [];
  protected localFilter: any;

  constructor(options: DataSourceConstructor<T>) {
    super(options);
  }

  getElements(): Observable<T[]> {
    const start = this.tablePage.size * (this.tablePage.pageNumber - 1);
    const end = this.tablePage.size * (this.tablePage.pageNumber);

    if (this.localRows.length > 0 && this.filter === this.localFilter) {
      return of(this.localRows.slice(start, end) || []);
    }

    this.localFilter = this.filter;

    return this.observable().pipe(
      map((res: T[]) => {
        this.tablePage.totalElements = res?.length || 0;
        this.localRows = res;
        return this.localRows.slice(start, end) || [];
      })
    );
  }

  refresh(): void {
    this.localRows = [];
    this.localFilter = {};
    super.refresh();
  }

}
