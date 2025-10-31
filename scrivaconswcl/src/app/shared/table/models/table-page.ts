/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export class TablePage {
  // The number of elements in the page
  size = 10;
  // The total number of elements
  totalElements = 0;
  // The total number of pages
  totalPages = 0;
  // The current page number
  pageNumber = 1;

  constructor(options?: Partial<TablePage>) {
    if (options?.size) {
      this.size = options.size;
    }

    if (
      options &&
      options?.pageNumber !== undefined &&
      options?.pageNumber !== null
    ) {
      this.pageNumber = options.pageNumber;
    }
  }
}
