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
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormioBaseComponent, FormioComponent } from 'angular-formio';
import {
  FormioForm,
  FormioOptions,
  FormioRefreshValue,
} from 'angular-formio/formio.common';
import { clone, cloneDeep } from 'lodash';
import { NgxSpinnerService } from 'ngx-spinner';
import { forkJoin, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import {
  OggettoIstanza,
  Opera,
  TipologiaOggetto,
} from 'src/app/features/ambito/models';
import { Adempimento, Help, ModalConfig, Quadro } from 'src/app/shared/models';
import {
  AuthStoreService,
  HelpService,
  MessageService,
} from 'src/app/shared/services';
import { AttoreGestioneIstanzaEnum } from 'src/app/shared/utils';
import { ScrivaServerError } from '../../../../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../../../../core/enums/scriva-codes-messages.enums';
import { FormioCustomEvents } from '../../../../../../../shared/components/formio/utilities/formio.enums';
import { CommonConsts } from '../../../../../../../shared/consts/common-consts.consts';
import { ScrivaComponenteApp } from '../../../../../../../shared/enums/scriva-utilities/scriva-utilities.enums';
import { IFormIoRenderOptions } from '../../../../../../../shared/interfaces/scriva-utilities.interfaces';
import { FormioService } from '../../../../../../../shared/services/formio/formio.service';
import {
  IFormioRenderOptionsParams,
  IFormioValidationCheck,
} from '../../../../../../../shared/services/formio/utilities/formio.interfaces';
import { IChiaveHelpParams } from '../../../../../../../shared/services/help/utilities/help.interfaces';
import { LoggerService } from '../../../../../../../shared/services/logger.service';
import { IDTCaptazioniOperaModalCallbacks } from '../../../captazioni/modals/captazioni-opera-modal/utilities/captazioni-opera-modal.interfaces';
import { OpereConsts } from '../../utilities/opere.consts';
import {
  IDTOperaModalParams,
  IDTOperaSalvataggio,
  IDTOperModalReq,
  IFormIoDTOperaParams,
} from './utilities/dati-tecnici-opera-modal.interfaces';

@Component({
  selector: 'app-dati-tecnici-opera-modal',
  templateUrl: './dati-tecnici-opera-modal.component.html',
  styleUrls: ['./dati-tecnici-opera-modal.component.scss'],
})
export class DatiTecniciOperaModalComponent implements OnInit {
  /** Oggetto di costanti comuni all'applicazione. */
  protected C_C = new CommonConsts();
  /** OpereConsts con le informazioni costanti per il componente. */
  protected O_C = new OpereConsts();
  /** string che definisce la proprietà del FormIo che definisce se una sezione dati risulta obbligatoria. */
  protected SECTION_REQUIRED: string = this.O_C.SECTION_REQUIRED;

  /** string costante che definisce il contenitore per agganciare i messaggi di segnalazione. */
  readonly ALERT_TARGET_MODALE: string = 'captazione-content';

  /** ViewChild che permette di accedere alla struttura dati del componente della libreria Formio. */
  @ViewChild('ngFormio', { static: false }) ngFormio: FormioComponent;

  /** Input IDTOperaModalParams con i parametri passati alla modale come dati di configurazione. */
  @Input() dataModal: IDTOperaModalParams;
  /** Input IDTCaptazioniOperaModalCallbacks con le funzioni di callback per la gestione dei dati tecnici opera. */
  @Input() callbacks: IDTCaptazioniOperaModalCallbacks;
  /** Output IDTOperaSalvataggio con le informazioni aggiornate dei dati tecnici opera. */
  @Output() onSalvaModifiche$ = new EventEmitter<IDTOperaSalvataggio>();

  /** Help[] con gli help collegati alla maschera. */
  protected helpQuadro: Help[] = [];

  /** any contenente una copia dei dati del formio sulla quale l'utente può lavorare ed usato dal formio in pagina. */
  protected formioUserData: any;
  /** boolean come flag per sapere se la configurazione FormIo è valida. */
  validFormIo: boolean = false;
  /** FormioForm con la struttura del FormIo da visualizzare in pagina. */
  customForm: FormioForm;
  /** EventEmitter<FormioRefreshValue> FormIo che permette la gestione dell'aggiornamento dei dati del formio. */
  triggerRefresh = new EventEmitter<FormioRefreshValue>();
  /** IFormIoRenderOptions contenente le informazioni da iniettare all'interno del formio. */
  renderOptions: IFormIoRenderOptions = {};
  /** FormioOptions con le configurazione per la gestione del componente FormIo. */
  formioOptions: FormioOptions = {};
  /** boolean che definisce lo stato di scrittura dei dati per FormIo. */
  readOnlyFormIo: boolean = false;
  /** any con le informazioni da passare al FormIo come sorgente dati per compilare i campi del form. */
  formioSubmission: any;
  /** boolean come flag che tiene traccia delle modifiche apportate sul formio. Se l'utente compie qualche azione, il form verrà considerato modificat. */
  formioTouched: boolean = false;

  /**
   * Costruttore.
   */
  constructor(
    public activeModal: NgbActiveModal,
    protected _authStore: AuthStoreService,
    protected _formio: FormioService,
    protected _help: HelpService,
    protected _logger: LoggerService,
    protected _message: MessageService,
    protected _spinner: NgxSpinnerService
  ) {
    // Lancio la funzione di setup componente
    this.setupComponente();
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  // #region "SETUP COMPONENTE"

  /**
   * Funzione di setup per le informazioni del componente.
   */
  protected setupComponente() {
    // Lancio il setup per i parametri di redenrizzazione formio
    this.setupFormIoOptions();
    this.setupFormIoRenderOptions();
  }

  /**
   * Funzione di setup che definisce le logiche di configurazione del componente FormIo.
   */
  protected setupFormIoOptions() {
    // Definisco l'oggetto con i parametri per le render options
    const overrideOptions: FormioOptions = {};
    // Richiedo la generazione dell'oggetto per le render options
    this.formioOptions = this._formio.getFormIoOptions(overrideOptions);
  }

  /**
   * Funzione di setup che raccoglie tutte le logiche, servizi e configurazioni da passare come riferimento al contesto FormIo.
   */
  protected setupFormIoRenderOptions() {
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
    // Lancio l'init dati per la struttura formio
    this.initFormIoData();
    // Lancio la funzione di init per la configurazione del form
    this.initForm();

    // Lancio la funzione completa per lo scarico dei dati del componente
    this.initData();
  }

  /**
   * Funzione di init che gestisce l'inizializzazione dei dati per la gestione del formio.
   */
  protected initFormIoData() {
    // Creo una copia dei dati del formio e l'assegno per la sessione di lavoro
    this.formioUserData = this.sourceData;
  }

  /**
   * Funzione di init che gestisce tutte le logiche per il formio e la pagina.
   */
  protected initForm() {
    // Verifico e recupero la struttura del formio
    const jsonForm = this.handleFormIoStructure();
    // Verifico se è stato configurato correttamente l'oggetto
    if (!jsonForm) {
      // Non c'è la struttura blocco il caricamento dati
      return;
      // #
    }

    // Inizializzo tutti i dati per la struttura del FormIo
    this.initFormIoStructure(jsonForm);
  }

  /**
   * Funzione adibita alla gestione specifica della struttura e dati per il FormIo.
   * @param jsonForm any con la struttura per la visualizzazione del formio.
   */
  protected initFormIoStructure(jsonForm: any) {
    // Verifico l'input
    if (!jsonForm) {
      // Manca la configurazione
      return;
    }

    // Creo una copia della struttura in input
    const jsonFormIo = clone(jsonForm);

    // Assegno alla variabile del componente e pagina la struttura dati
    this.customForm = jsonFormIo;
    // Creo un oggetto con le informazioni extra da definire come render options
    const formioDTOperaParams: IFormIoDTOperaParams =
      this.initFormIoInjectParams();
    // Richiamo la funzione per aggiornare i render options
    this._formio.addFormIoRenderOptions(
      this.renderOptions,
      formioDTOperaParams
    );

    // Gestisco la condizione di sola lettura per formio
    const isAGIWrite: boolean =
      this.attoreGestioneIstanza === AttoreGestioneIstanzaEnum.WRITE;
    const readOnlyParams: boolean = this.readOnly;
    // Definisco il valore per il readonly del formio
    const readOnlyFormIo: boolean = readOnlyParams || !isAGIWrite;
    // Assegno alla variabile del componente
    this.readOnlyFormIo = readOnlyFormIo;

    // NOTA DELLO SVILUPPATORE: durante il refactor mi son fermato su questo, non capisco perché è stato messo dentro come timeout, da capire
    setTimeout(() => {
      // Definisco le informazioni per i dati da visualizzare all'interno del FormIo
      const data = this.formioUserData ?? {};
      // Definisco per l'oggetto submission di formio le informazioni per la popolazione dei campi
      this.formioSubmission = { data };
      // Considero valida la struttura
      this.validFormIo = true;
      // #
    }, 0);
    // #
  }

  /**
   * Funzione di init che genera le informazioni da iniettare all'interno del contesto FormIo.
   * @returns IFormIoDTOperaParams con le informazioni da iniettare.
   */
  protected initFormIoInjectParams(): IFormIoDTOperaParams {
    // Creo un oggetto con le informazioni extra da definire come render options
    const formioDTOperaParams: IFormIoDTOperaParams = {
      componenteQuadro: this.componente as ScrivaComponenteApp,
      oggetto: this.opera,
      oggettoIstanza: this.oggettoIstanza,
    };

    // Ritorno la configurazione
    return formioDTOperaParams;
  }

  /**
   * Funzione di init che si occupa di scaricare tutte le informazioni dai servizi per il componente e i dati ad essi derivati.
   * La funzione gestisce anche la parte grafica di caricamento.
   */
  protected initData() {
    // Avvio lo spinner
    this._spinner.show();

    // Avvio lo scarico dati
    this.initData$().subscribe({
      next: (response: any) => {
        // Blocco lo spinner
        this._spinner.hide();
        // #
      },
      error: (e: ScrivaServerError) => {
        // Blocco lo spinner
        this._spinner.hide();
        // Gestisco la comunicazione all'utente
        this.onServiziError(e);
      },
    });
  }

  /**
   * Funzione di init che si occupa di scaricare tutte le informazioni dai servizi per il componente e i dati ad essi derivati.
   */
  protected initData$(): Observable<any> {
    // Lancio la prima chiamata di scarico dati a se stanti
    const req: IDTOperModalReq = {
      help: this.initHelp$(),
    };

    // Avvio lo scarico dati
    return forkJoin(req);
  }

  // #endregion "INIT COMPONENTE"

  // #region "INIT HELP"

  /**
   * Funzione che scarica e imposta localmente le informazioni per gli oggetti di help.
   * @returns Observable<Help[]> con le informazioni delle configurazioni dell'help scaricati.
   */
  protected initHelp$(): Observable<Help[]> {
    // Recupero le informazioni per lo scarico dati
    const helpConfig: IChiaveHelpParams = {
      adempimento: this.adempimento,
      componente: this._authStore.getComponente(),
      quadro: this.quadro,
    };

    // Lancio la funzione di scarico e set dei dati
    return this._help.getHelpMascheraByParams(helpConfig).pipe(
      tap((help: Help[]) => {
        // Vado a salvare localmente le informazioni
        this.helpQuadro = help;
        // #
      })
    );
  }

  // #endregion "INIT HELP"

  /**
   * #############################################
   * FUNZIONALITA' FORMIO CONFIGURAZIONE E UTILITY
   * #############################################
   */

  // #region "FUNZIONALITA' FORMIO"

  /**
   * Funzione che forza l'aggiornamento dei dati formio.
   * @debug Il refresh attiva di nuovo tutti i trigger e i controlli di tutti, verificare che sul FormIo ci siano delle condizioni ad hoc che impediscano il comportamento (vedi documentazione per le modali nella documentazione interna "SCRIVA - Best practices di progettazione V3.0.docx").
   * @param data any con le informazioni per aggiornare il formio.
   */
  protected updateFormIo(data: any) {
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

  // #endregion "FUNZIONALITA' FORMIO"

  /**
   * ###########################
   * FUNZIONALITA' EVENTI FORMIO
   * ###########################
   */

  // #region "EVENTI FORMIO"

  /**
   * Funzione collegata all'evento del componente FormIo al cambio dei dati interni al form.
   * La funzione colleziona le modifiche e aggiorna la variabile del componente.
   * @param formData any con l'oggetto generato da FormIo con i dati del form.
   */
  onFormioChange(formData: any) {
    // Verifico se l'oggetto passato esiste e risulta modificato
    if (formData?.data && formData?.isModified) {
      // Aggiorno il flag del form, definendolo "sporco"
      this.formioTouched = true;
      // Le informazioni sono state aggiorna, allineo l'oggetto del componente
      this.formioUserData = formData.data;
      // #
    }
  }

  /**
   * Funzione che si registra all'evento di "formLoad" di FormIo.
   * @param event any con le informazioni dell'evento.
   * @unused (formLoad)="formLoad($event)" on HTML Component
   */
  onFormioLoad(event: any) {
    // Unused
  }

  /**
   * Funzione che rimane in ascolto dell'evento "ready" di FormIo.
   * La funzione, al termine del caricamento di FormIo, recupererà le informazioni al suo interno e le salverà localmente.
   * @param formioComponent FormioBaseComponent con l'istanza del componente FormIo.
   */
  onFormioReady(formioComponent: FormioBaseComponent) {
    // Recupero dall'istanza le informazioni del formio
    const data: any = formioComponent?.submission?.data;
    // Verifico se esiste un oggetto effettivo per i dati
    if (data) {
      // Esistono dati, li salvo localmente
      this.formioUserData = data;
    }
  }

  /**
   * Funzione invocata all'emissione di un evento custom da parte del componente formio.
   * @param formioEvent any contenente le informazioni di dettaglio per l'evento generato dal componente formio.
   */
  onCustomEvent(formioEvent: any) {
    // Cerco d'identificare l'evento generato dal formio
    switch (formioEvent.type) {
      // ### Visualizzazione degli helper
      case FormioCustomEvents.visualizzaHelper:
        // Tento di visualizzare l'helper data la chiave identificativa censita sul formio
        this.formioHelp(formioEvent);
        break;
      // ### default
      default:
        break;
    }
  }

  /**
   * Funzione che gestisce le logiche per l'apertura di un help derivato dall'evento formio.
   * @param formioEvent any con l'oggetto contenente i dati generati per l'evento formio.
   */
  protected formioHelp(formioEvent: any) {
    // Definisco i parametri per aprire il prompt help di formio
    const chiaveHelpParams: IChiaveHelpParams = {
      adempimento: this.adempimento,
      componente: this.componente as ScrivaComponenteApp,
      quadro: this.quadro,
    };
    // Richiamo la funzione del servizio
    this._formio.formioHelp(formioEvent, chiaveHelpParams, this.helpQuadro);
  }

  // #endregion "EVENTI FORMIO"

  /**
   * #####################################
   * FUNZIONALITA' SALVATAGGIO DATI FORMIO
   * #####################################
   */

  // #region "SALVATAGGIO FORMIO"

  /**
   * Funzione che gestisce le logiche per il salvataggio dei dati della modale.
   * La funzione è agganciata al pulsante HTML "SALVA MODIFICHE" della modale.
   */
  onSaveChanges() {
    // Definisco le istruzioni per la callback da invocare quando il formio sarà validato
    const callback: (valid: boolean) => any = (valid: boolean) => {
      // Verifico se il form è valido
      if (!valid) {
        // Gestisco la segnalazione
        this.onSaveChangesErrors();
        // Blocco il flusso
        return;
      }

      // Recupero le informazioni del formio
      const formioData: any = cloneDeep(this.formioUserData);
      // Genero l'oggetto per salvare i dati tecnici
      const dtOperaSave: IDTOperaSalvataggio = { datiTecnici: formioData };

      // Condizione per l'uso dell'evento da propagare, lo lancio
      this.onSalvaModifiche$.emit(dtOperaSave);
      // Chiudo la modale passando le informazioni del FormIo
      this.onConfirm(dtOperaSave);
      // #
    };
    // Recupero l'istanza del componente del formio gestito
    const formFormIo: FormioComponent = this.ngFormio;
    // Costruisco l'oggetto con i parametri per la gestione della validità del FormIo
    const params: IFormioValidationCheck = { callback, formFormIo };

    // Richiamo la funzione di gestione
    this._formio.handleFormIoValidation(params);
  }

  /**
   * Funzione di comodo che gestisce la segnalazione dell'errore quando l'utente tenta di salvare i dati del form.
   */
  protected onSaveChangesErrors() {
    // Definisco delle variabili di comodo per la gestione per l'errore
    const code: string = ScrivaCodesMesseges.E001;
    const target: string = this.ALERT_TARGET_MODALE;
    const autoFade: boolean = false;
    // Effettuo la segnalazione all'utente
    this._message.showMessage(code, target, autoFade);
  }

  // #endregion "SALVATAGGIO FORMIO"

  /**
   * ############################
   * FUNZIONI SEGNALAZIONE UTENTE
   * ############################
   */

  // #region "FUNZIONI SEGNALAZIONE"

  /**
   * Funzione di check che verifica che la configurazione per la modale sia valida per quanto riguarda la struttura del FormIo.
   * Se viene intercettato errore, la funzione gestisce la comunicazione all'utente per la configurazione invalida.
   * @returns any con la struttura del FormIo. Undefined se non è definito.
   */
  protected handleFormIoStructure(): any {
    // Recupero la configurazione per la struttura del FormIo
    const jsonForm = this.structureFormIo;
    // Verifico se è stato configurato correttamente l'oggetto
    if (!jsonForm) {
      // Definisco delle variabili di comodo per la gestione per l'errore
      const code: string = ScrivaCodesMesseges.E100;
      const target: string = this.ALERT_TARGET_MODALE;
      const autoFade: boolean = false;
      // Effettuo la segnalazione all'utente
      this._message.showMessage(code, target, autoFade);

      // Definisco le variabili per il log in console
      const t = `Exception - handleFormIoStructure`;
      const b = {
        quadro: this.quadro,
        oggettoIstanza: this.oggettoIstanza,
        detail: `FormIo structure not found.`,
      };
      // Loggo l'errore
      this._logger.error(t, b);

      // La struttura non è valida
      return undefined;
      // #
    }

    // La struttura è valida
    return jsonForm;
  }

  // #endregion "FUNZIONI SEGNALAZIONE"

  /**
   * #####################
   * FUNZIONI DELLA MODALE
   * #####################
   */

  // #region "FUNZIONI MODALE"

  /**
   * Funzione che invca la chiusura della modale con success.
   * @param data IDTOperaSalvataggio con possibili valori da passare al componente chiamante.
   */
  onConfirm(dtOperaSave: IDTOperaSalvataggio) {
    // Richiamo la chiusura della modale con successo
    this.activeModal.close(dtOperaSave);
  }

  /**
   * Funzione che invoca la chiusura della modale.
   * @param askConfirm boolean che chiede all'utente la conferma per chiudere la modale. Per default è: false.
   */
  onClose(askConfirm: boolean = false) {
    // Verifico se devo chiedere conferma per chiudere la modale poiché l'utente ha "toccato" il formio
    if (askConfirm && this.formioTouched) {
      // Definisco le callback in caso l'utente confermi o annulli l'operazione
      const onConfirm = () => {
        // Richiamo la chiusura della modale
        this.activeModal.close();
        // #
      };
      // Genero la configurazione per la richiesta di conferma da parte utente
      let askConfirm: ModalConfig;
      askConfirm = this.C_C.confermaAnnullamentoModalConfigs(onConfirm);
      // Richiamo il servizio di messaggistica per chiedere conferma all'utente la conferma di uscita dalla modale
      this._message.showConfirmation(askConfirm);
      // #
    } else {
      // Flusso normale, chiudo la modale
      this.activeModal.close();
      // #
    }
  }

  /**
   * Funzione che invoca la chiusura della modale.
   */
  onCancel(data?: any) {
    // Richiamo la chiusura della modale
    this.activeModal.dismiss(data);
  }

  // #endregion "FUNZIONI MODALE"

  /**
   * ###################
   * FUNZIONI DI UTILITY
   * ###################
   */

  // #region "UTILITY"

  /**
   * Funzione di supporto che gestisce gli errori generati dalla chiamata ai servizi.
   * @param e ScrivaServerError con l'oggetto d'errore generato dalla chiamata.
   */
  protected onServiziError(e?: ScrivaServerError) {
    // Definisco le informazioni per il messaggio d'errore
    const errorCode: string = e?.error?.code;
    const m: string = errorCode ? errorCode : ScrivaCodesMesseges.E100;
    const fade: boolean = !errorCode;
    const target: string = this.ALERT_TARGET_MODALE;
    // Richiamo la visualizzazione del messaggio
    this._message.showMessage(m, target, fade);
  }

  // #endregion "UTILITY"

  /**
   * ###############
   * GETTER E SETTER
   * ###############
   */

  // #region "GETTER E SETTER"

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns string con il valore del paramatro.
   */
  get attoreGestioneIstanza(): string {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return clone(this.dataModal?.attoreGestioneIstanza);
  }

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns string con il valore del paramatro.
   */
  get componente(): string {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return clone(this.dataModal?.componente);
  }

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns Opera con il valore del paramatro.
   */
  get opera(): Opera {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return cloneDeep(this.dataModal?.opera);
  }

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns OggettoIstanza con il valore del paramatro.
   */
  get oggettoIstanza(): OggettoIstanza {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return cloneDeep(this.dataModal?.oggettoIstanza);
  }

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns boolean con il valore del paramatro.
   */
  get readOnly(): boolean {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return clone(this.dataModal?.readOnly);
  }

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns Quadro con il valore del paramatro.
   */
  get quadro(): Quadro {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return cloneDeep(this.dataModal?.quadro);
  }

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns Adempimento con il valore del paramatro.
   */
  get adempimento(): Adempimento {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return cloneDeep(this.dataModal?.adempimento);
  }

  /**
   * Getter che recupera le informazioni dall'oggetto dei parametri della modale.
   * @returns any con il valore del paramatro.
   */
  get sourceData(): any {
    // Recupero la configurazione dai parametri della modale e creo una copia
    return cloneDeep(this.dataModal?.sourceData);
  }

  /**
   * Getter che recupera la configurazione per mostrare il salva della modale.
   * Per default è true.
   * @returns boolean con la configurazione.
   */
  get showModalSalva(): boolean {
    // Cerco di recuperare la configurazione
    return this.dataModal?.showModalSalva ?? true;
  }

  /**
   * Getter che recupera la configurazione strutturale per FormIo.
   * @returns any con la struttura FormIo da caricare.
   */
  get structureFormIo(): any {
    // Definisco delle variabili di supporto per il recupero dati
    let oggettoIstanza: OggettoIstanza;
    oggettoIstanza = this.oggettoIstanza;
    let quadro: Quadro;
    quadro = this.quadro;

    // Recupero il codice tipologia dall'oggetto istanza
    let tipologiaOggetto: TipologiaOggetto;
    tipologiaOggetto = oggettoIstanza?.tipologia_oggetto;
    const codTipologia: string = tipologiaOggetto?.cod_tipologia_oggetto;

    // Definisco la variabile per la struttura del formio
    let jsonForm: any;

    // Per sicurezza racchiudo le logiche in un try catch
    try {
      // Recupero la configurazione per la struttura del FormIo
      jsonForm = quadro?.json_configura_quadro[codTipologia];
      // #
    } catch (e) {
      // Errore trscurabile
    }

    // Ritorno la configurazione
    return jsonForm;
  }

  /**
   * Getter che recupera la modalità applicativa in uso.
   * @returns boolean con il risultato del check.
   */
  get isFrontOffice(): boolean {
    // Recupero il valore dal servizio
    return this._authStore.isFrontOffice;
  }

  /**
   * Getter che recupera la modalità applicativa in uso.
   * @returns boolean con il risultato del check.
   */
  get isBackOffice(): boolean {
    // Recupero il valore dal servizio
    return this._authStore.isBackOffice;
  }

  /**
   * Getter che definisce le logiche per visualizzare il footer con il pulsante: CHIUDI.
   * @returns boolean con il risultato del check.
   */
  get showChiudi(): boolean {
    // Il pulsante chiudi si vede se siamo in sola lettura o la modalità "SALVA MODIFICHE" interne a FormIo
    const readOnly = this.readOnly;
    const salvaInnerFormio = !this.showModalSalva;
    // Se una delle due condizioni è vera, allora visualizzo solo il chiudi
    return readOnly || salvaInnerFormio;
  }

  // #endregion "GETTER E SETTER"
}
