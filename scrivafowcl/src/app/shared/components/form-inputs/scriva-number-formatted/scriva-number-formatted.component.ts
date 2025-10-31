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
  ElementRef,
  forwardRef,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { FormInputsService } from '../../../services/form-inputs/form-inputs.service';
import { ScrivaUtilitiesService } from '../../../services/scriva-utilities/scriva-utilities.service';
import { FormInputComponent } from '../form-input/form-input.component';
import { ScrivaFormInputNumber } from '../form-input/utilities/form-input.classes';

@Component({
  selector: 'scriva-number-formatted',
  templateUrl: './scriva-number-formatted.component.html',
  styleUrls: ['./scriva-number-formatted.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaNumberFormattedComponent),
      multi: true,
    },
  ],
})
export class ScrivaNumberFormattedComponent
  extends FormInputComponent<ScrivaFormInputNumber>
  implements OnInit, ControlValueAccessor
{
  /** Number che definisce il numero di decimali da visualizzare. Per default è 2. */
  @Input() decimals: number = 2;
  /** Funzione richiamata all'evento di blur della input. */
  @Input() onBlur: (value: string) => {};

  /** ViewChild che definisce il collegamento tra l'input del DOM e il componente. */
  @ViewChild('scrivaNumber') scrivaNumber: ElementRef;

  /** ScrivaFormInputNumber che definisce le configurazioni per i dati della input. */
  dataConfig: ScrivaFormInputNumber;

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
  onInputBlur() {}

  /**
   * #########################################
   * INTERFACCE PER GESTIONE DEL FORM CONTROL
   * #########################################
   */

  /**
   * Funzione implementata dall'interfaccia ControlValueAccessor per il cambio del valore dal Form padre.
   * NOTA: sovrascrivo l'interfaccia di default per poter manipolare il valore impostato nella input.
   * @param value number con il nuovo valore.
   */
  writeValue(value: number) {
    // Formatto il valore numerico in una stringa formattata
    const v = this._scrivaUtilities.formatImportoITA(value);
    // Assegno locamente il valore
    this._value = v;
  }
}
