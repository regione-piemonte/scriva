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
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ColumnMode } from '@swimlane/ngx-datatable';
import * as moment from 'moment';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, interval } from 'rxjs';
import {
  debounce,
  distinctUntilChanged,
  filter,
  map,
  takeUntil,
} from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import { AdvancedActions } from 'src/app/features/advanced-actions/enums/advanced-actions.enums';
import { IdTipiQuadro } from 'src/app/shared/enums/tipo-quadro.enums';
import {
  Adempimento,
  Comune,
  Help,
  IstanzaSearch,
  OggettoApp,
  Provincia,
  ResultCount,
  TipoAdempimento,
  TipoEvento,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  EventiService,
  HelpService,
  IstanzaService,
  LocationService,
  MessageService,
} from 'src/app/shared/services';
import {
  AttoreGestioneIstanzaEnum,
  StatoIstanzaEnum,
} from 'src/app/shared/utils';
import { trimFormValue } from '../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { Page } from '../../models';
import { SearchService, SearchStoreService } from '../../services';
import { IstanzeTableConsts } from './utilities/istanze-table.consts';

@Component({
  selector: 'app-search',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent
  extends AutoUnsubscribe
  implements OnInit, AfterViewInit
{
  /** IstanzeTableConsts con le costanti per la tabella. */
  IT_C = new IstanzeTableConsts();

  @ViewChild('baseActionsTemplate') baseActionsTemplate: TemplateRef<any>;
  @ViewChild('advancedActionsTemplate')
  advancedActionsTemplate: TemplateRef<any>;
  @ViewChild('identificativoIstanzaTemplate')
  identificativoIstanzaTemplate: TemplateRef<any>;
  @ViewChild('statoIstanzaProcedimentoTemplate')
  statoIstanzaProcedimentoTemplate: TemplateRef<any>;
  @ViewChild('adempimentoTemplate') adempimentoTemplate: TemplateRef<any>;
  @ViewChild('soggettoTemplate') soggettoTemplate: TemplateRef<any>;
  @ViewChild('denominazioneTemplate') denominazioneTemplate: TemplateRef<any>;
  @ViewChild('comuneTemplate') comuneTemplate: TemplateRef<any>;
  @ViewChild('dateTemplate') dateTemplate: TemplateRef<any>;
  @ViewChild('autoreModificaTemplate') autoreModificaTemplate: TemplateRef<any>;
  @ViewChild('statoPagamentoTemplate') statoPagamentoTemplate: TemplateRef<any>;
  @ViewChild('codiceProcedimentoTemplate')
  codiceProcedimentoTemplate: TemplateRef<any>;

  searchMode: string;
  page = new Page();
  rows = [];
  filteredRows = [];
  columns = [];
  ColumnMode = ColumnMode;

  userEditedForm = false;
  flgDaPagare = false;
  optionsState = {};
  tipoAdempimentoFilter: string;

  gestioneEnum = AttoreGestioneIstanzaEnum;
  StatoIstanzaEnum = StatoIstanzaEnum;
  status = StatoIstanzaEnum.TUTTE;
  statusFilters: ResultCount[];

  searchForm: FormGroup;
  advancedSearchForm: FormGroup;

  currentSort: { dir: string; prop: string } = null;

  today = new Date();
  tipiAdempimento: TipoAdempimento[];
  adempimenti: Adempimento[];
  province: Provincia[];
  comuni: Comune[];
  tipiEvento: TipoEvento[] = [];

  componente: string;
  isFrontOffice: boolean;
  // compilante: Compilante;

  codMaschera: string;
  helpList: Help[];

  // ultima ricerca
  _lastSearch: any;
  isLastSearch: boolean;
  simpleSearchRouting: string;

  constructor(
    public router: Router,
    private route: ActivatedRoute,
    public activatedRoute: ActivatedRoute,
    private searchService: SearchService,
    private istanzaService: IstanzaService,
    private store: SearchStoreService,
    private fb: FormBuilder,
    private ms: MessageService,
    private spinner: NgxSpinnerService,
    private locationService: LocationService,
    private adempimentoService: AdempimentoService,
    private authStoreService: AuthStoreService,
    private helpService: HelpService,
    private eventiService: EventiService
  ) {
    super();
    this.componente = this.authStoreService.getComponente();
    // this.compilante = this.authStoreService.getCompilante();
    if (this.componente === 'BO') {
      this.isFrontOffice = false;
    } else {
      this.isFrontOffice = true;
    }

    this.page.pageNumber = 0;
    this.page.size = 10;

    this.store.countIstanzeSub
      .pipe(
        takeUntil(this.destroy$),
        filter((count) => !!count)
      )
      .subscribe((countString) => {
        this.statusFilters = JSON.parse(countString);
      });
  }

  ngOnInit() {
    this.activatedRoute.paramMap
      .pipe(map(() => window.history.state))
      .subscribe((state) => {
        if (state.status) {
          this.status = state.status;
        }
        if (state.flgDaPagare) {
          this.flgDaPagare = true;
        }
        // SCRIVA-1464
        if (state.options) {
          this.optionsState = state.options;
        }
        if (state.tipoAdempimento) {
          this.tipoAdempimentoFilter = state.tipoAdempimento;
        }
      });

    this.route.paramMap.subscribe((paramMap) => {
      this.simpleSearchRouting = paramMap.get('simplesearch');
    });

    this.route.data.subscribe(
      (v) => (this.isLastSearch = v.ultimaRicerca ? true : false)
    );

    this.spinner.show();

    forkJoin([
      this.locationService.getProvinceAttive(),
      this.adempimentoService.getTipiAdempimento(),
      this.adempimentoService.getAdempimenti(),
      this.eventiService.getTipiEvento(),
    ])
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (result) => {
          this.province = result[0];
          this.tipiAdempimento = result[1];
          this.adempimenti = result[2];
          this.tipiEvento = result[3];

          if (this.isLastSearch) {
            this.lastSearch = { ...this.store.dataSearch };
            // setto il searchmode e lo forzo
            this.searchMode = this.store.searchMode;
          } else if (this.simpleSearchRouting) {
            // per ottenere una ricerca mirata solo tramite il routing
            this.lastSearch = {
              simple_search: this.simpleSearchRouting,
            };
          }

          if (this.tipoAdempimentoFilter) {
            this.onAdvancedMode();
            const tipoAdemp = this.tipiAdempimento.find(
              (tipo) => tipo.cod_tipo_adempimento === this.tipoAdempimentoFilter
            );
            this.advancedSearchForm.get('tipoAdempimento').setValue(tipoAdemp);
          } else if (this.searchMode === 'advanced') {
            this.onAdvancedMode(this.lastSearch);
            this.userEditedForm = true;
            this.setPage({ offset: 0 }, this.lastSearch);
          } else {
            this.onSimpleMode(this.lastSearch);
          }
          this._buildSearchForm();
          this.setStatus();
        },
        (error) => {
          if (error.error?.code) {
            this.ms.showMessage(error.error.code, 'searchForm', false);
          } else {
            this.ms.showMessage('E100', 'searchForm', true);
          }
        }
      );

    // RIF: SCRIVA-829
    // tutte le volte che passiamo dalla ricerca istanze
    // settiamo a false il flag che permette di visualizzare
    // il messaggio di successo per utente BO
    this.istanzaService.messageSuccessBO = false;
  }

  setStatus() {
    if (
      this.lastSearch &&
      (this.lastSearch.stato_istanza as StatoIstanzaEnum)
    ) {
      this.status = this.lastSearch.stato_istanza as StatoIstanzaEnum;
    }
  }

  ngAfterViewInit(): void {
    this.columns = [
      {
        name: this.IT_C.COL_NAME_AZIONI_BASE,
        cellTemplate: this.baseActionsTemplate,
        sortable: false,
      },
      {
        name: this.IT_C.COL_NAME_AZIONI_AVANZATE,
        cellTemplate: this.advancedActionsTemplate,
        sortable: false,
      },
      {
        name: this.IT_C.COL_NAME_IDENTIFICATIVO_ISTANZA,
        cellTemplate: this.identificativoIstanzaTemplate,
        prop: 'cod_istanza',
      },
      {
        name: this.IT_C.COL_NAME_CODICE_PROCEDIMENTO,
        cellTemplate: this.codiceProcedimentoTemplate,
        prop: 'cod_pratica',
      },
      {
        name: this.IT_C.COL_NAME_ADEMPIMENTO,
        cellTemplate: this.adempimentoTemplate,
        prop: 'des_adempimento',
      },
      {
        name: this.IT_C.COL_NAME_STATO_ISTANZA_PROCEDIMENTO,
        cellTemplate: this.statoIstanzaProcedimentoTemplate,
        prop: 'des_stato_istanza',
      },
      {
        name: this.IT_C.COL_NAME_SOGGETTO,
        cellTemplate: this.soggettoTemplate,
        prop: 'den_soggetto',
      },
      {
        name: this.IT_C.COL_NAME_DENOMINAZIONE,
        cellTemplate: this.denominazioneTemplate,
        prop: 'den_oggetto',
      },
      {
        name: this.IT_C.COL_NAME_COMUNE,
        cellTemplate: this.comuneTemplate,
        prop: 'comune', // E' una stringa composta da "[nome_comune],"
      },
      {
        name: this.IT_C.COL_NAME_DATA_MODIFICA_ISTANZA, // Valido per: isFrontOffice
        cellTemplate: this.dateTemplate,
        prop: 'data_modifica_istanza', // Valido per: isFrontOffice
      },
      {
        name: this.IT_C.COL_NAME_AUTORE_MODIFICA,
        cellTemplate: this.autoreModificaTemplate,
        prop: 'attore_modifica_fo', // Valido per: isFrontOffice
      },
      // {
      //   name: this.IT_C.COL_NAME_STATO_PAGAMENTO,
      //   cellTemplate: this.statoPagamentoTemplate,
      //   prop: 'des_stato_sintesi_pagamento',
      // },
    ];

    if (!this.isFrontOffice) {
      // Modifico l'oggetto con chiave COL_NAME_DATA_MODIFICA_ISTANZA, accedendo all'indice
      this.columns[9].name = this.IT_C.COL_NAME_DATA_MODIFICA_PROVVEDIMENTO;
      this.columns[9].prop = 'data_modifica_pratica';
      // Modifico l'oggetto con chiave COL_NAME_AUTORE_MODIFICA, accedendo all'indice
      this.columns[10].prop = 'attore_modifica_bo';
    }
  }

  onSimpleMode(search: any = null) {
    this.searchMode = 'simple';
    this.store.searchMode = this.searchMode;
    this.advancedSearchForm = null;
    this.codMaschera = '.MSCR004D';
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    this.getHelpList();
    this.setPage({ offset: 0 }, search);
  }

  onAdvancedMode(search: any = null) {
    this.searchMode = 'advanced';
    this.store.searchMode = this.searchMode;
    this._buildAdvancedSearchForm(search);
    this.codMaschera = '.MSCR005D';
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    this.getHelpList();
  }

  getHelpList() {
    this.helpService
      .getHelpByChiave(this.componente + this.codMaschera)
      .subscribe(
        (res) => {
          this.helpList = res;
        },
        (err) => {
          if (err.error?.code) {
            this.ms.showMessage(err.error.code, 'searchForm', false);
          } else {
            this.ms.showMessage('E100', 'searchForm', true);
          }
          console.log(
            '# Error retrieving help array ',
            this.componente + this.codMaschera
          );
        }
      );
  }

  onHelpClicked(chiave: string) {
    const modalContent =
      this.helpList.find(
        (help) =>
          help.chiave_help === this.componente + this.codMaschera + '.' + chiave
      )?.des_testo_help || 'Help non trovato...';

    this.ms.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  onStatusChange(newStatus) {
    if (newStatus !== this.status) {
      this.status = newStatus;
      this.userEditedForm = true;
      this.setPage({ offset: 0 });
    }
  }

  private _buildSearchForm() {
    this.searchForm = this.fb.group({
      searchText: [null],
      tipoPratiche: [null],
    });

    if (this.lastSearch && this.lastSearch.simple_search) {
      this.searchForm.get('searchText').setValue(this.lastSearch.simple_search);
    }

    if (!this.isFrontOffice) {
      this.searchForm.get('tipoPratiche').setValue('competenza');
    }

    this.searchForm.valueChanges
      .pipe(
        takeUntil(this.destroy$),
        debounce(() => interval(1000))
      )
      .subscribe((val: { searchText: string; tipoPratiche: string }) => {
        // Aggiorno il valore dell'input senza attivare le logiche di change
        trimFormValue(val.searchText, this.searchForm, 'searchText', false);

        this.userEditedForm = true;
        // SCRIVA-1464
        this.optionsState = {};
        this.flgDaPagare = false;
        this.setPage({ offset: 0 });
      });
  }

  private _buildAdvancedSearchForm(search: any = null) {
    this.advancedSearchForm = this.fb.group({
      tipoAdempimento: [null],
      adempimento: [null],
      provincia: [null],
      comune: [null],
      tipoEvento: [null],
      dataEventoDa: [null],
      dataEventoA: [null],
      // inserimentoDa: [null],
      // inserimentoA: [null],
      // presentazioneDa: [null],
      // presentazioneA: [null],
    });

    if (search) {
      this.advancedSearchForm
        .get('tipoAdempimento')
        .setValue(
          this.tipiAdempimento.find(
            (t) => t.cod_tipo_adempimento == search.tipo_adempimento
          )
            ? this.tipiAdempimento.find(
                (t) => t.cod_tipo_adempimento == search.tipo_adempimento,
                { emitEvent: false }
              )
            : null
        );

      this.advancedSearchForm
        .get('adempimento')
        .setValue(
          this.adempimenti.find((t) => t.cod_adempimento === search.adempimento)
            ? this.adempimenti.find(
                (t) => t.cod_adempimento === search.adempimento,
                { emitEvent: false }
              )
            : null
        );

      this.advancedSearchForm
        .get('provincia')
        .setValue(
          this.province.find(
            (t) => t.sigla_provincia === search.provincia_oggetto_istanza
          )
            ? this.province.find(
                (t) => t.sigla_provincia === search.provincia_oggetto_istanza,
                { emitEvent: false }
              ).cod_provincia
            : null
        );

      this.setProvinciaComuni(
        this.advancedSearchForm?.get('provincia').value,
        search.comune_oggetto_istanza
      );

      this.advancedSearchForm
        .get('tipoEvento')
        .setValue(search.id_tipo_evento ? search.id_tipo_evento : null, {
          emitEvent: false,
        });

      this.advancedSearchForm
        .get('dataEventoDa')
        .setValue(search.data_evento_from, { emitEvent: false });
      this.advancedSearchForm
        .get('dataEventoA')
        .setValue(search.data_evento_to, { emitEvent: false });
    }

    this.advancedSearchForm.valueChanges
      .pipe(takeUntil(this.destroy$), distinctUntilChanged())
      .subscribe(() => {
        this.userEditedForm = true;
        // SCRIVA-1464
        this.optionsState = {};
        this.flgDaPagare = false;
        this.setPage({ offset: 0 });
      });

    this.advancedSearchForm
      .get('provincia')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((codProvincia) => !!codProvincia),
        distinctUntilChanged()
      )
      .subscribe((codProvincia) => {
        this.setProvinciaComuni(codProvincia);
      });

    this.advancedSearchForm
      .get('tipoAdempimento')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        filter((TipoAdempimento) => !!TipoAdempimento),
        distinctUntilChanged()
      )
      .subscribe((TipoAdempimento) => {
        this.setAmbitiTipoAdempimento(TipoAdempimento);
      });
  }

  setAmbitiTipoAdempimento(
    tipoAdempimento: TipoAdempimento,
    adempimento: Adempimento = null
  ) {
    if (!tipoAdempimento) return;

    this.adempimentoService
      .getAdempimentiByTipo(tipoAdempimento.id_tipo_adempimento)
      .subscribe(
        (res) => {
          this.adempimenti = res;
          this.spinner.hide();

          if (adempimento) {
            this.advancedSearchForm
              .get('adempimento')
              .setValue(adempimento, { emitEvent: false });
          }
        },
        (error) => {
          if (error.error?.code) {
            this.ms.showMessage(error.error.code, 'searchForm', false);
          } else {
            this.ms.showMessage('E100', 'searchForm', true);
          }
        }
      );
  }

  setProvinciaComuni(codProvincia, comune = null) {
    if (!codProvincia) return;

    this.locationService.getComuniByProvincia(codProvincia).subscribe(
      (res) => {
        this.comuni = res.filter((comune) => !comune.data_fine_validita);
        this.spinner.hide();

        if (comune) {
          this.advancedSearchForm
            .get('comune')
            .setValue(comune, { emitEvent: false });
        }
      },
      (error) => {
        if (error.error?.code) {
          this.ms.showMessage(error.error.code, 'searchForm', false);
        } else {
          this.ms.showMessage('E100', 'searchForm', true);
        }
      }
    );
  }

  resetSearchFilters() {
    this.searchForm.get('searchText').reset(null, { emitEvent: false });
    this.advancedSearchForm.reset();
  }

  compareTipoAdempimento(ta1: TipoAdempimento, ta2: TipoAdempimento) {
    return ta1 && ta2 && ta1.cod_tipo_adempimento === ta2.cod_tipo_adempimento;
  }

  // compareAdempimento(a1: Adempimento, a2: Adempimento) {
  //   return a1 && a2 && a1.id_adempimento === a2.id_adempimento;
  // }

  // compareComune(c1: Comune, c2: Comune) {
  //   return c1 && c2 && c1.id_comune === c2.id_comune;
  // }

  // compareProvincia(p1: Provincia, p2: Provincia) {
  //   return p1 && p2 && p1.sigla_provincia === p2.sigla_provincia;
  // }

  // onSearchButtonClick() {
  //   this.setPage({ offset: 0 });
  // }

  /**
   * Populate the table with new data based on the page number
   * @param page The page to select
   */
  setPage(pageInfo, search = null) {
    this.spinner.show();
    this.page.pageNumber = pageInfo.offset;

    const prov = this.province.find((i: Provincia) => {
      const codProvincia = i.cod_provincia;
      const codProvinciaForm = this.advancedSearchForm?.get('provincia')?.value;
      // Ritorno il match tra codici provincia
      return codProvincia === codProvinciaForm;
    })
      ? this.province.find(
          (i) =>
            i.cod_provincia === this.advancedSearchForm?.get('provincia')?.value
        ).sigla_provincia
      : null;

    // Definisco la configurazione per la ricerca
    const simpleSearch: string =
      this.searchForm?.get('searchText')?.value?.trim() ?? '';

    const data = search
      ? search
      : {
          // cf_compilante: this.compilante?.cf_compilante || '',
          // cf_soggetto: null,
          ...this.optionsState,
          stato_istanza:
            this.status === StatoIstanzaEnum.TUTTE ? '' : this.status,
          simple_search: simpleSearch,
          tipo_adempimento:
            this.tipoAdempimentoFilter ||
            (
              this.advancedSearchForm?.get('tipoAdempimento')
                ?.value as TipoAdempimento
            )?.cod_tipo_adempimento,
          adempimento:
            (this.advancedSearchForm?.get('adempimento')?.value as Adempimento)
              ?.cod_adempimento || '',
          provincia_oggetto_istanza: prov || '',
          comune_oggetto_istanza:
            this.advancedSearchForm?.get('comune')?.value || '',
          id_tipo_evento:
            this.advancedSearchForm?.get('tipoEvento')?.value || '',
          data_evento_from:
            this.advancedSearchForm
              ?.get('dataEventoDa')
              ?.value?.split('T')[0] || '',
          data_evento_to:
            this.advancedSearchForm?.get('dataEventoA')?.value?.split('T')[0] ||
            '',
          tipo_pratiche:
            this.searchForm?.get('tipoPratiche')?.value === 'ALL' ? 'ALL' : '',
          des_stato_sintesi_pagamento: this.flgDaPagare
            ? 'DA EFFETTUARE'
            : null,

          // data_inserimento_from:
          //   this.advancedSearchForm?.get('inserimentoDa')?.value?.split('T')[0] || '',
          // data_inserimento_to:
          //   this.advancedSearchForm?.get('inserimentoA')?.value?.split('T')[0] || '',
          // data_presentazione_from:
          //   this.advancedSearchForm?.get('presentazioneDa')?.value?.split('T')[0] || '',
          // data_presentazione_to:
          //   this.advancedSearchForm?.get('presentazioneA')?.value?.split('T')[0] || '',
        };
    this.searchService
      .search(this.page, data, this.currentSort)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (pagedData) => {
          this.page = pagedData.page;
          this.rows = pagedData.data;
          this.tipoAdempimentoFilter = undefined;
          this._filterRows(this.rows);
          this.spinner.hide();
        },
        (error) => {
          if (error.error?.code) {
            this.ms.showMessage(error.error.code, 'searchForm', false);
          } else {
            this.ms.showMessage('E100', 'searchForm', true);
          }
        }
      );
  }

  private _filterRows(rows) {
    this.filteredRows = rows.filter(
      (row) =>
        this.status === StatoIstanzaEnum.TUTTE ||
        this.status.substring(0, this.status.length - 1) ===
          row.cod_stato_istanza.substring(0, row.cod_stato_istanza.length - 1)
    );

    if (this.filteredRows?.length === 0) {
      this.userEditedForm
        ? this.ms.showMessage('I001', 'searchForm', true)
        : this.ms.showMessage('I003', 'searchForm', true);
    }
  }

  onSort(event) {
    this.currentSort = event.sorts[0];
    this.setPage({ offset: 0 });
  }

  // getFormattedDate(date = null) {
  //   return date ? new Date(date).toLocaleDateString() : '';
  // }

  getMinDate(d1, d2) {
    return moment(d1).isAfter(moment(d2)) ? d2 : d1;
  }

  editRow(
    row: IstanzaSearch,
    attoreGestioneIstanza?: string,
    idTipiQuadro?: IdTipiQuadro
  ) {
    const codAdempimento = row.cod_adempimento;
    const codAmbito = row.cod_ambito;
    const oggAppPulsanti = this.filterOggettiApplicativi(row, 'PULSANTE');

    // setto il jump se passo il tipo quadro di redirect
    const jumpState = idTipiQuadro
      ? { jumpToQuadroIdTipoQuadro: idTipiQuadro }
      : {};

    this.router.navigate([`../ambito/${codAmbito}/istanza/${codAdempimento}`], {
      relativeTo: this.route.parent,
      state: {
        idIstanza: row.id_istanza,
        attoreGestioneIstanza:
          attoreGestioneIstanza || row.attore_gestione_istanza,
        oggAppPulsanti,
        ...jumpState,
      },
    });
  }

  showRow(row: IstanzaSearch) {
    const codAdempimento = row.cod_adempimento;
    const codAmbito = row.cod_ambito;

    this.router.navigate([`../ambito/${codAmbito}/istanza/${codAdempimento}`], {
      relativeTo: this.route.parent,
      state: {
        idIstanza: row.id_istanza,
        attoreGestioneIstanza: this.gestioneEnum.READ_ONLY,
      },
    });
  }

  deleteRow(row: IstanzaSearch) {
    this.ms.showConfirmation({
      title: 'Conferma eliminazione',
      codMess: 'A014',
      buttons: [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'CONFERMA',
          type: 'btn-primary',
          callback: () => {
            this.deleteIstanza(row);
          },
        },
      ],
    });
  }

  deleteIstanza(row: IstanzaSearch) {
    this.istanzaService.deleteIstanza(row.gestuid_istanza).subscribe(
      (res) => {
        this.setPage({ offset: 0 });
      },
      (err) => {
        if (err.error?.code) {
          this.ms.showMessage(err.error.code, 'searchForm', false);
        } else {
          this.ms.showMessage('E100', 'searchForm', true);
        }
      }
    );
  }

  toggleDropDown(event) {
    const id = event.id.split('-')[1];
    const dropMenus = document.getElementsByClassName('dropdown-menu');

    for (let i = 0; i < dropMenus.length; i++) {
      const style = dropMenus[i].getAttribute('style');

      if (dropMenus[i].id === 'dropMenuButton-' + id) {
        const dispValue = style === 'display: block' ? 'none' : 'block';
        dropMenus[i].setAttribute('style', 'display: ' + dispValue);
      } else {
        dropMenus[i].setAttribute('style', 'display: none');
      }
    }
  }

  /**
   * Metodo che suppoerta per andare alle azione avanzata
   * @param row IstanzaSearch
   * @param action
   */
  goToAdvancedAction(row: IstanzaSearch, action: string) {
    // non è una azione avanzata standard
    if (action === 'INTEGRA_ALLEG_RICH') {
      this.editRow(row, 'QDR_ALLEGATO', IdTipiQuadro.allegati);
      return;
    }
    let actionEnum: string =
      AdvancedActions[action as keyof typeof AdvancedActions];
    if (actionEnum) {
      // se ho azione avanzate censita chiamo il metodo nel servizio azioni avanzate
      this.router.navigate(['/azione-avanzata', actionEnum], {
        state: {
          idIstanza: row.id_istanza,
        },
      });
      return;
    }

    // in qwesta parte di codice non si dovrebbe entrare
    // a meno di azione avanzate non ancora implementata
  }

  filterOggettiApplicativi(row: IstanzaSearch, codTipoOggettoApp: string) {
    let newArray: OggettoApp[] = [];

    let oggAppProfilo: OggettoApp[] = [];
    const oggAppStato: OggettoApp[] = [];
    row.profili_app?.forEach((profiloApp) => {
      if (profiloApp.oggetti_app) {
        oggAppProfilo = [...oggAppProfilo, ...profiloApp.oggetti_app];
      }
    });
    row.tipi_adempimento_ogg_app?.forEach((element) => {
      if (element.oggetto_app) {
        oggAppStato.push(element.oggetto_app);
      }
    });

    const oggAppList: OggettoApp[] = [...oggAppProfilo, ...oggAppStato];
    if (oggAppList?.length > 0) {
      oggAppList.forEach((oggApp) => {
        const check = newArray.some(
          (item) => item.des_oggetto_app === oggApp.des_oggetto_app
        );
        if (!check) {
          newArray.push(oggApp);
        }
      });
      newArray = newArray.filter(
        (oggApp) =>
          oggApp.tipo_oggetto_app.cod_tipo_oggetto_app === codTipoOggettoApp
      );
    }
    return newArray;
  }

  checkIstanzaModificabile(row: IstanzaSearch) {
    const azioniBase = this.filterOggettiApplicativi(row, 'AZ_BASE');
    return azioniBase.some(
      (azione) => azione.cod_oggetto_app === 'MODIFICA_ISTANZA'
    );
  }

  checkIstanzaEliminabile(row: IstanzaSearch) {
    // SCRIVA-786
    const azioniBase = this.filterOggettiApplicativi(row, 'AZ_BASE');
    return azioniBase.some(
      (azione) => azione.cod_oggetto_app === 'ELIMINA_ISTANZA'
    );
  }

  set lastSearch(search) {
    this._lastSearch = search;
  }

  get lastSearch() {
    return this._lastSearch;
  }
}
