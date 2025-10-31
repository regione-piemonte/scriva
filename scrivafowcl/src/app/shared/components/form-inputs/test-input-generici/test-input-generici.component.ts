import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TestInputGenericiConsts } from './utilities/test-input-form.consts';
import { CommonConsts } from 'src/app/shared/consts/common-consts.consts';
import { TestInputFormFieldsConfig } from './utilities/test-input-form.fields-configs';
import { TestInputFormFormConfigs } from './utilities/test-input-form.form-configs';
import { IRFCFormErrorsConfigs, IScrivaAnnoSelect } from './utilities/test-input-form.interfaces';
import { LoggerService } from 'src/app/shared/services/logger.service';
import { ScrivaUtilitiesService } from 'src/app/shared/services/scriva-utilities/scriva-utilities.service';
import { Observable, Subscription } from 'rxjs';
import { ScrivaServerError } from 'src/app/core/classes/scriva.classes';
import { tap } from 'rxjs/operators';
import { ScrivaErrorsMap } from 'src/app/shared/classes/scriva-input-errors/scriva-errors-maps.class';
import { TestInputFormService } from './services/test-input-form.service';
import { ScrivaFormBuilderService } from 'src/app/shared/services/form-inputs/scriva-form-builder.service';
import { IMappaErroriFormECodici } from '../form-input/utilities/form-input.interfaces';

@Component({
  selector: 'scriva-test-input-generici',
  templateUrl: './test-input-generici.component.html',
  styleUrls: ['./test-input-generici.component.scss'],
})
export class TestInputGenericiComponent implements OnInit, OnDestroy {
  /** Oggetto contenente le costanti comuni all'applicazione. */
  C_C = new CommonConsts();
  /** Oggetto con le costanti del componente di riferimento. */
  TEST_CONSTS = new TestInputGenericiConsts();

  /** Classe costante contenente il mapping degli errori per i form. */
  EM = new ScrivaErrorsMap();

  /** TestInputFieldsConfig come classe che definisce la struttura dei campi di ricerca del form. */
  formInputs: TestInputFormFieldsConfig;
  /** TestInputFormConfigs come classe che definisce la struttura di configurazione del form. */
  formConfigs: TestInputFormFormConfigs;

  /** IScrivaAnnoSelect[] con la lista degli anni disponibili per la selezione. */
  listaAnni: IScrivaAnnoSelect[] = [];

  /** Subscription che viene invocato quando il componente emette l'evento di submit. */
  onSubmit: Subscription;
  /** Subscription che viene invocato quando il componente emette l'evento di reset. */
  onReset: Subscription;

  /** Form group che definisce la struttura della form principale. */
  mainForm: FormGroup;
  /** Boolean che tiene traccia dello stato di submit del mainForm. */
  mainFormSubmitted = false;
  /** Array di MappaErroriFormECodici contente la lista degli errori da gestire per il mainForm. */
  formErrors: IMappaErroriFormECodici[] = [];

  /** Boolean che definisce se prima della varifica della validazione, il form deve essere checkato. */
  protected checkValueValidity = false;

  @Output('onServiziError') onEmitServiziError$ =
    new EventEmitter<ScrivaServerError>();
  /** Output con evento che tiene traccia degli errori della form. */
  @Output('onFormErrors') onFormErrors$ = new EventEmitter<string[]>();
  /** Output con evento che tiene traccia delle modifiche della form. */
  @Output('onFormSubmit') onFormSubmit$ = new EventEmitter<any>();

  /**
   * Costruttore.
   */
  constructor(
    private _testInputForm: TestInputFormService,
    private _logger: LoggerService,
    private _formBuilder: FormBuilder,
    private _scrivaFormBuilder: ScrivaFormBuilderService,
    private _scrivaUtilities: ScrivaUtilitiesService
  ) {
    // Lancio il setup del componente
    this.setupComponente();
  }

  ngOnInit(): void {
    // Lancio l'init del componente
    this.initComponente();
  }

  ngOnDestroy() {
    // Tento di fare l'unsubscribe dei listener
    try {
      // Verifico che esistano i listener
      if (this.onSubmit) {
        this.onSubmit.unsubscribe();
      }
      if (this.onReset) {
        this.onReset.unsubscribe();
      }
    } catch (e) {
      // Loggo l'errore
      this._logger.warning('ScrivaFormChildComponent failed to unsubscribe', e);
    }
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  /**
   * Funzione di setup per il componente.
   */
  private setupComponente() {
    // Lancio la funzione di gestione per gli errori del form
    this.setupMainFormErrors();
    // Lancio la funzione di setup delle input del form
    this.setupFormInputs();
    // Lancio la funzione di setup per la struttura del form
    this.setupFormConfigs();
    // Lancio la funzione di set per le liste del componente
    this.setupLists();
  }

  /**
   * Funzione di setup per gli errori da verificare al momento della submit della form principale.
   */
  private setupMainFormErrors() {
    // Imposto la lista di errori
    this.formErrors = [...this.EM.MAP_FORM_GROUP_REQUIRED];
  }

  /**
   * Funzione di setup per la configurazione dei dati per la costruzione delle input del form.
   */
  private setupFormInputs() {
    // Recupero il servizio di configurazione
    const s = this._scrivaFormBuilder;
    // Classe di supporto che definisce la configurazioni per le input
    this.formInputs = new TestInputFormFieldsConfig(s);
  }

  /**
   * Funzione di setup per la configurazione strutturale del form group.
   */
  private setupFormConfigs() {
    // Recupero il servizio di configurazione
    const s = this._formBuilder;
    // Istanzio l'oggetto di configurazione
    this.formConfigs = new TestInputFormFormConfigs(s);
    // Tramite la classe di configurazione, definisco la struttura per il main form
    this.mainForm = this.formConfigs.instantiateForm();
  }

  /**
   * Funzione di setup per lo scarico dei dati delle liste del componente.
   */
  private setupLists() {
    // Richiamo la funzione di scarico degli anni per id ambito
    this.aggiornaListaAnni().subscribe({
      error: (e: ScrivaServerError) => {
        // Lancio la gestione dell'errore
        this.onEmitServiziError$.emit(e);
      },
    });
  }

  /**
   * ################
   * FUNZIONI DI INIT
   * ################
   */

  /**
   * Funzione di init per le informazioni del componente.
   */
  private initComponente() {
    // Non ci sono logiche per il momento
  }

  /**
   * #######################
   * FUNZIONI DEL COMPONENTE
   * #######################
   */

  /**
   * Funzione che richiede i dati riferiti alla lista degli anni.
   * La funzione prevede l'aggiornamento automatico della lista omonima.
   * @returns Observable<IScrivaAnnoSelect[]> con le informazioni scaricate.
   */
  aggiornaListaAnni(): Observable<IScrivaAnnoSelect[]> {
    // Richiamo la funzione di scarico degli anni per id ambito
    return this._testInputForm.getAnniList().pipe(
      tap((anni: IScrivaAnnoSelect[]) => {
        // Definisco la lista di anni per la select
        this.listaAnni = anni;
        // #
      })
    );
  }

  /**
   * Funzione che richiede i dati riferiti alla lista dei canoni ufficiali.
   * La funzione prevede l'aggiornamento automatico della lista omonima ed effettua un submit dei dati con i dati di default.
   * @returns Observable<IScrivaAnnoSelect[]> con le informazioni scaricate.
   */
  aggiornaListaAnniSubmit() {
    // Richiamo la funzione di scarico degli anni per id ambito
    this.aggiornaListaAnni().subscribe({
      next: (listaanni: IScrivaAnnoSelect[]) => {
        // Imposto un timeout, per poter permettere alla funzione di aggiornare i dati nella form
        setTimeout(() => {
          // Lancio il submit della form
          this.onFormSubmit();
          // #
        }, 100);
        // #
      },
      error: (e: ScrivaServerError) => {
        // Lancio la gestione dell'errore
        this.onEmitServiziError$.emit(e);
      },
    });
  }

  /**
   * ###############
   * AZIONI SUI FORM
   * ###############
   */

  /**
   * Funzione agganciata all'evento di Submit per il mainForm.
   */
  onFormSubmit() {
    // Verifico che la form esisti
    if (!this.mainForm) {
      return;
    }

    // Lancio la funzione di pre-validazione del form
    this.prepareMainFormForValidation();
    // Il form è stato submittato
    this.mainFormSubmitted = true;

    // Verifico che la form sia valida
    if (this.mainForm.valid) {
      // Recupero i dati della form
      const formData = this.getMainFormRawValue();
      // Gestisco il success per il submit della form
      this.onFormSuccess(formData);
      // #
    } else {
      // Gestisco la visualizzazione degli errori del form usiForm
      this.onFormErrors(this.mainForm);
    }
  }

  /**
   * Funzione che permette di definire le logiche per la verifica del mainForm.
   */
  prepareMainFormForValidation() {
    // Definire le logiche di pre-validazione.
  }

  /**
   * Funzione che permette il recupero del raw value del main form del child.
   * @param c any che definisce una configurazione generica che permette di gestire logiche nell'override della funzione.
   * @returns T contente le informazioni del form in modalità raw value.
   */
  getMainFormRawValue(c?: any): any {
    // Verifico esista l'oggetto
    if (!this.mainForm) {
      return undefined;
    }
    // Il main form esiste, ritorno i dati del form
    return this.mainForm.getRawValue();
  }

  /**
   * Funzione di reset del form e del componente.
   * @param args any con possibili parametri da passare alla funzione.
   */
  onFormReset(...args: any) {
    // Resetto il flag di submit
    this.mainFormSubmitted = false;
    // Reset manuale della form
    this.mainForm.reset();
  }
  /**
   * Funzione di supporto che gestisce il success del submit della form.
   * @param data any contenente le informazioni da emettere al componente padre.
   */
  protected onFormSuccess(data: any) {
    // Emetto l'evento comunicando i messaggi
    this.onFormSubmit$.emit(data);
  
  }

  /**
   * Funzione di supporto che gestisce la visualizzazione dei messaggi d'errore per il form group passato in input.
   * @param formGroup FormGroup da verificare.
   * @param errConfigs IRFCFormErrorsConfigs contenente le configurazioni extra per la gestione della funzione.
   */
  protected onFormErrors(
    formGroup: FormGroup,
    errConfigs?: IRFCFormErrorsConfigs
  ) {
    // Variabili di comodo
    const fg = formGroup;
    const ec = errConfigs;
    // Recupero le configurazioni
    const { formErrors, serverError } = ec || {};

    // Definisco al mappatura degli errori da recuperare
    const me = formErrors || this.formErrors || [];
    // Recupero tutti i messaggi
    const mef = this._scrivaUtilities.getAllErrorMessagesFromForm(fg, me);

    // Verifico e recupero un eventuale messaggio d'errore dall'oggetto errore del server
    const mse =
      this._scrivaUtilities.getMessageFromScrivaServerError(serverError);
    // Verifico se esiste il messaggio
    if (mse) {
      // Aggiungo il messaggio alla lista dei messaggi errore
      mef.push(mse);
    }

    // Emetto l'evento comunicando i messaggi
    this.onFormErrors$.emit(mef);
   
  }


  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */
}
