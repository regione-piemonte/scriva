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
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class AmbitoStoreService {

  jsonConfiguraTemplateSub = new BehaviorSubject<any>(null);
  private startingPage: string;

  constructor() {
  }

  setStartingPage(page: string) {
    this.startingPage = page;
  }

  getStartingPage(): string {
    return this.startingPage;
  }
}
