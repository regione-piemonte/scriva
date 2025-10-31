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
import { SharedModule } from '../shared/shared.module';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { AmbitoModule } from './ambito/ambito.module';
import { FeaturesRoutingModule } from './features-routing.module';
import { NotificheModule } from './notifiche/notifiche.module';
import { ProfiloModule } from './profilo/profilo.module';

@NgModule({
  declarations: [AccessDeniedComponent],
  imports: [
    FeaturesRoutingModule,
    SharedModule,
    NotificheModule,
    ProfiloModule,
    AmbitoModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  entryComponents: [],
})
export class FeaturesModule {}
