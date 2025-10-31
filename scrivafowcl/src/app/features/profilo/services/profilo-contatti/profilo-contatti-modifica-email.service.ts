/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Injectable } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, switchMap, tap } from 'rxjs/operators';
import { DynamicObjAny } from '../../../../core/interfaces/scriva.interfaces';
import { Compilante } from '../../../../shared/models';
import { AuthStoreService } from '../../../../shared/services';
import { AuthService } from '../../../../shared/services/auth/auth.service';
import { scrivaCheckboxTrue } from '../../../../shared/services/scriva-utilities/scriva-utilities.validators';
import { Accreditamento } from '../../../accreditamento/models';
import { AccreditamentoService } from '../../../accreditamento/services';
import { PROFILO_CONTATTI_MODIFICA_EMAIL_CONSTS } from '../../modals/profilo-contatti-modifica-email/utilities/profilo-contatti-modifica-email.consts';
import { IProfiloContattiModificaEmail } from '../../modals/profilo-contatti-modifica-email/utilities/profilo-contatti-modifica-email.interfaces';

@Injectable({ providedIn: 'root' })
export class ProfiloContattiModificaEmailService {
  /** Costante contenente le informazioni costanti del componente. */
  private PCME_C = PROFILO_CONTATTI_MODIFICA_EMAIL_CONSTS;

  /**
   * Costruttore.
   */
  constructor(
    private _authStore: AuthStoreService,
    private _auth: AuthService,
    private _accreditamento: AccreditamentoService
  ) {}

  /**
   * ################################################
   * FUNZIONI PER LA GESTIONE DEL FORM DEL COMPONENTE
   * ################################################
   */

  /**
   * Funzione di init di supporto per la generazione delle configurazioni per il form group del componente.
   * @returns DynamicObjAny contenente le configurazioni dei campi del form.
   */
  generateControlsConfig(): DynamicObjAny {
    // Definisco un oggetto composto da chiavi definite come costanti per la configurazione del form del componente
    const controlsConfig: DynamicObjAny = {};
    // Definisco chiave e valore specifico per ogni elemento del form
    // # Flag di autorizzazione
    controlsConfig[this.PCME_C.FC_AUTORIZZO] = new FormControl(
      { value: null, disabled: false },
      { validators: [scrivaCheckboxTrue()] }
    );
    // # Email
    controlsConfig[this.PCME_C.FC_EMAIL] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required, Validators.email] }
    );
    // # Ripeti email
    controlsConfig[this.PCME_C.FC_EMAIL_RIPETI] = new FormControl(
      { value: null, disabled: false },
      { validators: [Validators.required, Validators.email] }
    );

    // Ritorno l'insieme di configurazioni
    return controlsConfig;
  }

  /**
   * ##############################
   * FUNZIONI CON RICHIAMO ALLE API
   * ##############################
   */

  /**
   * Funzione che genera il codice OTP per la mail dell'utente in uso.
   * L'otp verrà generato per la modifica effettiva dell'email.
   * @param formData IProfiloContattiModificaEmail con le informazioni impostate sul form dati.
   * @returns Observable<string> con il gestUID risultante dalla chiamata in success per la richiesta dell'OTP.
   */
  generaOTPEmail(formData: IProfiloContattiModificaEmail): Observable<string> {
    // Recupero i dati del compilante
    const compilante = this._authStore.getCompilante();
    // Genero un oggetto Accreditamento per la generazione dell'OTP
    const accreditamento: Accreditamento = {
      cf_accredito: compilante.cf_compilante,
      cognome_accredito: compilante.cognome_compilante,
      nome_accredito: compilante.nome_compilante,
      des_email_accredito: formData.email,
      flg_autorizza_dati_personali: true,
    };

    // Richiamo il servizio di generazione dell'OTP
    return this._accreditamento.sendMailOtp(accreditamento).pipe(
      map((accreditamento: Accreditamento) => {
        // Estraggo il gestUID dall'oggetto generato
        return accreditamento.gestUID;
      })
    );
  }

  /**
   * Funzione che invoca la verifica dell'OTP e salva le informazioni per la nuova email del compilante.
   * @param otp string con il codice OTP generato.
   * @param gestUID string contenente il gestUID generato assieme al codice OTP.
   * @param email string che definisce la nuova email per l'aggiornamento.
   * @returns Observable<Compilante> con le informazioni aggiornate per il compilante.
   */
  aggiornaEmail(
    otp: string,
    gestUID: string,
    email: string
  ): Observable<Compilante> {
    // Il primo passo per la modifica dell'email è la verifica dell'otp
    return this._accreditamento.verificaOTP(gestUID, otp).pipe(
      switchMap((compilanteRes: Compilante) => {
        // La verifica OTP è andata a buon fine, recupero il compilante in sessione
        const compilante = this._authStore.getCompilante();
        // Aggiorno l'email del compilante con la nuova email
        compilante.des_email_compilante = email;
        // Lancio l'aggiornamento del compilante
        return this._auth.putCompilante(compilante);
        // #
      }),
      tap((compilante: Compilante) => {
        // Aggiorniamo le informazioni locali per il compilante
        this._authStore.setCompilante(compilante);
      })
    );
  }
}
