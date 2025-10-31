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
import { Help } from '../../../../shared/models';

@Component({
  selector: 'header-help-quadro',
  templateUrl: './header-help-quadro.component.html',
  styleUrls: ['./header-help-quadro.component.scss'],
})
export class HeaderHelpQuadroComponent implements OnInit {
  /** Input Help con l'oggetto di Help per la visualizzazione delle informazioni. */
  @Input('help') helpHeader: Help;

  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * ngOnInit.
   */
  ngOnInit() {}
}
