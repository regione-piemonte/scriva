/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { ProfiloAnagraficaService } from '../../services/profilo-anagrafica/profilo-anagrafica.service';
import { PROFILO_CONSTS } from '../profilo/utilities/profilo.consts';
import { PROFILO_ANAGRAFICA_CONSTS } from './utilities/profilo-anagrafica.consts';

@Component({
  selector: 'scriva-profilo-anagrafica',
  templateUrl: './profilo-anagrafica.component.html',
  styleUrls: ['./profilo-anagrafica.component.scss'],
})
export class ProfiloAnagraficaComponent implements OnInit {
  /** Costante contenente le informazioni costanti del componente. */
  P_C = PROFILO_CONSTS;
  PA_C = PROFILO_ANAGRAFICA_CONSTS;

  /** FormGroup contenente le informazioni del profilo utente. */
  paForm: FormGroup;

  /**
   * Costruttore.
   */
  constructor(
    private _formBuilder: FormBuilder,
    private _profiloAnag: ProfiloAnagraficaService
  ) {
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
  private setupComponente() {}

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
    const fields = this._profiloAnag.generateControlsConfig();
    const validators = { validators: [] };

    // Istanzio il form con le informazioni
    this.paForm = this._formBuilder.group(fields, validators);

    // Inizializzo le informazioni del form
    this._profiloAnag.setFormInfo(this.paForm);
    // Definisco l'accessibilità del form
    this._profiloAnag.setAccessibilitaForm(this.paForm);
  }

  /**
   * ###############
   * GETTER & SETTER
   * ###############
   */

  /**
   * Getter per il form control riferito a: codiceFiscale.
   * @returns AbstractControl come FormControl del campo.
   */
  get codiceFiscaleFC(): AbstractControl {
    // Rirotno il form control del form group
    return this.paForm?.get(this.P_C.FC_CODICE_FISCALE);
  }

  /**
   * Getter per il form control riferito a: cognome.
   * @returns AbstractControl come FormControl del campo.
   */
  get cognomeFC(): AbstractControl {
    // Rirotno il form control del form group
    return this.paForm?.get(this.P_C.FC_COGNOME);
  }

  /**
   * Getter per il form control riferito a: nome.
   * @returns AbstractControl come FormControl del campo.
   */
  get nomeFC(): AbstractControl {
    // Rirotno il form control del form group
    return this.paForm?.get(this.P_C.FC_NOME);
  }

  /**
   * Getter di comodo per verificare che il form sia stato inizializzato correttamente.
   * @returns boolean con il risultato del controllo.
   */
  get checkForm(): boolean {
    // Verifico l'esistenza del form del componente
    return this.paForm != undefined;
  }
}
