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

import { OsservazioniRoutingModule } from './osservazioni-routing.module';
import { NuovaOsservazioneResolver } from '../../services/nuova-osservazione.resolve';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ThemeModule,
    TranslateModule,
    SharedModule,
    OsservazioniRoutingModule
  ],
  providers: [
    NuovaOsservazioneResolver
  ]
})
export class OsservazioniModule { }
