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
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.scss']
})
export class ConfirmModalComponent implements OnInit {
  @Input() data;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit() {
    const styleRules = document.createElement('style');
    styleRules.innerHTML = '.modal-body > span > h3 { font-size: 1rem; line-height: 1.5; margin: 0; }';
    document.body.appendChild(styleRules);
  }

}
