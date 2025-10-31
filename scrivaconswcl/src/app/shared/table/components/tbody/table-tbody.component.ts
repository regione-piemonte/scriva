/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, EventEmitter, Input, Output, PipeTransform, TemplateRef } from '@angular/core';
import { BaseRow } from '../../models/base-row.model';
import { TableColumn } from '../../models/column.model';
import { DataSource } from '../../models/data-source';
import { ServerPageableDataSource } from '../../models/server-pageable-data-source';
import { DeepObjectPipe } from '../../pipes/deep-object.pipe';

@Component({
  // tslint:disable-next-line: component-selector
  selector: '[app-table-tbody]',
  styleUrls: ['./table-tbody.component.scss'],
  templateUrl: './table-tbody.component.html'
})
export class TableTbodyComponent<T extends BaseRow> {
  @Input() columns: TableColumn[];
  @Input() dataSource: DataSource<T> | ServerPageableDataSource<T>;

  @Output() rowClick: EventEmitter<T> = new EventEmitter();
  @Output() checkboxToggle: EventEmitter<{
    event: Event;
    row: T;
  }> = new EventEmitter();

  @Input() emptyMessage: string;
  @Input() rowDetail: TemplateRef<any>;
  @Input() rowDetailBorder: boolean;
  @Input() rowIdentity: (x: T) => string | number;
  @Input() selected: T[];
  @Input() showEmptyMessage = true;

  deepObj = new DeepObjectPipe().transform;

  onRowClick(row: T) {
    this.rowClick.emit(row);
  }

  /**
   * TODO: richiamare un metodo sul template non è performante
   * spostare la logica in una direttiva o su un pipe
   * @param row
   * @param column
   */
  getClass(row: T, column: TableColumn): string {
    if (typeof column.cellClass === 'function') {
      return column.cellClass(row);
    }

    if (typeof column.cellClass === 'string') {
      return column.cellClass;
    }

    return '';
  }

  /**
   * TODO: richiamare un metodo sul template non è performante
   * spostare la logica in una direttiva o su un pipe
   *
   * @param row
   * @param column
   */
  getValue(row: T, column: TableColumn): string {
    let value = this.deepObj(row, column.prop);
    const userPipe: PipeTransform = column.pipe;

    if (userPipe) {
      value = userPipe.transform(value);
    }

    return value;
  }

  checked(row: T): boolean {
    return (
      this.selected.findIndex(
        (r) => this.rowIdentity(r) === this.rowIdentity(row)
      ) > -1
    );
  }

  get accordionPrefix(): string {
    return 'accordion-table';
  }

  getAccordionDataTarget(index: number): string {
    return `#${this.getAccordionId(index)}`;
  }

  getAccordionId(index: number): string {
    return `${this.accordionPrefix}-${index}`;
  }
}
