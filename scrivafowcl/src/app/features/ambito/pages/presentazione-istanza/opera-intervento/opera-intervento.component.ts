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
  Inject,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode, SelectionType } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import {
  BehaviorSubject,
  forkJoin,
  Observable,
  of,
  Subscription,
  throwError,
} from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';
import { ScrivaCodesMesseges } from 'src/app/core/enums/scriva-codes-messages.enums';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  CompetenzaTerritorio,
  Comune,
  DataQuadro,
  Istanza,
  ModalConfig,
  Provincia,
  StepConfig,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  LocationService,
  MessageService,
  StepManagerService,
  VincoliAutService,
} from 'src/app/shared/services';
import {
  IGeecoOpen,
  IGeecoQuitUrlParams,
  IGeoGeeco,
  IGeoGeecoConfigs,
} from 'src/app/shared/services/geeco/utilities/geeco.interfaces';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { CommonConsts } from '../../../../../shared/consts/common-consts.consts';
import {
  IAlertConfig,
  IServiziErrorConfig,
} from '../../../../../shared/interfaces/scriva-utilities.interfaces';
import { GeecoService } from '../../../../../shared/services/geeco/geeco.service';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { ActionsOggettiSearchEnum } from '../../../enums/actions-oggetti-search.enum';
import {
  ConfigElement,
  IInfoOggettiIstanzaQdr,
  IstanzaVincoloAut,
  OggettoIstanza,
  Opera,
  ProcedimentoCollegato,
  QdrOggettoConfig,
  SoggettoIstanza,
  TipologiaOggetto,
  UbicazioneOggetto,
  UbicazioneOggettoIstanza,
  VincoloAutorizza,
} from '../../../models';
import { AmbitoService } from '../../../services';
import { OperaService } from '../../../services/opera/opera.service';
import { ICheckACParams } from '../../../services/opera/utilities/opera.interfaces';
import { NumeroEnum, ObjectHelper } from '../../../utils';
import { OperaFormComponent } from './opera-form/opera-form.component';
import { GestioniDatiProgetto } from './opera-form/utilities/opera-form.enums';
import {
  IElementoDiscontinuita,
  IOperaFormPayload,
} from './opera-form/utilities/opera-form.interfaces';
import { OperaInterventoConsts } from './utilities/opera-intervento.consts';
import {
  CodiciVincoliAutorizza,
  TipiGeoriferimento,
} from './utilities/opera-intervento.enums';
import {
  IAdempimentoLinkReq,
  IAdempimentoLinkRes,
  IAggiornaQdrConfig,
  IInstanzaDerivatiReq,
  IInstanzaDerivatiRes,
  IMessaggiGeometriaReq,
  IMessaggiGeometriaRes,
} from './utilities/opera-intervento.interfaces';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';
import {
  IConfigsSaveDataQuadro,
  ISalvaJsonDataRes,
  RequestDataQuadro,
  RequestSaveBodyQuadro,
} from 'src/app/shared/components/stepper-istanza/utilities/stepper-istanza.interfaces';

@Component({
  selector: 'app-opera-intervento',
  templateUrl: './opera-intervento.component.html',
  styleUrls: ['./opera-intervento.component.scss'],
})
export class OperaInterventoComponent
  extends StepperIstanzaComponent
  implements OnInit
{
  /** CommonConsts con le costanti comuni all'applicazione. */
  private C_C = new CommonConsts();
  /** OperaInterventoConsts con le costanti per l'omonimo componente. */
  private OI_C = new OperaInterventoConsts();

  @ViewChild('checkboxHeaderTemplate') checkboxHeaderTemplate: TemplateRef<any>;
  @ViewChild('checkboxColTemplate') checkboxColTemplate: TemplateRef<any>;

  @ViewChild('comuneTemplate') comuneTemplate: TemplateRef<any>;
  @ViewChild('ubicazioneTemplate') ubicazioneTemplate: TemplateRef<any>;
  @ViewChild('praticheCollegateTemplate')
  praticheCollegateTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  codMaschera = '.MSCR005F';
  codContesto = '';

  codAdempimento: string;
  idAdempimento: number;

  qdr_config;
  valutazioneIncidenza;

  istanza: Istanza;
  idSoggettiList: number[] = [];

  gestioneEnum = AttoreGestioneIstanzaEnum;

  numerazione = NumeroEnum;

  opereSelection: string;
  comuneSingolo: boolean;
  geoMode: string;
  configOggetto: QdrOggettoConfig;
  jsElementiDiscontinuita;
  showViaconvinca;

  showGeoRefBtn = false;
  disableGeoRefBtn = true;
  fromGeoref = false;

  showOperaForm = false;
  disableBtn;

  province: Provincia[] = [];
  ricercaComuniList: Comune[] = [];
  tipologieOggetto: TipologiaOggetto[] = [];

  opereList: Opera[] = [];
  searchResultsColumns = [];
  associazioniColumns = [];
  ColumnMode = ColumnMode;
  tableSelect = SelectionType.checkbox;

  operaEdit: Opera | OggettoIstanza;
  selectedOpere: Opera[] = [];
  previousSelection: Opera;
  opereIstanza: Opera[] = [];
  operaAssociata: OggettoIstanza; // used for georef
  associazioniOggettoIstanza: OggettoIstanza[] = [];

  modalRef: NgbModalRef;
  modalEventSubscription: Subscription;

  saveWithPut = false;

  dataQuadro;

  // utili per la ricera oggetti
  // BehaviorSubject per azioni Oggetti Serch
  actionsOggettiSearch$: BehaviorSubject<ActionsOggettiSearchEnum> =
    new BehaviorSubject<ActionsOggettiSearchEnum>(null);
  // codice tipo oggetto che si legge da configurazione QDR_CONFIG.COD_TIPOLOGIA_OGGETTO
  configTipologiaOggetto: string;

  // Vincolo per vinca con scriva
  vincoloVNCS: Partial<IstanzaVincoloAut>;

  /** boolean che permette di gestire il flusso di gestione di checkAC al salvataggio del progetto. */
  segnalaCheckAC: boolean = false;

  /**
   * Costruttore.
   */
  constructor(
    private route: ActivatedRoute,
    private _ambito: AmbitoService,
    private adempimentoService: AdempimentoService,
    private locationService: LocationService,
    private _logger: LoggerService,
    private _geeco: GeecoService,
    private _opera: OperaService,
    private modalService: NgbModal,
    private vincoliService: VincoliAutService,
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
    this.codAdempimento = this.route.snapshot.paramMap.get('codAdempimento');
    this.setVisibilityButtonNext(false);
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * ngOnInit.
   */
  ngOnInit() {
    // Lancio la funzione di init del componente
    this.initComponente();
  }

  /**
   * Funzione di init che gestisce le logiche d'inizializzazione delle configurazioni del componente.
   */
  private initComponente() {
    // Lancio la funzione di init delle configurazioni iniziali
    this.initConfigurations();
    // Lancio la funzione di init di tutti i dati del componente mediante scarico da server
    this.initData();
  }

  /**
   * Funzione di init adibita all'iniziazione delle configurazioni del componente.
   * Le configurazioni prevedono il set interno delle variabili dall'injection paramaters.
   */
  private initConfigurations() {
    // Setto le altre informazioni del componente
    this.helpService.setCodMaschera(this.codMaschera);
  }

  /**
   * Funzione di init che invoca tutti i servizi necessari per lo scarico delle informazioni del quadro.
   * La funzione gestirà anche il loader per poter caricare i dati, bloccando le interazioni utente.
   * @param codiceAdempimento string con il codice adempimento per il caricamento dei dati. Se non è definito, per default, verrà recuperato il valore definito a livello di componente.
   */
  private initData(codiceAdempimento?: string) {
    // Avvio lo spinner di caricamento
    this.spinner.show();

    // Gestisco l'input
    codiceAdempimento = codiceAdempimento ?? this.codAdempimento;

    // Scarico i dati relativi all'adempimento
    this.adempimentoService
      .getAdempimentoByCode(codiceAdempimento)
      .pipe(
        switchMap((adempimento: Adempimento) => {
          // Scarico e setto tutte le informazioni collegate all'adempimento
          return this.initDataAdempimento$(adempimento);
          // #
        }),
        tap((response: IAdempimentoLinkRes) => {
          // Inizializzo i dati per l'attore gestione istanza
          this.initAttoreGestioneIstanza();
          // #
        }),
        switchMap((response: IAdempimentoLinkRes) => {
          // Lancio la funzione di init dati istanza
          return this.initDatiIstanza$();
          // #
        }),
        switchMap((response: any) => {
          // Lancio la funzione di init dati istanza
          return this.initSyncGeecoData$();
          // #
        })
      )
      .subscribe({
        next: (response: any) => {
          // Nascondo lo spinner di caricamento
          this.spinner.hide();
          // #
        },
        error: (e: ScrivaServerError) => {
          // Effettuo un log dell'errore, può capitare che sia roba FE
          const t =
            '[SCRIVA_FE] opera-intervento.component.ts - initData - exception catched';
          this._logger.warning(t, e);

          // Gestisco la segnalazione dell'errore
          this.onServiziError(e);
          // Nascondo lo spinner di caricamento
          this.spinner.hide();
          // #
        },
      });
  }

  // #region "DATI ADEMPIMENTO"

  /**
   * Funzione che va definire le chiamate per il recupero dati collegati all'adempimento.
   * La funzione prevede internamente le logiche di set dei dati collegati nel componente, in maniera tale che oltre lo scarico, ci sia anche il set e sia necessario solo richiamare la funzione senza ulteriori logiche.
   * @param adempimento Adempimento con l'oggetto di riferimento per il set dei dati del componente.
   * @returns Observable<IAdempimentoLinkRes> con la informazioni ritornate dalla richiesta dati.
   */
  private initDataAdempimento$(
    adempimento: Adempimento
  ): Observable<IAdempimentoLinkRes> {
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
    // # codice composto per gli helper
    let helper: string;
    helper = `${this.componente}.${codTipoAdempimento}.${codAdempimento}.${codQuadro}`;

    // Definisco l'oggetto per le richieste dati
    const req: IAdempimentoLinkReq = {
      province: this.locationService.getProvinceByAdempimento(idAdempimento),
      tipologieOggetto:
        this._ambito.getTipologieOggettoByAdempimento(codAdempimento),
      helpList: this.helpService.getHelpByChiave(helper),
    };

    // Ritorno l'insieme di richieste
    return forkJoin(req).pipe(
      tap((res: IAdempimentoLinkRes) => {
        // Imposto i dati per l'adempimento
        this.idAdempimento = adempimento?.id_adempimento;
        this.province = res.province;
        this.tipologieOggetto = res.tipologieOggetto;
        this.helpList = res.helpList;
        // #
      })
    );
  }

  // #endregion "DATI ADEMPIMENTO"

  // #region "ATTORE GESTIONE"

  /**
   * Funzione che gestisce ed inizializza i dati per l'attore gestione dell'istanza.
   */
  private initAttoreGestioneIstanza() {
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

  // #region "INIT DATI ISTANZA"

  /**
   * Funzione che definisce le logiche di scarico dati per l'istanza e le informazioni ad esso connessa.
   * La funzione è gestita in modalità stand alone, con lo spinner di caricamento
   * @param idIstanza number con l'id istanza per lo scarico dati. Se non è definito, per default, verrà usato l'id istanza definito a livello di componente.
   */
  private initDatiIstanza(idIstanza?: number) {
    // Lancio lo spinner
    this.spinner.show();
    // Lancio lo scarico dati per l'istanza
    this.initDatiIstanza$(idIstanza).subscribe({
      next: (response: any) => {
        // Nascondo lo spinner
        this.spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Effettuo un log dell'errore, può capitare che sia roba FE
        const t =
          '[SCRIVA_FE] opera-intervento.component.ts - initDatiIstanza - exception catched';
        this._logger.warning(t, e);
        // Gestisco la segnalazione d'errore

        this.onServiziError(e);
        // Nascondo lo spinner
        this.spinner.hide();
        // #
      },
    });
  }

  /**
   * Funzione che definisce le logiche di scarico dati per l'istanza e le informazioni ad esso connessa.
   * @param idIstanza number con l'id istanza per lo scarico dati. Se non è definito, per default, verrà usato l'id istanza definito a livello di componente.
   * @returns Observable<any> con le informazioni ritornate dal flusso di gestione dei dati istanza.
   */
  private initDatiIstanza$(idIstanza?: number): Observable<any> {
    // Verifico l'input
    idIstanza = idIstanza ?? this.idIstanza;

    // Effettuo tutte le chiamate e le sotto chiamate per scaricare tutti i dati dell'istanza e dei dati da essi derivati
    return this.istanzaService.getIstanzaById(idIstanza).pipe(
      tap((istanza: Istanza) => {
        // Lancio il set dei dati per l'istanza
        this.setDatiIstanza(istanza);
        // #
      }),
      switchMap((istanza: Istanza) => {
        // Richiamo l'insieme delle chiamate per la gestione dei dati derivati dall'instaza
        return this.initDatiIstanzaDerivati$(istanza);
        // #
      }),
      switchMap((response: IInstanzaDerivatiRes) => {
        // Lancio la funzione di init dai dati del quadro
        return this.initConfigurazioniQuadro$();
        // #
      }),
      switchMap((response: IInstanzaDerivatiRes) => {
        // Lancio la funzione di gestione delle geometrie
        return this.initDatiGeometrie$();
        // #
      }),
      tap((response: any) => {
        // Lancio la funzione per completare il flusso di set dei dati
        this.setStepCompletion();
        // #
      })
    );
  }

  /**
   * Funzione che definisce tutte le logiche per il set delle variabili del componente riferiti all'istanza.
   * @param istanza Istanza con l'oggetto scaricato da cui impostare i dati.
   */
  private setDatiIstanza(istanza: Istanza) {
    // Imposto a livello di componente l'oggetto dell'istanza
    this.istanza = istanza;

    // Gestisco le informazioni per la definizione del contesto
    let codTipoAdempimento: string;
    codTipoAdempimento =
      istanza?.adempimento?.tipo_adempimento?.cod_tipo_adempimento;
    let codAdempimento: string;
    codAdempimento = istanza?.adempimento?.cod_adempimento;
    // Imposto il valore per il codice del contesto
    this.codContesto = `.${codTipoAdempimento}.${codAdempimento}`;
    this.helpService.setCodContesto(this.codContesto);

    // Converto le informazioni per il json data dell'istanza
    const jsonData = JSON.parse(istanza?.json_data);
    this.qdr_config = jsonData.QDR_CONFIG;
    this.qdr_riepilogo = jsonData.QDR_RIEPILOGO;

    // Cerco di recuperare le informazioni dal json data, per la proprietà del codice quadro del componente
    const datiByCodTQ = jsonData[this.codTipoQuadro];
    // Verifico se esistono le informazioni
    if (datiByCodTQ) {
      // Esistono le informazioni per il codice tipo quadro, cerco di estrarre le possibili informazioni per gli elementi discontinuità
      let jsElemDiscByCodTQ: any;
      jsElemDiscByCodTQ = datiByCodTQ.JS_ELEMENTI_DISCONTINUITA;
      let jsElemDiscByCodTQAndQuadro: any;
      jsElemDiscByCodTQAndQuadro =
        datiByCodTQ[this.codQuadro]?.JS_ELEMENTI_DISCONTINUITA;

      // Vado ad impostare a livello di componente le informazioni per gli elementi di discontinuità
      this.jsElementiDiscontinuita =
        jsElemDiscByCodTQ ?? jsElemDiscByCodTQAndQuadro;
    }
  }

  /**
   * Funzione dedicata allo scarico dati relativi all'istanza e al relativo set delle informazioni connesse alle chiamate.
   * @param istanza Istanza con l'oggetto contenente i dati dell'istanza scaricata.
   * @returns Observable<IInstanzaDerivatiRes> con i dati ritornati dalla funzione.
   */
  private initDatiIstanzaDerivati$(
    istanza: Istanza
  ): Observable<IInstanzaDerivatiRes> {
    // Recupero dall'input l'id istanza per effettuare le chiamata
    const idIstanza: number = istanza?.id_istanza;

    // Definisco l'oggetto con la chiamate da effettuare per il set dei dati
    const req: IInstanzaDerivatiReq = {
      soggettiIstanza: this.initSoggettiIstanza$(idIstanza),
      opereIstanza: this.initOpereIstanza$(idIstanza),
      oggettiIstanza: this.initOggettiIstanza$(idIstanza),
      vincoliIstanza: this.initVincoliIstanza$(idIstanza),
    };

    // Lancio l'insieme delle chiamate
    return forkJoin(req);
  }

  // #endregion "INIT DATI ISTANZA"

  // #region "INIT DATI ISTANZA DERIVATI"

  /**
   * Funzione che scarica e setta i dati dei soggetto all'interno del componente.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<SoggettoIstanza[]> con la lista di elementi scaricati.
   */
  private initSoggettiIstanza$(
    idIstanza: number
  ): Observable<SoggettoIstanza[]> {
    return this._ambito.getSoggettiIstanzaByIstanza(idIstanza).pipe(
      tap((soggettiIstanza: SoggettoIstanza[]) => {
        // Se la risposta è valida, estraggo gli id e li assegno alla variabile del componente
        const idSoggettiIstanza: number[] = soggettiIstanza.map(
          (soggettoIstanza: SoggettoIstanza) =>
            soggettoIstanza.soggetto.id_soggetto
        );
        this.idSoggettiList = idSoggettiIstanza;
      }),
      catchError(() => {
        // SCRIVA-1548 fix errore nel quadro oggetto se non presente nessun soggetto
        // Se c'è un errore, restituisco un array vuoto e assicuro che la lista locale sia vuota
        this.idSoggettiList = [];
        return of([]);
      })
    );
  }

  /**
   * Funzione che scarica e setta i dati delle opere all'interno del componente.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<Opera[]> con la lista di elementi scaricati.
   */
  private initOpereIstanza$(idIstanza: number): Observable<Opera[]> {
    // Lancio la chiamata al servizio e scarico i dati
    return this._ambito.getOpereByIstanza(idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Verifico se non sono stati trovati elementi
        if (e.status == 404) {
          // Non blocco la chiamata, ma ritorno array vuoto
          return of([]);
        } else {
          // E' un altro tipo di errore, ritorno l'eccezione
          return throwError(e);
        }
      }),
      tap((opere: Opera[]) => {
        // Salvo localmente le informazioni
        this.opereIstanza = opere;
      })
    );
  }

  /**
   * Funzione che scarica e setta i dati degli oggetti istanza all'interno del componente.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<OggettoIstanza[]> con la lista di elementi scaricati.
   */
  private initOggettiIstanza$(idIstanza: number): Observable<OggettoIstanza[]> {
    // Lancio la chiamata al servizio e scarico i dati
    return this._ambito.getOggettiIstanzaByIstanza(idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Verifico se non sono stati trovati elementi
        if (e.status == 404) {
          // Non blocco la chiamata, ma ritorno null
          return of([]);
        } else {
          // E' un altro tipo di errore, ritorno l'eccezione
          return throwError(e);
        }
      }),
      tap((oggettiIstanza: OggettoIstanza[]) => {
        // Salvo localmente le informazioni
        this.associazioniOggettoIstanza = oggettiIstanza;
        if (this.associazioniOggettoIstanza.length>0){
          this.setVisibilityButtonNext(true);
        }
      })
    );
  }

  /**
   * Funzione che scarica e setta i dati vincoli istanza all'interno del componente.
   * @param idIstanza number con l'id istanza per lo scarico dati.
   * @returns Observable<IstanzaVincoloAut[]> con la lista di elementi scaricati.
   */
  private initVincoliIstanza$(
    idIstanza: number
  ): Observable<IstanzaVincoloAut[]> {
    // Lancio la chiamata al servizio e scarico i dati
    return this.vincoliService.getVincoliAutByIstanza(idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Verifico se non sono stati trovati elementi
        if (e.status == 404) {
          // Non blocco la chiamata, ma ritorno null
          return of([]);
          // #
        } else {
          // E' un altro tipo di errore, ritorno l'eccezione
          return throwError(e);
        }
      }),
      tap((vincoliIstanza: IstanzaVincoloAut[]) => {
        // Salvo le informazioni all'interno del servizio dedicato
        this._opera.setVincoli(vincoliIstanza);
      })
    );
  }

  // #endregion "INIT DATI ISTANZA DERIVATI"

  // #region "INIT DATI LEGATI AL QUADRO"

  /**
   * Funzione che gestisce l'inizializzazione dati per tutte le informazioni collegate al quadro configurazioni.
   * @param quadroConfigs any con le informazioni del quadro per la configurazione. Se non definito viene recuperata la configurazione dal valore del componente.
   * @returns Observable<any> con il risultato delle funzioni. Principalmente usato per sapere quando le funzioni asincrone sono completate.
   */
  private initConfigurazioniQuadro$(quadroConfigs?: any): Observable<any> {
    // Verifico l'input
    quadroConfigs = quadroConfigs ?? this.qdr_config;

    // Recupero le informazioni dall'oggetto quadro
    this.opereSelection = quadroConfigs.IndNumOggetto;
    this.comuneSingolo = quadroConfigs.IndNumComuniOggetto === 'S';
    this.geoMode = quadroConfigs.IndGeoMode;

    // Verifico il tipo di georeferenzazione attiva
    if (this.geoMode === TipiGeoriferimento.nonRichiesto_N) {
      // Non visualizzo il pulsante di georeferenzazione
      this.showGeoRefBtn = false;
      // #
    } else {
      // Visualizzo il pulsante di georeferenzazione
      this.showGeoRefBtn = true;
    }

    // Dichiaro una variabile che conterrà la richiesta di gestione dei dati
    let requestVincoli: Observable<any>;
    // Estraggo il flag di gestione di VIA con VINCA
    let VIAconVINCA: any = quadroConfigs.QDR_OGGETTO.via_con_vinca;
    // Verifico la tipologia della variabile e forzo la conversione a boolean
    if (typeof VIAconVINCA === 'string') {
      // E' una stringa, gestisco in maniera specifica la variabile
      VIAconVINCA = VIAconVINCA === 'true';
      // #
    }

    // Verifico se il quadro oggetto ha il flag di "via con vinca", come caso specifico
    if (VIAconVINCA) {
      // C'è bisogno di gestire l'iniziazione dei dati del vincolo via con vinca
      requestVincoli = this.initDatiVincoloVNCS$();
      // Il flag via con vinca è attivo
      this.showViaconvinca = true;
    } else {
      // Non c'è bisogno di logiche specifiche per i vincoli, la richiesta è vuota
      requestVincoli = of(null);
      // Il flag via con vinca è disabilitato
      this.showViaconvinca = false;
    }

    // Imposto nel servizio il flag di gestione della visualizzazione per via con vinca
    this._opera.setShowViaconvinca(this.showViaconvinca);
    // Setto localmente le configurazioni oggetto dal quadro di configurazione
    this.configOggetto = quadroConfigs[this.codTipoQuadro];

    // Recupero dalla configuirazione il codice tipologia oggetto
    const jsonDataParsed: any = JSON.parse(this.istanza.json_data);
    // Recupero la configurazione per la tipologia oggetto del componente
    this.configTipologiaOggetto =
      jsonDataParsed?.QDR_CONFIG.COD_TIPOLOGIA_OGGETTO;

    // Un po' di log
    this._logger.log('# Selection type: ', this.opereSelection);
    this._logger.log('# Comune singolo: ', this.comuneSingolo);
    this._logger.log('# Geo mode: ', this.geoMode);

    // Lancio la configurazione per le tabelle del componente
    this.configTables();
    // Lancio l'evento per la gestione del form di ricerca
    this.updateSearchForm();

    // Lancio le logiche di gestione dei vincoli e dei dati del quadro, ritornando la richiesta
    return requestVincoli;
  }

  /**
   * Funzione di configurazione per le tabelle del componente.
   */
  private configTables() {
    this.searchResultsColumns = [];
    this.associazioniColumns = [];

    const denomColumn = {
      name: 'Denominazione',
      minWidth: 160,
      prop: 'den_oggetto',
      cellClass: 'pippo-ellipsis',
    };
    const comuneColumn = {
      name: 'Comune',
      minWidth: 120,
      prop: 'ubicazioni[0].comune.denom_comune',
      // sortable: false,
      cellTemplate: this.comuneTemplate,
    };
    const ubicazioneColumn = {
      name: 'Ubicazione',
      minWidth: 160,
      sortable: false,
      cellTemplate: this.ubicazioneTemplate,
    };
    const praticheCollegateColumn = {
      name: 'Procedimenti collegati',
      minWidth: 280,
      sortable: false,
      cellTemplate: this.praticheCollegateTemplate,
    };
    const azioniColumn = {
      name: 'Azioni',
      sortable: false,
      minWidth: 90,
      maxWidth: 120,
      resizable: false,
      cellTemplate: this.azioniTemplate,
    };

    this.searchResultsColumns.push({
      name: '',
      sortable: false,
      canAutoResize: false,
      draggable: false,
      resizable: false,
      width: 40,
      headerTemplate: this.checkboxHeaderTemplate,
      cellTemplate: this.checkboxColTemplate,
      cellClass: 'checkbox-cell',
    });

    this.associazioniColumns.push(
      // todo: use the same as in searchResultsColumns once multiple georef is implemented
      {
        name: '',
        sortable: false,
        canAutoResize: false,
        draggable: false,
        resizable: false,
        width: 40,
        cellTemplate: this.checkboxColTemplate,
        cellClass: 'checkbox-cell',
      }
    );

    if (this.configOggetto.risultati_ricerca.den_oggetto.visibile) {
      this.searchResultsColumns.push(denomColumn);
      this.associazioniColumns.push(denomColumn);
    }
    if (this.configOggetto.risultati_ricerca.comune.visibile) {
      this.searchResultsColumns.push(comuneColumn);
      this.associazioniColumns.push(comuneColumn);
    }
    if (this.configOggetto.risultati_ricerca.ubicazione.visibile) {
      this.searchResultsColumns.push(ubicazioneColumn);
      this.associazioniColumns.push(ubicazioneColumn);
    }
    // if (!this.configOggetto.risultati_ricerca.pratiche_collegate.visibile) { // mock
    if (this.configOggetto.risultati_ricerca.pratiche_collegate.visibile) {
      this.searchResultsColumns.push(praticheCollegateColumn);
    }

    this.searchResultsColumns.push(azioniColumn);
    this.associazioniColumns.push(azioniColumn);

    this.searchResultsColumns = [...this.searchResultsColumns];
    this.associazioniColumns = [...this.associazioniColumns];
  }

  /**
   * Funzione che emette l'evento per la gestione delle azioni sugli oggetti.
   */
  private updateSearchForm() {
    // chiamo il next del BehaviorSubject con l'azione ActionsOggettiSearchEnum.UPDATEVALUEANDVALIDITY per il form di ricerca oggetti
    this.actionsOggettiSearch$.next(
      ActionsOggettiSearchEnum.UPDATEVALUEANDVALIDITY
    );
  }

  // #endregion "INIT DATI LEGATI AL QUADRO"

  // #region "INIT DATI VINCOLI VIA CON VINCA"

  /**
   * Funzione specifica che gestisce dati per i vincoli per via con vinca.
   * Oltre alla chiamata vengono eseguite le logiche di set delle informazioni locali.
   * @param idIstanza number con l'id dell'istanza per gestire i dati dei vincoli di via con vinca. Se non definito, viene recuperato il valore dell'id istanza presente a livello di componente.
   * @param idAdempimento number con l'id dell'adempimento per gestire i dati dei vincoli di via con vinca. Se non definito, viene recuperato il valore dell'id istanza presente a livello di componente.
   * @returns Observable<any> con la risposta ottenuta dai servizi.
   */
  private initDatiVincoloVNCS$(
    idIstanza?: number,
    idAdempimento?: number
  ): Observable<any> {
    // Verifico l'input
    idIstanza = this.idIstanza ?? this.idIstanza;
    idAdempimento = idAdempimento ?? this.idAdempimento;

    // Recupero i vincoli per istanza corrente
    return this.vincoliService.getVincoliAutByIstanza(idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Verifico se è stato generato un errore diverso dal 404 (not found)
        if (e?.status != 404) {
          // E' un errore da gestire

          return throwError(e);
          // #
        }

        // In caso dati non trovati, recupero i vincoli per adempimento
        return this.vincoliService.getVincoliAutByAdempimento(idAdempimento);
        // #
      }),
      switchMap((vincoli: IstanzaVincoloAut[] | VincoloAutorizza[]) => {
        // Gestisco il flusso con le verifiche sui vincoli x via con vinca
        return this.gestisciVincoliVNCS$(idAdempimento, vincoli);
        // #
      }),
      tap((vincoli: IstanzaVincoloAut[] | VincoloAutorizza[]) => {
        // Lancio la funzione di init dei dati per i vincoli e assegno il vincolo
        this.vincoloVNCS = this.initVincoliVNCS(idIstanza, vincoli);
        // #
      })
    );
  }

  /**
   * Funzione specifica che gestisce i controlli sui vincoli scaricati per istanza o adempimento.
   * Il flusso dati verrà gestito a seconda che i vincoli scaricati abbiano al loro interno un oggetto "via con vinca".
   * @param idAdempimento number con l'id dell'adempimento per la gestione del flusso.
   * @param vincoli IstanzaVincoloAut[] | VincoloAutorizza[] con l'array dei vincoli scaricati e da controllare.
   * @returns Observable<IstanzaVincoloAut[] | VincoloAutorizza[]> con la risposta a seguito della gestione del flusso.
   */
  private gestisciVincoliVNCS$(
    idAdempimento: number,
    vincoli: IstanzaVincoloAut[] | VincoloAutorizza[]
  ): Observable<IstanzaVincoloAut[] | VincoloAutorizza[]> {
    // Verifico se il vincolo è x l'istanza
    let isVincoloIst: boolean;
    isVincoloIst = this.checkVincoloIstanzaAdempimento(vincoli[0]);

    // Verifico se l'oggetto del vincolo è di un'istanza
    if (isVincoloIst) {
      // Effettuo un parse della response come lista di vincoli istanza
      let vincoliIst: IstanzaVincoloAut[];
      vincoliIst = <IstanzaVincoloAut[]>vincoli;
      // Cerco di estrarre dalla lista dei vincoli istanza, quella con il codice di via con vinca
      let isVincoloVNCS: boolean;
      isVincoloVNCS = vincoliIst.some((vincoloIst: IstanzaVincoloAut) => {
        // Verifico se il codice del vincolo autorizzazione è via con vinca
        return this.isVincoloIstanzaViaConVinca(vincoloIst);
        // #
      });

      // Verifico se è stato trovato effettivamente un oggetto
      if (!isVincoloVNCS) {
        // In caso di VNCS non presente per istanza corrente recupero i vincoli per adempimento
        return this.vincoliService.getVincoliAutByAdempimento(idAdempimento);
      }
    }

    // La lista di vincoli è quella di via con vinca valida
    return of(vincoli);
    // #
  }

  /**
   * Funzione che gestisce l'init delle informazioni per i vincoli via con vinca per l'applicazione.
   * @param idIstanza number con l'id istanza per la gestione dei dati dei vincoli.
   * @param vincoli IstanzaVincoloAut[] | VincoloAutorizza[] con l'array dei vincoli scaricati e da controllare.
   */
  private initVincoliVNCS(
    idIstanza: number,
    vincoli: IstanzaVincoloAut[] | VincoloAutorizza[]
  ) {
    // Verifico l'input
    if (!vincoli) {
      // Non esiste la lista, blocco il flusso
      return;
    }

    // Definisco il contenitore per i dati per l'oggetto del vincolo via con vinca
    let vinVNCS: Partial<IstanzaVincoloAut>;

    // Verifico se il vincolo è x l'istanza
    let isVincoloIst: boolean;
    isVincoloIst = this.checkVincoloIstanzaAdempimento(vincoli[0]);

    // Verifico se il vincolo è istanza, ma verificando che sia strettamente un boolean a true
    if (isVincoloIst) {
      // Effettuo un parse della response come lista di vincoli istanza
      let vincoliIst: IstanzaVincoloAut[];
      vincoliIst = <IstanzaVincoloAut[]>vincoli;

      // Cerco di estrarre dalla lista dei vincoli istanza, quella con il codice di via con vinca e lo assegno alla variabile locale
      vinVNCS = vincoliIst.find((vincoloIst: IstanzaVincoloAut) => {
        // Verifico se il codice del vincolo autorizzazione è via con vinca
        return this.isVincoloIstanzaViaConVinca(vincoloIst);
        // #
      });
      // #
    } else {
      // Effettuo un parse della response come lista di vincoli adempimento
      let vincoliAdempi: VincoloAutorizza[];
      vincoliAdempi = <VincoloAutorizza[]>vincoli;

      // Cerco di estrarre dalla lista dei vincoli adempimento, quella con il codice di via con vinca e lo assegno alla variabile locale
      let vincoloAutorizza: VincoloAutorizza = vincoliAdempi.find(
        (vincoloAdempi: VincoloAutorizza) => {
          // Verifico se il codice del vincolo autorizzazione è via con vinca
          return this.isVincoloAdempimentoViaConVinca(vincoloAdempi);
          // #
        }
      );

      // Il vincolo è dall'adempimento, compongo un oggetto vincolo istanza, ma con l'id_istanza forzato
      vinVNCS = {
        id_istanza: idIstanza,
        vincolo_autorizza: vincoloAutorizza,
      };
    }
    return vinVNCS;
  }

  /**
   * Funzione di controllo che verifica se l'oggetto in input è un vincolo istanza o un vincolo adempimento.
   * @param vincolo IstanzaVincoloAut | VincoloAutorizza da verificare.
   * @returns boolean con il risultato del controllo. Se true, l'oggetto è un vincolo istanza. Altrimenti un vincolo adempimento. Se undefined, vuol dire che l'input non è valido.
   */
  private checkVincoloIstanzaAdempimento(
    vincolo: IstanzaVincoloAut | VincoloAutorizza
  ): boolean {
    // Verifico l'input
    if (!vincolo) {
      // Input non valido
      return undefined;
    }

    // Effettuo un parse su "any" per gestire delle condizioni specifiche
    const vincoloAny: any = vincolo;
    // Verifico se l'oggetto del vincolo ha la prorpietà "id_istanza_vincolo_aut", quindi la risposta è di vincoli istanza
    return vincoloAny.id_istanza_vincolo_aut != undefined;
  }

  /**
   * Funzione di controllo che verifica se un oggetto IstanzaVincoloAut contiene il codice vincolo
   * @param vincoloIst IstanzaVincoloAut con l'oggetto e i dati da verificare.
   * @returns boolean con il risultato del check.
   */
  private isVincoloIstanzaViaConVinca(vincoloIst: IstanzaVincoloAut): boolean {
    // Cerco di estrarre il codice del vincolo autorizzazione
    let vincoloAut: VincoloAutorizza;
    vincoloAut = vincoloIst?.vincolo_autorizza;
    let codVincoloAut: string;
    codVincoloAut = vincoloAut?.cod_vincolo_autorizza;
    // Verifico se il codice del vincolo autorizzazione è via con vinca
    return codVincoloAut === CodiciVincoliAutorizza.viaConVinca;
  }

  /**
   * Funzione di controllo che verifica se un oggetto VincoloAutorizza contiene il codice vincolo
   * @param vincoloAdempi VincoloAutorizza con l'oggetto e i dati da verificare.
   * @returns boolean con il risultato del check.
   */
  private isVincoloAdempimentoViaConVinca(
    vincoloAdempi: VincoloAutorizza
  ): boolean {
    // Cerco di estrarre il codice del vincolo autorizzazione
    let codVincoloAut: string;
    codVincoloAut = vincoloAdempi?.cod_vincolo_autorizza;
    // Verifico se il codice del vincolo autorizzazione è via con vinca
    return codVincoloAut === CodiciVincoliAutorizza.viaConVinca;
  }

  // #endregion "INIT DATI VINCOLI VIA CON VINCA"

  // #region "INIT DATI GEOMETRIE"

  /**
   * Funzione che gestisce le logiche di inizializzazione dei dati per le geometrie.
   * Insieme allo scarico delle informazioni, la funzione prevede anche il set interno dei dati per il componente.
   * @param associazioniOggettoIstanza OggettoIstanza[] con la lista dati per gli oggetti istanza per la verifica. Se non è definito, verrà recuperato il dato dalla variabile del componente.
   * @returns Observable<any>[] con la risposta all'init completato dei dati.
   */
  private initDatiGeometrie$(
    associazioniOggettoIstanza?: OggettoIstanza[]
  ): Observable<any> {
    // Definisco il contenitore della richiesta per la gestione del flusso dati
    let request: Observable<any>;

    // Verifico l'input
    const oggettiIstanza: OggettoIstanza[] =
      associazioniOggettoIstanza ?? this.associazioniOggettoIstanza;
    // Verifico se gli oggetti istanza hanno dati
    const hasOIElements = oggettiIstanza?.length > 0;

    // Controllo il risultato del check
    if (hasOIElements) {
      // Imposto a true il flag per il salvataggio dati con le API di PUT
      this.saveWithPut = true;
      // Assegno la richiesta da ritornare con il caricamento delle geometrie
      request = this.initGeometrie$();
      // #
    } else {
      // Non è necessario gestire le geometrie, definisco una callback vuota
      request = of(null);
    }

    // Ritorno la richiesta
    return request;
  }

  /**
   * Funzione che gestisce le informazioni per le geometrie ed il set delle informazioni derivate da Geeco.
   * La funzione, oltre allo scarico, si occuperà anche del set dei dati all'interno del componente.
   * @param geometrie OggettoIstanza[] con la lista degli oggetti istanza per il set dati delle geometrie. Se non è definito in input, verrà recuperato il valore impostato nel componente.
   * @returns Observable<any[]> con il risultato della risposta e del set dati.
   */
  private initGeometrie$(geometrie?: OggettoIstanza[]): Observable<any[]> {
    // Verifico l'input
    geometrie = geometrie ?? this.associazioniOggettoIstanza;
    // Controllo se ci sono geometrie
    if (!geometrie || geometrie.length === 0) {
      // Non ci sono dati da iterare
      return of([]);
    }

    // Definisco una variabile che conterrà le chiamate per ritornare la logica
    let requestes: Observable<any>[] = [];

    // Rimappo la gestione delle geometrie come array di request
    requestes = geometrie.map((geometria: OggettoIstanza) => {
      // Lancio la funzione di gestione dei dati della singola geometria
      return this.initGeometria$(geometria, geometrie);
      // #
    });

    // Ritorno l'inseme delle richieste
    return forkJoin(requestes);
  }

  /**
   * Funzione che gestisce l'init dei dati specifici di una geometria.
   * La funzione scaricherà, gestirà e setterà le informazioni per la geometrie.
   * @param geometria OggettoIstanza con i dati della geometria da gestire.
   * @param geometrie OggettoIstanza[] con le informazioni delle geometrie per la gestione del set dei dati della singola geometria.
   * @returns Observable<any> con la risposta al termine delle logiche di gestione.
   */
  private initGeometria$(
    geometria: OggettoIstanza,
    geometrie: OggettoIstanza[]
  ): Observable<any> {
    // Verifico se la geometria risulta modificata per la georeferenzazione
    const modeGeo: boolean = geometria?.flg_geo_modificato;

    // Verifico il flag per la georeferenzazione
    if (modeGeo) {
      // Geometria georeferenziata, richiama la funzione di gestione
      return this.initGeometriaGeoreferenziata$(geometria, geometrie);
      // #
    } else {
      // Gestisco la geometria se questa non ha il flag di georeferenzazione modificato
      return this.initGeometriaGeecoGenerica$(geometria, this.geecoFlag);
      // #
    }
  }

  /**
   * Funzione che gestisce i dati della geometria georeferenziata.
   * La funzione gestirà il flusso e tutte le logiche di set dati per il componente.
   * La geometria è con flag flg_geo_modificato, per ogni sezione verifico i tab interni e gestire i dati specifici.
   * @param geometria OggettoIstanza con l'oggetto di riferimento per la chiamta.
   * @param geometrie OggettoIstanza[] con le informazioni delle geometrie per la gestione del set dei dati della singola geometria.
   */
  private initGeometriaGeoreferenziata$(
    geometria: OggettoIstanza,
    geometrie: OggettoIstanza[]
  ): Observable<any> {
    // Verifico l'input
    if (!geometria || !geometrie) {
      // Mancano le informazioni minime
      return of(null);
    }

    // Definisco delle variabili di comodo
    const idOggIstanza: number = geometria.id_oggetto_istanza;

    // Resetto i "consigli" per geeco
    this._opera.resetGeecoSuggest(idOggIstanza);
    // # NOTA DEL REFACTOR: questa istruzione viene lanciata per ogni geometria, ma l'oggetto è "globale" per il componente? Forse bisognerebbe setterla prima della lista?
    // Imposto configOggetto del componente nel servizio operaService, in particolare per avere le chiavi dei messaggi
    this._opera.setConfigOggetto(this.configOggetto);

    // Definisco l'insieme delle chiamate per i dati delle geometrie
    const geometriaReqs: IMessaggiGeometriaReq = {
      msgsSitiNatura2000: of([]),
      msgsAreeProtette: of([]),
      msgsDatiCatastali: of([]),
      msgsVincoli: of([]),
      msgsComuni: of([]),
      msgsGenerici: of([]),
    };

    // Verifico i flag della configurazione oggetto per determinare quali messaggi scaricare
    const dettaglioOggetto = this.configOggetto?.dettaglio_oggetto;
    const sitiReteNatura: ConfigElement = dettaglioOggetto?.siti_rete_natura;
    const areeProtette: ConfigElement = dettaglioOggetto?.aree_protette;
    const datiCatastali: ConfigElement = dettaglioOggetto?.dati_catastali;
    const vincoli: ConfigElement = dettaglioOggetto?.vincoli;

    // Verifico il flag "visibile" per le configurazioni e definisco la chiamata per i messaggi
    // In generale, "visibile" può essere: true, false o una stringa che identifica il componente applicativo. Nel nostro caso basta che non sia il booleano false o stringa vuota.
    if (sitiReteNatura?.visibile) {
      // Definisco la chiamata per i messaggi per la sezione specifica
      geometriaReqs.msgsSitiNatura2000 =
        this._opera.setGeecoOggettiIstanzaNatura2000GetMessage(
          idOggIstanza,
          geometrie
        );
      // #
    }
    if (areeProtette?.visibile) {
      // Definisco la chiamata per i messaggi per la sezione specifica
      geometriaReqs.msgsAreeProtette =
        this._opera.setGeecoOggettiIstanzaAreaProtetteGetMessage(
          idOggIstanza,
          geometrie
        );
      // #
    }
    if (datiCatastali?.visibile) {
      // Definisco la chiamata per i messaggi per la sezione specifica
      geometriaReqs.msgsDatiCatastali =
        this._opera.setGeecoOggettiIstanzaCatastoParticellaGetMessage(
          idOggIstanza,
          geometrie
        );
      // #
    }
    if (vincoli?.visibile) {
      // Definisco la chiamata per i messaggi per la sezione specifica
      geometriaReqs.msgsVincoli =
        this._opera.setGeecoOggettiIstanzaVincoliIdroGetMessage(
          idOggIstanza,
          geometrie
        );
      // #
    }
    geometriaReqs.msgsComuni = this._opera.getGeecoMessagesComuni(
      geometria,
      this.geecoFlag
    );
    geometriaReqs.msgsGenerici = this._opera.getGeecoMessagesGeneric(
      geometria,
      this.geecoFlag
    );

    // Effettuo la chiamata per lo scarico di tutti i messaggi
    return forkJoin(geometriaReqs).pipe(
      tap((res: IMessaggiGeometriaRes) => {
        // Richiamo la funzione di gestione dei messaggi
        this.messaggiGeometria(res);
        // #
      })
    );
  }

  /**
   * Funzione che gestisce una geometria che non risulta con il flag di georeferenzazione modificata.
   * La funzione effettuerà la chiamata e gestirà il flusso dati autonomamente.
   * @jira SCRIVA-1211.
   * @param geometria OggettoIstanza con l'oggetto di riferimento per la chiamta.
   * @param geecoFlag boolean con il flag che indica se il processo di gestione è attivo per Geeco. Se non definito, il valore verrà recuperato dal valore del componente.
   * @returns Observable<string[]> con la risposta alla gestione della geometria generica geeco.
   */
  private initGeometriaGeecoGenerica$(
    geometria: OggettoIstanza,
    geecoFlag?: boolean
  ): Observable<string[]> {
    // Gestisco l'input
    geecoFlag = geecoFlag ?? this.geecoFlag;

    // Richiamo il servizio passando le informazioni della geometria
    return this._opera.getGeecoMessagesGeneric(geometria, geecoFlag).pipe(
      tap((messages: string[]) => {
        // Verifico se è stato ritornato un messaggio
        const msg: string = messages ? messages[0] : undefined;
        // Verifico l'esistenza del messaggio
        if (msg != undefined) {
          // E' stato scaricato un messaggio, lo visualizzo
          this._messageService.showMessage(
            msg,
            this.OI_C.OPERA_CONTAINER,
            false
          );
          // #
        }
      })
    );
  }

  /**
   * Funzione che gestisce i messaggi per una geometria scaricata.
   * @param res IMessaggiGeometriaRes con i messaggi scaricati e da gestire.
   * @notes Il refactor di questa funzione è troppo rischiosa, sono state spostate le logiche e lasciate invariate.
   */
  private messaggiGeometria(res: IMessaggiGeometriaRes) {
    let all = [
      ...res.msgsSitiNatura2000,
      ...res.msgsAreeProtette,
      ...res.msgsDatiCatastali,
      ...res.msgsVincoli,
      ...res.msgsComuni,
    ];
    // Rimuovo i doppioni
    all = [...new Set(all)];
    console.log([...all]);
    // se non ci sono suggerimenti comuni o dei tab interni uso il messaggio generico
    if (all.length === 0) {
      all = [...res.msgsGenerici];
    }
    const indexR3 = all.indexOf(
      this._opera.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_3')
    );
    const indexR2 = all.indexOf(
      this._opera.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_2')
    );
    const indexR1 = all.indexOf(
      this._opera.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_1')
    );
    console.log('indexR3', indexR3);
    console.log('indexR2', indexR2);
    console.log('indexR1', indexR1);
    // priorita'
    // cod_messaggio_post_geeco_riepilogo_3, 37
    // cod_messaggio_post_geeco_riepilogo_2, 36
    // cod_messaggio_post_geeco_riepilogo_1, 35
    if (indexR3 > -1) {
      // se ho un 37 rimuovo altri due se presenti
      if (indexR1 > -1) {
        all.splice(indexR1, 1);
      }
      if (indexR2 > -1) {
        // indice è cambiato
        all.splice(
          all.indexOf(
            this._opera.getCodeMessageByKey(
              'cod_messaggio_post_geeco_riepilogo_2'
            )
          ),
          1
        );
      }
      console.log([...all]);
    } else if (indexR2 > -1 && indexR1 > -1) {
      // se ho 35+36 è un 37
      // cod_messaggio_post_geeco_riepilogo_1 +cod_messaggio_post_geeco_riepilogo_2 = cod_messaggio_post_geeco_riepilogo_3
      if (indexR1 > -1) {
        all.splice(indexR1, 1);
      }
      if (indexR2 > -1) {
        // indice è cambiato
        all.splice(
          all.indexOf(
            this._opera.getCodeMessageByKey(
              'cod_messaggio_post_geeco_riepilogo_2'
            )
          ),
          1
        );
      }
      all.push(
        this._opera.getCodeMessageByKey('cod_messaggio_post_geeco_riepilogo_3')
      );
      console.log([...all]);
    }
    console.log([...all]);
    // mostro i messaggi opportunamente
    all.forEach((codMess) => {
      this._messageService.showMessage(
        codMess,
        this.OI_C.OPERA_CONTAINER,
        false
      );
    });
  }

  // #endregion "INIT DATI GEOMETRIE"

  // #region "UPDATE GEECO DATA"

  /**
   * Funzione che gestisce le logiche di aggiornamento dati per le strutture di Geeco in caso si ritorni da Geeco.
   * @returns Observable<any> con il risultato della gestione dei dati per geeco.
   */
  private initSyncGeecoData$(): Observable<any> {
    // Recupero il flag per sapere se son tornato da Geeco
    const geecoFlag: boolean = this.geecoFlag;

    // Verifico il flag di geeco
    if (geecoFlag) {
      //setto la configurazione per il salvataggio
      const configs = this._buildConfigSave(false);
      // E' necessario aggiornare i dati per geeco
      return this.saveDataQuadro(configs);
      // #
    } else {
      // Non c'è da gestire il salvataggio, definisco una richiesta vuota
      return of(null);
      // #
    }
  }

  // #endregion "UPDATE GEECO DATA"

  /**
   * ###################
   * ALTRE FUNZIONALITA'
   * ###################
   */

  getHelpBannerText(chiave: string): string {
    const helpBanner = this.helpList?.find(
      (help) =>
        help.tipo_help.cod_tipo_help === 'BNR' &&
        help.chiave_help.includes(chiave)
    );
    return (
      helpBanner?.des_testo_help ||
      "Errore: il testo di questo paragrafo non è stato trovato. Contattare l'assistenza."
    );
  }

  search(opere: Opera[]) {
    this.opereList = opere;
  }

  selectOpere(event) {
    if (this.selectedOpere.length === 0) {
      return;
    }

    if (
      this.opereSelection === this.numerazione.SINGOLO ||
      this.opereSelection === this.numerazione.OPZIONALE
    ) {
      if (this.selectedOpere.length > 1) {
        const i = this.selectedOpere.findIndex(
          (item) => item.id_oggetto === this.previousSelection.id_oggetto
        );
        this.selectedOpere.splice(i, 1);
        this.selectedOpere = [...this.selectedOpere];
      }
      this.previousSelection = ObjectHelper.cloneObject(this.selectedOpere[0]);
    }
  }

  /**
   * Funzione che gestisce l'associazione dell'oggetto istanza alla pratica.
   * @returns void in caso di errore
   */
  onAssocia() {
    //mostro lo spinner e lo chiuderò al termine del salvataggio o in caso di errore.
    this.spinner.show();
    //resetto le variabili operaEdit, showOperaForm e imposta abilitato oggettiSearch.
    this.resetVariables();
    if (!this.opereIstanza) {
      this.opereIstanza = [];
      this.opereIstanza.push(...this.selectedOpere);
    } else {
      let addCount = 0;
      this.selectedOpere.forEach((opera) => {
        const check = this.opereIstanza.some(
          (op) => op.id_oggetto === opera.id_oggetto
        );
        if (!check) {
          this.opereIstanza.push(opera);
        } else {
          addCount++;
        }
      });
      if (addCount === this.selectedOpere.length) {
        this._messageService.showMessage('E051', 'searchTableCard', false);
        this.spinner.hide();
        return;
      }
    }
    this.disableGeoRefBtn = true;
    this.associaOggettiIstanza();
  }

  /**
   * Funzione che associa l'istanza all'oggetto istanza della selezione.
   */
  private associaOggettiIstanza() {
    if (this.associazioniOggettoIstanza.length === 0) {
      this.creaOggettiIstanzaDaAssociazioni(this.opereIstanza);
    } else {
      const toAdd: Opera[] = [];

      if (this.opereIstanza.length > 0) {
        this.opereIstanza.forEach((opera) => {
          const isPresent = this.associazioniOggettoIstanza.some(
            (associazione) => associazione.id_oggetto === opera.id_oggetto
          );
          if (!isPresent) {
            toAdd.push(opera);
          }
        });
      }

      if (toAdd.length > 0) {
        this.creaOggettiIstanzaDaAssociazioni(toAdd);
      }
    }
  }

  /**
   * Funzione che crea gli oggetti istanza a partire dalle associazioni.
   * La funzione verifica se l'oggetto istanza ha una pratica collegata e, in caso affermativo, scarica gli oggetti istanza associati alla pratica.
   * La funzione salva poi l'associazione dell'oggetto istanza.
   * @param list Opera[]
   */
  private creaOggettiIstanzaDaAssociazioni(list: Opera[]) {
    // Verifico se la lista è definita e contiene elementi
    if (list && list.length > 0) {
      // Itero la lista delle opere e per ogni opera verifico se ha una pratica collegata
      list.forEach((opera, index) => {
        if (opera.pratica_collegata && opera.pratica_collegata.length > 0) {
          opera.pratica_collegata.forEach((elem) => {
            //controllo se l'istanza collegata ha la valutazione incidenza/vincolo e ottengo gli oggetti istanza.
            this.checkVincoloFindOggetti(elem).subscribe(
              (oggettiIstanza: OggettoIstanza[]) => {
                //trovo oggetto istanza
                const oggettoSelezionato = oggettiIstanza.find(
                  (ogg) => ogg.id_oggetto === opera.id_oggetto
                );
                //se esiste l'oggetto lo mappo per il salvataggio
                if (oggettoSelezionato) {
                  const oggettoIstanzaMappato: OggettoIstanza =
                    this.mappaAssociazioneOggettoIstanza(oggettoSelezionato);
                  //procedo al salvataggio dell'associazione dell'oggetto istanza
                  this.salvaAssociazioneOggettoIstanza(
                    oggettoSelezionato,
                    oggettoIstanzaMappato,
                    index,
                    list
                  );
                } else {
                  this._logger.warning('Oggetto non trovato', '');
                }
              }
            );
          });
        }
      });
    }
  }

  /**
   * Funzione che verifica se l'istanza collegata ha la valutazione incidenza/vincolo e ottengo gli oggetti istanza.
   * @param elem ProcedimentoCollegato elemento della lista delle pratiche collegate.
   * @returns Observable<OggettoIstanza[]> con la lista degli oggetti istanza associati alla pratica collegata.
   */
  private checkVincoloFindOggetti(
    elem: ProcedimentoCollegato
  ): Observable<OggettoIstanza[]> {
    // Verifico se l'istanza collegata ha la valutazione incidenza/vincolo
    return this.vincoliService.getVincoliAutByIstanza(elem.id_istanza).pipe(
      catchError(this.handleError),
      switchMap((vincoliIst: IstanzaVincoloAut[]) => {
        const vinVNCS = this.initVincoliVNCS(elem.id_istanza, vincoliIst);

        if (vinVNCS && vinVNCS.vincolo_autorizza) {
          const tmpVincolo: Partial<IstanzaVincoloAut> = {
            id_istanza: this.idIstanza,
            vincolo_autorizza: { ...vinVNCS.vincolo_autorizza },
          };

          return this.vincoliService.postVincoliAut(tmpVincolo).pipe(
            switchMap((istanzaVincoloAut: IstanzaVincoloAut) => {
              this.vincoloVNCS = istanzaVincoloAut;
              return this._ambito
                .getOggettiIstanzaByIstanza(elem.id_istanza)
                .pipe(catchError(this.handleError));
            })
          );
        }

        return of([]); // Se non ci sono vincoli da salvare, ritorno un array vuoto
      })
    );
  }

  /**
   * Funzione che gestisce gli errori delle chiamate al server senza bloccare il flusso in caso di 404.
   * In caso di errore 404, ritorna un array vuoto, altrimenti l'eccezione.
   * @param e ScrivaServerError errore da gestire
   * @returns array vuoto oppure l'eccezione in caso di errore
   */
  private handleError(e: ScrivaServerError) {
    this.spinner.hide();
    if (e.status === 404) {
      return of([]); // Non blocco la chiamata, ma ritorno un array vuoto
    }
    return throwError(e); // Ritorno l'eccezione per altri tipi di errore
  }

  /**
   * Funzione che mappa l'oggetto istanza in modo da poterlo salvare
   * @param oggettoIstCercato OggettoIstanza da mappare
   * @returns OggettoIstanza mappato
   */
  private mappaAssociazioneOggettoIstanza(
    oggettoIstCercato: OggettoIstanza
  ): OggettoIstanza {
    const oggettoIstanza: OggettoIstanza = {
      id_oggetto: oggettoIstCercato.id_oggetto,
      id_istanza: this.idIstanza,
      tipologia_oggetto: oggettoIstCercato.tipologia_oggetto,
      cod_oggetto_fonte: oggettoIstCercato.cod_oggetto_fonte,
      den_oggetto: oggettoIstCercato.den_oggetto,
      des_oggetto: oggettoIstCercato.des_oggetto,
      coordinata_x: oggettoIstCercato.coordinata_x,
      coordinata_y: oggettoIstCercato.coordinata_y,
      id_masterdata_origine: oggettoIstCercato.id_masterdata_origine,
      id_masterdata: oggettoIstCercato.id_masterdata,
      // flg_principale: true,
      ind_livello: 1,
      ubicazione_oggetto: [],

      //SCRIVA-1616: aggiunta dei campi del dettaglio nell'associa.
      siti_natura_2000: [],
      aree_protette: [],
      vincoli: [],
      // quando salvo da scriva setto flg_geo_modificato a false
      flg_geo_modificato: false,
    };

    if (oggettoIstCercato.siti_natura_2000) {
      oggettoIstanza.siti_natura_2000 = oggettoIstCercato.siti_natura_2000;
    }
    if (oggettoIstCercato.aree_protette) {
      oggettoIstanza.aree_protette = oggettoIstCercato.aree_protette;
    }
    if (oggettoIstCercato.vincoli) {
      oggettoIstanza.vincoli = oggettoIstCercato.vincoli;
    }
    if (oggettoIstCercato.note_atto_precedente) {
      oggettoIstanza.note_atto_precedente =
        oggettoIstCercato.note_atto_precedente;
    }
    if (oggettoIstCercato.ubicazione_oggetto) {
      oggettoIstCercato.ubicazione_oggetto.forEach((ubicazione) => {
        oggettoIstanza.ubicazione_oggetto.push({
          comune: ubicazione.comune,
          den_indirizzo: ubicazione.den_indirizzo,
          num_civico: ubicazione.num_civico,
          des_localita: ubicazione.des_localita,
          ind_geo_provenienza: ubicazione.ind_geo_provenienza,
          dati_catastali: ubicazione.dati_catastali
            ? ubicazione.dati_catastali
            : [],
        });
      });
    }
    return oggettoIstanza;
  }

  /**
   * Funzione che salva l'associazione dell'oggetto istanza.
   * Setta il flag to_be_validated a true per i vincoli, le aree protette e i siti natura 2000, dati catastali.
   * Aggiunge l'oggetto istanza alla lista delle associazioni e aggiorna il quadro e lo step.
   * @param oggettoIst OggettoIstanza trovato partendo dall'istanza
   * @param oggettoIstanzaMappato OggettoIstanza mappato
   * @param index number indice dell'oggetto della riga
   * @param list lista selezionata di opere
   */
  private salvaAssociazioneOggettoIstanza(
    oggettoIst: OggettoIstanza,
    oggettoIstanzaMappato: OggettoIstanza,
    index: number,
    list: Opera[]
  ) {
    const infoAssociazioni = [];
    this._ambito.salvaOggettoIstanza(oggettoIstanzaMappato).subscribe(
      (res) => {
        res.siti_natura_2000?.forEach((element) => {
          element.to_be_validated = true;
        });
        res.aree_protette?.forEach((element) => {
          element.to_be_validated = true;
        });
        res.vincoli?.forEach((element) => {
          element.to_be_validated = true;
        });
        res.ubicazione_oggetto?.forEach((element) => {
          element.dati_catastali?.forEach((item) => {
            item.to_be_validated = true;
          });
        });

        this.associazioniOggettoIstanza.push(res);
        infoAssociazioni.push({
          idOggettoIstanza: oggettoIstanzaMappato.id_oggetto_istanza,
          flagNuovoOggetto: false,
        });
        if (index === list.length - 1) {
          this.updateQdrConfig(infoAssociazioni);
          this.setStepCompletion();
        }

        this._messageService.showMessage('P014', this.OI_C.OPERA_CONTAINER, false);
        this.spinner.hide();
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, this.OI_C.OPERA_CONTAINER);
        const i = this.opereIstanza.findIndex(
          (oggetto) => oggetto.id_oggetto === oggettoIst.id_oggetto
        );
        this.opereIstanza.splice(i, 1);
        this.spinner.hide();
      }
    );
  }

  getCallVNCSUpdate(valIncidenza: string) {
    let call = of(null);
    if (this.showViaconvinca) {
      if (valIncidenza === 'si' && !this.vincoloVNCS.gestUID) {
        call = this.vincoliService.postVincoliAut(this.vincoloVNCS).pipe(
          switchMap((istanzaVincoloAut: IstanzaVincoloAut) => {
            this.vincoloVNCS = istanzaVincoloAut;
            return of(null);
          })
        );
      } else if (valIncidenza === 'no' && this.vincoloVNCS.gestUID) {
        const tmpVincoloVNCS = {
          id_istanza: this.idIstanza,
          vincolo_autorizza: { ...this.vincoloVNCS.vincolo_autorizza },
        };
        call = this.vincoliService
          .deleteVincoloAut(this.vincoloVNCS.gestUID)
          .pipe(
            switchMap((istanzaVincoloAut: IstanzaVincoloAut) => {
              this.vincoloVNCS = tmpVincoloVNCS;
              return of(null);
            })
          );
      }
    }
    return call;
  }

  editOggettoIstanza(
    tableIndex: number,
    codScriva: string,
    valIncidenza: string
  ) {
    // Quando salvo da scriva setto flg_geo_modificato a false
    if (
      'flg_geo_modificato' in this.operaEdit &&
      this.operaEdit.flg_geo_modificato
    ) {
      this.operaEdit.flg_geo_modificato = false;
    }
    this.valutazioneIncidenza = valIncidenza;

    // Lancio lo spinner
    this.spinner.show();

    this.getCallVNCSUpdate(valIncidenza)
      .pipe(
        switchMap(() =>
          this._ambito
            .salvaOggettoIstanza(this.operaEdit as OggettoIstanza)
            .pipe(
              catchError((e: ScrivaServerError) => {
                // Gestisco in maniera specifica l'errore
                this.onEditOggettoIstanzaError(e);
                // Ritorno l'errore per la continuazione del flusso
                return throwError(e);
              })
            )
        ),
        switchMap((oggIstRes: OggettoIstanza) => {
          this.associazioniOggettoIstanza[tableIndex] = oggIstRes;
          this.associazioniOggettoIstanza = [
            ...this.associazioniOggettoIstanza,
          ];

          //il pulsante avanti è visibile dopo il primo inserimento
          if (this.associazioniOggettoIstanza.length>0){
            this.setVisibilityButtonNext(true);
          }

          let oggettoToEdit: Opera;
          let i: number;
          this.opereIstanza.forEach((oggetto, index) => {
            if (oggetto.id_oggetto === this.operaEdit.id_oggetto) {
              oggettoToEdit = oggetto;
              i = index;
            }
          });

          oggettoToEdit.tipologia_oggetto = this.operaEdit.tipologia_oggetto;
          oggettoToEdit.cod_oggetto_fonte = this.operaEdit.cod_oggetto_fonte;
          oggettoToEdit.den_oggetto = this.operaEdit.den_oggetto;
          oggettoToEdit.des_oggetto = this.operaEdit.des_oggetto;
          oggettoToEdit.coordinata_x = this.operaEdit.coordinata_x;
          oggettoToEdit.coordinata_y = this.operaEdit.coordinata_y;
          if (codScriva) {
            oggettoToEdit.cod_scriva = codScriva;
          }

          (<OggettoIstanza>this.operaEdit).ubicazione_oggetto.forEach(
            (ubicazione) => {
              const savedUbi = oggettoToEdit.ubicazione_oggetto.find(
                (item) =>
                  item.comune.cod_istat_comune ===
                  ubicazione.comune.cod_istat_comune
              );
              if (savedUbi) {
                savedUbi.den_indirizzo = ubicazione.den_indirizzo;
                savedUbi.des_localita = ubicazione.des_localita;
                savedUbi.num_civico = ubicazione.num_civico;
              } else {
                oggettoToEdit.ubicazione_oggetto.push({
                  id_oggetto: oggettoToEdit.id_oggetto,
                  comune: ubicazione.comune,
                  den_indirizzo: ubicazione.den_indirizzo,
                  des_localita: ubicazione.des_localita,
                  num_civico: ubicazione.num_civico,
                  ind_geo_provenienza: ubicazione.ind_geo_provenienza,
                });
              }
            }
          );

          for (
            let j = oggettoToEdit.ubicazione_oggetto.length - 1;
            j >= 0;
            j--
          ) {
            const isPresent = (<OggettoIstanza>(
              this.operaEdit
            )).ubicazione_oggetto.some(
              (item) =>
                item.comune.cod_istat_comune ===
                oggettoToEdit.ubicazione_oggetto[j].comune.cod_istat_comune
            );
            if (!isPresent) {
              oggettoToEdit.ubicazione_oggetto.splice(j, 1);
            }
          }

          if (
            this.istanza.stato_istanza.flg_aggiorna_oggetto ||
            (this.istanza.stato_istanza.id_stato_istanza === 10 &&
              oggettoToEdit.id_istanza_aggiornamento === this.idIstanza)
          ) {
            // Devo andare a salvare i dati dell'opera
            return this._ambito
              .salvaOpera(oggettoToEdit, this.codAdempimento)
              .pipe(
                tap((operaRes: Opera) => {
                  // Assegno all'opera istanza specifica l'oggetto ritornata dalla risposta
                  this.opereIstanza[i] = operaRes;
                }),
                switchMap((operaRes: Opera) => {
                  // Per il flusso devo ritornare l'oggetto istanza
                  return of(oggIstRes);
                  // #
                }),
                catchError((e: ScrivaServerError) => {
                  // Gestisco in maniera specifica l'errore
                  this.onEditOggettoIstanzaError(e);
                  // Ritorno l'errore per la continuazione del flusso
                  return throwError(e);
                })
              );
          } else {
            // Assegno all'opera istanza specifica l'oggetto da editare
            this.opereIstanza[i] = ObjectHelper.cloneObject(oggettoToEdit);
            // Procedo con l'esecuzione del flusso ritornando l'oggetto istanza
            return of(oggIstRes);
          }
          // #
        }),
        // Richiamo la funzione di verifica per le informazioni per le autorità competenti
        switchMap((oggettoIstanza: OggettoIstanza) => {
          // Imposto il target per gli errori
          const target: string = this.OI_C.OPERA_CONTAINER;
          // Richiamo la funzione la verifica e la gestione degli errori generati dal check sulle autorità competenti
          return this.verificaAutoritaCompetentiProgetto(target).pipe(
            // Per gestire il flusso devo però ritornare sempre l'oggetto-istanza salvato
            map((competenzeTerritorio: CompetenzaTerritorio[]) => {
              // Ritorno sempre l'oggetto istanza
              return oggettoIstanza;
              // #
            }),
            catchError((e: ScrivaServerError) => {
              // Ho un errore specifico per il check ac
              this.segnalaCheckAC = true;
              // Gestisco l'errore, ma le informazioni devono procedere, passo quindi l'oggetto-istanza
              return of(oggettoIstanza);
              // #
            })
          );
          // #
        })
      )
      .subscribe({
        next: (res: OggettoIstanza) => {
          // Chiudo lo spinner
          this.spinner.hide();
          // Gestisco il flusso dati
          this.onSalvataggioOggettoIstanzaCompletato(res);
          // #
        },
        error: (err) => {
          // Chiudo lo spinner
          this.spinner.hide();
          // #
        },
      });
  }

  /**
   * Funzione di supporto che gestisce la segnalazione degli errori per la funzione "editOggettoIstanza".
   * @param e ScrivaServerError con le informazioni sull'errore generato.
   */
  private onEditOggettoIstanzaError(e: ScrivaServerError) {
    // Definisco il targe per l'errore
    const target: string = this.OI_C.OPERA_FORM_MODAL_CONTAINER;
    //mostro errore
    this.showErrorsQuadroConCodeENoCode(e, target);
  }

  /**
   *
   * @param savedItem
   * @param flgSaveQuadro
   */
  onSalvataggioOggettoIstanzaCompletato(
    savedItem: OggettoIstanza,
    flgSaveQuadro = true
  ) {
    this.setStepCompletion();
    this.resetVariables();
    if (flgSaveQuadro) {
      this.salvaDatiQuadro();
    }
    this.modalRef?.close();
    this.modalRef = null;
    this.modalEventSubscription?.unsubscribe();

    // Gestisco la casistica specifica per quanto riguarda il checkAC: se ci sono errori sul check ac non visualizzo altre cose
    if (this.segnalaCheckAC) {
      // La gestione finale è conclusa, resetto il flag per le segnalazioni di errore del checkAC
      this.segnalaCheckAC = false;
      // #
    } else {
      // Non ci sono stati errori sul check AC, gestisco il normale flusso per georeferenzazione / messaggio di modifica dati avvenuta corretamente
      if (this.fromGeoref) {
        // L'utente ha richiesto l'apertura di geeco, resetto il flag di gestione
        this.fromGeoref = false;
        // Lancio la funzione per il redirect su geeco
        this.beforeGeeco(savedItem.id_oggetto_istanza);
        // #
      } else {
        this._messageService.showMessage(
          'P001',
          this.OI_C.OPERA_CONTAINER,
          true
        );
      }
      // #
    }
  }

  selectOperaAssociata(event) {
    // todo: to be refactored within multi-georef implementation
    this.operaAssociata = event.selected[0];
    if (this.operaAssociata) {
      this.disableGeoRefBtn = false;
    } else {
      this.disableGeoRefBtn = true;
    }
  }

  georeferisci(fromTable?: boolean) {
    if (fromTable) {
      // todo: to be refactored within multi-georef implementation
      const idOggettoIstanza = this.associazioniOggettoIstanza.find(
        (associazione) =>
          associazione.id_oggetto_istanza ===
          this.operaAssociata.id_oggetto_istanza
      ).id_oggetto_istanza;
      this.beforeGeeco(idOggettoIstanza);
    } else {
      const idOggettoIstanza = (<OggettoIstanza>this.operaEdit)
        .id_oggetto_istanza;
      this.beforeGeeco(idOggettoIstanza);
    }
  }

  /**
   * Funzione che imposta la logica di gestione prima di aprire l'applicazione di Geeco.
   * Nel flusso, viene verificata la validità per l'autorità competente tramite verifica: "check_ac".
   * @param idOggettoIstanza number con l'id dell'oggetto-istanza di riferimento per l'apertura di geeco.
   * @author Ismaele Bottelli
   * @date 15/01/2025
   * @jira SCRIVA-1539
   * @notes La collega ha segnalato che il flusso del check_ac non è stato implementato, permettendo l'utente di andare su Geeco anche se il check_ac potrebbe dare picche.
   *        Aggiungo la logica che verifichi prima di aprire Geeco.
   */
  beforeGeeco(idOggettoIstanza: number) {
    // Attivo lo spinner di caricamento
    this.spinner.show();

    // L'utente ha richiesto l'apertura di geeco, verifico l'ac dell'istanza
    this.verificaAutoritaCompetentiProgetto().subscribe({
      next: (compentezaTerritorio: CompetenzaTerritorio[]) => {
        // Chiudo lo spinner di caricamento
        this.spinner.hide();
        // La verifica dell'ac è andata a buon fine, proseguo
        this.confermaInformativaGeeco(idOggettoIstanza);
        // #
      },
      error: (e: ScrivaServerError) => {
        // Chiudo lo spinner di caricamento e basta, la segnalazione è all'interno della funzione
        this.spinner.hide();
      },
    });
  }

  /**
   * Funzione che recupera il messaggio per la comunicazione all'utente dell'informativa su Geeco.
   * @param idOggettoIstanza number con l'id dell'oggetto-istanza di riferimento per l'apertura di geeco.
   * @author Ismaele Bottelli
   * @date 15/01/2025
   * @jira SCRIVA-1539
   * @notes Rifattorizzo il codice e separo questa parte di flusso rispetto alle altre attività "pre-geeco".
   */
  private confermaInformativaGeeco(idOggettoIstanza: number) {
    const messaggio = this._messageService.messaggi.find(
      (mess) =>
        mess.cod_messaggio ===
        this.configOggetto.messaggi.cod_messaggio_pre_geeco
    );

    this._messageService.showConfirmation({
      title: 'INFORMATIVA',
      codMess: messaggio.cod_messaggio,
      buttons: [
        {
          label: 'ANNULLA',
          type: 'btn-link',
          callback: () => {},
        },
        {
          label: 'APRI',
          type: 'btn-primary',
          callback: () => {
            this.navigateToGeeco(idOggettoIstanza);
          },
        },
      ],
    });
  }

  /**
   * ######################################################
   * FUNZIONI COLLEGATE ALL'INSERIMENTO DATI SU MAPPA GEECO
   * ######################################################
   */

  /**
   * Funzione collegata al pulsante di inserimento punti geometria per geeco.
   * @param idOggettoIstanza number con l'id di riferimento all'oggetto per l'apertura di geeco.
   */
  navigateToGeeco(idOggettoIstanza: number) {
    // Definisco le configurazioni specifiche per Geeco
    const geecoQuitUrlParams: IGeecoQuitUrlParams = {
      codQuadro: this.codQuadro,
    };
    const geoGeeco: IGeoGeecoConfigs = {
      id_istanza: this.istanza.id_istanza,
      id_oggetto_istanza: [idOggettoIstanza],
      chiave_config: '',
      quit_url_params: this._geeco.createGeecoQuitUrlParams(geecoQuitUrlParams),
    };
    const attoreGestioneIstanza: string = this.attoreGestioneIstanza;
    const errorConfigs: IAlertConfig = {
      code: ScrivaCodesMesseges.E100,
      idDOM: this.OI_C.OPERA_CONTAINER,
      autoFade: true,
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

  /**
   * #########################################
   */

  showDetails(record: any, onlyRead: boolean, operaAssociataFlag: boolean) {
    const sourceArray = operaAssociataFlag
      ? this.associazioniOggettoIstanza
      : this.opereList;
    const tableIndex = sourceArray.findIndex(
      (item) => item.id_oggetto === record.id_oggetto
    );

    this.operaEdit = ObjectHelper.cloneObject(
      operaAssociataFlag
        ? this.associazioniOggettoIstanza[tableIndex]
        : this.opereList[tableIndex]
    );
    this.disableBtn = onlyRead;

    this.modalRef = this.modalService.open(OperaFormComponent, {
      centered: true,
      scrollable: true,
      windowClass: 'scriva-dettaglio-progetto',
      backdrop: 'static',
      size: 'xl',
    });

    this.modalRef.componentInstance.loadAsModal = true;
    this.modalRef.componentInstance.componente = this.componente;
    this.modalRef.componentInstance.attoreGestioneIstanza =
      this.attoreGestioneIstanza;
    this.modalRef.componentInstance.idAdempimento = this.idAdempimento;
    this.modalRef.componentInstance.configOggetto = this.configOggetto;
    this.modalRef.componentInstance.province = this.province;
    this.modalRef.componentInstance.tipologieOggetto = this.tipologieOggetto;
    this.modalRef.componentInstance.helpList = this.helpList;
    this.modalRef.componentInstance.disableBtn = this.disableBtn;
    this.modalRef.componentInstance.comuneSingolo = this.comuneSingolo;
    this.modalRef.componentInstance.showGeoRefBtn = this.showGeoRefBtn;
    this.modalRef.componentInstance.showViaconvinca = this.showViaconvinca;

    this.modalRef.componentInstance.operaEdit = this.operaEdit;
    if (operaAssociataFlag) {
      const codScriva = this.opereIstanza.find(
        (ogg) => ogg.id_oggetto === this.operaEdit.id_oggetto
      ).cod_scriva;
      this.modalRef.componentInstance.codScriva = codScriva;

      this.modalRef.componentInstance.infoOggettoIstanza =
        this.configOggetto?.info_oggetti_istanza?.find(
          (item) =>
            item.id_oggetto_istanza ===
            (<OggettoIstanza>this.operaEdit).id_oggetto_istanza
        );
      this.modalRef.componentInstance.elemDiscontinuita =
        this.jsElementiDiscontinuita?.find(
          (element) =>
            element.id_oggetto_istanza ===
            (<OggettoIstanza>this.operaEdit).id_oggetto_istanza
        );
    }

    this.modalEventSubscription =
      this.modalRef.componentInstance.closeEvent.subscribe(
        ({
          action,
          opera,
          codScriva,
          elemDiscontinuita,
          valutazioneIncidenza,
        }) => {
          this.operaEdit = opera;

          if (elemDiscontinuita) {
            if (!this.jsElementiDiscontinuita) {
              this.jsElementiDiscontinuita = [];
            }
            const elDisc = this.jsElementiDiscontinuita.find(
              (element) =>
                element.id_oggetto_istanza ===
                elemDiscontinuita.id_oggetto_istanza
            );
            if (elDisc) {
              elDisc.flg_valore = elemDiscontinuita.flg_valore;
              elDisc.des_note = elemDiscontinuita.des_note;
            } else {
              this.jsElementiDiscontinuita.push(elemDiscontinuita);
            }
          } else {
            this.jsElementiDiscontinuita = null;
          }

          if (action === 'georef') {
            this.modalRef.close();
            this.modalRef = null;
            this.modalEventSubscription.unsubscribe();
            this.georeferisci();
          } else {
            // save || save-georef
            this.fromGeoref = action === 'save-georef';
            this.editOggettoIstanza(
              tableIndex,
              codScriva,
              valutazioneIncidenza
            );
          }
        }
      );
  }

  onDelete(record: OggettoIstanza) {
    //SCRIVA-1668, 26/06/25
    this.deleteOggettoIstanza(record, true);
  }

  /**
   * SCRIVA-1668, 26/06/25
   * Funzione che apre la modale di conferma per l'eliminazione dell'oggetto istanza.
   * Mostra un messaggio di conferma e due pulsanti: Annulla e Conferma.
   * Se l'utente conferma, viene chiamata la funzione deleteOggettoIstanza.
   * @param record OggettoIstanza da eliminare
   */
  openDeleteModal(record: OggettoIstanza,errCode: string,testoModaleConferma: string) {
    this._messageService.showConfirmation({
      title: 'Conferma eliminazione',
      codMess: errCode,
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
            this.resetVariables();
            this.deleteOggettoIstanza(record, false);
          },
        },
      ],
      testo: testoModaleConferma
    });
  }

  /**
   * Funzione che elimina l'oggetto istanza selezionato.
   * Rimuove l'oggetto istanza dalla lista delle associazioni e aggiorna il quadro e lo step.
   * @param record OggettoIstanza da eliminare
   */

  deleteOggettoIstanza(record: OggettoIstanza, conferma: boolean) {
    this._ambito.eliminaOggettoIstanza(record.gestUID, conferma).subscribe(
      (res) => {
        const associazioneIndex = this.associazioniOggettoIstanza.findIndex(
          (associazione) =>
            associazione.id_oggetto_istanza === record.id_oggetto_istanza
        );
        const operaIndex = this.opereIstanza.findIndex(
          (oggetto) => (oggetto.id_oggetto = record.id_oggetto)
        );
        const elemDiscIndex = this.jsElementiDiscontinuita?.findIndex(
          (element) => element.id_oggetto_istanza === record.id_oggetto_istanza
        );

        this.associazioniOggettoIstanza.splice(associazioneIndex, 1);
        this.opereIstanza.splice(operaIndex, 1);
        if (elemDiscIndex > -1) {
          this.jsElementiDiscontinuita.splice(elemDiscIndex, 1);
        }

        const info = {
          idOggettoIstanza: record.id_oggetto_istanza,
          flagNuovoOggetto: null,
        };
        this.updateQdrConfig([info], true);

        this._messageService.showMessage(ScrivaCodesMesseges.P002, this.OI_C.OPERA_CONTAINER, true);
      },
      (err) => {
        if (err.error?.code) {
          const errCode = err.error.code;
          const handledCodes = [ScrivaCodesMesseges.A047, ScrivaCodesMesseges.A048];
          if (errCode && handledCodes.includes(errCode)) {
            const testoModaleConferma = err.error.title;
            this.openDeleteModal(record, errCode, testoModaleConferma);
            return;
          }
          this._messageService.showMessage(
            err.error.code,
            this.OI_C.OPERA_CONTAINER,
            false
          );
        } else {
          this._messageService.showMessage('E100', this.OI_C.OPERA_CONTAINER, true);
        }
      }
    );
  }

  addNew() {
    // chiamo il next del BehaviorSubject con l'azione ActionsOggettiSearchEnum.RESETDISABLE per il form di ricerca oggetti
    this.actionsOggettiSearch$.next(ActionsOggettiSearchEnum.RESETDISABLE);

    (<OggettoIstanza>this.operaEdit) = {
      id_istanza: this.idIstanza,
      id_masterdata_origine: 1,
      id_masterdata: 1,
    };
    this.showOperaForm = true;
    this.disableBtn = false;
  }

  /**
   * ####################
   * SALVATAGGIO PROGETTO
   * ####################
   */

  // #region "SALVATAGGIO PROGETTO - COMPLETO"

  /**
   * Funzione invocata per il salvataggio dati di un nuovo progetto.
   * @param elemDiscontinuita IElementoDiscontinuita
   * @param codScriva string
   * @param valIncidenza string
   */
  salvaNuovoInserimento(
    elemDiscontinuita: IElementoDiscontinuita,
    codScriva: string,
    valIncidenza: string = null
  ) {
    // Verifico se è stata passata una valutazione d'incidenza
    if (valIncidenza) {
      // Assegno localmente il valore
      this.valutazioneIncidenza = valIncidenza;
      // #
    }

    if (!this.operaEdit.tipologia_oggetto) {
      const codTipologia = this.qdr_config['COD_TIPOLOGIA_OGGETTO'];
      this.operaEdit.tipologia_oggetto = codTipologia
        ? this.tipologieOggetto.find(
            (tipologia) => tipologia.cod_tipologia_oggetto === codTipologia
          )
        : null;
    }

    const nuovaOpera: Opera = {
      tipologia_oggetto: this.operaEdit.tipologia_oggetto,
      // stato_oggetto: {
      //   id_stato_oggetto: 10,
      //   cod_stato_oggetto: 'BOZZA',
      //   des_stato_oggetto: 'Oggetto in stato Bozza',
      // },
      stato_oggetto: null,
      cod_oggetto_fonte: this.operaEdit.cod_oggetto_fonte,
      den_oggetto: this.operaEdit.den_oggetto,
      des_oggetto: this.operaEdit.des_oggetto,
      coordinata_x: this.operaEdit.coordinata_x,
      coordinata_y: this.operaEdit.coordinata_y,
      id_masterdata_origine: 1,
      id_istanza_aggiornamento: this.idIstanza,
      id_masterdata: 1,
      ubicazione_oggetto: [],
    };
    if (codScriva) {
      nuovaOpera.cod_scriva = codScriva;
    }

    if (this.operaEdit.ubicazione_oggetto) {
      this.operaEdit.ubicazione_oggetto.forEach(
        (ubi: UbicazioneOggettoIstanza) => {
          nuovaOpera.ubicazione_oggetto.push({
            comune: ubi.comune,
            den_indirizzo: ubi.den_indirizzo,
            num_civico: ubi.num_civico,
            des_localita: ubi.des_localita,
            ind_geo_provenienza: ubi.ind_geo_provenienza,
          });
        }
      );
    }

    // Variabili di comodo per migliorare la lettura
    const p: Opera = nuovaOpera;
    const codAdemp: string = this.codAdempimento;
    const valInc: string = valIncidenza;
    const elemDisc: IElementoDiscontinuita = elemDiscontinuita;
    // Richiamo il flusso per la gestione del salvataggio dati
    // # NUOVO - SCRIVA-1539
    this.nuovoFlussoSalvaProgetto(p, codAdemp, valInc, elemDisc);
    // # VECCHIO
    // this.vecchioFlussoSalvaProgetto(p, codAdemp, valInc, elemDisc);
  }

  /**
   * Funzione che gestisce l'intero flusso di salvataggio dei dati del progetto.
   * Il salvataggio prevede diversi step, fino alla finalizzazione finale dei dati.
   * @param progetto Opera con le informazioni del progetto da salvare.
   * @param codAdempimento string con il codice dell'adempimento per il salvataggio dei dati.
   * @param valutazioneIncidenza any con le informazioni per la gestione della valutazione di incidenza.
   * @param elementoDiscontinuita IElementoDiscontinuita con le informazioni di un eventuale elemento di discontinuità da gestire.
   * @author Ismaele Bottelli
   * @date 05/12/2024
   * @jira SCRIVA-1539
   * @notes Questo è il nuovo flusso sviluppato per poter gestire la segnazione della SCRIVA-1539.
   *        Essendo che vengono toccate molte cose, separo la logica di flusso e creo un "nuovo" e un "vecchio" approccio.
   *        In caso di errori si potrà fare una rollback sulle vecchie cose, con la possibilità di correggere quelle nuove.
   */
  private nuovoFlussoSalvaProgetto(
    progetto: Opera,
    codAdempimento: string,
    valutazioneIncidenza: any,
    elementoDiscontinuita: IElementoDiscontinuita
  ) {
    // Lancio lo spinner di caricamento
    this.spinner.show();

    // @jira SCRIVA-1539 questo flusso è un delirio totale, la gestione degli errori è stata incasinata sulle chiamate
    // #1 - richiamo il salvataggio del progetto come oggetto Opera e aggiorno i dati correlati
    this.salvaOpera(progetto, codAdempimento)
      .pipe(
        // #2 - richiamo la funzione per la gestione del salvataggio dei vincoli
        switchMap(() => this.getCallVNCSUpdate(valutazioneIncidenza)),
        // #3 - richiamo la funzione per il salvataggio delle informazioni dell'oggetto-istanza (come legame tra opera e istanza)
        switchMap(() => {
          /**
           * @author Ismaele Bottelli
           * @date 03/12/2024
           * @notes Il flusso rifattorizzato prevede che nella chiamata "this.salvaOpera" le informazioni "globali" della variabile "this.operaEdit" vengano modificate al termine del salvataggio dell'opera.
           *        Oltre a quello, anche la lista che tiene conto dei progetti inseriti dall'utente viene aggiornata nella funzione sopra citata per la variabile: "this.opereIstanza".
           *        Il motivo di questa decisione non è conosciuta, riporto il flusso per com'è stato pensato inizialmente.
           */
          // Recupero l'opera in modifica e lo tratto come oggetto istanza e forzo la tipizzazione
          const oggIst: OggettoIstanza = <OggettoIstanza>this.operaEdit;
          // Cerco di salvare le informazioni per l'oggetto istanza
          return this.salvaOggettoIstanza(oggIst, elementoDiscontinuita);
          // #
        }),
        // #4 - richiamo la funzione di aggiornamento dei dati correlati al qdr_config, sia locali che su DB come jsondata
        switchMap((oggettoIstanza: OggettoIstanza) => {
          // Recupero le informazioni puntali per l'aggiornamento dati
          const idOggettoIstanza: number = oggettoIstanza.id_oggetto_istanza;
          const flagNuovoOggetto: boolean = true;
          // Creo l'oggetto per gestire l'aggiornamento dati del quadro
          let info: IAggiornaQdrConfig;
          info = { idOggettoIstanza, flagNuovoOggetto };

          // Richiamo la funzione per aggiornare i dati per il qdr_config
          return this.updateQdrConfig$([info]).pipe(
            // Per gestire il flusso devo però ritornare sempre l'oggetto-istanza salvato
            map((dataQuadro: DataQuadro) => {
              // Ritorno sempre l'oggetto istanza
              return oggettoIstanza;
              // #
            })
          );
          // #
        }),
        // #5 - richiamo la funzione di verifica per le informazioni per le autorità competenti
        switchMap((oggettoIstanza: OggettoIstanza) => {
          // Imposto il target per gli errori
          const target: string = this.OI_C.OPERA_CONTAINER;
          // Richiamo la funzione la verifica e la gestione degli errori generati dal check sulle autorità competenti
          return this.verificaAutoritaCompetentiProgetto(target).pipe(
            // Per gestire il flusso devo però ritornare sempre l'oggetto-istanza salvato
            map((competenzeTerritorio: CompetenzaTerritorio[]) => {
              // Ritorno sempre l'oggetto istanza
              return oggettoIstanza;
              // #
            }),
            catchError((e: ScrivaServerError) => {
              // Ho un errore specifico per il check ac
              this.segnalaCheckAC = true;
              // Gestisco l'errore, ma le informazioni devono procedere, passo quindi l'oggetto-istanza
              return of(oggettoIstanza);
              // #
            })
          );
          // #
        })
      )
      .subscribe({
        next: (oggettoIstanza: OggettoIstanza) => {
          // Lancio le logiche di finalizzazione flusso per il componente
          const saveDataQuadro: boolean = false;
          this.onSalvataggioOggettoIstanzaCompletato(
            oggettoIstanza,
            saveDataQuadro
          );
          // Chiudo lo spinner
          this.spinner.hide();
          // #
        },
        error: (e: ScrivaServerError) => {
          // Chiudo lo spinner
          this.spinner.hide();
          // #
        },
      });
  }

  /**
   * Funzione che gestisce l'intero flusso di salvataggio dei dati del progetto.
   * Il salvataggio prevede diversi step, fino alla finalizzazione finale dei dati.
   * @param progetto Opera con le informazioni del progetto da salvare.
   * @param codAdempimento string con il codice dell'adempimento per il salvataggio dei dati.
   * @param valutazioneIncidenza any con le informazioni per la gestione della valutazione di incidenza.
   * @param elementoDiscontinuita IElementoDiscontinuita con le informazioni di un eventuale elemento di discontinuità da gestire.
   * @author Ismaele Bottelli
   * @date 05/12/2024
   * @jira SCRIVA-1539
   * @notes Questo è il nuovo flusso sviluppato per poter gestire la segnazione della SCRIVA-1539.
   *        Essendo che vengono toccate molte cose, separo la logica di flusso e creo un "nuovo" e un "vecchio" approccio.
   *        In caso di errori si potrà fare una rollback sulle vecchie cose, con la possibilità di correggere quelle nuove.
   */
  private vecchioFlussoSalvaProgetto(
    progetto: Opera,
    codAdempimento: string,
    valutazioneIncidenza: any,
    elementoDiscontinuita: IElementoDiscontinuita
  ) {
    // Lancio lo spinner di caricamento
    this.spinner.show();

    this._ambito
      .salvaOpera(progetto, codAdempimento)
      .pipe(
        catchError((error) => {
          this.showErrorsQuadroConCodeENoCode(error, 'operaFormCard');
          return throwError(error);
        }),
        tap((resp) => {
          if (!this.opereIstanza) {
            this.opereIstanza = [];
          }
          this.opereIstanza.push(resp);
          (<OggettoIstanza>this.operaEdit).id_oggetto = resp.id_oggetto;
          // (<OggettoIstanza>this.operaEdit).flg_principale = true;
          (<OggettoIstanza>this.operaEdit).ind_livello = 1;
        }),
        switchMap(() => this.getCallVNCSUpdate(valutazioneIncidenza)),
        switchMap(() =>
          this._ambito.salvaOggettoIstanza(<OggettoIstanza>this.operaEdit).pipe(
            catchError((err) => {
              this.showErrorsQuadroConCodeENoCode(
                err,
                this.OI_C.OPERA_CONTAINER
              );

              this._ambito
                .eliminaOpera(
                  this.opereIstanza[this.opereIstanza.length - 1].gestUID
                )
                .subscribe();
              this.opereIstanza.pop();
              return throwError(err);
            })
          )
        )
      )
      .subscribe({
        next: (res) => {
          this.associazioniOggettoIstanza.push(res);
          this.associazioniOggettoIstanza = [
            ...this.associazioniOggettoIstanza,
          ];
          if (this.associazioniOggettoIstanza.length>0){
            this.setVisibilityButtonNext(true);
          }
          if (elementoDiscontinuita) {
            if (!this.jsElementiDiscontinuita) {
              this.jsElementiDiscontinuita = [];
            }
            elementoDiscontinuita.id_oggetto_istanza = res.id_oggetto_istanza;
            this.jsElementiDiscontinuita.push(elementoDiscontinuita);
          }

          const info: IAggiornaQdrConfig = {
            idOggettoIstanza: res.id_oggetto_istanza,
            flagNuovoOggetto: true,
          };
          this.updateQdrConfig([info]);
          this.onSalvataggioOggettoIstanzaCompletato(res, false);

          // Chiudo lo spinner
          this.spinner.hide();
          // #
        },
        error: (e: ScrivaServerError) => {
          // Chiudo lo spinner
          this.spinner.hide();
          // #
        },
      });
  }

  /**
   * Funzione che verifica le autorità competenti di un progetto salvato.
   * Le segnalazioni per eventuali errori specifici del check_ac, sono gestite internamente dalla funzione.
   * @param targetErrori string con l'identificativo per la gestione della messaggistica verso l'utente. Per default è: OperaInterventoConsts.OPERA_CONTAINER.
   * @returns Observable<CompetenzaTerritorio[]> con il risultato del check.
   */
  private verificaAutoritaCompetentiProgetto(
    targetErrori?: string
  ): Observable<CompetenzaTerritorio[]> {
    // Recupero le informazioni per generare i parametri di verifica per il check_ac
    const idIst: number = this.idIstanza;
    const qdrConfig: any = this.qdr_config;
    // Creo gli oggetti di configurazione per la gestione della verifica delle autorità compententi
    // # Parametri servizio check_ac
    const checkACParams: ICheckACParams = this.parametriCheckAutoritaCompetenti(
      idIst,
      qdrConfig
    );

    // Verifico il target per le segnalazioni
    targetErrori = targetErrori ?? this.OI_C.OPERA_CONTAINER;
    // # Parametri gestione segnalazione
    const gestioneErrore: IServiziErrorConfig = {
      target: targetErrori,
      defaultCode: ScrivaCodesMesseges.E100,
      autoFade: false,
    };

    // Richiamo il servizio di verifica dell'AC con la gestione di eventuali errori
    return this._opera.checkCompetenzeOggettoSegnalazione(
      checkACParams,
      gestioneErrore
    );
  }

  // #endregion "SALVATAGGIO PROGETTO - COMPLETO"

  // #region "SALVATAGGIO OPERA"

  /**
   * Funzione di supporto che gestisce le logiche specifiche per il salvataggio di un nuovo progetto, come Opera.
   * @param opera Opera con le informazioni dell'opera da salvare.
   * @param codiceAdempimento string con il codice adempimento per il riferimento al salvataggio dell'Opera.
   * @param targetErrori string con l'identificativo per gestire i possibili errori della chiamata. Per default è identificato dalla costante: OperaInterventoConsts.FORM_CARD_CONTAINER.
   * @param useSpinner boolean con l'indicazione dinamica per l'uso dello spinner. Per default è: false.
   * @returns Observable<Opera> con la risposta del servizio.
   * @author Ismaele Bottelli
   * @jira SCRIVA-1539 questa funzione è un refactor per la gestione del problema. Con il refactor cerco di mettere dentro delle configurazioni per rendere più modulare la funzionalità.
   */
  private salvaOpera(
    opera: Opera,
    codiceAdempimento: string,
    targetErrori?: string,
    useSpinner: boolean = false
  ): Observable<Opera> {
    // Verifico se è stato definito un target specifico per la gestione degli errori
    targetErrori = targetErrori ?? this.OI_C.OPERA_FORM_CARD_CONTAINER;
    // Gestisco lo spinner in maniera dinamica per la chiamata
    this.showSpinner(useSpinner);

    // Richiamo il servizio per il salvataggio dei dati
    return this._ambito.salvaOpera(opera, codiceAdempimento).pipe(
      tap((opera: Opera) => {
        // Gestisco lo spinner
        this.hideSpinner(useSpinner);
        // Ad avvenuto salvataggio del progetto, gestisco le informazioni del componente derivate dal dato
        this.onSalvaOpera(opera);
        // #
      }),
      catchError((e: ScrivaServerError) => {
        // Gestisco lo spinner
        this.hideSpinner(useSpinner);
        // Gestisco in maniera specifica l'errore passando il target
        this.onServiziError(e, targetErrori);
        // Ritorno di nuovo l'errore
        return throwError(e);
      })
    );
  }

  /**
   * Funzione di comodo creata per gestire le logiche al termine, con successo, della funzione "salvaOpera".
   * @param operaSalvata Opera con l'oggetto ritornato dal servizio che ha salvato i dati.
   * @author Ismaele Bottelli
   * @jira SCRIVA-1539 questa funzione è un refactor per la gestione del problema.
   *       Con il refactor cerco di mettere dentro delle configurazioni per rendere più modulare la funzionalità.
   */
  private onSalvaOpera(operaSalvata: Opera) {
    // Verifico se all'interno del componente è già presente la lista di progetti dell'istanza
    this.opereIstanza = this.opereIstanza ?? [];
    // Aggiungo l'opera salvata all'interno della lista di opere dell'istanza
    this.opereIstanza.push(operaSalvata);

    // Recupero le informazioni dell'oggetto opera in modifica, forzando il parse però del tipo in "OggettoIstanza" (hanno informazioni comuni)
    const operaEditAsOggIst: OggettoIstanza = this.operaEdit as OggettoIstanza;
    // Modifico le informazioni aggiungendo alcune delle informazioni dall'opera salvata
    operaEditAsOggIst.id_oggetto = operaSalvata.id_oggetto;
    // Forzo il livello di gestione del dato a: 1
    operaEditAsOggIst.ind_livello = 1;
    // Riassegno le informazioni alla variabile del componente
    this.operaEdit = operaEditAsOggIst;

    // Vecchio codice, lo mantengo per sicurezza
    // (<OggettoIstanza>this.operaEdit).id_oggetto = operaSalvata.id_oggetto;
    // (<OggettoIstanza>this.operaEdit).flg_principale = true;
    // (<OggettoIstanza>this.operaEdit).ind_livello = 1;
  }

  // #endregion "SALVATAGGIO OPERA"

  // #region "SALVATAGGIO OGGETTO ISTANZA"

  /**
   * Funzione del flusso di salvataggio dei dati di un progetto.
   * Questo passaggio prevede il salvataggio delle informazioni dell'oggetto-istanza a seguito del salvataggio dei dati del progetto come oggetto Opera.
   * In caso in cui il salvataggio dell'oggetto-istanza dovesse fallire, ci sarà una rollback delle informazioni anche per quanto riguarda il progetto salvato su DB.
   * @param oggettoIstanza OggettoIstanza con le informazioni da salvare.
   * @param elementoDiscontinuita any con l'oggetto per la gestione dell'elemento discontinuità.
   * @param targetErrori string con l'identificativo per gestire i possibili errori della chiamata. Per default è identificato dalla costante: OperaInterventoConsts.OPERA_CONTAINER.
   * @param useSpinner boolean con l'indicazione dinamica per l'uso dello spinner. Per default è: false.
   * @returns Observable<OggettoIstanza> con le informazioni salvate dal servizio.
   * @author Ismaele Bottelli
   * @date 03/12/2024
   * @jira SCRIVA-1539
   * @notes Rifattorizzando il codice ho visto che viene fatta una rollback "manuale" da parte del front-end.
   *        Questo è un enorme problema perché si rischia di avere dati incongruenti se il rollback fallisse su questo livello.
   */
  private salvaOggettoIstanza(
    oggettoIstanza: OggettoIstanza,
    elementoDiscontinuita?: any,
    targetErrori?: string,
    useSpinner: boolean = false
  ): Observable<OggettoIstanza> {
    // Verifico se è stato definito un target specifico per la gestione degli errori
    targetErrori = targetErrori ?? this.OI_C.OPERA_CONTAINER;

    // // Recupero il codice quadro da passare come header
    // const quadro: string = this.codQuadro;

    // Gestisco lo spinner in maniera dinamica per la chiamata
    this.showSpinner(useSpinner);

    // Lancio il salvataggio dell'oggetto in input
    return this._ambito.salvaOggettoIstanza(oggettoIstanza /*, quadro */).pipe(
      tap((oggettoIstanza: OggettoIstanza) => {
        // Gestisco lo spinner
        this.hideSpinner(useSpinner);
        // Lancio la funzione per aggiornare i dati correlati all'oggetto istanza
        this.onSalvaOggettoIstanza(oggettoIstanza, elementoDiscontinuita);
        // #
      }),
      catchError((e: ScrivaServerError) => {
        // Gestisco lo spinner
        this.hideSpinner(useSpinner);
        // Gestisco la segnalazione dell'errore
        this.onServiziError(e, targetErrori);

        // Si è verificato un errore, recupero le informazioni per la rollback
        let operaRB: Opera;
        operaRB = this.opereIstanza[this.opereIstanza.length - 1];

        // Lancio la funzione per gestire la rollback specific
        return this.salvaOggettoIstanzaRollback(
          operaRB,
          targetErrori,
          useSpinner
        ).pipe(
          switchMap((operaDel: Opera) => {
            // Scrivo in console del rollback manuale
            this.segnalaRollbackProgetto(operaRB);
            // La cancellazione è andata bene, aggiorno la lista globale del componente per rimuovere effettivamente l'opera
            this.opereIstanza.pop();
            // Per la gestione specifica di questo flusso, se la rollback va a buon fine, ritorno l'errore generato dal salvataggio dell'oggetto istanza
            return throwError(e);
            // #
          })
        );
        // #
      })
    );
  }

  /**
   * Funzione del flusso di salvataggio dei dati di un progetto.
   * Questo passaggio prevede il rollback delle informazioni anche per quanto riguarda il progetto salvato su DB.
   * Il flusso è strettamente collegato alla funzione chiamante "this.salvaOggettoIstanza".
   * @param operaRollback Opera con l'oggetto da rimuovere come flusso di rollback.
   * @param targetErrori string con l'identificativo per gestire i possibili errori della chiamata. Per default è identificato dalla costante: OperaInterventoConsts.OPERA_CONTAINER.
   * @param useSpinner boolean con l'indicazione dinamica per l'uso dello spinner. Per default è: false.
   * @returns Observable<OggettoIstanza> con le informazioni salvate dal servizio.
   * @author Ismaele Bottelli
   * @date 03/12/2024
   * @jira SCRIVA-1539
   * @notes Rifattorizzando il codice ho visto che viene fatta una rollback "manuale" da parte del front-end. Questo è un enorme problema perché si rischia di avere dati incongruenti se il rollback fallisse su questo livello.
   */
  private salvaOggettoIstanzaRollback(
    operaRollback: Opera,
    targetErrori?: string,
    useSpinner: boolean = false
  ): Observable<Opera> {
    // Verifico se è stato definito un target specifico per la gestione degli errori
    targetErrori = targetErrori ?? this.OI_C.OPERA_CONTAINER;
    // Gestisco lo spinner in maniera dinamica per la chiamata
    this.showSpinner(useSpinner);

    // Dall'opera in input estraggo il gestUID per la cancellazione
    const gestUID: string = operaRollback?.gestUID;
    // Richiamo e ritorno le logiche per la cancellazione dell'opera
    return this._ambito.eliminaOpera(gestUID).pipe(
      tap(() => {
        // Gestisco lo spinner
        this.hideSpinner(useSpinner);
        // #
      }),
      catchError((e: ScrivaServerError) => {
        // Gestisco lo spinner
        this.hideSpinner(useSpinner);
        // Gestisco la segnalazione dell'errore
        this.onServiziError(e, targetErrori);
        // Ritorno l'errore generato dal servizio
        return throwError(e);
        // #
      })
    );
  }

  /**
   * Funzione di comodo che segnala in console che è stato eseguito il rollback manuale dell'Opera poiché il salvataggio dell'OggettoIstanza è fallito.
   * @param opera Opera con i dati dell'opera rollbackata poiché il salvataggio dell'oggetto-istanza è fallito.
   * @author Ismaele Bottelli
   * @date 03/12/2024
   * @jira SCRIVA-1539
   * @notes Rifattorizzando il codice ho visto che viene fatta una rollback "manuale" da parte del front-end. Questo è un enorme problema perché si rischia di avere dati incongruenti se il rollback fallisse su questo livello.
   */
  private segnalaRollbackProgetto(operaRollback: Opera) {
    // Casistica non gestita, non viene fatto niente, ma per sicurezza loggo un warning
    const t: string = `[opera-intervento.component.ts] segnalaRollbackProgetto - Salvataggio OggettoIstanza da gestione "progetto" fallito, l'oggetto Opera di riferimento è stato cancellato da DB.`;
    const b: any = { opera: operaRollback };
    this._logger.warning(t, b);
  }

  /**
   * Funzione del flusso di salvataggio dei dati di un progetto.
   * Questo passaggio prevede l'aggiornamento di tutte le informazioni del componente una volta che il processo di salvataggio del progetto come "Opera" e come "OggettoIstanza" è completato.
   * Il flusso è strettamente collegato alla funzione chiamante "this.salvaOggettoIstanza".
   * @param oggettoIstanza OggettoIstanza con l'oggetto salvato su DB.
   * @param elementoDiscontinuita any con l'oggetto per la gestione dell'elemento discontinuità.
   */
  private onSalvaOggettoIstanza(
    oggettoIstanza: OggettoIstanza,
    elementoDiscontinuita?: any
  ) {
    // Vado ad aggiornare la lista delle associazioni oggetti-istanza riassegnando il riferimento
    let associazioniOggettoIstanza: OggettoIstanza[];
    associazioniOggettoIstanza = [
      ...this.associazioniOggettoIstanza,
      oggettoIstanza,
    ];
    /**
     * @author Ismaele Bottelli
     * @date 04/12/2024
     * @notes Non ho la più pallida idea del perché viene riassegnato il riferimento alla variabile del componente.
     *        Penso che serva a forzare la digest di Angular e aggiornare tutte le componentistiche sul fronte HTML.
     */
    this.associazioniOggettoIstanza = associazioniOggettoIstanza;

    // Verifico se è stato passato un elemento di discontinuità per i dati
    if (elementoDiscontinuita) {
      // Esiste l'oggetto in input, verifico che esista la lista a livello componente
      if (!this.jsElementiDiscontinuita) {
        // Non esiste, effetto un'assegnazione di default
        this.jsElementiDiscontinuita = [];
        // #
      }

      // Recupero dall'oggettoIstanza il suo id_oggetto_istanza
      const idOggIst = oggettoIstanza?.id_oggetto_istanza;
      // Modifico l'id oggetto istanza dell'elemento di discontinuità e assegno quello dell'oggetto istanza inserito
      elementoDiscontinuita.id_oggetto_istanza = idOggIst;
      // Aggiorno l'elemento discontinuità all'array di dati presente a livello globale del componente
      this.jsElementiDiscontinuita.push(elementoDiscontinuita);
    }
  }

  // #endregion "SALVATAGGIO OGGETTO ISTANZA"

  resetVariables() {
    this.operaEdit = null;
    this.showOperaForm = false;
    // chiamo il next del BehaviorSubject con l'azione ActionsOggettiSearchEnum.ENABLE per il form di ricerca oggetti
    this.actionsOggettiSearch$.next(ActionsOggettiSearchEnum.ENABLE);
  }

  onDismissForm() {
    this.resetVariables();
  }

  /**
   * Funzione collegata all'evento di "chiusura" del componente del form del progetto.
   * @param data IOperaFormPayload con le informazioni ritornate dal componente di gestione dati.
   */
  onCloseForm(data: IOperaFormPayload) {
    // Assegno localmente le informazioni per il progetto editato
    this.operaEdit = data.opera;

    // Verifico il tipo dell'azione ritornata
    if (data.action !== GestioniDatiProgetto.salvataggio) {
      // Non è richiesto un salvataggio dati, imposto il flag per la gestione della georeferenzazione
      this.fromGeoref = true;
      // #
    }

    // Lancio la funzione di salvataggio dei dati
    this.salvaNuovoInserimento(
      data.elemDiscontinuita,
      data.codScriva,
      data.valutazioneIncidenza
    );
  }

  onHelpClicked(chiave: string) {
    const modalContent =
      this.helpList.find(
        (help) =>
          help.tipo_help.cod_tipo_help === 'MDL' &&
          help.chiave_help.includes(chiave)
      )?.des_testo_help || 'Help non trovato...';

    this._messageService.showConfirmation({
      title: 'HELP',
      codMess: null,
      content: modalContent,
      buttons: [],
    });
  }

  buildComuniList(
    ubicazioni: UbicazioneOggetto[] | UbicazioneOggettoIstanza[]
  ): string {
    let list = ubicazioni[0].comune.denom_comune;
    if (ubicazioni.length > 1) {
      for (let i = 1; i < ubicazioni.length; i++) {
        list += ', ' + ubicazioni[i].comune.denom_comune;
      }
    }
    return list;
  }

  buildUbicazioneList(ubicazioni): string {
    let list;
    const ubiConIndirizzo: UbicazioneOggetto | UbicazioneOggettoIstanza =
      ubicazioni.find((ubi) => !!ubi.den_indirizzo);
    if (ubiConIndirizzo) {
      list = `${ubiConIndirizzo.den_indirizzo}`;
      if (ubiConIndirizzo.num_civico) {
        list += ` ${ubiConIndirizzo.num_civico}`;
      }
    } else {
      const index = ubicazioni.findIndex((ubi) => !!ubi.des_localita);
      list = index > -1 ? ubicazioni[index].des_localita : '';
      if (ubicazioni.length > 1) {
        for (let i = 1; i < ubicazioni.length; i++) {
          if (ubicazioni[i].des_localita) {
            list += ', ' + ubicazioni[i].des_localita;
          }
        }
      }
    }
    return list;
  }

  buildPraticheCollegateArray(procedimento: ProcedimentoCollegato[]): string[] {
    const array: string[] = [];
    if (procedimento?.length > 0) {
      procedimento.forEach((procedimento) => {
        const index = array.findIndex((element) =>
          element.includes(procedimento.cod_istanza)
        );
        if (index === -1) {
          if (procedimento.soggetto.den_soggetto) {
            array.push(
              `${procedimento.cod_istanza} presentata da ${procedimento.soggetto.den_soggetto}`
            );
          } else {
            array.push(
              `${procedimento.cod_istanza} presentata da ${procedimento.soggetto.nome} ${procedimento.soggetto.cognome}`
            );
          }
        } else {
          if (procedimento.soggetto.den_soggetto) {
            array[index] += `, ${procedimento.soggetto.den_soggetto}`;
          } else {
            array[
              index
            ] += `, ${procedimento.soggetto.nome} ${procedimento.soggetto.cognome}`;
          }
        }
      });
    }
    return array;
  }

  showPraticheCollegateModal(procedimenti: ProcedimentoCollegato[]) {
    const array = this.buildPraticheCollegateArray(procedimenti);
    let modalContent = '';
    array.forEach((element) => {
      modalContent += `${element}<br>`;
    });

    this._messageService.showConfirmation({
      title: 'Procedimenti collegati',
      codMess: null,
      content: modalContent,
      buttons: [
        {
          label: 'CHIUDI',
          type: 'btn-primary single',
          callback: () => {},
        },
      ],
    });
  }

  // #region "UPDATE QDR_CONFIG"

  /**
   * Funzione che gestisce l'aggiornamento dei dati per il quadro.
   * La funzione è pensata per gestire le logiche di gestione con la gestione dello spinner.
   * @param info IAggiornaQdrConfig[] con le informazioni per l'aggiornamento dei dati.
   * @param flagRemove boolean che definisce le logiche per gestire i dati per l'aggiornamento delle configurazioni del quadro.
   */
  updateQdrConfig(info: IAggiornaQdrConfig[], flagRemove: boolean = false) {
    // Visualizzo lo spinner
    this.spinner.show();
    // Lancio la funzione di salvataggio dei dati per il json quadro
    this.updateQdrConfig$(info, flagRemove).subscribe({
      next: (dataQuadro: DataQuadro) => {
        // Chiudo lo spinner
        this.spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Chiudo lo spinner
        this.spinner.hide();
        // Segnalo l'errore
        this.onServiziError(e, 'associazioniTableCard');
        // #
      },
    });
  }

  /**
   * Funzione che gestisce l'aggiornamento dei dati per il quadro.
   * @param info IAggiornaQdrConfig[] con le informazioni per l'aggiornamento dei dati.
   * @param flagRemove boolean che definisce le logiche per gestire i dati per l'aggiornamento delle configurazioni del quadro.
   * @returns Observable<DataQuadro> con le informazioni a seguito del salvataggio dei dati.
   */
  private updateQdrConfig$(
    info: IAggiornaQdrConfig[],
    flagRemove: boolean = false
  ): Observable<DataQuadro> {
    // Lancio al funzione per gestire l'aggiornamento delle informazioni per i dati locali
    this.gestisciAggiornamentoQdrConfig(info, flagRemove);

    /**
     * @author Ismaele Bottelli
     * @date 04/12/2024
     * @notes Non ho idea del perché ci sia -1 nell'id_template_quadro.
     */
    // Genero il payload per la richiesta
    const payload: DataQuadro = {
      id_istanza: this.idIstanza,
      id_template_quadro: -1,
      json_data_quadro: JSON.stringify(this.qdr_config),
    };

    // Lancio la funzione di salvataggio dei dati per il json quadro
    return this.salvaJsonDataQuadro$(payload, true).pipe(
      switchMap((dataQuadro: DataQuadro) => {
        //setto la configurazione per il salvataggio
        const configs = this._buildConfigSave(false);
        // A seguito del salvataggio, lancio il salvataggio dati quadro senza procede con le logiche
        return this.saveDataQuadro(configs);
        // #
      })
    );
  }

  /**
   * Funzione che richiama il servizio di aggiornamento dei dati per il jsonData del quadro.
   * @param data DataQuadro con l'oggetto per l'aggiornamento dati.
   * @param update boolean con il flag che definisce se inserire [false] o aggiornare [true] i dati su DB.
   * @returns Observable<DataQuadro> con il risultato del salvataggio dei dati.
   */
  private salvaJsonDataQuadro$(
    data: DataQuadro,
    update?: boolean
  ): Observable<DataQuadro> {
    // Richiamo e ritorno il risultato delle logiche del servizio
    return this.stepManagerService.salvaJsonDataQuadro(data, update);
    // #
  }

  // #endregion "UPDATE QDR_CONFIG"

  // #region "GESTIONE DATI LOCALI QDR_CONFIG"

  /**
   * Funzione che gestisce le informazioni specifiche dell'oggetto del quadro presente all'interno dei dati componente.
   * @param datiAggiornamento IAggiornaQdrConfig[] con le informazioni per l'aggiornamento dei dati.
   * @param flagRemove boolean che definisce le logiche per gestire i dati per l'aggiornamento delle configurazioni del quadro. Per default è: false.
   * @author Ismaele Bottelli
   * @date 04/12/2024
   * @jira SCRIVA-1539 - Rifattorizzazione del codice per gestire la richiesta.
   */
  private gestisciAggiornamentoQdrConfig(
    datiAggiornamento: IAggiornaQdrConfig[],
    flagRemove: boolean = false
  ) {
    // Verifico come deve essere gestito il dato
    if (flagRemove) {
      /**
       * @author Ismaele Bottelli
       * @date 04/12/2024
       * @notes Non ho la più pallida idea del perché venga preso solo l'oggetto alla posizione 0.
       *        Forse la logica per la rimozione del dato vale solo per il primo oggetto e sta funzione per la cancellazione si aspetta sempre un solo elemento.
       */
      const dataRemove: IAggiornaQdrConfig = datiAggiornamento[0];
      // Lancio la funzione di gestione dei dati per la cancellazione delle informazioni
      this.gestisciRimozioneDatiQdrConfig(dataRemove);
      // #
    } else {
      // Lancio la funzione di gestione dai dati per l'aggiunta delle nuove informazioni
      this.gestisciAggiuntaDatiQdrConfig(datiAggiornamento);
      // #
    }
  }

  /**
   * Funzione che gestisce la rimozione delle informazioni dalle informazioni globali della variabile che gestisce i dati delle configurazioni quadro.
   * La funzione è pensata per essere direttamente utilizzata dalla funzione "this.gestisciAggiornamentoQdrConfig".
   * @param datoRimozione IAggiornaQdrConfig con l'informazione per la cancellazione delle configurazioni.
   * @author Ismaele Bottelli
   * @date 04/12/2024
   * @notes Questa funzione è frutto di un refactor di logiche.
   * @jira SCRIVA-1539
   */
  private gestisciRimozioneDatiQdrConfig(datoRimozione: IAggiornaQdrConfig) {
    // Verifico che esista effettivamente L'informazione per la cancellazione
    if (!datoRimozione) {
      // Mancano le informazioni per la gestione della cancellazione
      return;
    }

    // Definisco delle variabili di comodo per la gestione dei dati
    const configsByTipoQdr: any = this.qdr_config[this.codTipoQuadro];
    const infoOggIst: any[] = configsByTipoQdr?.info_oggetti_istanza ?? [];
    // Cerco all'interno della configurazione un match tra "id_oggetto_istanza"
    const iInfo = infoOggIst.findIndex((data: any) => {
      // Recupero le informazioni per il match dati
      const elemIdOggIst: number = data.id_oggetto_istanza;
      const removeIdOggIst: number = datoRimozione.idOggettoIstanza;
      // Effettuo un match tra gli id
      return elemIdOggIst === removeIdOggIst;
      // #
    });

    // Verifico che siano state trovate informazioni effettivamente
    if (iInfo > -1) {
      // L'informazione esiste, rimuovo dalla lista dati l'elemento
      this.qdr_config[this.codTipoQuadro].info_oggetti_istanza.splice(iInfo, 1);
      // #
    }
  }

  /**
   * Funzione che gestisce l'aggiunta delle informazioni dalle informazioni globali della variabile che gestisce i dati delle configurazioni quadro.
   * La funzione è pensata per essere direttamente utilizzata dalla funzione "this.gestisciAggiornamentoQdrConfig".
   * @param datiAggiunta IAggiornaQdrConfig[] con le informazioni per l'inserimento configurazioni.
   * @author Ismaele Bottelli
   * @date 04/12/2024
   * @notes Questa funzione è frutto di un refactor di logiche.
   * @jira SCRIVA-1539
   */
  private gestisciAggiuntaDatiQdrConfig(datiAggiunta: IAggiornaQdrConfig[]) {
    // Gestisco l'input
    const configToAdd = datiAggiunta ?? [];

    // Definisco delle variabili di comodo per la gestione dei dati
    const configsByTipoQdr: any = this.qdr_config[this.codTipoQuadro];
    // Verifico che esista il dato radice
    if (!configsByTipoQdr) {
      // Mancano le informazioni
      return;
    }

    // Verifico se dal dato di configurazione radice esiste la lista di informazioni di oggetti istanza
    if (!configsByTipoQdr.info_oggetti_istanza) {
      // Non è presente, lo inizializzo
      this.qdr_config[this.codTipoQuadro].info_oggetti_istanza = [];
      // #
    }

    // Rimappo le informazioni d'aggiungere in una nuova lista compatibile con la struttura delle configurazioni
    configToAdd
      .map((e: IAggiornaQdrConfig) => {
        // Creo un oggetto compatibile con le configurazioni
        const config: IInfoOggettiIstanzaQdr = {
          id_oggetto_istanza: e?.idOggettoIstanza,
          flg_nuovo_oggetto: e?.flagNuovoOggetto,
        };
        // Ritorno l'oggetto generato
        return config;
        // #
      })
      .forEach((info: IInfoOggettiIstanzaQdr) => {
        // Aggiungo le informazioni alla lista di configurazioni
        this.qdr_config[this.codTipoQuadro].info_oggetti_istanza.push(info);
        // #
      });
  }

  // #endregion "GESTIONE DATI LOCALI QDR_CONFIG"

  /**
   * Funzione che gestisce le logiche di completamento per la gestione del quadro come "step" dell'istanza.
   */
  setStepCompletion() {
    // SCRIVA-1486 => Nella correzione la collega ha riferito che il pulsante avanti deve essere sempre visibile, e i controlli vanno fatti solo sul click.
    // const check = this.checkStep(false, false);
    // if (check) {
    //   this.showButtonNext = true;
    // }
    // SCRIVA-1486 => QUINDI: pulsante sempre visibile, controlli solo al click, non so cosa fa this.stepManagerService.setCompleted('OperaInterventoComponent', check);
    // Quindi la tengo con le logiche originali, non ho tempo di approfondire.
    // SCRIVA-1609 => nei test hanno richiesto il pulsante avanti sia visibile solo dopo il primo inserimento
    const check = this.isButtonInteract(false);
    this.setVisibilityButtonNext(this.associazioniOggettoIstanza.length>0);
    this.setStepCompletedEmit('OperaInterventoComponent', check);
  }

  /**
   * checkNuoveGeometrieAvanti controlla se ci sono dei
   * suugerimenti da Gecco nei tabs e mostra errori opportuni
   * SCRIVA-1273
   * @param showErrors boolean
   * @returns boolean con presenza errore
   */
  checkNuoveGeometrieAvanti(showErrors: boolean = false): boolean {
    let hasError: boolean = false;
    this.associazioniOggettoIstanza?.forEach((element) => {
      // questo flag ci informa se elemento corrente e'geomodifcato
      let modeGeo = element.flg_geo_modificato;
      let messages = [];
      if (modeGeo) {
        const reteNatura2000Suggeriti =
          this._opera.getGeecoOggettiIstanzaNatura2000(
            element.id_oggetto_istanza
          );
        const areeProtetteSuggerite =
          this._opera.getGeecoOggettiIstanzaAreaProtette(
            element.id_oggetto_istanza
          );
        const particelleSuggerite =
          this._opera.getGeecoOggettiIstanzaCatastoParticella(
            element.id_oggetto_istanza
          );
        const vincoliSuggeriti = this._opera.getGeecoOggettiIstanzaVincoliIdro(
          element.id_oggetto_istanza
        );
        let faultyTabs = [];

        if (reteNatura2000Suggeriti.length > 0) {
          // diverso messaggio se obbligatorio o meno
          if (
            this.configOggetto.dettaglio_oggetto.siti_rete_natura?.obbligatorio
          ) {
            messages.push('E043');
          } else {
            faultyTabs.push('Siti Rete Natura 2000');
          }
        }
        if (areeProtetteSuggerite.length > 0) {
          // diverso messaggio se obbligatorio o meno
          if (
            this.configOggetto.dettaglio_oggetto.aree_protette?.obbligatorio
          ) {
            messages.push('E044');
          } else {
            faultyTabs.push('Aree Naturali Protette');
          }
        }
        if (particelleSuggerite.length > 0) {
          // diverso messaggio se obbligatorio o meno
          if (
            this.configOggetto.dettaglio_oggetto.dati_catastali?.obbligatorio
          ) {
            messages.push('E046');
          } else {
            faultyTabs.push('Dati catastali');
          }
        }
        if (vincoliSuggeriti.length > 0) {
          // diverso messaggio se obbligatorio o meno
          if (this.configOggetto.dettaglio_oggetto.vincoli?.obbligatorio) {
            messages.push('E045');
          } else {
            faultyTabs.push('Vincoli');
          }
        }

        if (messages.length > 0 || faultyTabs.length > 0) {
          hasError = true;
        }

        if (showErrors) {
          /** SCRIVA-1273 */
          if (faultyTabs.length > 0) {
            const swapPh = {
              ph: '{PH_DES_SEZIONE}',
              swap: faultyTabs.join(', '),
            };

            this._messageService.showMessage(
              'E042',
              this.OI_C.OPERA_CONTAINER,
              false,
              swapPh
            );
          }
          messages.forEach((codMess) => {
            this._messageService.showMessage(
              codMess,
              this.OI_C.OPERA_CONTAINER,
              false
            );
          });
        }
      }
    });

    return hasError;
  }

  private _buildConfigSave(onAvantiFlag: boolean): IConfigsSaveDataQuadro {
    const datiQuadro = {
      oggettiIstanza: onAvantiFlag
        ? this.dataQuadro
        : this.associazioniOggettoIstanza,
      indGeoMode: this.geoMode,
      valutazioneIncidenza: this.valutazioneIncidenza,
    };
    const configs: IConfigsSaveDataQuadro = {
      onAvantiFlag,
      showSpinner: true,
      isPutDatiQuadro: this.saveWithPut,
      datiQuadro: datiQuadro,
    };
    return configs;
  }

  /**
   * Funzione che effettua il salvataggio dei dati del quadro.
   * @jira SCRIVA-1486.
   * @param onAvantiFlag boolean che definisce se a seguito del salvataggio, lo stepper deve spostarsi al quadro successivo.
   */
  salvaDatiQuadro(onAvantiFlag = false) {
    const configs: IConfigsSaveDataQuadro = this._buildConfigSave(onAvantiFlag);
    // Richiamo la funzione per salvare i dati del quadro
    this.saveDataQuadro(configs).subscribe({
      next: (response: ISalvaJsonDataRes) => {
        // Verifico se devo andare avanti con lo stepper
        if (onAvantiFlag) {
          //
          this.goToStep(this.stepIndex);
          // #
        }
        // Chiudo lo spinner
        this.spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Gestisco l'errore
        this.onServiziError(e);
        // Blocco lo spinner
        this.spinner.hide();
        // #
      },
    });
  }

  protected doForkjoinSalvaDatiQuadroERiepilogo(
    requestData: RequestSaveBodyQuadro
  ): Observable<any> {
    const requestDataRiepilogo: RequestDataQuadro =
      requestData.requestDataRiepilogo;
    return this.salvaJsonDataQuadro$(requestDataRiepilogo, true);
  }

  /**
   * SCRIVA-1430
   * Controllo se esiste almeno un OggettoIstanza senza ind_geo_stato uguale a G
   * @returns boolean
   */
  private _checkGeometrieNotPresent(): boolean {
    // Recupero la configurazione per geoMode
    const geoMode: string = this.geoMode;
    // Definisco le casistiche di gestione per la geoMode
    const isGMObbligatoria = geoMode === TipiGeoriferimento.obbligatorio_M;
    const isGMNonEseguito = geoMode === TipiGeoriferimento.nonEseguito_P;
    // Verifico che le georeferenziazione non sia obbligatori ed eseguita
    if (!isGMObbligatoria && !isGMNonEseguito) {
      // Una delle due condizioni è vera, la geometria è presente
      return false;
      // #
    }

    // Recupero gli oggetti istanza a livello di componente
    const dataQuadroOggettiIstanza: OggettoIstanza[] = [...this.dataQuadro];
    // Filtro tutti gli oggetti istanza ed estraggo solo quelli con stato G
    const dataQuadroOggettiIstanzaWithG: OggettoIstanza[] =
      dataQuadroOggettiIstanza.filter((i: OggettoIstanza) => {
        // Verifico il flag per ind_geo_stato
        return i.ind_geo_stato === 'G';
        // #
      });

    // Recupero la quantità di oggetti istanza presenti a livello quadro
    const qntOggIstComp: number = dataQuadroOggettiIstanza.length;
    // Recupero la quantità di oggetti istanza a livello di quadro che hanno la georeferenzazione a G
    const qntOggIstCompG: number = dataQuadroOggettiIstanzaWithG.length;

    // Se almeno uno non ha ind_geo_stato uguale a G restituisco true
    return qntOggIstComp !== qntOggIstCompG;
  }

  /**
   * #################################
   * OVERRIDE PER LO STEP
   * #################################
   */

  // #region "STEP CHECK"
  /**
   * Funzione che verifica se le azioni del pulsante siano interagibili
   * @override
   * @param showErrors boolean che definisce se attivare la logica di visualizzazione degli errori. Per default è: false.
   */
  protected isButtonInteract(showErrors: boolean = false): boolean {
    let finalCheck = true;

    finalCheck = this._checkAssociazioniLiv1(showErrors);

    let comuniNonValidi = '';
    this.associazioniOggettoIstanza.forEach((ogg) => {
      ogg.ubicazione_oggetto.forEach((ubi) => {
        if (ubi.comune.data_fine_validita) {
          finalCheck = false;
          if (comuniNonValidi.length === 0) {
            comuniNonValidi = ubi.comune.denom_comune;
          } else {
            comuniNonValidi += `, ${ubi.comune.denom_comune}`;
          }
        }
      });
    });
    if (showErrors && comuniNonValidi.length > 0) {
      const swapPh = {
        ph: '[{PH_COMUNE}]',
        swap: comuniNonValidi,
      };

      this._messageService.showMessage(
        'E065',
        this.OI_C.OPERA_CONTAINER,
        false,
        swapPh
      );
    }

    let faultyElementIndexes_mandatory = [];
    let faultyElementIndexes_validated = [];

    Object.keys(this.configOggetto?.dettaglio_oggetto)?.forEach((key) => {
      if (
        key !== 'cod_scriva' &&
        key !== 'note_atto_precedente' &&
        key !== 'dati_catastali'
      ) {
        this.associazioniOggettoIstanza?.forEach((element, index) => {
          if (
            key === 'siti_rete_natura' ||
            key === 'aree_protette' ||
            key === 'vincoli'
          ) {
            if (element[key]?.some((el) => el.to_be_validated)) {
              faultyElementIndexes_validated.push(index + 1);
            }
          }

          if (this.configOggetto?.dettaglio_oggetto[key]?.obbligatorio) {
            // Discrepanza di chiave tra configurazione e modello dei siti natura2000
            const key2 = key === 'siti_rete_natura' ? 'siti_natura_2000' : key;

            if (!element[key2]) {
              faultyElementIndexes_mandatory.push(index + 1);
            }
            if (
              key === 'siti_rete_natura' ||
              key === 'aree_protette' ||
              key === 'vincoli'
            ) {
              if (element[key2]?.length === 0) {
                faultyElementIndexes_mandatory.push(index + 1);
              }
            }
          }
        });
      }
    });

    // check note_atto_precedente
    this.associazioniOggettoIstanza?.forEach((element, index) => {
      const infoCreazione = this.configOggetto?.info_oggetti_istanza?.find(
        (el) => {
          // Cerco per id_oggetto_istanza
          return el.id_oggetto_istanza === element.id_oggetto_istanza;
        }
      );
      const configItem =
        this.configOggetto?.dettaglio_oggetto?.note_atto_precedente?.find(
          (item) =>
            item?.ricerca_oggetto === this.configOggetto?.ricerca_oggetto &&
            item?.flg_nuovo_oggetto === infoCreazione?.flg_nuovo_oggetto
        );
      if (configItem?.obbligatorio) {
        if (!element?.note_atto_precedente) {
          faultyElementIndexes_mandatory?.push(index + 1);
        }
      }
    });

    this.associazioniOggettoIstanza?.forEach((element, index) => {
      // check modifiche geometrie Geeco
      let modeGeo = element.flg_geo_modificato;
      if (modeGeo) {
        // recuperiamo i messaggi a livello di operaservice
        // this.messageService.showMessage('P1GIS', this.OI_C.OPERA_CONTAINER, true);
        modeGeo = !modeGeo;
      }

      // check dati_catastali
      element.ubicazione_oggetto.forEach((ubi) => {
        if (ubi.dati_catastali?.some((catasto) => catasto.to_be_validated)) {
          faultyElementIndexes_validated.push(index + 1);
        }

        if (
          this.configOggetto?.dettaglio_oggetto?.dati_catastali?.obbligatorio
        ) {
          if (!ubi.dati_catastali || ubi.dati_catastali?.length === 0) {
            faultyElementIndexes_mandatory.push(index + 1);
          }
        }
      });
    });

    if (faultyElementIndexes_mandatory.length > 0) {
      finalCheck = false;
      if (showErrors) {
        if (this.associazioniOggettoIstanza.length === 1) {
          this._messageService.showMessage(
            'E001',
            this.OI_C.OPERA_CONTAINER,
            true
          );
        } else {
          let ph_mandatory = null;
          faultyElementIndexes_mandatory = [
            ...new Set(faultyElementIndexes_mandatory),
          ];
          faultyElementIndexes_mandatory.forEach((num) => {
            if (!ph_mandatory) {
              ph_mandatory = num + '<sup>o</sup>';
            } else {
              ph_mandatory += `, ${num}<sup>o</sup>`;
            }
          });

          const swapPh = {
            ph: '{PH_NUM_ORDINAMENTO_ELEM}',
            swap: ph_mandatory,
          };

          this._messageService.showMessage(
            'E047',
            this.OI_C.OPERA_CONTAINER,
            false,
            swapPh
          );
        }
      }
    }

    if (faultyElementIndexes_validated.length > 0) {
      finalCheck = false;
      if (showErrors) {
        if (this.associazioniOggettoIstanza?.length === 1) {
          this._messageService.showMessage(
            'E059',
            this.OI_C.OPERA_CONTAINER,
            true
          );
        } else {
          let ph_validated = null;
          faultyElementIndexes_validated = [
            ...new Set(faultyElementIndexes_validated),
          ];
          faultyElementIndexes_validated.forEach((num) => {
            if (!ph_validated) {
              ph_validated = num + '<sup>o</sup>';
            } else {
              ph_validated += `, ${num}<sup>o</sup>`;
            }
          });

          const swapPh = {
            ph: '{PH_NUM_ORDINAMENTO_ELEM}',
            swap: ph_validated,
          };

          this._messageService.showMessage(
            'E058',
            this.OI_C.OPERA_CONTAINER,
            false,
            swapPh
          );
        }
      }
    }

    /** SCRIVA-1273 */
    if (this.checkNuoveGeometrieAvanti(showErrors)) {
      finalCheck = false;
    }

    return finalCheck;
  }

  private _checkAssociazioniLiv1(showErrors: boolean) {
    let finalCheck = true;
    if (
      this.opereSelection === this.numerazione.MULTIPLO ||
      this.opereSelection === this.numerazione.SINGOLO
    ) {
      const check = this.associazioniOggettoIstanza?.some(
        (elem) => elem.ind_livello === 1
      );
      if (!check) {
        finalCheck = false;
        if (showErrors) {
          this._messageService.showMessage(
            'E011',
            this.OI_C.OPERA_CONTAINER,
            false
          );
        }
      }
    }
    return finalCheck;
  }

  //#endregion "STEP CHECK"

  // #region "STEP AVANTI"

  /**
   * Funzione che gestisce le logiche di verifica e/o salvataggio per poter procedere al prossimo step.
  @override 
  */
  protected onAvanti() {
    // Verifico che l'utente sia effettivamente abilitato alla scrittura sul quadro
    const canWrite: boolean =
      this.attoreGestioneIstanza === this.gestioneEnum.WRITE;
    // Verifico se l'utente può proseguire con le logiche di verifica e aggiornamento dati

    if (canWrite) {
      const isButtonInteract = this.isButtonInteract(true);
      if (isButtonInteract) {
        this.isStepValid().subscribe((isValid)=>{
          if(isValid){
            this.salvaDatiQuadro(true);
          }
        })
       
      } else {
        // Non può modificare i dati, lo faccio procedere al prossimo quadro
        this.goToStep(this.stepIndex);
      }
    }
  }

  
  /**
   * @return Observable<boolean> definisce se lo step è valido o no
   * @override
   */
  protected isStepValid(): Observable<boolean> {
    const isButtonInteract = this.isButtonInteract(true);
    if (isButtonInteract) {
      return this.verificaDatiPagina().pipe(
        switchMap((oggettiIstanzaChecked: OggettoIstanza[]) => {
          return this.verificaGeometrieAggiornate();
        }),
        tap(() => this.spinner.hide()), // Nasconde lo spinner dopo la verifica delle geometrie
        catchError((error: ScrivaServerError) => {
          this.spinner.hide();
          return of(false);
        })
      );
    } else {
      return of(false);
    }
  }

  /**
   * Funzione specifica di supporto per avanzare allo step successivo.
   * Nel flusso verrà gestita la seguente logica:
   * 1) Verrà verifica l'autorità competente rispetto ai dati inseriti dall'utente;
   * 2) Verranno verificate le geometrie inserite.
   * 3) A seconda del tipo di geometrie inserite, ci potrà essere:
   *    - Un blocco del flusso;
   *    - Una richiesta di conferma di avanzamento;
   *    - Un proseguimento diretto al salvatagio e avanzamento al prossimo step;
   */
  private verificaDatiPagina(): Observable<any> {
    // Recupero le informazioni per generare i parametri di verifica per il check_ac
    const idIst: number = this.idIstanza;
    const qdrConfig: any = this.qdr_config;
    // Creo gli oggetti di configurazione per la gestione della verifica delle autorità compententi
    // # Parametri servizio check_ac
    const checkACParams: ICheckACParams = this.parametriCheckAutoritaCompetenti(
      idIst,
      qdrConfig
    );

    // # Parametri gestione segnalazione
    const gestioneErrore: IServiziErrorConfig = {
      target: this.OI_C.OPERA_CONTAINER,
      defaultCode: ScrivaCodesMesseges.E100,
      autoFade: false,
    };

    // Lancio lo spinner di caricamento
    this.spinner.show();
    // Richiamo il servizio di verifica dell'AC con la gestione di eventuali errori
    return this._opera
      .checkCompetenzeOggettoSegnalazione(checkACParams, gestioneErrore)
      .pipe(
        switchMap((competenzeTerritorio: CompetenzaTerritorio[]) => {
          // A seguito del check delle autorità competenti, verifico le geometrie
          const codAdemp: string = this.codAdempimento;
          const idIst: number = this.idIstanza;
          // Ritorno il check chiamando il servizio, gestendo singolarmente gli errori per evitare una possibile multi segnalazione nella subscribe
          return this.checkGeometrie(codAdemp, idIst);
          // #
        }),
        tap((oggettiIstanzaChecked: OggettoIstanza[]) => {
          // Assegno localmente le informazioni ottenute dal check delle geometrie
          this.dataQuadro = oggettiIstanzaChecked;
          // #
        })
      );
  }

  /**
   * Funzione di comodo che genera le informazioni per la verifica delle autorità componenti.
   * @param idIstanza number con l'id istanza per la verifica.
   * @param qdrConfig any con la configurazione del quadro.
   * @returns ICheckACParams con i parametri per la verifica.
   */
  private parametriCheckAutoritaCompetenti(
    idIstanza: number,
    qdrConfig: any
  ): ICheckACParams {
    // Recupero dalle configurazioni del quadro le informazioni per il check di autorità competenti
    const tipoSelezione = qdrConfig.IndTipoSelezioneAC || null;
    const ac3 = qdrConfig.IndTipoCalcoloAC == 'AC3' ? true : false;
    // Creo gli oggetti di configurazione per la gestione della verifica delle autorità compententi
    // # Parametri servizio check_ac
    const checkACParams: ICheckACParams = {
      calcoloAC3: ac3,
      idIstanza: idIstanza,
      tipoSelezioneAC: tipoSelezione,
    };

    // Ritorno l'oggetto generato
    return checkACParams;
  }

  /**
   * Funzione pensata per gestire in maniera specifica il check delle geometrie con eventuale segnalazione degli errori.
   * @param codAdempimento string con il codice adempimento per il check delle geometrie.
   * @param idIstanza number con il riferimento all'id istanza per il check delle geometrie.
   * @returns Observable<OggettoIstanza[]> con il risultato della chiamata.
   */
  private checkGeometrie(
    codAdempimento: string,
    idIstanza: number
  ): Observable<OggettoIstanza[]> {
    // Ritorno il check chiamando il servizio, gestendo singolarmente gli errori per evitare una possibile multi segnalazione nella subscribe
    return this._ambito.checkGeometrie(codAdempimento, idIstanza).pipe(
      catchError((e: ScrivaServerError) => {
        // Richiamo la funzione di gestione dell'errore
        this.onServiziError(e);
        // Ritorno l'errore
        return throwError(e);
        // #
      })
    );
    // #
  }

  /**
   * Funzione specifica che verifica lo stato delle informazioni a seguito delle geometrie verificate e aggiornate.
   * A seconda del tipo di geometrie aggiornate (definite a livello globale del componente), ci potrà essere:
   *  - Un blocco del flusso;
   *  - Una richiesta di conferma di avanzamento;
   *  - Un proseguimento diretto al salvatagio e avanzamento al prossimo step;
   * @jira SCRIVA-1430 Potrebbe essere superflua this.ambitoService.checkGeometrie, ma usa sia dataQuadro che associaOggettiIstanza e allo stato attuale non effettuo modifiche che andrebbero verificate con un numero piu' elevato di test.
   */
  private verificaGeometrieAggiornate(): Observable<any> {
    // Verifico se non ci sono geometrie presenti (qualcosa con il valore di indGeo)
    const geometrieNotPresente: boolean = this._checkGeometrieNotPresent();
    // Verifico il check delle geometrie
    if (!geometrieNotPresente) {
      // Interrompo il flusso dati
      return of(true);
    }

    // Recupero la geoMode impostata a livello di servizio
    const geoMode: string = this.geoMode;

    // Verifico i possibili valori per la modalità delle geometrie
    if (geoMode === TipiGeoriferimento.obbligatorio_M) {
      return this.gestisciGeoModeCaseM();
    } else if (geoMode === TipiGeoriferimento.nonEseguito_P) {
      return this.gestisciGeoModeCaseP();
    } else {
      return this.gestisciGeoModeDefault(geoMode);
    }
    // #
  }

  /**
   * Funzione di comodo che gestisce la verifica geometrie nel caso in cui la geoMode sia: obbligatoria (M).
   * La funzione è stata pensata per lavorare strettamente con la funzione padre: verificaGeometrieAggiornate.
   * Quindi alcuni controlli vengono dati ignorati, poiché eseguiti della funzione chiamante.
   */
  private gestisciGeoModeCaseM() {
    // Il georiferimento è obbligatorio, segnalo errore
    const codeMsg = ScrivaCodesMesseges.E013;
    const target = this.OI_C.OPERA_CONTAINER;
    const fade = true;
    // Lancio la funzione per gestire la segnalazione
    this._messageService.showMessage(codeMsg, target, fade);
    return of(false);
  }

  /**
   * Funzione di comodo che gestisce la verifica geometrie nel caso in cui la geoMode sia: richiedi conferma (P).
   * La funzione è stata pensata per lavorare strettamente con la funzione padre: verificaGeometrieAggiornate.
   * Quindi alcuni controlli vengono dati ignorati, poiché eseguiti della funzione chiamante.
   */
  private gestisciGeoModeCaseP() {
    // Il geriferimento chiede la conferma specifica, compongo i parametri per la conferma utente
    const title: string = this.OI_C.CONFERMA_TITLE_GEOREF_P;
    const codeModal = ScrivaCodesMesseges.A1GIS;
    const onConfirm = () => {
      // Alla conferma, lancio il salvataggio dati
      return of(true);
      // #
    };
    // Creo la configurazione per l'apertura della modale di conferma
    let modalParams: ModalConfig;
    modalParams = this.C_C.confermaModalConfigs(title, codeModal, onConfirm);
    // Richiamo la funzione per aprire la modale e chiedere conferma all'utente se vuole proseguire con il salvataggio dati
    this._messageService.showConfirmation(modalParams);
    return of(false);
  }

  /**
   * Funzione di comodo che gestisce un messaggio di warning per la mancanta gestione di una modalità di Georeferenzazione.
   * @param geoMode string con la modalità di georeferenzazione non gestita.
   */
  private gestisciGeoModeDefault(geoMode: string) {
    // Casistica non gestita, non viene fatto niente, ma per sicurezza loggo un warning
    const t: string = `[opera-intervento.component.ts] gestisciGeoModeDefault - Tipo georiferimento non gestito.`;
    const b: string = `Georifermento: ${geoMode}`;
    this._logger.warning(t, b);
    return of(false);
  }

  // #endregion "STEP AVANTI"

  ngOnDestroy(): void {
    super.ngOnDestroy();
  }

  /**
   * #########
   * UTILITIES
   * #########
   */

  /**
   * Funzione di supporto che gestisce gli errori generati dalla chiamata ai servizi.
   * @param e ScrivaServerError con l'oggetto d'errore generato dalla chiamata.
   * @param target string con l'identificativo per un target specifico.
   */
  private onServiziError(e?: ScrivaServerError, target?: string) {
    // Definisco le informazioni per il messaggio d'errore
    const errorCode: string = e?.error?.code;
    const m: string = errorCode ? errorCode : ScrivaCodesMesseges.E100;
    const fade: boolean = !errorCode;
    target = target ?? this.OI_C.OPERA_CONTAINER;
    // Richiamo la visualizzazione del messaggio
    this._messageService.showMessage(m, target, fade);
  }

  /**
   * Funzione di comodo per gestire le logiche di attivazione di uno spinner in maniera dinamica.
   * @param useSpinner boolean con l'indicazione per l'attivazione dello spinner. Per default è: true.
   */
  private showSpinner(useSpinner: boolean = true) {
    // Verifico se devo gestire lo spinner
    if (useSpinner) {
      // Apro lo spinner di caricamento
      this.spinner.show();
      // #
    }
  }

  /**
   * Funzione di comodo per gestire le logiche di disattivazione di uno spinner in maniera dinamica.
   * @param useSpinner boolean con l'indicazione per l'attivazione dello spinner. Per default è: true.
   */
  private hideSpinner(useSpinner: boolean = true) {
    // Verifico se devo gestire lo spinner
    if (useSpinner) {
      // Chiudo lo spinner di caricamento
      this.spinner.hide();
      // #
    }
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che recupera il codice tipo adempimento per sapere su quale adempimento si sta lavorando.
   * @returns string con il dato recuperato.
   */
  get codTipoAdempimento(): string {
    // Estraggo dall'oggetto istanza il dato
    return this.istanza?.adempimento?.tipo_adempimento?.cod_tipo_adempimento;
  }
}
