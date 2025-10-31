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
import { BaseComponent, UtilityService, ValueMapperService } from '@app/core';
import { I18nService } from '@app/core/translate/public-api';
import { Ambito, TipoAdempimento } from '@app/pages/ambiti/model';
import { Pratica } from '@app/pages/pratiche/model';
import { DropDownItem } from '@app/shared/models';
import { HelpService } from '@app/shared/services/help/help.service';
import { TableColumn } from '@app/shared/table/models/column.model';
import { TablePage } from '@app/shared/table/models/table-page';
import { LoadingService } from '@app/theme/layouts/loading.services';
import { HeaderPagedDataSource } from '@shared/table/models/header-paged-data-source';
import { forkJoin } from 'rxjs';
import { takeUntil, tap } from 'rxjs/operators';
import { AvvisiService } from '../../services/avvisi.service';
import { TableSort } from '@app/shared/table/models/table-sort';
import { PraticheService } from '@app/pages/pratiche/services/pratiche.service';

@Component({
  selector: 'app-avvisi',
  templateUrl: './avvisi.component.html',
  styleUrls: ['./avvisi.component.scss']
})
export class AvvisiComponent
  extends BaseComponent
  implements OnInit, AfterViewInit
{
  codMaschera: string = 'PUBWEB.MSCR006P';

  @ViewChild('settingsTemplate') settingsTemplate: TemplateRef<any>;

  detailIcon: DropDownItem = {
    icon: {
      name: 'eng-scriva-view-detail',
      type: 'eng',
      cssClass: 'cursor-pointer mx-2 mt-1',
      fill: '#005095',
      size: 'small'
    },
    label: this.i18n.translate('PRACTICE.LIST.DROPDOWN.VIEW')
  };

  columns: TableColumn[] = [];

  ambito: Ambito;
  tipoAdempimento: TipoAdempimento;

  id_ambito: number;
  id_tipo_adempimento: number;
  cod_ambito: string;
  cod_tipo_adempimento: string;
  id_istanza: number;
  dataSource: HeaderPagedDataSource<Pratica>;
  breadcrumbs = [];
  pipeFormatDate = {
    transform: (value: string) =>
      value ? formatDate(value, 'dd/MM/yyyy ', this.locale) : ''
  };

  constructor(
    private avvisiService: AvvisiService,
    private utilityService: UtilityService,
    private loadingService: LoadingService,
    private i18n: I18nService,
    private route: ActivatedRoute,
    private router: Router,
    private valueMapper: ValueMapperService,
    private helpService: HelpService,
    private praticheService: PraticheService,
    @Inject(LOCALE_ID) public locale: string
  ) {
    super();
  }

  ngOnInit(): void {
    this.helpService.setCodMaschera(this.codMaschera);
    this.loadingService.show();
    this.cod_ambito = this.route.snapshot.params.id_ambito;
    this.cod_tipo_adempimento = this.route.snapshot.params.id_tipo_adempimento;

    this.id_ambito = this.valueMapper.getAmbito(this.cod_ambito).id_ambito;
    this.id_tipo_adempimento = this.valueMapper.getTipoAdempimento(
      this.cod_tipo_adempimento
    ).id_tipo_adempimento;

    this._initTitle();

    this.dataSource = new HeaderPagedDataSource({
      observable: this.getAvvisiList.bind(this),
      tablePage: new TablePage()
    });

    this._initBreadcrumbs();
  }

  getAvvisiList(filter) {
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

    return this.avvisiService
      .getAvvisiList(
        this.id_ambito,
        this.id_tipo_adempimento,
        1,
        filter.page,
        filter.size,
        filter.sort
      )
      .pipe(
        tap((res) => {
          this.loadingService.hide();
        })
      );
  }

  private _initBreadcrumbs() {
    const snapshot = this.route.snapshot.params;

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
      {
        prop: 'des_adempimento',
        name: this.i18n.translate('AVVISI.LIST.FIELDS.ADEMPIMENTO'),
        cellClass: 'align-middle',
        width: 25,
        sortable: true
      },
      {
        prop: 'des_oggetto',
        name: this.i18n.translate('AVVISI.LIST.FIELDS.DESCRIZIONE'),
        cellClass: 'align-middle',
        sortable: true,
        width: 25
      },
      {
        prop: 'data_pubblicazione',
        name: this.i18n.translate('AVVISI.LIST.FIELDS.DATA_PUBBLICAZIONE'),
        pipe: this.pipeFormatDate,
        cellClass: 'align-middle',
        sortable: true,
        width: 25
      },
      {
        prop: 'data_fine_osservazione',
        name: this.i18n.translate('AVVISI.LIST.FIELDS.SCAD_OSSERVAZIONI'),
        pipe: {
          transform: (value: string) =>
            value ? formatDate(value, 'dd/MM/yyyy', this.locale) : null
        },
        sortable: true,
        cellClass: 'align-middle',
        width: 25
      },
      {
        prop: 'setting',
        name: this.i18n.translate('UTILS.TABLE.ACTIONS'),
        cellTemplate: this.settingsTemplate,
        sortable: false,
        width: 100
      }
    ];
  }

  onDetail(event) {
    const selected: Pratica = event;

    if (!selected.ambito?.cod_ambito) {
      selected.ambito = {} as any;
      selected.ambito.cod_ambito = this.cod_ambito;
    }

    this.id_istanza = event.id_istanza;
    this.router.navigate(
      [
        'procedimenti',
        event.cod_ambito,
        event.cod_tipo_adempimento,
        this.id_istanza
      ],
      { queryParams: { fromAvvisi: true } }
    );
  }
}
