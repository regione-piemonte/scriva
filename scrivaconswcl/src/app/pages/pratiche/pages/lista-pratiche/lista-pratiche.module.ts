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
import { ListaPraticheRoutingModule } from './lista-pratiche-routing.module';
import { ListaPraticheComponent } from '@pages/pratiche/pages/lista-pratiche/pages/lista-pratiche/lista-pratiche.component';

@NgModule({
  declarations: [
    ListaPraticheComponent
  ],
  imports: [
    CommonModule,
    ThemeModule,
    TranslateModule,
    SharedModule,
    ListaPraticheRoutingModule
  ]
})
export class ListaPraticheModule {
}
