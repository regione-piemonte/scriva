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
import { BaseInput } from '../../models';
import { ModalService } from '@app/shared/modal/modal.service';
import { HelpModalComponent } from '@app/core/components/help-modal/help-modal.component';
import { I18nService } from '@app/core/translate/public-api';

@Component({
  selector: 'app-input-label',
  template: `
    <label
      for="{{ 'input' + name }}"
      class="label custom-label font-size-16 mb-0"
      [class.font-weight-bold]="field.required"
      [class.font-weight-normal]="!field.required"
      style="text-align: left; position: relative"
      *ngIf="
        field.label &&
        field.inputType !== 'checkbox' &&
        field.inputType !== 'fieldset'
      "
      >{{ field.label | translate }}
      <span *ngIf="field.required && !filter" class="font-weight-light">
        {{ 'UTILS.FORM.MANDATORY' | translate }}
      </span>

    <button *ngIf="field.help" type="button" class="btn pb-1 px-1 pt-0" (click)="onHelp()">
      <app-icon
        [size]="'extra-small'"
        [name]="'eng-scriva-help'"
        [type]="'eng'"
      ></app-icon>
    </button>
    </label>

  `
})
export class InputLabelComponent {
  @Input() name: string;
  @Input() field: BaseInput;
  @Input() filter: boolean;

  constructor(private modalService: ModalService, private i18n: I18nService) {}

  onHelp(): void {
    this.modalService.openDialog(HelpModalComponent, {
      header: this.i18n.translate('PRACTICE.DETAIL.HELP.HEADER'),
      showCloseButton: true,
      context: {
        helpOBJ: this.field.help,
        placeholders: this.field.help.placeholders
      }
    });
  }

  isToBeSeen() {
    return (
      this.field.help.des_testo_help.includes('{') &&
      this.field.help.des_testo_help.includes('}') &&
      !this.field.help.placeholders
    );
  }
}
