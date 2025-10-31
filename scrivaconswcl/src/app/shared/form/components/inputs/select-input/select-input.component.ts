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
import { SelectInput } from '../../../models/inputs/select-input';
import { BaseInputComponent } from '../base-input/base-input.component';

@Component({
  selector: 'app-select-input',
  template: `
    <select [formControl]="control" class="custom-input shape-rectangle size-large nb-transition input-full-width">
      <option
        *ngFor="let option of control.options | async"
        [value]="option.id"
        >{{ option.denominazione | translate }}</option
      >
    </select>
  `,
})
export class SelectInputComponent extends BaseInputComponent {
  @Input() control: SelectInput;
}

// <nb-select
//       fullWidth
//       size="large"
//       [status]="control.colorStatus"
//       [formControl]="control"
//       [placeholder]="control.placeholder | translate"
//     >
//       <nb-option
//         *ngFor="
//           let option of control.options
//             | async
//             | addClearOption: control.clearable
//         "
//         [value]="option.value"
//         >{{ option.label | translate }}</nb-option
//       >
//     </nb-select>
