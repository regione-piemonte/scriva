/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { ApiFilterRequest } from './api-filter-request';
import { ApiSortRequest } from '@app/core';
import { TableSort } from '@shared/table/models/table-sort';

export class CSIApiFiltersRequest {
  filters: any;
  page?: number;
  size?: number;
  sort?: TableSort[];

  constructor(
    options: Omit<CSIApiFiltersRequest, 'toServer' | 'removeFilter'>
  ) {
    this.filters = options.filters;
    this.page = options.page;
    this.size = options.size;
    this.sort = options.sort;
  }

  removeFilter(fieldFilterName: string): void {
    const index = this.filters.findIndex((f) => f.field === fieldFilterName);
    this.filters.splice(index, 1);
  }

  toServer(): {
    filters: ApiFilterRequest[];
    page?: number;
    size?: number;
  } {
    if (this.page && this.size) {
      return { filters: this.filters, page: this.page, size: this.size };
    }
    if (this.page) {
      return { filters: this.filters, page: this.page };
    }
    return { filters: this.filters };
  }
}
