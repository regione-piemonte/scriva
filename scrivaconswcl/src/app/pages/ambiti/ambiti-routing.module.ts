/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { AmbitiComponent } from '@pages/ambiti/ambiti.component';
import { TipoAndempimentiResolver } from '@app/pages/ambiti/services/tipo-adempimenti.resolve';

const routes: Routes = [
  {
    path: '',
    component: AmbitiComponent,
    children: [
      {
        path: ':id_ambito',
        resolve: {
          tipiAdempimento: TipoAndempimentiResolver
        },
        loadChildren: () =>
          import('./pages/ambiti-home/ambiti-home.module').then((m) => m.AmbitiHomeModule),
        data: {
          headerLabel: 'AMBITI.LIST.TITLE'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AmbitiRoutingModule {
}
