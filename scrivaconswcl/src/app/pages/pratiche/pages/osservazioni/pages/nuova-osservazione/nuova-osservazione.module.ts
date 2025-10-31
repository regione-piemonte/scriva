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

import { NuovaOsservazioneRoutingModule } from './nuova-osservazione-routing.module';
import { RiepilogoOsservazioniComponent } from '../../components/riepilogo-osservazioni/riepilogo-osservazioni/riepilogo-osservazioni.component';
import { OsservazioniComponent } from '../../osservazioni.component';
import { NuovaOsservazioneComponent } from './nuova-osservazione.component';
import { FormsModule } from '@angular/forms';
import { NotificationModule } from '@app/shared/notification/notification.module';


@NgModule({
  declarations: [
    NuovaOsservazioneComponent,
    RiepilogoOsservazioniComponent,
    OsservazioniComponent
  ],
  imports: [
    CommonModule,
    ThemeModule,
    TranslateModule,
    SharedModule,
    NuovaOsservazioneRoutingModule,
    FormsModule,
    NotificationModule
  ]
})
export class NuovaOsservazioneModule {}
