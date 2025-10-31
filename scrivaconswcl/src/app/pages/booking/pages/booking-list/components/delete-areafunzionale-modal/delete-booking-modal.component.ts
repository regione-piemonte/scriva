/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input, OnInit } from '@angular/core';
import { LoadingService } from '@theme/layouts/loading.services';
import { NotificationService } from '@shared/notification/notification.service';
import { I18nService } from '@eng-ds/translate';
import { ModalComponent } from '@shared/modal/models/modal.component';
import { Booking } from '@pages/booking/model';
import { BookingService } from '@pages/booking/services/booking.service';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';

@UntilDestroy()
@Component({
  selector: 'app-delete-booking-modal',
  templateUrl: './delete-booking-modal.component.html',
  styleUrls: ['./delete-booking-modal.component.scss']
})
export class DeleteBookingModalComponent extends ModalComponent implements OnInit {
  @Input() booking: Booking;

  constructor(private loadingService: LoadingService,
              private bookingService: BookingService,
              private notificationService: NotificationService,
              private i18n: I18nService) {
    super();
  }

  ngOnInit(): void {
  }

  onDismiss() {
    this.modalContainer.close();
  }

  onDelete() {
    this.loadingService.show();
    this.bookingService.deleteBooking(this.booking)
      .pipe(
        untilDestroyed(this)
      )
      .subscribe((response) => {
        this.loadingService.hide();
        this.notificationService.success({
          title: this.i18n.translate('BOOKING.DELETE.NOTIFICATION.TITLE'),
          text: this.i18n.translate('BOOKING.DELETE.NOTIFICATION.TEXT')
        });
        this.modalContainer.close();
      });
  }
}
