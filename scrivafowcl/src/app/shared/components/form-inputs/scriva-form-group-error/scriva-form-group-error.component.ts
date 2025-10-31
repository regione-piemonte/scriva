/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { CommonConsts } from '../../../consts/common-consts.consts';
import { ScrivaFormInputCss } from '../form-input/utilities/form-input.classes';
import { IMappaErroriFormECodici } from '../form-input/utilities/form-input.interfaces';

@Component({
  selector: 'scriva-form-group-error',
  templateUrl: './scriva-form-group-error.component.html',
  styleUrls: ['./scriva-form-group-error.component.scss'],
})
export class ScrivaFormGroupErrorComponent implements OnInit {
  /** Oggetto contenente le costanti comuni all'applicazione. */
  C_C = new CommonConsts();

  /** Input che definisce le configurazioni per gli stili della input. */
  @Input() cssConfig: ScrivaFormInputCss;

  /** FormGroup a cui fa riferimento il componente. */
  @Input() formGroup: FormGroup;
  /** IMappaErroriFormECodici[] contenente la lista degli errori che devono essere gestiti per il form control. */
  @Input('errMapFormGroup') mappaErroriFG: IMappaErroriFormECodici[] = [];
  /** Boolean che tiene traccia dello stato di Submit del form padre. */
  @Input() formSubmitted: boolean = false;

  /** Boolean che definisce se abilitare la modalità di debug. */
  @Input() debug = false;

  constructor(private _formInputs: FormInputsService) {}

  ngOnInit() {
    // Verifico che esistano i dati input
    if (!this.formGroup) {
      throw new Error('formGroup not defined');
    }
  }

  /**
   * ################
   * GETTER DI COMODO
   * ################
   */

  /**
   * Getter di comodo per il recupero della casistica di errore sul form.
   * @returns boolean con il check di presenza di errori nel form group.
   */
  get hasFormErrors(): boolean {
    // Lancio la funzione del servizio
    return this._formInputs.getFormErrors(this.formGroup, this.mappaErroriFG);
  }

  /**
   * Getter di comodo per il recupero della casistica di errore sul form.
   */
  get formErrors() {
    // Tento di recuperare l'oggetto degli errori del form
    return this.formGroup?.errors;
  }
}
