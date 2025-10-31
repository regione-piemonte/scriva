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
import { Istanza } from 'src/app/shared/models';

@Component({
  selector: 'advanced-action-riepilogo-istanza',
  templateUrl: './advanced-action-riepilogo-istanza.component.html',
  styleUrls: ['./advanced-action-riepilogo-istanza.component.scss'],
})
export class AdvancedActionRiepilogoIstanza implements OnInit {
  
  /** Input string che definisce la denomincazione del titolare dell'istanza. */
  @Input() denTitolareIstanza: string = '';

  @Input() istanza: Istanza;


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
