/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Injectable } from '@angular/core';
import { DateInput, Form, SelectInput, TextareaInput, TextInput } from '@shared/form';
import { BookingService } from '@pages/booking/services/booking.service';
import { AbstractFormHelperService } from '@core/helper/abstract-form-helper.service';

@Injectable({
  providedIn: 'root'
})
export class FormHelperService extends AbstractFormHelperService {
  size = '12|6|6|4|4';

  constructor(
    private bookingService: BookingService
  ) {
    super();
    this._initForms();
  }

  protected _initForms() {
    this._items.set(
      'booking',
      this._getBookingForm.bind(this)
    );
  }

  protected _getBookingForm() {
    const form = new Form({
      header: { show: false },
      filter: true,
      controls: {
        startDate: new DateInput({
          label: 'BOOKING.FORM.FIELDS.START_DATE.LABEL',
          placeholder: 'BOOKING.FORM.FIELDS.START_DATE.PLACEHOLDER',
          size: this.size,
          clearable: true
        }),
        endDate: new DateInput({
          label: 'BOOKING.FORM.FIELDS.END_DATE.LABEL',
          placeholder: 'BOOKING.FORM.FIELDS.END_DATE.PLACEHOLDER',
          size: this.size,
          clearable: true
        }),
        address: new TextInput({
          type: 'text',
          label: 'BOOKING.FORM.FIELDS.ADDRESS.LABEL',
          placeholder: 'BOOKING.FORM.FIELDS.ADDRESS.PLACEHOLDER',
          size: this.size,
          clearable: true
        }),
        city: new SelectInput({
          label: 'BOOKING.FORM.FIELDS.CITY.LABEL',
          placeholder: 'BOOKING.FORM.FIELDS.CITY.PLACEHOLDER',
          options: this.bookingService.getCity(),
          multiple: true,
          size: this.size,
          clearable: true
        }),
        amount: new TextInput({
          type: 'text',
          label: 'BOOKING.FORM.FIELDS.AMOUNT.LABEL',
          placeholder: 'BOOKING.FORM.FIELDS.AMOUNT.PLACEHOLDER',
          size: this.size,
          clearable: true
        }),
        dicountCode: new TextInput({
          type: 'text',
          label: 'BOOKING.FORM.FIELDS.DISCOUNT_CODE.LABEL',
          placeholder: 'BOOKING.FORM.FIELDS.DISCOUNT_CODE.PLACEHOLDER',
          size: this.size,
          clearable: true
        }),
        notes: new TextareaInput({
          label: 'BOOKING.FORM.FIELDS.NOTES.LABEL',
          placeholder: 'BOOKING.FORM.FIELDS.NOTES.PLACEHOLDER',
          size: '12|12|12|12|12',
          clearable: true
        })
      }
    });
    return form;
  }
}
