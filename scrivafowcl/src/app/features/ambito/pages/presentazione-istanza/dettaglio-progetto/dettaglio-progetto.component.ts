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
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of, Subscription, throwError } from 'rxjs';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  switchMap,
  tap,
} from 'rxjs/operators';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  CompetenzaTerritorio,
  Help,
  Istanza,
  IstanzaCompetenza,
  ResultCount,
  StepConfig,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  CategorieProgettualiService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { trimFormValue } from '../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import {
  CategoriaOggetto,
  OggettoIstanza,
  OggettoIstanzaCategoria,
} from '../../../models';
import { AmbitoService } from '../../../services';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import { RequestDataQuadro } from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';
import { config } from 'process';
import {
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo,
  ISalvaJsonDataRes,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';

@Component({
  selector: 'app-dettaglio-progetto',
  templateUrl: './dettaglio-progetto.component.html',
  styleUrls: ['./dettaglio-progetto.component.scss'],
})
export class DettaglioProgettoComponent
  extends StepperIstanzaComponent
  implements OnInit, AfterViewInit
{
  @ViewChild('competenzaTemplate') competenzaTemplate: TemplateRef<any>;
  @ViewChild('nuovaFlgTemplate') nuovaFlgTemplate: TemplateRef<any>;
  @ViewChild('modificaFlgTemplate') modificaFlgTemplate: TemplateRef<any>;
  @ViewChild('principaleFlgTemplate') principaleFlgTemplate: TemplateRef<any>;
  @ViewChild('nuovaFlgTemplateSummary')
  nuovaFlgTemplateSummary: TemplateRef<any>;
  @ViewChild('modificaFlgTemplateSummary')
  modificaFlgTemplateSummary: TemplateRef<any>;
  @ViewChild('principaleFlgTemplateSummary')
  principaleFlgTemplateSummary: TemplateRef<any>;

  codMaschera = '.MSCR015F';

  istanza: Istanza;

  // jsonData;

  savedData: OggettoIstanzaCategoria[];
  // saveWithPut = false;

  gestioneEnum = AttoreGestioneIstanzaEnum;

  acLoadedList: IstanzaCompetenza[];
  acPrincipali: CompetenzaTerritorio[];
  acPrincipaliList: CompetenzaTerritorio[];
  acSecondarieList: CompetenzaTerritorio[];
  acListWithSecondarie: IstanzaCompetenza[];
  oggettiIstanza: OggettoIstanza[];

  ricercaForm: FormGroup;

  categorieList: CategoriaOggetto[];
  categorieProgettiFull: OggettoIstanzaCategoria[][] = [];
  categorieProgetto: OggettoIstanzaCategoria[] = [];
  categorieSelezionate: OggettoIstanzaCategoria[];

  tipiCompFilterList: ResultCount[];
  tipoCompFilter = 'TUTTE';
  stringFilter = '';
  selectedFilter = false;

  columns = [];
  summaryColumns = [];
  ColumnMode = ColumnMode;
  tableRecords: OggettoIstanzaCategoria[];

  showSummaryTable = false;

  autCompetenteCardTestoFinaleHtml: string = '';

  /**
   * Subsctription
   */
  searchTextValueChangesSub: Subscription;
  progettoValueChangesSub: Subscription;

  constructor(
    private adempimentoService: AdempimentoService,
    private ambitoService: AmbitoService,
    private categorieService: CategorieProgettualiService,
    private fb: FormBuilder,
    _presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) injConfig: StepConfig,
    messageService: MessageService,
    helpService: HelpService,
    istanzaService: IstanzaService,
    authStoreService: AuthStoreService,
    stepManagerService: StepManagerService,
    spinner: NgxSpinnerService
  ) {
    super(
      _presentazioneIstanzaService,
      injConfig,
      messageService,
      helpService,
      istanzaService,
      authStoreService,
      stepManagerService,
      spinner
    );
  }

  ngOnInit() {
    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.buildForm();
    const requestHelp = this.componente + this.codMaschera;
    this.getHelpList(requestHelp, 'containerUploadDocumenti');
    this.loadData();
  }

  ngAfterViewInit() {
    this._setColumns();
  }

  /**
   * Funzione che setta il valore delle colonne,
   * richiama anche la funzione per il settaggio delle colonne di summary
   */
  private _setColumns() {
    this.columns = [
      {
        name: 'Cod. categoria',
        prop: 'categoria_oggetto.cod_categoria_oggetto',
      },
      { name: 'Competenza', cellTemplate: this.competenzaTemplate },
      {
        name: 'Descrizione categoria',
        prop: 'categoria_oggetto.des_categoria_oggetto_estesa',
      },
      {
        name: 'Nuova realizzazione',
        prop: 'flg_cat_nuovo_oggetto',
        cellTemplate: this.nuovaFlgTemplate,
      },
      {
        name: 'Modifica/estensione di progetto/opera esistente',
        prop: 'flg_cat_modifica_oggetto',
        cellTemplate: this.modificaFlgTemplate,
      },
      {
        name: 'Categoria principale',
        prop: 'flg_cat_principale',
        cellTemplate: this.principaleFlgTemplate,
      },
    ];
    this._setColumnsSummary();
  }
  /**
   * Funzione per settare le colonne di summary
   */
  private _setColumnsSummary() {
    this.summaryColumns = [
      {
        name: 'Cod. categoria',
        prop: 'categoria_oggetto.cod_categoria_oggetto',
      },
      { name: 'Competenza', cellTemplate: this.competenzaTemplate },
      {
        name: 'Descrizione categoria',
        prop: 'categoria_oggetto.des_categoria_oggetto',
      },
      {
        name: 'Nuova realizzazione',
        prop: 'flg_cat_nuovo_oggetto',
        cellTemplate: this.nuovaFlgTemplateSummary,
      },
      {
        name: 'Modifica/estensione di progetto/opera esistente',
        prop: 'flg_cat_modifica_oggetto',
        cellTemplate: this.modificaFlgTemplateSummary,
      },
      {
        name: 'Categoria principale',
        prop: 'flg_cat_principale',
        cellTemplate: this.principaleFlgTemplateSummary,
      },
    ];
  }

  buildForm() {
    this.ricercaForm = this.fb.group({
      searchText: '',
      progetto: null,
    });
    this.progettoValueChangesSub = this.ricercaForm
      .get('progetto')
      .valueChanges.pipe(distinctUntilChanged())
      .subscribe((p: OggettoIstanza) => {
        this.categorieProgetto = this.categorieProgettiFull.find(
          (array) => array[0].id_oggetto_istanza === p.id_oggetto_istanza
        );
      });
    this.searchTextValueChangesSub = this.ricercaForm
      .get('searchText')
      .valueChanges.pipe(distinctUntilChanged(), debounceTime(500))
      .subscribe((searchString: string) => {
        // Aggiorno il valore dell'input senza attivare le logiche di change
        trimFormValue(searchString, this.ricercaForm, 'searchText', false);
        // Effettuo la trim della stringa
        searchString = searchString ?? '';
        searchString = searchString.trim();
        // Lancio le logiche di filtro
        this.filterByInput(searchString);
      });
  }

  getHelpBannerText(chiave: string): string {
    let swapPh = [];
    let acP = '';
    let acS = '';
    const help: Help = {
      ...this.helpList?.find(
        (help) =>
          help.tipo_help.cod_tipo_help === 'BNR' &&
          help.chiave_help.includes(chiave)
      ),
    };
    if (help?.chiave_help === 'FO.MSCR015F.Testo_finale') {
      if (this.acPrincipaliList) {
        this.acPrincipaliList.forEach((ac) => {
          acP = acP + ac.des_competenza_territorio + '<br>';
        });
      }
      if (this.acSecondarieList) {
        this.acSecondarieList.forEach((ac) => {
          acS = acS + ac.des_competenza_territorio + '<br>';
        });
      }
      swapPh.push({
        ph: '[{PH_ACP_DES_COMPETENZA_TERRITORIO}]',
        swap: acP,
      });
      swapPh.push({
        ph: '[{PH_ACS_DES_COMPETENZA_TERRITORIO}]',
        swap: acS,
      });
      help.des_testo_help = this._messageService._getSwapReplacedTestoMessaggio(
        help.des_testo_help,
        swapPh
      );
      help.des_testo_help = this._messageService.getConditionalTestoMessaggio(
        help.des_testo_help,
        'PH_ACS_DES_COMPETENZA_TERRITORIO',
        !!acS
      );
    }

    return (
      help?.des_testo_help ||
      "Errore: il testo di questo paragrafo non è stato trovato. Contattare l'assistenza."
    );
  }

  loadData() {
    // Resetto il contesto per il servizio degli helper
    this.helpService.setCodContesto(undefined);
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.ricercaForm.enable();
    } else {
      this.ricercaForm.disable();
    }

    this.istanzaService
      .getIstanzaById(this.idIstanza)
      .pipe(
        tap((istanza) => {
          this.istanza = istanza;
          this.qdr_riepilogo = JSON.parse(istanza.json_data).QDR_RIEPILOGO;

          // Recupero dall'istanza l'oggetto dell'adempimento
          const adempimento: Adempimento = istanza.adempimento;
          // Lancio il setup per il contesto dell'helper
          this.helpService.setCodContestoFromAdempimento(adempimento);
          // #
        }),
        switchMap(() => {
          const getACPratica =
            this.adempimentoService.getAutoritaCompetenteByIstanza(
              this.idIstanza
            );
          const getCategorieList =
            this.categorieService.getCategorieProgettualiByAdempimento(
              this.istanza.adempimento.id_adempimento
            );
          const getOggettiIstanza =
            this.ambitoService.getOggettiIstanzaByIstanza(this.idIstanza);
          const getCategorieAssociate = this.categorieService
            .getCategorieProgettualiByIstanza(this.idIstanza)
            .pipe(
              catchError((err) => {
                if (err.status === 404) {
                  return of(null);
                } else {
                  return throwError(err);
                }
              })
            );
          return forkJoin([
            getACPratica,
            getCategorieList,
            getOggettiIstanza,
            getCategorieAssociate,
          ]);
        })
      )
      .subscribe(
        (res) => {
          this.acLoadedList = res[0];
          this.acPrincipali = res[0]
            .filter((item) => item.flg_autorita_principale)
            .map((elem) => elem.competenza_territorio);

          this.categorieList = res[1].body.filter(
            (categoria) => categoria.tipi_competenza
          );
          this.tipiCompFilterList = JSON.parse(
            res[1].headers.get('CountCompetenze')
          );

          this.oggettiIstanza = res[2];
          this.ricercaForm.get('progetto').setValue(this.oggettiIstanza[0]);

          this.savedData = res[3];
          // if (this.savedData) {
          //   this.saveWithPut = true;
          // }
          this.buildCategorieProgetto();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'searchFormCard');
        }
      );
  }

  buildCategorieProgetto() {
    this.oggettiIstanza.forEach((oggIstanza, i) => {
      this.categorieProgettiFull[i] = [];
      this.categorieList.forEach((categ) => {
        let gestUID = null;
        let flagNuovo = false;
        let flagModifica = false;
        let flagPrincipale = false;
        if (this.savedData) {
          const savedCat = this.savedData.find(
            (cat) =>
              cat.id_oggetto_istanza === oggIstanza.id_oggetto_istanza &&
              cat.categoria_oggetto.id_categoria_oggetto ===
                categ.id_categoria_oggetto
          );
          if (savedCat) {
            gestUID = savedCat.gestUID;
            flagNuovo = savedCat.flg_cat_nuovo_oggetto;
            flagModifica = savedCat.flg_cat_modifica_oggetto;
            flagPrincipale = savedCat.flg_cat_principale;
          }
        }

        const catProgetto: OggettoIstanzaCategoria = {
          gestUID,
          id_oggetto_istanza: oggIstanza.id_oggetto_istanza,
          categoria_oggetto: categ,
          flg_cat_nuovo_oggetto: flagNuovo,
          flg_cat_modifica_oggetto: flagModifica,
          flg_cat_principale: flagPrincipale,
        };

        this.categorieProgettiFull[i].push(catProgetto);
      });
    });

    this.categorieProgetto = this.categorieProgettiFull[0];
    this.applyFilters();
    this.spinner.hide();
    if (this.savedData) {
      this.onProsegui(true);
    }
  }

  sortByCompetenza(order: string, summTable?: boolean) {
    if (!this.tableRecords || this.tableRecords.length === 0) {
      return;
    }

    if (!summTable) {
      if (order === 'asc') {
        this.tableRecords.sort(
          (categA, categB) =>
            categA.categoria_oggetto.tipi_competenza[0].id_tipo_competenza -
            categB.categoria_oggetto.tipi_competenza[0].id_tipo_competenza
        );
      } else {
        this.tableRecords.sort(
          (categA, categB) =>
            categB.categoria_oggetto.tipi_competenza[0].id_tipo_competenza -
            categA.categoria_oggetto.tipi_competenza[0].id_tipo_competenza
        );
      }
      this.tableRecords = [...this.tableRecords];
    } else {
      if (order === 'asc') {
        this.categorieSelezionate.sort(
          (categA, categB) =>
            categA.categoria_oggetto.tipi_competenza[0].id_tipo_competenza -
            categB.categoria_oggetto.tipi_competenza[0].id_tipo_competenza
        );
      } else {
        this.categorieSelezionate.sort(
          (categA, categB) =>
            categB.categoria_oggetto.tipi_competenza[0].id_tipo_competenza -
            categA.categoria_oggetto.tipi_competenza[0].id_tipo_competenza
        );
      }
      this.categorieSelezionate = [...this.categorieSelezionate];
    }
  }

  onSort(event, summTable?: boolean) {
    if (event.column.name === 'Competenza') {
      this.sortByCompetenza(event.newValue, summTable);
    }
  }

  filterByCompetenza(compFilter: string) {
    this.tipoCompFilter = compFilter;
    this.applyFilters();
  }

  filterByInput(searchString: string) {
    this.stringFilter = searchString.toLowerCase();
    this.applyFilters();
  }

  filterSelected(flag: boolean) {
    this.selectedFilter = flag;
    this.applyFilters();
  }

  applyFilters() {
    if (!this.categorieProgetto) {
      return;
    }

    let filteredResults: OggettoIstanzaCategoria[] = [];

    filteredResults = this.categorieProgetto.filter(
      (categ) =>
        categ.categoria_oggetto.cod_categoria_oggetto
          .toLowerCase()
          .includes(this.stringFilter) ||
        categ.categoria_oggetto.des_categoria_oggetto
          .toLowerCase()
          .includes(this.stringFilter)
    );
    if (this.selectedFilter) {
      filteredResults = filteredResults.filter(
        (categ) =>
          categ.flg_cat_nuovo_oggetto === true ||
          categ.flg_cat_modifica_oggetto === true ||
          categ.flg_cat_principale === true
      );
    }
    if (this.tipoCompFilter !== 'TUTTE') {
      filteredResults = filteredResults.filter(
        (categ) =>
          categ.categoria_oggetto.tipi_competenza[0].cod_tipo_competenza ===
          this.tipoCompFilter
      );
    }

    this.tableRecords = filteredResults;
  }

  showFlagPrincipale(row: OggettoIstanzaCategoria) {
    return this.acPrincipali.some(
      (ac) =>
        ac.tipo_competenza.cod_tipo_competenza ===
        row.categoria_oggetto.tipi_competenza[0].cod_tipo_competenza
    );
  }

  onFlagNuovo(idCateg, checked) {
    const categoria = this.categorieProgetto.find(
      (item) => item.categoria_oggetto.id_categoria_oggetto === idCateg
    );
    categoria.flg_cat_nuovo_oggetto = checked;
    if (categoria.flg_cat_modifica_oggetto === true) {
      categoria.flg_cat_modifica_oggetto = false;
    }
    this.checkFlagPrincipale(categoria);
  }

  onFlagModifica(idCateg, checked) {
    const categoria = this.categorieProgetto.find(
      (item) => item.categoria_oggetto.id_categoria_oggetto === idCateg
    );
    categoria.flg_cat_modifica_oggetto = checked;
    if (categoria.flg_cat_nuovo_oggetto === true) {
      categoria.flg_cat_nuovo_oggetto = false;
    }
    this.checkFlagPrincipale(categoria);
  }

  onFlagPrincipale(idCateg, checked) {
    const duplicate = this.categorieProgetto.find(
      (categ) => categ.flg_cat_principale === true
    );
    if (duplicate && checked) {
      this._messageService.showConfirmation({
        title: 'Attenzione',
        codMess: 'A015',
        buttons: [
          {
            label: 'ANNULLA',
            type: 'btn-link',
            callback: () => {
              const element = this.categorieProgetto.find(
                (item) =>
                  item.categoria_oggetto.id_categoria_oggetto === idCateg
              );
              element.flg_cat_principale = true;
              setTimeout(() => {
                element.flg_cat_principale = false;
              }, 100);
            },
          },
          {
            label: 'CONFERMA',
            type: 'btn-primary',
            callback: () => {
              duplicate.flg_cat_principale = false;
              this.categorieProgetto.find(
                (item) =>
                  item.categoria_oggetto.id_categoria_oggetto === idCateg
              ).flg_cat_principale = true;
            },
          },
        ],
      });
    } else {
      this.categorieProgetto.find(
        (item) => item.categoria_oggetto.id_categoria_oggetto === idCateg
      ).flg_cat_principale = checked;
    }
  }

  checkFlagPrincipale(element: OggettoIstanzaCategoria) {
    if (!element.flg_cat_modifica_oggetto && !element.flg_cat_nuovo_oggetto) {
      element.flg_cat_principale = false;
    }
  }

  onProsegui(firstLoad = false) {
    this.spinner.hide();

    if (this.categorieProgettiFull.length > 1) {
      console.log(
        '# Scenario progetti multipli: funzionalità non ancora implementata!'
      );
      return;
    }

    /* 
    Commentata per modifiche lato BE 
    FEAT: CALCOLA AC - CATEGORIE PROGETTUALI jira 1176-1242
    Verificare e poi rimuovere del tutto
    const checkSecondarie = this.acListWithSecondarie?.some(ac => ac.flg_autorita_principale === false);
    if (checkSecondarie) {
      this.adempimentoService.deleteAutoritaCompetentiSecondarie(this.idIstanza).subscribe(
        res => {
          this.checkCategorie(firstLoad);
        },
        err => {
          if (err.error?.code) {
            this._messageService.showMessage(err.error.code, 'searchFormCard', false);
          } else {
            this._messageService.showMessage('E100', 'searchFormCard', true);
          }
        }
      );
    } else {
      this.checkCategorie(firstLoad);
    }
    */
    this.checkCategorie(firstLoad);
  }

  checkCategorie(firstLoad = false) {
    this.spinner.show();

    this.categorieSelezionate = [];
    this.categorieProgetto.forEach((categ) => {
      if (categ.flg_cat_nuovo_oggetto || categ.flg_cat_modifica_oggetto) {
        this.categorieSelezionate.push(categ);
      }
    });

    const flgPrincipale = this.categorieSelezionate.some(
      (categ) => categ.flg_cat_principale === true
    );
    if (!flgPrincipale) {
      if (this.categorieSelezionate.length === 1) {
        const check = this.showFlagPrincipale(this.categorieSelezionate[0]);
        if (check) {
          this.categorieSelezionate[0].flg_cat_principale = true;
          this.showSummaryTable = true;
          this.calcolaAutCompetenti(firstLoad);
        } else {
          this.showSummaryTable = false;
          this.showErrorCategPrincipale();
        }
      } else {
        this.showSummaryTable = false;
        this.showErrorCategPrincipale();
      }
    } else {
      this.showSummaryTable = true;
      this.calcolaAutCompetenti(firstLoad);
    }
  }

  showErrorCategPrincipale() {
    this.spinner.hide();
    this._messageService.showConfirmation({
      title: 'Attenzione',
      codMess: 'A016',
      buttons: [
        {
          label: 'CHIUDI',
          type: 'btn-primary single',
          callback: () => {
            this.categorieSelezionate = null;
          },
        },
      ],
    });
  }

  calcolaAutCompetenti(firstLoad = false) {
    if (firstLoad) {
      this.acListWithSecondarie = this.acLoadedList;
      this.acPrincipaliList = this.acLoadedList
        .filter((item) => item.flg_autorita_principale)
        .map((elem) => elem.competenza_territorio);
      this.acSecondarieList = this.acLoadedList
        .filter((item) => item.flg_autorita_principale == false)
        .map((elem) => elem.competenza_territorio);
      this.autCompetenteCardTestoFinaleHtml =
        this.getHelpBannerText('Testo_finale');
      this.setVisibilityButtonNext(true);
      this.setStepCompletedEmit('DettaglioProgettoComponent', true);
      this.spinner.hide();
    } else {
      const compRegionale = this.categorieSelezionate.some(
        (categ) =>
          categ.categoria_oggetto.tipi_competenza[0].cod_tipo_competenza ===
          'REGIONALE'
      );
      const compProvinciale = this.categorieSelezionate.some(
        (categ) =>
          categ.categoria_oggetto.tipi_competenza[0].cod_tipo_competenza ===
          'PROVINCIALE'
      );
      const compComunale = this.categorieSelezionate.some(
        (categ) =>
          categ.categoria_oggetto.tipi_competenza[0].cod_tipo_competenza ===
          'COMUNALE'
      );
      const compArray = [];
      if (compRegionale) {
        compArray.push('REGIONALE');
      }
      if (compProvinciale) {
        compArray.push('PROVINCIALE');
      }
      if (compComunale) {
        compArray.push('COMUNALE');
      }
      const compList = compArray.toString();
      this.adempimentoService
        .calcolaAutoritaCompetenti(this.idIstanza, compList)
        .subscribe(
          (res) => {
            this.acListWithSecondarie = res;
            this.acPrincipaliList = this.acListWithSecondarie
              .filter((item) => item.flg_autorita_principale)
              .map((elem) => elem.competenza_territorio);
            this.acSecondarieList = this.acListWithSecondarie
              .filter((item) => item.flg_autorita_principale == false)
              .map((elem) => elem.competenza_territorio);
            this.autCompetenteCardTestoFinaleHtml =
              this.getHelpBannerText('Testo_finale');

            this.salvaCategorie();
            // this.spinner.hide();
          },
          (err) => {
            this.showErrorsQuadroConCodeENoCode(err, 'searchFormCard');
          }
        );
    }
  }

  checkACSecondarie(list: IstanzaCompetenza[]): IstanzaCompetenza[] {
    return list.filter((ac) => !ac.flg_autorita_principale);
  }

  onAnnulla() {
    this.categorieSelezionate = null;
  }

  salvaCategorie() {
    if (!this.categorieSelezionate) {
      this._messageService.showMessage('E001', 'searchFormCard', true);
      this.spinner.hide();
      return;
    }

    const saveList = [];
    const deleteList = [];
    if (this.savedData) {
      this.categorieSelezionate.forEach((categ) => {
        const flgPut = this.savedData.some(
          (element) =>
            element.id_oggetto_istanza === categ.id_oggetto_istanza &&
            element.categoria_oggetto.id_categoria_oggetto ===
              categ.categoria_oggetto.id_categoria_oggetto
        );
        saveList.push(
          this.categorieService.saveCategorieProgettuali(categ, flgPut)
        );
      });
      this.savedData.forEach((element) => {
        const savedCategoria = this.categorieSelezionate.find(
          (categ) =>
            categ.id_oggetto_istanza === element.id_oggetto_istanza &&
            categ.categoria_oggetto.id_categoria_oggetto ===
              element.categoria_oggetto.id_categoria_oggetto
        );
        if (!savedCategoria) {
          deleteList.push(
            this.categorieService.deleteCategorieProgettuali(element.gestUID)
          );
        }
      });
    } else {
      this.categorieSelezionate.forEach((categ) => {
        saveList.push(this.categorieService.saveCategorieProgettuali(categ));
      });
    }

    forkJoin([...saveList, ...deleteList]).subscribe(
      (res: OggettoIstanzaCategoria[]) => {
        res.forEach((respObj) => {
          const categIndex = this.categorieSelezionate.findIndex(
            (categ) =>
              categ.categoria_oggetto.id_categoria_oggetto ===
                respObj?.categoria_oggetto?.id_categoria_oggetto &&
              categ.id_oggetto_istanza === respObj.id_oggetto_istanza
          );
          if (categIndex > -1) {
            this.categorieSelezionate[categIndex] = respObj;
          }
        });
        // todo: missing logic for multiple oggettiIstanza
        this.categorieService
          .calcolaCategorieAggiuntive(this.oggettiIstanza[0].id_oggetto_istanza)
          .subscribe();
        this.salvaDatiQuadro();
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'divProsegui');
      }
    );
  }

  salvaDatiQuadro() {
    // Preparo le informazioni per il salvataggio dei dati
    const configs: IConfigsSaveQuadro = {
      datiQuadro: { categorieProgettuali: this.categorieSelezionate },
      datiRiepilogo: this.qdr_riepilogo,
    };
    this.saveDataQuadro(configs).subscribe(
      (res) => {
        this.setVisibilityButtonNext(true);
        this.setStepCompletedEmit('DettaglioProgettoComponent', true);
        this.savedData = this.categorieSelezionate;
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'searchFormCard');
      }
    );
  }
  /**
   *
   * @param configs
   * @returns Observable<any>
   * @override
   */
  protected saveDataQuadro(configs: IConfigsSaveDataQuadro): Observable<any> {
    this.spinner.show();
    const qdrRiepilogoConfigs: IPrepareDatiRiepilogo = {
      codQuadro: this.codQuadro,
      codTipoQuadro: this.codTipoQuadro,
      datiQuadro: configs.datiQuadro,
    };
    this.qdr_riepilogo = this.prepareDatiRiepilogo(qdrRiepilogoConfigs);
    const reqSaveQuadroConfigs: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: configs.datiQuadro,
      datiRiepilogo: this.qdr_riepilogo,
    };
    const requestData: RequestSaveBodyQuadro =
      this.buildRequestDataToSaveQuadro(reqSaveQuadroConfigs);
    const requestDataRiepilogo: RequestDataQuadro =
      requestData.requestDataRiepilogo;

    return this.stepManagerService
      .salvaJsonDataQuadro(requestDataRiepilogo, true)
      .pipe(
        tap(() => this.spinner.hide()),
        catchError((e: ScrivaServerError) => {
          this.spinner.hide();
          return throwError(e);
        })
      );
  }

  compareProgetto(p1: OggettoIstanza, p2: OggettoIstanza) {
    return p1 && p2 && p1.id_oggetto_istanza === p2.id_oggetto_istanza;
  }

  /**
   * @returns @override
   */
  protected onAvanti() {
    this.isStepValid().subscribe((isValid: boolean) => {
      if (isValid) {
        this.goToStep(this.stepIndex);
      }else {
        this._messageService.showMessage('E066', 'divProsegui', false);
      }
    });
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
    this.searchTextValueChangesSub.unsubscribe();
    this.progettoValueChangesSub.unsubscribe();
  }

   
  /**
   * @return Observable<boolean> definisce se lo step è valido o no
   * @override
   */
  protected isStepValid(): Observable<boolean> {
    if (!(this.categorieSelezionate?.length > 0)) {
      this._messageService.showMessage('E066', 'divProsegui', false);
      return of(false);
    }
    return of(true);
  }
}
