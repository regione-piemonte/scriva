/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { DatePipe, DecimalPipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';
import { distinctUntilChanged, filter, takeUntil } from 'rxjs/operators';
import { AutoUnsubscribe } from 'src/app/core/components';
import {
  Allegato,
  CategoriaAllegato,
  TipoAllegato,
} from 'src/app/features/ambito/models';
import { TipoAdempimentoOggApp } from 'src/app/shared/models';
import { MessageService } from 'src/app/shared/services';
import { CommonConsts } from '../../consts/common-consts.consts';
import { CardRiepilogoAllegatiConsts } from '../card-riepilogo-allegati/utilities/card-riepilogo-allegati.consts';
import { LoggerService } from '../../services/logger.service';

@Component({
  selector: 'app-edit-allegato-modal',
  templateUrl: './edit-allegato-modal.component.html',
  styleUrls: ['./edit-allegato-modal.component.scss'],
})
export class EditAllegatoModalComponent
  extends AutoUnsubscribe
  implements OnInit
{
  /** CommonConsts con le costanti comuni all'applicazione. */
  C_C = new CommonConsts();
  /** CardRiepilogoAllegatiConsts con le costanti condivise da un altro componente. */
  CRA_C = new CardRiepilogoAllegatiConsts();

  @Input() allegatoInEdit: Allegato;
  @Input() categorie: CategoriaAllegato[];
  @Input() fullListTipiAllegato: TipoAllegato[];
  @Input() maxNote: number;
  @Input() isFrontOffice: boolean;
  @Input() oggAppBtnProtocolla: TipoAdempimentoOggApp;
  /** Input boolean che definisce se il componente deve essere forzato in modalità visualizzazione. Per default è: false. */
  @Input() visualizzazione: boolean = false;

  @Input() maxLengths: {
    key: string;
    max: number;
  }[];

  numProtocolloMaxLength: number;
  numAttoMaxLength: number;
  titoloMaxLength: number;
  autoreMaxLength: number;

  editForm: FormGroup;
  tipiAllegato: TipoAllegato[] = [];
  showInfoAtto = false;
  today = new Date();

  /** boolean che definisce la condizione di sola lettura data dalla condizione di esistenza dati correlata alla categoria.  */
  private _readonlyCategoria: boolean = false;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private messageService: MessageService,
    public activeModal: NgbActiveModal,
    private _decimalPipe: DecimalPipe,
    private _logger: LoggerService
  ) {
    super();
  }

  ngOnInit() {
    this.setMaxLenght();

    this.editForm = this.fb.group({
      categoria: [null, { validators: [Validators.required] }],
      tipologia: [null, { validators: [Validators.required] }],
      riservato: [false, { validators: [Validators.required] }],
      nota: null,
      numProtocollo: [
        null,
        {
          validators: [Validators.maxLength(this.numProtocolloMaxLength)],
        },
      ],
      dataProtocollo: [null, [this.maxDateValidator]],
    });

    // if (this.oggAppBtnProtocolla) {
    //   this.editForm.addControl('numProtocollo', new FormControl(null));
    //   this.editForm.addControl('dataProtocollo', new FormControl(null));
    // }

    this.setFormValues();
    this.onFormValueChanges();
  }

  maxDateValidator(control: AbstractControl): ValidationErrors | null {
    // Verifico che la data sia definita
    if (!control.value) {
      // Non c'è da verificare la data
      return null;
    }
    
    const selectedDate = new Date(control.value);
    const maxDate = new Date();
    return selectedDate <= maxDate ? null : { maxDateExceededToday: true };
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

  setFormValues() {
    let categoria = this.categorie.find(
      (cat) =>
        cat.id_categoria_allegato ===
        this.allegatoInEdit.tipologia_allegato.categoria_allegato
          .id_categoria_allegato
    );
    let tipologia;
    if (categoria) {
      tipologia = this.fullListTipiAllegato.find(
        (tipo) =>
          tipo.tipologia_allegato.id_tipologia_allegato ===
          this.allegatoInEdit.tipologia_allegato.id_tipologia_allegato
      );
      this.tipiAllegato = this.fullListTipiAllegato.filter(
        (tipo) =>
          tipo.tipologia_allegato.categoria_allegato.id_categoria_allegato ===
          categoria?.id_categoria_allegato
      );
      // Gestisco la casistica di sola lettura data dall'esistenza della categoria
      this._readonlyCategoria = false;
      // #
    } else {
      this.categorie = [
        ...this.categorie,
        this.allegatoInEdit.tipologia_allegato.categoria_allegato,
      ];

      const tipo = {
        adempimento: null,
        tipologia_allegato: this.allegatoInEdit.tipologia_allegato,
        flg_firma_digitale: false,
        flg_firma_non_valida_bloccante: false,
        flg_integrazione: true,
        flg_nota: false,
        flg_obbligo: false,
        flg_riservato: false,
      };
      this.tipiAllegato = [tipo];
      categoria = this.allegatoInEdit.tipologia_allegato.categoria_allegato;
      tipologia = this.tipiAllegato[0];
      // Gestisco la casistica di sola lettura data dall'esistenza della categoria
      this._readonlyCategoria = true;
      // #
    }

    this.editForm.get('categoria').setValue(categoria, { emitEvent: false });
    this.editForm.get('tipologia').setValue(tipologia, { emitEvent: false });
    this.editForm.get('riservato').setValue(this.allegatoInEdit.flg_riservato);
    this.editForm.get('nota').setValue(this.allegatoInEdit.note);
    this.editForm
      .get('numProtocollo')
      ?.setValue(this.allegatoInEdit.num_protocollo_allegato);
    this.editForm
      .get('dataProtocollo')
      ?.setValue(
        this.datePipe.transform(
          this.allegatoInEdit.data_protocollo_allegato,
          'yyyy-MM-dd'
        )
      );

    if (!this.isFrontOffice && tipologia?.tipologia_allegato?.flg_atto) {
      this.editForm.addControl(
        'numAtto',
        new FormControl(this.allegatoInEdit.num_atto, [
          Validators.maxLength(this.numAttoMaxLength),
        ])
      );
      this.editForm.addControl(
        'dataAtto',
        new FormControl(this.allegatoInEdit.data_atto)
      );
      this.editForm.addControl(
        'titolo',
        new FormControl(this.allegatoInEdit.titolo_allegato, [
          Validators.maxLength(this.titoloMaxLength),
        ])
      );
      this.editForm.addControl(
        'autore',
        new FormControl(this.allegatoInEdit.autore_allegato, [
          Validators.maxLength(this.autoreMaxLength),
        ])
      );
      this.showInfoAtto = true;
    }
    // il form segue il readonly calcolato
    if (this.readonly) {
      this.editForm.disable();
    } else {
      this.editForm.enable();
    }
  }

  onFormValueChanges() {
    this.editForm
      .get('categoria')
      .valueChanges.pipe(distinctUntilChanged(), takeUntil(this.destroy$))
      .subscribe((categoria) => {
        this.editForm.get('tipologia').reset();
        this.tipiAllegato = this.fullListTipiAllegato.filter(
          (tipo) =>
            tipo.tipologia_allegato.categoria_allegato.id_categoria_allegato ===
            categoria.id_categoria_allegato
        );
      });

    this.editForm
      .get('tipologia')
      .valueChanges.pipe(
        distinctUntilChanged(),
        filter((tipo) => !!tipo),
        takeUntil(this.destroy$)
      )
      .subscribe((tipo) => {
        this.editForm.get('riservato').setValue(tipo.flg_riservato);
        if (tipo.flg_riservato && this.isFrontOffice) {
          this.editForm.get('riservato').disable();
        } else {
          this.editForm.get('riservato').enable();
        }

        this.editForm.get('nota').clearValidators();
        if (tipo.flg_nota) {
          this.editForm
            .get('nota')
            .setValidators([
              Validators.required,
              Validators.maxLength(this.maxNote),
            ]);
        } else {
          this.editForm
            .get('nota')
            .setValidators([Validators.maxLength(this.maxNote)]);
        }
        this.editForm.get('nota').updateValueAndValidity();

        if (!this.isFrontOffice && tipo.tipologia_allegato.flg_atto) {
          if (!this.showInfoAtto) {
            this.editForm.addControl('numAtto', new FormControl(null));
            this.editForm.addControl('dataAtto', new FormControl(null));
          }
        } else {
          if (this.showInfoAtto) {
            this.editForm.removeControl('numAtto');
            this.editForm.removeControl('dataAtto');
          }
        }
        this.showInfoAtto =
          !this.isFrontOffice && tipo.tipologia_allegato.flg_atto;
      });
  }

  compareCategoria(c1: CategoriaAllegato, c2: CategoriaAllegato) {
    return c1 && c2 && c1.cod_categoria_allegato === c2.cod_categoria_allegato;
  }

  compareTipologia(t1: TipoAllegato, t2: TipoAllegato) {
    return (
      t1 &&
      t2 &&
      t1.tipologia_allegato?.cod_tipologia_allegato ===
        t2.tipologia_allegato?.cod_tipologia_allegato
    );
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
    if (!this.editForm.valid) {
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

    const tipologia = this.editForm.get('tipologia').value.tipologia_allegato;

    if (!this.isFrontOffice) {
      this.allegatoInEdit.num_atto =
        this.editForm.get('numAtto')?.value || null;
      this.allegatoInEdit.data_atto =
        this.editForm.get('dataAtto')?.value || null;
      this.allegatoInEdit.titolo_allegato =
        this.editForm.get('titolo')?.value || null;
      this.allegatoInEdit.autore_allegato =
        this.editForm.get('autore')?.value || null;
    } else if (this.allegatoInEdit.tipologia_allegato !== tipologia) {
      this.allegatoInEdit.num_atto = null;
      this.allegatoInEdit.data_atto = null;
      this.allegatoInEdit.titolo_allegato = null;
      this.allegatoInEdit.autore_allegato = null;
    }

    this.allegatoInEdit.tipologia_allegato = tipologia;
    this.allegatoInEdit.flg_riservato = this.editForm.get('riservato')?.value;
    this.allegatoInEdit.note = this.editForm.get('nota')?.value;

    if (this.oggAppBtnProtocolla) {
      this.allegatoInEdit.num_protocollo_allegato =
        this.editForm.get('numProtocollo').value;
      this.allegatoInEdit.data_protocollo_allegato = this.editForm.get(
        'dataProtocollo'
      ).value
        ? this.editForm.get('dataProtocollo').value + ' 00:00:00'
        : null;
    }

    this.activeModal.close(this.allegatoInEdit);
  }

  /**
   * Getter di comodo per il recuperodella sola lettura del form.
   * @returns boolean per il readonly
   */
  get readonly(): boolean {
    // Definisco le condizioni per il readonly
    const c1: boolean = this.visualizzazione;
    const c2: boolean = this._readonlyCategoria;

    // Ritorno l'insieme delle casistiche, ne basta una per rendere readonly la modale
    return c1 || c2;
  }

  /**
   * Getter che recupera il valore dall'oggetto.
   * @returns string con il valore recuperato.
   */
  get dataCaricamento(): string {
    // Recupero l'informazione dalla configurazione
    let v: string = this.allegatoInEdit?.data_upload;

    // Converto la data stringa in un moment
    const formatFrom = this.C_C.MOMENT_COMPLETE_YEAR_DASH_VIEW_FORMAT;
    const m = moment(v, formatFrom);
    // Formatto la data in modalità view completa
    const formatTo = this.C_C.MOMENT_DATE_COMPLETE_VIEW_FORMAT;
    const date: string = m.isValid()
      ? m.format(this.C_C.MOMENT_DATE_COMPLETE_VIEW_FORMAT)
      : v;

    // Genero dei log per monitorare il discorso date, perché non vorrei che ci fosse un problema di formattazione delle date in entrata e crei casino
    this._logger.log("Check data_upload format", v);
    this._logger.log("data_upload format used", formatFrom);
    this._logger.log("On data_upload formatted", date);
    this._logger.log("data_upload formatted with", formatTo);

    // Ritorno il valore
    return date;
  }

  /**
   * Getter che recupera il valore dall'oggetto.
   * @returns string con il valore recuperato.
   */
  get codiceElaborato(): string {
    // Recupero l'informazione dalla configurazione
    let v: string = this.allegatoInEdit?.cod_allegato ?? '';
    // Ritorno il valore
    return v;
  }

  /**
   * Getter che recupera il valore dall'oggetto.
   * @returns string con il valore recuperato.
   */
  get dimensione(): string {
    // Recupero l'informazione dalla configurazione
    const n: number = this.allegatoInEdit?.dimensione_upload;
    // Definisco la label da ritornare
    let fileSize: string = '';

    // Tento di convertire il dato
    try {
      // Calcolo il peso in megabyte
      let fileSizeMB: number = n / this.C_C.MEGAYTE_SIZE;
      // Formatto il dato
      fileSize = this._decimalPipe.transform(
        fileSizeMB,
        this.C_C.FILE_SIZE_FORMAT
      );
      // Aggiungo la dimensione descrittiva
      fileSize = `${fileSize} MB`;
      // #
    } catch (e) {}

    // Ritorno il valore
    return fileSize;
  }

  /**
   * Getter per il nome del documento.
   * @returns string con il nome del documento da visualizzare.
   */
  get documento(): string {
    // Recupero l'informazione dalla configurazione
    let v: string = this.allegatoInEdit?.nome_allegato ?? '';
    // Ritorno il valore
    return v;
  }
}
