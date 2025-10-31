/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ScrivaServerError } from '../../../../core/classes/scriva.classes';
import { ScrivaCodesMesseges } from '../../../../core/enums/scriva-codes-messages.enums';
import { ScrivaCheckboxComponent } from '../../../../shared/components/form-inputs/scriva-checkbox/scriva-checkbox.component';
import { ScrivaModalUtilitiesComponent } from '../../../../shared/modals/scriva-modal-utilities/scriva-modal-utilities.component';
import { Compilante } from '../../../../shared/models';
import { IstanzaService, MessageService } from '../../../../shared/services';
import { ScrivaFormBuilderService } from '../../../../shared/services/form-inputs/scriva-form-builder.service';
import { ProfiloContattiModificaEmailService } from '../../services/profilo-contatti/profilo-contatti-modifica-email.service';
import {
  IPCMEFormConfigs,
  stessaEmail,
} from './form-validators/pcme.validators.form-group';
import { PROFILO_CONTATTI_MODIFICA_EMAIL_CONSTS } from './utilities/profilo-contatti-modifica-email.consts';
import { ProfiloContattiModificaEmailErrorsMap } from './utilities/profilo-contatti-modifica-email.errors-maps';
import { PCMEFieldsConfigClass } from './utilities/profilo-contatti-modifica-email.input-configs';
import { TipoEventoEnum } from 'src/app/shared/utils';
import { ChannelType } from 'src/app/features/notifiche/enums/notifiche.enums';
import { IProfiloContattiModificaEmail } from './utilities/profilo-contatti-modifica-email.interfaces';

@Component({
  selector: 'scriva-profilo-contatti-modifica-email',
  templateUrl: './profilo-contatti-modifica-email.component.html',
  styleUrls: ['./profilo-contatti-modifica-email.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ProfiloContattiModificaEmailModalComponent
  extends ScrivaModalUtilitiesComponent<any>
  implements OnInit
{
  /** Costante contenente le informazioni costanti del componente. */
  PCME_C = PROFILO_CONTATTI_MODIFICA_EMAIL_CONSTS;
  /** Costante contenente le mappature degli errori da gestire per le inputs. */
  PCME_EM = new ProfiloContattiModificaEmailErrorsMap();

  /** ViewChild ScrivaCheckboComponent come collegamento al componente che gestisce il flag di autorizzazione. */
  @ViewChild('scrivaCheckboxAutorizzo')
  checkboxAutorizzo: ScrivaCheckboxComponent;

  /** FormGroup contenente le informazioni del profilo utente. */
  pcmeForm: FormGroup;
  /** Boolean che tiene traccia del submit della form. */
  pcmeFormSubmitted: boolean = false;
  /** IPCMEFormConfigs che definisce le configurazioni dinamiche da passare ai validatori del form. */
  pcmeConfigs: IPCMEFormConfigs = { formSubmitted: false };
  /** PCMEFieldsConfigClass contenente la configurazione di supporto per la generazione delle input del form. */
  inputs: PCMEFieldsConfigClass;

  /** String contenente il gestUID per la richiesta di verifica dell'OTP. */
  private gestUIDOTP: string;
  /** String che definsce il modello dati collegato alla input per il codice di verifica email. */
  codiceOTP: string;

  /**
   * Costruttore.
   */
  constructor(
    public activeModal: NgbActiveModal,
    private _formBuilder: FormBuilder,
    private _istanzaService: IstanzaService,
    private _message: MessageService,
    private _profiloContattiME: ProfiloContattiModificaEmailService,
    private _scrivaFormBuilder: ScrivaFormBuilderService
  ) {
    // Richiamo il super
    super(activeModal);

    // Lancio il setup del componente
    this.setupComponente();
  }

  /**
   * NgOnInit.
   */
  ngOnInit() {
    // Lancio l'init del componente
    this.initComponente();
  }

  /**
   * ################################
   * FUNZIONI DI SETUP DEL COMPONENTE
   * ################################
   */

  /**
   * Funzione di setup del componente, lanciato dal costruttore.
   */
  private setupComponente() {
    // Inizializzo i campi del form
    this.setupInputsForm();
  }

  /**
   * Funzione di setup per la configurazione delle input del componente.
   */
  private setupInputsForm() {
    // Effettuo la new della classe con le configurazioni dell'input
    this.inputs = new PCMEFieldsConfigClass({
      scrivaFormBuilder: this._scrivaFormBuilder,
    });
  }

  /**
   * ###############################
   * FUNZIONI DI INIT DEL COMPONENTE
   * ###############################
   */

  /**
   * Funzione di init del componente, lanciato dall'NgOnInit.
   */
  private initComponente() {
    // Lancio l'inizializzazione del form
    this.initForm();
  }

  /**
   * Funzione di comodo per l'init del form del componente.
   */
  private initForm() {
    // Genero la configurazione dei campi
    const fields = this._profiloContattiME.generateControlsConfig();
    const validators = {
      validators: [
        stessaEmail(
          this.PCME_C.FC_EMAIL,
          this.PCME_C.FC_EMAIL_RIPETI,
          this.pcmeConfigs
        ),
      ],
    };
    // Istanzio il form con le informazioni
    this.pcmeForm = this._formBuilder.group(fields, validators);
  }

  /**
   * ###################################
   * FUNZIONI DI VERIFICA/CONFERMA EMAIL
   * ###################################
   */

  /**
   * Funzione collegata alla pagina di conferma del cambio email.
   */
  confermaEmail() {
    // Conferma premuto, modifico il flag di submission del form
    this.pcmeFormSubmitted = true;
    this.pcmeConfigs.formSubmitted = true;
    // Lancio manualmente il processo di verifica sui validatori del form
    this.pcmeForm.updateValueAndValidity();

    // Verifico che il form sia valido
    if (!this.pcmeForm.valid) {
      // Blocco il flusso, i campi non vanno bene
      return;
    }

    // Estraggo l'oggetto dal form
    let formData: IProfiloContattiModificaEmail;
    formData = this.pcmeForm.getRawValue();

    // Richiamo il servizio per la generazione dell'OTP
    this._profiloContattiME.generaOTPEmail(formData).subscribe({
      next: (gestUID: string) => {
        // Lancio la funzione di gestione a seguito dell'otp genrato
        this.onOTPGenerato(gestUID);
        // #
      },
      error: (e: ScrivaServerError) => {
        // Visualizzo il messaggio d'errore generato dal server
        this.onServiceError(e);
      },
    });
  }

  /**
   * Funzione richiamata quando il servizio di generazione OTP per il cambio email è completato.
   * @param gestUID string con il gestUID generato.
   */
  private onOTPGenerato(gestUID: string) {
    // Recupero il dato dell'email dal form
    const email = this.emailFC.value;
    // Definisco le informazioni per la generazione dell'evento
    const idIst: number = null;
    const codTE: string = TipoEventoEnum.UPD_CONTATTO_UTENTE;
    const canale: string = ChannelType.email;

    this._istanzaService
      .generaEvento(idIst, codTE, canale, gestUID, email)
      .subscribe({
        next: (res: any) => {
          // Otp richiesto, salvo il gest uid da mandare per la verifica
          this.gestUIDOTP = gestUID;
          // Generato l'OTP vado a disabilitare i campi per email/ripeti email
          this.pcmeForm.disable();
          // Visualizzo il messaggio all'utente di success
          const code = ScrivaCodesMesseges.I002;
          this.showAlert(code);
          // #
        },
        error: (e: ScrivaServerError) => {
          // Visualizzo il messaggio d'errore generato dal server
          this.onServiceError(e);
        },
      });
  }

  /**
   * #######################################
   * FUNZIONI DI GESTIONE PER CONFERMA/RESET
   * #######################################
   */

  /**
   * Funzione invocata dal pulsante "ANNULLA" della modale.
   * La funzione prevede il reset dei dati della modale.
   */
  resetModificaEmail() {
    // Effettuo il reset del flag autorizzo
    this.checkboxAutorizzo.reset();
    // Effettuo il reset del form per le email
    this.pcmeForm.reset();
    this.pcmeForm.enable();
    this.pcmeFormSubmitted = false;
    this.pcmeConfigs.formSubmitted = false;
    // Pulisco le informazioni relative al controllo email
    this.codiceOTP = undefined;
    this.gestUIDOTP = undefined;
  }

  /**
   * Funzione invocata dal pulsante "CONFERMA" della modale.
   * La funzione prevede il salvataggio della nuova email dell'utente.
   */
  modificaEmail() {
    // Per la modifica dell'email recupero otp e gestUID opt
    const otp = this.codiceOTP;
    const gestUID = this.gestUIDOTP;
    const email = this.emailFC.value;

    // Verifico che ci siano i dati per poter proseguire
    if (!otp || !gestUID || !email) {
      // Blocco il flusso
      return;
    }

    // Richiamo la funzione del servizio che verifichi l'otp e salvi la nuova email
    this._profiloContattiME.aggiornaEmail(otp, gestUID, email).subscribe({
      next: (compilante: Compilante) => {
        // I dati sono stati salvati, chiudo la modale
        this.modalConfirm(compilante);
        // #
      },
      error: (e: ScrivaServerError) => {
        // Gestisco l'errore generato dal server
        this.onServiceError(e);
        // #
      },
    });
  }

  /**
   * #####################################
   * FUNZIONI DI UTILITY PER IL COMPONENTE
   * #####################################
   */

  /**
   * Funzione di supporto per la gestione della segnalazione dell'errore da parte dei servizi.
   * @param e ScrivaServerError con il dettaglio d'errore generato.
   */
  private onServiceError(e?: ScrivaServerError) {
    // Si è verificato un errore, gestisco la segnalazione utente
    const code = e?.error?.code ?? ScrivaCodesMesseges.E100;
    // Visualizzo il messaggio
    this.showAlert(code);
  }

  /**
   * Funzione di supporto per la gestione degli errori nel componente, dato il codice errore.
   * @param code string con il codice del messaggio da visualizzare.
   */
  private showAlert(code: string) {
    // Definisco le configurazioni per la visualizzazione dell'alert
    const target = this.PCME_C.ALERT_ANCHOR;
    const autoFade = true;
    // Visualizzo il messaggio
    this._message.showMessage(code, target, autoFade);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter per il recupero del form control per il campo: autorizzo.
   * @returns AbstractControl con il form control del campo.
   */
  get autorizzoFC(): AbstractControl {
    // Recupero il form control dal form
    return this.pcmeForm.get(this.PCME_C.FC_AUTORIZZO);
  }

  /**
   * Getter per il recupero del form control per il campo: email.
   * @returns AbstractControl con il form control del campo.
   */
  get emailFC(): AbstractControl {
    // Recupero il form control dal form
    return this.pcmeForm.get(this.PCME_C.FC_EMAIL);
  }

  /**
   * Getter per il recupero del form control per il campo: email.
   * @returns AbstractControl con il form control del campo.
   */
  get emailRipetiFC(): AbstractControl {
    // Recupero il form control dal form
    return this.pcmeForm.get(this.PCME_C.FC_EMAIL_RIPETI);
  }

  /**
   * Getter di comodo per la gestione dell'abilitazione del pulsante di conferma salvataggio email.
   * @returns boolean che definisce se le condizioni per il salvataggio dell'email sono OK.
   */
  get allowSalvaEmail(): boolean {
    // Recupero il flag di autorizzazione gestione dati personali
    const autorizzo: boolean = this.autorizzoFC?.value;
    // Verifico le informazioni per il codice OTP
    const emailConfermata = this.gestUIDOTP != undefined;
    const otpInserito = this.codiceOTP != undefined && this.codiceOTP != '';

    // L'abilitazione al salva email prevede tutti i check validi
    return autorizzo && emailConfermata && otpInserito;
  }
}
