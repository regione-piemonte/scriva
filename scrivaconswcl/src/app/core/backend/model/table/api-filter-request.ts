/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Condition } from './condition';

export abstract class ApiFilterRequest implements Condition {
  field: string;
  operator: string;
  value: any;
  condition?: 'AND' | 'OR' = 'OR';

  constructor(options: Omit<ApiFilterRequest, 'operator'>) {
    this.field = options.field;
    this.value = options.value;

    if (options.condition) {
      this.condition = options.condition;
    }
  }
}
