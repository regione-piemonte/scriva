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
  SecurityService,
  UserInfo,
  UtilityService
} from '@core/index';
import { I18nService } from '@eng-ds/translate';
import { Form } from '@shared/form';
import { DropDownItem } from '@shared/models';
import { TableColumn } from '@shared/table/models/column.model';
import { TablePage } from '@shared/table/models/table-page';
import { LoadingService } from '@theme/layouts/loading.services';
import { SearchFormHelperService } from '../../helper/search-form-helper';
import { PraticheService } from '@pages/pratiche/services/pratiche.service';
import { Pratica } from '@pages/pratiche/model';
import { forkJoin, throwError } from 'rxjs';
import { catchError, finalize, mergeMap, take } from 'rxjs/operators';
import { HeaderPagedDataSource } from '@app/shared/table/models/header-paged-data-source';
import { FasePubblicazione } from '@app/pages/pratiche/enum';
import { environment } from '@env/environment';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { RicercaStore } from '@pages/pratiche/services/ricerca.store';
import { TruncateTextPipe } from '@app/shared/pipes/truncate-text.pipe';
import { Help } from '@app/core/interfaces/help';
import { HelpService } from '@app/shared/services/help/help.service';
import * as moment from 'moment';
import { TableSort } from '@app/shared/table/models/table-sort';

@UntilDestroy()
@Component({
  selector: 'app-ricerca-pratiche',
  templateUrl: './ricerca-pratiche.component.html',
  styleUrls: ['./ricerca-pratiche.component.scss'],
  providers: [SearchFormHelperService]
})
export class RicercaPraticheComponent
  extends BaseComponent
  implements OnInit, AfterViewInit
{
  codMaschera: string = 'PUBWEB.MSCR005P';

  @ViewChild('settingsTemplate') settingsTemplate: TemplateRef<any>;

  isLoggedIn = false;

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

  columns: TableColumn[] = [];

  columnsCsv: any[] = [
    {
      prop: 'cod_pratica',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.CODICE_PRATICA')
      // pipe: new TruncateTextPipe(),
    },
    {
      prop: 'des_competenza_territorio',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.AUTORITA_COMP'),
      //  pipe: new TruncateTextPipe(),
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
      // pipe: new TruncateTextPipe(),
    },
    {
      prop: 'denom_comune',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.LOCALIZZAZIONE'),
      //  pipe: new TruncateTextPipe(),
      pipe: {
        transform: (value) =>
          value?.denom_comune ? value.denom_comune.replace(/\n/g, ' ') : ''
      }
    },
    {
      prop: 'label_stato',
      name: this.i18n.translate('PRACTICE.LIST.FIELDS.STATO_PRATICA')
      //  pipe: new TruncateTextPipe(),
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

  practiceSearchForm: Form;
  searched: boolean;

  dataSource = new HeaderPagedDataSource<any>({
    observable: this.searchPratiche.bind(this).bind(this),
    tablePage: new TablePage()
  });

  id_ambito: number;
  id_tipo_adempimento: number;
  id_istanza: number;

  pipeFormatDate = {
    transform: (value: string) =>
      value ? this.formatDate(value, 'dd/MM/yyyy ', this.locale) : ''
  };

  formatDate(value, format, local) {
    if (typeof value == 'string') return formatDate(value, format, local);
  }

  help: Help;

  constructor(
    private loadingService: LoadingService,
    private formHelperService: SearchFormHelperService,
    private practiceSearchService: PraticheService,
    private i18n: I18nService,
    private router: Router,
    private route: ActivatedRoute,
    private store: RicercaStore,
    private securityService: SecurityService,
    private utilityService: UtilityService,
    private helpService: HelpService,
    private praticheService: PraticheService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
    this.loadingService.hide();
  }

  ngOnInit(): void {
    this.helpService.setCodMaschera(this.codMaschera);
    this.securityService
      .getUser()
      .pipe(untilDestroyed(this))
      .subscribe((user: UserInfo) => {
        if (!!user) {
          this.isLoggedIn = true;
        }
      });

    this.utilityService
      .getHelp('PUBWEB.MSCR005P.cod_procedimento')
      .subscribe((help) => {
        //  PUBWEB.MSCR003P.cod_procedimento con lista PH... (NON DOVREBBE SERVIRE)
        [this.help] = help;

        this.formHelperService.setHelp(this.help);
        this.formHelperService.getAdempimentiPubblicati();

        this._initForm();

        const clear = this.route.snapshot.queryParams['clear'];

        if (clear) {
          this.store.clear();
        }

        this.store
          .getSearchForm()
          .pipe(take(1))
          .subscribe((searchedValue) => {
            if (searchedValue) {
              const keys = Object.keys(searchedValue);
              keys.forEach((k) => {
                if (searchedValue[k]) {
                  if (k === 'id_tipo_adempimento') {
                    this.practiceSearchForm
                      .get(k)
                      .setValue({ id: searchedValue[k] });
                  }

                  this.practiceSearchForm.get(k).setValue(searchedValue[k]);
                }
              });
            }
          });
        this.store
          .getSearched()
          .pipe(take(1))
          .subscribe((searched) => {
            if (searched) {
              this.onSearch();
            }
          });
      });
  }

  ngAfterViewInit(): void {
    this._initTable();
  }

  getNumberOfElements(): number {
    return this.dataSource?.tablePage.totalElements || 0;
  }

  onClear(): void {
    this.practiceSearchForm.reset();
    this.practiceSearchForm.clearValidators();
    this.store.saveSearch(this.practiceSearchForm.value);
  }

  onSearch(): void {
    this.loadingService.show();
    this.searched = true;

    const query = this._handleAnnoPresentazioneProgetto(
      this._clearFromDivider(this.practiceSearchForm.value)
    );

    if (query.codice_pratica) {
      query.codice_pratica = query.codice_pratica.trim();
    }

    this.store.saveSearch(Object.assign({}, query));

    if (query.flg_infrastrutture_strategiche || query.flg_vinca) {
      query.vincolo = [];
    }
    if (query.flg_infrastrutture_strategiche) {
      query.vincolo.push('L443');
    }
    if (query.flg_vinca) {
      query.vincolo.push('VNCS');
    }
    if (
      query.sigla_provincia_oggetto &&
      query.sigla_provincia_oggetto !== '-1' &&
      query.sigla_provincia_oggetto !== -1
    ) {
      query.sigla_provincia_oggetto = this.utilityService.getProvincia(
        query.sigla_provincia_oggetto
      ).sigla_provincia;
    }

    this.dataSource.setObservable(this.searchPratiche.bind(this, query));

    this.dataSource.refresh();
  }

  searchPratiche(query: any, filter) {
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

    return this.practiceSearchService
      .searchPratiche(query, FasePubblicazione.TUTTE, filter.page, sort)
      .pipe(
        catchError((err) => {
          this.dataSource.tablePage.totalElements = 0;
          return throwError(err);
        }),
        finalize(() => {
          this.loadingService.hide();
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

  onDetail(row) {
    const selected: Pratica = row;

    if (!selected.ambito?.cod_ambito) {
      selected.ambito = {} as any;
      selected.ambito.cod_ambito = 'ND';
    }

    this.router.navigate(
      [
        '/procedimenti',
        selected.cod_ambito,
        selected.cod_tipo_adempimento,
        selected.id_istanza
      ],
      { queryParams: { fromSearch: true } }
    );
  }

  onNewRemark(row) {
    const selected: Pratica = row;

    if (!selected.ambito?.cod_ambito) {
      selected.ambito = {} as any;
      selected.ambito.cod_ambito = 'ND';
    }

    this.router.navigate(
      [
        '/procedimenti',
        selected.cod_ambito,
        selected.cod_tipo_adempimento,
        selected.id_istanza,
        'osservazioni',
        'nuova'
      ],
      {
        queryParams: { fromSearch: true }
      }
    );
  }

  private _clearFromDivider(query: any): any {
    const keys = Object.keys(query);
    keys.forEach((k) => {
      if (k.indexOf('divider') >= 0) {
        delete query[k];
      }
    });
    return query;
  }

  private _handleAnnoPresentazioneProgetto(query: any): any {
    if (query['anno_presentazione_progetto_superiore'] === 'minore_di') {
      query['minore_di'] = true;
      query['maggiore_di'] = false;
    } else if (
      query['anno_presentazione_progetto_superiore'] === 'maggiore_di'
    ) {
      query['minore_di'] = false;
      query['maggiore_di'] = true;
    }

    delete query['anno_presentazione_progetto_superiore'];
    return query;
  }

  private _initForm(): void {
    this.practiceSearchForm = this.formHelperService.get('pratiche-search');
  }

  private _initTable(): void {
    this.columns = [
      {
        prop: 'cod_pratica',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.CODICE_PRATICA'),
        cellClass: 'align-middle font-weight-bold',
        // pipe: new TruncateTextPipe(),
        sortable: true
      },
      {
        prop: 'des_competenza_territorio',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.AUTORITA_COMP'),
        cellClass: 'align-middle',
        //  pipe: new TruncateTextPipe(),
        pipe: {
          transform: (value: string) => {
            const values = value.split(',');
            const truncates = [];
            values.forEach((v) => {
              truncates.push(new TruncateTextPipe().transform(v, 35));
            });
            return values.join(',\r\n');
          }
        },
        sortable: true
      },
      {
        prop: 'den_oggetto',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.DENOMINAZIONE'),
        cellClass: 'align-middle',
        // pipe: new TruncateTextPipe(),
        sortable: true
      },
      {
        prop: 'denom_comune',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.LOCALIZZAZIONE'),
        cellClass: 'align-middle',
        //  pipe: new TruncateTextPipe(),
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
        },
        sortable: true
      },
      {
        prop: 'label_stato',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.STATO_PRATICA'),
        cellClass: 'align-middle',
        //  pipe: new TruncateTextPipe(),
        sortable: true
      },
      {
        prop: 'data_fine_osservazione',
        name: this.i18n.translate('PRACTICE.LIST.FIELDS.SCAD_OSSERVAZIONI'),
        pipe: this.pipeFormatDate,
        cellClass: 'align-middle',
        sortable: true
      },
      //  06/12/2022 reinserita dopo SCRIVA-842
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

  toLogin() {
    window.location = `${environment.backend.baseUrl}/auth` as (
      | string
      | Location
    ) &
      Location;
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
    // Recupero l'ultima query di ricerca salvata
    const query = this.store.getLastSearch();

    const offset = 1;
    const limit = this.getNumberOfElements() || 500; // Imposto un limite di 500 (al momento il CSI non l'ha specificato ma noi lo impostiamo per non avere millemilionesimi di risultati da stampare)

    this.practiceSearchService
      .searchPratiche(query, FasePubblicazione.TUTTE, 
        offset,
        null,
        limit
      )
      .pipe(finalize(() => this.loadingService.hide()))
      .subscribe({
        next: (response) => {
          //console.log('Risposta ricevuta per exportCSV:', response);
          const data : any = response?.body || [];
          const columns = [...this.columnsCsv];
          this.utilityService.createCSV(
            data,
            columns,
            `Procedimenti_filtrati_${new Date().toLocaleDateString()}`
          );
        },
        error: (err) => {
          console.error("Errore durante l'esportazione CSV:", err);
        }
      });
  }
}
