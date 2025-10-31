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
import { HomeRoutingModule } from './home-routing.module';
import { ThemeModule } from '../../theme/theme.module';
import { TranslateModule } from '@eng-ds/translate';
import { HomeComponent } from './home.component';
import { SharedModule } from '../../shared/shared.module';
import { HomeService } from './services/home.service';
@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    ThemeModule,
    TranslateModule,
    HomeRoutingModule,
    SharedModule
  ],
  providers: [
    HomeService
  ]
})
export class HomeModule {
}
