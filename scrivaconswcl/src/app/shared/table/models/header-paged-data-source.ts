/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiFiltersRequest, CSIApiFiltersRequest } from '../../../core';
import { AbstractDataSource, CatchErrorFunction } from './abstract-data-source';
import { TablePage } from './table-page';
import { TableSort } from './table-sort';

export declare type HeaderPagedApiFunction<T> = (
  filter: ApiFiltersRequest
) => Observable<HttpResponse<T[]>>;

interface DataSourceConstructor<T> {
  observable: HeaderPagedApiFunction<T>;
  catchErrorFunction?: CatchErrorFunction;
  tablePage?: TablePage;
  tableSort?: TableSort[];
  filters?: any;
}

export class HeaderPagedDataSource<T> extends AbstractDataSource<T> {
  protected observable: HeaderPagedApiFunction<T>;

  filter: any;

  constructor(options: DataSourceConstructor<T>) {
    super(options);
  }

  getElements(): Observable<T[]> {
    const filterRequest = new CSIApiFiltersRequest({
      filters: this.filter,
      page: this.tablePage.pageNumber,
      size: this.tablePage.size,
      sort: this.tableSort
    });

    return this.observable(filterRequest).pipe(
      map((res: HttpResponse<T[]>) => {
        const paginationInfo = this.paginationParse(
          res.headers.get('PaginationInfo')
        );
        this.tablePage.totalElements = paginationInfo.total_elements || 0;
        this.tablePage.totalPages = paginationInfo.total_pages || 0;
        this.tablePage.size = paginationInfo.page_size || 0;
        return res?.body || [];
      })
    );
  }

  filtering(filter: any = this.filter, local: boolean = false): void {
    this.fetch$.next({ filter, local, pageNumber: this.initialPage });
  }

  paginationParse(paginationInfo: string): Record<string, number> {
    const record = {};
    paginationInfo = paginationInfo.replace('{', '').replace('}', '');
    const paginationElements = paginationInfo.split(', ');
    paginationElements.forEach((el) => {
      const keyvalue = el.split('=');
      record[keyvalue[0]] = keyvalue[1];
    });
    return record;
  }
}
