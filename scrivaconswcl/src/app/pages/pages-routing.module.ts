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
import { AuthGuard, HomeGuard } from '@app/core';
import { NotAuthorizedComponent } from '@pages/not-authorized/not-authorized.component';
import { PagesComponent } from '@pages/pages.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ServiceHomeComponent } from '@pages/service-home/service-home.component';
import { ContattiComponent } from './contatti/contatti.component';

const routes: Routes = [
  {
    path: '',
    component: PagesComponent,
    children: [
      {
        path: 'service-home',
        data: {
          permission: 'home',
          resource: 'service-home'
        },
        canActivate: [HomeGuard],
        component: ServiceHomeComponent
      },
      {
        path: 'home',
        data: {
          permission: 'home',
          resource: 'home'
        },
        canActivate: [HomeGuard],
        loadChildren: () =>
          import('./home/home.module').then((m) => m.HomeModule)
      },
      {
        path: 'booking',
        loadChildren: () =>
          import('./booking/booking.module').then((m) => m.BookingModule)
      },
      {
        path: 'ambito',
        loadChildren: () =>
          import('./ambiti/ambiti.module').then((m) => m.AmbitiModule)
      },
      {
        path: 'procedimenti',
        loadChildren: () =>
          import('./pratiche/pratiche.module').then((m) => m.PraticheModule)
      },
      {
        path: 'avvisi',
        loadChildren: () =>
          import('./avvisi/avvisi.module').then((m) => m.AvvisiModule)
      },
      {
        path: 'osservazioni',
        loadChildren: () =>
          import('./osservazioni/osservazioni.module').then(
            (m) => m.OsservazioniModule
          ),
        canActivate: [AuthGuard],
        data: {
          permission: 'view',
          resource: 'osservazioni'
        }
      },
      {
        path: 'contatti',
        component: ContattiComponent
      },
      {
        path: 'not-authorized',
        component: NotAuthorizedComponent
      },
      {
        path: '',
        redirectTo: 'service-home',
        pathMatch: 'full'
      },
      {
        path: '**',
        component: NotFoundComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule {}
