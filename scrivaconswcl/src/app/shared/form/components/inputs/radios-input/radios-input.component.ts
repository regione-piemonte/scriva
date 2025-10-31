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
import { RadiosInput } from '../../../models/inputs/radios-input';
import { BaseInputComponent } from '../base-input/base-input.component';

@Component({
  selector: 'app-radios-input',
  template: ` <div class="form-check">
  <div [ngClass]="{'custom-control-inline':control?.inline}"  *ngFor="let option of control.options | async; index as i">
    <input
      *ngIf="control; else noControl"
      type="radio" id="{{ 'input_' + control?.label + '_' + (i+1) }}" name="{{control?.label || label}}"
      [formControl]="control" class="custom-control-input"
      [value]="option.value">
    <ng-template #noControl>
      <input
        id="{{ 'input_' + control?.label + '_' + (i+1) }}"
        type="radio"
        disabled
      />
    </ng-template>
    <label class="font-weight-bold" for="{{ 'input_' + control?.label + '_' + (i+1) }}">{{
      option.label }}</label>
  </div>
</div>`,
})
export class RadiosInputComponent extends BaseInputComponent {
  @Input() name: string;
  @Input() label = '';
  @Input() control: RadiosInput;

}
