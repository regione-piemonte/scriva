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
import { AutocompleteInput } from '../../../models/inputs/autocomplete-input';
import { BaseInputComponent } from '../base-input/base-input.component';

@Component({
  selector: 'app-autocomplete-input',
  template: `
    <ng-select
      bindLabel="denominazione"
      bindValue="id"
      [minTermLength]="control.minTermLength"
      [typeToSearchText]="control.typeToSearchText | translate"
      [notFoundText]="control.notFoundText | translate"
      [typeahead]="control.typeahead"
      [clearable]="control.clearable"
      [multiple]="control.multiple"
      [searchable]="control.searchable"
      [items]="control.options | async"
      [loading]="control.loading"
      [formControl]="control"
      [placeholder]="control.placeholder | translate"
      [appColorStatus]="control.colorStatus"
      [compareWith]="control.compareWith"
    >
    </ng-select>
  `,
})
export class AutocompleteInputComponent extends BaseInputComponent {
  @Input() control: AutocompleteInput;
}

/* <ng-select
      bindLabel="label"
      [minTermLength]="control.minTermLength"
      [typeToSearchText]="control.typeToSearchText | translate"
      [notFoundText]="control.notFoundText | translate"
      [typeahead]="control.typeahead"
      [clearable]="control.clearable"
      [loading]="control.loading"
      [items]="control.options | async"
      [formControl]="control"
      [placeholder]="control.placeholder | translate"
      [appColorStatus]="control.colorStatus"
    >
    </ng-select> */
