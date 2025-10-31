/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { DropDownItem, Icon } from '@shared/models';
import { BaseComponent } from '@app/core';
import { AfterViewInit, Component, Inject, LOCALE_ID, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Booking } from '@pages/booking/model';
import { BookingService } from '@pages/booking/services/booking.service';
import { I18nService } from '@eng-ds/translate';
import { TableColumn } from '@shared/table/models/column.model';
import { LoadingService } from '@theme/layouts/loading.services';
import { TablePage } from '@shared/table/models/table-page';
import { ModalService } from '@shared/modal/modal.service';
import { DeleteBookingModalComponent } from '@pages/booking/pages/booking-list/components/delete-areafunzionale-modal/delete-booking-modal.component';
import { Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { LocalPagedDataSource } from '@shared/table/models/local-paged-data-source';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.scss']
})
export class BookingListComponent extends BaseComponent implements OnInit, AfterViewInit {
  @ViewChild('userTemplate') userTemplate: TemplateRef<any>;
  @ViewChild('settingsTemplate') settingsTemplate: TemplateRef<any>;

  iconDropDownMenu: Icon = {
    name: 'eng-more-items',
    type: 'eng',
    cssClass: 'cursor-pointer align-top',
    fill: '#005095',
    size: 'small'
  };

  dropdownMenu: DropDownItem[] = [];
  columns: TableColumn[] = [];

  dataSource: LocalPagedDataSource<Booking> = new LocalPagedDataSource({
    observable: this.bookingService.get.bind(this.bookingService),
    tablePage: new TablePage()
  });

  pipeFormatDate = {
    transform: (value: string) =>
      formatDate(value, 'dd/MM/yyyy ', this.locale)
  };

  constructor(
    private i18n: I18nService,
    private bookingService: BookingService,
    private loadingService: LoadingService,
    private router: Router,
    private modalService: ModalService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    this._initDropdown();
  }

  ngAfterViewInit(): void {
    this._initTable();
  }

  getNumberOfElements(): number {
    return this.dataSource?.tablePage.totalElements || 0;
  }

  onDelete(row) {
    this.modalService.openDialog(DeleteBookingModalComponent, {
      header: this.i18n.translate('BOOKING.DELETE.TITLE'),
      showCloseButton: true,
      context: { booking: row.optContent }
    });
  }

  onEdit(row) {
    this.router.navigate(['booking', row.optContent.id, 'edit']);
  }

  private _initDropdown() {
    this.dropdownMenu.push(
      {
        icon: {
          name: 'eng-edit',
          type: 'eng',
          cssClass: 'cursor-pointer mx-2 mt-1',
          fill: '#FFFFFF',
          size: 'small'
        },
        label: this.i18n.translate('BOOKING.LIST.DROPDOWN.EDIT'),
        onClick: this.onEdit.bind(this)
      },
      {
        icon: {
          name: 'eng-delete',
          type: 'eng',
          cssClass: 'cursor-pointer mx-2 mt-1',
          fill: '#FFFFFF',
          size: 'small'
        },
        label: this.i18n.translate('BOOKING.LIST.DROPDOWN.DELETE'),
        onClick: this.onDelete.bind(this)
      }
    );

  }

  private _initTable(): void {
    this.columns = [
      {
        prop: 'startDate',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.START_DATE'),
        pipe: this.pipeFormatDate
      },
      {
        prop: 'endDate',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.END_DATE'),
        pipe: this.pipeFormatDate
      },
      {
        prop: 'bookingDate',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.BOOKING_DATE'),
        pipe: this.pipeFormatDate
      },
      {
        prop: 'id',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.ID'),
        sortable: false
      },

      {
        prop: 'address',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.ADDRESS')
      },
      {
        prop: 'city',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.CITY')
      },
      {
        prop: 'user.fullName',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.USER_FULLNAME'),
        cellTemplate: this.userTemplate
      },
      {
        prop: 'user.company',
        name: this.i18n.translate('BOOKING.LIST.FIELDS.COMPANY')
      },
      {
        prop: 'setting',
        name: this.i18n.translate('UTILS.TABLE.ACTIONS'),
        cellTemplate: this.settingsTemplate,
        sortable: false
      }
    ];
  }
}
