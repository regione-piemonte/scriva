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
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { ModalComponent } from '@app/shared/modal/models/modal.component';

@Component({
  selector: 'app-help-modal',
  templateUrl: './help-modal.component.html',
  styleUrls: ['./help-modal.component.scss']
})
export class HelpModalComponent extends ModalComponent implements OnInit {
  @Input() helpOBJ;

  constructor(
    private praticheService: PraticheService
  ) {
    super();
  }

  ngOnInit(): void { }

}
