/*
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { compact } from 'lodash';

@Component({
  selector: 'app-recap-table',
  templateUrl: './recap-table.component.html',
  styleUrls: ['./recap-table.component.scss'],
})
export class RecapTableComponent implements OnInit, OnChanges {
  @Input() title = 'Riepilogo';
  @Input() showIcon = true;
  @Input() records = [];
  @Input() columns: {
    label: string;
    arrayProperty: string;
    separator: string;
    properties: string[];
    colStyles?: any;
  }[] = [];
  @Input() editEnabled = true;
  @Input() deleteEnabled = true;
  @Input() detailEnabled = false;
  @Input() downloadEnabled = false;
  @Input() selectionType = 'N';
  /** Input boolean con la configurazione per gestire la modale in sola lettura. Per default è: false. */
  @Input() readonly: boolean = false;
  @Input() propostaComuneDaGeo: boolean = false;
  @Input() defaultStructure: boolean = true;
  @Input() customTableCss: string = '';

  @Output() deleteRecord = new EventEmitter();
  @Output() editRecord = new EventEmitter();
  @Output() detailRecord = new EventEmitter();
  @Output() downloadRecord = new EventEmitter();
  @Output() selected = new EventEmitter();

  actionsEnabled = true;
  selectionColumn;

  showSelectAll;
  allSelected = false;
  disableCheckbox = false;
  enabledIndex = null;

  selectedRecords = [];

  @Input() compilante;
  @Input() tipoSoggetto;

  /** (objectIterated: any, ...params: any[]) => string; con la funzione che deve produrre la label per la cella della colonna. */
  updateLayoutLabel: (objectIterated: any, ...params: any[]) => string;

  constructor() {
    // Definisco la logica custom di esecuzione per la funzione di update layout label
    this.updateLayoutLabel = (o: any, ...params: any[]): string => {
      // Verifico l'input
      o = o ?? {};
      params = params ?? [];
      // Cerco di estrarre dai parametri, le proprietà per quanto riguarda il richiamo della pipe nell'html
      const p: string[] = params[0] ?? [];

      // Richiamo al funzione di estrazione della proprietà per ogni proprietà della lista in input
      let labels: string[] = p.map((property: string) => {
        // Verifico se la proprietà non rientri nella seguente verifica
        const validProperty: boolean = !(
          (property === 'ruoloCompilante.des_ruolo_compilante' &&
            this.tipoSoggetto === 'PF') ||
          (property === 'ruoloCompilante.des_ruolo_compilante' &&
            o?.anagraficaSoggetto?.cf === this.compilante?.cf_compilante)
        );

        // Verifico la condizione
        if (validProperty) {
          // Eseguo la funzione di estrazione del dato
          return this.byString(o, property);
          // #
        } else {
          // Proprietà non valida
          return '';
        }
      });

      // Vado a rimuovere dall'array delle labels tutte quelle label non definite
      labels = compact(labels);
      // Ritorno l'insieme delle label generate, separate da spazio vuoto
      return labels.join(' ');
      // #
    };
  }

  ngOnInit() {
    this.actionsEnabled =
      this.deleteEnabled || this.editEnabled || this.detailEnabled;

    switch (this.selectionType) {
      case 'S':
        this.selectionColumn = true;
        this.showSelectAll = false;
        this.disableCheckbox = true;
        break;
      case 'M':
        this.selectionColumn = true;
        this.showSelectAll = true;
        this.disableCheckbox = false;
        break;
      case 'N':
        this.selectionColumn = false;
        this.showSelectAll = false;
        break;
      case 'O':
        this.selectionColumn = true;
        this.showSelectAll = false;
        this.disableCheckbox = true;
        break;
      default:
        break;
    }

    // SCRIVA-1308
    if (this.propostaComuneDaGeo) {
      this.selectedRecords = [];
      this._initComuni();
    }
  }

  /**
   * ngOnChanges SCRIVA-1308
   * @param changes SimpleChanges
   */
  ngOnChanges(changes: SimpleChanges): void {
    // Recupero il possibile oggetto di changes: records
    const pnChanges = changes.records;
    // Verifico se è stato aggiornato: records agigungendo un comune
    if (
      this.propostaComuneDaGeo &&
      pnChanges &&
      !pnChanges.firstChange &&
      pnChanges.currentValue.length > pnChanges.previousValue.length
    ) {
      // Lancio l'aggiornamento pe selezionare ultimo comune aggiunto
      this._initComuni(false, true);
    }
  }

  /**
   * Aggiorno i comuni selezionati SCRIVA-1308
   * @param init boolean
   * @param onlyLastSelected  boolean
   */
  private _initComuni(init: boolean = true, onlyLastSelected: boolean = false) {
    this.records.forEach((value, index) => {
      if (
        value.ind_geo_provenienza != 'GEO' &&
        value.ind_geo_provenienza != 'GEOD'
      ) {
        if (
          onlyLastSelected &&
          this.records[this.records.length - 1] === value
        ) {
          this.selectedRecords.push(this.records[index]);
        } else if (!onlyLastSelected) {
          this.selectedRecords.push(this.records[index]);
        }
      }
    });
    this.allSelected =
      this.selectedRecords.length === this.records.length ? true : false;
    if (init) {
      this.selected.emit(this.selectedRecords);
    }
  }

  onEdit(record, index): void {
    // console.log('recap-table > onEdit', record);
    this.editRecord.emit({ record, index });
  }

  onDetail(record, index): void {
    // console.log('recap-table > onDetail', record);
    this.detailRecord.emit({ record, index });
  }

  onDelete(record, index): void {
    // console.log('recap-table > onDelete', record);
    this.deleteRecord.emit({ record, index });
  }

  onDownload(record, index): void {
    // console.log('recap-table > onDownload', record);
    this.downloadRecord.emit({ record, index });
  }

  onCheckboxAction(event, index) {
    if (event.target.checked) {
      this.selectedRecords.push(this.records[index]);
      if (this.selectionType === 'S') {
        this.enabledIndex = index;
      }
    } else {
      const position = this.selectedRecords.findIndex(
        (record) => record === this.records[index]
      );
      this.selectedRecords.splice(position, 1);
      this.enabledIndex = null;
      this.allSelected = false;
    }
    this.selected.emit(this.selectedRecords);
  }

  onSelectAll(event) {
    if (event.target.checked) {
      this.selectedRecords = [];
      this.selectedRecords.push(...this.records);
      this.allSelected = true;
    } else {
      this.selectedRecords = [];
      this.allSelected = false;
    }
    this.selected.emit(this.selectedRecords);
  }

  isSelected(index) {
    return this.selectedRecords.some(
      (record) => record === this.records[index]
    );
  }

  byString(o, s) {
    s = s.replace(/\[(\w+)\]/g, '.$1'); // convert indexes to properties
    s = s.replace(/^\./, ''); // strip a leading dot
    const a = s.split('.');
    for (let i = 0, n = a.length; i < n; ++i) {
      const k = a[i];
      if (k in o) {
        o = o[k];
      } else {
        return;
      }
    }
    return o;
  }

  showCustom(property, record, j) {
    return !(
      property === 'des_localita' &&
      !!record.ubicazione_oggetto[j]?.den_indirizzo
    );
  }
}
