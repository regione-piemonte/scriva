/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AutoUnsubscribe } from 'src/app/core/components';
import { IstanzaSitoNatura } from 'src/app/shared/models/istanza/istanza-sitinatura.model';
import { MessageService } from 'src/app/shared/services';

@Component({
  selector: 'app-siti-natura-form',
  templateUrl: './siti-natura-form.component.html',
  styleUrls: ['./siti-natura-form.component.scss'],
})
export class SitiNaturaFormComponent extends AutoUnsubscribe implements OnInit {
  @Input() sitoNatura;
  @Input() dettaglioOggetto;
  @Input() idIstanza: number;
  /** Input boolean con la configurazione per gestire la modale in sola lettura. Per default è: false. */
  @Input() readonly: boolean = false;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<IstanzaSitoNatura>();

  form: FormGroup;
  modalTitle: string =
    'Modifica Siti Rete Natura 2000 esterni al Progetto/Intervento/Attività ma potenzialmente interferiti';

  constructor(
    private fb: FormBuilder,
    public activeModal: NgbActiveModal,
    private messageService: MessageService
  ) {
    super();
  }

  ngOnInit() {
    this.buildForm();
    this.setValueForm();
  }

  buildForm() {
    this.form = this.fb.group({
      flg_elemento_discontinuita: [null, Validators.required],
      des_elemento_discontinuita: [null, this.conditionalRequired],
      sitoReteNaturaInterferito: null,
      num_distanza: [null],
    });
    if (
      this.dettaglioOggetto.siti_rete_natura?.elementi_discontinuita?.distanza
        ?.obbligatorio === 'true'
    ) {
      this.form
        .get('num_distanza')
        .setValidators([Validators.required, Validators.max(99999)]);
    } else {
      this.form.get('num_distanza').setValidators([Validators.max(99999)]);
    }

    // Verifico se la modale è in sola lettura
    if (this.readonly) {
      // Imposto il form in sola lettura
      this.form.disable({ emitEvent: false });
    }
  }

  // Custom validator function for des_elemento_discontinuita
  conditionalRequired(control: AbstractControl): { [key: string]: any } | null {
    const flg_elemento_discontinuita = control.root.get(
      'flg_elemento_discontinuita'
    );
    if (
      flg_elemento_discontinuita &&
      flg_elemento_discontinuita.value === 'yes'
    ) {
      return Validators.required(control);
    }
    return null;
  }

  setValueForm() {
    this.form
      .get('des_elemento_discontinuita')
      .setValue(this.sitoNatura.des_elemento_discontinuita);
    if (this.sitoNatura.des_elemento_discontinuita) {
      this.form.get('flg_elemento_discontinuita').setValue('si');
    } else {
      this.form.get('flg_elemento_discontinuita').setValue('no');
    }
    this.form.get('num_distanza').setValue(this.sitoNatura.num_distanza);
  }

  onDismiss() {
    this.activeModal.dismiss();
  }

  onAnnulla() {
    if (this.form.dirty) {
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
    const data = {
      ...this.sitoNatura,
      ...this.form.value,
    };

    this.activeModal.close(data);
  }
}
