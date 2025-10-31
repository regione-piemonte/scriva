/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FormioComponent as NgFormioComp } from 'angular-formio';
import {
  FormioForm,
  FormioOptions,
  FormioRefreshValue,
} from 'angular-formio/formio.common';
import { cloneDeep } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin } from 'rxjs';
import {
  distinctUntilChanged,
  filter,
  switchMap,
  takeUntil,
  tap,
} from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import {
  Adempimento,
  CompetenzaTerritorio,
  Help,
  Quadro,
  TipoAdempimento,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AmbitiService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { TipoInfoAdempimento } from 'src/app/shared/services/configurazioni/utilities/configurazioni.enums';
import { FormioService } from 'src/app/shared/services/formio/formio.service';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import {
  ScrivaCodTipiAdempimenti,
  ScrivaComponenteApp,
} from '../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { IFormIoRenderOptions } from '../../../../shared/interfaces/scriva-utilities.interfaces';
import { IFormioRenderOptionsParams } from '../../../../shared/services/formio/utilities/formio.interfaces';
import { AmbitoStoreService } from '../../services';
import { OrientamentoIstanzaDichiarazioniService } from '../../services/orientamento-istanza/orientamento-istanza-dichiarazioni/orientamento-istanza-dichiarazioni.service';
import { IGestisciACDichiarazioni } from '../../services/orientamento-istanza/orientamento-istanza-dichiarazioni/utilities/orientamento-istanza-dichiarazioni.interfaces';
import { ObjectHelper } from '../../utils';
import { OrientamentoIstanzaConsts } from './utilities/orientamento-istanza.consts';
import { IFormIoOrientamentoDichiarazioni } from './utilities/orientamento-istanza.interfaces';
import { DerivazioniBozzaService } from '../../services/ambito/derivazioni/derivazioni-bozza/derivazioni-bozza.service';

@Component({
  selector: 'app-orientamento-istanza',
  templateUrl: './orientamento-istanza.component.html',
  styleUrls: ['./orientamento-istanza.component.scss'],
  providers: [DerivazioniBozzaService],
})
export class OrientamentoIstanzaComponent
  extends AutoUnsubscribe
  implements OnInit
{
  /** OrientamentoIstanzaConsts con le costanti del componente. */
  private OI_C = new OrientamentoIstanzaConsts();

  /** ViewChild che permette di accedere alla struttura dati del componente della libreria Formio. */
  @ViewChild('ngFormio', { static: false }) ngFormio: NgFormioComp;

  /** FormioOptions come oggetto di opzioni per il formio date in input lato template alla componente formio. */
  formioOptions: FormioOptions = { disableAlerts: true };

  codMaschera = '.MSCR008D';
  title = '';

  tipoAdempimento: TipoAdempimento;
  adempimenti: Adempimento[] = [];
  idIstanza: number;
  attoreGestioneIstanza;

  componente: string;

  helpMaschera: Help[];
  helpQuadro: Help[];

  adempimentoForm: FormGroup;
  selectedAdempimento: Adempimento = null;
  quadroOrientamento: Quadro;
  jsonConfiguraTemplate = null;
  temp_jsonConfiguraTemplate = null;
  IndTipoSelezioneAC = 'N';

  jsonData;

  acList: CompetenzaTerritorio[];
  // defaultAC: CompetenzaTerritorio[];
  selectedAC: CompetenzaTerritorio[] = [];
  showBtnAC = true;

  acTableRecords: CompetenzaTerritorio[] = [];
  acTableColumns = [
    { label: 'Ente', properties: ['des_competenza_territorio'] },
  ];
  acTableTitle = 'Riepilogo enti di competenza';

  orientamentoForm: FormioForm;
  submission: any = {};

  validFormFlag = false;
  showProsegui = false;
  enableProsegui_VINCA = true;
  info: TipoInfoAdempimento = TipoInfoAdempimento.acWeb;

  visualizzaInfoPrivacy: boolean = true;

  /** boolean con il flag di gestione per la disabilitazione dei form FormIo. */
  readOnly: boolean = false;

  /** boolean che verifica la modalità di lavoro applicativa, se FO o BO. */
  isFrontOffice: boolean;

  /** EventEmitter<FormioRefreshValue> FormIo che permette la gestione dell'aggiornamento dei dati del formio. */
  triggerRefresh = new EventEmitter<FormioRefreshValue>();
  /** IFormIoRenderOptions contenente le informazioni da iniettare all'interno del formio. */
  renderOptions: IFormIoRenderOptions = {};

  /** boolean con un flag che permette la visualizzazione del FormIo. */
  formIoReady: boolean = true;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private adempimentoService: AdempimentoService,
    private ambitiService: AmbitiService,
    private authStoreService: AuthStoreService,
    private ambitoStoreService: AmbitoStoreService,
    private istanzaService: IstanzaService,
    private helpService: HelpService,
    private stepManagerService: StepManagerService,
    private spinner: NgxSpinnerService,
    private messageService: MessageService,
    private configurazioniService: ConfigurazioniScrivaService,
    protected _formio: FormioService,
    private _orientamentoIstanzaDichiarazioni: OrientamentoIstanzaDichiarazioniService,
    private _bozzaDER: DerivazioniBozzaService
  ) {
    super();

    const state = this.router.getCurrentNavigation().extras.state;
    if (state) {
      const clearFlag = state['clearIdIstanza'];
      this.attoreGestioneIstanza = state['attoreGestioneIstanza'];
      this.temp_jsonConfiguraTemplate = state['jsonConfiguraTemplate'];
      const startingPage = state['startingPage'];

      if (clearFlag) {
        this.istanzaService.setIdIstanza(null);
      }
      if (startingPage) {
        this.ambitoStoreService.setStartingPage(startingPage);
      }
    }

    // Recupero la modalità attiva di lavoro dell'applicazione
    this.isFrontOffice = this.authStoreService.isFrontOffice;
    // Definisco le configurazioni per l'iniezione dati dentro formio
    this.setupFormIoRenderOptions();
  }

  ngOnInit() {
    this.idIstanza = this.istanzaService.getIdIstanza();
    this.componente = this.authStoreService.getComponente();

    this.spinner.show();
    this.buildForm();
    this.helpService.setCodMaschera(this.codMaschera);
    if (this.idIstanza) {
      this.istanzaService.getIstanzaById(this.idIstanza).subscribe(
        (res) => {
          this.jsonData = JSON.parse(res.json_data);
          this._init();
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'card', false);
          } else {
            this.messageService.showMessage('E100', 'card', true);
          }
        }
      );
    } else {
      this._init();
    }
  }

  _init() {
    const codTipoAdempimento =
      this.route.snapshot.paramMap.get('codTipoAdempimento');
    const codAmbito = this.route.snapshot.paramMap.get('codAmbito');

    this.ambitiService
      .getAmbitoByCode(codAmbito)
      .pipe(
        switchMap((ambito) => {
          const getHelpMaschera = this.helpService.getHelpByChiave(
            this.componente + this.codMaschera
          );
          const getHelpQuadro = this.helpService.getHelpByChiave(
            this.componente + '.' + codTipoAdempimento
          );
          const getTipiAdempimento =
            this.adempimentoService.getTipiAdempimentoByAmbito(
              ambito.id_ambito
            );
          return forkJoin([getHelpMaschera, getHelpQuadro, getTipiAdempimento]);
        }),
        tap((res) => {
          this.helpMaschera = res[0];
          this.helpQuadro = res[1];
          this.tipoAdempimento = res[2].find(
            (tipo) => tipo.cod_tipo_adempimento === codTipoAdempimento
          );
          this.title =
            'Nuova ' +
            this.tipoAdempimento.cod_tipo_adempimento +
            ' - ' +
            this.tipoAdempimento.des_tipo_adempimento;
        }),
        switchMap(() =>
          this.adempimentoService.getAdempimentiByTipo(
            this.tipoAdempimento.id_tipo_adempimento
          )
        )
      )
      .subscribe(
        (resp) => {
          this.adempimenti = resp;
          if (this.adempimenti.length === 1 && !this.jsonData) {
            this.selectedAdempimento = this.adempimenti[0];
            this.adempimentoForm
              .get('adempimento')
              .setValue(this.selectedAdempimento, { emitEvent: false });
            this.getQuadro();
          }
          this.loadData();
          this.spinner.hide();
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'card', false);
          } else {
            this.messageService.showMessage('E100', 'card', true);
          }
        }
      );
  }

  buildForm() {
    this.adempimentoForm = this.fb.group({
      adempimento: [null, Validators.required],
      autCompetente: null,
    });

    this.adempimentoForm
      .get('adempimento')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        distinctUntilChanged(),
        tap(() => (this.enableProsegui_VINCA = true)),
        filter((ademp) => !!ademp)
      )
      .subscribe((ademp) => {
        this.selectedAdempimento = ademp;
        const element = document.getElementById('seleziona_adempimento');
        const adempimentoBnr = this.helpMaschera
          .filter(
            (help) =>
              help.tipo_help.cod_tipo_help === 'BNR' &&
              help.chiave_help ===
                `${this.componente}${this.codMaschera}.seleziona_adempimento`
          )
          .find(
            (item) =>
              item.valore_campo_help ===
              this.selectedAdempimento.cod_adempimento
          );
        element.innerHTML = adempimentoBnr
          ? adempimentoBnr.des_testo_help
          : 'Help non trovato...';

        // style from .scss file doesn't apply
        const styleRules = document.createElement('style');
        styleRules.innerHTML =
          '#seleziona_adempimento > h3 { font-size: 1rem; line-height: 1.5; margin: 0; }';
        document.body.appendChild(styleRules);

        if (this.jsonData) {
          this.adempimentoForm.disable();
        } else {
          this.getQuadro();
        }
      });

    this.adempimentoForm
      .get('autCompetente')
      .valueChanges.pipe(
        takeUntil(this.destroy$),
        distinctUntilChanged(),
        filter((ac) => !!ac)
      )
      .subscribe((ac) => {
        if (this.IndTipoSelezioneAC === 'S') {
          this.selectedAC = [ac];
          this.jsonConfiguraTemplate.ACPratica = [
            // ...this.defaultAC,
            ...this.selectedAC,
          ];
          if (this.orientamentoForm) {
            this.orientamentoForm['ACPratica'] =
              this.jsonConfiguraTemplate.ACPratica;
          } else {
            this.showProsegui = true;
          }
        }
      });
  }

  loadData() {
    if (this.jsonData) {
      this.selectedAdempimento = this.adempimenti.find(
        (ademp) => ademp.id_adempimento === this.jsonData.QDR_CONFIG.Adempimento
      );
      this.adempimentoForm
        .get('adempimento')
        .setValue(this.selectedAdempimento);
      this.showBtnAC = false;
      this.title = this.title.split('Nuova ')[1];
      this.getQuadro(true);

      // Imposto a disabilitato il formio, segue le logiche di this.getQuadro ed il parametro loadSaveData vedi nota #CCDS_NOTA
      this.readOnly = true;
      // #
    } else if (this.temp_jsonConfiguraTemplate) {
      this.selectedAdempimento = this.adempimenti.find(
        (ademp) =>
          ademp.id_adempimento === this.temp_jsonConfiguraTemplate.Adempimento
      );
      this.adempimentoForm
        .get('adempimento')
        .setValue(this.selectedAdempimento);
    }
  }

  getQuadro(loadSavedData = false) {
    this.spinner.show();
    this.stepManagerService
      .getQuadriByAdempimento(this.selectedAdempimento.cod_adempimento)
      .subscribe(
        (res) => {
          this.jsonConfiguraTemplate = JSON.parse(res.json_configura_template);
          this.jsonConfiguraTemplate.Adempimento =
            this.selectedAdempimento.id_adempimento;
          this.quadroOrientamento = res.quadri.find(
            (quadro) =>
              quadro.tipo_quadro.cod_tipo_quadro === 'QDR_ORIENTAMENTO'
          );
          this.IndTipoSelezioneAC =
            this.jsonConfiguraTemplate.IndTipoSelezioneAC || 'N';
          /* MOCK */
          // this.IndTipoSelezioneAC = 'M';
          /* ---- */

          this.getAutCompetenti(loadSavedData);
          this.configQuadro(loadSavedData);
          this.getLinkDichiarazione();
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'card', false);
          } else {
            this.messageService.showMessage('E100', 'card', true);
          }
        }
      );
  }

  getAutCompetenti(loadSavedData: boolean) {
    if (this.IndTipoSelezioneAC === 'N') {
      this.acList = undefined;
      this.spinner.hide();
      return;
    }

    this.adempimentoService
      .getCompetenzeTerritorioByAdempimento(
        this.selectedAdempimento.id_adempimento
      )
      .subscribe(
        (resp) => {
          this.acList = resp.filter((ac) => ac.ind_adesione_adempimento === 1);
          // this.defaultAC = resp.filter(
          //   (ac) => ac.ind_adesione_adempimento === 2
          // );
          this.selectedAC = [];
          this.acTableRecords = [];

          if (loadSavedData || this.temp_jsonConfiguraTemplate) {
            const configObj = loadSavedData
              ? this.jsonData.QDR_CONFIG
              : this.temp_jsonConfiguraTemplate;
            configObj.ACPratica.forEach((ac: CompetenzaTerritorio) => {
              this.selectedAC.push(ac);
            });

            if (this.IndTipoSelezioneAC === 'S') {
              this.adempimentoForm
                .get('autCompetente')
                .setValue(this.selectedAC[this.selectedAC.length - 1]);

              // E' stata popolata la select con l'autorità competente, lancio il flusso come se fosse stato l'utente a selezionarla
              this.onChangeAutoritaCompetente();
              // #
            } else {
              this.acTableRecords = [...this.selectedAC];
            }
          } else {
            this.adempimentoForm.get('autCompetente').setValue(null);
          }
          this.spinner.hide();
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(err.error.code, 'card', false);
          } else {
            this.messageService.showMessage('E100', 'card', true);
          }
        }
      );
  }

  getLinkDichiarazione() {
    // cod_adempimento = 'VER' Or 'VAL'
    // info = 'WEB_AC_GDPR'
    // chiave = codice_competenza_territorio  ('A1600A')
    // let codTipoAdempimento = this.tipoAdempimento.cod_tipo_adempimento;
    if (
      this.componente === 'FO' &&
      this.tipoAdempimento.cod_tipo_adempimento === 'VIA' &&
      this.selectedAdempimento.cod_adempimento &&
      this.temp_jsonConfiguraTemplate
    ) {
      let codTipoAdempimento =
        this.selectedAdempimento.tipo_adempimento.cod_tipo_adempimento;
      let codAutCompetente =
        this.temp_jsonConfiguraTemplate.ACPratica[0].cod_competenza_territorio;
      let cod_adempimento = this.selectedAdempimento.cod_adempimento;

      this.configurazioniService
        .getConfigurazioniByInfoAndChiave(
          cod_adempimento,
          this.info,
          codAutCompetente
        )
        .subscribe(
          (res) => {
            let acWeb = res[0].valore;
            if (acWeb === undefined) {
              console.log('Url sito web non trovato');
            }
            let fo: any = { ...this.orientamentoForm };
            fo.imports = {
              acWeb: acWeb,
              codTipoAdempimento: codTipoAdempimento,
              codAutCompetente: codAutCompetente,
            };
            this.orientamentoForm = fo;
          },
          (err) => {
            console.log('err.status: ', err.status);
            if (err.status === 404) {
              this.spinner.hide();
              return;
            }
          }
        );
    }
  }

  configQuadro(loadSavedData: boolean) {
    if (this.quadroOrientamento) {
      this.quadroOrientamento.json_configura_quadro = JSON.parse(
        this.quadroOrientamento.json_configura_quadro
      );

      this.orientamentoForm =
        this.quadroOrientamento.json_configura_quadro.jsonForm;
      if (!this.orientamentoForm) {
        this.validFormFlag = true;
        return;
      }

      if (loadSavedData || this.temp_jsonConfiguraTemplate) {
        setTimeout(() => {
          this.submission = {
            data: loadSavedData
              ? this.jsonData.QDR_CONFIG
              : this.temp_jsonConfiguraTemplate,
          };

          this.submission.data.disableFlag = loadSavedData
            ? 'DISABLED'
            : 'ENABLED';
          // Nota #CCDS_NOTA => settare il flag qua per formio non disabilita correttamente il form, vedere nota sopra

          this.showProsegui = true;
          if (this.temp_jsonConfiguraTemplate) {
            this.jsonConfiguraTemplate = ObjectHelper.cloneObject(
              this.temp_jsonConfiguraTemplate
            );
          }
        }, 200);
      } else {
        setTimeout(() => {
          this.submission = {};
        }, 200);
      }
    } else {
      this.orientamentoForm = null;
      this.validFormFlag = true;
      this.showProsegui = true;
      if (this.adempimenti.length === 1) {
        this.selectedAdempimento = this.adempimenti[0];
        this.onProsegui();
      }
    }
  }

  onFormioChange(event) {
    if (event.data) {
      this.validFormFlag = event.isValid && event.isModified;
      if (
        event.isValid &&
        event.isModified === false &&
        (this.jsonData || this.temp_jsonConfiguraTemplate)
      ) {
        this.validFormFlag = true;
        this.temp_jsonConfiguraTemplate = null;
      }

      // SCRIVA-1026 il caso del submit del formio per generare gli errori
      // deve tenere validFormFlag a true anche in caso di event.isModified non settato o a false
      // diversamente la variabile validFormFlag sarebbe disallineata dalla situazione reale del form
      if (event.isValid && event.flags.noCheck) {
        this.validFormFlag = true;
      }

      if (!event.isValid) {
        this.showProsegui = false;
      }

      if (event.data && this.validFormFlag) {
        // Lancio il check e l'esecuzione di possibili logiche custom del formio
        event.data = this.handleFormioSubmitForDB(event.data);

        Object.keys(event.data).forEach((key) => {
          if (event.data.COD_TIPOLOGIA_OGGETTO === 'ATTIVITA') {
            this.enableProsegui_VINCA = true;
          } else {
            if (key === 'PROGETTO_VIA') {
              if (event.data[key] === 1) {
                this.enableProsegui_VINCA = false;
              } else {
                this.enableProsegui_VINCA = true;
              }
            }
          }
          if (event.data[key] !== null && event.data[key] !== undefined) {
            this.jsonConfiguraTemplate[key] = event.data[key];
          }
          //  _.assignIn(this.jsonConfiguraTemplate[key], event.data[key]);
        });
        this.showProsegui = true;
      }

      this.visualizzaInfoPrivacy = this.displayInfoPrivacy();
    }
  }

  onCustomEvent(event) {
    if (event.type === 'helpBtnClick') {
      const chiaveHelp = `${this.componente}.${this.tipoAdempimento.cod_tipo_adempimento}.${this.selectedAdempimento.cod_adempimento}.QDR_ORIENTAMENTO.${event.component.key}`;
      const modalContent =
        this.helpQuadro.find((help) => help.chiave_help === chiaveHelp)
          ?.des_testo_help || 'Help non trovato...';

      this.messageService.showConfirmation({
        title: 'HELP',
        codMess: null,
        content: modalContent,
        buttons: [],
      });
    }
  }

  onHelpClicked(chiave: string) {
    const modalContent =
      this.helpMaschera.find(
        (help) =>
          help.tipo_help.cod_tipo_help === 'MDL' &&
          help.chiave_help === `${this.componente}${this.codMaschera}.${chiave}`
      )?.des_testo_help || 'Help non trovato...';

    this.messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  displayInfoPrivacy(): boolean {
    if (
      this.componente === 'BO' &&
      this.tipoAdempimento.cod_tipo_adempimento != undefined
    ) {
      if (this.tipoAdempimento.cod_tipo_adempimento === 'VIA') {
        this.showProsegui = true;
        this.validFormFlag = true;

        this.ngFormio.formio.submission.data.disableFlag = 'DISABLED';
        this.readOnly = true;

        const privacy = {
          customMessage: '',
          required: false,
        };
        this.orientamentoForm.components[5].components[0].validate = privacy;
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  /**
   * ############################################
   * FUNZIONI ALLA SELEZIONE AUTORITA' COMPETENTE
   * ############################################
   */

  // #region "SELEZIONE AUTORITA' COMPETENTE"

  /**
   * Funzione che gestisce le logiche al cambio dell'autorità competente selezionata.
   * @param resetDichiarazioni boolean che definisce se le informazioni sui flag delle dichiarazioni vanno resettate. Per default è: false.
   * @author Ismaele Bottelli
   * @date 09/01/2025
   * @jira SCRIVA-1568
   * @notes A seguito di alcune correzioni iniziali, è necessario gestire una forzatura sul reset delle informazioni come FormIo.
   *        Aggiunto quindi un flag che va ad impattare le informazioni del FormIo nella gestione delle dichiarazioni.
   */
  onChangeAutoritaCompetente(resetDichiarazioni: boolean = false) {
    // cod_adempimento = 'VIA'
    // info = 'WEB_AC_GDPR'
    // chiave = codice_competenza_territorio  ('A1600A')
    if (this.componente === ScrivaComponenteApp.frontOffice) {
      // Vado a resettare le informazioni all'interno delle strutture FormIo per poter gestire la nuova AC
      this.allineaDatiACFormIo(resetDichiarazioni);

      // Compongo l'oggetto con i parametri per gestire le dichiarazioni
      const paramsDicharazioni: IGestisciACDichiarazioni = {
        codiceProcedimento: this.selectedAdempimento.cod_adempimento,
        autoritaCompetente: this.selectedAC[0],
        formioStructure: this.orientamentoForm,
        tipoAdempimento: this.tipoAdempimento,
        tipoInformazione: this.info,
      };

      // Visualizzo lo spinner di caricamento
      this.spinner.show();
      // Nascondo il formio poiché potrebbe necessitare di informazioni aggiuntive
      this.formIoReady = false;
      // Lancio il servizio per lo scarico delle informazioni
      this._orientamentoIstanzaDichiarazioni
        .gestisciDatiACDichiarazioniFormIo(paramsDicharazioni)
        .subscribe({
          next: (formioStructure: IFormIoOrientamentoDichiarazioni) => {
            // Lancio la funzione per gestire le informazioni di configurazioni specifiche per via
            this.onGestisciDatiACDichiarazioniFormIo(formioStructure);
            // Nascondo lo spinner di caricamento
            this.spinner.hide();
            // Visualizzo nuovamente il formio
            this.formIoReady = true;
            // #
          },
          error: (e: ScrivaServerError) => {
            // Nascondo lo spinner di caricamento
            this.spinner.hide();
            // Visualizzo nuovamente il formio
            this.formIoReady = true;
            // Gestisco la segnalazione
            console.log('err.status: ', e.status);
            if (e.status === 404) {
              return;
            }
          },
        });
    }
  }

  /**
   * Funzione che gestisce l'allineamento dati dell'orientamento e del formio di gestione dell'autorità competente.
   * @param resetDichiarazioni boolean che definisce se le informazioni sui flag delle dichiarazioni vanno resettate. Per default è: false.
   * @author Ismaele Bottelli
   * @date 10/12/2024
   * @jira SCRIVA-1585
   * @notes Il problema è nato dal fatto che questo componente è stato scritto terribilmente e ci sono troppe variabili globali in giro.
   *        Le strutture dati che tengono traccia dello stato delle informazioni non sono disaccoppiate, quindi i dati sono d'aggiornare manualmente sulle strutture.
   *        In questo caso il problema è che il dato dell'AC selezionata viene INIETTATA dentro l'oggetto di FormIo e non viene mai sincronizzato con gli eventi di change della pagina.
   *        Quindi bisogna collegare gli eventi e le informazioni in maniera tale da avere un flusso stabile.
   *        Hash di riferimento: 68747470733a2f2f7777772e796f75747562652e636f6d2f77617463683f763d7044305344556c497a666f2661625f6368616e6e656c3d416c62336e3236.
   */
  private allineaDatiACFormIo(resetDichiarazioni: boolean = false) {
    // Verifico se è stato chiesto il reset dei dati per le dichiarazioni
    if (resetDichiarazioni && this.submission) {
      // Gestisco in un try-catch per sicurezza
      try {
        // Recupero l'oggetto "submission" con i dati pre-valorizzati di formio e creo una copia
        const submission: any = cloneDeep(this.submission);
        // Verifico se è definita le proprietà "data"
        if (submission.data) {
          // Esiste, rimuovo le informazioni relative alle dichiarazioni
          delete submission.data.dichiarazioni;
          delete submission.data['dich-orientamento'];

          // Oltre ai dati delle dichiarazioni aggiorno anche l'oggetto per l'AC selezionata
          submission.data.ACPratica = this.selectedAC;

          // Riassegno l'oggetto di submission per il formio
          this.submission = submission;
          // #
        }
        // #
      } catch (e) {}
      // #
    }
  }

  /**
   * Funzione che gestisce il formio aggiornato a seguito delle logiche per la gestione delle dichiarazioni.
   * @param formioStructure IFormIoOrientamentoDichiarazioni con la struttura FormIo aggiornata con le informazioni per le dichiarazioni.
   * @author Ismaele Bottelli
   * @date 10/12/2024
   * @jira SCRIVA-1568
   * @notes Rifattorizzazione codice.
   */
  private onGestisciDatiACDichiarazioniFormIo(
    formioStructure: IFormIoOrientamentoDichiarazioni
  ) {
    // Riassegno la struttura del formio con le nuove informazioni delle dichiarazioni
    this.orientamentoForm = { ...formioStructure };
    // #
  }

  // #region "SELEZIONE AUTORITA' COMPETENTE"

  aggiungiAC() {
    const ac: CompetenzaTerritorio =
      this.adempimentoForm.get('autCompetente').value;
    const isPresent = this.acTableRecords.some(
      (elem) => elem.id_competenza_territorio === ac?.id_competenza_territorio
    );
    if (isPresent || !ac) {
      return;
    }

    this.acTableRecords.push(ac);
    this.selectedAC = [...this.acTableRecords];
    this.jsonConfiguraTemplate.ACPratica = [
      // ...this.defaultAC,
      ...this.selectedAC,
    ];
    if (this.orientamentoForm) {
      this.orientamentoForm['ACPratica'] = this.jsonConfiguraTemplate.ACPratica;
    } else {
      this.showProsegui = true;
    }
  }

  eliminaAC(event) {
    this.acTableRecords.splice(event.index, 1);
    this.selectedAC = [...this.acTableRecords];
    this.jsonConfiguraTemplate.ACPratica = [
      // ...this.defaultAC,
      ...this.selectedAC,
    ];
    if (this.orientamentoForm) {
      this.orientamentoForm['ACPratica'] = this.jsonConfiguraTemplate.ACPratica;
    }
  }

  goToDashboard() {
    this.router.navigate(['/ambito/' + this.tipoAdempimento.ambito.cod_ambito]);
  }

  onIndietro() {
    const targetUrl = this.ambitoStoreService.getStartingPage() || 'home';
    this.router.navigate([`/${targetUrl}`]);
  }

  goToHome() {
    this.router.navigate(['/home']);
  }

  /**
   * Funzione invocata dal pulsante in pagina "PROSEGUI".
   * La funzione effettuerà un submit dei dati del formio, a seconda del risultato visualizzerà eventuali messaggi di segnalazione.
   * I dati generati dal formio saranno definiti all'interno della variabile: this.jsonConfiguraTemplate.
   */
  onProsegui() {
    // Lancio manualmente il submit del formio
    this.ngFormio?.formio?.submit();

    if (!this.validFormFlag) {
      this.messageService.showMessage('E001', 'card', true);
      return;
    }
    if (this.IndTipoSelezioneAC !== 'N' && this.selectedAC?.length < 1) {
      this.messageService.showMessage('E001', 'card', true);
      return;
    }

    // Verifico se esiste il json data dell'istanza
    if (this.jsonData) {
      // Vado al prossimo step
      this.goToNextStep();
      // #
    } else {
      // Chiedo conferma all'utente, informandolo che i dati definiti non potranno essere modificati
      this.messageService.showConfirmation({
        title: 'Attenzione',
        codMess: 'A011',
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
              this.goToNextStep();
            },
          },
        ],
      });
    }
  }

  /**
   * Funzione che gestisce le logiche per avanzare allo step successivo per i quadri dell'istanza.
   * La funzione prevede il passaggio logico per alcune funzioni che possono andare ad effettuare logiche sulla base dell'adempimento.
   */
  goToNextStep() {
    // Recupero l'oggetto contenente le informazioni del formio e dei dati dell'orientamento
    const orientamentoData: any = this.jsonConfiguraTemplate;
    // Recupero l'informazione relativa all'adempimento attivo
    const tipoAdempimento: TipoAdempimento = this.tipoAdempimento;
    // Lancio la funzione che va a manipolare le informazioni del submit di formio per adempimento e sovrascrivo l'oggetto del json data
    this.jsonConfiguraTemplate = this.completaDatiOrientamentoPerAdempimento(
      tipoAdempimento,
      orientamentoData
    );

    this.ambitoStoreService.jsonConfiguraTemplateSub.next(
      this.jsonConfiguraTemplate
    );
    this.router.navigate(
      [`../../istanza/${this.selectedAdempimento.cod_adempimento}`],
      {
        relativeTo: this.route,
        state: {
          attoreGestioneIstanza: this.attoreGestioneIstanza,
        },
      }
    );
  }

  /**
   * Funzione che verifica l'adempimento passato in input. A seconda dell'adempimento, verrano invocate le funzioni di complemento dati per l'orientamento.
   * @param tipoAdempimento TipoAdempimento con le informazioni per l'adempimento da gestire.
   * @param orientamentoData any con l'oggetto dati generato per l'orientamento.
   * @returns any con le informazioni manipolate per adempimento.
   */
  private completaDatiOrientamentoPerAdempimento(
    tipoAdempimento: TipoAdempimento,
    orientamentoData: any
  ) {
    // Verifico l'input
    if (!tipoAdempimento || !tipoAdempimento.cod_tipo_adempimento) {
      // Mancano le informazioni minime
      return orientamentoData;
      // #
    }

    // Recupero il codice del tipo adempimento per gestire possibili elaborazioni dati
    const codTipoAdempi: string = tipoAdempimento.cod_tipo_adempimento;
    // Verifico quale adempimento l'utente sta utilizzando
    switch (codTipoAdempi) {
      case ScrivaCodTipiAdempimenti.DER:
        // Gestisco la casistica per derivazioni
        return this._bozzaDER.completaDatiBozzaOrientamento(orientamentoData);
      // #
    }

    // Ritorno le informazioni modificate
    return orientamentoData;
  }

  compareAdempimento(a1: Adempimento, a2: Adempimento) {
    return a1 && a2 && a1.id_adempimento === a2.id_adempimento;
  }

  compareAC(a1: CompetenzaTerritorio, a2: CompetenzaTerritorio) {
    return (
      a1 && a2 && a1.id_competenza_territorio === a2.id_competenza_territorio
    );
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni dall'oggetto di submit del formio.
   * Il dato verrà preparato per il salvataggio su DB.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any con l'oggetto sanitizzato per il salvataggio su DB.
   */
  private handleFormioSubmitForDB(formioData: any): any {
    // Richiamo e ritorno il risultato dalla funzione del servizio
    return this._formio.handleFormioSubmitForDB(formioData);
  }

  /**
   * #############################################
   * FUNZIONALITA' FORMIO CONFIGURAZIONE E UTILITY
   * #############################################
   */

  /**
   * Funzione di setup che raccoglie tutte le logiche, servizi e configurazioni da passare come riferimento al contesto FormIo.
   */
  private setupFormIoRenderOptions() {
    // Definisco l'oggetto con i parametri per le render options
    const formioParams: IFormioRenderOptionsParams = {
      formioUpdate: (data: any) => {
        // Lancio la funzione locale di aggiornamento
        this.updateFormIo(data);
      },
      isFrontOffice: this.isFrontOffice,
    };

    // Richiedo la generazione dell'oggetto per le render options
    this.renderOptions = this._formio.getFormIoRenderOptions(formioParams);
  }

  /**
   * Funzione che forza l'aggiornamento dei dati formio.
   * @debug Il refresh attiva di nuovo tutti i trigger e i controlli di tutti, verificare che sul FormIo ci siano delle condizioni ad hoc che impediscano il comportamento (vedi documentazione per le modali nella documentazione interna "SCRIVA - Best practices di progettazione V3.0.docx").
   * @param data any con le informazioni per aggiornare il formio.
   */
  private updateFormIo(data: any) {
    // Verifico l'input
    if (!data) {
      // Non c'è l'oggetto di aggiornamento
      return;
    }

    // Lancio il refresh manuale del formio
    this.triggerRefresh.emit({
      /* value: data */ /* form: this.customForm */ submission: { data },
    });
  }
}
