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
import { FormGroup } from '@angular/forms';
import { BaseInput } from '../../models';

@Component({
  selector: 'app-form-input',
  templateUrl: './form-input.component.html'
})
export class FormInputComponent {
  @Input() filter: boolean;
  @Input() form: FormGroup;
  @Input() field: BaseInput;
  @Input() name: string;
}
