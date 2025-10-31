/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Observable } from 'rxjs';
import { BaseInput, BaseInputConstructor } from './base-input';
import { SelectOption } from './select-input';

export interface RadioOption<T = string, E = string>
  extends SelectOption<T, E> { }

export interface CustomRadioOption<T = string, E = string> {
  label: T;
  value: E;
}
export interface RadiosInputConstructor<T, E>
  extends Omit<BaseInputConstructor<T>, 'placeholder' | 'pattern'> {
  options: Observable<CustomRadioOption<T, E>[]>;
  inline?: boolean;
}

export class RadiosInput<T = string, E = string> extends BaseInput<T> {
  inputType = 'radios';
  options: Observable<CustomRadioOption<T, E>[]>;
  inline?: boolean;

  constructor(options: RadiosInputConstructor<T, E>) {
    super(options);
    this.options = options.options;

    if (options.inline) {
      this.inline = options.inline;
    }
  }
}
