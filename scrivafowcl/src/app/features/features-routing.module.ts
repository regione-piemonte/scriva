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
import { AuthGuard } from '../shared/guards/auth.guard';
import { ProfiloGuard } from '../shared/guards/profilo/profilo.guard';
import { AccessDeniedComponent } from './access-denied/access-denied.component';

const routes: Routes = [
  {
    path: 'accreditamento',
    loadChildren: () =>
      import('./accreditamento/accreditamento.module').then(
        (m) => m.AccreditamentoModule
      ),
  },
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then((m) => m.HomeModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'azione-avanzata',
    loadChildren: () =>
      import('./advanced-actions/advanced-actions.module').then((m) => m.AdvancesActionsModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'notifiche',
    loadChildren: () =>
      import('./notifiche/notifiche.module').then((m) => m.NotificheModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'profilo',
    loadChildren: () =>
      import('./profilo/profilo.module').then((m) => m.ProfiloModule),
    canActivate: [AuthGuard, ProfiloGuard],
  },
  {
    path: 'ambito/:codAmbito',
    loadChildren: () =>
      import('./ambito/ambito.module').then((m) => m.AmbitoModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'impostazioni',
    loadChildren: () =>
      import('./settings/settings.module').then((m) => m.SettingsModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'contatti',
    loadChildren: () =>
      import('./contacts/contacts.module').then((m) => m.ContactsModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'documenti',
    loadChildren: () =>
      import('./documents/documents.module').then((m) => m.DocumentsModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'attivita',
    loadChildren: () =>
      import('./activities/activities.module').then((m) => m.ActivitiesModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'ricerca',
    loadChildren: () =>
      import('./search/search.module').then((m) => m.SearchModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'adempimenti',
    loadChildren: () =>
      import('./adempimenti/adempimenti.module').then(
        (m) => m.AdempimentiModule
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'pagamento-istanza',
    loadChildren: () => import('./ppay/ppay.module').then((m) => m.PpayModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'accesso-negato',
    component: AccessDeniedComponent
    
  },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FeaturesRoutingModule {}
