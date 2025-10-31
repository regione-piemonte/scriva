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
import { Observable, Subject, of } from 'rxjs';
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

export class LocalPagedDataSourceWithEmit<T> extends AbstractDataSource<T> {
  protected observable: ServerApiFunction<T>;
  protected localRows: T[] = [];
  protected localFilter: any;

  onLocalRowsEvalueted$ = new Subject<T[]>();

  constructor(options: DataSourceConstructor<T>) {
    super(options);
  }

  getElements(): Observable<T[]> {
    const start = this.tablePage.size * (this.tablePage.pageNumber - 1);
    const end = this.tablePage.size * this.tablePage.pageNumber;

    if (this.localRows.length > 0 && this.filter === this.localFilter) {
      // Gestisco il dato per le local rows
      const localRows = this.localRows.slice(start, end) || [];
      // Emetto l'evento per le rows valorizzate
      this.onLocalRowsEvalueted$.next(localRows);
      // Ritorno i dati delle local rows
      return of(localRows);
    }

    this.localFilter = this.filter;

    return this.observable().pipe(
      map((res: T[]) => {
        this.tablePage.totalElements = res?.length || 0;
        this.localRows = res;

        // Gestisco il dato per le local rows
        const localRows = this.localRows.slice(start, end) || [];
        // Emetto l'evento per le rows valorizzate
        this.onLocalRowsEvalueted$.next(localRows);
        // Ritorno i dati delle local rows
        return localRows;
      })
    );
  }

  refresh(): void {
    this.localRows = [];
    this.localFilter = {};
    super.refresh();
  }
  
}
