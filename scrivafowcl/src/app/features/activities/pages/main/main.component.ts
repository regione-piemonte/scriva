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
import { AutoUnsubscribe } from 'src/app/core/components';

@Component({
  selector: 'app-activities',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent extends AutoUnsubscribe implements OnInit {
  constructor() {
    super();
  }

  ngOnInit(): void {}
}
