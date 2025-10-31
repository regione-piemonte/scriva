/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, Input, OnInit } from '@angular/core';
import { BaseInputComponent } from '../base-input/base-input.component';
import { AutocompleteWithInsertInput } from '@app/shared/form/models/inputs/autocomplete-with-insert-input';
import { take } from 'rxjs/operators';
import { SelectOption } from '@app/shared/form';

@Component({
  selector: 'app-autocomplete-with-insert-input',
  template: `
    <ng-select
      bindLabel='denominazione'
      bindValue='id'
      [minTermLength]='control.minTermLength'
      [typeToSearchText]='control.typeToSearchText | translate'
      [notFoundText]='control.notFoundText | translate'
      [typeahead]='control.typeahead'
      [clearable]='control.clearable'
      [multiple]='control.multiple'
      [searchable]='control.searchable'
      [items]='items'
      [loading]='control.loading'
      [formControl]='control'
      [placeholder]='control.placeholder | translate'
      [appColorStatus]='control.colorStatus'
      (search)='onSearch($event)'
      (close)='onClose()'
    >
    </ng-select>
  `
})
export class AutocompleteWithInsertInputComponent extends BaseInputComponent implements OnInit {

  @Input() control: AutocompleteWithInsertInput;

  items: SelectOption<any, any>[];

  searchedValue = '';

  ngOnInit(): void {
    this.control.options.pipe(take(1)).subscribe(
      (options: SelectOption[]) => {
        this.items = options;
      }
    );
  }

  onSearch(searched: { term: string, items: Array<any> }) {
    if (searched.items.length === 0) {
      this.searchedValue = searched.term;
      return;
    }

    this.searchedValue = '';
  }

  onClose() {
    if (this.searchedValue !== '') {
      const searched = { denominazione: this.searchedValue, id: this.searchedValue };
      this.items = [...this.items, searched];
      this.control.setValue(this.searchedValue);

      this.searchedValue = '';
    }
  }

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
