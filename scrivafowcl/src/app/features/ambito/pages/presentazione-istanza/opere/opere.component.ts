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
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  ColumnMode,
  SelectionType,
  TableColumn,
} from '@swimlane/ngx-datatable';
import { clone, cloneDeep, differenceBy, filter } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { BehaviorSubject, forkJoin, Observable, of } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import {
  OggettoIstanza,
  Opera,
  SoggettoIstanza,
} from 'src/app/features/ambito/models';
import { AmbitoService } from 'src/app/features/ambito/services';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import {
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  Comune,
  ConfigAdempimento,
  Help,
  Istanza,
  ModalButton,
  Provincia,
  Quadro,
  StepConfig,
  TemplateQuadro,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  ConfigurazioniScrivaService,
  HelpService,
  IstanzaService,
  LocationService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import {
  IGeecoOpen,
  IGeecoQuitUrlParams,
  IGeoGeeco,
  IGeoGeecoConfigs,
} from 'src/app/shared/services/geeco/utilities/geeco.interfaces';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { ScrivaServerError } from '../../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../../core/enums/scriva-codes-messages.enums';
import { ISNgxDTSelect } from '../../../../../core/interfaces/scriva-ngx-datable.interfaces';
import { ScrivaGestioneDatiQuadro } from '../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import {
  IAlertConfig,
  IServiziErrorConfig,
} from '../../../../../shared/interfaces/scriva-utilities.interfaces';
import { TipoInfoAdempimento } from '../../../../../shared/services/configurazioni/utilities/configurazioni.enums';
import { FormioService } from '../../../../../shared/services/formio/formio.service';
import { GeecoService } from '../../../../../shared/services/geeco/geeco.service';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { IMsgPlacholder } from '../../../../../shared/services/message/utilities/message.interfaces';
import { ActionsOggettiSearchEnum } from '../../../enums/actions-oggetti-search.enum';
import { OpereVerificheStepService } from '../../../services/opere/opere-verifiche-step.service';
import { OpereService } from '../../../services/opere/opere.service';
import { IAssociaOggettiIstanza } from '../../../services/opere/utilities/opere-modal.interfaces';
import { IOpereSaveJsonDataRes } from '../../../services/opere/utilities/opere.interfaces';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import { DatiTecniciOperaModalComponent } from './modals/dati-tecnici-opera-modal/dati-tecnici-opera-modal.component';
import {
  IDTOperaModalCallbacks,
  IDTOperaModalParams,
  IDTOperaSalvataggio,
} from './modals/dati-tecnici-opera-modal/utilities/dati-tecnici-opera-modal.interfaces';
import { OpereConsts } from './utilities/opere.consts';
import {
  IAdempimentoOperaLinkReq,
  IAdempimentoOperaLinkRes,
  IListeDatiTecniciOggettiIstanze,
  IOperaInstanzaDerivatiReq,
  IOperaInstanzaDerivatiRes,
} from './utilities/opere.interfaces';

@Component({
  selector: 'app-opere',
  templateUrl: './opere.component.html',
  styleUrls: ['./opere.component.scss'],
  providers: [OpereVerificheStepService],
})
export class OpereComponent
  extends StepperIstanzaComponent
  implements OnInit, OnDestroy, AfterViewInit
{
  /** OpereConsts con le informazioni costanti per il componente. */
  public OPERE_CONSTS = new OpereConsts();
  /** string che definisce il prefisso per l'identificazione delle informazioni dei dati tecnici. */
  protected PREFIX_JS_DT: string = this.OPERE_CONSTS.PREFIX_JS_DT;
  /** string che definisce la proprietà del FormIo che definisce se una sezione dati risulta obbligatoria. */
  protected SECTION_REQUIRED: string = this.OPERE_CONSTS.SECTION_REQUIRED;
  /** string che definisce il nome del container per la visualizzazione dei messaggi al rientro da Geeco. */
  public readonly GEECO_ALERT_CONTAINER: string =
    this.OPERE_CONSTS.GEECO_ALERT_CONTAINER;

  @ViewChild('checkboxHeaderTemplate') checkboxHeaderTemplate: TemplateRef<any>;
  @ViewChild('checkboxColTemplate') checkboxColTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  /** ViewChild con il riferimento al template html per: comune provincia opera. */
  @ViewChild('comuneProvinciaOperaTemplate')
  comuneProvinciaOperaTemplate: TemplateRef<any>;
  /** ViewChild con il riferimento al template html per: comune provincia oggetto istanza. */
  @ViewChild('comuneProvinciaOggIstTemplate')
  comuneProvinciaOggIstTemplate: TemplateRef<any>;

  @ViewChild('localitaTemplate') localitaTemplate: TemplateRef<any>;

  /** ViewChild con il riferimento al template html per: codice scriva opera. */
  @ViewChild('codiceScrivaOperaTemplate')
  codiceScrivaOperaTemplate: TemplateRef<any>;
  /** ViewChild con il riferimento al template html per: codice scriva opera. */
  @ViewChild('codiceRilievoOperaTemplate')
  codiceRilievoOperaTemplate: TemplateRef<any>;
  /** ViewChild con il riferimento al template html per: codice scriva oggetto istanza. */
  @ViewChild('codiceScrivaOggIstTemplate')
  codiceScrivaOggIstTemplate: TemplateRef<any>;

  codMaschera = '.MSCR024F';

  codAdempimento: string;
  adempimento: Adempimento;
  istanza: Istanza;
  quadro: Quadro;

  gestioneEnum = AttoreGestioneIstanzaEnum;

  /** Opera[] con le opere scaricate per la selezione in base all'istanza. */
  opere: Opera[];
  /** OggettoIstanza[] con la lista degli oggetti istanza scaricati per il quadro. */
  protected _oggettiIstanza: OggettoIstanza[] = [];

  datiTecnici;
  tipologieOpere: string[];
  idSoggettiList: number[];

  province: Provincia[] = [];
  ricercaComuniList: Comune[] = [];
  searchResults: Opera[] = [];
  searchResultsColumns: TableColumn[] = [];
  selectedOpere: Opera[];
  associazioniColumns: TableColumn[] = [];
  ColumnMode = ColumnMode;
  selectionType = SelectionType.checkbox;

  saveWithPut = false;

  sourceDataForModal: any;

  /** String che definisce la tipologia ricerca per gli oggetti istanza. */
  configTipologiaOggetto: string;

  configQuadro: any;
  idOggettiIstanza: any;

  autoFade = true;

  /** Help con l'header help per il quadro. Non tutti i quadri delle opere avranno per forza un header help quadro. */
  helpHeader: Help;

  // BehaviorSubject per azioni Oggetti Search
  actionsOggettiSearch$: BehaviorSubject<ActionsOggettiSearchEnum> =
    new BehaviorSubject<ActionsOggettiSearchEnum>(null);

  /** string che definisce il nome del componente di riferimento. E' pensato per essere overridato con il nome del componente che estende la classe opere.component.ts. */
  protected componentName: string = 'OpereComponent';

  /** any per gestire il componente da usare per la modale di dettaglio delle opere. Per default è: DatiTecniciOperaModalComponent.  */
  protected componenteModale: any = DatiTecniciOperaModalComponent;

  /**
   * Costruttore.
   */
  constructor(
    @Inject(CONFIG) injConfig: StepConfig,
    protected _ambito: AmbitoService,
    protected _adempimento: AdempimentoService,
    protected _configurazioni: ConfigurazioniScrivaService,
    protected _formio: FormioService,
    protected _geeco: GeecoService,
    protected _location: LocationService,
    protected _logger: LoggerService,
    protected _modal: NgbModal,
    protected _opere: OpereService,
    protected _opereVerificheStep: OpereVerificheStepService,
    protected _route: ActivatedRoute,
    presentazioneIstanzaService: PresentazioneIstanzaService,
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
    // Lancio il setup per il codice dell'adempimento
    this.setupCodiceAdempimento();
    this.setVisibilityButtonBack(true);
    this.setVisibilityButtonNext(true);
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  // #region "SETUP COMPONENTE"

  /**
   * Funzione di setup che recupera la configurazione per il codice adempimento attivo.
   */
  protected setupCodiceAdempimento() {
    // Definisco il parametro per il recupero del dato dalla route
    const keyCodAdempi = 'codAdempimento';
    // Recupero l'informazione dal routing applicativo
    this.codAdempimento = this._route.snapshot.paramMap.get(keyCodAdempi);
  }

  // #endregion "SETUP COMPONENTE"

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  // #region "INIT COMPONENTE"

  /**
   * ngOnInit.
   */
  ngOnInit() {
    // Lancio l'init del componente
    this.initComponente();
  }

  /**
   * Funzione di init che imposta i dati per il componente.
   */
  protected initComponente() {
    // Lancio l'init per le informazioni dai servizi
    this.initServiceData();
    // Lancio la funzione di init di tutti i dati del componente mediante scarico da server
    this.initData();
  }

  /**
   * Funzione di init che gestisce il set delle informazioni tramite servizi applicativi.
   */
  protected initServiceData() {
    // Imposto il dato per la gestione delle maschere dell'helper
    this.helpService.setCodMaschera(this.codMaschera);
  }

  /**
   * Funzione di init che invoca tutti i servizi necessari per lo scarico delle informazioni del quadro.
   * La funzione gestirà anche il loader per poter caricare i dati, bloccando le interazioni utente.
   * @param codiceAdempimento string con il codice adempimento per il caricamento dei dati. Se non è definito, per default, verrà recuperato il valore definito a livello di componente.
   */
  protected initData(codiceAdempimento?: string) {
    // Avvio lo spinner di caricamento
    this.spinner.show();

    // Gestisco l'input
    codiceAdempimento = codiceAdempimento ?? this.codAdempimento;

    // Scarico i dati relativi all'adempimento
    this._adempimento
      .getAdempimentoByCode(codiceAdempimento)
      .pipe(
        switchMap((adempimento: Adempimento) => {
          // Scarico e setto tutte le informazioni collegate all'adempimento
          return this.initDataAdempimento$(adempimento);
          // #
        }),
        tap((response: IAdempimentoOperaLinkRes) => {
          // Inizializzo i dati per l'attore gestione istanza
          this.initAttoreGestioneIstanza();
          // #
        }),
        switchMap((response: IAdempimentoOperaLinkRes) => {
          // Lancio la funzione di init dati istanza
          return this.initDatiQuadroByIstanza$();
          // #
        })
      )
      .subscribe({
        next: (response: any) => {
          // Nascondo lo spinner di caricamento
          this.spinner.hide();
          // Lancio al funzione che esegue le logiche a seguito del caricamento dati
          this.onInitData();
          // #
        },
        error: (e: ScrivaServerError) => {
          // Nascondo lo spinner di caricamento
          this.spinner.hide();
          // Gestisco la segnalazione dell'errore
          this.onServiziError(e);
          // #
        },
      });
  }

  /**
   * Funzione invocata nel momento che il caricamento dati è stato complatato.
   */
  protected onInitData() {
    // Lancio la funzione di set dell'oggetto help per l'header
    this.initHeaderHelp();
    // Lancio la funzione per verificare se si è ritornati da geeco
    this.handleGeecoReturn();
  }

  /**
   * Funzione di init per l'help dell'header per i quadri delle opere.
   * Il quadro generico non ha nessun help specifico da caricare, ma i quadri che estendono il quadro delle opere possono integrare logiche spefiche.
   */
  protected initHeaderHelp() {
    // Recupero la chiave del quadro per la ricerca all'interno degli help
    const chiaveHelpHeader: string = this.OPERE_CONSTS.KEY_HELP_QUADRO_HEADER;
    // Recupero la lista degli help scaricati
    const listaHelp: Help[] = this.helpList ?? [];

    // Cerco all'interno della lista degli help l'oggetto per stessa chiave
    const help: Help = listaHelp.find((h: Help) => {
      // Estraggo dall'oggetto la chiave identificativa
      const chiaveHelp: string = h?.chiave_help ?? '';
      // Verifico se all'interno della chiave è presente il pezzo di chiave da ricercare
      return chiaveHelp.includes(chiaveHelpHeader);
      // #
    });

    // Assegno l'oggetto dell'help alla variabile del componente
    this.helpHeader = help;
    // #
  }

  /**
   * Funzione che gestisce specificamente la messaggistica se l'apertura del componente è dovuta da geeco.
   */
  protected handleGeecoReturn() {
    // Verifico se il flag di rientro da geeco è attivo
    if (this.geecoFlag) {
      // Definisco le informazioni per l'alert al rientro da geeco
      const code = ScrivaCodesMesseges.P1GIS;
      const target = this.GEECO_ALERT_CONTAINER;
      const fade = true;
      // Siamo rientrati da geeco, visualizzo un messaggio
      this._messageService.showMessage(code, target, fade);
      // #
    }
  }

  // #endregion "INIT COMPONENTE"

  // #region "INIT DATI ADEMPIMENTO"

  /**
   * Funzione che va definire le chiamate per il recupero dati collegati all'adempimento.
   * La funzione prevede internamente le logiche di set dei dati collegati nel componente, in maniera tale che oltre lo scarico, ci sia anche il set e sia necessario solo richiamare la funzione senza ulteriori logiche.
   * @param adempimento Adempimento con l'oggetto di riferimento per il set dei dati del componente.
   * @returns Observable<IAdempimentoOperaLinkRes> con la informazioni ritornate dalla richiesta dati.
   */
  protected initDataAdempimento$(
    adempimento: Adempimento
  ): Observable<IAdempimentoOperaLinkRes> {
    // Definisco le informazioni specifiche per lo scarico dei dati per adempimento
    // # id adempimento
    let idAdempimento: number;
    idAdempimento = adempimento?.id_adempimento;
    // # codice tipo adempimento
    let codTipoAdempimento: string;
    codTipoAdempimento = adempimento?.tipo_adempimento?.cod_tipo_adempimento;
    // # codice adempimento
    let codAdempimento: string;
    codAdempimento = adempimento?.cod_adempimento;
    // # codice quadro
    let codQuadro: string;
    codQuadro = this.codQuadro;
    // # tipo informazione adempimento
    let tipoInfoAdempimento: TipoInfoAdempimento;
    tipoInfoAdempimento = TipoInfoAdempimento.searchTipoOgg;
    // # codice composto per gli helper
    let helper: string;
    helper = `${this.componente}.${codTipoAdempimento}.${codAdempimento}.${codQuadro}`;

    // Definisco l'oggetto per le richieste dati
    const req: IAdempimentoOperaLinkReq = {
      province: this._location.getProvinceByAdempimento(idAdempimento),
      configsAdempimento: this._configurazioni.getConfigurazioniByInfoAndChiave(
        codAdempimento,
        tipoInfoAdempimento,
        codQuadro
      ),
      helpList: this.helpService.getHelpByChiave(helper),
    };

    // Ritorno l'insieme di richieste
    return forkJoin(req).pipe(
      tap((res: IAdempimentoOperaLinkRes) => {
        // Imposto i dati per l'adempimento
        this.adempimento = adempimento;
        this.province = res.province;
        this.helpList = res.helpList;
        this.initTipologieOpere(res.configsAdempimento, codQuadro);
        // #
      })
    );
  }

  /**
   * Funzione che inizializza le informazioni per le tipologie opere data le configurazioni adempimento.
   * @param configsAdempimento ConfigAdempimento[] con la lista di configurazioni scaricate.
   * @param codQuadro string con il codice del quadro per la gestione dei dati.
   */
  protected initTipologieOpere(
    configsAdempimento: ConfigAdempimento[],
    codQuadro: string
  ) {
    // Gestisco l'input con una clausola di inizializzazione
    configsAdempimento = configsAdempimento ?? [];

    // Estraggo dalle configurazioni tutti gli oggetti per il quadro specifico
    let configAdempimento: ConfigAdempimento;
    configAdempimento = configsAdempimento.find(
      (configAdempi: ConfigAdempimento) => {
        // Effettuo un match tra l'oggetto dell'array e il codice quadro
        return configAdempi.chiave === codQuadro;
      }
    );
    // Estraggo il valore specifico dalla configurazione trovata
    const valueConfigAdempi: string = configAdempimento?.valore ?? '';

    // Definisco localmente il valore estratto per la tipologia oggetto
    this.configTipologiaOggetto = valueConfigAdempi;
    // Lancio l'init per le tipologie opere
    this.tipologieOpere = this.tipologieOpereByConfig(valueConfigAdempi);
  }

  /**
   * Funzione di set dati che gestisce le tipologie opere dalla configurazione stringa dal DB.
   * La configurazione in input prevederà dei valori concatenati tramite carattere "|".
   * Per il set delle informazioni, si estrarranno i valori e imposteranno a livello componente.
   * @param configString string con le configurazioni da estrarre.
   * @returns string[] con la lista di tipologie opere mappata.
   */
  protected tipologieOpereByConfig(configString: string): string[] {
    // Richiamo e ritorno la funzione del servizio
    return this._opere.tipologieOpereByConfig(configString);
  }

  // #endregion "INIT DATI ADEMPIMENTO"

  // #region "INIT ATTORE GESTIONE"

  /**
   * Funzione che gestisce ed inizializza i dati per l'attore gestione dell'istanza.
   * @param codQuadro string con il codice quadro di riferimento per le verifiche sull'attore istanza. Se non definito, verrà recuperato il codice quadro impostato a livello di componente.
   */
  private initAttoreGestioneIstanza(codQuadro?: string) {
    // Verifico l'input
    codQuadro = codQuadro ?? this.codQuadro;
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    // Verifico qual è il valore impostato per il valore dell'attore gestione istanza
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      // Siamo in WRITE: chiamo il next del BehaviorSubject con l'azione ActionsOggettiSearchEnum.RESETENABLE per il form di ricerca oggetti
      this.actionsOggettiSearch$.next(ActionsOggettiSearchEnum.RESETENABLE);
      // #
    } else {
      // Non siamo in WRITE: chiamo il next del BehaviorSubject con l'azione ActionsOggettiSearchEnum.RESETDISABLE per il form di ricerca oggetti
      this.actionsOggettiSearch$.next(ActionsOggettiSearchEnum.RESETDISABLE);
      // #
    }
  }

  // #endregion "ATTORE GESTIONE"

  // #region "INIT DATI QUADRO/ISTANZA"

  /**
   * Funzione che definisce le logiche di scarico dati per il quadro collegato all'istanza e le informazioni ad esso connesse.
   * @param idIstanza number con l'id istanza per lo scarico dati. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   * @param idTemplateQuadro number con l'id template del quadro per lo scarico dati. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   * @returns Observable<any> con le informazioni ritornate dal flusso di gestione dei dati quadro ed istanza.
   */
  private initDatiQuadroByIstanza$(
    idIstanza?: number,
    idTemplateQuadro?: number
  ): Observable<any> {
    // Verifico l'input
    idIstanza = idIstanza ?? this.idIstanza;
    idTemplateQuadro = idTemplateQuadro ?? this.idTemplateQuadro;

    // Richiamo il servizio per lo scarico dati e gestisco il flusso
    return this.stepManagerService
      .getQuadroByIdIstanza(idIstanza, idTemplateQuadro)
      .pipe(
        tap((templateQuadro: TemplateQuadro) => {
          // Lancio la funzione di init per i dati di quadro e istanza
          this.initDatiQuadroEdIstanza(templateQuadro);
          // #
        }),
        switchMap((templateQuadro: TemplateQuadro) => {
          // Richiamo e ritorno le logiche per il caricamento dei dati derivati dell'istanza
          return this.initDatiIstanzaDerivati$(this.istanza);
          // #
        }),
        tap((response: IOperaInstanzaDerivatiRes) => {
          // Lancio la funzione di completo caricamento dei dati
          this.onInitComponentData();
        })
      );
  }

  /**
   * Funzione di init specifica che imposta i dati all'interno del componente a seguito dello scarico dati del quadro x istanza.
   * @param templateQuadro TemplateQuadro con l'insieme delle informazioni scaricate per i dati del quadro ed istanza.
   * @param codQuadro string con il codice quadro per l'assegnazione dati. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   * @param codTipoQuadro string con il codice tipo quadro per l'assegnazione. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   */
  protected initDatiQuadroEdIstanza(
    templateQuadro: TemplateQuadro,
    codQuadro?: string,
    codTipoQuadro?: string
  ) {
    // Verifico l'input
    codQuadro = codQuadro ?? this.codQuadro;
    codTipoQuadro = codTipoQuadro ?? this.codTipoQuadro;

    // ### ISTANZA - Effettuo l'assegnamento delle informazioni x istanza
    this.initDatiIstanzaByTemplateQuadro(
      templateQuadro,
      codQuadro,
      codTipoQuadro
    );
    // ### QUADRO - Effettuo l'assegnamento delle informazioni x il quadro
    this.initDatiQuadroByTemplateQuadro(templateQuadro);
  }

  /**
   * Funzione di init specifica che imposta i dati all'interno del componente a seguito dello scarico dati del quadro x istanza.
   * La funzione imposta i dati specifici con dovuti controlli per l'istanza.
   * @param templateQuadro TemplateQuadro con l'insieme delle informazioni scaricate per i dati del quadro ed istanza.
   * @param codQuadro string con il codice quadro per l'assegnazione dati. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   * @param codTipoQuadro string con il codice tipo quadro per l'assegnazione. Se non è definito, per default, verrà usato il dato definito a livello di componente.
   */
  protected initDatiIstanzaByTemplateQuadro(
    templateQuadro: TemplateQuadro,
    codQuadro?: string,
    codTipoQuadro?: string
  ) {
    // Verifico l'input
    codQuadro = codQuadro ?? this.codQuadro;
    codTipoQuadro = codTipoQuadro ?? this.codTipoQuadro;

    // Recupero dall'input le informazioni di cofigurazione
    const istanza: Istanza = templateQuadro?.istanza;

    // ### ISTANZA - Effettuo l'assegnamento delle informazioni x istanza
    this.istanza = istanza;
    // Si utilizzano logiche di parse JSON, uso un try catch
    try {
      // Effettuo il parse dei dati del json_data dell'istanza
      const jsonData: any = JSON.parse(istanza.json_data);
      // Recupero i dati per il quadro riepilogo
      this.qdr_riepilogo = jsonData?.QDR_RIEPILOGO;
      // Recupero la configurazione specifica del quadro (ex.: QDR_DER_CAPTAZIONE)
      this.configQuadro = jsonData?.QDR_CONFIG[codQuadro];
      // Per i dati tecnici, avremo codice tipo quadro diverso da cod quadro, tento di accedere "internamente"
      this.datiTecnici = jsonData[codTipoQuadro][codQuadro];

      // Verifico se esistono effettivamente i dati tecnici
      if (this.datiTecnici) {
        // Esistono dei dati tecnici, il salvataggio deve essere in modifica
        this.saveWithPut = true;
      }
      // #
    } catch (e) {
      // Loggo l'errore generato dal try catch
      const t = `Exception: initDatiIstanzaByTemplateQuadro [ISTANZA] - codQuadro: ${codQuadro} | codTipoQuadro: ${codTipoQuadro}`;
      const b = { templateQuadro, exception: e };
      this._logger.error(t, b);
      // #
    }
  }

  /**
   * Funzione di init specifica che imposta i dati all'interno del componente a seguito dello scarico dati del quadro x istanza.
   * La funzione imposta i dati specifici con dovuti controlli per il quadro.
   * @param templateQuadro TemplateQuadro con l'insieme delle informazioni scaricate per i dati del quadro ed istanza.
   */
  private initDatiQuadroByTemplateQuadro(templateQuadro: TemplateQuadro) {
    // Recupero dall'input le informazioni di cofigurazione
    const quadri: Quadro[] = templateQuadro?.template?.quadri ?? [];
    const quadroAttuale: Quadro = quadri[0];

    // ### QUADRO - Effettuo l'assegnamento delle informazioni x il quadro
    // Creo una copia dell'oggetto del quadro attuale estratto
    this.quadro = clone(quadroAttuale);
    // Si utilizzano logiche di parse JSON, uso un try catch
    try {
      // Estraggo la configurazione come stringa dai dati del quadro
      let jsonConfigQuadro: string;
      jsonConfigQuadro = JSON.parse(quadroAttuale.json_configura_quadro);
      // Riassegno all'oggetto del componente la proprietà di configurazione con un oggetto e non stringa
      this.quadro.json_configura_quadro = jsonConfigQuadro;
      // #
    } catch (e) {
      // Loggo l'errore generato dal try catch
      const t = `Exception: initDatiQuadroByTemplateQuadro [QUADRO]`;
      const b = { templateQuadro, exception: e };
      this._logger.error(t, b);
      // #
    }
  }

  // #endregion "INIT DATI QUADRO/ISTANZA"

  // #region "INIT DATI DERIVATI ISTANZA"

  /**
   * Funzione dedicata allo scarico dati relativi all'istanza e al relativo set delle informazioni connesse alle chiamate.
   * @param istanza Istanza con l'oggetto contenente i dati dell'istanza scaricata.
   * @returns Observable<IInstanzaDerivatiRes> con i dati ritornati dalla funzione.
   */
  private initDatiIstanzaDerivati$(
    istanza: Istanza
  ): Observable<IOperaInstanzaDerivatiRes> {
    // Recupero dall'input l'id istanza per effettuare le chiamata
    const idIstanza: number = istanza?.id_istanza;

    // Definisco l'oggetto con la chiamate da effettuare per il set dei dati
    const req: IOperaInstanzaDerivatiReq = {
      soggettiIstanza: this.initSoggettiIstanza$(idIstanza),
      opereIstanza: this.initOpereIstanza$(idIstanza),
      oggettiIstanza: this.initOggettiIstanza$(idIstanza),
    };

    // Lancio l'insieme delle chiamate
    return forkJoin(req);
  }

  /**
   * Funzione che scarica e setta i dati dei soggetto all'interno del componente.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<SoggettoIstanza[]> con la lista di elementi scaricati.
   */
  protected initSoggettiIstanza$(
    idIstanza: number
  ): Observable<SoggettoIstanza[]> {
    // Lancio la chiamata per il recupero dei soggetti istanza e vado a settare le informazioni nel componente
    return this._ambito.getSoggettiIstanzaByIstanza(idIstanza).pipe(
      tap((soggettiIstanza: SoggettoIstanza[]) => {
        // Estraggo dalla lista dei soggetti dell'istanza gli id e li assegno alla variabile del componente
        const idSoggettiIstanza: number[] = soggettiIstanza.map(
          (soggettoIstanza: SoggettoIstanza) => {
            // Ritorno l'id del soggetto dell'oggetto soggetto istanza
            return soggettoIstanza.soggetto.id_soggetto;
            // #
          }
        );
        // Assegno localmente la lista ottenuta
        this.idSoggettiList = idSoggettiIstanza;
        // #
      })
    );
  }

  /**
   * Funzione che scarica e setta i dati dei soggetti all'interno del componente.
   * @param soggettiIstanza SoggettoIstanza[] con la lista degli elementi scaricati.
   */
  protected onInitSoggettiIstanza(soggettiIstanza: SoggettoIstanza[]) {
    // Estraggo dalla lista dei soggetti dell'istanza gli id e li assegno alla variabile del componente
    const idSoggettiIstanza: number[] = soggettiIstanza.map(
      (soggettoIstanza: SoggettoIstanza) => {
        // Ritorno l'id del soggetto dell'oggetto soggetto istanza
        return soggettoIstanza.soggetto.id_soggetto;
        // #
      }
    );
    // Assegno localmente la lista ottenuta
    this.idSoggettiList = idSoggettiIstanza;
  }

  /**
   * Funzione che scarica e setta i dati delle opere all'interno del componente.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<Opera[]> con la lista di elementi scaricati.
   */
  protected initOpereIstanza$(idIstanza: number): Observable<Opera[]> {
    // Lancio la chiamata al servizio e scarico i dati
    return this._ambito.getOpereByIstanza(idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Gestisco l'errore e permetto il 404
        return this._opere.allowNotFoundArray(e);
        // #
      }),
      tap((opereIstanza: Opera[]) => {
        // Gestisco le informazioni tramite funzione
        this.onInitOpereIstanza(opereIstanza);
        // #
      })
    );
  }

  /**
   * Funzione di supporto invocata nel momento in cui i dati sono stati scaricati per il dato contestuale.
   * La funzione gestisce le logiche di assegnamento delle informazioni per i dati del componente.
   * @param opereIstanza Opera[] con la lista di elementi scaricati.
   */
  protected onInitOpereIstanza(opereIstanza: Opera[]) {
    // Filtro gli oggetti e assegno il risultato localmente
    this.opere = this.filterOpereOggettiIstanzaByQuadro(opereIstanza);
    // #
  }

  /**
   * Funzione che scarica e setta i dati degli oggetti istanza all'interno del componente.
   * Nel caso di gestione con override, si può utilizzare la definizione della funzione con un generic.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<OggettoIstanza[] | T[]> con la lista di elementi scaricati.
   */
  protected initOggettiIstanza$<T>(
    idIstanza: number
  ): Observable<OggettoIstanza[] | T[]> {
    // Lancio la chiamata al servizio e scarico i dati
    return this._ambito.getOggettiIstanzaByIstanza(idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Gestisco l'errore e permetto il 404
        return this._opere.allowNotFoundArray(e);
        // #
      }),
      tap((oggettiIstanza: OggettoIstanza[]) => {
        // Richiamo la funzione di gestione del risultato
        this.onInitOggettiIstanza(oggettiIstanza);
        // #
      })
    );
  }

  /**
   * Funzione di supporto invocata nel momento in cui i dati sono stati scaricati per il dato contestuale.
   * La funzione gestisce le logiche di assegnamento delle informazioni per i dati del componente.
   * @param oggettiIstanza OggettoIstanza[] con la lista di elementi scaricati.
   */
  protected onInitOggettiIstanza(oggettiIstanza: OggettoIstanza[]) {
    // Effettuo il filtro sulla lista degli oggetti istanza forzando la tipizzazione del risultato della funzione
    let oggIstQdr: OggettoIstanza[] = <OggettoIstanza[]>(
      this.filterOpereOggettiIstanzaByQuadro(oggettiIstanza)
    );
    // Assegno l'informazione alla lista
    this.oggettiIstanza = oggIstQdr;
  }

  /**
   * Funzione di filtro che effettua delle logiche di selezione per gli oggetti in input.
   * Partendo da una fonte di oggetti scaricati per istanza, si filtrano tutti quegli oggetti compatibili con le tipologie opere del componente.
   * @param opereOggettiIstanza Opera[] | OggettoIstanza[] con la lista di elementi da filtrare.
   * @returns Opera[] | OggettoIstanza[] con la lista di elementi filtrati.
   */
  protected filterOpereOggettiIstanzaByQuadro(
    opereOggettiIstanza: Opera[] | OggettoIstanza[]
  ): Opera[] | OggettoIstanza[] {
    // Richiamo e ritorno le logiche della funzione del servizio
    return this._opere.filterOpereOggettiIstanzaByQuadro(
      opereOggettiIstanza,
      this.tipologieOpere
    );
  }

  /**
   * Funzione di filtro che effettua delle logiche di selezione per gli oggetti in input.
   * Partendo da una fonte di oggetti scaricati per istanza, si filtrano tutti gli oggetti per il loro "ind_livello".
   * @param oggettiIstanza OggettoIstanza[] con la lista di elementi da filtrare.
   * @param livello number con l'indicazione di filtro per i dati.
   * @returns OggettoIstanza[] con la lista di elementi filtrati.
   */
  protected filterOggettiIstanzaByLivello(
    oggettiIstanza: OggettoIstanza[],
    livello: number
  ): OggettoIstanza[] {
    // Richiamo e ritorno le logiche della funzione del servizio
    return this._opere.filterOggettiIstanzaByLivello(oggettiIstanza, livello);
  }

  // #endregion "INIT DATI DERIVATI ISTANZA"

  // #region "CHIUSURA CARICAMENTO DATI"

  /**
   * Funzione invocata al termine delle logiche di caricamento dei dati.
   */
  protected onInitComponentData() {
    // Lancio la verifica dello step
    this.isStepValid().subscribe((check) => {
      // Imposto le informazioni per la gestione del coponente
      this.setStepCompletedEmit(this.componentName, check);
    });
  }

  // #endregion "CHIUSURA CARICAMENTO DATI"

  /**
   * ########################################
   * FUNZIONI DI AFTERVIEWINIT DEL COMPONENTE
   * ########################################
   */

  // #region "FUNZIONI AFTERVIEWINIT"

  /**
   * ngAfterViewInit.
   */
  ngAfterViewInit() {
    // Lancio il set della configurazione per le tabelle
    this.setTableRicercaOpere();
    this.setTableAssociazioneOpere();
  }

  /**
   * Funzione di set che va a definire la struttura della tabella per la ricerca delle opere.
   * @notes Gli oggetti gestiti da questa tabella sono: Opera.
   */
  protected setTableRicercaOpere() {
    // Definisco la configurazione per la tabella
    this.searchResultsColumns = [
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizeable: false,
        width: 55,
        headerTemplate: this.checkboxHeaderTemplate,
        cellTemplate: this.checkboxColTemplate,
        cellClass: 'checkbox-cell',
      },
      {
        name: 'Tipologia di opera',
        prop: 'tipologia_oggetto.des_tipologia_oggetto',
      },
      { name: 'Denominazione', prop: 'den_oggetto' },
      { name: 'Descrizione', prop: 'des_oggetto' },
      { name: 'Codice ROC', prop: 'cod_scriva' },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOperaTemplate,
        sortable: false,
      },
    ];
  }

  /**
   * Funzione di set che va a definire la struttura della tabella di associazione delle opere.
   * @notes Gli oggetti gestiti da questa tabella sono: OggettoIstanza.
   */
  protected setTableAssociazioneOpere() {
    // Definisco la configurazione per la tabella
    this.associazioniColumns = [
      { name: 'Denominazione', prop: 'den_oggetto' },
      { name: 'Descrizione', prop: 'des_oggetto' },
      { name: 'Codice ROC', cellTemplate: this.codiceScrivaOggIstTemplate },
      {
        name: 'Comune',
        cellTemplate: this.comuneProvinciaOggIstTemplate,
        sortable: false,
      },
      {
        name: 'Località',
        cellTemplate: this.localitaTemplate,
        sortable: false,
      },
      {
        name: 'Azioni',
        sortable: false,
        minWidth: 90,
        maxWidth: 120,
        resizeable: false,
        cellTemplate: this.azioniTemplate,
      },
    ];
  }

  // #endregion "FUNZIONI AFTERVIEWINIT"

  /**
   * ###################
   * FUNZIONI DI DESTROY
   * ###################
   */

  // #region "FUNZIONI DESTROY"

  /**
   * ngOnDestroy.
   */
  ngOnDestroy() {
    // Richiamo il destroy della classe padre
    super.ngOnDestroy();
  }

  // #endregion "FUNZIONI DESTROY"

  /**
   * #################################################
   * FUNZIONI COLLEGATE AL COMPONENTE DI RICERCA OPERE
   * #################################################
   */

  // #region "RICERCA OPERE"

  /**
   * Funzione collegata all'emitter del componente di ricerca delle opere.
   * @param opere Opera[] con la lista delle opere trovate dalla ricerca.
   */
  onElementiCercati(opere: Opera[]) {
    // Resetto le opere selezionate attualmente dall'utente
    this.selectedOpere = [];
    // Aggiorno la variabile che contiene la lista delle opere per la tabella
    this.searchResults = opere;
  }

  // #endregion "RICERCA OPERE"

  /**
   * ######################################################
   * FUNZIONI COLLEGATE ALL'INSERIMENTO DATI SU MAPPA GEECO
   * ######################################################
   */

  // #region "GEECO"

  /**
   * Funzione collegata al pulsante di inserimento punti geometria per geeco.
   */
  onInserisciSuMappa() {
    // Definisco le configurazioni specifiche per Geeco
    const geecoQuitUrlParams: IGeecoQuitUrlParams = {
      codQuadro: this.codQuadro,
    };
    // Creo l'oggetto completo per la generazione dell'url, mandando come quit_url_params il codice del quadro per il rientro nell'applicazione
    const geoGeeco: IGeoGeecoConfigs = {
      id_istanza: this.istanza.id_istanza,
      id_oggetto_istanza: [],
      chiave_config: `${this.codQuadro}.GEECO`,
      quit_url_params: this._geeco.createGeecoQuitUrlParams(geecoQuitUrlParams),
    };
    const attoreGestioneIstanza: string = this.attoreGestioneIstanza;
    const errorConfigs: IAlertConfig = {
      code: ScrivaCodesMesseges.E100,
      idDOM: this.OPERE_CONSTS.ALERT_TARGET_OPERA,
      autoFade: this.autoFade,
    };
    // Vado a generare le informazioni per l'apertura di geeco
    const configs: IGeecoOpen = {
      geoGeeco,
      attoreGestioneIstanza,
      errorConfigs,
    };

    // Faccio partire lo spinner di caricamento
    this.spinner.show();

    // Lancio la chiamata di apertura di geeco
    this._geeco.openGeeco(configs).subscribe({
      next: (connection: IGeoGeeco) => {
        // Chiudo partire lo spinner di caricamento
        this.spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Chiudo partire lo spinner di caricamento
        this.spinner.hide();
        // #
      },
    });
  }

  // #endregion "GEECO"

  /**
   * ###############################################
   * FUNZIONALITA' NON RIFATTORIZZATE DAL COMPONENTE
   * ###############################################
   */

  /**
   * Funzione agganciata all'evento di selezione delle opere per la tabella opere.
   * @param onSelectedRows ISNgxDTSelect<Opera[]> con la lista di righe selezionate dalla tabella.
   */
  selectOpere(onSelectedRows: ISNgxDTSelect<Opera[]>) {
    // Recupero dall'oggetto emesso le righe selezionate
    const { selected } = onSelectedRows || {};
    // Verifico le opere selezionate
    const opere = selected ?? [];

    // Recuperate le opere, assegno alla variabile del componente
    this.selectedOpere = opere;
  }

  /**
   * Funzione che gestisce l'apertura della modale con le informazioni di un'opera, tramite la tabella degli oggetti istanza.
   * @param oggettoIstanzaRow OggettoIstanza con i dati dell'oggetto istanza della riga della tabella.
   * @param onlyRead boolean con il flag che gestisce la modalità di sola lettura definita dalla tabella (azioni: modifica, dettaglio).
   * @param checkIdOggettoIstanza boolean che definisce se è necessario verificare l'id oggetto istanza dell'oggetto per poterne aprire il dettaglio.
   */
  showDetails(
    oggettoIstanzaRow: OggettoIstanza,
    onlyRead: boolean,
    checkIdOggettoIstanza: boolean
  ) {
    // Recupero l'id per l'oggetto istanza
    let existIdOggIst: boolean;
    existIdOggIst = oggettoIstanzaRow.id_oggetto_istanza != undefined;
    // Verifico il flag per l'oggetto istanza
    if (checkIdOggettoIstanza && !existIdOggIst) {
      // Blocco le logiche
      return;
    }

    // Dalla lista delle opere scaricate, recupero l'oggetto opera collegato all'oggetto istanza
    const opera = this.opere.find((operaElem: Opera) => {
      // Effettuo una comparazione tra id oggetto
      return operaElem.id_oggetto === oggettoIstanzaRow.id_oggetto;
      // #
    });
    // Recupero l'oggetto specifico dalla lista degli oggetti istanza scaricati
    const oggettoIstanza = this.oggettiIstanza.find((oggIstaElem) => {
      // Recupero gli id oggetti istanza per una comparazione
      const idOggIstElem = oggIstaElem.id_oggetto_istanza;
      const idOggIstRow = oggettoIstanzaRow.id_oggetto_istanza;
      // Effettuo una comparazione tra id
      return idOggIstElem === idOggIstRow;
      // #
    });

    // Verifico se esistono dati tecnici estratti dal json data
    if (this.datiTecnici) {
      // Richiamo la funzione specifica di estrazione dati tecnici per l'opera
      this.sourceDataForModal = this._opere.estraiDatiTecniciOpera(
        this.datiTecnici,
        opera,
        oggettoIstanzaRow
      );
      // #
    }

    // Definisco l'oggetto che conterrà i parametri da passare alla modale
    const modalConfig: IDTOperaModalParams = this.paramsModaleDatiTecnici(
      opera,
      oggettoIstanza,
      onlyRead
    );
    // Definisco l'oggetto con la callback per la modale ed i suoi eventi
    const modalCallbacks: IDTOperaModalCallbacks = this.datiTecniciCallbacks(
      opera,
      oggettoIstanza
    );

    // Richiamo la funzione del servizio
    this.apriModaleDatiTecnici(modalConfig, modalCallbacks);
  }

  /**
   * Funzione che genera le configurazioni con i parametri da passare alla modale dei dati tecnici.
   * @param opera Opera con il riferimento all'oggetto per la gestione dei dati tecnici.
   * @param oggettoIstanza OggettoIstanza con il riferimento all'oggetto per la gestione dei dati tecnici.
   * @param readOnly boolean con l'indicazione di attivare la gestione dei dati in sola lettura.
   */
  protected paramsModaleDatiTecnici(
    opera: Opera,
    oggettoIstanza: OggettoIstanza,
    readOnly: boolean
  ): IDTOperaModalParams {
    // Definisco l'oggetto per i parametri da passare alla modale
    const modalConfig: IDTOperaModalParams = {
      adempimento: this.adempimento,
      attoreGestioneIstanza: this.attoreGestioneIstanza,
      componente: this.componente,
      oggettoIstanza: oggettoIstanza,
      opera: opera,
      quadro: this.quadro,
      readOnly: readOnly,
      sourceData: this.sourceDataForModal,
    };

    // Ritorno la configurazione
    return modalConfig;
  }

  /**
   * Funzione che definisce le callback che verranno invocate al salvataggio o alla chiusura della modale dei dati tecnici.
   * Questa funzione è pensata per l'override, in maniera tale che qualunque classe estenda questo componente, può definire una propria logica di salvataggio.
   * Per default, questa funzione permette il salvataggio di tutto il json data prodotto dalla modale dei dati tecnici.
   * @param opera Opera con il riferimento all'oggetto Opera per la gestione dei dati tecnici.
   * @param oggettoIstanza OggettoIstanza con il riferimento all'oggetto Opera per la gestione dei dati tecnici.
   * @returns IDTOperaModalCallbacks con le callback per il savataggio dati.
   */
  protected datiTecniciCallbacks(
    opera: Opera,
    oggettoIstanza: OggettoIstanza
  ): IDTOperaModalCallbacks {
    // Definisco l'oggetto con le callback per il salvataggio
    const callbacks: IDTOperaModalCallbacks = {
      onModaleChiusa: (modalResponse?: IDTOperaSalvataggio) => {
        // Vado a resettare la variabile che contiene i dati tecnici per la modale
        this.sourceDataForModal = null;

        // Verifico se è stato ritornato qualche dato dalla modale
        if (modalResponse) {
          // Lancio la funzione di salvataggio dati tecnici
          this.saveDatiTecniciOpera(modalResponse, opera, oggettoIstanza);
          // #
        }
      },
    };

    // Ritorno le callback
    return callbacks;
  }

  /**
   * Funzione che gestisce l'apertura della modale per la gestione dei dati tecnici di un'opera ed imposta le logiche di callbacks per il salvataggio dati.
   * @param dataModal IDTOperaModalParams con i parametri da passare alla modale come configurazione dati.
   * @param callbacks IDTOperaModalCallbacks con le funzioni di callback utilizzate per il salvataggio dei dati tecnici opera.
   */
  apriModaleDatiTecnici(
    dataModal: IDTOperaModalParams,
    callbacks: IDTOperaModalCallbacks
  ) {
    // Istanzio l'oggetto per la gestione della modale
    const modalRef = this._modal.open(this.componenteModale, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'xl',
    });

    // Definisco i parametri da passare come informazioni alla modale
    modalRef.componentInstance.dataModal = dataModal;
    modalRef.componentInstance.callbacks = callbacks;

    // Gestisco il flusso logico per la chiusura della modale
    modalRef.result
      .then((modalResponse?: IDTOperaSalvataggio) => {
        // Se definita, richiamo la callback specifica di chiusura modale
        callbacks?.onModaleChiusa(modalResponse);
        // #
      })
      .catch();
  }

  /**
   * Funzione che gestisce le logiche per l'aggiornamento dei dati tecnici avvenuto all'interno della modale dei dati tecnici dell'opera.
   * @param datiTecniciDaSalvare IDTOperaSalvataggio con le informazioni relative al salvataggio dati tecnici dell'opera.
   * @param opera Opera con le informazioni dell'oggetto opera passato alla modale.
   * @param oggettoIstanza OggettoIstanza con i dati dell'oggetto istanza passato alla modale.
   */
  protected saveDatiTecniciOpera(
    datiTecniciDaSalvare: IDTOperaSalvataggio,
    opera: Opera,
    oggettoIstanza: OggettoIstanza
  ) {
    // Verifico l'input
    if (!datiTecniciDaSalvare) {
      // Manca la configurazione
      return;
    }

    // Lancio la funzione di aggiornamento dati di dettaglio
    this.updateDetails(opera, oggettoIstanza, datiTecniciDaSalvare);
    // #
  }

  /**
   * Funzione che effettua l'aggiornamento delle informazioni per i dati in input.
   * La funzione gestirà il set delle informazioni e poi avvierà il salvataggio dei dati tecnici.
   * @param opera Opera con l'oggetto identificativo per il salvataggio.
   * @param oggettoIstanza OggettoIstanza con l'oggetto identificativo per il salvataggio.
   * @param datiTecniciDaSalvare IDTOperaSalvataggio con le informazioni dei dati tecnici per il salvattaggio.
   */
  protected updateDetails(
    opera: Opera,
    oggettoIstanza: OggettoIstanza,
    datiTecniciDaSalvare: IDTOperaSalvataggio
  ) {
    // Estraggo dall'input le informazioni per il salvataggio
    let datiTecnici: any = datiTecniciDaSalvare?.datiTecnici;
    // Effettuo una sanitizzazione dei dati generati dal FormIo
    datiTecnici = this._formio.handleFormioSubmitForDB(datiTecnici);

    // Gestisco l'aggiornamento dei dati tecnici
    this.updateDatailDatiTecnici(opera, oggettoIstanza, datiTecnici);

    // Lancio il salvataggio dei dati del quadro
    this.salvaDatiQuadro();
    // Definisco lo step come completo
    this.onInitComponentData();
  }

  /**
   * Funzione che gestisce l'aggiornamento dei dati per i dati tecnici dell'opera/oggetto istanza.
   * @param opera Opera per la gestione dati.
   * @param oggettoIstanza OggettoIstanza per la gestione dati.
   * @param datiTecnici any con le informazioni dei dati tecnici aggiornati.
   */
  protected updateDatailDatiTecnici(
    opera: Opera,
    oggettoIstanza: OggettoIstanza,
    datiTecnici: any
  ) {
    // Verifico se i dati tecnici esistono
    if (!this.datiTecnici) {
      // Non esistono, inizializzo l'oggetto
      this.datiTecnici = {};
      // #
    }

    // Vado a comporre il nome della proprietà per la gestione dei dati tecnici
    const arrayName = this._opere.keyDatiTecniciOpera(this.PREFIX_JS_DT, opera);
    // Verifico se esiste la proprietà all'interno l'oggetto dei dati tecnici
    if (!this.datiTecnici[arrayName]) {
      // Non esiste, inizializzo la proprietà come array vuoto
      this.datiTecnici[arrayName] = [];
      // #
    }

    // Definisco un contenitore vuoto per le informazioni dei dati tecnici da gestire
    let dataToSave: any = {};
    // Inizializzo il dato con i dati completi per i dati tecnici
    dataToSave = datiTecnici;
    // Vado a sovrascrivere le informazioni per due specifiche proprietà "id_oggetto_istanza" e "id_oggetto"
    dataToSave.id_oggetto_istanza = oggettoIstanza.id_oggetto_istanza;
    dataToSave.id_oggetto = opera.id_oggetto;

    // Recupero la lista dei dati tecnici salvati su DB per questo quadro/dato tecnico
    const datiTecniciDB: any[] = this.datiTecnici[arrayName];
    // Verifico se all'interno dell'array dei dati tecnici esistono già le informazioni per questo oggetto istanza
    const dtIndex: number = datiTecniciDB.findIndex((dtDB: any) => {
      // Recupero le informazioni dall'oggetto iterato
      const idOggIstDB = dtDB.id_oggetto_istanza;
      const identificDB = dtDB.id_oggetto;
      // Recupero le informazioni dall'oggetto da salvare
      const idOggIstSave = dataToSave.id_oggetto_istanza;
      const identificSave = dataToSave.id_oggetto;
      // Verifico per id oggetto istanza e id_oggetto
      const sameIdOggIst = idOggIstDB === idOggIstSave;
      const sameIdentific = identificDB === identificSave;
      // Ritorno le condizioni
      return sameIdOggIst && sameIdentific;
    });

    // Controllo il risultato della ricerca
    if (dtIndex !== -1) {
      // Trovata corrispondenza, aggiorno il dato
      this.datiTecnici[arrayName][dtIndex] = dataToSave;
      // #
    } else {
      // Non esiste, vado a definire le informazioni per i dati tecnici per la specifica proprietà riferita alla tipologia oggetto
      this.datiTecnici[arrayName].push(dataToSave);
    }
  }

  /**
   * ######################################
   * FUNZIONE DI DELETE PER LE ASSOCIAZIONI
   * ######################################
   */

  /**
   * Funzione collegata al click del pulsante "elimina" per un oggetto istanza della tabella.
   * @param row OggettoIstanza contenete le informazioni della riga che si sta cercando di cancellare.
   */
  deleteOggettoIstanzaTable(row: OggettoIstanza) {
    // Definisco le informazioni per l'apertura dell'alert
    const title = 'Conferma eliminazione';
    const codMess = ScrivaCodesMesseges.A041;
    const btnAnnulla: ModalButton = {
      label: 'ANNULLA',
      type: 'btn-link',
      callback: () => {},
    };
    const btnConferma: ModalButton = {
      label: 'CONFERMA',
      type: 'btn-primary',
      callback: () => {
        // Conferma, lancio la delete di oggetto istanza
        this.deleteOggettoIstanza(row);
      },
    };

    // Richiamo l'apertura della modale con configurazione
    this._messageService.showConfirmation({
      title,
      codMess,
      buttons: [btnAnnulla, btnConferma],
    });
  }

  /**
   * Funzione che esegue la cancellazione dell'oggetto istanza passato alla funzione.
   * @param oggettoIstanza OggettoIstanza da cancellare.
   */
  protected deleteOggettoIstanza(oggettoIstanza: OggettoIstanza) {
    // Lancio lo spinner
    this.spinner.show();

    // Estraggo il gestUID dell'oggetto in input per la cancellazione
    const { gestUID } = oggettoIstanza || {};
    // Invoco la cancellazione dell'oggetto istanza
    this._ambito.eliminaOggettoIstanza(gestUID, false).subscribe({
      next: () => {
        // Terminata la chiamata alla delete, gestisco il flusso del componente
        this.onDeleteOggettoIstanza(oggettoIstanza);
        // Nascondo lo spinner
        this.spinner.hide();
        // #
      },
      error: (e: any) => {
        // Configurazione per la messaggistica
        const configs: IServiziErrorConfig = {
          e: e,
          target: this.OPERE_CONSTS.ALERT_TARGET_OPERA,
          defaultCode: ScrivaCodesMesseges.E100,
        };
        // Gestisco l'errore server
        this.onServiziErrorConfigs(configs);
        // Nascondo lo spinner
        this.spinner.hide();
      },
    });
  }

  /**
   * Funzione invocata nel momento che il servizio per la cancellazione dell'oggetto istanza ha terminato.
   * @param oggIstDel OggettoIstanza con l'oggetto che risulta essere stato cancellato da DB.
   */
  protected onDeleteOggettoIstanza(oggIstDel: OggettoIstanza) {
    // Aggiorno le strutture locali sulla base dell'oggetto eliminato
    this.aggiornaListeOpereAssociazioni(oggIstDel);
    // Aggiorno la struttura dei dati tecnici
    this.aggiornaDatiTecnici(oggIstDel);

    // Lancio le altre funzioni di aggiornamento dei dati
    this.salvaDatiQuadro(false);

    // Definisco le informazioni per la messaggistica
    const P002 = ScrivaCodesMesseges.P002;
    const target = 'operaContaier';
    this._messageService.showMessage(P002, target, this.autoFade);

    // Aggiorno le informazioni per lo stepper
    this.onInitComponentData();
  }

  /**
   * Funzione di supporto invocata a seguito della delete di un oggetto istanza.
   * La funzione aggiornerà le strutture locali di oggettiIstanza e oggetti/opere, per mantenere coerenza tra i dati.
   * @param oggIstDel OggettoIstanza rappresentate l'oggetto eliminato.
   */
  protected aggiornaListeOpereAssociazioni(oggIstDel: OggettoIstanza) {
    // Recupero dall'oggetto eliminato gli id per l'aggiornamento delle strutture del componente
    const idOggIstDel = oggIstDel?.id_oggetto_istanza;
    const idOggDel = oggIstDel?.id_oggetto;

    // Cerco all'interno degli array locali un match tra i dati
    const iOggIst = this.oggettiIstanza.findIndex((oi: OggettoIstanza) => {
      // Verifico per stesso id_oggetto_istanza
      return oi.id_oggetto_istanza === idOggIstDel;
    });
    const iOpera = this.opere.findIndex((o: Opera) => {
      // Verifico per stesso id_oggetto
      return o.id_oggetto === idOggDel;
    });

    // Verifico che gli id rappresentino la posizione degli oggetti trovati
    if (iOggIst > -1) {
      // Aggiorno la lista oggettiIstanza
      this.oggettiIstanza.splice(iOggIst, 1);
    }
    if (iOpera > -1) {
      // Aggiorno la lista opere/oggetti
      this.opere.splice(iOpera, 1);
    }
  }

  /**
   * Funzione di supporto invocata a seguito della delete di un oggetto istanza.
   * La funzione aggiornerà la struttura locale dei dati tecnici, per mantenere coerenza tra i dati.
   * @param oggIstDel OggettoIstanza rappresentate l'oggetto eliminato.
   */
  protected aggiornaDatiTecnici(oggIstDel: OggettoIstanza) {
    // Verifico se sono definiti all'interno del componente dei dati tecnici
    if (this.datiTecnici) {
      // Estraggo la lista delle proprietà dei dati tecnici
      const dtProperties: string[] = Object.keys(this.datiTecnici);

      // Itero la lista delle proprietà
      dtProperties.forEach((property: string) => {
        // Estraggo la lista di elementi assegnata alla chiave property
        const propertyElements = this.datiTecnici[property];
        // Verifico se all'interno degli elementi estratti, esiste un oggetto che ha in comune la proprietà id_oggetto_istanza
        const iElem = propertyElements?.findIndex((elem: any) => {
          // Verifico per stessa proprietà per il match tra elementi
          return elem?.id_oggetto_istanza === oggIstDel.id_oggetto_istanza;
        });

        // Verifico se è stato trovato un match tra dati
        if (iElem > -1) {
          // Aggiorno la struttura dati per i dati tecnici
          this.datiTecnici[property].splice(iElem, 1);
        }
      });
    }
  }

  /**
   * #######################################
   * FUNZIONI DI GESTIONE ASSOCIAZIONE OPERE
   * #######################################
   */

  /**
   * Funzione aggianciata al pulsante di "associa opere".
   * Vengono verificate le informazioni per le opere selezionate e si procede al salvataggio e gestione degli step successivi.
   */
  onAssocia() {
    // Verifico i valori per oggetti (opere) del componente
    if (!this.opere) {
      // Inizializzo e assegno oggetti con le opere selezionate dalla tabella opere
      this.opere = [];
      // Aggiungo e aggiorno l'array oggeti con le opere selezionate
      this.opere = this.opere.concat(this.selectedOpere);
      // #
    } else {
      // Definisco la proprietà dell'oggetto come discrimanate per la differenza tra array
      const property = 'id_oggetto';
      // Recupero i possibili oggetti "opera" dalla lista selectedOpere che NON si trovano già nella lista oggetti
      const newOpSel = differenceBy(this.selectedOpere, this.opere, property);

      // Se non esistono nuove opere selezionate rispetto all'array oggetti, genero errore
      if (newOpSel.length === 0) {
        // Definisco il codice del messaggio
        const E051 = ScrivaCodesMesseges.E051;
        // Visualizzo il messaggio d'errore
        this._messageService.showMessage(
          E051,
          'searchTableCard',
          this.autoFade
        );
        // Interrompo il flusso di associazione delle opere
        return;
      }

      // Ci sono nuove opere selezionate dalla tabella, le aggiungo alla lista oggetti
      this.opere = this.opere.concat(newOpSel);
      // #
    }

    // Lancio il salvataggio/associazione degli oggetti istanza
    this.gestioneOggettiIstanza(this.opere);
    // #
  }

  /**
   * Funzione che gestisce la lista ed il salvataggio degli oggetti istanza per le opere.
   * @param opere Opera[] che definisce la lista di oggetti come base dati di supporto per la compilazione della lista oggettiIstanza.
   */
  protected gestioneOggettiIstanza(opere: Opera[]) {
    // Recupero id oggetto istanza del padre
    const idOggettoIstanzaPadre: number =
      this.qdr_riepilogo.QDR_DATI_TECNICI.QDR_DER_DATIGEN.id_oggetto_istanza ||
      null;
    // Creo l'oggetto di configurazione per l'associazione oggetti istanza
    const configs: IAssociaOggettiIstanza = {
      istanza: this.istanza,
      oggettiIstanza: this.oggettiIstanza,
      opere,
      idOggettoIstanzaPadre: idOggettoIstanzaPadre,
    };
    // Creo una lista di oggettiIstanza per effettuare l'aggiornamento dati
    let oggettiIstanzaToSave: OggettoIstanza[];
    oggettiIstanzaToSave = this._opere.associaOggettiIstanza(configs);

    // Lancio il salvataggio effettivo degli oggetti istanza
    this.salvaOggettiIstanza(oggettiIstanzaToSave);
  }

  /**
   * Funzione che gestisce il salvataggio degli oggetti istanza da salvare su DB.
   * @param oggettiIstanza OggettoIstanza[] da salvare su DB.
   */
  protected salvaOggettiIstanza(oggettiIstanza: OggettoIstanza[]) {
    // Verifico l'input
    if (!oggettiIstanza || oggettiIstanza.length === 0) {
      // Non si salva niente
      return;
    }

    // Lancio lo spinner per il caricamento delle logiche
    this.spinner.show();

    // Definisco un oggetto dinamico che ha come property il valore id_oggetto di oggetto istanza e come valore l'oggetto effettivo oggetto istanza
    let request: Observable<OggettoIstanza | undefined>[] = [];
    // Itero la lista di oggetti istanza e definisco una specifica request per ogni elemento
    request = oggettiIstanza.map((oggettoIstanza: OggettoIstanza) => {
      // Aggiungo chiave/chiamata all'API alla request
      return this.salvaOggettoIstanzaWithCacth(oggettoIstanza);
      // #
    });

    // Lancio la request multipla tramite forkJoin
    forkJoin(request).subscribe({
      next: (oggIstSaved: (OggettoIstanza | undefined)[]) => {
        // Richiamo la funzione di gestione del risultato
        this.onSalvaOggettiIstanza(oggIstSaved);
        // Nascondo lo spinner
        this.spinner.hide();
        // #
      },
      error: (e: any) => {
        // Configurazione per la messaggistica
        const configs: IServiziErrorConfig = {
          e: e,
          target: 'searchTableCard',
          defaultCode: ScrivaCodesMesseges.E100,
        };
        // Gestisco l'errore server
        this.onServiziErrorConfigs(configs);
        // Nascondo lo spinner
        this.spinner.hide();
        // #
      },
    });
  }

  /**
   * Funzione di supporto che gestisce la chiamata al salvataggio dell'oggetto OggettoIstanza.
   * Questa funzione ha come funzionalità specifica quella di gestire i possibili errori sul singolo salvataggio.
   * Nel caso in cui si verifichi un qualunque errore, verrà intercettato e l'errore verrà gestito come segue:
   * - La lista, con scope del compoennte, oggetti verrà aggiornato rimuovendo l'oggetto tramite id_oggetto;
   * - Per evitare che possibili forkJoin falliscano completamente, l'errore verrà modificato ritornando lo specifico valore "undefined". Questo indica che la chiamata non è andata a buon fine per qualunque motivo;
   * @param oggettoIstanza OggettoIstanza da salvare.
   * @returns Observable<OggettoIstanza | undefined> che definisce il risultato del salvataggio. Se si è verificato un qualunque errore, il ritorno sarà: undefined.
   */
  protected salvaOggettoIstanzaWithCacth(
    oggettoIstanza: OggettoIstanza
  ): Observable<OggettoIstanza | undefined> {
    // Lancio il salvataggio dell'oggetto
    return this._ambito.salvaOggettoIstanza(oggettoIstanza).pipe(
      catchError((e: any) => {
        // Configurazione per la messaggistica
        const configs: IServiziErrorConfig = {
          e: e,
          target: 'searchTableCard',
          defaultCode: ScrivaCodesMesseges.E100,
        };
        // Gestisco l'errore server
        this.onServiziErrorConfigs(configs);

        // Aggiorno l'array del componente oggetti, rimuovendo l'oggetto istanza in errore
        const iOggRemove = this.opere.findIndex((o: Opera) => {
          // Verifico per stesso id_oggetto
          return o.id_oggetto === oggettoIstanza.id_oggetto;
          // #
        });
        // Verifico se esiste effettivamente l'oggetto nell'array
        if (iOggRemove > -1) {
          // Rimuovo l'oggetto dalla lista
          this.opere.splice(iOggRemove, 1);
        }

        // Ritorno al chiamate un observable undefined
        return of(undefined);
      })
    );
  }

  /**
   * Funzione di supporto che gestisce il flusso dati a seguito del salvataggio dati per gli oggetti istanza.
   * @param oggIstSaved (OggettoIstanza | undefined)[] con la lista dei risultati per il salvataggio.
   */
  protected onSalvaOggettiIstanza(oggIstSaved: (OggettoIstanza | undefined)[]) {
    // Dall'array di risposte bisogna verifica che almeno 1 chiamata sia andata a buon fine
    const saveOK: boolean = oggIstSaved.some((oggIst: OggettoIstanza) => {
      // Verifico se l'oggetto non è undefined
      return oggIst !== undefined;
    });

    // Se il salvataggio lo si può considerare OK, allora procedo con le logiche di fine salvataggio
    if (saveOK) {
      // Rimuovo dalla lista di oggetti salvati tutti quelli undefined
      let oggIstOK: OggettoIstanza[];
      oggIstOK = filter(oggIstSaved, (o: OggettoIstanza) => {
        // Mantengo l'oggetto se non è undefined
        return o !== undefined;
      });

      // Una volta recuperati gli oggetti salvati, aggiorno la lista locale
      this.oggettiIstanza = this.oggettiIstanza.concat(oggIstOK);
      // Lancio gli step di conclusione del salvataggio
      this.salvaDatiQuadro(false);
      this.onInitComponentData();

      // Visualizzo il messaggio di oggetti salvati
      const P014 = ScrivaCodesMesseges.P014;
      this._messageService.showMessage(P014, 'searchTableCard', this.autoFade);
      // #
    }
  }

  filterOggettiIstanzaByTipologia(codTipologia: string): OggettoIstanza[] {
    return this.oggettiIstanza.filter(
      (element) =>
        element.tipologia_oggetto.cod_tipologia_oggetto === codTipologia
    );
  }

  getTableTitle(codTipologia: string): string {
    const tipologia = this.oggettiIstanza.find(
      (elem) => elem.tipologia_oggetto.cod_tipologia_oggetto === codTipologia
    ).tipologia_oggetto;
    return tipologia.des_tipologia_oggetto;
  }

  /**
   * Funzione per creare body per il salvataggio per i dati del quadro e i dati del quadro riepilogo.
   * @param configs IPrepareDatiRiepilogo con l'oggetto contenente i parametri per la generazione dei dati per il savataggio dei quadri.
   * @returns RequestSaveBodyQuadro contenente l'oggetto per i salvataggio dati per il quadro delle opere.
   */
  private prepareDataQuadroERiepilogo(
    configs: IPrepareDatiRiepilogo
  ): RequestSaveBodyQuadro {
    // Recupero dall'input le informazioni dei dati tecnici
    const datiQuadro: any = configs?.datiQuadro;
    // Definisco le variabili di comodo rispetto alle variabili globali del componente
    const QUADRO: string = this.codQuadro;
    const TIPO_QUADRO: string = this.codTipoQuadro;
    // Preparo le informazioni per il salvataggio dei dati
    const dataQuadro: any = {};

    // Recupero i dati tecnici del formio/pannello della modale
    const datiTecniciQuadro: any = cloneDeep(datiQuadro);

    // Inserisco all'interno dell'oggetto per i dati del quadro le informazioni generate per i dati tecnici
    dataQuadro[QUADRO] = datiTecniciQuadro;

    // Verifico se all'interno delle informazioni del quadro riepilogo esistono informazioni per il tipo quadro
    if (!this.qdr_riepilogo[TIPO_QUADRO]) {
      // Non esistono ancora informazioni, genero un oggetto vuoto in preparazione
      this.qdr_riepilogo[TIPO_QUADRO] = {};
      // #
    }
    // Aggiorno le informazioni per la variabile globale del quadro riepilogo
    this.qdr_riepilogo[TIPO_QUADRO][QUADRO] = datiTecniciQuadro;

    // Creo l'oggetto di richiesta quadro
    const reqSaveQuadroConfigs: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro: dataQuadro,
      datiRiepilogo: this.qdr_riepilogo,
    };

    // Creo la richiesta e ritorno il servizio per il salvataggio
    return this.buildRequestDataToSaveQuadro(reqSaveQuadroConfigs);
    // #
  }

  /**
   * Funzione che gestisce le logiche per il salvataggio del json data dei dati tecnici.
   * @param segnalaRisultato boolean che definisce l'attivazione delle segnalazioni verso l'utente, per il salvataggio dati riuscito o fallito. Per default è: true.
   */
  salvaDatiQuadro(segnalaRisultato = true) {
    const configs: IConfigsSaveQuadro = {
      datiQuadro: this.datiTecnici,
    };

    this.saveDataQuadro(configs).subscribe(
      (res) => {
        this.saveWithPut = true;
        if (segnalaRisultato) {
          this._messageService.showMessage(
            'P001',
            'captazione-content',
            this.autoFade
          );
        }
      },
      (err) => {
        // Gestisco l'errore in entrata per il componente
        this.showErrorsQuadroConCodeENoCode(err, 'captazione-content');
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

    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      this.saveWithPut,
      true,
      true
    );
  }

  /**
   * Funzione che gestisce i dati per poter accedere alla pagina successiva.
   * Le logiche prevedono la verifica dei dati per le opere collegate all'istanza, verificando che le informazioni siano valide.
   * @override
   * @param segnalaRisultato boolean che definisce l'attivazione delle segnalazioni verso l'utente, per il salvataggio dati riuscito o fallito. Per default è: false.
   */
  protected isStepValid(segnalaRisultato = false): Observable<boolean> {
    // Recupero il flag di gestione per il flusso dalla classe di costante
    const gestioneDatiQuadro: ScrivaGestioneDatiQuadro =
      this.OPERE_CONSTS.GESTIONE_DATI_QUADRO;

    // Controllo il flag di gestione per il flusso
    switch (gestioneDatiQuadro) {
      case ScrivaGestioneDatiQuadro.standard:
        // Flusso standard
        return this.checkStepConOpereAssegnate(segnalaRisultato);
      // #
      case ScrivaGestioneDatiQuadro.quadroSenzaDettagli:
        // Flusso senza opere assegnate
        return of(this.checkStepSenzaOpereAssegnate(segnalaRisultato));
      // #
      default:
        // Flusso non gestito, ritorno sempre false
        const t: string = `opere.component.ts - checkStep`;
        const b: string = `this.OPERE_CONSTS.GESTIONE_DATI_QUADRO - configuration missing for current page, cannot handle verifiction no data`;
        this._logger.warning(t, b);
        // Blocco il flusso
        return of(false);
      // #
    }
  }

  /**
   * Funzione che gestisce i dati per poter accedere alla pagina successiva.
   * Le logiche prevedono la verifica dei dati per le opere collegate all'istanza, verificando che le informazioni siano valide.
   * La validità si basa sulla presenza dei dati tecnici che devono essere presenti per i vari livelli degli oggetti-istanza.
   * @param segnalaRisultato boolean che definisce l'attivazione delle segnalazioni verso l'utente, per il salvataggio dati riuscito o fallito. Per default è: false.
   */
  private checkStepConOpereAssegnate(
    segnalaRisultato = false
  ): Observable<boolean> {
    // Verifico e gestisco la logica e le segnalazioni per: l'utente ha associato almeno un OggettoIstanza all'Istanza
    const checkOggIstAssegnati: boolean =
      this.verificaSegnalaOggIstAssociati(segnalaRisultato);

    // Verifico e gestisco la logica e le segnalazioni per: gli OggettiIstanza associati all'Istanza hanno tutti dei dati tecnici associati
    const checkDatiTecnici: boolean =
      this.verificaTuttiOggettiIstanzaHannoDatiTecnici(segnalaRisultato);

    // Verifico e gestisco la logica e le segnalazioni per: tutte le informazioni dei dati tecnici hanno internamente le loro sezioni obbligatorie valorizzate
    const checkMandatoryFields: boolean =
      this.verificaPresenzaDatiTecniciObbligatori(segnalaRisultato);

    // Ritorno la validità di tutti i check dati
    return of(checkOggIstAssegnati && checkDatiTecnici && checkMandatoryFields);
  }

  /**
   * Funzione che gestisce i dati per poter accedere alla pagina successiva.
   * Questa funzione prevede una logica diversa rispetto al controllo formala generico. In generale non assegnare opere al quadro è un'operazione valida.
   * Questo rende la prima funzione di controllo bypassabile dal flusso di segnalazione.
   * Se però esistono delle opere, quindi la funzione restituisce [true], bisogna verificare anche tutte le altre condizioni di validità sui dati.
   * @param segnalaRisultato boolean che definisce l'attivazione delle segnalazioni verso l'utente, per il salvataggio dati riuscito o fallito. Per default è: false.
   */
  private checkStepSenzaOpereAssegnate(segnalaRisultato = false): boolean {
    // Verifico e gestisco la logica e le segnalazioni per: l'utente ha associato almeno un OggettoIstanza all'Istanza
    const checkOggIstAssegnati: boolean =
      this.verificaSegnalaOggIstAssociati(false);

    // Verifico subito la condizione sullo stato di assegnazione delle opere
    if (!checkOggIstAssegnati) {
      // Non ci sono opere assegnate al quadro, quindi salto tutti gli altri controlli e valido il quadro
      return true;
      // #
    } else {
      // # ELSE => CI SONO DELLE OPERE E DEVO CONTROLLARE LA VALIDAZIONE DEI DATI DI DETTAGLIO DI TUTTE LE SOTTO INFORMAZIONI
      // Verifico e gestisco la logica e le segnalazioni per: gli OggettiIstanza associati all'Istanza hanno tutti dei dati tecnici associati
      const checkDatiTecnici: boolean =
        this.verificaTuttiOggettiIstanzaHannoDatiTecnici(segnalaRisultato);

      // Verifico e gestisco la logica e le segnalazioni per: tutte le informazioni dei dati tecnici hanno internamente le loro sezioni obbligatorie valorizzate
      const checkMandatoryFields: boolean =
        this.verificaPresenzaDatiTecniciObbligatori(segnalaRisultato);

      // Ritorno la validità di tutti i check dati
      return checkOggIstAssegnati && checkDatiTecnici && checkMandatoryFields;
      // #
    }
  }

  /**
   * #######################################################
   * FUNZIONI DI VERIFICA PASSAGGIO AL PROSSIMO STEP ISTANZA
   * #######################################################
   */

  // #region "VERIFICA OGGETTI ISTANZA ASSOCIATI AD ISTANZA"

  /**
   * Funzione che gestisce i controlli per verificare se l'utente ha collegato degli OggettiIstanza all'istanza in lavorazione.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   */
  protected verificaSegnalaOggIstAssociati(segnalaRisultato: boolean) {
    // Recupero le informazioni del componente
    const oggettiIstanza: OggettoIstanza[] = this.oggettiIstanza;
    // Richiamo la verifica definita nel servizio e ritorno il risultato
    return this._opereVerificheStep.verificaSegnalaOggIstAssociati(
      oggettiIstanza,
      segnalaRisultato
    );
  }

  // #endregion "VERIFICA OGGETTI ISTANZA ASSOCIATI AD ISTANZA"

  // #region "VERIFICA TUTTI OGGETTI ISTANZA HANNO DATI TECNICI COLLEGATI"

  /**
   * Funzione che gestisce i controlli per verificare che ogni OggettoIstanza collegato all'istanza abbia collegato a se le informazioni dei dati tecnici.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   */
  protected verificaTuttiOggettiIstanzaHannoDatiTecnici(
    segnalaRisultato: boolean
  ): boolean {
    // Recupero le informazioni del componente
    const datiTecnici: IListeDatiTecniciOggettiIstanze = this.datiTecnici;
    const oggettiIstanza: OggettoIstanza[] = this.oggettiIstanza;
    const opere: Opera[] = this.opere;
    // Richiamo la verifica definita nel servizio e ritorno il risultato
    return this._opereVerificheStep.verificaTuttiOggettiIstanzaHannoDatiTecnici(
      datiTecnici,
      oggettiIstanza,
      opere,
      segnalaRisultato
    );
  }

  // #endregion "VERIFICA TUTTI OGGETTI ISTANZA HANNO DATI TECNICI COLLEGATI"

  // #region "VERIFICA SEZIONI OBBLIGATORIE DATI TECNICI SONO COMPILATI"

  /**
   * Funzione che gestisce i controlli per verificare che ogni sezione definita come obbligatoria dei dati tecnici sia effettivamente compilata.
   * @param segnalaRisultato boolean con un flag che definisce le logiche di segnalazione all'utente di eventuali errori.
   * @returns boolean con il risultato della verifica. [true] se la verifica è passata, [false] se ci sono errori.
   */
  protected verificaPresenzaDatiTecniciObbligatori(
    segnalaRisultato: boolean
  ): boolean {
    // Recupero le informazioni del componente
    const datiTecnici: IListeDatiTecniciOggettiIstanze = this.datiTecnici;
    const tipologieOpere: string[] = this.tipologieOpere;
    const oggettiIstanza: OggettoIstanza[] = this.oggettiIstanza;
    const opere: Opera[] = this.opere;
    const quadro: Quadro = this.quadro;
    // Richiamo la verifica definita nel servizio e ritorno il risultato
    return this._opereVerificheStep.verificaPresenzaDatiTecniciObbligatori(
      datiTecnici,
      tipologieOpere,
      oggettiIstanza,
      opere,
      quadro,
      segnalaRisultato
    );
  }

  // #endregion "VERIFICA SEZIONI OBBLIGATORIE DATI TECNICI SONO COMPILATI"

  /**
   * ###############################################
   * ALTRE FUNZIONI PER PASSARE ALLO STEP SUCCESSIVO
   * ###############################################
   */

  /**
   * @override
   * @returns
   */
  protected onAvanti() {
    if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
      this.isStepValid(true).subscribe((isValid: boolean) => {
        // Recupero il flag di gestione per il flusso dalla classe di costante
        const gestioneDatiQuadro: ScrivaGestioneDatiQuadro =
          this.OPERE_CONSTS.GESTIONE_DATI_QUADRO;

        // Controllo il flag di gestione per il flusso
        switch (gestioneDatiQuadro) {
          // Flusso standard
          case ScrivaGestioneDatiQuadro.standard:
            // Richiamo la funzione di flusso
            if (isValid) {
              this.goToStep(this.stepIndex);
            }
            break;
          // Flusso con salvataggio per quadro che può essere "vuoto"
          case ScrivaGestioneDatiQuadro.quadroSenzaDettagli:
            // Richiamo la funzione di flusso
            this.onAvantiFlussoSenzaDettagli(isValid);
            break;
          // #
        }
      });
    }
  }

  /**
   * Funzione per proseguire con gli step dell'istanza.
   * @author Ismaele Bottelli
   * @date 07/05/2025
   * @jira 1642 - L'utente deve associare un'opera, ma non è obbligato a compilare i dati di dettaglio (formio) dell'opera associata.
   * @notes Questa funzione specifica permette di salvare le informazioni del quadro per permettere all'utente di non inserire informazioni di dettaglio per le opere.
   * Sostanzialmente l'utente deve associare almeno un'opera al quadro, ma poi non deve compilare le informazioni di dettaglio.
   * Questa specifica logica è pensata per poter bypassare il concetto di compilazione dei quadri di DER che se non si compilano i dettagli delle opere, questi risultano come vuoti.
   * I quadri vuoti, non sono compilati su DB a livello di json data e creano problemi sull'istanza perché risultano come step non compilati dall'utente.
   * Bisogna quindi modificare la logica del check step (è una roba tendenzialmente custom per quadro di DER) e verificare la struttura del json data quadro e riepilogo.
   * Se i json data hanno la struttura dati del quadro specifico allora si può andare avanti direttamente, altrimenti bisogna salvare una struttura vuota, poi si può proseguire.
   */
  private onAvantiFlussoSenzaDettagli(isValidCheckStep: boolean) {
    // Se non passa la validazione blocco il flusso
    if (this.isAttoreGestioneIstanzaWRITE && !isValidCheckStep) {
      // Blocco il flusso per andare allo step successivo
      return;
    }
    // Lancio la verifica per controllare la presenza delle informazioni per il json data
    const isJsonStoricizzato: boolean = this.verificaJsonDataStoricizzato();
    // Verifico se esiste un jsondata storicizzato
    if (isJsonStoricizzato) {
      // Json data storicizzato, proseguo con lo step normalmente
      this.goToStep(this.stepIndex);
      // #
    } else {
      // Lancio la funzione di salvataggio dei dati vuoti
      this.salvaQuadroVuoto();
      // #
    }
  }

  /**
   * Funzione pensata per gestire le logiche di salvataggio del quadro "vuoto".
   * Bisogna salvare il quadro, ma impostando degli oggetti vuoti per poter comunque permettere a tutto il sistema di poter continuare a funzionare correttamente.
   */
  private salvaQuadroVuoto() {
    // Lancio le chiamate dati per il flusso di salvataggio dati
    this.spinner.show();

    /**
     * Si fa questo passaggio per poter tenere in piedi l'infrastruttura tecnica su cui si basa l'applicazione.
     * Il processo si basa sui json data e i json data riepilogo per cui basta che esistano dei dati, anche oggetti vuoti.
     * Una volta salvati i dati, lo stepper legge il fatto che per i quadri ci sono i dati, per cui considera il quadro effettivamente completato.
     * Bisogna ricorrere a questo metodo perché non è mai stato pensato che un quadro non potesse avere un json data associato, e quindi non sono stati pensate
     * altre variabili o flag per considerare un quadro in un certo stato.
     */
    // Genero un finto oggetto come se fosse stato creato dal dettaglio opera
    const dtVuoto: any = this.OPERE_CONSTS.datoTecnicoDataQuadro;
    // Creo l'oggetto da passare come parametro alla funzione
    const paramsDataQuadro: IConfigsSaveDataQuadro = { datiQuadro: dtVuoto };

    // Richiamo la funzione di save data quadro
    this.saveDataQuadro(paramsDataQuadro).subscribe({
      next: (response: IOpereSaveJsonDataRes) => {
        // Chiudo lo spinner di caricamento
        this.spinner.hide();
        // Richiamo la funzione per andare al prossimo step
        this.goToStep(this.stepIndex);
        // #
      },
      error: (e: ScrivaServerError) => {
        // Chiudo lo spinner di caricamento
        this.spinner.hide();
        // Richiamo la funzione di gestione della response
        this.onSaveDataQuadroError(e);
        // #
      },
    });
  }

  // #endregion "GESTIONE AVANZAMENTO STEP"

  /**
   * ####################################
   * FUNZIONI DI COMODO PER IL COMPONENTE
   * ####################################
   */
  /**
   * Funzione di gestione per il flusso di error per il salvataggio del json data del quadro e del riepilogo.
   * @param e ScrivaServerError con le informazioni di errore generate al salvataggio dati per i json data.
   */
  private onSaveDataQuadroError(e: ScrivaServerError) {
    // Recupero le informazioni comuni per la visualizzazione del messaggio
    const code: string = e?.error?.code ?? ScrivaCodesMesseges.E100;
    const target: string = this.OPERE_CONSTS.ALERT_TARGET_CAPTAZIONE;
    const autofade: boolean = this.autoFade;
    // Visualizzo il messaggio di segnalazione
    this._messageService.showMessage(code, target, autofade);
    // #
  }

  /**
   * Funzione di supporto che gestisce le segnalazioni da effettuare verso l'utente con target: opera.
   * @param code ScrivaCodesMesseges con il codice da visualizzare all'utente.
   * @param autoFade boolean con le indicazioni per la gestione della scomparsa automatica del messaggio.
   * @param placeholders IMsgPlacholder | IMsgPlacholder[] con la configurazione per i placeholder dentro il codice messaggio.
   */
  protected alertOpera(
    code: ScrivaCodesMesseges,
    autoFade?: boolean,
    placeholders?: IMsgPlacholder | IMsgPlacholder[]
  ) {
    // Definisco la configurazione per la gestione dell'errore
    code = code ?? ScrivaCodesMesseges.E100;
    autoFade = autoFade ?? this.autoFade;
    const target = this.OPERE_CONSTS.ALERT_TARGET_OPERA;

    // Visualizzo il messaggio d'errore
    this._messageService.showMessage(code, target, autoFade, placeholders);
  }

  /**
   * Funzione di supporto che gestisce gli errori generati dalla chiamata ai servizi.
   * @param e ScrivaServerError con l'oggetto d'errore generato dalla chiamata.
   */
  protected onServiziError(e?: ScrivaServerError, target?: string) {
    // Definisco la configurazione per la gestione dell'errore
    const errorConfigs: IServiziErrorConfig = {
      target: target ?? 'searchFormCard',
      autoFade: this.autoFade,
      defaultCode: ScrivaCodesMesseges.E100,
      e,
    };

    // Richiamo la funzione di gestione degli errori con configurazione
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
    const { e, defaultCode, target } = configs;

    // Estraggo il possibile codice d'errore dall'oggetto ritornato dal server
    let errCode = e?.error?.code;
    // Verifico se esiste il codice, altrimenti definisco un default
    errCode = errCode ?? defaultCode ?? ScrivaCodesMesseges.E100;
    // Visualizzo il messaggio d'errore
    this._messageService.showMessage(errCode, target, this.autoFade);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che recupera il codice della maschera, preceduto da un ".".
   * Il codice della maschera è usato principalmente per la generazione degli helper.
   * @returns string con il codice della maschera.
   */
  get codiceMaschera(): string {
    // Recupero dalla classe di help il codice dalla maschera
    const help: string = this.OPERE_CONSTS.CODICE_MASCHERA;
    // Creo il codice maschera
    return `.${help}`;
    // #
  }

  /**
   * Getter che recupera la condizioni di gestione per l'attore gestione istanza: WRITE
   * @returns boolean con il valore del check.
   */
  get isAttoreGestioneIstanzaWRITE(): boolean {
    // Ritorno la condazione
    return this.attoreGestioneIstanza === AttoreGestioneIstanzaEnum.WRITE;
  }

  /**
   * Getter che recupera la condizioni di gestione per l'attore gestione istanza: WRITE_LOCK
   * @returns boolean con il valore del check.
   */
  get isAttoreGestioneIstanzaWRITELOCK(): boolean {
    // Ritorno la condazione
    return this.attoreGestioneIstanza === AttoreGestioneIstanzaEnum.WRITE_LOCK;
  }

  /**
   * Getter che recupera le condizioni per la modifica di un oggetto istanza.
   * @returns boolean con il check per la condizione.
   */
  get checkModificaOggIst(): boolean {
    // Verifico le condizioni
    const isAGIWRITE = this.isAttoreGestioneIstanzaWRITE;
    const isAGIWRITELOCK = this.isAttoreGestioneIstanzaWRITELOCK;

    // Ritorno le condizioni
    return isAGIWRITE || isAGIWRITELOCK;
  }

  /**
   * Getter che recupera le condizioni per la visualizzazione dettaglio di un oggetto istanza.
   * @returns boolean con il check per la condizione.
   */
  get checkVisualizzaOggIst(): boolean {
    // Verifico le condizioni
    const isAGIWRITE = this.isAttoreGestioneIstanzaWRITE;
    const isAGIWRITELOCK = this.isAttoreGestioneIstanzaWRITELOCK;

    // Ritorno le condizioni
    return isAGIWRITE || isAGIWRITELOCK;
  }

  /**
   * Getter che recupera le condizioni per la cancellazione di un oggetto istanza.
   * @returns boolean con il check per la condizione.
   */
  get checkEliminaOggIst(): boolean {
    // Verifico le condizioni
    const isAGIWRITE = this.isAttoreGestioneIstanzaWRITE;
    const isAGIWRITELOCK = this.isAttoreGestioneIstanzaWRITELOCK;

    // Ritorno le condizioni
    return isAGIWRITE || isAGIWRITELOCK;
  }

  /**
   * Getter che recupera la lista degli oggetti-istanza scaricati per l'istanza.
   * @returns OggettoIstanza[] con le informazioni recuperate.
   */
  get oggettiIstanza(): OggettoIstanza[] {
    // Ritorno la lista degli oggetti istanza
    return this._oggettiIstanza;
    // #
  }

  /**
   * Setter che assegna una lista degli oggetti-istanza scaricati per l'istanza.
   * @params OggettoIstanza[] con le informazioni d'assegnare.
   */
  set oggettiIstanza(oggettiIstaza: OggettoIstanza[]) {
    // Ritorno la lista degli oggetti istanza
    this._oggettiIstanza = oggettiIstaza;
    // #
  }
}
