/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiFilterRequest, ApiFiltersRequest, ApiPageableRequest, ApiPageableResponse } from '../../../core';
import { AbstractDataSource, CatchErrorFunction } from './abstract-data-source';
import { TablePage } from './table-page';
import { TableSort } from './table-sort';

export declare type PageableApiFunction<T> = (
  filter: ApiFiltersRequest,
) => Observable<ApiPageableResponse<T>>;

interface DataSourceConstructor<T> {
  observable: PageableApiFunction<T>;
  catchErrorFunction?: CatchErrorFunction;
  tablePage?: TablePage;
  tableSort?: TableSort[];
  filters?: ApiFilterRequest[];
}

export class ServerPageableDataSource<T> extends AbstractDataSource<T> {
  protected observable: PageableApiFunction<T>;

  constructor(options: DataSourceConstructor<T>) {
    super(options);
  }

  getElements(): Observable<T[]> {
    const filterRequest = new ApiFiltersRequest({
      filters: this.filter,
      page: this.tablePage.pageNumber,
      size: this.tablePage.size,
    });

    return this.observable(filterRequest).pipe(
      map((res: ApiPageableResponse<T>) => {
        this.tablePage.totalElements = res?.totalElements || 0;
        this.tablePage.totalPages = res?.totalPages || 0;
        return res?.content || [];
      }),
    );
  }
}
