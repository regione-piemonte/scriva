/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import {
  ChangeDetectorRef,
  Component,
  ComponentFactoryResolver,
  Input,
  ViewChild,
} from '@angular/core';
import { DatepickerHostDirective } from '../../../directives';
import { DateInput } from '../../../models/inputs/date-input';
import { BaseInputComponent } from '../base-input/base-input.component';

@Component({
  selector: 'app-date-input',
  template: `<input
    class="custom-input shape-rectangle size-large nb-transition input-full-width"
    type="date"
    [formControl]="control"
  />`,
})
export class DateInputComponent extends BaseInputComponent {
  @ViewChild(DatepickerHostDirective) datepickerHost: DatepickerHostDirective;
  @Input() control: DateInput;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private cdr: ChangeDetectorRef
  ) {
    super();
  }
}
