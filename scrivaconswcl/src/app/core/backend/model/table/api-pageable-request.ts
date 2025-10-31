/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export type ApiPageableRequestContructor = Omit<ApiPageableRequest, 'toServer'>;

export class ApiPageableRequest {
  page: number;
  size: number;
  sort?: ApiSortRequest[];

  constructor(options: ApiPageableRequestContructor) {
    this.page = options.page;
    this.size = options.size;
    this.sort = options.sort;
  }

  toServer(): { page: number; size: number; sort?: ApiSortRequest } {
    return {
      page: this.page - 1,
      size: this.size,
      sort: this.sort[0] || {
        direction: 'ASC',
        field: 'id'
      }
    };
  }
}

export interface ApiSortRequest {
  direction: 'DESC' | 'ASC';
  field: string;
}
