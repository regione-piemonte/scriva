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
  selector: 'app-stepper-footer',
  templateUrl: './stepper-footer.component.html',
  styleUrls: ['./stepper-footer.component.scss']
})
export class StepperFooterComponent implements OnInit {

  @Input() showNext;
  @Input() showPrevious;
  @Input() disableNext;

  @Output() avanti = new EventEmitter();
  @Output() indietro = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  onAvanti() {
    this.avanti.emit();
  }

  onIndietro() {
    this.indietro.emit();
  }

}
