/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component(
  {
    template: ''
  }
)
// tslint:disable-next-line:component-class-suffix
export abstract class AutoUnsubscribe implements OnDestroy {
  destroy$: Subject<boolean> = new Subject<boolean>();

  constructor() {
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.unsubscribe();
  }
}
