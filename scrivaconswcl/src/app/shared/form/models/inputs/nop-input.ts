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

export interface NopConstructor<T, E>
  extends Omit<BaseInputConstructor<T>, 'label'> {}

export class NopInput<T = string, E = string> extends BaseInput<T> {
  inputType = 'nop';

  constructor(options: NopConstructor<T, E>) {
    super(options);
  }
}
