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
import { TextareaInput } from '../../../models';
import { BaseInputComponent } from '../base-input/base-input.component';

@Component({
  selector: 'app-texarea-input',
  template: `
    <textarea
      id="{{ 'input' + name }}"
      class="custom-input text-area shape-rectangle size-large nb-transition input-full-width"
      [appColorStatus]="control.colorStatus"
      [rows]="control.rows"
      [placeholder]="control.placeholder | translate"
      [formControl]="control"
    ></textarea>
  `,
})
export class TextareaInputComponent extends BaseInputComponent {
  @Input() name: string;
  @Input() control: TextareaInput;
}
