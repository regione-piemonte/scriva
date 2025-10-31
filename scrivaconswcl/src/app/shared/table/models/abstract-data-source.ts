/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Observable, of, Subject } from 'rxjs';
import { catchError, map, share, switchMap, tap } from 'rxjs/operators';
import { TablePage } from './table-page';
import { TableSort } from './table-sort';
import { ApiFilterRequest, ApiFiltersRequest } from '../../../core';

interface AbstractDataSourceConstructor {
  tablePage?: TablePage;
  tableSort?: TableSort[];
  observable: any;
  catchErrorFunction?: CatchErrorFunction;
  filters?: ApiFilterRequest[];
}

export declare type CatchErrorFunction = (error: any) => void;

export interface FetchSubjectData {
  local?: boolean;
  pageNumber?: number;
  pageSize?: number;
  sort?: TableSort[];
  filter?: ApiFilterRequest[];
}

export abstract class AbstractDataSource<T> {
  fetch$: Subject<FetchSubjectData> = new Subject<FetchSubjectData>();
  rows: T[];
  tablePage: TablePage;
  tableSort: TableSort[] = [];
  protected initialPage: number;
  protected filter: ApiFilterRequest[] = [];
  protected observable: any;
  protected catchErrorFunction: CatchErrorFunction;
  rows$: Observable<any[] | T[]> = this.fetch$
    .pipe(
      switchMap((data: FetchSubjectData = {}) => {
        this._setPageNumber(data.pageNumber);
        this._setPageSize(data.pageSize);
        this._setSort(data.sort);
        this._setFilter(data.filter);
        return this.getElements(data.local).pipe(
          catchError((e) => {
            if (
              this.catchErrorFunction &&
              typeof this.catchErrorFunction === 'function'
            ) {
              this.catchErrorFunction(e);
            }
            return of([]);
          })
        );
      }),
      share()
    )
    .pipe(
      /*       catchError((e) => {
        if (
          this.catchErrorFunction &&
          typeof this.catchErrorFunction === 'function'
        ) {
          this.catchErrorFunction(e);
        }
        return of([]);
      }), */
      tap((rows: T[]) => (this.rows = rows))
    );

  constructor(options: AbstractDataSourceConstructor) {
    this.tablePage = options.tablePage || new TablePage();
    this.tableSort = options.tableSort || [];
    this.observable = options.observable;
    this.catchErrorFunction = options.catchErrorFunction || (() => {});
    this.filter = options.filters || [];
    this.initialPage = this.tablePage.pageNumber;
  }

  setObservable(observable: any): void {
    this._setPageNumber(1);
    this.observable = observable;
  }

  setCatchErrorFunction(catchErrorFunction: CatchErrorFunction): void {
    this.catchErrorFunction = catchErrorFunction;
  }

  refresh(): void {
    this.fetch$.next();
  }

  paging(
    pageNumber: number = this.tablePage.pageNumber,
    pageSize: number = this.tablePage.size
  ): void {
    this.fetch$.next({ pageNumber, pageSize });
  }

  sorting(sort: TableSort[] = this.tableSort): void {
    this.fetch$.next({ sort, pageNumber: 1 });
  }

  filtering(filter: ApiFilterRequest[] = this.filter): void {
    this.fetch$.next({ filter, pageNumber: this.initialPage });
  }

  getElements(local: boolean = false): Observable<T[]> {
    const filterRequest = new ApiFiltersRequest({
      filters: this.filter
    });

    return this.observable(filterRequest).pipe(
      map((res: any) => {
        return res.result;
      })
    );
  }

  protected _setPageNumber(
    pageNumber: number = this.tablePage.pageNumber
  ): void {
    this.tablePage.pageNumber = pageNumber;
  }

  protected _setPageSize(pageSize: number = this.tablePage.size): void {
    this.tablePage.size = pageSize;
  }

  protected _setSort(sort: TableSort[] = this.tableSort): void {
    this.tableSort = sort;
  }

  protected _setFilter(filter: ApiFilterRequest[] = this.filter): void {
    this.filter = filter;
  }

  // protected _getApiSortRequest(): ApiSortRequest[] {
  //   return this.tableSort.map((sort: TableSort) => ({
  //     direction: sort.dir,
  //     field: sort.prop,
  //   }));
  // }
}
