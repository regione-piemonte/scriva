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
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AutoUnsubscribe } from 'src/app/core/components';

@Component({
  selector: 'app-info-protocollo-atto-modal',
  templateUrl: './info-protocollo-atto-modal.component.html',
  styleUrls: ['./info-protocollo-atto-modal.component.scss'],
})
export class InfoProtocolloAttoModalComponent
  extends AutoUnsubscribe
  implements OnInit
{
  @Input() type: 'P' | 'A';
  @Input() tempValues;

  maxLengths: {
    key: string;
    max: number;
  }[] = [
    { key: 'numProtocollo', max: 20 }, // num_protocollo_allegato
    { key: 'numAtto', max: 20 }, // num_atto
    { key: 'titolo', max: 1000 }, // titolo allegato
    { key: 'autore', max: 300 }, // autore allegato
  ];

  numProtocolloMaxLength: number;
  numAttoMaxLength: number;
  titoloMaxLength: number;
  autoreMaxLength: number;

  @Output() closeEvent = new EventEmitter();

  form: FormGroup;
  modalTitle: string;
  labelData: string;
  labelNumero: string;

  today = new Date();

  constructor(private fb: FormBuilder, public activeModal: NgbActiveModal) {
    super();
  }

  ngOnInit() {
    this.setMaxLenght();

    this.form = this.fb.group({
      data: this.tempValues?.data || null,
      numero: this.tempValues?.numero || null,
    });

    if (this.type === 'P') {
      this.modalTitle =
        this.tempValues?.data?.length > 0
          ? 'Modifica dati protocollazione'
          : 'Inserisci dati protocollazione';
      this.labelData = 'Data di protocollo';
      this.labelNumero = 'Numero di protocollo';
      this.form
        .get('numero')
        .setValidators([Validators.maxLength(this.numProtocolloMaxLength)]);
    } else {
      // 'A'
      this.modalTitle =
        this.tempValues?.data?.length > 0
          ? 'Modifica riferimenti atto'
          : 'Inserisci riferimenti atto';
      this.labelData = 'Data atto';
      this.labelNumero = 'Numero atto';

      this.form.addControl('titolo', new FormControl(this.tempValues?.titolo));
      this.form.addControl('autore', new FormControl(this.tempValues?.autore));

      this.form
        .get('numero')
        .setValidators([Validators.maxLength(this.numProtocolloMaxLength)]);
      this.form
        .get('titolo')
        .setValidators([Validators.maxLength(this.titoloMaxLength)]);
      this.form
        .get('autore')
        .setValidators([Validators.maxLength(this.autoreMaxLength)]);
    }
  }

  setMaxLenght() {
    this.numProtocolloMaxLength = this.maxLengths.find(
      (i) => i.key === 'numProtocollo'
    ).max;
    this.titoloMaxLength = this.maxLengths.find((i) => i.key === 'titolo').max;
    this.autoreMaxLength = this.maxLengths.find((i) => i.key === 'autore').max;
    this.numAttoMaxLength = this.maxLengths.find(
      (i) => i.key === 'numAtto'
    ).max;
  }

  onDismiss() {
    this.activeModal.dismiss();
  }

  onAnnulla() {
    this.form.reset();
  }

  onConferma() {
    if (this.form.valid) {
      const values = {
        data: this.form.get('data').value,
        numero: this.form.get('numero').value,
        titolo: this.form.get('titolo')?.value,
        autore: this.form.get('autore')?.value,
      };
      this.activeModal.close(values);
    }
  }
}
