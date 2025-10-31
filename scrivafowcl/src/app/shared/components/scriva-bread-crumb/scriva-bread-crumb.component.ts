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
  selector: 'scriva-bread-crumb',
  templateUrl: './scriva-bread-crumb.component.html',
  styleUrls: ['./scriva-bread-crumb.component.scss'],
})
export class ScrivaBreadCrumbComponent implements OnInit {
  
  /** Input string che definisce il nome della pagina come titolo. */
  @Input() items: ScrivaBreadCrumbItem[] = [];

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

export interface ScrivaBreadCrumbItem {
  label: string,
  ariaLabel? : string;
  emitAction? : string
}