/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { FormioForm } from 'angular-formio/formio.common';
import { compact } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable, of, Subscription } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { AdvancedActionsRiepilogoModalComponent } from 'src/app/features/advanced-actions/components/advanced-actions-riepilogo-modal/advanced-actions-riepilogo-modal.component';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  IFunzionarioAutorizzato,
  Help,
  Istanza,
  IstanzaCompetenza,
  IstanzaEvento,
  IstanzaStato,
  Quadro,
  StepConfig,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import {
  AttoreGestioneIstanzaEnum,
  StatoIstanzaEnum,
  TipoEventoEnum,
} from 'src/app/shared/utils';
import { ScrivaServerError } from '../../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../../core/enums/scriva-codes-messages.enums';
import { ScrivaComponenteApp } from '../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { IstanzaResponsabile } from '../../../../../shared/models/istanza/istanza-responsabile.model';
import { Allegato } from '../../../models';
import { AllegatiService } from '../../../services';
import { RiepilogoConsts } from './utilities/riepilogo.consts';
import {
  ICheckCategorieAllegatiReq,
  ICheckCategorieAllegatiRes,
  ICheckScaricaModuloReq,
  ICheckScaricaModuloRes,
  IDichiarazioneFormio,
} from './utilities/riepilogo.interfaces';
import { LoggerService } from '../../../../../shared/services/logger.service';
import { StepperIstanzaComponent } from 'src/app/shared/components/stepper-istanza/stepper-istanza.component';
import { PresentazioneIstanzaService } from '../../../services/presentazione-istanza/presentazione-istanza.service';

@Component({
  selector: 'app-riepilogo',
  templateUrl: './riepilogo.component.html',
  styleUrls: ['./riepilogo.component.scss'],
})
export class RiepilogoComponent
  extends StepperIstanzaComponent
  implements OnInit
{
  /** RiepilogoConsts con le informazioni di configurazione/costanti del componente. */
  R_C: RiepilogoConsts;

  ColumnMode = ColumnMode;

  codMaschera = '.MSCR010F';
  funzionario: IFunzionarioAutorizzato;

  adempimento: Adempimento;

  messageSuccessBO = false;

  quadri: Quadro[];
  jsonData = null;
  stepLabels = [];
  quadriRiepilogo: { formioForm: FormioForm; idQuadro: number }[] = [];
  submissions = [];
  formsReady = false;

  tipoEventoEnum = TipoEventoEnum;
  gestioneEnum = AttoreGestioneIstanzaEnum;

  istanza: Istanza;
  acPraticaList: IstanzaCompetenza[];
  acListString: string;

  moduloFirmato: Allegato;
  nomeElencoAllegati: string;
  codElencoAllegati: string;
  missingAllegatiFlag = false;

  allowDownloadIstanza = false;

  helpMaschera: Help[];
  helpQuadro: Help[];

  infoEditForm = false;
  infoText: string;

  modalRef: NgbModalRef;
  modalEventSubscription: Subscription;

  allegatiScaricati: boolean = false;

  showBoxPresentazione: boolean = false;
  showBtnDatiProc: boolean = false;

  constructor(

    private adempimentoService: AdempimentoService,
    private allegatiService: AllegatiService,
    private modalService: NgbModal,
    private router: Router,
    private route: ActivatedRoute,
    private _logger: LoggerService,
    presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) injConfig: StepConfig,
    messageService: MessageService,
    helpService: HelpService,
    istanzaService: IstanzaService,
    authStoreService: AuthStoreService,
    stepManagerService: StepManagerService,
    spinner: NgxSpinnerService,
  ) {
    super(
      presentazioneIstanzaService,
      injConfig,
      messageService,
      helpService,
      istanzaService,
      authStoreService,
      stepManagerService,
      spinner,
    );
    this.setVisibilityButtonBack(false);
  }

  // #region 'Setup componente'

  /**
   * Funzione di setup con le logiche di configurazione del componente.
   * @override
   */
  protected setupComponente() {
    // Genero la classe per le configurazioni FE del componente
    this.R_C = new RiepilogoConsts();

    // Richiamo la funzione di setup per la componente applicativa
    this.setupComponenteApp();

    this.setVisibilityButtonBack(false);
    this.setVisibilityButtonNext(false);
  }

  /**
   * Funzione di setup per il tipo di componente applicativa.
   */
  private setupComponenteApp() {
    // Recupero dal servizio il tipo di componente applicativa
    this.componente = this.authStoreService.getComponente();

    // Verifico se la componente è BO
    if (this.componente === ScrivaComponenteApp.backOffice) {
      // Siamo nella modalità BO
      this.isFrontOffice = false;
      // Recupero i dati del funzionario
      this.funzionario = this.authStoreService.getFunzionarioAutorizzato();
      // #
    } else {
      // Siamo nella modalità FO
      this.isFrontOffice = true;
    }
  }

  // #endregion 'Setup componente'

  // #region 'Init componente'

  ngOnInit() {
    // Lancio la funzione di init del componente
    this.initComponente();
  }

  /**
   * Funzione che gestisce le logiche di init del componente.
   */
  private initComponente() {
    // Lancio l'init dati per le informazioni generiche del componente
    this.initDatiGenerici();

    // Richiamo la logica di caricamento dei dati per istanza ed helpers
    this.initIstanzaEdHelpers();

    // SCRIVA-1426 clicchi su torni a un quadro specifico o sullo stepper e il riepilogo deve essere ancora cliccabile
    this.setStepCompletedEmit('RiepilogoComponent', true);
  }

  /**
   * Funzione di init che gestisce il set dei dati generici e di utility del componente.
   */
  private initDatiGenerici() {
    // Imposto il codice maschera all'interno del servizio di helper
    this.helpService.setCodMaschera(this.codMaschera);
    // Resetto il codice contesto nel servizio di helper
    this.helpService.setCodContesto(undefined);
  }

  /**
   * Funzione di init che carica i dati per istanza ed helper.
   * La funzione gestisce il caricamento di entrambe le informazioni e il relativo spinner di caricamento.
   */
  private initIstanzaEdHelpers() {
    // Caricamento dati per l'istanza
    const loadIstanza: Observable<any> = this.caricaDatiIstanza();
    // Caricamento dati per gli helper
    const loadHelper: Observable<any> = this.caricaDatiHelper();

    // Avvio lo spinner per la chiamata
    this.spinner.show();
    // Lancio le chiamate per scaricare le informazioni per il riepilogo
    forkJoin({ loadIstanza, loadHelper }).subscribe({
      next: (res: { loadIstanza: any; loadHelper: any }) => {
        // Chiudo lo spinner di caricamento
        this.spinner.hide();
        // #
      },
      error: (err: ScrivaServerError) => {
        // Chiudo lo spinner di caricamento, l'errore è gestito nelle chiamate
        this.spinner.hide();
      },
    });
  }

  /**
   * Funzione di init che carica i dati che va a recuperare le informazioni dell'istanza e definisce le logiche di avvio.
   * La funzione prevede al suo interno già tutte le logiche di set dati all'interno del componente.
   * @returns Observable<any> con il risultato dei dati caricati dell'istanza.
   */
  private caricaDatiIstanza(): Observable<any> {
    // Gestisco la logica per il set della modalità di gestione del quadro
    this.setGestioneQuadro();

    // Lancio e ritorno tutte le logiche di gestione
    return forkJoin(
      this.istanzaService.getIstanzaById(this.idIstanza),
      this.istanzaService
        .getResponsabili(this.idIstanza)
        .pipe(catchError((err) => of([])))
    ).pipe(
      tap((res: any[]) => {
        this.istanza = res[0];
        this.istanza.responsabili_istanza = res[1];
        this.jsonData = JSON.parse(this.istanza.json_data);
        this.adempimento = this.istanza.adempimento;
        this.setInfoElencoAllegati();
        this.infoText = this.buildMessage(this.istanza?.cod_istanza);
        this.messageSuccessBO = this.istanzaService.messageSuccessBO;
      }),
      switchMap(() => {
        const getACPratica =
          this.adempimentoService.getAutoritaCompetenteByIstanza(
            this.idIstanza
          );
        const getQuadri = this.stepManagerService.getQuadri(
          this.adempimento.cod_adempimento,
          this.istanza
        );
        const getModuloFirmato = this.allegatiService.getAllAllegatiIstanza(
          this.idIstanza
        );
        return forkJoin([getACPratica, getQuadri, getModuloFirmato]);

        /* const getCodConclProc = this.configurazioniService.getConfigurazioniByInfoAndChiave(this.adempimento.cod_adempimento, 'AZ_TIPO_QUADRO', 'CONCLUDI_PROC');
          return forkJoin([getACPratica, getQuadri, getModuloFirmato, getCodConclProc]); */
      }),
      tap((res: any[]) => {
        // Eseguo le logiche di "success"
        // Recupero i dati dallo swithMap: getACPratica;
        this.acPraticaList = res[0];
        // Recupero i dati dallo swithMap: getQuadri; Riordino i quadri secondo ordinamento_template_quadro
        this.quadri = res[1].quadri.sort(
          (a, b) =>
            a.ordinamento_template_quadro - b.ordinamento_template_quadro
        );
        // Recupero i dati dallo swithMap: getModuloFirmato;
        this.moduloFirmato = res[2].find(
          (allegato) =>
            allegato.tipologia_allegato.cod_tipologia_allegato === 'MOD_IST'
        );

        // Lancio le funzioni di gestione per il componente e i dati
        this.setBoxPresentazione();
        this.setVisibilityBtnDatiProc();
        this.buildAutCompetentiString();
        this.buildForms();
        // #
      }),
      catchError((err: ScrivaServerError) => {
        // Definisco le informazioni per la gestione degli errori
        const target: string = 'headerCard';
        // Richiamo la funzione per la gestione degli errori dei servizi
        this.onServiziError(err, target);
        // Proseguo ritornando l'errore, ma non bloccando le logiche
        return of(err);
      })
    );
  }

  /**
   * Funzione che esegue le logiche generali di flusso di gestione dei dati per gli helper.
   * @returns Observable<any> con il termine delle logiche di caricamento degli helper.
   */
  private caricaDatiHelper(): Observable<any> {
    // Lancio e ritorno la logica di gestione dell'helper
    return this.stepManagerService
      .getQuadroByIdIstanza(this.idIstanza, this.idTemplateQuadro)
      .pipe(
        // @what-the-faq => ma perché viene chiamato il quadro istanza by id istanza se poi i dati di ritorno non vengono usati?
        switchMap((res: any) => {
          // Eseguo le logiche per la "success"
          return this.getHelpers();
          // #
        }),
        catchError((err: ScrivaServerError) => {
          // Definisco le informazioni per la gestione degli errori
          const target: string = 'formContainer';
          // Richiamo la funzione per la gestione degli errori dei servizi
          this.onServiziError(err, target);
          // Proseguo ritornando l'errore, ma non bloccando le logiche
          return of(err);
        })
      );
  }

  /**
   * Funzione che scarica le informazioni relative agli helper per il componente.
   * La funzione è pensata per impostare direttamente le informazioni degli helper, è necessario solo fare la subscription alla funzione.
   * @returns Observable<any> con le informazioni scaricati per gli helper.
   */
  private getHelpers(): Observable<any> {
    // Definisco la chiave della maschera
    const mascheraKey: string = `${this.componente}${this.codMaschera}`;
    // Recupero l'helper della maschera
    const getHelpMaschera = this.helpService.getHelpByChiave(mascheraKey);

    // Definisco la chiave del quadro composta dai "domini" tramite configurazioni
    const comp: string = this.componente;
    const codTipAdemp: string =
      this.adempimento.tipo_adempimento.cod_tipo_adempimento;
    const codAdemp: string = this.adempimento.cod_adempimento;
    const codQuadro: string = 'QDR_DETT_OGGETTO';
    const quadroKey: string = `${comp}.${codTipAdemp}.${codAdemp}.${codQuadro}`;
    // URL to get Help description, nota per @Ismaele @Marco, ho forzato il codQuadro del Dettaglio Progetto
    const getHelpQuadro = this.helpService.getHelpByChiave(quadroKey);

    // Effettuo la forkjoin e ritorno la chiamata
    return forkJoin([getHelpMaschera, getHelpQuadro]).pipe(
      tap((res: any[]) => {
        // Recupero i dati da: getHelpMaschera
        this.helpMaschera = res[0];
        // Recupero i dati da getHelpQuadro
        this.helpQuadro = res[1];
        // #
      }),
      catchError((err: ScrivaServerError) => {
        // Definisco le informazioni per la gestione degli errori
        const target: string = 'formContainer';
        // Richiamo la funzione per la gestione degli errori dei servizi
        this.onServiziError(err, target);
        // Proseguo ritornando l'errore, ma non bloccando le logiche
        return of(err);
      })
    );
  }

  /**
   * #################
   * SET DATI SINCRONI
   * #################
   */

  setInfoElencoAllegati() {
    const elencoAllegati =
      this.jsonData.QDR_RIEPILOGO.QDR_ALLEGATO?.documentiDiSistema?.find(
        (allegato) => allegato.codTipologia === 'ELENCO_ALLEGATI'
      );
    this.nomeElencoAllegati = elencoAllegati?.nomeDocumento;
    this.codElencoAllegati = elencoAllegati?.codAllegato;
  }

  /*   setBoxPresentazione() {
    const firstLevelArray = this.isFrontOffice
      ? this.istanzaService.getProfiliApp()
      : this.funzionario.funzionario_profilo;
    const secondLevelArrayName = this.isFrontOffice
      ? 'oggetti_app'
      : 'oggetto_app';

    firstLevelArray?.forEach((elem) => {
      if (
        elem[secondLevelArrayName]?.some(
          (item) => item.cod_oggetto_app === 'frm_presenta_istanza'
        )
      ) {
        this.showBoxPresentazione = true;
      }
    });
  } */

  setBoxPresentazione() {
    // SCRIVA-1418 senza  tipi_adempimento_ogg_app o profili_app si rompeva js,
    // usiamo direttmaente metodo getIstanzaOggettoApp del servizio istanzaService
    this.showBoxPresentazione = !!this.istanzaService.getIstanzaOggettoApp(
      this.istanza,
      'frm_presenta_istanza'
    );
  }

  setVisibilityBtnDatiProc() {
    // SCRIVA-1418 senza  tipi_adempimento_ogg_app o profili_app si rompeva js,
    // usiamo direttmaente metodo getIstanzaOggettoApp del servizio istanzaService
    this.showBtnDatiProc = !!this.istanzaService.getIstanzaOggettoApp(
      this.istanza,
      'btn_gestisci_info_gen'
    );
  }

  buildAutCompetentiString() {
    const tempArray = [];
    this.acPraticaList.forEach((ac) => {
      if (ac.flg_autorita_principale) {
        tempArray.push(ac.competenza_territorio.des_competenza_territorio);
      }
    });
    this.acListString = tempArray.join('<br>');
  }

  /**
   * Funzione dedicata alla costruzione dei dati per i componenti con i dati di riepilogo.
   * Le informazioni verranno resettate e composte dalle logiche e i dati presenti al momento dell'esecuzione del flusso.
   */
  buildForms() {
    // SCRIVA-1426
    this.quadriRiepilogo = [];
    // Resetto l'array contenente le label di testata per le sezione di riepilogo dei quadri
    this.stepLabels = [];
    // Resetto l'array con le informazioni che verranno passate ai componenti di riepilogo
    this.submissions = [];

    // Ciclo la lista dei quadri per la composizione delle informazioni da utilizzare per la visualizzazione dati
    this.quadri.forEach((quadro) => {
      if (
        quadro.json_configura_riepilogo &&
        (this.jsonData.QDR_RIEPILOGO[quadro.cod_quadro] ||
          (this.jsonData.QDR_RIEPILOGO[quadro.tipo_quadro.cod_tipo_quadro] &&
            this.jsonData.QDR_RIEPILOGO[quadro.tipo_quadro.cod_tipo_quadro][
              quadro.cod_quadro
            ]))
      ) {
        this.quadriRiepilogo.push({
          formioForm: JSON.parse(quadro.json_configura_riepilogo),
          idQuadro: quadro.id_quadro,
        });

        let dataRiepilogo;

        if (quadro.tipo_quadro.cod_tipo_quadro === quadro.cod_quadro) {
          dataRiepilogo =
            this.jsonData.QDR_RIEPILOGO[quadro.tipo_quadro.cod_tipo_quadro];
        } else {
          dataRiepilogo =
            this.jsonData.QDR_RIEPILOGO[quadro.tipo_quadro.cod_tipo_quadro][
              quadro.cod_quadro
            ];
        }

        // Definisco il flag che indica se, per i quadri formio, devono essere in sola lettura
        if (this.attoreGestioneIstanza === this.gestioneEnum.WRITE) {
          // Scrittura abilitata
          dataRiepilogo.disableFlag = 'ENABLED';
        } else {
          // Sola lettura abilitata
          dataRiepilogo.disableFlag = 'DISABLED';
        }

        // Aggiungo l'oggetto contenente i dati di riepilogo da visualizzare
        this.submissions.push({ data: dataRiepilogo });

        // Aggiungo la label grafica del quadro da visualizzare al di fuori dei dati di dettaglio
        this.stepLabels.push(
          JSON.parse(quadro.json_configura_quadro)['label'].toUpperCase()
        );
      }
    });

    // Loggo la struttura dei quadri riepilogo per la verifica delle strutture
    const t = `Strutture quadri riepilogo`;
    const b = {
      quadri: this.quadri,
      quadriRiepilogo: this.quadriRiepilogo,
      datiQuadriRiepilogo: this.submissions,
    };
    this._logger.success(t, b);

    // style from .scss file doesn't apply
    const styleRules = document.createElement('style');
    styleRules.innerHTML =
      '.formio-box h2 { font-size: 1rem; line-height: 1.5; margin: 0; }';
    document.body.appendChild(styleRules);

    this.formsReady = true;
  }

  // #region 'Init componente'

  onChange(data) {}

  onCustomEvent(customEvent) {
    switch (customEvent.type) {
      case 'helpBtnClick':
        this.formioHelp(customEvent.component.key);
        break;

      default:
        break;
    }
  }

  //on click di btn Help(modal)
  formioHelp(key: string) {
    // this line removes the JS_HELP. prefix
    key = key.substring(8);

    // chiave per controllo Help, nota per @Ismaele @Marco, ho forzato il codQuadro del Dettaglio Progetto
    const chiaveHelp = `${this.componente}.${this.adempimento.tipo_adempimento.cod_tipo_adempimento}.${this.adempimento.cod_adempimento}.QDR_DETT_OGGETTO.${key}`;
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
   * Funzione che effettua il check delle categorie obbligatorie e le categorie adempimento.
   * @returns ICheckCategorieAllegatiRes con le liste ritornate dai check.
   */
  private checkCategorieObbligatorieEAdempimento(): Observable<ICheckCategorieAllegatiRes> {
    // Definisco le chiamate di check per le categorie dell'istanza
    const checkCategorieObbligatorie =
      this.allegatiService.checkCategorieObbligatorie(this.idIstanza);
    const checkCategorieAdempimento =
      this.allegatiService.checkCategorieAdempimento(this.idIstanza);

    // Definisco le richieste per il controllo
    const req: ICheckCategorieAllegatiReq = {
      categorieObbligatorie: checkCategorieObbligatorie,
      categorieAdempimento: checkCategorieAdempimento,
    };

    // Lancio le verifiche dati
    return forkJoin(req).pipe(
      tap((res: ICheckCategorieAllegatiRes) => {
        // Estraggo dalla risposta le informazioni sulle categorie
        const catObbligatorie = res.categorieObbligatorie;
        const catAdempimento = res.categorieAdempimento;
        // Lancio il check degli allegati sulla risposta del servizio
        this.checkAllegatiList(catObbligatorie, catAdempimento);
        // #
      })
    );
  }

  /**
   * Funzione che esegue le funzioni di check per lo scarico del modulo e ritorna una risposta asincrona.
   */
  private checkScaricaModulo(): Observable<ICheckScaricaModuloRes> {
    // Il check per le dichiarazioni obbligatorie lo faccio diventare asincrono
    const checkDO = of(this.checkDichiarazioniObbligatorie());
    const checkI = this.istanzaService.checkIstanza(
      this.idIstanza,
      this.adempimento.cod_adempimento
    );
    // Richiamo le funzioni di check per lo scarica modulo
    const req: ICheckScaricaModuloReq = {
      checkDichiarazioniObbligatorie: checkDO,
      checkIstanza: checkI,
    };
    // Ritorno l'insieme delle chiamate
    return forkJoin(req);
    // #
  }

  /**
   * Funzione che gestisce le informazioni a seguito del check per lo scarico modulo e produce una stringa da visualizzare all'utente SE sono risultati degli errori.
   * @param res ICheckScaricaModuloRes con i risultati dei check effettauti.
   */
  private onCheckScaricaModulo(res: ICheckScaricaModuloRes) {
    // # NOTA BENE: il check istanza, se non va bene, manda in errore la chiamata, gestendo in modo a se stante la segnalazione tramite "error" della subscribe.
    // Definisco una variabile contenitore per l'eventuale messaggio da visualizzare
    let segnalazione: string;

    // Recupero le condizioni sui check dati
    const dichObblErr = !res.checkDichiarazioniObbligatorie;
    const allegatiMancanti = this.missingAllegatiFlag;
    // Verifico se uno dei due check è risultato in "errore"
    if (dichObblErr || allegatiMancanti) {
      // Definisco un array con le infomazioni di placeholder
      let phs: string[] = [];
      // Verifico i check ed aggiungo uno specifico placholder a seconda del flag
      dichObblErr ? phs.push('DICHIARAZIONI') : false;
      allegatiMancanti ? phs.push('ALLEGATI') : false;
      // Creo la stringa di placeholder da passare al servizio
      const swap = `${phs.join(', ')}`;
      // Definisco l'oggetto per effettuare il placholder delle informazioni
      const swapPh = { ph: '{PH_DES_TIPO_QUADRO}', swap };
      // Recupero il messaggio
      const m = this._messageService.getMessaggioPlaceholders('A006', swapPh);
      // Assegno il messaggio alla segnalazione
      segnalazione = m.des_testo_messaggio;
    }

    // Ritorno l'eventuale segnalazione
    return segnalazione;
    // #
  }

  /**
   * Funzione collegata al pulsante di scarico degli allegati dell'istanza.
   */
  onScaricaAllegati() {
    // Lancio lo spinner di caricamento
    this.spinner.show();

    // Verifico categorie obbligatorie ed adempimenti
    this.checkCategorieObbligatorieEAdempimento()
      .pipe(
        switchMap(() => {
          // Effettuo il chekc della delega per l'istanza
          return this.allegatiService.checkDelega(this.idIstanza);
          // #
        })
      )
      .subscribe(
        (res: any) => {
          // Nascondo lo spinner di caricamento
          this.spinner.hide();
          // Lancio il downloa dell'elenco allegati
          this.downloadElencoAllegati();
          // #
        },
        (err) => {
          // Nascondo lo spinner di caricamento
          this.spinner.hide();
          this.showErrorsQuadroConCodeENoCode(err, 'actionCard');
        }
      );
  }

  checkAllegatiList(list1, list2) {
    // Resetto il flag
    this.missingAllegatiFlag = false;
    // se non è presente il cod_tipo_quadro QDR_ALLEGATO non darò mai errore missingAllegatiFlag
    if (
      !this.quadri.find((q) => q.tipo_quadro.cod_tipo_quadro == 'QDR_ALLEGATO')
    ) {
      return;
    }

    if (list1.length > 0) {
      this.spinner.hide();
      let list = '<br>';
      list1.forEach((category, index) => {
        if (index === list1.length - 1) {
          list = list + category.des_categoria_allegato + '.';
        } else {
          list = list + category.des_categoria_allegato + ', ';
        }
      });
      const swapPh = {
        ph: '[{PH_CATEGORIA}].',
        swap: list,
      };

      this._messageService.showMessage('E023', 'actionCard', false, swapPh);

      this.missingAllegatiFlag = true;
      return;
    }

    if (list2.length > 0) {
      this.spinner.hide();
      let list = '';
      list2.forEach((category, index) => {
        if (index === list2.length - 1) {
          list = list + category.des_categoria_allegato + '.';
        } else {
          list = list + category.des_categoria_allegato + ', ';
        }
      });
      const swapPh = {
        ph: '[{PH_CATEGORIA}]',
        swap: list,
      };

      this._messageService.showMessage('E024', 'actionCard', false, swapPh);

      this.missingAllegatiFlag = true;
      return;
    }
  }

  /**
   * Funzione collegata al pulsante "Scarica modulo".
   */
  onScaricaModulo() {
    // Verifico se posso scaricare il modulo
    this.checkScaricaModulo()
      .pipe(
        map((res: ICheckScaricaModuloRes) => {
          // Ritorno l'eventuale segnalazione
          return this.onCheckScaricaModulo(res);
          // #
        })
      )
      .subscribe({
        next: (segnalazione: string) => {
          // Verifico se c'è qualche segnalazione
          if (segnalazione) {
            // C'è da comunicare all'utente qualcosa, apro la modale
            this.apriConfermaModuloBozza(segnalazione);
            // #
          } else {
            // Niente segnalazioni, scarico direttamente
            this.downloadModulo();
          }
        },
        error: (e: any) => {
          // Verifico se c'è qualche segnalazione dal server
          if (e.error?.code) {
            // C'è da comunicare all'utente qualcosa, apro la modale
            this.apriConfermaModuloBozza(e.error.title);
            // #
          } else {
            // Altro tipo d'errore
            this._messageService.showMessage('E100', 'actionCard', true);
          }
        },
      });
  }

  /**
   * Funzione di comodo che gestisce la modale con la segnalazione di conferma scarico modulo se l'istanza è in bozza.
   * @param messaggio string con il messaggio da visualizzare.
   * @param title string con il titolo della modale.
   */
  private apriConfermaModuloBozza(
    messaggio: string,
    title: string = 'Attenzione'
  ) {
    // Apro la modale con la conferma di scarico del modulo anche se l'istanza è in bozza
    this._messageService.showConfirmation({
      title,
      codMess: null,
      content: messaggio,
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
            this.downloadModulo(true);
          },
        },
      ],
    });
  }

  /**
   * Funzione che verifica all'interno del quadro dichiarazioni, che ogni dichiarazione (dentro "elenco dichiarazioni") con flag "obbligatorio" a true, abbia il suo valore "check" a true.
   * @returns boolean che indica [true] che tutte le dichiarazioni sono state definite obbligatorie.
   */
  checkDichiarazioniObbligatorie() {
    // se non è presente il cod_tipo_quadro QDR_DICHIARAZIONE non darò mai errore
    if (
      !this.quadri.find(
        (q) => q.tipo_quadro.cod_tipo_quadro == 'QDR_DICHIARAZIONE'
      )
    ) {
      return true;
    }
    // Recupero l'oggetto del quadro dichiarazioni
    const qdrDichiarazioni = this.jsonData?.QDR_DICHIARAZIONE;
    // Se manca il quadro dichiarazioni il check fallisce
    if (!qdrDichiarazioni) {
      // Manca il quadro, lo considero errore
      return false;
    }
    // Estraggo dai dati del quadro le informazioni sulle dichiarazioni
    const dichiarazioni = qdrDichiarazioni.dichiarazioni ?? [];

    // Rimuovo dall'array possibili elementi vuoti
    const dichiarazioniValide = compact(dichiarazioni);
    // Filtro gli oggetti che risultano invalidi secondo la condizione: obbligatorio === true && check === false
    const dichiarazioniObblInvalide = dichiarazioniValide.filter(
      (dichiarazione: IDichiarazioneFormio) => {
        // Definisco le condizioni di filtro
        const check1 = dichiarazione.obbligatorio === true;
        const check2 = dichiarazione.check === false;
        // Ritorno le condizioni
        return check1 && check2;
      }
    );

    // Se esiste anche una sola dichiarazione invalida ritorno false
    return dichiarazioniObblInvalide.length == 0;
  }

  downloadElencoAllegati() {
    if (this.codElencoAllegati) {
      this.allegatiService.getAllegatoByCode(this.codElencoAllegati).subscribe(
        (res) => {
          const blob = new Blob([res], { type: 'application/pdf' });
          const url = window.URL.createObjectURL(blob);
          const fileName = this.nomeElencoAllegati;

          const a = document.createElement('a');
          a.href = url;
          a.download = fileName;
          a.click();
          window.URL.revokeObjectURL(url);
          a.remove();
          this.spinner.hide();
          this.allowDownloadIstanza = true;
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'actionCard');
        }
      );
    } else {
      this._messageService.showMessage('I022', 'actionCard', true);
      //messaggio I022 e abilito btn
      this.spinner.hide();
      this.allowDownloadIstanza = true;
    }

    // Definisco gli allegati come scaricati
    this.allegatiScaricati = true;
  }

  downloadModulo(flagBozza = false) {
    this.spinner.show();
    if (!flagBozza) {
      this._generaEvento(this.tipoEventoEnum.DA_FIRMARE, true);
    } else {
      this._downloadModuloIstanza();
    }
  }

  /**
   * Funzione che genera evento in base al tipo evento e permette di scaricare file
   *  solo se isToDownload e tipo evento è da firmare
   */
  private _generaEvento(
    tipoEvento: TipoEventoEnum,
    isToDownload: boolean = false
  ) {
    this.istanzaService.generaEvento(this.idIstanza, tipoEvento).subscribe(
      (response) => {
        this.attoreGestioneIstanza = response.headers.get('Attore-Gestione');
        this.istanzaService.setAttoreGestioneIstanza(
          this.attoreGestioneIstanza
        );
        this.aggiornaStato(response.body, tipoEvento);
        if (tipoEvento === this.tipoEventoEnum.DA_FIRMARE && isToDownload) {
          this._downloadModuloIstanza(tipoEvento);
        }
      },
      (error) => {
        this.showErrorsQuadroConCodeENoCode(error, 'actionCard');
      }
    );
  }

  private _downloadModuloIstanza(tipoEvento?: TipoEventoEnum) {
    this.istanzaService.downloadMuduloIstanza(this.idIstanza).subscribe(
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
        this.showErrorsQuadroConCodeENoCode(err, 'actionCard');
        // richiama la genera evento ma cambiando il tipo,
        // tipo CORREGGI non da la possibilità di scaricare il file
        // viene passato in input il tipo perché il download potrebbe non dover correggere l'evento
        if (tipoEvento && tipoEvento === this.tipoEventoEnum.DA_FIRMARE) {
          this._generaEvento(this.tipoEventoEnum.CORREGGI);
        }
      }
    );
  }

  downloadModuloFirmato() {
    this.spinner.show();
    this.allegatiService
      .getAllegatoByUuid(this.moduloFirmato.uuid_index)
      .subscribe(
        (response) => {
          const blob = new Blob([response], { type: 'application/pdf' });
          const url = window.URL.createObjectURL(blob);
          const fileName = this.moduloFirmato.nome_allegato;

          const a = document.createElement('a');
          a.href = url;
          a.download = fileName;
          a.click();
          window.URL.revokeObjectURL(url);
          a.remove();
          this.spinner.hide();
        },
        (err) => {
          this.spinner.hide();
          this.showErrorsQuadroConCodeENoCode(err, 'actionCard');
        }
      );
  }

  uploadModulo(files) {
    if (files?.length > 0) {
      this.spinner.show();
      this.istanzaService
        .uploadMuduloIstanza(this.idIstanza, files[0])
        .subscribe(
          (resp) => {
            if (resp[0].ind_firma === 1 || resp[0].ind_firma === 3) {
              this._messageService.showMessage('I009', 'actionCard', false);
            }
            this.moduloFirmato = resp[0];

            this.istanzaService
              .generaEvento(this.idIstanza, this.tipoEventoEnum.FIRMA_MOD)
              .subscribe(
                (res) => {
                  this.attoreGestioneIstanza =
                    res.headers.get('Attore-Gestione');
                  this.istanzaService.setAttoreGestioneIstanza(
                    this.attoreGestioneIstanza
                  );
                  this.aggiornaStato(res.body, this.tipoEventoEnum.FIRMA_MOD);
                  this.spinner.hide();
                  this._messageService.showMessage('P011', 'actionCard', true);
                },
                (error) => {
                  if (error.error?.code) {
                    if (error.error.code === 'E037') {
                      this._messageService.showMessage(
                        error.error.code,
                        'actionCard',
                        false,
                        null,
                        error.error.title
                      );
                    } else {
                      this._messageService.showMessage(
                        error.error.code,
                        'actionCard',
                        false
                      );
                    }
                  } else {
                    this._messageService.showMessage(
                      'E100',
                      'actionCard',
                      true
                    );
                  }
                }
              );
          },
          (err) => {
            if (err.error?.code) {
              this._messageService.showMessage(
                err.error.code,
                'actionCard',
                false,
                null,
                err.error.title
              );
            } else {
              this._messageService.showMessage('E100', 'actionCard', true);
            }
          }
        );
    }
  }

  onDelete(codTipoEvento: TipoEventoEnum) {
    this._messageService.showConfirmation({
      title: 'Conferma eliminazione',
      codMess:
        codTipoEvento === this.tipoEventoEnum.DA_FIRMARE ? 'A004' : 'A005',
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
            this.deleteFile(codTipoEvento);
          },
        },
      ],
    });
  }

  deleteFile(codTipoEvento: TipoEventoEnum) {
    this.spinner.show();
    if (this.moduloFirmato) {
      this.istanzaService
        .deleteMuduloIstanza(this.moduloFirmato.uuid_index)
        .subscribe(
          (resp) => {
            this.moduloFirmato = null;
            this.allowDownloadIstanza = false;
            this._generaEvento(codTipoEvento);
          },
          (err) => {
            this.showErrorsQuadroConCodeENoCode(err, 'actionCard');
          }
        );
    } else {
      this._generaEvento(codTipoEvento);
    }
  }

  aggiornaStato(eventi: IstanzaEvento[], codTipoEvento: string) {
    const eventiFiltered = eventi.filter(
      (evento) => evento.tipo_evento.cod_tipo_evento === codTipoEvento
    );
    if (eventiFiltered.length === 1) {
      this.istanza.stato_istanza =
        eventiFiltered[0].tipo_evento.stato_istanza_evento;
    } else {
      const ultimoEvento = eventiFiltered.reduce((previous, current) =>
        new Date(previous.data_evento).getTime() >
        new Date(current.data_evento).getTime()
          ? previous
          : current
      );
      this.istanza.stato_istanza = eventiFiltered.find(
        (evento) => evento.id_istanza_evento === ultimoEvento.id_istanza_evento
      ).tipo_evento.stato_istanza_evento;
    }
  }

  /**
   * Verifico che nello storico degli stati esiste almeno uno stato
   * inviato PRESENTATA | DA_ASSEGNARE | IN_CARICO
   * @param stati IstanzaStato[]
   * @returns boolean
   */
  private _isAlreadySubmitted(stati: IstanzaStato[]): boolean {
    return stati.some(
      (stato) =>
        stato.stato_istanza.codice_stato_istanza === StatoIstanzaEnum.PRESENTATA
    );
  }

  /**
   * Recupero il codTipoEvento in caso in cui non ci sia AlreadySubmitted
   * nello storico degli stati
   * @returns TipoEventoEnum
   */
  private _recuperaCodTipoEventoEvento(): TipoEventoEnum | null {
    const acPrincipali = this.acPraticaList.filter(
      (ac) => ac.flg_autorita_principale
    );

    const eventoSuape = this.jsonData?.QDR_CONFIG.FLG_INVIO_A_SUAP_SUE;
    // se l'istanza è associata a più AC NON è un invio a SUAP/SUE: evento DA_ASSEGNARE
    if (acPrincipali.length > 1 && !eventoSuape) {
      return TipoEventoEnum.DA_ASSEGNARE;
    }

    // se l'istanza è da inviare al SUAP/SUE (QDR_CONFIG.FLG_INVIO_A_SUAP_SUE = true):
    if (eventoSuape) {
      return TipoEventoEnum.DA_ASSEGNARE_UNICO;
    }

    // SCRIVA-1206
    // se l'istanza è associata ad un'unica AC senza COSMO : evento IN_CARICO
    // return TipoEventoEnum.IN_CARICO;
    return null;
  }

  onConferma() {
    let codTipoEvento;
    let stati;
    this.spinner.show();
    this.istanzaService
      .checkIstanza(this.idIstanza, this.adempimento.cod_adempimento)
      .pipe(
        switchMap(() => this.istanzaService.getStoricoStati(this.idIstanza)),
        switchMap((statiStorico) => {
          stati = statiStorico;
          codTipoEvento = this._isAlreadySubmitted(stati)
            ? TipoEventoEnum.COMPL_PERF_ISTANZA
            : this.tipoEventoEnum.PRESENTA_MOD;
          return this.istanzaService.generaEvento(
            this.idIstanza,
            codTipoEvento
          );
        }),
        switchMap((res) => {
          if (this._isAlreadySubmitted(stati)) {
            return of(res);
          }
          let codTipoEventoSecondStep = this._recuperaCodTipoEventoEvento();
          if (codTipoEventoSecondStep) {
            codTipoEvento = codTipoEventoSecondStep;
            return this.istanzaService.generaEvento(
              this.idIstanza,
              codTipoEvento
            );
          }
          return of(res);
        })
      )
      .subscribe(
        (res) => {
          this.attoreGestioneIstanza = res.headers.get('Attore-Gestione');
          this.istanzaService.setAttoreGestioneIstanza(
            this.attoreGestioneIstanza
          );
          this.aggiornaStato(res.body, codTipoEvento);
          this.spinner.hide();
          this._presentazioneIstanzaService.emitIstanzaSubmittedSub();
        },
        (err) => {
          this.showErrorsQuadroConCodeENoCode(err, 'actionCard');
        }
      );
  }

  showDetails() {
    const modalRef = this.modalService.open(
      AdvancedActionsRiepilogoModalComponent,
      {
        centered: true,
        scrollable: true,
        backdrop: 'static',
        size: 'xl',
      }
    );

    modalRef.componentInstance.istanza = this.istanza;

    modalRef.result
      .then((response: Istanza) => {
        if (response) {
          this.istanza = response;
          this.istanzaService
            .getResponsabili(this.idIstanza)
            .pipe(catchError((err) => of([])))
            .subscribe({
              next: (res) => (this.istanza.responsabili_istanza = res),
            });
        }
      })
      .catch();
  }

  buildMessage(codPratica: string) {
    let mes =
      this._messageService.messaggi?.find((msg) => msg.cod_messaggio === 'P016')
        ?.des_testo_messaggio ||
      'Errore inaspettato nella gestione della richiesta. Riprova a breve';

    let swapPh = [];
    swapPh.push({
      ph: '{PH_COD_PRATICA}',
      swap: codPratica ? codPratica : '',
    });

    swapPh.forEach((s) => {
      mes = mes.replace(s.ph, s.swap);
    });

    return mes;
  }

  goToHome() {
    this.istanzaService.setIdIstanza(null);
    this.router.navigate(['/home']);
  }

  goToLastSearch() {
    this.istanzaService.setIdIstanza(null);
    this.router.navigate(['/ricerca', 'ultima']);
  }

  goToWizard() {
    this.istanzaService.setIdIstanza(null);
    this.router.navigate(
      [
        `../../orientamento/${this.adempimento.tipo_adempimento.cod_tipo_adempimento}`,
      ],
      {
        relativeTo: this.route,
      }
    );
  }

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  /**
   * Funzione di comodo che verifica l'esistenza di una stringa.
   * La stringa non deve essere:
   * - Vuota;
   * - Undefined;
   * - Null;
   * @param s string da verificare.
   * @returns boolean con il risultato del check.
   */
  private isStringDefined(s: string): boolean {
    // Definisco i check
    const isStr = typeof s === 'string';
    const notUndef = s !== undefined;
    const notNull = s !== null;
    const notEmpty = s !== '';

    // Ritorno l'insieme dei check
    return isStr && notUndef && notNull && notEmpty;
  }

  /**
   * Funzione di gestione degli errori per i servizi.
   * @param err ScrivaServerError con l'errore generato dal servizio.
   * @param target string con l'id dell'elemento HTML del DOM per agganciare l'errore.
   * @param defaultErrCode ScrivaCodesMesseges con il codice di default per la visualizzazione dell'errore. Per default è: ScrivaCodesMesseges.E100.
   */
  private onServiziError(
    err: ScrivaServerError,
    target: string,
    defaultErrCode: ScrivaCodesMesseges = ScrivaCodesMesseges.E100
  ) {
    // Estraggo dall'errore del servizio il possibile codice da visualizzare
    let code: string = err.error?.code;
    // Definisco l'autofade in base all'esistenza del codice errore dall'oggetto del servizio
    const autoFade: boolean = code == undefined;
    // Riverifico il codice ed eventualmente assegno il default
    code = code ? code : defaultErrCode;

    // Lancio la gestione della visualizzazione del messaggio
    this._messageService.showMessage(code, target, autoFade);
  }

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  /**
   * Getter che recupera le informazioni per il quadro: QDR_ISTANZA.
   * @return any con la configurazione del quadro.
   */
  get quadroIstanza(): any {
    // Tento di accedere al quadro dal json data
    return this.jsonData?.QDR_ISTANZA;
  }

  /**
   * Getter che recupera il dato omonimo dal quadro istanza.
   * @returns string con il valore del dato.
   */
  get qdrIstDesAdempimento(): string {
    // Tento di recuperare il dato dalla configurazione
    return this.quadroIstanza?.adempimento?.des_estesa_adempimento;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkIdentificativoIstanza(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.identificativoIstanza);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get identificativoIstanza(): string {
    // Tento di recuperare il dato
    return this.istanza?.cod_istanza;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkCodiceProcedimento(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.codiceProcedimento);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get codiceProcedimento(): string {
    // Tento di recuperare il dato
    return this.istanza?.cod_pratica;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkStatoIstanzaProvvedimento(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.statoIstanzaProvvedimento);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get statoIstanzaProvvedimento(): string {
    // Tento di recuperare il dato
    return this.istanza?.stato_istanza?.descrizione_stato_istanza;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkAutoritaCompetente(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.autoritaCompetente);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get autoritaCompetente(): string {
    // Tento di recuperare il dato
    return this.acListString;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkDataInvioIstanza(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.dataInvioIstanza);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get dataInvioIstanza(): string {
    // SCRIVA-1384
    return this.istanza?.data_invio_istanza;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkDataDiProtocollo(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.dataDiProtocollo);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get dataDiProtocollo(): string {
    // Tento di recuperare il dato
    return this.istanza?.data_protocollo_istanza;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkNumeroDiProtocollo(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.numeroDiProtocollo);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get numeroDiProtocollo(): string {
    // Tento di recuperare il dato
    return this.istanza?.num_protocollo_istanza;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkTermineProcedimento(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.termineProcedimento);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get termineProcedimento(): string {
    // Tento di recuperare il dato
    return this.istanza?.data_scadenza_procedimento;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkEsitoProcedimento(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.esitoProcedimento);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get esitoProcedimento(): string {
    // Tento di recuperare il dato
    return this.istanza?.esito_procedimento?.des_esito_procedimento;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkDataInizioOsservazioni(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.dataInizioOsservazioni);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get dataInizioOsservazioni(): string {
    // Tento di recuperare il dato
    return this.istanza?.data_inizio_osservazioni;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkDataFineOsservazioni(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.dataFineOsservazioni);
  }

  /**
   * Getter che recupera una proprità dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get dataFineOsservazioni(): string {
    // Tento di recuperare il dato
    return this.istanza?.data_fine_osservazioni;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkResponsabiliIstanza(): boolean {
    // Recupero e verifico il dato
    return this.responsabiliIstanza?.length > 0;
  }

  /**
   * Getter che verifica che esista la proprità dalle configurazioni istanza.
   * @returns boolean con il risultato del check.
   */
  get checkDataPubblicazione(): boolean {
    // Recupero e verifico il dato
    return this.isStringDefined(this.dataPubblicazione);
  }

  /**
   * Getter che recupera una proprietà dalle configurazioni istanza.
   * @returns string con il valore del dato.
   */
  get dataPubblicazione(): string {
    // Tento di recuperare il dato
    return this.istanza?.data_pubblicazione;
  }

  /**
   * Getter che recupera una proprietà dalle configurazioni istanza.
   * I dati saranno filtrati secondo le seguenti logiche:
   * - flg_riservato == 0;
   * @returns IstanzaResponsabile[] con il valore del dato.
   */
  get responsabiliIstanza(): IstanzaResponsabile[] {
    // Definisco la lista contenitori
    let responsabili: IstanzaResponsabile[] = [];
    // Tento di recuperare il dato e filtrarlo
    responsabili = this.istanza?.responsabili_istanza?.filter(
      (ri: IstanzaResponsabile) => {
        return ri.flg_riservato;
      }
    );

    // Ritorno la lista generata
    return responsabili ?? [];
  }

  ngOnDestroy(): void {
    super.ngOnDestroy();
  }
}
