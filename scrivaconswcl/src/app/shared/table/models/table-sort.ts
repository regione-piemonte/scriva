/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
export class TableSort {
  dir: 'desc' | 'asc';
  prop: string;

  constructor(options: Partial<TableSort>) {
    this.dir = options.dir;
    this.prop = options.prop;
  }
}
