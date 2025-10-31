/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Help } from '@app/core/interfaces/help';
import {
  BaseInput,
  BaseInputConstructor,
  ValidatorOrOptions
} from './base-input';

export type TextInputType =
  | 'text'
  | 'url'
  | 'email'
  | 'tel'
  | 'password'
  | 'number'
  | 'search'
  | 'date';

export interface TextInputConstructor extends BaseInputConstructor {
  type: TextInputType;
  help?: Help;
  validatorOrOpts?: ValidatorOrOptions;
}

export class TextInput extends BaseInput {
  inputType = 'textbox';
  type: TextInputType;
  help?: Help;

  constructor(options: TextInputConstructor) {
    super(options);
    this.type = options.type;
    this.help = options.help;
  }
}
