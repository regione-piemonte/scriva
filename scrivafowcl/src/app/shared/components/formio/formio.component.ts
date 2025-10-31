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
  Component,
  EventEmitter,
  Inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormioComponent as NgFormioComp } from 'angular-formio';
import {
  FormioForm,
  FormioOptions,
  FormioRefreshValue,
} from 'angular-formio/formio.common';
import { cloneDeep } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of, Subscription } from 'rxjs';
import {
  AllegatiService,
  AmbitoService,
} from 'src/app/features/ambito/services';
import { ScrivaServerError } from '../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../core/enums/scriva-codes-messages.enums';
import { CONFIG } from '../../config.injectiontoken';
import {
  IFormIoRenderOptions,
  IServiziErrorConfig,
} from '../../interfaces/scriva-utilities.interfaces';
import { Adempimento, Help, Istanza, Quadro, StepConfig } from '../../models';
import { FormioImports } from '../../models/formio/formio-imports.model';
import {
  AppConfigService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from '../../services';
import { FormioImporterService } from '../../services/formio/formio-importer.service';
import { FormioModalsService } from '../../services/formio/formio-modals.service';
import { FormioService } from '../../services/formio/formio.service';
import { FormioI18NService } from '../../services/formio/i18n/formio-i18n.service';
import {
  IFormioRenderOptionsParams,
  IFormioValidationCheck,
} from '../../services/formio/utilities/formio.interfaces';
import { QuadriService } from '../../services/quadri/quadri.service';
import { IQuadroDettOggConfigs } from '../../services/quadri/utilities/quadri.interfaces';
import { ScrivaUtilitiesService } from '../../services/scriva-utilities/scriva-utilities.service';
import { AttoreGestioneIstanzaEnum } from '../../utils';
import {
  AbilitazioniFormio,
  FormioCustomEvents,
} from './utilities/formio.enums';
import { StepperIstanzaComponent } from '../stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from 'src/app/features/ambito/services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo,
  RequestSaveBodyQuadro
} from '../stepper-istanza/utilities/stepper-istanza.interfaces';
import { CodiciTipiQuadro } from '../../enums/tipo-quadro.enums';

@Component({
  selector: 'app-formio',
  templateUrl: './formio.component.html',
  styleUrls: ['./formio.component.scss'],
})
export class FormioComponent extends StepperIstanzaComponent implements OnInit {
  /** ViewChild che permette di accedere alla struttura dati del componente della libreria Formio. */
  @ViewChild('ngFormio', { static: false }) ngFormio: NgFormioComp;

  beUrl: string;

  helpMaschera: Help[];
  helpQuadro: Help[];

  customForm: FormioForm;
  submission: any = {};

  codMaschera: string;
  istanza: Istanza;
  idOggIstanza: number;
  codAdempimento: string;
  idOggetto: number;
  gestioneEnum = AttoreGestioneIstanzaEnum;

  quadro: Quadro;

  adempimento: Adempimento;

  validFormFlag = false;

  // showButtonNext = false;

  jsonDataAll = null;
  jsonConfigAll = null;

  jsonDataQuadro = null;
  saveWithPut = false;

  readOnly: boolean = false;

  /** EventEmitter<FormioRefreshValue> FormIo che permette la gestione dell'aggiornamento dei dati del formio. */
  triggerRefresh = new EventEmitter<FormioRefreshValue>();
  /** IFormIoRenderOptions contenente le informazioni da iniettare all'interno del formio. */
  renderOptions: IFormIoRenderOptions = {};
  /** FormioOptions con le configurazione per la gestione del componente FormIo. */
  formioOptions: FormioOptions = {};

  private subscriptionImporter = new Subscription();

  constructor(
    protected ambitoService: AmbitoService,
    protected configService: AppConfigService,
    protected _formio: FormioService,
    protected _formioImporter: FormioImporterService,
    protected _formioModals: FormioModalsService,
    protected _formioI18N: FormioI18NService,
    protected _quadri: QuadriService,
    protected allegati: AllegatiService,
    protected _scrivaUtilities: ScrivaUtilitiesService,
    presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) protected injConfig: StepConfig,
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
    this.attoreGestioneIstanza = AttoreGestioneIstanzaEnum.WRITE as string;
    this.setVisibilityButtonNext(true);
    this.beUrl = this.configService.getBEUrl();

    // Definisco le configurazioni per l'iniezione dati dentro formio
    this.setupFormIoOptions();
    this.setupFormIoRenderOptions();
  }

  ngOnInit() {
    this.spinner.show();
    this.helpService.setCodMaschera(this.codMaschera);
    this.helpService.setCodContesto(undefined);
    this.subscriptionImporter.unsubscribe();
    this.loadData();
  }

  loadData() {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();
    // Modo alternativo al disableflag per settare il readonly del form
    // Fase 1 scommentare chiamata qui sotto e lasciando anche il modo con disableflag verificare che funzioni tutto nei diversi casi
    // Fase 2 sui nuovi adempimenti/quadri utilizzare la modalita con il formio senza logica per il disableflag
    // Fase 3 rimuovere logica superflua dai formio pregressi
    this.enableFormioReadonly();

    // #
    this.stepManagerService
      .getQuadroByIdIstanza(this.idIstanza, this.idTemplateQuadro)
      .subscribe(
        (res) => {
          this.quadro = res.template.quadri[0];
          this.istanza = res.istanza;
          this.adempimento = this.istanza.adempimento;
          this.helpService.setCodContestoFromAdempimento(this.adempimento);
          this.codAdempimento = this.adempimento.cod_adempimento;
          // Mappa per associare i codici adempimento ai rispettivi codici quadro - SCRIVA-1545, solo caso VINCA al momento
          const mappingQuadro = {
            SCR: 'QDR_DETT_OGG_SCR',
            APP: 'QDR_DETT_OGG_APP',
          };
          // Verifico se almeno uno dei `codQuadro` presenti nel mappa (solo vinca al momento) contiene uno dei dettagli e setto il relativo codice maschera se necessario
          if (mappingQuadro[this.codAdempimento]) {
            this.setCodiceMaschera(mappingQuadro[this.codAdempimento]);
          }

          if (this.istanza.json_data) {
            this.jsonDataAll = JSON.parse(this.istanza.json_data);
            this.qdr_riepilogo = this.jsonDataAll.QDR_RIEPILOGO;
          }
          this.jsonConfigAll =
            this.jsonDataAll?.QDR_CONFIG ||
            JSON.parse(res.template.json_configura_template);
          this.getHelpList();
          this.configQuadro();
          this.spinner.hide();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'formContainer');
        }
      );

    this.ambitoService.getOggettiIstanzaByIstanza(this.idIstanza).subscribe(
      (res) => {
        this.idOggIstanza = res[0].id_oggetto_istanza;
        this.idOggetto = res[0].id_oggetto;
      },
      (err) => {
        if (err.error?.code) {
          this._messageService.showMessage(
            err.error.code,
            'formContainer',
            false
          );
        }
      }
    );
  }

  setCodiceMaschera(codQuadro) {
    if (codQuadro) {
      switch (codQuadro) {
        case 'QDR_DICHIARAZIONE':
          this.codMaschera = '.MSCR016F';
          break;
        case 'QDR_DETT_OGG_SCR':
          this.codMaschera = '.MSCR019F';
          break;
        case 'QDR_DETT_OGG_APP':
          this.codMaschera = '.MSCR020F';
          break;
        case 'QDR_DER_DATIGEN':
          this.codMaschera = '.MSCR022F';
          break;
        case CodiciTipiQuadro.altriUsi:
          this.codMaschera = '.MSCR031F';
          break;
        case 'QDR_DETT_OGGETTO':
          this.codMaschera = this.helpService.getCodMaschera();
          break;
        default:
          break;
      }
    } else {
      this.codMaschera = this.helpService.getCodMaschera();
    }
    this.helpService.setCodMaschera(this.codMaschera);
  }

  /**
   * @override
   */
  getHelpList() {
    const getHelpMaschera = this.helpService.getHelpByChiave(
      this.componente + this.codMaschera
    );
    const getHelpQuadro = this.helpService.getHelpByChiave(
      `${this.componente}.${this.adempimento.tipo_adempimento.cod_tipo_adempimento}.${this.adempimento.cod_adempimento}.${this.codQuadro}`
    );

    forkJoin([getHelpMaschera, getHelpQuadro]).subscribe(
      (res) => {
        this.helpMaschera = res[0];
        this.helpQuadro = res[1];
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, 'formContainer');
      }
    );
  }

  configQuadro() {
    this.quadro.json_configura_quadro = JSON.parse(
      this.quadro.json_configura_quadro
    );

    if (this.jsonDataAll) {
      this.quadro.json_configura_quadro.jsonForm['json_istanza'] =
        this.jsonDataAll;
    }
    if (this.jsonConfigAll) {
      this.quadro.json_configura_quadro.jsonForm['json_config'] =
        this.jsonConfigAll;
    }

    const puntamento = {
      value: this.beUrl,
    };

    this.quadro.json_configura_quadro.jsonForm['puntamento'] = puntamento;

    // Verifico le informazioni che devo importare nel formio
    // tramite un servizio apposito che prende informazioni e le mette
    // a disposizione del contesto formio
    this.subscriptionImporter = this._formioImporter.getImports().subscribe({
      next: (response: FormioImports) => {
        this.setcustomform(response);
      },
      error: (err) => {
        this.setcustomform();
      },
    });
    this._formioImporter.setImports(
      this.istanza,
      this.quadro.json_configura_quadro.imports
    );
  }

  private setcustomform(imports: FormioImports = null) {
    let customForm = { ...this.quadro.json_configura_quadro.jsonForm };
    // metto a disposizione anche dalla parte del form
    // serve per leggere in un tag html
    // es.  "content": "<strong>id_template {{form.imports.ISTANZA.id_template}}</strong>",
    customForm.imports = imports;
    this.customForm = customForm;
    // TEST ONLY: @Ismaele @Marco => MOCKARE SOLO PER TEST, RIMUOVERE QUANDO FATTO
    // this.customForm.components = TEST_FORMIO;

    if (
      this.jsonDataAll[this.codQuadro] ||
      (this.jsonDataAll[this.codTipoQuadro] &&
        this.jsonDataAll[this.codTipoQuadro][this.codQuadro])
    ) {
      const nestedBlock = this.codQuadro !== this.codTipoQuadro;
      this.saveWithPut = nestedBlock
        ? !!this.jsonDataAll[this.codTipoQuadro][this.codQuadro]
        : !!this.jsonDataAll[this.codQuadro];

      setTimeout(() => {
        // Definisco le informazioni del formio tramite oggetto submission
        this.setupFormioData(this.customForm, imports);
        // #
      }, 500);
    } else {
      // ci agganciamo alla submission
      // con le stesse modalità del caso nell'if sopra
      setTimeout(() => {
        // Definisco le informazioni del formio tramite oggetto submission
        this.submission = {
          data: {
            imports: imports,
          },
        };
        // #
      }, 500);
    }
  }

  /**
   * Funzione di supporto che gestisce le logiche di definizione delle informazioni del formio per la visualizzazione dei dati.
   * @param customForm FormioForm che definisce l'oggetto per la generazione del formio. Verrà modificato se contenente specifiche configurazioni.
   */
  private setupFormioData(
    customForm: FormioForm,
    imports: FormioImports = null
  ) {
    // Definisco l'oggetto d'informazioni da passare al formio
    let formioData: any = this.defineFormioData();

    // Definisco il flag per la scrittura/lettura del formio
    formioData.disableFlag = this.enableFormio();

    // Lancio tutte le gestioni del formio come setup iniziale
    formioData = this.handleFormioSetupFromDB(customForm, formioData);

    // Se ci sono degli import recuperati li aggiungo
    if (imports) {
      formioData.imports = imports;
    }
    // Definisco l'oggetto di submission per la pre-popolazione delle informazioni
    this.submission = { data: formioData };
  }

  /**
   * Funzione di supporto che verifica se le informazioni del formio risultano innestate nei dati del quadro.
   * Terminata la verifica, ritorna le informazioni per i dati del formio.
   * @returns any con la configurazione dati per pre-popolare il formio.
   */
  private defineFormioData(): any {
    // Imposto una variabile di comodo per i dati del formio
    let formioData: any;

    // Verifico se i dati sono innestati nei quadri
    if (this.isNestedData) {
      // Sono innestati, recupero le informazioni
      formioData = this.jsonDataAll[this.codTipoQuadro][this.codQuadro];
      // #
    } else {
      // Non sono innestati, recupero subito l'informazione
      formioData = this.jsonDataAll[this.codQuadro];
      // #
    }

    // Creo una copia e ritorno il valore
    return cloneDeep(formioData);
  }

  /**
   * Funzione che verifica se l'attore in linea è abilitato in scrittura.
   * Viene ritornato un codice che permette di gestire la modalità di scrittura o di sola lettura.
   * @returns literal AbilitazioniFormio che definisce se la scrittura è abilitata.
   */
  private enableFormio(): AbilitazioniFormio {
    // Verifico il flag di abilitazione
    if (this.isAbilitataScrittura) {
      // Abilitato in scrittura
      return AbilitazioniFormio.scrittura;
      // #
    } else {
      // Abilitato in sola lettura
      return AbilitazioniFormio.lettura;
      // #
    }
  }

  /**
   * setto il readonly per il formio
   */
  private enableFormioReadonly(): void {
    // Verifico il flag di abilitazione
    if (this.isAbilitataScrittura) {
      this.readOnly = false;
    } else {
      this.readOnly = true;
    }
  }

  onChange(formData) {
    if (formData?.data) {
      this.jsonDataQuadro = formData.data;
    }

    if (formData?.isModified) {
      // formio onChange setto la variabile di ambiente dello stepper changed
      this.changed = true;
    }

    if (
      (formData?.isModified || this.submission.hasOwnProperty('data')) &&
      formData?.isValid === true
    ) {
      // this.showButtonNext = true;
      this.validFormFlag = true;
      this.setStepCompletedEmit('FormioComponent', true, this.quadro.id_quadro);
    } else if (formData?.isValid === false) {
      this.validFormFlag = false;
      this.setStepCompletedEmit(
        'FormioComponent',
        false,
        this.quadro.id_quadro
      );
    }
  }

  /**
   * Funzione invocata all'emissione di un evento custom da parte del componente formio.
   * @param customEvent any contenente le informazioni di dettaglio per l'evento generato dal componente formio.
   */
  onCustomEvent(customEvent: any) {
    // Cerco d'identificare l'evento generato dal formio
    switch (customEvent.type) {
      // ### Visualizzazione degli helper
      case FormioCustomEvents.visualizzaHelper:
        // Tento di visualizzare l'helper data la chiave identificativa censita sul formio
        this.formioHelp(customEvent.component.key);
        break;
      default:
        break;
    }
  }

  formioHelp(key: string) {
    // this line removes the JS_HELP. prefix
    key = key.substring(8);

    const chiaveHelp = `${this.componente}.${this.adempimento.tipo_adempimento.cod_tipo_adempimento}.${this.adempimento.cod_adempimento}.${this.codQuadro}.${key}`;
    const modalContent =
      this.helpQuadro.find((help) => help.chiave_help === chiaveHelp)
        ?.des_testo_help || 'Help non trovato...';

    this._messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  /**
   * @override
   */
  protected onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.saveData();
    } else {
      this.goToStep(this.stepIndex);
    }
  }

  /**
   * Funzione che gestisce le logiche di salvataggio della pagina e tentativo di procedere allo step successivo per i quadri.
   */
  saveData() {
    // #region "VECCHIO CODICE"
    // @Ismaele - 11/11/2024 => Ho fatto una migliorativa e testata su modale RESTITUZIONI, riporto anche qua, i test mi sembrano OK, ma tengo codice vecchio in caso di problemi inattesi.
    // // Prima di andare allo step successivo validiamo il form
    // this.ngFormio.formio.onSubmission.then(() => {
    //   setTimeout(() => {
    //     const validForm = !this.ngFormio.alerts.alerts.find(
    //       (i) => i.type === 'danger'
    //     )
    //       ? true
    //       : false;
    //     // TODO Remove console
    //     console.log('alerts: ', this.ngFormio.alerts.alerts);
    //     console.log('validForm: ', validForm);
    //     this.goToNextStepOrShowMessage(validForm);
    //   }, 500);
    // });

    // // Lancio manualmente il submit del formio.
    // // Note: la funzione evidenziera' i campi non validati in rosso con .formio-error-wrapper
    // this.ngFormio.formio.submit();
    // #endregion "VECCHIO CODICE"

    // Definisco le istruzioni per la callback da invocare quando il formio sarà validato
    const callback: (valid: boolean) => any = (valid: boolean) => {
      // Lancio la funzione
      this.checkValidityStepAndSave(valid);
      // #
    };
    // Recupero l'istanza del componente del formio gestito
    const formFormIo: NgFormioComp = this.ngFormio;
    // Costruisco l'oggetto con i parametri per la gestione della validità del FormIo
    const params: IFormioValidationCheck = { callback, formFormIo };
    // Richiamo la funzione di gestione
    this._formio.handleFormIoValidation(params);
  }
  /**
   * Funzione di comodo che verifica se all'interno del componente FormioComponent della libreria sono presenti degli errori.
   * @param formFormIo FormioComponent con il riferimento al componente FormIo.
   * @returns boolean con il risultato del check sugli errori del form.
   * @override
   */
  protected isStepValid(): Observable<boolean> {
    // Recupero l'istanza del componente del formio gestito
    const formFormIo: NgFormioComp = this.ngFormio;
    return of(this._formio.isFormIoValid(formFormIo));
  }

  /**
   * Funzione che mappa l'oggetto in modo da prepararlo per salvataggio su DB
   * @returns oggetto che salva su DB
   */
  protected getJsonDataQuadroforDB() {
    let jsonDataQuadro = cloneDeep(this.jsonDataQuadro);
    // Lancio il check e l'esecuzione di possibili logiche custom del formio
    return this.handleFormioSubmitForDB(jsonDataQuadro);
  }

  /**
   * Funzione che controlla se form è valido
   * se valido richiama funzione di salvataggio
   * oppure mostra messaggio di errore
   * @param validForm
   */
  protected checkValidityStepAndSave(validForm: boolean) {
    // Verifico se il formio è valido
    if (validForm) {
      this.azioneDopoValidazione();
      // #
    } else {
      // Visualizzo un messaggio d'errore
      this._messageService.showMessage('E001', 'formContainer', true);
    }
  }
  /**
   * Funzione che viene invocata dopo la validazione del formio.
   * @override
   */
  protected azioneDopoValidazione(): void {
    // Creo una copia dell'oggetto generato dal formio per poterci lavorare
    let jsonDataQuadro = this.getJsonDataQuadroforDB();
    // vado avanti nel salvataggio del json e nello stepper
    this.salvaDatiQuadro(jsonDataQuadro);
  }

  /**
   * Funzione che setta i dati del quadro e aggiorna riepilogo per il salvataggio.
   * @param configsRiepilogo contenente i dati quadro
   * @returns dataQuadro aggiornato
   */
  private prepareDatiSalvataggio(configsRiepilogo: IPrepareDatiRiepilogo): any {
     const nestedBlock = this.codQuadro !== this.codTipoQuadro;
     console.log('nestedBlock',nestedBlock);
    let dataQuadro = {};
    if (nestedBlock) {
      if (!this.qdr_riepilogo[this.codTipoQuadro]) {
        this.qdr_riepilogo[this.codTipoQuadro] = {};
      }
      dataQuadro[this.codQuadro] = configsRiepilogo.datiQuadro;
      this.qdr_riepilogo[this.codTipoQuadro][this.codQuadro] = configsRiepilogo.datiQuadro;
    } else {
      dataQuadro = configsRiepilogo.datiQuadro;
      this.qdr_riepilogo[this.codTipoQuadro] = configsRiepilogo.datiQuadro;
    }
    console.log('dataQuadro',dataQuadro);
    console.log(' this.qdr_riepilogo[this.codTipoQuadro]',nestedBlock);
    return dataQuadro;
  }

  /**
   * Funzione per creare body per il salvataggio del quadro
   * @returns RequestSaveBodyQuadro contenente le info del quadro e del riepilogo
   */
  private prepareDataQuadroERiepilogo(
    configsRiepilogo: IPrepareDatiRiepilogo
  ): RequestSaveBodyQuadro {    
    //preparo dati per il salvataggio del quadro e riepilogo
   const dataQuadro = this.prepareDatiSalvataggio(configsRiepilogo);

    // Creo la configurazione per l'aggiornamento dati del quadro
    const configs: IQuadroDettOggConfigs = {
      idOggettoIstanza: this.idOggIstanza,
      idOggetto: this.idOggetto,
    };
    // Lancio la gestione di aggiornamento dei dati
    this.handleQuadroDettOgg(this.qdr_riepilogo, configs);

    const reqSaveQuadroConfigs: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: dataQuadro,
      datiRiepilogo: this.qdr_riepilogo,
    };
    const requestData: RequestSaveBodyQuadro =
      this.buildRequestDataToSaveQuadro(reqSaveQuadroConfigs);
    return requestData;
  }


  salvaDatiQuadro(jsonDataQuadro: any) {
    // Preparo le informazioni per il salvataggio dei dati
    const configs: IConfigsSaveQuadro = {
      datiQuadro: jsonDataQuadro,
      datiRiepilogo: this.qdr_riepilogo,
    };

    this.saveDataQuadro(configs).subscribe(
      (res) => {
        this.spinner.hide();
        this.saveWithPut = true;
        this.goToStep(this.stepIndex);
      },
      (err) => {
        this.spinner.hide();
        this.showErrorsQuadroConCodeENoCode(err, 'formContainer');
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
    const requestData: RequestSaveBodyQuadro =
      this.prepareDataQuadroERiepilogo(requestConfigs);
    this.spinner.show();
    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      this.saveWithPut,
      true,
      true
    );
  }

  /**
   * Funzionalità di supporto che gestisce il setup delle informazioni dall'oggetto di pre-configurazione del formio.
   * Il dato verrà preparato per la lettura dalla libreria.
   * @param customForm FormioForm contenente le configurazioni del formio.
   * @param formioData any contenente l'oggetto "data" che verrà passato come pre-configurazione del formio.
   * @returns any con l'oggetto formattato per la lettura da parte della libreria.
   */
  private handleFormioSetupFromDB(
    customForm: FormioForm,
    formioData: any
  ): any {
    // Richiamo e ritorno il risultato dalla funzione del servizio
    return this._formio.handleFormioSetupFromDB(customForm, formioData);
  }

  /**
   * Funzionalità di supporto che gestisce la pulizia delle informazioni dall'oggetto di submit del formio.
   * Il dato verrà preparato per il salvataggio su DB.
   * @param formioData any contenente l'oggetto "data" restituito come risultato del submit del formio.
   * @returns any con l'oggetto sanitizzato per il salvataggio su DB.
   */
  protected handleFormioSubmitForDB(formioData: any): any {
    // Richiamo e ritorno il risultato dalla funzione del servizio
    return this._formio.handleFormioSubmitForDB(formioData);
  }

  /**
   * Funzionalità di supporto che gestisce l'aggiunta delle informazioni all'oggetto dei quadri.
   * Il dato verrà preparato per il salvataggio su DB.
   * @param qdrRiepilogo any contenente l'oggetto dei quadri da salvare.
   * @param configs IQuadroDettOggConfigs con i dati di configurazione per l'aggiornamento.
   * @returns any con l'oggetto sanitizzato per il salvataggio su DB.
   */
  private handleQuadroDettOgg(
    qdrRiepilogo: any,
    configs: IQuadroDettOggConfigs
  ) {
    // Richiamo il servizio di aggiornamento dei dati del quadro
    this._quadri.handleQuadroDettOgg(qdrRiepilogo, configs);
  }

  /**
   * #############################################
   * FUNZIONALITA' FORMIO CONFIGURAZIONE E UTILITY
   * #############################################
   */

  /**
   * Funzione di setup che definisce le logiche di configurazione del componente FormIo.
   */
  private setupFormIoOptions() {
    // Definisco l'oggetto con i parametri per le render options
    const overrideOptions: FormioOptions = {};
    // Richiedo la generazione dell'oggetto per le render options
    this.formioOptions = this._formio.getFormIoOptions(overrideOptions);
  }

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

  /**
   * #######################
   * GESTIONE ERRORI SERVIZI
   * #######################
   */

  /**
   * Funzione di supporto che gestisce gli errori generati dalla chiamata ai servizi.
   * @param e ScrivaServerError con l'oggetto d'errore generato dalla chiamata.
   */
  protected onServiziError(e?: ScrivaServerError, target?: string) {
    // Definisco la configurazione per la gestione dell'errore
    const errorConfigs: IServiziErrorConfig = {
      target: target ?? 'formContainer',
      autoFade: false,
      defaultCode: ScrivaCodesMesseges.E100,
      e,
    };

    // Richiamo la funzione di gestione degli errori con configurazion
    this.onServiziErrorConfigs(errorConfigs);
  }

  /**
   * Funzione di comodo che gestisce i possibili errori generati dal server.
   * @param configs IServiciErrorConfig che definisce la configurazione per la visualizzazione degli errori.
   */
  protected onServiziErrorConfigs(configs: IServiziErrorConfig) {
    // Verifico l'input
    if (!configs) {
      // Nessuna configurazione, blocco
      return;
    }

    // Estraggo le configurazioni dall'input
    const { e, defaultCode, target, autoFade } = configs;

    // Estraggo il possibile codice d'errore dall'oggetto ritornato dal server
    let errCode = e?.error?.code;
    // Verifico se esiste il codice, altrimenti definisco un default
    errCode = errCode ?? defaultCode ?? ScrivaCodesMesseges.E100;
    // Visualizzo il messaggio d'errore
    this._messageService.showMessage(errCode, target, autoFade);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter di comodo che definisce se i dati del formio sono innestati
   * @returns boolean con il risultato del check.
   */
  get isNestedData(): boolean {
    // Effettuo il confronto tra le informazioni dei quadri
    return this.codQuadro !== this.codTipoQuadro;
  }

  /**
   * Getter di comodo che definisce se l'attore in linea è abilitato in scrittura.
   * @returns boolean con il risultato del check.
   */
  get isAbilitataScrittura(): boolean {
    // Verifico se l'attore che gestisce l'istanza è abilitato alla scrittura
    return this.attoreGestioneIstanza === this.gestioneEnum.WRITE;
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
    this.subscriptionImporter.unsubscribe();
  }
}
