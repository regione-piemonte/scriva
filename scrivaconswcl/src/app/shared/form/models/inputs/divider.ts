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

export interface DividerConstructor<T, E>
  extends Omit<BaseInputConstructor<T>, 'label'> {
  marginTop?: number;
  marginBottom?: number;
}

export class Divider<T = string, E = string> extends BaseInput<T> {
  inputType = 'divider';

  marginTop: number;
  marginBottom: number;

  constructor(options: DividerConstructor<T, E> = {}) {
    super(options);

    this.marginTop = options.marginTop === undefined ? 2 : options.marginTop;
    this.marginBottom =
      options.marginBottom === undefined ? 2 : options.marginBottom;
  }
}
