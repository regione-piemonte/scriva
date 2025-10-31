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
import { IstanzaCompetenza } from 'src/app/shared/models';
import { CompetenzaTerritorioResponsabile } from 'src/app/shared/models/aut-competente/competenza-territorio-responsabile.model';
import { TipoResponsabile } from 'src/app/shared/models/aut-competente/tipo-responsabile.model';
import { IstanzaResponsabile } from 'src/app/shared/models/istanza/istanza-responsabile.model';
import { MessageService } from 'src/app/shared/services';

@Component({
  selector: 'app-advanced-action-responsabile',
  templateUrl: './advanced-action-responsabile.component.html',
  styleUrls: ['./advanced-action-responsabile.component.scss'],
})
export class AdvancedActionResponsabileComponent
  extends AutoUnsubscribe
  implements OnInit
{
  @Input() istanzaResponsabile: IstanzaResponsabile;
  @Input() idIstanza: number;
  @Input() tipiResponsabile: TipoResponsabile[] = [];
  @Input() istanzaCompetenzaList: IstanzaCompetenza[] = [];
  @Input() isModal: boolean = true;
  @Input() readonly: boolean = false;

  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<IstanzaResponsabile>();

  isCheckedFlgRiservato: boolean = false;
  label_responsabile: string;

  ctrList: CompetenzaTerritorioResponsabile[] = [];
  form: FormGroup;
  modalTitle: string;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private messageService: MessageService
  ) {
    super();
  }

  ngOnInit() {
    this.modalTitle = 'Modifica Responsabile';
    this.buildForm();
    this.setValueForm();
    this.label_responsabile = '';
  }

  /**
   * Setup del form e onchange dei select
   */
  buildForm() {
    this.form = this.fb.group({
      tipoResponsabile: [null, Validators.required],
      scegliResponsabile: null,
      labelResponsabile: null,
      nominativoResponsabile: [null, Validators.required],
      recapitoResponsabile: [null, Validators.required],
      flgRiservato: null,
    });
    this.form
      .get('tipoResponsabile')
      .valueChanges.subscribe((t: TipoResponsabile) => {
        if (t) {
          this.label_responsabile = t.des_tipo_responsabile;
          this.form.get('nominativoResponsabile').setValue(null);
          this.form.get('recapitoResponsabile').setValue(null);
          const ctr = this.getIstanzaCompetenzaByTipoResponsabile(t);
          if (ctr?.length > 0) {
            // getione caso con un solo CompetenzaTerritorioResponsabile
            if (ctr.length == 1) {
              this.ctrList = [];
              this.setValueFormByCompetenzaTerritorioResponsabile(ctr[0]);
            } else {
              this.ctrList = ctr;
            }
          } else {
            this.ctrList = [];
          }
        }
      });
    this.form
      .get('scegliResponsabile')
      .valueChanges.subscribe((ctr: CompetenzaTerritorioResponsabile) => {
        if (ctr) {
          this.setValueFormByCompetenzaTerritorioResponsabile(ctr);
          // SCRIVA-1423
          // this.ctrList = [];
        }
      });
  }

  /**
   * Recupero le CompetenzaTerritorioResponsabile in base ad un TipoResponsabile
   * filtrando opportunamente le istanzaCompetenzaList (IstanzaCompetenza) della istanza corrente
   * @param t TipoResponsabile
   * @returns CompetenzaTerritorioResponsabile[]
   */
  getIstanzaCompetenzaByTipoResponsabile(
    t: TipoResponsabile
  ): CompetenzaTerritorioResponsabile[] {
    let ctrList: CompetenzaTerritorioResponsabile[] = [];
    this.istanzaCompetenzaList.forEach((ic) => {
      if (ic.competenza_territorio?.responsabili_competenza_territorio) {
        ic.competenza_territorio?.responsabili_competenza_territorio.forEach(
          (ctr) => {
            if (
              ctr.tipo_responsabile.id_tipo_responsabile ==
              t.id_tipo_responsabile
            ) {
              ctrList.push(ctr);
            }
          }
        );
      }
    });
    return ctrList;
  }

  /**
   * Proietto nel form la selezione di una CompetenzaTerritorioResponsabile
   * @param ctr CompetenzaTerritorioResponsabile
   */
  setValueFormByCompetenzaTerritorioResponsabile(
    ctr: CompetenzaTerritorioResponsabile
  ) {
    this.form
      .get('nominativoResponsabile')
      .setValue(ctr?.nominativo_responsabile);
    this.form.get('recapitoResponsabile').setValue(ctr?.recapito_responsabile);
    if (ctr.label_responsabile) {
      this.label_responsabile = ctr.label_responsabile;
    }
    // SCRIVA-1423 cambio scegliResponsabile non deve modificare il visibile su portale
    // this.isCheckedFlgRiservato = ctr?.flg_riservato;
  }

  /**
   * Setto in fase di edit nel form i campi della istanzaResponsabile corrente
   */
  setValueForm() {
    this.form
      .get('nominativoResponsabile')
      .setValue(this.istanzaResponsabile?.nominativo_responsabile);
    this.form
      .get('recapitoResponsabile')
      .setValue(this.istanzaResponsabile?.recapito_responsabile);
    this.isCheckedFlgRiservato = this.istanzaResponsabile?.flg_riservato;
    this.form
      .get('tipoResponsabile')
      .setValue(this.istanzaResponsabile?.tipo_responsabile, {
        emitEvent: false,
      });
    // SCRIVA-1423
    if (this.istanzaResponsabile?.tipo_responsabile) {
      const ctr = this.getIstanzaCompetenzaByTipoResponsabile(
        this.istanzaResponsabile.tipo_responsabile
      );
      if (ctr?.length > 0) {
        this.ctrList = ctr;
      }
    }
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
   * Aggoiung un nuovo IstanzaResponsabile
   */
  onAdd() {
    const newIstanza: Partial<IstanzaResponsabile> = {
      id_istanza_responsabile: Date.now(),
      id_istanza: this.idIstanza,
      label_responsabile: this.label_responsabile,
    };
    this.istanzaResponsabile = {
      ...newIstanza,
      ...this._getFieldsForm(),
    };
    this.onEmit();
    this.onReset();
  }

  /**
   * Reset della componente corrente
   */
  onReset() {
    this.form.reset();
    this.isCheckedFlgRiservato = false;
    this.istanzaResponsabile = null;
  }

  /**
   * Click sul conferma in edit della modale
   */
  onConferma() {
    this.istanzaResponsabile = {
      ...this.istanzaResponsabile,
      ...this._getFieldsForm(),
    };
    this.onEmit();
  }

  /**
   * Prendo dal form dei campi e li metto in formato
   * adatto a istanzaResponsabile
   * @returns parziale di istanzaResponsabile
   */
  private _getFieldsForm(): any {
    return {
      nominativo_responsabile: this.form.get('nominativoResponsabile').value,
      recapito_responsabile: this.form.get('recapitoResponsabile').value,
      flg_riservato: this.isCheckedFlgRiservato,
      tipo_responsabile: this.form.get('tipoResponsabile').value,
    };
  }

  /**
   * Emetto il salvatataggio tramite il close della modale
   * o il next dell'emit
   */
  private onEmit() {
    if (this.isModal) {
      this.activeModal.close(this.istanzaResponsabile);
    } else {
      this.emit.next(this.istanzaResponsabile);
    }
  }

  /**
   * Comparazione TipoResponsabile
   * @param c1 TipoResponsabile
   * @param c2 TipoResponsabile
   * @returns boolean
   */
  compareTipoResponsabile(c1: TipoResponsabile, c2: TipoResponsabile): boolean {
    return c1 && c2 && c1.cod_tipo_responsabile === c2.cod_tipo_responsabile;
  }
}
