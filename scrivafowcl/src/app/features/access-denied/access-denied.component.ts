/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { HttpErrorService } from '../../core/services/http-error-response.service';

@Component({
  selector: 'app-access-denied',
  templateUrl: './access-denied.component.html'
})
export class AccessDeniedComponent implements OnInit {
  constructor(
    private httpErrorService: HttpErrorService
  ) { 

  }

  ngOnInit() {
    // Tramite il servizio resetto errore del 
    this.httpErrorService.resetError();
  }
}
