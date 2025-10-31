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
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AutoUnsubscribe } from 'src/app/core/components';
import { AllegatiService } from 'src/app/features/ambito/services';
import { IntegrazioneIstanza } from 'src/app/shared/models/istanza/integrazione-istanza.model';
import { MessageService } from 'src/app/shared/services';
import { AdvancedActionService } from '../../services/advanced-action.service';

@Component({
  selector: 'app-advanced-action-integrazione',
  templateUrl: './advanced-action-integrazione.component.html',
  styleUrls: ['./advanced-action-integrazione.component.scss'],
})
export class AdvancedActionIntegrazioneComponent
  extends AutoUnsubscribe
  implements OnInit
{
  @Input() integrazione: IntegrazioneIstanza;
  @Input() idIstanza: number;
  @Input() isModal: boolean = true;
  @Input() readonly: boolean = false;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<IntegrazioneIstanza>();

  form: FormGroup;
  modalTitle: string;
  today = new Date();
  numProtocolloMaxLength: number;

  constructor(
    public activeModal: NgbActiveModal,
    private advancedActionService: AdvancedActionService,
    private fb: FormBuilder,
    private messageService: MessageService
  ) {
    super();
  }

  ngOnInit() {
    this.modalTitle = 'Dati Protocollazione Integrazione';
    this.buildForm();
    this.setValueForm();
  }

  /**
   * Setup del form e onchange dei select
   */
  buildForm() {
    this.form = this.fb.group({
      dataProtocolloAllegato: [null, Validators.required],
      numProtocolloAllegato: [null, Validators.required],
    });

    this.numProtocolloMaxLength =
      AllegatiService.maxLengths.find((i) => i.key === 'numProtocollo').max ||
      20;
  }

  /**
   * Setto in fase di edit nel form i campi della istanzaResponsabile corrente
   */
  setValueForm() {
    this.form
      .get('dataProtocolloAllegato')
      .setValue(
        this.advancedActionService.convertDateBE4Form(
          this.integrazione.data_protocollo
        )
      );
    this.form
      .get('numProtocolloAllegato')
      .setValue(this.integrazione.num_protocollo);
  }

  /**
   * Quando si visualizza in modo modale chiudo la modale stessa
   */
  onDismiss() {
    this.activeModal.dismiss();
  }

  /**
   * Click su Annulla in modo modale chiudo la modale stessa, ma prima controllo se
   * estistono dei dati non salvati
   */
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

  /**
   * Reset della componente corrente
   */
  onReset() {
    this.form.reset();
    this.integrazione = null;
  }

  /**
   * Click sul conferma in edit della modale
   */
  onConferma() {
    this.integrazione = {
      ...this.integrazione,
      ...this._getFieldsForm(),
    };
    this.onEmit();
  }

  /**
   * Prendo dal form dei campi e li metto in formato
   * adatto a Allegato
   * @returns parziale di istanzaResponsabile
   */
  private _getFieldsForm(): any {
    return {
      data_protocollo: this.advancedActionService.convertDateForm4BE(
        this.form.get('dataProtocolloAllegato').value,
        'yyyy-MM-dd'
      ),
      num_protocollo: this.form.get('numProtocolloAllegato').value,
    };
  }

  /**
   * Emetto il salvatataggio tramite il close della modale
   * o il next dell'emit
   */
  private onEmit() {
    if (this.isModal) {
      this.activeModal.close(this.integrazione);
      this.onReset();
    } else {
      this.emit.next(this.integrazione);
      this.onReset();
    }
  }
}
