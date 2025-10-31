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

export interface TextareaConstructor extends BaseInputConstructor {
  rows?: number;
}

export class TextareaInput extends BaseInput {
  inputType = 'textarea';
  rows?: number;

  constructor(options: TextareaConstructor) {
    super(options);
    this.rows = options.rows || 5;
  }
}
