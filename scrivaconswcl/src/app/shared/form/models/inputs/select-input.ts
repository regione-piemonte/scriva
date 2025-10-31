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
// TODO o si modifica lato shared/form per
// aver SelectOption con modello nuovo o si ripristina il vecchio modello
// dalla versione precedente
export interface SelectOption<T = string, E = string> {
  id?: T;
  denominazione?: E;
}

export interface SelectInputConstructor<T, E> extends BaseInputConstructor<T> {
  options: Observable<any[]>;
}

export class SelectInput<T = string, E = string> extends BaseInput<T> {
  inputType = 'select';
  options: Observable<any[]>;

  constructor(options: any) {
    super(options);
    this.options = options.options;
  }
}
