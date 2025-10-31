/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input } from '@angular/core';
import { TextInput } from '../../../models/inputs/text-input';
import { BaseInputComponent } from '../base-input/base-input.component';

@Component({
  selector: 'app-textbox-input',
  template: `
    <input
      id="{{ 'input' + name }}"
      class="custom-input shape-rectangle size-large nb-transition input-full-width"
      [appColorStatus]="control.colorStatus"
      [type]="control.clearable ? 'search' : control.type"
      [placeholder]="control.placeholder | translate"
      [formControl]="control"
    />
  `,
})
export class TextboxInputComponent extends BaseInputComponent {
  @Input() name: string;
  @Input() control: TextInput;
}
