/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingListComponent } from './main/booking-list.component';
import { SharedModule } from '@shared/shared.module';
import { ThemeModule } from '@theme/theme.module';
import { TranslateModule } from '@eng-ds/translate';
import { BookingListRoutingModule } from './booking-list-routing.module';
import { DeleteBookingModalComponent } from './components/delete-areafunzionale-modal/delete-booking-modal.component';

@NgModule({
  declarations: [BookingListComponent, DeleteBookingModalComponent],
  exports: [BookingListComponent],
  imports: [
    CommonModule,
    SharedModule,
    ThemeModule,
    TranslateModule,
    BookingListRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: []
})
export class BookingListModule {
}
