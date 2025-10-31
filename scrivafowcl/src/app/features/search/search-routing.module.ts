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

import { MainComponent } from './pages/main/main.component';
import { GestioneAbilitazioniComponent } from './pages/gestione-abilitazioni/gestione-abilitazioni.component';
import { RevocaDelegaComponent } from './pages/revoca-delega/revoca-delega.component';
import { ConclusioneProcedimentoComponent } from './pages/conclusione-procedimento/conclusione-procedimento.component';
// import { PresaInCaricoComponent } from './pages/presa-in-carico/presa-in-carico.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
  },
  {
    path: 'ultima',
    component: MainComponent, 
    data: {ultimaRicerca: true}
  },
  {
    path: 'semplice/:simplesearch',
    component: MainComponent,
  },
  {
    path: 'abilitazioni',
    component: GestioneAbilitazioniComponent,
  },
  {
    path: 'concludi-proc',
    component: ConclusioneProcedimentoComponent,
  },/* 
  {
    path: 'prendi-in-carico',
    component: PresaInCaricoComponent,
  }, */
  {
    path: 'revoca-delega',
    component: RevocaDelegaComponent,
  },
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SearchRoutingModule {}
