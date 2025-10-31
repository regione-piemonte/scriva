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
import { BookingItemComponent } from './main/booking-item.component';
import { SharedModule } from '@shared/shared.module';
import { ThemeModule } from '@theme/theme.module';
import { TranslateModule } from '@eng-ds/translate';
import { BookingItemRoutingModule } from './booking-item-routing.module';
import { FormHelperService } from './helper/form-helper.service';
import { BookingResolver } from '../../booking.resolve';

@NgModule({
  declarations: [BookingItemComponent],
  exports: [BookingItemComponent],
  imports: [
    CommonModule,
    SharedModule,
    ThemeModule,
    TranslateModule,
    BookingItemRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [FormHelperService, BookingResolver]
})
export class BookingItemModule {
}
