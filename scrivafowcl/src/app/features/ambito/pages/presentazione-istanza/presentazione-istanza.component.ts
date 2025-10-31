/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { CdkStepper } from '@angular/cdk/stepper';
import {
  ChangeDetectorRef,
  Component,
  Injector,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { of, Subscription, throwError } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import {
  FormioComponent,
  FormioDatiGeneraliComponent,
} from 'src/app/shared/components';
import { CONFIG } from 'src/app/shared/config.injectiontoken';
import {
  Adempimento,
  Istanza,
  Quadro,
  StepConfig,
  Template,
  TipoAdempimento,
  TipoAdempimentoOggApp,
} from 'src/app/shared/models';
import {
  AdempimentoService,
  AuthStoreService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from 'src/app/shared/services';
import { GeecoService } from 'src/app/shared/services/geeco/geeco.service';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { IdTipiQuadro } from '../../../../shared/enums/tipo-quadro.enums';
import { IGeecoQuitUrlParams } from '../../../../shared/services/geeco/utilities/geeco.interfaces';
import { LoggerService } from '../../../../shared/services/logger.service';
import { isDefinedString } from '../../../../shared/services/scriva-utilities/scriva-utilities.functions';
import { AmbitoStoreService } from '../../services';
import { PresentazioneIstanzaService } from '../../services/presentazione-istanza/presentazione-istanza.service';
import { AllegatiComponent } from './allegati/allegati.component';
import { CaptazioniComponent } from './captazioni/captazioni.component';
import { DettaglioProgettoComponent } from './dettaglio-progetto/dettaglio-progetto.component';
import { DettaglioViaStataleComponent } from './dettaglio-via-statale/dettaglio-via-statale.component';
import { DichiarazioniValComponent } from './dichiarazioni-val/dichiarazioni-val.component';
import { DichiarazioniVerComponent } from './dichiarazioni-ver/dichiarazioni-ver.component';
import { DichiarazioniVincaComponent } from './dichiarazioni-vinca/dichiarazioni-vinca.component';
import { InfrastruttureAccessorieComponent } from './infrastrutture-accessorie/infrastrutture-accessorie.component';
import { OperaInterventoComponent } from './opera-intervento/opera-intervento.component';
import { OpereTrasportoComponent } from './opere-trasporto/opere-trasporto.component';
import { OpereComponent } from './opere/opere.component';
import { PagamentiComponent } from './pagamenti/pagamenti.component';
import { RestituzioniComponent } from './restituzioni/restituzioni.component';
import { RiepilogoComponent } from './riepilogo/riepilogo.component';
import { SoggettiComponent } from './soggetti/soggetti.component';
import { TitoliAbilitativiComponent } from './titoli-abilitativi/titoli-abilitativi.component';
import { UsiUloDerivazioniComponent } from './usi-ulo-derivazioni/usi-ulo-derivazioni.component';
import { TipiComponentiQuadro } from './utilities/presenzatione-istanza.enum';

@Component({
  selector: 'app-presentazione-istanza',
  templateUrl: './presentazione-istanza.component.html',
  styleUrls: ['./presentazione-istanza.component.scss'],
})
export class PresentazioneIstanzaComponent implements OnInit, OnDestroy {
  /**
   * COMPONENTE CHE
   * RICHIAMA LO STEPPER,
   * CREA GLI STEP,
   * OTTIENE DATI DEI QUADRI,
   * LISTENER PER LO STEP COMPLETATO,  SELEZIONATO E ISTANZA INVIATA
   */

  /** string readonly che definisce il nome del componente di gestione FormIo generico. Deve avere lo stesso nome indicato per l'oggetto del componente: "componentsForStepper". */
  private readonly FORMIO_COMPONENT: string = 'FormioComponent';

  @ViewChild('stepper') stepper: CdkStepper;

  title = 'Nuovo adempimento';

  idIstanza: number;
  istanza: Istanza;
  attoreGestioneIstanza;
  pagamentoFlag = false;
  geecoFlag = false;
  oggAppPulsanti: TipoAdempimentoOggApp[];

  // IdTipiQuadro dove si vuole essere
  // rimandati all'atterraggio
  jumpToQuadroIdTipoQuadro: IdTipiQuadro;

  configuration = null;

  idTemplate: number;
  tipoAdempimento: TipoAdempimento;
  adempimento: Adempimento;

  componente: string;

  componentsForStepper = {
    SoggettiComponent: SoggettiComponent,
    OperaInterventoComponent: OperaInterventoComponent,
    DettaglioProgettoComponent: DettaglioProgettoComponent,
    FormioComponent: FormioComponent,
    AllegatiComponent: AllegatiComponent,
    PagamentiComponent: PagamentiComponent,
    TitoliAbilitativiComponent: TitoliAbilitativiComponent,
    DettaglioViaStataleComponent: DettaglioViaStataleComponent,
    // DettaglioProcConclusoComponent: DettaglioProcConclusoComponent,
    RiepilogoComponent: RiepilogoComponent,
    DichiarazioniValComponent: DichiarazioniValComponent,
    DichiarazioniVerComponent: DichiarazioniVerComponent,
    DichiarazioniVincaComponent: DichiarazioniVincaComponent,
    // DER
    FormioDatiGeneraliComponent: FormioDatiGeneraliComponent,
    OpereComponent: OpereComponent,
    CaptazioniComponent: CaptazioniComponent,
    RestituzioniComponent: RestituzioniComponent,
    InfrastruttureAccessorieComponent: InfrastruttureAccessorieComponent,
    OpereTrasportoComponent: OpereTrasportoComponent,
    UsiUloDerivazioniComponent: UsiUloDerivazioniComponent,
  };

  steps: Quadro[] = [];
  stepInjectors: Injector[] = [];
  stepperReady = false;
  loadSteps = false;
  showStepper = true;

  /** IGeecoQuitUrlParams contenente le informazioni che verranno definite all'interno dell'URL per la callback di Geeco. */
  private geecoQuitUrlParams: IGeecoQuitUrlParams;

  private jsonData = null;
  private state: {
    [k: string]: any;
  };

  //Subscription

  stepCompletedSub: Subscription;
  selectStepSub: Subscription;
  istanzaSetpSub: Subscription;
  refreshStepSub: Subscription;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authStoreService: AuthStoreService,
    private ambitoStoreService: AmbitoStoreService,
    private adempimentoService: AdempimentoService,
    private spinner: NgxSpinnerService,
    public stepManagerService: StepManagerService,
    private istanzaService: IstanzaService,
    private messageService: MessageService,
    private injector: Injector,
    private _geecoService: GeecoService,
    private _logger: LoggerService,
    public presentazioneIstanzaService: PresentazioneIstanzaService,
    private cd: ChangeDetectorRef
  ) {
    const state = this.router.getCurrentNavigation().extras.state;
    if (state) {
      this.state = state;
      this.idIstanza = state['idIstanza'];
      this.istanza = null;
      this.attoreGestioneIstanza = state['attoreGestioneIstanza'];
      this.pagamentoFlag = state['pagamentoFlag'];
      this.geecoFlag = state['geecoFlag'];
      this.oggAppPulsanti = state['oggAppPulsanti'];
      this.jumpToQuadroIdTipoQuadro = state['jumpToQuadroIdTipoQuadro'];

      // Recupero i possibili parametri per la callback di geeco
      this.geecoQuitUrlParams =
        this._geecoService.retreiveGeecoQuitUrlParams(state);
    }
  }

  ngOnInit() {
    this.spinner.show();
    this.componente = this.authStoreService.getComponente();
    if (this.idIstanza) {
      this.istanzaService.setIdIstanza(this.idIstanza);
    }
    if (this.attoreGestioneIstanza) {
      this.istanzaService.setAttoreGestioneIstanza(this.attoreGestioneIstanza);
    }
    if (this.oggAppPulsanti?.length > 0) {
      this.istanzaService.setOggAppPulsanti(this.oggAppPulsanti);
    }

    this.onStepperEvents();
    const codAdempimento = this.route.snapshot.paramMap.get('codAdempimento');

    this.ambitoStoreService.jsonConfiguraTemplateSub
      .pipe(
        switchMap((data) => {
          if (data) {
            this.configuration = data;
            return of(null);
          } else {
            //SCRIVA-1615 INSERITO TRUE IN MODO DA AGGIORNARE ATTOREGESTIONEISTANZA
            return this.istanzaService
              .getIstanzaById(this.idIstanza, true)
              .pipe(
                catchError((err) => {
                  return throwError(err);
                }),
                tap((res: Istanza) => {
                  this.istanza = res;
                  this.configuration = JSON.parse(res.json_data).QDR_CONFIG;
                  this.jsonData = JSON.parse(res.json_data);
                })
              );
          }
        }),
        switchMap(() =>
          this.adempimentoService.getAdempimentoByCode(codAdempimento)
        )
      )
      .subscribe(
        (res) => {
          this.adempimento = res;
          this.tipoAdempimento = this.adempimento.tipo_adempimento;
          this.title =
            this.tipoAdempimento.cod_tipo_adempimento +
            ' - ' +
            this.tipoAdempimento.des_tipo_adempimento;
          if (!this.idIstanza) {
            this.title = 'Nuova ' + this.title;
          }
          this.getQuadriStepper();
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(
              err.error.code,
              'pageContainer',
              false
            );
          } else {
            this.messageService.showMessage('E100', 'pageContainer', true);
          }
        }
      );
  }
  refreshKey = 0;
  /**
   * Funzione per i listener sullo stepper
   */
  onStepperEvents() {
    this.stepCompletedSub =
      this.presentazioneIstanzaService.stepCompleted$.subscribe((value) => {
        this.setCompleted(value.stepComponent, value.completed, value.idQuadro);
      });
    this.selectStepSub = this.presentazioneIstanzaService.selectStep$.subscribe(
      (idQuadro) => {
        const index = this.steps.findIndex(
          (step) => step.id_quadro === idQuadro
        );
        if (index === -1) {
          console.log(
            `Errore: impossibile trovare il quadro con id_quadro = ${index}`
          );
          return;
        }
        const step = this.steps[index];
        // Crea una nuova reference dell'oggetto step per far fare refresh anche se clicco sull'attuale step
        this.steps[index] = { ...step };
        // Aggiorna lo stepper
        this.stepper.selectedIndex = index;
      }
    );
    this.istanzaSetpSub =
      this.presentazioneIstanzaService.istanzaSubmitted$.subscribe(() => {
        this.showStepper = false;
      });
  }

  /**
   * SCRIVA-1416 Aggiorna template nella t_istanza
   * @param t Template
   */
  private _updateTemplate(t: Template) {
    if (
      this.istanza &&
      this.istanza.id_template !== t.id_template &&
      this.attoreGestioneIstanza === AttoreGestioneIstanzaEnum.WRITE
    ) {
      this.istanza.id_template = t.id_template;
      this.istanzaService.salvaIstanza(this.istanza).subscribe();
    }
  }

  /**
   * Funzione per ottenere i quadri
   */
  getQuadriStepper() {
    this.stepManagerService
      .getQuadri(this.adempimento.cod_adempimento, this.istanza)
      .subscribe(
        (res: Template) => {
          // SCRIVA-1416
          this._updateTemplate(res);
          this.idTemplate = res.id_template;
          this.steps = res.quadri.filter(
            (quadro) =>
              quadro.tipo_quadro.cod_tipo_quadro !== 'QDR_ORIENTAMENTO'
          );
          const idTemplateQdrRiepilogo = res.quadri.find(
            (quadro) => quadro.tipo_quadro.cod_tipo_quadro === 'QDR_RIEPILOGO'
          )?.id_template_quadro;
          this.stepManagerService.setIdTemplateQuadroRiepilogo(
            idTemplateQdrRiepilogo
          );

          if (this.steps?.length > 0) {
            this.buildStepper();
          } else {
            console.log('Ops... quadri non trovati.', res);
            this.spinner.hide();
          }
        },
        (err) => {
          if (err.error?.code) {
            this.messageService.showMessage(
              err.error.code,
              'pageContainer',
              false
            );
          } else {
            this.messageService.showMessage('E100', 'pageContainer', true);
          }
        }
      );
  }

  /**
   * Funzione che identifica gli steps
   */
  buildStepper() {
    // Definisco una costante come parte iniziale della chiave per i nomi dei quadri presenti nelle configurazioni
    const QDR: string = 'QDR_';
    // Recupero la lista di configurazioni dal componente
    const configurazioni: any[] = this.configuration;

    // Itero la lista delle chiavi dalla lista di configurazioni presente nel componente
    Object.keys(configurazioni).forEach((key: string) => {
      // Verifico se la chiave è il nome di un quadro
      const isKeyOfQuadro: boolean = key.startsWith(QDR);
      // Verifico se il quadro, come oggetto, è nascosto
      const quadroNascosto: boolean = configurazioni[key]?.visibile === false;

      // Verifico i flag di verifica
      if (isKeyOfQuadro && quadroNascosto) {
        // Filtro dalla lista degli step, impostando quello della chiave
        this.steps = this.steps.filter((quadro) => quadro.cod_quadro !== key);
        // #
      }
    });

    // Vado ad ordinare gli step, utilizzando la proprietà ordinamento template quadro
    this.steps.sort((stepA: Quadro, stepB: Quadro) => {
      // Recupero la proprietà per l'ordinamento
      const sortA: number = stepA.ordinamento_template_quadro;
      const sortB: number = stepB.ordinamento_template_quadro;
      // Ritorno la condizione di sort
      return sortA - sortB;
      // #
    });

    // Vado a definire le logiche per aggiungere la proprietà di attivazione dello step (blu: [attivo] grigio: [disabilitato])
    this.steps.forEach((quadro: Quadro) => {
      // Definisco il flag per la possibilià di clickare sullo step
      let clickable: boolean = this.isStepClickable(quadro, this.jsonData);
      // Aggiorno la proprietà dello state per il quadro stesso
      quadro.state = { clickable, id_quadro: quadro.id_quadro };
      // #
    });

    this.steps.forEach((step, index) => {
      step.completed = false;

      // Per ogni step vado a generare la configurazione da stringa ad oggetto
      step.json_configura_quadro = JSON.parse(step.json_configura_quadro);
      // Gestisco il nome del componente da gestire per il quadro
      const componentName: string = this.defineStepComponent(step);
      // Riassegno il nome del componente a seguito delle verifiche
      step.json_configura_quadro.componentName = componentName;

      const stepConfig: StepConfig = {
        // firstStep: index === 0,
        stepIndex: index + 1,
        idTemplate: this.idTemplate,
        idTemplateQuadro: step.id_template_quadro,
        codTipoQuadro: step.tipo_quadro.cod_tipo_quadro,
        codQuadro: step.cod_quadro,
        state: this.state,
      };
      const options = {
        providers: [{ provide: CONFIG, useValue: stepConfig }],
        parent: this.injector,
      };
      this.stepInjectors[index] = Injector.create(options);
    });

    this.stepperReady = true;

    if (this.idIstanza) {
      this.steps.find(
        (quadro) => quadro.tipo_quadro.cod_tipo_quadro === 'QDR_RIEPILOGO'
      ).state.clickable = true;

      if (this.pagamentoFlag) {
        this.jumpToPagamenti();
      }
      if (this.geecoFlag) {
        // Recupero, dato che è stato impostato il flag di geeco, i dati di "quit"
        const geecoQUP = this.geecoQuitUrlParams;
        // Richiamo la funzione per gestire la navigazione da geeco a scriva
        this.jumpToProgettoGeeco(geecoQUP);
        // #
      } else if (this.jumpToQuadroIdTipoQuadro) {
        this.jumpToQuadro(this.jumpToQuadroIdTipoQuadro);
      } else {
        this.jumpToRiepilogo();
      }
    } else {
      this.loadSteps = true;
      this.spinner.hide();
    }
  }

  /**
   * Funzione che verifica e ritorna se uno step è clickabile.
   * @param quadro Quadro con l'oggetto del quadro per la verifica del check.
   * @param jsonData any con l'oggetto per la verifica del check.
   * @returns boolean con il risultato del check.
   */
  private isStepClickable(quadro: Quadro, jsonData: any): boolean {
    // Definisco il flag per la possibilià di clickare sullo step
    let clickable: boolean = false;

    // Estraggo dall'oggetto del quadro le informazioni per il check
    const jsonConfigRiepilogo: any = quadro?.json_configura_riepilogo;
    const codiceQuadro: string = quadro?.cod_quadro;
    const codiceTipoQuadro: string = quadro?.tipo_quadro?.cod_tipo_quadro;
    // Estraggo dal json data le informazioni per il quadro riepilogo
    const quadroRiepilogo: any = jsonData?.QDR_RIEPILOGO;

    // Verifico se esisteno gli oggetti base dei json o il quadro
    const existQDRRiepilogo: boolean = quadroRiepilogo != undefined;
    const existData: boolean = jsonConfigRiepilogo && existQDRRiepilogo;

    // Verifico se esistono i nodi principali dati per le verifiche di clickabilità
    if (existData) {
      // Verifico se esistono le informazioni del quadro specifiche del quadro riepilogo per codice quadro/codice tipo quadro
      const existDatiQuadro: boolean =
        quadroRiepilogo[codiceQuadro] !== undefined;
      const existDatiTipoQuadro: boolean =
        quadroRiepilogo[codiceTipoQuadro] &&
        quadroRiepilogo[codiceTipoQuadro][codiceQuadro];

      // Verifico tutte le condizioni per l'esistenza delle informazioni
      if (existDatiQuadro || existDatiTipoQuadro) {
        // Le condizioni sono valide, quindi lo step del quadro è clickabile
        clickable = true;
        // #
      }
      // #
    }

    // Ritorno il flag di clickabilità
    return clickable;
    // #
  }

  /**
   * Funzione di comodo che gestisce la definizione del componente da caricare per uno step.
   * @important NOTA BENE: l'oggetto Quadro in input deve aver già la proprietà "json_configura_quadro" convertita da string ad oggetto.
   * @param step Quadro con le informazioni dello step per la gestione del componente.
   * @returns string con il nome del componente da caricare per lo step.
   */
  private defineStepComponent(step: Quadro): string {
    // Verifico l'input
    if (!step) {
      // Manca la configurazione
      return undefined;
    }

    // Definisco delle variabili di comodo
    const t: string = `presentazione-istanza.component.ts - defineStepComponent`;

    // Definisco una variabile per il nome del componente
    let componentName: string = '';
    // Tento di estrarre la configurazione
    try {
      // Recupero dalla configurazione il componente specifico da richiamare
      componentName = step.json_configura_quadro.componentName;
      // #
    } catch (e) {
      // Gestisco la segnalazione
      const d: string = `step.json_configura_quadro not parsed.`;
      const b: any = { error: e, description: d };
      this._logger.error(t, b);
      // Ritorno undefined
      return undefined;
    }

    // Estraggo dalla configurazione dello step la configurazione che indica il tipo di quadro
    const tipoGestStep: string = step.flg_tipo_gestione;
    // Verifico qual è il tipo gestione per lo step
    const isTGSFormIo = tipoGestStep == TipiComponentiQuadro.formIo;
    const isTGSCustom = tipoGestStep == TipiComponentiQuadro.custom;
    // Verifico se la configurazione per il componente esiste
    const existCN: boolean = isDefinedString(componentName);
    // Verifico se esiste ed il nome è custom (FormioComponent è preso dalla variabile: componentsForStepper)
    const isCNCustom: boolean =
      existCN && componentName != this.FORMIO_COMPONENT;

    // Verifico le varie condizioni e gestisco eventuali segnalazioni
    // ### 1) La configurazione ha il flag formio e non esiste la configurazione sul json configura oppure esiste, ma è formio
    if (isTGSFormIo && (!existCN || !isCNCustom)) {
      // Ritorno il nome del componente
      return this.FORMIO_COMPONENT;
      // #
    }
    // ### 2) La configurazione ha il flag custom ed esiste la configurazione sul json configura come custom
    if (isTGSCustom && isCNCustom) {
      // Ritorno il nome del componente
      return componentName;
      // #
    }
    // ### 3) La configurazione tramite flag e la configurazione sul json configura sono in conflitto
    const flgFormioConfigCustom: boolean = isTGSFormIo && isCNCustom;
    if (flgFormioConfigCustom) {
      // C'è una discrepanza tra le configurazioni
      const d1: string = `"step.flg_tipo_gestione" conflicted with "step.json_configura_quadro.componentName" configuration.`;
      const d2: string = `${componentName} will be used as component.`;
      const b: any = { step, description: `${d1} ${d2}` };
      this._logger.warning(t, b);
      // Ritorno il nome del componente
      return componentName;
      // #
    }
    // ### 4) La configurazione tramite flag e la configurazione sul json configura sono in conflitto
    const flgCustomConfigFormio: boolean = isTGSCustom && !existCN;
    if (flgFormioConfigCustom || flgCustomConfigFormio) {
      // C'è una discrepanza tra le configurazioni
      const d1: string = `"step.flg_tipo_gestione" conflicted with "step.json_configura_quadro.componentName" configuration.`;
      const d2: string = `"step.json_configura_quadro.componentName" is not defined.`;
      const b: any = { step, description: `${d1} ${d2}` };
      this._logger.warning(t, b);
      // Ritorno il nome del componente
      return undefined;
      // #
    }

    // Per default se esiste il componentName lo si usa, altrimenti si gestisce come FormIo
    return existCN ? componentName : this.FORMIO_COMPONENT;
  }

  /**
   * Funzione che setta lo stato dello step completed
   * @param stepComponent
   * @param completed
   * @param idQuadro
   */
  setCompleted(stepComponent: string, completed: boolean, idQuadro: number) {
    this.steps.map((step) => {
      if (idQuadro) {
        if (step.id_quadro === idQuadro) {
          step.completed = completed;
        }
      } else {
        if (step.json_configura_quadro.componentName === stepComponent) {
          step.completed = completed;
        }
      }
    });
  }

  jumpToRiepilogo() {
    setTimeout(() => {
      this.stepper.selectedIndex = this.steps.length - 1;
      this.loadSteps = true;
    }, 500);
  }

  jumpToPagamenti() {
    const index = this.steps.findIndex(
      (step) => step.tipo_quadro.id_tipo_quadro === IdTipiQuadro.pagamento
    );
    setTimeout(() => {
      this.stepper.selectedIndex = index;
      this.loadSteps = true;
    }, 500);
  }

  /**
   * Si viene rimandati verso il tipo quadro in input attraverso lo stepper
   * @param idTipiQuadro IdTipiQuadro che identifica il quadro
   */
  jumpToQuadro(idTipiQuadro: IdTipiQuadro) {
    const index = this.steps.findIndex(
      (step) => step.tipo_quadro.id_tipo_quadro === idTipiQuadro
    );
    setTimeout(() => {
      this.stepper.selectedIndex = index;
      this.loadSteps = true;
    }, 500);
  }

  /**
   * Funzione che dati i parametri restituiti dalla chiamata di callback di Geeco, reimposta la navigazione sugli step.
   * @param geecoParams IGeecoQuitUrlParams con le informazioni ritornate dalla chiusura di Geeco.
   */
  jumpToProgettoGeeco(geecoQUP: IGeecoQuitUrlParams) {
    // Verifico l'input
    if (!geecoQUP) {
      // Effettuo il redirect
      this.goToHome();
      // Blocco il flusso
      return;
      // #
    }

    // Verifico se esiste tra gli step uno che matchi con la configurazione di geeco
    const iStep = this._geecoService.getStepIndexByGeecoQuitCallback(
      this.steps,
      geecoQUP
    );

    // Verifico se è stato trovato lo step nell'array
    if (iStep > -1) {
      // Imposto un timeout per gestire meglio la navigazione applicativa
      setTimeout(() => {
        // Aggiorno l'indice dello step selezionato
        this.stepper.selectedIndex = iStep;
        // Imposto lo step come caricato
        this.loadSteps = true;
        // #
      }, 500);
      // #
    } else {
      // Le infomrazioni non coincidono, reindirizzo alla home
      this.goToHome();
    }
  }

  goToDashboard() {
    this.router.navigate(['/ambito/' + this.tipoAdempimento.ambito.cod_ambito]);
  }

  goToHome() {
    this.router.navigate(['/home']);
  }

  ngOnDestroy() {
    this.istanzaService.setAttoreGestioneIstanza(null);
    this.istanzaService.setOggAppPulsanti(null);
    /**
     * @author Ismaele Bottelli
     * @author Debora Ferroni
     * @notes Ismaele TO Debora: Perché non hai fatto l'unsubscribe di jsonConfiguraTemplateSub per poi eventualmente creare
     *        un nuovo BehaviorSubject quando presentazione-istanza viene instanziato come componente? Piuttosto non fare niente,
     *        o cambia gestione a variabile + Subject in maniera tale da non incasinarti con un BehaviorSubject.
     *        Fare una emit di null va a triggherare tutti i componenti che sono connessi e attiva le logiche di tutti i componenti.
     *        In questo modo si rischia di andare ad attivare logiche appena prima che i componenti vengano distrutti.
     *        In console ho visto che avviena una chiamata legata a questo componente passando poi undefined, perché non riesce ad
     *        accedere ad una proprietà dell'oggetto, poiché viene passata a null. Sarebbe da verificare meglio questo flusso o
     *        farlo presente e poi eventualmente lo rivedo io.
     */
    this.ambitoStoreService.jsonConfiguraTemplateSub.next(null);
    this.stepManagerService.setIdTemplateQuadroRiepilogo(null);
    this.stepCompletedSub?.unsubscribe();
    this.selectStepSub?.unsubscribe();
    this.istanzaSetpSub?.unsubscribe();
  }
}
