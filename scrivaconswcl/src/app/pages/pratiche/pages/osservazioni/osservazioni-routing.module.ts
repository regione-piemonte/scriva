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
import { NuovaOsservazioneResolver } from '../../services/nuova-osservazione.resolve';
import { OsservazioniComponent } from './osservazioni.component';

const routes: Routes = [
  {
    path: '',
    component: OsservazioniComponent,
    children: [
      {
        path: 'nuova',
        resolve: {
          pratica: NuovaOsservazioneResolver
        },
        loadChildren: () =>
          import('./pages/nuova-osservazione/nuova-osservazione.module').then(
            (m) => m.NuovaOsservazioneModule
          )
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OsservazioniRoutingModule { }
