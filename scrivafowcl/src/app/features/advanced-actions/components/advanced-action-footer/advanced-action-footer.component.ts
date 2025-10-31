/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'advanced-action-footer',
  templateUrl: './advanced-action-footer.component.html',
  styleUrls: ['./advanced-action-footer.component.scss'],
})
export class AdavancedActionFooterComponent implements OnInit {
  
  @Input() showAnnulla:boolean  = false;
  @Input() showConferma:boolean  = true;
  @Input() showIndietro:boolean  = true;
  @Input() disable:boolean  = false;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<string>();
  
  /**
   * Costruttore.
   */
  constructor() {}

  /**
   * NgOnInit.
   */
  ngOnInit() {
  }

  /**
   * Propago evento verso alto verso componente che contiene il breadcrumb
   * @param ev stringa che dice quale evento deve essere emesso
   */
  clickEmit(ev:string) {
    this.emit.next(ev);
  }

}
