/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  AfterViewInit,
  Component,
  Inject,
  LOCALE_ID,
  TemplateRef,
  ViewChild
} from '@angular/core';
import { BaseComponent, Status, UtilityService } from '@app/core';
import { I18nService } from '@app/core/translate/public-api';
import { StatoIstanza } from '../../enums/stato-istanza';
import { TableColumn } from '@app/shared/table/models/column.model';
import { TablePage } from '@app/shared/table/models/table-page';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { OsservazioniService } from '../../services/osservazioni.service';
import { TableSort } from '@app/shared/table/models/table-sort';
import { formatDate } from '@angular/common';
import { HeaderPagedDataSource } from '@app/shared/table/models/header-paged-data-source';
import { catchError, takeUntil, tap } from 'rxjs/operators';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from '@app/shared/notification/notification.service';
import { throwError } from 'rxjs';
import {
  IstanzaOss,
  Osservazione
} from '@pages/osservazioni/interfaces/osservazione';
import { TruncateTextPipe } from '@app/shared/pipes/truncate-text.pipe';
import { HelpService } from '@app/shared/services/help/help.service';
import { ConswebCodesMesseges } from '@app/core/enums/codes-messagges.enums';
import { NotificationModel } from '@app/shared/notification/model/notification.model';
import { MessageService } from '@app/shared/services/message.service';
import { NotificationIcons } from '@app/shared/notification/components/notification/utilities/notification.enum';

@Component({
  selector: 'app-osservazioni-list',
  templateUrl: './osservazioni-list.component.html',
  styleUrls: ['./osservazioni-list.component.scss']
})
export class OsservazioniListComponent extends BaseComponent {
  codMaschera: string = 'PUBWEB.MSCR008P';

  @ViewChild('codPratica') codPratica: TemplateRef<any>;
  @ViewChild('ademp') ademp: TemplateRef<any>;
  @ViewChild('stato') stato: TemplateRef<any>;
  @ViewChild('settingsTemplate') settingsTemplate: TemplateRef<any>;

  columns: TableColumn[] = [];
  pipeFormatDate = {
    transform: (value: string | Date) => {
      if (!value) return '';

      let date: Date;
      if (value instanceof Date) {
        // È già una data valida
        date = value;
      } else {
        // Converto la stringa ISO in un oggetto Date
        date = new Date(value);
      }

      // Verifico se la conversione ha avuto successo
      if (isNaN(date.getTime())) {
        console.error('Formato data non valido:', value);
        return '';
      }

      return formatDate(date, 'dd/MM/yyyy', this.locale);
    }
  };
  StatoIstanza = StatoIstanza;
  ossInserite = 0;
  ossPubblicate = 0;
  ossInviate = 0;
  ossTotali = 0;
  selectedStatus: StatoIstanza = StatoIstanza.TUTTI;

  dataSource = new HeaderPagedDataSource<any>({
    observable: this.getOsservazioni.bind(this),
    tableSort: [new TableSort({ dir: 'desc', prop: 'data_osservazione' })],
    tablePage: new TablePage()
  });
  ossStati: { label: string; value: string }[] = [];
  elencoTabella;
  notificationErCancOssConfig: NotificationModel;
  listRows: number;
  rows: Osservazione[];

  constructor(
    private i18n: I18nService,
    private loadingService: LoadingService,
    private osservazioniService: OsservazioniService,
    private praticheService: PraticheService,
    private notification: NotificationService,
    private router: Router,
    private helpService: HelpService,
    private conswebMessages: MessageService,
    @Inject(LOCALE_ID) public locale: string,
    private utilityService: UtilityService
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    this.helpService.setCodMaschera(this.codMaschera);
  }

  ngAfterViewInit(): void {
    this._initTable();
  }

  getOsservazioni(filter) {
    return this.osservazioniService
      .getOsservazioni(
        this.selectedStatus === StatoIstanza.TUTTI ? null : this.selectedStatus,
        filter.page,
        filter.size,
        filter.sort
      )
      .pipe(
        tap((res) => {
          this.rows = res.body;
          this.listRows = this.rows.length;
          this.ossStati = [];
          const counter = this.paginationParse(res.headers.get('Counter'));
          let i = 1;
          while (counter[`${i}_NumeroOsservazione`]) {
            this.ossStati.push({
              label: counter[`${i}_StatoOsservazione`],
              value: counter[`${i}_NumeroOsservazione`]
            });
            i += 1;
          }
        })
      );
  }

  paginationParse(paginationInfo: string): Record<string, string> {
    const record = {};
    paginationInfo = paginationInfo.replace('{', '').replace('}', '');
    const paginationElements = paginationInfo.split(', ');
    paginationElements.forEach((el) => {
      const keyvalue = el.split('=');
      record[keyvalue[0]] = keyvalue[1];
    });
    return record;
  }

  private _initTable(): void {
    this.columns = [
      {
        prop: 'istanza.adempimento.des_adempimento',
        rowProp: 'des_adempimento',
        name: this.i18n.translate('OBSERVATIONS.TABLE.ADEMP'),
        cellClass: 'align-middle',
        sortable: true,
        cellTemplate: this.ademp,
        width: 18
      },
      {
        prop: 'istanza.cod_pratica',
        rowProp: 'cod_pratica',
        name: this.i18n.translate('OBSERVATIONS.TABLE.CODE'),
        cellClass: 'align-middle',
        sortable: true,
        cellTemplate: this.codPratica,
        width: 20
      },
      {
        prop: 'den_oggetto',
        rowProp: 'den_oggetto',
        name: this.i18n.translate('OBSERVATIONS.TABLE.NAME'),
        cellClass: 'align-middle',
        sortable: true,
        pipe: new TruncateTextPipe(),
        width: 20
      },
      {
        prop: 'stato_osservazione.des_stato_osservazione',
        rowProp: 'des_stato_osservazione',
        name: this.i18n.translate('OBSERVATIONS.TABLE.STATUS'),
        cellClass: 'align-middle',
        sortable: true,
        cellTemplate: this.stato,
        width: 10
      },
      {
        prop: 'data_osservazione',
        rowProp: 'data_osservazione',
        name: this.i18n.translate('OBSERVATIONS.TABLE.INSERT_DATE'),
        cellClass: 'align-middle',
        sortable: true,
        pipe: this.pipeFormatDate,
        width: 15
      },
      {
        prop: 'istanza.data_pubblicazione',
        rowProp: 'data_pubblicazione',
        name: this.i18n.translate('OBSERVATIONS.TABLE.PUBLISH_DATE'),
        cellClass: 'align-middle',
        sortable: true,
        pipe: this.pipeFormatDate,
        width: 20
      },
      {
        prop: 'setting',
        name: this.i18n.translate('UTILS.TABLE.ACTIONS'),
        cellTemplate: this.settingsTemplate,
        cellClass: 'text-center align-middle p-0',
        sortable: false,
        width: 20
      }
    ];
  }

  onChangeStatus(status: StatoIstanza) {
    this.selectedStatus = status;
    this.dataSource.paging(1);
  }

  onDetail(row): void {
    this.router.navigate(
      [
        '/procedimenti',
        'ND',
        row.istanza.adempimento.cod_tipo_adempimento,
        row.istanza.id_istanza,
        'osservazioni',
        'nuova'
      ],
      {
        queryParams: {
          id: row.id_istanza_osservazione,
          obs: btoa(JSON.stringify(row)),
          fromMyObs: true
        }
      }
    );
  }

  onDelete(row): void {
    this.praticheService
      .deleteOsservazioni(row.id_istanza_osservazione)
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();

          const desAlert = this.conswebMessages.getDesMessaggio(
            ConswebCodesMesseges.E054
          );
          this.notificationErCancOssConfig = {
            title: this.i18n.translate(
              'OSSERVAZIONI.NOTIFICATION.ERROR.DELETE.TITLE'
            ),
            text: desAlert,
            status: Status.error,
            icon: NotificationIcons.ERROR,
            dismissable: false
          };
          return throwError(e);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.dataSource.refresh();
        const desAlert = this.conswebMessages.getDesMessaggio(
          ConswebCodesMesseges.P015
        );
        this.notificationErCancOssConfig = {
          title: this.i18n.translate(
            'OSSERVAZIONI.NOTIFICATION.ERROR.DELETE.TITLE'
          ),
          text: desAlert,
          status: Status.success,
          icon: NotificationIcons.SUCCESS,
          dismissable: true,
          time: 7000
        };
      });
  }

  exportCSV() {
    this.elencoTabella = [];
    this.rows.forEach((value) => {
      this.elencoTabella.push({
        des_adempimento: value.istanza?.adempimento.des_adempimento,
        cod_pratica: value.istanza?.cod_pratica,
        den_oggetto: value.den_oggetto,
        des_stato_osservazione: value.stato_osservazione.des_stato_osservazione,
        data_osservazione: value.data_osservazione,
        data_pubblicazione: value.istanza.data_pubblicazione
      });
    });
    this.utilityService.createCsvOss(
      this.elencoTabella,
      this.columns.filter((col) => col.prop !== 'setting'),
      `Elenco_Osservazioni_${new Date().toLocaleDateString()}`
    );
  }
}
