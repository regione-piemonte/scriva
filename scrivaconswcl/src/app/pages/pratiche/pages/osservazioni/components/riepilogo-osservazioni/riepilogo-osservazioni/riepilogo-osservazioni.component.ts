/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { formatDate } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import {
  AfterViewInit,
  Component,
  EventEmitter,
  Inject,
  Input,
  LOCALE_ID,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  TemplateRef,
  ViewChild
} from '@angular/core';
import { BaseComponent, UtilityService } from '@app/core';
import { I18nService } from '@app/core/translate/public-api';
import { AllegatoInsert } from '@app/pages/pratiche/model/allegato.model';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';
import { NotificationService } from '@app/shared/notification/notification.service';
import { FileDimensionPipe } from '@app/shared/pipes/file-dimension.pipe';
import { TableColumn } from '@app/shared/table/models/column.model';
import { LocalPagedDataSource } from '@app/shared/table/models/local-paged-data-source';
import { TablePage } from '@app/shared/table/models/table-page';
import { LoadingService } from '@app/theme/layouts/loading.services';
import * as moment from 'moment';
import { throwError } from 'rxjs';
import { catchError, takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-riepilogo-osservazioni',
  templateUrl: './riepilogo-osservazioni.component.html',
  styleUrls: ['./riepilogo-osservazioni.component.scss']
})
export class RiepilogoOsservazioniComponent
  extends BaseComponent
  implements AfterViewInit, OnChanges, OnInit
{
  @ViewChild('settingsTemplate') settingsTemplate: TemplateRef<any>;
  @ViewChild('dimensioneUpload') dimensioneUpload: TemplateRef<any>;

  columns: TableColumn[] = [];

  @Output() onSave: EventEmitter<any> = new EventEmitter<any>();
  @Input() refreshList: boolean;
  @Input() isSubmitted: boolean;
  @Input() canCreateObs: boolean;
  @Input() id_osservazione: number;

  pipeFormatDate = {
    transform: (value: string) =>
      value ? formatDate(value, 'dd/MM/yyyy', this.locale) : ''
  };
  dataSource: LocalPagedDataSource<AllegatoInsert>;

  columnsCsv = [
    {
      prop: 'nome_allegato',
      name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.NOME_DOC')
    },
    {
      prop: 'data_upload',
      name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.DATA_DOC'),
      pipe: {
        transform: (value) =>
          value?.data_upload
            ? moment(value.data_upload).format('DD/MM/YYYY')
            : ''
      }
    },
    {
      prop: 'dimensione_upload',
      name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.DIMENSIONE'),
      pipe: {
        transform: (value) =>
          value?.dimensione_upload ? this.pipeMb(value.dimensione_upload) : ''
      },
    }
  ];

  constructor(
    private loadingService: LoadingService,
    private praticheService: PraticheService,
    private i18n: I18nService,
    @Inject(LOCALE_ID) public locale: string,
    private notification: NotificationService,
    private utilityService: UtilityService
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    if (this.id_osservazione && this.id_osservazione !== 0) {
      this.dataSource = new LocalPagedDataSource({
        observable: this.praticheService.getRiepilogoAllegati.bind(
          this.praticheService,
          this.id_osservazione
        ),
        tablePage: new TablePage()
      });
      this.dataSource.refresh();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.id_osservazione && changes.id_osservazione?.previousValue === 0) {
      this.dataSource = new LocalPagedDataSource({
        observable: this.praticheService.getRiepilogoAllegati.bind(
          this.praticheService,
          this.id_osservazione
        ),
        tablePage: new TablePage()
      });
    }
    if (this.id_osservazione && this.refreshList && this.dataSource) {
      this.dataSource.refresh();
    }
  }

  ngAfterViewInit(): void {
    if (this.canCreateObs) {
      this._initTable();
    } else {
      this._initTableView();
    }
  }

  get totalElements(): number {
    return this.dataSource?.tablePage?.totalElements || 0;
  }

  private _initTable(): void {
    this.columns = [
      {
        prop: 'nome_allegato',
        name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.NOME_DOC'),
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'data_upload',
        name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.DATA_DOC'),
        pipe: this.pipeFormatDate,
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'dimensione_upload',
        name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.DIMENSIONE'),
        cellClass: 'align-middle',
        pipe: new FileDimensionPipe(),
        sortable: false
      },
      {
        prop: 'setting',
        name: this.i18n.translate('UTILS.TABLE.ACTIONS'),
        cellTemplate: this.settingsTemplate,
        sortable: false
      }
    ];
  }
  private _initTableView(): void {
    this.columns = [
      {
        prop: 'nome_allegato',
        name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.NOME_DOC'),
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'data_upload',
        name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.DATA_DOC'),
        pipe: this.pipeFormatDate,
        cellClass: 'align-middle',
        sortable: false
      },
      {
        prop: 'dimensione_upload',
        name: this.i18n.translate('OSSERVAZIONI.RIEPILOGO.FIELDS.DIMENSIONE'),
        cellClass: 'align-middle',
        pipe: new FileDimensionPipe(),
        sortable: false
      }
    ];
  }

  onDeleteAllegato(event) {
    this.loadingService.show();
    this.praticheService
      .deleteAllegato(event.cod_allegato)
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          this.notification.error({
            title: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DELETE.TITLE'
            ),
            text: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DELETE.TEXT'
            )
          });
          return throwError(e);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.dataSource.refresh();
      });
  }

  onSendOsservazione() {
    this.onSave.emit(true);
  }

  exportAllFile() {
    this.praticheService
      .downloadZipAllegatiOsservazione(this.id_osservazione)
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          this.notification.error({
            title: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TITLE'
            ),
            text: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TEXT'
            )
          });
          return throwError(e);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe();
  }

  /**
   *
   * @param byte dimensione file in byte
   * @returns Questa funzione converte il numero di byte in megabyte dividendo per 1024^2 (il numero di byte in un megabyte).
   */
  pipeMb(byte: number) {
    if (byte) {
      let mb = byte / 1048576;
      // toFixed() viene utilizzato per arrotondare il risultato a x decimali
      return mb.toFixed(2);
    }
    return '';
  }

  exportCSV() {
    let data = this.dataSource.rows;
    let columns = [...this.columnsCsv];

    this.utilityService.createCSV(
      data,
      columns,
      `Elenco_Allegati_${
        this.id_osservazione
      }_${new Date().toLocaleDateString()}`
    );
    /*this.praticheService
      .downloadAllegatiOsservazione(this.id_osservazione)
      .pipe(
        catchError((e: HttpErrorResponse) => {
          this.loadingService.hide();
          this.notification.error({
            title: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TITLE'
            ),
            text: this.i18n.translate(
              'OSSERVAZIONI.ALLEGATI.NOTIFICATION.ERROR.DOWNLOAD.TEXT'
            )
          });
          return throwError(e);
        }),
        takeUntil(this.destroy$)
      )
      .subscribe();*/
  }
}
