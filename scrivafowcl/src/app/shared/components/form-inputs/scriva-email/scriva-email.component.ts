/*
 * ========================LICENSE_START=================================
 *
 * Copyright (C) 2025 Regione Piemonte
 *
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, forwardRef, Input, OnInit } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { FormInputComponent } from '../form-input/form-input.component';
import {
  ScrivaFormInputEmail,
  ScrivaFormInputText,
} from '../form-input/utilities/form-input.classes';

@Component({
  selector: 'scriva-email',
  templateUrl: './scriva-email.component.html',
  styleUrls: ['./scriva-email.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaEmailComponent),
      multi: true,
    },
  ],
})
export class ScrivaEmailComponent
  extends FormInputComponent<ScrivaFormInputEmail>
  implements OnInit, ControlValueAccessor
{
  /** Funzione richiamata all'evento di blur della input. */
  @Input() onBlur: (value: string) => {};

  /** ScrivaFormInputText che definisce le configurazioni per i dati della input. */
  dataConfig: ScrivaFormInputText;

  /**
   * Costruttore.
   */
  constructor(
    formInputs: FormInputsService,
    scrivaUtilities: ScrivaUtilitiesService
  ) {
    // Richiamo il super
    super(formInputs, scrivaUtilities);
  }

  ngOnInit() {
    // Verifico che esistano i dati input richiamando il super
    super.ngOnInit();
  }

  /**
   * Funzione agganciata all'evento blur della input.
   */
  onInputBlur() {
    // Verifico se è stata definita una funzione per il blur
    if (this.onBlur !== undefined) {
      // Eseguo la funzione di blur passando il value
      this.onBlur(this.value);
    } else {
      // Lancio il change del valore
      this.onChange(this.value);
    }

    // Applico la trasformazione del testo
    this.transformText();
  }
}
