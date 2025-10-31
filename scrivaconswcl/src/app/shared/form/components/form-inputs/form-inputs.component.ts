/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { KeyValue } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { BaseInput } from '../../models';

@Component({
  selector: 'app-form-inputs',
  templateUrl: './form-inputs.component.html'
})
export class FormInputsComponent {
  @Input() filter: boolean;
  @Input() form: FormGroup;
  @Input() controls: BaseInput[];

  keyvaluePipeCompareFn(
    a: KeyValue<string, BaseInput>,
    b: KeyValue<string, BaseInput>
  ): number {
    if (a.value.index > b.value.index) {
      return 1;
    }

    if (a.value.index < b.value.index) {
      return -1;
    }
    return 0;
  }
}
