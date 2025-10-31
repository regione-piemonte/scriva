/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {ApiCSIPageableResponse, ApiFiltersRequest, CSIApiFiltersRequest} from '../../../core';
import {AbstractDataSource, CatchErrorFunction} from './abstract-data-source';
import {TablePage} from './table-page';
import {TableSort} from './table-sort';

export declare type CSIPageableApiFunction<T> = (
  filter: ApiFiltersRequest,
) => Observable<ApiCSIPageableResponse<T>>;

interface DataSourceConstructor<T> {
  observable: CSIPageableApiFunction<T>;
  catchErrorFunction?: CatchErrorFunction;
  tablePage?: TablePage;
  tableSort?: TableSort[];
  filters?: any;
}

export class CSIServerPageableDataSource<T> extends AbstractDataSource<T> {
  protected observable: CSIPageableApiFunction<T>;

  protected filter: any;

  constructor(options: DataSourceConstructor<T>) {
    super(options);
  }

  getElements(): Observable<T[]> {
    const filterRequest = new CSIApiFiltersRequest({
      filters: this.filter,
      page: this.tablePage.pageNumber,
      size: this.tablePage.size,
    });

    return this.observable(filterRequest).pipe(
      map((res: ApiCSIPageableResponse<T>) => {
        this.tablePage.totalElements = res?.result.totalElements || 0;
        this.tablePage.totalPages = res?.result.totalPages || 0;
        return res?.result.content || [];
      }),
    );
  }

  filtering(
    filter: any = this.filter,
    local: boolean = false,
  ): void {
    this.fetch$.next({filter, local, pageNumber: this.initialPage});
  }

}
