/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import { Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ColumnMode } from '@swimlane/ngx-datatable';
import { NgxSpinnerService } from 'ngx-spinner';
import { IstanzaService, MessageService } from 'src/app/shared/services';
import { Istanza, NotaIstanza } from '../../models';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EditNotaModalComponent } from '../edit-nota-modal/edit-nota-modal.component';
import { ScrivaNoteService } from '../../services/scriva-note/scriva-note.service';
import * as moment from 'moment';
import { AllegatiService } from 'src/app/features/ambito/services';

enum ColumnLabels {
  COGNOME = 'Cognome',
  NOME = 'Nome',
  NOTA = 'Nota',
  DATA_INS = 'Data inserimento',
  NOTA_R = 'Nota riservata'
}
@Component({
  selector: 'scriva-note',
  templateUrl: './scriva-note.component.html',
  styleUrls: ['./scriva-note.component.scss'],
})
export class ScrivaNoteComponent implements OnInit {
  /** Output che definisce l'evento passato verso alto */
  @Output() emit = new EventEmitter<any>();
  /** Output NotaIstanza con le informazioni della nota inserita. */
  @Output() onNotaInserted$ = new EventEmitter<NotaIstanza>();
  /** Output NotaIstanza con le informazioni della nota cancellata. */
  @Output() onNotaDeleted$ = new EventEmitter<NotaIstanza>();

  @Input() noteIstanza: NotaIstanza[] = [];
  @Input() idIstanza: number;
  @Input() identIstanza: string;

  @Input() notSaveBE: boolean = true;

  @ViewChild('dataTemplate') dataTemplate: TemplateRef<any>;
  @ViewChild('flgRiservataTemplate') flgRiservataTemplate: TemplateRef<any>;
  @ViewChild('azioniTemplate') azioniTemplate: TemplateRef<any>;

  noteTableColumns = [];
  dettaglioFormNote: FormGroup;
  ColumnMode = ColumnMode;

  istanza: Istanza;
  denTitolareIstanza: string;
  disableBtn: boolean;
  maxNote = 1000;

  columnsCsv: any[] = [
    {
      prop: 'funzionario.cognome_funzionario',
      name: ColumnLabels.COGNOME,
      getter: {
        transform: (value: NotaIstanza) =>
          value.funzionario?.cognome_funzionario
            ? value.funzionario.cognome_funzionario
            : '',
      },
    },
    {
      prop: 'funzionario.nome_funzionario',
      name: ColumnLabels.NOME,
      getter: {
        transform: (value: NotaIstanza) =>
          value.funzionario?.nome_funzionario
            ? value.funzionario.nome_funzionario
            : '',
      },
    },
    {
      prop: 'des_nota',
      name: ColumnLabels.NOTA,
      getter: {
        transform: (value: NotaIstanza) =>
          value?.des_nota ? value.des_nota.replace(/\n/g, ' ') : '',
      },
    },
    {
      prop: 'data_nota',
      name: ColumnLabels.DATA_INS,
      formatDate: {
        transform: (value: NotaIstanza) =>
          moment(value.data_nota).format('DD/MM/YYYY - HH:mm:ss'),
      },
    },
    {
      prop: 'ind_visibile',
      name: ColumnLabels.NOTA_R,
      getter: {
        transform: (value: any) =>
          value.ind_visibile === 'BO' ? 'Riservata' : '',
      },
    },
  ];

  /**
   * Costruttore.
   */
  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    private modalService: NgbModal,
    private scrivaNoteService: ScrivaNoteService,
    private spinner: NgxSpinnerService,
    private allegatiService: AllegatiService
  ) {}

  /**
   * NgOnInit.
   */
  ngOnInit() {
    this.buildForm();
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.noteTableColumns = [
        {
          name: 'Cognome',
          minWidth: 100,
          prop: 'funzionario.cognome_funzionario',
        },
        { name: 'Nome', minWidth: 100, prop: 'funzionario.nome_funzionario' },
        { name: 'Nota', minWidth: 200, prop: 'des_nota' },
        {
          name: 'Data inserimento',
          minWidth: 100,
          cellTemplate: this.dataTemplate,
          prop: 'data_nota',
        },
        {
          name: 'Nota riservata',
          minWidth: 100,
          cellTemplate: this.flgRiservataTemplate,
          prop: 'ind_visibile',
        },
        {
          name: 'Azioni',
          sortable: false,
          minWidth: 90,
          maxWidth: 120,
          resizable: false,
          cellTemplate: this.azioniTemplate,
        },
      ];
    });
  }

  buildForm() {
    this.dettaglioFormNote = this.fb.group({
      notaIstanza: [null, Validators.maxLength(this.maxNote)],
    });
  }

  onAggiungiNota() {
    const nuovaNota: NotaIstanza = {
      id_istanza: this.idIstanza,
      des_nota: this.dettaglioFormNote.get('notaIstanza').value,
    };

    this.spinner.show();
    this.scrivaNoteService.saveNotaIstanza(nuovaNota, this.notSaveBE).subscribe(
      (res) => {
        this.noteIstanza.push(res);
        this.noteIstanza = [...this.noteIstanza];
        this._updateNote();
        this.onNotaInserted$.emit(res);
        this.dettaglioFormNote.get('notaIstanza').reset();
        this.spinner.hide();
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'dettaglioContainer',
            false
          );
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  onRimuoviNota(gestUID) {
    this.spinner.show();
    this.scrivaNoteService.deleteNotaIstanza(gestUID, this.notSaveBE).subscribe(
      (res) => {
        const i = this.noteIstanza.findIndex(
          (nota) => nota.gestUID === gestUID
        );

        // Recupero l'oggetto della nota prima di cancellarlo per emetterlo tramite evento
        const notaCancellata = this.noteIstanza[i];
        this.onNotaDeleted$.emit(notaCancellata);

        this.noteIstanza.splice(i, 1);
        this.noteIstanza = [...this.noteIstanza];
        this._updateNote();
        this.spinner.hide();
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'dettaglioContainer',
            false
          );
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  showDetails(row: NotaIstanza, onlyRead: boolean) {
    this.disableBtn = onlyRead;

    const modalRef = this.modalService.open(EditNotaModalComponent, {
      centered: true,
      scrollable: true,
      backdrop: 'static',
      size: 'xl',
    });

    modalRef.componentInstance.notaInEdit = row;
    modalRef.componentInstance.disableBtn = this.disableBtn;
    modalRef.componentInstance.maxNote = this.maxNote;

    modalRef.result
      .then((nota) => {
        this.spinner.show();
        this.updateNota(nota);
        console.log('result then modal -->', nota);
      })
      .catch();
  }

  setNotaRiservataFlag(idNota, checked) {
    const nota = this.noteIstanza.find(
      (elem) => elem.id_nota_istanza === idNota
    );
    nota.ind_visibile = checked ? 'BO' : 'FO_BO_PUBWEB';
    this.spinner.show();
    this.updateNota(nota);
  }

  updateNota(nota) {
    this.scrivaNoteService.saveNotaIstanza(nota, this.notSaveBE).subscribe(
      (res) => {
        this.noteIstanza = [...this.noteIstanza];
        this._updateNote();
        this.spinner.hide();
      },
      (err) => {
        if (err.error?.code) {
          this.messageService.showMessage(
            err.error.code,
            'dettaglioContainer',
            false
          );
        } else {
          this.messageService.showMessage('E100', 'dettaglioContainer', true);
        }
      }
    );
  }

  exportCSV() {
    console.log('NOTE--> ', this.noteIstanza);

    let note: any[] = this.noteIstanza;
    let columns = [...this.columnsCsv];

    this.allegatiService.createCSV(
      note,
      columns,
      `Elenco_note_procedimento_${this.identIstanza}_${new Date().toLocaleDateString()}`
    );
  }

  /**
   * Propago evento verso alto verso componente che contiene il Note
   * @param ev stringa che dice quale evento deve essere emesso
   */
  clickEmit(ev: string) {
    this.emit.next(ev);
  }

  private _updateNote() {
    // serve per tenere in pancia le note solo se non sto salvando immediatamente
    if (this.notSaveBE) {
      const ev = {
        action: 'onUpdateNote',
        args: [...this.noteIstanza],
      };
      this.emit.next(ev);
    }
  }
}
