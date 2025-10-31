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
import { ThemeModule } from '@theme/theme.module';
import { TranslateModule } from '@eng-ds/translate';
import { SharedModule } from '@shared/shared.module';
import { AvvisiComponent } from './pages/avvisi/avvisi.component';
import { AvvisiRoutingModule } from './avvisi-routing.module';

@NgModule({
  declarations: [
    AvvisiComponent
  ],
  imports: [
    CommonModule,
    ThemeModule,
    TranslateModule,
    SharedModule,
    AvvisiRoutingModule
  ]
})
export class AvvisiModule {
}
