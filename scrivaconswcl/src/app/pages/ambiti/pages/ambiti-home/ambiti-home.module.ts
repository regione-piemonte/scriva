/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdempimentiListComponent } from './main/adempimenti-list.component';
import { SharedModule } from '@shared/shared.module';
import { ThemeModule } from '@theme/theme.module';
import { TranslateModule } from '@eng-ds/translate';
import { AmbitiHomeRoutingModule } from './ambiti-home-routing.module';

@NgModule({
  declarations: [AdempimentiListComponent],
  exports: [AdempimentiListComponent],
  imports: [
    CommonModule,
    SharedModule,
    ThemeModule,
    TranslateModule,
    AmbitiHomeRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: []
})
export class AmbitiHomeModule {
}
