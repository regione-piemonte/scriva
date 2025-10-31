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
import { BaseInputComponent } from '../base-input/base-input.component';
import { NopInput } from '../../../models';

@Component({
  selector: 'app-nop-input',
  template: ` <div class="col-12 p-0"></div> `
})
export class NopInputComponent extends BaseInputComponent {
  @Input() name: string;
  @Input() control: NopInput;
}
