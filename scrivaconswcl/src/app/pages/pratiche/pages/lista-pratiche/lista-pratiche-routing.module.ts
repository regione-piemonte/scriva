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
import { ListaPraticheComponent } from '@pages/pratiche/pages/lista-pratiche/pages/lista-pratiche/lista-pratiche.component';

const routes: Routes = [
  {
    path: '',
    component: ListaPraticheComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ListaPraticheRoutingModule {
}
