/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BaseComponent } from '@core/components';
import { RicercaStore } from '@pages/pratiche/services/ricerca.store';
import { NotificationService } from '@shared/notification/notification.service';

@Component({
  selector: 'app-main-pratiche',
  template: '<router-outlet></router-outlet>'
})
export class PraticheComponent extends BaseComponent implements OnInit, OnDestroy {
  constructor(private store: RicercaStore) {
    super();
  }

  ngOnInit() {}

  ngOnDestroy() {
    super.ngOnDestroy();
    this.store.clear();
  }
}
