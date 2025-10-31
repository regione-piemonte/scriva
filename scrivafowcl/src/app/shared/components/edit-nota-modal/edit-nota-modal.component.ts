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
import {
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AutoUnsubscribe } from 'src/app/core/components';
import { NotaIstanza } from 'src/app/shared/models';
import { MessageService } from 'src/app/shared/services';

@Component({
  selector: 'app-edit-nota-modal',
  templateUrl: './edit-nota-modal.component.html',
  styleUrls: ['./edit-nota-modal.component.scss'],
})
export class EditNotaModalComponent extends AutoUnsubscribe implements OnInit {
  @Input() notaInEdit: NotaIstanza;
  @Input() disableBtn: boolean;
  @Input() maxNote: number;

  editForm: FormGroup;
  modalTitle: string;

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    public activeModal: NgbActiveModal
  ) {
    super();
  }

  ngOnInit() {
    this.editForm = this.fb.group({
      nota: null
    });
    if (this.disableBtn) {
        this.modalTitle = 'Visualizza nota';
    } else {
        this.modalTitle = 'Modifica nota';
    }

    this.setFormValues();
    this.onFormValueChanges();
  }

  setFormValues() {
    this.editForm.get('nota').setValue(this.notaInEdit.des_nota);
  }

  onFormValueChanges() {
    this.editForm.get('nota').setValidators([
        Validators.required,
        Validators.maxLength(this.maxNote),
    ]);
    this.editForm.get('nota').updateValueAndValidity();
  }

  onDismiss() {
    this.activeModal.dismiss();
  }

  onAnnulla() {
    if (this.editForm.dirty) {
      this.messageService.showConfirmation({
        title: 'Conferma annullamento modifiche',
        codMess: 'A001',
        buttons: [
          {
            label: 'ANNULLA',
            type: 'btn-link',
            callback: () => {},
          },
          {
            label: 'CONFERMA',
            type: 'btn-primary',
            callback: () => {
              this.onDismiss();
            },
          },
        ],
      });
    } else {
      this.onDismiss();
    }
  }

  onConferma() {
    // console.log('AGGOIORNATA?? ', this.notaInEdit.des_nota); // Jessica ricordati di togliere questi log :D o usa il servizio _logger così almeno in ambienti di TST PRDO non si vedono :D

    /*     if (!this.editForm.valid) {
      if (
        !this.editForm.get('categoria').value ||
        !this.editForm.get('tipologia').value
      ) {
        this.messageService.showMessage(
          'E018',
          'containerUploadDocumenti',
          false
        );
      } else {
        this.messageService.showMessage(
          'E017',
          'containerUploadDocumenti',
          false
        );
      }
      return;
    }
 */

    this.notaInEdit.des_nota = this.editForm.get('nota')?.value;

    this.activeModal.close(this.notaInEdit);
  }
}