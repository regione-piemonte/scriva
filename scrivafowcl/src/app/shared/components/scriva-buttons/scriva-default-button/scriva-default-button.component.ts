/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component } from '@angular/core';
import { ScrivaButtonComponent } from '../scriva-button/scriva-button.component';

@Component({
  selector: 'scriva-default-button',
  templateUrl: './scriva-default-button.component.html',
  styleUrls: ['./scriva-default-button.component.scss'],
})
export class ScrivaDefaultButtonComponent extends ScrivaButtonComponent {
  /**
   * Costruttore
   */
  constructor() {
    super();
  }
}
