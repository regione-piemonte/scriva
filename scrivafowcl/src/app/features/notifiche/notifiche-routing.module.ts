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
import { RouterModule, Routes } from '@angular/router';
import { NotificaDettaglioComponent } from './pages/notifica-dettaglio/notifica-dettaglio.component';
import { NotificheComponent } from './pages/notifiche/notifiche.component';
import { NotificaApplicativaResolver } from './resolvers/notifica-applicativa.resolver';

const routes: Routes = [
  {
    path: '',
    component: NotificheComponent,
  },
  {
    path: ':id',
    resolve: {
      notificaApplicativa: NotificaApplicativaResolver,
    },
    component: NotificaDettaglioComponent,
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NotificheRoutingModule {}
