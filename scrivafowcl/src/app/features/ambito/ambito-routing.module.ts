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
import { AmbitoDashboardComponent } from './pages/ambito-dashboard/ambito-dashboard.component';
import { PresentazioneIstanzaComponent } from './pages/presentazione-istanza/presentazione-istanza.component';
import { OrientamentoIstanzaComponent } from './pages/orientamento-istanza/orientamento-istanza.component';
// import { TipoAdempimentoResolver } from './services';

const routes: Routes = [
  {
    path: '',
    component: AmbitoDashboardComponent,
  },
  {
    path: 'orientamento/:codTipoAdempimento',
    component: OrientamentoIstanzaComponent,
    // resolve: { tipoAdempimento: TipoAdempimentoResolver },
  },
  {
    path: 'istanza/:codAdempimento',
    component: PresentazioneIstanzaComponent,
    // resolve: { adempimento: ... }
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AmbitoRoutingModule {}
