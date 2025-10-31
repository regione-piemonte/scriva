/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AutoUnsubscribe } from 'src/app/core/components';
import { Referente } from 'src/app/features/ambito/models';
import { MessageService } from 'src/app/shared/services';
import { RegexUtil } from 'src/app/shared/utils';

@Component({
  selector: 'app-referente-form',
  templateUrl: './referente-form.component.html',
  styleUrls: ['./referente-form.component.scss'],
})
export class ReferenteFormComponent extends AutoUnsubscribe implements OnInit {
  @Output() confermaInserisciReferente$ = new EventEmitter();
  @Output() annullaInserisciReferente$ = new EventEmitter();

  @Input() numReferenti: string;
  @Input() data;
  @Input() formReferenteRow: { record: Referente; index: number } = null;

  @Input() onlyShow: boolean;

  formInserisciReferente: FormGroup;

  constructor(
    private ms: MessageService,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {
    super();
  }

  ngOnInit(): void {
    this._buildFormInserisciReferente(this.formReferenteRow?.record);
  }

  /*
   * Builder form inserisci referente
   */
  private _buildFormInserisciReferente(referente: Referente = null) {
    this.formInserisciReferente = this.fb.group({
      id: referente?.id_referente_istanza || null,
      gestUID: referente?.gestUID || null,
      cognome: [
        referente?.cognome_referente || '',
        {
          validators: [
            Validators.required,
            Validators.maxLength(50),
            Validators.pattern(RegexUtil.Name_Surname),
          ],
        },
      ],
      nome: [
        referente?.nome_referente || '',
        {
          validators: [
            Validators.required,
            Validators.maxLength(50),
            Validators.pattern(RegexUtil.Name_Surname),
          ],
        },
      ],
      telefono: [
        referente?.num_tel_referente || '',
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
            //Validators.minLength(10),
          ],
        },
      ],
      cellulare: [
        referente?.num_cellulare_referente || '',
        {
          validators: [
            Validators.pattern(RegexUtil.Phone),
            Validators.maxLength(25),
            //Validators.minLength(10),
          ],
        },
      ],
      email: [
        referente?.des_email_referente || '',
        {
          validators: [
            Validators.required,
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      pec: [
        referente?.des_pec_referente || '',
        {
          validators: [
            Validators.pattern(RegexUtil.Email),
            Validators.maxLength(100),
          ],
        },
      ],
      mansione: [
        referente?.des_mansione_referente || '',
        {
          validators: [Validators.maxLength(50)],
        },
      ],
    });

    //Verfico se il componente è in sola lettura
    if (this.onlyShow) {
      // Disabilito il form
      this.formInserisciReferente.disable({ emitEvent: false });
    }
  }

  checkTypeErrorForm() {
    if (
      this.formInserisciReferente.controls.nome?.status === 'VALID' ||
      this.formInserisciReferente.controls.cognome?.status === 'VALID' ||
      this.formInserisciReferente.controls.telefono?.status === 'VALID' ||
      this.formInserisciReferente.controls.email?.status === 'VALID'
    ) {
      return true;
    } else {
      return false;
    }
  }

  inserisciReferente() {
    this.formInserisciReferente.markAllAsTouched();
    const formatError = this.checkTypeErrorForm();

    if (this.formInserisciReferente.valid) {
      this.confermaInserisciReferente$.emit({formRawValue: this.formInserisciReferente.getRawValue()});
    } else if (formatError) {
      this.ms.showMessage('E004', 'formInserisciReferente', true);
    } else {
      this.ms.showMessage('E001', 'formInserisciReferente', true);
    }
  }

  // general button click event
  buttonClicked(i, label = '') {
    if (label === 'ANNULLA') {
      this.activeModal.close({
        buttonIndex: i,
      });
      return;
    }
    if (i < 0) {
      this.activeModal.close({
        buttonIndex: i,
      });
    } else {
      this.formInserisciReferente.markAllAsTouched();
      if (
        this.formInserisciReferente.valid ||
        this.formInserisciReferente.status == 'DISABLED'
      ) {
        this.activeModal.close({
          buttonIndex: i,
          formRawValue: this.formInserisciReferente.getRawValue(),
        });
      } else {
        //this.ms.showMessage('E085', 'formInserisciReferente', true);
        // Verifico qual è l'errore sul form
        const isRequired =
          this.formHasError(this.formInserisciReferente, 'required') > 0;
        const isErrorPattern =
          this.formHasError(this.formInserisciReferente, 'pattern') > 0;
        // Verifico se "required" perchè quest'errore ha la priorità a livello ordine di controlli
        if (isRequired) {
          this.ms.showMessage('E001', 'formInserisciReferente', true);
        } else if (isErrorPattern) {
          // L'errore per il pattern è il secondo per importanza
          this.ms.showMessage('E004', 'formInserisciReferente', true);
        } else {
          // Per tutte le altre casistiche visualizziamo l'errore di pattern perchè nessuno ha mai gestito tutte le altre possibili casistiche.
          // Per esclusione l'errore non può essere "required". Se capitasse, la gestione del "required" probabilmente è definita con un custom error e non con il required di Angular!
          this.ms.showMessage('E004', 'formInserisciReferente', true);
        }
      }
    }
  }

  annullaInserisciReferente() {
    // ask user then delete
    this.ms.showConfirmation({
      title: 'Attenzione',
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
            this.annullaInserisciReferente$.emit();
          },
        },
      ],
    });
  }

  // Utility per cercare errori all'interno dei controlli della form
  formHasError(form: FormGroup | AbstractControl, errorName: string) {
    let result = 0;
    if (!form.valid) {
      if (
        form?.errors &&
        Object.keys(form?.errors).find((item) => item === errorName)
      ) {
        result++;
      } else {
        if (form instanceof FormGroup) {
          Object.keys(form?.controls).forEach((key) => {
            result += this.formHasError(form?.controls[key], errorName);
          });
        }
      }
    }
    return result;
  }
}
