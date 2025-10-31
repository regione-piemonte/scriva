/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { BaseInput, BaseInputConstructor } from './base-input';
import { Validators } from '@angular/forms';

export interface CheckboxInputConstructor
  extends Omit<
    BaseInputConstructor<boolean>,
    'placeholder' | 'clearable' | 'pattern'
  > {}

export class CheckboxInput extends BaseInput<boolean> {
  inputType = 'checkbox';
  constructor(options: CheckboxInputConstructor) {
    super(options);

    if (options.required) {
      this.setValidators(Validators.requiredTrue);
    }
  }
}
