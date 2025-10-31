/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input, OnInit } from '@angular/core';
import {
  ScrivaIconTextButtonConfig,
  ScrivaIconTextButtonCss,
} from '../../../classes/scriva-utilities/scriva-utilities.classes';
import { ScrivaButtonComponent } from '../scriva-button/scriva-button.component';

@Component({
  selector: 'scriva-icon-text-button',
  templateUrl: './scriva-icon-text-button.component.html',
  styleUrls: ['./scriva-icon-text-button.component.scss'],
})
export class ScrivaIconTextButtonComponent
  extends ScrivaButtonComponent
  implements OnInit
{
  /** Input che definisce le configurazioni per gli stili della input. */
  @Input() cssConfig: ScrivaIconTextButtonCss;
  /** Input che definisce le configurazioni per i dati della input. */
  @Input() dataConfig: ScrivaIconTextButtonConfig;

  /**
   * Costruttore
   */
  constructor() {
    // Lancio il super
    super();
  }

  ngOnInit() {}
}
