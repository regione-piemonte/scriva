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
import { AvvisiComponent } from './pages/avvisi/avvisi.component';

const routes: Routes = [
  {
    path: ':id_ambito/:id_tipo_adempimento',
    component: AvvisiComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AvvisiRoutingModule {
}
