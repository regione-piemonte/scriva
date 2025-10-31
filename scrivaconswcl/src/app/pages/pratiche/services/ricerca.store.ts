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

@Injectable({
  providedIn: 'root'
})
export class RicercaStore {
  private formRicerca$: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  private searched$: BehaviorSubject<boolean> = new BehaviorSubject<any>(false);
  private lastSearchQuery: any;

  constructor() {}

  saveSearch(form: any) {
    this.lastSearchQuery = form;
    this.formRicerca$.next(form);
    this.searched$.next(true);
  }

  clear() {
    this.formRicerca$.next(null);
    this.searched$.next(false);
  }

  getSearched() {
    return this.searched$.asObservable();
  }

  getSearchForm() {
    return this.formRicerca$.asObservable();
  }

  getLastSearch(): any {
    return this.lastSearchQuery;
  }
}
