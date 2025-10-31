/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { BaseComponent } from '@core/components';
import { FormHelperService } from '@pages/booking/pages/booking-item/helper/form-helper.service';
import { NotificationService } from '@shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';
import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { LoadingService } from '@theme/layouts/loading.services';
import { ActivatedRoute } from '@angular/router';
import { FormMode } from '@core/enums/action-form.enum';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { Form } from '@shared/form';
import { Booking } from '@pages/booking/model';
import { BookingService } from '@pages/booking/services/booking.service';
import { formatDate } from '@angular/common';

@UntilDestroy()
@Component({
  selector: 'app-booking-item',
  templateUrl: './booking-item.component.html',
  styleUrls: ['./booking-item.component.scss']
})
export class BookingItemComponent extends BaseComponent implements OnInit {
  form: Form;
  booking: Booking;

  formMode: FormMode;

  constructor(
    private loadingService: LoadingService,
    private notificationService: NotificationService,
    private i18n: I18nService,
    private formHelperService: FormHelperService,
    private route: ActivatedRoute,
    private service: BookingService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    this._initForms();

    if (this.route.snapshot.data.booking) {
      this.formMode = FormMode.EDIT;
      this.booking = this.route.snapshot.data.booking;
      this._setData(this.route.snapshot.data.booking);
    } else {
      this.formMode = FormMode.CREATE;
    }
  }

  onSave() {
    const toSave = this.form.value;

    if (this.formMode === FormMode.EDIT) {
      this.service.updateBooking(this.booking.id, toSave)
        .pipe(
          untilDestroyed(this)
        )
        .subscribe((response: any) => {
          this.notificationService.success({
            title: this.i18n.translate('BOOKING.EDIT.NOTIFICATION.TITLE'),
            text: this.i18n.translate('BOOKING.EDIT.NOTIFICATION.TEXT')
          });
        });
    }

    if (this.formMode === FormMode.CREATE) {
      this.service.createBooking(toSave)
        .pipe(
          untilDestroyed(this)
        )
        .subscribe((response: any) => {
          this.notificationService.success({
            title: this.i18n.translate('BOOKING.NEW.NOTIFICATION.TITLE'),
            text: this.i18n.translate('BOOKING.NEW.NOTIFICATION.TEXT')
          });
        });
    }
  }

  private _initForms(): void {
    this.form = this.formHelperService.get('booking');
  }

  private _setData(data: Booking): void {
    const form = this.form;
    form.get('startDate').setValue(formatDate(data.startDate, 'yyyy-MM-dd', 'en'));
    form.get('endDate').setValue(formatDate(data.endDate, 'yyyy-MM-dd', 'en'));
    form.get('address').setValue(data.address);
    form.get('amount').setValue(data.amount);
    form.get('dicountCode').setValue(data.dicountCode);
    form.get('city').setValue(data.city.id);
    form.get('notes').setValue(data.notes);
  }

}
