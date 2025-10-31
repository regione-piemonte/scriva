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
import { RicercaPraticheRoutingModule } from './ricerca-pratiche-routing.module';
import { SearchFormHelperService } from './helper/search-form-helper';
import { RicercaPraticheComponent } from './pages/ricerca-pratiche/ricerca-pratiche.component';

@NgModule({
  declarations: [
    RicercaPraticheComponent
  ],
  imports: [
    CommonModule,
    ThemeModule,
    TranslateModule,
    SharedModule,
    RicercaPraticheRoutingModule
  ],
  providers: [
    SearchFormHelperService
  ]
})
export class RicercaPraticheModule {
}
