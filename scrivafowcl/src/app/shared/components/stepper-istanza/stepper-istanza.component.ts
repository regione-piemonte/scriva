/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */

import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { PresentazioneIstanzaService } from 'src/app/features/ambito/services/presentazione-istanza/presentazione-istanza.service';
import { forkJoin, Observable, of, Subscription, throwError } from 'rxjs';
import { IClickOnStepper } from '../../services/step-manager/utilities/step-manager.interfaces';
import { CONFIG } from '../../config.injectiontoken';
import { DataQuadro, Help, StepConfig } from '../../models';
import {
  AuthStoreService,
  HelpService,
  IstanzaService,
  MessageService,
  StepManagerService,
} from '../../services';
import { ScrivaCodesMesseges } from 'src/app/core/enums/scriva-codes-messages.enums';
import { AttoreGestioneIstanzaEnum } from '../../utils';
import {
  ICambiaStep,
  IConfigsSaveDataQuadro,
  IConfigsSaveQuadro,
  IPrepareDatiRiepilogo,
  ISalvaJsonDataReq,
  ISalvaJsonDataRes,
  RequestDataQuadro,
  RequestSaveBodyQuadro,
} from './utilities/stepper-istanza.interfaces';
import { NgxSpinnerService } from 'ngx-spinner';
import { catchError, tap, switchMap } from 'rxjs/operators';
import { cloneDeep } from 'lodash';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';
import { IPositionStepClicked } from '../stepper/stepper.component';

@Component({
  selector: 'app-stepper-istanza',
  templateUrl: './stepper-istanza.component.html',
  styleUrls: ['./stepper-istanza.component.scss'],
})
export class StepperIstanzaComponent implements OnInit, OnDestroy {
  /**
   * Componente generico che implementa step singolo
   * viene esteso dai vari componenti dei quadri
   */

  //Subscription
  selectStepSub: Subscription;
  stepCompletedSub: Subscription;
  istanzaSubmittedSub: Subscription;
  checkSelectedStepSub: Subscription;
  avantiSub: Subscription;
  indietroSub: Subscription;

  /**
   * variabile per poter inserire condizione per la visualizzazione del pulsante
   */
  public mostraAvanti: boolean = false;
  public mostraIndietro: boolean = true;
  protected qdr_riepilogo: any;

  protected _stepIndex: number = 0;
  protected _idTemplateQuadro: number = 0;
  protected _idTemplate: number = 0;
  protected _codTipoQuadro: string = '';
  protected _codQuadro: string = '';

  /** boolean che definisce come flag se la pagina
   * è stata caricata a seguito del ritorno dalla pagina di geeco.
   * */
  protected _geecoFlag: boolean = false;

  protected _helpList: Help[] = [];

  // utilizzato per verificare se è possibile modificare lo step
  public attoreGestioneIstanza = AttoreGestioneIstanzaEnum.WRITE as string;

  protected _componente: string = '';
  protected _isFrontOffice: boolean = false;

  constructor(
    protected _presentazioneIstanzaService: PresentazioneIstanzaService,
    @Inject(CONFIG) protected injConfig: StepConfig,
    protected _messageService: MessageService,
    protected helpService: HelpService,
    protected istanzaService: IstanzaService,
    protected authStoreService: AuthStoreService,
    protected stepManagerService: StepManagerService,
    protected spinner: NgxSpinnerService
  ) {
    // Lancio la funzione di gestione e definizione dati obbligatori
    this.setupStepConfig();
    // Lancio la funzione di setup del componente, la funzione setta se frontOffice o meno
    this.setupComponente();
    // controlla se step valido poi vai allo step cliccato
    this.checkBeforeChangeStep();
    //setto visibilità tasto avanti
    this.setVisibilityButtonNext(this.mostraAvanti);
    //setto la visibilità del tasto indietro
    this.setVisibilityButtonBack(this.mostraIndietro);
    //sottoscrizione agli eventi di avanti e indietro dello stepper
    this.checkClickOnButton();
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.selectStepSub?.unsubscribe();
    this.stepCompletedSub?.unsubscribe();
    this.istanzaSubmittedSub?.unsubscribe();
    this.checkSelectedStepSub?.unsubscribe();
    this.avantiSub?.unsubscribe();
    this.indietroSub?.unsubscribe();
    this.changed = false;
  }

  /**
   * Funzione che richiama il componente e verifica se frontOffice o no
   */
  protected setupComponente() {
    this.componente = this.authStoreService.getComponente();
    if (this.componente === 'BO') {
      this.isFrontOffice = false;
    } else {
      this.isFrontOffice = true;
    }
  }

  /**
   * Funzione per verificare se lo step è valido e a seguito del controllo andare allo step cliccato
   */
  checkBeforeChangeStep() {
    this.checkSelectedStepSub =
      this._presentazioneIstanzaService.checkSelectedStep$.subscribe(
        (data: ICambiaStep) => {
          //cambio step
          this._cambiaStep(data);
        }
      );
  }

  /**
   * Funzione per il cambio step,
   * onAvanti la richiama se non overridata.
   * onIndietro non necessità controlli.
   * se lo step è riepilogo non necessità controlli
   * in modo da poterla eventualmente overridare in futuro.
   * @param payload
   */
  private _cambiaStep(payload: ICambiaStep) {
    //controlla se è riepilogo oppure è stato cliccato uno step antecente all'attuale
    // nel caso lo step corrente è il penultimo (anche se dopo c'è il riepilogo) controlla la validità
    // gli step antecedenti non necessitano di controlli di validità
    if (
      (payload.currentIndex !== payload.stepArrayLength - 2 &&
        payload.stepClickedPosition === IPositionStepClicked.LAST) ||
      payload.stepClickedPosition === IPositionStepClicked.BEFORE ||
      payload.stepClickedPosition === IPositionStepClicked.EQUAL
    ) {
      this.changed = false;
      this._presentazioneIstanzaService.clickOnStepper(payload);
    } else {
      this.stepIndex = payload.idQuadro;
      this.onAvanti(payload);
    }
  }

  //Funzione che viene richiamata dopo la validazione dello step,
  // verrà overrideata per permettere in eseguire le stesse azioni di onAvanti
  // per non ripetere il controllo di validazione al click
  protected azioneDopoValidazione(payload: ICambiaStep) {
    this.changed = false;
    this._presentazioneIstanzaService.clickOnStepper(payload);
  }

  /**
   * Funzione che imposta la visibilità del pulsante avanti
   * @param isMostraAvanti
   */
  setVisibilityButtonNext(isMostraAvanti: boolean) {
    this._presentazioneIstanzaService.emitShowMostraAvantiSub(isMostraAvanti);
  }

  /**
   * Funzione che setta visibilità del pulsante indietro
   * @param isMostraIndietro
   */
  setVisibilityButtonBack(isMostraIndietro: boolean) {
    this._presentazioneIstanzaService.emitShowMostraIndietroSub(
      isMostraIndietro
    );
  }

  /**
   * Funzione per sottoscrizione ai click dei pulsanti avanti e indietro
   */
  checkClickOnButton() {
    this.indietroSub =
      this._presentazioneIstanzaService.indietroClicked$.subscribe(
        (actualStep: IClickOnStepper) => {
          this.onIndietro(actualStep);
        }
      );
    this.avantiSub = this._presentazioneIstanzaService.avantiClicked$.subscribe(
      (actualStep: number) => {
        const payload: ICambiaStep = { idQuadro: actualStep };
        this.stepIndex = actualStep;
        this._cambiaStep(payload);
      }
    );
  }

  /**
   * Funzione di init che gestisce la verifica ed il set dati obbligatori per il componente.
   */
  setupStepConfig() {
    // Verifico se esiste la configurazione iniettata per il componente
    if (!this.injConfig) {
      console.log('Something went wrong... injected config has no value.');
    } else {
      // Imposto tutte le informazioni recuperate dall'oggetto dei parametri iniettati
      this.stepIndex = this.injConfig.stepIndex;
      this.idTemplateQuadro = this.injConfig.idTemplateQuadro;
      this.idTemplate = this.injConfig.idTemplate;
      this.codTipoQuadro = this.injConfig.codTipoQuadro;
      this.codQuadro = this.injConfig.codQuadro;
      this.geecoFlag = this.injConfig.state?.geecoFlag;
    }
  }

  /**
   * Funzione che controlla se step è valido, default a true.
   * @returns Observable<boolean> per validare step
   */
  protected isStepValid(): Observable<boolean> {
    return of(true);
  }
  /**
   * Funzione che verrà gestita dallo step, richiama la funzione che va allo step indicato
   * @param idQuadro
   */
  protected onIndietro(payload: ICambiaStep) {
    this.changed = false;
    this._presentazioneIstanzaService.clickOnStepper(payload);
  }

  /**
   * Funzione che richiama l'emit del servizio per lo step completato
   */
  protected setStepCompletedEmit(
    stepComponent: string,
    completed: boolean,
    idQuadro: number = null
  ) {
    this._presentazioneIstanzaService.emitStepCompletedSub({
      stepComponent: stepComponent,
      completed: completed,
      idQuadro: idQuadro,
    });
  }

  /**
   * Funzione che richiama l'emit di andare ad uno step indicato con idQuadro
   */
  protected goToStep(idQuadro: number) {
    // non inserisco controllo di validità perché viene usata nel componenti dei quadri dopo il controllo
    this._presentazioneIstanzaService.emitSelectStepSub(idQuadro);
  }

  /**
   * Funzione che verrà gestita dallo step, richiama la funzione che va allo step indicato
   * @param payload
   */
  protected onAvanti(payload: ICambiaStep) {
   
    //se è stato cliccato uno step dopo l'attuale
    this.isStepValid().subscribe((isValid: boolean) => {
      //se è penultimo step e non è valido non proseguo
      if (!isValid && payload.currentIndex === payload.stepArrayLength - 2) {
        return;
      }
      if (isValid) {
        this.azioneDopoValidazione(payload);
      }
    });
  }

  /**
   * Funzione per creare body del quadro per poter procedere al salvataggio
   * @param idIstanza
   * @param idTemplateQuadro
   * @param datiQuadro
   * @returns requestDataQuadro dati quadro da salvare
   */
  protected buildRequestQuadro(
    idIstanza: number,
    idTemplateQuadro: number,
    datiQuadro: any
  ): RequestDataQuadro {
    const requestDataQuadro: RequestDataQuadro = {
      id_istanza: idIstanza,
      id_template_quadro: idTemplateQuadro,
      json_data_quadro: JSON.stringify(datiQuadro),
    };
    return requestDataQuadro;
  }

  /**
   * Funzione per mostrare i messaggi di errore del salvataggio quadro
   * @param err errore dato dalla chiamata
   * @param elementIdForCode id da usare per mostrare il messaggio di errore se c'è il campo code nell'errore
   * @param elementIdForAll id da usare per mostrare il messaggio di errore se non c'è il campo code nell'errore,
   * può non essere passato se è uguale all'elementIdForCode
   */
  protected showErrorsQuadroConCodeENoCode(
    err: any,
    elementIdForCode: string,
    elementIdForAll?: string
  ) {
    //controllo se c'è il campo code nell'errore
    if (err.error?.code) {
      // se il codice è E037 mostro anche title
      if (err.error.code === ScrivaCodesMesseges.E037) {
        this._messageService.showMessage(
          err.error.code,
          elementIdForCode,
          false,
          null,
          err.error.title
        );
      } else {
        this._messageService.showMessage(
          err.error.code,
          elementIdForCode,
          false
        );
      }
    } else {
      //E100 è il codice di default
      this._messageService.showMessage(
        ScrivaCodesMesseges.E100,
        elementIdForAll ? elementIdForAll : elementIdForCode,
        true
      );
    }
  }

  /**
   * Funzione per ottenere la lista per l'help
   * @param componente
   * @param codMaschera
   * @param elementIdForError id da inserire per mostrare il messaggio di errore
   */
  protected getHelpList(requestHelp: string, elementIdForError: string) {
    this.helpList = [];
    this.helpService.getHelpByChiave(requestHelp).subscribe(
      (res) => {
        this.helpList = res;
      },
      (err) => {
        this.showErrorsQuadroConCodeENoCode(err, elementIdForError);
      }
    );
  }

  /**
   * Funzione che imposta all'interno del componente l'informazione di gestione dei dati del quadro.
   * L'informazione viene ricavata dal dato "attore gestione istanza" dell'istanza, e a seconda del suo valore, viene trasformata in: scrittura/lettura.
   * @jira SCRIVA-1564
   */
  protected setGestioneQuadro() {
    // Recupero dal dato del componente il codice quadro
    const codQuadro: string = this.codQuadro;
    // Richiamo il servizio che calcoli, per il quadro, il tipo di abilitazione dei dati
    const gestioneIstanza: string =
      this.istanzaService.accessoDatiIstanza(codQuadro);
    // Assegno localmente l'informazione per la gestione dati
    this.attoreGestioneIstanza = gestioneIstanza;
  }

  /**
   * Funzione che definisce le logiche per il salvataggio dei dati del quadro.
   * La funzione è pensata per essere overridata, con l'implementazione specifica di salvataggio dati
   * che richiede il quadro specifico.
   * Di default la funzione salva una configurazione dati sia per quadro che per riepilogo,
   * con il corpo dati come oggetto vuoto.
   * @param configs any come informazioni di payload generiche che potrebbero essere utili per l'override.
   * @returns Observable<ISalvaJsonDataRes> con l'indicazione di avvenuto salvataggio dei dati.
   */
  protected saveDataQuadro(
    configs?: IConfigsSaveDataQuadro
  ): Observable<ISalvaJsonDataRes | any> {
    if (!configs) {
      return of(undefined);
    }
    // estraggo le configurazioni
    const {
      showSpinner,
      isPutDatiQuadro,
      isPutDatiRiepilogo,
      datiQuadro,
      datiRiepilogo,
    } = configs;
    if (showSpinner) {
      this.spinner.show();
    }
    const qdrRiepilogoConfigs: IPrepareDatiRiepilogo = {
      codQuadro: this.codQuadro,
      codTipoQuadro: this.codTipoQuadro,
      datiQuadro,
      datiRiepilogo,
    };
    this.qdr_riepilogo = this.prepareDatiRiepilogo(qdrRiepilogoConfigs);

    const reqSaveQuadroConfigs: IConfigsSaveQuadro = {
      idIstanza: this.idIstanza,
      idTemplateQuadro: this.idTemplateQuadro,
      datiQuadro,
      datiRiepilogo: this.qdr_riepilogo,
    };
    const requestData: RequestSaveBodyQuadro =
      this.buildRequestDataToSaveQuadro(reqSaveQuadroConfigs);

    return this.doForkjoinSalvaDatiQuadroERiepilogo(
      requestData,
      isPutDatiQuadro,
      isPutDatiRiepilogo,
      showSpinner
    );
  }

  protected doForkjoinSalvaDatiQuadroERiepilogo(
    requestData: RequestSaveBodyQuadro,
    isPutDatiQuadro: boolean,
    isPutDatiRiepilogo: boolean,
    showSpinner: boolean
  ): Observable<DataQuadro> {
    const requestDataQuadro: RequestDataQuadro = requestData.requestDataQuadro;
    const requestDataRiepilogo: RequestDataQuadro =
      requestData.requestDataRiepilogo;
    // ATTENZIONE: le due chiamate vanno fatte in sequenza, provata a mettere forkjoin e da errore su DEV (a volte) e sempre su TEST
    /**
     * @todo Rifattorizzare con Roberto.
     * @author Ismaele Bottelli
     */
    // Eseguo prima il salvataggio del quadro
    return this.stepManagerService.salvaJsonDataQuadro(
      requestDataQuadro,
      isPutDatiQuadro
    ).pipe(
      // Poi eseguo il salvataggio del riepilogo usando switchMap
      switchMap((resultQuadro: DataQuadro) => 
        this.stepManagerService.salvaJsonDataQuadro(
          requestDataRiepilogo,
          isPutDatiRiepilogo
        ).pipe(
          // Combino i risultati - restituisco l'ultimo risultato (riepilogo)
          tap((resultRiepilogo: DataQuadro) => {
            if (showSpinner) {
              this.spinner.hide();
            }
          })
        )
      ),
      catchError((e: ScrivaServerError) => {
        if (showSpinner) {
          this.spinner.hide();
        }
        return throwError(e);
      })
    );
  }

  /**
   * Funzione di comodo
   */
  protected prepareDatiRiepilogo(configs: IPrepareDatiRiepilogo) {
    // verifico se esiste la configurazione
    if (!configs) {
      // ritorno configurazioni vuote
      return undefined;
    }
    //estraggo i parametri per la gestione delle configurazioni
    const { codQuadro, codTipoQuadro } = configs;
    const datiQuadro = configs.datiQuadro ?? {};
    const datiRiepilogo = cloneDeep(configs.datiRiepilogo) ?? {};

    const nestedBlock: boolean = codQuadro !== codTipoQuadro;

    if (nestedBlock) {
      if (!datiRiepilogo[codTipoQuadro]) {
        datiRiepilogo[codTipoQuadro] = {};
      }
      datiRiepilogo[codTipoQuadro][codQuadro] = datiQuadro;
    } else {
      datiRiepilogo[codTipoQuadro] = datiQuadro;
    }
    return datiRiepilogo;
  }

  /**
   * Funzione che definisce le logiche per la costruzione degli oggetti di salvataggio dati per il json data.
   * Le infomazioni trattate prevedono la configurazione per il salvataggio dei dati del quadro e dei dati di riepilogo.
   * @param configs IConfigsSaveQuadro come informazioni di payload che potrebbero essere utili per l'override.
   * @returns RequestSaveBodyQuadro
   */
  protected buildRequestDataToSaveQuadro(
    configs: IConfigsSaveQuadro
  ): RequestSaveBodyQuadro {
    // verifico se esiste la configurazione
    if (!configs) {
      // ritorno configurazioni vuote
      return undefined;
    }

    //estraggo i parametri per la gestione delle configurazioni
    const { idIstanza, idTemplateQuadro } = configs;
    const datiQuadro = configs.datiQuadro ?? {};
    const datiRiepilogo = configs.datiRiepilogo ?? {};
    const body: RequestSaveBodyQuadro = {
      requestDataQuadro: undefined,
      requestDataRiepilogo: undefined,
    };
    const requestDataQuadro = this.buildRequestQuadro(
      idIstanza,
      idTemplateQuadro,
      datiQuadro
    );
    const idTemplateQuadroRiepilogo = this.idTemplateQuadroRiepilogo;
    const requestDataRiepilogo = this.buildRequestQuadro(
      idIstanza,
      idTemplateQuadroRiepilogo,
      datiRiepilogo
    );

    body.requestDataQuadro = requestDataQuadro;
    body.requestDataRiepilogo = requestDataRiepilogo;
    return body;
  }

  /**
   * Funzione che verifica la presenza di informazioni già storicizzate sul json data.
   * Se sono presenti informazioni si può proseguire senza problemi la funzione restituisce [true] altrimenti false.
   * Il concetto si basa che le informazioni sul json data vengono salvati e aggiornati quando l'utente effettua delle operazioni sulla pagina.
   * Tra le operazioni che può effettuare ci sono:
   * - L'associazione oggetti-istanza/opere;
   * - L'aggiunta di dettagli tramite la modifica dettaglio oggetti-istanza;
   * Per cui il pulsante AVANTI non effettua operazioni di questo tipo.
   * La logica ha similitudini con la funzione "saveDataQuadro" che viene invocata per salvare i jsonData quadro e riepilogo.
   * @returns boolean che indica se ci sono dati già storicizzati [true], altrimenti [false].
   */
  protected verificaJsonDataStoricizzato(): boolean {
    // Essendo la struttura dinamica e non stabile, uso un try-catch
    try {
      // Definisco le variabili di comodo rispetto alle variabili globali del componente
      const QUADRO: string = this.codQuadro;
      const TIPO_QUADRO: string = this.codTipoQuadro;
      // Verifico se all'interno delle informazioni del quadro riepilogo esistono informazioni per il tipo quadro
      if (this.qdr_riepilogo[TIPO_QUADRO]) {
        // Aggiorno le informazioni per la variabile globale del quadro riepilogo
        const datiQuadro: any = this.qdr_riepilogo[TIPO_QUADRO][QUADRO];
        // Se esistono informazioni all'interno del quadro riepilogo vuol dire che sono state salvate informazioni
        return datiQuadro != undefined;
        // #
      }
    } catch (e) {
      // Qualcosa è andato storto sui dati, ritorno false
      return false;
      // #
    }

    // Non ci sono dati
    return false;
    // #
  }

  //#region GETTER E SETTER
  /**
   * Getter per la condizione di modifica dello step
   * @memberof StepperIstanzaComponent
   * @returns boolean con il ritorno se è cambiato lo step
   */
  get changed() {
    return this._presentazioneIstanzaService.changed;
  }

  /**
   * Setter per valorizzare se cambiato lo step
   * @memberof StepperIstanzaComponent
   */
  set changed(value: boolean) {
    this._presentazioneIstanzaService.changed = value;
  }

  //#region "GETTER E SETTER PER INJECTION"

  get stepIndex(): number {
    return this._stepIndex;
  }

  set stepIndex(newValue: number) {
    this._stepIndex = newValue;
  }
  get idTemplateQuadro(): number {
    return this._idTemplateQuadro;
  }

  set idTemplateQuadro(newValue: number) {
    this._idTemplateQuadro = newValue;
  }

  get idTemplate(): number {
    return this._idTemplate;
  }

  set idTemplate(newValue: number) {
    this._idTemplate = newValue;
  }

  get codTipoQuadro(): string {
    return this._codTipoQuadro;
  }

  set codTipoQuadro(newValue: string) {
    this._codTipoQuadro = newValue;
  }

  get codQuadro(): string {
    return this._codQuadro;
  }

  set codQuadro(newValue: string) {
    this._codQuadro = newValue;
  }

  get geecoFlag() {
    return this._geecoFlag;
  }
  set geecoFlag(newValue: boolean) {
    this._geecoFlag = newValue;
  }

  get helpList() {
    return this._helpList;
  }
  set helpList(newList: Help[]) {
    this._helpList = newList;
  }

  get componente() {
    return this._componente;
  }
  set componente(newValue: string) {
    this._componente = newValue;
  }

  get isFronOffice() {
    return this._isFrontOffice;
  }
  set isFrontOffice(newValue: boolean) {
    this._isFrontOffice = newValue;
  }

  get idIstanza() {
    return this.istanzaService.getIdIstanza();
  }

  get idTemplateQuadroRiepilogo() {
    return this.stepManagerService.getIdTemplateQuadroRiepilogo();
  }

  //#endregion "GETTER E SETTER PER INJECTION"
}
