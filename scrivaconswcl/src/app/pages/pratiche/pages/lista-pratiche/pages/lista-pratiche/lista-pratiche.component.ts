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
import {
  AfterViewInit,
  Component,
  Inject,
  LOCALE_ID,
  OnInit,
  TemplateRef,
  ViewChild
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  BaseComponent,
  CSIApiFiltersRequest,
  UtilityService,
  ValueMapperService
} from '@core/index';
import { I18nService } from '@eng-ds/translate';
import { DropDownItem } from '@shared/models';
import { TableColumn } from '@shared/table/models/column.model';
import { TablePage } from '@shared/table/models/table-page';
import { LoadingService } from '@theme/layouts/loading.services';
import { PraticheService } from '@pages/pratiche/services/pratiche.service';
import { Pratica } from '@pages/pratiche/model';
import { FasePubblicazione, StatoIstanza } from '@pages/pratiche/enum';
import { forkJoin, of } from 'rxjs';
import { catchError, finalize, takeUntil, tap } from 'rxjs/operators';
import { Ambito, TipoAdempimento } from '@pages/ambiti/model';
import { HeaderPagedDataSource } from '@app/shared/table/models/header-paged-data-source';
import { TruncateTextPipe } from '@app/shared/pipes/truncate-text.pipe';
import { Help } from '@app/core/interfaces/help';
import { ModalService } from '@app/shared/modal/modal.service';
import { HelpService } from '@app/shared/services/help/help.service';
import * as moment from 'moment';
import { TableSort } from '@app/shared/table/models/table-sort';
import { ca } from 'date-fns/locale';

@Component({
  selector: 'app-lista-pratiche',
  templateUrl: './lista-pratiche.component.html',
  styleUrls: ['./lista-pratiche.component.scss']
})
export class ListaPraticheComponent
  extends BaseComponent
  implements OnInit, AfterViewInit
{
  codMaschera: string = 'PUBWEB.MSCR003P';

  @ViewChild('settingsTemplate') settingsTemplate: TemplateRef<any>;

  StatoIstanza = StatoIstanza;

  detailIcon: DropDownItem = {
    icon: {
      name: 'eng-scriva-view-detail',
      type: 'eng',
      cssClass: 'cursor-pointer',
      fill: '#005095',
      size: 'small'
    },
    label: this.i18n.translate('PRACTICE.LIST.DROPDOWN.VIEW')
  };
  breadcrumbs = [];
  columns: TableColumn[] = [];

  columnsCsv: any = [
    {
      prop: 'cod_pratica',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.CODICE_PRATICA')
      //pipe: new TruncateTextPipe(),
    },
    {
      prop: 'des_competenza_territorio',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.AUTORITA_COMP'),
      // pipe: new TruncateTextPipe(),
      pipe: {
        transform: (value) =>
          value?.des_competenza_territorio
            ? value.des_competenza_territorio.replace(/\n/g, ' ')
            : ''
      }
    },
    {
      prop: 'den_oggetto',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.DENOMINAZIONE')
      //pipe: new TruncateTextPipe(),
    },
    {
      prop: 'denom_comune',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.LOCALIZZAZIONE'),
      // pipe: new TruncateTextPipe(),
      pipe: {
        transform: (value) =>
          value?.denom_comune ? value.denom_comune.replace(/\n/g, ' ') : ''
      }
    },
    {
      prop: 'label_stato',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.STATO_PRATICA')
    },
    {
      prop: 'data_fine_osservazione',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.SCAD_OSSERVAZIONI'),
      pipe: {
        transform: (value) =>
          value?.data_fine_osservazione
            ? moment(value.data_fine_osservazione).format('DD/MM/YYYY')
            : ''
      }
    },
    {
      prop: 'data_conclusione_procedimento',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.DATA_PROVV_CONCLUSO'),
      pipe: {
        transform: (value) =>
          value?.data_conclusione_procedimento
            ? moment(value.data_conclusione_procedimento).format('DD/MM/YYYY')
            : ''
      }
    }
  ];

  selectedStatus: StatoIstanza = StatoIstanza.IN_CORSO;

  ambito: Ambito;
  tipoAdempimento: TipoAdempimento;
  id_competenza_territorio: number;
  id_ambito: number;
  id_tipo_adempimento: number;
  cod_ambito: string;
  cod_tipo_adempimento: string;
  id_istanza: number;
  dataSource: HeaderPagedDataSource<Pratica>;
  praticheStati = [];

  pipeFormatDate = {
    transform: (value: string) =>
      value ? formatDate(value, 'dd/MM/yyyy', this.locale) : ''
  };

  help: Help;

  //controlla se la prima chiamata della get dei dati è vuota
  isFirstEmpty: boolean = false;
  firstStatus: StatoIstanza | null = null;

  constructor(
    private praticheService: PraticheService,
    private loadingService: LoadingService,
    private utilityService: UtilityService,
    private modalService: ModalService,
    private i18n: I18nService,
    private route: ActivatedRoute,
    private router: Router,
    private valueMapper: ValueMapperService,
    private helpService: HelpService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
  }

  ngOnInit(): void {
    this.helpService.setCodMaschera(this.codMaschera);

    if (this.selectedStatus === StatoIstanza.IN_CORSO) {
      this.selectedStatus = StatoIstanza.IN_CORSO_DI_ISTRUTTORIA;
    }

    this.loadingService.show();
    this.cod_ambito = this.route.snapshot.parent.params.id_ambito;
    this.cod_tipo_adempimento =
      this.route.snapshot.parent.params.id_tipo_adempimento;

    const idCompTerritorioString =
      this.route.snapshot.parent.params?.id_competenza_territorio;
    this.id_competenza_territorio = isNaN(Number(idCompTerritorioString))
      ? null
      : Number(idCompTerritorioString);

    this.id_ambito = this.valueMapper.getAmbito(this.cod_ambito).id_ambito;
    this.id_tipo_adempimento = this.valueMapper.getTipoAdempimento(
      this.cod_tipo_adempimento
    ).id_tipo_adempimento;

    this._initTitle();

    this.dataSource = new HeaderPagedDataSource<any>({
      observable: this.getPracticesList.bind(this, FasePubblicazione.IN_CORSO),
      tablePage: new TablePage()
    });

    this._initBreadcrumbs();
  }

  private _initBreadcrumbs() {
    const snapshot = this.route.snapshot.parent.params;

    const ambito = this.valueMapper.getAmbito(this.cod_ambito);

    this.breadcrumbs = [
      {
        label: `${ambito.des_ambito}`,
        href: `/ambito/${ambito.cod_ambito}`
      },
      {
        label: `${snapshot['id_tipo_adempimento']}`,
        href: null
      }
    ];
  }

  ngAfterViewInit(): void {
    this._initTable();
  }

  getNumberOfElements(): number {
    return this.dataSource?.tablePage.totalElements || 0;
  }

  getLastStatus(): string {
    if (this.selectedStatus === StatoIstanza.IN_CORSO) {
      this.selectedStatus = StatoIstanza.IN_CORSO_DI_ISTRUTTORIA;
    }
    return this.selectedStatus;
  }

  /**
   * Funzione che gestisce le logiche di scarico dati tramite ordinamento e configurazioni.
   * @param fase_pubblicazione FasePubblicazione con le indicazioni per la pubblicazione.
   * @param filter CSIApiFiltersRequest con le configurazioni di filtri e paginazione.
   */
  getPracticesList(
    fase_pubblicazione: FasePubblicazione,
    filter: CSIApiFiltersRequest
  ) {
    // Creo una copia dell'array senza riferimenti all'oggetto filter
    let sort: TableSort[];
    sort = this.praticheService.cloneTableSortFromCSIApiFiltersRequest(filter);
    // Verifico se è necessario andare a gestire puntalmente la colonna "data_fine_osservazione"
    const switchName =
      sort[0]?.prop === 'data_fine_osservazione' ? true : false;
    // Verifico se devo modificare la proprietà per la ricerca dati
    if (switchName) {
      // Per allineare la ricerca con i parametri corretti, vado a modificare la proprietà di ricerca per data fine osservazine/i
      sort[0].prop = 'data_fine_osservazioni';
      // #
    }
    return this.praticheService
      .getPracticesList(
        this.id_ambito,
        this.id_tipo_adempimento,
        filter.page,
        filter.size,
        sort,
        this.id_competenza_territorio || null,
        fase_pubblicazione
      )
      .pipe(
        tap((res) => {
          this.loadingService.hide();
          this.praticheStati = [];
          const counter = this.paginationParse(res.headers.get('Counter'));
          let i = 1;
          while (counter[`${i}_contatore`]) {
            this.praticheStati.push({
              label: counter[`${i}_fase`],
              value: counter[`${i}_contatore`]
            });
            i += 1;
          }
          if (this.isFirstEmpty && this.firstStatus) {
            this.isFirstEmpty = false;
            this.onChangeStatus(this.firstStatus);
          }
        }),
        catchError((err: any) => {
          // Se la chiamata va in errore e non vi sono contatori,
          // se lo stato selezionato è TUTTI e non ci sono pratiche non deve fare la chiamata,
          //  salva lo stato nel firstStatus per selezionare il tab
          //  modificare lo stato in IN_CORSO_DI_ISTRUTTORIA
          if (
            this.praticheStati.length === 0 &&
            this.selectedStatus != StatoIstanza.TUTTI
          ) {
            this.isFirstEmpty = true;
            //salvo stato precedente per poi poter selezionare il tab corretto
            this.firstStatus = this.selectedStatus;
            if (this.selectedStatus === StatoIstanza.IN_CORSO) {
              this.selectedStatus = StatoIstanza.IN_CORSO_DI_ISTRUTTORIA;
            }
            //chiamata per ottenere i contatori
            this.onChangeStatus(StatoIstanza.TUTTI);
          }
          return of(err);
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

  private _initTitle(): void {
    /* tslint:disable-next-line */
    const ambito = this.utilityService.getAmbito(this.cod_ambito);
    /* tslint:disable-next-line */
    const tipoAdempimento = this.utilityService.getTipoAdempimento(
      this.cod_tipo_adempimento
    );

    forkJoin([ambito, tipoAdempimento])
      .pipe(takeUntil(this.destroy$))
      .subscribe((allResponse) => {
        const ambitoResponse = allResponse[0];
        const tipoResponse = allResponse[1] as any;
        this.ambito = ambitoResponse[0];
        this.tipoAdempimento = tipoResponse[0];
      });
  }

  private _initTable(): void {
    this.columns = [
      // {
      //   prop: 'des_adempimento',
      //   name: this.i18n.translate('PRACTICE.LIST.FIELDS.ADEMPIMENTO'),
      //   cellClass: 'align-middle',
      //   sortable: true
      // },
      {
        prop: 'cod_pratica',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.CODICE_PRATICA'),
        cellClass: 'align-middle font-weight-bold',
        //pipe: new TruncateTextPipe(),
        sortable: true
      },
      {
        prop: 'des_competenza_territorio',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.AUTORITA_COMP'),
        cellClass: 'align-middle',
        sortable: true,
        // pipe: new TruncateTextPipe(),
        pipe: {
          transform: (value: string) => {
            const values = value.split(',');
            const truncates = [];
            values.forEach((v) => {
              truncates.push(new TruncateTextPipe().transform(v, 35));
            });
            return values.join(',\r\n');
          }
        }
      },
      {
        prop: 'den_oggetto',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.DENOMINAZIONE'),
        cellClass: 'align-middle',
        //pipe: new TruncateTextPipe(),
        sortable: true
      },
      {
        prop: 'denom_comune',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.LOCALIZZAZIONE'),
        cellClass: 'align-middle',
        sortable: true,
        // pipe: new TruncateTextPipe(),
        pipe: {
          transform: (value: string) => {
            const values = value.split(',');
            if (values.length > 3) {
              values.slice(0, 3);
              values.push('[...]');
            }
            const truncates = [];
            values.forEach((v) => {
              truncates.push(new TruncateTextPipe().transform(v, 35));
            });
            return values.join(',\r\n');
          }
        }
      },
      {
        prop: 'label_stato',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.STATO_PRATICA'),
        cellClass: 'align-middle',
        sortable: true
      },
      {
        prop: 'data_fine_osservazione',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.SCAD_OSSERVAZIONI'),
        pipe: this.pipeFormatDate,
        cellClass: 'align-middle',
        sortable: true
      },
      // 06/12/2022 reinserita dopo SCRIVA-842
      {
        prop: 'data_conclusione_procedimento',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.DATA_PROVV_CONCLUSO'),
        pipe: this.pipeFormatDate,
        cellClass: 'align-middle',
        sortable: true
      },
      {
        prop: 'setting',
        name: this.i18n.translate('UTILS.TABLE.ACTIONS'),
        cellTemplate: this.settingsTemplate,
        cellClass: 'text-center align-middle p-0',
        sortable: false
      }
    ];
  }

  onDetail(row) {
    const selected: Pratica = row;
    this.router.navigate([
      '/procedimenti',
      this.cod_ambito,
      this.cod_tipo_adempimento,
      selected.id_istanza
    ]);
  }

  onChangeStatus(status: StatoIstanza) {
    this.loadingService.show();
    this.selectedStatus = status;
    this.dataSource.setObservable(this.getPracticesList.bind(this, status));

    this.dataSource.refresh();
  }

  onNewRemark(row) {
    const selected: Pratica = row;
    this.router.navigate([
      '/procedimenti',
      'ND',
      this.cod_tipo_adempimento,
      selected.id_istanza,
      'osservazioni',
      'nuova'
    ]);
  }

  checkDataFineOsservazione(row) {
    return !(
      !row.data_fine_osservazione ||
      new Date(
        new Date(row.data_fine_osservazione).setHours(0, 0, 0, 0)
      ).getTime() < new Date(new Date().setHours(0, 0, 0, 0)).getTime() ||
      row.fase_pubblicazione === FasePubblicazione.CONCLUSE
    );
  }

  // SCRIVA-1657 recupero l'intera lista per l'esportazione
  exportCSV() {
    this.loadingService.show();

    // Imposto un limite di 500 per la chiamata (al momento il CSI non l'ha specificato ma noi lo impostiamo per non avere millemilionesimi di risultati da stampare)
    const limit = this.getNumberOfElements() || 500;
    const offset = 0;

    const fasePubblicazione = this.getLastStatus() as FasePubblicazione;
    const sort: TableSort[] = []; // opzionale: popola se serve ordinamento

    this.praticheService
      .getPracticesList(
        this.id_ambito,
        this.id_tipo_adempimento,
        offset,
        undefined,
        sort,
        this.id_competenza_territorio || null,
        fasePubblicazione,
        limit
      )
      .pipe(finalize(() => this.loadingService.hide()))
      .subscribe({
        next: (res) => {
          const data: any = res.body || [];

          const columns = [...this.columnsCsv];
          this.utilityService.createCSV(
            data,
            columns,
            `Procedimenti_${this.ambito.cod_ambito}_${
              this.tipoAdempimento.cod_tipo_adempimento
            }_${new Date().toLocaleDateString()}`
          );
        },
        error: (err) => {
          console.error("Errore durante l'esportazione CSV:", err);
          this.loadingService.hide();
        }
      });
  }
}
