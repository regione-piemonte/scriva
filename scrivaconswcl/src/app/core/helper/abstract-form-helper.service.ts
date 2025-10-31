/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Injectable } from '@angular/core';
import { BaseInput, Form } from '../../shared/form';
import { Help } from '../interfaces/help';

@Injectable({
  providedIn: 'root'
})
export abstract class AbstractFormHelperService {
  protected _items: Map<string, () => Form> = new Map<string, () => Form>();
  _help: Help;

  constructor() {
  }

  protected abstract _initForms();

  get(
    name: string
  ): Form {
    return this._items.get(name)();
  }

  set<T>(
    form: Form,
    data: Partial<T>
  ): void {
    for (const k in data) {
      if (data.hasOwnProperty(k)) {
        const input: BaseInput = form.get(k);
        if (input) {
          input.setValue(data[k].toString());
        }
      }
    }
  }

}
