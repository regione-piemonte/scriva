/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { AutoUnsubscribe } from './auto-unsubscribe.component';
import { Component } from '@angular/core';

@Component(
  {
    template: '',
  },
)
// tslint:disable-next-line:component-class-suffix
export abstract class BaseComponent extends AutoUnsubscribe {
  constructor() {
    super();
  }
}
