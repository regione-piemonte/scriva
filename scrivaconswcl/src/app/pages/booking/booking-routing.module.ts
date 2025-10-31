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
import { BookingComponent } from '@pages/booking/booking.component';
import { BookingResolver } from '@pages/booking/booking.resolve';

const routes: Routes = [
  {
    path: '',
    component: BookingComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./pages/booking-list/booking-list.module').then((m) => m.BookingListModule),
        data: {
          breadcrumbs: [
            {
              label: 'BOOKING.LIST.TITLE',
              href: undefined
            }
          ],
          headerLabel: 'BOOKING.LIST.TITLE'
        }
      },
      {
        path: 'new',
        loadChildren: () =>
          import('./pages/booking-item/booking-item.module').then((m) => m.BookingItemModule),
        data: {
          breadcrumbs: [
            {
              label: ('BOOKING.LIST.TITLE'),
              href: '/booking'
            },
            {
              label: ('BOOKING.NEW.TITLE'),
              href: undefined
            }
          ],
          headerLabel: 'BOOKING.NEW.TITLE'
        }
      },
      {
        path: ':id/edit',
        loadChildren: () =>
          import('./pages/booking-item/booking-item.module').then((m) => m.BookingItemModule),
        resolve: { booking: BookingResolver },
        data: {
          breadcrumbs: [
            {
              label: ('BOOKING.LIST.TITLE'),
              href: '/booking'
            },
            {
              label: ('BOOKING.EDIT.TITLE'),
              href: undefined
            }
          ],
          headerLabel: 'BOOKING.EDIT.TITLE'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingRoutingModule {
}
