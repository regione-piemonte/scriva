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
import { ScrivaFormInputRadio } from '../form-input/utilities/form-input.classes';
import { IScrivaRadioData } from '../form-input/utilities/form-input.interfaces';

@Component({
  selector: 'scriva-radio',
  templateUrl: './scriva-radio.component.html',
  styleUrls: ['./scriva-radio.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ScrivaRadioComponent),
      multi: true,
    },
  ],
})
export class ScrivaRadioComponent
  extends FormInputComponent<ScrivaFormInputRadio>
  implements OnInit, ControlValueAccessor
{
  /** Lista di oggetti IScrivaRadioData che contiene il pool di dati da visualizzare come voci. */
  @Input('dataSource') source: IScrivaRadioData[] = [];
  /** String che definisce il nome della proprietà da visualizzare come label del radio. */
  @Input() propertyToShow: string = 'label';

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
    // Aggiungo una classe di stile alla label
    this.cssConfig.customLabel = `${this.cssConfig.customLabel} form-check-label`;

    // Verifico che esistano i dati input
    if (!this.source) throw new Error('source not defined');
    if (!this.propertyToShow) throw new Error('propertyToShow not defined');
  }

  /**
   * Funzione collegata al change del radio.
   */
  onRadioChange() {
    // Emetto l'evento di change
    this.onChange(this.value);
  }
}
