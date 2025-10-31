/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from './components/alert/alert.component';
import { SharedComponentsModule } from '../components/shared-components.module';
import { AlertService } from './alert.service';
import { TranslateModule } from '@eng-ds/translate';


@NgModule({
  declarations: [AlertComponent],
  imports: [
    CommonModule,
    SharedComponentsModule,
    TranslateModule
  ],
  providers: [
    AlertService
  ]
})
export class AlertModule {
}
