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
import { Observable } from 'rxjs';

import { Help } from './utilities/help.interfaces';
import { UtilityService } from '@app/core';

@Injectable({ providedIn: 'root' })
export class HelpService {
  private codMaschera: string;

  constructor(private utilityService: UtilityService) {}

  getHelpByChiave(chiave: string): Observable<Help[]> {
    return this.utilityService.getHelp(chiave);
  }

  getCodMaschera(): string {
    return this.codMaschera;
  }

  setCodMaschera(codMasc: string) {
    this.codMaschera = codMasc;
  }
}
