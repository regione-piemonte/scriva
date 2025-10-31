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
import { TranslateModule } from '@eng-ds/translate';
import { ThemeModule } from '@theme/theme.module';
import { SharedModule } from '@shared/shared.module';
import { CommonModule } from '@angular/common';
import { BookingResolver } from '@pages/booking/booking.resolve';
import { BookingService } from '@pages/booking/services/booking.service';
import { BookingComponent } from '@pages/booking/booking.component';
import { BookingRoutingModule } from '@pages/booking/booking-routing.module';

@NgModule({
  declarations: [BookingComponent],
  exports: [BookingComponent],
  imports: [
    CommonModule,
    SharedModule,
    ThemeModule,
    TranslateModule,
    BookingRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    BookingService,
    BookingResolver
  ]
})
export class BookingModule {
}
