/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BaseRow } from '../../models/base-row.model';
import { TableColumn } from '../../models/column.model';
import { DataSource } from '../../models/data-source';
import { ServerPageableDataSource } from '../../models/server-pageable-data-source';
import { TableSort } from '../../models/table-sort';

@Component({
  // tslint:disable-next-line: component-selector
  selector: '[app-table-thead]',
  templateUrl: './table-thead.component.html'
})
export class TableTheadComponent<T extends BaseRow> {
  @Input() columns: TableColumn[];
  @Input() dataSource: DataSource<T> | ServerPageableDataSource<T>;
  @Input() rowIdentity: (x: T) => string | number;
  @Input() selected: T[];

  @Output() checkboxToggle: EventEmitter<Event> = new EventEmitter();

  currentDirection(prop: TableColumn['prop']): TableSort['dir'] | '' {
    const column = this.dataSource.tableSort.find((s) => s.prop === prop);

    if (!column) {
      return '';
    }

    return column.dir;
  }

  /**
   * TODO: richiamare un metodo sul template non è performante
   * spostare la logica in una direttiva o su un pipe
   */
  checkboxChecked() {
    return this.hasSelectedOnPage() && this.isAllSelectedOnPage();
  }

  /**
   * TODO: richiamare un metodo sul template non è performante
   * spostare la logica in una direttiva o su un pipe
   */
  checkboxIndeterminate(): boolean {
    return this.hasSelectedOnPage() && !this.isAllSelectedOnPage();
  }

  /**
   * Controlla se sono presenti degli elementi selezionati nella pagina corrente
   */
  hasSelectedOnPage(): boolean {
    return this.dataSource?.rows?.some((r) =>
      this.selected?.some((sr) => this.rowIdentity(sr) === this.rowIdentity(r))
    );
  }

  /**
   * Controlla se gli elementi della pagina corrente sono tutti selezionati
   */
  isAllSelectedOnPage(): boolean {
    return this.dataSource?.rows?.every(
      (r) =>
        this.selected?.findIndex(
          (sr) => this.rowIdentity(sr) === this.rowIdentity(r)
        ) > -1
    );
  }

  sort(event: Event, column: TableColumn) {
    event.preventDefault();

    // primo sort
    let direction: TableSort['dir'] = 'asc';
    const columnSort = this.dataSource.tableSort.find(
      (s) => s.prop === column.prop
    );

    if (columnSort) {
      direction = columnSort.dir === 'asc' ? 'desc' : 'asc';
    }

    this.dataSource.sorting([
      {
        prop: column.prop,
        dir: direction
      }
    ]);
  }
}
