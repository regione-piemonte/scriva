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
import { DettaglioPraticaComponent } from '@pages/pratiche/pages/dettaglio-pratica/pages/dettaglio-pratica/dettaglio-pratica.component';
import { MappaPraticaComponent } from './components/mappa-pratica/mappa-pratica.component';
import { DettaglioPraticaResolver } from './resolvers/dettaglio-pratica.resolver';
import { MapPraticaResolver } from './resolvers/map-pratica.resolver';
import { NuovaOsservazioneResolver } from '@pages/pratiche/services/nuova-osservazione.resolve';

const routes: Routes = [
  {
    path: '',
    component: DettaglioPraticaComponent,
    resolve: {
      pratica: DettaglioPraticaResolver
    }
  },
  {
    path: 'mappa',
    component: MappaPraticaComponent,
    resolve: {
      pratica: DettaglioPraticaResolver,
      mappa: MapPraticaResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DettaglioPraticaRoutingModule {}
