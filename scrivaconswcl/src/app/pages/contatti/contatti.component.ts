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
import { Router } from '@angular/router';
import { LoadingService } from '@theme/layouts/loading.services';

@Component({
  styleUrls: ['./contatti.component.scss'],
  templateUrl: './contatti.component.html'
})
export class ContattiComponent {
  constructor(private router: Router, private loadingService: LoadingService) {
    this.loadingService.hide();
  }

  goToHome() {
    this.router.navigate(['home']);
  }
}
