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

import { BookingItemComponent } from './main/booking-item.component';

const routes: Routes = [
  {
    path: '',
    component: BookingItemComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingItemRoutingModule {
}
