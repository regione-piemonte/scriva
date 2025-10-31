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
import { AuthGuard } from '@app/core';
import { PraticheComponent } from '@pages/pratiche/pratiche.component';

const routes: Routes = [
  {
    path: '',
    component: PraticheComponent,
    children: [
      {
        path: 'ricerca',
        loadChildren: () =>
          import('./pages/ricerca-pratiche/ricerca-pratiche.module').then(
            (m) => m.RicercaPraticheModule
          )
      },
      {
        path: ':id_ambito',
        children: [
          {
            path: ':id_tipo_adempimento',
            loadChildren: () =>
              import('./pages/lista-pratiche/lista-pratiche.module').then(
                (m) => m.ListaPraticheModule
              )
          },
          {
            path: ':id_tipo_adempimento/:id_competenza_territorio/competenza-territorio',
            loadChildren: () =>
              import('./pages/lista-pratiche/lista-pratiche.module').then(
                (m) => m.ListaPraticheModule
              )
          },
          {
            path: ':id_tipo_adempimento/:id_istanza',
            loadChildren: () =>
              import('./pages/dettaglio-pratica/dettaglio-pratica.module').then(
                (m) => m.DettaglioPraticaModule
              )
          },
          {
            path: ':id_tipo_adempimento/:id_istanza/osservazioni',
            loadChildren: () =>
              import('./pages/osservazioni/osservazioni.module').then(
                (m) => m.OsservazioniModule
              ),
            canActivate: [AuthGuard],
            data: {
              permission: 'create',
              resource: 'osservazioni'
            }
          }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PraticheRoutingModule {}
