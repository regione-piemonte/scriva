/*
* ========================LICENSE_START=================================
* 
* Copyright (C) 2025 Regione Piemonte
* 
* SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
* =========================LICENSE_END==================================
*/
/* tslint:disable:semicolon */
import { Component, EventEmitter, Input, OnInit, Output, TemplateRef } from '@angular/core';
import { AutoUnsubscribe } from '../../../../core';
import { TableColumn } from '../../models/column.model';
import { DataSource } from '../../models/data-source';
import { ServerPageableDataSource } from '../../models/server-pageable-data-source';
import { BaseRow } from '../../models/base-row.model';
import { LoadingService } from '../../../../theme/layouts/loading.services';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';

interface PagerEvent {
  page?: number;
  size?: number;
}

@UntilDestroy()
@Component({
  selector: 'app-table',
  templateUrl: './table.component.html'
})
export class TableComponent<T extends BaseRow>
  extends AutoUnsubscribe
  implements OnInit
{
  @Output() rowClick: EventEmitter<T> = new EventEmitter();
  // tslint:disable-next-line: no-output-native
  @Output() select: EventEmitter<T[]> = new EventEmitter();

  /**
   * Rige pre selezionate
   */
  @Input() selected: T[] = [];

  /**
   * Eventuale dettaglio della riga
   */
  @Input() rowDetail: TemplateRef<any>;

  /**
   * Eventuale riga da far vedere nel caso di nessuna row
   */
  @Input() emptyMessage = 'UTILS.TABLE.EMPTY';

  /**
   * Indica se mostrare il bordo della riga di dettaglio
   */
  @Input() rowDetailBorder = true;

  /**
   * Eventuale dettaglio della riga
   */
  @Input() tableClass = 'table';

  /**
   * Indica se visualizzare la header
   */
  @Input() showHeader = true;

  /**
   * Indica se visualizzare il paginatore
   */
  @Input() showPager = true;
  // tslint:disable-next-line: variable-name
  private _dataSource: DataSource<T> | ServerPageableDataSource<T>;
  // tslint:disable-next-line: variable-name
  private _columns: TableColumn[];
  /**
   * Questa funzione verrà usata per comparare le righe selezionate
   *
   * (`fn(x) === fn(y)` instead of `x === y`)
   */
  @Input() rowIdentity: (x: T) => string | number = (x: T) => {
    return x.uuid;
  };

  get dataSource(): DataSource<T> | ServerPageableDataSource<T> {
    return this._dataSource;
  }

  /**
   * Datasource della tabella
   */
  @Input() set dataSource(value: DataSource<T> | ServerPageableDataSource<T>) {
    // appena il datasource è valorizzato
    this._dataSource = value;
    // loading hide ogni che le righe cambiano
    this._initHideLoadingSubscribe();
  }

  get columns(): TableColumn[] {
    return this._columns;
  }

  /**
   * Colonne della tabella
   */
  @Input() set columns(value: TableColumn[]) {
    // apena le colonne sono valorizzate
    // si imposta il sorting di default a true
    this._columns = value.map((c) => ({
      ...c,
      sortable: c.sortable === undefined ? true : c.sortable
    }));
  }

  @Input() showEmptyMessage = true;

  ngOnInit() {
    // popolamento iniziale

    setTimeout(() => {
      this.dataSource?.refresh();
    });
  }

  constructor(private loadingService: LoadingService) {
    super();
  }

  // evento dal pager
  pagerChange(page: number) {
    // cambio pagina
    if (page) {
      this.loadingService.show();
      this.dataSource.paging(page);
    }
  }

  sizeChange(size: number) {
    // cambio size pagina
    if (size) {
      this.loadingService.show();
      this.dataSource.paging(1, size);
    }
  }

  checkboxToggleHeader(event: Event) {
    if ((event.target as HTMLInputElement).checked) {
      // aggiunge ai selezionati solo quelli della pagina corrente
      // facendo attenzione ai selezionati già presenti per evitare duplicati
      this.dataSource.rows.forEach((r) => {
        // inserisce tra i selezionati solo se non già presente
        if (
          this.selected.findIndex(
            (sr) => this.rowIdentity(sr) === this.rowIdentity(r)
          ) === -1
        ) {
          this.selected.push(r);
        }
      });
    } else {
      // elimina dai selezionati solo quelli della pagina corrente
      this.selected = [
        ...this.selected.filter(
          (sr) =>
            this.dataSource.rows.findIndex(
              (r) => this.rowIdentity(sr) === this.rowIdentity(r)
            ) === -1
        )
      ];
    }

    this.select.emit(this.selected);
  }

  checkboxToggleRow({ event, row }: { event: Event; row: T }) {
    if ((event.target as HTMLInputElement).checked) {
      this.selected.push(row);
    } else {
      this.selected = this.selected.filter(
        (r) => this.rowIdentity(r) !== this.rowIdentity(row)
      );
    }

    this.select.emit(this.selected);
  }

  private _initHideLoadingSubscribe(): void {
    this.dataSource.rows$.pipe(untilDestroyed(this)).subscribe(() => {
      this.loadingService.hide(500);
    });
  }
}
