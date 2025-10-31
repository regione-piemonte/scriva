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
import { Divider } from '../../../models';

@Component({
  selector: 'app-divider-input',
  template: `
  <!-- <app-divider
    [marginBottom]="control.marginBottom"
    [marginTop]="control.marginTop"
  ></app-divider> -->
  `
})
export class DividerInputComponent extends BaseInputComponent {
  @Input() control: Divider;
}
