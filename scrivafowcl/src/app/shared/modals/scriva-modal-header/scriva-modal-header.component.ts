/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'scriva-modal-header',
  templateUrl: './scriva-modal-header.component.html',
  styleUrls: ['./scriva-modal-header.component.scss'],
})
export class ScrivaModalHeaderComponent {
  /** Titolo da mostrare nell'header */
  @Input() title: string;
  /** Evento al click della X: chiede al padre di chiudere la modale */
  @Output('onClickClose') onClickClose$ = new EventEmitter<any>();

  constructor() {}

  /**
   * Emette l'evento di chiusura della modale al padre
   */
  modalDismiss() {
    this.onClickClose$.emit();
  }
}
