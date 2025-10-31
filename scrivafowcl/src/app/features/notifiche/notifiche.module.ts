/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { NotificaCardComponent } from './components/notifica-card/notifica-card.component';
import { NotificheButtonCountComponent } from './components/notifiche-button-count/notifiche-button-count.component';
import { NotificheHomeComponent } from './components/notifiche-home/notifiche-home.component';
import { NotificheSearchComponent } from './components/notifiche-search/notifiche-search.component';
import { NotificheSidebarComponent } from './components/notifiche-sidebar/notifiche-sidebar.component';
import { NotificheRoutingModule } from './notifiche-routing.module';
import { NotificaDettaglioComponent } from './pages/notifica-dettaglio/notifica-dettaglio.component';
import { NotificheComponent } from './pages/notifiche/notifiche.component';
import { NotificaApplicativaResolver } from './resolvers/notifica-applicativa.resolver';

@NgModule({
  declarations: [
    NotificaCardComponent,
    NotificaDettaglioComponent,
    NotificheButtonCountComponent,
    NotificheHomeComponent,
    NotificheComponent,
    NotificheSearchComponent,
    NotificheSidebarComponent,
  ],
  imports: [CommonModule, SharedModule, NotificheRoutingModule],
  exports: [
    NotificheButtonCountComponent,
    NotificheHomeComponent,
    NotificheSidebarComponent,
  ],
  providers: [NotificaApplicativaResolver],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class NotificheModule {}
