/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { Form } from '../../models';
import { AutoUnsubscribe } from '../../../../core';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
})
export class FormComponent extends AutoUnsubscribe
  implements OnInit, OnDestroy {
  @Input() form: Form;
  @Output() save: EventEmitter<any> = new EventEmitter();
  // tslint:disable-next-line: no-output-native
  @Output() reset: EventEmitter<any> = new EventEmitter();

  @ViewChild('htmlInputSubmit') private submitElement: ElementRef<HTMLInputElement>;

  constructor() {
    super();
  }

  ngOnInit(): void {
    this.form.submit = () => {
      this.submitElement.nativeElement.click();
    };
  }

  showFooter(): boolean {
    return !!this.form.footer;
  }

  showClearButton(): boolean {
    return this.form.footer && this.form.footer.clear !== undefined;
  }

  isSubmitDisabled(): boolean {
    return this.form.footer && this.form.footer.submit.disabled;
  }

  resetForm(): void {
    this.form.reset();
    this.reset.emit();
  }

  ngOnDestroy(): void {
    if (!this.form?.skipDestroy) {
      this.form?.onDestroy();
    }
  }
}
