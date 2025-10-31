/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AbstractDataSource } from './abstract-data-source';
import { TablePage } from './table-page';
import { TableSort } from './table-sort';
import { ApiFilterRequest, ApiFiltersRequest, ApiListResponse } from '../../../core';

export type ObserverDataType<T> = ApiListResponse<T>;

export declare type ListApiFunction<T> = (
  filter: ApiFiltersRequest,
) => Observable<ApiListResponse<T>>;

interface DataSourceConstructor<T> {
  tablePage?: TablePage;
  observable: ListApiFunction<T>;
  filters?: ApiFilterRequest[];
}

export interface FetchSubjectData {
  pageNumber?: number;
  pageSize?: number;
  sort?: TableSort[];
  filter?: ApiFilterRequest[];
}

export class DataSource<T> extends AbstractDataSource<T> {
  protected observable: ListApiFunction<T>;
  protected tmpRows: T[];

  constructor(options: DataSourceConstructor<T>) {
    super(options);
    this.tablePage = options.tablePage || new TablePage({ pageNumber: 0 });
    this.initialPage = this.tablePage.pageNumber;
  }

  setObservable(observable: ListApiFunction<T>): void {
    this.observable = observable;
  }

  paging(
    pageNumber: number = this.tablePage.pageNumber,
    pageSize: number = this.tablePage.size,
  ): void {
    this.fetch$.next({ pageNumber, pageSize, local: true });
  }

  filtering(
    filter: ApiFilterRequest[] = this.filter,
    local: boolean = false,
  ): void {
    this.fetch$.next({ filter, local, pageNumber: this.initialPage });
  }

  getElements(local: boolean = false): Observable<T[]> {
    if (local) {
      return this._applyLocalFilters();
    }
    const filterRequest = new ApiFiltersRequest({
      filters: this.filter,
    });

    return this.observable(filterRequest).pipe(
      map((res: ApiListResponse<T>) => {
        this.tablePage.totalElements = res?.content.length || 0;
        this.tablePage.totalPages =
          Math.floor((res?.content?.length || 0) / this.tablePage.size) || 0;
        this.tmpRows = res.content;
        return res.content;
      }),
    );
  }

  private _applyLocalFilters(): Observable<T[]> {
    if (this.filter.length) {
      const rows = [];
      this.tmpRows.forEach((row) => {
        if (
          this.filter.some((f) => {
            return row[f.field].search(f.value) > -1;
          })
        ) {
          rows.push(row);
        }
      });
      return of(rows);
    }
    return of(this.tmpRows);
  }
}
