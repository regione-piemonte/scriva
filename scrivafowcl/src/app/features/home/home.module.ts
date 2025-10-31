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
import { NotificheModule } from '../notifiche/notifiche.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { HomeComponent } from './pages/home/home.component';
import { HomeRoutingModule } from './home-routing.module';
import { HomeService, HomeStoreService } from './services';
import { RiepilogoIstanzeListComponent } from './components';

@NgModule({
  declarations: [HomeComponent, RiepilogoIstanzeListComponent],
  imports: [CommonModule, HomeRoutingModule, NotificheModule, SharedModule],
  providers: [HomeService, HomeStoreService],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HomeModule {}
