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
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { clone, identity, isEqual, pickBy } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of, Subscription, throwError } from 'rxjs';
import { catchError, filter, flatMap, switchMap, tap } from 'rxjs/operators';
import {
  ConfigRuoloAdempimento,
  FormSoggettoPF,
  FormSoggettoPG,
  GruppoSoggetto,
  RecapitoAlternativo,
  Referente,
  RuoloSoggetto,
  Soggetto,
  SoggettoIstanza,
  TipoNaturaGiuridica,
  TipoSoggetto,
} from 'src/app/features/ambito/models';
import {
  ControlloCf,
  NumeroEnum,
  SoggettiUtil,
} from 'src/app/features/ambito/utils';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  CompetenzaTerritorio,
  Compilante,
  Istanza,
  IstanzaCompetenza,
  ModalButton,
  Nazione,
  RuoloCompilante,
  StepConfig,
  TipoAdempimento,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  LocationService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { AttoreGestioneIstanzaEnum, RegexUtil } from 'src/app/shared/utils';
import { ScrivaServerError } from '../../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../../core/enums/scriva-codes-messages.enums';
import { ScrivaTipiPersona } from '../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { IAlertConfig } from '../../../../../shared/interfaces/scriva-utilities.interfaces';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { trimOnChange } from '../../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { SoggettiService } from '../../../../../shared/services/soggetti/soggetti.service';
import {
  AmbitoService,
  AmbitoStoreService,
  SoggettoStoreService,
} from '../../../services';
import { ReferenteFormComponent } from './referente-form/referente-form.component';
import { SoggettoFormComponent } from './soggetto-form/soggetto-form.component';
import { ISoggettoFormSubmitData } from './soggetto-form/utilities/soggetto-form.interfaces';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import { IVerificaReferenti } from './utilities/soggetti.utilities';
import {
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo,
  RequestDataQuadro,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';
import { SoggettiConsts } from './utilities/soggetti.consts';

@Component({
  selector: 'app-soggetti',
  templateUrl: './soggetti.component.html',
  styleUrls: ['./soggetti.component.scss'],
})
export class SoggettiComponent
  extends StepperIstanzaComponent
  implements OnInit, AfterViewInit
{
  /** ISoggettiConsts con le costanti del componente. */
  readonly S_C = new SoggettiConsts();

  @ViewChild('denominazioneTemplate') denominazioneTemplate: TemplateRef<any>;
  @ViewChild('indirizzoTemplate') indirizzoTemplate: TemplateRef<any>;
  @ViewChild('dichiaranteTemplate') dichiaranteTemplate: TemplateRef<any>;
  @ViewChild('principaleTemplate') principaleTemplate: TemplateRef<any>;
  @ViewChild('ruoloTemplate') ruoloTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  infoMessModDelega: any = false;
  infoMessModDelegaTxt: any;
  codMaschera = '.MSCR001F';
  codContesto = '';
  nazioni: Nazione[];
  nazioniAttive: Nazione[];

  // def recap soggetti
  ColumnMode = ColumnMode;
  soggettiColumns = [];

  soggetti: (FormSoggettoPF | FormSoggettoPG)[] = [];
  soggettiIstanza: SoggettoIstanza[][] = [];
  recapitiAlternativi: RecapitoAlternativo[] = [];
  editIndexSoggetto = -1;

  // def recap referente
  referentiColumns = [
    {
      label: 'Cognome',
      properties: ['cognome_referente'],
    },
    {
      label: 'Nome',
      properties: ['nome_referente'],
    },
    {
      label: 'Telefono',
      properties: ['num_tel_referente'],
    },
    {
      label: 'Email',
      properties: ['des_email_referente'],
    },
    {
      label: 'Mansione',
      properties: ['des_mansione_referente'],
    },
  ];
  referenti: Referente[] = [];
  editIndexReferente = -1;
  recapReferentiTitle = '<strong>Riepilogo referenti</strong>';

  // logged user
  compilante: Compilante;

  // data from API
  formCercaSoggetti: FormGroup;
  tipiSoggetto: TipoSoggetto[] = [];
  tipiNaturaGiuridica: TipoNaturaGiuridica[] = [];
  ruoliCompilante: RuoloCompilante[];
  ruoliSoggetto: RuoloSoggetto[] = [];
  tipoAdempimento: TipoAdempimento;
  adempimento: Adempimento;
  istanza: Istanza;

  // configuration
  adempimentoConfigList: ConfigRuoloAdempimento[] = [];
  jsonConfiguraTemplate;
  numSoggetti: NumeroEnum;
  tipoSoggetto: string;
  numReferenti: NumeroEnum;
  flagRecapitoAlt: boolean;
  abilitaGruppo: string;

  saveWithPut = false;

  // enums
  tipologiaPersona = ScrivaTipiPersona;
  numerazione = NumeroEnum;
  gestioneEnum = AttoreGestioneIstanzaEnum;

  // search variables
  ultimoTipoPersonaCercato = '';
  ultimoSoggettoCercato: Partial<Soggetto> = null;
  ultimoSoggettoCercatoInAngrafica: boolean = false;
  ultimoCFCercato = '';

  // becomes true on first condition fullfilled (soggetti.length > 0)
  // becomes false on self click then shows no more
  // using 3 states is better: 0 hide 1 show 2 hide permanently
  showButtonContinue = 0;
  // depends on configuration
  showButtonAddReferenti = false;
  // depends on configuration;
  // true on buttonContinue or on buttonAddReferenti
  // false onAnnullaInserisci
  showFormAddReferente = false;

  notaInserimento: string = '';
  segnalazioneInserimento: IAlertConfig;
  anableAddReferenti: boolean = true;
  onlyShow: boolean = false;
  showAction: boolean = true;
  anagraficaRichiedente: any;
  soggettiIstanzaBefore: any;

  //Subscriptions
  jsonConfiguraTemplateSub: Subscription;
  ambitoConfigSub: Subscription;
  formCercaSoggettiValueChangeSub: Subscription;
  eliminaSoggettiIstanzaSub: Subscription;

  //controlla se referente Multipla Operazione
  isReferenteMO: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private ambitoService: AmbitoService,
    private ambitoStoreService: AmbitoStoreService,
    private adempimentoService: AdempimentoService,
    private locationService: LocationService,
    private fb: FormBuilder,
    private modalService: NgbModal,
    private soggettoStoreService: SoggettoStoreService,
    private _soggetti: SoggettiService,
    private _logger: LoggerService,
    presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) injConfig: StepConfig,
    messageService: MessageService,
    helpService: HelpService,
    istanzaService: IstanzaService,
    authStoreService: AuthStoreService,
    stepManagerService: StepManagerService,
    spinner: NgxSpinnerService
  ) {
    super(
      presentazioneIstanzaService,
      injConfig,
      messageService,
      helpService,
      istanzaService,
      authStoreService,
      stepManagerService,
      spinner
    );
  }

  ngAfterViewInit() {
    this._filterSoggettiColumns();
  }

  ngOnInit() {
    this.spinner.show();

    this.infoMessModDelegaTxt = this._messageService.getMessaggio('I028');

    this._getNazioni();

    this._updateStepStatus();
    this.compilante = this.authStoreService.getCompilante();
    this.helpService.setCodMaschera(this.codMaschera);

    this._watchConfigTemplateChange();
  }

  private _getNazioni() {
    this.locationService.getNazioni().subscribe(
      (res) => {
        this.nazioni = res;
        this.nazioniAttive = this.nazioni.filter(
          (nazione) => !nazione.data_fine_validita
        );
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'formInserisciSoggetto');
      }
    );
  }

  private _watchConfigTemplateChange() {
    this.jsonConfiguraTemplateSub =
      this.ambitoStoreService.jsonConfiguraTemplateSub.subscribe((data) => {
        if (data) {
          this.jsonConfiguraTemplate = data;
          if (this.idIstanza) {
            this.loadData();
          } else {
            this._setConfig();
          }
        } else if (this.idIstanza) {
          this.loadData();
        }
      });
  }

  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();
    // ottengo dati istanza, soggetti, referenti e config riepilogo
    this._getIstanza();
  }

  /**
   * Funzione per ottenere i dati dell'istanza
   * settare le informazioni del riepilogo e dei soggetti
   * settare visibilità dei pulsanti avanti, continua e azioni
   * ottenere dati per soggetti e referenti
   * aggiornare stato step
   */
  private _getIstanza() {
    this.istanzaService
      .getIstanzaById(this.idIstanza)
      .pipe(
        tap((istanza) => {
          this.saveWithPut = true;
          this.istanza = istanza;
          this.jsonConfiguraTemplate = JSON.parse(istanza.json_data).QDR_CONFIG;
          this.qdr_riepilogo = JSON.parse(istanza.json_data).QDR_RIEPILOGO;
          this.soggetti =
            this.qdr_riepilogo[this.codTipoQuadro].soggettiForms ||
            this.qdr_riepilogo[this.codTipoQuadro][this.codQuadro]
              ?.soggettiForms;
          this.setVisibilityButtonNext(
            this.mostraAvanti && this.soggetti.length >= 1
          );
        }),
        switchMap((istanza) => {
          const getSoggettiIstanza = this.ambitoService
            .getSoggettiIstanzaByIstanza(istanza.id_istanza)
            .pipe(catchError((err) => of([])));
          const getReferenti = this.ambitoService
            .getReferentiIstanza(istanza.id_istanza)
            .pipe(
              catchError((err) => {
                if (err.status === 404) {
                  return of([]);
                } else {
                  return throwError(err);
                }
              })
            );
          return forkJoin([getSoggettiIstanza, getReferenti]);
        })
      )
      .subscribe(
        (res) => {
          const numQdrAttivi = Object.values(this.qdr_riepilogo).length;
          this.soggettiIstanza = this._buildSoggettiIstanzaArray(res[0]);
          this.recapitiAlternativi = this._buildRecapitiAlternativiArray(
            res[0]
          );
          this.referenti = res[1];

          // warning in caso di elimina soggetto di un'istanza già creata (per ciò verifico che sia attivo almeno 1step successivo)
          if (numQdrAttivi > 2 && this.soggettiIstanza.length == 0) {
            this._messageService.showMessage('A055', 'content', false);
          }

          if (this.referenti.length === 0) {
            this.showButtonContinue = 1;
          } else {
            this.showButtonContinue = 2;
            this.showButtonAddReferenti = true;
          }

          this.showAction =
            (this.numReferenti === this.numerazione.SINGOLO &&
              this.referenti.length === 1) ||
            (this.numReferenti === this.numerazione.OPZIONALE &&
              this.referenti.length === 1)
              ? false
              : true;

          this._setConfig();
          this._updateStepStatus();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'searchFormCard');
        }
      );
  }

  private _buildSoggettiIstanzaArray(
    list: SoggettoIstanza[]
  ): SoggettoIstanza[][] {
    const newArray: SoggettoIstanza[][] = [];
    list.forEach((soggIst) => {
      if (!soggIst.id_soggetto_padre) {
        const i = newArray.findIndex(
          (pair) => pair[0].id_soggetto_istanza === soggIst.id_soggetto_istanza
        );
        if (i === -1) {
          newArray.push([soggIst]);
        }
      } else {
        const i = newArray.findIndex(
          (pair) => pair[0].id_soggetto_istanza === soggIst.id_soggetto_padre
        );
        if (i > -1) {
          newArray[i].push(soggIst);
        } else {
          const soggPadre = list.find(
            (padre) => padre.id_soggetto_istanza === soggIst.id_soggetto_padre
          );
          newArray.push([soggPadre, soggIst]);
        }
      }
    });
    return newArray;
  }

  private _buildRecapitiAlternativiArray(
    list: SoggettoIstanza[]
  ): RecapitoAlternativo[] {
    const recAlt: RecapitoAlternativo[] = [];
    list.forEach((soggIst) => {
      if (soggIst.recapito_alternativo?.length > 0) {
        recAlt.push(...soggIst.recapito_alternativo);
      }
    });
    return recAlt;
  }

  private _setConfig() {
    this.numSoggetti = this.jsonConfiguraTemplate.IndNumSoggetto;
    this.tipoSoggetto = this.jsonConfiguraTemplate.IndTipoSoggetto;
    this.numReferenti = this.jsonConfiguraTemplate.IndNumReferente;
    this.flagRecapitoAlt =
      this.jsonConfiguraTemplate.IndAbilitaRecA === this.numerazione.SINGOLO;
    this.abilitaGruppo = this.jsonConfiguraTemplate.IndAbilitaGruppo;

    if (
      (this.idIstanza && this.numReferenti === this.numerazione.OPZIONALE) ||
      this.numerazione.MULTIPLA_OPZIONALE
    ) {
      this.showButtonAddReferenti = true;
    }

    this._filterSoggettiColumns();
    this._getAdempimento();
  }

  private _filterSoggettiColumns() {
    this.soggettiColumns = [
      {
        name: 'Denominazione',
        minWidth: 160,
        cellTemplate: this.denominazioneTemplate,
        sortable: false,
      },
      {
        name: 'Codice fiscale',
        prop: 'anagraficaSoggetto.cf',
        sortable: false,
      },
      {
        name: 'Indirizzo',
        cellTemplate: this.indirizzoTemplate,
        sortable: false,
      },
      {
        name: 'Dichiarante',
        cellTemplate: this.dichiaranteTemplate,
        sortable: false,
      },
      {
        name: 'Principale',
        cellTemplate: this.principaleTemplate,
        sortable: false,
      },
      {
        name: 'Ruolo ricoperto',
        cellTemplate: this.ruoloTemplate,
        sortable: false,
      },
      { name: 'Azioni', cellTemplate: this.azioniTemplate, sortable: false },
    ];

    if (this.numSoggetti && this.numSoggetti !== this.numerazione.MULTIPLO) {
      // fix visibilità colonna "Principale" ???
      const i = this.soggettiColumns.findIndex(
        (col) => col.name === 'Principale'
      );
      if (i > -1) {
        this.soggettiColumns.splice(i, 1);
      }
    }
  }

  private _getAdempimento() {
    this.adempimentoService
      .getAdempimentoById(this.jsonConfiguraTemplate.Adempimento)
      .subscribe(
        (res) => {
          this.adempimento = res;
          this.tipoAdempimento = this.adempimento.tipo_adempimento;
          this.codContesto = `.${this.tipoAdempimento.cod_tipo_adempimento}.${this.adempimento.cod_adempimento}`;
          this.helpService.setCodContesto(this.codContesto);
          this._init();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'containerCercaSoggetti');
        }
      );
  }

  private _init() {
    this.ambitoConfigSub = forkJoin([
      this.ambitoService.getTipiSoggetto(),
      this.ambitoService.getTipiNaturaGiuridica(),
      this.ambitoService.getConfigurazioneRuoliCompilanteByAdempimento(
        this.adempimento?.id_adempimento
      ),
      this.ambitoService.getRuoliSoggetto(),
    ]).subscribe(
      (result) => {
        this.tipiSoggetto = result[0];
        this.tipiNaturaGiuridica = result[1];
        this.adempimentoConfigList = result[2];
        this.ruoliSoggetto = result[3];

        this._buildFormCercaSoggetti();
        // Vado a vedere se dalle configurazioni dell'IndTipoSoggetto è necessario settare il tipo soggetto di default
        this._setTipoSoggettoByJsonConfiguraTemplate();
        this.onChangeTipoSoggetto();
        this.spinner.hide();
      },
      (error) => {
        this.showErrorsQuadroConCodeENoCode(error, 'containerCercaSoggetti');
      }
    );
    return this.ambitoConfigSub;
  }

  /**
   * Funzione di comodo che gestisce il set dei dati all'interno del form di ricerca soggetti.
   * Essendo che il codice è fatto male, è impiegato un try catch per evitare problemi di oggetti non referenziati.
   */
  private _setTipoSoggettoByJsonConfiguraTemplate() {
    // Uso un try catch di sicurezza per non spaccare tutto nel caso in cui mancano gli oggetti per qualche motivo
    try {
      // Recupero valore del tipo soggetto impostato dopo il set delle configurazioni del json configura template
      const codiceTipoSoggetto: string = this.tipoSoggetto;
      const ts = this.tipiSoggetto.find((tipoSoggetto: TipoSoggetto) => {
        // Effettuo un match tra codici
        return tipoSoggetto.cod_tipo_soggetto === codiceTipoSoggetto;
        // #
      });
      // Imposto nel form il tipo soggetto
      this.formCercaSoggetti.get('tipoSoggetto').setValue(ts);
      // #
    } catch (e) {}
  }

  filterTipiSoggetto(tipi: TipoSoggetto[]) {
    const filteredList: TipoSoggetto[] = [];
    if (this.tipoSoggetto.includes(this.tipologiaPersona.PF)) {
      filteredList.push(
        tipi.find((tipo) => tipo.cod_tipo_soggetto === this.tipologiaPersona.PF)
      );
    }
    if (this.tipoSoggetto.includes(this.tipologiaPersona.PG)) {
      filteredList.push(
        tipi.find((tipo) => tipo.cod_tipo_soggetto === this.tipologiaPersona.PG)
      );
    }
    if (this.tipoSoggetto.includes(this.tipologiaPersona.PB)) {
      filteredList.push(
        tipi.find((tipo) => tipo.cod_tipo_soggetto === this.tipologiaPersona.PB)
      );
    }
    return filteredList;
  }

  onHelpClicked(chiaveHelp: string) {
    let chiave = `${this.componente}.${this.tipoAdempimento.cod_tipo_adempimento}.${this.adempimento.cod_adempimento}.${this.codQuadro}`;
    if (chiaveHelp) {
      chiave += `.${chiaveHelp}`;
    }

    this.helpService.getHelpByChiave(chiave).subscribe(
      (res) => {
        const modalContent = res[0]?.des_testo_help || 'Help non trovato...';

        this._messageService.showConfirmation({
          title: 'HELP',
          codMess: null,
          content: modalContent,
          buttons: [],
        });
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'searchForm');
      }
    );
  }

  private _buildFormCercaSoggetti() {
    this.formCercaSoggetti = this.fb.group(
      {
        cf: [
          '',
          {
            validators: [
              Validators.required,
              Validators.maxLength(16),
              Validators.pattern(RegexUtil.CF_OR_PIVA),
            ],
          },
        ],
        tipoSoggetto: [
          '',
          {
            validators: [Validators.required],
          },
        ],
      },
      {
        validators: [this.cfValidator],
      }
    );

    if (this.attoreGestioneIstanza === 'WRITE') {
      this.formCercaSoggetti.get('cf').enable();
      this.formCercaSoggetti.get('tipoSoggetto').enable();
    } else {
      this.formCercaSoggetti.get('cf').disable();
      this.formCercaSoggetti.get('tipoSoggetto').disable();
      this.anableAddReferenti = false;
      this.onlyShow = true;
    }
    this.formCercaSoggettiValueChangeSub = this.formCercaSoggetti
      .get('tipoSoggetto')
      .valueChanges.pipe(filter((tipoSoggetto) => !!tipoSoggetto))
      .subscribe((tipoSoggetto: TipoSoggetto) => {
        // change validator on CF
        // keep duplicated control for more readability
        if (tipoSoggetto.cod_tipo_soggetto === this.tipologiaPersona.PF) {
          this.formCercaSoggetti
            .get('cf')
            ?.setValidators([
              Validators.required,
              Validators.maxLength(16),
              Validators.pattern(RegexUtil.CF),
            ]);

          this.formCercaSoggetti.get('cf').markAsTouched({ onlySelf: true });
          this.formCercaSoggetti.get('cf').updateValueAndValidity();
        } else if (
          tipoSoggetto.cod_tipo_soggetto === this.tipologiaPersona.PB
        ) {
          this.formCercaSoggetti
            .get('cf')
            ?.setValidators([
              Validators.required,
              Validators.maxLength(11),
              Validators.pattern(RegexUtil.PIVA),
            ]);

          this.formCercaSoggetti.get('cf').markAsTouched({ onlySelf: true });
          this.formCercaSoggetti.get('cf').updateValueAndValidity();
        } else {
          // PG
          this.formCercaSoggetti
            .get('cf')
            ?.setValidators([
              Validators.required,
              Validators.maxLength(16),
              Validators.pattern(RegexUtil.CF_OR_PIVA),
            ]);

          this.formCercaSoggetti.get('cf').markAsTouched({ onlySelf: true });
          this.formCercaSoggetti.get('cf').updateValueAndValidity();
        }
      });

    trimOnChange(this.formCercaSoggetti, 'cf');
  }

  onChangeTipoSoggetto() {
    // Resetto la lista dei ruoli compilante
    this.ruoliCompilante = [];

    //setto che i dati dello step sono cambiati
    this.changed = true;

    // Vado a completare la lista di oggetti per i ruoli compilante con gli specifici flag
    this._soggetti.completaRuoliCompilantiInRuoliAdempimentiConfigs(
      this.adempimentoConfigList
    );

    // Recupero il codice del tipo soggetto dalla form
    const codTipoSoggetto = (<TipoSoggetto>(
      this.formCercaSoggetti.get('tipoSoggetto').value
    ))?.cod_tipo_soggetto;
    // Imposto un flag per il tipo persona fisica
    const tipoSoggettoPF = this.tipologiaPersona.PF;
    // Verifico se il soggetto ricerca è lo stesso dell'attore in linea
    const soggettoAsAttoreInLinea =
      this.formCercaSoggetti.get('cf')?.value ===
      this.compilante?.cf_compilante;

    // Verifico se il tipo persona ricerca è una persona fisica
    if (codTipoSoggetto === tipoSoggettoPF) {
      // Richiamo la funzione per ottenere i ruoli compilante per PF
      this.ruoliCompilante = this._soggetti.ruoliCompilantePF(
        this.adempimentoConfigList,
        soggettoAsAttoreInLinea
      );
      // #
    } else {
      // Richiamo la funzione per ottenere i ruoli compilante per PG
      this.ruoliCompilante = this._soggetti.ruoliCompilantePG(
        this.adempimentoConfigList
      );
      // #
    }
  }

  /**
   * Funzione che effettua le verifiche e la ricerca dati per il soggetto.
   */
  onCercaSoggetto() {
    // Resetto le segnalazioni per inserimento soggetto
    this.segnalazioneInserimento = undefined;

    this.spinner.show();
    if (this.numSoggetti === NumeroEnum.SINGOLO && this.soggetti?.length > 0) {
      this._messageService.showMessage('E003', 'containerCercaSoggetti', true);
      return;
    }

    this.ultimoSoggettoCercato = null;
    this.ultimoSoggettoCercatoInAngrafica = false;
    this.editIndexSoggetto = -1;
    this.formCercaSoggetti.markAllAsTouched();

    // Verifico la validità del form di ricerca soggetto
    if (this.formCercaSoggetti.valid) {
      // Lancio la funzione per gestire il flusso se il form è valido
      this.onCercaSoggettoFormValid();
      // #
    } else {
      const cfErrors = this.formCercaSoggetti.get('cf').errors;
      if (cfErrors) {
        if (cfErrors.required) {
          this._messageService.showMessage(
            'E002',
            'containerCercaSoggetti',
            true
          );
        } else {
          this._messageService.showMessage(
            'E004',
            'containerCercaSoggetti',
            true
          );
        }
        return;
      }

      const tipoSoggettoErrors =
        this.formCercaSoggetti.get('tipoSoggetto').errors;
      if (tipoSoggettoErrors) {
        if (tipoSoggettoErrors.required) {
          this._messageService.showMessage(
            'E002',
            'containerCercaSoggetti',
            true
          );
        } else {
          this._messageService.showMessage(
            'E004',
            'containerCercaSoggetti',
            true
          );
        }
        return;
      }

      this._messageService.showMessage('E100', 'containerCercaSoggetti', true);
    }
  }

  /**
   * Funzione che gestisce le logiche di ricerca per il soggetto quando il form contiene dati validi.
   */
  private onCercaSoggettoFormValid() {
    // Recupero dall'input del codice fiscale il valore inserito
    const CFCercato = this.formCercaSoggetti.get('cf').value;
    // Cerco all'interno dei soggetti già caricati nel componente è presente quello per il cf inserito nell'input
    let isCFAlreadyInSoggetti: boolean;
    isCFAlreadyInSoggetti = this.soggetti?.some(
      (soggetto: FormSoggettoPF | FormSoggettoPG) => {
        // Verifico per stesso codice fiscale
        return soggetto.anagraficaSoggetto.cf === CFCercato;
        // #
      }
    );

    // Verifico se ho trovato il codice fiscale nei soggetti
    if (isCFAlreadyInSoggetti) {
      // Gestisco l'errore, non può cercare se il soggetto è già inserito
      this._messageService.showMessage('E012', 'containerCercaSoggetti', true);
      // Blocco il flusso
      return;
    }

    // Recupero il tipo soggetto dall'input della form
    let tipoSoggettoForm: Partial<TipoSoggetto>;
    tipoSoggettoForm = this.formCercaSoggetti.get('tipoSoggetto')?.value;
    // Dal tipo soggetto della form, recupro il suo codice tipo soggetto
    const codTipoSogg: string = tipoSoggettoForm?.cod_tipo_soggetto;
    // Recupero il codice adempimento
    const codAdempimento: string = this.adempimento.cod_adempimento;

    // Effetuo la ricerca del soggetto poiché non è già inserito nei soggetti dell'istanza
    this._getSoggettoAdempimento(CFCercato, codTipoSogg, codAdempimento);
  }

  /**
   * Funzione per la ottenere il soggetto in base al cf,tipo e codAdempimento del soggetto
   * @param CFCercato
   * @param codTipoSogg
   * @param codAdempimento
   */
  private _getSoggettoAdempimento(
    CFCercato: string,
    codTipoSogg: string,
    codAdempimento: string
  ) {
    this.ambitoService
      .getSoggettoAdempimento(CFCercato, codTipoSogg, codAdempimento)
      .subscribe({
        next: (soggetto: Soggetto) => {
          // Chiudo lo spinner
          this.spinner.hide();
          // Richiamo la funzione per gestire il flusso di success
          this.onCercaSoggettoSuccess(soggetto);
          // #
        },
        error: (error: ScrivaServerError) => {
          // Chiudo lo spinner
          this.spinner.hide();
          // Lancio la funzione per gestire l'errore alla ricerca soggetto
          this.onCercaSoggettoError(error);
          // #
        },
      });
  }

  /**
   * Funzione che gestisce il flusso dati nel momento in cui la ricerca soggetto va a buon fine.
   * @param soggetto Soggetto con i dati del soggetto scaricati.
   */
  private onCercaSoggettoSuccess(soggetto: Soggetto) {
    // Recupero dal soggetto scaricato la data cessazione
    const dataCessazione: string = soggetto.data_cessazione_soggetto;
    // Verifico se esiste la data cessazione
    if (dataCessazione) {
      // Il soggetto ha una data cessazione, chiedo conferma dell'inserimento
      this.chiediConfermaPerSoggettoCessato(soggetto);
      // #
    } else {
      // Il soggetto potrebbe essere parziale, definisco una nota
      const notaInserimento: string = `Verifica i dati anagrafici del soggetto ricercato e se necessario aggiorna le informazioni.`;
      // Vado ad aggiungere il soggetto normalmente
      this.onCercaSoggettoAggiungiDati(soggetto, notaInserimento);
      // #
    }
  }

  /**
   * Funzione di flusso che chiede la conferma di aggiunta di un soggetto ricercato nel caso in cui il soggetto sia "cessato".
   * @param soggetto Soggetto con i dati del soggetto trovato dalla chiamata.
   */
  private chiediConfermaPerSoggettoCessato(soggetto: Soggetto) {
    // Definisco le informazioni per l'apertura della modale di conferma
    const title = 'Attezione';
    const code = ScrivaCodesMesseges.A025;
    const btnAnnulla: ModalButton = {
      label: 'ANNULLA',
      type: 'btn-link',
      callback: () => {},
    };
    const btnConferma: ModalButton = {
      label: 'CONFERMA',
      type: 'btn-primary',
      callback: () => {
        // Richiamo il salvataggio
        this.onCercaSoggettoAggiungiDati(soggetto);
        // #
      },
    };
    // Apro la modale passando le configurazioni per chiedere conferma all'utente
    this._messageService.showConfirmation({
      title: title,
      codMess: code,
      buttons: [btnAnnulla, btnConferma],
    });
    // #
  }

  /**
   * Funzione che gestisce il flusso di finalizzazione dei dati del soggetto per l'istanza.
   * @param soggetto Soggetto con i dati del soggetto trovato dalla ricerca.
   * @param notaInserimento string con eventuali note per l'inserimento del soggetto.
   */
  private onCercaSoggettoAggiungiDati(
    soggetto: Soggetto,
    notaInserimento?: string
  ) {
    // Resetto i dati della form di ricerca
    this.formCercaSoggetti.reset();
    // Aggiorno le informazioni del componente
    this.ultimoSoggettoCercato = soggetto;
    this.ultimoSoggettoCercatoInAngrafica = true;
    this.ultimoCFCercato = soggetto.cf_soggetto;
    this.ultimoTipoPersonaCercato = soggetto.tipo_soggetto.cod_tipo_soggetto;
    this.notaInserimento = notaInserimento ?? '';
    this.soggettoStoreService.soggettoSnapshot = clone(
      this.ultimoSoggettoCercato
    );
    this.onChangeTipoSoggetto();

    /**
     * @author Ismaele Bottelli
     * @date 19/12/2024
     * @jira SCRIVA-1581
     * @notes E' stato segnalato un bug, a quanto pare la reset delle formCercaSoggetti, in casi specifici (il tipo soggetto è nascosto) va
     *        a resettare dei dati che tecnicamente dovrebbero rimanere fissi. Non tocco il flusso che c'è adesso, ma aggiungo una gestione.
     *        Come avviene per l'inizializzazione dei dati [] cerco d'impostare il tipo soggetto in automatico se definito dalle configurazioni.
     *        Sta pagina dovrebbe essere demolita e rifatta da zero per quanto è stata fatta male aggiungendo codice su codice.
     */
    // Vado ad impostare nuovamente il tipo soggetto se esiste una configurazione specifica
    this._setTipoSoggettoByJsonConfiguraTemplate();
  }

  /**
   * Funzione che gestisce il flusso dati nel momento in cui la ricerca soggetto va in errore.
   * @param error ScrivaServerError con l'errore ritornato dal server.
   */
  private onCercaSoggettoError(error: ScrivaServerError) {
    // Verifico se non sono stati trovati dati
    if (error.status === 404) {
      // SCRIVA-1236 => La segnalazione è da far visualizzare all'interno del contenitore del soggetto e non "fuori", appena sopra come ora
      // this.ms.showMessage('I001', 'containerCercaSoggetti', true);
      // Definisco un oggetto di configurazione da passare alla form soggetto
      const config404: IAlertConfig = {
        code: 'I001',
        idDOM: 'containerSegnalazioniInserimento',
        autoFade: true,
      };
      // Aggiungo la segnalazione alla lista
      this.segnalazioneInserimento = config404;

      this.ultimoTipoPersonaCercato = (<TipoSoggetto>(
        this.formCercaSoggetti.get('tipoSoggetto').value
      ))?.cod_tipo_soggetto;
      this.ultimoCFCercato = this.formCercaSoggetti.get('cf').value;
      this.ultimoSoggettoCercato = {
        cf_soggetto: this.ultimoCFCercato,
        tipo_soggetto: {
          cod_tipo_soggetto: this.ultimoTipoPersonaCercato,
        },
      };
      this.ultimoSoggettoCercatoInAngrafica = false;
      this.notaInserimento =
        "Il soggetto non è stato trovato: puoi procedere con l'inserimento del soggetto oppure modifica i criteri di ricerca.";
      this.onChangeTipoSoggetto();
      this.soggettoStoreService.soggettoSnapshot = clone(
        this.ultimoSoggettoCercato
      );
    } else {
      this.notaInserimento = '';
      this.showErrorsQuadroConCodeENoCode(error, 'containerCercaSoggetti');
    }
  }

  /**
   * Funzione che resetta in maniera completa le informazioni relative alla ricerca del soggetto.
   * @author Ismaele Bottelli
   * @date 17/01/2025
   * @jira SCRIVA-1586
   * @notes E' stata richiesta una reset completa delle informazioni, ma sto componente è un delirio.
   *        Ho replicato le logiche di reset di quando cerchi e trovi effettivamente il soggetto, andando però a resettare tutte le informazioni.
   */
  private resetFormRicercaSoggetto() {
    // Resetto i dati della form di ricerca
    this.formCercaSoggetti.reset();
    // Aggiorno le informazioni del componente
    this.ultimoCFCercato = '';
    this.ultimoSoggettoCercato = null;
    this.ultimoSoggettoCercatoInAngrafica = false;
    this.ultimoTipoPersonaCercato = '';
    this.editIndexSoggetto = -1;

    /**
     * @author Ismaele Bottelli
     * @date 19/12/2024
     * @jira SCRIVA-1581
     * @notes E' stato segnalato un bug, a quanto pare la reset delle formCercaSoggetti, in casi specifici (il tipo soggetto è nascosto) va
     *        a resettare dei dati che tecnicamente dovrebbero rimanere fissi. Non tocco il flusso che c'è adesso, ma aggiungo una gestione.
     *        Come avviene per l'inizializzazione dei dati [] cerco d'impostare il tipo soggetto in automatico se definito dalle configurazioni.
     *        Sta pagina dovrebbe essere demolita e rifatta da zero per quanto è stata fatta male aggiungendo codice su codice.
     */
    // Vado ad impostare nuovamente il tipo soggetto se esiste una configurazione specifica
    this._setTipoSoggettoByJsonConfiguraTemplate();
  }

  /**
   * ####################################
   * FUNZIONI DI GESTIONE SUBMIT SOGGETTO
   * ####################################
   */

  /**
   * Funzione agganciata all'evento del componente soggetto-form.
   * La funzione intercetta il submit dei dati del soggeto.
   * @param formSubmit ISoggettoFormSubmitData con le informazioni generate dal submit del form.
   */
  onInserisciSoggetto(formSubmit: ISoggettoFormSubmitData) {
    // Lancio lo spinner di caricamento
    this.spinner.show();

    // Verifico se l'istanza ha già un "gestUID"
    if (this.istanza?.gestUID) {
      // Ne ha già uno, lancio l'inserimento soggetto
      this._inserisciSoggetto(formSubmit);
      // #
    } else {
      // Lancio il flusso di creazione di una nuova istanza, prima di inserire il soggetto
      this.gestisciIstanzaPoiSoggetto(formSubmit);
      // #
    }
  }

  /**
   * Funzione specifica che indirizza il flusso dati generando le informazioni dell'istanza.
   * Una volta completata la gestione dell'istanza si continuerà con l'aggiornamento del soggetto.
   * @param formSubmit ISoggettoFormSubmitData compatibile con le logiche del componente per l'inserimento soggetto.
   */
  private gestisciIstanzaPoiSoggetto(formSubmit: ISoggettoFormSubmitData) {
    // Definisco un oggetto per la gestione della dichiarazione orientamento (dati tecnici)
    const dichiarazioni: any = this.jsonConfiguraTemplate['dichiarazioni'];
    const dichiarazioneOrientamento = { dichiarazioni };

    // Creo un nuovo oggetto Istanza, definendo le informazioni per il salvataggio
    const nuovaIstanza: Istanza = {
      // compilante: this.compilante,
      id_template: this.idTemplate,
      adempimento: this.adempimento,
      stato_istanza: this.istanza?.stato_istanza || { id_stato_istanza: 10 },
      json_data: JSON.stringify(dichiarazioneOrientamento),
    };

    // Lancio la funzione gi salvataggio di una nuova istanza
    this._salvaIstanza(nuovaIstanza, formSubmit);
  }

  /**
   * Funzione che salva la nuova istanza
   * @param nuovaIstanza
   */
  private _salvaIstanza(
    nuovaIstanza: Istanza,
    formSubmit: ISoggettoFormSubmitData
  ) {
    this.istanzaService
      .salvaIstanza(nuovaIstanza)
      .pipe(
        // Intercetto possibili errori sul salvataggio dell'istanza
        catchError((err) => {
          // Lancio la funzione di gestione dell'errore
          this.onCercaSoggettiError(err);
          // Continuo con la gestione dell'errore
          return throwError(err);
          // #
        }),
        // A salvataggio completato, aggiorno le informazioni del componente
        tap((istanza: Istanza) => {
          this.istanza = istanza;
          this.istanzaService.setIdIstanza(this.istanza.id_istanza);
          this.istanzaService.setAttoreGestioneIstanza(
            AttoreGestioneIstanzaEnum.WRITE as string
          );
        }),
        // Effettuo il salvataggio dei dati per il quadro
        switchMap(() => this.saveQuadroConfig())
      )
      .subscribe((res: any) => {
        // Lancio il salvataggio dei dati del soggetto
        this._inserisciSoggetto(formSubmit);
      });
  }

  /**
   * Funzione dedicata al salvataggio dei dati del soggetto generati dal submit del form soggetto.
   * La funzione gestisce le casistiche dati generate dal form, aggiorna le informazioni a livello di componente e tenta di salvare il soggetto.
   * @param formSubmit ISoggettoFormSubmitData compatibile con le logiche del componente per l'inserimento soggetto.
   */
  private _inserisciSoggetto(formSubmit: ISoggettoFormSubmitData) {
    // Estraggo dall'oggetto in input le informazioni per il salvataggio del soggetto
    const { soggetto, firmatario, formRawValue } = formSubmit || {};

    // Verifico il tipo di persona gestito dal form
    if (firmatario) {
      // Lancio la funzione d'inserimento specifica per un soggetto con firmatario
      this.inserisciSoggettoConCompilante(soggetto, firmatario, formRawValue);
      // #
    } else {
      this.soggettiIstanzaBefore = this.soggettiIstanza[0];
      // Lancio la funzione d'inserimento specifica per il solo soggetto
      this.inserisciSoggettoSenzaCompilante(soggetto, formRawValue);
    }

    // elimina eventuale messaggio di warning
    this._messageService.showMessage(
      'A055',
      'content',
      true,
      { ph: '', swap: '' },
      '',
      true
    );
  }

  private cancellaRichiedente() {
    if (this.soggettiIstanzaBefore && this.anagraficaRichiedente) {
      let utenteTrovato: any;
      let gestUID: string | null = null;

      const anagraficaRichiedente = this.anagraficaRichiedente;

      this.soggettiIstanzaBefore.forEach((soggetto) => {
        if (anagraficaRichiedente.cf === soggetto.cf_soggetto) {
          utenteTrovato = soggetto;
          gestUID = soggetto.gestUID;
          return;
        }
      });

      // elimino il richiedente trovato
      if (utenteTrovato) {
        this._watchEliminaSub(gestUID);
      } else {
        console.log('Nessun richiedente trovato.');
      }
    }
  }

  /**
   * Funzione per avviare subscription sull'evento elimina soggetto istanza
   * @param gestUID
   */
  private _watchEliminaSub(gestUID: string) {
    this.eliminaSoggettiIstanzaSub = this.ambitoService
      .eliminaSoggettiIstanza(gestUID)
      .subscribe((error) => {
        console.log(
          'Si è verificato un errore durante la cancellazione del Richiedente ',
          error
        );
      });
  }

  /**
   * Funzione che gestisce il flusso di salvataggio del soggetto a seguito del submit del form soggetto e il soggetto è di tipo: persona fisica.
   * @param soggetto Soggetto con le informazioni del soggetto passate dal submit del form.
   * @param formRawValue any con l'oggetto generato dal form con i dati del soggetto presenti in maschera.
   */
  private inserisciSoggettoSenzaCompilante(
    soggetto: Soggetto,
    formRawValue: any
  ) {
    // Per tipo soggetto = persona fisica > 1 soggetto > 1 soggetto-istanza
    const sogg = soggetto;

    // Recupero i dati per aggiornare i soggetto
    const soggettoSnapshot = this.soggettoStoreService.soggettoSnapshot;
    // Aggiorno i dati per gli id_masterdata e origine
    this._aggiornaSoggIdMasterdataEOrigine(sogg, soggettoSnapshot);

    // Lancio il salvataggio del soggetto
    this._salvaSoggetti(sogg, formRawValue);
  }

  /**
   * Funzione per salvare soggetti
   * @param sogg
   * @param formRawValue
   */
  private _salvaSoggetti(sogg: Soggetto, formRawValue: any) {
    this.ambitoService
      .salvaSoggetti(sogg)
      .pipe(
        // Intercetto il completamento del salvataggio per aggiornare i dati delle anagrafiche dell'oggetto del form
        tap((soggetto: Soggetto) => {
          // Recupero l'anagrafica dall'oggetto del form
          const anagraficaSoggetto = formRawValue?.anagraficaSoggetto;
          // Aggiorno i dati dell'anagrafica soggetto
          this._aggiornaAnagraficaForm(anagraficaSoggetto, soggetto);
          // Lancio il salvataggio del quadro
          this.salvaDatiQuadro();
          // #
        }),
        switchMap((soggetto: Soggetto) => {
          // Tento di recuperare il soggetto dell'istanza
          const soggIstanza = this._setDatiSoggettoIstanza(
            soggetto,
            formRawValue
          );
          return this.ambitoService.salvaSoggettiIstanza(soggIstanza);
        })
      )
      .subscribe({
        next: (soggettoIstanza: SoggettoIstanza) => {
          if (this.editIndexSoggetto > -1) {
            this.soggetti.splice(this.editIndexSoggetto, 1, formRawValue);
            this.soggettiIstanza.splice(this.editIndexSoggetto, 1, [
              soggettoIstanza,
            ]);
          } else {
            this.soggetti.push(formRawValue);
            this.soggettiIstanza.push([soggettoIstanza]);
          }
          this._updateSoggettiEStatusECheckRecapito(
            soggettoIstanza,
            formRawValue,
            this.soggetti,
            true
          );
        },
        error: (e: ScrivaServerError) => {
          // Gestisco l'errore con la specifica del fatto che l'inserisci soggetto è fallito
          this.onInserisciSoggettiError(e);
          // #
        },
      });
  }

  /**
   * Funzione che dopo aver trovato soggetto istanza setta
   * flag capogruppo, id e des gruppo, flag creazione automatica
   * @param soggetto
   * @param formRawValue
   * @returns SoggettoIstanza con le informazioni aggiornate.
   */
  private _setDatiSoggettoIstanza(soggetto: Soggetto, formRawValue: any) {
    // Recupero dall'oggetto del form il ruolo compilante
    const ruoloCompilante = formRawValue.ruoloCompilante;
    let soggIstanza: SoggettoIstanza = this._recuperaSoggettoIstanza(
      soggetto,
      ruoloCompilante
    );
    if (this.numSoggetti === this.numerazione.MULTIPLO) {
      if (this.soggetti.length === 0) {
        soggIstanza.flg_capogruppo = true;
      } else {
        soggIstanza.flg_capogruppo = soggIstanza.flg_capogruppo || false;
      }
      formRawValue.anagraficaSoggetto.flg_capogruppo =
        soggIstanza.flg_capogruppo;

      if (!soggIstanza.gruppo_soggetto) {
        soggIstanza.gruppo_soggetto = {
          id_gruppo_soggetto:
            this.soggettiIstanza.length > 0
              ? this.soggettiIstanza[0][0].gruppo_soggetto.id_gruppo_soggetto
              : null,
          des_gruppo_soggetto:
            this.soggettiIstanza.length > 0
              ? this.soggettiIstanza[0][0].gruppo_soggetto.des_gruppo_soggetto
              : soggetto.cognome + ' ' + soggetto.nome,
          flg_creazione_automatica: this.abilitaGruppo === 'SYS',
        };
      }
    }
    return soggIstanza;
  }

  /**
   * Funzione che gestisce il flusso di salvataggio del soggetto a seguito del submit del form soggetto e il soggetto è di tipo: persona giuridica.
   * @param soggetto Soggetto con le informazioni del soggetto passate dal submit del form.
   * @param firmatario Soggetto con le informazioni del soggetto firmatario dal submit del form.
   * @param formRawValue any con l'oggetto generato dal form con i dati del soggetto presenti in maschera.
   */
  private inserisciSoggettoConCompilante(
    soggetto: Soggetto,
    firmatario: Soggetto,
    formRawValue: any
  ) {
    // Recupero i dati per aggiornare i soggetto
    const soggettoSnapshot = this.soggettoStoreService.soggettoSnapshot;
    const richiedenteSnapshot = this.soggettoStoreService.richiedenteSnapshot;
    // Aggiorno i dati per gli id_masterdata e origine
    this._aggiornaSoggIdMasterdataEOrigine(soggetto, soggettoSnapshot);
    this._aggiornaSoggIdMasterdataEOrigine(firmatario, richiedenteSnapshot);

    // Effettuo un doppio salvataggio del soggetto, sia per quello cercato sia quello richiedente
    forkJoin({
      soggetto: this.ambitoService.salvaSoggetti(soggetto),
      richiedente: this.ambitoService.salvaSoggetti(firmatario),
    })
      .pipe(
        // Intercetto il completamento del salvataggio per aggiornare i dati delle anagrafiche dell'oggetto del form
        tap((soggetti: { soggetto: Soggetto; richiedente: Soggetto }) => {
          // Estraggo dall'oggetto di response le informazioni aggiornate di soggetto e richiedente
          const { soggetto: soggettoIns, richiedente: richiedenteIns } =
            soggetti || {};

          // Recupero le anagrafiche dall'oggetto del form
          const anagraficaSoggetto = formRawValue?.anagraficaSoggetto;
          const anagraficaRichiedente = formRawValue?.anagraficaRichiedente;
          // Aggiorno i dati delle anagrafiche
          this._aggiornaAnagraficaForm(anagraficaSoggetto, soggettoIns);
          this._aggiornaAnagraficaForm(anagraficaRichiedente, richiedenteIns);
          // Lancio il salvataggio del quadro
          this.salvaDatiQuadro();
          // #
        })
      )
      .subscribe(
        (soggetti: { soggetto: Soggetto; richiedente: Soggetto }) => {
          // Estraggo dall'oggetto di response le informazioni aggiornate di soggetto e richiedente
          const { soggetto: soggettoIns, richiedente: richiedenteIns } =
            soggetti || {};

          // Tento di recuperare il soggetto dell'istanza
          const soggIstanza = this._setDatiSoggettoIstanza(
            soggetto,
            formRawValue
          );

          this.ambitoService
            .salvaSoggettiIstanza(soggIstanza)
            .pipe(
              catchError((error) => {
                throw new Error(
                  'salvaSoggettiIstanza::error: ' + error.error?.title
                );
              }),
              tap((soggettoIstanza) => {
                if (this.editIndexSoggetto > -1) {
                  this.soggettiIstanza[this.editIndexSoggetto][0] =
                    soggettoIstanza;
                } else {
                  this.soggettiIstanza.push([soggettoIstanza]);
                }
                this.checkRecapitoAlternativo(
                  soggettoIstanza.id_soggetto_istanza,
                  formRawValue?.anagraficaSoggetto?.recapitoAlternativoSedeLegale
                );
              }),
              switchMap((soggettoIstanzaPadre: SoggettoIstanza) => {
                // Per il salvataggio del richiedente, bisogna verificare che esisti effettivamente un richiedente da modificare, altrimenti lo si tratta come inserimento
                const istanza: Istanza = this.istanza;
                const richiedente: Soggetto = richiedenteIns;
                const ruoloCompilante: RuoloCompilante =
                  formRawValue?.ruoloCompilante;
                const gruppoSoggetto: GruppoSoggetto = null;
                const ruoloSoggettoPadre: RuoloSoggetto =
                  formRawValue?.anagraficaRichiedente?.ruoloSoggetto;
                // Definisco le informazioni specifiche per la gestione del soggetto (richiedente) istanza
                let richiedenteIstanza: SoggettoIstanza;
                let idRicIstanza: number = null;
                let gestUIDRicIstanza: string = null;

                // Per il richiedente tento di recuperarlo dalla lista presente a livello componente con la gestione dell'index soggetto
                if (this.editIndexSoggetto > -1) {
                  // Recupero l'oggetto del richiedente
                  richiedenteIstanza =
                    this.soggettiIstanza[this.editIndexSoggetto][1];

                  // Verifico se esiste effettivamente un soggetto istanza, con i dati del richiedente, nel componente
                  if (richiedenteIstanza) {
                    // E' già presente un soggetto istanza per il richiedente, recupero i dati
                    idRicIstanza = richiedenteIstanza.id_soggetto_istanza;
                    gestUIDRicIstanza = richiedenteIstanza.gestUID;
                  }
                }

                // Genero l'oggetto SoggettoIstanza per la gestione del richiedente
                const soggIstanzaFiglio = SoggettiUtil.returnSoggettoIstanza(
                  istanza,
                  richiedente,
                  ruoloCompilante,
                  idRicIstanza,
                  gestUIDRicIstanza,
                  gruppoSoggetto,
                  ruoloSoggettoPadre,
                  soggettoIstanzaPadre
                );

                if (this.numSoggetti === this.numerazione.MULTIPLO) {
                  soggIstanzaFiglio.gruppo_soggetto =
                    soggettoIstanzaPadre.gruppo_soggetto;
                  soggIstanzaFiglio.flg_capogruppo = false;
                }

                return this.ambitoService.salvaSoggettiIstanza(
                  soggIstanzaFiglio
                );
              })
            )
            .subscribe(
              (soggettoIstanza: SoggettoIstanza) => {
                if (this.editIndexSoggetto > -1) {
                  this.soggetti.splice(this.editIndexSoggetto, 1, formRawValue);
                  this.soggettiIstanza[this.editIndexSoggetto][1] =
                    soggettoIstanza;
                } else {
                  this.soggetti.push(formRawValue);
                  this.soggettiIstanza[this.soggettiIstanza.length - 1].push(
                    soggettoIstanza
                  );
                }
                this._updateSoggettiEStatusECheckRecapito(
                  soggettoIstanza,
                  formRawValue,
                  this.soggetti,
                  false
                );
              },
              (error) => {
                this.showErrorsQuadroConCodeENoCode(
                  error,
                  'containerCercaSoggetti'
                );
              }
            );
        },
        (error) => {
          this.showErrorsQuadroConCodeENoCode(error, 'containerCercaSoggetti');
        }
      );
  }

  /**
   * Funzione che aggiorna dati dei soggetti e visibilità del pulsante avanti,
   * pulisce informazioni del quadro
   * mostra pulsante continua e controlla se mostrare pulsante aggiungi referenti
   * controlla recapito alternativo
   * aggiorna lo stato dello step
   * @param soggettoIstanza
   * @param formRawValue
   * @param soggetti
   */
  private _updateSoggettiEStatusECheckRecapito(
    soggettoIstanza: SoggettoIstanza,
    formRawValue: any,
    soggetti: (FormSoggettoPF | FormSoggettoPG)[],
    isCancellaDatiRichiedente: boolean
  ) {
    this.soggetti = [...soggetti];
    this.setVisibilityButtonNext(
      this.mostraAvanti && this.soggetti.length >= 1
    );
    this.onAnnullaInserisciSoggetto();
    if (isCancellaDatiRichiedente) {
      this.cancellaRichiedente();
    }

    if (this.showButtonContinue < 1) {
      this.showButtonContinue++;
      if (
        this.numReferenti === this.numerazione.OPZIONALE ||
        this.numerazione.MULTIPLA_OPZIONALE
      ) {
        this.showButtonAddReferenti = true;
      }
    }

    this.checkRecapitoAlternativo(
      soggettoIstanza?.id_soggetto_istanza,
      formRawValue?.anagraficaRichiedente?.recapitoAlternativoResidenza
    );

    this._updateStepStatus();
    this._messageService.showMessage('P001', 'containerCercaSoggetti', true);
  }

  /**
   * Funzione di supporto che gestisce le logiche di aggiornamento di due proprietà dell'oggetto Soggetto:
   * - id_masterdata;
   * - id_masterdata_origine;
   * La funzione aggiornerà le proprietà dell'oggetto, quindi l'oggetto sarà aggiornato per referenza.
   * @param soggetto Soggetto sulla quale gestire le informazioni per gli id master data e origine.
   * @param confronto Partial<Soggetto> come oggetto sulla quale effettuare il confronto per l'aggiornamento.
   */
  private _aggiornaSoggIdMasterdataEOrigine(
    soggetto: Soggetto,
    confronto: Partial<Soggetto>
  ) {
    // Estraggo dal soggetto le proprietà per confrontarle con il soggetto dentro la snapshot
    const soggPicked = pickBy(soggetto, identity);
    // Definisco l'oggetto Soggetto di confronto
    const soggConfronto = confronto;

    // Definisco una variabile contenitore per id_masterdata
    let idMasterData = 1;
    // Verifico se gli oggetti hanno le stesse proprietà
    if (isEqual(soggPicked, soggConfronto)) {
      // Sono lo stesso soggetto, gestisco l'attributo id_masterdata
      idMasterData = soggetto.id_masterdata || 1;
      // #
    }
    // Aggiorno l'id_masterdata
    soggetto.id_masterdata = idMasterData;
    // Verifico e aggiorno anche l'id_masterdata_origine
    soggetto.id_masterdata_origine = soggetto.id_masterdata_origine || 1;
  }

  /**
   * Funzione di supporto che gestisce le logiche di aggiornamento delle proprietà dell'anagrafica dell'oggetto generato dal form-soggetto.
   * La funzione aggiornerà le proprietà dell'oggetto, quindi l'oggetto sarà aggiornato per referenza.
   * @param anagrafica any con l'oggetto dell'anagrafica d'aggiornare.
   * @param confronto Soggetto con l'oggetto salvato su DB, dalla quale estrarre i dati per l'aggiornamento dell'anagrafica.
   */
  private _aggiornaAnagraficaForm(anagrafica: any, soggetto: Soggetto) {
    // Verifico che esista l'oggetto dell'anagrafica
    if (!anagrafica) {
      // Niente oggetto
      return;
    }

    // Aggiorno i dati dell'anagrafica
    anagrafica.gestUID = soggetto?.gestUID;
    anagrafica.id_soggetto = soggetto?.id_soggetto;
    anagrafica.id_masterdata = soggetto?.id_masterdata;
    anagrafica.id_masterdata_origine = soggetto?.id_masterdata_origine;
  }

  /**
   * Funzione di supporto che, dati in input i parametri, ritorna l'oggetto SoggettoIstanza collegato all'istanza.
   * @param soggetto Soggetto con i dati del soggetto per il recupero dati.
   * @param ruoloCompilante RuoloCompilante con i dati per il recupero dati.
   * @returns SoggettoIstanza con le informazioni ritornate.
   */
  private _recuperaSoggettoIstanza(
    soggetto: Soggetto,
    ruoloCompilante: RuoloCompilante
  ): SoggettoIstanza {
    // Definisco le informazioni per il recupero del soggetto istanza
    const i = this.istanza;
    const s = soggetto;
    const rc = ruoloCompilante;
    const idSI =
      this.editIndexSoggetto > -1
        ? this.soggettiIstanza[this.editIndexSoggetto][0].id_soggetto_istanza
        : null;
    const gestUID =
      this.editIndexSoggetto > -1
        ? this.soggettiIstanza[this.editIndexSoggetto][0].gestUID
        : null;
    const gs =
      this.editIndexSoggetto > -1
        ? this.soggettiIstanza[this.editIndexSoggetto][0].gruppo_soggetto
        : null;

    // Utilizzo la funzione dell'oggetto SoggettoUtil
    return SoggettiUtil.returnSoggettoIstanza(i, s, rc, idSI, gestUID, gs);
  }

  /**
   * #######################################
   * #######################################
   * #######################################
   */

  onFlagCapoGruppo(idSoggetto: number, checked: boolean, $event: any) {
    const duplicated = this.soggetti.find(
      (soggetto) => !!soggetto?.anagraficaSoggetto?.flg_capogruppo
    );
    if (duplicated && checked) {
      this._messageService.showConfirmation({
        title: 'Attenzione',
        codMess: 'A026',
        buttons: [
          {
            label: 'ANNULLA',
            type: 'btn-link',
            callback: () => {
              this.soggetti.find(
                (soggetto) =>
                  soggetto.anagraficaSoggetto.id_soggetto === idSoggetto
              ).anagraficaSoggetto.flg_capogruppo = false;
              $event.target.checked = false;
            },
          },
          {
            label: 'CONFERMA',
            type: 'btn-primary',
            callback: () => {
              duplicated.anagraficaSoggetto.flg_capogruppo = false;
              this.setFlagCapoGruppo(idSoggetto, checked);
            },
          },
        ],
      });
    } else {
      this.setFlagCapoGruppo(idSoggetto, checked);
    }
  }

  setFlagCapoGruppo(idSoggetto: number, checked: boolean) {
    let oldCapoGruppo;
    //verifico la presenza di un flag
    this.soggettiIstanza.forEach((sog) => {
      if (sog[0].flg_capogruppo === true) {
        oldCapoGruppo = sog[0];
      }
    });

    if (oldCapoGruppo) {
      //se già presente, aggiorno vecchio capo gruppo
      oldCapoGruppo.flg_capogruppo = false;
      this.updateGruppoSoggetto(oldCapoGruppo);

      //e poi recupero e aggiorno anche il nuovo capo gruppo
      this.soggetti.find(
        (soggetto) => soggetto.anagraficaSoggetto.id_soggetto === idSoggetto
      ).anagraficaSoggetto.flg_capogruppo = checked;

      const soggIst = this.soggettiIstanza.find(
        (soggIstanza) => soggIstanza[0].soggetto.id_soggetto === idSoggetto
      )[0];
      soggIst.flg_capogruppo = checked;
      this.updateGruppoSoggetto(soggIst);
    } else {
      //recupero e aggiorno solamente il nuovo capo gruppo
      this.soggetti.find(
        (soggetto) => soggetto.anagraficaSoggetto.id_soggetto === idSoggetto
      ).anagraficaSoggetto.flg_capogruppo = checked;

      const soggIst = this.soggettiIstanza.find(
        (soggIstanza) => soggIstanza[0].soggetto.id_soggetto === idSoggetto
      )[0];
      soggIst.flg_capogruppo = checked;
      this.updateGruppoSoggetto(soggIst);
    }
  }

  onChangeNomeGruppo(newName: string) {
    this.soggettiIstanza.forEach((soggIst) => {
      soggIst[0].gruppo_soggetto.des_gruppo_soggetto = newName;
      this.updateGruppoSoggetto(soggIst[0]);
    });
  }

  updateGruppoSoggetto(soggettoIstanza: SoggettoIstanza) {
    this.spinner.show();
    this.ambitoService.salvaSoggettiIstanza(soggettoIstanza).subscribe(
      (res) => {
        const index = this.soggettiIstanza.findIndex(
          (soggIst) =>
            soggIst[0].id_soggetto_istanza === res.id_soggetto_istanza
        );
        this.soggettiIstanza[index][0] = res;
        this.spinner.hide();
        this.salvaDatiQuadro();
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'recapSoggetti');
      }
    );
  }

  checkRecapitoAlternativo(idSoggettoIstanza: number, formRecapitoAlternativo) {
    const recapitoObj = formRecapitoAlternativo || {};
    const isRecAltPresent = Object.values(recapitoObj).some((value) => !!value);

    if (!isRecAltPresent) {
      const savedRecapito = this.recapitiAlternativi.some(
        (recapito) => recapito.id_soggetto_istanza === idSoggettoIstanza
      );
      if (savedRecapito) {
        this.deleteRecapitoAlternativo(idSoggettoIstanza);
      } else {
        this.salvaDatiQuadro();
      }
    } else {
      const recAlt: RecapitoAlternativo =
        SoggettiUtil.returnRecapitoAlternativo(
          idSoggettoIstanza,
          formRecapitoAlternativo
        );
      this.saveRecapitoAlternativo(recAlt, formRecapitoAlternativo);
    }
  }

  saveRecapitoAlternativo(
    recAlt: RecapitoAlternativo,
    formRecapitoAlternativo
  ) {
    this.ambitoService.salvaRecapitoAlternativo(recAlt).subscribe(
      (res) => {
        const index = this.recapitiAlternativi.findIndex(
          (recapito) => recapito.id_soggetto_istanza === res.id_soggetto_istanza
        );
        if (index > -1) {
          this.recapitiAlternativi[index] = res;
        } else {
          this.recapitiAlternativi.push(res);
          formRecapitoAlternativo.idSoggettoIstanza =
            recAlt.id_soggetto_istanza;
          formRecapitoAlternativo.idRecapitoAlternativo =
            res.id_recapito_alternativo;
          formRecapitoAlternativo.codRecapitoAlternativo =
            res.cod_recapito_alternativo;
          formRecapitoAlternativo.gestUID = res.gestUID;
        }
        this.ambitoService
          .getSoggettoIstanzaById(res.id_soggetto_istanza)
          .subscribe(
            (resp) => {
              let row, column;
              this.soggettiIstanza.forEach((array, i_1) => {
                const i_2 = array.findIndex(
                  (element) =>
                    element.id_soggetto_istanza === res.id_soggetto_istanza
                );
                if (i_2 > -1) {
                  row = i_1;
                  column = i_2;
                }
              });
              this.soggettiIstanza[row][column] = resp;
              this.salvaDatiQuadro();
            },
            (error) => {
              this.showErrorsQuadroConCodeENoCode(
                error,
                'containerCercaSoggetti'
              );
            }
          );
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'containerCercaSoggetti');
      }
    );
  }

  deleteRecapitoAlternativo(idSoggettoIstanza: number) {
    this.ambitoService.eliminaRecapitoAlternativo(idSoggettoIstanza).subscribe(
      (res) => {
        const index = this.recapitiAlternativi.findIndex(
          (recapito) => recapito.id_soggetto_istanza === idSoggettoIstanza
        );
        this.recapitiAlternativi.splice(index, 1);
        this.ambitoService.getSoggettoIstanzaById(idSoggettoIstanza).subscribe(
          (resp) => {
            let row, column;
            this.soggettiIstanza.forEach((array, i_1) => {
              const i_2 = array.findIndex(
                (element) => element.id_soggetto_istanza === idSoggettoIstanza
              );
              if (i_2 > -1) {
                row = i_1;
                column = i_2;
              }
            });
            this.soggettiIstanza[row][column] = resp;
            this.salvaDatiQuadro();
          },
          (error) => {
            this.showErrorsQuadroConCodeENoCode(
              error,
              'containerCercaSoggetti'
            );
          }
        );
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'containerCercaSoggetti');
      }
    );
  }

  checkFlagModuloDelega(row: FormSoggettoPF | FormSoggettoPG) {
    let check = row?.ruoloCompilante?.flg_modulo_delega ?? false;
    this.infoMessModDelega = check;
    if (this.soggetti?.length > 1) {
      check = check && row?.anagraficaSoggetto?.flg_capogruppo;
      this.infoMessModDelega = check;
    }
    return check;
  }

  onDownloadDelega() {
    this.istanzaService.downloadModuloDelega(this.idIstanza).subscribe(
      (resp) => {
        const blob = new Blob([resp.body], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const contentDispositionHeader = resp.headers.get(
          'Content-Disposition'
        );
        let fileName = contentDispositionHeader.split('filename="')[1];
        fileName = fileName.slice(0, -1);

        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        a.click();
        window.URL.revokeObjectURL(url);
        a.remove();

        this.spinner.hide();
      },
      (err) => {
        const blob = new Blob([err.error]);
        const reader = new FileReader();

        reader.addEventListener('loadend', (e) => {
          const r: string = e.target.result as string;
          console.log(JSON.parse(r).title);
          // TODO  chiamao showMessageStatic per mostrare il messaggio
          this._messageService.showMessageStatic(
            'recapSoggetti',
            false,
            JSON.parse(r).title
          );
        });
        reader.readAsText(blob);
      }
    );
  }

  saveQuadroConfig(): Observable<any> {
    if (!this.jsonConfiguraTemplate) {
      return of(true);
    }

    // salvataggio config del quadro orientamento
    const dataQuadroOrientamento = {
      id_istanza: this.idIstanza || this.istanza.id_istanza,
      // id_template_quadro: -1,
      cod_tipo_quadro: 'QDR_CONFIG',
      json_data_quadro: JSON.stringify(this.jsonConfiguraTemplate),
    };

    let acCall$;
    if (!this.jsonConfiguraTemplate.ACPratica) {
      acCall$ = of(null);
    } else {
      const postList = [];
      this.jsonConfiguraTemplate.ACPratica.forEach(
        (ac: CompetenzaTerritorio) => {
          const acPayload: IstanzaCompetenza = {
            id_istanza: this.idIstanza,
            flg_autorita_principale: ac.flg_principale,
            competenza_territorio: ac,
          };
          postList.push(
            this.adempimentoService.saveAutoritaCompetente(acPayload)
          );
        }
      );
      acCall$ = forkJoin(postList);
    }

    return acCall$.pipe(
      // Intercetto possibili errori sul salvataggio dell'istanza
      catchError((err) => {
        // Lancio la funzione di gestione dell'errore
        this.onCercaSoggettiError(err);
        // Continuo con la gestione dell'errore
        return throwError(err);
        // #
      }),
      switchMap(() => {
        return this.stepManagerService
          .salvaJsonDataQuadro(dataQuadroOrientamento)
          .pipe(
            // Intercetto possibili errori sul salvataggio dell'istanza
            catchError((err) => {
              // Lancio la funzione di gestione dell'errore
              this.onCercaSoggettiError(err);
              // Continuo con la gestione dell'errore
              return throwError(err);
              // #
            })
          );
      })
    );
  }

  /**
   * Funzione agganciata all'evento di "reset" del form d'inserimento soggetto.
   * La funzione gestisce la pulizia delle informazioni per il quadro.
   * @author Ismaele Bottelli
   * @date 17/01/2025
   * @jira SCRIVA-1586
   * @notes Rifattorizzo le informazioni per la gestione del reset.
   */
  onAnnullaInserisciSoggetto() {
    // Lancio il reset del form ricerca soggetto
    this.resetFormRicercaSoggetto();
    // #
  }

  getArrayIndex(row: FormSoggettoPF | FormSoggettoPG) {
    return this.soggetti.findIndex(
      (sogg) =>
        sogg.anagraficaSoggetto.id_soggetto ===
        row.anagraficaSoggetto.id_soggetto
    );
  }

  onDeleteSoggetto(row: {
    record: FormSoggettoPF | FormSoggettoPG;
    index: number;
  }) {
    if (this.soggetti?.length > 1) {
      this._deleteSoggetto(row);
    } else {
      this._messageService.showConfirmation({
        title: 'Conferma eliminazione',
        codMess: 'A003',
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
              this._deleteSoggetto(row);
            },
          },
        ],
      });
    }
  }

  private _deleteSoggetto(row: {
    record: FormSoggettoPF | FormSoggettoPG;
    index: number;
  }) {
    this.spinner.show();

    const soggIstanza = this.soggettiIstanza[row.index];

    if (soggIstanza.length === 2) {
      this.ambitoService
        .eliminaSoggettiIstanza(soggIstanza[1].gestUID)
        .pipe(
          flatMap(() => {
            return this.ambitoService.eliminaSoggettiIstanza(
              soggIstanza[0].gestUID
            );
          })
        )
        .subscribe(
          () => {
            this.soggetti.splice(row.index, 1);
            this.soggettiIstanza.splice(row.index, 1);
            this.soggetti = [...this.soggetti];
            this.setVisibilityButtonNext(
              this.mostraAvanti && this.soggetti.length >= 1
            );
            //Rimuovendo il soggetto, (in presenza di soggetto 'Delegato potere di firma') Rimuovo il messaggio informativo (blu)
            this.infoMessModDelega = false;
            // this.deleteRecapitoAlternativo(soggIstanza[0].id_soggetto_istanza);
            // this.deleteRecapitoAlternativo(soggIstanza[1].id_soggetto_istanza);
            this._updateStepStatus();
            this.salvaDatiQuadro();
            this._messageService.showMessage('A055', 'content', true);
          },
          (error) => {
            this.showErrorsQuadroConCodeENoCode(
              error,
              'containerCercaSoggetti'
            );
          }
        );
    } else {
      this._eliminaSoggettoIstanza(soggIstanza, row);
    }
  }

  /**
   * Funzione per eliminare soggetto istanza
   * @param soggIstanza
   * @param row
   */
  private _eliminaSoggettoIstanza(
    soggIstanza: SoggettoIstanza[],
    row: {
      record: FormSoggettoPF | FormSoggettoPG;
      index: number;
    }
  ) {
    this.ambitoService.eliminaSoggettiIstanza(soggIstanza[0].gestUID).subscribe(
      () => {
        this.soggetti.splice(row.index, 1);
        this.soggettiIstanza.splice(row.index, 1);
        this.soggetti = [...this.soggetti];
        this.setVisibilityButtonNext(
          this.mostraAvanti && this.soggetti.length >= 1
        );
        // this.deleteRecapitoAlternativo(soggIstanza[0].id_soggetto_istanza);
        this._updateStepStatus();
        this.salvaDatiQuadro();
      },
      (error) => {
        this.showErrorsQuadroConCodeENoCode(error, 'containerCercaSoggetti');
      }
    );
  }

  /**
   * Funzione agganciata all'evento di modifica di una riga della tabella dei soggetti inseriti per l'adempimento.
   * @param row { record: FormSoggettoPF | FormSoggettoPG; index: number; } con le informazioni della riga in modifica.
   */
  onEditSoggetto(
    row: {
      record: FormSoggettoPF | FormSoggettoPG;
      index: number;
    },
    onlyShow: boolean
  ) {
    // Estraggo dall'oggetto della riga le informazioni
    const { record, index } = row || {};
    // Estraggo dall'oggetto "record" le informazioni del soggetto
    const { anagraficaSoggetto } = record || {};

    this.anagraficaRichiedente = record?.anagraficaRichiedente;
    this.editIndexSoggetto = index;
    this.ultimoTipoPersonaCercato =
      anagraficaSoggetto?.tipoSoggetto.cod_tipo_soggetto;

    this.ultimoSoggettoCercatoInAngrafica = anagraficaSoggetto ? true : false;

    let title = onlyShow
      ? 'Dettaglio dati inseriti'
      : 'Modifica i dati inseriti';

    const modalRef = this.modalService.open(SoggettoFormComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'lg',
    });

    modalRef.componentInstance.componente = this.componente;
    modalRef.componentInstance.nazioni = this.nazioni;
    modalRef.componentInstance.formSoggettoRow = row;
    modalRef.componentInstance.nazioniAttive = this.nazioniAttive;
    modalRef.componentInstance.ultimoTipoPersonaCercato =
      this.ultimoTipoPersonaCercato;
    modalRef.componentInstance.ultimoCFCercato = this.ultimoCFCercato;
    modalRef.componentInstance.adempimentoConfigList =
      this.adempimentoConfigList;
    modalRef.componentInstance.compilante = this.compilante;
    modalRef.componentInstance.tipoAdempimento = this.tipoAdempimento;
    modalRef.componentInstance.adempimento = this.adempimento;
    modalRef.componentInstance.tipiSoggetto = this.tipiSoggetto;
    modalRef.componentInstance.ruoliSoggetto = this.ruoliSoggetto;
    modalRef.componentInstance.ruoliCompilante = this.ruoliCompilante;
    modalRef.componentInstance.tipiNaturaGiuridica = this.tipiNaturaGiuridica;
    modalRef.componentInstance.flagRecapitoAlt = this.flagRecapitoAlt;
    modalRef.componentInstance.soggettoInAnagrafica =
      this.ultimoSoggettoCercatoInAngrafica;
    modalRef.componentInstance.codQuadro = this.codQuadro;

    modalRef.componentInstance.onlyShow = onlyShow;

    let btns = [];
    if (!onlyShow) {
      btns = [
        {
          id_action: 'BTN_CLOSE',
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'SALVA MODIFICHE',
          type: 'btn-primary',
          callback: (formSubmit: ISoggettoFormSubmitData) => {
            this.onInserisciSoggetto(formSubmit);
          },
        },
      ];
    } else {
      btns = [
        {
          id_action: 'BTN_CLOSE',
          label: '',
          type: 'btn-link',
          callback: () => {},
        },
        {
          id_action: 'BTN_CLOSE',
          label: 'CHIUDI',
          type: 'btn-primary single',
          callback: () => {},
        },
      ];
    }

    modalRef.componentInstance.data = {
      title: title,
      buttons: btns,
    };

    modalRef.result.then((formSubmit: ISoggettoFormSubmitData) => {
      // Estraggo i dati dall'oggetto di ritorno
      const { buttonIndex } = formSubmit;

      if (buttonIndex > -1) {
        btns[buttonIndex].callback(formSubmit);
      }
    });
  }

  onProsegui() {
    this.showButtonContinue = 2;
    this.showFormAddReferente = true;
    this.showButtonAddReferenti = true;
  }

  /**
   * Funzione per creare body per la chiamata al servizio di salvataggio referente
   * @param referenteFormSubmitData modale ha il tipo: ISoggettoFormSubmitData
   */
  onInserisciReferente(referenteFormSubmitData: ISoggettoFormSubmitData) {
    this.spinner.show();
    const formRawValue = referenteFormSubmitData.formRawValue;
    const ref: Referente = {
      id_referente_istanza: formRawValue.id || null,
      gestUID: formRawValue.gestUID,
      id_istanza: this.istanza.id_istanza,
      cognome_referente: formRawValue.cognome,
      nome_referente: formRawValue.nome,
      num_tel_referente: formRawValue.telefono,
      num_cellulare_referente: formRawValue.cellulare,
      des_email_referente: formRawValue.email,
      des_pec_referente: formRawValue.pec,
      des_mansione_referente: formRawValue.mansione,
    };
    this._salvaReferente(ref);
  }

  private _salvaReferente(ref: Referente) {
    this.ambitoService.salvaReferente(ref).subscribe(
      (referente: Referente) => {
        this.editIndexReferente > -1
          ? this.referenti.splice(this.editIndexReferente, 1, referente)
          : this.referenti.push(referente);
        this.onAnnullaInserisciReferente();
        this._updateStepStatus();
        this.salvaDatiQuadro();
        this._messageService.showMessage('P001', 'afterRecapReferenti', true);
        this.showAction =
          (this.numReferenti === this.numerazione.SINGOLO &&
            this.referenti.length === 1) ||
          (this.numReferenti === this.numerazione.OPZIONALE &&
            this.referenti.length === 1)
            ? false
            : true;
      },
      (error) => {
        this.showErrorsQuadroConCodeENoCode(error, 'afterRecapReferenti');
      }
    );
  }

  onAnnullaInserisciReferente() {
    this.showFormAddReferente = false;
    this.editIndexReferente = -1;
    if (this.referenti.length === 0) {
      this.showButtonAddReferenti = true;
    }
  }

  onAggiungiReferente() {
    this.showAction =
      (this.numReferenti === this.numerazione.SINGOLO &&
        this.referenti.length === 1) ||
      (this.numReferenti === this.numerazione.OPZIONALE &&
        this.referenti.length === 1)
        ? false
        : true;

    this.showFormAddReferente = true;
  }

  onDeleteReferente(row: { record: Referente; index: number }) {
    this._messageService.showConfirmation({
      title: 'Conferma eliminazione',
      codMess: 'A002',
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
            this._deleteReferente(row);
          },
        },
      ],
    });
  }

  private _deleteReferente(row: { record: Referente; index: number }) {
    this.spinner.show();
    this.ambitoService.eliminaReferente(row.record.gestUID).subscribe(
      () => {
        this.spinner.hide();
        this.referenti.splice(row.index, 1);
        this.onAnnullaInserisciReferente();
        this._updateStepStatus();
        this.salvaDatiQuadro();
        this.showAction =
          (this.numReferenti === this.numerazione.SINGOLO &&
            this.referenti.length === 1) ||
          (this.numReferenti === this.numerazione.OPZIONALE &&
            this.referenti.length === 1)
            ? false
            : true;
      },
      (error) => {
        this.showErrorsQuadroConCodeENoCode(error, 'recapReferenti');
      }
    );
  }

  onEditReferente(
    row: { record: Referente; index: number },
    onlyShow: boolean
  ) {
    this.editIndexReferente = row.index;

    const modalRef = this.modalService.open(ReferenteFormComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'lg',
    });

    modalRef.componentInstance.formReferenteRow = row;
    modalRef.componentInstance.onlyShow = onlyShow;
    // modalRef.componentInstance.numReferenti = this.numReferenti;

    let title = onlyShow
      ? 'Dettaglio dati inseriti'
      : 'Modifica i dati inseriti';

    let btns = [];

    if (!onlyShow) {
      btns = [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'SALVA MODIFICHE',
          type: 'btn-primary',
          callback: (formRawValue) => {
            this.onInserisciReferente(formRawValue);
          },
        },
      ];
    } else {
      btns = [
        {
          label: '',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'CHIUDI',
          type: 'btn-primary single',
          callback: () => {},
        },
      ];
    }

    modalRef.componentInstance.data = {
      title: title,
      buttons: btns,
    };

    modalRef.result.then(({ buttonIndex, data, formRawValue }) => {
      if (buttonIndex > -1) {
        btns[buttonIndex].callback({ data, formRawValue });
      }
    });
  }

  /**
   * Funzione che controlla validità del soggetto
   * riferito al numero di soggetti per la configurazione ed il numero di soggetti effettivi in input.
   * @returns boolean controlla se soggetto è valido
   */
  private _isSoggettiValid(
    numSuggetti: NumeroEnum,
    soggetti?: (FormSoggettoPF | FormSoggettoPG)[]
  ) {
    let soggettiValid = false;
    // soggetti possono essere S o M
    switch (numSuggetti) {
      case NumeroEnum.MULTIPLO:
        soggettiValid = soggetti?.length > 0;
        break;
      case NumeroEnum.SINGOLO:
        soggettiValid = soggetti?.length === 1;
        break;
      case NumeroEnum.OPZIONALE:
        soggettiValid = true;
        break;
      default:
        soggettiValid = false;
        break;
    }
    return soggettiValid;
  }

  /**
   * Funzione che controlla validità del referente riferito
   * al numero di referenti per la configurazione ed il numero di referenti effettivi in input.
   * @returns IVerificaReferenti controlla se referente è valido e se il numero dei referenti è multipla opzionale
   */
  private _isReferentiValid(
    numReferenti: string,
    referenti?: Referente[]
  ): IVerificaReferenti {
    const referentiValid: IVerificaReferenti = {
      valid: false,
      isMultiplaOpzionale: false,
    };
    // referenti possono essere N, O, S, M
    switch (numReferenti) {
      case NumeroEnum.MULTIPLO:
        referentiValid.valid = referenti?.length > 0;
        break;
      case NumeroEnum.SINGOLO:
        referentiValid.valid = referenti?.length === 1;
        break;
      case NumeroEnum.OPZIONALE:
        referentiValid.valid = true;
        break;
      case NumeroEnum.MULTIPLA_OPZIONALE:
        referentiValid.valid = true;
        referentiValid.isMultiplaOpzionale = true;
        break;
      case NumeroEnum.NESSUNO:
        referentiValid.valid = true;
        break;
      default:
        referentiValid.valid = false;
        break;
    }
    return referentiValid;
  }

  /**
   * Funzione che
   * controlla validità dei soggetti e dei referenti in base al numero,
   * mostra il pulsante avanti se soggetti e referenti sono validi e nel caso di referente multipla opzionale,
   * imposta lo stato completato se il pulsante avanti è mostrato
   */
  private _updateStepStatus(): void {
    // viene controllata la validità dei soggetti in base al numero
    const soggettiValid: boolean = this._isSoggettiValid(
      this.numSoggetti,
      this.soggetti
    );
    // viene controllata la validità dei referenti in base al numero e
    // i referenti di tipo multipla opzionale hanno sempre abilitato il pulsante avanti e fanno diventare lo step completato
    // per gli altri referenti viene controllata la validità
    const referentiValid: IVerificaReferenti = this._isReferentiValid(
      this.numReferenti,
      this.referenti
    );
    // il pulsante avanti è mostrato se referente è multiplaOpzionale oppure se i soggetti e i referenti sono validi
    this.mostraAvanti =
      referentiValid.isMultiplaOpzionale ||
      (soggettiValid && referentiValid.valid);
    this._presentazioneIstanzaService.emitShowMostraAvantiSub(
      this.mostraAvanti
    );

    this.setStepCompletedEmit('SoggettiComponent', this.mostraAvanti);
  }

  checkFlgCapoGruppo(): boolean {
    let flgCapoGruppo = false;
    this.soggettiIstanza.forEach((soggetto) => {
      if (soggetto[0].flg_capogruppo) {
        flgCapoGruppo = true;
      }
    });
    return flgCapoGruppo;
  }

  /**
   * Funzione che controlla copoGruppo, validità soggetto, validità referente e mostra relativi messaggi di errore
   */
  checkConfiguration(): boolean {
    const checkCapoGruppo = this.checkFlgCapoGruppo();
    if (!checkCapoGruppo && this.soggettiIstanza.length !== 1) {
      this._messageService.showMessage('E035', 'soggettiContainer', true);
      return;
    }
    // viene controllata la validità dei soggetti in base al numero
    const soggettiValid: boolean = this._isSoggettiValid(
      this.numSoggetti,
      this.soggetti
    );
    // viene controllata la validità dei referenti in base al numero e
    // i referenti di tipo multipla opzionale hanno sempre abilitato il pulsante avanti e fanno diventare lo step completato
    // per gli altri referenti viene controllata la validità
    const referentiValid: IVerificaReferenti = this._isReferentiValid(
      this.numReferenti,
      this.referenti
    );

    //vengono mostrati i messaggi di errore
    if (!soggettiValid && !referentiValid.valid) {
      this._messageService.showMessage('E008', 'soggettiContainerError', true);
      return false;
    } else if (!soggettiValid) {
      this._messageService.showMessage('E006', 'soggettiContainerError', true);
      return false;
    } else if (!referentiValid.valid) {
      this._messageService.showMessage('E007', 'soggettiContainerError', true);
      return false;
    } else {
      this.showFormAddReferente = false;
      this.ultimoCFCercato = null;
      return true;
    }
  }

  compareTipoSoggetto(ts1: TipoSoggetto, ts2: TipoSoggetto) {
    return ts1 && ts2 && ts1.cod_tipo_soggetto === ts2.cod_tipo_soggetto;
  }

  cfValidator(control: AbstractControl): ValidationErrors | null {
    const cf = control.get('cf');
    const tipo = control.get('tipoSoggetto');
    let failedValidation = null;

    if (
      cf &&
      tipo &&
      (tipo.value?.cod_tipo_soggetto === 'PF' ||
        tipo.value?.cod_tipo_soggetto === 'PG')
    ) {
      failedValidation = ControlloCf.validateCF(cf.value);
    } else if (cf && tipo && tipo.value?.cod_tipo_soggetto === 'PB') {
      failedValidation = ControlloCf.validatePI(cf.value);
    }
    if (failedValidation) {
      if (cf.errors) {
        if (
          tipo.value.cod_tipo_soggetto === 'PF' ||
          tipo.value.cod_tipo_soggetto === 'PG'
        ) {
          cf.errors['_controlloCf'] = true;
        } else {
          cf.errors['_controlloPIVA'] = true;
        }
      } else {
        if (
          tipo.value.cod_tipo_soggetto === 'PF' ||
          tipo.value.cod_tipo_soggetto === 'PG' ||
          tipo.value?.cod_tipo_soggetto === 'PB'
        ) {
          cf.setErrors({ _controlloCf: true });
        } else {
          cf.setErrors({ _controlloPIVA: true });
        }
      }
      return { controlloCf: true };
    } else {
      return null;
    }
  }

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  /**
   * Funzione di comodo per gestire le segnalazioni d'errore generate dai servizi.
   * @param code string che definisce il codice del messaggio da visualizzare.
   * @param elementId string con l'id del DOM dell'elemento a cui "attaccare" il messaggio.
   * @param autoFade boolean come flag per la gestione della scomparsa automatica.
   */
  private onServiziError(code: string, elementId: string, autoFade: boolean) {
    // Mostro il messaggio con il codice passato in input
    this._messageService.showMessage(code, elementId, autoFade);
  }

  /**
   * Funzione di comodo per gestire le segnalazioni d'errore generate dai servizi.
   * @param httpError ScrivaServerError con l'oggetto generato dalla chiamata dall'http client di Angular.
   * @param target string con il target che definisce l'id del DOM per la quale agganciare l'errore.
   * @param autoFade boolean come flag per la gestione della scomparsa automatica.
   */
  private onServiziSoggettiError(
    httpError: ScrivaServerError,
    target: string,
    autoFade?: boolean
  ) {
    // Definisco l'id del DOM per la form cerca soggetti, se non definito imposto un default
    const idDOMCercaSoggetti = target ?? 'containerCercaSoggetti';

    // Verifico se l'oggetto di errore ha il codice
    if (httpError.error?.code) {
      // Gestisco il flag per autoFade
      autoFade = autoFade ?? false;
      // Mostro il messaggio con il codice passato in input
      this.onServiziError(httpError.error.code, idDOMCercaSoggetti, false);
      // #
    } else {
      // Gestisco il flag per autoFade
      autoFade = autoFade ?? true;
      // Mostro un errore generico
      this.onServiziError('E100', idDOMCercaSoggetti, true);
      // #
    }
    // #
  }
  
  /*
   * Funzione di comodo per gestire le segnalazioni d'errore generate dai servizi.
   * Questa funzione è dedicata ai servizi di ricerca soggetti.
   * @param httpError ScrivaServerError con l'oggetto generato dalla chiamata dall'http client di Angular.
   * @param autoFade boolean come flag per la gestione della scomparsa automatica.
   */
  private onCercaSoggettiError(
    httpError: ScrivaServerError,
    autoFade?: boolean
  ) {
    // Gestisco l'errore con informazioni di default
    this.onServiziSoggettiError(httpError, undefined, autoFade);
    // #
  }

  /**
   * Funzione di comodo per gestire le segnalazioni d'errore generate dai servizi.
   * Questa funzione è dedicata ai servizi d'inserimento dei soggetti.
   * @param httpError ScrivaServerError con l'oggetto generato dalla chiamata dall'http client di Angular.
   * @param autoFade boolean come flag per la gestione della scomparsa automatica.
   */
  private onInserisciSoggettiError(
    httpError: ScrivaServerError,
    autoFade?: boolean
  ) {
    // Se l'errore è generato dal pulsante INSERISCI SOGGETTO, del componente figlio, allora il target è diverso e specifico
    const target: string = this.S_C.ID_DOM_INSERISCI_SOGGETTO;

    // Estraggo dall'errore il codice per intercettare specifiche situazioni d'errore
    const code: string = httpError?.error?.code;
    // Gestisco i casi specifici di errore dati specifici codici
    switch (code) {
      case ScrivaCodesMesseges.E076:
        // L'errore è dovuto al ruolo utente di default non definito come configurazione, recupero la descrizione dell'errore poiché gestita dal servizio
        const title: string = httpError.error.title;
        // Vado a visualizzare il messaggio d'errore con la descrizione ritornata
        this._messageService.showMessage(code, target, false, null, title);
        break;
      // #
      default:
        // Gestisco l'errore con informazioni di default
        this.onServiziSoggettiError(httpError, target, autoFade);
      // #
    }
  }

  /**
   * ###############
   * Override
   * ###############
   */

  /**
   * @override
   */
  onAvanti() {
    if (this.attoreGestioneIstanza === 'WRITE') {
      this.isStepValid().subscribe((isValid: boolean) => {
        if (isValid) {
          this.salvaDatiQuadro(true);
        }
      });
    } else {
      this.goToStep(this.stepIndex);
    }
  }

  salvaDatiQuadro(onAvantiFlag: boolean = false) {
    // Preparo le informazioni per il salvataggio dei dati
    const configs: IConfigsSaveDataQuadro = {
      onAvantiFlag,
      datiQuadro: { soggettiForms: this.soggetti },
    };

    this.saveDataQuadro(configs).subscribe(
      (res: any) => {
        if (!res) {
          this.goToStep(this.stepIndex);
        } else {
          this.istanza = res;
        }
      },
      (err) => {
        // Gestisco l'errore in entrata per il componente
        this.showErrorsQuadroConCodeENoCode(err, 'afterRecapReferenti');
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
    const requestConfigs: IPrepareDatiRiepilogo = {
      codQuadro: this.codQuadro,
      codTipoQuadro: this.codTipoQuadro,
      datiQuadro: configs.datiQuadro,
      datiRiepilogo: configs.datiRiepilogo,
    };
    this.qdr_riepilogo = this.prepareDatiRiepilogo(requestConfigs);
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
      .salvaJsonDataQuadro(requestDataRiepilogo, this.saveWithPut)
      .pipe(
        tap(() => {
          this.saveWithPut = true;
        }),
        switchMap(() => {
          if (configs.onAvantiFlag) {
            this.setStepCompletedEmit('RiepilogoComponent', true)
            return of(null);
          } else {
            // why do we need to retrieve the istanza back? can't remember...
            return this.istanzaService.getIstanzaById(this.istanza.id_istanza);
          }
        })
      );
  }

   
  /**
   * @return Observable<boolean> definisce se lo step è valido o no
   * @override
   */
  protected isStepValid(): Observable<boolean> {
    if (this.attoreGestioneIstanza === 'WRITE') {
      const check: boolean = this.checkConfiguration();
      return of(check);
    } else {
      return of(true);
    }
  }

  /**
   * @override
   */
  onIndietro() {
    this.router.navigate(
      [`../../orientamento/${this.tipoAdempimento.cod_tipo_adempimento}`],
      {
        relativeTo: this.route,
        state: {
          attoreGestioneIstanza: this.attoreGestioneIstanza,
          jsonConfiguraTemplate: this.jsonConfiguraTemplate,
        },
      }
    );
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
    this.jsonConfiguraTemplateSub?.unsubscribe();
    this.ambitoConfigSub?.unsubscribe();
    this.formCercaSoggettiValueChangeSub?.unsubscribe();
    this.eliminaSoggettiIstanzaSub?.unsubscribe();
  }

}
