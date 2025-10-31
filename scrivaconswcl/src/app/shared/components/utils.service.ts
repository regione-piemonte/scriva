/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UtilsService {

  constructor() {
  }

  isDefined(variable: any): boolean {
    return variable !== undefined;
  }

  isNotNull(variable: any): boolean {
    return variable !== null;
  }

  isNotEmpty(s: string): boolean {
    return s !== '';
  }

  stringIsNotEmpty(s: string): boolean {
    return this.isDefined(s) && this.isNotNull(s) && this.isNotEmpty(s);
  }

}
