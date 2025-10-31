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
import { ApiClient } from '@eng-ds/api-client';
import { SelectOption } from '@app/shared/form';
import { Observable, of } from 'rxjs';
import { ApiPageableResponse } from '@app/core';
import { Booking } from '@pages/booking/model';

@Injectable()
export class BookingService {
  constructor(private apiClient: ApiClient) {
  }

  get(): Observable<ApiPageableResponse<Booking>> {
    return this.apiClient.request('getBookings');
  }

  getDetail(id: number): Observable<Booking> {
    return this.apiClient.request('getBookingDetail', null, { id });
  }

  deleteBooking(booking: Booking) {
    return this.apiClient.request<any>('deleteBooking', null, { id: booking.id });
  }

  createBooking(booking: Booking) {
    // tslint:disable-next-line:no-console
    console.log('[createBooking.body]', booking);
    return this.apiClient.request<any>('createBooking', booking);
  }

  updateBooking(id: number, booking: Booking) {
    // tslint:disable-next-line:no-console
    console.log('[updateBooking.body]', id, booking);
    return this.apiClient.request<any>('updateBooking', booking, null, { id });
  }

  getUser(): Observable<SelectOption<number, string>[]> {
    // return this.apiClient.request('getBookingUser');
    return of([
      { id: 1, denominazione: 'Utente 1' },
      { id: 2, denominazione: 'Utente 2' },
      { id: 3, denominazione: 'Utente 3' },
      { id: 4, denominazione: 'Utente 4' }
    ]);
  }

  getCompany(): Observable<SelectOption<number, string>[]> {
    // return this.apiClient.request('getBookingUser');
    return of([
      { id: 1, denominazione: 'Azienda 1' },
      { id: 2, denominazione: 'Azienda 2' },
      { id: 3, denominazione: 'Azienda 3' },
      { id: 4, denominazione: 'Azienda 4' }
    ]);
  }

  getType(): Observable<SelectOption<number, string>[]> {
    // return this.apiClient.request('getBookingUser');
    return of([
      { id: 1, denominazione: 'Tipo 1' },
      { id: 2, denominazione: 'Tipo 2' },
      { id: 3, denominazione: 'Tipo 3' },
      { id: 4, denominazione: 'Tipo 4' }
    ]);
  }

  getCity(): Observable<SelectOption<number, string>[]> {
    // return this.apiClient.request('getBookingUser');
    return of([
      { id: 1, denominazione: 'Milano' },
      { id: 2, denominazione: 'Palermo' },
      { id: 3, denominazione: 'Roma' },
      { id: 4, denominazione: 'Firenze' }
    ]);
  }
}
