/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CheckboxInput } from '../../../models/inputs/checkbox-input';
import { BaseInputComponent } from '../base-input/base-input.component';

@Component({
  selector: 'app-checkbox-input',
  template: `
    <div class="form-check">
      <input
        *ngIf="control; else noControl"
        id="{{ 'input' + name }}"
        type="checkbox"
        [formControl]="control"
      />
      <ng-template #noControl>
        <input
          id="{{ 'input' + name }}"
          type="checkbox"
          [checked]="checked"
          [indeterminate]="indeterminate"
          (change)="change.emit($event)"
        />
      </ng-template>
      <label class="font-weight-bold" for="{{ 'input' + name }}">{{
        control?.label || label | translate
      }}</label>
    </div>
  `,
})
export class CheckboxInputComponent extends BaseInputComponent {
  @Input() name: string;
  @Input() control?: CheckboxInput;
  @Input() checked: boolean;
  @Input() indeterminate: boolean;
  @Input() label = '';

  // tslint:disable-next-line: no-output-native
  @Output() change: EventEmitter<any> = new EventEmitter();
}
