/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, OnInit } from '@angular/core';
import { BaseComponent } from '@core/components';

@Component({
  selector: 'app-main-booking',
  template: '<router-outlet></router-outlet>'
})
export class BookingComponent extends BaseComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
